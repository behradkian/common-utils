package ir.baarmaan.utility.net;

import org.apache.cxf.configuration.jsse.TLSClientParameters;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;


public class SSLConnector {
    protected static final Logger logger = LoggerFactory.getLogger(SSLConnector.class);

    private final String url;
    private final String SSLPassword;
    private final String keyPath;

    public SSLConnector(String url, String SSLPassword, String keyPath ){
        this.url = url;
        this.SSLPassword = SSLPassword;
        this.keyPath = keyPath;
    }
    public <T> T getService(Class<T> clazz)
            throws  IOException {
        String endPointUrl=url;
        String trustPass=SSLPassword;
        byte[] trustStoreFile= getKeyData(keyPath);
        logger.info("SSLConnector -- start of getService()");

        JaxWsProxyFactoryBean factoryBean = new JaxWsProxyFactoryBean();
        factoryBean.setAddress(endPointUrl);
        factoryBean.setServiceClass(clazz);
        Map<String, Object> properties = factoryBean.getProperties();
        if (properties == null) {
            properties = new HashMap<>();
        }
        properties.put(Constant.HEADER_JAXB, Constant.FALSE);
        properties.put(Constant.HEADER_CXF, true);
        properties.put(Constant.HEADER_MTOM, Boolean.TRUE);
        factoryBean.setProperties(properties);
        T proxyObject = (T) factoryBean.create();
        if (proxyObject == null) {
            throw new NullPointerException("Can not create ServiceBean Object. ServiceBean Object is null");
        }
        Client client = ClientProxy.getClient(proxyObject);
        HTTPConduit httpConduit = (HTTPConduit) client.getConduit();
        applyTwoWayBindingSSL(httpConduit, trustStoreFile, trustPass);
        HTTPClientPolicy policy = httpConduit.getClient();
        logger.info("SSLConnector -- End of getService()");
        return proxyObject;
    }


    private void applyTwoWayBindingSSL(HTTPConduit httpConduit, byte[] trustStoreFile, String trustPass) {
        logger.info("SSLConnector -- start of applyTwoWayBindingSSL");
        try {
            httpConduit.setTlsClientParameters(new TLSClientParameters());
            TLSClientParameters tlsParams = httpConduit.getTlsClientParameters();
            tlsParams.setDisableCNCheck(true);

            KeyStore keyStore = KeyStore.getInstance(Constant.STORE_JKS_TYPE);//JKS key store type
            InputStream inputStream = new ByteArrayInputStream(trustStoreFile);
            keyStore.load(inputStream, trustPass.toCharArray());
            KeyManagerFactory factory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            factory.init(keyStore, trustPass.toCharArray());
            KeyManager[] keyManagers = factory.getKeyManagers();
            httpConduit.getTlsClientParameters().setKeyManagers(keyManagers);
            TrustManagerFactory trustFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustFactory.init(keyStore);
            TrustManager[] tm = trustFactory.getTrustManagers();
            tlsParams.setTrustManagers(tm);
            httpConduit.getTlsClientParameters().setKeyManagers(keyManagers);
            trustingAllSslCertificates(httpConduit, keyManagers);

        } catch (Exception e) {
            logger.error(" SSLConnector -- Error on of applyTwoWayBindingSSL() with message : {} {}", e.getMessage(),e.getStackTrace());
        }
    }


    public static void trustingAllSslCertificates(HTTPConduit httpConduit, KeyManager[] keyManagers) throws KeyManagementException, NoSuchAlgorithmException {
        logger.info("SSLConnector -- start of trustingAllSslCertificates()");
        TLSClientParameters tlsParams = httpConduit.getTlsClientParameters();
        tlsParams.setDisableCNCheck(true);
        tlsParams.getSSLSocketFactory();

        TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {

                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] certs, String authType) throws CertificateException {
                    }

                    @Override
                    public void checkClientTrusted(X509Certificate[] certs, String authType) throws CertificateException {
                    }
                }
        };

    }

    private byte[] getKeyData(String path) throws IOException {
        logger.info("SSLConnector -- start getKeyStore loading");
        Path dir = Paths.get(path);
        byte[] keyData = Files.readAllBytes(dir);
        logger.info("SSLConnector -- file has been read !");
        return keyData;
    }

}

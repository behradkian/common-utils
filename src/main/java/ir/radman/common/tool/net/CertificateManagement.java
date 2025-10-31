package ir.radman.common.tool.net;

import org.apache.cxf.configuration.jsse.TLSClientParameters;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.transport.http.HTTPConduit;
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


public class CertificateManagement {

    private static final Logger LOGGER = LoggerFactory.getLogger(CertificateManagement.class);

    public WebClient createTrustedWebClient(WebClient webClient , String certificateFilePath , String certificatePassword) throws Exception {
        HTTPConduit httpConduit = WebClient.getConfig(webClient).getHttpConduit();
        Path dir = Paths.get(certificateFilePath, new String[0]);
        byte[] trustStoreFile = Files.readAllBytes(dir);
        applyTwoWayBindingSSL(httpConduit, trustStoreFile, certificatePassword);
        return webClient;
    }


    private void applyTwoWayBindingSSL(HTTPConduit httpConduit, byte[] trustStoreFile, String trustPass)  {

        try {
            httpConduit.setTlsClientParameters(new TLSClientParameters());
            TLSClientParameters tlsParams = httpConduit.getTlsClientParameters();
            tlsParams.setDisableCNCheck(true);

            KeyStore keyStore = KeyStore.getInstance("JKS");
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

            e.printStackTrace();
        }
    }


    private void trustingAllSslCertificates(HTTPConduit httpConduit, KeyManager[] keyManagers) throws KeyManagementException, NoSuchAlgorithmException {

        TLSClientParameters tlsParams = httpConduit.getTlsClientParameters();
        tlsParams.setDisableCNCheck(true);
        tlsParams.getSSLSocketFactory();

        TrustManager[] trustAllCerts = {new X509TrustManager() {

            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }


            public void checkServerTrusted(X509Certificate[] certs, String authType) throws CertificateException {
            }


            public void checkClientTrusted(X509Certificate[] certs, String authType) throws CertificateException {
            }
        }};
    }

    private byte[] getKeyData(String path) throws IOException {
        Path dir = Paths.get(path, new String[0]);
        return  Files.readAllBytes(dir);
    }
}
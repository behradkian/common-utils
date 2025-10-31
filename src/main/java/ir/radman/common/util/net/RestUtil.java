package ir.radman.common.util.net;

import ir.radman.common.general.dto.net.RestResponseDto;
import ir.radman.common.general.enumeration.net.ContentType;
import ir.radman.common.general.enumeration.net.Protocols;
import ir.radman.common.general.exception.net.RestCallException;
import jakarta.ws.rs.core.MediaType;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import static ir.radman.common.util.net.Constant.*;

/**
 * this class is utils use for calling rest service.
 * in this class use WebClient Library to rest call service.
 *
 * @function postJsonService: this function use for POST service
 * @function getJsonService: this function use for GET service
 * @function postFormService: this function is used for services that use ContentType APPLICATION_X_WWW_FORM_URLENCODED
 */

public class RestUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestUtil.class);

    public RestResponseDto postJsonService(String url, String input, Map<String, String> headers, Protocols protocol, String certificateFilePath, String certificatePassword) throws IOException {
        LOGGER.debug("function postJsonService in class RestUtil is started with parameters url: {} , input: {} , header: {}", url, input, headers);
        WebClient webClient = createWebClient(url);
        webClient.accept(MediaType.APPLICATION_JSON);
        webClient.type(ContentType.APPLICATION_JSON.getValue());
        if (protocol != null && protocol == Protocols.HTTPS)
            webClient = setCertificate(webClient, certificateFilePath, certificatePassword);
        setHeaders(webClient, headers);
        LOGGER.debug("going to call service with post method");
        jakarta.ws.rs.core.Response response = webClient.post(input);
        LOGGER.debug("response of service is: {}", response);
        return readResponseService(response);
    }

    public RestResponseDto getJsonService(String url, Map<String, String> headers, Map<String, String> params, Protocols protocol, String certificateFilePath, String certificatePassword) throws IOException {
        LOGGER.debug("function getJsonService in class RestUtil is started with parameters url: {} , header: {}", url, headers);
        WebClient webClient = createWebClient(url);
        webClient.type(ContentType.APPLICATION_JSON.getValue());
        if (protocol != null && protocol == Protocols.HTTPS)
            webClient = setCertificate(webClient, certificateFilePath, certificatePassword);
        setHeaders(webClient, headers);
        setQueryParam(webClient, params);
        LOGGER.debug("going to call service with get method");
        jakarta.ws.rs.core.Response response = webClient.get();
        LOGGER.debug("response of service is: {}", response);
        RestResponseDto responseUtil = readResponseService(response);
        return responseUtil;
    }

    public RestResponseDto getJsonService(String url, Map<String, String> headers, Protocols protocol, String certificateFilePath, String certificatePassword) throws IOException {
        return getJsonService(url, headers, null, protocol, certificateFilePath, certificatePassword);
    }


    public RestResponseDto postFormService(String url, Map<String, String> input, Map<String, String> headers, Protocols protocol, String certificateFilePath, String certificatePassword) throws IOException {
        LOGGER.debug("function postFormService in class RestUtil is started with parameters url: {} , input: {} , header: {}", url, input, headers);
        WebClient webClient = createWebClient(url);
        webClient.type(ContentType.APPLICATION_X_WWW_FORM_URLENCODED.getValue());
        if (protocol != null && protocol == Protocols.HTTPS)
            webClient = setCertificate(webClient, certificateFilePath, certificatePassword);
        setHeaders(webClient, headers);
        jakarta.ws.rs.core.Form form = new jakarta.ws.rs.core.Form();
        for (Map.Entry<String, String> entry : input.entrySet())
            form.param(entry.getKey(), entry.getValue());
        LOGGER.debug("going to call service with form method");
        jakarta.ws.rs.core.Response response = webClient.form(form);
        LOGGER.debug("response of service is: {}", response);
        RestResponseDto responseUtil = readResponseService(response);
        return responseUtil;
    }

    private WebClient createWebClient(String url) {
        LOGGER.debug("function createWebClient in class RestUtil is started with parameter url: {}", url);
        WebClient client = WebClient.create(url);
        client.acceptEncoding("UTF-8");
        return client;
    }

    private WebClient setCertificate(WebClient webClient, String certificateFilePath, String certificatePassword) {
        LOGGER.debug("function setCertificate in class RestUtil is started with parameter certificateFilePath: {}", certificateFilePath);
        CertificateManagement certificateManagement = new CertificateManagement();
        if (certificateFilePath == null || certificateFilePath.isEmpty()) {
            LOGGER.error("in protocol HTTPS Certificate File Path is important ");
            throw new RestCallException("in protocol HTTPS Certificate File Path is important ");
        }
        if (certificatePassword == null || certificatePassword.isEmpty()) {
            LOGGER.error("in protocol HTTPS Certificate Password is important ");
            throw new RestCallException("in protocol HTTPS Certificate Password is important ");
        }
        try {
            webClient = certificateManagement.createTrustedWebClient(webClient, certificateFilePath, certificatePassword);
            return webClient;
        } catch (Exception e) {
            LOGGER.error("Exception in set Certificate to request with message: {} " + e.getMessage());
            throw new RestCallException("Exception in set Certificate to request with message: " + e.getMessage());
        }

    }

    private WebClient setHeaders(WebClient webClient, Map<String, String> headers) {
        LOGGER.debug("function setHeaders in class RestUtil is started ");
        if (headers != null && headers.size() > 0)
            for (String headerKey : headers.keySet())
                webClient.header(headerKey, headers.get(headerKey));
        return webClient;
    }

    private WebClient setQueryParam(WebClient webClient, Map<String, String> queryParam) {
        LOGGER.debug("function setHeaders in class RestUtil is started ");
        if (queryParam != null && queryParam.size() > 0)
            for (String param : queryParam.keySet())
                webClient.query(param, queryParam.get(param));
        return webClient;
    }

    private RestResponseDto readResponseService(jakarta.ws.rs.core.Response response) throws IOException {
        LOGGER.debug("function readResponseService in class RestUtil is started ");
        RestResponseDto responseUtil = new RestResponseDto();
        responseUtil.setStatusCode(response.getStatus());
        if (response.getEntity() != null) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader((InputStream) response.getEntity()));
            StringBuilder stringBuilder = new StringBuilder();
            String output = "";
            while ((output = bufferedReader.readLine()) != null) {
                stringBuilder.append(output);
            }
            responseUtil.setBody(stringBuilder.toString());
        }
        LOGGER.debug("function readResponseService in class RestUtil is end with return value: {} ", responseUtil);
        return responseUtil;
    }

    private RestResponseDto callGetService(String url, String contentType, Object input, Map<String, String> headers, Protocols protocol) {
        return null;
    }

    public void io() {

        try {

            URL url = new URL("http://localhost:8080/RESTfulExample/json/product/get");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String output;
            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                System.out.println(output);
            }

            conn.disconnect();

        } catch (MalformedURLException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        }

    }

    public void post() {

        try {

            URL url = new URL("http://localhost:8080/RESTfulExample/json/product/post");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");

            String input = "{\"qty\":100,\"name\":\"iPad 4\"}";

            OutputStream os = conn.getOutputStream();
            os.write(input.getBytes());
            os.flush();

            if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String output;
            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                System.out.println(output);
            }

            conn.disconnect();

        } catch (MalformedURLException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        }

    }

    public static void main(String[] args) throws IOException {

        HttpClient client = HttpClientBuilder.create().build();
        HttpUriRequest httpUriRequest = new HttpGet("URL");

       // HttpResponse response = (HttpResponse) client.execute(httpUriRequest);
    }

    private static String callServiceForGetToken(String url, String scope) throws IOException {

        String note;
        String result;
        URL requestURL = new URL(url);
        HttpURLConnection httpURLConnection = (HttpURLConnection) requestURL.openConnection();

        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

        String body = "scope=" + scope + "&grant_type=client_credentials";
        byte[] byteBody = body.getBytes(StandardCharsets.UTF_8);
        httpURLConnection.setRequestProperty("Content-Length", Integer.toString(byteBody.length));
        httpURLConnection.setRequestProperty(CHARSET, UTF8);
        httpURLConnection.setRequestProperty(AUTH, BASIC + "");
        httpURLConnection.setUseCaches(false);
        httpURLConnection.setDoInput(true);
        httpURLConnection.setDoOutput(true);

        try (DataOutputStream dataOutputStream = new DataOutputStream(httpURLConnection.getOutputStream())) {

            dataOutputStream.write(byteBody);

            try (BufferedReader br = new BufferedReader(new InputStreamReader((httpURLConnection.getInputStream()), UTF8))) {

                StringBuilder response = new StringBuilder();
                String responseLine = null;

                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                result = response.toString();
            }
            return result;

        } catch (Exception e) {

            note = String.format("in InquiryService.callServiceForGetToken() : %s", e.getMessage());
            LOGGER.error(note);
            throw new RuntimeException(note);

        } finally {
            httpURLConnection.disconnect();
        }

    }

    private static String callGetServiceByBearerToken(String url, String token) throws IOException {

        String note;
        String result;
        URL requestURL = new URL(url);
        HttpURLConnection httpURLConnection = (HttpURLConnection) requestURL.openConnection();

        httpURLConnection.setRequestMethod("GET");
        httpURLConnection.setRequestProperty(CHARSET, UTF8);
        httpURLConnection.setRequestProperty(AUTH, BEARER + token);
        httpURLConnection.setUseCaches(false);
        httpURLConnection.setDoInput(true);
        httpURLConnection.setDoOutput(true);


        try (BufferedReader br = new BufferedReader(new InputStreamReader((httpURLConnection.getInputStream()), UTF8))) {

            StringBuilder response = new StringBuilder();
            String responseLine = null;

            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            result = response.toString();

            return result;

        } catch (Exception e) {

            note = String.format("in InquiryService.callGetServiceByBearerToken() : %s", e.getMessage());
            LOGGER.error(note);
            throw new RuntimeException(note);

        } finally {
            httpURLConnection.disconnect();
        }

    }

    private static String callPostServiceByBearerToken(String url, String token, String body) throws IOException {

        String note;
        String result;
        URL requestURL = new URL(url);
        HttpURLConnection httpURLConnection = (HttpURLConnection) requestURL.openConnection();

        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setRequestProperty("Content-Type", "application/json");
        httpURLConnection.setRequestProperty(CHARSET, UTF8);
        httpURLConnection.setRequestProperty(AUTH, BEARER + token);
        httpURLConnection.setUseCaches(false);
        httpURLConnection.setDoInput(true);
        httpURLConnection.setDoOutput(true);


        try (DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream())) {

            byte[] byteBody = body.getBytes(StandardCharsets.UTF_8);
            wr.write(byteBody);

            try (BufferedReader br = new BufferedReader(new InputStreamReader((httpURLConnection.getInputStream()), UTF8))) {

                StringBuilder response = new StringBuilder();
                String responseLine = null;

                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                result = response.toString();
            }

            return result;

        } catch (Exception e) {

            note = String.format("in InquiryService.callPostServiceByBearerToken() : %s", e.getMessage());
            LOGGER.error(note);
            throw new RuntimeException(note);

        } finally {
            httpURLConnection.disconnect();
        }

    }

}

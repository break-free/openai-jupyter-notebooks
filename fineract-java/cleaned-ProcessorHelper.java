
package org.apache.fineract.infrastructure.hooks.processor;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import okhttp3.OkHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
@Service
public final class ProcessorHelper {
    private static final Logger LOG = LoggerFactory.getLogger(ProcessorHelper.class);
    @SuppressWarnings("unused")
    private static final X509TrustManager insecureX509TrustManager = new X509TrustManager() {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {}
        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {}
        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[] {};
        }
    };
    private final boolean insecureHttpClient = Boolean.getBoolean("fineract.insecureHttpClient");
    private final SSLContext insecureSSLContext;
    public ProcessorHelper() throws KeyManagementException, NoSuchAlgorithmException {
        if (insecureHttpClient) {
            insecureSSLContext = createInsecureSSLContext();
        } else {
            insecureSSLContext = null;
        }
    }
    private OkHttpClient createClient() {
        var okBuilder = new OkHttpClient.Builder();
        if (insecureHttpClient) {
            configureInsecureClient(okBuilder);
        }
        return okBuilder.build();
    }
    private void configureInsecureClient(final OkHttpClient.Builder okBuilder) {
        okBuilder.sslSocketFactory(insecureSSLContext.getSocketFactory(), insecureX509TrustManager);
        HostnameVerifier insecureHostnameVerifier = (hostname, session) -> true;
        okBuilder.hostnameVerifier(insecureHostnameVerifier);
    }
    private SSLContext createInsecureSSLContext() throws NoSuchAlgorithmException, KeyManagementException {
        SSLContext insecureSSLContext = SSLContext.getInstance("TLS"); 
        insecureSSLContext.init(null, new TrustManager[] { insecureX509TrustManager }, new SecureRandom());
        return insecureSSLContext;
    }
    @SuppressWarnings("rawtypes")
    public Callback createCallback(final String url) {
        return new Callback() {
            @Override
            public void onResponse(@SuppressWarnings("unused") Call call, retrofit2.Response response) {
                LOG.info("URL: {} - Status: {}", url, response.code());
            }
            @Override
            public void onFailure(@SuppressWarnings("unused") Call call, Throwable t) {
                LOG.error("URL: {} - Retrofit failure occured", url, t);
            }
        };
    }
    public WebHookService createWebHookService(final String url) {
        final OkHttpClient client = createClient();
        final Retrofit.Builder retrofitBuilder = new Retrofit.Builder();
        retrofitBuilder.baseUrl(url);
        retrofitBuilder.client(client);
        retrofitBuilder.addConverterFactory(GsonConverterFactory.create());
        final Retrofit retrofit = retrofitBuilder.build();
        return retrofit.create(WebHookService.class);
    }
    @SuppressWarnings("rawtypes")
    public Callback createCallback(final String url, String payload) {
        return new Callback() {
            @Override
            public void onResponse(@SuppressWarnings("unused") Call call, retrofit2.Response response) {
                LOG.info("URL: {} - Status: {}", url, response.code());
            }
            @Override
            public void onFailure(@SuppressWarnings("unused") Call call, Throwable t) {
                LOG.error("URL: {} - Retrofit failure occured", url, t);
            }
        };
    }
}

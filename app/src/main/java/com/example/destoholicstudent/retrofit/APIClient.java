package com.example.destoholicstudent.retrofit;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {

    public static final String BASE_URL = "https://destoholic.com/";
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();

        HttpLoggingInterceptor.Level logLevel = HttpLoggingInterceptor.Level.BODY;
        logging.setLevel(logLevel);
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(getUnsafeOkHttpClient().addNetworkInterceptor(logging).build())
                    .build();
        }
        return retrofit;
    }

    @NonNull
    public static OkHttpClient.Builder getUnsafeOkHttpClient() {

        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                        }

                        @NonNull
                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient.Builder builder = new OkHttpClient.Builder()
                    .readTimeout(120, TimeUnit.SECONDS)
                    .connectTimeout(120, TimeUnit.SECONDS);

            builder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);
            builder.hostnameVerifier(new HostnameVerifier() {
                @SuppressLint("BadHostnameVerifier")
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
            builder.addInterceptor(new Interceptor() {
                @NotNull
                @Override
                public Response intercept(@NotNull Chain chain) throws IOException {
                    Request.Builder requestBuilder = chain.request().newBuilder();
                    requestBuilder.addHeader("Content-Type", "application/json");
                    Response response = chain.proceed(requestBuilder.build());

                    if (response != null) {
//                        Debug.trace("this is responsess" + response.toString());
                        Log.d("APIClient","this is responsess code" + response.code());
                        Log.d("APIClient","this is responsess issuccess" + response.isSuccessful());
                        Log.d("APIClient","this is responsess :: " + response);
                    }

                    return response;

                    /*String fileName = "demo" + ".pdf";
                    FileCreate fileCreate = new FileCreate(BaseApplication.parent);
                    File file = new File(fileCreate.getDirectoryPath(FileCreate.folder_Barcode_Lab), fileName);

                    InputStream inputStream = null;
                    OutputStream outputStream = null;

                    try {
                        byte[] fileReader = new byte[4096];

                        long fileSize = response.body().contentLength();
                        long fileSizeDownloaded = 0;

                        inputStream = response.body().byteStream();
                        outputStream = new FileOutputStream(file);

                        while (true) {
                            int read = inputStream.read(fileReader);

                            if (read == -1) {
                                break;
                            }

                            outputStream.write(fileReader, 0, read);

                            fileSizeDownloaded += read;

                            Log.d("LabReportAdapterHMIS", "file download: " + fileSizeDownloaded + " of " + fileSize);
                        }

                        outputStream.flush();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    String responseobj = new String(response.body().bytes());


                    return response.newBuilder().body(ResponseBody.create(responseobj, response.body().contentType())).build();*/
                }
            });
            return builder;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
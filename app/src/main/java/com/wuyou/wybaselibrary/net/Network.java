package com.wuyou.wybaselibrary.net;

import android.util.Log;

import com.wuyou.wybaselibrary.Factory.Factory;
import com.wuyou.wybaselibrary.utils.SPUtil;

import java.io.IOException;
import java.util.HashSet;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.wuyou.wybaselibrary.Constant.BASE_URL;

public class Network {
    private static Network instance;
    private OkHttpClient client;
    private Retrofit retrofit;

    static {
        instance = new Network();
    }

    private Network() {
    }

    public static OkHttpClient getClient() {
        if (instance.client != null)
            return instance.client;

        // 存储起来
        instance.client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                // 给所有的请求添加一个拦截器
                .addInterceptor(new ReceivedCookiesInterceptor())
                .addInterceptor(new AddCookiesInterceptor())
                .build();
        return instance.client;
    }

    //构建一个retrofit
    private static Retrofit getRetrofit() {
        if (instance.retrofit != null)
            return instance.retrofit;

        OkHttpClient client = getClient();
        Retrofit.Builder builder = new Retrofit.Builder();
        instance.retrofit = builder.baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(Factory.getGson()))
                .build();
        return instance.retrofit;

    }

    /**
     * 返回一个请求代理
     *
     * @return RemoteService
     */
    public static RemoteService remote() {
        return Network.getRetrofit().create(RemoteService.class);
    }


    /**
     * 处理Response中的Cookie
     */
    private static class ReceivedCookiesInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Response originalResponse = chain.proceed(chain.request());

            if (!originalResponse.headers("Set-Cookie").isEmpty()) {

                HashSet<String> cookies = new HashSet<>(
                        originalResponse.headers("Set-Cookie"));
                SPUtil.putCookie(SPUtil.SP_COOKIE, cookies);
            }

            return originalResponse;
        }
    }

    /**
     * 每次Request带上Cookie
     */
    private static class AddCookiesInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request.Builder builder = chain.request().newBuilder();
            HashSet<String> cookies = SPUtil.getCookie(SPUtil.SP_COOKIE, null);
            if (cookies != null) {
                for (String cookie : cookies) {
                    builder.addHeader("Cookie", cookie);
                    Log.v("OkHttp", "Adding Header: " + cookie);
                }
            }
            return chain.proceed(builder.build());
        }
    }
}

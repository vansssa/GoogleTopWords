package com.utapass.topwords;

import com.utapass.topwords.api.APIInterface;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@PowerMockIgnore("javax.net.ssl.*")
@RunWith(PowerMockRunner.class)
public class ApiUnitTest {

    @Test
    public void verifyAPISize() throws Exception {
        String url = "http://hawttrends.appspot.com";
        Call<HashMap<String, ArrayList<String>>> call = getClientConnection(url);
        HashMap<String, ArrayList<String>> apiResponse = call.execute().body();
        Assert.assertEquals(47 ,apiResponse.size());
    }

    @Test
    public void verifyAPIIsConnected() throws Exception {
        String url = "http://hawttrends.appspot.com";
        Call<HashMap<String, ArrayList<String>>> call = getClientConnection(url);
        int result = call.execute().code();
        Assert.assertEquals(200 ,result);
    }

    @Test
    public void verifyAPIIsInternalError() throws Exception {
        String url = "http://hawttrends.appspot.com";
        FakeInterceptor interceptor = new FakeInterceptor(500 , "Internal Error");
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        int code = retrofit.create(APIInterface.class).doGetTopWordsList().execute().code();
        String message = retrofit.create(APIInterface.class).doGetTopWordsList().execute().message();
        HashMap<String, ArrayList<String>> body = retrofit.create(APIInterface.class).doGetTopWordsList().execute().body();
        Assert.assertEquals(500,code);
        Assert.assertEquals("Internal Error",message);
        Assert.assertNull(body);
    }

    @Test
    public void verifyAPIIsNotFound() throws Exception {
        String url = "http://hawttrends.appspot.com";
        FakeInterceptor interceptor = new FakeInterceptor(404 , "Not Found");
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        int code = retrofit.create(APIInterface.class).doGetTopWordsList().execute().code();
        String message = retrofit.create(APIInterface.class).doGetTopWordsList().execute().message();
        HashMap<String, ArrayList<String>> body = retrofit.create(APIInterface.class).doGetTopWordsList().execute().body();
        Assert.assertEquals(404,code);
        Assert.assertEquals("Not Found",message);
        Assert.assertNull(body);
    }

    private Call<HashMap<String, ArrayList<String>>> getClientConnection(String url){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        return retrofit.create(APIInterface.class).doGetTopWordsList();

    }
}
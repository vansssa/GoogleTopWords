package com.utapass.topwords.data;

import android.os.AsyncTask;

import com.utapass.topwords.api.APIClient;
import com.utapass.topwords.api.APIInterface;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Response;

public class DataRemoteStore implements DataStore {

    private APIInterface apiInterface;
    private AsyncTask<Call, Void, HashMap<String, ArrayList<String>>> task;
    private HashMap<String, ArrayList<String>> hotWordsMap;
    private LoadDataListener listener;

    @Override
    public void loadData() {
        if (task != null)
            task.cancel(true);

        apiInterface = APIClient.getClient().create(APIInterface.class);
        Call call = apiInterface.doGetTopWordsList();
        task = new CallTask().execute(call);
    }

    @Override
    public void setLoadDataListener(LoadDataListener listener) {
        this.listener = listener;
    }

    private class CallTask extends AsyncTask<Call,Void, HashMap<String,ArrayList<String>>> {
        Response<HashMap<String, ArrayList<String>>>  response;
        @Override
        protected HashMap<String, ArrayList<String>> doInBackground(Call... calls) {
            Call call = calls[0];
            try {
                response = call.execute();
                return response.body();
            }catch (IOException e) {
                e.printStackTrace();
                listener.onLoadDataFailed(response.message());
                return null;
            }
        }

        @Override
        protected void onPostExecute(HashMap<String, ArrayList<String>> stringArrayListHashMap) {
            hotWordsMap = stringArrayListHashMap;
            listener.onLoadDataSuccess(hotWordsMap);
        }
    }

}

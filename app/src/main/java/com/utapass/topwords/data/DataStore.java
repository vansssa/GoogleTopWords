package com.utapass.topwords.data;


import java.util.ArrayList;
import java.util.HashMap;

public interface  DataStore {

    void loadData();

    interface LoadDataListener {
        void onLoadDataSuccess(HashMap<String, ArrayList<String>> hotWordsMap);

        void onLoadDataFailed(String errorCode);
    }
    void setLoadDataListener(LoadDataListener listener);
}

package com.utapass.topwords.data;


import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;


public class DataRepository implements DataStore, DataRemoteStore.LoadDataListener{

    private final DataRemoteStore dataRemoteStore;
    private ArrayList<String> randomWords;
    private LoadDataListener listener;
    private static HashMap<String, ArrayList<String>> cacheHotWordsMap;
    private String TAG = getClass().getSimpleName();

    public DataRepository() {
        dataRemoteStore = new DataRemoteStore();
        dataRemoteStore.setLoadDataListener(this);
    }

    @Override
    public void loadData() {
        if (cacheHotWordsMap == null) {
            dataRemoteStore.loadData();
            dataRemoteStore.setLoadDataListener(this);
            randomWords = null;
        }
        else
           listener.onLoadDataSuccess(cacheHotWordsMap);

    }

    public ArrayList<String> getTrendWordsAt(String countryNo) {
        if (countryNo.equals(CountryList.ALLREGIONS))
            return getAllRegions();
        else {
            return cacheHotWordsMap.get(countryNo);
        }
    }

    @Override
    public void setLoadDataListener(LoadDataListener listener) {
        this.listener = listener;
    }

    @Override
    public void onLoadDataSuccess(HashMap<String, ArrayList<String>> hotWordsMap) {
        cacheHotWordsMap = hotWordsMap;
        listener.onLoadDataSuccess(cacheHotWordsMap);
    }

    @Override
    public void onLoadDataFailed(String errorCode) {
        listener.onLoadDataFailed(errorCode);
    }

    private ArrayList<String> getAllRegions() {
        if(randomWords == null) {
            HashMap<String, ArrayList<String>> map = cacheHotWordsMap;
            ArrayList<String> words = new ArrayList<>();
            for (String key : map.keySet()) {
                words.addAll(map.get(key));
            }

            Collections.shuffle(words);
            Log.i(TAG, "shuffle : " + words.subList(0, 19).toString());
            randomWords = new ArrayList<>(words.subList(0,19));
        }
        return randomWords;
    }


    public String getCountryNo(String countryName) {
        return CountryList.countryListMap.get(countryName);
    }
}

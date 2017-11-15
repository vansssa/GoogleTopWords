package com.utapass.topwords.domain;

import com.utapass.topwords.data.DataRemoteStore;
import com.utapass.topwords.data.DataRepository;

import java.util.ArrayList;
import java.util.HashMap;


public class TrendUseCase implements UseCase,DataRemoteStore.LoadDataListener{

    private static DataRepository dataRepository;
    private GetAllTrendsListener listener;

    public TrendUseCase() {
        dataRepository = new DataRepository();
        dataRepository.setLoadDataListener(this);
    }

    @Override
    public ArrayList<String> getTrendWords(String countryNo) {
        return dataRepository.getTrendWordsAt(countryNo);
    }

    @Override
    public void getAllTrends() {
        dataRepository.loadData();
    }

    @Override
    public String getCountryNo(String countryName) {
        return dataRepository.getCountryNo(countryName);
    }

    @Override
    public void onLoadDataSuccess(HashMap<String, ArrayList<String>> hotWordsMap) {
        listener.onGetAllTrendsSuccess();
    }

    @Override
    public void onLoadDataFailed(String errorCode) {
        listener.onGetAllTrendsFailed(errorCode);
    }

    @Override
    public void setGetAllTrendsListener(GetAllTrendsListener listener) {
        this.listener = listener;
    }


}


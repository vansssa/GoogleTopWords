package com.utapass.topwords.main;

import com.utapass.topwords.data.CountryList;
import com.utapass.topwords.domain.TrendUseCase;

import java.util.ArrayList;

public class MainFragmentPresenterIml implements MainFragmentPresenter,TrendUseCase.GetAllTrendsListener {

    public static int columnNumber = 1;
    public static int rowNumber = 1;
    private final MainFragmentView fragment;
    private TrendUseCase trendUserCase;
    public String targetCountry = CountryList.TAIWAN;

    public MainFragmentPresenterIml(Object object) {
        this.fragment = (MainFragmentView) object;
        trendUserCase = new TrendUseCase();
        trendUserCase.setGetAllTrendsListener(this);
    }

    public void updateString(ArrayList<String> strings) {
        fragment.initUI(strings);
    }

    public ArrayList<String> getTopWords(String countryNo) {
        return trendUserCase.getTrendWords(countryNo);
    }

    @Override
    public void getAllWords() {
        trendUserCase.getAllTrends();
    }

    @Override
    public String getCountryNo(String countryName) {
        return trendUserCase.getCountryNo(countryName);
    }

    @Override
    public void onGetAllTrendsSuccess() {
        updateString(getTopWords(targetCountry));
    }

    @Override
    public void onGetAllTrendsFailed(String errorCode) {
    }
}

package com.utapass.topwords.domain;

import java.util.ArrayList;

/**
 * Created by vanessatsai on 2017/10/25.
 */

public interface  UseCase {

    ArrayList<String> getTrendWords(String countryNo);

    void getAllTrends();

    String getCountryNo(String countryName);

    interface GetAllTrendsListener {
        void onGetAllTrendsSuccess();

        void onGetAllTrendsFailed(String errorCode);
    }

    void setGetAllTrendsListener(GetAllTrendsListener listener);
}

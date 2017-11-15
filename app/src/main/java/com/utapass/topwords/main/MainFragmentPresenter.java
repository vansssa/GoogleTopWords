package com.utapass.topwords.main;

import java.io.IOException;
import java.util.ArrayList;

public interface MainFragmentPresenter {

    void updateString(ArrayList<String> strings);

    ArrayList<String> getTopWords(String language) throws IOException;

    void getAllWords() throws IOException;

    String getCountryNo(String countryName);
}

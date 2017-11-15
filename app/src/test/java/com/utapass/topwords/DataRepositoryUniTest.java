package com.utapass.topwords;

import com.utapass.topwords.data.CountryList;
import com.utapass.topwords.data.DataRepository;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.HashMap;

import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@PowerMockIgnore("javax.net.ssl.*")
@RunWith(PowerMockRunner.class)
public class DataRepositoryUniTest {

    public static final String FAKEAUSTRA = "101";
    DataRepository mockDataRepository;

    private static HashMap<String, ArrayList<String>> mockHotkeyMapList;
    private static final ArrayList<String> mockArrayList = new ArrayList<>();;
    static {
        mockArrayList.add("Sri Lanka");
        mockArrayList.add("Priti Patel");
        mockArrayList.add("Thomas Gravesen");
        mockArrayList.add("Perfect Stranger");
        mockArrayList.add("AG");
        mockHotkeyMapList = new HashMap<>();
        mockHotkeyMapList.put(FAKEAUSTRA, mockArrayList);
    }

    @Before
    public void init() throws Exception {
        mockDataRepository = giveDataRepository();
        mockDataRepository = PowerMockito.mock(DataRepository.class);
        PowerMockito.whenNew(DataRepository.class).withNoArguments().thenReturn(mockDataRepository);
    }

    @Test
    public void verifyRepositoryCachesAfterFirstApiCall() {
        mockDataRepository.loadData();
        verify(mockDataRepository).loadData();
    }

    @Test
    public void verifyGetCorrectRegionNo() {
        mockDataRepository.loadData();
        when(mockDataRepository.getCountryNo(CountryList.COLOMBIA)).thenReturn(FAKEAUSTRA);
        Assert.assertThat(mockDataRepository.getCountryNo(CountryList.COLOMBIA),is(FAKEAUSTRA));
    }

    @Test
    public void verifyGetRegionHotKeyWord() {
        mockDataRepository.loadData();
        when(mockDataRepository.getTrendWordsAt(CountryList.COLOMBIA)).thenReturn(mockArrayList);
        Assert.assertThat(mockDataRepository.getTrendWordsAt(CountryList.COLOMBIA),is(mockArrayList));
    }

    private DataRepository giveDataRepository(){
       return  new DataRepository();
    }

}
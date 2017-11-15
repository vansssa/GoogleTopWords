package com.utapass.topwords;

import com.utapass.topwords.data.CountryList;
import com.utapass.topwords.main.MainActivityFragment;
import com.utapass.topwords.main.MainFragmentPresenterIml;
import com.utapass.topwords.main.MainFragmentView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@PowerMockIgnore("javax.net.ssl.*")
@RunWith(PowerMockRunner.class)
public class PresenterUniTest {
    public static final String FAKEAUSTRA = "101";


    private static HashMap<String, ArrayList<String>> mockHotkeyMapList;
    private static final ArrayList<String> mockArrayList = new ArrayList<>();;

    @InjectMocks
    MainFragmentView mainFragmentView ;

    @Mock
    MainFragmentPresenterIml mockPresenter;

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
        mainFragmentView = new MainActivityFragment();
        mockPresenter = givePresenter(mainFragmentView);
        mockPresenter = PowerMockito.mock(MainFragmentPresenterIml.class);
        PowerMockito.whenNew(MainFragmentPresenterIml.class).withArguments(mainFragmentView).thenReturn(mockPresenter);
    }

    @Test
    public void verifyGetTopWords() throws IOException {
        when(mockPresenter.getTopWords(CountryList.COLOMBIA)).thenReturn(mockArrayList);
        mockPresenter.getTopWords(CountryList.COLOMBIA);
        verify(mainFragmentView).initUI(mockArrayList);
    }

    @Test
    public void verifyUpdateString() throws IOException {
        mockPresenter.updateString(mockArrayList);
        verify(mainFragmentView).initUI(mockArrayList);
    }

    private MainFragmentPresenterIml givePresenter(MainFragmentView mainFragmentView) throws Exception {
            return new MainFragmentPresenterIml(mainFragmentView);
    }


}
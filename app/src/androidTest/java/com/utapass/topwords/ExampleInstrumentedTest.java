package com.utapass.topwords;

import android.app.Activity;
import android.os.Build;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.action.GeneralClickAction;
import android.support.test.espresso.action.GeneralLocation;
import android.support.test.espresso.action.Press;
import android.support.test.espresso.action.Tap;
import android.support.test.filters.SdkSuppress;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.Until;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import org.hamcrest.Matcher;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Collection;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.runner.lifecycle.Stage.RESUMED;
import static junit.framework.Assert.assertEquals;
import static org.hamcrest.core.Is.is;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class ExampleInstrumentedTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);
    private UiDevice mDevice;
    private long TIMEOUT= 10000;
    private java.lang.String PACKAGENAME = "com.google.android.googlequicksearchbox";


    @Before
    public void setUp(){
        if (Build.VERSION.SDK_INT >= 18)
            mDevice = UiDevice.getInstance(getInstrumentation());
    }

    @Test
    public void launchBrowserIsSuccess() throws Exception {
        onView(withId(R.id.grid_recyclerview)).perform(click());
        mDevice.wait(Until.hasObject(By.pkg(PACKAGENAME).depth(0)),
                TIMEOUT);
        assertEquals("Browser launch failed.",mDevice.getCurrentPackageName(),PACKAGENAME);
    }

    @Test
    public void changeLayoutIsSuccess() throws Exception {
        onView(withId(R.id.action_relayout)).perform(click());
        onView(withId(R.id.seekBar_X)).perform(new GeneralClickAction(Tap.SINGLE, GeneralLocation.CENTER, Press.FINGER));
        onView(withId(R.id.seekBar_Y)).perform(new GeneralClickAction(Tap.SINGLE, GeneralLocation.CENTER, Press.FINGER));
        onView(withId(R.id.confirm)).perform(click());
        int count= getRecycleviewItemCount(getCurrentActivity().findViewById(R.id.grid_recyclerview));
        Assert.assertThat(count, is(4));
    }

    private static int getRecycleviewItemCount(final View view) {
        RecyclerView recyclerView = (RecyclerView) view;
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        return  adapter.getItemCount();
    }

    private static Activity getCurrentActivity() {
        final Activity[] currentActivity = new Activity[1];

        getInstrumentation().runOnMainSync(new Runnable() {
            public void run() {
                Collection resumedActivities = ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(RESUMED);
                if (resumedActivities.iterator().hasNext())
                    currentActivity[0] = (Activity) resumedActivities.iterator().next();
            }
        });

        return currentActivity[0];
    }

    private static String getText(final ViewInteraction matcher) {
        final String[] stringHolder = { null };
        matcher.perform(new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isAssignableFrom(TextView.class);
            }

            @Override
            public String getDescription() {
                return "getting text from a TextView";
            }

            @Override
            public void perform(UiController uiController, View view) {
                TextView tv = (TextView) view; //Save, because of check in getConstraints()
                stringHolder[0] = tv.getText().toString();
            }
        });
        return stringHolder[0];
    }

}

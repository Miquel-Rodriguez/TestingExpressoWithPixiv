package cat.itb.pixiv;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.assertion.ViewAssertions.*;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@RunWith(AndroidJUnit4.class)
public class NavigationTest {

    private final String USER_TO_BE_TYPED = "miquel";
    private final String PASS_TO_BE_TYPED = "12345678";

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void click_on_login_button_and_do_login(){
        onView(withId(R.id.loginButton)).perform(click());
        onView(withId(R.id.input_text_login_username)).perform(typeText(USER_TO_BE_TYPED));
        onView(withId(R.id.input_text_login_password)).perform(typeText(PASS_TO_BE_TYPED),closeSoftKeyboard());
        onView(withId(R.id.loginButtonn)).perform(click());
    }

    @Test
    public void FirstFragment_Login_Illustrations_Manga(){
        click_on_login_button_and_do_login();
        onView(withId(R.id.homeIlustrationns)).perform(swipeLeft());
        onView(withId(R.id.homeManga)).check(matches(isDisplayed()));
    }
    @Test
    public void FirstFragment_Login_Illustrations_Novels() throws InterruptedException {
        click_on_login_button_and_do_login();

        onView(withId(R.id.homeIlustrationns)).perform(swipeLeft());
        Thread.sleep(1000);
        onView(withId(R.id.homeManga)).perform(swipeLeft());
        onView(withId(R.id.homeNovels)).check(matches(isDisplayed()));
    }
    @Test
    public void FirstFragment_Login_Illustrations_NavView_SubmitWork_SumbitIllustrations(){
        click_on_login_button_and_do_login();

        ViewInteraction appCompatImageButton = onView(
                allOf(withContentDescription("Navigation Drawer open"),
                        childAtPosition(
                                allOf(withId(R.id.top_appbar),
                                        childAtPosition(
                                                withClassName(is("com.google.android.material.appbar.AppBarLayout")),
                                                0)),
                                2),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        onView(withId(R.id.submitWork)).perform(click());
        onView(withId(R.id.illustrations_button)).perform(click());
        onView(withId(R.id.sumbitWorkNovelsManga)).check(matches(isDisplayed()));
    }
    @Test
    public void FirstFragment_Login_Illustrations_NavView_MyWorks(){
        click_on_login_button_and_do_login();


        ViewInteraction appCompatImageButton = onView(
                allOf(withContentDescription("Navigation Drawer open"),
                        childAtPosition(
                                allOf(withId(R.id.top_appbar),
                                        childAtPosition(
                                                withClassName(is("com.google.android.material.appbar.AppBarLayout")),
                                                0)),
                                2),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        onView(withId(R.id.yourWorks)).perform(click());
        onView(withId(R.id.fragmentMyWorks)).check(matches(isDisplayed()));
    }
    @Test
    public void FirstFragment_Login_Illustrations_NavView_logout_login() throws InterruptedException {
        click_on_login_button_and_do_login();


        ViewInteraction appCompatImageButton = onView(
                allOf(withContentDescription("Navigation Drawer open"),
                        childAtPosition(
                                allOf(withId(R.id.top_appbar),
                                        childAtPosition(
                                                withClassName(is("com.google.android.material.appbar.AppBarLayout")),
                                                0)),
                                2),
                        isDisplayed()));
        appCompatImageButton.perform(click());

//        onView(withId(R.id.navigator_view)).perform(scrollTo());
        onView(withId(R.id.navigator_view)).perform(swipeUp());
        Thread.sleep(1000);
        onView(withId(R.id.logout)).perform(click());
        onView(withId(R.id.fragmentLogin)).check(matches(isDisplayed()));
    }

    @Test
    public void FirstFragment_Login_click_on_recycler_show_content_Fragment(){
        onView(withId(R.id.recycler_view_illustrations_recommended))
                .perform(actionOnItemAtPosition< RecyclerView.ViewHolder >);
    }







    static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}

package cat.itb.pixiv;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

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
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@RunWith(AndroidJUnit4.class)
public class UseCaseTest {
    private final String USER_TO_BE_TYPED = "primer";
    private final String PASS_TO_BE_TYPED = "12345678";
    private final String TITLE_TO_BE_TYPED = "this is a title";
    private final String DESCRIPTION_TO_BE_TYPED = "this is a description";

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
    public void FirstFragment_Login_Illustrations_NavView_SubmitWork_SumbitIllustrations_submit() throws InterruptedException {
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

        onView(withId(R.id.edit_text_title_submit_illustration)).perform(typeText(TITLE_TO_BE_TYPED),closeSoftKeyboard());
        onView(withId(R.id.edit_description_title_submit_illustration)).perform(typeText(DESCRIPTION_TO_BE_TYPED),closeSoftKeyboard());
        onView(withId(R.id.radio_button_public_illustrations)).perform(click());
        onView(withId(R.id.submitIllustrationManga)).perform(click());
    }
    @Test
    public void FirstFragment_Login_click_on_like_buttton() throws InterruptedException {
        click_on_login_button_and_do_login();

        Thread .sleep(3000);
        ViewInteraction appCompatImageView = onView(
                allOf(withId(R.id.image_view_illustrations_recommended_like),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recycler_view_illustrations_recommended),
                                        1),
                                1),
                        isDisplayed()));
        appCompatImageView.perform(click());
        Thread .sleep(3000);
    }

    @Test
    public void FirstFragment_Login_Illustrations_NavView_SubmitWork_SubmitNovel_submit() throws InterruptedException {
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
        onView(withId(R.id.novels_button)).perform(click());
        onView(withId(R.id.edit_text_title_submit_novel)).perform(typeText(TITLE_TO_BE_TYPED),closeSoftKeyboard());
        onView(withId(R.id.edit_text_description_submit_novel)).perform(typeText(DESCRIPTION_TO_BE_TYPED),closeSoftKeyboard());
        onView(withId(R.id.radio_button_public_novels)).perform(click());
        onView(withId(R.id.submitIllustrationManga)).perform(click());
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

package cat.itb.pixiv;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.pressImeActionButton;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void mainActivityTest() {
        ViewInteraction materialButton = onView(
                allOf(withId(R.id.loginButton), withText("Login"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragment_container),
                                        0),
                                1),
                        isDisplayed()));
        materialButton.perform(click());

        ViewInteraction textInputEditText = onView(
                allOf(withId(R.id.input_text_login_username),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.input_layout_login_username),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText.perform(replaceText("primer"), closeSoftKeyboard());

        ViewInteraction textInputEditText2 = onView(
                allOf(withId(R.id.input_text_login_password),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.input_layout_login_password),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText2.perform(replaceText("12345678"), closeSoftKeyboard());

        ViewInteraction textInputEditText3 = onView(
                allOf(withId(R.id.input_text_login_password), withText("12345678"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.input_layout_login_password),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText3.perform(pressImeActionButton());

        ViewInteraction materialButton2 = onView(
                allOf(withId(R.id.loginButtonn), withText("Login"),
                        childAtPosition(
                                allOf(withId(R.id.fragmentLogin),
                                        childAtPosition(
                                                withId(R.id.fragment_container),
                                                0)),
                                3),
                        isDisplayed()));
        materialButton2.perform(click());

        ViewInteraction materialButton3 = onView(
                allOf(withId(R.id.loginButtonn), withText("Login"),
                        childAtPosition(
                                allOf(withId(R.id.fragmentLogin),
                                        childAtPosition(
                                                withId(R.id.fragment_container),
                                                0)),
                                3),
                        isDisplayed()));
        materialButton3.perform(click());

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


    }

    private static Matcher<View> childAtPosition(
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

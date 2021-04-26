package cat.itb.pixiv;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class LoginRegisterTest {

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
        onView(withId(R.id.activityMain)).check(matches(isDisplayed()));
    }

    @Test
    public void click_on_register_button_and_do_resgister(){
        onView(withId(R.id.registerButton)).perform(click());

        onView(withId(R.id.input_text_username)).perform(typeText(USER_TO_BE_TYPED));
        onView(withId(R.id.input_text_password)).perform(typeText(PASS_TO_BE_TYPED));
        onView(withId(R.id.input_text_password_repeat)).perform(typeText(PASS_TO_BE_TYPED),closeSoftKeyboard());

        onView(withId(R.id.registerButton)).perform(click());
        onView(withId(R.id.activityMain)).check(matches(isDisplayed()));
    }
}

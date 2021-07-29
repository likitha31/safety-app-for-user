package latrobesafety.mad.latrobesafety;

import android.view.View;
import android.widget.TimePicker;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.runner.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static java.security.AccessController.getContext;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.AllOf.allOf;
import static org.junit.Assert.*;


@RunWith(AndroidJUnit4.class)
public class PickUpOverviewTest {

    @Rule
    public ActivityTestRule<requestForm> activityTestRule =
            new ActivityTestRule(requestForm.class);

    @Before
    public void setup() {
        onView(withId(R.id.spinner))
                .perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Agora East")))
                .perform(click());
        onView(withId(R.id.spinner))
                .check(matches(withSpinnerText(containsString("Agora East"))));

        ViewInteraction view = onView(withClassName(Matchers.equalTo(TimePicker.class.getName())));
         view.perform(setTime(12,8));
        onView(withId(R.id.nameET))
                .perform(typeText("Ally"), closeSoftKeyboard());
        onView(withId(R.id.msgET))
                .perform(typeText("UI_TESTING"), closeSoftKeyboard());

    }

    @Test
    public void checkClick()
    {
        onView(withId(R.id.confirmBtn)).perform(click());
    }

    public static ViewAction setTime(final int hour, final int minute) {
        return new ViewAction() {
            @Override
            public void perform(UiController uiController, View view) {
                TimePicker tp = (TimePicker) view;
                tp.setHour(hour);
                tp.setMinute(minute);
            }
            @Override
            public String getDescription() {
                return "Set the passed time into the TimePicker";
            }
            @Override
            public Matcher<View> getConstraints() {
                return ViewMatchers.isAssignableFrom(TimePicker.class);
            }
        };
    }
}

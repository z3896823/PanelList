package sysu.zyb.panellisttest;

import android.app.Instrumentation;
import android.graphics.Rect;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.PopupMenu;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import sysu.zyb.panellistlibrary.PanelListLayout;
import sysu.zyb.panellistlibrary.MyHorizontalScrollView;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static androidx.test.espresso.Espresso.onView;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.action.GeneralSwipeAction;
import androidx.test.espresso.action.Press;
import androidx.test.espresso.action.Swipe;
import org.hamcrest.Matcher;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

/** Exercises the original demo workflows after the AndroidX/build migration. */
@RunWith(AndroidJUnit4.class)
public class DemoSmokeTest {
    @Rule
    public ActivityTestRule<MainActivity> activityRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void tableMenusKeepRowsAndHeadersInSync() {
        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
        instrumentation.runOnMainSync(() -> {
            MainActivity activity = activityRule.getActivity();
            ListView content = activity.findViewById(R.id.id_lv_content);
            PanelListLayout panel = activity.findViewById(R.id.id_pl_root);
            ListView headers = panel.getAdapter().getColumnListView();
            Menu menu = new PopupMenu(activity, content).getMenu();
            activity.onCreateOptionsMenu(menu);

            assertEquals(50, content.getCount());
            assertEquals(50, headers.getCount());
            activity.onOptionsItemSelected(menu.findItem(R.id.id_menu_insert));
            assertEquals(51, content.getCount());
            assertEquals(51, headers.getCount());
            activity.onOptionsItemSelected(menu.findItem(R.id.id_menu_delete));
            assertEquals(50, content.getCount());
            assertEquals(50, headers.getCount());
            activity.onOptionsItemSelected(menu.findItem(R.id.id_menu_updateData));
            assertEquals(499, content.getCount());
            assertEquals(499, headers.getCount());
        });
    }

    @Test
    public void scrollingKeepsHeadersAligned() {
        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
        instrumentation.waitForIdleSync();
        instrumentation.runOnMainSync(() -> {
            MainActivity activity = activityRule.getActivity();
            PanelListLayout panel = activity.findViewById(R.id.id_pl_root);
            List<MyHorizontalScrollView> scrollViews = new ArrayList<>();
            collectHorizontalScrollViews(panel, scrollViews);
            assertEquals(2, scrollViews.size());
            scrollViews.get(0).scrollTo(120, 0);
            assertEquals(120, scrollViews.get(0).getScrollX());
            assertEquals(120, scrollViews.get(1).getScrollX());
            scrollViews.get(1).scrollTo(240, 0);
            assertEquals(240, scrollViews.get(0).getScrollX());
            assertEquals(240, scrollViews.get(1).getScrollX());
        });
        // The ListView is wider than the screen inside a horizontal scroller.
        // Swipe only its visible area, as a finger would, rather than requiring
        // Espresso's default 90% visibility for the entire wide table.
        onView(withId(R.id.id_lv_content)).perform(new ViewAction() {
            @Override public Matcher<View> getConstraints() { return isDisplayed(); }
            @Override public String getDescription() { return "swipe visible table upwards"; }
            @Override public void perform(UiController controller, View view) {
                new GeneralSwipeAction(Swipe.SLOW,
                        v -> visiblePoint(v, 0.85f),
                        v -> visiblePoint(v, 0.15f), Press.FINGER).perform(controller, view);
                controller.loopMainThreadUntilIdle();
            }
        });
        instrumentation.waitForIdleSync();
        instrumentation.runOnMainSync(() -> {
            MainActivity activity = activityRule.getActivity();
            ListView content = activity.findViewById(R.id.id_lv_content);
            PanelListLayout panel = activity.findViewById(R.id.id_pl_root);
            ListView headers = panel.getAdapter().getColumnListView();
            assertTrue(content.getFirstVisiblePosition() > 10);
            assertEquals(content.getFirstVisiblePosition(), headers.getFirstVisiblePosition());
            assertEquals(content.getChildAt(0).getTop(), headers.getChildAt(0).getTop());
        });
    }

    private static float[] visiblePoint(View view, float heightFraction) {
        Rect visible = new Rect();
        assertTrue(view.getGlobalVisibleRect(visible));
        return new float[] {visible.exactCenterX(),
                visible.top + visible.height() * heightFraction};
    }

    private static void collectHorizontalScrollViews(View view,
            List<MyHorizontalScrollView> result) {
        if (view instanceof MyHorizontalScrollView) {
            result.add((MyHorizontalScrollView) view);
        }
        if (view instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) view;
            for (int i = 0; i < group.getChildCount(); i++) {
                collectHorizontalScrollViews(group.getChildAt(i), result);
            }
        }
    }

    @Test
    public void roomDemoOpensFromMenu() {
        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
        Instrumentation.ActivityMonitor monitor = instrumentation.addMonitor(
                RoomActivity.class.getName(), null, false);
        try {
            instrumentation.runOnMainSync(() -> {
                MainActivity activity = activityRule.getActivity();
                Menu menu = new PopupMenu(activity,
                        activity.findViewById(R.id.id_lv_content)).getMenu();
                activity.onCreateOptionsMenu(menu);
                activity.onOptionsItemSelected(menu.findItem(R.id.id_menu_next));
            });
            RoomActivity room = (RoomActivity) monitor.waitForActivityWithTimeout(5000);
            assertNotNull("Room demo must open", room);
            instrumentation.waitForIdleSync();
            instrumentation.runOnMainSync(() -> {
                ListView content = room.findViewById(R.id.id_lv_content);
                PanelListLayout panel = room.findViewById(R.id.id_pl_root);
                assertEquals(20, content.getCount());
                assertEquals(20, panel.getAdapter().getColumnListView().getCount());
                room.finish();
            });
        } finally {
            instrumentation.removeMonitor(monitor);
        }
    }
}

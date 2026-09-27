package sysu.zyb.panellisttest;

import android.os.Build;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;

/** Keeps the action bar and table inside the safe area on edge-to-edge Android. */
public abstract class DemoActivity extends AppCompatActivity {
    @Override
    public void setContentView(int layoutResId) {
        super.setContentView(layoutResId);
        if (Build.VERSION.SDK_INT >= 35) {
            // AppCompat includes the action bar height in the insets it dispatches
            // to the content frame. Apply them here so both bars remain unobscured.
            View content = findViewById(android.R.id.content);
            ViewCompat.setOnApplyWindowInsetsListener(content, (view, windowInsets) -> {
                Insets safeArea = windowInsets.getInsets(
                        WindowInsetsCompat.Type.systemBars() | WindowInsetsCompat.Type.displayCutout());
                // AppCompat may consume navigation insets before forwarding them.
                WindowInsetsCompat rootInsets = ViewCompat.getRootWindowInsets(view);
                if (rootInsets != null) {
                    safeArea = Insets.max(safeArea, rootInsets.getInsets(
                            WindowInsetsCompat.Type.systemBars() | WindowInsetsCompat.Type.displayCutout()));
                }
                view.setPadding(safeArea.left, safeArea.top, safeArea.right, safeArea.bottom);
                return WindowInsetsCompat.CONSUMED;
            });
            WindowCompat.getInsetsController(getWindow(), getWindow().getDecorView())
                    .setAppearanceLightStatusBars(true);
            WindowCompat.getInsetsController(getWindow(), getWindow().getDecorView())
                    .setAppearanceLightNavigationBars(true);
            ViewCompat.requestApplyInsets(content);
        }
    }
}

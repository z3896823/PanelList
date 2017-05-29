package sysu.zyb.panellistlibrary;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.Checkable;

/**
 * <pre>
 *     author : zyb
 *     e-mail : hbdxzyb@hotmail.com
 *     time   : 2017/05/24
 *     desc   : a simple LinearLayout that can be checked
 *     version: 1.0
 * </pre>
 */

public class CheckableLinearLayout extends LinearLayout implements Checkable {

    private boolean mChecked;

    public CheckableLinearLayout(Context context) {
        super(context);
    }

    public CheckableLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setChecked(boolean mChecked) {
        this.mChecked = mChecked;
    }

    @Override
    public boolean isChecked() {
        return mChecked;
    }

    @Override
    public void toggle() {
        setChecked(!mChecked);
    }
}

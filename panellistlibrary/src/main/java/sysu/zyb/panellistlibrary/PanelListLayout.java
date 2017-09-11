package sysu.zyb.panellistlibrary;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

/**
 * <pre>
 *     author : zyb
 *     e-mail : hbdxzyb@hotmail.com
 *     time   : 2017/05/28
 *     desc   :
 *     version: 1.0
 * </pre>
 */

public class PanelListLayout extends RelativeLayout {

    private PanelListAdapter adapter;

    public PanelListAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(PanelListAdapter adapter) {
        this.adapter = adapter;
        adapter.initAdapter();
    }

    public PanelListLayout(Context context) {
        super(context);
    }

    public PanelListLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PanelListLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}

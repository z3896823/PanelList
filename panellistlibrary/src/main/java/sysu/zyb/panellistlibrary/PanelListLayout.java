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
 *     desc   : 其实就是一个RelativeLayout，这里这样写是为了让使用者不要想太多
 *              不然外面写一个 RelativeLayout 会让人觉得莫名其妙
 *     version: 1.0
 * </pre>
 */

public class PanelListLayout extends RelativeLayout {

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

package sysu.zyb.panellistlibrary;


import android.content.Context;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;

/**
 * <pre>
 *     author : zyb
 *     e-mail : hbdxzyb@hotmail.com
 *     time   : 2017/05/22
 *     desc   :
 *     version: 1.0
 * </pre>
 */

public class MyHorizontalScrollView extends HorizontalScrollView{

    // 自定义的监听器
    private OnHorizontalScrollListener listener;

    public MyHorizontalScrollView(Context context){
        super(context);
    }

    public MyHorizontalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyHorizontalScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setOnHorizontalScrollListener(OnHorizontalScrollListener listener){
        this.listener = listener;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        // 通知自定义的listener
        if (listener != null){
            listener.onHorizontalScrolled(this, l, t, oldl, oldt);
        }
    }

    //内部接口，用来监听系统的onScrollChangedListener监听到的数据
    interface OnHorizontalScrollListener {
        void onHorizontalScrolled(MyHorizontalScrollView view, int l, int t, int oldl, int oldt);
    }
}

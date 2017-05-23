package sysu.zyb.panellistlibrary;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 *     author : zyb
 *     e-mail : hbdxzyb@hotmail.com
 *     time   : 2017/05/23
 *     desc   : 总的adapterController
 *     version: 1.0
 * </pre>
 */

public class PanelListAdapter {

    private Context context;//预留

    /**
     * 两个横向滑动layout
     */
    private MyHorizontalScrollView mhsv_rowTitle;
    private MyHorizontalScrollView mhsv_content;

    /**
     * 两个垂直滑动ListView
     */
    private ListView lv_column;
    private ListView lv_content;

    /**
     * 两个监听器，分别控制水平和垂直方向上的同步滑动
     */
    private HorizontalScrollListener horizontalScrollListener = new HorizontalScrollListener();
    private VerticalScrollListener verticalScrollListener = new VerticalScrollListener();

    /**
     * constructor
     *
     * @param mhsv_rowTitle  包裹横向表头的HorizontalScrollView
     * @param mhsv_content  包裹内容的HorizontalScrollView
     * @param lv_column  纵向表头的ListView
     * @param lv_content  内容的ListView
     */
    public PanelListAdapter(Context context,MyHorizontalScrollView mhsv_rowTitle, MyHorizontalScrollView mhsv_content,
                            ListView lv_column, ListView lv_content) {
        this.context = context;
        this.mhsv_rowTitle = mhsv_rowTitle;
        this.mhsv_content = mhsv_content;
        this.lv_column = lv_column;
        this.lv_content = lv_content;
    }

    /**
     * 初始化总Adapter，加载数据到视图
     */
    public void initAdapter(){

        mhsv_rowTitle.setOnHorizontalScrollListener(horizontalScrollListener);
        mhsv_content.setOnHorizontalScrollListener(horizontalScrollListener);

        //性能瓶颈处，以下两行代码会占用大量的CPU资源，不断进行对齐操作，暂未找到解决方案
        lv_content.setOnScrollListener(verticalScrollListener);
        lv_column.setOnScrollListener(verticalScrollListener);
    }

    /**
     * HorizontalScrollView的滑动监听（水平方向同步控制）
     */
    protected class HorizontalScrollListener implements MyHorizontalScrollView.OnHorizontalScrollListener {
        @Override
        public void onHorizontalScrolled(MyHorizontalScrollView view, int l, int t, int oldl, int oldt) {
            if (view == mhsv_content){
                mhsv_rowTitle.scrollTo(l,t);
            } else{
                mhsv_content.scrollTo(l,t);
            }
        }
    }

    /**
     * 两个ListView的滑动监听（垂直方向同步控制）
     */
    protected class VerticalScrollListener implements AbsListView.OnScrollListener{
        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            if (scrollState == SCROLL_STATE_IDLE || scrollState == SCROLL_STATE_TOUCH_SCROLL){
                View subView = view.getChildAt(0);//?
                if (subView != null){
                    int top = subView.getTop();
                    int position = view.getFirstVisiblePosition();
                    lv_content.setSelectionFromTop(position,top);
                    lv_column.setSelectionFromTop(position,top);
                }
            }
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            View subView = view.getChildAt(0);//??
            if (subView != null){
                int top = subView.getTop();
                lv_content.setSelectionFromTop(firstVisibleItem,top);
                lv_column.setSelectionFromTop(firstVisibleItem,top);
            }
        }
    }
}
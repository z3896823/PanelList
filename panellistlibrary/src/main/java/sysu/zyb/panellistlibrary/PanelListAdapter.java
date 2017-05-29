package sysu.zyb.panellistlibrary;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *     author : zyb
 *     e-mail : hbdxzyb@hotmail.com
 *     time   : 2017/05/23
 *     desc   : 总的adapterController
 *     version: 1.0
 * </pre>
 */

public abstract class PanelListAdapter {

    private Context context;

    /**
     * 两个横向滑动layout
     */
    private MyHorizontalScrollView mhsv_row;
    private MyHorizontalScrollView mhsv_content;


    /**
     * 整个页面的所有布局
     */
    private PanelListLayout pl_root;//外层的根布局
    private TextView tv_title;//左上角的title
    private LinearLayout ll_row;//上方的表头
    private ListView lv_column;//左边的表头
    private ListView lv_content;//中间的内容部分
    private LinearLayout ll_contentItem;//中间的内容部分的子布局

    /** 标题的宽和高,同时也是列表头的宽和列表头的高 */
    private int titleWidth = 50;
    private int titleHeight = 30;
    private int columnItemHeight = 30;

    protected String title = "Title";
    private List<String> columnDataList;
    private List<String> rowDataList;


    private String columnColor = "#346cf1";//default color of column: blue
    private String titleColor = "#fdfdfd";//default color of title: white
    private String rowColor = "#f9ba1d";//default color of title: yellow

    private BaseAdapter columnAdapter;

    /**
     * 两个监听器，分别控制水平和垂直方向上的同步滑动
     */
    private HorizontalScrollListener horizontalScrollListener = new HorizontalScrollListener();
    private VerticalScrollListener verticalScrollListener = new VerticalScrollListener();


    /**
     * constructor
     *
     * @param lv_content  内容的ListView
     */
    public PanelListAdapter(Context context, PanelListLayout pl_root, ListView lv_content) {
        this.context = context;
        this.pl_root = pl_root;
        this.lv_content = lv_content;
    }

    //region APIs
    /**
     * 设置表的标题
     *
     * @param title title
     */
    protected void setTitle(String title) {
        this.title = title;
    }

    /**
     * 设置表头的宽度
     *
     * @param titleWidth title width
     */
    protected void setTitleWidth(int titleWidth) {
        this.titleWidth = titleWidth;
    }

    /**
     * 设置表头的高度
     *
     * @param titleHeight title height
     */
    protected void setTitleHeight(int titleHeight) {
        this.titleHeight = titleHeight;
    }

    /**
     * 设置横向表头的标题（！！必须调用！！）
     *
     * @param rowDataList data list of row layout, must be a List<String>
     */
    protected void setRowDataList(List<String> rowDataList) {
        this.rowDataList = rowDataList;
    }

    /**
     * 设置纵向表头的内容
     *
     * @param columnDataList data list of column layout, must be a List<String>
     */
    protected void setColumnDataList(List<String> columnDataList) {
        this.columnDataList = columnDataList;
    }

    /**
     * 设置纵向表头的背景色
     *
     * @param columnColor background color of column
     */
    protected void setColumnColor(String columnColor) {
        this.columnColor = columnColor;
    }

    /**
     * 设置标题的背景色
     *
     * @param titleColor background color of title
     */
    protected void setTitleColor(String titleColor) {
        this.titleColor = titleColor;
    }

    /**
     * 设置横向表头的背景色
     *
     * @param rowColor background color of row
     */
    protected void setRowColor(String rowColor) {
        this.rowColor = rowColor;
    }

    /**
     * 设置纵向表头的适配器
     *
     * @param columnAdapter adapter of column ListView
     */
    protected void setColumnAdapter(BaseAdapter columnAdapter) {
        this.columnAdapter = columnAdapter;
    }


    //endregion

    /**
     * 初始化总Adapter，加载数据到视图
     */
    protected void initAdapter(){

        reorganizeViewGroup();

        mhsv_row.setOnHorizontalScrollListener(horizontalScrollListener);
        mhsv_content.setOnHorizontalScrollListener(horizontalScrollListener);


        // 性能bug处，在7.0以上系统中，以下两行代码会占用大量的CPU资源，不断进行对齐操作，暂未找到解决方案
        // 在4.4系统中则不会出现此类情况，但是内存资源占用会偏高
        lv_content.setOnScrollListener(verticalScrollListener);
        lv_column.setOnScrollListener(verticalScrollListener);
    }

    // must be override
    protected abstract int getCount();

    /**
     * 整理重组整个表的布局
     *
     * 主要包含4个部分
     * 1. title
     * 2. row
     * 3. column
     * 4. content
     */
    private void reorganizeViewGroup() {

        if (titleHeight == 0 || titleWidth == 0){
            Log.e("PanelList", "title height and title width must be set");
            return;
        }

//        if (ll_contentItem == null){
//            Log.e("PanelList", "the item view of content-ListView must be set");
//            Log.e("PanelList", "if you don`t tell me what the content item looks like, how am I gonna" +
//                    " be able to generate the row title that matches your content, baby?");
//            return;
//        }

        // clear root viewGroup
        pl_root.removeView(lv_content);

        // 1. title
        tv_title = new TextView(context);
        tv_title.setText(title);
        tv_title.setBackgroundColor(Color.parseColor(titleColor));
        tv_title.setId(View.generateViewId());//设置一个随机id，这样可以保证不冲突
        RelativeLayout.LayoutParams lp_tv_title = new RelativeLayout.LayoutParams(titleWidth, titleHeight);
        pl_root.addView(tv_title, lp_tv_title);
        tv_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "title clicked", Toast.LENGTH_SHORT).show();
                ll_contentItem = (LinearLayout) lv_content.getChildAt(lv_content.getFirstVisiblePosition());
                initRowLayout();
            }
        });

        // 2. row
        ll_row = new LinearLayout(context);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        ll_row.setLayoutParams(lp);
        mhsv_row = new MyHorizontalScrollView(context);
        mhsv_row.setHorizontalScrollBarEnabled(false);
        mhsv_row.addView(ll_row);//暂时先不给ll_row添加子view，等布局画出来了再添加
        mhsv_row.setId(View.generateViewId());
        RelativeLayout.LayoutParams lp_mhsv_row = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, titleHeight);
        lp_mhsv_row.addRule(RelativeLayout.END_OF, tv_title.getId());
        lp_mhsv_row.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        pl_root.addView(mhsv_row, lp_mhsv_row);

        pl_root.post(new Runnable() {
            @Override
            public void run() {
                ll_contentItem = (LinearLayout) lv_content.getChildAt(lv_content.getFirstVisiblePosition());
                Log.d("ybz", "ll_contentItem width = " + ll_contentItem.getWidth());
                Log.d("ybz", "ll_contentItem height = " + ll_contentItem.getHeight());
                columnItemHeight = ll_contentItem.getChildAt(0).getHeight();
                lv_column.setAdapter(getColumnAdapter());
                initRowLayout();
            }
        });

        // 3. column
        lv_column = new ListView(context);
        lv_column.setBackgroundColor(Color.parseColor(columnColor));
        lv_column.setId(View.generateViewId());
        lv_column.setVerticalScrollBarEnabled(false);
        RelativeLayout.LayoutParams lp_lv_column = new RelativeLayout.LayoutParams(titleWidth, ViewGroup.LayoutParams.MATCH_PARENT);
        lp_lv_column.addRule(RelativeLayout.BELOW, tv_title.getId());
        pl_root.addView(lv_column, lp_lv_column);

        // 4. content
        mhsv_content = new MyHorizontalScrollView(context);
        mhsv_content.addView(lv_content);
        RelativeLayout.LayoutParams lp_mhsv_content = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        lp_mhsv_content.addRule(RelativeLayout.RIGHT_OF, lv_column.getId());
        lp_mhsv_content.addRule(RelativeLayout.BELOW, tv_title.getId());
        pl_root.addView(mhsv_content, lp_mhsv_content);
    }

    /**
     * 初始化横向表头的布局，必须在所有的布局都载入完之后才能调用
     *
     * must be called in pl_root.post();
     */
    private void initRowLayout(){

        if (rowDataList == null){
            Log.e("PanelList", "row data list must be set");
            return;
        }

        ll_row.setBackgroundColor(Color.parseColor(rowColor));
        ll_row.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        ll_row.setDividerDrawable(context.getResources().getDrawable(R.drawable.row_item_divider));

        int rowCount = ll_contentItem.getChildCount();
        for (int i = 0;i<rowCount; i++){
            TextView contentItem = (TextView) ll_contentItem.getChildAt(i);
            TextView rowItem = new TextView(context);
            rowItem.setText(rowDataList.get(i));//设置文字
            rowItem.setWidth(contentItem.getWidth());//设置宽度
            rowItem.setHeight(titleHeight);//设置高度
            rowItem.setGravity(Gravity.CENTER);
            ll_row.addView(rowItem);
        }
    }

    /**
     * 返回纵向表头的数据列表
     *
     * @return data list of column ListView
     */
    private List<String> getColumnDataList(){
        if (columnDataList == null){
            columnDataList = new ArrayList<>();
            for (int i = 1;i<= getCount();i++){
                columnDataList.add(String.valueOf(i));
            }
        }
        return columnDataList;
    }

    /**
     * 返回纵向表头的适配器
     *
     * @return adapter of column ListView
     */
    private BaseAdapter getColumnAdapter(){
        if (columnAdapter == null){
            columnAdapter = new ColumnAdapter(context,android.R.layout.simple_list_item_1,getColumnDataList());
        }
        return columnAdapter;
    }


    /**
     * HorizontalScrollView的滑动监听（水平方向同步控制）
     */
    private class HorizontalScrollListener implements MyHorizontalScrollView.OnHorizontalScrollListener {
        @Override
        public void onHorizontalScrolled(MyHorizontalScrollView view, int l, int t, int oldl, int oldt) {
            if (view == mhsv_content){
                mhsv_row.scrollTo(l,t);
            } else{
                mhsv_content.scrollTo(l,t);
            }
        }
    }

    /**
     * 两个ListView的滑动监听（垂直方向同步控制）
     */
    private class VerticalScrollListener implements AbsListView.OnScrollListener{
        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            if (scrollState == SCROLL_STATE_IDLE || scrollState == SCROLL_STATE_TOUCH_SCROLL){
                View subView = view.getChildAt(0);//?
                if (subView != null&& view == lv_content){
                    int top = subView.getTop();
                    int position = view.getFirstVisiblePosition();
                    lv_column.setSelectionFromTop(position,top);
                } else if (subView != null&& view == lv_column){
                    int top = subView.getTop();
                    int position = view.getFirstVisiblePosition();
                    lv_content.setSelectionFromTop(position,top);
                }
            }
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            View subView = view.getChildAt(0);
            if (subView != null && view == lv_content){
                int top = subView.getTop();
                lv_column.setSelectionFromTop(firstVisibleItem,top);
            } else if (subView != null&& view == lv_column){
                int top = subView.getTop();
                lv_content.setSelectionFromTop(firstVisibleItem,top);
            }
        }
    }

    /**
     * 默认的columnAdapter
     *
     * 之所以重写是为了根据content的item之高度动态设置column的item之高度
     */
    private class ColumnAdapter extends ArrayAdapter{

        private int resourceId;
        private List<String> columnDataList;

        ColumnAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<String> objects) {
            super(context, resource, objects);
            resourceId = resource;
            columnDataList = objects;
        }

        @Override
        public int getCount() {
            return super.getCount();
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View view;

            if (convertView == null){
                view = LayoutInflater.from(context).inflate(resourceId,parent,false);
                view.getLayoutParams().height = columnItemHeight;//由于自定义的布局高度比默认的小，所以必须要用这种方式设置高度才会生效
                Log.d("ybz", "getView: view.height = "+ view.getHeight());
            } else {
                view = convertView;
            }

            ((TextView)view).setText(columnDataList.get(position));

            return view;
        }
    }
}
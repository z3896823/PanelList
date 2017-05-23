package sysu.zyb.panellisttest;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import sysu.zyb.panellistlibrary.MyHorizontalScrollView;
import sysu.zyb.panellistlibrary.PanelListAdapter;

/**
 * <pre>
 *     author : zyb
 *     e-mail : hbdxzyb@hotmail.com
 *     time   : 2017/05/23
 *     desc   : 整个页面的Adapter，内部使用了两个子Adapter
 *              开发者可自行定义两个子Adapter
 *     version: 1.0
 * </pre>
 */

public class MyPanelListAdapter extends PanelListAdapter {

    private Context context;

    /**
     * 两个垂直滑动ListView
     */
    private ListView lv_column;
    private ListView lv_content;

    /**
     * 两个垂直滑动ListView的子布局
     */
    private int columnResourceId;
    private int contentResourceId;

    /**
     * 两个ListView的数据
     */
    private List<String> columnTitleList = new ArrayList<>();
    private List<Map<String, String>> contentList = new ArrayList<>();

    /**
     * constructor
     *
     * @param context  上下文
     * @param mhsv_row  横向表头的外部布局
     * @param mhsv_content  内容的外部布局
     * @param lv_column  纵向表头的ListView
     * @param lv_content  内容的ListView
     * @param columnResourceId  纵向表头的子布局
     * @param contentResourceId  内容的子布局
     * @param columnTitleList  纵向表头的数据列表
     * @param contentList  内容的数据列表
     */
    public MyPanelListAdapter(Context context, MyHorizontalScrollView mhsv_row, MyHorizontalScrollView mhsv_content,
                              ListView lv_column, ListView lv_content,
                              int columnResourceId, int contentResourceId,
                              List<String> columnTitleList, List<Map<String, String>> contentList) {
        super(context, mhsv_row, mhsv_content, lv_column, lv_content);
        this.context = context;
        this.lv_column = lv_column;
        this.lv_content = lv_content;
        this.columnResourceId = columnResourceId;
        this.contentResourceId = contentResourceId;
        this.columnTitleList = columnTitleList;
        this.contentList = contentList;
    }

    /**
     * 调用父类方法进行同步控制
     *
     * 自行编写Adapter并进行setAdapter
     */
    @Override
    public void initAdapter() {
        super.initAdapter();//这里一定要先调用父类的方法

        ContentAdapter contentAdapter = new ContentAdapter(context,contentResourceId,contentList);
        lv_content.setAdapter(contentAdapter);

        //为简单说明问题，这里不再使用复杂的Adapter，开发者可以根据需要自行设计
        ArrayAdapter<String> columnAdapter = new ArrayAdapter<>(context,columnResourceId,columnTitleList);
        lv_column.setAdapter(columnAdapter);
    }

    /**
     * content部分的adapter
     */
    private class ContentAdapter extends ArrayAdapter {

        private List<Map<String, String>> contentList;
        private int resourceId;

        ContentAdapter(Context context, int resourceId, List<Map<String, String>> contentList) {
            super(context, resourceId);
            this.contentList = contentList;
            this.resourceId = resourceId;
        }


        @Override
        public int getCount() {
            return contentList.size();
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            final Map<String, String> data = contentList.get(position);
            View view;
            ViewHolder viewHolder;

            if (convertView == null) {
                view = LayoutInflater.from(parent.getContext()).inflate(resourceId, parent, false);
                viewHolder = new ViewHolder(view);
                view.setTag(viewHolder);
            } else {
                view = convertView;
                viewHolder = (ViewHolder) view.getTag();
            }

            viewHolder.tv_01.setText(data.get("1"));
            viewHolder.tv_02.setText(data.get("2"));
            viewHolder.tv_03.setText(data.get("3"));
            viewHolder.tv_04.setText(data.get("4"));
            viewHolder.tv_05.setText(data.get("5"));
            viewHolder.tv_06.setText(data.get("6"));
            viewHolder.tv_07.setText(data.get("7"));

            return view;
        }

        private class ViewHolder {
            TextView tv_01;
            TextView tv_02;
            TextView tv_03;
            TextView tv_04;
            TextView tv_05;
            TextView tv_06;
            TextView tv_07;

            ViewHolder(View itemView) {
                tv_01 = (TextView) itemView.findViewById(R.id.id_tv_01);
                tv_02 = (TextView) itemView.findViewById(R.id.id_tv_02);
                tv_03 = (TextView) itemView.findViewById(R.id.id_tv_03);
                tv_04 = (TextView) itemView.findViewById(R.id.id_tv_04);
                tv_05 = (TextView) itemView.findViewById(R.id.id_tv_05);
                tv_06 = (TextView) itemView.findViewById(R.id.id_tv_06);
                tv_07 = (TextView) itemView.findViewById(R.id.id_tv_07);
            }
        }
    }
}

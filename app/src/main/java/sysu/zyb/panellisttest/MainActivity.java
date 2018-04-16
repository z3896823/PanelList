package sysu.zyb.panellisttest;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import sysu.zyb.panellistlibrary.AbstractPanelListAdapter;
import sysu.zyb.panellistlibrary.PanelListLayout;


/**
 * @author zyb
 */
public class MainActivity extends AppCompatActivity {

    private PanelListLayout pl_root;
    private ListView lv_content;

    private AbstractPanelListAdapter adapter;

    private List<List<String>> contentList = new ArrayList<>();

    private List<Integer> itemWidthList = new ArrayList<>();

    private List<String> rowDataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        initRowDataList();
        initContentDataList();
        initItemWidthList();

        adapter = new AbstractPanelListAdapter(this,pl_root,lv_content) {
            @Override
            protected BaseAdapter getContentAdapter() {
                return null;
            }
        };
        adapter.setInitPosition(10);
        adapter.setSwipeRefreshEnabled(true);
        adapter.setRowDataList(rowDataList);
        adapter.setTitle("example");
        adapter.setOnRefreshListener(new CustomRefreshListener());
        adapter.setContentDataList(contentList);
        adapter.setItemWidthList(itemWidthList);
        adapter.setItemHeight(40);
        pl_root.setAdapter(adapter);

        // 注意：
        // 如果你决定自己实现自己的Column，而不是使用默认的1，2，3。。。
        // 请注意更新contentList时手动更新columnList
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.id_menu_updateData:
                changeContentDataList();
                break;
            case R.id.id_menu_insert:
                insertData();
                break;
            case R.id.id_menu_delete:
                removeData();
                break;
            case R.id.id_menu_next:
                Intent intent = new Intent(MainActivity.this, RoomActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
        adapter.notifyDataSetChanged();
        return true;
    }

    private void initView() {

        pl_root = findViewById(R.id.id_pl_root);
        lv_content = findViewById(R.id.id_lv_content);

        //设置listView为多选模式，长按自动触发
        lv_content.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        lv_content.setMultiChoiceModeListener(new MultiChoiceModeCallback());

        //listView的点击监听
        lv_content.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, "你选中的position为：" + position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public class CustomRefreshListener implements SwipeRefreshLayout.OnRefreshListener{
        @Override
        public void onRefresh() {
            // you can do sth here, for example: make a toast:
            Toast.makeText(MainActivity.this, "custom SwipeRefresh listener", Toast.LENGTH_SHORT).show();
            // don`t forget to call this
            adapter.getSwipeRefreshLayout().setRefreshing(false);
        }
    }

    /** 生成一份横向表头的内容
     *
     * @return List<String>
     */
    private void initRowDataList(){
        rowDataList.add("第一列");
        rowDataList.add("第二列");
        rowDataList.add("第三列");
        rowDataList.add("第四列");
        rowDataList.add("第五列");
        rowDataList.add("第六列");
        rowDataList.add("第七列");
    }

    /**
     * 初始化content数据
     */
    private void initContentDataList() {
        for (int i = 1; i <= 50; i++) {
            List<String> data = new ArrayList<>();
            data.add("第" + i + "行第一个");
            data.add("第" + i + "行第二个");
            data.add("第" + i + "行第三个");
            data.add("第" + i + "行第四个");
            data.add("第" + i + "行第五个");
            data.add("第" + i + "行第六个");
            data.add("第" + i + "行第七个");
            contentList.add(data);
        }
    }

    /**
     * 初始化 content 部分每一个 item 的每个数据的宽度
     */
    private void initItemWidthList(){
        itemWidthList.add(100);
        itemWidthList.add(100);
        itemWidthList.add(100);
        itemWidthList.add(100);
        itemWidthList.add(100);
        itemWidthList.add(100);
        itemWidthList.add(100);
    }

    /**
     * 更新content数据源
     */
    private void changeContentDataList() {
        contentList.clear();
        for (int i = 1; i < 500; i++) {
            List<String> data = new ArrayList<>();
            data.add("第" + i + "第一个");
            data.add("第" + i + "第二个");
            data.add("第" + i + "第三个");
            data.add("第" + i + "第四个");
            data.add("第" + i + "第五个");
            data.add("第" + i + "第六个");
            data.add("第" + i + "第七个");
            contentList.add(data);
        }
    }

    /**
     * 插入一个数据
     */
    private void insertData(){
        List<String> data = new ArrayList<>();
        data.add("插入1");
        data.add("插入2");
        data.add("插入3");
        data.add("插入4");
        data.add("插入5");
        data.add("插入6");
        data.add("插入7");
        contentList.add(5,data);
    }

    /**
     * 删除一个数据
     */
    private void removeData(){
        contentList.remove(10);
    }

    /**
     * 多选模式的监听器
     */
    private class MultiChoiceModeCallback implements ListView.MultiChoiceModeListener {

        private View actionBarView;
        private TextView tv_selectedCount;

        /**
         * 进入ActionMode时调用
         * 可设置一些菜单
         *
         * @param mode
         * @param menu
         * @return
         */
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            // menu
            getMenuInflater().inflate(R.menu.menu_multichoice, menu);
            // actionBar
            if (actionBarView == null) {
                actionBarView = LayoutInflater.from(MainActivity.this).inflate(R.layout.actionbar_listviewmultichoice, null);
                tv_selectedCount = (TextView) actionBarView.findViewById(R.id.id_tv_selectedCount);
            }
            mode.setCustomView(actionBarView);
            return true;
        }

        /**
         * 和onCreateActionMode差不多的时机调用，不写逻辑
         *
         * @param mode
         * @param menu
         * @return
         */
        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        /**
         * 当ActionMode的菜单项被点击时
         *
         * @param mode
         * @param item
         * @return
         */
        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.id_menu_selectAll:
                    for (int i = 0; i < lv_content.getAdapter().getCount(); i++) {
                        lv_content.setItemChecked(i, true);
                    }
                    tv_selectedCount.setText(String.valueOf(lv_content.getAdapter().getCount()));
                    break;
                case R.id.id_menu_draw:
                    //draw
                    SparseBooleanArray booleanArray = lv_content.getCheckedItemPositions();
                    Log.d("ybz", booleanArray.toString());

                    List<Integer> checkedItemPositionList = new ArrayList<>();
                    for (int i = 0; i < contentList.size(); i++) {
                        if (lv_content.isItemChecked(i)) {
                            checkedItemPositionList.add(i);
                            Log.d("ybz", "被选中的item： " + i);
                        }
                    }

                    StringBuilder checkedItemString = new StringBuilder();
                    for (int i = 0; i < checkedItemPositionList.size(); i++) {
                        checkedItemString.append(checkedItemPositionList.get(i) + ",");
                    }

                    Toast.makeText(MainActivity.this, "你选中的position有：" + checkedItemString, Toast.LENGTH_SHORT).show();
                    break;
            }
            return true;
        }

        /**
         * 退出ActionMode时调用
         *
         * @param mode
         */
        @Override
        public void onDestroyActionMode(ActionMode mode) {
            lv_content.clearChoices();
        }

        /**
         * 当item的选中状态发生改变时调用
         *
         * @param mode
         * @param position
         * @param id
         * @param checked
         */
        @Override
        public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
            int selectedCount = lv_content.getCheckedItemCount();
            tv_selectedCount.setText(String.valueOf(selectedCount));
            ((ArrayAdapter) lv_content.getAdapter()).notifyDataSetChanged();
        }
    }
}

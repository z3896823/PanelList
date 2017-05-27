package sysu.zyb.panellisttest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sysu.zyb.panellistlibrary.MyHorizontalScrollView;


public class MainActivity extends AppCompatActivity {

    private ListView lv_content;
    private ListView lv_columnTitle;

    private MyHorizontalScrollView mhsv_row;
    private MyHorizontalScrollView mhsv_content;

    List<String> columnList = new ArrayList<>();
    List<Map<String ,String>> contentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initDataList();

        MyPanelListAdapter adapter = new MyPanelListAdapter(this, mhsv_row, mhsv_content, lv_columnTitle, lv_content,
                R.layout.item_column, R.layout.item_content, columnList, contentList);
        adapter.initAdapter();
    }

    private void initView(){
        mhsv_row = (MyHorizontalScrollView) findViewById(R.id.id_mhsv_rowTitle);
        mhsv_content = (MyHorizontalScrollView) findViewById(R.id.id_mhsv_content);

        lv_content = (ListView) findViewById(R.id.id_lv_content);
        lv_columnTitle = (ListView) findViewById(R.id.id_lv_columnTitle);

        lv_content.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        lv_content.setMultiChoiceModeListener(new MultiChoiceModeCallback());

        lv_content.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, columnList.get(position), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initDataList(){
        for (int i = 0;i<1000;i++){
            Map<String ,String> data = new HashMap<>();
            data.put("1","第"+i+"行第一个");
            data.put("2","第"+i+"行第二个");
            data.put("3","第"+i+"行第三个");
            data.put("4","第"+i+"行第四个");
            data.put("5","第"+i+"行第五个");
            data.put("6","第"+i+"行第六个");
            data.put("7","第"+i+"行第七个");
            contentList.add(data);
        }

        for (int i = 0;i<1000;i++){
            columnList.add("表头"+i);
        }
    }

    /**
     * 多选模式的监听器
     *
     */
    private class MultiChoiceModeCallback implements ListView.MultiChoiceModeListener{

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
            getMenuInflater().inflate(R.menu.menu_multichoice,menu);
            // actionBar
            if (actionBarView == null){
                actionBarView = LayoutInflater.from(MainActivity.this).inflate(R.layout.actionbar_listviewmultichoice,null);
                tv_selectedCount = (TextView) actionBarView.findViewById(R.id.id_tv_selectedCount);
            }
            mode.setCustomView(actionBarView);
            return true;
        }

        /**
         * 和onCreateActionMode差不多的时机调用，不写逻辑
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
            switch (item.getItemId()){
                case R.id.id_menu_selectAll:
                    for (int i = 0; i<lv_content.getAdapter().getCount();i++){
                        lv_content.setItemChecked(i,true);
                    }
                    tv_selectedCount.setText(String.valueOf(lv_content.getAdapter().getCount()));
                    break;
                case R.id.id_menu_draw:
                    //draw
                    long[] checkedIds = lv_content.getCheckedItemIds();
                    Log.d("ybz", " 数组长度为：  "+checkedIds.length);
                    for (int i = 0;i<checkedIds.length;i++){
                        Log.d("ybz", "checked id = "+checkedIds[i]);
                    }
                    SparseBooleanArray booleanArray = lv_content.getCheckedItemPositions();
                    Log.d("ybz", booleanArray.toString());

                    List<Integer> checkedItemPositionList = new ArrayList<>();
                    for (int i = 0; i<contentList.size();i++){
                        if (lv_content.isItemChecked(i)){
                            checkedItemPositionList.add(i);
                            Log.d("ybz", "被选中的item： " + i);
                        }
                    }


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
         * @param mode
         * @param position
         * @param id
         * @param checked
         */
        @Override
        public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
            int selectedCount = lv_content.getCheckedItemCount();
            tv_selectedCount.setText(String.valueOf(selectedCount));
            ((ArrayAdapter)lv_content.getAdapter()).notifyDataSetChanged();
        }
    }
}

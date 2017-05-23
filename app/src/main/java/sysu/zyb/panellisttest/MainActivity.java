package sysu.zyb.panellisttest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

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
}

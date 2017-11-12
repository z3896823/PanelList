package sysu.zyb.panellisttest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import sysu.zyb.panellistlibrary.AbstractPanelListAdapter;
import sysu.zyb.panellistlibrary.PanelListLayout;

/**
 * <pre>
 *     author : zyb
 *     e-mail : hbdxzyb@hotmail.com
 *     time   : 2017/9/9
 *     desc   :
 *     version: 1.0
 * </pre>
 */

public class RoomActivity extends AppCompatActivity {

    private static final String TAG = "ybz";

    private PanelListLayout pl_root;
    private AbstractPanelListAdapter adapter;
    private ListView lv_content;
    private List<Room> roomList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);
        pl_root = (PanelListLayout) findViewById(R.id.id_pl_root);
        lv_content = (ListView) findViewById(R.id.id_lv_content);
        initRoomData();

        adapter = new RoomPanelListAdapter(this, pl_root, lv_content, roomList, R.layout.item_room);
        adapter.setRowDataList(generateRowData());
        adapter.setColumnDataList(generateColumnData());
        adapter.setTitle("week/room");
        adapter.setRowColor("#0288d1");
        adapter.setColumnColor("#81d4fa");
        pl_root.setAdapter(adapter);

    }

    /**
     * 初始化房间列表和房间信息
     */
    private void initRoomData() {
        for (int i = 201;i<221;i++){
            Room room = new Room(i);
            room.setRoomDetail(Utility.generateRandomRoomDetail());
            roomList.add(room);
        }
    }

    private List<String> generateRowData(){
        List<String> rowDataList = new ArrayList<>();
        rowDataList.add("周一");
        rowDataList.add("周二");
        rowDataList.add("周三");
        rowDataList.add("周四");
        rowDataList.add("周五");
        rowDataList.add("周六");
        rowDataList.add("周日");
        return rowDataList;
    }

    private List<String> generateColumnData(){
        List<String> columnDataList = new ArrayList<>();
        for (Room room : roomList){
            columnDataList.add(String.valueOf(room.getRoomNo()));
        }
        return columnDataList;
    }
}

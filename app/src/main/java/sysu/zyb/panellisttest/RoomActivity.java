package sysu.zyb.panellisttest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import sysu.zyb.panellistlibrary.PanelListAdapter;
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
    private PanelListAdapter adapter;
    private ListView lv_content;
    private List<Room> roomList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);

        adapter = new RoomPanelListAdapter(this,pl_root,lv_content,roomList);
//        adapter.



    }

    /**
     * 初始化房间列表和房间信息
     */
    private void initRoomData(){
        Room room1 = new Room(201);
        Room room2 = new Room(202);
        Room room3 = new Room(203);
        Room room4 = new Room(204);
        Room room5 = new Room(205);
        Room room6 = new Room(206);

        roomList.add(room1);
        roomList.add(room2);
        roomList.add(room3);
        roomList.add(room4);
        roomList.add(room5);
        roomList.add(room6);

        for (Room room : roomList){
            room.setRoomDetail(Utility.generateRandomRoomDetail());
        }
    }

}

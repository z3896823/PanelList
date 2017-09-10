package sysu.zyb.panellisttest;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;

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

public class RoomPanelListAdapter extends PanelListAdapter {

    private List<Room> roomList;

    public RoomPanelListAdapter(Context context, PanelListLayout pl_root, ListView lv_content,
                                List<Room> roomList) {
        super(context, pl_root, lv_content);
        this.roomList = roomList;
    }

    @Override
    protected BaseAdapter getContentAdapter() {
        return null;
    }

    @Override
    protected int getCount() {
        return roomList.size();
    }

    class ContentAdapter extends ArrayAdapter {

        public ContentAdapter(@NonNull Context context, @LayoutRes int resource) {
            super(context, resource);
        }
    }
}

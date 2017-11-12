package sysu.zyb.panellisttest;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Map;

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

public class RoomPanelListAdapter extends AbstractPanelListAdapter {

    private Context context;
    private List<Room> roomList;
    private int resourceId;

    public RoomPanelListAdapter(Context context, PanelListLayout pl_root, ListView lv_content,
                                List<Room> roomList,int resourceId) {
        super(context, pl_root, lv_content);
        this.context = context;
        this.roomList = roomList;
        this.resourceId = resourceId;
    }

    @Override
    protected BaseAdapter getContentAdapter() {
        return new ContentAdapter(context,resourceId,roomList);
    }

    private class ContentAdapter extends ArrayAdapter {

        private int itemResourceId;
        private List<Room> roomList;

        public ContentAdapter(@NonNull Context context, @LayoutRes int resource,List<Room> roomList) {
            super(context, resource);
            this.itemResourceId = resource;
            this.roomList = roomList;
        }

        @Override
        public int getCount() {
            return roomList.size();
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View view;
            ViewHolder viewHolder;
            Map<Integer,Integer> roomDetail = roomList.get(position).getRoomDetail();
            //这种设置监听的方式并不是性能最优的，其实可以像复用ViewHolder一样复用监听器，每次改变监听的position参数即可
            //监听器作为一个tag-Object存放在convertView中。可能需要调试一下，不过应该没问题，有需要的自己可以动手试试
            RoomClickListener listener = new RoomClickListener(position);

            if (convertView == null){
                view = LayoutInflater.from(parent.getContext()).inflate(itemResourceId,parent,false);
                viewHolder = new ViewHolder(view);
                view.setTag(viewHolder);
            } else {
                view = convertView;
                viewHolder = (ViewHolder) view.getTag();
            }

            viewHolder.tv_1.setOnClickListener(listener);
            viewHolder.tv_2.setOnClickListener(listener);
            viewHolder.tv_3.setOnClickListener(listener);
            viewHolder.tv_4.setOnClickListener(listener);
            viewHolder.tv_5.setOnClickListener(listener);
            viewHolder.tv_6.setOnClickListener(listener);
            viewHolder.tv_7.setOnClickListener(listener);

            viewHolder.tv_1.setBackgroundResource(getBackgroundResource(roomDetail.get(1)));
            viewHolder.tv_1.setText(getText(roomDetail.get(1)));
            viewHolder.tv_2.setBackgroundResource(getBackgroundResource(roomDetail.get(2)));
            viewHolder.tv_2.setText(getText(roomDetail.get(2)));
            viewHolder.tv_3.setBackgroundResource(getBackgroundResource(roomDetail.get(3)));
            viewHolder.tv_3.setText(getText(roomDetail.get(3)));
            viewHolder.tv_4.setBackgroundResource(getBackgroundResource(roomDetail.get(4)));
            viewHolder.tv_4.setText(getText(roomDetail.get(4)));
            viewHolder.tv_5.setBackgroundResource(getBackgroundResource(roomDetail.get(5)));
            viewHolder.tv_5.setText(getText(roomDetail.get(5)));
            viewHolder.tv_6.setBackgroundResource(getBackgroundResource(roomDetail.get(6)));
            viewHolder.tv_6.setText(getText(roomDetail.get(6)));
            viewHolder.tv_7.setBackgroundResource(getBackgroundResource(roomDetail.get(7)));
            viewHolder.tv_7.setText(getText(roomDetail.get(7)));
            return view;
        }

        int getBackgroundResource(int i){
            switch (i){
                case Room.AVAILABLE:
                    return R.drawable.bg_room_green_available;
                case Room.OCCUPIED:
                    return R.drawable.bg_room_orange_occupied;
                case Room.RESERVED:
                    return R.drawable.bg_room_blue_reserved;
                case Room.OUTOFSERVICE:
                    return R.drawable.bg_room_gray_outofservice;
                default:
                    return -1;
            }
        }

        String getText(int i){
            switch (i){
                case Room.AVAILABLE:
                    return "可入住";
                case Room.OCCUPIED:
                    return "已入住";
                case Room.RESERVED:
                    return "已预订";
                case Room.OUTOFSERVICE:
                    return "维修中";
                default:
                    return null;
            }
        }

        private class ViewHolder{
            private TextView tv_1;
            private TextView tv_2;
            private TextView tv_3;
            private TextView tv_4;
            private TextView tv_5;
            private TextView tv_6;
            private TextView tv_7;

            ViewHolder(View itemView) {
                tv_1 = (TextView) itemView.findViewById(R.id.id_tv_01);
                tv_2 = (TextView) itemView.findViewById(R.id.id_tv_02);
                tv_3 = (TextView) itemView.findViewById(R.id.id_tv_03);
                tv_4 = (TextView) itemView.findViewById(R.id.id_tv_04);
                tv_5 = (TextView) itemView.findViewById(R.id.id_tv_05);
                tv_6 = (TextView) itemView.findViewById(R.id.id_tv_06);
                tv_7 = (TextView) itemView.findViewById(R.id.id_tv_07);
            }
        }
    }

    class RoomClickListener implements View.OnClickListener{

        int position;

        public RoomClickListener(int i) {
            super();
            position = i;
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.id_tv_01:
                    Toast.makeText(context, roomList.get(position).getRoomNo()+"房间周一的入住详情", Toast.LENGTH_SHORT).show();

                    break;
                case R.id.id_tv_02:
                    Toast.makeText(context, roomList.get(position).getRoomNo()+"房间周二的入住详情", Toast.LENGTH_SHORT).show();

                    break;
                case R.id.id_tv_03:
                    Toast.makeText(context, roomList.get(position).getRoomNo()+"房间周三的入住详情", Toast.LENGTH_SHORT).show();

                    break;
                case R.id.id_tv_04:
                    Toast.makeText(context, roomList.get(position).getRoomNo()+"房间周四的入住详情", Toast.LENGTH_SHORT).show();

                    break;
                case R.id.id_tv_05:
                    Toast.makeText(context, roomList.get(position).getRoomNo()+"房间周五的入住详情", Toast.LENGTH_SHORT).show();

                    break;
                case R.id.id_tv_06:
                    Toast.makeText(context, roomList.get(position).getRoomNo()+"房间周六的入住详情", Toast.LENGTH_SHORT).show();

                    break;
                case R.id.id_tv_07:
                    Toast.makeText(context, roomList.get(position).getRoomNo()+"房间周日的入住详情", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    }
}

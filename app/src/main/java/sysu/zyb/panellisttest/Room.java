package sysu.zyb.panellisttest;

import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 *     author : zyb
 *     e-mail : hbdxzyb@hotmail.com
 *     time   : 2017/9/9
 *     desc   :
 *     经过查阅API，Java的Date类基本被废弃，取而代之的是Calendar类，但该类的很多变量是static
 *     不适合作为存储日期的对象。因此决定用String变量存储日期，然后iu使用Calendar的静态方法从String中提取信息
 *     version: 1.0
 * </pre>
 */

public class Room {

    static final int AVAILABLE = 0;
    static final int RESERVED = 1;
    static final int OCCUPIED = 2;
    static final int OUTOFSERVICE = 3;

    public Room(int roomNo) {
        this.roomNo = roomNo;
    }

    private int roomNo;
    /**
     * 日期作为key，上面的四个变量作为value
     * 例：（0910，0）表示9月10日房间可用
     */
    private Map<Integer,Integer> roomDetail = new HashMap<>();

    public int getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(int roomNo) {
        this.roomNo = roomNo;
    }

    public Map<Integer, Integer> getRoomDetail() {
        return roomDetail;
    }

    public void setRoomDetail(Map<Integer, Integer> roomDetail) {
        this.roomDetail = roomDetail;
    }
}

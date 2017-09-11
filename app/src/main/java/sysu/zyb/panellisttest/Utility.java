package sysu.zyb.panellisttest;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * <pre>
 *     author : zyb
 *     e-mail : hbdxzyb@hotmail.com
 *     time   : 2017/9/10
 *     desc   :
 *     version: 1.0
 * </pre>
 */

public class Utility {

    public static Map<Integer,Integer> generateRandomRoomDetail(){
        Map<Integer,Integer> roomDetail = new HashMap<>();
        Random random = new Random();
        roomDetail.put(1, random.nextInt(4));
        roomDetail.put(2, random.nextInt(4));
        roomDetail.put(3, random.nextInt(4));
        roomDetail.put(4, random.nextInt(4));
        roomDetail.put(5, random.nextInt(4));
        roomDetail.put(6, random.nextInt(4));
        roomDetail.put(7, random.nextInt(4));
        return roomDetail;
    }
}

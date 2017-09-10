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


    /**
     * 根据传入的日期，返回当前星期
     */
    public static int getWeekByDate(String date){


        return 0;
    }

    public static Map<Integer,Integer> generateRandomRoomDetail(){
        Map<Integer,Integer> roomDetail = new HashMap<>();
        roomDetail.put(910, getRandomNumber());
        roomDetail.put(911, getRandomNumber());
        roomDetail.put(912, getRandomNumber());
        roomDetail.put(913, getRandomNumber());
        roomDetail.put(914, getRandomNumber());
        roomDetail.put(915, getRandomNumber());
        roomDetail.put(916, getRandomNumber());
        roomDetail.put(917, getRandomNumber());
        roomDetail.put(918, getRandomNumber());
        roomDetail.put(919, getRandomNumber());
        roomDetail.put(920, getRandomNumber());
        return roomDetail;
    }

    private static int getRandomNumber(){
        Random random = new Random();
        return random.nextInt(4);
    }
}

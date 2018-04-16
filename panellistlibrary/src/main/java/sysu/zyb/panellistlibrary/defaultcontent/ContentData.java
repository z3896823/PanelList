package sysu.zyb.panellistlibrary.defaultcontent;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *     @author: zyb
 *     email  : hbdxzyb@hotmail.com
 *     time   : 2018/4/15 下午1:29
 *     desc   :
 *     version: 1.0
 * </pre>
 */

public class ContentData {

    private List<List<String>> contentDataList = new ArrayList<>();

    private int itemSize;

    public ContentData() {

    }

    public List<String> getItem(int position){
        return contentDataList.get(position);
    }

}

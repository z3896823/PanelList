# PanelList

PanelList is a simple library for displaying massive data.

中间的内容部分支持上下滑动和左右滑动，且内容滑动时，对应的表头跟随滑动。

主要用于展示大量数据，如酒店订房数据，股票，实验数据等。

![](https://github.com/z3896823/PanelList/blob/master/PanelList.gif)

# 本库的不足和改进之处
- 目前由于时间关系，封装程度不够，开发者使用上不够极致简单。请按照下面的示例配置自己的项目。
- 未来会添加更多功能接口。诸如多选的接口，目前需要开发者自己定义CheckableView。




# Using PanelList in your application
Step 1. Add the JitPack repository to your build file

Add it in your root build.gradle at the end of repositories:
```gradle
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
Step 2. Add the dependency in your module gradle
```gradle
dependencies {
	        compile 'com.github.z3896823:PanelList:v1.0'
	}
```

# Get started
### xml文件布局示例：
```xml
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <!--最左上角的标题，一个简单的TextView-->
    <TextView
        android:id="@+id/id_tv_tableName"
        android:layout_width="50dp"
        android:layout_height="30dp"
        android:gravity="center"
        android:text="name"
        android:layout_alignParentTop="true"
        android:layout_alignParentStart="true" />

    <!--横向表头的布局，用MyHorizontalScrollView包裹-->
    <sysu.zyb.panellistlibrary.MyHorizontalScrollView
        android:id="@+id/id_mhsv_rowTitle"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_toRightOf="@id/id_tv_tableName"
        android:scrollbars="none">

        <include layout="@layout/item_row" />

    </sysu.zyb.panellistlibrary.MyHorizontalScrollView>

    <!--纵向表头的列表，一个简单的ListView,取消滑动条-->
    <ListView
        android:id="@+id/id_lv_columnTitle"
        android:layout_width="50dp"
        android:layout_height="match_parent"
        android:layout_below="@id/id_tv_tableName"
        android:scrollbars="none"
        android:fastScrollEnabled="false"/>

    <!--内容的布局，用MyHorizontalScrollView包裹-->
    <sysu.zyb.panellistlibrary.MyHorizontalScrollView
        android:id="@+id/id_mhsv_content"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:layout_toRightOf="@id/id_lv_columnTitle"
        android:layout_below="@id/id_mhsv_rowTitle">

        <ListView
            android:id="@+id/id_lv_content"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:fastScrollEnabled="false"/>
    </sysu.zyb.panellistlibrary.MyHorizontalScrollView>

</RelativeLayout>
```
### MainActivity.java
```java
        MyPanelListAdapter adapter = new MyPanelListAdapter(this, mhsv_row, mhsv_content, lv_columnTitle, lv_content,
                R.layout.item_column, R.layout.item_content, columnList, contentList);
        adapter.initAdapter();
```

### MyPanelListAdapter
```java
public class MyPanelListAdapter extends PanelListAdapter {

    private Context context;

    /**
     * 两个垂直滑动ListView
     */
    private ListView lv_column;
    private ListView lv_content;

    /**
     * 两个垂直滑动ListView的子布局
     */
    private int columnResourceId;
    private int contentResourceId;

    /**
     * 两个ListView的数据
     */
    private List<String> columnTitleList = new ArrayList<>();
    private List<Map<String, String>> contentList = new ArrayList<>();

    /**
     * constructor
     *
     * @param context  上下文
     * @param mhsv_row  横向表头的外部布局
     * @param mhsv_content  内容的外部布局
     * @param lv_column  纵向表头的ListView
     * @param lv_content  内容的ListView
     * @param columnResourceId  纵向表头的子布局
     * @param contentResourceId  内容的子布局
     * @param columnTitleList  纵向表头的数据列表
     * @param contentList  内容的数据列表
     */
    public MyPanelListAdapter(Context context, MyHorizontalScrollView mhsv_row, MyHorizontalScrollView mhsv_content,
                              ListView lv_column, ListView lv_content,
                              int columnResourceId, int contentResourceId,
                              List<String> columnTitleList, List<Map<String, String>> contentList) {
        super(context, mhsv_row, mhsv_content, lv_column, lv_content);
        this.context = context;
        this.lv_column = lv_column;
        this.lv_content = lv_content;
        this.columnResourceId = columnResourceId;
        this.contentResourceId = contentResourceId;
        this.columnTitleList = columnTitleList;
        this.contentList = contentList;
    }

    /**
     * 调用父类方法进行同步控制
     *
     * 自行编写Adapter并进行setAdapter
     */
    @Override
    public void initAdapter() {
        super.initAdapter();//这里一定要先调用父类的方法

        ContentAdapter contentAdapter = new ContentAdapter(context,contentResourceId,contentList);
        lv_content.setAdapter(contentAdapter);

        //为简单说明问题，这里不再使用复杂的Adapter，开发者可以根据需要自行设计
        ArrayAdapter<String> columnAdapter = new ArrayAdapter<>(context,columnResourceId,columnTitleList);
        lv_column.setAdapter(columnAdapter);
    }

    /**
     * content部分的adapter
     */
    private class ContentAdapter extends ArrayAdapter {
    	//此处省略，和普通的Adapter无区别。如需参考请clone项目查看
    }
}
```





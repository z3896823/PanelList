# PanelList

PanelList is a simple library for displaying data. It makes it possible to display massive data on a limited screen, just like what you see in Microsoft Excel. 

It could be used to show hotel reservation data (like the demo below), stock data and etc.

If you have any problem using this library, please feel free to contact me via hbdxzyb@hotmail.com.

Don`t forget to star if it really helps you. ：）

[Introduction in Chinese](https://github.com/z3896823/PanelList/blob/master/README_CHS.md)

## DEMO 

![](https://github.com/z3896823/PanelList/blob/master/PanelList_1.gif)

![](https://github.com/z3896823/PanelList/blob/master/PanelList_2.gif)

## ChangeLog

- v1.1 (20170523)— add some APIs
- v1.1.1 (20170723) — add swipe refresh
- v1.1.1.1 (20170812) — add an API to set initial position
- v1.2.0 (20170911) — Big change! Now you can set everything in your activity and call setAdapter() to finish your job. And also some bug fixed.

## Installing
Step 1. add this to your project build.gradle
```gradle
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
Step 2. add this to build.gradle of your module
```gradle
dependencies {
	        compile 'com.github.z3896823:PanelList:v1.x.x' //please click the release tag up ahead to fill in the latest version 
	}
```



## Get Started

### 1、xml files

```xml
<!--activity view-->
<sysu.zyb.panellistlibrary.PanelListLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:id="@+id/id_pl_root">

    <ListView
        android:id="@+id/id_lv_content"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:fastScrollEnabled="false"
        android:overScrollMode="never"/>

</sysu.zyb.panellistlibrary.PanelListLayout>
```

```xml
<!--item view.use CheckableLinearLayout if you want multiselection.-->
<!--even if you don`t, I still suggest you use this viewgroup-->
<sysu.zyb.panellistlibrary.CheckableLinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:orientation="horizontal"
    android:layout_width="match_parent"
    android:layout_height="50dp"
    android:gravity="center">

  <!--build your item views here-->
</sysu.zyb.panellistlibrary.CheckableLinearLayout>
```

### 2、adapter

```java
public class MyPanelListAdapter extends PanelListAdapter {

    private Context context;
    private ListView lv_content;
    private int contentResourceId;
    private List<Map<String, String>> contentList = new ArrayList<>();

    /**
     * constructor
     */
    public MyPanelListAdapter(Context context, PanelListLayout pl_root, ListView lv_content,
                              int contentResourceId, List<Map<String,String>> contentList) {
        super(context, pl_root, lv_content);
        this.context = context;
        this.lv_content = lv_content;
        this.contentResourceId = contentResourceId;
        this.contentList = contentList;
    }

    /**
     * return the content adapter
     */
    @Override
    protected BaseAdapter getContentAdapter() {
        return new ContentAdapter(context,contentResourceId,contentList);
    }

    /**
     * return size of content data
     */
    @Override
    protected int getCount() {
        return contentList.size();
    }

    /**
     * content adapter, nothing different from a listview adapter
     */
    private class ContentAdapter extends ArrayAdapter {
		//your content adapter
    }
}
```

### 3、activity

```java
public class MainActivity extends AppCompatActivity {

    private PanelListLayout pl_root;
    private ListView lv_content;
    private MyPanelListAdapter adapter;
    private List<Map<String, String>> contentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initContentDataList();

        adapter = new MyPanelListAdapter(this, pl_root, lv_content, R.layout.item_content, contentList);
        adapter.setInitPosition(10);
        adapter.setSwipeRefreshEnabled(true);
        //set anything you want here, then call pl_root.setAdapter() to get everything done
        pl_root.setAdapter(adapter);
    }
}
```



## APIs

```java
  
    public void setTitle(String title);

    public void setTitleBackgroundResource(int resourceId);

    public void setTitleWidth(int titleWidth) ;

    public void setTitleHeight(int titleHeight);

    public void setRowDataList(List<String> rowDataList);

    public void setColumnDataList(List<String> columnDataList);

    public void setRowDivider(Drawable rowDivider) ;

    public void setColumnDivider(Drawable columnDivider);

    public void setColumnColor(String columnColor);

    public void setTitleColor(String titleColor);

    public void setRowColor(String rowColor) ;

    public void setColumnAdapter(BaseAdapter columnAdapter);

    /**
     * if you want the 100th data to be your first data on screen, pass 100
     */
    public void setInitPosition(int initPosition);

    public ListView getContentListView();

    public ListView getColumnListView() ;

    public LinearLayout getRowLayout();

    /**
     * unless you call this method and pass true, the swiperefresh is disabled
     */
    public void setSwipeRefreshEnabled(boolean bool);

    public void setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener listener) ;

    public SwipeRefreshLayout getSwipeRefreshLayout();
```



## License

   ```
    Copyright 2017 z3896823(hbdxzyb)
    
    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at
    
       http://www.apache.org/licenses/LICENSE-2.0
    
    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
    
   ```
# PanelList

PanelList is a simple library for displaying massive data.

中间的内容部分支持上下滑动和左右滑动，且内容滑动时，对应的表头跟随滑动。

主要用于展示大量数据，如酒店订房数据，股票行情，实验数据等。

【本库已应用于作者所在实验室的内部项目中，反馈良好。】

如有问题欢迎使用 hbdxzyb@hotmail.com 联系我。

如果本项目对你有帮助，请star一下~ 谢谢~



![](https://github.com/z3896823/PanelList/blob/master/PanelList.gif)

# 更新日志
### v1.1.1.1 — 2017/08/12

应广大群众需求，添加设置初始位置的方法，类似ListView的setSelection()。

### v1.1.1 — 2017/07/23

添加下拉刷新，使用方法见下面的API文档

### v1.1 — 2017/05/23
v1.1版本相对初版进行了相对更高度的封装，大大降低了开发者的使用门槛，使得开发者可以像用ListView一样使用PanelList。
同时，增加了很多个性化接口，供开发者按照自己的需求改变PanelList的效果。


# 导入说明
Step 1. 在project的build.gradle文件中添加
```gradle
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
Step 2. 在主module的build.gradle文件中添加
```gradle
dependencies {
	        compile 'com.github.z3896823:PanelList:v1.1.x' //请查阅上访的release标签，使用最新版本号
	}
```

# 使用说明

使用本库时，开发者只需要关心中间content部分的adapter怎么写，其余的表头部分只需要将数据传进去就可以了。剩下的数据填充及同步滑动部分将由本库自动完成。
而且表头每个item的高度（纵向表头）和宽度（横向表头）将跟随开发者content部分的item大小自动适应。

### xml文件布局示例：
```xml
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


### MyPanelListAdapter
```java
public class MyPanelListAdapter extends PanelListAdapter {

    private Context context;

    private ListView lv_content;
    private int contentResourceId;
    private List<Map<String, String>> contentList = new ArrayList<>();

    /**
     * constructor
     *
     */
    public MyPanelListAdapter(Context context, PanelListLayout pl_root, ListView lv_content, int contentResourceId, List<Map<String,String>> contentList) {
        super(context, pl_root, lv_content);
        this.context = context;
        this.lv_content = lv_content;
        this.contentResourceId = contentResourceId;
        this.contentList = contentList;
    }

    /**
     * 1. 自行编写Adapter并进行setAdapter
     * 2. 调用父类方法进行同步控制
     */
    @Override
    public void initAdapter() {
        setTitle("test");//设置表的标题
        setTitleHeight(100);//设置表标题的高
        setTitleWidth(150);//设置表标题的宽
        setRowDataList(getRowDataList());//设置横向表头的内容

        // set自己写的contentAdapter
        ContentAdapter contentAdapter = new ContentAdapter(context,contentResourceId,contentList);
        lv_content.setAdapter(contentAdapter);

        //setOnRefreshListener(new CustomRefreshListener());//需要在调用父类的方法之前设置监听，比较麻烦，不推荐
      
		setInitPosition(88);//指定初始位置。该方法public，可在Activity中调用
        super.initAdapter();//一定要在设置完后再调用父类的方法
    }
    
    /**
     * 如果需要刷新数据，请重写该方法
     *
     * 虽然支持自定义OnRefreshListener来设置下拉刷新的监听，但是推荐以重写该方法的方式来实现刷新逻辑
     */
    @Override
    public void refreshData(){
        Log.d(null, "refreshData: in custom adapter");
    }

    /**
     * 重写该方法，返回数据的个数即可
     *
     * @return size of content
     */
    @Override
    protected int getCount() {
        return contentList.size();
    }

    /**
     * content部分的adapter
     */
    private class ContentAdapter extends ArrayAdapter {
    //略
    }
}
```

### MainActivity.java
```java
        MyPanelListAdapter adapter = new MyPanelListAdapter(this, pl_root, lv_content, R.layout.item_content, contentList);
        adapter.initAdapter();
```

# APIs：

```java
    /**
     * 设置表的标题
     *
     * @param title title
     */
    setTitle(String title);

    /**
     * 设置表头的宽度
     *
     * @param titleWidth title width
     */
    setTitleWidth(int titleWidth);

    /**
     * 设置表头的高度
     *
     * @param titleHeight title height
     */
    setTitleHeight(int titleHeight);

    /**
     * 设置横向表头的标题（！！必须调用！！）
     *
     * @param rowDataList data list of row layout, must be a List<String> object.There is no default data for this.
     */
    setRowDataList(List<String> rowDataList);

    /**
     * 设置纵向表头的内容
     *
     * @param columnDataList data list of column layout, must be a List<String> object. if you don`t call this method, the default column list will be used, which is 1,2,3,4...
     */
    setColumnDataList(List<String> columnDataList);

    /**
     * 设置纵向表头的背景色
     *
     * @param columnColor background color of column
     */
    setColumnColor(String columnColor);

    /**
     * 设置标题的背景色
     *
     * @param titleColor background color of title
     */
    setTitleColor(String titleColor);

    /**
     * 设置横向表头的背景色
     *
     * @param rowColor background color of row
     */
    setRowColor(String rowColor);
    
	/**
     * 设置是否开启下拉刷新（默认开启）
     *
     * @param bool pass true if you want to use pull to refresh
     */
    protected void setSwipeRefreshEnabled(boolean bool){
        swipeRefreshEnable = bool;
    }

    /**
     * 刷新数据
     *
     * 虽然支持自定义OnRefreshListener来设置下拉刷新的监听，但是推荐重写父类的该方法来实现刷新逻辑
     */
    @Override
    public void refreshData(){
        Log.d(null, "refreshData: in custom adapter");
    }

	/**
     * 设置content的初始position
     * 
     * @param initPosition
     */
    public void setInitPosition(int initPosition){
        this.initPosition = initPosition;
    }
```







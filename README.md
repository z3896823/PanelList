# PanelList v1.1

PanelList is a simple library for displaying massive data.

中间的内容部分支持上下滑动和左右滑动，且内容滑动时，对应的表头跟随滑动。

主要用于展示大量数据，如酒店订房数据，股票，实验数据等。

【本库已应用于作者所在实验室的内部项目中，反馈良好。】

【欢迎大家使用后提建议，有问题也欢迎提issue。如果觉得不错请star一下，这将是对我莫大的鼓励！谢谢~~】

![](https://github.com/z3896823/PanelList/blob/master/PanelList.gif)

# What`s new
v1.1版本相对初版进行了相对更高度的封装，大大降低了开发者的使用门槛，使得开发者可以像用ListView一样使用PanelList。
同时，增加了很多个性化接口，供开发者按照自己的需求改变PanelList的效果。

使用本库时，开发者只需要关心中间content部分的adapter怎么写，其余的表头部分只需要将数据传进去就可以了。剩下的数据填充及同步滑动部分将由本库自动完成。
而且表头每个item的高度（纵向表头）和宽度（横向表头）将跟随开发者content部分的item大小自动适应。


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
	        compile 'com.github.z3896823:PanelList:v1.1.0.1'
	}
```

# Get started
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
### MainActivity.java
```java
        MyPanelListAdapter adapter = new MyPanelListAdapter(this, pl_root, lv_content, R.layout.item_content, contentList);
        adapter.initAdapter();

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
    public MyPanelListAdapter(Context context, PanelListLayout pl_root, ListView lv_content,
                              int contentResourceId, List<Map<String,String>> contentList) {
        super(context, pl_root, lv_content);
        this.context = context;
        this.lv_content = lv_content;
        this.contentResourceId = contentResourceId;
        this.contentList = contentList;
    }

    /**
     * 调用父类方法进行同步控制
     *
     * 自行编写Adapter并进行setAdapter
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

        super.initAdapter();//一定要在设置完**后**调用父类的方法
    }

    /**
     * 重写父类的该方法，返回数据的个数即可
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

# APIs

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
     * @param rowDataList data list of row layout, must be a List<String>
     */
    protected void setRowDataList(List<String> rowDataList);

    /**
     * 设置纵向表头的内容
     *
     * @param columnDataList data list of column layout, must be a List<String>. if you don`t call this method, the default column list will be used
     */
    protected void setColumnDataList(List<String> columnDataList);

    /**
     * 设置纵向表头的背景色
     *
     * @param columnColor background color of column
     */
    protected void setColumnColor(String columnColor);

    /**
     * 设置标题的背景色
     *
     * @param titleColor background color of title
     */
    protected void setTitleColor(String titleColor);

    /**
     * 设置横向表头的背景色
     *
     * @param rowColor background color of row
     */
    protected void setRowColor(String rowColor);

```


 PS:多选暂时不属于本库的功能，如果有人需要的话请联系我，人多的话我抽时间封装一下。
 email：hbdxzyb@hotmail.com






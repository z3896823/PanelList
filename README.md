# PanelList v1.1

PanelList is a simple library for displaying massive data.

中间的内容部分支持上下滑动和左右滑动，且内容滑动时，对应的表头跟随滑动。

主要用于展示大量数据，如酒店订房数据，股票，实验数据等。

![](https://github.com/z3896823/PanelList/blob/master/PanelList.gif)

# What`s new
v1.1版本相对初版进行了相对更高度的封装，大大降低了开发者的使用门槛，使得开发者可以像用ListView一样使用PanelList。
同时，增加了很多个性化接口，供开发者按照自己的需求改变PanelList的效果。

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
	        compile 'com.github.z3896823:PanelList:v1.1'
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

        super.initAdapter();//一定要在设置完后调用父类的方法
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


### PS:多选暂时不属于本库的功能，未来会考虑封装进去。





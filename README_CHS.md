# PanelList

PanelList是一个二维表格，主要用于展示大量数据，如酒店订房数据，股票行情，实验数据等。

主要特色在于中间的内容部分滑动时，对应的表头可以跟随滑动。方便找到任意一个数据的对应表头。

【本库已应用于作者所在实验室的内部项目中，目前反馈良好。】

如有问题欢迎使用 hbdxzyb@hotmail.com 联系我。

如果本项目对你有帮助，请star一下~ 谢谢~

## 示例

![](https://github.com/z3896823/PanelList/blob/master/PanelList_1.gif)

![](https://github.com/z3896823/PanelList/blob/master/PanelList_2.gif)

## 更新日志

-  v1.3.0 — 2018/04/16

   对于最基础的使用提供了 adapter 的默认实现，方便新人快速上手（个性化的使用请参考 demo 中的 RoomActivity.java)。

   修复了一些 bug

-  v1.2.3 — 2017/11/12

​    一些bug修复


-  v1.2.0 —2017/09/10

​    进一步封装，并按照适配器模式对库的使用方法做了大幅度修改，以符合开发者的使用习惯。

​    解决了部分小bug。修复了之前同步滑动存在的性能问题，感谢wm_8800@163.com。

​    更新了示例（MainActivity），添加了一些多次被网友问起的功能的示范。

​    添加了一个复杂示例（RoomActivity），模拟酒店的订房情况。

- v1.1.1.1 — 2017/08/12

​    应广大群众需求，添加设置初始位置的方法，类似ListView的setSelection()。

- v1.1.1 — 2017/07/23

​    添加下拉刷新，使用方法见下面的API文档

- v1.1 — 2017/05/23

​    v1.1版本相对初版进行了相对更高度的封装，大大降低了开发者的使用门槛，使得开发者可以像用ListView一样使用PanelList。
​    同时，增加了很多个性化接口，供开发者按照自己的需求改变PanelList的效果。

# 注意

该库会引起输出如下错误信息：

> requestLayout() improperly called by android.widget.ListView{168a808 VFED..C.. ......ID 0,100-150,1700 #f} during layout: running second layout pass

经过查询似乎是ListView 的 FastScroll功能的官方 bug。经过测试，该错误信息**并不会**导致任何功能或性能问题，就是会稍微污染控制台，请知悉。暂无解决办法，抱歉。

# 导入

jitpack 出了点问题，大家先下载 panellistlibrary 添加本地依赖吧，后面有空了再修复，抱歉。

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
	        compile 'com.github.z3896823:PanelList:v1.x.x' //请点击上方的release标签，使用最新版本号
	}
```

# 使用说明

使用本库时，开发者只需要关心中间content部分的adapter怎么写，其余的表头部分只需要将数据传进去就可以了。剩下的数据填充及同步滑动部分将由本库自动完成。
而且表头每个item的高度（纵向表头）和宽度（横向表头）将跟随开发者content部分的item大小自动适应。

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

### 2、activity

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

        initRowDataList();
        initContentDataList();
        initItemWidthList();

        adapter = new AbstractPanelListAdapter(this,pl_root,lv_content) {
            @Override
            protected BaseAdapter getContentAdapter() {
                return null;
            }
        };
        adapter.setInitPosition(10);
        adapter.setSwipeRefreshEnabled(true);
        adapter.setRowDataList(rowDataList);
        adapter.setTitle("example");
        adapter.setOnRefreshListener(new CustomRefreshListener());
        adapter.setContentDataList(contentList);
        adapter.setItemWidthList(itemWidthList);
        adapter.setItemHeight(40);
        pl_root.setAdapter(adapter);

        // 注意：
        // 如果你决定自己实现自己的Column，而不是使用默认的1，2，3。。。
        // 请注意更新contentList时手动更新columnList
    }
}
```

# APIs：

```java
    /**
     * 设置表的标题
     */
    public void setTitle(String title);

    /**
     * 设置表标题的背景
     */
    public void setTitleBackgroundResource(int resourceId);

    /**
     * 设置表头的宽度
     */
    public void setTitleWidth(int titleWidth) ;

    /**
     * 设置表头的高度
     */
    public void setTitleHeight(int titleHeight);

    /**
     * 设置横向表头的标题
     */
    public void setRowDataList(List<String> rowDataList);

    /**
     * 设置纵向表头的内容
     */
    public void setColumnDataList(List<String> columnDataList);

    /**
     * 横向表头的分割线
     */
    public void setRowDivider(Drawable rowDivider) ;

    /**
     * 纵向表头的分割线
     */
    public void setColumnDivider(Drawable columnDivider);

    /**
     * 设置纵向表头的背景色
     * 颜色格式如：#607D8B(String)
     * 下同
     */
    public void setColumnColor(String columnColor);

    /**
     * 设置标题的背景色
     */
    public void setTitleColor(String titleColor);

    /**
     * 设置横向表头的背景色
     */
    public void setRowColor(String rowColor) ;

    /**
     * 设置纵向表头的适配器
     */
    public void setColumnAdapter(BaseAdapter columnAdapter);

    /**
     * 设置content的初始position
     * 比如你想进入这个Activity的时候让第300条数据显示在屏幕上（前提是该数据存在）
     * 那么在这里传入299即可
     */
    public void setInitPosition(int initPosition);

    /**
     * 返回中间内容部分的ListView
     */
    public ListView getContentListView();

	/**
	 * 返回中间内容部分的Adapter
	 */
	public BaseAdapter getContentAdapter();

    /**
     * 返回左边表头的ListView
     */
    public ListView getColumnListView() ;

	/**
	 * 返回左边表头的Adapter
	 */
	public BaseAdapter getColumnAdapter();

    /**
     * 返回上方表头的最外层布局
     */
    public LinearLayout getRowLayout();

    /**
     * 设置是否开启下拉刷新（默认关闭）
     */
    public void setSwipeRefreshEnabled(boolean bool);

    /**
     * 设置监听
     */
    public void setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener listener) ;

	/**
	 * 返回下拉刷新控件
	 */
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
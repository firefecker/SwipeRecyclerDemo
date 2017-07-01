## SwipeRecyclerDemo

### function

- A universal adapter that encapsulates `RecyclerView` enables multiple layouts
- According to the combination of `RecyclerView` and `SwipeRefreshLayout`, the pull-down, refresh and pull-up loads are achieved

### screenshot

<table align="center">
    <tr>
        <td><img src="http://onvxkl171.bkt.clouddn.com/single.gif"/></td>
        <td><img src="http://onvxkl171.bkt.clouddn.com/multi.gif"/></td>
    </tr>
</table>

### use method

- gradle

   1.Add it in your root build.gradle at the end of repositories:
   ~~~bash
        allprojects {
            repositories {
                ...
                maven { url 'https://jitpack.io' }
            }
        }
   ~~~

   Step 2. Add the dependency
   ~~~hash
        dependencies {
                compile 'com.github.firefecker:SwipeRecyclerDemo:0.0.1'
        }

   ~~~

 - maven

    Step 1. Add the JitPack repository to your build file

     ~~~bash
        <repositories>
            <repository>
                <id>jitpack.io</id>
                <url>https://jitpack.io</url>
            </repository>
        </repositories>
     ~~~

     Step 2. Add the dependency
     ~~~bash
        <dependency>
            <groupId>com.github.firefecker</groupId>
            <artifactId>SwipeRecyclerDemo</artifactId>
            <version>0.0.1</version>
        </dependency>
      ~~~


### how to use in code?

 - 1.single layout adapter extends RecyclerCommonAdapter ,and add about generic paradigm,Override convert() method,add a constructor method

    ~~~ bash
        public class TypeListAdapter extends RecyclerCommonAdapter<MultiEntity>{

            public TypeListAdapter(Context context, List<MultiEntity> data) {
                super(context, data,R.layout.item_one);
            }

            @Override
            protected void convert(ViewHolder holder, MultiEntity data, int position) {
                holder.setText(R.id.text1, data.getType()+"")
                        .setText(R.id.text,data.getName())
                        .setImageResource(R.id.image1,R.mipmap.ic_launcher);;
            }
        }
    ~~~

 - 2.multiple layout adapter extends RecyclerCommonAdapter ,and add about generic paradigm,Override convert() method,add a constructor method,implenment MulitiYTypeSupport interface and return about type.

      ~~~ bash
            public class TypeListMulitiAdapter extends RecyclerCommonAdapter<MultiEntity> implements MulitiYTypeSupport<MultiEntity> {

                public TypeListMulitiAdapter(Context mContext, List<MultiEntity> results) {
                    super(mContext,results);
                    setMulitiYTypeSupport(this);
                }

                @Override
                protected void convert(ViewHolder holder, MultiEntity data, int position) {
                    holder.setText(R.id.text1, data.getType()+"")
                            .setText(R.id.text,data.getName());
                    switch (data.getType()) {
                        case 0:
                            holder.setImageResource(R.id.image1,R.mipmap.ic_launcher);
                            break;
                        case 1:
                            holder.setImageResource(R.id.image2,R.mipmap.ic_launcher);
                            break;
                        case 2:
                            holder.setImageResource(R.id.image3,R.mipmap.ic_launcher);
                            break;
                        default:
                            holder.setImageResource(R.id.image1,R.mipmap.ic_launcher);
                            break;
                    }
                }

                @Override
                public int getLayoutId(MultiEntity item) {
                    switch (item.getType()) {
                        case 0:
                            return R.layout.item_one;
                        case 1:
                            return R.layout.item_two;
                        case 2:
                            return R.layout.item_three;
                        default:
                            return R.layout.item_one;
                    }
                }
            }
      ~~~

 - 3.refresh and loadMore about code.

     ~~~bash
        /*寻找并设置控件*/
        swipeRecyclerView = (SwipeRecyclerView) findViewById(R.id.swipeRecycler);
        mRecyclerView = swipeRecyclerView.getRecyclerView();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        /*设置下拉刷新的颜色*/
        swipeRecyclerView.getSwipeRefreshLayout().setColorSchemeColors(colors);
        /*设置上啦的颜色*/
        swipeRecyclerView.setColorSchemeColors(colors);
        /*设置分割线*/
        decoration = new DividerItemDecoration(this, LinearLayoutManager.VERTICAL);
        decoration.setColor(ContextCompat.getColor(this, android.R.color.black));
        decoration.setItemSize(2.0f);
        mRecyclerView.addItemDecoration(decoration);
        /*添加适配器*/
        typeListAdapter = new TypeListAdapter(this, results);
        swipeRecyclerView.setAdapter(typeListAdapter);
        /*实现刷新和加载*/
        swipeRecyclerView.setOnLoadListener(this);

        /*设置刷新*/
        swipeRecyclerView.getRecyclerView().postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRecyclerView.setRefreshing(true);

            }
        },500);
     ~~~

    ~~~bash
            /**
             * 下拉刷新
             */
            @Override
            public void onRefresh() {

            }


            /**
             * 上拉加载
             */
            @Override
            public void onLoadMore() {

            }
     ~~~
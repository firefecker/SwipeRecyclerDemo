package com.fire.test;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.fire.refresh.SwipeRecyclerView;
import com.fire.widget.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/1.
 */
public class SecondActivity extends AppCompatActivity implements SwipeRecyclerView.OnLoadListener {

    private SwipeRecyclerView swipeRecyclerView;
    private RecyclerView mRecyclerView;

    private List<MultiEntity> results = new ArrayList<>();
    private TypeListMulitiAdapter typeListAdapter;
    private DividerItemDecoration decoration;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                //刷新
                initData();
                dismiss(true);
            } else {
                //加载
                MultiEntity multiEntity4;
                for (int i = 0; i < 10; i++) {
                    multiEntity4 = new MultiEntity();
                    multiEntity4.setName("MultiEntity type");
                    multiEntity4.setType(i%3);
                    results.add(multiEntity4);
                }
                dismiss(false);
            }
            typeListAdapter.notifyDataSetChanged();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        /*寻找并设置控件*/
        swipeRecyclerView = (SwipeRecyclerView) findViewById(R.id.swipeRecycler);
        mRecyclerView = swipeRecyclerView.getRecyclerView();
        swipeRecyclerView.getSwipeRefreshLayout().setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        /*设置分割线*/
        decoration = new DividerItemDecoration(this, LinearLayoutManager.VERTICAL);
        decoration.setColor(ContextCompat.getColor(this, android.R.color.black));
        decoration.setItemSize(2.0f);
        mRecyclerView.addItemDecoration(decoration);
        /*添加适配器*/
        typeListAdapter = new TypeListMulitiAdapter(this, results);
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
    }

    private void initData() {
        MultiEntity multiEntity1 = new MultiEntity("MultiEntity type",0);
        MultiEntity multiEntity2 = new MultiEntity("MultiEntity type",1);
        MultiEntity multiEntity3 = new MultiEntity("MultiEntity type",2);
        results.add(multiEntity1);
        results.add(multiEntity2);
        results.add(multiEntity3);
    }

    /**
     * 下拉刷新
     */
    @Override
    public void onRefresh() {
        handler.sendEmptyMessageDelayed(1,3000);
    }

    /**
     * 上拉加载
     */
    @Override
    public void onLoadMore() {
        handler.sendEmptyMessageDelayed(2,3000);
    }

    public void dismiss(boolean isRefresh) {
        if (isRefresh) {
            if(results.size() == 0){
//                swipeRecyclerView.setEmptyView("-- the end --");
            }
            swipeRecyclerView.complete();
        } else {
            if(results.size() == 0){
                swipeRecyclerView.onNoMore("-- the end --");
            }else {
                swipeRecyclerView.stopLoadingMore();
            }
        }
    }

}

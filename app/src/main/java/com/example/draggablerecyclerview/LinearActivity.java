package com.example.draggablerecyclerview;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nightfarmer.draggablerecyclerview.DraggableRecyclerView;
import com.nightfarmer.draggablerecyclerview.ProgressStyle;

import java.util.ArrayList;

public class LinearActivity extends AppCompatActivity {
    private DraggableRecyclerView mRecyclerView;
    private MyAdapter mAdapter;
    private ArrayList<String> listData;
    private int refreshTime = 0;
    private int times = 0;

    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);
        mRecyclerView = (DraggableRecyclerView) this.findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRecyclerView.setLaodingMoreProgressStyle(ProgressStyle.BallRotate);
        mRecyclerView.setArrowImageView(R.drawable.iconfont_downgrey);

        View header = LayoutInflater.from(this).inflate(R.layout.recyclerview_header, (ViewGroup) findViewById(android.R.id.content), false);
        mRecyclerView.addHeaderView(header);

        handler = new Handler();

        mRecyclerView.setLoadingListener(new DraggableRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                refreshTime++;
                times = 0;
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        listData.clear();
                        for (int i = 0; i < 15; i++) {
                            listData.add("item" + i + "after " + refreshTime + " times of refresh");
                        }
                        mAdapter.notifyDataSetChanged();
                        mRecyclerView.refreshComplete();
                    }
                }, 1000);            //refresh data here
            }

            @Override
            public void onLoadMore() {
                if (times < 1) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            for (int i = 0; i < 15; i++) {
                                listData.add("item" + (i + listData.size()));
                            }
                            mAdapter.notifyDataSetChanged();
                            mRecyclerView.loadMoreComplete();
//                            mRecyclerView.refreshComplete();
                        }
                    }, 1000);
                } else {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mAdapter.notifyDataSetChanged();
//                            mRecyclerView.noMoreLoading();
                            mRecyclerView.loadMoreComplete();
                        }
                    }, 1000);
                }
                times++;
            }

            @Override
            public void onCancelRefresh() {
                handler.removeCallbacksAndMessages(null);
            }
        });

        listData = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            listData.add("item" + (i + listData.size()));
        }
        mAdapter = new MyAdapter(listData);

        mRecyclerView.setAdapter(mAdapter);
    }




}

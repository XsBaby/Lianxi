package com.xushuai.lianxi;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.xushuai.lianxi.adapter.MyAdapter;
import com.xushuai.lianxi.bean.UserBean;
import com.xushuai.lianxi.utils.OkHttp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recycleView;
    private List<UserBean.DataBean> list = new ArrayList<>();
    String url = "http://www.yulin520.com/a2a/impressApi/news/mergeList?sign=C7548DE604BCB8A17592EFB9006F9265&pageSize=20&gender=2&ts=1871746850&page=";
    private MyAdapter adapter;
    private SpringView sv;
    private int page = 1;
    private List<UserBean.DataBean> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    private void initView() {
        recycleView = (RecyclerView) findViewById(R.id.recycleView);
        sv = (SpringView) findViewById(R.id.sv);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recycleView.setLayoutManager(layoutManager);

        //设置默认的下拉上拉动画
        sv.setHeader(new DefaultHeader(this));
        sv.setFooter(new DefaultFooter(this));

        adapter = new MyAdapter(this, list);
        recycleView.setAdapter(adapter);
        sv.setType(SpringView.Type.FOLLOW);

        sv.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        sv.onFinishFreshAndLoad();
                        page = 1;
                        list.clear();
                        initData();
                        adapter.notifyDataSetChanged();
                    }
                }, 2000);
            }

            @Override
            public void onLoadmore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        page++;
                        initData();
                        sv.onFinishFreshAndLoad();
                        adapter.notifyDataSetChanged();
                    }
                }, 2000);
            }
        });

        adapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(int position) {
                Toast.makeText(MainActivity.this, list.get(position).getIntroduction(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClickListener(int position) {
                Toast.makeText(MainActivity.this, list.get(position).getIntroduction(), Toast.LENGTH_SHORT).show();
            }
        });

        recycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int itemPosition = layoutManager.findLastVisibleItemPosition();
                if (itemPosition == data.size() - 1) {
                    page++;
                    initData();
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }


    private void initData() {
        OkHttp.getAsync(url, new OkHttp.DataCallBack() {
            @Override
            public void requestFailure(Request request, IOException e) {

            }

            @Override
            public void requestSuccess(String result) throws Exception {
                Gson gson = new Gson();
                UserBean bean = gson.fromJson(result, UserBean.class);
                data = bean.getData();
                list.addAll(data);
                //自定义空指针异常
//                list = null;
                adapter.notifyDataSetChanged();
            }
        });
    }
}
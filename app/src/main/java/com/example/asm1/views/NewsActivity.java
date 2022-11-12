package com.example.asm1.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.example.asm1.R;
import com.example.asm1.adapter.newsAdapter;
import com.example.asm1.services.NewsService;

import java.util.ArrayList;

public class NewsActivity extends AppCompatActivity {

    ListView lvnews;
    private newsAdapter NewsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        lvnews=findViewById(R.id.lvnews);
    }

    private BroadcastReceiver newsbroadcast = new BroadcastReceiver() {
        NewsService news= new NewsService();
        @Override
        public void onReceive(Context context, Intent intent) {
            ArrayList<NewsService.NewsModel> list = (ArrayList<NewsService.NewsModel>) intent
                    .getSerializableExtra("list");
            NewsAdapter= new newsAdapter(list);
            lvnews.setAdapter(NewsAdapter);

        }
    };


    public void onGetNews(View view)
    {
        Intent intent= new Intent(this, NewsService.class);
        startService(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        onGetNews(null);
        IntentFilter newsFilter = new IntentFilter("news");
        LocalBroadcastManager.getInstance(this)
                .registerReceiver(newsbroadcast, newsFilter);
    }
    @Override
    protected void onPause() {
        super.onPause();
//        unregisterReceiver(osReceiver);
        LocalBroadcastManager.getInstance(this). unregisterReceiver(newsbroadcast);
    }
}
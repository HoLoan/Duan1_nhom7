package com.example.asm1.services;

import android.app.IntentService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class NewsService extends IntentService {
    private final AsyncHttpClient aClient= new SyncHttpClient();
    private final String url = "https://api.stackexchange.com/2.3/questions?page=1&pagesize=5&order=desc&sort=activity&site=stackoverflow";
    public NewsService()
    {
        super("NewsServices");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent!=null)
        {

            aClient.get(this,url,new JsonHttpResponseHandler()
            {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    super.onSuccess(statusCode, headers, response);

                    try {
                        JSONArray array= response.getJSONArray("items");
                        ArrayList<NewsModel> list= new ArrayList<>();
                        for (int i = 0; i < array.length() ; i++) {

                            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy -- hh:mm:ss aa");
                            JSONObject object= (JSONObject) array.get(i);
                            Long creation_date=object.getLong("creation_date");

                            String creation_dates = sdf.format(Long.parseLong(String.valueOf(creation_date*1000)));

                            String title=object.getString("title");
                            NewsModel newsModel=new NewsModel(title,creation_dates);
                            list.add(newsModel);

                            Intent outIntent = new Intent("news");
                            outIntent.putExtra("list",list);
                            LocalBroadcastManager.getInstance(NewsService.this).sendBroadcast(outIntent);


                        }

                        Log.d(">>>>>TAG","onSuccess"+list.toString());

                    }catch (JSONException e)
                    {
                        e.printStackTrace();
                    }

                }
            });

        }

    }

    public class NewsModel
    {
        private  String title;
        private String creation_date;
        public NewsModel(String title, Long creation_date)
        {

        }

        public NewsModel(String title, String creation_date) {
            this.title = title;
            this.creation_date = creation_date;
        }

        public String getTitle() {
            return title;
        }

        public void setTile(String tile) {
            this.title = title;
        }

        public String getCreation_date() {



            return creation_date;
        }

        public void setCreation_date(String creation_date) {
            this.creation_date = creation_date;
        }
    }
}

package com.example.asm1.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.asm1.R;
import com.example.asm1.services.NewsService;

import java.util.ArrayList;

public class newsAdapter extends BaseAdapter {
    private ArrayList<NewsService.NewsModel> list;
    public newsAdapter(ArrayList<NewsService.NewsModel> list){
        this.list = list;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int _i, View _view, ViewGroup _viewGroup) {


        View view = _view;
        if(view==null) {
            view = View.inflate(_viewGroup.getContext(), R.layout.one_item_news, null);
            TextView title = view.findViewById(R.id.tvtitle);
            TextView creationdate = view.findViewById(R.id.tvcreationdate);
            ViewHolder holder = new ViewHolder(creationdate,title);
            view.setTag(holder);
        }
        NewsService.NewsModel news = (NewsService.NewsModel) getItem(_i);
        ViewHolder holder =(ViewHolder) view.getTag();
        holder.title.setText((news.getTitle()));
        holder.creationdate.setText(news.getCreation_date() + "");
        return view;
    }
    private static class ViewHolder{
        final TextView creationdate,title;

        public ViewHolder(TextView creationdate, TextView title) {
            this.creationdate = creationdate;
            this.title = title;
        }
    }
}

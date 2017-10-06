package com.example.dinesh.lab3;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by DINESH on 9/18/2017.
 */

public class MyAdapter extends RecyclerView.Adapter<MyHolder> {
    List<String> Description = Collections.emptyList();
    List<String> Image = Collections.emptyList();
    List<String> Links = Collections.emptyList();
    Context c;
    LayoutInflater layoutInflater;

    public MyAdapter(ArrayList<String> Desc, ArrayList<String> img, ArrayList<String> link, Context context) {
        Description = Desc;
        Image = img;
        Links = link;
        c = context;
        layoutInflater = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.listitem, parent, false);
        MyHolder holder = new MyHolder(view,c,Links);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        holder.textView.setText(Description.get(position));
        Uri uri = Uri.parse(Image.get(position));
        Picasso.with(c).load(uri).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return Image.size();
    }
}
class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView textView;
        ImageView imageView;
        List<String> uris;
        Context c;
        public MyHolder(View itemView,Context context,List<String> Uri) {
            super(itemView);
            textView = (TextView)itemView.findViewById(R.id.des);
            imageView = (ImageView)itemView.findViewById(R.id.imgicon);
            c = context;
            uris = Uri;
            itemView.setOnClickListener(this);
        }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(c,Web.class);
        String uri = uris.get(getAdapterPosition());
        intent.putExtra("uri",uri);
        c.startActivity(intent);
    }
}



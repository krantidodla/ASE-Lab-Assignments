package com.example.dinesh.lab3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.android.volley.toolbox.Volley.newRequestQueue;

public class News extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<String> Description,Links,Image;
    MyAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        Description = new ArrayList<>();
        Links = new ArrayList<>();
        Image = new ArrayList<>();
        recyclerView = (RecyclerView)findViewById(R.id.cycle);
        adapter = new MyAdapter(Description,Image,Links,getApplicationContext());
        RequestQueue queue = newRequestQueue(this);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,"https://newsapi.org/v1/articles?source=cnn&sortBy=top&apiKey=7a51c92205a54bd4ab71c00a9e780b73", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(getApplicationContext(),"Yes",Toast.LENGTH_SHORT).show();
                for (int i=0;i<10;i++)
                {
                    try {
                        String img = response.getJSONArray("articles").getJSONObject(i).getString("urlToImage");
                        String Desc = response.getJSONArray("articles").getJSONObject(i).getString("description");
                        String link = response.getJSONArray("articles").getJSONObject(i).getString("url");
                        Image.add(img);
                        Description.add(Desc);
                        Links.add(link);
                        adapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(request);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));
    }
}

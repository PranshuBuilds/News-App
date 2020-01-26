package com.crazy.tajhindnews.Tabs;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.crazy.tajhindnews.DetailsActivity;
import com.crazy.tajhindnews.Model.Function;
import com.crazy.tajhindnews.Model.ListNewsAdapter;
import com.crazy.tajhindnews.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class Tab2 extends Fragment {


    ListView listNews;
    ProgressBar loader;

    ArrayList<HashMap<String, String>> dataList = new ArrayList<>();
    public static final String KEY_AUTHOR = "author";
    public static final String KEY_TITLE = "title";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_URL = "url";
    public static final String KEY_URLTOIMAGE = "urlToImage";
    public static final String KEY_PUBLISHEDAT = "publishedAt";
    public Tab2() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root= inflater.inflate(R.layout.fragment_tab2, container, false);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listNews = view.findViewById(R.id.listNews);
        loader = view.findViewById(R.id.loader);
        listNews.setEmptyView(loader);



        if (Function.isNetworkAvailable(getContext())) {
            DownloadNews newsTask = new DownloadNews();
            newsTask.execute();
        } else {
            Toast.makeText(getContext(), "No Internet Connection", Toast.LENGTH_LONG).show();
        }
    }

    class DownloadNews extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() { super.onPreExecute(); }

        protected String doInBackground(String... args) {
            String xml = Function.excuteGet("https://newsapi.org/v2/top-headlines?country=in&apiKey=656f1d72fe0743f09db260af4bfc3e02");
            return xml;
        }

        @Override
        protected void onPostExecute(String xml) {

            if (xml.length() > 10) { // Just checking if not empty

                try {
                    JSONObject jsonResponse = new JSONObject(xml);
                    JSONArray jsonArray = jsonResponse.optJSONArray("articles");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        HashMap<String, String> map = new HashMap<>();
                        map.put(KEY_AUTHOR, jsonObject.optString(KEY_AUTHOR));
                        map.put(KEY_TITLE, jsonObject.optString(KEY_TITLE));
                        map.put(KEY_DESCRIPTION, jsonObject.optString(KEY_DESCRIPTION));
                        map.put(KEY_URL, jsonObject.optString(KEY_URL));
                        map.put(KEY_URLTOIMAGE, jsonObject.optString(KEY_URLTOIMAGE));
                        map.put(KEY_PUBLISHEDAT, jsonObject.optString(KEY_PUBLISHEDAT));
                        dataList.add(map);
                    }
                } catch (JSONException e) {
                    Toast.makeText(getContext(), "Unexpected error", Toast.LENGTH_SHORT).show();
                }

                ListNewsAdapter adapter = new ListNewsAdapter(getActivity(), dataList);
                listNews.setAdapter(adapter);

                listNews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        Intent i = new Intent(getContext(), DetailsActivity.class);
                        i.putExtra("url", dataList.get(+position).get(KEY_URL));
                        i.putExtra("Title", dataList.get(+position).get(KEY_TITLE));
                        startActivity(i);
                    }
                });

            } else {
                Toast.makeText(getContext(), "No news found", Toast.LENGTH_SHORT).show();
            }
        }
    }

}

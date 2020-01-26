package com.crazy.tajhindnews.Tabs;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.crazy.tajhindnews.DetailsActivity;
import com.crazy.tajhindnews.Model.Function;
import com.crazy.tajhindnews.Model.ListNewsAdapter;
import com.crazy.tajhindnews.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Tab5 extends Fragment {

    ListView listNews;
    ProgressBar loader;

    ArrayList<HashMap<String, String>> dataList = new ArrayList<>();
    public static final String KEY_AUTHOR = "author";
    public static final String KEY_TITLE = "title";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_URL = "url";
    public static final String KEY_URLTOIMAGE = "urlToImage";
    public static final String KEY_PUBLISHEDAT = "publishedAt";
    public Tab5() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root= inflater.inflate(R.layout.fragment_tab2, container, false);
        listNews = root.findViewById(R.id.listNews);
        loader = root.findViewById(R.id.loader);
        listNews.setEmptyView(loader);

        if (Function.isNetworkAvailable(getContext())) {
            Tab5.DownloadNews newsTask = new Tab5.DownloadNews();
            newsTask.execute();
        } else {
            Toast.makeText(getContext(), "No Internet Connection", Toast.LENGTH_LONG).show();
        }
        return root;
    }
    class DownloadNews extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() { super.onPreExecute(); }

        protected String doInBackground(String... args) {
            String xml = Function.excuteGet("https://newsapi.org/v2/top-headlines?country=in&category=sports&apiKey=656f1d72fe0743f09db260af4bfc3e02");
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
//                        Intent i = new Intent(MainActivity.this, DetailsActivity.class);
//                        i.putExtra("url", dataList.get(+position).get(KEY_URL));
//                        startActivity(i);
                        Intent i = new Intent(getContext(), DetailsActivity.class);
                        i.putExtra("url", dataList.get(+position).get(KEY_URL));
                        startActivity(i);
                    }
                });

            } else {
                Toast.makeText(getContext(), "No news found", Toast.LENGTH_SHORT).show();
            }
        }
    }

}

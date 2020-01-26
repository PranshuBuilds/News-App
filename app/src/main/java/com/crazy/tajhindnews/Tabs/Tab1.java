package com.crazy.tajhindnews.Tabs;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.crazy.tajhindnews.Model.BloggerApi;
import com.crazy.tajhindnews.Model.PostList;
import com.crazy.tajhindnews.Model.PostAdapter;
import com.crazy.tajhindnews.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class Tab1 extends Fragment {


    private static final int NUM_COLUMNS = 2;
    RecyclerView recyclerView;
    public Tab1() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tab1, container, false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView=view.findViewById(R.id.recycler_view1);

        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(NUM_COLUMNS, LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(staggeredGridLayoutManager);
//        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),
//                DividerItemDecoration.VERTICAL));
        getdata();

    }
    private void getdata(){
        Call<PostList> postListCall= BloggerApi.getService().getPostList();
        postListCall.enqueue(new Callback<PostList>() {
            @Override
            public void onResponse(Call<PostList> call, Response<PostList> response) {
                PostList list=response.body();
                recyclerView.setAdapter(new PostAdapter(getContext(),list.getItems()));

//                Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<PostList> call, Throwable t) {
                Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();

            }
        });
    }

}

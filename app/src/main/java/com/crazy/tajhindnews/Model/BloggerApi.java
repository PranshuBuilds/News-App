package com.crazy.tajhindnews.Model;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public class BloggerApi {
    private static final String key="AIzaSyDTuHn7okZC8_OtaXkJouvstA8gEj-vPYU";
    private static final String url="https://www.googleapis.com/blogger/v3/blogs/1427504138271990097/posts/";

    public static PostServices postServices=null;
    public static PostServices getService()
    {
        if(postServices==null)
        {
            Retrofit retrofit=new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            postServices =retrofit.create(PostServices.class);
        }
        return postServices;
    }
    public interface PostServices
    {
        @GET("?key="+key)
        Call<PostList> getPostList();

//        for getting post through ids
        @GET("?{postId}/?key="+key)
        Call<Item> getPostById(@Path("postId") String id);

    }
}

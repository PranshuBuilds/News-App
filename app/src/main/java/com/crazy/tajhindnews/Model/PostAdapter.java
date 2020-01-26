package com.crazy.tajhindnews.Model;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.crazy.tajhindnews.DetailActivity;
import com.crazy.tajhindnews.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    private Context context;
    private List<Item> items;

    public PostAdapter(Context context, List<Item> item) {
        this.context = context;
        this.items = item;
    }

    public class PostViewHolder extends RecyclerView.ViewHolder {
        TextView postTitle,postDesc;
        ImageView image;
        LinearLayout linearLayout;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            postDesc =itemView.findViewById(R.id.disc);
            postTitle =itemView.findViewById(R.id.title);
            image =itemView.findViewById(R.id.image);
            linearLayout=itemView.findViewById(R.id.lineShare);
        }
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.post_item,parent,false);
        return new PostViewHolder(view);

    }

    @NonNull

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {


        final Item item=items.get(position);
//        Jsoup Library
        final Document document= Jsoup.parse(item.getContent());
        final Elements elements=document.select("img");
        Image image;
        item.getUrl();

        holder.postTitle.setText(item.getTitle());
        holder.postDesc.setText(document.text());
        final String imgURL=elements.attr("src");
        if (imgURL != null ) {
            Glide.with(context).load(elements.attr("src")).into(holder.image);
        }
        else{ holder.image.setVisibility(View.GONE); }


        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intn =new Intent(Intent.ACTION_SEND);
                intn.setType("text/plain");

                String shareSub="\n\nTAJ HIND NEWS\nhttps://play.google.com/store/apps/details?id=com.crazy.tajhindnews";
                intn.putExtra(Intent.EXTRA_SUBJECT,shareSub);
                intn.putExtra(Intent.EXTRA_TEXT,shareSub);
                context.startActivity(Intent.createChooser(intn,"SHARE with"));

            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(context, DetailActivity.class);
                i.putExtra("url",elements.attr("src"));
                i.putExtra("title",item.getTitle());
                i.putExtra("content",document.text());
                i.putExtra("kind",item.getKind());
                i.putExtra("time",item.getPublished());
//                i.putExtra("labels",item.getLabels(new String<Lable> str));
                context.startActivity(i);

            }
        });
    }



    @Override
    public int getItemCount() {

        return items.size();
    }
}

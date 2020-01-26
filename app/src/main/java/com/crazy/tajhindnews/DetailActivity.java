package com.crazy.tajhindnews;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class DetailActivity extends AppCompatActivity {

    TextView Title,Content,Kind,Time;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        //intents
        Intent intent=getIntent();
        String url = intent.getStringExtra("url");
        String title = intent.getStringExtra("title");
        String content = intent.getStringExtra("content");
        String kind = intent.getStringExtra("kind");
        String time = intent.getStringExtra("time");
//        Glide.with(context).load(elements.attr("src")).into(holder.image);

        Title=findViewById(R.id.Title);
        Content=findViewById(R.id.discc);
        Kind=findViewById(R.id.kind);
        Time=findViewById(R.id.time);
        imageView=findViewById(R.id.img);

        Title.setText(title);
        Content.setText(content);
        Time.setText(time);
        Kind.setText(kind);

        Glide.with(this).load(url).centerCrop().into(imageView);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.share, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_share:
                Intent intn =new Intent(Intent.ACTION_SEND);
                intn.setType("text/plain");
                Intent intent=getIntent();
                String title = intent.getStringExtra("title");

                String shareSub=title+"\n\nTAJ HIND NEWS\nhttps://play.google.com/store/apps/details?id=com.crazy.tajhindnews";
                intn.putExtra(Intent.EXTRA_SUBJECT,shareSub);
                intn.putExtra(Intent.EXTRA_TEXT,shareSub);
                startActivity(Intent.createChooser(intn,"SHARE with"));
                break;
                default:
                    return super.onOptionsItemSelected(item);
        }
        return true;
    }
}

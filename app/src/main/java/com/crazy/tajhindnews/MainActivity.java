package com.crazy.tajhindnews;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.crazy.tajhindnews.Model.ViewPagerAdapter;
import com.crazy.tajhindnews.Tabs.Tab1;
import com.crazy.tajhindnews.Tabs.Tab2;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

//    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;
//    RecyclerView recyclerView;
    InterstitialAd interstitialAd;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout =  findViewById(R.id.tabs);
        viewPager =  findViewById(R.id.viewPager);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        navigationView = findViewById(R.id.drawer_view);

        navigationView.setNavigationItemSelectedListener(this);


        Toolbar toolbar=(Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("TAJ HIND NEWS");


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
//        setSupportActionBar(toolbar);
        DrawerLayout drawerLayout=findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout , toolbar, R.string.action_settings, R.string.action_settings)
        {
            public void onDrawerClosed(View view)
            {
                supportInvalidateOptionsMenu();
                //drawerOpened = false;
            }
            public void onDrawerOpened(View drawerView)
            {
                supportInvalidateOptionsMenu();
                //drawerOpened = true;
            }
        };
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        drawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
        prepare();
        ScheduledExecutorService sedular= Executors.newSingleThreadScheduledExecutor();
        sedular.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(interstitialAd.isLoaded()) {
                            interstitialAd.show();
                        }else{
                        }
                        prepare();

                    }
                });

            }
        },70,70, TimeUnit.SECONDS);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    public void prepare(){
        interstitialAd=new InterstitialAd(this);
        interstitialAd.setAdUnitId("ca-app-pub-9053901924309821/9313141178");
        interstitialAd.loadAd(new AdRequest.Builder().build());

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    @Override
    public void onBackPressed(){
        final AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("Really want to exit ??");
        builder.setCancelable(true);
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });builder.setPositiveButton("Exit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        AlertDialog alertDialog= builder.create();
        alertDialog.show();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.home:
                Toast.makeText(this, "Home page", Toast.LENGTH_SHORT).show();
                break;
            case R.id.share:
                Intent intn =new Intent(Intent.ACTION_SEND);
                intn.setType("text/plain");

                String shareSub="Download the best news app for all latest news\n\nTAJ HIND NEWS\nhttps://play.google.com/store/apps/details?id=com.crazy.tajhindnews";
                intn.putExtra(Intent.EXTRA_SUBJECT,shareSub);
                intn.putExtra(Intent.EXTRA_TEXT,shareSub);
                startActivity(Intent.createChooser(intn,"SHARE with"));
                break;
            case R.id.about:
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://ewappdevelopment.blogspot.com/p/about-us.html"));
                startActivity(browserIntent);
                break;
            case R.id.Privacy:
                Intent browserntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://ewappdevelopment.blogspot.com/2019/12/privacy-policy.html"));
                startActivity(browserntent);
                break;
            case R.id.exit:
                final AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("Really want to exit ??");
                builder.setCancelable(true);
                builder.setNegativeButton("no", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });builder.setPositiveButton("exit", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
                AlertDialog alertDialog= builder.create();
                alertDialog.show();
                break;
        }
        return false;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.action_add:
                Intent intn =new Intent(Intent.ACTION_SEND);
                intn.setType("text/plain");

                String shareSub="Download the best news app for all latest news\n\nTAJ HIND NEWS\nhttps://play.google.com/store/apps/details?id=com.crazy.tajhindnews";
                intn.putExtra(Intent.EXTRA_SUBJECT,shareSub);
                intn.putExtra(Intent.EXTRA_TEXT,shareSub);
                startActivity(Intent.createChooser(intn,"SHARE with"));
                break;
            case R.id.action_About:
                Uri uri = Uri.parse("market://details?id=" +getPackageName());
                Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                // To count with Play market backstack, After pressing back button,
                // to taken back to our application, we need to add following flags to intent.
                goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                        Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                        Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                try {
                    startActivity(goToMarket);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));
                }
                break;
            case R.id.nav_gallery:

                startActivity(new Intent(MainActivity.this, Tab2.class));
                closeOptionsMenu();
                break;
            case R.id.Home:
                startActivity(new Intent(MainActivity.this, Tab1.class));
                closeOptionsMenu();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
}

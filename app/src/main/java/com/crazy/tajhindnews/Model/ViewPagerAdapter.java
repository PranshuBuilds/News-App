package com.crazy.tajhindnews.Model;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.crazy.tajhindnews.Tabs.Tab1;
import com.crazy.tajhindnews.Tabs.Tab2;
import com.crazy.tajhindnews.Tabs.Tab3;
import com.crazy.tajhindnews.Tabs.Tab4;
import com.crazy.tajhindnews.Tabs.Tab5;
import com.crazy.tajhindnews.Tabs.Tab6;

/**
 * Created by Priyabrat on 21-08-2015.
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                Tab1 tab1 = new Tab1();
                return tab1;
            case 1:
                Tab2 tab2 = new Tab2();
                return tab2;
            case 2:
                Tab3 tab3 = new Tab3();
                return tab3;
            case 3:
                Tab4 tab4 = new Tab4();
                return tab4;
            case 4:
                Tab5 tab5 = new Tab5();
                return tab5;
            case 5:
                Tab6 tab6 = new Tab6();
                return tab6;
                default:
                 return null;
        }
//        if (position == 0)
//        {
//            Tab1 tab1 = new Tab1();
//            return tab1;
//        }
//        else if (position == 1)
//        {
//            Tab2 tab2 = new Tab2();
//            return tab2;
//        }
//        else if (position == 2)
//        {
//            Tab3 tab3 = new Tab3();
//            return tab3;
//        }
//
    }

    @Override
    public int getCount() {
        return 6;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        if (position == 0) {
            title = "Latest";
        } else if (position == 1) {
            title = "Top Tending";
        } else if (position == 2) {
            title = "Business";
        }else if (position == 3) {
            title = "Health";
        }else if (position == 4) {
            title = "Sports";
        }else if (position == 5) {
            title = "Entertainment";
        }
        return title;
    }
}

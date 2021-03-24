package com.vc19005768.weatherv2.ui.main;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.vc19005768.weatherv2.CitySearchFragment;
import com.vc19005768.weatherv2.CurrentWeatherFragment;
import com.vc19005768.weatherv2.DailyForecastsFragment;
import com.vc19005768.weatherv2.R;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2, R.string.tab_text_3};
    private final Context mContext;
    private final String locationName;
    private final String locationKey;

    public SectionsPagerAdapter(Context context, FragmentManager fm,
                                String locationName, String locationKey) {
        super(fm);
        mContext = context;
        this.locationName = locationName;
        this.locationKey = locationKey;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.

        switch (position){
            case 0:
                return CurrentWeatherFragment.newInstance(locationName, locationKey);
            case 1:
                return DailyForecastsFragment.newInstance(1,locationKey);
            default:
                return new CitySearchFragment();
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return 3;
    }
}
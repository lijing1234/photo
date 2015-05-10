package me.fireant.photobrowse.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

import me.fireant.photobrowse.ui.PhotoFragment;

/**
 * Created by 火蚁 on 15/5/10.
 */
public class PhotoAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> fragments;

    public PhotoAdapter(FragmentManager fm) {
        super(fm);
        fragments = new ArrayList<>();
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    public void add(String[] imageUrls) {
        if (imageUrls == null || imageUrls.length == 0) return;
        for (int i = 0; i < imageUrls.length; i++) {
            this.fragments.add(PhotoFragment.newInstance(imageUrls[i]));
        }
        notifyDataSetChanged();
    }
}

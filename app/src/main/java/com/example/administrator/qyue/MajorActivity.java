package com.example.administrator.qyue;


import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class MajorActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    ViewPager viewPager;
    BottomNavigationView bottomNavigation;
    List<Fragment> fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_major);
        initView();
    }

    private void initView() {
        viewPager=findViewById(R.id.view_pager_bottom_navigation);
        bottomNavigation=findViewById(R.id.bottom_navigation);

        fragments = new ArrayList<>();
        fragments.add(new AddressFragment());
        fragments.add(new MyFragment());
        fragments.add(new MessageFragment());

        viewPager.addOnPageChangeListener(this);

        bottomNavigation.setOnNavigationItemSelectedListener(item -> {
            viewPager.setCurrentItem(item.getOrder());
            return true;
        });

        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return fragments.get(i);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        });
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        bottomNavigation.getMenu().getItem(i).setChecked(true);
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }
}

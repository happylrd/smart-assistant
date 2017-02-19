package io.happylrd.smartassistant;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import io.happylrd.smartassistant.fragment.AssistantFragment;
import io.happylrd.smartassistant.fragment.GirlFragment;
import io.happylrd.smartassistant.fragment.UserFragment;
import io.happylrd.smartassistant.fragment.WeChatFragment;
import io.happylrd.smartassistant.ui.SettingActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    private List<String> mTitleList;
    private List<Fragment> mFragmentList;

    private FloatingActionButton fab_setting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TODO: replace the ActionBar with Toolbar later
        getSupportActionBar().setElevation(0);

        initData();
        initView();
    }

    private void initData() {
        mTitleList = new ArrayList<>();
        //TODO: need to be replaced with the string resource file later
        mTitleList.add("服务助手");
        mTitleList.add("微信精选");
        mTitleList.add("美女社区");
        mTitleList.add("个人中心");

        mFragmentList = new ArrayList<>();
        mFragmentList.add(new AssistantFragment());
        mFragmentList.add(new WeChatFragment());
        mFragmentList.add(new GirlFragment());
        mFragmentList.add(new UserFragment());
    }

    private void initView() {
        mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        fab_setting = (FloatingActionButton) findViewById(R.id.fab_setting);

        mViewPager.setOffscreenPageLimit(mFragmentList.size());

        fab_setting.setVisibility(View.GONE);

        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragmentList.get(position);
            }

            @Override
            public int getCount() {
                return mFragmentList.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return mTitleList.get(position);
            }
        });

        mTabLayout.setupWithViewPager(mViewPager);

        fab_setting.setOnClickListener(this);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.i("TAG", "position:" + position);
                if (position == 0) {
                    fab_setting.setVisibility(View.GONE);
                } else {
                    fab_setting.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_setting:
                startActivity(new Intent(this, SettingActivity.class));
        }
    }
}

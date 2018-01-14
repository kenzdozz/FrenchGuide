package com.ahtaya.chidozie.frenchguide;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.info).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(intent);
            }
        });

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.app_name);
        toolbar.setBackgroundColor(getResources().getColor(R.color.numbersColorDark));
        changeStatusBarColor(R.color.numbersColor);

        ViewPager pager = findViewById(R.id.viewPager);
        pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));

        final TabLayout tabLayout = findViewById(R.id.main_tab);
        tabLayout.setupWithViewPager(pager);
        //tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0){
                    tabLayout.setBackgroundColor(getResources().getColor(R.color.numbersColorDark));
                    toolbar.setBackgroundColor(getResources().getColor(R.color.numbersColorDark));
                    changeStatusBarColor(R.color.numbersColor);
                }else if (tab.getPosition() == 1){
                    tabLayout.setBackgroundColor(getResources().getColor(R.color.sentenceColorDark));
                    toolbar.setBackgroundColor(getResources().getColor(R.color.sentenceColorDark));
                    changeStatusBarColor(R.color.sentenceColor);
                }else if (tab.getPosition() == 2){
                    tabLayout.setBackgroundColor(getResources().getColor(R.color.colorsColorDark));
                    toolbar.setBackgroundColor(getResources().getColor(R.color.colorsColorDark));
                    changeStatusBarColor(R.color.colorsColor);
                }else {
                    tabLayout.setBackgroundColor(getResources().getColor(R.color.phrasesColorDark));
                    toolbar.setBackgroundColor(getResources().getColor(R.color.phrasesColorDark));
                    changeStatusBarColor(R.color.phrasesColor);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void changeStatusBarColor(int color){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = this.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(color));
        }
    }
}
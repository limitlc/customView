package com.paxw.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.widget.RelativeLayout;

import com.paxw.myapplication.banner.BannerBaseView;
import com.paxw.myapplication.banner.MainBannerView;
import com.paxw.myapplication.utils.GetBannerData;

public class BannerActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);
        RelativeLayout bannerContent = (RelativeLayout) findViewById(R.id.banner_cont);
        BannerBaseView banner = new MainBannerView(this);
        bannerContent.addView(banner);
        banner.update(GetBannerData.getBannerData());
    }




}

package com.paxw.myapplication.banner;

import android.content.Context;
import android.util.AttributeSet;


public class MainBannerView extends BannerBaseView {
	

	private static final float BANNER_RATIO = 0.1f;

	private static final float INDICATOR_RATIO = 0.04f;

	private static final int BANNER_SCROLL_TIME = 4000;
	
	public MainBannerView(Context context) {
		this(context, null);
	}

	public MainBannerView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	protected float getBannerRatio() {
		return BANNER_RATIO;
	}
	
	protected float getIndicatorRatio() {
		return INDICATOR_RATIO;
	}
	
	protected int getCutTime() {
		return BANNER_SCROLL_TIME;
	}
	
}

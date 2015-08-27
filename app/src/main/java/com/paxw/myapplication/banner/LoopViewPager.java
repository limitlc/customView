package com.paxw.myapplication.banner;

/*
 * Copyright (C) 2013 Leszek Mzyk
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.animation.DecelerateInterpolator;
import android.widget.Scroller;

import java.lang.reflect.Field;

public class LoopViewPager extends ViewPager{

    private static final boolean DEFAULT_BOUNDARY_CASHING = false;

    ViewPager.OnPageChangeListener mOuterPageChangeListener;
    private LoopPagerAdapterWrapper mAdapter;
    private boolean mBoundaryCaching = DEFAULT_BOUNDARY_CASHING;
    private PagerAdapter mAdaperInner;
    
    public LoopViewPager(Context context) {
        this(context, null);
    }

    public LoopViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    
    /**
     * helper function which may be used when implementing FragmentPagerAdapter
     *   
     * @param position
     * @param count
     * @return (position-1)%count
     */
    public static int toRealPosition( int position, int count ){
        position = position-1;
        if( position < 0 ){
            position += count;
        }else{
            position = position%count;
        }
        return position;
    }
    
    /**
     * If set to true, the boundary views (i.e. first and last) will never be destroyed
     * This may help to prevent "blinking" of some views 
     * 
     * @param flag
     */
    public void setBoundaryCaching(boolean flag) {
        mBoundaryCaching = flag;
        if (mAdapter != null) {
            mAdapter.setBoundaryCaching(flag);
        }
    }

    @Override
    public void setAdapter(PagerAdapter adapter) {
    	if (adapter == null) return;
    	this.mAdaperInner = adapter;
        mAdapter = new LoopPagerAdapterWrapper(adapter);
        mAdapter.setBoundaryCaching(mBoundaryCaching);
        super.setAdapter(mAdapter);
        setCurrentItem(0, false);
    }
    
    @Override
    public PagerAdapter getAdapter() {
        return mAdapter != null ? mAdapter.getRealAdapter() : mAdapter;
    }

    @Override
    public int getCurrentItem() {
        return mAdapter != null ? mAdapter.toRealPosition(super.getCurrentItem()) : 0;
    }

    public void setCurrentItem(int item, boolean smoothScroll) {
    	if (mAdapter == null) mAdapter = new LoopPagerAdapterWrapper(mAdaperInner);
        int realItem = mAdapter.toInnerPosition(item);
        super.setCurrentItem(realItem, smoothScroll);
    }

    @Override
    public void setCurrentItem(int item) {
        if (getCurrentItem() != item) {
            setCurrentItem(item, true);
        }
    }

    @Override
    public void setOnPageChangeListener(OnPageChangeListener listener) {
        mOuterPageChangeListener = listener;
    };

    private void init() {
		try {
		    Field mScroller;
		    mScroller = ViewPager.class.getDeclaredField("mScroller");
		    mScroller.setAccessible(true); 
		    FixedSpeedScroller scroller = new FixedSpeedScroller(getContext());
		    mScroller.set(this, scroller);
		} catch (NoSuchFieldException e) {
		} catch (IllegalArgumentException e) {
		} catch (IllegalAccessException e) {
		}
        super.setOnPageChangeListener(onPageChangeListener);
    }
    
    private OnPageChangeListener onPageChangeListener = new OnPageChangeListener() {
        private float mPreviousOffset = -1;
        private float mPreviousPosition = -1;

        @Override
        public void onPageSelected(int position) {
        	if(mAdapter == null || mAdapter.getMAdapter() == null){
        		return;
        	}
            int realPosition = mAdapter.toRealPosition(position);
            if (mPreviousPosition != realPosition) {
                mPreviousPosition = realPosition;
                if (mOuterPageChangeListener != null) {
                    mOuterPageChangeListener.onPageSelected(realPosition);
                }
            }
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        	if(mAdapter == null || mAdapter.getMAdapter() == null){
        		return;
        	}
            int realPosition = position;
            if (mAdapter != null) {
                realPosition = mAdapter.toRealPosition(position);
                if (positionOffset == 0
                        && mPreviousOffset == 0
                        && (position == 0 || position == mAdapter.getCount() - 1)) {
                    setCurrentItem(realPosition, false);
                }
            }

            mPreviousOffset = positionOffset;
            if (mOuterPageChangeListener != null && mAdapter != null && mAdapter.getRealAdapter() != null) {
                if (realPosition != mAdapter.getRealCount() - 1) {
                    mOuterPageChangeListener.onPageScrolled(realPosition, positionOffset, positionOffsetPixels);
                } else {
                    if (positionOffset > .5) {
                        mOuterPageChangeListener.onPageScrolled(0, 0, 0);
                    } else {
                        mOuterPageChangeListener.onPageScrolled(realPosition, 0, 0);
                    }
                }
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        	if(mAdapter == null || mAdapter.getMAdapter() == null){
        		return;
        	}
            if (mAdapter != null) {
                int position = LoopViewPager.super.getCurrentItem();
                int realPosition = mAdapter.toRealPosition(position);
                if (state == ViewPager.SCROLL_STATE_IDLE && (position == 0 || position == mAdapter.getCount() - 1)) {
                    setCurrentItem(realPosition, false);
                }
            }
            if (mOuterPageChangeListener != null) {
                mOuterPageChangeListener.onPageScrollStateChanged(state);
            }
        }
    };

	
    
    private class FixedSpeedScroller extends Scroller {

    	private int mDuration = 300;

    	public FixedSpeedScroller(Context context) {
    		super(context, new DecelerateInterpolator());
    	}

    	@Override
    	public void startScroll(int startX, int startY, int dx, int dy, int duration) {
    		// Ignore received duration, use fixed one instead
    		super.startScroll(startX, startY, dx, dy, mDuration);
    	}

    	@Override
    	public void startScroll(int startX, int startY, int dx, int dy) {
    		super.startScroll(startX, startY, dx, dy, mDuration);
    	}

    }
	
	
}
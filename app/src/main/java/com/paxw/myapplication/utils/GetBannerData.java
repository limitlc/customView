package com.paxw.myapplication.utils;

import com.paxw.myapplication.banner.bean.BaseBannerBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/8/28 0028.
 */
public class GetBannerData {
    public static List<BaseBannerBean> getBannerData(){
        List<BaseBannerBean> list = new ArrayList<BaseBannerBean>();
        list.add(new BaseBannerBean("http://mxycsku.qiniucdn.com/group5/M00/5B/0C/wKgBfVXdYkqAEzl0AAL6ZFMAdKk401.jpg"));
        list.add(new BaseBannerBean("http://mxycsku.qiniucdn.com/group6/M00/98/E9/wKgBjVXdGPiAUmMHAALfY_C7_7U637.jpg"));
        list.add(new BaseBannerBean("http://mxycsku.qiniucdn.com/group6/M00/96/F7/wKgBjVXbxnCABW_iAAKLH0qKKXo870.jpg"));
        return list;
    }
}

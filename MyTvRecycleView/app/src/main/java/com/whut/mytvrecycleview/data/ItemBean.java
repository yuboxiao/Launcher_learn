package com.whut.mytvrecycleview.data;

/**
 * Created by owen on 2017/7/6.
 */

public class ItemBean {
    public int id;
    public String title;
    public String imgUrl;
    
    public ItemBean(){}
    
    public ItemBean(int id, String imgUrl) {
        this(id, imgUrl, imgUrl.substring(imgUrl.lastIndexOf("/")));
    }
    
    public ItemBean(int id, String imgUrl, String title) {
        this.id = id;
        this.imgUrl = imgUrl;
        this.title = title;
    }
}

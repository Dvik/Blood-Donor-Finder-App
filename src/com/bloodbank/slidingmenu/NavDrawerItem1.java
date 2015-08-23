package com.bloodbank.slidingmenu;



public class NavDrawerItem1 {
    private boolean showNotify;
    private String title;
 
 
    public NavDrawerItem1() {
 
    }
 
    public NavDrawerItem1(boolean showNotify, String title) {
        this.showNotify = showNotify;
        this.title = title;
    }
 
    public boolean isShowNotify() {
        return showNotify;
    }
 
    public void setShowNotify(boolean showNotify) {
        this.showNotify = showNotify;
    }
 
    public String getTitle() {
        return title;
    }
 
    public void setTitle(String title) {
        this.title = title;
    }
}
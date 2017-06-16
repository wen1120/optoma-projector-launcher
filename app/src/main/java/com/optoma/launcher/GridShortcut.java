package com.optoma.launcher;


public class GridShortcut {

    String itemText;
    int itemImage;

    public GridShortcut(int img, String lab) {

        this.itemImage=img;
        this.itemText=lab;

    }

    public String getItemName()
    {
        return itemText;
    }
    public int getItemImage()
    {
        return itemImage;
    }

}



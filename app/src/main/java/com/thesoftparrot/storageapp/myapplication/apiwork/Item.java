package com.thesoftparrot.storageapp.myapplication.apiwork;

import java.io.Serializable;

public class Item implements Serializable {
    private String name;
    private String imageLink;

    public Item() {}

    public Item(String name, String imageLink) {
        this.name = name;
        this.imageLink = imageLink;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }
}

package com.thesoftparrot.storageapp.myapplication;

public interface AddToCartCallback<T> {
    void onItemAdded(T item, int position);
}

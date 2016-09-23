package com.example.aaron.homerwork1_implicitintent;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Oscar Aaron Revilla Escalona on 9/22/2016.
 */

public class ImageItem implements Parcelable{

    private Bitmap image;
    private String title;

    public ImageItem(Bitmap image, String title) {
        super();
        this.image = image;
        this.title = title;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeArray(new Object[] {  this.image,
                                        this.title});
    }

    public ImageItem(Parcel in){
        Object[] data = new Object[2];

        in.readArray(data.getClass().getClassLoader());
        this.image = (Bitmap) data[0];
        this.title = (String) data[1];
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public ImageItem createFromParcel(Parcel in) {
            return new ImageItem(in);
        }

        public ImageItem[] newArray(int size) {
            return new ImageItem[size];
        }
    };
}

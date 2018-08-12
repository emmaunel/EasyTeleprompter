package com.wordpress.ayo218.easy_teleprompter.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Scripts implements Parcelable{
    private String title;
    private String content;
    private String date_created;

    public Scripts() {
    }

    public Scripts(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public Scripts(String title, String content, String date_created) {
        this.title = title;
        this.content = content;
        this.date_created = date_created;
    }

    protected Scripts(Parcel in) {
        title = in.readString();
        content = in.readString();
        date_created = in.readString();
    }

    public static final Creator<Scripts> CREATOR = new Creator<Scripts>() {
        @Override
        public Scripts createFromParcel(Parcel in) {
            return new Scripts(in);
        }

        @Override
        public Scripts[] newArray(int size) {
            return new Scripts[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getDate_created() {
        return date_created;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(content);
        parcel.writeString(date_created);
    }
}

package com.wordpress.ayo218.easy_teleprompter.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

@Entity(tableName = "Scripts")
public class Scripts implements Parcelable{

    // TODO: 9/6/18 Remove date_created because firebase will handle that 
    @PrimaryKey(autoGenerate = true)
    private int uid;

    private String title;
    private String content;
    private String date_created;
    private String date_updated;
    private int scrollSpeed;
    private int fontSize;
    private int backgroundColor;
    private int fontColor;

    @Ignore
    public Scripts() {
    }

    @Ignore
    public Scripts(String title, String content) {
        this.title = title;
        this.content = content;
    }

    @Ignore
    public Scripts(String title, String content, int scrollSpeed){
        this.title = title;
        this.content = content;
        this.scrollSpeed = scrollSpeed;
    }

    @Ignore
    public Scripts(String title, String content, String date_created, String date_updated) {
        this.title = title;
        this.content = content;
        this.date_created = date_created;
        this.date_updated = date_updated;
    }

    @Ignore
    public Scripts(String title, String content, String date_created, String date_updated,
                   int scrollSpeed, int fontSize, int backgroundColor, int fontColor) {
        this.title = title;
        this.content = content;
        this.date_created = date_created;
        this.date_updated = date_updated;
        this.scrollSpeed = scrollSpeed;
        this.fontSize = fontSize;
        this.backgroundColor = backgroundColor;
        this.fontColor = fontColor;
    }

    @Ignore
    public Scripts(String title, String content, int scrollSpeed, int fontSize, int backgroundColor, int fontColor) {
        this.title = title;
        this.content = content;
        this.date_created = date_created;
        this.date_updated = date_updated;
        this.scrollSpeed = scrollSpeed;
        this.fontSize = fontSize;
        this.backgroundColor = backgroundColor;
        this.fontColor = fontColor;
    }

    public Scripts(int uid, String title, String content, String date_created, String date_updated) {
        this.uid = uid;
        this.title = title;
        this.content = content;
        this.date_created = date_created;
        this.date_updated = date_updated;
    }

    protected Scripts(Parcel in) {
        uid = in.readInt();
        title = in.readString();
        content = in.readString();
        date_created = in.readString();
        date_updated = in.readString();
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

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate_created() {
        return date_created;
    }

    public void setDate_created(String date_created) {
        this.date_created = date_created;
    }

    public String getDate_updated() {
        return date_updated;
    }

    public void setDate_updated(String date_updated) {
        this.date_updated = date_updated;
    }

    public int getScrollSpeed() {
        return scrollSpeed;
    }

    public void setScrollSpeed(int scrollSpeed) {
        this.scrollSpeed = scrollSpeed;
    }

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public int getFontColor() {
        return fontColor;
    }

    public void setFontColor(int fontColor) {
        this.fontColor = fontColor;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(uid);
        dest.writeString(title);
        dest.writeString(content);
        dest.writeString(date_created);
        dest.writeString(date_updated);
        dest.writeInt(scrollSpeed);
        dest.writeInt(fontSize);
        dest.writeInt(backgroundColor);
        dest.writeInt(fontColor);
    }
}

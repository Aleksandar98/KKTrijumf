package com.aca.kktrijumf.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Coach implements Parcelable {
    String name;
    String surName;
    ArrayList<String> groups;
    String id;

    public Coach() {
    }

    public Coach(String name, String surName, ArrayList<String> groups, String id) {
        this.name = name;
        this.surName = surName;
        this.groups = groups;
        this.id = id;
    }


    protected Coach(Parcel in) {
        name = in.readString();
        surName = in.readString();
        groups = in.createStringArrayList();
        id = in.readString();
    }

    public static final Creator<Coach> CREATOR = new Creator<Coach>() {
        @Override
        public Coach createFromParcel(Parcel in) {
            return new Coach(in);
        }

        @Override
        public Coach[] newArray(int size) {
            return new Coach[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public ArrayList<String> getGroups() {
        return groups;
    }

    public void setGroups(ArrayList<String> groups) {
        this.groups = groups;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(surName);
        parcel.writeStringList(groups);
        parcel.writeString(id);
    }
}

package com.aca.kktrijumf.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Player implements Parcelable {
    String id;
    String name;
    String surname;
    String number;
    String parentNumber;
    String coach;
    String group;
    String address;
    ArrayList<Placanje> payments;
    public Player() {
    }

    public Player(String id, String name, String surname, String number, String parentNumber, String coach, String group, String address) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.number = number;
        this.parentNumber = parentNumber;
        this.coach = coach;
        this.group = group;
        this.address = address;
        this.payments = new ArrayList<>();
    }


    protected Player(Parcel in) {
        id = in.readString();
        name = in.readString();
        surname = in.readString();
        number = in.readString();
        parentNumber = in.readString();
        coach = in.readString();
        group = in.readString();
        address = in.readString();
        payments = in.readArrayList(Placanje.class.getClassLoader());
    }

    public static final Creator<Player> CREATOR = new Creator<Player>() {
        @Override
        public Player createFromParcel(Parcel in) {
            return new Player(in);
        }

        @Override
        public Player[] newArray(int size) {
            return new Player[size];
        }
    };

    public ArrayList<Placanje> getPayments() {
        return payments;
    }

    public void setPayments(ArrayList<Placanje> payments) {
        this.payments = payments;
    }

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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getParentNumber() {
        return parentNumber;
    }

    public void setParentNumber(String parentNumber) {
        this.parentNumber = parentNumber;
    }

    public String getCoach() {
        return coach;
    }

    public void setCoach(String coach) {
        this.coach = coach;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(name);
        parcel.writeString(surname);
        parcel.writeString(number);
        parcel.writeString(parentNumber);
        parcel.writeString(coach);
        parcel.writeString(group);
        parcel.writeString(address);
        parcel.writeList(payments);
    }


}

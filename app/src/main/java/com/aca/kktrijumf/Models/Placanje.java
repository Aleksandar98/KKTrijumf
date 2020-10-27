package com.aca.kktrijumf.Models;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import androidx.annotation.Nullable;

import java.io.Serializable;
import java.util.Map;

public class Placanje implements Parcelable {

    String datum;
    String napomena;

    @Override
    public boolean equals(@Nullable Object obj) {
        String datumProsledjeni = (String) obj;

        if (datum.equals(datumProsledjeni))
            return true;
        else
            return false;
    }

    public Placanje(String datum, String napomena) {
        this.datum = datum;
        this.napomena = napomena;
    }

    public Placanje() {
    }

    protected Placanje(Parcel in) {
        datum = in.readString();
        napomena = in.readString();
    }

    public static final Creator<Placanje> CREATOR = new Creator<Placanje>() {
        @Override
        public Placanje createFromParcel(Parcel in) {
            return new Placanje(in);
        }

        @Override
        public Placanje[] newArray(int size) {
            return new Placanje[size];
        }
    };

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }

    public String getNapomena() {
        return napomena;
    }

    public void setNapomena(String napomena) {
        this.napomena = napomena;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(datum);
        parcel.writeString(napomena);
    }

    public boolean platioZaMesec(String currentDate) {
        int dayDatum = Integer.parseInt(datum.subSequence(0, 2).toString());
        int monthDatum = Integer.parseInt(datum.subSequence(3, 5).toString());

        int dayCurrent = Integer.parseInt(currentDate.subSequence(0, 2).toString());
        int monthCurrent = Integer.parseInt(currentDate.subSequence(3, 5).toString());

        return monthCurrent == monthDatum;

//        if (dayCurrent > 15) {
//
//            if (monthDatum == monthCurrent) {
//                if (dayDatum >= 15) {
//                    return true;
//                } else {
//                    return false;
//                }
//            }
//            else if (monthDatum == monthCurrent + 1) {
//                if (dayDatum <= 15) {
//                    return true;
//                } else {
//                    return false;
//                }
//            } else {
//                return false;
//            }
//
//        } else {
//
//            if (monthDatum == monthCurrent) {
//
//                if (dayDatum <= 15) {
//                    return true;
//                } else {
//                    return false;
//                }
//            }
//            else if (monthDatum == monthCurrent - 1) {
//                if (dayDatum >= 15) {
//                    return true;
//                } else {
//                    return false;
//                }
//            }else {
//                return false;
//            }
//
//        }


    }
}


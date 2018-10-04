package com.paulomiguel010.mywebviewssqlite;

import android.os.Parcel;
import android.os.Parcelable;

public class MyContact implements Parcelable {

    private String name, age, number, gender, nationality;

    public MyContact(String name, String age, String phoneNumber, String gender, String nationality) {
        this.name = name;
        this.age = age;
        this.number = phoneNumber;
        this.gender = gender;
        this.nationality = nationality;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    protected MyContact(Parcel in) {
        name = in.readString();
        number = in.readString();
        age = in.readString();
        gender = in.readString();
        nationality = in.readString();



    }

    public static final Creator<MyContact> CREATOR = new Creator<MyContact>() {
        @Override
        public MyContact createFromParcel(Parcel in) {
            return new MyContact(in);
        }

        @Override
        public MyContact[] newArray(int size) {
            return new MyContact[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(name);
        parcel.writeString(gender);
    }

    @Override
    public String toString() {
        return
                "name='" + name + '\'' +
                ", number='" + number + '\'' +
                        ", age='" + age + '\'' +
                        ", gender='" + gender + '\'' +
                        ", nationality='" + nationality + '\'' +
                '}';
    }
}



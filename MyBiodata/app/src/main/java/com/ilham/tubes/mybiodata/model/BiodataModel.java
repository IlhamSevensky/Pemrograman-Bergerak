package com.ilham.tubes.mybiodata.model;

import android.os.Parcel;
import android.os.Parcelable;

public class BiodataModel implements Parcelable {
    private Integer id, age;
    private String name;
    private String address;
    private String gender;
    private String major;
    private String study_program;

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    private String birthdate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getStudy_program() {
        return study_program;
    }

    public void setStudy_program(String study_program) {
        this.study_program = study_program;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    protected BiodataModel(Parcel in) {
        id = in.readInt();
        name = in.readString();
        age = in.readInt();
        gender = in.readString();
        birthdate = in.readString();
        address = in.readString();
        major = in.readString();
        study_program = in.readString();
    }

    public BiodataModel() {

    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeInt(age);
        parcel.writeString(gender);
        parcel.writeString(birthdate);
        parcel.writeString(address);
        parcel.writeString(major);
        parcel.writeString(study_program);
    }

    public static final Creator<BiodataModel> CREATOR = new Creator<BiodataModel>() {
        @Override
        public BiodataModel createFromParcel(Parcel in) {
            return new BiodataModel(in);
        }

        @Override
        public BiodataModel[] newArray(int size) {
            return new BiodataModel[size];
        }
    };
}

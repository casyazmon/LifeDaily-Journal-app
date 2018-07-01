package com.example.kasi.lifedaily.db.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.example.kasi.lifedaily.converter.DateConverter;

import java.util.Date;



@Entity(tableName = "dairy")
@TypeConverters(DateConverter.class)
public class Dairy {

    @PrimaryKey(autoGenerate = true)
    public long id;
    public String name;
    public String address;
    public Date birthday;
    /*public String phone;
    public String email;*/

    @Ignore
    public Dairy() {
        this.name = "";
        this.address = "";
        this.birthday = null;
        /*this.phone = "";
        this.email = "";*/
    }

    public Dairy(String name, String address, Date birthday) {
        this.name = name;
        this.address = address;
        this.birthday = birthday;
        /*this.phone = phone;
        this.email = email;*/
    }
}

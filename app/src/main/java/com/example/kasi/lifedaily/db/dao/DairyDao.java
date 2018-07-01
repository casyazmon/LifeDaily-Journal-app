package com.example.kasi.lifedaily.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.kasi.lifedaily.db.entity.Dairy;


import java.util.List;



import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

@Dao
public interface DairyDao {


    @Query("SELECT * FROM dairy ORDER BY name ASC")
    LiveData<List<Dairy>> findAllDairys();

    @Query("SELECT * FROM dairy")
    List<Dairy> getAllChannels();

    @Query("SELECT * FROM dairy WHERE id=:id")
    Dairy findDairyById(String id);

    @Query("SELECT * FROM dairy WHERE id=:id")
    Dairy findDairy(long id);

    @Insert(onConflict = IGNORE)
    long insertDairy(Dairy dairy);

    @Update
    int updateDairy(Dairy dairy);

    @Update
    void updateDairy(List<Dairy> dairy);

    @Delete
    void deleteDairy(Dairy dairy);

    @Query("DELETE FROM dairy")
    void deleteAll();
}

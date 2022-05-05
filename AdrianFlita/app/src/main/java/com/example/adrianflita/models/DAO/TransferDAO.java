package com.example.adrianflita.models.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.adrianflita.models.ContUser;
import com.example.adrianflita.models.TransferBancar;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface TransferDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertTransfer(TransferBancar transferBancar);

    @Query("SELECT * from transfer where userId = :userId")
    List<TransferBancar> getAllTransactions(long userId);
}

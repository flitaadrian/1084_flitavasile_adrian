package com.example.adrianflita.models.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.adrianflita.models.ContUser;
import com.example.adrianflita.models.CreateUser;

@Dao
public interface ContUserDAO {
        @Insert(onConflict = OnConflictStrategy.REPLACE)
        long insertContUser(ContUser contUser);

        @Query("SELECT * from contUser_join where userId = :userId")
        ContUser contUser(long userId);

        @Query("UPDATE contUser_join SET amount =:amount  WHERE id =:contId")
        int updateCont(long contId, float amount);

        @Query("UPDATE contUser_join SET amountEco =:amountEco, amount =:amount  WHERE id =:contId")
        int updateAmountEco(long contId, float amountEco, float amount);
}

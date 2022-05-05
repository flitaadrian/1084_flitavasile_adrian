package com.example.adrianflita.models.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.adrianflita.models.CreateUser;

@Dao
public interface CreateUserDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insertUser(CreateUser user);

    @Query("SELECT * from createUser where username = :usernameReq and password = :passwordReq")
    CreateUser login(String usernameReq, String passwordReq);

    @Query("UPDATE createUser SET name =:fname ,surname=:lname WHERE id =:userId")
    int updateUser(long userId,String fname, String lname);
}

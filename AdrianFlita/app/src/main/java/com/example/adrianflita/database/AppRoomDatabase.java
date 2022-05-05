package com.example.adrianflita.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.adrianflita.models.ContUser;
import com.example.adrianflita.models.CreateUser;
import com.example.adrianflita.models.DAO.ContUserDAO;
import com.example.adrianflita.models.DAO.CreateUserDAO;
import com.example.adrianflita.models.DAO.TransferDAO;
import com.example.adrianflita.models.TransferBancar;

@Database(entities = {CreateUser.class, ContUser.class, TransferBancar.class},exportSchema = true, version = 5)
public abstract class AppRoomDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "BankingDb";

    private static AppRoomDatabase appRoomDatabase;

    public synchronized static AppRoomDatabase getInstance(Context context){
        if (appRoomDatabase == null){
            appRoomDatabase = Room.databaseBuilder(context, AppRoomDatabase.class, DATABASE_NAME)
                            .fallbackToDestructiveMigration()
                            .build();

        }
        return appRoomDatabase;
    }

    public abstract CreateUserDAO getCreateUserDAO();
    public abstract ContUserDAO getContUserDAO();
    public abstract TransferDAO getTransferDAO();
}

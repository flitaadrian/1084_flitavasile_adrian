package com.example.adrianflita.models;


import static androidx.room.ForeignKey.CASCADE;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.adrianflita.utils.DateConverter;

import java.util.Date;

@Entity(
        tableName = "transfer",
        foreignKeys = {
                @ForeignKey(
                        entity = CreateUser.class,
                        parentColumns = "id",
                        childColumns = "id",
                        onDelete = CASCADE
                ),
                @ForeignKey(
                        entity = ContUser.class,
                        parentColumns = "id",
                        childColumns = "id")
        },
        indices = {
                @Index(value = "userId"),
                @Index(value = "contId")
        })
public class TransferBancar {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long id;

    @ColumnInfo(name = "amount")
    private float amount;

    @ColumnInfo(name = "date_transaction")
    @TypeConverters({DateConverter.class})
    private Date dateExpires;

    @ColumnInfo(name = "userId")
    private long userId;

    @ColumnInfo(name = "contId")
    private long contId;

    public TransferBancar(float amount, Date dateExpires, long userId, long contId) {
        this.amount = amount;
        this.dateExpires = dateExpires;
        this.userId = userId;
        this.contId = contId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public Date getDateExpires() {
        return dateExpires;
    }

    public void setDateExpires(Date dateExpires) {
        this.dateExpires = dateExpires;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getContId() {
        return contId;
    }

    public void setContId(long contId) {
        this.contId = contId;
    }
}

package com.example.adrianflita.models;

import static androidx.room.ForeignKey.CASCADE;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.adrianflita.utils.DateConverter;

import java.io.Serializable;
import java.util.Date;

@Entity(
        tableName = "contUser_join",
        foreignKeys = {
                @ForeignKey(
                        entity = CreateUser.class,
                        parentColumns = "id",
                        childColumns = "id",
                        onDelete = CASCADE
                )

        }, indices = {@Index(value = {"userId"}, unique = true)})
public class ContUser implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long id;

    private String IBAN;

    @ColumnInfo(name = "dateExpires")
    @TypeConverters({DateConverter.class})
    private Date dateExpires;

    @ColumnInfo(name = "amount")
    private float amount;

    @ColumnInfo(name = "amountEco")
    private float amountEco;

    @ColumnInfo(name = "userId")
    private long userId;


    public ContUser(String IBAN, Date dateExpires, float amount, float amountEco, long userId) {
        this.IBAN = IBAN;
        this.dateExpires = dateExpires;
        this.amount = amount;
        this.amountEco = amountEco;
        this.userId = userId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIBAN() {
        return IBAN;
    }

    public void setIBAN(String IBAN) {
        this.IBAN = IBAN;
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

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }


    public float getAmountEco() {
        return amountEco;
    }

    public void setAmountEco(float amountEco) {
        this.amountEco = amountEco;
    }
}

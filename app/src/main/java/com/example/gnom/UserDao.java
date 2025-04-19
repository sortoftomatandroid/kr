package com.example.gnom;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface UserDao {

    @Query("SELECT * FROM user WHERE id = :id")
    User get(int id);

    @Query("SELECT * FROM user WHERE email = :email")
    User getByEmail(String email);

    @Query("SELECT * FROM user WHERE phone = :phone")
    User getByPhone(String phone);

    @Query("SELECT * FROM user WHERE (email = :emailOrPhone OR phone = :emailOrPhone) AND password = :password")
    User login(String emailOrPhone, String password);

    @Insert
    long insert(User user);

    @Query("UPDATE user SET password = :password WHERE email = :email")
    void password(String email, String password);

    @Update
    void update(User user);
}

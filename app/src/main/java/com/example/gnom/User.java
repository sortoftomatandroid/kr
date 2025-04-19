package com.example.gnom;

import android.graphics.Bitmap;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.time.LocalDate;

@Entity
@TypeConverters({Converters.class})
public class User {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;
    private String surname;
    private String email;
    private String phone;
    private String password;

    private Bitmap avatar;
    private LocalDate created = LocalDate.now();

    public User(String name, String surname, String email, String password) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getSurname() { return surname; }

    public void setSurname(String surname) { this.surname = surname; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }

    public void setPhone(String phone) { this.phone = phone; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    public Bitmap getAvatar() { return avatar; }

    public void setAvatar(Bitmap avatar) { this.avatar = avatar; }

    public LocalDate getCreated() { return created; }

    public void setCreated(LocalDate created) { this.created = created; }
}

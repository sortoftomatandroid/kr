package com.example.gnom;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.annotation.NonNull;
import androidx.room.TypeConverter;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class Converters {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT);

    @TypeConverter
    public static LocalDate parse(String text) {
        return LocalDate.parse(text, formatter);
    }

    @TypeConverter
    public static String format(@NonNull LocalDate localDate) {
        return localDate.format(formatter);
    }

    @TypeConverter
    public byte[] compress(Bitmap bitmap) {
        if (bitmap == null) {
            return null;
        }
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    @TypeConverter
    public Bitmap decode(byte[] bytes) {
        return bytes == null ? null : BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }
}

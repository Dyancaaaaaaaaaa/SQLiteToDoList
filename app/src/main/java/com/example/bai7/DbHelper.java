package com.example.bai7;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.sql.SQLClientInfoException;

public class DbHelper  extends SQLiteOpenHelper {
    public DbHelper(Context context) {

        super(context, "ToDoDatabase", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE TODO (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                " TITLE TEXT, CONTENT TEXT, DATE TEXT, TYPE TEXT,STATUS INTEGER )";
        sqLiteDatabase.execSQL(sql);

        String data = "INSERT INTO TODO VALUES (1, 'Học Java', 'Học Java cơ bản', '25/12/2023', 'Bình thường'), " +
                                              "(2, 'Học React Native', 'Học React Native cơ bản', '26/1/2023', 'Khó'), " +
                                               "(3, 'Học Kotlin', 'Học Kotlin cơ bản', '27/2/2023', 'Dễ ')";
        sqLiteDatabase.execSQL(data);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        if(i != i1){
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS TODO");
            onCreate(sqLiteDatabase);
        }


    }
}

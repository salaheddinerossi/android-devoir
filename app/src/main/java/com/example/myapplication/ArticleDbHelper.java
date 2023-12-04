package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class ArticleDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Articles.db";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + ArticleContract.ArticleEntry.TABLE_NAME + " (" +
                    ArticleContract.ArticleEntry._ID + " INTEGER PRIMARY KEY," +
                    ArticleContract.ArticleEntry.COLUMN_NAME_LABEL + " TEXT," +
                    ArticleContract.ArticleEntry.COLUMN_NAME_PU + " INTEGER)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + ArticleContract.ArticleEntry.TABLE_NAME;

    public ArticleDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public void insertArticle(String libelle, int pu) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ArticleContract.ArticleEntry.COLUMN_NAME_LABEL, libelle);
        values.put(ArticleContract.ArticleEntry.COLUMN_NAME_PU, pu);

        long newRowId = db.insert(ArticleContract.ArticleEntry.TABLE_NAME, null, values);
    }

    public List<Article> getAllArticles() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Article> articles = new ArrayList<>();

        String[] projection = {
                ArticleContract.ArticleEntry._ID,
                ArticleContract.ArticleEntry.COLUMN_NAME_LABEL,
                ArticleContract.ArticleEntry.COLUMN_NAME_PU
        };

        Cursor cursor = db.query(
                ArticleContract.ArticleEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(ArticleContract.ArticleEntry._ID));
            String libelle = cursor.getString(cursor.getColumnIndexOrThrow(ArticleContract.ArticleEntry.COLUMN_NAME_LABEL));
            int pu = cursor.getInt(cursor.getColumnIndexOrThrow(ArticleContract.ArticleEntry.COLUMN_NAME_PU));
            articles.add(new Article(id, libelle, pu));
        }
        cursor.close();

        return articles;
    }
}

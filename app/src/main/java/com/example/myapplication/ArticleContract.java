package com.example.myapplication;

import android.provider.BaseColumns;

public final class ArticleContract {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private ArticleContract() {}

    /* Inner class that defines the table contents */
    public static class ArticleEntry implements BaseColumns {
        public static final String TABLE_NAME = "article";
        public static final String COLUMN_NAME_LABEL = "libelle";
        public static final String COLUMN_NAME_PU = "PU";
    }
}

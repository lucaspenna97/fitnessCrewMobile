package com.example.fitnesscrew.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.fitnesscrew.bean.User;

public class Banco {

    private static SQLiteOpenHelper sqLiteOpenHelper = null;

    private static final int VERSAO_BANCO = 1;

    private static String CREATE_TABLE_USER =
            "CREATE TABLE IF NOT EXISTS " + User.TABLE_NAME + " (" +
                    User.CPF    + " PRIMARY KEY," +
                    User.NOME   + "," +
                    User.IDADE  + "," +
                    User.SEXO   + "," +
                    User.ALTURA + "," +
                    User.PESO   + "," +
                    User.EMAIL  + ");";

    private static final String[] SCRIPT_CREATE_DATABASE = new String [] {CREATE_TABLE_USER};

    private static final String[] SCRIPT_DROP_TABLES = {"DROP TABLE IF EXISTS " + User.TABLE_NAME};

    private Banco() {}

    public static SQLiteOpenHelper getBanco(Context ctx){

        if(sqLiteOpenHelper == null ) {

            sqLiteOpenHelper = new SQLiteOpenHelper(ctx, "database", null, VERSAO_BANCO) {

                @Override
                public void onCreate(SQLiteDatabase sqLiteDatabase) {
                    for (String sql : SCRIPT_CREATE_DATABASE) { sqLiteDatabase.execSQL(sql); }
                }

                @Override
                public void onUpgrade(SQLiteDatabase sqLiteDatabase, int versaoAntiga, int versaoNova) {

                    for (String sql : SCRIPT_DROP_TABLES){ sqLiteDatabase.execSQL(sql); }
                    onCreate(sqLiteDatabase);
                }
            };

        }

        return sqLiteOpenHelper;
    }

}

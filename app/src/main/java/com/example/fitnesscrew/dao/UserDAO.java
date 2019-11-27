package com.example.fitnesscrew.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.fitnesscrew.bean.User;

import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    private static SQLiteOpenHelper sqLiteOpenHelper;
    private static SQLiteDatabase sqLiteDatabase;

    public UserDAO(Context context) {
        sqLiteOpenHelper = Banco.getBanco(context);
    }

    private static void getWritableDatabase(){
        sqLiteDatabase = sqLiteOpenHelper.getWritableDatabase();
    }

    private static void getReadableDatabase(){
        sqLiteDatabase = sqLiteOpenHelper.getReadableDatabase();
    }



    public void insertPedido(User user){

        getWritableDatabase();
        sqLiteDatabase.replace(User.TABLE_NAME,null, userContentValues(user, false));

    }

    public void updateUser(User user){
        getWritableDatabase();

        ContentValues contentValues = userContentValues(user, true);
        sqLiteDatabase.update(User.TABLE_NAME, contentValues, User.CPF + " = '" + user.getCpf() + "'" , null);

    }

    public void deletePedido (String cpf){

        getWritableDatabase();
        sqLiteDatabase.delete(User.TABLE_NAME, User.CPF + " = '" + cpf + "'", null);
    }

    public User getUser(String cpf) {
        getReadableDatabase();

        User user = null;

        String sql = "SELECT * FROM user WHERE " + User.CPF + " = '" + cpf + "';" ;

        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);

        //Caso exista pedido pendente
        if (cursor != null){
            if(cursor.moveToFirst()) {
                user = new User();
                user.setCpf(cursor.getString(cursor.getColumnIndex(User.CPF)));
                user.setNome(cursor.getString(cursor.getColumnIndex(User.NOME)));
                user.setIdade(cursor.getString(cursor.getColumnIndex(User.IDADE)));
                user.setSexo(cursor.getString(cursor.getColumnIndex(User.SEXO)));
                user.setAltura(cursor.getString(cursor.getColumnIndex(User.ALTURA)));
                user.setPeso(cursor.getString(cursor.getColumnIndex(User.PESO)));
                user.setEmail(cursor.getString(cursor.getColumnIndex(User.EMAIL)));
            }
        }

        if (cursor != null) cursor.close();

        return user;
    }

    public List<User> getUsers(){
        getReadableDatabase();

        String sql = "SELECT * FROM " + User.TABLE_NAME + ";";

        List<User> users = new ArrayList<>();
        User user;

        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);

        //Caso exista pedido pendente
        if (cursor != null && cursor.moveToFirst()){

            do{
                user = new User();
                user.setCpf(cursor.getString(cursor.getColumnIndex(User.CPF)));
                user.setNome(cursor.getString(cursor.getColumnIndex(User.NOME)));
                user.setIdade(cursor.getString(cursor.getColumnIndex(User.IDADE)));
                user.setSexo(cursor.getString(cursor.getColumnIndex(User.SEXO)));
                user.setAltura(cursor.getString(cursor.getColumnIndex(User.ALTURA)));
                user.setPeso(cursor.getString(cursor.getColumnIndex(User.PESO)));
                user.setEmail(cursor.getString(cursor.getColumnIndex(User.EMAIL)));

                users.add(user);
            } while (cursor.moveToNext());
        }

        if (cursor != null) cursor.close();

        return users;
    }

    private ContentValues userContentValues (User user, boolean updateUser) {

        ContentValues contentValues = new ContentValues();

        if (!updateUser) contentValues.put(User.CPF, user.getCpf());

        contentValues.put(User.NOME, user.getNome());
        contentValues.put(User.IDADE, user.getIdade());
        contentValues.put(User.SEXO, user.getSexo());
        contentValues.put(User.ALTURA, user.getAltura());
        contentValues.put(User.PESO, user.getPeso());
        contentValues.put(User.EMAIL, user.getEmail());

        return contentValues;
    }



}

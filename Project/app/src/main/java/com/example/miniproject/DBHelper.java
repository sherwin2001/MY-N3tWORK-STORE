package com.example.miniproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.miniproject.Models.OrdersModel;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    final static String DBNAME="mydatabase.db";
    final static int DBVERSION=3;
    public DBHelper(@Nullable Context context) {
        super(context, DBNAME, null, DBVERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
       sqLiteDatabase.execSQL(
               "create table orders " +
                       "(id integer primary key autoincrement," +
                       "name text," +
                       "phone text," +
                       "email text," +
                       "address text," +
                       "price int," +
                       "image int," +
                       "quantity int," +
                       "itemname text)"
       );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP table if exists orders");
        onCreate(sqLiteDatabase);
    }

    public boolean insertOrder(String name, String phone, String email, String address, int price, int image, int quantity, String itemname){
        SQLiteDatabase database=getReadableDatabase();
        ContentValues values =new ContentValues();
        values.put("name",name);
        values.put("phone",phone);
        values.put("email",email);
        values.put("address",address);
        values.put("price",price);
        values.put("image",image);
        values.put("quantity",quantity);
        values.put("itemname",itemname);
        long id= database.insert("orders",null,values);
        if(id<=0){
            return false;
        }else{
            return true;
        }
    }

    public ArrayList<OrdersModel> getOrders(){
        ArrayList<OrdersModel> orders = new ArrayList<>();
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor= database.rawQuery("Select id, itemname, image, price from orders ", null);
        if(cursor.moveToFirst()){
            while(cursor.moveToNext()){
                OrdersModel model=new OrdersModel(0,"","","");
                model.setOrderNumber(cursor.getInt(0)+"");
                model.setSoldItemName(cursor.getString(1));
                model.setOrderImage(cursor.getInt(2));
                model.setPrice(cursor.getInt(3)+"");
                orders.add(model);
            }
        }
        cursor.close();
        database.close();
        return orders;
    }
    public Cursor getOrderById(int Id){
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor= database.rawQuery("Select * from orders where id = "+ Id, null);

        if(cursor!=null)
            cursor.moveToFirst();

        return cursor;
    }
    public boolean updateOrder(String name, String phone, String email, String address, int price, int image, int quantity, String itemname, int id){
        SQLiteDatabase database=getReadableDatabase();
        ContentValues values =new ContentValues();
        values.put("name",name);
        values.put("phone",phone);
        values.put("email",email);
        values.put("address",address);
        values.put("price",price);
        values.put("image",image);
        values.put("quantity",quantity);
        values.put("itemname",itemname);
        long row= database.update("orders",values,"id="+id, null);
        if(row<=0){
            return false;
        }else{
            return true;
        }
    }

    public int deletedOrder(String id){
        SQLiteDatabase database=this.getWritableDatabase();
        return database.delete("orders", "id="+id,null);
    }
}

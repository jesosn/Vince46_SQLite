package com.codingke.vince46_sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * descreption:
 * company:
 * Created by vince on 15/3/13.
 */
public class DatabaseAdapter {

    private DatabaseHelper dbHelper;

    public DatabaseAdapter(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    //事务处理
    public void transaction(){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.beginTransaction();//开始事务
        try {
            db.execSQL("insert into dog(name,age)values('duang',4)");
            db.execSQL("insert into dog(name,age)values('guang',3)");
            db.setTransactionSuccessful();//设置事务的成功标记（true）
        }finally {
            db.endTransaction();//结束事务，判断事务标记是否为true，如果为true那么就提交事务，否则回滚
        }
        db.close();
    }

    public void rawAdd(Dog dog){
        String sql = "insert into dog(name,age) values (?,?)";
        Object[] args = {dog.getName(),dog.getAge()};
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL(sql,args);
        db.close();

    }

    //添加操作
    public void add(Dog dog){
        //获取操作数据库的工具类
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PetMetaData.DogTable.NAME,dog.getName());
        values.put(PetMetaData.DogTable.AGE,dog.getAge());

        //参数（表名，可以为NULL的列名，ContentValues）
        //合法：insert into dog(name,age)values('xx',2)
        //不合法：insert into dog(name) values(null)
        db.insert(PetMetaData.DogTable.TABLE_NAME, PetMetaData.DogTable.NAME, values);
        db.close();
    }

    public void rawDelete(int id){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String sql = "delete from dog where _id =?";
        Object[] args = {id};
        db.execSQL(sql, args);
        db.close();
    }
    //删除操作
    public void delete(int id){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String whereClause = PetMetaData.DogTable._ID+"=?";
        String[] whereArgs = {String.valueOf(id)};
        //表名，条件，条件的值
        db.delete(PetMetaData.DogTable.TABLE_NAME, whereClause, whereArgs);
        db.close();
    }

    public void rawUpdate(Dog dog){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String sql = "update dog set name=?,age=? where _id=?";
        Object[] args = {dog.getName(),dog.getAge(),dog.getId()};
        db.execSQL(sql, args);
        db.close();
    }
    //更新操作
    public void update(Dog dog){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PetMetaData.DogTable.NAME,dog.getName());
        values.put(PetMetaData.DogTable.AGE,dog.getAge());
        String whereClause = PetMetaData.DogTable._ID+"=?";
        String[] whereArgs = {String.valueOf(dog.getId())};
        //表名，ContentValues,条件，条件的值
        db.update(PetMetaData.DogTable.TABLE_NAME, values, whereClause, whereArgs);
        db.close();
    }

    public Dog rawFindById(int id){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = "select _id,name,age from dog where _id=?";
        Cursor c = db.rawQuery(sql,new String[]{String.valueOf(id)});
        Dog dog = null;
        if(c.moveToNext()){
            dog = new Dog();
            dog.setId(c.getInt(c.getColumnIndexOrThrow(PetMetaData.DogTable._ID)));
            dog.setName(c.getString(c.getColumnIndexOrThrow(PetMetaData.DogTable.NAME)));
            dog.setAge(c.getInt(c.getColumnIndexOrThrow(PetMetaData.DogTable.AGE)));
        }
        c.close();
        db.close();
        return dog;
    }

    //根据ID查询单记录
    public Dog findById(int id){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] columns = {PetMetaData.DogTable._ID,PetMetaData.DogTable.NAME,PetMetaData.DogTable.AGE};
        //是否去除重复记录，表名，要查询的列，查询条件，查询条件的值，分组条件，分组条件的值，排序，分页条件
        Cursor c = db.query(true, PetMetaData.DogTable.TABLE_NAME, columns, PetMetaData.DogTable._ID+"=?", new String[]{String.valueOf(id)}, null, null, null, null);
        Dog dog = null;
        if(c.moveToNext()){
            dog = new Dog();
            dog.setId(c.getInt(c.getColumnIndexOrThrow(PetMetaData.DogTable._ID)));
            dog.setName(c.getString(c.getColumnIndexOrThrow(PetMetaData.DogTable.NAME)));
            dog.setAge(c.getInt(c.getColumnIndexOrThrow(PetMetaData.DogTable.AGE)));
        }
        c.close();
        db.close();
        return dog;
    }

    public ArrayList<Dog> rawFindAll(){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = "select _id,name,age from dog";
        Cursor c = db.rawQuery(sql,null);
        ArrayList<Dog> dogs = new ArrayList<>();
        Dog dog = null;
        while(c.moveToNext()){
            dog = new Dog();
            dog.setId(c.getInt(c.getColumnIndexOrThrow(PetMetaData.DogTable._ID)));
            dog.setName(c.getString(c.getColumnIndexOrThrow(PetMetaData.DogTable.NAME)));
            dog.setAge(c.getInt(c.getColumnIndexOrThrow(PetMetaData.DogTable.AGE)));
            dogs.add(dog);
        }
        c.close();
        db.close();
        return dogs;
    }
    //查询所有
    public ArrayList<Dog> findAll(){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] columns = {PetMetaData.DogTable._ID,PetMetaData.DogTable.NAME,PetMetaData.DogTable.AGE};
        //是否去除重复记录，表名，要查询的列，查询条件，查询条件的值，分组条件，分组条件的值，排序，分页条件
        Cursor c = db.query(true, PetMetaData.DogTable.TABLE_NAME, columns, null, null, null, null, null, null);

        ArrayList<Dog> dogs = new ArrayList<>();
        Dog dog = null;
        while(c.moveToNext()){
            dog = new Dog();
            dog.setId(c.getInt(c.getColumnIndexOrThrow(PetMetaData.DogTable._ID)));
            dog.setName(c.getString(c.getColumnIndexOrThrow(PetMetaData.DogTable.NAME)));
            dog.setAge(c.getInt(c.getColumnIndexOrThrow(PetMetaData.DogTable.AGE)));
            dogs.add(dog);
        }
        c.close();
        db.close();
        return dogs;
    }
}

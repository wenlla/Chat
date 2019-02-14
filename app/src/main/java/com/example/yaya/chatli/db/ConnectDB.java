package com.example.yaya.chatli.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.read.biff.BiffException;

public class ConnectDB extends SQLiteOpenHelper  {
   //建立full-text-search数据库
   private static final String TAG = "ChatbotDatabase";
    private static final String DB_NAME = "chat.db";
    private static final int DB_VERSION = 1;

    // chatbot表
    private static final String TABLE_NAME_CHATBOT = "chatbot";
    private static final String COL_RESPUESTA = "respuesta";
    private static final String COL_URL = "url";

    //conversacion 表
    private static final String TABLE_NAME_CONVERSACION = "conversacion";
    private static final String COL_PREGUNTAS = "preguntas";
    private static final String COL_RESPUESTAS = "respuestas";


    private static ConnectDB connectDB;

    // singleton class
    public static ConnectDB getInstance(Context context) {
        if (null == connectDB)
            connectDB = new ConnectDB(context);
        return connectDB;
    }

    private ConnectDB(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // create the CHTBOT table
        db.execSQL("CREATE VIRTUAL TABLE " + TABLE_NAME_CHATBOT +
                " USING fts3 ( " + COL_RESPUESTA + "," + COL_URL +")");

        // create the CONVERSACIÓN  table
        db.execSQL("CREATE VIRTUAL TABLE " + TABLE_NAME_CONVERSACION +
                " USING fts3 ( " + COL_PREGUNTAS + ","+ COL_RESPUESTAS +")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_CHATBOT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_CONVERSACION);
        onCreate(db);
    }


    public void initDataChat(String respuesta, String url) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("respuesta", respuesta);
        cv.put("url", url);
        database.insert(TABLE_NAME_CHATBOT, null, cv);
    }

    public void initDataConver(String preguntas, String respuestas) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("preguntas",preguntas);
        cv.put("respuestas", respuestas);
        database.insert(TABLE_NAME_CONVERSACION, null, cv);
    }


// insert

    public void insertCHATBOTData(ArrayList<chatbot> mList) {
        int rowsInserted = -1;

        SQLiteDatabase database = getWritableDatabase();
        database.beginTransaction(); // START Transaction

        try {
            for (chatbot chatbot : mList) {
                ContentValues values = new ContentValues();
                values.put(COL_RESPUESTA, chatbot.getRespuesta());
                values.put(COL_URL, chatbot.getUrl());
                //values.put(COL_INDEXER,chatbot.getIndexer());


                database.insert(TABLE_NAME_CHATBOT, null, values);
            }
            database.setTransactionSuccessful(); //TRANSACTION SUCCESSFUL

            rowsInserted = mList.size();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println(">>>>ROWS INSERTED CHATBOT= " + rowsInserted);
            database.endTransaction(); // END Transaction
        }
    }

//inseart

    public void insertCONVERSACIONData(ArrayList<conversacion> cList) {
        int rowsInserted = -1;

        SQLiteDatabase database = getWritableDatabase();
        database.beginTransaction(); // START Transaction

        try {
            for (conversacion conversacion : cList) {
                ContentValues values = new ContentValues();
                values.put(COL_PREGUNTAS,conversacion.getPreguntas());
                values.put(COL_RESPUESTAS,conversacion.getRespuestas());

                database.insert(TABLE_NAME_CONVERSACION, null, values);
            }
            database.setTransactionSuccessful(); //TRANSACTION SUCCESSFUL

            rowsInserted = cList.size();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println(">>>>ROWS INSERTED CONVERSACIÓN = " + rowsInserted);
            database.endTransaction(); // END Transaction
        }
    }

// delete
    public void deleteCHATBOTData() {
        SQLiteDatabase database = getWritableDatabase();
        database.delete(TABLE_NAME_CHATBOT, null, null);
        database.close();
    }

    public void deleteCONVERSACIONData() {
        SQLiteDatabase database = getWritableDatabase();
        database.delete(TABLE_NAME_CONVERSACION, null, null);
        database.close();
    }

  // search

    public ArrayList<chatbot> searchCHATBOT(String s) {
        ArrayList<chatbot> listInfo = new ArrayList<>();

        SQLiteDatabase database = getReadableDatabase();

        Cursor cursor = database.query(true,TABLE_NAME_CHATBOT,
                 new String[]{"rowid", COL_RESPUESTA, COL_URL}, TABLE_NAME_CHATBOT +
                        " MATCH ?", new String[]{s + "*"}, null, null, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {
                chatbot informacion = new chatbot();
                informacion.setId(cursor.getString(cursor.getColumnIndex("rowid")));
                informacion.setRespuesta(cursor.getString(cursor.getColumnIndex(COL_RESPUESTA)));
                informacion.setUrl(cursor.getString(cursor.getColumnIndex(COL_URL)));
                listInfo.add(informacion);
                cursor.moveToNext();
            }
        }
        if (cursor != null) {
            cursor.close();
        }
        return listInfo;
    }


    public ArrayList<conversacion> searchCONVERSACION(String s) {
        ArrayList<conversacion> listConser = new ArrayList<>();

        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.query(true,TABLE_NAME_CONVERSACION,
                new String[]{"rowid", COL_PREGUNTAS,COL_RESPUESTAS}, TABLE_NAME_CONVERSACION +
                        " MATCH ?", new String[]{s + "*"}, null, null, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {
                conversacion conversacion = new conversacion();
                conversacion.setId(cursor.getString(cursor.getColumnIndex("rowid")));
                conversacion.setPreguntas(cursor.getString(cursor.getColumnIndex(COL_PREGUNTAS)));
                conversacion.setRespuestas(cursor.getString(cursor.getColumnIndex(COL_RESPUESTAS)));
                listConser.add(conversacion);
                cursor.moveToNext();
            }
        }
        if (cursor != null) {
            cursor.close();
        }
        return listConser;
    }

}

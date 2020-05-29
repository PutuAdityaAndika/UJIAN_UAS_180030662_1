package com.aa183.PutuAdityaAndika;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class DataBaseHandler extends SQLiteOpenHelper {

    private final static int DATABASE_VERSION = 2;
    private final static String DATABASE_NAME ="db_BUKUKUADITYAANDIKA";
    private final static String TABLE_BERITA = "t_berita";
    private final static String KEY_ID_BERITA ="ID_Berita";
    private final static String KEY_JUDUL = "Judul";
    private final static String KEY_TGL = "Tanggal";
    private final static String KEY_GAMBAR = "Gambar";
    private final static String KEY_CAPTION = "Caption";
    private final static String KEY_PENULIS = "Penulis";
    private final static String KEY_ISI_BERITA = "Isi_Buku";
    private final static String KEY_LINK = "Link";
    private SimpleDateFormat sdFormat = new SimpleDateFormat("dd/MM/yy hh:mm", Locale.getDefault());
    private Context context;

    public DataBaseHandler(Context ctx){
        super (ctx, DATABASE_NAME, null, DATABASE_VERSION );
        this.context = ctx;

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_BERITA = " CREATE TABLE " + TABLE_BERITA
                + "(" + KEY_ID_BERITA + " INTERGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_JUDUL + " TEXT, " + KEY_TGL + " DATE, "
                + KEY_GAMBAR + " TEXT, " + KEY_CAPTION + " TEXT, "
                + KEY_PENULIS + " TEXT, " + KEY_ISI_BERITA + " TEXT, "
                + KEY_LINK + " TEXT);";


        db.execSQL(CREATE_TABLE_BERITA);
        inisialisasiBeritaAwal(db);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_BERITA;
        db.execSQL(DROP_TABLE);
        onCreate(db);


    }

    public void tambahBerita(Berita dataBerita){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        
        cv.put(KEY_JUDUL, dataBerita.getJudul());
        cv.put(KEY_TGL, sdFormat.format(dataBerita.getTanggal()));
        cv.put(KEY_GAMBAR, dataBerita.getGambar());
        cv.put(KEY_CAPTION, dataBerita.getCaption());
        cv.put(KEY_PENULIS, dataBerita.getPenulis());
        cv.put(KEY_ISI_BERITA, dataBerita.getIsiBerita());
        cv.put(KEY_LINK, dataBerita.getLink());
        db.insert(TABLE_BERITA, null, cv);
        db.close();

    }
    public void tambahBerita (Berita dataBerita, SQLiteDatabase db){
        ContentValues cv = new ContentValues();


        cv.put(KEY_JUDUL, dataBerita.getJudul());
        cv.put(KEY_TGL, sdFormat.format(dataBerita.getTanggal()));
        cv.put(KEY_GAMBAR, dataBerita.getGambar());
        cv.put(KEY_CAPTION, dataBerita.getCaption());
        cv.put(KEY_PENULIS, dataBerita.getPenulis());
        cv.put(KEY_ISI_BERITA, dataBerita.getIsiBerita());
        cv.put(KEY_LINK, dataBerita.getLink());
        db.insert(TABLE_BERITA, null, cv);

    }

    public void editBerita(Berita dataBerita) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(KEY_ID_BERITA, dataBerita.getIdBerita());
        cv.put(KEY_JUDUL, dataBerita.getJudul());
        cv.put(KEY_TGL, sdFormat.format(dataBerita.getTanggal()));
        cv.put(KEY_GAMBAR, dataBerita.getGambar());
        cv.put(KEY_CAPTION, dataBerita.getCaption());
        cv.put(KEY_PENULIS, dataBerita.getPenulis());
        cv.put(KEY_ISI_BERITA, dataBerita.getIsiBerita());
        cv.put(KEY_LINK, dataBerita.getLink());

        db.update(TABLE_BERITA, cv, KEY_ID_BERITA + "=?", new String[]{String.valueOf(dataBerita.getIdBerita())});
        db.close();


    }

    public void hapusData(int idBerita) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_BERITA, KEY_ID_BERITA+ "=?", new String[]{String.valueOf(idBerita)});
        db.close();
    }

    public ArrayList<Berita> getAllBerita() {
        ArrayList<Berita> dataBerita = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_BERITA;
        SQLiteDatabase db = getReadableDatabase();
        Cursor csr = db.rawQuery(query, null);
        if(csr. moveToFirst()){
            do {
                Date tempDate = new Date();
                try{
                    tempDate = sdFormat.parse(csr.getString(2));
                }catch(ParseException er) {
                    er.printStackTrace();
                }

                Berita tempBerita = new Berita(
                        csr.getInt(0),
                        csr.getString(1),
                        tempDate,
                        csr.getString(3),
                        csr.getString(4),
                        csr.getString(5),
                        csr.getString(6),
                        csr.getString(7)
                );

                dataBerita.add(tempBerita);
            }while (csr.moveToNext());
        }

        return dataBerita;
    }

    private  String storeImageFile(String imageName, int id){
        String location;
        Bitmap image = BitmapFactory.decodeResource(context.getResources(), id);
        location = InputActivity.saveImageToInternalStorage(image, context);
        return location;
    }

    private void inisialisasiBeritaAwal(SQLiteDatabase db){
        int idBerita = 0;
        Date tempDate = new Date();



    }

}

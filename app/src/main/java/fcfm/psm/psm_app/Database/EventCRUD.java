package fcfm.psm.psm_app.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import fcfm.psm.psm_app.Model.Event;

/**
 * Created by Gerardo Soriano on 06/11/2016.
 */

public class EventCRUD extends SQLHelper{

    public static final String ID              = "lg_event";
    public static final String TABLE_NAME      = "lg_event";
    public static final String NAME            = "name";
    public static final String IMAGE           = "imagePath";
    public static final String DESCRIPTION     = "description";
    public static final String COVER           = "coverPath";
    public static final String PRICE           = "price";
    public static final String DATE            = "date";
    public static final String CATEGORY        = "category";
    public static final String ADDRESS         = "address";
    public static final String RATING          = "rating";
    public static final String FOLLOWING          = "following";

    /*
    *   Aquí hacemos todas las siguientes operaciones con los eventos:
    *       Crear uno nuevo.
    *       Obtener uno o todos los eventos.
     */

    public EventCRUD(Context context) {
        super(context);
    }

    public void createEvent(Event event){
        Event comprobarEvento = readEvent(event.getId());

        if(comprobarEvento == null) {
            SQLiteDatabase db = getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(NAME, event.getName());
            values.put(DESCRIPTION, event.getDescription());
            values.put(IMAGE, event.getImgPath());
            values.put(COVER, event.getCoverPath());
            values.put(PRICE, event.getPrice());
            values.put(DATE, event.getDate().getTime());
            values.put(CATEGORY, "ART & CULTURE"); //TODO:CAMBIAR
            values.put(ADDRESS, event.getAddress());
            values.put(RATING, event.getRating());
            values.put(FOLLOWING, 0); //TODO:CAMBIAR
            db.insert(TABLE_NAME,null,values);
            db.close();
        }

    }

    public void follow(int id){
        Event event = null;
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FOLLOWING, 1);
        String where = ID + " = " + id;
        db.update(TABLE_NAME, values, where, null);

        db.close();
    }

    public void unfollow(int id){
        Event event = null;
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FOLLOWING, 0);
        String where = ID + " = " + id;
        db.update(TABLE_NAME, values, where, null);

        db.close();
    }

    public Event readEvent(int id){
        Event event = null;
        SQLiteDatabase db = getWritableDatabase();

        String where = ID + " = " + id;
        Cursor cursor = db.query(TABLE_NAME,null,where,null,null,null,null,null);

        if (cursor.moveToFirst()){
            String name         = cursor.getString(cursor.getColumnIndex(NAME));
            String description  = cursor.getString(cursor.getColumnIndex(DESCRIPTION));
            String imagePath    = cursor.getString(cursor.getColumnIndex(IMAGE));
            String coverPath    = cursor.getString(cursor.getColumnIndex(COVER));
            int price           = cursor.getInt(cursor.getColumnIndex(PRICE));
            long date           = cursor.getLong(cursor.getColumnIndex(DATE));
            String category     = cursor.getString(cursor.getColumnIndex(CATEGORY));
            String address      = cursor.getString(cursor.getColumnIndex(ADDRESS));
            float rating        = cursor.getInt(cursor.getColumnIndex(RATING));
            int following       = cursor.getInt(cursor.getColumnIndex(FOLLOWING));
            cursor.close();
            event = new Event(id, name, description, new Date(date), address, price, imagePath, coverPath, rating, category, following);
        }
        db.close();
        return event;
    }

    public List<Event> readEvents(){
        List<Event> event = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();

        Cursor cursor = db.query(TABLE_NAME,null,null,null,null,null,null,null);

        if (cursor.moveToFirst()){
            while (!cursor.isAfterLast()){
                int id              = cursor.getInt(cursor.getColumnIndex(ID));
                String name         = cursor.getString(cursor.getColumnIndex(NAME));
                String description  = cursor.getString(cursor.getColumnIndex(DESCRIPTION));
                String imagePath    = cursor.getString(cursor.getColumnIndex(IMAGE));
                String coverPath    = cursor.getString(cursor.getColumnIndex(COVER));
                int price           = cursor.getInt(cursor.getColumnIndex(PRICE));
                long date           = cursor.getLong(cursor.getColumnIndex(DATE));
                String category     = cursor.getString(cursor.getColumnIndex(CATEGORY));
                String address      = cursor.getString(cursor.getColumnIndex(ADDRESS));
                float rating        = cursor.getInt(cursor.getColumnIndex(RATING));
                int following       = cursor.getInt(cursor.getColumnIndex(FOLLOWING));

                event.add( new Event(id, name, description, new Date(date), address, price, imagePath, coverPath, rating, category, following));
                cursor.moveToNext();
            }
        }

        db.close();
        return event;
    }

    public List<Event> readEvents(String category){
        List<Event> event = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + CATEGORY + " = '" + category + "'",null);

        if (cursor.moveToFirst()){
            while (!cursor.isAfterLast()){
                int id              = cursor.getInt(cursor.getColumnIndex(ID));
                String name         = cursor.getString(cursor.getColumnIndex(NAME));
                String description  = cursor.getString(cursor.getColumnIndex(DESCRIPTION));
                String imagePath    = cursor.getString(cursor.getColumnIndex(IMAGE));
                String coverPath    = cursor.getString(cursor.getColumnIndex(COVER));
                int price           = cursor.getInt(cursor.getColumnIndex(PRICE));
                long date           = cursor.getLong(cursor.getColumnIndex(DATE));
                String cat          = cursor.getString(cursor.getColumnIndex(CATEGORY));
                String address      = cursor.getString(cursor.getColumnIndex(ADDRESS));
                float rating        = cursor.getInt(cursor.getColumnIndex(RATING));
                int following       = cursor.getInt(cursor.getColumnIndex(FOLLOWING));

                event.add( new Event(id, name, description, new Date(date), address, price, imagePath, coverPath, rating, category, following));
                cursor.moveToNext();
            }
        }

        db.close();
        return event;
    }

    public List<Event> readEvents(int following){
        List<Event> event = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + FOLLOWING + " = " + following,null);

        if (cursor.moveToFirst()){
            while (!cursor.isAfterLast()){
                int id              = cursor.getInt(cursor.getColumnIndex(ID));
                String name         = cursor.getString(cursor.getColumnIndex(NAME));
                String description  = cursor.getString(cursor.getColumnIndex(DESCRIPTION));
                String imagePath    = cursor.getString(cursor.getColumnIndex(IMAGE));
                String coverPath    = cursor.getString(cursor.getColumnIndex(COVER));
                int price           = cursor.getInt(cursor.getColumnIndex(PRICE));
                long date           = cursor.getLong(cursor.getColumnIndex(DATE));
                String category     = cursor.getString(cursor.getColumnIndex(CATEGORY));
                String address      = cursor.getString(cursor.getColumnIndex(ADDRESS));
                float rating        = cursor.getInt(cursor.getColumnIndex(RATING));
                int follow       = cursor.getInt(cursor.getColumnIndex(FOLLOWING));

                event.add( new Event(id, name, description, new Date(date), address, price, imagePath, coverPath, rating, category, following));
                cursor.moveToNext();
            }
        }

        db.close();
        return event;
    }

}

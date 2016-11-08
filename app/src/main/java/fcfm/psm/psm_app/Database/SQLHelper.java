package fcfm.psm.psm_app.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by Gerardo Soriano on 06/11/2016.
 */

public class SQLHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "lets_go";
    private static final int DATABASE_VERSION = 1;

    private static final String CREATE_TABLE_EVENT = "CREATE TABLE " + EventCRUD.TABLE_NAME + "(" +
            EventCRUD.ID              + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            EventCRUD.NAME            + " TEXT NOT NULL, " +
            EventCRUD.DESCRIPTION     + " TEXT NOT NULL, " +
            EventCRUD.IMAGE           + " TEXT NOT NULL, " +
            EventCRUD.COVER           + " TEXT NOT NULL, " +
            EventCRUD.PRICE           + " INTEGER NOT NULL, " +
            EventCRUD.DATE            + " NUMERIC NOT NULL, "    +
            EventCRUD.ADDRESS         + " TEXT NOT NULL, "      +
            EventCRUD.CATEGORY        + " TEXT NOT NULL "       +
            EventCRUD.RATING          + " NUMERIC NOT NULL, "   +
            EventCRUD.FOLLOWING       + " INTEGER NOT NULL " +
            ");";

    public SQLHelper(Context context) {
        super(context, SQLHelper.DATABASE_NAME, null, SQLHelper.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_EVENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS" + EventCRUD.TABLE_NAME);
        onCreate(db);
    }
}

package com.example.dissertation12;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper
{
    private static final String DBNAME = "Login.db";

    //Table to store user data
    private static final String TABLE_USER = "USER_DATA";
    private static final String USER_COL_1 = "USER_ID";
    private static final String USER_COL_2 = "USER_USERNAME";
    private static final String USER_COL_3 = "USER_PASSWORD";

    //Table to store region data
    private static final String TABLE_REGION = "REGION_DATA";
    private static final String REGION_COL_1 = "REGION_ID";
    private static final String REGION_COL_2 = "REGION_NAME";

    //Table to store puzzle data
    private static final String TABLE_PUZZLES = "PUZZLE_DATA";
    private static final String PUZZLE_COL_1 = "PUZZLE_ID";
    private static final String PUZZLE_COL_2 = "PUZZLE_ANSWER";
    private static final String PUZZLE_COL_3 = "REGION_ID";

    //Table that joins user and puzzle together to check if puzzle has been solved
    private static final String TABLE_SOLVED = "SOLVED_DATA";
    private static final String SOLVED_COL_1 = "SOLVED_ID";
    private static final String SOLVED_COL_2 = "USER_ID";
    private static final String SOLVED_COL_3 = "PUZZLE_ID";
    private static final String SOLVED_COL_4 = "IS_SOLVED";


    public DBHelper(Context context)
    {
        super(context, "Login.db", null, 21);
    }

    @Override
    public void onCreate(SQLiteDatabase MyDB)
    {
        MyDB.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_USER + "(USER_ID INTEGER PRIMARY KEY AUTOINCREMENT, USER_USERNAME TEXT, USER_PASSWORD TEXT)");
        MyDB.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_REGION + "(REGION_ID INTEGER PRIMARY KEY AUTOINCREMENT, REGION_NAME TEXT)");
        MyDB.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_PUZZLES + "(PUZZLE_ID INTEGER PRIMARY KEY AUTOINCREMENT, PUZZLE_ANSWER TEXT, REGION_ID INTEGER, FOREIGN KEY(REGION_ID) REFERENCES TABLE_REGION(REGION_ID))");
        MyDB.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_SOLVED + "(SOLVED_ID INTEGER PRIMARY KEY AUTOINCREMENT, USER_ID INTEGER, PUZZLE_ID INTEGER, IS_SOLVED INTEGER, FOREIGN KEY(USER_ID) REFERENCES TABLE_USER(USER_ID), FOREIGN KEY(PUZZLE_ID) REFERENCES TABLE_PUZZLE(PUZZLE_ID))");

        MyDB.execSQL("INSERT INTO " + TABLE_REGION + "(REGION_NAME) VALUES ('Ballycastle')");
        MyDB.execSQL("INSERT INTO " + TABLE_REGION + "(REGION_NAME) VALUES ('Belfast')");

        MyDB.execSQL("INSERT INTO " + TABLE_PUZZLES + "(PUZZLE_ANSWER, REGION_ID) VALUES ('Golden Chip', 1)");
        MyDB.execSQL("INSERT INTO " + TABLE_PUZZLES + "(PUZZLE_ANSWER, REGION_ID) VALUES ('Red Dragon', 1)");
        MyDB.execSQL("INSERT INTO " + TABLE_PUZZLES + "(PUZZLE_ANSWER, REGION_ID) VALUES ('Dessert Bar', 1)");
        MyDB.execSQL("INSERT INTO " + TABLE_PUZZLES + "(PUZZLE_ANSWER, REGION_ID) VALUES ('Bake Well', 1)");
        MyDB.execSQL("INSERT INTO " + TABLE_PUZZLES + "(PUZZLE_ANSWER, REGION_ID) VALUES ('Indigo', 1)");
        MyDB.execSQL("INSERT INTO " + TABLE_PUZZLES + "(PUZZLE_ANSWER, REGION_ID) VALUES ('Salt House', 1)");
        MyDB.execSQL("INSERT INTO " + TABLE_PUZZLES + "(PUZZLE_ANSWER, REGION_ID) VALUES ('Promenade', 1)");
        MyDB.execSQL("INSERT INTO " + TABLE_PUZZLES + "(PUZZLE_ANSWER, REGION_ID) VALUES ('Diamond Bar', 1)");
        MyDB.execSQL("INSERT INTO " + TABLE_PUZZLES + "(PUZZLE_ANSWER, REGION_ID) VALUES ('Homemade Beautiful', 1)");
        MyDB.execSQL("INSERT INTO " + TABLE_PUZZLES + "(PUZZLE_ANSWER, REGION_ID) VALUES ('Our Dollys', 1)");



        MyDB.execSQL("INSERT INTO " + TABLE_PUZZLES + "(PUZZLE_ANSWER, REGION_ID) VALUES ('Dirty Onion', 2)");
        MyDB.execSQL("INSERT INTO " + TABLE_PUZZLES + "(PUZZLE_ANSWER, REGION_ID) VALUES ('The Points', 2)");
        MyDB.execSQL("INSERT INTO " + TABLE_PUZZLES + "(PUZZLE_ANSWER, REGION_ID) VALUES ('The Onyx', 2)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int i, int i1)
    {
        //Drops tables when new version of database is created
        MyDB.execSQL(" DROP TABLE IF EXISTS " + TABLE_PUZZLES);
        MyDB.execSQL(" DROP TABLE IF EXISTS " + TABLE_REGION);
        //MyDB.execSQL(" DROP TABLE IF EXISTS " + TABLE_SOLVED);
        onCreate(MyDB);
    }

    //Inserts user information into the user table.
    public Boolean insertData(String username, String password)
    {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USER_COL_2, username);
        contentValues.put(USER_COL_3, password);

        long result = MyDB.insert(TABLE_USER, null, contentValues);

        if (result == -1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    public Boolean checkusername(String username)
    {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        String[] columns = { USER_COL_1 };
        String selction = USER_COL_2 + "=?";
        String[] selectionargs = {username};
        Cursor cursor = MyDB.query(TABLE_USER, columns, selction, selectionargs, null, null, null);
        int count = cursor.getCount();
        MyDB.close();
        cursor.close();

        if (count > 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public Boolean checkusernamepassword(String username, String password)
    {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        String[] columns = { USER_COL_1 };
        String selction = USER_COL_2 + "=?" + " and " + USER_COL_3 + "=?";
        String[] selectionargs = {username , password};
        Cursor cursor = MyDB.query(TABLE_USER, columns, selction, selectionargs, null, null, null);
        int count = cursor.getCount();
        MyDB.close();
        cursor.close();

        if (count > 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public int getUserID(String username)
    {
        int userID = 0;

        SQLiteDatabase MyDB = this.getReadableDatabase();

        //Query to count from table where valence level = Pleasant
        String idQuery = "SELECT " + USER_COL_1 +  " FROM " + TABLE_USER + " WHERE " + USER_COL_2 + " ='" + username + "'";
        //Cursor reads database with countQuery
        Cursor cursor = MyDB.rawQuery(idQuery, null);

        //If there are records in the count
        if (cursor.getCount() > 0)
        {
            cursor.moveToFirst();
            userID = cursor.getInt(0);
        }
        //Closes cursor and method returns count amount
        MyDB.close();
        cursor.close();
        return userID;
    }

    public int getUserBallycastleScore(int userID)
    {
        int userScore = 0;

        //Query to count from table where emotion = its string
        String countQuery = "SELECT COUNT (*) FROM " + TABLE_SOLVED +  " INNER JOIN " + TABLE_PUZZLES + " ON " + TABLE_PUZZLES + "." + PUZZLE_COL_1 + " = " + TABLE_SOLVED + "." + SOLVED_COL_3 + " WHERE " + SOLVED_COL_4 + " = 1" + " AND " + SOLVED_COL_2 + " ='" + userID + "'" + " AND " + PUZZLE_COL_3 + " = 1";
//        private final String MY_QUERY = "SELECT * FROM table_a a INNER JOIN table_b b ON a.id=b.other_id WHERE b.property_id=?";

        //Cursor reads database with countQuery
        Cursor cursor = getReadableDatabase().rawQuery(countQuery, null);

        //If there are records in the count
        if (cursor.getCount() > -1)
        {
            //Moves the cursor to the first row and use valenceTotal to hold the amount of rows
            cursor.moveToFirst();
            userScore = cursor.getInt(0);
        }
        //Closes cursor and method returns count amount
        cursor.close();

        return userScore;
    }

    public int getUserBelfastScore(int userID)
    {
        int userScore = 0;

        //Query to count from table where emotion = its string
        String countQuery = "SELECT COUNT (*) FROM " + TABLE_SOLVED +  " INNER JOIN " + TABLE_PUZZLES + " ON " + TABLE_PUZZLES + "." + PUZZLE_COL_1 + " = " + TABLE_SOLVED + "." + SOLVED_COL_3 + " WHERE " + SOLVED_COL_4 + " = 1" + " AND " + SOLVED_COL_2 + " ='" + userID + "'" + " AND " + PUZZLE_COL_3 + " = 2";
//        private final String MY_QUERY = "SELECT * FROM table_a a INNER JOIN table_b b ON a.id=b.other_id WHERE b.property_id=?";

        //Cursor reads database with countQuery
        Cursor cursor = getReadableDatabase().rawQuery(countQuery, null);

        //If there are records in the count
        if (cursor.getCount() > -1)
        {
            //Moves the cursor to the first row and use valenceTotal to hold the amount of rows
            cursor.moveToFirst();
            userScore = cursor.getInt(0);
        }
        //Closes cursor and method returns count amount
        cursor.close();

        return userScore;
    }

    public Boolean checkPuzzleSolved(int userID, int puzzleID)
    {
        SQLiteDatabase MyDB = this.getReadableDatabase();

        String idQuery = "SELECT " + SOLVED_COL_1 +  " FROM " + TABLE_SOLVED  + " WHERE " + SOLVED_COL_2 + " ='" + userID + "'" + " AND " + SOLVED_COL_3 + " ='" + puzzleID + "'" + " AND " + SOLVED_COL_4 + " = 1";
        Cursor cursor = MyDB.rawQuery(idQuery, null);

        int count = cursor.getCount();
        MyDB.close();
        cursor.close();

        if (count > 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public Boolean checkPuzzleAnswer(int puzzleID, String userAnswer)
    {
        SQLiteDatabase MyDB = this.getReadableDatabase();

        String answerQuery = "SELECT " + PUZZLE_COL_1 +  " FROM " + TABLE_PUZZLES  + " WHERE "  + PUZZLE_COL_1 + " ='" + puzzleID + "'" + " AND " + PUZZLE_COL_2 + " LIKE '%" + userAnswer + "%'";
        Cursor cursor = MyDB.rawQuery(answerQuery, null);

        int count = cursor.getCount();
        MyDB.close();
        cursor.close();

        if (count > 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public Boolean insertSolvedAnswer(int userID, int puzzleID)
    {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(SOLVED_COL_2, userID);
        contentValues.put(SOLVED_COL_3, puzzleID);
        contentValues.put(SOLVED_COL_4, 1);

        long result = MyDB.insert(TABLE_SOLVED, null, contentValues);

        if (result == -1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }
}
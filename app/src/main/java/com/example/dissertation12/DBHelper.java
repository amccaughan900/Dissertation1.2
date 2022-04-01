package com.example.dissertation12;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

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
    private static final String PUZZLE_COL_2 = "PUZZLE_CLUE";
    private static final String PUZZLE_COL_3 = "PUZZLE_HINT";
    private static final String PUZZLE_COL_4 = "PUZZLE_ANSWER";
    private static final String PUZZLE_COL_5 = "PUZZLE_SECOND_ANSWER";
    private static final String PUZZLE_COL_6 = "REGION_ID";

    //Table that joins user and puzzle together to check if puzzle has been solved
    private static final String TABLE_SOLVED = "SOLVED_DATA";
    private static final String SOLVED_COL_1 = "SOLVED_ID";
    private static final String SOLVED_COL_2 = "USER_ID";
    private static final String SOLVED_COL_3 = "PUZZLE_ID";
    private static final String SOLVED_COL_4 = "IS_SOLVED";

    private static final String TABLE_HINT = "HINT_DATA";
    private static final String HINT_COL_1 = "HINT_ID";
    private static final String HINT_COL_2 = "USER_ID";
    private static final String HINT_COL_3 = "HINT_TOTAL";

    private static final String TABLE_HINT_USED = "HINT_USED_DATA";
    private static final String HINT_USED_COL_1 = "HINT_USED_ID";
    private static final String HINT_USED_COL_2 = "USER_ID";
    private static final String HINT_USED_COL_3 = "PUZZLE_ID";
    private static final String HINT_USED_COL_4 = "HINT_IS_USED";


    public DBHelper(Context context)
    {
        super(context, "Login.db", null, 36);
    }

    @Override
    public void onCreate(SQLiteDatabase MyDB)
    {
        MyDB.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_USER + "(USER_ID INTEGER PRIMARY KEY AUTOINCREMENT, USER_USERNAME TEXT, USER_PASSWORD TEXT)");
        MyDB.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_REGION + "(REGION_ID INTEGER PRIMARY KEY AUTOINCREMENT, REGION_NAME TEXT)");
        MyDB.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_PUZZLES + "(PUZZLE_ID INTEGER PRIMARY KEY AUTOINCREMENT, PUZZLE_CLUE TEXT, PUZZLE_HINT TEXT,PUZZLE_ANSWER TEXT, PUZZLE_SECOND_ANSWER TEXT, REGION_ID INTEGER, FOREIGN KEY(REGION_ID) REFERENCES TABLE_REGION(REGION_ID))");
        MyDB.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_SOLVED + "(SOLVED_ID INTEGER PRIMARY KEY AUTOINCREMENT, USER_ID INTEGER, PUZZLE_ID INTEGER, IS_SOLVED INTEGER, FOREIGN KEY(USER_ID) REFERENCES TABLE_USER(USER_ID), FOREIGN KEY(PUZZLE_ID) REFERENCES TABLE_PUZZLE(PUZZLE_ID))");
        MyDB.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_HINT + "(HINT_ID INTEGER PRIMARY KEY AUTOINCREMENT, USER_ID INTEGER, HINT_TOTAL TEXT, FOREIGN KEY(USER_ID) REFERENCES TABLE_USER(USER_ID))");
        MyDB.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_HINT_USED + "(HINT_USED_ID INTEGER PRIMARY KEY AUTOINCREMENT, USER_ID INTEGER, PUZZLE_ID INTEGER, HINT_IS_USED INTEGER, FOREIGN KEY(USER_ID) REFERENCES TABLE_USER(USER_ID),FOREIGN KEY(PUZZLE_ID) REFERENCES TABLE_PUZZLE(PUZZLE_ID))");

        MyDB.execSQL("INSERT INTO " + TABLE_REGION + "(REGION_NAME) VALUES ('Ballycastle')");
        MyDB.execSQL("INSERT INTO " + TABLE_REGION + "(REGION_NAME) VALUES ('Belfast')");

        MyDB.execSQL("INSERT INTO " + TABLE_PUZZLES + "(PUZZLE_CLUE, PUZZLE_HINT, PUZZLE_ANSWER, PUZZLE_SECOND_ANSWER, REGION_ID) VALUES ('Puzzle 1: A shining form of potato?', 'Hint: Two word answer. 1st - The colour of winning 1st at the olympics, 2nd - also known as fries','Golden Chip','', 1)");
        MyDB.execSQL("INSERT INTO " + TABLE_PUZZLES + "(PUZZLE_CLUE, PUZZLE_HINT, PUZZLE_ANSWER, PUZZLE_SECOND_ANSWER, REGION_ID) VALUES ('Puzzle 2: A colour and a mythic being, simple right?', 'Hint: Two word answer. 1st - colour that usually represents danger or death, 2nd - scaly, fire breathing creature', 'Red Dragon', '', 1)");
        MyDB.execSQL("INSERT INTO " + TABLE_PUZZLES + "(PUZZLE_CLUE, PUZZLE_HINT, PUZZLE_ANSWER, PUZZLE_SECOND_ANSWER, REGION_ID) VALUES ('Puzzle 3: This bar will fill the spot after some dinner', 'Hint: Two word answer. 1st - The meal that comes after dinner, 2nd - 3 letter word, a place that usually serves drinks.', 'Dessert Bar', '', 1)");
        MyDB.execSQL("INSERT INTO " + TABLE_PUZZLES + "(PUZZLE_CLUE, PUZZLE_HINT, PUZZLE_ANSWER, PUZZLE_SECOND_ANSWER, REGION_ID) VALUES ('Puzzle 4: Bakers are expected to do this.', 'Hint: B _ K _ W _ _ _ ', 'Bakewell', '', 1)");
        MyDB.execSQL("INSERT INTO " + TABLE_PUZZLES + "(PUZZLE_CLUE, PUZZLE_HINT, PUZZLE_ANSWER, PUZZLE_SECOND_ANSWER, REGION_ID) VALUES ('Puzzle 5: A colour of the rainbow', 'Hint: Answer is the missing colour of Red, Orange, Yellow, Green, Blue, _, Violet', 'Indigo', 'Indigo Jewellers', 1)");
        MyDB.execSQL("INSERT INTO " + TABLE_PUZZLES + "(PUZZLE_CLUE, PUZZLE_HINT, PUZZLE_ANSWER, PUZZLE_SECOND_ANSWER, REGION_ID) VALUES ('Puzzle 6: Two word answer: 1st - _ & pepper, 2nd - a building used to live in.', 'Hint: 1st - _ and vinegar, 2nd - humans live in this.', 'Salt House', 'Salt House Hotel',1)");
        MyDB.execSQL("INSERT INTO " + TABLE_PUZZLES + "(PUZZLE_CLUE, PUZZLE_HINT, PUZZLE_ANSWER, PUZZLE_SECOND_ANSWER, REGION_ID) VALUES ('Puzzle 7: A walkway alongside the seafront','Hint: Located near puzzle 4.', 'Promenade','Promenade Cafe', 1)");
        MyDB.execSQL("INSERT INTO " + TABLE_PUZZLES + "(PUZZLE_CLUE, PUZZLE_HINT, PUZZLE_ANSWER, PUZZLE_SECOND_ANSWER, REGION_ID) VALUES ('Puzzle 8: The name of this bar is based on a precious gem usually given in an engagement.', 'Hint: pressure underneath the earth makes these', 'Diamond', 'Diamond Bar', 1)");
        MyDB.execSQL("INSERT INTO " + TABLE_PUZZLES + "(PUZZLE_CLUE, PUZZLE_HINT, PUZZLE_ANSWER, PUZZLE_SECOND_ANSWER, REGION_ID) VALUES ('Puzzle 9: DIY that is in a gorgeous state', 'Location: located between the park and the town centre.', 'Homemade Beautiful', 'Home Made Beautiful', 1)");
        MyDB.execSQL("INSERT INTO " + TABLE_PUZZLES + "(PUZZLE_CLUE, PUZZLE_HINT, PUZZLE_ANSWER, PUZZLE_SECOND_ANSWER, REGION_ID) VALUES ('Puzzle 10: Owning something together and a famous female country-pop singer', 'Location: traffic lights can be seen from this place', 'Our Dollys', 'Our Dollys Cafe', 1)");
        MyDB.execSQL("INSERT INTO " + TABLE_PUZZLES + "(PUZZLE_CLUE, PUZZLE_HINT, PUZZLE_ANSWER, PUZZLE_SECOND_ANSWER, REGION_ID) VALUES ('Puzzle 11: ususal award for being runners-up and an extremely steep incline made of rock', 'Location: traffic lights can be seen from this place', 'Silver Cliffs', 'Silvercliffs', 1)");

        MyDB.execSQL("INSERT INTO " + TABLE_PUZZLES + "(PUZZLE_CLUE, PUZZLE_HINT, PUZZLE_ANSWER, PUZZLE_SECOND_ANSWER, REGION_ID) VALUES ('Puzzle 1: This peeling vegetable is not in a good condition.', 'Hint: something that could do with a clean and a vegetable that can make you cry', 'Dirty Onion', 'Dirty Onion and Yardbird', 2)");
        MyDB.execSQL("INSERT INTO " + TABLE_PUZZLES + "(PUZZLE_CLUE, PUZZLE_HINT, PUZZLE_ANSWER, PUZZLE_SECOND_ANSWER, REGION_ID) VALUES ('Puzzle 2: This pub has a name that suggests it is holding some sort of score.', 'Hint: between botanic avenue and the city centre', 'Points', 'Points Bar', 2)");
        MyDB.execSQL("INSERT INTO " + TABLE_PUZZLES + "(PUZZLE_CLUE, PUZZLE_HINT, PUZZLE_ANSWER, PUZZLE_SECOND_ANSWER, REGION_ID) VALUES ('Puzzle 3: An opaque, all-black gemstone.', 'Hint: o _ y _ ', 'Onyx', 'Onyx House', 2)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int i, int i1)
    {
        //Drops tables when new version of database is created
        MyDB.execSQL(" DROP TABLE IF EXISTS " + TABLE_PUZZLES);
        MyDB.execSQL(" DROP TABLE IF EXISTS " + TABLE_REGION);
        //MyDB.execSQL(" DROP TABLE IF EXISTS " + TABLE_SOLVED);
//        MyDB.execSQL(" DROP TABLE IF EXISTS " + TABLE_HINT);
//        MyDB.execSQL(" DROP TABLE IF EXISTS " + TABLE_HINT_USED);
        //MyDB.execSQL(" DROP TABLE IF EXISTS " + TABLE_USER);
        onCreate(MyDB);
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

    public Boolean insertInitialHintCoin(int userID)
    {
        int initialHintCoin = 10;

        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(HINT_COL_2, userID);
        contentValues.put(HINT_COL_3, initialHintCoin);

        long result = MyDB.insert(TABLE_HINT, null, contentValues);

        if (result == -1)
        {
            return false;
        }
        else
        {
            return true;
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

    //Add region id to sql statement as well
    public int getPuzzleID(String puzzleClue)
    {
        int puzzleID = 0;

        SQLiteDatabase MyDB = this.getReadableDatabase();

        //Query to count from table where valence level = Pleasant
        String idQuery = "SELECT " + PUZZLE_COL_1 +  " FROM " + TABLE_PUZZLES + " WHERE " + PUZZLE_COL_2 + " ='" + puzzleClue + "'";
        //Cursor reads database with countQuery
        Cursor cursor = MyDB.rawQuery(idQuery, null);

        //If there are records in the count
        if (cursor.getCount() > 0)
        {
            cursor.moveToFirst();
            puzzleID = cursor.getInt(0);
        }
        //Closes cursor and method returns count amount
        MyDB.close();
        cursor.close();

        return puzzleID;
    }



    public int getUserScore(int userID, int regionID)
    {
        int userScore = 0;

        //Query to count from table where emotion = its string
        String countQuery = "SELECT COUNT (*) FROM " + TABLE_SOLVED +  " INNER JOIN " + TABLE_PUZZLES + " ON " + TABLE_PUZZLES + "." + PUZZLE_COL_1 + " = " + TABLE_SOLVED + "." + SOLVED_COL_3 + " WHERE " + SOLVED_COL_4 + " = 1" + " AND " + SOLVED_COL_2 + " ='" + userID + "'" + " AND " + PUZZLE_COL_6 + " ='" + regionID + "'";
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

    public int getTotalScore(int regionID)
    {
        int totalScore = 0;

        //Query to count from table where emotion = its string
        String countQuery = "SELECT COUNT (*) FROM " + TABLE_PUZZLES + " WHERE " + PUZZLE_COL_6 + " ='" + regionID + "'";
//        private final String MY_QUERY = "SELECT * FROM table_a a INNER JOIN table_b b ON a.id=b.other_id WHERE b.property_id=?";

        //Cursor reads database with countQuery
        Cursor cursor = getReadableDatabase().rawQuery(countQuery, null);

        //If there are records in the count
        if (cursor.getCount() > -1)
        {
            //Moves the cursor to the first row and use valenceTotal to hold the amount of rows
            cursor.moveToFirst();
            totalScore = cursor.getInt(0);
        }
        //Closes cursor and method returns count amount
        cursor.close();

        return totalScore;
    }

    public int getUserHintAmount(int userID)
    {
        int hintAmount = 0;

        SQLiteDatabase MyDB = this.getReadableDatabase();

        String hintQuery = "SELECT " + HINT_COL_3 +  " FROM " + TABLE_HINT + " WHERE " + HINT_COL_2 + " ='" + userID + "'";

        Cursor cursor = MyDB.rawQuery(hintQuery, null);

        //If there are records in the count
        if (cursor.getCount() > 0)
        {
            cursor.moveToFirst();
            hintAmount = cursor.getInt(0);
        }

        //Closes cursor and method returns count amount
        MyDB.close();
        cursor.close();

        return hintAmount;
    }

    //Method to update user profile

//    public boolean updateUserAccount(int userID, String username)
//    {
//        //Allows for writing into the database
//        SQLiteDatabase MyDB = this.getWritableDatabase();
//        //Stores the column values
//        ContentValues cv = new ContentValues();
//
//        cv.put(USER_COL_2, username);
//
//        long update = MyDB.update(TABLE_USER, cv, "USER_ID" + " = ?", new String[]{String.valueOf(userID)});
//
//        if (update == -1) {
//            return false;
//        } else {
//            return true;
//        }
//    }

    public boolean updateHintAmount(int userID, int hintAmount)
    {
        //Allows for writing into the database
        SQLiteDatabase MyDB = this.getWritableDatabase();
        //Stores the column values
        ContentValues cv = new ContentValues();

        cv.put(HINT_COL_2, userID);
        cv.put(HINT_COL_3, hintAmount);

        long update = MyDB.update(TABLE_HINT, cv, "USER_ID" + " = ?", new String[] {String.valueOf(userID)});

        if (update == -1)
        {
            return false;
        }

        else
        {
            return true;
        }
    }

    public boolean checkHintUnlocked(int userID, int puzzleID)
    {
        SQLiteDatabase MyDB = this.getReadableDatabase();

        String idQuery = " SELECT " + HINT_USED_COL_1 + " FROM " + TABLE_HINT_USED + " WHERE " + HINT_USED_COL_2 + " ='" + userID + "'" + " AND " + HINT_USED_COL_3 + " ='" + puzzleID + "'" + " AND " + HINT_USED_COL_4 + "= 1";
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

    public boolean insertPuzzleHintUnlocked(int userID, int puzzleID)
    {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(HINT_USED_COL_2, userID);
        contentValues.put(HINT_USED_COL_3, puzzleID);
        contentValues.put(HINT_USED_COL_4, 1);

        long result = MyDB.insert(TABLE_HINT_USED, null, contentValues);

        if (result == -1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    public String getPuzzleHint(int puzzleID)
    {
        String puzzleHint = "";

        SQLiteDatabase MyDB = this.getReadableDatabase();

        String hintQuery = "SELECT " + PUZZLE_COL_3 +  " FROM " + TABLE_PUZZLES + " WHERE " + PUZZLE_COL_1 + " ='" + puzzleID + "'";

        Cursor cursor = MyDB.rawQuery(hintQuery, null);

        //If there are records in the count
        if (cursor.getCount() > 0)
        {
            cursor.moveToFirst();
            puzzleHint = cursor.getString(0);
        }

        //Closes cursor and method returns count amount
        MyDB.close();
        cursor.close();

        return puzzleHint;
    }

    public Boolean checkPuzzleSolved(int userID, String puzzleClue, int regionID)
    {
        SQLiteDatabase MyDB = this.getReadableDatabase();

        String idQuery = "SELECT " + SOLVED_COL_1 +  " FROM " + TABLE_SOLVED  + " INNER JOIN " + TABLE_PUZZLES + " ON " + TABLE_PUZZLES + "." + PUZZLE_COL_1 + " = " + TABLE_SOLVED + "." + SOLVED_COL_3 + " WHERE " + SOLVED_COL_2 + " ='" + userID + "'" + " AND " + PUZZLE_COL_2 + " ='" + puzzleClue + "'" + " AND " + SOLVED_COL_4 + "= 1" + " AND " + PUZZLE_COL_6 + " ='" + regionID + "'";
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

    public Boolean checkUserVSPuzzleAnswer(String puzzleClue, String userAnswer)
    {
        SQLiteDatabase MyDB = this.getReadableDatabase();

        String answerQuery = "SELECT " + PUZZLE_COL_1 +  " FROM " + TABLE_PUZZLES  + " WHERE "  + PUZZLE_COL_2  + " ='" + puzzleClue + "'" + " AND " + "lower(" + PUZZLE_COL_4 + ")" + "='" + userAnswer + "'";
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

    public Boolean checkUserVSPuzzleSecondAnswer(String puzzleClue, String userAnswer)
    {
        SQLiteDatabase MyDB = this.getReadableDatabase();

        String answerQuery = "SELECT " + PUZZLE_COL_1 +  " FROM " + TABLE_PUZZLES  + " WHERE "  + PUZZLE_COL_2  + " ='" + puzzleClue + "'" + " AND " + "lower(" + PUZZLE_COL_5 + ")" + "='" + userAnswer + "'";
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

    public List<PuzzleModel> selectAllSolved(int userID, int region)
    {
        List<PuzzleModel> returnPuzzleSolved = new ArrayList<>();

        SQLiteDatabase MyDB = this.getReadableDatabase();

        String queryDB = "SELECT * FROM " + TABLE_PUZZLES  + " INNER JOIN " + TABLE_SOLVED + " ON " + TABLE_PUZZLES + "." + PUZZLE_COL_1 + " = " + TABLE_SOLVED + "." + SOLVED_COL_3 + " WHERE " + SOLVED_COL_2 + " ='" + userID + "'" + " AND " + PUZZLE_COL_6 + " ='" + region + "'" + " AND " + SOLVED_COL_4 + " = 1" + " ORDER BY " + PUZZLE_COL_1;

        Cursor cursor = MyDB.rawQuery(queryDB, null);

        //Populates the list with each record in database.
        if (cursor.moveToFirst())
        {
            //Loops through the results and puts them into new objects to put into the list while cursor can move to next set.
            do
            {
                String puzzleName = cursor.getString(1);
                String puzzleHint = cursor.getString(2);
                String puzzleAnswer = cursor.getString(3);

                //EmotionModel constructor is called here to hold each value and pass into newEvent
                PuzzleModel newEvent = new PuzzleModel(puzzleName, puzzleHint, puzzleAnswer);
                //Adds newEvent to returnList array
                returnPuzzleSolved.add(newEvent);
            }

            while(cursor.moveToNext());
        }

        else
        {
            //Error: do not add to the list
        }

        //Closes both database and cursor when finished.
        cursor.close();
        MyDB.close();

        //return returnList from method
        return returnPuzzleSolved;
    }
}
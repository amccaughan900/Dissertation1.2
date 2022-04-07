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

    private static final String TABLE_USER = "USER_DATA";
    private static final String USER_COL_1 = "USER_ID";
    private static final String USER_COL_2 = "USER_USERNAME";
    private static final String USER_COL_3 = "USER_PASSWORD";
    private static final String USER_COL_4 = "USER_SECRET_ANSWER";

    private static final String TABLE_REGION = "REGION_DATA";
    private static final String REGION_COL_1 = "REGION_ID";
    private static final String REGION_COL_2 = "REGION_NAME";

    private static final String TABLE_PUZZLES = "PUZZLE_DATA";
    private static final String PUZZLE_COL_1 = "PUZZLE_ID";
    private static final String PUZZLE_COL_2 = "PUZZLE_CLUE";
    private static final String PUZZLE_COL_3 = "PUZZLE_HINT";
    private static final String PUZZLE_COL_4 = "PUZZLE_ANSWER";
    private static final String PUZZLE_COL_5 = "PUZZLE_SECOND_ANSWER";
    private static final String PUZZLE_COL_6 = "REGION_ID";

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
        super(context, "Login.db", null, 43);
    }

    @Override
    public void onCreate(SQLiteDatabase MyDB)
    {
        MyDB.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_USER + "(USER_ID INTEGER PRIMARY KEY AUTOINCREMENT, USER_USERNAME TEXT, USER_PASSWORD TEXT, USER_SECRET_ANSWER TEXT)");
        MyDB.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_REGION + "(REGION_ID INTEGER PRIMARY KEY AUTOINCREMENT, REGION_NAME TEXT)");
        MyDB.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_PUZZLES + "(PUZZLE_ID INTEGER PRIMARY KEY AUTOINCREMENT, PUZZLE_CLUE TEXT, PUZZLE_HINT TEXT,PUZZLE_ANSWER TEXT, PUZZLE_SECOND_ANSWER TEXT, REGION_ID INTEGER, FOREIGN KEY(REGION_ID) REFERENCES TABLE_REGION(REGION_ID))");
        MyDB.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_SOLVED + "(SOLVED_ID INTEGER PRIMARY KEY AUTOINCREMENT, USER_ID INTEGER, PUZZLE_ID INTEGER, IS_SOLVED INTEGER, FOREIGN KEY(USER_ID) REFERENCES TABLE_USER(USER_ID), FOREIGN KEY(PUZZLE_ID) REFERENCES TABLE_PUZZLE(PUZZLE_ID))");
        MyDB.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_HINT + "(HINT_ID INTEGER PRIMARY KEY AUTOINCREMENT, USER_ID INTEGER, HINT_TOTAL TEXT, FOREIGN KEY(USER_ID) REFERENCES TABLE_USER(USER_ID))");
        MyDB.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_HINT_USED + "(HINT_USED_ID INTEGER PRIMARY KEY AUTOINCREMENT, USER_ID INTEGER, PUZZLE_ID INTEGER, HINT_IS_USED INTEGER, FOREIGN KEY(USER_ID) REFERENCES TABLE_USER(USER_ID),FOREIGN KEY(PUZZLE_ID) REFERENCES TABLE_PUZZLE(PUZZLE_ID))");

        MyDB.execSQL("INSERT INTO " + TABLE_REGION + "(REGION_NAME) VALUES ('Ballycastle')");
        MyDB.execSQL("INSERT INTO " + TABLE_REGION + "(REGION_NAME) VALUES ('Belfast')");

        MyDB.execSQL("INSERT INTO " + TABLE_PUZZLES + "(PUZZLE_CLUE, PUZZLE_HINT, PUZZLE_ANSWER, PUZZLE_SECOND_ANSWER, REGION_ID) VALUES ('Puzzle 1: A shining form of potato?', 'Hint: Two word answer. 1st - The colour of winning 1st at the olympics, 2nd - also known as fries','Golden Chip','Golden Chip Fish and chips', 1)");
        MyDB.execSQL("INSERT INTO " + TABLE_PUZZLES + "(PUZZLE_CLUE, PUZZLE_HINT, PUZZLE_ANSWER, PUZZLE_SECOND_ANSWER, REGION_ID) VALUES ('Puzzle 2: A colour and a mythic being, simple right?', 'Hint: Two word answer. 1st - colour that usually represents danger or death, 2nd - scaly, fire breathing creature', 'Red Dragon', '', 1)");
        MyDB.execSQL("INSERT INTO " + TABLE_PUZZLES + "(PUZZLE_CLUE, PUZZLE_HINT, PUZZLE_ANSWER, PUZZLE_SECOND_ANSWER, REGION_ID) VALUES ('Puzzle 3: This bar will fill the spot after some dinner', 'Hint: Two word answer. 1st - The meal that comes after dinner, 2nd - 3 letter word, a place that usually serves drinks.', 'Dessert Bar', 'Dessert Bar Restaurant', 1)");
        MyDB.execSQL("INSERT INTO " + TABLE_PUZZLES + "(PUZZLE_CLUE, PUZZLE_HINT, PUZZLE_ANSWER, PUZZLE_SECOND_ANSWER, REGION_ID) VALUES ('Puzzle 4: Bakers are expected to do this.', 'Hint: B _ K _ W _ _ _ ', 'Bakewell', 'Bake Well', 1)");
        MyDB.execSQL("INSERT INTO " + TABLE_PUZZLES + "(PUZZLE_CLUE, PUZZLE_HINT, PUZZLE_ANSWER, PUZZLE_SECOND_ANSWER, REGION_ID) VALUES ('Puzzle 5: A colour of the rainbow', 'Hint: Answer is the missing colour of Red, Orange, Yellow, Green, Blue, _, Violet', 'Indigo', 'Indigo Jewellers', 1)");
        MyDB.execSQL("INSERT INTO " + TABLE_PUZZLES + "(PUZZLE_CLUE, PUZZLE_HINT, PUZZLE_ANSWER, PUZZLE_SECOND_ANSWER, REGION_ID) VALUES ('Puzzle 6: Two word answer: 1st - _ & pepper, 2nd - a building used to live in.', 'Hint: 1st word in answer - _ and vinegar, 2nd word in answer - humans live in this.', 'Salt House', 'Salt House Hotel',1)");
        MyDB.execSQL("INSERT INTO " + TABLE_PUZZLES + "(PUZZLE_CLUE, PUZZLE_HINT, PUZZLE_ANSWER, PUZZLE_SECOND_ANSWER, REGION_ID) VALUES ('Puzzle 7: A walkway alongside the seafront','Hint: Located near puzzle 4.', 'Promenade','Promenade Cafe', 1)");
        MyDB.execSQL("INSERT INTO " + TABLE_PUZZLES + "(PUZZLE_CLUE, PUZZLE_HINT, PUZZLE_ANSWER, PUZZLE_SECOND_ANSWER, REGION_ID) VALUES ('Puzzle 8: The name of this bar is based on a precious gem usually given in an engagement.', 'Hint: Pressure underneath the earth makes these', 'Diamond', 'Diamond Bar', 1)");
        MyDB.execSQL("INSERT INTO " + TABLE_PUZZLES + "(PUZZLE_CLUE, PUZZLE_HINT, PUZZLE_ANSWER, PUZZLE_SECOND_ANSWER, REGION_ID) VALUES ('Puzzle 9: DIY that is in a gorgeous state', 'Location: Located between the park and the town centre.', 'Homemade Beautiful', 'Home Made Beautiful', 1)");
        MyDB.execSQL("INSERT INTO " + TABLE_PUZZLES + "(PUZZLE_CLUE, PUZZLE_HINT, PUZZLE_ANSWER, PUZZLE_SECOND_ANSWER, REGION_ID) VALUES ('Puzzle 10: Owning something together and a famous female country-pop singer', 'Location: Traffic lights can be seen from this place', 'Our Dollys', 'Our Dollys Cafe', 1)");
        MyDB.execSQL("INSERT INTO " + TABLE_PUZZLES + "(PUZZLE_CLUE, PUZZLE_HINT, PUZZLE_ANSWER, PUZZLE_SECOND_ANSWER, REGION_ID) VALUES ('Puzzle 11: Ususal award for being runners-up and an extremely steep incline made of rock', 'Hint: Cutlery is this colour normally and something only rock climbers should attempt to climb.', 'Silver Cliffs', 'Silvercliffs', 1)");
        MyDB.execSQL("INSERT INTO " + TABLE_PUZZLES + "(PUZZLE_CLUE, PUZZLE_HINT, PUZZLE_ANSWER, PUZZLE_SECOND_ANSWER, REGION_ID) VALUES ('Puzzle 12: I never knew Sherlock Holmes sidekick was struggling with his sight', 'Hint: A father would say this to his boy if he did not hear him and a place for sore eyes','Watsons Optican', 'Watsons Opticans', 1)");
        MyDB.execSQL("INSERT INTO " + TABLE_PUZZLES + "(PUZZLE_CLUE, PUZZLE_HINT, PUZZLE_ANSWER, PUZZLE_SECOND_ANSWER, REGION_ID) VALUES ('Puzzle 13: This area on Earth has less area disovered than Mars and a place of rest and safety', 'Location: This place has front-row seats for a view of Rathlin Island','Sea Heaven', 'Sea Heaven Therapy', 1)");
        MyDB.execSQL("INSERT INTO " + TABLE_PUZZLES + "(PUZZLE_CLUE, PUZZLE_HINT, PUZZLE_ANSWER, PUZZLE_SECOND_ANSWER, REGION_ID) VALUES ('Puzzle 14: Spice and friends', 'Location: It is near Round-About somewhere','Thyme and co', 'Thyme and co cafe', 1)");
        MyDB.execSQL("INSERT INTO " + TABLE_PUZZLES + "(PUZZLE_CLUE, PUZZLE_HINT, PUZZLE_ANSWER, PUZZLE_SECOND_ANSWER, REGION_ID) VALUES ('Puzzle 15: A tool used to cut metal wiring', 'Location: The business in front of it could make decent use of a cutting tool.','Snip', 'Snips', 1)");
        MyDB.execSQL("INSERT INTO " + TABLE_PUZZLES + "(PUZZLE_CLUE, PUZZLE_HINT, PUZZLE_ANSWER, PUZZLE_SECOND_ANSWER, REGION_ID) VALUES ('Puzzle 16: Angels are usually depicted to have this above their head', 'Hint: Shaped like a ring.','Halo', 'Halo Nightclub', 1)");
        MyDB.execSQL("INSERT INTO " + TABLE_PUZZLES + "(PUZZLE_CLUE, PUZZLE_HINT, PUZZLE_ANSWER, PUZZLE_SECOND_ANSWER, REGION_ID) VALUES ('Puzzle 17: It does not have a deck that the namesake would have us believe', 'Location: Beside another business of the same name','D J Mclister', 'DJ McLister', 1)");
        MyDB.execSQL("INSERT INTO " + TABLE_PUZZLES + "(PUZZLE_CLUE, PUZZLE_HINT, PUZZLE_ANSWER, PUZZLE_SECOND_ANSWER, REGION_ID) VALUES ('Puzzle 18: Providing a helpful service', 'Being in a two-player tean','Cooperative', 'Coop', 1)");
        MyDB.execSQL("INSERT INTO " + TABLE_PUZZLES + "(PUZZLE_CLUE, PUZZLE_HINT, PUZZLE_ANSWER, PUZZLE_SECOND_ANSWER, REGION_ID) VALUES ('Puzzle 19: A metallic element in the periodic table, symbolised by pt.', 'Hint: An award that is viewed by some to be better than gold.','Platinum', 'Platinum Support and Care Services', 1)");
        MyDB.execSQL("INSERT INTO " + TABLE_PUZZLES + "(PUZZLE_CLUE, PUZZLE_HINT, PUZZLE_ANSWER, PUZZLE_SECOND_ANSWER, REGION_ID) VALUES ('Puzzle 20: Another word for coast and a winged animal', 'Location: Between the golf course and the ferry terminal.','Shorebird', 'Shorebird Cafe', 1)");
        MyDB.execSQL("INSERT INTO " + TABLE_PUZZLES + "(PUZZLE_CLUE, PUZZLE_HINT, PUZZLE_ANSWER, PUZZLE_SECOND_ANSWER, REGION_ID) VALUES ('Puzzle 21: _ & pestle', 'Hint: Can be a name for a male','Mortons', 'Mortons fish and chips', 1)");
        MyDB.execSQL("INSERT INTO " + TABLE_PUZZLES + "(PUZZLE_CLUE, PUZZLE_HINT, PUZZLE_ANSWER, PUZZLE_SECOND_ANSWER, REGION_ID) VALUES ('Puzzle 22: This business shares the name of a magic nanny in a movie', 'Hint: M _ A _ E _','McAfee', 'McAfee properties', 1)");
        MyDB.execSQL("INSERT INTO " + TABLE_PUZZLES + "(PUZZLE_CLUE, PUZZLE_HINT, PUZZLE_ANSWER, PUZZLE_SECOND_ANSWER, REGION_ID) VALUES ('Puzzle 23: Where would  most of the rackets be in the town', 'Hint: The question is more directed than you might think. The last word is court to help you.','Ballycastle Tennis court', 'Tennis court', 1)");
        MyDB.execSQL("INSERT INTO " + TABLE_PUZZLES + "(PUZZLE_CLUE, PUZZLE_HINT, PUZZLE_ANSWER, PUZZLE_SECOND_ANSWER, REGION_ID) VALUES ('Puzzle 24: Any living creature found in the sea are considered _ life and a place ususally filled with tourists.', 'Location: On the corner of a road, with the view of the sea straight ahead of it.','Marine Hotel', 'Marine', 1)");
        MyDB.execSQL("INSERT INTO " + TABLE_PUZZLES + "(PUZZLE_CLUE, PUZZLE_HINT, PUZZLE_ANSWER, PUZZLE_SECOND_ANSWER, REGION_ID) VALUES ('Puzzle 25: Playing by the rules and the section of body that contain the most variety of senses.', 'Location: Not directly in Ballycastle but there for all to see','Fair Head', '', 1)");


        MyDB.execSQL("INSERT INTO " + TABLE_PUZZLES + "(PUZZLE_CLUE, PUZZLE_HINT, PUZZLE_ANSWER, PUZZLE_SECOND_ANSWER, REGION_ID) VALUES ('Puzzle 1: This peeling vegetable is not in a good condition.', 'Hint: Something that could do with a clean and a vegetable that can make you cry', 'Dirty Onion', 'Dirty Onion and Yardbird', 2)");
        MyDB.execSQL("INSERT INTO " + TABLE_PUZZLES + "(PUZZLE_CLUE, PUZZLE_HINT, PUZZLE_ANSWER, PUZZLE_SECOND_ANSWER, REGION_ID) VALUES ('Puzzle 2: This pub has a name that suggests it is holding some sort of score.', 'Location: Between botanic avenue and the city centre', 'Points', 'Points Bar', 2)");
        MyDB.execSQL("INSERT INTO " + TABLE_PUZZLES + "(PUZZLE_CLUE, PUZZLE_HINT, PUZZLE_ANSWER, PUZZLE_SECOND_ANSWER, REGION_ID) VALUES ('Puzzle 3: An opaque, all-black gemstone.', 'Hint: o _ y _ ', 'Onyx', 'Onyx House', 2)");
        MyDB.execSQL("INSERT INTO " + TABLE_PUZZLES + "(PUZZLE_CLUE, PUZZLE_HINT, PUZZLE_ANSWER, PUZZLE_SECOND_ANSWER, REGION_ID) VALUES ('Puzzle 4: Having proof of where abouts during a crime', 'Hint: Saying farewell to a mate called Ali', 'Alibi', 'Alibi Nightclub', 2)");
        MyDB.execSQL("INSERT INTO " + TABLE_PUZZLES + "(PUZZLE_CLUE, PUZZLE_HINT, PUZZLE_ANSWER, PUZZLE_SECOND_ANSWER, REGION_ID) VALUES ('Puzzle 5: An image usually framed and a place to view various art designs', 'Hint: 1st word in answer- The saying goes that you should always look at the bigger _ , second word in answer - _ _ L L _ R Y', 'Picture Gallery', '', 2)");
        MyDB.execSQL("INSERT INTO " + TABLE_PUZZLES + "(PUZZLE_CLUE, PUZZLE_HINT, PUZZLE_ANSWER, PUZZLE_SECOND_ANSWER, REGION_ID) VALUES ('Puzzle 6: This pub seems to be popular with a certain greek demi-god', 'Hint: A beetle with a rhino-like horn is called this', 'Hercules Bar', 'Hercules ', 2)");
        MyDB.execSQL("INSERT INTO " + TABLE_PUZZLES + "(PUZZLE_CLUE, PUZZLE_HINT, PUZZLE_ANSWER, PUZZLE_SECOND_ANSWER, REGION_ID) VALUES ('Puzzle 7: A lot of these parts make up a puzzle', 'Location: Found near Victoria Square', 'Jigsaw', 'Jigsaw Clothing Store', 2)");
        MyDB.execSQL("INSERT INTO " + TABLE_PUZZLES + "(PUZZLE_CLUE, PUZZLE_HINT, PUZZLE_ANSWER, PUZZLE_SECOND_ANSWER, REGION_ID) VALUES ('Puzzle 8: This is worn on a foot and a predefined area.', 'Hint: S _ _ _ Z _ _ _', 'Shoe Zone', '', 2)");
        MyDB.execSQL("INSERT INTO " + TABLE_PUZZLES + "(PUZZLE_CLUE, PUZZLE_HINT, PUZZLE_ANSWER, PUZZLE_SECOND_ANSWER, REGION_ID) VALUES ('Puzzle 9: A mythical creature that depicts the head of a human and the body of a lion', 'Hint: Egyptians were masters at sculpturing statues of these creatures', 'Sphinx', 'Sphinx Kebab', 2)");
        MyDB.execSQL("INSERT INTO " + TABLE_PUZZLES + "(PUZZLE_CLUE, PUZZLE_HINT, PUZZLE_ANSWER, PUZZLE_SECOND_ANSWER, REGION_ID) VALUES ('Puzzle 10: Usually comes with lemon, especially in drinks, the sun or a torch emits this to a degree', 'Location: Between a river and a Nandos', 'Limelight', 'Lime light', 2)");
        MyDB.execSQL("INSERT INTO " + TABLE_PUZZLES + "(PUZZLE_CLUE, PUZZLE_HINT, PUZZLE_ANSWER, PUZZLE_SECOND_ANSWER, REGION_ID) VALUES ('Puzzle 11: Many people have done this with a blanket or duvet if they are cold', 'Location: This business can be found in Castle Court', 'Wrapped Up', '', 2)");
        MyDB.execSQL("INSERT INTO " + TABLE_PUZZLES + "(PUZZLE_CLUE, PUZZLE_HINT, PUZZLE_ANSWER, PUZZLE_SECOND_ANSWER, REGION_ID) VALUES ('Puzzle 12: Entering the world for the first time and growing up in the same place', 'Hint: A child that is just conceived has just been _ , 2nd word in answer is and, some dogs can be pure _', 'Born and Bred', '', 2)");
        MyDB.execSQL("INSERT INTO " + TABLE_PUZZLES + "(PUZZLE_CLUE, PUZZLE_HINT, PUZZLE_ANSWER, PUZZLE_SECOND_ANSWER, REGION_ID) VALUES ('Puzzle 13: The name of this business is very ironic, something that is trash whilst also being clean.', 'Hint: Something that is neewly made and a slang word for terrible', 'Fresh Garbage', '', 2)");
        MyDB.execSQL("INSERT INTO " + TABLE_PUZZLES + "(PUZZLE_CLUE, PUZZLE_HINT, PUZZLE_ANSWER, PUZZLE_SECOND_ANSWER, REGION_ID) VALUES ('Puzzle 14: A item that is kept as a reminder of someone or something.', 'Location: Found on the Ormeau Road.', 'Memento', 'Memento Floral Design', 2)");
        MyDB.execSQL("INSERT INTO " + TABLE_PUZZLES + "(PUZZLE_CLUE, PUZZLE_HINT, PUZZLE_ANSWER, PUZZLE_SECOND_ANSWER, REGION_ID) VALUES ('Puzzle 15: A container that is full of flavour liquid', 'Hint: J _ _ C _  j _ R.', 'Juice Jar', '', 2)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int i, int i1)
    {
        //Drops tables when new version of database is created
        MyDB.execSQL(" DROP TABLE IF EXISTS " + TABLE_PUZZLES);
        MyDB.execSQL(" DROP TABLE IF EXISTS " + TABLE_REGION);
        MyDB.execSQL(" DROP TABLE IF EXISTS " + TABLE_SOLVED);
        MyDB.execSQL(" DROP TABLE IF EXISTS " + TABLE_HINT);
        MyDB.execSQL(" DROP TABLE IF EXISTS " + TABLE_HINT_USED);
        MyDB.execSQL(" DROP TABLE IF EXISTS " + TABLE_USER);
        onCreate(MyDB);
    }


    public Boolean checkUsername(String username)
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
    public Boolean insertUserData(String username, String password, String secretAnswer)
    {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USER_COL_2, username);
        contentValues.put(USER_COL_3, password);
        contentValues.put(USER_COL_4, secretAnswer);

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

    public boolean checkResetAllowed(String user, String secterAnswer)
    {
        SQLiteDatabase MyDB = this.getReadableDatabase();

        String idQuery = " SELECT " + USER_COL_1 +  " FROM " + TABLE_USER + " WHERE " + USER_COL_2 + " ='" + user + "'" + " AND " + USER_COL_4 + " ='" + secterAnswer + "'";

        Cursor cursor = MyDB.rawQuery(idQuery, null);
//
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

    public boolean updateUserInfo(int userID, String newPassword)
    {
        //Allows for writing into the database
        SQLiteDatabase MyDB = this.getWritableDatabase();
        //Stores the column values
        ContentValues cv = new ContentValues();

        cv.put(USER_COL_3, newPassword);

        long update = MyDB.update(TABLE_USER, cv, "USER_ID" + " = ?", new String[] {String.valueOf(userID)});

        if (update == -1)
        {
            return false;
        }

        else
        {
            return true;
        }
    }


    public Boolean insertHintCoin(int userID)
    {
        int hintCoin = 1;

        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(HINT_COL_2, userID);
        contentValues.put(HINT_COL_3, hintCoin);

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

        String countQuery = "SELECT COUNT (*) FROM " + TABLE_SOLVED +  " INNER JOIN " + TABLE_PUZZLES + " ON " + TABLE_PUZZLES + "." + PUZZLE_COL_1 + " = " + TABLE_SOLVED + "." + SOLVED_COL_3 + " WHERE " + SOLVED_COL_4 + " = 1" + " AND " + SOLVED_COL_2 + " ='" + userID + "'" + " AND " + PUZZLE_COL_6 + " ='" + regionID + "'";

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

        String countQuery = "SELECT COUNT (*) FROM " + TABLE_PUZZLES + " WHERE " + PUZZLE_COL_6 + " ='" + regionID + "'";

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

        String idQuery = " SELECT " + HINT_USED_COL_1 + " FROM " + TABLE_HINT_USED + " WHERE " + HINT_USED_COL_2 + " ='"
                + userID + "'" + " AND " + HINT_USED_COL_3 + " ='" + puzzleID + "'" + " AND " + HINT_USED_COL_4 + "= 1";
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

        String hintQuery = "SELECT " + PUZZLE_COL_3 +  " FROM " + TABLE_PUZZLES + " WHERE "
                + PUZZLE_COL_1 + " ='" + puzzleID + "'";

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

        String queryDB = "SELECT * FROM " + TABLE_PUZZLES  + " INNER JOIN " + TABLE_SOLVED + " ON " + TABLE_PUZZLES
                + "." + PUZZLE_COL_1 + " = " + TABLE_SOLVED + "." + SOLVED_COL_3 + " WHERE " + SOLVED_COL_2 + " ='"
                + userID + "'" + " AND " + PUZZLE_COL_6 + " ='" + region + "'" + " AND " + SOLVED_COL_4 + " = 1" + " ORDER BY " + PUZZLE_COL_1;

        Cursor cursor = MyDB.rawQuery(queryDB, null);

        if (cursor.moveToFirst())
        {
            do
            {
                String puzzleName = cursor.getString(1);
                String puzzleHint = cursor.getString(2);
                String puzzleAnswer = cursor.getString(3);

                PuzzleModel newEvent = new PuzzleModel(puzzleName, puzzleHint, puzzleAnswer);

                returnPuzzleSolved.add(newEvent);
            }

            while(cursor.moveToNext());
        }

        else
        {

        }

        cursor.close();
        MyDB.close();

        //return returnList from method
        return returnPuzzleSolved;
    }
}
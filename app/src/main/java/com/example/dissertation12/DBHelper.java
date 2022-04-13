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
    //Database name
    private static final String DBNAME = "Login.db";

    //User table
    private static final String TABLE_USER = "USER_DATA";
    //User ID column
    private static final String USER_COL_1 = "USER_ID";
    //Username coluumn
    private static final String USER_COL_2 = "USER_USERNAME";
    //User Password column
    private static final String USER_COL_3 = "USER_PASSWORD";
    //User Secret answer column
    private static final String USER_COL_4 = "USER_SECRET_ANSWER";
    //User steps walked
    private static final String USER_COL_5 = "USER_STEP_AMOUNT";

    //Region table
    private static final String TABLE_REGION = "REGION_DATA";
    //Region ID column
    private static final String REGION_COL_1 = "REGION_ID";
    //Region name column
    private static final String REGION_COL_2 = "REGION_NAME";

    //Puzzle table
    private static final String TABLE_PUZZLES = "PUZZLE_DATA";
    //Puzzle ID column
    private static final String PUZZLE_COL_1 = "PUZZLE_ID";
    //Puzzle Clue column
    private static final String PUZZLE_COL_2 = "PUZZLE_CLUE";
    //Puzzle Hint column
    private static final String PUZZLE_COL_3 = "PUZZLE_HINT";
    //Puzzle Answer column
    private static final String PUZZLE_COL_4 = "PUZZLE_ANSWER";
    //Puzzle Answer 2 column
    private static final String PUZZLE_COL_5 = "PUZZLE_SECOND_ANSWER";
    //Puzzle's region ID
    private static final String PUZZLE_COL_6 = "REGION_ID";

    //Solved puzzles table
    private static final String TABLE_SOLVED = "SOLVED_DATA";
    //Puzzles solved ID
    private static final String SOLVED_COL_1 = "SOLVED_ID";
    //Users who solved puzzle ID
    private static final String SOLVED_COL_2 = "USER_ID";
    //Puzzles that have been solved ID
    private static final String SOLVED_COL_3 = "PUZZLE_ID";
    //Puzzle has been solved column
    private static final String SOLVED_COL_4 = "IS_SOLVED";

    //Hint table
    private static final String TABLE_HINT = "HINT_DATA";
    //Hint ID column
    private static final String HINT_COL_1 = "HINT_ID";
    //User who owns hint column
    private static final String HINT_COL_2 = "USER_ID";
    //Total user hint amount column
    private static final String HINT_COL_3 = "HINT_TOTAL";

    //Hint is used table
    private static final String TABLE_HINT_USED = "HINT_USED_DATA";
    //Hint used ID
    private static final String HINT_USED_COL_1 = "HINT_USED_ID";
    //User who unlocked Hint ID
    private static final String HINT_USED_COL_2 = "USER_ID";
    //Puzzle that has it's hint unlocked ID
    private static final String HINT_USED_COL_3 = "PUZZLE_ID";
    //Hint is unlocked column
    private static final String HINT_USED_COL_4 = "HINT_IS_USED";

    //Used to upgrade database on changes by changing version number
    public DBHelper(Context context)
    {
        super(context, "Login.db", null, 65);
    }

    @Override
    public void onCreate(SQLiteDatabase MyDB)
    {
        //Create user table with User ID as primary key.
        MyDB.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_USER + "(USER_ID INTEGER PRIMARY KEY AUTOINCREMENT, USER_USERNAME TEXT, USER_PASSWORD TEXT, USER_SECRET_ANSWER TEXT, USER_STEP_AMOUNT TEXT)");
        //Create region table with Region ID as primary key
        MyDB.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_REGION + "(REGION_ID INTEGER PRIMARY KEY AUTOINCREMENT, REGION_NAME TEXT)");
        //Create puzzle table with Puzzle ID as primary key. Foreign key is region ID to refer to the region the puzzle is based in.
        MyDB.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_PUZZLES + "(PUZZLE_ID INTEGER PRIMARY KEY AUTOINCREMENT, PUZZLE_CLUE TEXT, PUZZLE_HINT TEXT,PUZZLE_ANSWER TEXT, PUZZLE_SECOND_ANSWER TEXT, REGION_ID INTEGER, FOREIGN KEY(REGION_ID) REFERENCES TABLE_REGION(REGION_ID))");
        //Create puzzle solved table with Solved ID as primary key and User ID/Puzzle ID acting as foreigin keys.
        MyDB.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_SOLVED + "(SOLVED_ID INTEGER PRIMARY KEY AUTOINCREMENT, USER_ID INTEGER, PUZZLE_ID INTEGER, IS_SOLVED INTEGER, FOREIGN KEY(USER_ID) REFERENCES TABLE_USER(USER_ID), FOREIGN KEY(PUZZLE_ID) REFERENCES TABLE_PUZZLE(PUZZLE_ID))");
        //Create table hint with hint ID acting as primary key and user ID acting as foreign key.
        MyDB.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_HINT + "(HINT_ID INTEGER PRIMARY KEY AUTOINCREMENT, USER_ID INTEGER, HINT_TOTAL TEXT, FOREIGN KEY(USER_ID) REFERENCES TABLE_USER(USER_ID))");
        //Create table hint already used which holds hint used ID as the primary key abd user ID and puzzle ID as foreign keys
        MyDB.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_HINT_USED + "(HINT_USED_ID INTEGER PRIMARY KEY AUTOINCREMENT, USER_ID INTEGER, PUZZLE_ID INTEGER, HINT_IS_USED INTEGER, FOREIGN KEY(USER_ID) REFERENCES TABLE_USER(USER_ID),FOREIGN KEY(PUZZLE_ID) REFERENCES TABLE_PUZZLE(PUZZLE_ID))");

        //Inserts location into region table
        MyDB.execSQL("INSERT INTO " + TABLE_REGION + "(REGION_NAME) VALUES ('Ballycastle')");
        MyDB.execSQL("INSERT INTO " + TABLE_REGION + "(REGION_NAME) VALUES ('Belfast')");

        //Inserts Ballycastle's puzzles into puzzle table including the puzzle name, puzzle hint, puzzle answer, puzzles second answer and region ID.
        MyDB.execSQL("INSERT INTO " + TABLE_PUZZLES + "(PUZZLE_CLUE, PUZZLE_HINT, PUZZLE_ANSWER, PUZZLE_SECOND_ANSWER, REGION_ID) VALUES ('Puzzle 1: A shining form of potato?', 'Hint: Two word answer. 1st - The colour of winning 1st at the olympics, 2nd - also known as fries','GoldenChip','GoldenChipFishAndChips', 1)");
        MyDB.execSQL("INSERT INTO " + TABLE_PUZZLES + "(PUZZLE_CLUE, PUZZLE_HINT, PUZZLE_ANSWER, PUZZLE_SECOND_ANSWER, REGION_ID) VALUES ('Puzzle 2: A colour and a mythic being, simple right?', 'Hint: Two word answer. 1st - colour that usually represents danger or death, 2nd - scaly, fire breathing creature', 'RedDragon', '', 1)");
        MyDB.execSQL("INSERT INTO " + TABLE_PUZZLES + "(PUZZLE_CLUE, PUZZLE_HINT, PUZZLE_ANSWER, PUZZLE_SECOND_ANSWER, REGION_ID) VALUES ('Puzzle 3: This bar will fill the spot after some dinner', 'Hint: Two word answer. 1st - The meal that comes after a main course, 2nd - 3 letter word, a place that usually serves drinks.', 'DessertBar', 'DessertBarRestaurant', 1)");
        MyDB.execSQL("INSERT INTO " + TABLE_PUZZLES + "(PUZZLE_CLUE, PUZZLE_HINT, PUZZLE_ANSWER, PUZZLE_SECOND_ANSWER, REGION_ID) VALUES ('Puzzle 4: Bakers are expected to do this.', 'Hint: B _ K _ W _ _ _ ', 'Bakewell', 'Bakewells', 1)");
        MyDB.execSQL("INSERT INTO " + TABLE_PUZZLES + "(PUZZLE_CLUE, PUZZLE_HINT, PUZZLE_ANSWER, PUZZLE_SECOND_ANSWER, REGION_ID) VALUES ('Puzzle 5: A colour of the rainbow', 'Hint: Answer is the missing colour of Red, Orange, Yellow, Green, Blue, _, Violet', 'Indigo', 'IndigoJewellers', 1)");
        MyDB.execSQL("INSERT INTO " + TABLE_PUZZLES + "(PUZZLE_CLUE, PUZZLE_HINT, PUZZLE_ANSWER, PUZZLE_SECOND_ANSWER, REGION_ID) VALUES ('Puzzle 6: Two word answer: 1st - _ & pepper, 2nd - a building for living in.', 'Hint: 1st word in answer - _ and vinegar, 2nd word in answer - humans live in this.', 'SaltHouse', 'SaltHouseHotel',1)");
        MyDB.execSQL("INSERT INTO " + TABLE_PUZZLES + "(PUZZLE_CLUE, PUZZLE_HINT, PUZZLE_ANSWER, PUZZLE_SECOND_ANSWER, REGION_ID) VALUES ('Puzzle 7: A walkway alongside the seafront','Hint: Located near puzzle 4.', 'Promenade','PromenadeCafe', 1)");
        MyDB.execSQL("INSERT INTO " + TABLE_PUZZLES + "(PUZZLE_CLUE, PUZZLE_HINT, PUZZLE_ANSWER, PUZZLE_SECOND_ANSWER, REGION_ID) VALUES ('Puzzle 8: The name of this bar is based on a precious gem usually given in an engagement.', 'Hint: Pressure underneath the earth makes these', 'Diamond', 'DiamondBar', 1)");
        MyDB.execSQL("INSERT INTO " + TABLE_PUZZLES + "(PUZZLE_CLUE, PUZZLE_HINT, PUZZLE_ANSWER, PUZZLE_SECOND_ANSWER, REGION_ID) VALUES ('Puzzle 9: DIY that is in a gorgeous state', 'Location: Located between the park and the town centre.', 'HomemadeBeautiful', '', 1)");
        MyDB.execSQL("INSERT INTO " + TABLE_PUZZLES + "(PUZZLE_CLUE, PUZZLE_HINT, PUZZLE_ANSWER, PUZZLE_SECOND_ANSWER, REGION_ID) VALUES ('Puzzle 10: Owning something together and a famous female country-pop singer', 'Location: Traffic lights can be seen from this place', 'OurDollys', 'OurDollysCafe', 1)");
        MyDB.execSQL("INSERT INTO " + TABLE_PUZZLES + "(PUZZLE_CLUE, PUZZLE_HINT, PUZZLE_ANSWER, PUZZLE_SECOND_ANSWER, REGION_ID) VALUES ('Puzzle 11: Ususal award for being runners-up and an extremely steep incline made of rock', 'Hint: Cutlery is this colour normally and something only rock climbers should attempt to climb.', 'SilverCliff', 'SilverCliffs', 1)");
        MyDB.execSQL("INSERT INTO " + TABLE_PUZZLES + "(PUZZLE_CLUE, PUZZLE_HINT, PUZZLE_ANSWER, PUZZLE_SECOND_ANSWER, REGION_ID) VALUES ('Puzzle 12: I never knew Sherlock Holmes sidekick was struggling with his sight', 'Hint: A father would say this to his boy if he did not hear him and a place for sore eyes','WatsonsOptician', 'WatsonOptician', 1)");
        MyDB.execSQL("INSERT INTO " + TABLE_PUZZLES + "(PUZZLE_CLUE, PUZZLE_HINT, PUZZLE_ANSWER, PUZZLE_SECOND_ANSWER, REGION_ID) VALUES ('Puzzle 13: This area on Earth has less area discovered than Mars and a place of rest and safety', 'Location: This place has front-row seats for a view of Rathlin Island','SeaHaven', 'SeaHavenTherapy', 1)");
        MyDB.execSQL("INSERT INTO " + TABLE_PUZZLES + "(PUZZLE_CLUE, PUZZLE_HINT, PUZZLE_ANSWER, PUZZLE_SECOND_ANSWER, REGION_ID) VALUES ('Puzzle 14: Herb and a short term for a company', 'Location: It is near Round-About somewhere','ThymeAndCo', 'ThymeAndCoCafe', 1)");
        MyDB.execSQL("INSERT INTO " + TABLE_PUZZLES + "(PUZZLE_CLUE, PUZZLE_HINT, PUZZLE_ANSWER, PUZZLE_SECOND_ANSWER, REGION_ID) VALUES ('Puzzle 15: A tool used to cut metal wiring', 'Location: The business in front of it could make decent use of a cutting tool.','Snip', 'Snips', 1)");
        MyDB.execSQL("INSERT INTO " + TABLE_PUZZLES + "(PUZZLE_CLUE, PUZZLE_HINT, PUZZLE_ANSWER, PUZZLE_SECOND_ANSWER, REGION_ID) VALUES ('Puzzle 16: Angels are usually depicted to have this above their head', 'Hint: Shaped like a ring.','Halo', 'HaloNightclub', 1)");
        MyDB.execSQL("INSERT INTO " + TABLE_PUZZLES + "(PUZZLE_CLUE, PUZZLE_HINT, PUZZLE_ANSWER, PUZZLE_SECOND_ANSWER, REGION_ID) VALUES ('Puzzle 17: It does not have a music deck that the namesake would have us believe', 'Location: Beside another business of the same name','DjMcLister', 'DjMcListers', 1)");
        MyDB.execSQL("INSERT INTO " + TABLE_PUZZLES + "(PUZZLE_CLUE, PUZZLE_HINT, PUZZLE_ANSWER, PUZZLE_SECOND_ANSWER, REGION_ID) VALUES ('Puzzle 18: A chicken lives in this', 'Hint: Providing a help service to someone else','Cooperative', 'Coop', 1)");
        MyDB.execSQL("INSERT INTO " + TABLE_PUZZLES + "(PUZZLE_CLUE, PUZZLE_HINT, PUZZLE_ANSWER, PUZZLE_SECOND_ANSWER, REGION_ID) VALUES ('Puzzle 19: A metallic element in the periodic table, symbolised by pt.', 'Hint: An award that is viewed by some to be better than gold.','Platinum', 'PlatinumSupportAndCareServices', 1)");
        MyDB.execSQL("INSERT INTO " + TABLE_PUZZLES + "(PUZZLE_CLUE, PUZZLE_HINT, PUZZLE_ANSWER, PUZZLE_SECOND_ANSWER, REGION_ID) VALUES ('Puzzle 20: Another word for coast and a winged animal', 'Location: Between the golf course and the ferry terminal.','Shorebird', 'ShorebirdCafe', 1)");
        MyDB.execSQL("INSERT INTO " + TABLE_PUZZLES + "(PUZZLE_CLUE, PUZZLE_HINT, PUZZLE_ANSWER, PUZZLE_SECOND_ANSWER, REGION_ID) VALUES ('Puzzle 21: Flies do not want to get caught up in these', 'Hint: A spider produces these','Cobwebs', 'Cobweb', 1)");
        MyDB.execSQL("INSERT INTO " + TABLE_PUZZLES + "(PUZZLE_CLUE, PUZZLE_HINT, PUZZLE_ANSWER, PUZZLE_SECOND_ANSWER, REGION_ID) VALUES ('Puzzle 22: This business shares the name of a magic nanny in a movie', 'Hint: M _ A _ E _','Mcafee', 'McafeeProperties', 1)");
        MyDB.execSQL("INSERT INTO " + TABLE_PUZZLES + "(PUZZLE_CLUE, PUZZLE_HINT, PUZZLE_ANSWER, PUZZLE_SECOND_ANSWER, REGION_ID) VALUES ('Puzzle 23: Where would most of the rackets be in the town', 'Hint: The question is more directed than you might think. The last word is court to help you.','BallycastleTennisCourt', 'TennisCourt', 1)");
        MyDB.execSQL("INSERT INTO " + TABLE_PUZZLES + "(PUZZLE_CLUE, PUZZLE_HINT, PUZZLE_ANSWER, PUZZLE_SECOND_ANSWER, REGION_ID) VALUES ('Puzzle 24: Any living creature found in the sea are considered _ life and a building usually filled with tourists.', 'Location: On the corner of a road, with the view of the sea straight ahead of it.','MarineHotel', 'Marine', 1)");
        MyDB.execSQL("INSERT INTO " + TABLE_PUZZLES + "(PUZZLE_CLUE, PUZZLE_HINT, PUZZLE_ANSWER, PUZZLE_SECOND_ANSWER, REGION_ID) VALUES ('Puzzle 25: Playing by the rules and the section of body that contain the most variety of senses.', 'Location: Not directly in Ballycastle but there for all to see','FairHead', '', 1)");

        //Inserts Belfast's puzzles into puzzle table including the puzzle name, puzzle hint, puzzle answer, puzzles second answer and region ID.
        MyDB.execSQL("INSERT INTO " + TABLE_PUZZLES + "(PUZZLE_CLUE, PUZZLE_HINT, PUZZLE_ANSWER, PUZZLE_SECOND_ANSWER, REGION_ID) VALUES ('Puzzle 1: This peeling vegetable is not in a good condition.', 'Hint: Something that could do with a clean and a vegetable that can make you cry', 'DirtyOnion', 'DirtyOnionAndYardbird', 2)");
        MyDB.execSQL("INSERT INTO " + TABLE_PUZZLES + "(PUZZLE_CLUE, PUZZLE_HINT, PUZZLE_ANSWER, PUZZLE_SECOND_ANSWER, REGION_ID) VALUES ('Puzzle 2: This pub has a name that suggests it is holding some sort of score.', 'Location: Between botanic avenue and the city centre', 'Points', 'PointsBar', 2)");
        MyDB.execSQL("INSERT INTO " + TABLE_PUZZLES + "(PUZZLE_CLUE, PUZZLE_HINT, PUZZLE_ANSWER, PUZZLE_SECOND_ANSWER, REGION_ID) VALUES ('Puzzle 3: An opaque, all-black gemstone.', 'Hint: o _ y _ ', 'Onyx', 'OnyxHouse', 2)");
        MyDB.execSQL("INSERT INTO " + TABLE_PUZZLES + "(PUZZLE_CLUE, PUZZLE_HINT, PUZZLE_ANSWER, PUZZLE_SECOND_ANSWER, REGION_ID) VALUES ('Puzzle 4: Having proof of where abouts during a crime', 'Hint: Saying farewell to a mate called Ali', 'Alibi', 'AlibiNightClub', 2)");
        MyDB.execSQL("INSERT INTO " + TABLE_PUZZLES + "(PUZZLE_CLUE, PUZZLE_HINT, PUZZLE_ANSWER, PUZZLE_SECOND_ANSWER, REGION_ID) VALUES ('Puzzle 5: An image usually framed and a place to view various art designs', 'Hint: 1st word in answer- The saying goes that you should always look at the bigger _ , second word in answer - _ _ L L _ R Y', 'PictureGallery', '', 2)");
        MyDB.execSQL("INSERT INTO " + TABLE_PUZZLES + "(PUZZLE_CLUE, PUZZLE_HINT, PUZZLE_ANSWER, PUZZLE_SECOND_ANSWER, REGION_ID) VALUES ('Puzzle 6: This pub seems to be popular with a certain greek demi-god', 'Hint: A beetle with a rhino-like horn is called this', 'HerculesBar', 'Hercules', 2)");
        MyDB.execSQL("INSERT INTO " + TABLE_PUZZLES + "(PUZZLE_CLUE, PUZZLE_HINT, PUZZLE_ANSWER, PUZZLE_SECOND_ANSWER, REGION_ID) VALUES ('Puzzle 7: A lot of these parts make up a puzzle', 'Location: Found near Victoria Square', 'Jigsaw', 'JigsawClothingStore', 2)");
        MyDB.execSQL("INSERT INTO " + TABLE_PUZZLES + "(PUZZLE_CLUE, PUZZLE_HINT, PUZZLE_ANSWER, PUZZLE_SECOND_ANSWER, REGION_ID) VALUES ('Puzzle 8: This is worn on a foot and a predefined area.', 'Hint: S _ _ _ Z _ _ _', 'ShoeZone', '', 2)");
        MyDB.execSQL("INSERT INTO " + TABLE_PUZZLES + "(PUZZLE_CLUE, PUZZLE_HINT, PUZZLE_ANSWER, PUZZLE_SECOND_ANSWER, REGION_ID) VALUES ('Puzzle 9: A mythical creature that depicts the head of a human and the body of a lion', 'Hint: Egyptians were masters at sculpturing statues of these creatures', 'Sphinx', 'SphinxKebab', 2)");
        MyDB.execSQL("INSERT INTO " + TABLE_PUZZLES + "(PUZZLE_CLUE, PUZZLE_HINT, PUZZLE_ANSWER, PUZZLE_SECOND_ANSWER, REGION_ID) VALUES ('Puzzle 10: Usually comes with lemon, especially in drinks. The sun or a torch emits this to a degree', 'Location: Between a river and a Nandos', 'Limelight', 'LimelightNightclub', 2)");
        MyDB.execSQL("INSERT INTO " + TABLE_PUZZLES + "(PUZZLE_CLUE, PUZZLE_HINT, PUZZLE_ANSWER, PUZZLE_SECOND_ANSWER, REGION_ID) VALUES ('Puzzle 11: Many people have done this with a blanket or duvet if they are cold', 'Hint: A present is usually _ _', 'WrappedUp', 'WrapUp', 2)");
        MyDB.execSQL("INSERT INTO " + TABLE_PUZZLES + "(PUZZLE_CLUE, PUZZLE_HINT, PUZZLE_ANSWER, PUZZLE_SECOND_ANSWER, REGION_ID) VALUES ('Puzzle 12: Entering the world for the first time and growing up in the same place', 'Hint: A child that is just conceived has just been _ , 2nd word in answer is and, some dogs can be pure _', 'BornAndBred', '', 2)");
        MyDB.execSQL("INSERT INTO " + TABLE_PUZZLES + "(PUZZLE_CLUE, PUZZLE_HINT, PUZZLE_ANSWER, PUZZLE_SECOND_ANSWER, REGION_ID) VALUES ('Puzzle 13: The name of this business is very ironic, something that is trash whilst also being clean.', 'Hint: Something that is neewly made and a slang word for terrible', 'FreshGarbage', '', 2)");
        MyDB.execSQL("INSERT INTO " + TABLE_PUZZLES + "(PUZZLE_CLUE, PUZZLE_HINT, PUZZLE_ANSWER, PUZZLE_SECOND_ANSWER, REGION_ID) VALUES ('Puzzle 14: A item that is kept as a reminder of someone or something.', 'Location: Found on the Ormeau Road.', 'Memento', 'MementoFloralDesign', 2)");
        MyDB.execSQL("INSERT INTO " + TABLE_PUZZLES + "(PUZZLE_CLUE, PUZZLE_HINT, PUZZLE_ANSWER, PUZZLE_SECOND_ANSWER, REGION_ID) VALUES ('Puzzle 15: A container that is full of flavour liquid', 'Hint: J _ _ C _  J _ R.', 'JuiceJar', '', 2)");
    }

    //If changes are made to the database, comment out all tables not affected and change version number of super to upgrade table.
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


    //Method to check if username already exists in the database
    public Boolean checkUsername(String username)
    {
        //Allows for manipulation of the database
        SQLiteDatabase MyDB = this.getWritableDatabase();
        //Selects the USER_ID column for retrieval
        String[] columns = { USER_COL_1 };
        //Checks if username matches any records
        String selection = USER_COL_2 + "=?";
        //Username parsed variable placed in WHERE argument
        String[] selectionargs = {username};

        //Select USER_ID from USER_DATA WHERE username entered matches a record with the same username
        Cursor cursor = MyDB.query(TABLE_USER, columns, selection, selectionargs, null, null, null);
        //If above zero, record exists
        int count = cursor.getCount();
        //Finished with database tools

        MyDB.close();
        cursor.close();

        //If count is above 0, record exists within the database else, the record doesn't exist.
        if (count > 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    //Checks username and password entered by user to match credentials
    public Boolean checkusernamepassword(String username, String password)
    {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        //Select USER_ID
        String[] columns = { USER_COL_1 };
        //Where username and password matches a record
        String selection = USER_COL_2 + "=?" + " and " + USER_COL_3 + "=?";
        //Using the variable parsed by the user
        String[] selectionargs = {username , password};
        //Select users ID from user table where username and password matches a record in the table
        Cursor cursor = MyDB.query(TABLE_USER, columns, selection, selectionargs, null, null, null);

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
        //Acs as a method for data manipulation with a database.
        ContentValues contentValues = new ContentValues();
        //Insert username, password, secret answer entered by the user and a step count of zero for new users.
        contentValues.put(USER_COL_2, username);
        contentValues.put(USER_COL_3, password);
        contentValues.put(USER_COL_4, secretAnswer);
        contentValues.put(USER_COL_5, 0);

        //Inserts the contentValues into the user table
        long result = MyDB.insert(TABLE_USER, null, contentValues);

        //Checks if the method has been successful. If false, the data did not correctly add to the database
        if (result == -1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    //Method to allow user to reset their password by passing the security check
    public boolean checkResetAllowed(String user, String secterAnswer)
    {
        //Allows for reading of database but not data manipulation
        SQLiteDatabase MyDB = this.getReadableDatabase();

        //Selects user ID from table user where username is equal to a record with the same username AND the secret answer matches a record with the same answer
        String idQuery = " SELECT " + USER_COL_1 +  " FROM " + TABLE_USER
                + " WHERE " + USER_COL_2 + " ='" + user + "'" + " AND "
                + USER_COL_4 + " ='" + secterAnswer + "'";

        //performs query above by checking each record using idquery statement.
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

    //Method to update user password
    public boolean updateUserInfo(int userID, String newPassword)
    {
        //Allows for writing into the database
        SQLiteDatabase MyDB = this.getWritableDatabase();
        //Stores the column values
        ContentValues cv = new ContentValues();

        cv.put(USER_COL_3, newPassword);

        //Query to update user table at user password where user ID is equal to a record with the same user ID
        long update = MyDB.update(TABLE_USER, cv, "USER_ID" + " = ?", new String[] {String.valueOf(userID)});

        //Checks if the update was successful
        if (update == -1)
        {
            return false;
        }

        else
        {
            return true;
        }
    }

    //Insert hint coin into HINT_DATA method
    public Boolean insertHintCoin(int userID)
    {
        //Hint coin begins at one
        int hintCoin = 1;

        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        //Inserts user ID and hint coin into contentvalues for inserting into HINT_DATA table
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

    //A method to return the user ID from the user table.
    public int getUserID(String username)
    {
        //Initialises user ID
        int userID = 0;

        SQLiteDatabase MyDB = this.getReadableDatabase();
        //Query that selects user ID from the user table where username is equal to a record in the table.
        String idQuery = "SELECT " + USER_COL_1 +  " FROM " + TABLE_USER + " WHERE " + USER_COL_2 + " ='" + username + "'";
        Cursor cursor = MyDB.rawQuery(idQuery, null);

        //Finds the record and returns the user ID column into userID
        if (cursor.getCount() > 0)
        {
            cursor.moveToFirst();
            userID = cursor.getInt(0);
        }

        MyDB.close();
        cursor.close();

        //Passed into activity class.
        return userID;
    }

   //Method to return current user's step amount
    public int getUserStepAmount(int spUserID)
    {
        int stepAmount = 0;

        SQLiteDatabase MyDB = this.getReadableDatabase();

        //Selects user steps amount column in user table where user ID is equal to a record in the user table
        String idQuery = "SELECT " + USER_COL_5 +  " FROM " + TABLE_USER + " WHERE " + USER_COL_1 + " ='" + spUserID + "'";

        Cursor cursor = MyDB.rawQuery(idQuery, null);

        if (cursor.getCount() > 0)
        {
            cursor.moveToFirst();
            stepAmount = cursor.getInt(0);
        }

        MyDB.close();
        cursor.close();

        //Returns steps walked by user
        return stepAmount;
    }


    //Method to update user steps amount
    public boolean updateUserStepAmount(int userID, int userTotalStep)
    {
        //Allows for writing into the database
        SQLiteDatabase MyDB = this.getWritableDatabase();
        //Stores the column values
        ContentValues cv = new ContentValues();

        cv.put(USER_COL_5, userTotalStep);

        //Updates user table where user ID parsed in is equal to a record in the user table.
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

    //Method to return puzzle ID from PUZZLE_DATA table
    public int getPuzzleID(String puzzleClue)
    {
        int puzzleID = 0;

        SQLiteDatabase MyDB = this.getReadableDatabase();

        //Query that select puzzle ID from puzzle table where puzzle clue parsed in equals a puzzle clue in puzzle table
        String idQuery = "SELECT " + PUZZLE_COL_1 +  " FROM " + TABLE_PUZZLES
                + " WHERE " + PUZZLE_COL_2 + " ='" + puzzleClue + "'";

        Cursor cursor = MyDB.rawQuery(idQuery, null);

        //If there are records in the count
        if (cursor.getCount() > 0)
        {
            cursor.moveToFirst();
            puzzleID = cursor.getInt(0);
        }

        MyDB.close();
        cursor.close();

        //Returns puzzle ID
        return puzzleID;
    }


    //Method to retrieve user score for a certain region
    public int getUserScore(int userID, int regionID)
    {
        int userScore = 0;

        //Query that counts all the puzzles in SOLVED_DATA table solved by a user in a particular region
        //This requires INNER JOIN between PUZZLE_DATA and SOLVED_DATA to operate
        //Checks all the records that have been solved, by a user that the puzzle solved has the region ID being checked
        String countQuery = "SELECT COUNT (*) FROM "
                + TABLE_SOLVED +  " INNER JOIN " + TABLE_PUZZLES
                + " ON " + TABLE_PUZZLES + "." + PUZZLE_COL_1 + " = "
                + TABLE_SOLVED + "." + SOLVED_COL_3 + " WHERE "
                + SOLVED_COL_4 + " = 1" + " AND " + SOLVED_COL_2 + " ='"
                + userID + "'" + " AND " + PUZZLE_COL_6 + " ='" + regionID + "'";

        Cursor cursor = getReadableDatabase().rawQuery(countQuery, null);

        //If there are records in the count
        if (cursor.getCount() > -1)
        {
            cursor.moveToFirst();
            userScore = cursor.getInt(0);
        }
        cursor.close();

        //User score is counted and passed out
        return userScore;
    }

    //Return the total score in a region
    public int getTotalScore(int regionID)
    {
        int totalScore = 0;

        //Counts all the puzzles that exist in a region
        String countQuery = "SELECT COUNT (*) FROM " + TABLE_PUZZLES
                + " WHERE " + PUZZLE_COL_6 + " ='" + regionID + "'";

        Cursor cursor = getReadableDatabase().rawQuery(countQuery, null);
        //If there are records in the count
        if (cursor.getCount() > -1)
        {
            cursor.moveToFirst();
            totalScore = cursor.getInt(0);
        }
        cursor.close();

        //Return total score of a region
        return totalScore;
    }

    //Returns the user hint coins available
    public int getUserHintAmount(int userID)
    {
        int hintAmount = 0;

        SQLiteDatabase MyDB = this.getReadableDatabase();

        //Selects the hint coin total column where user ID is equal to a record in the hint table
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

        //Returns the user's hint coin total
        return hintAmount;
    }

    //Update the hint total column in hint table
    public boolean updateHintAmount(int userID, int hintAmount)
    {
        //Allows for writing into the database
        SQLiteDatabase MyDB = this.getWritableDatabase();
        //Stores the column values
        ContentValues cv = new ContentValues();

        cv.put(HINT_COL_2, userID);
        cv.put(HINT_COL_3, hintAmount);

        //Update user ID and hint total in table hint where user ID equals a record in hint table
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

    //Check if a hint has already been unlocked or used for a puzzle
    public boolean checkHintUnlocked(int userID, int puzzleID)
    {
        SQLiteDatabase MyDB = this.getReadableDatabase();

        //Selects record from table hint used where user ID and puzzle ID matches a record in hint used table and that the hint is unlocked
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

    //Inserts information if a hint for a puzzle has been unlocked to prevent repeated purchases
    public boolean insertPuzzleHintUnlocked(int userID, int puzzleID)
    {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        //Inserts user ID, puzzle ID and hint used is equal to one or true.
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

    //Returns the hint for a puzzle
    public String getPuzzleHint(int puzzleID)
    {
        //Blank String to hold puzzle hint
        String puzzleHint = "";

        SQLiteDatabase MyDB = this.getReadableDatabase();

        //Selects puzzle hint record where puzzle ID equals a puzzle ID in the database.
        String hintQuery = "SELECT " + PUZZLE_COL_3 +  " FROM " + TABLE_PUZZLES + " WHERE "
                + PUZZLE_COL_1 + " ='" + puzzleID + "'";

        Cursor cursor = MyDB.rawQuery(hintQuery, null);

        //If there are records in the count
        if (cursor.getCount() > 0)
        {
            cursor.moveToFirst();
            puzzleHint = cursor.getString(0);
        }

        MyDB.close();
        cursor.close();

        //Returns puzzle hint to SolvePuzzleActivity
        return puzzleHint;
    }

    //Checks if the puzzle has already been solved
    public Boolean checkPuzzleSolved(int userID, String puzzleClue, int regionID)
    {
        SQLiteDatabase MyDB = this.getReadableDatabase();

        //Inner joins solved table and puzzle table
        //Selects record where user ID is equal to passed in user ID
        //Puzzle clue is equal to a puzzle clue in puzzle table
        //Region ID is equal to the region ID passed in
        //Puzzle is solved is set to 1
        String idQuery = "SELECT " + SOLVED_COL_1 +  " FROM "
                + TABLE_SOLVED  + " INNER JOIN " + TABLE_PUZZLES
                + " ON " + TABLE_PUZZLES + "." + PUZZLE_COL_1 + " = "
                + TABLE_SOLVED + "." + SOLVED_COL_3 + " WHERE " + SOLVED_COL_2
                + " ='" + userID + "'" + " AND " + PUZZLE_COL_2 + " ='" + puzzleClue
                + "'" + " AND " + SOLVED_COL_4 + "= 1" + " AND " + PUZZLE_COL_6 + " ='"
                + regionID + "'";
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

    //Checks if the user answer is equal to the puzzle answer.
    public Boolean checkUserVSPuzzleAnswer(String puzzleClue, String userAnswer)
    {
        SQLiteDatabase MyDB = this.getReadableDatabase();

        //Selects the record where the current puzzle clue is equal to database's puzzle clue
        //AND puzzle answer is equal to user answer when converted to lowercase
        //This is to ensure no case sensitive issues arise.
        String answerQuery = "SELECT " + PUZZLE_COL_1 +  " FROM "
                + TABLE_PUZZLES  + " WHERE "  + PUZZLE_COL_2
                + " ='" + puzzleClue + "'" + " AND " + "lower("
                + PUZZLE_COL_4 + ")" + "='" + userAnswer + "'";
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

    //Repeats the same method as above but checks puzzle answer 2 instead
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

    //Inserts information into solved data table once a user solves a puzzle
    public Boolean insertSolvedAnswer(int userID, int puzzleID)
    {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        //Inserts their user ID, puzzle ID and that it is solved
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

    //Method to retrieve all the puzzles that have been solved in a region by a user.
    //Takes reference to PuzzleModel to organise the data.

    public List<PuzzleModel> selectAllSolved(int userID, int region)
    {
        //Creates a list ready for all the region's solved data to be passed into.
        List<PuzzleModel> returnPuzzleSolved = new ArrayList<>();
        SQLiteDatabase MyDB = this.getReadableDatabase();
        //Inner joins the puzzle table and the solved table
        //Selects all records where user ID equals current user, puzzle's region ID is equal to current region selected and puzzle is solved equal 1.
        //Ordered by puzzle ID number to present the list from puzzle 1 to puzzle 25 if it was Ballycastle
        String queryDB = "SELECT * FROM " + TABLE_PUZZLES  + " INNER JOIN " + TABLE_SOLVED + " ON " + TABLE_PUZZLES
                + "." + PUZZLE_COL_1 + " = " + TABLE_SOLVED + "." + SOLVED_COL_3 + " WHERE " + SOLVED_COL_2 + " ='"
                + userID + "'" + " AND " + PUZZLE_COL_6 + " ='" + region + "'" + " AND " + SOLVED_COL_4 + " = 1" + " ORDER BY " + PUZZLE_COL_1;

        Cursor cursor = MyDB.rawQuery(queryDB, null);
        if (cursor.moveToFirst())
        {
            //Retrieves the necessary puzzle information from each record amd applies them to PuzzleModel
            //Each puzzle solved is then added to the listview and this process repeats until all user's solved puzzle has been cycled through
            do
            {
                String puzzleName = cursor.getString(1);
                String puzzleHint = cursor.getString(2);
                String puzzleAnswer = cursor.getString(3);
                String puzzleAnswer2 = cursor.getString(4);

                PuzzleModel newEvent = new PuzzleModel(puzzleName, puzzleHint, puzzleAnswer, puzzleAnswer2);
                returnPuzzleSolved.add(newEvent);
            }
            while(cursor.moveToNext());
        }
        else
        {
        }
        cursor.close();
        MyDB.close();

        //returns list to listview
        return returnPuzzleSolved;
    }
}
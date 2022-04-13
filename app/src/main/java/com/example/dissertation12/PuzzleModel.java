package com.example.dissertation12;

public class PuzzleModel
{
    //Puzzle variables to hold data from PUZZLE_DATA table
    private String puzzleName;
    private String puzzleHint;
    private String puzzleAnswer;
    private String puzzleAnswer2;

    //Used to collect each column's record n during compiling of listview of solved puzzles
    public PuzzleModel(String puzzleName, String puzzleHint, String puzzleAnswer, String puzzleAnswer2)
    {
        this.puzzleName = puzzleName;
        this.puzzleHint = puzzleHint;
        this.puzzleAnswer = puzzleAnswer;
        this.puzzleAnswer2 = puzzleAnswer2;
    }

    //Converts values into string for display in listview of solved puzzles
    @Override
    public String toString()
    {
        //If second version of a puzzle isn't available, state so
        if (puzzleAnswer2.equals(""))
        {
            puzzleAnswer2 = "N/A";
        }
        else
        {
            //Adds spaces before each upper case letter to separate out each word in an answer
            puzzleAnswer2 = puzzleAnswer2.replaceAll("(\\p{Ll})(\\p{Lu})","$1 $2");
        }

        //Adds spaces before each upper case letter to separate out each word in an answer
        puzzleAnswer = puzzleAnswer.replaceAll("(\\p{Ll})(\\p{Lu})","$1 $2");

        //Organises the text neatly into the listview
        return System.lineSeparator() + puzzleName + System.lineSeparator() + System.lineSeparator() +
                "Puzzle Hint: " + puzzleHint + System.lineSeparator() + System.lineSeparator() +
                "Puzzle Answer: " + puzzleAnswer + System.lineSeparator() + System.lineSeparator() +
                "Puzzle Answer Version 2: " + puzzleAnswer2 + System.lineSeparator();
    }

    //Getters and setters
    public String getPuzzleName() {
        return puzzleName;
    }

    public void setPuzzleName(String puzzleName) {
        this.puzzleName = puzzleName;
    }

    public String getPuzzleHint() {
        return puzzleHint;
    }

    public void setPuzzleHint(String puzzleHint) {
        this.puzzleHint = puzzleHint;
    }

    public String getPuzzleAnswer() {
        return puzzleAnswer;
    }

    public void setPuzzleAnswer(String puzzleAnswer) {
        this.puzzleAnswer = puzzleAnswer;
    }

    public String getPuzzleAnswer2() { return puzzleAnswer2; }

    public void setPuzzleAnswer2(String puzzleAnswer2) { this.puzzleAnswer2 = puzzleAnswer2; }
}

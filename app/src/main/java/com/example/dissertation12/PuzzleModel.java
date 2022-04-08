package com.example.dissertation12;

public class PuzzleModel
{
    private String puzzleName;
    private String puzzleHint;
    private String puzzleAnswer;
    private String puzzleAnswer2;

    public PuzzleModel(String puzzleName, String puzzleHint, String puzzleAnswer, String puzzleAnswer2)
    {
        this.puzzleName = puzzleName;
        this.puzzleHint = puzzleHint;
        this.puzzleAnswer = puzzleAnswer;
        this.puzzleAnswer2 = puzzleAnswer2;
    }

    //Converts values into string for display mainly
    @Override
    public String toString()
    {
        if (puzzleAnswer2.equals(""))
        {
            puzzleAnswer2 = "N/A";
        }

        return System.lineSeparator() + puzzleName + System.lineSeparator() + System.lineSeparator() +
                "Puzzle Hint: " + puzzleHint + System.lineSeparator() + System.lineSeparator() +
                "Puzzle Answer: " + puzzleAnswer + System.lineSeparator() + System.lineSeparator() +
                "Puzzle Answer Version 2: " + puzzleAnswer2 + System.lineSeparator();
    }

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

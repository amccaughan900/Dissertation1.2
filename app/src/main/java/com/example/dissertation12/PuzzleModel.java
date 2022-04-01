package com.example.dissertation12;

public class PuzzleModel
{
    private String puzzleName;
    private String puzzleHint;
    private String puzzleAnswer;

    public PuzzleModel(String puzzleName, String puzzleHint, String puzzleAnswer)
    {
        this.puzzleName = puzzleName;
        this.puzzleHint = puzzleHint;
        this.puzzleAnswer = puzzleAnswer;
    }

    //Converts values into string for display mainly
    @Override
    public String toString()
    {
        return System.lineSeparator() + puzzleName + System.lineSeparator() + System.lineSeparator() +
                "Puzzle Hint: " + puzzleHint + System.lineSeparator() + System.lineSeparator() +
                "Puzzle Answer: " + puzzleAnswer + System.lineSeparator();
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
}

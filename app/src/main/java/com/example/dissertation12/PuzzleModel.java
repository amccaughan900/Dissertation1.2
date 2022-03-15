package com.example.dissertation12;

public class PuzzleModel
{
    private String puzzleName;
    private String puzzleAnswer;

    public PuzzleModel(String puzzleName, String puzzleAnswer)
    {
        this.puzzleName = puzzleName;
        this.puzzleAnswer = puzzleAnswer;
    }

    //Converts values into string for display mainly
    @Override
    public String toString()
    {
        return System.lineSeparator() + puzzleName + System.lineSeparator() + System.lineSeparator() +
                "Puzzle Answer: " + puzzleAnswer + System.lineSeparator();
    }

    public String getPuzzleName() {
        return puzzleName;
    }

    public void setPuzzleName(String puzzleName) {
        this.puzzleName = puzzleName;
    }

    public String getPuzzleAnswer() {
        return puzzleAnswer;
    }

    public void setPuzzleAnswer(String puzzleAnswer) {
        this.puzzleAnswer = puzzleAnswer;
    }
}

package com.srishti.tictactoe.model;

public enum LevelEnum {

    EASY, MEDIUM, HARD;

    static public LevelEnum parseFromInt(int aLevel)
    {
        return (aLevel == 1) ? EASY : ((aLevel == 2) ? MEDIUM : HARD);
    }

    public boolean isEasy()
    {
        return (this == LevelEnum.EASY);
    }

    public boolean isMedium()
    {
        return (this == LevelEnum.MEDIUM);
    }

    public boolean isHard()
    {
        return (this == LevelEnum.HARD);
    }

}

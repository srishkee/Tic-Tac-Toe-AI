package com.srishti.tictactoe.model;

public enum StatusEnum
{
    EMPTY, USER, COMPUTER;

    public boolean isUser()
    {
        return (this == StatusEnum.USER);
    }

    public boolean isComputer()
    {
        return (this == StatusEnum.COMPUTER);
    }

    public boolean isEmpty()
    {
        return (this == StatusEnum.EMPTY);
    }

}

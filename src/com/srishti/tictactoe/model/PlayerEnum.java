package com.srishti.tictactoe.model;

public enum PlayerEnum {

    USER, COMPUTER;

    public boolean isUser()
    {
        return (this == PlayerEnum.USER);
    }

    public boolean isComputer()
    {
        return (this == PlayerEnum.COMPUTER);
    }

}

package com.example.dropthenumber.model;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

@Id("block")
public class Block {


    @Param(0)
    private int row;
    @Param(1)
    private int column;
    @Param(2)
    private int value;
    @Param(3)
    private int score;
    @Param(4)
    private String match;

    public Block(int r,int c,int v, int s , String m){
        this.row=r;
        this.column=c;
        this.value=v;
        this.score=s;
        this.match = m;
    }

    public Block() {
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getMatch() {
        return match;
    }

    public void setMatch(String match) {
        this.match = match;
    }
}


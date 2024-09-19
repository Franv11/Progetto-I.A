package com.example.dropthenumber.controller;

import com.example.dropthenumber.utils.randBlockValue;
import java.util.ArrayList;

import static com.example.dropthenumber.App.COL_SIZE;
import static com.example.dropthenumber.App.ROW_SIZE;

public class ArrayPossibleBlock {

    private ArrayList<int[]> newBlocks;

    public ArrayPossibleBlock(int[][] matrix) {
        newBlocks = new ArrayList<>();
        findPossibleNewBlock(matrix);
    }

    public ArrayList<int[]> findPossibleNewBlock(int[][] matrix)
    {

        int rand = randBlockValue.getRandom();

        for(int j = 0; j < COL_SIZE; j++){
            for(int i = ROW_SIZE - 1; i >= 0; i--)
            {
                if(matrix[i][j] == 0)
                {
                    int[] arr = {i , j , rand};
                    newBlocks.add(arr);
                    break;
                }
            }
        }
        return newBlocks;
    }

    public ArrayList<int[]> getNewBlocks() {
        return newBlocks;
    }

}

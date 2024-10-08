package com.example.dropthenumber.controller;

import java.util.*;

import static com.example.dropthenumber.App.COL_SIZE;
import static com.example.dropthenumber.App.ROW_SIZE;

public class MapPossibleBlockArrayMatrix {

    // Membri della classe
    private ArrayPossibleBlock possBlock;
    private HashMap<int[], ArrayList<int[][]>> map;
    private HashMap<int[], Integer> scoreForMove;
    private int partialSum = 0;


    // Costruttore
    public MapPossibleBlockArrayMatrix() {
        map = new HashMap<>();
        scoreForMove = new HashMap<>();
    }

    public void startClass(int[][] matrix) {
        possBlock = new ArrayPossibleBlock(matrix);
        initializeKey(matrix , possBlock);
        calculateFinalMatrix();
    }

    // Inizializza l'ArrayList con le prime due mosse iniziali e associa nella mappa per ogni mossa possibile la matrice iniziale
    private void initializeKey(int[][] matrix, ArrayPossibleBlock possBlock) {
        for (int[] pB : possBlock.getNewBlocks()) {
            ArrayList<int[][]> initialArray = new ArrayList<>();
            initialArray.add(makeACopyOfMatrix(matrix));
            initialArray.add(firstMove(pB, makeACopyOfMatrix(matrix)));
            map.put(pB, initialArray);
        }
    }

    // Calcola per ogni mossa le matrici successive
    private void calculateFinalMatrix() {
        for (int[] block : map.keySet()) {
            searchForMatch(block, block);

            ArrayList<int[][]> list = map.get(block);
            int[][] lastMatrix = list.get(list.size() - 1);

            for(int i = ROW_SIZE - 1; i >= 0; i--) {
                for (int j = COL_SIZE - 1; j >= 0; j--) {
                    if (lastMatrix[i][j] != 0) {
                        int[] matrixBlock = new int[]{i, j, lastMatrix[i][j]};
                        if(searchForMatch(block, matrixBlock)){
                            i = ROW_SIZE - 1;
                            j = COL_SIZE - 1;
                        }
                    }
                }
            }

            partialSum = 0;
        }

    }

    public int[][] returnFinalMatrix(int[] block) {
        for (int[] key : map.keySet()) {
            if (Arrays.equals(block, key)) {
                ArrayList<int[][]> list = map.get(key);
                return list.get(list.size() - 1);
            }
        }
        return null;
    }


    private boolean searchForMatch(int[] key, int[] block) {
        //Copy of Block
        int[] copyBlock = block.clone();

        boolean match_found = true;
        boolean match_done = false;

        while (match_found) {
            ArrayList<int[][]> list = map.get(key);
            int[][] lastMatrix = list.get(list.size() - 1);

            // MATCH BELOW
            if (copyBlock[0] + 1 < ROW_SIZE && copyBlock[2] == lastMatrix[copyBlock[0] + 1][copyBlock[1]]) {
                updateMatrix("lower", key, copyBlock, lastMatrix);
                match_done = true;
                partialSum += copyBlock[2];
                continue;
            }
            // MATCH SX
            if (copyBlock[1] - 1 >= 0 && copyBlock[2] == lastMatrix[copyBlock[0]][copyBlock[1] - 1]) {
                updateMatrix("sx", key, copyBlock, lastMatrix);
                match_done = true;
                partialSum += copyBlock[2];
                continue;
            }

            // MATCH DX
            if (copyBlock[1] + 1 < COL_SIZE && copyBlock[2] == lastMatrix[copyBlock[0]][copyBlock[1] + 1]) {
                updateMatrix("dx", key, copyBlock, lastMatrix);
                match_done = true;
                partialSum += copyBlock[2];
                continue;
            }

            match_found = false;
        }

        scoreForMove.put(key,partialSum);

        return match_done;
    }

    private void updateMatrix(String typeOfMatch, int[] key, int[] copyBlock, int[][] matrix) {
        switch (typeOfMatch) {

            case "lower":

                int[][] copyLowerMatrix = makeACopyOfMatrix(matrix);
                copyLowerMatrix[copyBlock[0] + 1][copyBlock[1]] = copyBlock[2] * 2;
                copyLowerMatrix[copyBlock[0]][copyBlock[1]] = 0;
                addElementAtTheMatrix(key, copyLowerMatrix);

                //Update copyBlock for future match
                copyBlock[0] += 1;
                copyBlock[2] *= 2;
                break;

            case "sx":

                int[][] copySxMatrix = makeACopyOfMatrix(matrix);
                copySxMatrix[copyBlock[0]][copyBlock[1]] = copyBlock[2] * 2;
                copySxMatrix[copyBlock[0]][copyBlock[1] - 1] = 0;

                if (copyBlock[0] != 0 && copySxMatrix[copyBlock[0] - 1][copyBlock[1] - 1] != 0) {
                    for (int i = copyBlock[0] - 1; i >= 0; i--) {
                        copySxMatrix[i + 1][copyBlock[1] - 1] = copySxMatrix[i][copyBlock[1] - 1];
                        if (copySxMatrix[i][copyBlock[1] - 1] == 0) {
                            break;
                        }
                    }
                }

                addElementAtTheMatrix(key, copySxMatrix);

                //Update copyBlock for future match
                copyBlock[2] *= 2;

                break;

            case "dx":

                int[][] copyDxMatrix = makeACopyOfMatrix(matrix);
                copyDxMatrix[copyBlock[0]][copyBlock[1]] = copyBlock[2] * 2;
                copyDxMatrix[copyBlock[0]][copyBlock[1] + 1] = 0;

                if (copyBlock[0] != 0 && copyDxMatrix[copyBlock[0] - 1][copyBlock[1] + 1] != 0) {
                    for (int i = copyBlock[0] - 1; i >=     0; i--) {
                        copyDxMatrix[i + 1][copyBlock[1] + 1] = copyDxMatrix[i][copyBlock[1] + 1];
                        if (copyDxMatrix[i][copyBlock[1] + 1] == 0) {
                            break;
                        }
                    }
                }

                addElementAtTheMatrix(key, copyDxMatrix);

                //Update copyBlock for future match
                copyBlock[2] *= 2;

                break;
        }
    }


    public Set<int[]> getPossibleBlock() {
        return map.keySet();
    }


    public int getPossibleBlockAndScore(int[] pB)
    {
        return  getScoreForPossibleBlock(pB);
    }

    private int getScoreForPossibleBlock(int [] block)
    {
        ArrayList<int[][]> list = map.get(block);
        int[][] lastMatrix = list.get(list.size() - 1);
        return (int) Arrays.stream(lastMatrix).flatMapToInt(Arrays::stream).filter(num -> num != 0).count();
    }


    private void addElementAtTheMatrix(int[] key, int[][] matrix) {
        ArrayList<int[][]> arrayOfMatrix = map.get(key);
        arrayOfMatrix.add(matrix);
    }


    private int[][] firstMove(int[] key, int[][] matrix) {
        matrix[key[0]][key[1]] = key[2];
        return matrix;
    }


    public void printMap() {
        System.out.println();
        for (Map.Entry<int[], ArrayList<int[][]>> entry : map.entrySet()) {

            System.out.println("Key: " + Arrays.toString(entry.getKey()));
            System.out.println("Value: ");
            for (int[][] array : entry.getValue()) {
                System.out.println(Arrays.deepToString(array));
            }
        }
        System.out.println();
    }

    public int getScoreForBlockChosen(int[] block) {
        for(int [] key : scoreForMove.keySet()) {
            if(Arrays.equals(block, key)){
                return scoreForMove.get(key);
            }
        }
        return 0;
    }

    public ArrayList<int[][]> getArrayOfMatrixForABlock(int[] block) {
        for (int[] key : map.keySet()) {
            if (Arrays.equals(block, key)) {
                 return map.get(key);
            }
        }
        return null;
    }


    public String findIfMatch(int[] key) {
        ArrayList<int[][]> arrayOfMatrix = map.get(key);
        return arrayOfMatrix.size() > 2 ? "true" : "false";
    }


    private int[][] makeACopyOfMatrix(int[][] original) {
        assert original != null;

        int[][] copy = new int[original.length][];
        for (int i = 0; i < original.length; i++) {
            copy[i] = original[i].clone();
        }

        return copy;
    }


    public void clearDataStructure() {
        map.clear();
        scoreForMove.clear();
    }

}

package com.example.dropthenumber.utils;

import java.util.ArrayList;
import java.util.Random;

import static com.example.dropthenumber.App.COL_SIZE;
import static com.example.dropthenumber.App.ROW_SIZE;

public class randBlockValue {

    // Array da cui estrae casualmente il valore per il blocco successivo
    static ArrayList<Integer> list = new ArrayList<Integer>(){{add(2);}};

    static Random rand;

    public randBlockValue() {
        list = new ArrayList<Integer>(){{add(2);}};
        rand = new Random();
    }

    // Funzione responsabile per la scelta del rand
    public static int getRandom() {
        if (list.size() < 5) {
            //stampaArray(list, -1, -1);
            return list.get(rand.nextInt(0, list.size()));

        } else {

            double first_bound = Math.ceil(0.5 * list.size());
            double second_bound = Math.ceil(0.8 * list.size());

            //tampaArray(list, first_bound, second_bound);

            float value = rand.nextFloat(0, 1);

            if (value < 0.75) {
                int index = rand.nextInt(0, (int) first_bound);
                return list.get(index);

            } else if (value < 0.95) {
                int index = rand.nextInt((int) first_bound, (int) second_bound);

                return list.get(index);
            } else {
                int index = rand.nextInt((int) second_bound, list.size());
                return list.get(index);
            }
        }
    }


    public static void stampaArray(ArrayList<Integer> list, double first_bound, double second_bound) {
        System.out.print("[ ");
        for (int i = 0; i < list.size(); i++) {
            System.out.print(list.get(i) + " ");
            if (i == first_bound - 1 || i == second_bound - 1) {
                System.out.print("| ");
            }
        }
        System.out.println("]");
    }

    public void addValueToArray(int[][] matrix)
    {
        for(int i = ROW_SIZE - 1; i >= 0; i--) {
            for (int j = COL_SIZE - 1; j >= 0; j--) {
                if(matrix[i][j] != 0 && matrix[i][j] <= 256 && !checkIfAlredyAdded(matrix[i][j])) {
                    list.add(matrix[i][j]);
                }
            }
        }
    }

    private boolean checkIfAlredyAdded(int value) {
        return list.contains(value);
    }

}


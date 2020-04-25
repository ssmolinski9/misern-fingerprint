package com.misern.fingerprint.algorithms;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;

public class KMM {
    int [] valueMask = calculateValueMask();
    HashMap numbersToDelete = (HashMap) countDeleteNumbers();
    int [][] previousArray;

    public void calculate(BufferedImage img){
        int width = img.getWidth();
        int height = img.getHeight();
        int array[][] = new int[width][height];
        previousArray = new int[width][height];
        for(int i = 0; i < width; i++){
            for(int j = 0; j < height; j++){
                array[i][j] = 0;
            }
        }

        for(int i = 1; i < width-1; i++){
            for(int j = 1; j < height-1; j++){
                Color c = new Color(img.getRGB(i,j));
                if(c.equals(Color.BLACK)){
                    array[i][j] = 1;
                }
            }
        }

        boolean ifChanged;

        do{
            ifChanged = false;
            for(int i = 1; i < width-1; i++){
                for(int j = 1; j < height-1; j++){
                    if((array[i][j] == 1 && ((array[i + 1][j] == 0) || (array[i - 1][j] == 0) || (array[i][j + 1] == 0) || (array[i][j - 1] == 0)))){
                        array[i][j] = 2;
                    }
                }
            }

            for(int i = 1; i < width-1; i++){
                for(int j = 1; j < height-1; j++){
                    if((array[i][j] == 1 && ((array[i + 1][j + 1] == 0) || (array[i - 1][j - 1] == 0) || (array[i - 1][j + 1] == 0) || (array[i + 1][j - 1] == 0)))){
                        array[i][j] = 3;
                    }
                }
            }

            for(int i = 1; i < width-1; i++){
                for(int j = 1; j < height-1; j++){
                    if(array[i][j] == 2 && checkIfCorner(array,i,j)){
                        array[i][j] = 4;
                    }
                }
            }

            for(int i = 1; i < width; i++){
                for(int j = 1; j < height; j++){
                    if(array[i][j] == 4){
                        ifChanged = true;
                        array[i][j] = 0;
                        img.setRGB(i,j,Color.WHITE.getRGB());
                    }
                }
            }

            for(int i = 1; i < width-1; i++){
                for(int j = 1; j < height-1; j++){
                    if(array[i][j] == 2){
                        if(calculateValueForDelete(array, i, j)){
                            ifChanged = true;
                            array[i][j] = 0;
                            img.setRGB(i,j,Color.WHITE.getRGB());
                        }else{
                            array[i][j] = 1;
                        }
                    }
                }
            }

            for(int i = 1; i < width-1; i++){
                for(int j = 1; j < height-1; j++){
                    if(array[i][j] == 3){
                        if(calculateValueForDelete(array, i, j)){
                            img.setRGB(i,j,Color.WHITE.getRGB());
                            ifChanged = true;
                            array[i][j] = 0;
                        }else{
                            array[i][j] = 1;
                        }
                    }
                }
            }


        } while(ifChanged);
    }

    private boolean checkIfCorner(int [][] array, int i, int j){
        List<Integer> checkList = new ArrayList<>();
        checkList.add(array[i][j-1]);
        checkList.add(array[i+1][j-1]);
        checkList.add(array[i+1][j]);
        checkList.add(array[i+1][j+1]);
        checkList.add(array[i][j+1]);
        checkList.add(array[i-1][j+1]);
        checkList.add(array[i-1][j]);
        checkList.add(array[i-1][j-1]);
        int valueWithPixels;
        int valueWithoutPixels;
        for(int a = 0; a < 8; a++){
            valueWithPixels = 0;
            valueWithoutPixels = 0;
            if(checkList.get(a) != 0) valueWithPixels++;
            if(checkList.get((a+1)%8) != 0) valueWithPixels++;
            for(int b = a+2; b < a+8; b++){
                if(checkList.get(b%8) != 0) valueWithoutPixels++;
            }
            if(valueWithPixels == 2 && valueWithoutPixels == 6) return true;
        }
        for(int a = 0; a < 8; a++){
            valueWithPixels = 0;
            valueWithoutPixels = 0;
            if(checkList.get(a) != 0) valueWithPixels++;
            if(checkList.get((a+1)%8) != 0) valueWithPixels++;
            if(checkList.get((a+2)%8) != 0) valueWithPixels++;
            for(int b = a+3; b < a+8; b++){
                if(checkList.get(b%8) != 0) valueWithoutPixels++;
            }
            if(valueWithPixels == 3 && valueWithoutPixels == 5) return true;
        }
        for(int a = 0; a < 8; a++){
            valueWithPixels = 0;
            valueWithoutPixels = 0;
            if(checkList.get(a) != 0) valueWithPixels++;
            if(checkList.get((a+1)%8) != 0) valueWithPixels++;
            if(checkList.get((a+2)%8) != 0) valueWithPixels++;
            if(checkList.get((a+3)%8) != 0) valueWithPixels++;
            for(int b = a+4; b < a+8; b++){
                if(checkList.get(b%8) != 0) valueWithoutPixels++;
            }
            if(valueWithPixels == 4 && valueWithoutPixels == 4) return true;
        }
        return false;
    }

    private boolean calculateValueForDelete(int [][]array, int i, int j){
        int value = 0;
        if(array[i][j-1] != 0) value += valueMask[0];
        if(array[i+1][j-1] != 0) value += valueMask[1];
        if(array[i+1][j] != 0) value += valueMask[2];
        if(array[i+1][j+1] != 0) value += valueMask[3];
        if(array[i][j+1] != 0) value += valueMask[4];
        if(array[i-1][j+1] != 0) value += valueMask[5];
        if(array[i-1][j] != 0) value += valueMask[6];
        if(array[i-1][j-1] != 0) value += valueMask[7];
        if(numbersToDelete.containsKey(value)){
            return true;
        }
        return false;
    }

    private Map countDeleteNumbers(){
        HashMap<Integer,Integer> numbers = new HashMap();
        Integer[] array = new Integer[8];
        for(int i = 0; i < 8; i++){
            array[i] = (int)Math.pow(2,i);
        }
        Set<Integer> set = new HashSet<>();
        set.addAll(Arrays.asList(3,5 ,7,12,13,14,15,20,
                21, 	22,     23,     28,     29,     30,     31,     48,
                52,     53,     54,     55,     56,     60,     61,     62,
                63,     65,     67,     69,     71,     77,     79,     80,
                81,     83,     84,     85,     86,     87,     88,     89,
                91,     92,     93,     94,     95,     97,     99,     101,
                103,    109,    111,    112,    113,    115,    116,    117,
                118,    119,    120,    121,    123,    124,    125,    126,
                127,    131,    133,    135,    141,    143,    149,    151,
                157,    159,    181,    183,    189,    191,    192,    193,
                195,    197,    199,    205,    207,    208,    209,    211,
                212,    213,    214,    215,    216,    217,    219,    220,
                221,    222,    223,    224,    225,    227,    229,    231,
                237,    239,    240,    241,    243,    244,    245,    246,
                247,    248,    249,    251,    252,    253,    254,    255));
        set.forEach(number -> numbers.put(number, 1));
        return numbers;
    }

    private int[] calculateValueMask() {

        int [] valueMask = new int[8];
        for(int i = 0; i < 8; i++){
            valueMask[i] = (int) Math.pow(2,i);
        }
        return valueMask;
    }
}

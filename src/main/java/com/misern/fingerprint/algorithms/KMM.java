package com.misern.fingerprint.algorithms;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;

public class KMM {
    int [] valueMask = calculateValueMask();
    HashMap numbersToDelete = (HashMap) countDeleteNumbers();

    private int[] calculateValueMask() {

        int [] valueMask = new int[8];
        for(int i = 0; i < 8; i++){
            valueMask[i] = (int) Math.pow(2,i);
        }
        return valueMask;
    }

    public BufferedImage calculate(BufferedImage img){
        int width = img.getWidth();
        int height = img.getHeight();
        int array[][] = new int[width][height];

        for(int i = 0; i < width; i++){
            for(int j = 0; j < height; j++){
                array[i][j] = 0;
            }
        }

        for(int i = 0; i < width; i++){
            for(int j = 0; j < height; j++){
                Color c = new Color(img.getRGB(i,j));
                if(c.equals(Color.BLACK)){
                    array[i][j] = 1;
                }
            }
        }
        do{
            for(int i = 1; i < width; i++){
                for(int j = 1; j < height; j++){
                    if((array[i][j] == 1 && ((array[i + 1][j] == 0) || (array[i - 1][j] == 0) || (array[i][j + 1] == 0) || (array[i][j - 1] == 0)))){
                        array[i][j] = 2;
                    }
                }
            }

            for(int i = 1; i < width; i++){
                for(int j = 1; j < height; j++){
                    if((array[i][j] == 1 && ((array[i + 1][j + 1] == 0) || (array[i - 1][j - 1] == 0) || (array[i - 1][j + 1] == 0) || (array[i + 1][j - 1] == 0)))){
                        array[i][j] = 3;
                    }
                }
            }

            for(int i = 1; i < width; i++){
                for(int j = 1; j < height; j++){
                    if(array[i][j] == 2 && checkIfCorner(array,i,j)){
                        array[i][j] = 4;
                    }
                }
            }



            for(int i = 1; i < width; i++){
                for(int j = 1; j < height; j++){
                    if(array[i][j] == 4){
                        array[i][j] = 0;
                        img.setRGB(i,j,Color.WHITE.getRGB());
                    }
                }
            }

            for(int i = 1; i < width; i++){
                for(int j = 1; j < height; j++){
                    if(array[i][j] == 2){
                        if(calculateValueForDelete(array, i, j)){
                            array[i][j] = 0;
                            img.setRGB(i,j,Color.WHITE.getRGB());
                        }else{
                            array[i][j] = 1;
                        }
                    }
                }
            }

            for(int i = 1; i < height; i++){
                for(int j = 1; j < width; j++){
                    if(array[j][i] == 3){
                        if(calculateValueForDelete(array, j, i)){
                            array[j][i] = 0;
                            img.setRGB(j,i,Color.WHITE.getRGB());
                        }else{
                            array[j][i] = 1;
                        }
                    }
                }
            }
        } while(!checkIfDone(array,width,height));

        for(int i = 1; i < height; i++){
            for(int j = 1; j < width; j++){
                if(array [j][i] == 0) System.out.print(' ');
                else{
                    System.out.print(array[j][i]);
                }
            }System.out.println();
        }

        return null;
    }


    private int countCorners(int [][] array, int i, int j){
        int result = 0;
        if(array[i+1][j+1] != 0) result++;
        if(array[i-1][j+1] != 0) result++;
        if(array[i+1][j-1] != 0) result++;
        if(array[i-1][j-1] != 0) result++;
        return result;
    }

    private int countVertical(int [][] array, int i, int j){
        int result = 0;
        if(array[i][j+1] != 0) result++;
        if(array[i][j-1] != 0) result++;
        return result;
    }

    private int countHorizontal(int [][] array, int i, int j){
        int result = 0;
        if(array[i+1][j] != 0) result++;
        if(array[i-1][j] != 0) result++;
        return result;
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
        int valueWithPixels = 0;
        int valueWithoutPixels = 0;
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

    private int countCross(int [][] array, int i, int j){
        int result = 0;
        if(array[i][j+1] != 0) result++;
        if(array[i-1][j] != 0) result++;
        if(array[i][j-1] != 0) result++;
        if(array[i+1][j] != 0) result++;
        return result;
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
    /*for(int i = 0; i < 7; i++){
        numbers.put(array[i] + array[i+1],1);
        for(int j = i+2; j < 7; j++){
            numbers.put(array[i] + array[i+1] + array[j], 1);
            for(int k = j+1; k < 7; k++){
                numbers.put(array[i] + array[i+1] + array[j] + array[k], 1);
                for(int l = k+1; l < 7; l++){
                    numbers.put(array[i] + array[i+1] + array[j] + array[k] + array[l], 1);
                    for(int m = l+1; m < 7; m++){
                        numbers.put(array[i] + array[i+1] + array[j] + array[k] + array[l] + array[m], 1);
                        for(int n = m+1; n < 7; n++){
                            numbers.put(array[i] + array[i+1] + array[j] + array[k] + array[l] + array[m] + array[n], 1);
                        }
                    }
                }
            }
        }
    }*/
    /*int value = 0;
    for(int i = 0; i < 6; i++){
        value = 0;
        value = array[i] + array[i+1] + array[i+2];
        numbers.put(value,1);
        for(int j = i+3; j < 8; j++){
            numbers.put(value + array[j],1);
            for(int k = j+1; k < 8; k++){
                numbers.put(value + array[j] + array[k],1);
                for(int l = k+1; l < 8; l++){
                    numbers.put(value + array[j] + array[k] + array[l],1);
                    for(int m = l+1; m < 8; m++){
                        numbers.put(value + array[j] + array[k] + array[l] + array[m],1);
                    }
                }
            }
        }
    }
    for(int a = 0; a < 8; a+=2) {
        value = array[a] + array[a+1];
        for(int i = 0; i < 6; i++){
            value = 0;
            value = array[i] + array[i + 1] + array[i + 2];
            numbers.put(value, 1);
            for(int j = i + 3; j < 8; j++){
                numbers.put(value + array[j], 1);
                for(int k = j + 1; k < 8; k++){
                    numbers.put(value + array[j] + array[k], 1);
                    for(int l = k + 1; l < 8; l++){
                        numbers.put(value + array[j] + array[k] + array[l], 1);
                        for(int m = l + 1; m < 8; m++){
                            numbers.put(value + array[j] + array[k] + array[l] + array[m], 1);
                        }
                    }
                }
            }
        }
    }*/
    }

    private boolean checkIfDone(int [][]array, int width, int height){
        for(int i = 1; i < width; i++){
            for(int j = 1; j < height; j++){
                if(array[i][j] != 0){
                    if(countCross(array,i,j) > 2){
                        System.out.println(i + " " + j + " " + countCross(array,i,j) + " " + countCorners(array,i,j));
                        return false;
                    }
                }
            }
        }
        return true;
    }
}

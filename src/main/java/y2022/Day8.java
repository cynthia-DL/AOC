package y2022;

import all.Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;

record MyMap(int[][] array){
    public static MyMap fromList(ArrayList<String> input){
        int size = input.size();
        var array = new int[size][size];

        for (int i = 0; i < size; i++){
            var line = input.get(i);
            for (int j = 0; j < size; j++){
                array[i][j] = Integer.parseInt(((Character) line.charAt(j)).toString());
            }
        }
        return new MyMap(array);
    }

    public int size() {
        return array.length;
    }

    private int getValue(int x, int y){
        return array[x][y];
    }

    public boolean estVisible(int x, int y){
        if (x == 0 || y == 0 ||x == size()-1 || y == size()-1) {
            return true;
        }

        boolean cacheGauche = false;
        boolean cacheDroite = false;
        boolean cacheHaut = false;
        boolean cacheBas = false;

        //de gauche à droite
        for (int i = 0; i < x && !cacheGauche; i++){
            if (getValue(i, y) >= getValue(x, y)) {
                cacheGauche = true;
            }
        }

        //de droite à gauche
        for (int i = size()-1; i > x && !cacheDroite; i--){
            if (getValue(i, y) >= getValue(x, y)) {
                cacheDroite = true;
            }
        }

        //de gauche à droite
        for (int i = 0; i < y && !cacheHaut; i++){
            if (getValue(x, i) >= getValue(x, y)) {
                cacheHaut = true;
            }
        }

        //de droite à gauche
        for (int i = size()-1; i > y && !cacheBas; i--){
            if (getValue(x, i) >= getValue(x, y)) {
                cacheBas = true;
            }
        }

        return !(cacheGauche && cacheDroite && cacheHaut && cacheBas);
    }

    public int scenicScore(int x, int y){
        int vueGauche = 0;
        int vueDroite = 0;
        int vueHaut = 0;
        int vueBas = 0;
        int i;

        for (i = x-1; i > 0 ; i--){
            if(getValue(i, y) < getValue(x, y)){
                vueGauche++;
            } else {
                i = -1;
            }
        }

        for (i = x+1; i < size(); i++){
            if(getValue(i, y) < getValue(x, y)) {
                vueDroite++;
            } else {
                i = size();
            }
        }

        for (i = y-1; i > 0; i--){
            if(getValue(x, i) < getValue(x, y)){
                vueHaut++;
            } else {
                i = -1;
            }
        }

        for (i = y+1; i < size(); i++){
            if(getValue(x, i) < getValue(x, y)){
                vueBas++;
            } else {
                i = size();
            }
        }
        System.out.println(vueGauche+" * "+vueDroite+" * "+vueHaut+" * "+vueBas);
        return vueGauche * vueDroite * vueHaut * vueBas;
    }

    public String toString(){
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < size(); i++) {
            for (int j = 0; j < size(); j++) {
                str.append(getValue(i, j));
            }
            str.append("\n");
        }

        return str.toString();
    }
}

public class Day8 {
    public static void main(String[] args) {
        var lignes = Utils.listFromFile("./inputs/2022/day8_input.txt");
        var map = MyMap.fromList(lignes);

        int cmp = 0;
        for (int i = 0; i < map.size(); i++) {
            for (int j = 0; j < map.size(); j++) {
                cmp += map.estVisible(i, j) ? 1 : 0;
            }
        }

        System.out.println("cmp = " + cmp);

        int bestView = 0;
        for (int i = 0; i < map.size(); i++) {
            for (int j = 0; j < map.size(); j++) {
                bestView = Math.max(bestView, map.scenicScore(i, j));
            }
        }

        System.out.println("bestView = " + bestView);

    }
}

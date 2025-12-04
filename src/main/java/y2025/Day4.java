package y2025;

import all.Utils;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

class Grid {
    private final List<List<Boolean>> internal;
    private final int height;
    private final int width;

    public Grid (int height, int width){
        this.height = height;
        this.width = width;
        this.internal = new ArrayList<>();

        for (int i = 0; i < this.height; i++){
            ArrayList<Boolean> temp = new ArrayList<>();
            for (int j = 0; j < this.width; j++){
                temp.add(false);
            }
            internal.add(temp);
        }
    }

    public static Grid fill(List<String> input){
        Grid grid = new Grid(input.size(), input.get(0).length());
        
        for (int i = 0; i < grid.height; i++){
            var line = input.get(i);
            for (int j = 0; j < grid.width; j++){
                if (line.charAt(j) == '@') {
                    grid.setCase(i, j, true);
                }
            }
        }
        
        return grid;
    }

    private boolean isInGrid(int line, int column){
        if(line < 0 || line >= width){
            return false;
        }

        if(column < 0 || column >= height){
            return false;
        }

        return true;
    }

    public void setCase(int line, int column, boolean hasPaper){
        if (!isInGrid(line, column)) return;

        this.internal.get(line).set(column, hasPaper);

    }

    private boolean hasPaper(int line, int column){
        if (!isInGrid(line, column)) return false;

        return internal.get(line).get(column);
    }

    public boolean isAccessible(int line, int column){
        if(!hasPaper(line, column)) return false;

        int surounding = 0;
        for (int i = -1; i < 2; i++ ){
            for (int j = -1; j < 2; j++){
                surounding = surounding + (hasPaper(line+i, column+j) ? 1 : 0);
            }
        }

        return surounding <= 4;
    }

    public int height() {
        return height;
    }

    public int width() {
        return width;
    }
}

public class Day4 {
    public static void main(String[] args) {
        //List<String> inputs = Utils.listFromDemoFile();
        List<String> inputs = Utils.listForDay(2025, 4);
        var grid = Grid.fill(inputs);

        BigInteger sum = BigInteger.ZERO;
        for (int i = 0; i < grid.height(); i++){
            for (int j = 0; j < grid.width(); j++){
                if (grid.isAccessible(i, j)){
                    sum = sum.add(BigInteger.ONE);
                    System.out.printf("X");
                } else System.out.printf(".");
            }
            System.out.printf("\n");
        }

        System.out.println("sum = " + sum);

    }
}

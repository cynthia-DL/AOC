package y2025;

import all.AbstractGrid;
import all.Utils;
import kotlin.Pair;

import java.awt.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class Grid extends AbstractGrid<Boolean> {
    public Grid (int height, int width){
        super(height, width);
        for (int i = 0; i < this.height(); i++){
            for (int j = 0; j < this.width(); j++){
                internal().get(i).add(j, false);
            }
        }
    }

    @Override
    public Boolean get(int line, int column){
        Boolean value = super.get(line, column);
        return value != null && value;
    }

    public static Grid fill(List<String> input){
        Grid grid = new Grid(input.size(), input.get(0).length());
        
        for (int i = 0; i < grid.height(); i++){
            var line = input.get(i);
            for (int j = 0; j < grid.width(); j++){
                if (line.charAt(j) == '@') {
                    grid.set(i, j, true);
                }
            }
        }

        return grid;
    }

    public boolean isAccessible(int line, int column){
        if(!get(line, column)) return false;

        int surounding = 0;
        for (int i = -1; i < 2; i++ ){
            for (int j = -1; j < 2; j++){
                surounding = surounding + (get(line+i, column+j) ? 1 : 0);
            }
        }

        return surounding <= 4;
    }

    public boolean hasAccessible(){
        for (int i = 0; i < height(); i++){
            for (int j = 0; j < width(); j++){
                if (isAccessible(i, j)) return true;
            }
        }

        return false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < height(); i++){
            for (int j = 0; j < width(); j++){
                if (isAccessible(i, j)) sb.append('x');
                else if (get(i, j)) sb.append('@');
                else sb.append('.');
            }
            sb.append('\n');
        }

        return sb.toString();
    }
}

public class Day4 {
    public static int part1(List<String> inputs){
        var grid = Grid.fill(inputs);

        List<Pair<Integer, Integer>> accessible = new ArrayList<>();
        for (int i = 0; i < grid.height(); i++){
            for (int j = 0; j < grid.width(); j++){
                if (grid.isAccessible(i, j)){
                    accessible.add(new Pair<>(i, j));
                }
            }
        }

        return accessible.size();        
    }

    public static BigInteger part2(List<String> inputs){
        var grid = Grid.fill(inputs);
        List<Pair<Integer, Integer>> accessible = new ArrayList<>();
        BigInteger removed = BigInteger.ZERO;

        while (grid.hasAccessible()) {
            for (int i = 0; i < grid.height(); i++){
                for (int j = 0; j < grid.width(); j++){
                    if (grid.isAccessible(i, j)){
                        accessible.add(new Pair<>(i, j));
                    }
                }
            }

            removed = removed.add(new BigInteger(String.valueOf(accessible.size())));
            for (var pair : accessible){
                grid.remove(pair.component1(), pair.component2());
            }

            accessible = new ArrayList<>();

        }

        return removed;
    }

    
    public static void main(String[] args) {
        List<String> inputs = Utils.listForDay(2025, 4);

        System.out.println(" part1(inputs) = " +  part1(inputs));
        System.out.println(" part2(inputs) = " +  part2(inputs));
    }
}

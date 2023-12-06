package y2022;

import all.Utils;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;


record TreeMap(int[][] grid, boolean[][] visible){

    private static boolean isVisible(int[][] grid, int line, int column){
        Objects.checkIndex(line, grid.length);
        Objects.checkIndex(column, grid[0].length);

        if (line == 0 || line == grid.length -1 || column == 0 || column == grid[0].length -1){
            return true;
        }
        var currentValue = grid[line][column];

        boolean flag = true;

        //check if visible from top
        for (int i = 0; i < line; i++){
            if (grid[i][column] >= currentValue) {
                flag = false;
                break;
            }
        }

        if (flag) return true;
        flag = true;

        //check if visible from bottom
        for (int i = grid.length-1; i > line; i--){
            if (grid[i][column] >= currentValue) {
                flag = false;
                break;
            }
        }

        if (flag) return true;
        flag = true;

        //check if visible from left
        for (int i = 0; i < column; i++){
            if (grid[line][i] >= currentValue) {
                flag = false;
                break;
            }
        }

        if (flag) return true;
        flag = true;

        //check if visible from right
        for (int i = grid[0].length-1; i > column; i--){
            if (grid[line][i] >= currentValue) {
                flag = false;
                break;
            }
        }

        return flag;
    }
    static TreeMap fromLines(List<String> lines){
        var lineSize = lines.size();
        var columnSize = lines.get(0).length();

        var grid = new int[lineSize][columnSize];
        var visible = new boolean[lineSize][columnSize];

        for (int i = 0; i < lines.size(); i++){
            var line = lines.get(i);
            for (int j = 0; j < line.length(); j++){
                grid[i][j] = Integer.parseInt(Character.toString(line.charAt(j)));
            }
        }

        for (int i = 0; i < grid.length; i++){
            for (int j = 0; j < grid[i].length; j++){
                visible[i][j] = isVisible(grid, i, j);
            }
        }

        return new TreeMap(grid, visible);
    }

    int countVisible(){
        int count = 0;
        for (var line : visible){
            for (var cel : line){
                count += cel ? 1 : 0;
            }
        }

        return count;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();

        for (var line : visible){
            sb.append(Arrays.toString(line)).append('\n');
        }

        return sb.toString();
    }

    int bestScenicScore(){
        int scenicScore = 1;
        for (int line = 0; line < grid.length; line++){
            for (int column = 0; column < grid[line].length; column++){
                if(visible[line][column]){
                    int currentValue = grid[line][column];
                    int currentScore = 1;
                    int toAdd = 0;

                    //check if visible from top
                    for (int i = 0; i < line; i++){
                        if (grid[line-i][column] >= currentValue) {
                            break;
                        }
                        toAdd++;
                    }

                    currentScore *= toAdd;
                    toAdd =0;

                    //check if visible from bottom
                    for (int i = grid.length-1; i > line; i--){
                        if (grid[line+i][column] >= currentValue) {
                            break;
                        }
                        toAdd++;
                    }

                    currentScore *= toAdd;
                    toAdd =0;

                    //check if visible from left
                    for (int i = 0; i < column; i++){
                        if (grid[line][column-i] >= currentValue) {
                            break;
                        }
                        toAdd++;
                    }

                    currentScore *= toAdd;
                    toAdd =0;

                    //check if visible from right
                    for (int i = grid[0].length-1; i > column; i--){
                        if (grid[line][column+i] >= currentValue) {
                            break;
                        }
                        toAdd++;
                    }

                    currentScore *= toAdd;
                    scenicScore = Math.max(scenicScore, currentScore);
                    
                }
            }
        }

        return scenicScore;
    }
}
public class Day8 {
    public static void main(String[] args) {
        var lines = Utils.listFromFile("inputs/demo.txt");
        //var lines = Utils.listFromFile("inputs/y2022/day8_input.txt");

        var treeMap = TreeMap.fromLines(lines);

        //System.out.println(treeMap);

        var result_p1 = treeMap.countVisible();
        var result_p2 = treeMap.bestScenicScore();
        
        System.out.println("result_p1 = " + result_p1);
        System.out.println("result_p2 = " + result_p2);
    }
}

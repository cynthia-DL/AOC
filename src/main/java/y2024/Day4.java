package y2024;

import all.Utils;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Day4 {
    private static boolean IS_DEBUG = false;

    public static void printArray(ArrayList<String> grid, Point origin, Point... toHighlight){
        if (!IS_DEBUG){
            return;
        }
        System.out.println((char)27 + "[36m"+" ______"+origin+"________________ "+(char)27 + "[39m");
        var asList = Arrays.stream(toHighlight).toList();
        for (int i = 0; i < grid.size(); i++){
            var line = grid.get(i);
            for (int j = 0; j < line.length(); j++){
                if (origin.equals(new Point(i, j))){
                    System.out.print((char)27 + "[42m"+(char)27 + "[30m"+ line.charAt(j) +(char)27 + "[49m"+(char)27 + "[39m");
                }
                else if (asList.contains(new Point(i, j))) {
                    System.out.print((char)27 + "[41m"+ line.charAt(j) +(char)27 + "[49m");
                } else {
                    System.out.print(line.charAt(j));
                }
            }
            System.out.print("\n");
        }
    }

    public static char fromGrid(ArrayList<String> grid, int x, int y){
        return grid.get(x).charAt(y);
    }

    public static int correctInBound(int current, int max){
        if (current < 0 || current >= max){
            return -1;
        }

        return current;
    }

    private static int step(int coordinate, int step){
        return step >= 0 ?
                coordinate + step :
                -((Math.abs(step)) - Math.abs(coordinate));
    }

    private static boolean isMAS(char m, char a, char s){
        return m == 'M' && a == 'A' && s == 'S';
    }

    private static boolean isInGrid(ArrayList<String> grid, int x, int y, int stepX, int stepY){
        if(x < 0){
            return false;
        }
        var line = grid.get(y);

        return correctInBound(step(x, stepX), line.length()) != -1 &&
                correctInBound(step(step(x, stepX), stepX), line.length()) != -1 &&
                correctInBound(step(step(step(x, stepX), stepX), stepX), line.length()) != -1 &&
                correctInBound(step(y, stepY), grid.size()) != -1 &&
                correctInBound(step(step(y, stepY), stepY),  grid.size()) != -1 &&
                correctInBound(step(step(step(y, stepY), stepY), stepY), grid.size()) != -1;
    }
    
    private static long processPart1(ArrayList<String> grid){
        long result = 0;

        for (int ligne = 0; ligne < grid.size(); ligne++){
            for (int column = 0; column < grid.get(ligne).length(); column++){
                var car = grid.get(ligne).charAt(column);

                if (car != 'X'){
                    continue;
                }

                //Check in same line forward
                if (isInGrid(grid, ligne, column, 0, 1)){
                    printArray(grid, new Point(ligne, column), new Point(ligne, column+1), new Point(ligne, column+2), new Point(ligne, column+3));
                    result+= isMAS(fromGrid(grid, ligne, column+1), fromGrid(grid, ligne, column+2), fromGrid(grid, ligne, column+3))
                    ? 1 : 0;
                }

                //Check in same line backward
                if (isInGrid(grid, ligne, column, 0, -1)){
                    printArray(grid, new Point(ligne, column), new Point(ligne, column-1), new Point(ligne, column-2), new Point(ligne, column-3));
                    result+= isMAS(fromGrid(grid, ligne, column-1), fromGrid(grid, ligne, column-2), fromGrid(grid, ligne, column-3))
                    ? 1 : 0;
                }

                //check column
                if (isInGrid(grid, ligne, column, 1, 0)){
                    printArray(grid, new Point(ligne, column), new Point(ligne+1, column), new Point(ligne+2, column), new Point(ligne+3, column));
                    result += isMAS(grid.get(ligne+1).charAt(column), grid.get(ligne+2).charAt(column),grid.get(ligne+3).charAt(column))
                            ? 1 : 0;

                }

                if (isInGrid(grid, ligne, column, -1, 0)){
                    printArray(grid, new Point(ligne, column), new Point(ligne-1, column), new Point(ligne-2, column), new Point(ligne-3, column));
                    result += isMAS(grid.get(ligne-1).charAt(column), grid.get(ligne-2).charAt(column),grid.get(ligne-3).charAt(column))
                            ? 1 : 0;
                }


                //Check diagonal
                if (isInGrid(grid, ligne, column, 1, 1)){
                    printArray(grid, new Point(ligne, column), new Point(ligne+1, column+1), new Point(ligne+2, column+2), new Point(ligne+3, column+3));
                    result += isMAS(grid.get(ligne+1).charAt(column+1),
                            grid.get(ligne+2).charAt(column+2),
                            grid.get(ligne+3).charAt(column+3))
                            ? 1 : 0;
                }


                if (isInGrid(grid, ligne, column, -1, -1)){
                    printArray(grid, new Point(ligne, column), new Point(ligne-1, column-1), new Point(ligne-2, column-2), new Point(ligne-3, column-3));
                    result += isMAS(grid.get(ligne-1).charAt(column-1),
                            grid.get(ligne-2).charAt(column-2),
                            grid.get(ligne-3).charAt(column-3))
                            ? 1 : 0;
                }


                if (isInGrid(grid, ligne, column, 1, -1)){
                    printArray(grid, new Point(ligne, column), new Point(ligne+1, column-1), new Point(ligne+2, column-2), new Point(ligne+3, column-3));
                    result += isMAS(grid.get(ligne+1).charAt(column-1),
                            grid.get(ligne+2).charAt(column-2),
                            grid.get(ligne+3).charAt(column-3))
                            ? 1 : 0;
                }

                if (isInGrid(grid, ligne, column, -1, +1)){
                    printArray(grid, new Point(ligne, column), new Point(ligne-1, column+1), new Point(ligne-2, column+2), new Point(ligne-3, column+3));
                    result += isMAS(grid.get(ligne-1).charAt(column+1),
                            grid.get(ligne-2).charAt(column+2),
                            grid.get(ligne-3).charAt(column+3))
                            ? 1 : 0;
                }
            }
        }

        return result;
    }

    public static void main(String[] args) {
        //var input = Utils.listFromDemoFile();
        var input = Utils.listForDay(2024, 4);

        var part1 = processPart1(input);

        System.out.println("part1 = " + part1);
    }
}

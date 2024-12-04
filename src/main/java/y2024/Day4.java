package y2024;

import all.Utils;

import java.util.ArrayList;

public class Day4 {
    public static int correctInBound(int current, int max){
        if (current < 0 || current > max){
            return -1;
        }

        return current;
    }

    private static boolean isMAS(char m, char a, char s){
        return m == 'M' && a == 'A' && s == 'S';
    }

    private static boolean isInGrid(ArrayList<String> grid, int x, int y, int stepX, int stepY){
        var line = grid.get(x);

        return correctInBound(x+stepX, grid.size()) != -1 ||
                correctInBound(Math.abs(x+stepX)+stepX, grid.size()) != -1 ||
                correctInBound(Math.abs(Math.abs(x+stepX)+stepX)+stepX, grid.size()) != -1 ||
                correctInBound(y+stepY, line.length()) != -1 ||
                correctInBound(Math.abs(y+stepY)+stepY, line.length()) != -1 ||
                correctInBound(Math.abs(Math.abs(y+stepY)+stepY)+stepY, line.length()) != -1;
    }
    
    private static long processPart1(ArrayList<String> grid){
        long result = 0;

        for (int x = 0; x < grid.size(); x++){
            var line = grid.get(x).toCharArray();
            for (int y = 0; y < line.length; y++){
                char car = line[y];

                if(car == 'X'){
                    // Check line
                    if (isInGrid(grid, x, y, 0, 1)){
                        result += isMAS(line[y+1], line[y+2], line[y+3]) ? 1 : 0;
                    }

                    if (isInGrid(grid, x, y, 0, -1)){
                        result += isMAS(line[y-1], line[y-2], line[y-3]) ? 1 : 0;
                    }

                    //check column
                    if (isInGrid(grid, x, y, 1, 1)){
                        result += isMAS(grid.get(x+1).charAt(y), grid.get(x+2).charAt(y),grid.get(x+3).charAt(y))
                                ? 1 : 0;
                    }

                    if (isInGrid(grid, x, y, 0, -1)){
                        result += isMAS(grid.get(x-1).charAt(y), grid.get(x-2).charAt(y),grid.get(x-3).charAt(y))
                                ? 1 : 0;
                    }

                    //Check diagonal
                    if (isInGrid(grid, x, y, 1, 1)){
                        result += isMAS(grid.get(x+1).charAt(y+1),
                                grid.get(x+2).charAt(y+2),
                                grid.get(x+3).charAt(y+3))
                                ? 1 : 0;
                    }
                    if (isInGrid(grid, x, y, -1, -1)){
                        result += isMAS(grid.get(x-1).charAt(y-1),
                                grid.get(x-2).charAt(y-2),
                                grid.get(x-3).charAt(y-3))
                                ? 1 : 0;
                    }

                    if (isInGrid(grid, x, y, 1, -1)){
                        result += isMAS(grid.get(x+1).charAt(y-1),
                                grid.get(x+2).charAt(y-2),
                                grid.get(x+3).charAt(y-3))
                                ? 1 : 0;
                    }
                    if (isInGrid(grid, x, y, -1, +1)){
                        result += isMAS(grid.get(x-1).charAt(y+1),
                                grid.get(x-2).charAt(y+2),
                                grid.get(x-3).charAt(y+3))
                                ? 1 : 0;
                    }
                }
            }
        }

        return result;
    }

    public static void main(String[] args) {
        var input = Utils.listFromDemoFile();

        var part1 = processPart1(input);

        System.out.println("part1 = " + part1);
    }
}

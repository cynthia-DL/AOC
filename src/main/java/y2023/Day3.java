package y2023;

import all.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

record PartNumber(int value, int line, int firstColumn, int lastColumn){
    static PartNumber fromLine(String line, int lineNumber, int firstColumn){
        Objects.requireNonNull(line);
        Objects.checkIndex(firstColumn, line.length());

        int lastColumn = firstColumn;
        int value = 0;

        while (lastColumn < line.length() && Utils.isParsable(line.charAt(lastColumn))){
            value = value*10 + Integer.parseInt(String.valueOf(line.charAt(lastColumn)));
            lastColumn++;
        }

        return new PartNumber(value, lineNumber, firstColumn, lastColumn-1);
    }

    boolean isValid(List<String> list){
        int MAX_LINE = list.size()-1;
        int MAX_COLUMN = list.get(0).length()-1;
        for (int i = Math.max(0, this.line-1); i <= Math.min(line+1, MAX_LINE); i++) {
            for (int j = Math.max(0, this.firstColumn - 1); j <= Math.min(lastColumn+1, MAX_COLUMN); j++) {
                if(i == line && j >= firstColumn && j <= lastColumn){
                    continue;
                }
                char toCheck = list.get(i).charAt(j);
                if(!Utils.isParsable(toCheck) && toCheck != '.'  || (toCheck == '*' && !GearNumber.isGearable(list, i, j))){
                    return true;
                }
            }
        }

        return false;
    }
}

record GearNumber(int line, int column, PartNumber p1, PartNumber p2){

    static boolean isGearable(List<String> list, int line, int column){
        boolean p1 = false;

        int MAX_LINE = list.size()-1;
        int MAX_COLUMN = list.get(0).length()-1;

         for (int i = Math.max(0,line-1); i <= Math.min(line+1, MAX_LINE); i++) {
            var string = list.get(i);
            for (int j = Math.max(0, column - 1); j <= Math.min(column + 1, MAX_COLUMN); j++) {
                if (Utils.isParsable(string.charAt(j))){
                    int tmpColumn = j;

                    while (tmpColumn > 0 && Utils.isParsable(string.charAt(tmpColumn-1))) tmpColumn--;
                    if (!p1) {
                        p1 = true;
                        while (tmpColumn < MAX_COLUMN && Utils.isParsable(string.charAt(tmpColumn))){
                            tmpColumn++;
                        }
                        j = tmpColumn;
                    } else {
                        return true;
                    }
                }
            }
        }

        return false;
    }
    static GearNumber fromLine(List<String> list, int lineNumber, int columnNumber){
        int MAX_LINE = list.size()-1;
        int MAX_COLUMN = list.get(0).length()-1;

        PartNumber p1 = null, p2 = null;

        for (int i = Math.max(0,lineNumber-1); i <= Math.min(lineNumber+1, MAX_LINE); i++) {
            var line = list.get(i);
            for (int j = Math.max(0, columnNumber - 1); j <= Math.min(columnNumber + 1, MAX_COLUMN); j++) {
                if (Utils.isParsable(line.charAt(j))){
                    int tmpColumn = j;

                    while (tmpColumn > 0 && Utils.isParsable(line.charAt(tmpColumn-1))) tmpColumn--;
                    if (p1 == null) {
                        p1 = PartNumber.fromLine(line, i, tmpColumn);
                        j = p1.lastColumn();
                    } else {
                        p2 = PartNumber.fromLine(line, i, tmpColumn);
                        j = p2.lastColumn();
                    }
                }
            }
        }

        return p2 == null ? null :  new GearNumber(lineNumber, columnNumber, p1, p2);
    }
}

public class Day3 {
    public static void main(String[] args) {

        var lines = Utils.listFromFile("inputs/y2023/day3_input.txt");
        //var lines = Utils.listFromFile("inputs/y2023/demo.txt");

        for (var line : lines){
            System.out.println(Arrays.toString(line.toCharArray()));
        }

        List<PartNumber> partNumers = new ArrayList<>();
        for (int i = 0; i < lines.size(); i++){
            var line = lines.get(i);
            for (int j = 0; j < line.length(); j++){
                if(Utils.isParsable(line.charAt(j))){
                    var pn = PartNumber.fromLine(line, i, j);
                    j = pn.lastColumn();
                    partNumers.add(pn);
                }
            }

        }
        
        var gears = new ArrayList<GearNumber>();

        for (int i = 0; i < lines.size(); i++){
            var line = lines.get(i);
            for (int j = 0; j < line.length(); j++){
                if (line.charAt(j) == '*'){
                    var gear = GearNumber.fromLine(lines, i, j);
                    if (gear != null) gears.add(gear);
                }
            }
        }

        var result1_p1 = partNumers.stream()
                .filter(partNumer -> partNumer.isValid(lines))
                .peek(System.out::println)
                .mapToInt(PartNumber::value)
                .sum();

        System.out.println("result1_p1 = " + result1_p1);
        
        var result1_p2 = gears.stream()
                .peek(System.out::println)
                .mapToInt(gear -> gear.p1().value() * gear.p2().value())
                .sum();

        System.out.println("result1_p2 = " + result1_p2);
    }
}

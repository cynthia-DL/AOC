package y2023;

import all.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

record PartNumer(int value, int line, int firstColumn, int lastColumn){
    static PartNumer fromLine(String line, int lineNumber, int firstColumn){
        Objects.requireNonNull(line);
        Objects.checkIndex(firstColumn, line.length());

        int lastColumn = firstColumn;
        int value = 0;

        while (lastColumn < line.length() && Utils.isParsable(line.charAt(lastColumn))){
            value = value*10 + Integer.parseInt(String.valueOf(line.charAt(lastColumn)));
            lastColumn++;
        }

        return new PartNumer(value, lineNumber, firstColumn, lastColumn-1);
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
                if(!Utils.isParsable(toCheck) && toCheck != '.'){
                    return true;
                }
            }
        }

        return false;
    }

}

public class Day3 {
    public static void main(String[] args) {

        var lines = Utils.listFromFile("inputs/y2023/day3_input.txt");
        //var lines = Utils.listFromFile("inputs/y2023/demo.txt");

        List<PartNumer> partNumers = new ArrayList<>();
        for (int i = 0; i < lines.size(); i++){
            var line = lines.get(i);
            for (int j = 0; j < line.length(); j++){
                if(Utils.isParsable(line.charAt(j))){
                    var pn = PartNumer.fromLine(line, i, j);
                    j = pn.lastColumn();
                    partNumers.add(pn);
                }
            }

        }

        var result1 = partNumers.stream()
                .peek(partNumer -> System.out.println(partNumer+" is valid : "+partNumer.isValid(lines)))
                .filter(partNumer -> partNumer.isValid(lines))
                .mapToInt(PartNumer::value)
                .sum();

        System.out.println("result1 = " + result1);
    }
}

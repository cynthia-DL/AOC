package y2023;

import all.Utils;

import java.util.ArrayList;
import java.util.List;

record MirrorField (List<String> grid){
    static List<MirrorField> fromLines(List<String> lines){
        var mirrorFields = new ArrayList<MirrorField>();
        int start = 0;

        for (int i = 0; i < lines.size(); i++){
            if (lines.get(i).isEmpty()){
                    mirrorFields.add(new MirrorField(lines.subList(start, i)));

                start = i+1;
            }
        }

        mirrorFields.add(new MirrorField(lines.subList(start, lines.size())));

        return mirrorFields;
    }

    private boolean isSymetryColumn(int column){
        if (column == 0 || column == grid.get(0).length()-1) return false;

        for (int i = column, j = column+1; i >= 0 && j < grid.get(0).length(); i--, j++){
            for (int line = 0; line < grid.size(); line++){
                if (grid.get(line).charAt(i) != grid.get(line).charAt(j)){
                    return false;
                }
            }
        }

        return true;

    }

    private boolean isSymetryLine(int line){
        if (line == 0 || line == grid.size()-1) return false;

        for (long i = line, j = line+1; i >= 0 && j < grid.size(); i--, j++){
            if(!grid.get((int) i).equals(grid.get((int) j))) {
                return false;
            }
        }

        return true;
    }

    public long findSymetryColumn() {
        for (int i = 0; i < grid.get(0).length(); i++) {
            if (isSymetryColumn(i)) return i;
        }

        return 0;
    }

    public long findSymetryLine(){
        for (int i = 0; i < grid.size(); i++){
            if (isSymetryLine(i)) return i;
        }

        return 0;
    }
}
public class Day13 {

    public static void main(String[] args) {
        //var lines = Utils.listFromFile("inputs/demo.txt");
        var lines = Utils.listFromFile("inputs/y2023/day13_input.txt");

        var field = MirrorField.fromLines(lines);

        var result_p1_lines = field.stream()
                .mapToLong(MirrorField::findSymetryLine)
                .peek(System.out::println)
                .sum();

        var result_p1_columns = field.stream()
                .mapToLong(MirrorField::findSymetryColumn)
                .peek(System.out::println)
                .sum();

        System.out.println("result_p1_columns = " + result_p1_columns);
        System.out.println("result_p1_lines = " + result_p1_lines);

        System.out.println("result_p1 = " + ((result_p1_columns*100L)+result_p1_lines));
    }
}

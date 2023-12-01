package y2023;

import all.Utils;

import java.util.Objects;

public class Day1 {

    private static boolean isParsable(String c){
        try {
            Integer.parseInt(Objects.requireNonNull(c));
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }


    public static int getValue(String line){
        int start = -1;
        int end = -1;

        for (Character c : line.toCharArray()){
            if (Day1.isParsable(c.toString())){
               var num = Integer.parseInt(c.toString());

               end = num;
               if (start == -1)
                   start = num;
            }
        }

        return start * 10 + end;
    }
    public static void main(String[] args) {
        var lines = Utils.listFromFile("inputs/y2023/day1_input.txt");

        var result = lines.stream().mapToInt(Day1::getValue).sum();

        System.out.println(result);
    }
}

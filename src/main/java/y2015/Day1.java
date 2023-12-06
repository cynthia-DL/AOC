package y2015;

import all.Utils;

import javax.swing.*;
import java.util.Arrays;
import java.util.stream.IntStream;

public class Day1 {
    public static void main(String[] args) {
        var line = Utils.listFromFile("inputs/y2015/day1_input.txt").get(0);


        var result_p1 = IntStream.range(0, line.length())
                .mapToObj(i -> Character.toString(line.charAt(i)))
                .mapToInt(c -> c.equals("(") ? 1 : -1)
                .sum();


        System.out.println("result_p1 = " + result_p1);

        var array = IntStream.range(0, line.length())
                .mapToObj(i -> Character.toString(line.charAt(i)))
                .mapToInt(c -> c.equals("(") ? 1 : -1)
                .toArray();

        var total = 0;
        var pos = 0;

        for (; pos < array.length && total != -1; pos++){
            total+=array[pos];
        }

        System.out.println("pos = " + pos);


    }
}

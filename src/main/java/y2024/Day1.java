package y2024;

import all.Utils;

import java.util.ArrayList;
import java.util.Objects;

public class Day1 {

    private static void fillLists(ArrayList<String> input, ArrayList<Integer> list1, ArrayList<Integer> list2){
        for(var line : input){
            var separatedLine = line.split(" {3}");
            list1.add(Integer.valueOf(separatedLine[0]));
            list2.add(Integer.valueOf(separatedLine[1]));
        }

        list1.sort(Integer::compareTo);
        list2.sort(Integer::compareTo);
    }

    public static int part1(){
        ArrayList<Integer> list1 = new ArrayList<>();
        ArrayList<Integer> list2 = new ArrayList<>();

        fillLists(Utils.listForDay(2024, 1), list1, list2);

        int valuePart1 = 0;

        for (int i = 0; i < list1.size(); i++){
            valuePart1 += Math.abs(list1.get(i) - list2.get(i));
        }

        return valuePart1;
    }

    public static int part2(){
        ArrayList<Integer> list1 = new ArrayList<>();
        ArrayList<Integer> list2 = new ArrayList<>();

        fillLists(Utils.listForDay(2024, 1), list1, list2);
        int result = 0;

        for (Integer element :  list1){
            result += (int) (element * list2.stream().filter(i -> Objects.equals(i, element)).count());
        }

        return result;
    }

    public static void main(String[] args) {
        System.out.println("part 1 = " + part1());
        System.out.println("part 2 = " + part2());
    }
}

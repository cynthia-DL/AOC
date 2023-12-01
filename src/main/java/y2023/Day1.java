package y2023;

import all.Utils;

import java.util.Arrays;
import java.util.Objects;

public class Day1 {

    private static final String[] numbers = {"one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};


    private static int parseStringToNumber(char[] charset, int startIndex){

        for (int currentNumber = 0; currentNumber < numbers.length; currentNumber++){
            int fits = numbers[currentNumber].length();
            boolean rightNumber = true;
            for (int currentChar = 0;
                 currentChar+startIndex < charset.length && currentChar < numbers[currentNumber].length();
                 currentChar++){
                if (charset[currentChar + startIndex] != numbers[currentNumber].charAt(currentChar)) {
                    fits--;
                    break;
                }
                rightNumber = currentChar == numbers[currentNumber].length()-1;
            }

            if (fits == numbers[currentNumber].length() && rightNumber) return currentNumber+1;
        }

        return -1;
    }

    private static boolean isParsable(char c){
        try {
            Integer.parseInt(Character.toString(c));
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }


    public static int getValue(String line){
        int start = -1;
        int end = -1;

        for (int i = 0; i < line.length();){
            char c = line.charAt(i);
            int num = -1;

            if (Day1.isParsable(c)){
               num = Integer.parseInt(Character.toString(c));
            } else {
                num = parseStringToNumber(line.toCharArray(), i);
            }
            i++;

            if (num != -1) {
                end = num;
                if (start == -1)
                    start = num;
            }
        }

        return start * 10 + end;
    }
    public static void main(String[] args) {
        //var lines = Utils.listFromFile("inputs/y2023/demo.txt");
        var lines = Utils.listFromFile("inputs/y2023/day1_input.txt");

        var result = lines
                .stream()
                .peek(e ->System.out.println(e+" = "+Day1.getValue(e)))
                .mapToInt(Day1::getValue)
                .sum();

        System.out.println(result);


    }
}

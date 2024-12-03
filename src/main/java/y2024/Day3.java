package y2024;

import all.LongPair;
import all.Utils;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day3 {
    public static ArrayList<LongPair> parseForPart1(String input){
        var result = new ArrayList<LongPair>();

        Pattern pattern = Pattern.compile("mul\\((\\d*),(\\d*)\\)");
        Matcher matcher = pattern.matcher(input);

        while (matcher.find()){
            var pair = new LongPair(Long.parseLong(matcher.group(1)), Long.parseLong(matcher.group(2)));
            result.add(pair);
        }

        return result;
    }

    private static ArrayList<String> segmentForPart2(String input){
        ArrayList<String> result = new ArrayList<>();
        ArrayList<String> brotherEw = new ArrayList<>();

        String doPattern = "do()";
        String dontPattern = "don't()";

        String toConsume = String.copyValueOf(input.toCharArray());

        String currentPattern = dontPattern;

        while (true){
            int toCut = toConsume.indexOf(currentPattern);

            if(toCut == -1){
                if (currentPattern.equals(dontPattern)) {
                    result.add(toConsume);
                } else {
                    brotherEw.add(toConsume);
                }
                break;
            }

            if (currentPattern.equals(dontPattern)) {
                result.add(toConsume.substring(0, toCut));
            } else {
                brotherEw.add(toConsume.substring(0, toCut));
            }
            toConsume = toConsume.substring(toCut + currentPattern.length());
            currentPattern = currentPattern.equals(doPattern) ? dontPattern : doPattern;
        }

        for (var s : brotherEw) {
            System.out.println("s = " + s);
        }
        return result;
    }

    public static ArrayList<LongPair> parseForPart2(String input){
        var result = new ArrayList<LongPair>();

        var segments = segmentForPart2(input);
        for (var segment : segments){
            System.out.println("segment = " + segment);
            result.addAll(parseForPart1(segment));
        }

        return result;
    }
    public static void main(String[] args) {
        //var input = Utils.listFromDemoFile();
        var input = Utils.listForDay(2024, 3);
        long part1 = 0;

        for (var line : input) {

            part1+= parseForPart1(line)
                    .stream()
                    .mapToLong(pair -> pair.a() * pair.b())
                    .sum();
        }

        System.out.println("part1 = " + part1);

        long part2 = 0;

        for (var line : input) {

            part2+= parseForPart2(line)
                    .stream()
                    .mapToLong(pair -> pair.a() * pair.b())
                    .sum();
        }

        System.out.println("part2 = " + part2);
    }
}

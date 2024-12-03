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
            System.out.println("matcher.group(0) = " + matcher.group(0));
            var pair = new LongPair(Long.parseLong(matcher.group(1)), Long.parseLong(matcher.group(2)));
            result.add(pair);
        }

        return result;
    }
    public static void main(String[] args) {
        var input =Utils.listFromDemoFile().get(0);
        //var input =Utils.listForDay(2024, 3).get(0);
        var part1 = parseForPart1(input)
                .stream()
                .mapToLong(pair -> pair.a() * pair.b())
                .sum();

        System.out.println("part1 = " + part1);
    }
}

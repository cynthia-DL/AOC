package y2023;


import all.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

record Race(long time, long distance){

    static Race fromLines(List<String> lines) {
        var tmpTime = lines.get(0).split(":")[1]
                .trim()
                .replace("\\s+", " ")
                .split(" ");
        var tmpDistances = lines.get(1).split(":")[1]
                .trim().replace("\\s+", " ")
                .split(" ");

        var times = Arrays.stream(tmpTime).filter(s -> !s.isBlank() && !s.isEmpty()).map(String::trim).reduce("", (total, current) -> total+current);
        var distances = Arrays.stream(tmpDistances).filter(s -> !s.isBlank() && !s.isEmpty()).map(String::trim).reduce("", (total, current) -> total+current);

        return new Race(Long.parseLong(times), Long.parseLong(distances));
    }
    static List<Race> listFromLines(List<String> lines){
        var races = new ArrayList<Race>();

        var tmpTime = lines.get(0).split(":")[1]
                .trim()
                .replace("\\s+", " ")
                .split(" ");
        var tmpDistances = lines.get(1).split(":")[1]
                .trim().replace("\\s+", " ")
                .split(" ");

        var times = Arrays.stream(tmpTime).filter(s -> !s.isBlank() && !s.isEmpty()).map(String::trim).mapToInt(Integer::parseInt).toArray();
        var distances = Arrays.stream(tmpDistances).filter(s -> !s.isBlank() && !s.isEmpty()).map(String::trim).mapToInt(Integer::parseInt).toArray();

        for (int i = 0; i < distances.length; i++){
            races.add(new Race(times[i], distances[i]));
        }

        return races;
    }

    long waysToWin(){
        long ways = 0;

        for (int holdFor = 0; holdFor < time; holdFor++) {
            long travelingTime = time - holdFor;
            long travelingDistance = travelingTime * holdFor;

            if(travelingDistance > distance) ways++;
        }

        return ways;
    }
}
public class Day6 {

    public static void main(String[] args) {
        var lines = Utils.listFromFile("inputs/y2023/day6_input.txt");
        //var lines = Utils.listFromFile("inputs/demo.txt");
        var races = Race.listFromLines(lines);

        var result_p1 = races.stream()
                .mapToLong(Race::waysToWin)
                .peek(System.out::println)
                .reduce(1, (total, element) -> total * element);


        System.out.println("result_p1 = " + result_p1);

        System.out.println("result_p2 = " + Race.fromLines(lines).waysToWin());
    }
}

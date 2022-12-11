package y2022;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

record Rucksack(String gauche, String droite){

    public static String charInCommon(List<String> bags){
        var str = new ArrayList<>(IntStream.range(0, bags.size())
                .mapToObj(i -> bags.get(0).charAt(i))
                .toList());

        for (var bag :bags){
            str.removeIf(ch -> !bag.contains(ch.toString()));
        }

        return str.stream()
                .map(Object::toString)
                .collect(Collectors.joining(""));
    }

    public static int priority(char c){
        var charAsInt = (int) c;
        if(charAsInt >= (int)'a' && charAsInt <= (int) 'z'){
            return charAsInt - 96;
        } else {
            return charAsInt - 38;
        }
    }
    public int sumPriorities(){
        return IntStream.range(0, gauche.length())
                .mapToObj(gauche::charAt)
                .distinct()
                .filter(c -> droite.contains(c.toString()))
                .mapToInt(Rucksack::priority)
                .reduce(0, Integer::sum);
    }

    public String fullRucksack(){return gauche+droite;}
}

public class Day3 {
ArrayList<Rucksack> rucksacks = new ArrayList<>();
ArrayList<ArrayList<Rucksack>> teams = new ArrayList<>();

    public static void main(String[] args) {

        var day = new Day3();
        try (BufferedReader reader = Files.newBufferedReader(Path.of("./inputs/day3_input.txt"), StandardCharsets.UTF_8)) {
            String line = null;
            int i = 0;

            while ((line = reader.readLine()) != null) {
                //System.out.println(line);
                day.rucksacks.add(new Rucksack(line.substring(0, line.length()/2), line.substring(line.length()/2)));
                if (i % 3 == 0){
                    day.teams.add(new ArrayList<>());
                }

                day.teams.get(i/3).add(day.rucksacks.get(i));
                i++;
            }
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }

        // System.out.println("doublons = "+day.rucksacks.stream().mapToInt(y2022.Rucksack::sumPriorities).reduce(0, Integer::sum));
        // System.out.println("day.teams = " + day.teams);

        var bags =  day.teams.stream()
                .map(list -> list.stream()
                        .map(Rucksack::fullRucksack)
                        .toList()
                )
                .toList();

        System.out.println("day.teams = " + bags);
        System.out.println(Rucksack.charInCommon(bags.get(1)));

        System.out.println(bags.stream()
                .map(Rucksack::charInCommon)
                .peek(System.out::println)
                .filter(s -> s.length() > 0)
                .map(s -> s.charAt(0))
                .mapToInt(Rucksack::priority)
                .reduce(Integer::sum));

    }
}

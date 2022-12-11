package y2022;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

record Section(int start, int finish){
    static Section fromString(String str){
        var arr = str.split("-");
        return new Section(Integer.parseInt(arr[0]), Integer.parseInt(arr[1]));
    }
}

record CleaningPair(Section a, Section b){
    static CleaningPair fromString(String str){
        var arr = str.split(",");
        return new CleaningPair(Section.fromString(arr[0]), Section.fromString(arr[1]));
    }

    public boolean oneContainOther(){
        return a.start() <= b.start() && a.finish() >= b.finish()
                || b.start() <= a.start() && b.finish() >= a.finish();
    }

    public boolean kingOfContainOther(){
        return a.start() >= b.start() && a.start() <= b.finish() ||
                a.finish() >= b.start() && a.finish() <= b.finish() ||
                b.start() >= a.start() && b.start() <= a.finish() ||
                b.finish() >= a.start() && b.finish() <= a.finish();
    }
}

public class Day4 {
    public static void main(String[] args) {
        try (BufferedReader reader = Files.newBufferedReader(Path.of("./inputs/day4_input.txt"), StandardCharsets.UTF_8)) {
            String line = null;
            var list = new ArrayList<CleaningPair>();

            while ((line = reader.readLine()) != null) {
                list.add(CleaningPair.fromString(line));
            }

            System.out.println(" 1 : "+list.stream().filter(CleaningPair::oneContainOther).count());
            System.out.println(" 2 : "+list.stream().filter(CleaningPair::kingOfContainOther).count());
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
    }
}

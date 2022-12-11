package y2022;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;

public class Day1 {
    private final ArrayList<ArrayList<Integer>> elves = new ArrayList<>();

    private int maxCalorie(){
        return elves.stream()
                .map(elf -> elf.stream().reduce(0, Integer::sum))
                .mapToInt(Integer::intValue)
                .reduce(0, Integer::max);
    }

    private int sumTop3Calories(){
       var sums = elves.stream()
               .map(elf -> elf.stream().reduce(0, Integer::sum))
               .sorted(Collections.reverseOrder())
               .toList();

       return sums.get(0) + sums.get(1) + sums.get(2);
    }

    public Day1(String fileName){

        var cs = StandardCharsets.UTF_8;

        try (BufferedReader reader = Files.newBufferedReader(Path.of("./inputs/"+fileName), cs)) {
            String line = null;
            int i = 0;
            elves.add(new ArrayList<>());
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
                if (line.length() != 0){
                    elves.get(i).add(Integer.parseInt(line));
                } else {
                    i++;
                    elves.add(new ArrayList<>());
                }
            }
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
    }

    public static void main(String[] args) {
        var d = new Day1("day1_input.txt");
        System.out.println("Max calories = "+d.maxCalorie());
        System.out.println("sum calories of 3 = "+d.sumTop3Calories());
    }
}

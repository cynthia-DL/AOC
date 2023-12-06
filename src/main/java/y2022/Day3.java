package y2022;

import all.Utils;

import java.util.ArrayList;
import java.util.List;

record Rucksack(String part1, String part2){
    static Rucksack fromLine(String line){
        return new Rucksack(line.substring(0, line.length()/2), line.substring(line.length()/2));
    }

    static int value(char c){
        if (c >= 'a' && c <= 'z'){
            return (int) c-'a'+ 1;
        } else return (int) c - 'A'+27;
    }

    int priorityOfUniqueItem(){
        for (var c : part1.toCharArray()){
            if (part2.contains(Character.toString(c))){
                return value(c);
            }
        }

        return -1;
    }

    boolean contains(char c){
        return part1.contains(Character.toString(c)) || part2.contains(Character.toString(c));
    }
}

record Group(Rucksack elf1, Rucksack elf2, Rucksack elf3){
    static List<Group> fromLines(List<String> lines){
        var groups = new ArrayList<Group>();

        for (int i = 0; i < lines.size(); i+=3){
            groups.add(new Group(
                    Rucksack.fromLine(lines.get(i)),
                    Rucksack.fromLine(lines.get(i+1)),
                    Rucksack.fromLine(lines.get(i+2))
            ));
        }

        return groups;
    }

    int bargePriority(){
        for (var element : elf1.part1().concat(elf1.part2()).toCharArray()){
            if (elf2.contains(element) && elf3.contains(element) ){
                return Rucksack.value(element);
            }
        }

        return -1;
    }
}
public class Day3 {
    public static void main(String[] args) {
        //var lines = Utils.listFromFile("inputs/demo.txt");
        var lines = Utils.listFromFile("inputs/y2022/day3_input.txt");


        var result_p1 = lines.stream()
                .map(Rucksack::fromLine)
                .mapToInt(Rucksack::priorityOfUniqueItem)
                .sum();

        var result_p2 = Group.fromLines(lines).stream()
                 .mapToInt(Group::bargePriority)
                 .sum();

        System.out.println("result_p1 = " + result_p1);
        System.out.println("result_p2 = " + result_p2);
    }
}

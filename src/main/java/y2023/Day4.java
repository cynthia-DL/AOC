package y2023;

import all.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

record Scratchcard(int card, List<Integer> winning, List<Integer> haved){

    static HashMap<Integer, Scratchcard> fromLines(List<String> lines){
        var hashmap = new HashMap<Integer, Scratchcard>();

        int i = 1;
        for (var line : lines){
            hashmap.put(i, fromLine(line));
            i++;
        }

        return hashmap;
    }
    static Scratchcard fromLine(String line){
        var winning = new ArrayList<Integer>();
        var haved = new ArrayList<Integer>();

        var cardArray = line.split(":")[0].replace("  ", " ").split(" ");

        int card;

        if(Utils.isParsable(cardArray[1])){
            card = Integer.parseInt(cardArray[1]);
        } else card = Integer.parseInt(cardArray[2]);


        var numbers = line.split(":")[1].replace("  ", " ").split("\\|");
        
        for (var winner : numbers[0].split(" ")){
            if(Utils.isParsable(winner)) {
                winning.add(Integer.parseInt(winner.strip()));
            }
        }

        for (var have : numbers[1].split(" ")){
            if(Utils.isParsable(have)) {
                haved.add(Integer.parseInt(have.strip()));
            }
        }

        return new Scratchcard(card, winning, haved);
    }

    int matches(){
        int value = 0;
        for (var have : haved){
            if (winning().contains(have)){
                value++;
            }
        }
        return value;
    }

    int value(){
        int value = 0;
        for (var have : haved){
            if (winning().contains(have)){
                value = value == 0 ? 1 : value*2;
            }
        }

        return value;
    }
}

public class Day4 {
    public static void main(String[] args) {
        var lines = Utils.listFromFile("inputs/y2023/day4_input.txt");

        var result_1 = lines.stream()
                .map(Scratchcard::fromLine)
                .mapToInt(Scratchcard::value)
                .sum();

        System.out.println("result_1 = " + result_1);

        var originals = Scratchcard.fromLines(lines);
        var scratchcards = new ArrayList<>(lines.stream().map(Scratchcard::fromLine).toList());

        for (int i = 0; i < scratchcards.size(); i++){
            var current = scratchcards.get(i);
            var matches = current.matches();

            for (int j = 1; j <= matches; j++){
                scratchcards.add(originals.get(current.card()+j));
            }

        }

        System.out.println("result_2 = "+scratchcards.size());
    }
}

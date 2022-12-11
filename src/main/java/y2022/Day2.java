package y2022;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;


enum Choice {
    ROCK(1), PAPER(2), SCISSORS(3);

    public int value;

    Choice(int value){
        this.value = value;
    }

    static Choice parseFromString(String str){
        if("A".equals(str) || "X".equals(str)){
            return ROCK;
        } else if ("B".equals(str) || "Y".equals(str)){
           return PAPER;
        }
        return SCISSORS;
    }
}

enum Result{
    LOSE, DRAW, WIN;

    static Result parseFromString(String str){
        if("X".equals(str)){
            return LOSE;
        } else if ("Y".equals(str)){
            return DRAW;
        }
        return WIN;
    }
}

class Match{
    Choice you;
    Choice me;
    public Match(Choice you, Choice me){
        this.you = you;
        this.me = me;
    }

    public Match(Choice you, Result r){
        this.you = you;
        switch (r){
            case DRAW -> this.me = you;
            case WIN ->
                this.me = switch (you){
                    case ROCK -> Choice.PAPER;
                    case PAPER -> Choice.SCISSORS;
                    default -> Choice.ROCK;
                };

            default -> this.me = switch (you){
                case ROCK -> Choice.SCISSORS;
                case PAPER -> Choice.ROCK;
                default -> Choice.PAPER;
            };
        }
    }
    public int score(){
        if(
                (you == Choice.ROCK && me == Choice.PAPER) ||
                (you == Choice.PAPER && me == Choice.SCISSORS) ||
                (you == Choice.SCISSORS && me == Choice.ROCK)
        ) {
            return me.value+6;
        }
        else if ( you.equals(me)){
            return me.value + 3;
        }

        return me.value;
    }

}

public class Day2 {

    public final ArrayList<Match> matches = new ArrayList<>();

    public Day2(String fileName){
        var cs = StandardCharsets.UTF_8;

        try (BufferedReader reader = Files.newBufferedReader(Path.of("./inputs/"+fileName), cs)) {
            String line = null;

            while ((line = reader.readLine()) != null) {
                //System.out.println(line);
                var inputs = line.split(" ");
                //matches.addToX(new y2022.Match(y2022.Choice.parseFromString(inputs[0]), y2022.Choice.parseFromString(inputs[1])));
                matches.add(new Match(Choice.parseFromString(inputs[0]), Result.parseFromString(inputs[1])));
            }
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
    }

    public static void main(String[] args) {
        var a = new Day2("day2_input.txt");
        System.out.println("a.matches = " + a.matches);

        var total = a.matches.stream()
                .mapToInt(Match::score)
                .reduce(0, Integer::sum);

        System.out.println(total);

    }
}

package y2023;

import all.Utils;

import java.util.Arrays;

record Game(int id, boolean possible, int power) {
    static final int MAX_RED = 12;
    static final int MAX_GREEN = 13;
    static final int MAX_BLUE = 14;


    static Game fromLine(String line) {
        var chunks = line.split(":");
        var id = Integer.parseInt(chunks[0].split(" ")[1]);

        int red = 0, green = 0, blue = 0;
        boolean possible = true;

        for (var draws : chunks[1].split(";")) {
            for (var draw : draws.split(",")) {
                var count = draw.trim().split(" ");
                switch (count[1]) {
                    case "red", "red,": {
                        if (Integer.parseInt(count[0]) > MAX_RED) {
                            possible = false;
                        }
                        red = Math.max(red, Integer.parseInt(count[0]));
                        break;
                    }
                    case "green,", "green": {
                        if (Integer.parseInt(count[0]) > MAX_GREEN) {
                            possible = false;
                        }

                        green = Math.max(green, Integer.parseInt(count[0]));
                        break;
                    }
                    case "blue,", "blue": {
                        if (Integer.parseInt(count[0]) > MAX_BLUE) {
                            possible = false;
                        }

                        blue = Math.max(blue, Integer.parseInt(count[0]));
                        break;
                    }


                }
            }

        }
        /*
        System.out.println("***"+line+"***");
        System.out.println("red = " + red);
        System.out.println("green = " + green);
        System.out.println("blue = " + blue);
        System.out.println("********************************");
        */

        return new Game(id, possible, red * green * blue);
    }
}

public class Day2 {

    public static void main(String[] args) {
        //var lines = Utils.listFromFile("inputs/y2023/demo.txt");
        var lines = Utils.listFromFile("inputs/y2023/day2_input.txt");

        var result_1 = lines.stream()
                .map(Game::fromLine)
                .filter(Game::possible)
                .mapToInt(Game::id)
                .sum();

        var result_2 = lines.stream()
                .map(Game::fromLine)
                .mapToInt(Game::power)
                .sum();

        System.out.println(result_1);
        System.out.println(result_2);
    }
}

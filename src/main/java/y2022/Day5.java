package y2022;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Day5 {
    public static ArrayList<Character> lineOfCrate(String line, int numberOfColumn){
        var array = line.toCharArray();
        var list = new ArrayList<Character>();
        for (int i = 0; i < numberOfColumn*4; i+=4){
            if(line.length() < i){
                list.add(' ');
            } else {
                list.add(array[i + 1]);
            }
        }

        return list;
    }
    public static void main(String[] args) {
        var lines = new ArrayList<String>();
        int separationLine = 0;
        int i = 0;

        // adding lines from input file onto the "lines" list, and
        try (BufferedReader reader = Files.newBufferedReader(Path.of("./inputs/day5_input.txt"), StandardCharsets.UTF_8)) {
            String line = null;

            while ((line = reader.readLine()) != null) {
                //System.out.println(line);
                lines.add(line);
                if (line.isEmpty()){
                    separationLine = i;
                } else {
                    i++;
                }
            }
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }

        // Getting the number of column
        var numbersOfColumnlines =  lines.get(separationLine-1);
        int numberColumn = -1;
        var sc = new Scanner(numbersOfColumnlines);
        while (sc.hasNextInt()){
            numberColumn = sc.nextInt();
        }
        System.out.println("numberColumn = " + numberColumn);

        // Creating crate stacks
        var stacks = new ArrayList<Stack<Character>>();

        for (i = 0; i< numberColumn; i++){
            stacks.add(new Stack<>());
        }

        //Adding stuff onto stacks
        //Iteration from last crate line to top
        System.out.println("separationLine = " + separationLine);
        for (i = separationLine-2; i >= 0; i--){
            var crateLine = Day5.lineOfCrate(lines.get(i), numberColumn);
            for(int j = 0; j < numberColumn; j++){
                //if there is a crate to addToX to a stack, addToX it
                if(!crateLine.get(j).equals(' ')) {
                    stacks.get(j).add(crateLine.get(j));
                }
            }
        }

        System.out.println("stacks = "+stacks);

        //Parsing instructions
        for (i = separationLine+1; i < lines.size(); i++){
            var words = lines.get(i).split(" ");
            int toMove = Integer.parseInt(words[1]);
            int from = Integer.parseInt(words[3])-1;
            int to = Integer.parseInt(words[5])-1;
            var stackTemp = new Stack<Character>();

            /* Same order */
            for(int j = 0; j < toMove; j++){
                stackTemp.push(stacks.get(from).pop());

                //stacks.get(to).push(stacks.get(from).pop());
            }for(int j = 0; j < toMove; j++){
                stacks.get(to).push(stackTemp.pop());
            }


        }

        System.out.println("stacks = " + stacks);
        for (var stack : stacks){
            System.out.print(stack.peek());
        }
    }
}

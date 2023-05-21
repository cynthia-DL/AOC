/**
 * This code represents a solution to the day 9 problem of year 2022.
 * @author Cynthia
 * Javadoc générated by GPT-3
 */
package y2022;


import all.Utils;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * Enum representing the possible directions for rope movement.
 */
enum RopeDirection {
    UP("U"), DOWN("D"), LEFT("L"), RIGHT("R");

    final String value;

    /**
     * Converts a string representation of a direction to the corresponding RopeDirection enum value.
     *
     * @param value the string representation of a direction
     * @return the RopeDirection enum value corresponding to the string, or null if no match is found
     */
    RopeDirection(String value){
        this.value = value;
    }
    static RopeDirection fromString(String string){
        for (var direction : RopeDirection.values()){
            if (direction.value.equals(string)){
                return direction;
            }
        }
        return null;
    }
}

/**
 * Class representing a grid on which a rope moves.
 */
class RopeGrid{

    Point head = new Point(0, 0);
    Point tail = new Point(0, 0);

    HashSet<Point> visited = new HashSet<>();

    final int MAX_X = 6;
    final int MAX_Y = 6;


    /**
     * Constructs a new RopeGrid object and initializes the visited set with the starting point.
     */
    RopeGrid(){
        visited.add(new Point(0, 0));
    }

    /**
     * Checks if the head and tail of the rope are adjacent.
     *
     * @return true if the head and tail are adjacent, false otherwise
     */
    boolean isAdjacent(){
        return (tail.x == head.x+1 || tail.x == head.x-1 || head.x == tail.x)
                && (tail.y == head.y+1 || tail.y == head.y-1 || head.y == tail.y);
    }

    private void move(Point point, RopeDirection direction){
        switch (direction){
            case UP -> point.y += 1;
            case DOWN -> point.y -= 1;
            case LEFT -> point.x -= 1;
            case RIGHT -> point.x += 1;
        }
    }

    /**
     * Moves the rope in the specified direction.
     *
     * @param direction the direction in which the rope should be moved
     */
    public void moveRope(RopeDirection direction){
        var oldHead = new Point(head);
        move(head, direction);

        if(!isAdjacent()){
            tail.x = oldHead.x;
            tail.y = oldHead.y;
            if(!visited.contains(tail)){
                visited.add(new Point(tail));
            }
        }
    }

    /**
     * Returns a string representation of the RopeGrid.
     *
     * @return a string representation of the RopeGrid
     */
    public String toString(){
        var sb = new StringBuilder();
        for (int i = 0; i < MAX_X; i++){
            for (int j = 0; j < MAX_Y; j++){
                if(MAX_X - 1 - head.x == i &&  head.y == j){
                    sb.append("H");
                } else if( MAX_X - 1 - tail.x == i && tail.y == j) {
                    sb.append("T");
                } else {
                    boolean checked = false;

                    for (var point : visited) {
                        if (!checked && (MAX_X - 1 - point.x == i && point.y == j)) {
                            sb.append("#");
                            checked = true;
                        }
                    }

                    if (!checked){
                        sb.append(".");
                    }
                }
            }
            sb.append("\n");
        }

        return sb.toString();
    }
}

/**
 * Class representing a pair of a number and a rope direction.
 */
class DirectionPair{
    int number;
    RopeDirection direction;


    public DirectionPair(int number, RopeDirection direction) {
        this.number = number;
        this.direction = direction;
    }

    @Override
    public String toString() {
        return "DirectionPair{" +
                "number=" + number +
                ", direction=" + direction +
                '}';
    }

    /**
     * Creates a DirectionPair object from a line of text.
     *
     * @param line the line of text representing a direction and number pair
     * @return a DirectionPair object created from the line of text
     */
    public static DirectionPair fromLine(String line){
        var array = line.split(" ");
        var number = Integer.parseInt(array[1]);
        var direction = RopeDirection.fromString(array[0]);

        return new DirectionPair(number, direction);
    }

}

/**
 * Main class for the day 9 problem solution.
 */
public class Day9 {
    /**
     * The main method reads lines from an input file, creates a list of DirectionPair objects from the lines,
     * creates a RopeGrid object, and executes the instructions on the grid. Finally, it prints the size of the visited set.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        var lignes = Utils.listFromFile("./inputs/2022/day9_input.txt");
        var instructions = new ArrayList<DirectionPair>();
        var rope = new RopeGrid();

        for (var ligne : lignes){
            instructions.add(DirectionPair.fromLine(ligne));
        }

        System.out.println("instructions = " + instructions);
        for (var dp : instructions){
            for (int i = 0; i < dp.number; i++){
                rope.moveRope(dp.direction);
            }
        }
        System.out.println(rope.visited.size());
    }
}

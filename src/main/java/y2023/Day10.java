package y2023;

import all.Utils;

import java.util.LinkedList;
import java.util.List;

enum Direction {
    NORTH(-1, 0), WEST(0, -1), SOUTH(1, 0), EAST(0, 1);

    private final int toX;
    private final int toY;

    Direction(int toX, int toY) {
        this.toX = toX;
        this.toY = toY;
    }

    public int toX() {
        return toX;
    }

    public int toY() {
        return toY;
    }

    public static Direction invert(Direction d){
        return switch (d){
            case NORTH -> SOUTH;
            case SOUTH -> NORTH;
            case EAST -> WEST;
            case WEST -> EAST;
        };
    }
}

record Pipe(int x, int y, Direction from, Direction to) {

    Pipe nextInLine(List<String> lines){

        var nextSymbol = lines.get(x+ to().toX()).charAt(y+ to().toY());

        Direction toGoTo;

        if (nextSymbol == '-' || nextSymbol == '|'){
            toGoTo = to;
        }  else if (nextSymbol == 'L'){
            toGoTo = switch (to){
                case SOUTH -> Direction.EAST;
                case WEST -> Direction.NORTH;
                default -> throw new IllegalStateException("Unexpected value: " + to);
            };
        }else if (nextSymbol == 'J'){
            toGoTo = switch (to){
                case SOUTH -> Direction.WEST;
                case EAST -> Direction.NORTH;
                default -> throw new IllegalStateException("Unexpected value: " + to);
            };
        }else if (nextSymbol == '7'){
            toGoTo = switch (to){
                case NORTH -> Direction.WEST;
                case EAST -> Direction.SOUTH;
                default -> throw new IllegalStateException("Unexpected value: " + to);
            };
        } else if (nextSymbol == 'F'){
            toGoTo = switch (to){
                case NORTH -> Direction.EAST;
                case WEST -> Direction.SOUTH;
                default -> throw new IllegalStateException("Unexpected value: " + to);
            };
        } else {
            toGoTo = null;
        }

        return new Pipe(x+ to().toX(), y+ to.toY(), Direction.invert(to), toGoTo);

    }

}

record Loop(LinkedList<Pipe> loop) {
    static Loop fromLines(List<String> lines) {
        var likedList = new LinkedList<Pipe>();
        char tmp = '.';
        int line = 0, column = 0;

        //Searching for the entrance
        for (; line < lines.size() && tmp != 'S'; line++) {
            var string = lines.get(line);
            for (column = 0; column < string.length() && tmp != 'S'; column++) {
                tmp = string.charAt(column);
            }
        }

        line--;
        column--;
        System.out.println("tmp = " + tmp + " in " + line + ", " + column);

        //Searching for one direction

        //is S under next direction?
        if (line >= 0
                && lines.get(line - 1).charAt(column) != '.'
                && lines.get(line - 1).charAt(column) != 'J'
                && lines.get(line - 1).charAt(column) != 'L'
                && lines.get(line - 1).charAt(column) != '-') {
            char next = lines.get(line - 1).charAt(column);

            Direction from = Direction.SOUTH;

            Direction to = switch (next) {
                case '|' -> Direction.NORTH;
                case 'F' -> Direction.WEST;
                case '7' -> Direction.EAST;
                default -> throw new IllegalStateException("Unexpected value: " + next);
            };
            likedList.add(new Pipe(line, column, from, to));


        } //is S over next direction?
        else if (line + 1 < lines.size()
                && lines.get(line + 1).charAt(column) != '.'
                && lines.get(line + 1).charAt(column) != '-'
                && lines.get(line + 1).charAt(column) != 'F'
                && lines.get(line + 1).charAt(column) != 'J') {
            char next = lines.get(line + 1).charAt(column);

            Direction from = Direction.NORTH;

            Direction to = switch (next) {
                case '|' -> Direction.SOUTH;
                case 'J' -> Direction.WEST;
                case 'L' -> Direction.EAST;
                default -> throw new IllegalStateException("Unexpected value: " + next);
            };
            likedList.add(new Pipe(line, column, from, to));

        } //is S on the right of next direction?
        else if (column >= 0
                && lines.get(line).charAt(column -1) != '.'
                && lines.get(line).charAt(column -1) != 'F'
                && lines.get(line).charAt(column -1) != 'L'
                && lines.get(line).charAt(column -1) != '|') {
            char next = lines.get(line).charAt(column -1);

            Direction from = Direction.WEST;

            Direction to = switch (next) {
                case '-' -> Direction.EAST;
                case 'J' -> Direction.NORTH;
                case '7' -> Direction.SOUTH;
                default -> throw new IllegalStateException("Unexpected value over S: " + next);
            };
            likedList.add(new Pipe(line, column, from, to));


        } //is S over next direction?
        else if (column + 1 < lines.get(0).length()
                && lines.get(line).charAt(column + 1) != '.'
                && lines.get(line ).charAt(column+ 1) != '|'
                && lines.get(line ).charAt(column+ 1) != 'J'
                && lines.get(line ).charAt(column+ 1) != '7') {
            char next = lines.get(line ).charAt(column + 1);

            Direction from = Direction.EAST;

            Direction to = switch (next) {
                case '-' -> Direction.WEST;
                case 'F' -> Direction.SOUTH;
                case 'L' -> Direction.NORTH;
                default -> throw new IllegalStateException("Unexpected value under S: " + next);
            };
            likedList.add(new Pipe(line, column, from, to));

        }

        while (likedList.getLast().to() != null){
            likedList.add(likedList.getLast().nextInLine(lines));
        }

        return new Loop(likedList);
    }
}

public class Day10 {
    public static void main(String[] args) {
        //var lines = Utils.listFromFile("inputs/demo.txt");
        var lines = Utils.listFromFile("inputs/y2023/day10_input.txt");

        var result_p1 = (Loop.fromLines(lines).loop().stream()
                .peek(System.out::println)
                .count()) /2 ;

        System.out.println("result_p1 = " + result_p1);
    }
}

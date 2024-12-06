package y2024;

import all.LongPair;
import all.Utils;

import java.util.ArrayList;

enum Direction {
    UP(new LongPair(-1, 0)), DOWN(new LongPair(1, 0)), LEFT(new LongPair(0, -1)), RIGHT(new LongPair(0, 1));

    private final LongPair toAdd;

    Direction(LongPair toAdd){
        this.toAdd = toAdd;
    }

    public LongPair add(LongPair position){
        return new LongPair(position.a()+this.toAdd.a(), position.b()+this.toAdd.b());
    }

    public Direction clockwise(){
        return switch (this){
            case UP -> RIGHT;
            case RIGHT -> DOWN;
            case DOWN -> LEFT;
            case LEFT -> UP;
        };
    }
}

class GuardMap{
    private LongPair position;
    private final ArrayList<LongPair> obstacles;
    private final ArrayList<LongPair> visited;
    long maxLine;
    long maxColumn;

    Direction direction;

    public GuardMap(LongPair position, ArrayList<LongPair> obstacles, long maxLine, long maxColumn, Direction direction) {
        this.position = position;
        this.obstacles = obstacles;
        this.maxLine = maxLine;
        this.maxColumn = maxColumn;
        this.direction = direction;
        this.visited = new ArrayList<>();
    }

    public String toString(){
        System.out.println((char)27 + "[36m"+" ______________________ "+(char)27 + "[39m");

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < maxLine; i++){
            for (int j = 0; j < maxColumn; j++){
                var current = new LongPair(i, j);

                if (current.equals(position)){
                    sb.append((char)27 + "[42m"+(char)27 + "[30m"+ 'X' +(char)27 + "[49m"+(char)27 + "[39m");
                }
                else if (obstacles.contains(current)) {
                    sb.append((char)27 + "[41m"+ '#' +(char)27 + "[49m");
                } else {
                    sb.append('.');
                }
            }
            sb.append("\n");
        }

        return sb.toString();
    }

    private void addVisited(LongPair p){
        if (!visited.contains(p)){
            visited.add(p);
        }
    }

    public static GuardMap fromGrig(ArrayList<String> grid){
        LongPair position = null;
        ArrayList<LongPair> obstacles = new ArrayList<>();
        Direction direction = null;

        for (int line = 0; line < grid.size(); line++){
            for (int column = 0; column < grid.get(line).length(); column++){
                char toProcess = grid.get(line).charAt(column);

                if (toProcess == '.'){
                    continue;
                }

                LongPair point = new LongPair(line, column);

                if(toProcess == '#'){
                    obstacles.add(new LongPair(line, column));
                } else {
                    position = point;
                    direction = switch (toProcess){
                        case '^' -> Direction.UP;
                        case '<' -> Direction.LEFT;
                        case '>' -> Direction.RIGHT;
                        case 'v' -> Direction.DOWN;
                        default -> throw new IllegalArgumentException();
                    };
                }
            }
        }

        return new GuardMap(position, obstacles, grid.size(), grid.get(0).length(), direction);
    }

    private boolean step(){
        //System.out.println(this);
        var toGo = direction.add(position);

        if (toGo.a() < 0 || toGo.a() >= maxLine || toGo.b() < 0 || toGo.b() >= maxColumn){
            return true;
        }

        else if (obstacles.contains(toGo)){
            direction = direction.clockwise();
            toGo = direction.add(position);
        }

        position = toGo;
        addVisited(position);
        return false;

    }

    public long walk(){
        while (!step()){
        }
        return visited.size();
    }
}
public class Day6 {
    public static void main(String[] args) {
        System.out.println("part1 = " + GuardMap.fromGrig(Utils.listForDay(2024, 6)).walk());
    }

}

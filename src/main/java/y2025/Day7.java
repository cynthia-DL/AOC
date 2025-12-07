package y2025;

import all.AbstractGrid;
import all.Utils;
import kotlin.Pair;

import java.awt.*;
import java.math.BigInteger;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day7 {
    static class TachyonManifold extends AbstractGrid<Boolean> { //Serieux c'est quoi ce nom??
        private final int start;

        TachyonManifold (int height, int width, int start){
            super(height, width);
            this.start = start;
        }

        static TachyonManifold fromFile(List<String> input){
            int start = -1;
            //Find start first
            String line = input.get(0);
            for (int i = 0; i < line.length() && start < 0; i++){
                if(line.charAt(i) == 'S') start = i;
            }

            var result = new TachyonManifold(input.size(), line.length(), start);


            //Fill grid now
            for (int i= 0; i < input.size(); i++){
                line = input.get(i);
                for (int j = 0; j < line.length(); j++){
                    if (line.charAt(j) == '^') result.internal().get(i).add(j, true);
                    else result.internal().get(i).add(j, false);
                }
            }

            return result;
        }

        public long getBeams(){
            Set<Point> visited = new HashSet<>();
            getBeams(0, start, visited);

            visited.stream()
                    .distinct()
                    .count();

            return                     visited.stream()
                    .distinct()
                    .count();
        }

        private void getBeams(int line, int column, Set<Point> visited){
            System.out.println(String.format("getBeam(%d, %d)", line, column));
            if (line == height() -1){
                return;
            }

            visited.add(new Point(line, column));
            if(get(line, column)){
                getBeams(line+1, column-1, visited);
                getBeams(line+1, column+1, visited);
            }

            getBeams(line+1, column, visited);
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < height(); i++){
                for (int j = 0; j < width(); j++){
                    if (isValid(i, j)){
                        if(i == 0 && j == start) sb.append('S');
                        else if(get(i,j)) sb.append('^');
                        else sb.append('.');
                    }
                }
                sb.append('\n');
            }

            return sb.toString();
        }
    }


    public static void main(String[] args) {
        var input = Utils.listFromDemoFile();
        var grid = TachyonManifold.fromFile(input);

        System.out.println(grid);
        System.out.println("grid.getBeams() = " + grid.getBeams());
    }
}

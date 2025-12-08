package y2025;

import all.AbstractGrid;
import all.Utils;
import kotlin.collections.EmptySet;

import java.awt.Point;
import java.util.Arrays;
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
            boolean[] beams = new boolean[width()];

            beams[start] = true;

            var sb = new StringBuilder();

            for (int i = 0; i < height(); i++){
                for (int j = 0; j < width(); j++){
                    Boolean isSplitter = get(i, j);
                    if(isSplitter != null && isSplitter){
                        beams[j] = false;
                        if (j-1 >= 0) beams[j-1] = true;
                        if (j+1 < width()) beams[j+1] = true;
                    }
                }

                for (var beam: beams)
                   sb.append(beam ? '|' : '.');
                sb.append('\n');
            }


            long sum = 0;
            for (var beam:beams){
                sum += beam ? 1 : 0;
            }

            return sum;
        }

        @Override
        public String toString(){
            return toString(EmptySet.INSTANCE);
        }

        public String toString(Set<Point> splitted) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < internal().size(); i++){
                for (int j = 0; j < width(); j++){
                    if(i == 0 && j == start) sb.append('S');
                    else if(!splitted.isEmpty() && splitted.contains(new Point(i, j))) sb.append('^');
                    else if(splitted.isEmpty() && get(i,j)) sb.append('^');
                    else sb.append('.');
                }
                sb.append('\n');
            }

            return sb.toString();
        }
    }


    public static void main(String[] args) {
        var input = Utils.listFromDemoFile();
        //var input = Utils.listForDay(2025, 7);
        var grid = TachyonManifold.fromFile(input);

        System.out.println("grid.getBeams() = " + grid.getBeams());
    }
}

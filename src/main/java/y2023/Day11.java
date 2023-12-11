package y2023;

import all.Utils;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;


record Universe(ArrayList<String> map){
    static Universe fromLines(List<String> map){
        return new Universe(new ArrayList<String>(List.copyOf(map)));
    }

    private boolean columnIsEmpty(int column){
        for (int i = 0; i < map.size(); i++){
            if(map.get(i).charAt(column) == '#') return false;
        }

        return true;
    }

    void expends(){
        //expends lines
        for (int i = map.size()-1; i >= 0; i--){
            if (map.get(i).replaceAll("\\.", "").isEmpty()){
                map.add(i, map.get(i));
            }
        }
        
        //expends columns
        int size = map.get(0).length();
        for (int i = size -1; i >= 0; i--) {
            if (columnIsEmpty(i)){
                for (int j = 0; j < map.size(); j++){
                    var part1 = map.get(j).substring(0, i);
                    var part2 = map.get(j).substring(i);
                    map.set(j, part1+'.'+part2);
                }
            }
        }
    }
    long plusCourtChemin(Point a, Point b){
        return Math.abs(b.x - a.x) + Math.abs(b.y - a.y);
    }

    List<Point> galaxies(){
        var list = new ArrayList<Point>();

        for (int i = 0; i < map.size(); i++){
            for (int j = 0; j < map.get(i).length(); j++){
                if(map.get(i).charAt(j) =='#'){
                    list.add(new Point(i, j));
                }
            }
        }

        return list;
    }
}

public class Day11 {
    public static void main(String[] args) {
        //var lines = Utils.listFromFile("inputs/demo.txt");
        var lines = Utils.listFromFile("inputs/y2023/day11_input.txt");

        var universe = Universe.fromLines(lines);
        universe.expends();


        for (int i = 0; i < universe.map().size(); i++) {
            var sb = new StringBuilder();

            sb.append(universe.map().get(i));
            sb.append(" * ");
            if(i < lines.size())
                sb.append(lines.get(i));

            System.out.println(sb);
        }

        var galaxies = universe.galaxies();

        long sum = 0;

        for (int i = 0; i < galaxies.size(); i++){
            for (int j = i+1; j < galaxies.size(); j++){
                sum+= universe.plusCourtChemin(galaxies.get(i), galaxies.get(j));
            }
        }

        System.out.println("sum = " + sum);

    }
}

package y2023;

import all.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

record Galaxy(ArrayList<String> map){
    static Galaxy fromLines(List<String> map){
        return new Galaxy(new ArrayList<String>(List.copyOf(map)));
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
}

public class Day11 {
    public static void main(String[] args) {
        var lines = Utils.listFromFile("inputs/demo.txt");



        var galaxy = Galaxy.fromLines(lines);
        galaxy.expends();


        for (int i = 0; i < galaxy.map().size(); i++) {
            var sb = new StringBuilder();

            sb.append(galaxy.map().get(i));
            sb.append(" * ");
            if(i < lines.size())
                sb.append(lines.get(i));

            System.out.println(sb.toString());
        }





    }
}

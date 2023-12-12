package y2023;

import all.Utils;

import java.util.ArrayList;
import java.util.Arrays;

record SpringLine(String line, ArrayList<Long> damagedClusters){
    static SpringLine fromLine(String line){
        var array = line.split(" ");
        var damagedClusters = Arrays.stream(array[1].split(",")).mapToLong(Long::parseLong).boxed().toList();

        return new SpringLine(array[0], new ArrayList<Long>(damagedClusters));
    }

    long possibilities(){
        long possibilities = 0;
        long nbQuestionMarks = line.replaceAll("\\.", "").replaceAll("#", "").length();
    
        for (long i = 0; i < Math.pow(2, nbQuestionMarks); i++){
            long pos = 0;
            String tmp = line;
            for (long j = 0; j < line.length(); j++){
                if (line.charAt((int) j) == '?'){
                    var p1 = tmp.substring(0, (int) (j));
                    var p2 = tmp.substring((int) j+1, line.length());

                    long mask = 1L << (long) pos;
                    char thisChar = ((i & mask) ) != 0 ? '#' : '.';
                    
                    tmp = p1+thisChar+p2;
                    pos++;
                }
            }

            //System.out.println("tmp = " + tmp);
            while (tmp.contains("..")){
                tmp = tmp.replaceAll("\\.\\.", ".");
            }

            if (tmp.startsWith(".")) tmp = tmp.substring(1);

            var array = tmp.split("\\.");

            if (array.length != damagedClusters.size())
                continue;

            boolean flag = true;
            for (int k = 0; k < damagedClusters.size() && flag; k++){
                if (array[k].length() != damagedClusters.get(k)) {
                    flag = false;
                }
            }

            possibilities += flag ? 1 : 0;


        }
        
        return possibilities;
    }
}
public class Day12 {
    public static void main(String[] args) {
        //var lines = Utils.listFromFile("inputs/demo.txt");
        var lines = Utils.listFromFile("inputs/y2023/day12_input.txt");

        
        var result_p1 = lines.stream()
                .map(SpringLine::fromLine)
                .mapToLong(SpringLine::possibilities)
                //.peek(System.out::println)
                .sum();

        System.out.println("result_p1 = " + result_p1);
    }
}

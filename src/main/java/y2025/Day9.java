package y2025;

import all.LongPair;
import all.Utils;

import java.util.ArrayList;
import java.util.List;

public class Day9 {
    private static long computeLedArea(LongPair p1, LongPair p2){
        return (Math.abs(p1.a()-p2.a())+1) * (Math.abs(p1.b()-p2.b())+1); //j'aurais du les appeler x et y smh
    }

    public static long process1(List<String> inputs){
        List<LongPair> points = new ArrayList<>();

        for(var line : inputs){
            var tmp = line.split(",");
            points.add(new LongPair(Long.valueOf(tmp[0]), Long.valueOf(tmp[1])));
        }

        long result = 0;

        for (int i = 0; i < points.size(); i++){
            for (int j = 0; j < points.size(); j++){
                if (i == j) continue;

                long tmp = computeLedArea(points.get(i), points.get(j));

                if(tmp > result) result = tmp;

            }
        }

        return result;
    }

    public static void main(String[] args) {
        //var input = Utils.listFromDemoFile();
        var input = Utils.listForDay(2025, 9);

        System.out.println("process1(input) = " + process1(input));
    }
}
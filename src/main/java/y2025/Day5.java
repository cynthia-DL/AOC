package y2025;

import all.Utils;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day5 {
    record Range(BigInteger start, BigInteger finish){
        public boolean contains(BigInteger value){
            //value >= start && value <= finish
            return value.compareTo(start) >= 0 && value.compareTo(finish) <= 0;
        }

        public static Range fromString(String input){
            var array = input.split("-");
            return new Range(new BigInteger(array[0]), new BigInteger(array[1]));
        }
    }

    static class FoodDatabase {
        Set<Range> ranges = new HashSet<>();
        Set<String> fresh = new HashSet<String>();

        public int computeIds(List<String> input){
            String line = input.get(0);
            int i;

            for (i = 1; !line.isEmpty(); i++){
                ranges.add(Range.fromString(line));
                line = input.get(i);
            }

            for (; i<input.size(); i++){
                int finalI = i;
                var contains = ranges.stream()
                        .filter(r -> r.contains(new BigInteger(input.get(finalI))))
                        .findAny();

                if (contains.isPresent()) {
                    fresh.add(input.get(i));
                }
            }

            return fresh.size();

        }
    }

    public static void main(String[] args) {
        //var input = Utils.listFromDemoFile();
        var input = Utils.listForDay(2025, 5);
        var database = new FoodDatabase();

        System.out.println("database.computeIds(input) = " + database.computeIds(input));
    }
}

package y2024;

import all.Utils;

import java.util.Arrays;

public class Day2 {

    private static boolean isStepIncorrect(long l1, long l2){
        var  difference = l2 - l1;
        return difference != 1 && difference != 2 && difference != 3;
    }

    private static boolean isStepOfMost3(long[] levels){
        if(isIncreasing(levels)){
            for (int i = 1; i < levels.length; i++){
                if (isStepIncorrect(levels[i - 1], levels[i])){
                    return false;
                }
            }
            return true;
        }

        for (int i = levels.length - 1; i > 0; i--){
            if (isStepIncorrect(levels[i], levels[i - 1])){
                return false;
            }
        }
        return true;
    }

    private  static  boolean isIncreasing(long[] levels){
        var sorted = Arrays.stream(levels).sorted().toArray();

        return Arrays.equals(sorted, levels);
    }

    private  static  boolean isDecreasing(long[] levels){
        for (int i = 1; i < levels.length; i++){
            if (levels[i-1] <= levels[i]){
                return false;
            }
        }
        return true;
    }

    private static boolean isSafe(long[] levels){
        return ((isIncreasing(levels) || isDecreasing(levels)) && isStepOfMost3(levels));
    }

    public static void main(String[] args) {
        //var input =  Utils.listFromDemoFile();
        var input =  Utils.listForDay(2024, 2);
        long res1 = 0L;
        long res2 = 0L;

        for (var report : input){
            var reportArray = Arrays.stream(report.split(" "))
                    .mapToLong(Long::valueOf)
                    .toArray();

            if (isSafe(reportArray)){
                res1++;
                res2++;
            } else {
                var withoutOne = new long[reportArray.length-1];
                for (int i = 0; i < reportArray.length; i++){
                    var shift = 0;
                    for (int j = 0; j < withoutOne.length; j++){
                        if(j == i){
                           shift = 1;
                        }

                        withoutOne[j] = reportArray[j+shift];
                    }

                    if (isSafe(withoutOne)){
                        res2++;
                        break;
                    }
                }

            }
        }

        System.out.println("res1 = " + res1);
        System.out.println("res2 = " + res2);
    }
}

package y2025;

import all.Utils;

import java.math.BigInteger;
import java.util.List;

public class Day3 {

    static BigInteger getGreaterNumber(String line){
        BigInteger biggest = BigInteger.ZERO;
        for (int i = 0; i < line.length(); i++){
            for (int j = i+1; j < line.length(); j++){
                String s = String.valueOf(line.charAt(i))+String.valueOf(line.charAt(j));
                BigInteger contestent = new BigInteger(s);

                if (contestent.compareTo(biggest) > 0) {
                    biggest = contestent;
                }
            }
        }
        return new BigInteger(biggest.toString());
    }

    public static void main(String[] args) {
        //List<String> inputs = Utils.listFromDemoFile();
        List<String> inputs = Utils.listForDay(2025, 3);

        BigInteger voltage = BigInteger.ZERO;

        for (var line : inputs){
            voltage = voltage.add(getGreaterNumber(line));
        }

        System.out.println("voltage = " + voltage);
    }
}

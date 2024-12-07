package y2024;

import all.Utils;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

record Equation(BigInteger result, ArrayList<BigInteger> components){
    public static Equation fromLine(String line){
        var split = line.split("(: )|( )");
        var component = new ArrayList<BigInteger>();

        for (int i = 1; i < split.length; i++){
            component.add(new BigInteger(split[i]));
        }

        return new Equation(new BigInteger(split[0]), component);
    }

    private static ArrayList<BigInteger> possibilities(ArrayList<BigInteger> components){
        if (components.size() == 1){
            return components;
        }

        var toReturn = new ArrayList<BigInteger>();
        int current = components.size() - 1;
        var currentBigInt = components.get(current);
        var listToCompute = new ArrayList<>(List.copyOf(components));
        listToCompute.remove(current);
        listToCompute = possibilities(listToCompute);

        for (var toCompute : listToCompute){
            toReturn.add(toCompute.add(currentBigInt));
            toReturn.add(toCompute.multiply(currentBigInt));
        }

        return toReturn;
    }

    public BigInteger compute(){
       return possibilities(components).contains(result) ? result : new BigInteger("0");

    }
}
public class Day7 {

    public static void main(String[] args) {
        //var input = Utils.listFromDemoFile();
        var input = Utils.listForDay(2024, 7);

        var part1 = input.stream()
                .map(Equation::fromLine)
                .map(Equation::compute)
                .reduce(BigInteger::add);

        System.out.println("part1 = " + part1.get());
    }
}

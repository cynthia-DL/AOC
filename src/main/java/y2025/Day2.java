package y2025;

import all.Utils;
import kotlin.Pair;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day2 {

    static class IdFinder{
        private final Set<Pair<BigInteger, BigInteger>> ranges;

        private IdFinder(Set<Pair<BigInteger, BigInteger>> ranges){
            this.ranges = ranges;
        }

        static IdFinder fromInput(String input){
            var ranges = new HashSet<Pair<BigInteger, BigInteger>>();
            var parts = input.split(",");
            for (String line: parts){
                var splited = line.strip().split("-");
                ranges.add(new Pair<>(new BigInteger(splited[0]), new BigInteger(splited[1])));

            }

            return new IdFinder(ranges);
        }

        public BigInteger process1(){
            BigInteger sum = BigInteger.ZERO;

            for (var range : ranges){
                // for i = firstId ; i <= lastId; i++
                ///  oui je sais c'est vénère de fou mais tkt
                for (BigInteger i = range.component1(); i.compareTo(range.component2()) <= 0; i = i.add(BigInteger.ONE)){
                    if (i.toString().length() % 2 != 0){
                        continue; //don't compute if we can't make double sequences
                    }

                    String toString = i.toString();

                    String subPart1 = toString.substring(0, toString.length()/2);
                    String subPart2 = toString.substring(toString.length()/2);

                    if(subPart1.equals(subPart2)) {
                        sum = sum.add(i);
                        System.out.println(i);
                    }
                }
            }

            return sum;
        }

        public BigInteger process2(){
            BigInteger sum = BigInteger.ZERO;

            for (var range : ranges){
                // for i = firstId ; i <= lastId; i++
                for (BigInteger i = range.component1(); i.compareTo(range.component2()) <= 0; i = i.add(BigInteger.ONE)){
                    var toAdd = new HashSet<BigInteger>();
                    int numberSize = i.toString().length();
                    for (int j = 1; j <= numberSize/2; j++){
                        if (numberSize%j != 0){
                            continue;
                        }

                        var list = chop(i.toString(), j);
                        var withness = list.remove(0);
                        boolean repeats = true;
                        for (String s : list) {
                            if (!s.equals(withness)) {
                                repeats = false;
                                break;
                            }
                        }

                        if (repeats) {
                            toAdd.add(i);
                        }
                    }
                    var all = toAdd.stream().reduce(BigInteger.ZERO, BigInteger::add);
                    sum = sum.add(all);
                }
            }

            return sum;
        }

        private List<String> chop(String input, int chunkSize){
            var result = new ArrayList<String>();
            for (int i = 0; i < input.length(); i+=chunkSize){
                result.add(input.substring(i, i+chunkSize));
            }
            return result;
        }

    }


    public static void main(String[] args) {
        var input = Utils.listForDay(2025, 2).get(0);
        //var input = Utils.listFromDemoFile().get(0);
        var idFinder = IdFinder.fromInput(input);

        System.out.println("idFinder.process1() = " + idFinder.process1());
        System.out.println("idFinder.process2() = " + idFinder.process2());
    }
}

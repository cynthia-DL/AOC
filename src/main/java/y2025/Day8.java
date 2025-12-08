package y2025;

import all.Utils;

import java.math.BigInteger;
import java.util.*;

public class Day8 {
    static class BigInteger3DPoint{
        private final BigInteger x;
        private final BigInteger y;
        private final BigInteger z;

        public BigInteger distance(BigInteger3DPoint b){
            BigInteger3DPoint a = this;

            BigInteger p1 = a.x.min(b.x).pow(2);
            BigInteger p2 = a.y.min(b.y).pow(2);
            BigInteger p3 = a.z.min(b.z).pow(2);

            return p1.add(p2).add(p3).sqrt();
        }

        public static BigInteger3DPoint fromString(String input){
            var splitted = input.trim().split(",");
            return new BigInteger3DPoint(
                    new BigInteger(splitted[0]),
                    new BigInteger(splitted[1]),
                    new BigInteger(splitted[2])
            );
        }

        // Under are equals, hashcode, getters and setters
        @Override
        public boolean equals(Object o) {
            if (!(o instanceof BigInteger3DPoint that)) return false;
            return Objects.equals(x, that.x) && Objects.equals(y, that.y) && Objects.equals(z, that.z);
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y, z);
        }

        public BigInteger getX() {
            return x;
        }

        public BigInteger getY() {
            return y;
        }

        public BigInteger getZ() {
            return z;
        }

        public BigInteger3DPoint(BigInteger x, BigInteger y, BigInteger z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        @Override
        public String toString() {
            return "<"+x+", "+y+", "+z+">";
        }
    }

    static BigInteger part1(List<String> input){
        var map = new HashMap<BigInteger3DPoint, List<BigInteger3DPoint>>();

        for (var line : input){
            map.put(BigInteger3DPoint.fromString(line), new ArrayList<>());
        }

        BigInteger3DPoint closest = null;
        BigInteger3DPoint currentPoint;

        for (var entry : map.entrySet()){
            BigInteger distance = null;
            currentPoint = entry.getKey();
            for (var other:map.keySet()){
                if(distance == null || distance.compareTo(currentPoint.distance(other)) > 0){
                    closest = other;
                    distance = currentPoint.distance(other);
                }
            }

            var arrayList = map.get(closest);
            arrayList.add(currentPoint);
            map.put(currentPoint, arrayList);
        }

        //System.out.println(map);
        var sums = map.values()
            .stream()
            .distinct()
            .map(List::size)
            .sorted(Comparator.reverseOrder())
            .toList();

        System.out.println(sums);

        return new BigInteger(String.valueOf(sums.get(0)*sums.get(1)*sums.get(2)));
    }

    public static void main(String[] args) {
        var input = Utils.listFromDemoFile();

        System.out.println("part1(input) = " + part1(input));
    }
}

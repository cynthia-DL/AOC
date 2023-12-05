package y2023;

import all.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

record Range(long destinationStart, long sourceStart, long length){
    static Range fromLine(String line){
        var array = line.replace("  ", " ").split(" ");
        return new Range(Long.parseLong(array[0]),Long.parseLong(array[1]),Long.parseLong(array[2]));
    }

    long passThrough(long source){
        if (source >= sourceStart && source < sourceStart+length)
            return destinationStart + (source-sourceStart);
        return source;
    }
}
record Pipe(ArrayList<Range> ranges){
    static Pipe fromLines(List<String> lines){
        var ranges = new ArrayList<Range>();

        lines.remove(0);
        
        String line = lines.remove(0);
        while (!line.isEmpty()){
            ranges.add(Range.fromLine(line));
            if (!lines.isEmpty())
                line = lines.remove(0);
            else
                line = "";
        }

        return new Pipe(ranges);
    }

    long apply(long source){
        long toReturn = source;

        for (var range : ranges){
            toReturn = range.passThrough(toReturn);
            if (source != toReturn) return toReturn;
        }

        return toReturn;
    }

    long[] applyAll(long [] sources){
        return Arrays.stream(sources).map(this::apply).toArray();
    }

    @Override
    public String toString() {

        var sb = new StringBuilder();

        sb.append("Pipe with ranges :\n");

        for (var range : ranges){
            sb.append(range).append('\n');
        }

        return sb.toString();
    }
}
public class Day5 {
    public static void main(String[] args) {
        var lines = Utils.listFromFile("inputs/y2023/day5_input.txt");
        //var lines = Utils.listFromFile("inputs/demo.txt");

        var seeds = Arrays.stream(lines.get(0).split(":")[1].trim().split(" ")).mapToLong(Long::parseLong).toArray();

        System.out.println("seeds = " + Arrays.toString(seeds));

        lines.subList(0, 2).clear();

        var pipes = new ArrayList<Pipe>();

        while (!lines.isEmpty()){
            pipes.add(Pipe.fromLines(lines));
        }

        long [] array_result_p1 = seeds.clone();

        for (var pipe : pipes){
            array_result_p1 = pipe.applyAll(array_result_p1);
            System.out.println("through "+pipe+" = "+Arrays.toString(array_result_p1));
        }

        System.out.println("array_result_p1 = "+Arrays.toString(array_result_p1));
        System.out.println("result_p1 ="+Arrays.stream(array_result_p1).min());
    }
}

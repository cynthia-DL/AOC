package y2023;

import all.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

record RangeSeed (long start, long end){
    static List<RangeSeed> fromLine(String line){
        var seeds = Arrays.stream(line.split(":")[1].trim().split(" ")).mapToLong(Long::parseLong).toArray();

        List<RangeSeed> ranges  = new ArrayList<>();


        for (int i = 0; i < seeds.length; i+=2){
            ranges.add(new RangeSeed(seeds[i], seeds[i]+seeds[i+1]-1));
        }

        return ranges;
    }
}
record RangeMaps(long destinationStart, long destinationEnd, long sourceStart, long sourceEnd){
    static RangeMaps fromLine(String line){
        var array = line.replace("  ", " ").split(" ");
        long dest = Long.parseLong(array[0]);
        long source = Long.parseLong(array[1]);
        long length = Long.parseLong(array[2]);
        return new RangeMaps(dest, dest+length, source, source+length);
    }

    private long map(long source){
        return destinationStart + (source-sourceStart);
    }

    long passThrough(long source){
        if (source >= sourceStart && source < sourceEnd)
            return map(source);
        return source;
    }

    List<RangeSeed> passThrough(RangeSeed seed){
        var ranges = new ArrayList<RangeSeed>();

        // If seed not in range value is the same
        if(seed.end() <= sourceStart || seed.start() > sourceEnd){
            ranges.add(new RangeSeed(seed.start(), seed.end()));
        }

        // if seed fully in range, apply to everyone
        // is ok
        else if(seed.start() >= sourceStart && seed.end() <= sourceEnd){
            ranges.add(new RangeSeed(map(seed.start()), map(seed.end())));
        }

        // if range fully in seed, apply to a part in the center
        // [seed][range][seed]
        else if (seed.start() < sourceStart && seed.end() > sourceEnd){
            ranges.add(new RangeSeed(seed.start(), sourceStart-1));
            ranges.add(new RangeSeed(destinationStart, destinationEnd-1));
            ranges.add(new RangeSeed(sourceEnd, seed.end()));
        }

        // if seed and map intersects, add multiple range to map
        // [map][seed]
        else if(sourceStart <= seed.start()){
            ranges.add(new RangeSeed(map(seed.start()), destinationEnd));
            ranges.add(new RangeSeed(sourceEnd+1, seed.end()));

        }
        // if [seed][map]
        else {
            ranges.add(new RangeSeed(seed.start(), sourceStart-1));
            ranges.add(new RangeSeed(destinationStart, map(seed.end())));
        }

        System.out.println(seed+" through " + this+" => "+  (seed.end() - seed.start()) +" = "+ranges.stream().mapToLong(s -> s.end() - s.start()).sum());

        return ranges;
    }
}
record Pipe(ArrayList<RangeMaps> ranges){
    static Pipe fromLines(List<String> lines){
        var ranges = new ArrayList<RangeMaps>();

        lines.remove(0);
        
        String line = lines.remove(0);
        while (!line.isEmpty()){
            ranges.add(RangeMaps.fromLine(line));
            if (!lines.isEmpty())
                line = lines.remove(0);
            else
                line = "";
        }

        return new Pipe(ranges);
    }

    private long apply(long source){
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

    List<RangeSeed> applyRangeSeed(List<RangeSeed> seeds){
        ArrayList<RangeSeed> computedRanges = new ArrayList<>(List.copyOf(seeds));

        for (var range: ranges){
            var lookout = List.copyOf(computedRanges);
            computedRanges = new ArrayList<>();
            for (var seed : lookout){
                computedRanges.addAll(range.passThrough(seed));
            }
        }

        return computedRanges;
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
        //var lines = Utils.listFromFile("inputs/y2023/day5_input.txt");
        var lines = Utils.listFromFile("inputs/demo.txt");

        var seeds = Arrays.stream(lines.get(0).split(":")[1].trim().split(" ")).mapToLong(Long::parseLong).toArray();

        var rangeSeed = RangeSeed.fromLine(lines.get(0));

        lines.subList(0, 2).clear();

        var pipes = new ArrayList<Pipe>();

        while (!lines.isEmpty()){
            pipes.add(Pipe.fromLines(lines));
        }

        var array_result_p1 = Arrays.stream(seeds)
                .mapToObj(s -> new RangeSeed(s, s))
                .toList();
        
        var result_p2 = List.copyOf(rangeSeed);

        for (var pipe : pipes){
            //array_result_p1 = pipe.applyRangeSeed(array_result_p1);
            result_p2 = pipe.applyRangeSeed(result_p2);

        }

        System.out.println("result_p2 = " + result_p2);

        System.out.println("result_p1 ="+array_result_p1.stream().mapToLong(RangeSeed::start).min());
        System.out.println("result_p2 ="+result_p2.stream().mapToLong(RangeSeed::start).min());

    }
}

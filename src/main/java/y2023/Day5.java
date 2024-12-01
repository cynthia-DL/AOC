package y2023;

/*
record Seed(long start, long end){
    static List<Seed> fromLine(String line){
        var seeds = Arrays.stream(line.split(":")[1].trim().split(" ")).mapToLong(Long::parseLong).toArray();

        List<Seed> ranges  = new ArrayList<>();


        for (int i = 0; i < seeds.length; i+=2){
            ranges.add(new Seed(seeds[i], seeds[i]+seeds[i+1]-1));
        }

        return ranges;
    }
}
record Mapper(long destinationStart, long destinationEnd, long sourceStart, long sourceEnd){
    static Mapper fromLine(String line){
        var array = line.replace("  ", " ").split(" ");
        long dest = Long.parseLong(array[0]);
        long source = Long.parseLong(array[1]);
        long length = Long.parseLong(array[2]);
        return new Mapper(dest, dest+length, source, source+length);
    }

    private long map(long source){
        return destinationStart + (source-sourceStart);
    }

    long passThrough(long source){
        if (source >= sourceStart && source < sourceEnd)
            return map(source);
        return source;
    }

    List<Seed> passThrough(Seed seed){
        var ranges = new ArrayList<Seed>();

        // If seed not in range value is the same
        if(seed.end() <= sourceStart || seed.start() >= sourceEnd){
            ranges.add(new Seed(seed.start(), seed.end()));
        }

        // if seed fully in range, apply to everyone
        // is ok
        else if(seed.start() >= sourceStart && seed.end() <= sourceEnd){
            ranges.add(new Seed(map(seed.start()), map(seed.end())));
        }

        // if range fully in seed, apply to a part in the center
        // [seed][range][seed]
        else if (seed.start() <= sourceStart && seed.end() >= sourceEnd){
            ranges.add(new Seed(seed.start(), sourceStart));
            ranges.add(new Seed(destinationStart, destinationEnd));
            ranges.add(new Seed(sourceEnd, seed.end()));
        }

        // if seed and map intersects, add multiple range to map
        // [map][seed]
        else if(sourceStart <= seed.start()){
            ranges.add(new Seed(map(seed.start()), destinationEnd));
            ranges.add(new Seed(sourceEnd+1, seed.end()));

        }
        // if [seed][map]
        else {
            ranges.add(new Seed(seed.start(), sourceStart));
            ranges.add(new Seed(destinationStart, map(seed.end())));
        }

        System.out.println("\t\t"+seed+" => "+ ranges);

        return ranges;
    }
}

record Pipe(ArrayList<Mapper> ranges){
    static Pipe fromLines(List<String> lines){
        var ranges = new ArrayList<Mapper>();

        lines.remove(0);
        
        String line = lines.remove(0);
        while (!line.isEmpty()){
            ranges.add(Mapper.fromLine(line));
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

    List<Seed> applyRangeSeed(List<Seed> seeds){
        var currentSeeds = List.copyOf(seeds);
        ArrayList<Seed> computedSeeds = new ArrayList<>();

        for (var range: ranges){
            System.out.println("\trange = " + range);
            for (var seed : currentSeeds){
                computedSeeds.addAll(range.passThrough(seed));
            }

            currentSeeds = computedSeeds;
            computedSeeds = new ArrayList<>();
        }

        return currentSeeds;
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
 */
public class Day5 {
    public static void main(String[] args) {
        /*
        //var lines = Utils.listFromFile("inputs/y2023/day5_input.txt");
        var lines = Utils.listFromFile("inputs/demo.txt");

        var seeds = Arrays.stream(lines.get(0).split(":")[1].trim().split(" ")).mapToLong(Long::parseLong).toArray();

        var rangeSeed = Seed.fromLine(lines.get(0));

        lines.subList(0, 2).clear();

        var pipes = new ArrayList<Pipe>();

        while (!lines.isEmpty()){
            pipes.add(Pipe.fromLines(lines));
        }

        var array_result_p1 = Arrays.stream(seeds)
                .mapToObj(s -> new Seed(s, s+1))
                .toList();
        
        //var result_p2 = List.copyOf(rangeSeed);

        for (var pipe : pipes){
            System.out.println("pipe = " + pipe);
            array_result_p1 = pipe.applyRangeSeed(array_result_p1);
            System.out.println("array_result_p1 = " + array_result_p1);
            System.out.println("*************************");
            //result_p2 = pipe.applyRangeSeed(result_p2);

        }

        System.out.println("result_p1 ="+array_result_p1.stream().mapToLong(Seed::start).min());
        //System.out.println("result_p2 ="+result_p2.stream().mapToLong(Seed::start).min());

         */
        System.out.println("To correct");
    }
}

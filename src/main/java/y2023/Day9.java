package y2023;

import all.Utils;

import java.util.ArrayList;
import java.util.Arrays;

record Sequence(long[] original){
    static Sequence fromLine(String line){
        return new Sequence(Arrays.stream(line.replaceAll(" {2}", " ").split(" "))
                .mapToLong(Long::parseLong).toArray());
    }

    private long[] differentialSequence(long[] old){
        long[] newArray = new long[old.length-1];

        for (int i = 0; i < newArray.length; i++){
            newArray[i] = old[i+1] - old[i];
        }

        return newArray;
    }

    private boolean ifNotFullOfZero(long[] toCheck){
        for (var item: toCheck){
            if (item != 0L) return true;
        }

        return false;
    }

    long futurPrediction(){
        ArrayList<Long> predictions = new ArrayList<>();
        ArrayList<long[]> sequences = new ArrayList<>();
        sequences.add(Arrays.copyOf(original, original.length));

        for (int i = 0; ifNotFullOfZero(sequences.get(i)); i++){
            sequences.add(differentialSequence(sequences.get(i)));
        }

        for (int i = 0; i < sequences.size(); i++){
            predictions.add(i == sequences.size()-1 ? 0L : -1L);
        }

        for (int i = predictions.size()-2; i >= 0; i--){
            var array = sequences.get(i);
            var toAdd = array[array.length-1]+predictions.get(i+1);
            predictions.set(i, toAdd);
        }

        return predictions.get(0);
    }

    long pastPrediction(){
        ArrayList<Long> predictions = new ArrayList<>();
        ArrayList<long[]> sequences = new ArrayList<>();
        sequences.add(Arrays.copyOf(original, original.length));

        for (int i = 0; ifNotFullOfZero(sequences.get(i)); i++){
            sequences.add(differentialSequence(sequences.get(i)));
        }

        for (int i = 0; i < sequences.size(); i++){
            predictions.add(i == sequences.size()-1 ? 0L : -1L);
        }

        for (int i = predictions.size()-2; i >= 0; i--){
            var array = sequences.get(i);
            var toAdd = array[0] - predictions.get(i+1);
            predictions.set(i, toAdd);
        }

        return predictions.get(0);
    }
    @Override
    public String toString() {
        return "Sequence{" +
                "original=" + Arrays.toString(original) +
                '}';
    }
}
public class Day9 {
    public static void main(String[] args) {
        //var lines = Utils.listFromFile("inputs/demo.txt");
        var lines = Utils.listFromFile("inputs/y2023/day9_input.txt");

        var result_p1 = lines.stream()
                .map(Sequence::fromLine)
                .mapToLong(Sequence::futurPrediction)
                .sum();        
        
        var result_p2 = lines.stream()
                .map(Sequence::fromLine)
                .mapToLong(Sequence::pastPrediction)
                .sum();

        System.out.println("result_p1 = " + result_p1);
        System.out.println("result_p2 = " + result_p2);
    }
}

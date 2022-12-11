package y2022;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class Day6 {
    public static boolean uniqueString(String str){
        var array = str.toCharArray();
        for (int i = 0; i < str.length(); i++){
            for (int j = 0; j < str.length(); j++){
                if (i != j && array[i] == array[j]){
                    return false;
                }
            }
        }
        return true;
    }

    public static void main(String[] args) {
        try (BufferedReader reader = Files.newBufferedReader(Path.of("./inputs/day6_input.txt"), StandardCharsets.UTF_8)) {
            String line = reader.readLine();
            System.out.println(line);

            for (int i = 4; i < line.length(); i++){
                var key = line.substring(i-4, i);
                if (Day6.uniqueString(key)){
                    System.out.println("key = "+i);
                    break;
                }
            }

            for (int i = 14; i < line.length(); i++){
                var key = line.substring(i-14, i);
                if (Day6.uniqueString(key)){
                    System.out.println("message = "+i);
                    break;
                }
            }
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
    }
}

package all;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

public class Utils {
    public static ArrayList<String> listFromFile(String path){
        var lignes = new ArrayList<String>();
        try (BufferedReader reader = Files.newBufferedReader(Path.of(path), StandardCharsets.UTF_8)) {
            String line = null;


            while ((line = reader.readLine()) != null){
                lignes.add(line);
            }

        } catch (IOException x) {
            throw new AssertionError(x);
        }

        return lignes;
    }

    public static void writeInFile(String path, String content){
        try (BufferedWriter writer = Files.newBufferedWriter(Path.of(path), StandardCharsets.UTF_8, StandardOpenOption.CREATE , StandardOpenOption.APPEND)){
            writer.write(content);
        } catch (IOException e){
            throw new AssertionError(e);
        }
    }

    public static boolean isParsable(char c){
       return isParsable(Character.toString(c));
    }

    public static boolean isParsable(String s){
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
}

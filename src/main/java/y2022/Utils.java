package y2022;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
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
            System.err.format("IOException: %s%n", x);
        }

        return lignes;
    }
}

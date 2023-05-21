package y2022;

import all.Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;

class Fichier {
    public String nom;
    public long taille;

    public Fichier(String nom, long taille) {
        this.nom = nom;
        this.taille = taille;
    }

    public static Fichier fromString(String str){
        var array = str.split(" ");
        return new Fichier(array[1], Integer.parseInt(array[0]));
    }

    public long taille() {
        return taille;
    }

    public String nom() {
        return nom;
    }

    @Override
    public String toString() {
        return "Fichier " + nom + "(" + taille + ')';
    }
}

class Dossier extends Fichier{
    public final ArrayList<Fichier> fils = new ArrayList<>();
    public final Dossier parent;

    Dossier(String nom, Dossier parent) {
        super(nom, 0);
        this.parent = parent;
    }

    public static Dossier fromString(String str, Dossier parent){
        var array = str.split(" ");
        return new Dossier(array[1], parent);
    }

    @Override
    public long taille(){
        int size = 0;

        for (var bruh : fils){
            size+= bruh.taille();
        }

        return size;
    }

    @Override
    public String toString() {
        return "Dossier{" +
                "nom=" + nom() + ", " +
                "taille=" + taille() + ", " +
                "fils=" + fils +
                '}';
    }
}

public class Day7 {
    public static void main(String[] args) {
        var lignes = Utils.listFromFile("./inputs/2022/day7_input.txt");

        Dossier root = new Dossier("/", null);
        HashSet<Dossier> existant = new HashSet<>();
        existant.add(root);
        Dossier cur = null;
        for (var ligne : lignes){
            if (ligne.charAt(0) == '$'){
                // System.out.println("ligne = " + ligne);

                var array = ligne.split(" ");

                if ("cd".equals(array[1])) {
                    switch (array[2]) {
                        case "/" -> cur = root;
                        case ".." -> cur = cur.parent == null ? root : cur.parent;
                        default -> {
                            Dossier finalCur = cur;
                            var doss = existant.stream()
                                    .filter(dossier -> dossier.nom.equals(array[2]) && dossier.parent.equals(finalCur)).findAny();

                            if (doss.isEmpty()) {
                                cur = new Dossier(array[2], cur);
                                existant.add(cur);
                            } else cur = doss.get();

                        }
                    }
                }

            } else if (ligne.charAt(0) == 'd') {
                var d = Dossier.fromString(ligne, cur);
                cur.fils.add(d);
                existant.add(d);
            } else cur.fils.add(Fichier.fromString(ligne));

        }

        //System.out.println("root = " + root);

        var occupiedSpace = root.taille();
        var unusedSpace = 70000000 - occupiedSpace;
        var spaceToFree = 30000000 - unusedSpace;

        System.out.println("unusedSpace = " + unusedSpace);
        System.out.println("spaceToFree = " + spaceToFree);
        System.out.println(existant.stream()
                .filter(d -> d.taille() >= spaceToFree)
                .sorted(Comparator.comparingLong(Dossier::taille))
                .peek(d -> System.out.println("delete "+d.nom+" unused space =  "+(spaceToFree + d.taille())))
                .findFirst().get().taille());

    }
}

package y2025;

import all.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class Day2 {

    static Collection<String> incorrectInputs(String ids){
        ArrayList<String> incorrectInputs = new ArrayList<>();

        System.out.println("ids = " + ids);
        System.out.println("ids.lengh = " + ids.length());

        for (int i = 0; i < ids.length(); i++){
            //System.out.println("i = " + i);
            for (int l = i+1; l <= ids.length(); l++) {
                //System.out.println("l = " + l);
                String substring = ids.substring(i, l);
                //System.out.println("substring = " + substring);
                String longuest = "";


                for (int j = l+1; j <= ids.length()+1; j++) {
                    for (int k = j + 1; k <= ids.length(); k++) {
                        String toCompare = ids.substring(j, k);
                        System.out.printf("i = %d, l = %d __ j = %d, k = %d substring = %s, toCompare = %s%n", i, l, j, k, substring, toCompare);

                        /*
                        if (substring.equals(toCompare) && longuest.length() < toCompare.length()) {
                            longuest = substring + toCompare;
                            System.out.println("longuest = " + longuest);
                        }

                         */
                    }
                }

                if (!incorrectInputs.isEmpty() && longuest.length() > incorrectInputs.get(0).length()) {
                    incorrectInputs = new ArrayList<>();
                }

                if (incorrectInputs.isEmpty() || (longuest.length() == incorrectInputs.get(0).length() && !incorrectInputs.contains(longuest))) {
                    incorrectInputs.add(longuest);
                }
            }
        }

        return incorrectInputs;
    }

    public static void main(String[] args) {
        String line = Utils.listFromDemoFile().get(0);
        String [] inputs = line.strip().split(",");
        System.out.println("inputs = " + Arrays.deepToString(inputs));

        String result = "";

        for (String ids: inputs){
            if(ids.charAt(0) == '0') continue;

            String tmp = ids.replaceAll("-", "");
            if (tmp.length() % 2 != 0) continue;

            System.out.println("incorrectInputs("+tmp+") = " + incorrectInputs(tmp));
        }
        
        

    }
}

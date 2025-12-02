package y2025;

import all.Utils;

import java.util.Arrays;
import java.util.List;

public class Day2 {

    static String compare(String s1, String s2){
        if (s1.equals("") || s2.equals("")){
            return "";
        }

        if (s1.equals(s2)){
            return s1;
        }

        int half = s1.length()/2;

        String o = compare(s1.substring(0, half-1), s1.substring(half));

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

        }
    }
}

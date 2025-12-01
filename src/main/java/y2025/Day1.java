package y2025;

import all.Utils;

import java.math.BigInteger;
import java.util.List;

public class Day1 {
    static BigInteger getNumToAdd(String mouvement){
        char sign = mouvement.charAt(0);
        BigInteger unsignNum = new BigInteger(mouvement.substring(1));

        return sign == 'L' ? unsignNum.negate() : unsignNum;
    }

    public static BigInteger move(BigInteger from, String mouvement){
       return from.add(getNumToAdd(mouvement)).mod(new BigInteger("100"));
    }

    public static void main(String[] args) {
        //List<String> inputs =  Utils.listFromDemoFile();
        List<String> inputs =  Utils.listForDay(2025, 1);

        BigInteger startNumber = new BigInteger("50");
        BigInteger compteur = BigInteger.ZERO;
        for (var input : inputs){
            startNumber = move(startNumber, input);

            if(startNumber.compareTo(BigInteger.ZERO) == 0){
                compteur = compteur.add(BigInteger.ONE);
            }
        }

        System.out.println("compteur = " + compteur);
    }
}

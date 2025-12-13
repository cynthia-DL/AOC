package y2025;

import all.Utils;

import java.math.BigInteger;
import java.util.List;

public class Day1 {
    private static final BigInteger HUNDRED = new BigInteger("100");
    private static final BigInteger FIFTY = new BigInteger("50");

    private static BigInteger getNumToAdd(String mouvement){
        char sign = mouvement.charAt(0);
        BigInteger unsignNum = new BigInteger(mouvement.substring(1));

        return sign == 'L' ? unsignNum.negate() : unsignNum;
    }
    
    public static BigInteger process1(List<String> inputs){
        BigInteger dial = FIFTY;
        BigInteger compteur = BigInteger.ZERO;
        for (var input : inputs){
            dial = dial.add(getNumToAdd(input)).mod(HUNDRED);

            if(dial.compareTo(BigInteger.ZERO) == 0){
                compteur = compteur.add(BigInteger.ONE);
            }
        }
        return compteur;
    }

    public static BigInteger process2(List<String> inputs){
        BigInteger dial = FIFTY;
        BigInteger compteur = BigInteger.ZERO;
        for (var input : inputs){
            var toAdd = getNumToAdd(input);
            var unmod = dial.add(toAdd.abs()).divide(HUNDRED);
            dial = dial.add(toAdd).mod(HUNDRED);

            if(dial.compareTo(BigInteger.ZERO) == 0 || unmod.compareTo(BigInteger.ZERO) > 0){
                compteur = compteur.add(BigInteger.ONE.max(unmod));
            }
        }
        return compteur;
    }

    public static void main(String[] args) {
        List<String> inputs =  Utils.listFromDemoFile();
        //List<String> inputs =  Utils.listForDay(2025, 1);

        System.out.println("process1(inputs) = " + process1(inputs));
        System.out.println("process2(inputs) = " + process2(inputs));
    }
}

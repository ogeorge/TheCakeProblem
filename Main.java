package Euler566;

import java.math.BigInteger;

public class Main {
    public static void main(String[] args) {
//        CakeSliceFlipping cakeSliceFlipping = new CakeSliceFlipping();
//        long startTime = System.currentTimeMillis();
//        Long flips = cakeSliceFlipping.countFlips(9,10,28);
//        long endTime = System.currentTimeMillis();
//        double totalTime = (endTime - startTime)/(double)60000;
//        int minutes = (int)totalTime;
//        double seconds = (totalTime - minutes) * 60;
//        System.out.println("Total number of flips: " + flips);
//        System.out.println("Execution time: " + minutes + " minutes and " + seconds + " seconds");

        int n = 14;
        long setupStartTime = System.currentTimeMillis();
        FlipSummation flipSummation = new FlipSummation(n);
        long setupEndTime = System.currentTimeMillis();

        Long answer = flipSummation.addUpFlips();
        long executeEndTime = System.currentTimeMillis();
        System.out.println("n: " + n + "\nanswer: " + answer);

        double totalSetupTime = (setupEndTime - setupStartTime) / (double) 60000;
        int setupMinutes = (int) totalSetupTime;
        double setupSeconds = (totalSetupTime - setupMinutes) * 60;

        double totalExecTime = (executeEndTime - setupEndTime) / (double) 60000;
        int execMinutes = (int) totalExecTime;
        double execSeconds = (totalExecTime - execMinutes) * 60;

        System.out.println("\n----------------------------STATS------------------------------------");
        System.out.println("Size of term parameters: " + flipSummation.getTermParameters().size());
        System.out.println("Set up time: " + setupMinutes + " minutes and " + setupSeconds + " seconds");
        System.out.println("Execution time: " + execMinutes + " minutes and " + execSeconds + " seconds");


//        Long num = new Long("3612985984524");
//        Long denom = new Long ("13717238173");
//        long startTime = System.currentTimeMillis();
//        Fraction reducer = new Fraction(num, denom);
//        long endTime = System.currentTimeMillis();
//        double totalTime = (endTime - startTime)/(double)60000;
//        int minutes = (int)totalTime;
//        double seconds = (totalTime - minutes) * 60;
//        System.out.println("Total number of flips: " + reducer.getNumerator() + "/" + reducer.getDenominator());
//        System.out.println("Execution time: " + minutes + " minutes and " + seconds + " seconds");
//
//
//        BigInteger bigNum = new BigInteger(num.toString());
//        BigInteger bigDenom = new BigInteger(denom.toString());
//        long startTime2 = System.currentTimeMillis();
//        BigInteger divisor = bigNum.gcd(bigDenom);
//        bigNum = bigNum.divide(divisor);
//        bigDenom = bigDenom.divide(divisor);
//        long endTime2 = System.currentTimeMillis();
//        double totalTime2 = (endTime2 - startTime2)/(double)60000;
//        int minutes2 = (int)totalTime2;
//        double seconds2 = (totalTime2 - minutes2) * 60;
//        System.out.println("Total number of flips: " + bigNum + "/" + bigDenom);
//        System.out.println("Execution time: " + minutes2 + " minutes and " + seconds2 + " seconds");
    }
    //Threading
    //Merging pieces
    //memory management
    //Writing to disk
    //mod fractions ---- this doesnt really help it just takes off a exponents
    //The longs are just overflowing so I'm getting the wrong values, need to use BigInteger
    //killer - 9,10,28
    //Make reducing happen only when it needs to reduce
}

package Euler566;

public class Main {
    public static void main(String[] args) {
//        CakeSliceFlipping cakeSliceFlipping = new CakeSliceFlipping();
//        long startTime = System.currentTimeMillis();
//        Long flips = cakeSliceFlipping.countFlips(15,16,17);
//        long endTime = System.currentTimeMillis();
//        double totalTime = (endTime - startTime)/(double)60000;
//        int minutes = (int)totalTime;
//        double seconds = (totalTime - minutes) * 60;
//        System.out.println("Total number of flips: " + flips);
//        System.out.println("Execution time: " + minutes + " minutes and " + seconds + " seconds");

        int n = 14;
        FlipSummation flipSummation = new FlipSummation(n);
        System.out.println("n: " + n + "\nanswer: " + flipSummation.addUpFlips());
        System.out.println("Size of term parameters: " + flipSummation.getTermParameters().size());
    }
    //Threading
    //Merging pieces
    //memory management
    //Writing to disk
    //mod fractions
}

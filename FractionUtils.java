package Euler566;

import java.math.BigInteger;
import java.util.Comparator;

public class FractionUtils {
    public static double maxPortion = 360.0;

    public static Fraction add(Fraction a, Fraction b) {
        if (a.getDenominator().equals(b.getDenominator())) {
            Long newNum = a.getNumerator() + b.getNumerator();
            return new Fraction(newNum, b.getDenominator());
        } else {
            Long newNum = (a.getNumerator() * b.getDenominator()) + (b.getNumerator() * a.getDenominator());
            Long newDenom = a.getDenominator() * b.getDenominator();
            if(newNum<0||newDenom<0||(a.getNumerator() * b.getDenominator()<0)||(b.getNumerator() * a.getDenominator()<0)) {
                System.out.println("STOP HERE");
            }
            return new Fraction(newNum, newDenom);
        }
    }

    public static Fraction subtract(Fraction a, Fraction b) { // a always has to be bigger
        if (a.getDenominator().equals(b.getDenominator())) {
            Long newNum = a.getNumerator() - b.getNumerator();
            if(newNum<0) {
                System.out.println("STOP HERE");
            }
            return new Fraction(newNum, b.getDenominator());
        } else {
            Long newNum = (a.getNumerator() * b.getDenominator()) - (b.getNumerator() * a.getDenominator());
            Long newDenom = a.getDenominator() * b.getDenominator();
            if(newNum<0||newDenom<0||(a.getNumerator() * b.getDenominator()<0)||(b.getNumerator() * a.getDenominator()<0)) {
                System.out.println("STOP HERE");
            }
            return new Fraction(newNum, newDenom);
        }
    }

    public static Fraction convertRadical(int c, Fraction corner, int counter) { //Recursive
        counter++;
        double sqrtc = Math.sqrt(c);

        int d = (int)sqrtc;
        Long topValue = new Long (c - (int)Math.pow(d,2));
        Fraction bottom = add(new Fraction(new Long(2*d),1L),corner);
        Fraction ratioDecimalDigits = new Fraction(topValue * bottom.getDenominator(), bottom.getNumerator());
        Fraction fractionD = new Fraction(new Long(d),1L);
        Fraction convertedRadical = add(fractionD, ratioDecimalDigits);
        if(sqrtc == convertedRadical.getDoubleValue()){
//            System.out.println("Went this far: " + counter);
//            System.out.println(convertedRadical.getNumerator() + "/" + convertedRadical.getDenominator());
            return convertedRadical;
        }
//        System.out.println("Square root of c:         "+ sqrtc);
//        System.out.println("Decimal version of ratio: "+ convertedRadical.getDoubleValue());
//        System.out.println("Continued fraction depth: "+ counter);
        return convertRadical(c, ratioDecimalDigits, counter);
    }

    public static Fraction convertRadical(int c, int iterations) { //Iterating
        int counter = 0;
        int d = (int)Math.sqrt(c);
        Fraction corner = new Fraction(0L,1L);
        Long topValue = new Long (c - (int)Math.pow(d,2));

        for(int i = 0; i < iterations; i++) {
            Fraction bottom = add(new Fraction(new Long(2*d),1L),corner);
            corner = new Fraction(topValue * bottom.getDenominator(), bottom.getNumerator());
        }

        Fraction fractionD = new Fraction(new Long(d),1L);
        Fraction convertedRadical = add(fractionD, corner);
//        System.out.println("\n-------------------------------This is the iteration version--------------------------------------");
//        System.out.println("Decimal version of ratio: "+ convertedRadical.getDoubleValue());
//        System.out.println(convertedRadical.getNumerator() + "/" + convertedRadical.getDenominator());
        return convertedRadical;
    }
}

package khoneki.genetic.algorithm;

import java.util.Comparator;
import java.util.Random;
import java.util.stream.IntStream;

/**
 * @author Khoneki <woni8708@naver.com>
 * @since 2015-11-05
 */
public class Gene implements Cloneable, Comparable<Gene> {
    public static final Comparator<Gene> COMPARATOR = Comparator.comparingLong(Gene::getScore);
    public static final int[] IMAGINARY = {0, 0, 0, 0, 0, 1, 1, 1, 1, 1};

    private int[] gene;
    private double fit = 0;

    public Gene() {
        this.gene = IntStream.range(0, Gene.IMAGINARY.length).map(i -> new Random().nextInt(2)).toArray();
    }

    public int[] getGene() {
        return gene;
    }

    public long getScore() {
        return IntStream.range(0, Gene.IMAGINARY.length).filter(i -> this.gene[i] == Gene.IMAGINARY[i]).count();
    }

    public double getFit() {
        return this.fit;
    }

    public double setFit(Gene min, Gene max) {
        return this.fit = (max.getScore() - this.getScore() + (max.getScore() - min.getScore()) / 3.0);
    }

    public void mutent() {
        IntStream.range(0, Gene.IMAGINARY.length).forEach(i -> {if(new Random().nextInt(100) <= Math.pow(10 - this.getScore(), 2)) this.gene[i] = (this.gene[i] == 0) ? 1 : 0;});
    }

    @Override
    public Gene clone(){
        try{
            return (Gene) super.clone();
        }catch(CloneNotSupportedException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public int compareTo(Gene that){
        return Gene.COMPARATOR.compare(this, that);
    }
}

package khoneki.genetic.algorithm;

import java.util.Random;
import java.util.stream.IntStream;

/**
 * @author Khoneki <woni8708@naver.com>
 * @since 2015-11-05
 */
public class GeneticAlgorithm {
    public static Population parent, son;
    public static Gene father, mother;
    public static int maxOfAverage = 0, minOfAverage = 20;

    public static void main(String[] args) {
        parent = new Population(100);
        son = new Population(100);

        for(int i = 0; i < 100; i++) {
            System.out.print(parent.getAverage()+" ");
            if((i+1)%10 == 0) System.out.println();
            if(parent.getAverage() >= 20) break;
            init();
            evolution();
            confront();
            //IntStream.range(0, Gene.IMAGINARY.length).forEach(m -> {IntStream.range(0, 10).forEach(n -> System.out.print(son.entities.get(m).getGene()[n])); System.out.print(",");});
            //System.out.println();
        }
    }

    public static void init() {
        parent.calculateFit();
        father = parent.rouletteWheel();
        mother = parent.rouletteWheel();
        //System.out.println(parent.getSumOfFit()+" "+father.getFit()+" "+mother.getFit());
    }

    public static void cross(int num) {
        int a = new Random().nextInt(son.entities.get(num).getGene().length-1)+1;
        int b = new Random().nextInt(son.entities.get(num).getGene().length-a)+a;
        for(int i = 0; i < a; i++) son.entities.get(num).getGene()[i] = father.getGene()[i];
        for(int n = a; n < b; n++) son.entities.get(num).getGene()[n] = mother.getGene()[n];
        for(int m = b; m < son.entities.get(num).getGene().length; m++) son.entities.get(num).getGene()[m] = father.getGene()[m];
    }

    public static void confront() {
        parent.sort(); son.sort();
        IntStream.range(0, parent.entities.size() / 2).forEach(i -> parent.entities.set(i, son.entities.get(son.entities.size() - 1 - i).clone()));
    }

    public static void evolution() {
        IntStream.range(0, son.entities.size()).forEach(i -> {
            cross(i); son.entities.get(i).mutent();
        });
    }
}

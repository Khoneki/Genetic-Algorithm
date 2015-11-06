package khoneki.genetic.algorithm;

import java.util.Random;

/**
 * @author Khoneki <woni8708@naver.com>
 * @since 2015-11-05
 */
public class GeneticAlgorithm {
    public static Population parent, son;
    public static Gene father, mother;
    public static int maxOfAverage = 0, minOfAverage = 10;

    public static void main(String[] args) {
        //TODO: Implement this method
        parent = new Population();
        son = new Population();
        for(int i = 0; i < 1000; i++) {
            if(parent.getAverage() >= 10) break;
            init();
            evolution();
            confront();
        }
    }

    public static void init() {
        parent.calculateFit();
        father = parent.rouletteWheel();
        mother = parent.rouletteWheel();
    }

    public static void cross(int num) {
        int a = new Random().nextInt(8)+1;
        int b = new Random().nextInt(10-a)+a;
        for(int i = 0; i < a; i++) son.entities.get(num).getGene()[i] = father.getGene()[i];
        for(int n = a; n < b; n++) son.entities.get(num).getGene()[n] = mother.getGene()[n];
        for(int m = b; m < 10; m++) son.entities.get(num).getGene()[m] = father.getGene()[m];
    }

    public static void confront() {
        for(int i = 0; i < parent.entities.size(); i++) parent.entities.set(i, son.entities.get(i).clone());
    }

    public static void evolution() {
        for(int i = 0; i < son.entities.size(); i++) {
            cross(i);
            son.entities.get(i).mutent();
        }
    }
}

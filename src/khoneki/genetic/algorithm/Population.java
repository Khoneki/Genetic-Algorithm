package khoneki.genetic.algorithm;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author Khoneki <woni8708@naver.com>
 * @since 2015-11-05
 */
public class Population {
    public List<Gene> entities;

    public Population(int size){
        this.entities = IntStream.range(0, size).mapToObj(i -> new Gene()).collect(Collectors.toList());
    }

    public Gene getMax() {
        return this.entities.stream().max(Gene.COMPARATOR).orElseThrow(NullPointerException::new);
    }
    public Gene getMin() {
        return this.entities.stream().min(Gene.COMPARATOR).orElseThrow(NullPointerException::new);
    }

    public double getAverage() {
        return this.entities.stream().mapToLong(Gene::getScore).average().orElseThrow(NullPointerException::new);
    }

    public void calculateFit() {
        Gene min = this.getMin();
        Gene max = this.getMax();

        this.entities.forEach(gene -> gene.setFit(min, max));
    }

    public double getSumOfFit() {
        return this.entities.stream().mapToDouble(Gene::getFit).sum();
    }

    public Gene rouletteWheel() {
        int sumOfFit = (int) this.getSumOfFit();
        int a = sumOfFit == 0 ? 0 : new Random().nextInt(sumOfFit);

        double sum = 0;
        for(Gene gene: this.entities) {
            sum += gene.getFit();
            if(sum > a) return gene;
        }

        return this.entities.get(0);
    }

    public void sort(){
        this.entities = this.entities.stream().sorted().map(Gene::clone).collect(Collectors.toList());
    }
}

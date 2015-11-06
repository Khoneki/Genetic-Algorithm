package khoneki.genetic.algorithm;

/**
 * @author Khoneki <woni8708@naver.com>
 * @since 2015-11-05
 */
public class Population {
    public int entCnt;
    public Gene entity[];
    public int max = 0, min = 10, sumOfFit = 0;

    public void regen() {
        for(int i = 0; i < entCnt; i++) {
            entity[i].setGene();
        }
    }
    public void getMax() {
        for(int i = 0; i < entCnt; i ++) {
            if(entity[i].score > max) max = entity[i].score;
        }
    }
    public void getMin() {
        for(int i = 0; i < entCnt; i ++) {
            if(entity[i].score < max) min = entity[i].score;
        }
    }
    public double getAverage() {
        int average = 0;
        for(int i = 0; i < entCnt; i++) {
            average += entity[i].score;
        }
        return average;
    }
    public void f() {
        for(int i = 0; i < entCnt; i++)
            entity[i].fit = max-entity[i].score + (max-min)/(4-1);
    }
    public void getSumOfFit() {
        sumOfFit = 0;
        for(int i = 0; i < entCnt; i++)
            sumOfFit += entity[i].fit;
    }
    public int rouletteWheel() {
        double sum = 0;
        int a = (int)Math.floor(Math.random()*sumOfFit);
        for(int i = 0; i < entCnt; i++) {
            sum += entity[i].fit;
            if(a<sum) return i;
        }
        return 0;
    }
    public void sort() throws java.lang.CloneNotSupportedException{
        Gene temp;
        for(int i = 0; i < entCnt-2; i++) {
            for(int m = i; m < entCnt-1; m++) {
                if(entity[m].score>entity[m+1].score) {
                    temp = entity[m].clone();
                    entity[m+1] = entity[m].clone();
                    entity[m+1] = temp.clone();
                }
            }
        }
    }
}

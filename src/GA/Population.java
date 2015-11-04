package GA;

import GA.Gene;

/**
 * Created by ¿ø on 2015-11-05.
 */
public class Population {
    public int entCnt;
    public Gene entity;
    public int max = 0, min = 10, sumOfFit = 0;

    public void regen() {}
    public void getMax() {}
    public void getMin() {}
    public double getAverage() {
        return 0;
    }
    public void f() {}
    public void getSumOfFit() {}
    public void rouletteWheel() {}
}

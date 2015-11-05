package khoneki.genetic.algorithm;

/**
 * @author Khoneki <woni8708@naver.com>
 * @since 2015-11-05
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

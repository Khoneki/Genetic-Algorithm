package khoneki.genetic.algorithm;

/**
 * @author Khoneki <woni8708@naver.com>
 * @since 2015-11-05
 */
public class Gene {
    public int gene[];
    public int score;
    public double fit;

    public void setGene() {
        for(int i = 0; i < 10; i++) {
            this.gene[i] =  Math.floor(Math.random()*2)==0?0:1;
        }
    }
    public void getScore() {
        score = 0;
        int imaginary[] = {0,0,0,0,0,1,1,1,1,1};
        for(int i = 0; i < 10; i++) {
            if(gene[i] == imaginary[i]) score++;
        }
    }
    public void mutent() {
        this.getScore();
        int a = (int)Math.floor(Math.random()*100);
        if(a <= (10-score)*(10-score)) {
            for(int i = 0; i < 10; i++) {
                switch(gene[i]) {
                    case 0:
                        gene[i] = 1;
                        break;
                    case 1:
                        gene[i] = 0;
                        break;
                }
            }
        }
    }
    @Override
    public Gene clone() {
        return this.clone();
    }
}

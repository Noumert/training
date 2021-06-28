package org.vova;

import java.util.ArrayList;
import java.util.List;

public class Model {
    private final int MAX_RAND = 100;
    private int hiddenNumber;
    private List<Integer> history = new ArrayList<Integer>();
    private int minBarrier = 0;
    private int maxBarrier = 100;

    public Model() {
        this.hiddenNumber = rand(minBarrier, maxBarrier);
    }
// The Program logic

    /**
     * in this method random number between minBarrier and maxBarrier
     *
     * @param minBarrier,maxBarrier
     * @return random number
     */
    public int rand(int minBarrier,int maxBarrier) {
        return (int)(Math.random()*(maxBarrier-minBarrier+1)+minBarrier);
    }

    /**
     * in this method random number between 0 and MAX_RAND
     *
     * @return random number
     */
    public int rand() {
        return (int)(Math.random()*(MAX_RAND+1));
    }

    public char CheckNumber(int number) {
        history.add(number);
        if (number > hiddenNumber) {
            return '<';
        } else if(number < hiddenNumber) {
            return '>';
        }else {
            return '0';
        }
    }

    public int getMinBarrier() {
        return minBarrier;
    }

    public int getMaxBarrier() {
        return maxBarrier;
    }

    public List<Integer> getHistory() {
        return history;
    }

    public void setMinBarrier(int minBarrier) {
        this.minBarrier = minBarrier;
    }

    public void setMaxBarrier(int maxBarrier) {
        this.maxBarrier = maxBarrier;
    }
}

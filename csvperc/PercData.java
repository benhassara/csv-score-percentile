/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csvperc;

import static java.lang.Integer.parseInt;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author bhassara
 */
public class PercData {
    
    // properties
    // hold score frequencies with scores as keys
    private HashMap<String, Integer> freqs;
    // hold the unique scores as ints
    private ArrayList<Integer> scores;
    
    // constructor
    public PercData() {
        this.freqs = new HashMap();
        this.scores = new ArrayList<>();
    }
    
    // sort and return unique scores
    public ArrayList<Integer> sort() {
        Collections.sort(this.scores);
        Collections.reverse(this.scores);
        return this.scores;
    }
    
    // add a score to the data set
    public void addScore(String score) {
        int intScore = parseInt(score);
        
        if(this.freqs.containsKey(score)) {
            int newScore = this.freqs.get(score) + 1;
            this.freqs.replace(score, newScore);
        }
        else {
            this.freqs.put(score, 1);
            this.scores.add(intScore);
        }
    }
    
    /*
    * return the total number of scores in the data set
    */
    private int getNumScores() {
      int total = 0;
      
      for (int freq: this.freqs.values()) {
          total += freq;
      }
      
      return total;
    }
    
    /*
     * return percentile ranks for each unique score in data set
     */
    public Map<String, Integer> getPercentiles() {
        
        HashMap<String, Integer> output = new HashMap<>();
        // n = total data points
        int n = this.getNumScores();
        
        this.sort();
        
        for (int i = 0; i < this.scores.size(); i++) {
            
            String key = this.scores.get(i).toString();
            
            // edge cases
            if (i == this.scores.size() - 1) {
                output.put(key, 1);
            }
            else if (i == 0) {
                output.put(key, 99);
            }
            else {
                // cf = cumulative freq
                int cf = 0;
                ArrayList<Integer> slice = new ArrayList<>();
                slice.addAll(this.scores.subList(0, i + 1));
            
                // iterate over sublist of unique scores to reduce into cumulative frequency
                for (int j = 0; j < slice.size(); j++) {
                    cf += this.freqs.get(slice.get(j).toString());
                }                
            
                // calc percentile rank
                int percRank = Math.round(100 - (((float)cf / (float)n) * 100));
            
                // add to hashmap with score as key
                output.put(this.scores.get(i).toString(), percRank);
            }
        }
        
        return output;
    }
}
package databaseconnections;

import java.util.Comparator;

public class Player implements Comparator<Player>, Comparable<Player>{

    public final long TIME;
    public final Double RATING;
    public final int ID;
    private final double DECAY = .5;
    
    public Player (){
        this.ID = 1;
        this.RATING = 1.1;
        TIME = System.currentTimeMillis();
    }
    
    public Player(int id, double rating){
        this.ID = id;
        this.RATING = rating;
        TIME = System.currentTimeMillis();
    }
    public long waited(){
        return System.currentTimeMillis()-TIME;
    }
    public boolean match(Player pair){
        if (pair.ID == ID) return false;
        //long time = this.TIME < pair.TIME? this.TIME:pair.TIME;
        //double dif = Math.pow((System.currentTimeMillis()-time), 0.5)*DECAY;
        double dif = Math.pow((2*System.currentTimeMillis()-TIME-pair.TIME), 0.5)*DECAY;
        return Math.abs(pair.RATING-RATING)<dif;
    }
     public String toString(){
         return "Player " + ID + " with rating " + RATING + " has waited " + Double.toString((System.currentTimeMillis()-TIME)/1000);
     }
    public int compareTo(Player d){
      return (this.RATING).compareTo(d.RATING);
   }

    
    public int compare(Player t, Player t1) {
        return Double.compare(t.RATING, t1.RATING);
    }
    
}

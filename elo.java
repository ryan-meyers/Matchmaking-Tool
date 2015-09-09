package databaseconnections;

import java.util.Scanner;

public class elo {
    public db players;
    
    public elo(){
        players = new db();
    }
    
    public elo(String game){
        players = new db(game);
    }
    public void updateRankings(Player p1, Player p2, double res1, double res2){
        if (res1 == 0.5){
            players.updateInfo(p1.ID, "draws");
            players.updateInfo(p2.ID, "draws");
        }
        else if (res1 >0.5){
            players.updateInfo(p1.ID, "losses");
            players.updateInfo(p2.ID, "wins");
        }
        else{
            players.updateInfo(p1.ID, "wins");
            players.updateInfo(p2.ID, "losses");
        }
        double rat1 =(p1.RATING+ 16*(res1-expctdRes(p1, p2)));
        double rat2 =(p2.RATING+ 16*(res2-expctdRes(p2, p1)));
        players.updateInfo(p1.ID, "rating", rat1);
        players.updateInfo(p2.ID, "rating", rat2);
    }
    
    private double expctdRes(Player p1, Player p2){
        double temp = (1/(1 + Math.pow(10, (p2.RATING-p1.RATING)/400)));
        return temp;
    }
     public static void main(String[] args)
    {
        //gui g = new gui();
        //g.init();
        elo ratings = new elo();
        MatchMaker game = new MatchMaker();
        //new gui();
        
        String[] data = ratings.players.getData("id rating");
        Player[] start = new Player[data.length];
        for (int i = 0; i < data.length; i++){
            String[] p = data[i].split("!");
            start[i] = new Player(Integer.parseInt(p[0]), Double.parseDouble(p[1]));
        }
        /*for(int i = 0; i < 10; i++){
            ratings.sim(game);
        }*/
        data = ratings.players.getData("id rating");
        Player[] end = new Player[data.length];
        for (int i = 0; i < data.length; i++){
            String[] p = data[i].split("!");
            end[i] = new Player(Integer.parseInt(p[0]), Double.parseDouble(p[1]));
        }
        for (int i = 0; i < start.length; i++){
            System.out.println(end[i].RATING-start[i].RATING);
            
        }
        //ratings.players.displayData();
        //ratings.updateRankings(1001, 1002, 0, 1);
        ratings.players.displayData();
    }
    private void sim(MatchMaker game){
        String[] data = players.getData("id rating");
        
        for (String s:data){
            String[] p = s.split("!");
            Player pl = new Player(Integer.parseInt(p[0]), Double.parseDouble(p[1]));
            game.add(pl);
        }
        Player[] pair;
        double max = 0;
        long time = 0;
        while (!game.isEmpty()){
            pair = game.match();
            double result = Math.random();
            if (pair != null){
                updateRankings(pair[0], pair[1], result, 1-result);
                double t = Math.abs(pair[0].RATING-pair[1].RATING);
                if (pair[0].waited()>time) time = pair[0].waited();
                if (t>max) max = t;
            }
            
        }
        System.out.println(max);
        System.out.println(time/1000);
        
    }
}

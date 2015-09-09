package databaseconnections;

import java.util.*;

public class MatchMaker {
    private final LinkedHashSet<Player> queue;

    public MatchMaker() {
        this.queue = new LinkedHashSet<>();
    }
    public void displayQueue(){
        Iterator<Player> itr = queue.iterator();
        while(itr.hasNext()){
            Player p = itr.next();
            System.out.println(p);
        }
    }
    public boolean add(Player user){
        return queue.add(user);
    }
    public Player match(Player user){
        Iterator<Player> itr = queue.iterator();
        if(queue.size()==2){
            Player opponent = itr.next();
            if (user == opponent) opponent = itr.next();
            queue.remove(opponent);
            queue.remove(user);
            return opponent;
        }
        while(itr.hasNext()){
            Player opponent = itr.next();
            if (user.match(opponent)){
                queue.remove(opponent);
                queue.remove(user);
                return opponent;
            }
        }
        return null;
    }
    public Player[] match(){
        if (queue.size()< 2) return null;
        Player[] pair = {null, null};
        Iterator<Player> itr = queue.iterator();
        while(itr.hasNext()){
            pair[0] = itr.next();
            pair[1] = match(pair[0]);
            if (pair[1]!=null) return pair;
        }
        return null;
    }
    public boolean isEmpty(){
        return queue.size()<2;
    }
    public boolean remove(Player user){
        return queue.remove(user);
    }
}

package databaseconnections;
    
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class PlayerList {
    private final ArrayList<Player> players;
    
    public PlayerList(){
        players = new ArrayList<>();
    }
    
    public void add(Player user){
        players.add(user);
    }
    
    public Player[] getList(){
        Player[] temp = new Player[players.size()];
        Iterator<Player> itr = players.iterator();
        int i = 0;
        while (itr.hasNext()){
            temp[i++] = itr.next();
        }
        return temp;
        
    }
    
    public void sort(){
        Collections.sort(players, new Player());
    }
    
}

package databaseconnections;
/**
 *
 * @author Ryan
 */
import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class db
{
    protected Connection con;
    protected Statement st;
    protected ResultSet rs;
    protected String game;
    
    public db()
    {
        game = "data";
        connect();
    }
    
    public db(String game)
    {
        this.game = game;
        connect();
    }
    
    private void close()
    {
        try {
            con.close();
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(db.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void connect()
    {
        try
        {
            String driver = "com.mysql.jdbc.Driver";
            Class.forName(driver).newInstance();
            
            
            Properties properties = new Properties();
            properties.put("user", "arcang_abel3");
            properties.put("password", "codeday1");
            String db = "jdbc:mysql://arcang.com/arcang_codeday";
            con = DriverManager.getConnection(db, properties);
            st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
            
           
            
            
        }catch(Exception ex){
            System.out.println("Error: "+ ex);

        }
    }
    public boolean addUser(String player){
        
        connect();
        try {            
            String sql = "INSERT  INTO data VALUES(?, ?, 0, 0, 0, 1200);";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, player.hashCode());
            ps.setString(2, player);
            return !ps.execute();
        } catch (SQLException ex) {
            Logger.getLogger(db.class.getName()).log(Level.SEVERE, null, ex);
        }
        close();
        return false;
    }
    public String[] getData(String elements){
        connect();
        try {
            String[] fields = elements.split(" ");
            String temp = "";
            String sql = "select * from data";
            rs = st.executeQuery(sql);
            while (rs.next()){
                for(String s:fields){
                    String el = rs.getString(s);
                    temp = temp.concat(el)+"!";
                }
                temp = temp.concat("#");
            }
            close();
            return temp.split("#");
        } catch (SQLException ex) {
            Logger.getLogger(db.class.getName()).log(Level.SEVERE, null, ex);
        }
        close();
        return null;
    }
    public void displayData(){
        connect();
        try{
            String sql = "select * from ";
            sql = sql.concat(game);
            rs = st.executeQuery(sql);
            System.out.println("Records from Database");
            while (rs.next()){
                String player = rs.getString("player");
                int wins = rs.getInt("wins");
                int losses = rs.getInt("losses");
                double rating = rs.getDouble("rating");
                System.out.println("Player: "+player+"  "+"Wins: "+wins+"   "+"Losses: "+losses+" Rating: "+rating);
                
            }
            rs.close();
        }catch(Exception ex){
            System.out.println("Error: "+ ex);
        }
        close();
    }
    
    public String getInfo(int id, String field){   
        connect();
        String val = "Error";
        try {
        // id, player, wins, losses, draws, rating
        String sql = "select * from data where id = ?";
        PreparedStatement ps = con.prepareStatement(sql);
       // ps.setString (1, field);
       // ps.setNString (2, game);
        ps.setInt (1, id);
        rs = ps.executeQuery();
        rs.next();
        val = rs.getString(field);
        rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(db.class.getName()).log(Level.SEVERE, null, ex);
        }
        close();
        return val;
    }
    
    public void updateInfo(int id, String field, double val){
        connect();
        try {
        // id, player, wins, losses, draws, rating
        String sql = "select * from data where id = ?";
        PreparedStatement ps = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        ps.setInt (1, id);
        rs = ps.executeQuery();
        rs.next();
        rs.updateDouble(field, val);
        rs.updateRow();
        rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(db.class.getName()).log(Level.SEVERE, null, ex);
        }
        close();
    }
     public void updateInfo(int id, String field){
        connect();
        try {
        // id, player, wins, losses, draws, rating
        String sql = "select * from data where id = ?";
        PreparedStatement ps = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        ps.setInt (1, id);
        rs = ps.executeQuery();
        rs.next();
        rs.updateInt(field, rs.getInt(field)+1);
        rs.updateRow();
        rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(db.class.getName()).log(Level.SEVERE, null, ex);
        }
        close();
    }
    
   
}

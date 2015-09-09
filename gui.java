package databaseconnections;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import net.miginfocom.swing.*;

public class gui extends db implements ActionListener, ItemListener{
    JFrame frame;
    JPanel panel;
    JButton[] btn;
    JComboBox dropDown;
    JCheckBox jcb1, jcb2, jcb3;
    
    String initPlyr;
    String opponent;
    String winner;
    elo rank;
    MatchMaker matcher;
    db database;
    
    int idSelected;
    
    int menu = 0;

    @Override
    public void itemStateChanged(ItemEvent e) {
        if(menu == 2) initPlyr = dropDown.getSelectedItem().toString();
        else winner = dropDown.getSelectedItem().toString();
    }
    
    @Override
    public void actionPerformed(ActionEvent e){
       if(menu == 1){
           if(e.getSource().equals(btn[0])) genSet();
           //else if(e.getSource().equals(btn[1])) optSet();
           else plSet();
       }else if(menu == 2){
           //if(e.getSource().equals(btn[0])) addPlayer();
       }
    }
    
    public void init(){ // initializes with the home menu
        frame = new JFrame("Stony Point huehuehue");
        panel = new JPanel();
        btn = new JButton[4];
        rank = new elo();
        database = rank.players;
        matcher = new MatchMaker();
        String[] data = database.getData("id rating");
        
        for (String s:data){
            String[] p = s.split("!");
            Player pl = new Player(Integer.parseInt(p[0]), Double.parseDouble(p[1]));
            matcher.add(pl);
        }
        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        homeSet();
    }
    
    private void homeSet(){
        panel.removeAll();
        menu = 1;
        
        btn[0] = new JButton("Generate");
        btn[1] = new JButton("Options");
        btn[2] = new JButton("Leaderboard");
        btn[3] = new JButton("Participants");
        
        JPanel left = new JPanel(new GridLayout(5, 1));
        JPanel right = new JPanel(new GridLayout(4, 1));
        
        dropDown = new JComboBox(); // TODO: this shit
            dropDown.addItemListener((ItemListener) this);
        
        left.add(dropDown);
        //left.add(new JTextField("         "));
        jcb1 = new JCheckBox("Handi"); // doesn't currently work
            jcb1.addActionListener((ActionListener) this);
        jcb2 = new JCheckBox("Randi"); // doesn't currently work
            jcb2.addActionListener((ActionListener) this);
        left.add(jcb1);
        left.add(jcb2);
        
        for(JButton ibtn : btn){
            ibtn.addActionListener((ActionListener) this);
            right.add(ibtn);
        }
        
        panel.add(left);
        panel.add(right);
        
        finishSet();
    }
    
    private void plSet(){
        panel.removeAll();
        menu = 2;
        
        JPanel temp1 = new JPanel(new MigLayout());
        
        JPanel temp = new JPanel(new MigLayout());
        JPanel temp2 = new JPanel(new MigLayout());
        JPanel temp3 = new JPanel(new BorderLayout());
        
        btn[0] = new JButton("->"); // add active player
        btn[1] = new JButton("<-"); // rem
        
        PlayerList users = new PlayerList();
        String[] data = database.getData("id rating");
        for (String s:data){
            String[] dat = s.split("!");
            Player p = new Player(Integer.parseInt(dat[0]), Double.parseDouble(dat[1]));
            
            users.add(p);
        }
        users.sort();
        Player[] ordered = users.getList();
        String[] names = new String[ordered.length];
        for(int i = 0; i < names.length; i++){
            names[i] = database.getInfo(ordered[i].ID, "player");
        }
        JList players = new JList(names);
        players.setVisibleRowCount(7);
        JScrollPane sp = new JScrollPane(players);
        
        finishSet();
    }
    
    private void genSet(){
        panel.removeAll();
        menu = 3;
        
        
        JPanel temp = new JPanel(new MigLayout());
        JPanel temp2 = new JPanel(new MigLayout());
        Player[] peeps = matcher.match();
        temp2.add(new JTextField(database.getInfo(peeps[0].ID, "player")));
        temp2.add(new JTextField(database.getInfo(peeps[1].ID, "player")), "wrap");
        btn[0] = new JButton("Back");
        btn[0].addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                homeSet();
            }
        });
        Integer[] stuff = {0, 1};
        JComboBox win = new JComboBox(stuff);
        btn[1] = new JButton("Update");
        btn[1].addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                int res = (int)win.getSelectedItem();
                rank.updateRankings(peeps[1], peeps[0], 1-res, res);
                homeSet();
            }
        });
        
        temp.add(temp2);
        temp.add(btn[0]);
        temp.add(btn[1]);
        
        
        panel.add(win);
        panel.add(temp, "span 2 3");
        
        finishSet();
    }
    
    private void lbSet(){
        panel.removeAll();
        menu = 4;
        
        // to be implemented
        
        //currentPanel;
        finishSet();
    }
    
    private void finishSet(){
        //frame.removeAll();
        //frame.add(panel);
        frame.pack();
        frame.setVisible(true);
    }
    public static void main(String[] args)
    {
        gui g = new gui();
        g.init();
        
        //for(int i = 0; i < 10; i++){
        //g.rank.sim(g.matcher);
        //}
            
        //ratings.players.displayData();
        //ratings.updateRankings(1001, 1002, 0, 1);
        g.database.displayData();
    }
}

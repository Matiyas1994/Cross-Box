package Crossbox;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class ButtonHandler implements ActionListener {
    GameBoard gui;
    Gui gui2;
    Logic logic = new Logic();
    HashMap<JButton, String> button_cordinates = new HashMap<>();
    static ArrayList<JButton> holder = new ArrayList<>(1);
    static ArrayList<JButton> p1 = new ArrayList<>();
    static ArrayList<JButton> p2 = new ArrayList<>();

    ButtonHandler(GameBoard obj, Gui gui2) {

        this.gui = obj;
        this.gui2=gui2;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        JButton source = (JButton) e.getSource();
        System.out.println(" on click p1 cointains "+p1);
        System.out.println(" on click p2 cointains "+p2);
        if (holder.isEmpty()) {
            String cordinate = button_cordinates.get(source);
            int next_i = Integer.parseInt(""+cordinate.charAt(0));
            int next_j = Integer.parseInt(""+cordinate.charAt(1));
            String validty = logic.is_valid_move(next_i, next_j,0,0);
            if (validty != "FF"){
                holder.add(source);
            }


        } else {
            String cordinate = button_cordinates.get(source);
            int next_i = Integer.parseInt(""+cordinate.charAt(0));
            int next_j = Integer.parseInt(""+cordinate.charAt(1));
            String cordinate2 = button_cordinates.get(holder.get(0));
            int cur_i = Integer.parseInt(""+cordinate2.charAt(0));
            int cur_j = Integer.parseInt(""+cordinate2.charAt(1));
            String validty = logic.is_valid_move(cur_i, cur_j, next_i, next_j);

            if (validty.charAt(0) == 'T') {

                holder.get(0).setBackground(new Color(113,40,47));
                holder.get(0).setEnabled(true);
                if (Client.player.role.equals("creator")){
                    p1.remove(holder.get(0));
                    p1.add(source);
                    Gui.p1Panel.setBackground(new Color(113,40,47));
                    Gui.p2Panel.setBackground(Color.black);
                }
                else{
                    p2.remove(holder.get(0));
                    p2.add(source);
                    Gui.p2Panel.setBackground(new Color(113,40,47));
                    Gui.p1Panel.setBackground(Color.black);
                }

                logic.move(cur_i,cur_j,next_i,next_j);
                UpdateGUI();
                holder.clear();
                //make all thing disabled
                setEnable(false);
                String cx = cordinate2+cordinate;
                int finished = logic.is_Game_finished();

                if ( finished!=0){
                    try {
                        Gui.onFinish(finished,gui2);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                    setEnable(false);
                }
                else{

                    if (Client.player.role.equals("creator")){
                        Gui.p2Panel.setBackground(new Color(113,40,47));
                        Gui.p1Panel.setBackground(Color.black);
                    }
                    else{
                        Gui.p1Panel.setBackground(new Color(113,40,47));
                        Gui.p2Panel.setBackground(Color.black);
                    }
                }
                Client.tellServer("move", cx);
            } else if (validty.charAt(1) == 'S') {
                holder.clear();
                holder.add(source);
            }

        }

    }

    public void UpdateGUI() {
        for (int i = 0; i < 3; i++) {
            p1.get(i).setBackground(new Color(230,157,0));
            p2.get(i).setBackground(new Color(0,179,179));
        }
    }


    public void onReceive(String cordinate) {

        int f_i = Integer.parseInt(""+cordinate.charAt(0));
        int f_j = Integer.parseInt(""+cordinate.charAt(1));
        int t_i = Integer.parseInt(""+cordinate.charAt(2));
        int t_j = Integer.parseInt(""+cordinate.charAt(3));
        setEnable(true);
        for (Map.Entry<JButton, String> entry : button_cordinates.entrySet()) {
            if (entry.getValue().equalsIgnoreCase(cordinate.substring(0,2))) {
                if (Client.player.role.equals("creator")){
                    p2.remove(entry.getKey());
                }
                else {
                    p1.remove(entry.getKey());
                }

                entry.getKey().setBackground(new Color(113,40,47));
            }
            else if (entry.getValue().equalsIgnoreCase(cordinate.substring(2))){
                if (Client.player.role.equals("creator")){
                    p2.add(entry.getKey());
                    entry.getKey().setBackground(new Color(0,179,179));
                }
                else {
                    p1.add(entry.getKey());
                    entry.getKey().setBackground(new Color(230,157,0));
                }

            }

        }
        for(int i = 0; i <3; i++){
            if (Client.player.role.equals("creator")){
                p2.get(i).setEnabled(false);
                Gui.p1Panel.setBackground(new Color(113,40,47));
                Gui.p2Panel.setBackground(Color.black);
            }

            else {
                p1.get(i).setEnabled(false);
                Gui.p2Panel.setBackground(new Color(113,40,47));
                Gui.p1Panel.setBackground(Color.black);
            }
        }
        logic.move(f_i,f_j,t_i,t_j);
        UpdateGUI();
        int finished = logic.is_Game_finished();
        if ( finished!=0){
            try {
                Gui.onFinish(finished,gui2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            setEnable(false);
        }
    }



    public void setEnable(boolean enable){
        Set<JButton> keys = button_cordinates.keySet();
        for ( JButton key : keys ) {
            key.setEnabled(enable);
        }
    }


}
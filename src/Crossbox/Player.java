package Crossbox;

import java.util.ArrayList;

public class Player {
    int id;
    String name;
    String role;
    int won;
    int lost;
    String oponnentName;
    public Player(int id, String name, int won, int lost) {
        this.id = id;
        this.name = name;
        this.won = won;
        this.lost = lost;
    }
    public String stringfyPlayer() {
        return id + "=" + name + "=" + won + "=" + lost;
    }

}

package Crossbox;

import java.util.ArrayList;
import java.util.UUID;
public class Match {
    static ArrayList<Match> all = new ArrayList<>();
    String uid;
    ClientHandler creator;
    ClientHandler joiner;

    public Match(ClientHandler creator) {
        this.uid = UUID.randomUUID().toString();
        this.creator = creator;
        all.add(this);
    }
}

package Crossbox;

import java.io.*;
import java.net.Socket;
import java.sql.*;

public class ClientHandler implements Runnable{

    private Socket socket;
    private PreparedStatement createMatchStmt;
    private PreparedStatement Rankstm;
    private PreparedStatement setWinnerStmt;
    private PreparedStatement createStmt;
    private PreparedStatement updateStmt;
    private PreparedStatement getByIdStmt;
    private PreparedStatement getByTempStmt;
    private Statement leaderboardStmt;
    private String name;
    private int playerId;
    private String role;
    private String matchUid;
    ClientHandler opponent;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    public ClientHandler(Socket socket, Connection con) {
        try {
            createMatchStmt = con.prepareStatement("INSERT INTO `match` (match_id,p1,p2) VALUES(?,?,?)");
            setWinnerStmt = con.prepareStatement("UPDATE `match` SET winner = ? WHERE match_id = ?");
            updateStmt = con.prepareStatement("UPDATE player SET name = ?, won = ?, lost = ? WHERE id = ?");
            createStmt = con.prepareStatement("INSERT INTO player (name, temp) VALUES(?, ?)");
            getByIdStmt = con.prepareStatement("SELECT * FROM player WHERE id = ?");
            leaderboardStmt = con.createStatement();
            getByTempStmt = con.prepareStatement("Select * from player where temp = ?");
            Rankstm = con.prepareStatement("SELECT RANK() OVER(ORDER BY won DESC) AS rank FROM player WHERE `id` = ?");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void tellClient(String msg) throws IOException {
        bufferedWriter.write(msg);
        bufferedWriter.newLine();
        bufferedWriter.flush();
    }
    public void createMatchDb(Match match) throws SQLException {
        createMatchStmt.setString(1, match.uid);
        createMatchStmt.setInt(2, match.creator.playerId);

        createMatchStmt.setInt(3, match.joiner.playerId);

        createMatchStmt.execute();
        System.out.println("createMDb "+match.creator.playerId);
        System.out.println("joinMDb "+match.joiner.playerId);
    }
    public void setWinnerDb(String given) throws SQLException {
        if(given.equals("1")) {
            if(role.equals("creator")) {
                setWinnerStmt.setInt(1, playerId);
            }else{
                setWinnerStmt.setInt(1, opponent.playerId);
            }
        }else {
            if(role.equals("creator")) {
                setWinnerStmt.setInt(1, opponent.playerId);
            }else{
                setWinnerStmt.setInt(1, playerId);
            }
        }
        setWinnerStmt.setString(2, matchUid);
        setWinnerStmt.executeUpdate();
    }
    public void updatePlayerDb(String player) throws SQLException {
        System.out.println("maaaaa"+player);
        String[] player_rry = player.split("=");
        int id = Integer.parseInt(player_rry[0]);
        String n = player_rry[1];
        name = n;
        int won = Integer.parseInt(player_rry[2]);
        int lost = Integer.parseInt(player_rry[3]);
        updateStmt.setString(1, n);
        updateStmt.setInt(2, won);
        updateStmt.setInt(3, lost);
        updateStmt.setInt(4, id);
        updateStmt.executeUpdate();
    }

    public int createPlayerDb() throws SQLException {
        int rand =(int) Math.floor(Math. random()*(10000 - 1000+1)+ 1000);
        String temp_user_name = "Egele"+rand;
        createStmt.setString(1,temp_user_name);
        createStmt.setInt(2,rand);
        createStmt.executeUpdate();

        getByTempStmt.setInt(1, rand);
        ResultSet rs = getByTempStmt.executeQuery();
        rs.next();
        return rs.getInt(1);
    }

    public String getPlayerDb(int id) throws SQLException {
        getByIdStmt.setInt(1, id);
        ResultSet rs = getByIdStmt.executeQuery();
        rs.next();
        String result = rs.getInt(1) + "," + rs.getString(2) + "," + rs.getInt(3) + "," + rs.getInt(4);
        System.out.println(result);
        name = rs.getString(2);
        playerId = id;
        return result;
    }
    private String joinGame(String given) throws SQLException {
        System.out.println("trying to join");
        boolean isFound = false;
        System.out.println("now"+Match.all);
        for(int i = 0;i < Match.all.size();i++) {
            if(given.equals(Match.all.get(i).uid)) {
                Match ourMatch = Match.all.get(i);
                this.role = "joiner";
                this.matchUid = ourMatch.uid;
                this.opponent = ourMatch.creator;
                ourMatch.creator.opponent = this;
                ourMatch.joiner = this;
                try {
                    this.opponent.tellClient("launch,"+name);

                    tellClient("launch,"+ourMatch.creator.name);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                isFound = true;
                createMatchDb(ourMatch);
            }
        }
        if(isFound) {
            return "1";
        }else {
            return "0";
        }
    }

    @Override
    public void run() {
        String msg;
        boolean goFlag = true;
        while (goFlag && socket.isConnected()) {
            try {
                msg = bufferedReader.readLine();

                String command = msg.split("~")[0];
                String[] value = msg.split("~")[1].split(",");
                if(command.equals("goodbye")) {
                    tellClient("goodbye");
                    goFlag = false;
                }else if (command.equals("create")) {
                    this.role = "creator";
                    Match match = new Match(this);
                    this.matchUid = match.uid;
                    try {
                        tellClient(match.uid);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else if (command.equals("join")) {
                    try {
                        tellClient(joinGame(value[0]));
                    } catch (IOException | SQLException e) {
                        e.printStackTrace();
                    }
                } else if (command.equals("move")) {
                    for (int i = 0; i < Match.all.size(); i++) {
                        if (matchUid.equals(Match.all.get(i).uid)) {
                            try {
                                opponent.tellClient(command+","+value[0]);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                } else if (command.equals("chat")) {
                    tellClient(command+","+name+": "+value[0]+"1");
                    opponent.tellClient(command+","+name+": "+value[0]+"0");

                } else if (command.equals("exit_game")) {
                    System.out.println("Herrrrrrrrrrr");
                    if(!value[0].equals("popup")) {
                        opponent.tellClient(command);
                    }
//                    for( Match a : Match.all){
//                        if(matchUid.equals(a.uid)){
//                            Match.all.remove(a);
//                        }
//                    }
                    int index = -1;
                    for(int i = 0;i < Match.all.size();i++) {
                        if(matchUid.equals(Match.all.get(i).uid)) {
                            index = i;
                        }
                    }
                    if(index != -1) {
                        Match.all.remove(index);
                    }
                } else if (command.equals("fetch_player")) {
                    if (value[0].equals("Nothing")) {
                        try {
                            tellClient(getPlayerDb(createPlayerDb()));
                        } catch (SQLException | IOException throwables) {
                            throwables.printStackTrace();
                        }
                    } else {
                        try {
                            tellClient(getPlayerDb(Integer.parseInt(value[0])));
                        } catch (SQLException | IOException throwables) {
                            throwables.printStackTrace();
                        }
                    }
                } else if (command.equals("update_player")) {
                    try {
                        updatePlayerDb(value[0]);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }else if (command.equals("set_winner")) {
                    setWinnerDb(value[0]);
                }else if (command.equals("leaderboard")) {
                    String result = "";
                    int rank;
                    try {
                        String sql = "SELECT RANK() OVER(ORDER BY won DESC) AS `rank` FROM player WHERE `id` = "+playerId+"";
                        Rankstm.setInt(1,Integer.parseInt(value[0]));
                        ResultSet rss = leaderboardStmt.executeQuery(sql);
                        rss.next();
                        rank = rss.getInt(1);
                        result += String.valueOf(rank)+ ",";

                        ResultSet resultSet =  leaderboardStmt.executeQuery("SELECT name, won FROM player ORDER BY won DESC LIMIT 10");
                        resultSet.next();
                        while (resultSet.next()){
                            String name =resultSet.getString("name");
                            int won = resultSet.getInt("won");
                            result +=name +"-"+ String.valueOf(won)+"||";
                        }


                        tellClient(result);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }

                }
            } catch (IOException | SQLException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Client Disconnected");
    }
}

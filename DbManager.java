package Crossbox;

import java.io.*;
import java.sql.*;

public class DbManager {
    Connection con;
    PreparedStatement prstm;

    DbManager() throws FileNotFoundException, IOException, SQLException {
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Crossbox", "root", "");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public Player whenStart() throws SQLException, IOException {
        int id;
        File myid = new File("myid.txt");
        if (!myid.exists()){
            int rand =(int) Math.floor(Math. random()*(10000 - 1000+1)+ 1000);
            String sql = "Insert into player (user_name,temp) values(?,?)";
            prstm = con.prepareStatement(sql);
            String temp_user_name = "Egele"+rand;
            prstm.setString(1,temp_user_name);
            prstm.setInt(2,rand);
            prstm.executeUpdate();

            prstm = con.prepareStatement("Select * from players where temp = ?");
            prstm.setInt(1,rand);
            ResultSet rs =prstm.executeQuery();
            id = rs.getInt(1);

            BufferedWriter writer = new BufferedWriter(new FileWriter("myid.txt"));
            writer.write(id);
        }

        try {
            BufferedReader br = new BufferedReader(new FileReader("myid.txt"));
            id = Integer.parseInt(br.readLine());
            return feachData(id);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


    public Player feachData (int id) throws SQLException {
        Player player = new Player();
        String sql = "SELECT * FROM player where player_id=?";
        prstm = con.prepareStatement(sql);
        prstm.setInt(1,id);
        ResultSet rs = prstm.executeQuery(sql);

        player.id = rs.getInt(1);
        player.name = rs.getString("name");
        player.won = rs.getInt("won");
        player.lost = rs.getInt("lost");
        return player;
    }

    public void updateDatabase(Player player) throws SQLException {
        prstm = con.prepareStatement("Update player set name =? , won = ? , lost = ? where id = ?");
        prstm.setString(1,player.name);
        prstm.setInt(2,player.won);
        prstm.setInt(3,player.lost);
        prstm.setInt(4,player.id);
        prstm.executeUpdate();
    }

}

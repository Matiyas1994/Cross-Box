package Crossbox;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private static Socket socket;
    public static String fromServer = "Nothing";
    private static BufferedReader bufferedReader;
    private static BufferedWriter bufferedWriter;
    public static Player player;
    Gui guibox;
    int counter = 0;
    public Client(Socket socket) throws IOException, InterruptedException {
        try {
            Client.socket = socket;
            Client.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            Client.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        }catch(IOException e) {
            e.printStackTrace();
        }

        listen();
        File myid = new File("myid.txt");
        if (myid.exists()){
            BufferedReader br = new BufferedReader(new FileReader("myid.txt"));
            String id = br.readLine();

            Client.tellServer("fetch_player", id);
            while(Client.fromServer.equals("Nothing")){
                System.out.println("Waiting for " + Client.fromServer);
            }
            String[] dbPlayer = Client.fromServer.split(",");

            player = new Player(Integer.parseInt(dbPlayer[0]), dbPlayer[1], Integer.parseInt(dbPlayer[2]), Integer.parseInt(dbPlayer[3]));
        }else {
            Client.tellServer("fetch_player", "Nothing");
            while(Client.fromServer.equals("Nothing")){
                System.out.println("Waiting for " + Client.fromServer);
            }
            String[] dbPlayer = Client.fromServer.split(",");
            BufferedWriter writer = new BufferedWriter(new FileWriter("myid.txt"));
            writer.write(dbPlayer[0]);
            writer.flush();
            writer.close();
            player = new Player(Integer.parseInt(String.valueOf(dbPlayer[0])), dbPlayer[1],
                    Integer.parseInt(String.valueOf(dbPlayer[2])), Integer.parseInt(String.valueOf(dbPlayer[3])));
        }
        this.guibox = new Gui();
    }
    public static void talk() {
        Scanner scanner = new Scanner(System.in);
        while(socket.isConnected()) {
            String msg = scanner.nextLine();
            try {
                bufferedWriter.write(msg);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
    public static void talkHalf(String given) {
        if(socket.isConnected()) {
            try {
                bufferedWriter.write(given);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static void tellServer(String command, String value) {
        if(socket.isConnected()) {
            try {
                bufferedWriter.write(command+"~"+value);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void listen() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(socket.isConnected()) {
                    try {
                        fromServer = bufferedReader.readLine();
                        //while (fromServer.equals("Nothing"))
                        System.out.println(fromServer);
                        if(fromServer.equals("exit_game")){
                            try {
                                guibox.gameBoard.handler.setEnable(false);
                                guibox.onFinish(3, guibox);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }else if(fromServer.length() >= 6 && fromServer.substring(0, 6).equals("launch")) {

                            player.oponnentName = fromServer.substring(7);
                            if(player.role.equals("creator")){
                                Gui.p1Label.setText(player.name);
                                Gui.p2Label.setText(player.oponnentName);
                            }
                            else {
                                Gui.p2Label.setText(player.name);
                                Gui.p1Label.setText(player.oponnentName);
                            }
                            guibox.alrt();
                        }else if(fromServer.length() >= 4 && fromServer.substring(0, 4).equals("move")) {
                            guibox.gameBoard.handler.onReceive(fromServer.split(",")[1]);
                        }else if(fromServer.length() >= 4 && fromServer.substring(0, 4).equals("chat")) {
                            JLabel l= new JLabel(fromServer.substring(5, fromServer.length()-1));
                            if(Character.toString(fromServer.charAt(fromServer.length()-1)).equals("1")){
                                l.setForeground(new Color(0,179,179));
                            }else{
                                l.setForeground(new Color(230,157,0));
                            }

                            Gui.chatPanel.add(l);
                            Gui.chatPanel.revalidate();
                            Gui.chatPanel.repaint();
                        }else if(fromServer.equals("goodbye")) {
                            socket.close();
                            break;
                        }
                        System.out.println("server: " + fromServer);
                    } catch (IOException e) {
                        e.printStackTrace();
                        break;
                    }
                }
            }
        }).start();
    }
}

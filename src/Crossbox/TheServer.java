package Crossbox;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TheServer {
    private final ServerSocket serverSocket;
    private Connection con;
    public TheServer(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
        try {
            this.con = DriverManager.getConnection("jdbc:mysql://localhost:3306/crossbox", "root", "root");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public void startServer() {
        try {
            while(!serverSocket.isClosed()) {
                System.out.println("The Server is running...");
                Socket socket = serverSocket.accept();
                System.out.println("A new client has been connected");
                ClientHandler clientHandler = new ClientHandler(socket, con);
                Thread thread = new Thread(clientHandler);
                thread.start();
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8000);
        TheServer theServer = new TheServer(serverSocket);
        theServer.startServer();
    }
}

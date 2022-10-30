package Crossbox;

import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.lang.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.Objects;

import static Crossbox.Client.tellServer;

/**
 * @author PT
 */
public class Gui extends JFrame implements MouseListener {
    private JFrame cbFrame;
    private JPanel introPanel;
    private JPanel createGamePanel;
    private JPanel joinGamePanel;
    protected static JPanel gamePanel;
    protected static JPanel gameWholePanel;
    private JPanel gameHeader;
    protected static JPanel p1Panel;
    protected static JPanel p2Panel;
    protected static JPanel popupPanel;
    private JPanel welcomePanel;
    protected static JPanel chatPanel;
    private JPanel profilePanel;
    private JPanel editProfilePanel;
    private JPanel createDisplayPanel;
    private JPanel joinDisplayPanel;

    protected static JScrollPane chatScrollPane;

    private JButton createButton;
    private JButton joinButton;
    private JButton profileButton;
    private JButton exitButton;
    private JButton exitGameButton;
    private JButton copyCodeButton;
    private JButton joinCodeButton;
    private JButton createHomeButton;
    private JButton joinHomeButton;
    private JButton chatButton;
    private JButton changeUNButton;
    private JButton profileHomeButton;
    private JButton topRanksButton;
    protected static JButton popupButton;

    private JTextField codeField;
    private JTextField joinCodeField;
    private JTextField userNameField;
    private JTextArea chatArea;

    private JLabel createGameMessage;
    private JLabel joinGameMessage;
    private JLabel gameVSLabel;
    protected static JLabel p1Label;
    protected static JLabel p2Label;
    protected static JLabel popupLabel;
    private JLabel winsLabel;
    protected static JLabel winsData;
    private JLabel lossesLabel;
    protected static JLabel lossesData;
    protected static JLabel updateNotifier;
    private JLabel rankLabel;
    protected static JLabel rankData;

    GameBoard gameBoard=new GameBoard(this);
    Border line =new LineBorder(new Color(52, 108, 158),2);
    Border empty = new EmptyBorder(10, 10, 10, 10);
    CompoundBorder border = new CompoundBorder(line, empty);
    static ImageIcon i = new ImageIcon(new ImageIcon(Objects.requireNonNull(Gui.class.getResource("/Crossbox/cup.gif"))).getImage().getScaledInstance(150, 150, Image.SCALE_DEFAULT));
    static ImageIcon i2 = new ImageIcon(new ImageIcon(Objects.requireNonNull(Gui.class.getResource("/Crossbox/loser.gif"))).getImage().getScaledInstance(150, 150, Image.SCALE_DEFAULT));
    private static class WelcomePanel extends JPanel {

        private int radius;
        private int insetwidth;

        WelcomePanel(int radius) {
            this.radius = radius;
//        super(label);
            Dimension size = getPreferredSize();
            size.width = size.height = Math.max(size.width, size.height);
            setPreferredSize(size);
            setOpaque(false);
        }


        @Override
        protected void paintBorder(Graphics g) {
            Font serifFont = new Font("Serif", Font.BOLD, 100);
            Graphics2D g2=(Graphics2D)g;
            g2.setStroke(new BasicStroke(10));
            g2.setColor(new Color(0,0,0));
            g2.fillRoundRect(5,5,getSize().width-10 , getSize().height-10,radius,radius);
            g2.setStroke(new BasicStroke(10));
            g2.setColor(new Color(84, 111, 153));
            g2.drawRoundRect(5,5,getSize().width-10 , getSize().height-10,radius,radius);
            String str="PRKRM";
            g2.setFont(serifFont);
            for(int i=0; i<str.length(); i++){
                if(i%2==0){
                    g2.setColor(new Color(0,179,179));
                }
                else{
                    g2.setColor(new Color(230,157,0));
                }
                g2.drawString(str.charAt(i)+"",  100+(85*i), (getSize().height)*3/5);
            }

            g2.setStroke(new BasicStroke(10));
            g2.setColor(new Color(24,56,78));
            g2.drawRoundRect(12,12,getSize().width-24 , getSize().height-24,radius-12,radius-12);
            g2.setColor(new Color(113,40,47));
            g2.drawRoundRect(19,19,getSize().width-38 , getSize().height-38,radius-19,radius-19);
        }

    }



    public Gui() throws InterruptedException {
//        initComponent();
        Image back = Toolkit.getDefaultToolkit().getImage(Gui.class.getResource("/Crossbox/game background.jpg"));
        cbFrame=new JFrame();
        cbFrame.setLayout(new CardLayout());
        cbFrame.setBackground(Color.WHITE);
        cbFrame.setTitle("CrossBox game");
        cbFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        cbFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Client.tellServer("goodbye", "Nothing");
                System.out.println("WindowClosingDemo.windowClosing");
                System.exit(0);
            }
        });
//home panel

        introPanel=new JPanel(null){
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                try {
                    Thread.sleep(1200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
//                Image i = Toolkit.getDefaultToolkit().getImage(Gui.class.getResource("/Crossbox/game background.jpg"));
                g.drawImage(back, 0, 0,this.getSize().width,this.getSize().height,null);
                this.repaint();
            }
        };
//        introPanel.setBackground(Color.gray);

        welcomePanel=new WelcomePanel(50);
        welcomePanel.setBounds(250,100,600,250);
        introPanel.add(welcomePanel);

        createButton=new JButton("Create Game");
        createButton.setBounds(325,400,200,40);
        createButton.setBackground(Color.black);
        createButton.setForeground(new Color(230,157,0));
        createButton.addMouseListener(this);
        introPanel.add(createButton);

        joinButton=new JButton("Join Game");
        joinButton.setBounds(575,400,200,40);
        joinButton.setBackground(Color.black);
        joinButton.setForeground(new Color(230,157,0));
        joinButton.addMouseListener(this);
        introPanel.add(joinButton);

        profileButton=new JButton("profile");
        profileButton.setBounds(325,460,200,40);
        profileButton.setBackground(Color.black);
        profileButton.setForeground(new Color(0,179,179));
        profileButton.addMouseListener(this);
        introPanel.add(profileButton);

        exitButton=new JButton("Exit");
        exitButton.setBounds(575,460,200,40);
        exitButton.setBackground(Color.black);
        exitButton.setForeground(new Color(0,179,179));
        exitButton.addMouseListener(this);
        introPanel.add(exitButton);



//gamePanel
        gamePanel=new JPanel(null){
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon img = new ImageIcon("");
                Image i = Toolkit.getDefaultToolkit().getImage(Gui.class.getResource("/Crossbox/game background.jpg"));
                g.drawImage(i, 0, 0,this.getSize().width,this.getSize().height,null);

                Graphics2D g2= (Graphics2D)g;
                g2.setColor(new Color(52, 108, 158));
                g2.setStroke(new BasicStroke(10));
                g2.drawRoundRect(20,140,250,340,5,5);
                g2.drawRoundRect(20,500,250,50,5,5);
                g2.drawRoundRect(300,90,750,600,5,5);
                g2.drawRoundRect(300,5,750,80,5,5);

            }
        };
        gamePanel.add(gameBoard);


        gameHeader= new JPanel(null);
        gameHeader.setBackground(Color.black);
        gameHeader.setBounds(300,5,750,80);
        gamePanel.add(gameHeader);

        gameVSLabel= new JLabel("Game:                          Vs");
        gameVSLabel.setBounds(100,5,525,70);
        gameVSLabel.setForeground(new Color(52, 108, 158));
        gameVSLabel.setFont(new Font("Arial",Font.PLAIN,30));

        p1Panel= new JPanel();
        p1Panel.setBackground(new Color(113,40,47));
        p1Panel.setBounds(200,20,180,40);
            p1Label= new JLabel("Player 1");
            p1Label.setFont(new Font("Arial",Font.PLAIN,20));
            p1Label.setForeground(Color.white);
        p1Panel.add(p1Label);

        p2Panel= new JPanel();
        p2Panel.setBackground(Color.black);
        p2Panel.setBounds(480,20,180,40);
            p2Label= new JLabel("Player 2");
            p2Label.setFont(new Font("Arial",Font.PLAIN,20));
            p2Label.setForeground(Color.white);
        p2Panel.add(p2Label);

            gameHeader.add(gameVSLabel);
            gameHeader.add(p1Panel);
            gameHeader.add(p2Panel);

        JPanel chatHeader=new JPanel();
        chatHeader.setBackground(Color.black);
        chatHeader.setBounds(20,140,250,40);

         chatPanel=new JPanel(new GridLayout(0,1));
            chatPanel.setBackground(new Color(5,5,5));
            chatPanel.setForeground(Color.white);
            chatPanel.setSize(250,9000);

        chatScrollPane=new JScrollPane(chatPanel,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        chatScrollPane.setBounds(20,180,250,300);


        JLabel chatLbl=new JLabel("Chat Box");
        chatLbl.setForeground(new Color(230,157,0));
        chatHeader.add(chatLbl);

        chatArea=new JTextArea();
        chatArea.setBounds(20,500,250,50);
        chatArea.setMargin(new Insets(10,10,10,10));
        chatArea.setBackground(Color.black);
        chatArea.setForeground(new Color(230,157,0));

        chatButton = new JButton("Send");
        chatButton.setBounds(200,560,70,30);
        chatButton.setBackground(Color.black);
        chatButton.setForeground(new Color(230,157,0));
        chatButton.addMouseListener(this);


        gamePanel.add(chatHeader);
        gamePanel.add(chatScrollPane);
        gamePanel.add(chatArea);
        gamePanel.add(chatButton);

         exitGameButton=new JButton("Exit Game");
         exitGameButton.setBounds(20,670,120,30);
         exitGameButton.setBackground(Color.black);
         exitGameButton.setForeground(new Color(230,157,0));
         exitGameButton.addMouseListener(this);
         gamePanel.add(exitGameButton);
         gamePanel.setBounds(0,0,1100,750);


//Whole game panel
        gameWholePanel= new JPanel(null);

        gameWholePanel.add(gamePanel);
        gameWholePanel.setVisible(false);

//profile panel
        profilePanel=new JPanel(null){
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon img = new ImageIcon("");
                Image i = Toolkit.getDefaultToolkit().getImage(Gui.class.getResource("/Crossbox/game background.jpg"));
                g.drawImage(i, 0, 0,this.getSize().width,this.getSize().height,null);

                Graphics2D g2= (Graphics2D)g;
                g2.setColor(new Color(52, 108, 158));
                g2.setStroke(new BasicStroke(10));
                g2.drawRoundRect(50,200,1000,200,5,5);

            }
        };


        editProfilePanel=new JPanel(null){
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2= (Graphics2D)g;
                g2.setColor(new Color(52, 108, 158));
                g2.setStroke(new BasicStroke(10));

            }
        };
        editProfilePanel.setBounds(50,200,1000,200);
        editProfilePanel.setBackground(Color.black);
        profilePanel.add(editProfilePanel);

        JLabel userNm =new JLabel("Username");
        userNm.setForeground(new Color(0,179,179));
        userNm.setBounds(150,40,150,30);
        userNm.setFont(new Font("Arial",Font.PLAIN,20));
        editProfilePanel.add(userNm);

        userNameField = new JTextField();
        userNameField.setBounds(150,80,200,40);
        userNameField.setBackground(Color.black);
        userNameField.setForeground(new Color(230,157,0));
        userNameField.setText(Client.player.name);
        userNameField.setBorder(border);

        editProfilePanel.add(userNameField);

        updateNotifier= new JLabel();
        updateNotifier.setBounds(200,130,200,40);
        updateNotifier.setForeground(Color.green);
        updateNotifier.setFont(new Font("Arial",Font.PLAIN,15));

        editProfilePanel.add(updateNotifier);

        changeUNButton=new JButton("change");
        changeUNButton.setBackground(Color.black);
        changeUNButton.setForeground(new Color(230,157,0));
        changeUNButton.setBounds(370,80,90,40);
        changeUNButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Client.player.name = userNameField.getText();
                tellServer("update_player", Client.player.stringfyPlayer());
                userNameField.setText(Client.player.name);
                updateNotifier.setText("Updated Successfully");
            }
        });
        editProfilePanel.add(changeUNButton);

         winsLabel = new  JLabel("Wins: ");
         winsLabel.setForeground(new Color(0,179,179));
         winsLabel.setBounds(510,40,70,30);
         winsLabel.setFont(new Font("Arial",Font.PLAIN,20));
         editProfilePanel.add(winsLabel);

         winsData = new  JLabel("0");
         winsData.setForeground(new Color(230,157,0));
         winsData.setBounds(580,40,150,30);
         winsData.setFont(new Font("Arial",Font.PLAIN,20));
         winsData.setText(String.valueOf(Client.player.won));
         editProfilePanel.add(winsData);

        lossesLabel= new  JLabel("Losses: ");
        lossesLabel.setForeground(new Color(0,179,179));
        lossesLabel.setBounds(510,80,80,30);
        lossesLabel.setFont(new Font("Arial",Font.PLAIN,20));
        editProfilePanel.add(lossesLabel);

        lossesData = new  JLabel("0");
        lossesData.setForeground(new Color(230,157,0));
        lossesData.setBounds(590,80,150,30);
        lossesData.setFont(new Font("Arial",Font.PLAIN,20));
        lossesData.setText(String.valueOf(Client.player.lost));
        editProfilePanel.add(lossesData);

        rankLabel =new  JLabel("Your Rank: ");
        rankLabel.setForeground(new Color(0,179,179));
        rankLabel.setBounds(510,120,110,30);
        rankLabel.setFont(new Font("Arial",Font.PLAIN,20));
//        editProfilePanel.add(rankLabel);

        rankData = new  JLabel("0");
        rankData.setForeground(new Color(230,157,0));
        rankData.setBounds(620,120,150,30);
        rankData.setFont(new Font("Arial",Font.PLAIN,20));
//        editProfilePanel.add(rankData);

        topRanksButton=new JButton("top 10 Scorers");
        topRanksButton.setBackground(Color.black);
        topRanksButton.setForeground(new Color(230,157,0));
        topRanksButton.setBounds(510,160,250,30);
        topRanksButton.addMouseListener(this);
//        editProfilePanel.add(topRanksButton);

        profileHomeButton=new JButton("Home");
        profileHomeButton.setBackground(Color.black);
        profileHomeButton.setForeground(new Color(230,157,0));
        profileHomeButton.setBounds(10,10,80,30);
        profileHomeButton.addMouseListener(this);
        profilePanel.add(profileHomeButton);

        profilePanel.setVisible(false);

//create game panel
        createGamePanel=new JPanel(null){
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon img = new ImageIcon("");
                
                Image i = Toolkit.getDefaultToolkit().getImage(Gui.class.getResource("/Crossbox/game background.jpg"));
                
                g.drawImage(i, 0, 0,this.getSize().width,this.getSize().height,null);
                Graphics2D g2= (Graphics2D)g;
                g2.setColor(new Color(52, 108, 158));
                g2.setStroke(new BasicStroke(10));
                g2.drawRoundRect(100,200,900,200,5,5);
            }
        };

        createGamePanel.setVisible(false);

        createDisplayPanel = new JPanel(null);
        createDisplayPanel.setBackground(Color.black);
        createDisplayPanel.setBounds(100,200,900,200);
        createGamePanel.add(createDisplayPanel);

        codeField=new JTextField();
        codeField.setBounds(50,50,700,40);
        codeField.setMargin(new Insets(10,10,10,10));
        codeField.setBackground(Color.black);
        codeField.setForeground(new Color(230,157,0));
        codeField.setBorder(border);
        createDisplayPanel.add(codeField);

        copyCodeButton=new JButton("Copy Code");
        copyCodeButton.setBounds(780,50,100,40);
        copyCodeButton.setBackground(Color.black);
        copyCodeButton.setForeground(new Color(230,157,0));
        copyCodeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                String selection = codeField.getText();
                if (selection == null){
                    return;
                }

                StringSelection clipString =new StringSelection(selection);

                Clipboard cBoard = getToolkit().getSystemClipboard();
                cBoard.setContents(clipString,clipString);
            }
        });
        createDisplayPanel.add(copyCodeButton);


        createGameMessage= new JLabel();
        ImageIcon loading = new ImageIcon(new ImageIcon(Objects.requireNonNull(Gui.class.getResource("/Crossbox/loading.gif"))).getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));

        createGameMessage.setIcon(loading);
        createGameMessage.setText("Waiting for player 2 to join... \n\n (send this code to your friend) ");
        createGameMessage.setBounds(120,100,700,60);
        createGameMessage.setForeground(new Color(230,157,0));
        createGameMessage.setFont(new Font("Arial",Font.PLAIN,22));
        createDisplayPanel.add(createGameMessage);

        createHomeButton=new JButton("Home");
        createHomeButton.setBounds(10,10,80,30);
        createHomeButton.setBackground(Color.black);
        createHomeButton.setForeground(new Color(230,157,0));
        createHomeButton.addMouseListener(this);
        createGamePanel.add(createHomeButton);

//join game panel
        joinGamePanel=new JPanel(null){
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon img = new ImageIcon("");
                
                Image i = Toolkit.getDefaultToolkit().getImage(Gui.class.getResource("/Crossbox/game background.jpg"));
                
                g.drawImage(i, 0, 0,this.getSize().width,this.getSize().height,null);
                Graphics2D g2= (Graphics2D)g;
                g2.setColor(new Color(52, 108, 158));
                g2.setStroke(new BasicStroke(10));
                g2.drawRoundRect(100,200,900,200,5,5);

            }
        };
        joinGamePanel.setVisible(false);

        joinDisplayPanel = new JPanel(null);
        joinDisplayPanel.setBackground(Color.black);
        joinDisplayPanel.setBounds(100,200,900,200);
        joinGamePanel.add(joinDisplayPanel);


        joinCodeField=new JTextField("\tEnter invitation Code");
        joinCodeField.setBounds(50,50,700,40);
        joinCodeField.setMargin(new Insets(10,10,10,10));
        joinCodeField.setBackground(Color.black);
        joinCodeField.setForeground(new Color(230,157,0));
        joinCodeField.setBorder(border);
        joinCodeField.addMouseListener(this);

        joinDisplayPanel.add(joinCodeField);

        joinCodeButton=new JButton("Join Game");
        joinCodeButton.setBounds(780,50,100,40);
        joinCodeButton.setBackground(Color.black);
        joinCodeButton.setForeground(new Color(230,157,0));
        joinCodeButton.addMouseListener(this);
        joinDisplayPanel.add(joinCodeButton);

        joinGameMessage= new JLabel("Get Your Invitation Code from a friend");
        joinGameMessage.setBounds(120,100,700,60);
        joinGameMessage.setForeground(new Color(230,157,0));
        joinGameMessage.setFont(new Font("Arial",Font.PLAIN,20));
        joinDisplayPanel.add(joinGameMessage);

        joinHomeButton=new JButton("Home");
        joinHomeButton.setBackground(Color.black);
        joinHomeButton.setForeground(new Color(230,157,0));
        joinHomeButton.setBounds(10,10,80,30);
        joinHomeButton.addMouseListener(this);
        joinGamePanel.add(joinHomeButton);





        cbFrame.add(introPanel);
        cbFrame.add(createGamePanel);
        cbFrame.add(joinGamePanel);
        cbFrame.add(gameWholePanel);
        cbFrame.add(profilePanel);
        cbFrame.pack();
        cbFrame.setSize(1100,750);
        cbFrame.setLocation(200,0);
        cbFrame.setVisible(true);
        cbFrame.setResizable(false);
        cbFrame.setTitle("CrossBox");
        ImageIcon img = new ImageIcon(Gui.class.getResource("/Crossbox/gameIcon.png"));
        cbFrame.setIconImage(img.getImage());

    }
    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource()==createButton){
            tellServer("create", "Nothing");
            introPanel.setVisible(false);
            joinGamePanel.setVisible(false);
            createGamePanel.setVisible(true);
            profilePanel.setVisible(false);
            gameWholePanel.setVisible(false);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
            codeField.setText(Client.fromServer);
            Client.player.role = "creator";
        }
        else if(e.getSource()==createHomeButton || e.getSource()==joinHomeButton|| e.getSource()==profileHomeButton){
            introPanel.setVisible(true);
            createGamePanel.setVisible(false);
            joinGamePanel.setVisible(false);
            profilePanel.setVisible(false);
            gameWholePanel.setVisible(false);
            updateNotifier.setText("");
        }
        else if(e.getSource()==joinButton){
            introPanel.setVisible(false);
            joinGamePanel.setVisible(true);
            createGamePanel.setVisible(false);
            profilePanel.setVisible(false);
            gameWholePanel.setVisible(false);
        }
        else if(e.getSource()==joinCodeButton) {
            if(joinCodeField.getText().equals("")) {
                joinGameMessage.setText("NO Such Game Exist");
                return;
            }
            Client.player.role = "joiner";
            tellServer("join", joinCodeField.getText());
            joinGameMessage.setText("Searching for Game to join...");
            while(Client.fromServer.equals("Nothing")) {
                System.out.println("Waiting for " + Client.fromServer);
            }
            if(Client.fromServer.equals("1")) {
                this.gameBoard.handler.setEnable(false);

            }else {
                joinGameMessage.setText("NO Such Game Exist");
            }
        }
        else if(e.getSource()==exitButton) {  cbFrame.dispatchEvent(new WindowEvent(cbFrame, WindowEvent.WINDOW_CLOSING));
        }
        else if(e.getSource()==profileButton){
            introPanel.setVisible(false);
            joinGamePanel.setVisible(false);
            createGamePanel.setVisible(false);
            gameWholePanel.setVisible(false);
            profilePanel.setVisible(true);
        }
        else if(e.getSource()==exitGameButton){
            introPanel.setVisible(true);
            joinGamePanel.setVisible(false);
            createGamePanel.setVisible(false);
            gameWholePanel.setVisible(false);
            profilePanel.setVisible(false);
            tellServer("exit_game","Nothing");
        }
        else if(e.getSource()==chatButton && !chatArea.getText().toString().isEmpty()){
            tellServer("chat", chatArea.getText().toString());
            chatArea.setText("");
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
       if(e.getSource()==joinCodeField) {
           if (joinCodeField.getText().equals("\tEnter invitation Code")) {
               joinCodeField.setText("");
//               joinCodeField.setForeground(Color.BLACK);
           }
       }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if(e.getSource()==joinCodeField) {
            if (joinCodeField.getText().isEmpty()) {
//                joinCodeField.setForeground(Color.GRAY);
                joinCodeField.setText("\tEnter invitation Code");
            }
         }
    }

    public void alrt() {
        introPanel.setVisible(false);
        joinGamePanel.setVisible(false);
        createGamePanel.setVisible(false);
        profilePanel.setVisible(false);
        gameWholePanel.setVisible(true);

        GameBoard.btn7.setEnabled(false);
        GameBoard.btn8.setEnabled(false);
        GameBoard.btn9.setEnabled(false);

        if (Client.player.role.equals("joiner")){
            GameBoard.btn1.setEnabled(false);
            GameBoard.btn2.setEnabled(false);
            GameBoard.btn3.setEnabled(false);
            GameBoard.btn4.setEnabled(false);
            GameBoard.btn5.setEnabled(false);
            GameBoard.btn6.setEnabled(false);
        }
    }

    public static void onFinish(int finished,Gui gui2) throws InterruptedException  {
        //popup panel
        popupPanel= new JPanel(null){
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2= (Graphics2D)g;
                g2.setColor(new Color(113,40,47));
                g2.setStroke(new BasicStroke(10));
                g2.drawRoundRect(85,55,810,180,5,5);
                g2.setColor(new Color(230,157,0));
                g2.drawRoundRect(95,65,790,160,5,5);
            }
        };
        popupPanel.setBackground(Color.black);
        popupPanel.setBounds(100,175,900,300);
            popupLabel=new JLabel();
            popupLabel.setFont(new Font("Arial",Font.PLAIN,40));
            popupLabel.setBounds(90,40,900,200);


            popupButton = new JButton("Ok");
            popupButton.setBounds(150,250,100,40);
            popupButton.setBackground(Color.black);
            popupButton.setForeground(new Color(230,157,0));
                Border line =new LineBorder(new Color(230,157,0),2);
                Border empty = new EmptyBorder(10, 10, 10, 10);
                CompoundBorder border = new CompoundBorder(line, empty);
            popupButton.setBorder(border);
            popupButton.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e){

                    tellServer("exit_game","popup");
                    System.out.println("POoooop");
                    gui2.introPanel.setVisible(true);
                    gui2.joinGamePanel.setVisible(false);
                    gui2.createGamePanel.setVisible(false);
                    gameWholePanel.setVisible(false);
                    gui2.profilePanel.setVisible(false);
                    popupPanel.setVisible(false);
                    Gui.onReset(gui2);
                    Component[] com =gamePanel.getComponents();
                    for (int a = 0; a < com.length; a++) {
                        com[a].setEnabled(true);
                    }
                    chatScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
                    gamePanel.setEnabled(true);
                }
            });

        popupPanel.add(popupLabel);
        popupPanel.add(popupButton);


            if (finished==1) {
                System.out.println("Player 1 wins");
                tellServer("set_winner","1");
                if (Client.player.role.equals("creator")) {
                    Client.player.won +=1;
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                ImageIcon i = new ImageIcon(new ImageIcon(Objects.requireNonNull(Gui.class.getResource("/Crossbox/cup.gif"))).getImage().getScaledInstance(150, 150, Image.SCALE_DEFAULT));
                   popupLabel.setIcon(i);
                   popupLabel.setForeground(new Color(230,157,0));
                   popupLabel.setText("\tYou Win!!!");
                }
                else {
                    Client.player.lost +=1;
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                    ImageIcon i = new ImageIcon(new ImageIcon(Objects.requireNonNull(Gui.class.getResource("/Crossbox/loser.gif"))).getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
                   popupLabel.setIcon(i);
                   popupLabel.setText("You Lose");
                   popupLabel.setForeground(new Color(113,40,47));
                }
            }
            else if(finished==3){
                int err = JOptionPane.showOptionDialog(null, "Connection Lost the other Player has Left the Game! \n", "Connection Lost", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
                if(err == 0) {
                    popupButton.doClick();
                    gui2.joinCodeField.setText("");
                    return;
                }
            }
            else {
                tellServer("set_winner","2");
                System.out.println("Player 2 wins");
                if (Client.player.role.equals("creator")) {
                    Client.player.lost +=1;
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                    ImageIcon i = new ImageIcon(new ImageIcon(Objects.requireNonNull(Gui.class.getResource("/Crossbox/loser.gif"))).getImage().getScaledInstance(150, 150, Image.SCALE_DEFAULT));
                    popupLabel.setIcon(i);
                    popupLabel.setText("You Lose");
                    popupLabel.setForeground(new Color(113,40,47));

                }
                else {
                    Client.player.won +=1;
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                    ImageIcon i = new ImageIcon(new ImageIcon(Objects.requireNonNull(Gui.class.getResource("/Crossbox/cup.gif"))).getImage().getScaledInstance(150, 150, Image.SCALE_DEFAULT));
                    popupLabel.setIcon(i);
                    popupLabel.setForeground(new Color(230,157,0));
                    popupLabel.setText("You Win!!!");
                }

            }
            Client.tellServer("update_player", Client.player.stringfyPlayer());


            Component[] com =gamePanel.getComponents();
            for (int a = 0; a < com.length; a++) {
                com[a].setEnabled(false);
            }
           chatScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
           gamePanel.setEnabled(false);

           p1Panel.setBackground(new Color(113,40,47));
           p2Panel.setBackground(new Color(113,40,47));

        popupLabel.setVisible(true);

           gameWholePanel.add(popupPanel);
           gameWholePanel.revalidate();
           gameWholePanel.repaint();
        popupLabel.revalidate();
        popupLabel.repaint();
        gui2.joinCodeField.setText("");
        }

    public static void onReset(Gui gui2){
        System.out.println("resetddddd");
        ButtonHandler.p1.clear();
        ButtonHandler.p2.clear();
        gui2.gameBoard.handler.holder.clear();

        gui2.gameBoard.handler.setEnable(true);
        Logic.board[0][0]=1;
        Logic.board[0][1]=1;
        Logic.board[0][2] =1;
        Logic.board[1][0]=0;
        Logic.board[1][1]=0;
        Logic.board[1][2] =0;
        Logic.board[2][0]=7;
        Logic.board[2][1]=7;
        Logic.board[2][2] =7;


        ButtonHandler.p1.add(GameBoard.btn1);
        ButtonHandler.p1.add(GameBoard.btn2);
        ButtonHandler.p1.add(GameBoard.btn3);
        ButtonHandler.p2.add(GameBoard.btn7);
        ButtonHandler.p2.add(GameBoard.btn8);
        ButtonHandler.p2.add(GameBoard.btn9);

        GameBoard.btn7.setEnabled(false);
        GameBoard.btn8.setEnabled(false);
        GameBoard.btn9.setEnabled(false);

        if (Client.player.role.equals("joiner")){
            GameBoard.btn1.setEnabled(false);
            GameBoard.btn2.setEnabled(false);
            GameBoard.btn3.setEnabled(false);
            GameBoard.btn4.setEnabled(false);
            GameBoard.btn5.setEnabled(false);
            GameBoard.btn6.setEnabled(false);
        }
        GameBoard.btn1.setBackground(new Color(230,157,0));
        GameBoard.btn2.setBackground(new Color(230,157,0));
        GameBoard.btn3.setBackground(new Color(230,157,0));

        GameBoard.btn4.setBackground(new Color(113,40,47));
        GameBoard.btn5.setBackground(new Color(113,40,47));
        GameBoard.btn6.setBackground(new Color(113,40,47));

        GameBoard.btn7.setBackground(new Color(0,179,179));
        GameBoard.btn8.setBackground(new Color(0,179,179));
        GameBoard.btn9.setBackground(new Color(0,179,179));

        //gui2.gameBoard.revalidate();
        //gui2.gameBoard.repaint();

    }

}

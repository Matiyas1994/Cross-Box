package Crossbox;
import java.lang.*;
import javax.swing.*;
import java.awt.*;

/**
 * @author Matiyas
 */

class RoundButton extends JButton {
    public RoundButton() {
//        super(label);
        Dimension size = getPreferredSize();
        size.width = size.height = Math.max(size.width, size.height);
        setPreferredSize(size);
        setContentAreaFilled(false);
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (getModel().isArmed()) {
            g.setColor(Color.lightGray);
        } else {
            g.setColor(getBackground());
        }

        g.fillOval(0, 0, getSize().width - 1, getSize().height - 1);

        super.paintComponent(g);
    }

    @Override
    protected void paintBorder(Graphics g) {
//        g.setStroke(new BasicStroke(1));
        Graphics2D g2=(Graphics2D)g;
        g2.setStroke(new BasicStroke(1));
        g2.setColor(new Color(113,40,47));
        g2.drawOval(0, 0, getSize().width-1 , getSize().height-1 );
    }

//    Shape shape;
//
//    @Override
//    public boolean contains(int x, int y) {
//        if (shape == null ||
//                !shape.getBounds().equals(getBounds())) {
//            shape = new Ellipse2D.Float(0, 0, getWidth(), getHeight());
//        }
//        return shape.contains(x, y);
//    }
}

/**
 * @author Matiyas
 */
public class GameBoard extends JPanel{

    static protected JButton btn1;
    static protected JButton btn2;
    static protected JButton btn3;
    static protected JButton btn4;
    static protected JButton btn5;
    static protected JButton btn6;
    static protected JButton btn7;
    static protected JButton btn8;
    static protected JButton btn9;
    ButtonHandler handler ;

    public int RadiusCalc(int r){
        return (r*50)/100;
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2=(Graphics2D)g;
        g2.setStroke(new BasicStroke(10));
        g2.setColor(new Color(24,56,78));

        g2.fillRect(125,55,500,500);
        g2.setColor(new Color(113,40,47));
        g2.drawRect(125,55,500,500);
        g2.setStroke(new BasicStroke(9));
        g2.drawLine(126,56,624,554);
        g2.drawLine(624,56,126,554);
        g2.drawLine(375,56,375,554);
        g2.drawLine(125,300,625,300);

    }
    public GameBoard(Gui gui2) {
       handler = new ButtonHandler(this,gui2);
        setLayout(null);
        setBackground(new Color(0,0,0));
        setBounds(300,90,750,600);

        btn1=new RoundButton();
        btn1.setBounds(95,30,60,60);
        btn1.setBackground(new Color(230,157,0));
        btn1.addActionListener(handler);


        btn2=new RoundButton();
        btn2.setBounds(345,30,60,60);
        btn2.setBackground(new Color(230,157,0));
        btn2.addActionListener(handler);

        btn3=new RoundButton();
        btn3.setBounds(595,30,60,60);
        btn3.setBackground(new Color(230,157,0));
        btn3.addActionListener(handler);


        btn4=new RoundButton();
        btn4.setBounds(95,270,60,60);
        btn4.setBackground(new Color(113,40,47));
        btn4.addActionListener(handler);

        btn5=new RoundButton();
        btn5.setBounds(345,270,60,60);
        btn5.setBackground(new Color(113,40,47));
        btn5.addActionListener(handler);

        btn6=new RoundButton();
        btn6.setBounds(595,270,60,60);
        btn6.setBackground(new Color(113,40,47));
        btn6.addActionListener(handler);

        btn7=new RoundButton();
        btn7.setBounds(95,525,60,60);
        btn7.setBackground(new Color(0,179,179));

        btn7.addActionListener(handler);


        btn8=new RoundButton();
        btn8.setBounds(345,525,60,60);
        btn8.setBackground(new Color(0,179,179));
        btn8.addActionListener(handler);

        btn9=new RoundButton();
        btn9.setBounds(595,525,60,60);
        btn9.setBackground(new Color(0,179,179));
        btn9.addActionListener(handler);


        this.add(btn1);
        this.add(btn2);
        this.add(btn3);
        this.add(btn4);
        this.add(btn5);
        this.add(btn6);
        this.add(btn7);
        this.add(btn8);
        this.add(btn9);

        handler.button_cordinates.put(btn1,"00");
        handler.button_cordinates.put(btn2,"01");
        handler.button_cordinates.put(btn3,"02");
        handler.button_cordinates.put(btn4,"10");
        handler.button_cordinates.put(btn5,"11");
        handler.button_cordinates.put(btn6,"12");
        handler.button_cordinates.put(btn7,"20");
        handler.button_cordinates.put(btn8,"21");
        handler.button_cordinates.put(btn9,"22");
        handler.p1.add(btn1);
        handler.p1.add(btn2);
        handler.p1.add(btn3);
        handler.p2.add(btn7);
        handler.p2.add(btn8);
        handler.p2.add(btn9);



//
//        for(int i = 0; i <3; i++){
//            if (Client.player.role.equals("creator")){
//                handler.p2.get(i).setEnabled(false);
////                Gui.p1Panel.setBackground(new Color(113,40,47));
////                Gui.p2Panel.setBackground(Color.black);
//            }
//
//            else {
//                handler.p1.get(i).setEnabled(false);
////                Gui.p2Panel.setBackground(new Color(113,40,47));
////                Gui.p1Panel.setBackground(Color.black);
//            }
//        }

    }

}

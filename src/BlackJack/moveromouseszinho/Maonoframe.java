/*
 * Main.java
 * 
 * Created on 21/01/2008, 01:44:50
 * 
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package BlackJack.moveromouseszinho;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;


/**
 *
 * @author Waldney Andrade
 */
public class Maonoframe extends JFrame{
    private int xMao,yMao;
    private Rectangle boundsMao;
    private int pt1X,pt1Y, pt2X,pt2Y, pt3X,pt3Y;
    private MouseAdapter ml;
    private JLabel jlmao=new JLabel(new ImageIcon("src\\BlackJack\\moveromouseszinho\\maozinha.PNG"));
       
    /** Creates a new instance of Main */
    public Maonoframe() {
        setSize(640,439);
        pt1X=100;
        pt1Y=100;
        pt2X=200;
        pt2Y=200;
        pt3X=300;
        pt3Y=300;
        ml=new MouseAdapter(){
          public void mousePressed(MouseEvent me){
                if(getCont().isAncestorOf(jlmao)){
                    getCont().remove(jlmao);
                }
                xMao=me.getX();
                yMao=me.getY();
                boundsMao=new Rectangle(xMao, yMao, 17, 22);
                //System.out.println(""+boundsMao.toString());
                jlmao.setBounds(boundsMao);
                getCont().add(jlmao,0);
                getCont().repaint();
                movetoPoint(pt1X,pt1Y);
                movetoPoint(pt2X,pt2Y);
           }
          public void movetoPoint(int ptX, int ptY){
            
                try {
                  while(xMao!=ptX || yMao!=ptY){
                     if(xMao<ptX-2){
                            java.lang.Thread.sleep(1);
                            xMao+=3;
                            getCont().remove(jlmao);
                            refazerMao();
                            
                    }
                     if(xMao>=ptX-2 && xMao<ptX){
                         java.lang.Thread.sleep(1);
                            xMao+=1;
                            getCont().remove(jlmao);
                            refazerMao();
                     }
                     if(xMao-2>ptX){
                         java.lang.Thread.sleep(1);
                         xMao-=3;
                         getCont().remove(jlmao);
                         refazerMao();
                     }
                     if(xMao-2<=ptX && xMao>ptX){
                         java.lang.Thread.sleep(1);
                            xMao-=1;
                            getCont().remove(jlmao);
                            refazerMao();
                     }
                     if(yMao<ptY-2){
                      
                            java.lang.Thread.sleep(1);
                            yMao+=3;
                            getCont().remove(jlmao);
                            refazerMao();
                            
                    }
                     if(yMao>=ptY-2 && yMao<ptY){
                         java.lang.Thread.sleep(1);
                            yMao+=1;
                            getCont().remove(jlmao);
                            refazerMao();
                     }
                     if(yMao-2>ptY){
                         java.lang.Thread.sleep(1);
                         yMao-=3;
                         getCont().remove(jlmao);
                         refazerMao();
                     }
                     if(yMao-2<=ptY && yMao>ptY){
                         java.lang.Thread.sleep(1);
                            yMao-=1;
                            getCont().remove(jlmao);
                            refazerMao();
                     }
                              
                            
                  }
                }catch (InterruptedException ex) {
                            Logger.getLogger("global").log(Level.SEVERE, null, ex);
                }
                
          }
        };
        setLayout(null);
        getContentPane().setBackground(new Color(0,100,0));
        getContentPane().addMouseListener(ml);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public Container getCont(){
        return getContentPane();
    }
    public void refazerMao(){
        boundsMao = new java.awt.Rectangle(xMao, yMao, 17, 22);
                            jlmao.setBounds(boundsMao);
                            getCont().add(jlmao,0);
                            getCont().paint(getCont().getGraphics());
                            getCont().validate();
                            java.lang.System.out.println("" + boundsMao.toString());
    }
    /**
     * @param args the command line arguments
     */
    //public static void main(String[] args) {
      //  new Maonoframe();
    //}

}

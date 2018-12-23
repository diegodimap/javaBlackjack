package BlackJack;
//para remover componentes remova do Container do JFrame.....obs.:já criastes o método getCont();

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.Random;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

public class BlackJack extends JFrame implements MouseListener,ActionListener{
    private JPanel p= new JPanel();
    private File pasta;
    private String[] auxdeck;
    private LinkedList<String> deck=new LinkedList<String>();
    private Baralho<String> baralho;
    private JButton embaralhar;
    private Vector<Carta> maomesa, maojogador;
    private String dir;
    private JButton puxar;
    private JMenuItem iniciar,sair;
    private JButton aposta5,aposta10,aposta50,aposta100,finalizar, dobrar;
    private int aposta=0;
    private JLabel apostatotal;
    private int cont=0;
    private int podeapostar=0;
    private int xMao,yMao;
    private Rectangle boundsMao;
    private int pt1X,pt1Y, pt2X,pt2Y, pt3X,pt3Y;
    private MouseAdapter ml;
    private JLabel jlmao=new JLabel(new ImageIcon("src\\BlackJack\\moveromouseszinho\\maozinha.PNG"));
    private int deDobrar=0;
    private JLabel pontosmao;
    private Graphics gc;
    private JPanel p1[]=new JPanel[2];
    private JPanel p2[]=new JPanel[2];
    private Container c;
    private JPanel panel[]=new JPanel[5];
    private JLabel pontosmesa;
    private boolean vezdamesa=false;
    private JPanel panel2[]=new JPanel[5];
    private int cont2=0;
    private int cont3=0;
    private MouseEvent mevent;
    private javax.swing.Timer t;
    private Cursor cursor;
    private boolean esperar=false;
    private String nome,escolha;
    private int fichas;
    private JLabel nomejogador;
    private JLabel jlaposta;
    private JLabel dealer;
    private int sl=1;
    private boolean mouselivre=false;
    private int resposta=JOptionPane.NO_OPTION;
    private boolean peeked=false;
    //fazer o peek
    
    public BlackJack(){
        setSize(640,480);
        setResizable(false);
        setLocationRelativeTo(this);
        maomesa=new Vector<Carta>();
        maojogador=new Vector<Carta>();
        auxdeck=new String[53];
        dir="src\\BlackJack\\cartas";
        t=new javax.swing.Timer(1000,(ActionListener)this);
        pasta=new File(dir);
        setTitle("SunCity JBlackJack");
        
        
        nome=Inicial.getNameJogador().toUpperCase();
        fichas=Inicial.getFichas();
        escolha=Inicial.getSelecionado();
        //JOptionPane.showMessageDialog(null,nome+"\n"+fichas+"\n"+escolha);
        
        if(pasta.exists()){
                auxdeck=pasta.list();
        }
        for(int i=0;i<53;i++){
            if(!auxdeck[i].toString().equals("Thumbs.db"))
            deck.add(auxdeck[i].toString());
        }
       
        baralho=new Baralho<String>((LinkedList)deck.clone());
        System.out.println(deck);
        
        String aux = "src\\BlackJack\\virado.JPG";
        embaralhar= new JButton(new ImageIcon(aux));
        embaralhar.setBackground(new Color(0,100,0));
        embaralhar.setBorderPainted(false);
        embaralhar.setEnabled(false);
        
        //menu do jogo
        
        JMenuBar menu=new JMenuBar();
        JMenu jogo=new JMenu("Ação");
        iniciar=new JMenuItem("Iniciar");
        
        sair=new JMenuItem("Sair");
        sair.addMouseListener(this);
        JMenu ajuda=new JMenu("Ajuda");
        
        jogo.add(iniciar);
        jogo.add(sair);
        menu.add(jogo);
        menu.add(ajuda);
        setJMenuBar(menu);
        
        
        cursor=getCursor();
                        
                
        //fim do trecho do menu
        if(escolha.equals("jogador")){
            nomejogador=new JLabel(nome);
            dealer=new JLabel("COMPUTADOR         ");
        }
        else{
            dealer=new JLabel(nome);
            nomejogador=new JLabel("COMPUTADOR         ");
        }
        nomejogador.setBounds(35,220,640,100);
        nomejogador.setHorizontalAlignment(JLabel.CENTER);
        nomejogador.setForeground(Color.LIGHT_GRAY);
        nomejogador.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));
        add(nomejogador);
        
       
        dealer.setBounds(35,80,640,100);
        dealer.setHorizontalAlignment(JLabel.CENTER);
        dealer.setForeground(Color.LIGHT_GRAY);
        dealer.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));
        add(dealer);
        
        jlaposta=new JLabel("U$ "+fichas+",00");
        jlaposta.setBounds(10, 0, 300, 70);
        jlaposta.setForeground(new Color(255, 200, 0));
        jlaposta.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));
        add(jlaposta);
        
        puxar=new JButton("PUXAR");
        puxar.setBackground(new Color(100, 0, 0));
        puxar.setForeground(Color.white);
        puxar.setBounds(550, 200, 75, 40);
        puxar.setEnabled(false);
        puxar.setBorder(BorderFactory.createLineBorder(new Color(255, 200, 0), 2));
        puxar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        puxar.addMouseListener(this);
        
        dobrar=new JButton("DOBRAR");
        dobrar.setBackground(new Color(255, 180, 0));
        dobrar.setForeground(Color.black);
        dobrar.setBounds(10,200,75,40);
        dobrar.setVisible(false);
        dobrar.setEnabled(false);
        dobrar.setBorder(BorderFactory.createLineBorder(new Color(255, 150, 0), 2));
        dobrar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        dobrar.addMouseListener(this);
        
        finalizar=new JButton("FINALIZAR");
        finalizar.setBackground(new Color(0, 0, 100));
        finalizar.setForeground(Color.white);
        finalizar.setBounds(550, 250, 75, 40);
        finalizar.setEnabled(false);
        finalizar.setBorder(BorderFactory.createLineBorder(new Color(255, 200, 0), 2));
        finalizar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        finalizar.addMouseListener(this);
        
        aposta5=new JButton(new ImageIcon("src\\BlackJack\\ficha5.png"));
        aposta5.setBackground(new Color(0,100,0));
        aposta5.setBounds(10,100,55,55);
        aposta5.setBorderPainted(false);
        aposta5.setVisible(false);
        aposta5.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        aposta5.addMouseListener(this);
        
        aposta10=new JButton(new ImageIcon("src\\BlackJack\\ficha10.png"));
        aposta10.setBackground(new Color(0,100,0));
        aposta10.setBounds(10,160,55,55);
        aposta10.setBorderPainted(false);
        aposta10.setVisible(false);
        aposta10.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        aposta10.addMouseListener(this);
        
        aposta50=new JButton(new ImageIcon("src\\BlackJack\\ficha50.png"));
        aposta50.setBackground(new Color(0,100,0));
        aposta50.setBounds(10,220,55,55);
        aposta50.setBorderPainted(false);
        aposta50.setVisible(false);
        aposta50.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        aposta50.addMouseListener(this);
        
        aposta100=new JButton(new ImageIcon("src\\BlackJack\\ficha100.png"));
        aposta100.setBackground(new Color(0,100,0));
        aposta100.setBounds(10,280,55,55);
        aposta100.setBorderPainted(false);
        aposta100.setVisible(false);
        aposta100.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        aposta100.addMouseListener(this);
        
        apostatotal=new JLabel("Aposta Total: U$ "+aposta);
        apostatotal.setBounds(10,350,200,55);
        apostatotal.setForeground(new Color(255, 200, 0));
        apostatotal.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
        add(apostatotal);
        
        pontosmao=new JLabel();
        
        
        
        pontosmao.setBounds(200,300,50,40);
        pontosmao.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));
        pontosmao.setForeground(Color.white);
        add(pontosmao);
        
        pontosmesa=new JLabel();
        
        pontosmesa.setBounds(200, 15, 50, 40);
        pontosmesa.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));
        pontosmesa.setForeground(Color.white);
        add(pontosmesa);
        pontosmesa.setVisible(false);
       
        
        iniciar.addMouseListener(this);
        embaralhar.addMouseListener(this);
        setLayout(null);
        p.add(embaralhar);
        p.setBounds(540,0, 90,115);
        p.setBackground(new Color(0,100,0));
        add(finalizar);
        add(aposta5);
        add(aposta10);
        add(aposta50);
        add(aposta100);
        add(dobrar);
        add(puxar);
        add(p);
        //pack();
        this.getContentPane().setBackground(new Color(0,100,0));
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gc=getCont().getGraphics();
        
        
    }
    
    public void movetoPoint(int ptX, int ptY){
            
                try {
                  while(xMao!=ptX || yMao!=ptY){
                     if(xMao<ptX-2){
                            java.lang.Thread.sleep(sl);
                            xMao+=3;
                            getCont().remove(jlmao);
                            refazerMao();                
                    }
                     if(xMao>=ptX-2 && xMao<ptX){
                         java.lang.Thread.sleep(sl);
                            xMao+=1;
                            getCont().remove(jlmao);
                            refazerMao();
                     }
                     if(xMao-2>ptX){
                         java.lang.Thread.sleep(sl);
                         xMao-=3;
                         getCont().remove(jlmao);
                         refazerMao();
                     }
                     if(xMao-2<=ptX && xMao>ptX){
                         java.lang.Thread.sleep(sl);
                            xMao-=1;
                            getCont().remove(jlmao);
                            refazerMao();
                     }
                     if(yMao<ptY-2){
                      
                            java.lang.Thread.sleep(sl);
                            yMao+=3;
                            getCont().remove(jlmao);
                            refazerMao();
                            
                    }
                     if(yMao>=ptY-2 && yMao<ptY){
                         java.lang.Thread.sleep(sl);
                            yMao+=1;
                            getCont().remove(jlmao);
                            refazerMao();
                     }
                     if(yMao-2>ptY){
                         java.lang.Thread.sleep(sl);
                         yMao-=3;
                         getCont().remove(jlmao);
                         refazerMao();
                     }
                     if(yMao-2<=ptY && yMao>ptY){
                         java.lang.Thread.sleep(sl);
                            yMao-=1;
                            getCont().remove(jlmao);
                            refazerMao();
                     }
                              
                            
                  }
                }catch (InterruptedException ex) {
                            Logger.getLogger("global").log(Level.SEVERE, null, ex);
                }     
          }
    public void refazerMao(){
        boundsMao = new java.awt.Rectangle(xMao, yMao, 17, 22);
                            jlmao.setBounds(boundsMao);
                            getCont().add(jlmao,0);
                            getCont().paint(getCont().getGraphics());
                            getCont().validate();
    }
    public Container getCont(){
        return this.getContentPane();
    }
    public int getPoints(Vector<Carta> vc){
        int cont=0;
        Enumeration <Carta>e=vc.elements();
        while(e.hasMoreElements()){
           cont+=e.nextElement().getPoint();     
        }
        return cont;
    }

    private void analise(Vector<Carta> vc1, Carta cta) {
        if(getPoints(vc1)<8 || (getPoints(vc1)<=11 && vc1.size()>2)){
            puxar.setEnabled(true);
            movetoPoint(550,200);
            esperar=true;
            t.start();
            mevent.setSource(puxar);
            mousePressed(mevent);
        }else if(getPoints(vc1)==21 && !temAs(vc1) && (cta.getPoint()!=11 || (resposta==JOptionPane.NO_OPTION || (resposta==JOptionPane.YES_OPTION && maomesa.get(1).getPoint()!=10)))){
            deDobrar=0;           
            mevent.setSource(finalizar);
            mousePressed(mevent);
        }else if(getPoints(vc1)==8 && vc1.size()==2 && cta.getPoint()==6 && (vc1.get(0).getPoint()==6 || vc1.get(1).getPoint()==6)){
            puxar.setEnabled(true);
            movetoPoint(550,200);
            esperar=true;
            t.start();
            mevent.setSource(puxar);
            mousePressed(mevent);
        }else if(getPoints(vc1)==8 && ((cta.getPoint()!=5 && cta.getPoint()!=6) || vc1.size()>2)){
            puxar.setEnabled(true);
            movetoPoint(550,200);
            esperar=true;
            t.start();
            mevent.setSource(puxar);
            mousePressed(mevent);
        }else if(getPoints(vc1)==8 && vc1.get(0).getPoint()!= vc1.get(1).getPoint() && vc1.size()==2 && (cta.getPoint()==5 || (cta.getPoint()==6 && (vc1.size()!=2 || (vc1.get(0).getPoint()==6 || vc1.get(1).getPoint()==6))))){
            dobrar.setEnabled(true);
            deDobrar=1;
            movetoPoint(dobrar.getX()+60,dobrar.getY()+30);
            mevent.setSource(dobrar);
            mousePressed(mevent);
        }else if(getPoints(vc1)==9 && cta.getPoint() < 7 && vc1.size()==2){
            dobrar.setEnabled(true);
            deDobrar=1;
            movetoPoint(dobrar.getX()+60,dobrar.getY()+30);
            mevent.setSource(dobrar);
            mousePressed(mevent);
        }else if(getPoints(vc1)==9 && (cta.getPoint() >= 7 || vc1.size()>2)){
            puxar.setEnabled(true);
            movetoPoint(550,200);
                       esperar=true;
                       t.start();
            
            mevent.setSource(puxar);
            mousePressed(mevent);
        }else if(getPoints(vc1)==10 && (cta.getPoint() >9 || vc1.size()>2)){
            puxar.setEnabled(true);
            movetoPoint(550,200);
                       esperar=true;
                       t.start();
            mevent.setSource(puxar);
            mousePressed(mevent);       
        }else if(getPoints(vc1)==10 && cta.getPoint() < 10 && vc1.size()==2){
            dobrar.setEnabled(true);
            deDobrar=1;
            movetoPoint(dobrar.getX()+60,dobrar.getY()+30);
            mevent.setSource(dobrar);
            mousePressed(mevent);
        }else if(getPoints(vc1)==11 && vc1.size()==2){
            deDobrar=1;
            dobrar.setEnabled(true);
            movetoPoint(dobrar.getX()+60,dobrar.getY()+30);
            mevent.setSource(dobrar);
            mousePressed(mevent);
        }else if(getPoints(vc1)==12 && (vc1.size()==4 || vc1.size()==5 || vc1.size()==6) && cta.getPoint()==3){ 
            deDobrar=0;
            mevent.setSource(finalizar);
            mousePressed(mevent);
        }else if(getPoints(vc1)==12 && vc1.size()==2 && cta.getPoint()==3 && ((vc1.get(0).getPoint()==7 || vc1.get(1).getPoint()==7) || (vc1.get(0).getPoint()==8 || vc1.get(1).getPoint()==8))){ 
            deDobrar=0;
            mevent.setSource(finalizar);
            mousePressed(mevent);
        }else if(getPoints(vc1)==12 && vc1.size()==2 && cta.getPoint()==4 && (vc1.get(0).getPoint()==10 || vc1.get(1).getPoint()==10)){ 
            puxar.setEnabled(true);
            movetoPoint(550,200);
                       esperar=true;
                       t.start();
            mevent.setSource(puxar);
            mousePressed(mevent); 
        }else if(getPoints(vc1)==12 && (cta.getPoint() > 6 || cta.getPoint() <3)){
            puxar.setEnabled(true);
            movetoPoint(550,200);
            esperar=true;
            t.start();
            mevent.setSource(puxar);
            mousePressed(mevent);
        }else if(getPoints(vc1)==12 && cta.getPoint() < 7 && cta.getPoint() >= 3){
            deDobrar=0;
            mevent.setSource(finalizar);
            mousePressed(mevent);
        }else if(getPoints(vc1)==13 && !temAs(vc1) && cta.getPoint() > 6){
            puxar.setEnabled(true);
            movetoPoint(550,200);
            esperar=true;
            t.start();
            mevent.setSource(puxar);
            mousePressed(mevent);
        }else if(getPoints(vc1)==13 && !temAs(vc1)  && cta.getPoint() < 7){
            deDobrar=0;
            mevent.setSource(finalizar);
            mousePressed(mevent);
        }else if(getPoints(vc1)==14 && !temAs(vc1) && (vc1.size()!=2 || vc1.get(0).getPoint()!= vc1.get(1).getPoint()) && cta.getPoint() > 6){
            puxar.setEnabled(true);
            movetoPoint(550,200);
                       esperar=true;
                       t.start();
            mevent.setSource(puxar);
            mousePressed(mevent);
        }else if(getPoints(vc1)==14 && !temAs(vc1) && (vc1.size()!=2 || vc1.get(0).getPoint()!= vc1.get(1).getPoint()) && cta.getPoint() < 7){
            
            deDobrar=0;
            mevent.setSource(finalizar);
            mousePressed(mevent);
        }else if(getPoints(vc1)==15 && !temAs(vc1)  && ((vc1.get(0).getPoint()==6 && vc1.get(1).getPoint()==5) || (vc1.get(0).getPoint()==6 && vc1.get(1).getPoint()==4) || (vc1.get(0).getPoint()==5 && vc1.get(1).getPoint()==4) || (vc1.get(0).getPoint()==4 && vc1.get(1).getPoint()==6) || (vc1.get(0).getPoint()==5 && vc1.get(1).getPoint()==6) || (vc1.get(0).getPoint()==4 && vc1.get(1).getPoint()==5)) && cta.getPoint()==10){
            deDobrar=0;
            mevent.setSource(finalizar);
            mousePressed(mevent);
        }else if(getPoints(vc1)==15 && !temAs(vc1)  && ((vc1.get(0).getPoint()==6 && vc1.get(1).getPoint()==6) || (vc1.get(0).getPoint()==6 && vc1.get(1).getPoint()==3) || (vc1.get(0).getPoint()==3 && vc1.get(1).getPoint()==6))&& cta.getPoint()==10){
            deDobrar=0;
            mevent.setSource(finalizar);
            mousePressed(mevent);
        }else if(getPoints(vc1)==15 && !temAs(vc1)  && vc1.get(0).getPoint()==5 && vc1.get(1).getPoint()==5 && cta.getPoint()==10){
            deDobrar=0;
            mevent.setSource(finalizar);
            mousePressed(mevent);
        }else if(getPoints(vc1)==15 && !temAs(vc1)  && cta.getPoint() > 6){
            puxar.setEnabled(true);
            movetoPoint(550,200);
                       esperar=true;
                       t.start();
            mevent.setSource(puxar);
            mousePressed(mevent);
        }else if(getPoints(vc1)==15 && !temAs(vc1)  && cta.getPoint() < 7){
            deDobrar=0;
            mevent.setSource(finalizar);
            mousePressed(mevent);
        }else if(getPoints(vc1)==16 && (vc1.size()>=2/*vc1.size()==4 || vc1.size()==5 || vc1.size()==6*/) && cta.getPoint()==10){ 
            deDobrar=0;
            mevent.setSource(finalizar);
            mousePressed(mevent);
        }else if(getPoints(vc1)==16 && (vc1.size()==5 || vc1.size()==6) && cta.getPoint()==9){ 
            deDobrar=0;
            mevent.setSource(finalizar);
            mousePressed(mevent);
        }else if(getPoints(vc1)==16 && vc1.size()==5 && cta.getPoint()==7){ 
            deDobrar=0;
            mevent.setSource(finalizar);
            mousePressed(mevent);
        }else if(getPoints(vc1)==16 && !temAs(vc1)  && ((vc1.get(0).getPoint()==6 && vc1.get(1).getPoint()==5) || (vc1.get(0).getPoint()==5 && vc1.get(1).getPoint()==5) || (vc1.get(0).getPoint()==5 && vc1.get(1).getPoint()==6)) && cta.getPoint()==9){
            deDobrar=0;
            mevent.setSource(finalizar);
            mousePressed(mevent);
        }else if(getPoints(vc1)==16 && !temAs(vc1)  && ((vc1.get(0).getPoint()==8 && vc1.get(1).getPoint()==4) || (vc1.get(0).getPoint()==4 && vc1.get(1).getPoint()==4) || (vc1.get(0).getPoint()==4 && vc1.get(1).getPoint()==8)) && cta.getPoint()==9){
            deDobrar=0;
            mevent.setSource(finalizar);
            mousePressed(mevent);
        }else if(getPoints(vc1)==16 && !temAs(vc1)  && ((vc1.get(0).getPoint()==8 && vc1.get(1).getPoint()==5) || (vc1.get(0).getPoint()==5 && vc1.get(1).getPoint()==3) || (vc1.get(0).getPoint()==8 && vc1.get(1).getPoint()==3) || (vc1.get(0).getPoint()==5 && vc1.get(1).getPoint()==8) || (vc1.get(0).getPoint()==3 && vc1.get(1).getPoint()==5) || (vc1.get(0).getPoint()==3 && vc1.get(1).getPoint()==8)) && cta.getPoint()==9){
            deDobrar=0;
            mevent.setSource(finalizar);
            mousePressed(mevent);
        }else if(getPoints(vc1)==16 && !temAs(vc1)  && ((vc1.get(0).getPoint()==6 && vc1.get(1).getPoint()==6) || (vc1.get(0).getPoint()==6 && vc1.get(1).getPoint()==4) || (vc1.get(0).getPoint()==4 && vc1.get(1).getPoint()==6))&& cta.getPoint()==10){
            puxar.setEnabled(true);
            movetoPoint(550,200);
                       esperar=true;
                       t.start();
            mevent.setSource(puxar);
            mousePressed(mevent);
        }else if(getPoints(vc1)==16 && !temAs(vc1)  && ((vc1.get(0).getPoint()==7 && vc1.get(1).getPoint()==6) || (vc1.get(0).getPoint()==6 && vc1.get(1).getPoint()==7) || (vc1.get(0).getPoint()==7 && vc1.get(1).getPoint()==3) || (vc1.get(0).getPoint()==3 && vc1.get(1).getPoint()==7) || (vc1.get(0).getPoint()==6 && vc1.get(1).getPoint()==3) || (vc1.get(0).getPoint()==3 && vc1.get(1).getPoint()==6)) && cta.getPoint()==10){
            puxar.setEnabled(true);
            movetoPoint(550,200);
                       esperar=true;
                       t.start();
            mevent.setSource(puxar);
            mousePressed(mevent);
        }else if(getPoints(vc1)==16 && !temAs(vc1)  && ((vc1.get(0).getPoint()==8 && vc1.get(1).getPoint()==6) || (vc1.get(0).getPoint()==6 && vc1.get(1).getPoint()==8) || (vc1.get(0).getPoint()==8 && vc1.get(1).getPoint()==2) || (vc1.get(0).getPoint()==2 && vc1.get(1).getPoint()==8) || (vc1.get(0).getPoint()==6 && vc1.get(1).getPoint()==2) || (vc1.get(0).getPoint()==2 && vc1.get(1).getPoint()==6))&& cta.getPoint()==10){
            puxar.setEnabled(true);
            movetoPoint(550,200);
                       esperar=true;
                       t.start();
            mevent.setSource(puxar);
            mousePressed(mevent);
        }else if(getPoints(vc1)==16 && temAs(vc1)  && vc1.size()==3 && (vc1.get(0).getPoint()==9 || vc1.get(1).getPoint()==9 || vc1.get(2).getPoint()==9) && cta.getPoint()==10){
            puxar.setEnabled(true);
            movetoPoint(550,200);
                       esperar=true;
                       t.start();
            mevent.setSource(puxar);
            mousePressed(mevent);
        }else if(getPoints(vc1)==16 && !temAs(vc1) &&  cta.getPoint() > 6 && cta.getPoint()!=10){
            puxar.setEnabled(true);
            movetoPoint(550,200);
                       esperar=true;
                       t.start();
            mevent.setSource(puxar);
            mousePressed(mevent);
        }else if(getPoints(vc1)==16 && !temAs(vc1) &&  (cta.getPoint() < 7 || cta.getPoint()!=10)){
            deDobrar=0;
            mevent.setSource(finalizar);
            mousePressed(mevent);
        }else if(getPoints(vc1)==17 && !temAs(vc1)){
            deDobrar=0;
            mevent.setSource(finalizar);
            mousePressed(mevent);
        }else if(getPoints(vc1)==13 && temAs(vc1) && ((cta.getPoint() > 6 || cta.getPoint() < 4)|| vc1.size()>2)){
            puxar.setEnabled(true);
            movetoPoint(550,200);
                       esperar=true;
                       t.start();
            mevent.setSource(puxar);
            mousePressed(mevent);
        }else if(temAs(vc1) && vc1.size()==2 && getPoints(vc1)==13 && cta.getPoint() < 7 && cta.getPoint() > 3 ){
            dobrar.setEnabled(true);
            deDobrar=1;
            movetoPoint(dobrar.getX()+60,dobrar.getY()+30);
            mevent.setSource(dobrar);
            mousePressed(mevent);
        }else if(temAs(vc1) && getPoints(vc1)==14 && ((cta.getPoint() > 6 || cta.getPoint() < 4)|| vc1.size()>2)){
            puxar.setEnabled(true);
            movetoPoint(550,200);
                       esperar=true;
                       t.start();
            mevent.setSource(puxar);
            mousePressed(mevent);
        }else if(temAs(vc1) && vc1.size()==2 && getPoints(vc1)==14 && cta.getPoint() < 7 && cta.getPoint() > 3 ){
            dobrar.setEnabled(true);
            deDobrar=1;
            movetoPoint(dobrar.getX()+60,dobrar.getY()+30);
            mevent.setSource(dobrar);
            mousePressed(mevent);
        }else if(temAs(vc1) && getPoints(vc1)==15 && ((cta.getPoint() > 6 || cta.getPoint() < 4)|| vc1.size()>2)){
            puxar.setEnabled(true);
            movetoPoint(550,200);
                esperar=true;
                       t.start();
            mevent.setSource(puxar);
            mousePressed(mevent);
        }else if(temAs(vc1) && vc1.size()==2 && getPoints(vc1)==15 && cta.getPoint() < 7 && cta.getPoint() > 3 ){
            dobrar.setEnabled(true);
            deDobrar=1;
            movetoPoint(dobrar.getX()+60,dobrar.getY()+30);
            mevent.setSource(dobrar);
            mousePressed(mevent);
        }else if(temAs(vc1) && getPoints(vc1)==16 && ((cta.getPoint() > 6 || cta.getPoint() < 4)|| vc1.size()>2)){
            puxar.setEnabled(true);
            movetoPoint(550,200);
                       esperar=true;
                       t.start();
            mevent.setSource(puxar);
            mousePressed(mevent);
        }else if(temAs(vc1) && vc1.size()==2 && getPoints(vc1)==16 && cta.getPoint() < 7 && cta.getPoint() > 3 ){
            dobrar.setEnabled(true);
            deDobrar=1;
            movetoPoint(dobrar.getX()+60,dobrar.getY()+30);
            mevent.setSource(dobrar);
            mousePressed(mevent);
        }else if(getPoints(vc1)==17 && temAs(vc1) && (cta.getPoint() > 6)|| vc1.size()>2){
            puxar.setEnabled(true);
            movetoPoint(550,200);
                       esperar=true;
                       t.start();
            mevent.setSource(puxar);
            mousePressed(mevent);
        }else if(temAs(vc1) && vc1.size()==2 && getPoints(vc1)==17 && cta.getPoint() < 7 ){
            dobrar.setEnabled(true);
            deDobrar=1;
           movetoPoint(dobrar.getX()+60,dobrar.getY()+30);
            mevent.setSource(dobrar);
            mousePressed(mevent);
        }else if(temAs(vc1) && getPoints(vc1)==18 && cta.getPoint() > 8){
            puxar.setEnabled(true);
            movetoPoint(550,200);
                       esperar=true;
                       t.start();
            mevent.setSource(puxar);
            mousePressed(mevent);
        }else if( getPoints(vc1)==18 && temAs(vc1) && ((cta.getPoint() > 6 || cta.getPoint() == 2) && cta.getPoint() < 9)|| vc1.size()>2){
            deDobrar=0;
            mevent.setSource(finalizar);
            mousePressed(mevent);
        }else if(temAs(vc1) && vc1.size()==2 && getPoints(vc1)==18 && cta.getPoint() > 2 && cta.getPoint() < 7){
            dobrar.setEnabled(true);
            deDobrar=1;
            movetoPoint(dobrar.getX()+60,dobrar.getY()+30);
            mevent.setSource(dobrar);
            mousePressed(mevent);
        }else  if(temAs(vc1) && getPoints(vc1)==19 && (cta.getPoint() != 6 || vc1.size()>2)){
            deDobrar=0;
            mevent.setSource(finalizar);
            mousePressed(mevent);
        }else  if(temAs(vc1) && getPoints(vc1)==20){
            deDobrar=0;
            mevent.setSource(finalizar);
            mousePressed(mevent);    
        }else  if(!temAs(vc1) && getPoints(vc1)==21){
            deDobrar=0;
            mevent.setSource(finalizar);
            mousePressed(mevent);
        }else if(temAs(vc1) && vc1.size()==2 && getPoints(vc1)==19 && cta.getPoint() == 6){
            deDobrar=1;
            dobrar.setEnabled(true);
            movetoPoint(dobrar.getX()+60,dobrar.getY()+30);
            mevent.setSource(dobrar);
            mousePressed(mevent);
        }else if(vc1.get(0).getPoint()==6 && vc1.get(1).getPoint()==6 && cta.getPoint() > 7){
            puxar.setEnabled(true);
            movetoPoint(550,200);
                       esperar=true;
                       t.start();
            mevent.setSource(puxar);
            mousePressed(mevent);
        }else if(vc1.get(0).getPoint()==7 && vc1.get(1).getPoint()==7 && (cta.getPoint() < 7 || cta.getPoint() == 10)){
            //mevent.setSource("split");
            deDobrar=0;
            mevent.setSource(finalizar);
            mousePressed(mevent);
       }else if(vc1.get(0).getPoint()==7 && vc1.get(1).getPoint()==7 && cta.getPoint() > 7 && cta.getPoint()!=10){
           puxar.setEnabled(true);
           movetoPoint(550,200);
                       esperar=true;
                       t.start();
           mevent.setSource(puxar);
            mousePressed(mevent);  
            
            
        }else if((!temAs(vc1) && getPoints(vc1)==17) || getPoints(vc1)>17){
            deDobrar=0;
            mevent.setSource(finalizar);
            mousePressed(mevent);
        }
        
        esperar=true;
        t.start();
        //repaint();
        
        //mevent.setSource(finalizar);
        //mousePressed(mevent);
    }
    private void comecarjogo() {
        try {
            new BlackJack();
            this.setVisible(false);
            this.finalize();
            System.gc();     
        } catch (Throwable ex) {
            Logger.getLogger(BlackJack.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void abilitarApostas(boolean x){
        if(podeapostar==1){
        aposta5.setVisible(x);
        aposta10.setVisible(x);
        aposta50.setVisible(x);
        aposta100.setVisible(x);
    }}
    public boolean temAs(Vector<Carta> vc){
        Enumeration <Carta>e=vc.elements();
        while(e.hasMoreElements()){
           if(e.nextElement().getPoint()==11)return true;
        }
        return false;
    }
    public Vector<Carta> setAs11to1(Vector<Carta> vc){
        Enumeration <Carta>e=vc.elements();
        int cont=0; 
        while(e.hasMoreElements()){
            Carta x=e.nextElement();
            if(x.getPoint()==11){
               System.out.println(vc.get(vc.indexOf(x)).getPoint()+"");
               vc.elementAt(cont).setPoints(1);
               break;
           }
        cont++;
        }
        //teste transformação 111 to 1
        Enumeration <Carta>z=vc.elements();
        while(z.hasMoreElements()){
            System.out.println(z.nextElement().getPoint());
           
        }
        return vc;
        //fim teste
    }
    public void bancojogador(){
        Image cursorImage = Toolkit.getDefaultToolkit().getImage("");   
        Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImage, new Point( 0, 0), "" );   
        getCont().setCursor( blankCursor );
        xMao=iniciar.getX();
        yMao=iniciar.getY();
        apostacomputador();
        sl=1;
        movetoPoint(525, 50);
        movetoPoint(535, 50);
        esperar=true;
        t.start();
        mevent.setSource(embaralhar);
        movetoPoint(525, 50);
        mousePressed(mevent);
        
    }
    public void apostacomputador(){
        int cont=0;
        int num=0;
        int e=4;
        if(fichas<100)e=3;
        if(fichas<50)e=2;
        if(fichas<10)e=1;
        Random n=new Random();
        while(aposta<100 && cont<3){
            num=n.nextInt(e);
            if(num==0 && fichas>=5){
                movetoPoint(65, 126);
                sl=15;
                movetoPoint(50, 126);
                mevent.setSource(aposta5);
                mousePressed(mevent);
                movetoPoint(65, 126);
                sl=1;
            }else if(num==1 && fichas>=10){
                movetoPoint(65, 186);
                sl=15;
                movetoPoint(50, 186);
                mevent.setSource(aposta10);
                mousePressed(mevent);
                movetoPoint(65, 186);
                sl=1;
            }else if(num==2 && fichas>=50){
                movetoPoint(65, 246);
                sl=15;
                movetoPoint(50, 246);
                mevent.setSource(aposta50);
                mousePressed(mevent);
                movetoPoint(65, 246);
                sl=1;
            }else if(num==3 && fichas>=100){
                movetoPoint(65, 306);
                sl=15;
                movetoPoint(50, 306);
                mevent.setSource(aposta100);
                mousePressed(mevent);
                movetoPoint(65, 306);
                sl=1;
            }
         cont++;
        }
    }
    public void getCondicao(MouseEvent me){
        try{                  
        if(Integer.parseInt(pontosmao.getText())>21){
            if(Integer.parseInt(pontosmesa.getText())==21)JOptionPane.showMessageDialog(getCont(),"Banco fez BlackJack!!!");
            JOptionPane.showMessageDialog(getCont(),"Fim da Partida, o Banco recolhe as apostas!!!");
        }else if(Integer.parseInt(pontosmao.getText())==21){
            if(maojogador.size()==2){
                if(Integer.parseInt(pontosmesa.getText())==21){JOptionPane.showMessageDialog(getCont(), "O Banco tmabém fez BlackJack, aposta repartida!!");fichas += aposta; }
                else {JOptionPane.showMessageDialog(getCont(), "Você ganhou com o BlackJack");fichas += (2*aposta)+(aposta*0.5);}
            }else{
                if(Integer.parseInt(pontosmesa.getText())==21)
                    JOptionPane.showMessageDialog(getCont(), "O Banco tem um BlackJack, você perdeu!!");
                else{
                    while(Integer.parseInt(pontosmesa.getText())<17 || (Integer.parseInt(pontosmesa.getText())==17 && temAs(maomesa))){
                       
                       movetoPoint(550,200);
                       esperar=true;
                       t.start();
                       puxar.setEnabled(true);
                       me.setSource(puxar);
                       mousePressed(me);
                       puxar.setEnabled(false);
                       movetoPoint(524,196);
                       
               }
                    if(Integer.parseInt(pontosmesa.getText())>21){JOptionPane.showMessageDialog(getCont(),"O Banco Estourou!!! Você ganhou"); fichas += 2*aposta;}
                    else if(Integer.parseInt(pontosmesa.getText())==21){ JOptionPane.showMessageDialog(getCont(),"O Banco também fez 21!!! Empatou!! O Banco recolhe a aposta!!"); fichas += aposta;}
                    else{JOptionPane.showMessageDialog(getCont(),"O jogador venceu!!!!!");}
                }
                
            }
            
        }else if (Integer.parseInt(pontosmao.getText())<21){
           while((Integer.parseInt(pontosmesa.getText())<17 || (Integer.parseInt(pontosmesa.getText())==17 && temAs(maomesa))) && Integer.parseInt(pontosmesa.getText())<22){
                       
                       
                       movetoPoint(550,200);
                       esperar=true;
                       t.start();
                       puxar.setEnabled(true);
                       me.setSource(puxar);
                       mousePressed(me);
                       puxar.setEnabled(false);
                       movetoPoint(524,196);
                       
           }
           if(Integer.parseInt(pontosmesa.getText())>21){JOptionPane.showMessageDialog(getCont(),"O Banco Estourou!!! Você ganhou"); fichas += 2*aposta;}
           else if(Integer.parseInt(pontosmesa.getText())<21 && Integer.parseInt(pontosmesa.getText())>=Integer.parseInt(pontosmao.getText())){JOptionPane.showMessageDialog(getCont(),"O Banco Venceu!!! O Banco recolhe a aposta!!");}
           else if(Integer.parseInt(pontosmesa.getText())<21 && Integer.parseInt(pontosmesa.getText())<Integer.parseInt(pontosmao.getText())){JOptionPane.showMessageDialog(getCont(),"O Banco Perdeu!!! Banco paga Jogador!!");fichas += 2*aposta;}
           else if(Integer.parseInt(pontosmesa.getText())==21){JOptionPane.showMessageDialog(getCont(),"O Banco fez 21!!! Banco recolhe a aposta!!");}
        }}catch(Exception e){}
        me.setSource(iniciar);
        validate();
        mousePressed(me);
    }
    //public static void main (String []args){
    //    new BlackJack();
    //}

    public void mouseClicked(MouseEvent e) {}

    public void mousePressed(MouseEvent me) {
    if(me.getSource()==embaralhar && embaralhar.isEnabled()){
                        baralho.reEmbaralhar((LinkedList)deck.clone());
                        abilitarApostas(false);
                        c=getCont();
                        c.setBounds(0,0,640,439);
                        maojogador.removeAllElements();
                        maomesa.removeAllElements();
                        for(int i=0;i<2;i++){
                            p1[i]=new JPanel();
                            Carta x=new Carta(baralho.get(0));
                            x.setPosition(i*20);
                            maojogador.addElement(x);
                            p1[i].setBounds(250+(i*20),300,74,105);
                            p1[i].add(x.getIcon());
                            p1[i].setBackground(new Color(0,100,0));
                            baralho.remove();
                            c.add(p1[i],1-i);
                            p2[i]=new JPanel();
                            Carta y=new Carta(baralho.get(0));
                            y.setPosition(i*20);
                            maomesa.addElement(y);
                            p2[i].setBounds(250+(i*20),0,74,105);
                            if(i==1){
                                p2[i].setBounds(251+(i*20),0,70,105);
                                p2[i].add(new JLabel(new ImageIcon("src\\BlackJack\\virado.JPG")));
                            }
                            else p2[i].add(y.getIcon());
                            p2[i].setBackground(new Color(0,100,0));
                            baralho.remove();
                            c.add(p2[i],3-i);
                        }
                    p2[1].addMouseListener(this);
                    
                    
                    pontosmao.setText(""+getPoints(maojogador));
                    
                     
                    if(temAs(maojogador) && getPoints(maojogador)>21){
                            maojogador=setAs11to1(maojogador);
                            pontosmao.setText(""+(Integer.parseInt(pontosmao.getText())-10));
                    }
                    if(maomesa.get(0).getPoint()==11 && fichas >= aposta*(1/2) && getPoints(maojogador)!=21){
                        if(escolha.equals("banco")){
                            JOptionPane.showMessageDialog(getCont(), "Um seguro foi oferecido ou jogador!!!");
                            getCont().validate();
                            resposta=computarCasos(maojogador);
                            if (resposta==JOptionPane.YES_OPTION){
                                JOptionPane.showMessageDialog(getCont(), "aceitou o seguro!!!");
                            }
                            else{
                                JOptionPane.showMessageDialog(getCont(), "recusou o seguro!!!");
                            }
                        }else{
                            resposta=JOptionPane.showConfirmDialog(getCont(), "O Banco mostra um Ás!! Você deseja um seguro!!!","SEGURO",JOptionPane.YES_NO_OPTION);
                        }if(resposta==JOptionPane.YES_OPTION){
                            fichas -= aposta*0.5;
                            validate();
                            jlaposta.setText("U$ "+fichas+",00");                                                     
                            if((maomesa.get(1).getPoint()==10)){
                                
                                
                                //banco paga 2 para 1, ou seja, o dobro do que você apostou...e sua aposa é devolvida
                                if(escolha.equals("jogador")){
                                xMao=320;
                                yMao=220;
                       
                                movetoPoint(330, 100);
                                
                                fichas += 3*aposta;
                                
                                p2[1].setBounds(270,0,72,105);
                                p2[1].add(maomesa.get(1).getIcon(),0);
                                getCont().add(p2[1],0);
                                
                                validate();
                                
                                }
                                else{
                                getCont().remove(jlmao);    
                                getCont().setCursor(cursor);
                                puxar.setEnabled(false);
                                dobrar.setEnabled(false);
                                finalizar.setEnabled(false);
                                JOptionPane.showMessageDialog(getCont(), "A segunada carta vale 10, vire a segunda carta");
                                }
                                
                                pontosmesa.setText(""+getPoints(maomesa));
                                esperar=true;
                                t.start();
                                
                                
                                
                                
                                if(escolha.equals("jogador")){
                                JOptionPane.showMessageDialog(getCont(), "Banco tem BlackJack....o seguro cobre 2 para 1......Jogador leva: "+3*aposta);
                                getCont().repaint();
                                me.setSource(iniciar);
                                mousePressed(me);}
                                                               
                            }else JOptionPane.showMessageDialog(getCont(), "A segunda carta do banco não vale 10");
                        }repaint();
                        if(resposta==JOptionPane.NO_OPTION && escolha.equals("banco")){
                                    dobrar.setVisible(true);
                                    analise(maojogador,maomesa.get(0));
                               }
                        repaint(); 
                        
                    }else if(getPoints(maojogador)==21){
                        JOptionPane.showMessageDialog(getCont(), "BlackJack!!! O banco paga 3 para 2!!!");
                        fichas += (2*aposta)+(aposta*0.5);
                        me.setSource(finalizar);
                        mousePressed(me);
                    }else if(resposta==JOptionPane.NO_OPTION && escolha.equals("banco")){
                                    dobrar.setVisible(true);
                                    analise(maojogador,maomesa.get(0));
                               }              
                    
                    
                    repaint(); 
                    
                    embaralhar.setEnabled(false);
                    dobrar.setVisible(true);
                    if(fichas>=aposta)dobrar.setEnabled(true);
                    puxar.setEnabled(true);
                    finalizar.setEnabled(true);
                    
                    
              }
                    if(me.getSource()==iniciar && iniciar.isEnabled()){
                        if(fichas < 5){
                                JOptionPane.showMessageDialog(getCont(), "FIM DE JOGO");
                                this.setVisible(false);
                                this.setEnabled(false);
                                Inicial.getInstancia().setVisible(true);
                        }
                        getCont().setCursor(cursor);
                        podeapostar=1;
                        vezdamesa=false;
                        pontosmao.setText("");
                        pontosmesa.setText("");
                        dobrar.setEnabled(false);
                        dobrar.setVisible(false);
                        abilitarApostas(true);
                        puxar.setEnabled(false);
                        finalizar.setEnabled(false);
                        embaralhar.setEnabled(false);
                        aposta=0;
                        apostatotal.setText("Aposta Total: "+aposta);
                            for(int i=0;i<=1;i++){
                                if(getCont().isAncestorOf(p1[i]))getCont().remove(p1[i]);
                                if(getCont().isAncestorOf(p2[i]))getCont().remove(p2[i]);
                            }
                            for(int i=0;i<=4;i++){
                            if(getCont().isAncestorOf(panel[i]))getCont().remove(panel[i]);
                            if(getCont().isAncestorOf(panel2[i]))getCont().remove(panel2[i]);
                            cont2=0;
                            cont=0;
                            }
                            getCont().repaint();
                        if(escolha.equals("banco")){
                            mevent=me;
                            iniciar.setEnabled(false);
                            bancojogador();
                        }
                    }
                    if(me.getSource()==aposta5 && me.getButton()==me.BUTTON1 && aposta5.isEnabled() && fichas >= 5){
                        aposta+=5;
                        if(aposta>=5) embaralhar.setEnabled(true);
                        apostatotal.setText("Aposta Total: "+aposta);
                        if(fichas>=5){fichas-=5;}
                        
                    }else if(me.getSource()==aposta5 && me.getButton()==me.BUTTON3 && aposta5.isEnabled() && aposta >= 5){
                        aposta-=5;
                        fichas+=5; 
                        if(aposta<5) embaralhar.setEnabled(false);
                        apostatotal.setText("Aposta Total: "+aposta);
                        jlaposta.setText("U$ "+fichas+",00");                        
                    }                  
    
                    if(me.getSource()==aposta10 && me.getButton()==me.BUTTON1 && aposta10.isEnabled() && fichas >= 10){
                        aposta+=10;
                        if(aposta>=5) embaralhar.setEnabled(true);
                        apostatotal.setText("Aposta Total: "+aposta);
                        if(fichas>=10){fichas-=10;}
                    }else if(me.getSource()==aposta10 && me.getButton()==me.BUTTON3 && aposta10.isEnabled() && aposta >= 10){
                        aposta-=10;
                        fichas+=10; 
                        if(aposta<5) embaralhar.setEnabled(false);
                        apostatotal.setText("Aposta Total: "+aposta);
                        jlaposta.setText("U$ "+fichas+",00");                        
                    }    
    
                    if(me.getSource()==aposta50 && me.getButton()==me.BUTTON1 && aposta50.isEnabled() && fichas >= 50){
                        aposta+=50;
                        if(aposta>=5) 
                            embaralhar.setEnabled(true);
                        apostatotal.setText("Aposta Total: "+aposta);
                        if(fichas>=50){fichas-=50;}
                    }else if(me.getSource()==aposta50 && me.getButton()==me.BUTTON3 && aposta50.isEnabled() && aposta >= 50){
                        aposta-=50;
                        fichas+=50; 
                        if(aposta<5) embaralhar.setEnabled(false);
                        apostatotal.setText("Aposta Total: "+aposta);
                        jlaposta.setText("U$ "+fichas+",00");                        
                    }          
    
                    if(me.getSource()==aposta100 && me.getButton()==me.BUTTON1 && aposta100.isEnabled() && fichas >= 100){
                        aposta+=100;
                        if(aposta>=5) embaralhar.setEnabled(true);
                        apostatotal.setText("Aposta Total: "+aposta);
                        if(fichas>=100){fichas-=100;}
                    }else if(me.getSource()==aposta100 && me.getButton()==me.BUTTON3 && aposta100.isEnabled() && aposta >= 100){
                        aposta-=100;
                        fichas+=100; 
                        if(aposta<5) embaralhar.setEnabled(false);
                        apostatotal.setText("Aposta Total: "+aposta);
                        jlaposta.setText("U$ "+fichas+",00");                        
                    }          
                
                    if(me.getSource()==puxar && puxar.isEnabled() && vezdamesa==false){
                        cont3++;
                        dobrar.setEnabled(false);        
                        panel[cont]=new JPanel();
                        Carta x=new Carta(baralho.getFirst());
                        panel[cont].setBounds(290+(cont*20),300,74,105);
                        panel[cont].add(x.getIcon());
                        panel[cont].setBackground(new Color(0,100,0));
                        maojogador.add(x);
                        baralho.remove();
                        getCont().add(panel[cont],0);
                        cont++;
                        if(deDobrar==0 && escolha.equals("banco"))  movetoPoint(524,196);
                        pontosmao.setText(""+getPoints(maojogador));
                        getCont().validate();
                        if(temAs(maojogador) && getPoints(maojogador)>21){
                            maojogador=setAs11to1(maojogador);
                            pontosmao.setText(""+(Integer.parseInt(pontosmao.getText())-10));
                        }
                        if(Integer.parseInt(pontosmao.getText())>21){
                                JOptionPane jop=new JOptionPane();
                                jop.showMessageDialog(getCont(), "Jogador Estourou!!!");
                                getCont().validate();
                                me.setSource(finalizar);
                                mousePressed(me);
                        }else if(escolha.equals("banco")){
                                if(deDobrar==0)analise(maojogador, maomesa.get(0));
                        }
                        
                        
                        
                    }
    
                    if(me.getSource()==puxar && puxar.isEnabled() && vezdamesa==true){
                         panel2[cont2]=new JPanel();
                        Carta x=new Carta(baralho.getFirst());
                        panel2[cont2].setBounds(290+(cont2*20),0,74,105);
                        panel2[cont2].add(x.getIcon());
                        panel2[cont2].setBackground(new Color(0,100,0));
                        maomesa.add(x);
                        baralho.remove();
                        getCont().add(panel2[cont2],0);
                        cont2++;
                        pontosmesa.setText(""+getPoints(maomesa));
                        if(temAs(maomesa) && getPoints(maomesa)>21){
                            maomesa=setAs11to1(maomesa);
                            pontosmesa.setText(""+(Integer.parseInt(pontosmesa.getText())-10));
                        }
                        System.out.println("*****"+getPoints(maomesa));
                        //pontosmesa.setText(""+);
                        getCont().repaint();
                        if(escolha.equals("banco")){
                             
                        if(getPoints(maomesa)<17 || (getPoints(maomesa)==17 && temAs(maomesa))){
                            puxar.setEnabled(true);
                            dobrar.setVisible(false);
                            dobrar.setEnabled(false);
                            finalizar.setEnabled(false);
                            embaralhar.setEnabled(false);
                            JOptionPane.showMessageDialog(getCont(),"O Banco deve puxar");
                        }else if(getPoints(maomesa)>17 || ((getPoints(maomesa)==17 && !temAs(maomesa)))){
                            puxar.setEnabled(false);
                            dobrar.setVisible(false);
                            dobrar.setEnabled(false);
                            finalizar.setEnabled(false);
                            embaralhar.setEnabled(false);
                            if(getPoints(maomesa)>21 && temAs(maomesa)){
                                maomesa=setAs11to1(maomesa);
                                pontosmesa.setText(""+getPoints(maomesa));
                                puxar.setEnabled(true);
                            }else if(getPoints(maomesa)>21 && !temAs(maomesa)){
                                getCont().remove(jlmao);
                                getCont().setCursor(cursor);
                                iniciar.setEnabled(true);
                                JOptionPane.showMessageDialog(getCont(), "Banco estourou!!! clique em iniciar para outra mão!");
                                fichas += 2*aposta;
                                aposta=0;
                                apostatotal.setText("Aposta Total: "+aposta);
                            }else if(getPoints(maomesa)<=21){
                                getCont().remove(jlmao);
                                getCont().setCursor(cursor);
                                iniciar.setEnabled(true);
                                if(getPoints(maomesa)>=getPoints(maojogador)){ 
                                    JOptionPane.showMessageDialog(getCont(), "Banco venceu!!! clique em iniciar para outra mão!");
                                    aposta=0;
                                    apostatotal.setText("Aposta Total: "+aposta);
                                }else if(getPoints(maomesa)<getPoints(maojogador)){
                                    JOptionPane.showMessageDialog(getCont(), "Banco perdeu!!! clique em iniciar para outra mão!");
                                    fichas += 2*aposta;
                                    aposta=0;
                                    apostatotal.setText("Aposta Total: "+aposta);
                                }
                            }
                        }
                       }
                    }
    
                    if(me.getSource()==finalizar && finalizar.isEnabled()){
                       if(escolha.equals("jogador")){
                       Image cursorImage = Toolkit.getDefaultToolkit().getImage("");   
                       Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImage, new Point( 0, 0), "" );   
                       getCont().setCursor( blankCursor );
                       mevent=me;
                       abilitarApostas(false);
                       puxar.setEnabled(false);
                       finalizar.setEnabled(false);
                       dobrar.setEnabled(false);
                       }
                       vezdamesa=true;
                       embaralhar.setEnabled(false);
                       if(getCont().isAncestorOf(jlmao)){
                            getCont().remove(jlmao);
                       }
                       if(escolha.equals("jogador")){
                       if(deDobrar==1){
                           xMao=dobrar.getX()+30;
                           yMao=dobrar.getY()-30;
                           
                       }else{
                        xMao=finalizar.getX();
                        System.out.println(""+xMao);
                        yMao=finalizar.getY();
                       }
                       movetoPoint(330, 100);
                       
                       p2[1].setBounds(270,0,72,105);
                       p2[1].add(maomesa.get(1).getIcon(),0);
                       getCont().add(p2[1],0);
                    }else {getCont().remove(jlmao); 
                           finalizar.setEnabled(false);
                           getCont().setCursor(cursor);
                    }
                       
                    if(escolha.equals("jogador")){
                       pontosmesa.setText(""+getPoints(maomesa));
                       pontosmesa.setVisible(true);
                       
                       esperar=false;
                       t.start();
                     }           
                       deDobrar=0;
                       if(escolha.equals("banco")){
                           p2[1].setEnabled(true);
                           JOptionPane.showMessageDialog(getCont(),"Desvire a sua segunda carta!");
                           mouselivre=true;puxar.setEnabled(false);
                        if(getPoints(maomesa)<17 || (getPoints(maomesa)==17 && temAs(maomesa))){
                            puxar.setEnabled(false);
                            dobrar.setVisible(false);
                            dobrar.setEnabled(false);
                            embaralhar.setEnabled(false);
                        }else if(getPoints(maomesa)>17){
                            puxar.setEnabled(false);
                            dobrar.setVisible(false);
                            dobrar.setEnabled(false);
                            embaralhar.setEnabled(false);
                        }
                        
                       } 
                    }
                    if(me.getSource()==dobrar && dobrar.isEnabled() && fichas>=aposta){
                       movetoPoint(dobrar.getX()+30,dobrar.getY()+20);
                       fichas-=aposta;
                       aposta*=2;
                       deDobrar=1;
                       jlaposta.setText("U$ "+fichas+",00");
                       apostatotal.setText("Aposta Total: "+aposta);
                       vezdamesa = false;
                       puxar.setEnabled(true);
                       me.setSource(puxar);
                       deDobrar=1;
                       mousePressed(me);
                       movetoPoint(dobrar.getX()+60,dobrar.getY()+30);
                       dobrar.setEnabled(false);
                       me.setSource(finalizar);
                       mousePressed(me);
                    }
    
                    if(me.getSource()==p2[1] && escolha.equals("banco") && me.getButton()==me.BUTTON1 && (resposta==JOptionPane.YES_OPTION || mouselivre==true)){
                       pontosmesa.setText(""+getPoints(maomesa));
                       pontosmesa.setVisible(true);
                       
                       p2[1].setBounds(270,0,72,105);
                       p2[1].add(maomesa.get(1).getIcon(),0);
                       getCont().add(p2[1],0);
                       if(mouselivre==false){
                           fichas += 3*aposta;
                           iniciar.setEnabled(true);
                           JOptionPane.showMessageDialog(getCont(),"Clique iniciar para iniciar outra mão!!!!!");
                       }
                       
                       if(mouselivre==true){
                                if(getPoints(maojogador)>21){
                                       JOptionPane.showMessageDialog(getCont(),"O Banco recolhe a aposta!!!! Clique em iniciar para outra mão");
                                        aposta=0;
                                        apostatotal.setText("Aposta Total: "+aposta);
                                        iniciar.setEnabled(true);
                                        dobrar.setEnabled(false);
                                        puxar.setEnabled(false);
                                        finalizar.setEnabled(false);
                               }else if(getPoints(maomesa)==21){
                                    if((maojogador.get(0).getPoint()+maojogador.get(1).getPoint())==21){
                                    JOptionPane.showMessageDialog(getCont(),"O banco fez BlackJack também, empate!!!!");
                                    aposta=0;
                                    apostatotal.setText("Aposta Total: "+aposta);
                                    fichas += aposta;
                                    iniciar.setEnabled(true); 
                                        dobrar.setEnabled(false);
                                        puxar.setEnabled(false);
                                        finalizar.setEnabled(false);
                                    fichas+=aposta/2;
                                    }else{  JOptionPane.showMessageDialog(getCont(),"BlackJack!!!!");
                                            dobrar.setEnabled(false);
                                            iniciar.setEnabled(true);
                                        puxar.setEnabled(false);
                                        finalizar.setEnabled(false);
                                    }
                               }else if(getPoints(maomesa)<17 || (getPoints(maomesa)==17 && temAs(maomesa) && !iniciar.isEnabled())){
                                   JOptionPane.showMessageDialog(getCont(),"banco deve puxar");
                                   puxar.setEnabled(true);}
                                else if(getPoints(maomesa)>=17 &&  !iniciar.isEnabled()){iniciar.setEnabled(true);
                                     if(getPoints(maomesa)>=getPoints(maojogador)){                  
                                         dobrar.setEnabled(false);
                                        puxar.setEnabled(false);
                                        finalizar.setEnabled(false);   
                                         JOptionPane.showMessageDialog(getCont(),"Banco ganhou, Clique em iniciar para iniciar outra mão");
                                         aposta=0;
                                        apostatotal.setText("Aposta Total: "+aposta);
                                     }else {
                                    dobrar.setEnabled(false);
                                        puxar.setEnabled(false);
                                        finalizar.setEnabled(false);
                                    JOptionPane.showMessageDialog(getCont(),"Jogador ganhou, Clique em iniciar para iniciar outra mão");fichas += 2*aposta;
                                    aposta=0;
                                    apostatotal.setText("Aposta Total: "+aposta);
                                     }
                                     }
                       }
                       if(getPoints(maomesa)>21){
                        maomesa=setAs11to1(maomesa);
                        pontosmesa.setText(""+getPoints(maomesa));
                       }
                       
                       getCont().setCursor(cursor);
                       getCont().repaint();
                       validate();
                       
                       if(mouselivre=false){
                       if(getPoints(maomesa)==21){
                       getCont().remove(jlmao);
                       dobrar.setEnabled(false);
                       puxar.setEnabled(false);
                       finalizar.setEnabled(false);
                       JOptionPane.showMessageDialog(getCont(), "Banco tem BlackJack....o seguro cobre 2 para 1......Jogador leva: "+apostatotal.getText());
                       getCont().repaint();
                       me.setSource(iniciar);
                       mousePressed(me);
                       mouselivre=false;
                       p2[1].setEnabled(false);
                       }}
                    }
                    if(me.getSource()==sair){
                                if(JOptionPane.showConfirmDialog(getCont(), "Tem certeza que deseja sair!!!")==JOptionPane.YES_OPTION){
                                this.setVisible(false);
                                this.setEnabled(false);
                                Inicial.getInstancia().setVisible(true);}
                    }
                    jlaposta.setText("U$ "+fichas+",00");
                    getCont().validate();}

    public void mouseReleased(MouseEvent e) {}

    public void mouseEntered(MouseEvent e) {
    embaralhar.setBackground(new Color(0,100,0));
    }

    public void mouseExited(MouseEvent e) {}

    public void actionPerformed(ActionEvent e) {
        if(esperar==false){
            getCondicao(mevent);
            getCont().remove(jlmao);}
        esperar=false;
        getCont().remove(jlmao);
        t.stop();
    }

    private int computarCasos(Vector<Carta> vc1){
        Random rd=new Random();
        int n=rd.nextInt(99);
        if(getPoints(vc1)>=4 && getPoints(vc1)<=11){
            if(n<70) return JOptionPane.NO_OPTION;
        }else if(getPoints(vc1)>=12 && getPoints(vc1)<=17 && !temAs(vc1)){
            if(n<65) return JOptionPane.NO_OPTION;
        }else if(getPoints(vc1)>=12 && getPoints(vc1)<=17 && temAs(vc1)){
            if(n<90) return JOptionPane.NO_OPTION;
        }else if(getPoints(vc1)>=18 && getPoints(vc1)<=19){
            if(n<98) return JOptionPane.NO_OPTION;
        }else if(getPoints(maojogador)==20) return JOptionPane.NO_OPTION;
        return JOptionPane.YES_OPTION;
    }
};
class Baralho<String> extends LinkedList<String>{
     public Baralho(){
         super();
     }
     public Baralho(LinkedList ll){
         this.clear();
         Random rd=new Random();
         System.out.println(ll.size());
         for(int i=ll.size()-1;i>=0;i--){
            int number;
            if(i!=0){
               number=rd.nextInt(i);
            }else{
                number=0;
            }
            this.add((String)ll.get(number));
            ll.remove(number);
        }
     }
     public void reEmbaralhar(LinkedList ll){
         this.clear();
         Random rd=new Random();
         System.out.println(ll.size());
         for(int i=ll.size()-1;i>=0;i--){
            int number;
            if(i!=0){
               number=rd.nextInt(i);
            }else{
                number=0;
            }
            this.add((String)ll.get(number));
            ll.remove(number);
        }
     }
}

class Carta{
   private String nipe;
   private String simbolo;
   private String arquivo;
   private JLabel icone;
   private Rectangle quadro;
   private static int position=250;
   private final String dir="src\\BlackJack\\cartas";;
   private int points=0;
   public Carta(String identifier){
       icone=new JLabel(new ImageIcon(dir+"\\"+identifier));
       quadro=new Rectangle(position, 250, 74, 98);
       arquivo=identifier;
       simbolo=identifier.substring(0, identifier.indexOf("_"));
       nipe=identifier.substring(identifier.indexOf('_')+1, identifier.indexOf('.'));
       points=this.getPoints();
       System.out.println(simbolo);
       System.out.println(nipe);
   }
   public int getPoints(){
       if(simbolo.equals("as")){
           points=11;
       }else if(simbolo.equals("dois")){
           points=2;
       }else if(simbolo.equals("tres")){
           points=3;
       }else if(simbolo.equals("quatro")){
           points=4;
       }else if(simbolo.equals("cinco")){
           points=5;
       }else if(simbolo.equals("seis")){
           points=6;
       }else if(simbolo.equals("sete")){
           points=7;
       }else if(simbolo.equals("oito")){
           points=8;
       }else if(simbolo.equals("nove")){
           points=9;
       }else if(simbolo.equals("dez") || simbolo.equals("valete") || simbolo.equals("dama") || simbolo.equals("reis")){
           points=10;
       }
       return points;  
   }
   public String getArquivo(){
       return arquivo;
   }
   public JLabel getIcon(){
       return icone;
   }
   public Rectangle getRectangle(){
       return quadro;
   }
   public void setPosition(int aux){
       position+=aux;
   }
   public void setPoints(int num){
       points=num;
   }
   public int getPoint(){
       return points;
   }
   public String getSimbolo(){
       return simbolo;
   }
}

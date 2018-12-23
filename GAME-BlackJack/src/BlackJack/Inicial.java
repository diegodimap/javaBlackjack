/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package BlackJack;

import java.awt.Container;
import java.awt.Cursor;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import javax.swing.*;
import javax.swing.text.MaskFormatter;
 
public class Inicial extends JFrame {
    private static LookAndFeel laf;
     
        private JPanel panel; 
        private JButton b_confirmar, b_sair;
        private JLabel l_nome,l_fichas; 
        private JFormattedTextField f_nome;
        private MaskFormatter masknome;
        private JRadioButton banco,jogador;
        private static String nome,escolha,fichas;
        private JComboBox c_fichas;
        private String[] lista = {"250","600","1100","1600","2000"};
        private static Inicial init;
        private static BlackJack bj;
    
    
    public Inicial() {
          super("SunCity JBlackJack");
        
            setSize(400, 250);
            setResizable(false);
            setLocationRelativeTo(this);
            setLayout(null);
            
            

            b_confirmar = new JButton("Confirmar");
            b_confirmar.setBounds(250, 70, 100, 50);
            b_confirmar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            b_sair= new JButton("Sair");
            b_sair.setBounds(250, 130, 100, 50);
            b_sair.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            
            getRootPane().setDefaultButton(b_confirmar);

            l_nome = new JLabel("Digite seu nome: ");
            l_nome.setBounds(10, 10, 150, 20);
            try {    
                masknome = new MaskFormatter("********************");
            } catch (ParseException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
            
            f_nome = new JFormattedTextField(masknome);
            f_nome.setBounds(120, 10, 200, 20);

            l_fichas = new JLabel("Fichas iniciais: ");
            l_fichas.setBounds(10, 40, 100, 20);

            c_fichas = new JComboBox(lista);
            c_fichas.setBounds(120, 40, 100, 20);
            c_fichas.setSelectedItem("1100");

            banco = new JRadioButton("Banco");
            //banco.setBounds(50, 80, 80, 20);
            jogador = new JRadioButton("Jogador");
            jogador.setSelected(true);

            panel = new JPanel();
            panel.setLayout(new GridLayout(2, 1));
            panel.setBounds(50, 80, 120, 100);
            panel.setBorder(BorderFactory.createTitledBorder("Escolha"));

            ButtonGroup butgrup = new ButtonGroup();
            butgrup.add(jogador);
            butgrup.add(banco);

            panel.add(jogador);
            panel.add(banco);
            add(f_nome);
            add(l_nome);
            add(panel);
            add(l_fichas);
            add(c_fichas);
            add(b_confirmar);
            add(b_sair);

            init = this;
            b_confirmar.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    if (banco.isSelected()) {
                        escolha = "banco";
                    }
                    if (jogador.isSelected()) {
                        escolha = "jogador";
                    }
                    nome = f_nome.getText();
                    fichas = (String) c_fichas.getSelectedItem();
                    setVisible(false);
                    try{
                        UIManager.setLookAndFeel(laf);
                        bj=new BlackJack();
                    }catch(Exception ex){}
                }
            });

            b_sair.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    if(JOptionPane.showConfirmDialog(getCont(), "tem certeza qu desja sair?")==JOptionPane.YES_OPTION){
                        getInstancia().setVisible(false);
                        javax.swing.Timer t=new javax.swing.Timer(3000, new ActionListener(){
                            public void actionPerformed(ActionEvent e) {
                                System.exit(0);                                                      
                            }                                                     
                        });
                        t.start();
                        JOptionPane.showMessageDialog(getCont(),"www.BlackJack.com\nwww.WizardOfOdds.com");
                    }
                }
            });
            
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setVisible(true);
      
    }
    public static String getNameJogador(){
        return nome;
    }
    public static String getSelecionado(){
        return escolha;
    }
    public static int getFichas(){
        return Integer.parseInt(fichas);
    }
    public static Inicial getInstancia(){
        return init; 
    }
    public static void finalizar(){
        bj=null;
    }
    public void gc(){
        System.gc();
    }
    public Container getCont(){
        return getContentPane(); 
    }
    
public static void main (String[] args){
    try{
        laf = UIManager.getLookAndFeel();
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName ());
        new Inicial();
    }
    catch(Exception e){
        
    }
}    
    
}

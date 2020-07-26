import java.awt.*;  
import java.awt.event.*;
import javax.swing.*;
import javax.swing.JFrame.*;
import javafx.scene.control.TextField; 
import java.lang.*;
import java.lang.Thread.*;

public class SudokoUI extends JFrame{
    JTextField[][] tfSudoko;
    JPanel c1,c2;
    GridBagConstraints c;
    Button validate;
    Button showError;
    int[][] sudoko,tempSudoko={{1,5,2,4,8,9,3,7,6},{7,2,9,2,5,6,8,4,1},{4,6,8,3,7,1,2,9,5},{3,8,7,1,2,4,6,5,9},{5,9,1,7,6,3,4,2,8},{2,4,6,8,9,5,7,1,3},{9,1,4,6,3,7,5,8,2},{6,2,5,9,4,8,1,3,7},{8,7,3,5,1,2,9,6,3}};
;
    Label lbResult;
    Thread[] tdRC;
    SudokoUI This;
    SudokoRCValidate[] sRCV;
    public SudokoUI(){
        This=this;
        setTitle("SUDOKO");
        sudoko=new int[9][9];
        c = new GridBagConstraints();
        tfSudoko=new JTextField[9][9];
       // tempSudoko={{1,5,2,4,8,9,3,7,6},{7,2,9,2,5,6,8,4,1},{4,6,3,1,2,4,6,5,9},{3,8,7,1,2,4,6,5,9},{5,9,1,7,6,3,4,2,8},{2,4,6,8,9,5,7,1,3},{9,1,4,6,3,7,5,8,2},{6,2,5,9,4,8,1,3,7},{8,7,3,5,1,2,9,6,4}};
        sRCV=new SudokoRCValidate[27];
        tdRC=new Thread[27];
        c2 =new JPanel();
        c1 =new JPanel();
        lbResult=new Label("Result");
        validate=new Button("Validate");
        showError=new Button("Show Errors");
        c1.setLayout(new GridLayout(9,9));
        c1.setSize(400,400);
        c2.setLayout(new GridLayout(2,2));
        this.setLayout(new GridLayout(2,1));
        c.fill = GridBagConstraints.HORIZONTAL;
        
        c.gridx = 0;
        c.gridy = 0;
        add(c1,c);
        
        c.gridx = 0;
        c.gridy = 1;
        add(c2,c);
        this.setSize(500,500);
        for(int i=0;i<9;i++)
            for(int j=0;j<9;j++){
                tfSudoko[i][j]=new JTextField(1);
                c1.add(tfSudoko[i][j]);
            }
            this.setVisible(true);
        c2.add(validate);
        c2.add(showError);
        c2.add(lbResult);
        addActionListeners();
            setValues();
    }
    void setValues(){
        for(int i=0;i<9;i++)
            for(int j=0;j<9;j++)
                tfSudoko[i][j].setText(""+tempSudoko[i][j]);
    }
    private void addActionListeners(){
        this.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                          System.exit(0);
            }
          });
        validate.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) { 
                
                for(int i=0;i<9;i++)
                    for(int j=0;j<9;j++){
                        sudoko[i][j]=Integer.parseInt(tfSudoko[i][j].getText());
                    }
                int a=0,b=0;
                for(int i=0;i<9;i++){
                    sRCV[i]=new SudokoRCValidate(sudoko,i,0,0,This,0);
                    sRCV[i+9]=new SudokoRCValidate(sudoko,i,0,1,This,0);
                    sRCV[i+18]=new SudokoRCValidate(sudoko,a,b,2,This,0);
                    b+=3;
                    if(b==9){
                        a+=3;
                        b=0;
                    }
                }

                for(int i=0;i<9;i++){
                    tdRC[i]=new Thread(sRCV[i]);
                    tdRC[i].start();

                    tdRC[i+9]=new Thread(sRCV[i+9]);
                    tdRC[i+9].start();

                    tdRC[i+18]=new Thread(sRCV[i+18]);
                    tdRC[i+18].start();
                }   
                try{
                    for(int i=0;i<9;i++){                    
                        tdRC[i].join();
                        tdRC[i+9].join();
                        tdRC[i+18].join();
                    }
                }
                catch(InterruptedException ex){
                    System.out.print("exception");
                }
                int status=0;
                for(int i=0;i<27;i++){
                    if(sRCV[i].getStatus()==1){
                        status=1;
                        break;
                    }

                }
                if(status==0){
                    setCorrect();
                    This.lbResult.setText("Correct Answer ");
                }
                
            }
            
        });
        showError.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) { 
                
                for(int i=0;i<9;i++)
                    for(int j=0;j<9;j++){
                        sudoko[i][j]=Integer.parseInt(tfSudoko[i][j].getText());
                    }

                int a=0,b=0;
                for(int i=0;i<9;i++){
                    sRCV[i]=new SudokoRCValidate(sudoko,i,0,0,This,1);
                    sRCV[i+9]=new SudokoRCValidate(sudoko,i,0,1,This,1);
                    sRCV[i+18]=new SudokoRCValidate(sudoko,a,b,2,This,1);
                    b+=3;
                    if(b==9){
                        a+=3;
                        b=0;
                    }
                }

                for(int i=0;i<9;i++){
                    tdRC[i]=new Thread(sRCV[i]);
                    tdRC[i].start();

                    tdRC[i+9]=new Thread(sRCV[i+9]);
                    tdRC[i+9].start();

                    tdRC[i+18]=new Thread(sRCV[i+18]);
                    tdRC[i+18].start();
                }   
                try{
                    for(int i=0;i<9;i++){                    
                        tdRC[i].join();
                        tdRC[i+9].join();
                        tdRC[i+18].join();
                    }
                }
                catch(InterruptedException ex){
                    System.out.print("exception");
                }
                int status=0;
                for(int i=0;i<27;i++){
                    if(sRCV[i].getStatus()==1){
                        status=1;
                        break;
                    }

                }
                if(status==0){
                    setCorrect();
                    This.lbResult.setText("Correct Answer ");
                }
            }
            
        });  
    }
    void setCorrect(){
        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                tfSudoko[i][j].setForeground(Color.green);
            }
        }
    }
    void setResult(String s){
        this.lbResult.setText(s);
    }
    public static void main(String[] argv){
        new SudokoUI();

    }
}
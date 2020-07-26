import java.lang.*;
import java.awt.*;
public class SudokoRCValidate implements Runnable{
    int[][] sudoko;
    int index,type,status,index2;
    int[] a;
    int errorVisible;
    SudokoUI sudokoUI;
    SudokoRCValidate(int[][] sudoko,int index,int index2,int type, SudokoUI sudokoUI,int errorVisible){
        this.a=new int[10];
        this.sudokoUI=sudokoUI;
        this.sudoko=new int[9][9];
        this.errorVisible=errorVisible;
        for(int i=0;i<9;i++)
            for(int j=0;j<9;j++){               
                this.sudoko[i][j]=sudoko[i][j];
            }
        this.index=index;
        this.index2=index2;
        this.type=type;
        this.status=0;
    }
    public int getStatus(){
        return this.status;
    }
    public void run(){//System.out.print(" t"+type+" ");
        if(type==0){//row validation
            for(int i=0;i<9;i++){
                if(a[sudoko[index][i]]==1){                   
                    if(errorVisible==1)
                        sudokoUI.tfSudoko[index][i].setForeground(Color.red);
                    status=1;//System.out.print(" r "+a[sudoko[index][i]]);
                    sudokoUI.setResult("Wrong Answer");
                    break;
                }
                a[sudoko[index][i]]=1;
            }
        }
        else if(type==1){//column validation
            for(int i=0;i<9;i++){
                if(a[sudoko[i][index]]==1){
                    if(errorVisible==1)
                        sudokoUI.tfSudoko[i][index].setForeground(Color.red);
                    status=1;System.out.print(" c "+a[sudoko[i][index]]);
                    sudokoUI.setResult("Wrong Answer");
                    break;
                }
                a[sudoko[i][index]]=1;
            }
        }
        else{//sub sudoko validation
            int c=index+3,b=index2+3;//System.out.print(" b "+a[sudoko[c][c]]+" "+index+c+" "+index2+b+"\n");
            for(int i=index;i<c;i++){
                for(int j=index2;j<b;j++){
                    if(a[sudoko[i][j]]==1){
                        if(errorVisible==1)
                            sudokoUI.tfSudoko[i][j].setForeground(Color.red);
                        status=1;
                        sudokoUI.setResult("Wrong Answer");
                        break;
                    }
                    a[sudoko[i][j]]=1;
                }       
            }
        }
    }
}

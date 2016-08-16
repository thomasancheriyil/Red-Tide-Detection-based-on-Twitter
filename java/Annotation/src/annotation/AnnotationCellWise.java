/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package annotation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintStream;
import java.io.PrintWriter;

/**
 *
 * @author ThomasGeorge
 */
public class AnnotationCellWise {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        try{
            String cellfile = "sample_cell_labels.txt";
            String labelfile = "sample_test_labels.txt";
            String st="",st1="";
            int j;
            int initcnt=0;
            //String output="", tweetid="",cellid="",tweetid1="",label="";
            String cellid="",label="",prevcellid="",prevlabel="";
            int relcnt=0,irrelcnt=0;
            BufferedReader br1 = new BufferedReader(new FileReader(cellfile));
            FileWriter fstream = new FileWriter("annotationcellwise.csv", true);
		BufferedWriter bw = new BufferedWriter(fstream);
            //PrintWriter pw = new PrintWriter(new FileWriter("annotationcell.csv"));
            while((st=br1.readLine())!=null){
                //Pattern p = Pattern.compile("^([0-9+])+([0-9]+.[0-9+])+(-?[0-9]+.[0-9+])+([0-9]+_[0-9+])");
                //Pattern p = Pattern.compile("^(.*)+(.*)+(.*)+(.*)");
                int i=0;
                String temp="";
                if(initcnt==0){
                    for (String retval: st.split("\\t", 2)){
                        if(i%2==0){
                            cellid=retval;


                        }
                        if(i%2==1){
                            label=retval;
                            if(label.equals("relevant"))
                                relcnt++;
                            if(label.equals("irrelevant"))
                                irrelcnt++;
                            //output+=retval+","+temp;
                            



                            
                        }
                        i++;
                    
                    }
                    
                    prevcellid=cellid;
                    prevlabel=label;
                    
                }
                else {
                    for (String retval: st.split("\\t", 2)){
                        if(i%2==0){
                            cellid=retval;


                        }
                        if(i%2==1){
                            label=retval;
                            if(cellid.equals(prevcellid)) {
                                if(label.equals("relevant"))
                                    relcnt++;
                                if(label.equals("irrelevant"))
                                    irrelcnt++;
                            //output+=retval+","+temp;
                            

                            }
                            else {
                                if(relcnt>=irrelcnt) {
                                    bw.write(prevcellid+","+"relevant\n");
                                }
                                else {
                                    bw.write(prevcellid+","+"irrelevant\n");
                                }
                                relcnt=0;
                                irrelcnt=0;
                                if(label.equals("relevant"))
                                    relcnt++;
                                if(label.equals("irrelevant"))
                                    irrelcnt++;
                                prevcellid=cellid;
                                prevlabel=label;
                            }
                            
                        }
                        i++;
                    
                    }
                }
                
                
                /*
                Pattern p = Pattern.compile("^([0-9+])+(.*)+([0-9+]_[0-9+])");
                Matcher m = p.matcher(st);

                if (m.find()) {
                    //System.out.println(m.group(4)+"\t"+m.group(1));
                    System.out.println(m.group(0));
                }
                        */
                initcnt++;
            }
            /*
            File file = new File("annotationcell.csv");
	  FileOutputStream fis = new FileOutputStream(file);
	  PrintStream out = new PrintStream(fis);
	  System.setOut(out);
            System.out.println(output);
                    */
            bw.close();
            
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }    
    
}

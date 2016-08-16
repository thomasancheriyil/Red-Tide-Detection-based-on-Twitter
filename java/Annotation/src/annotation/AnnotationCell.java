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
public class AnnotationCell {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        try{
            String cellfile = "sample_test_geo.txt";
            String labelfile = "sample_test_labels.txt";
            String st="",st1="";
            int j;
            String output="", tweetid="",cellid="",tweetid1="",label="";
            BufferedReader br1 = new BufferedReader(new FileReader(cellfile));
            FileWriter fstream = new FileWriter("annotationcell.csv", true);
		BufferedWriter bw = new BufferedWriter(fstream);
            //PrintWriter pw = new PrintWriter(new FileWriter("annotationcell.csv"));
            while((st=br1.readLine())!=null){
                //Pattern p = Pattern.compile("^([0-9+])+([0-9]+.[0-9+])+(-?[0-9]+.[0-9+])+([0-9]+_[0-9+])");
                //Pattern p = Pattern.compile("^(.*)+(.*)+(.*)+(.*)");
                int i=0;
                String temp="";
                
                for (String retval: st.split("\\t", 2)){
                    if(i%2==0){
                        temp=retval;
                        tweetid=retval;
                        
                    }
                    if(i%2==1){
                        cellid=retval;
                        //output+=retval+","+temp;
                        System.out.println("tweet id: "+tweetid+ " cell id: "+ cellid);
                        BufferedReader br2 = new BufferedReader(new FileReader(labelfile));
                        
                        while((st1=br2.readLine())!=null){
                            j=0;
                            for (String retval1: st1.split("\\t", 2)){
                                if(j%2==0){
                                    tweetid1=retval1;
                                    
                        
                                }
                                if(j%2==1){
                                    label=retval1;
                                    System.out.println("tweet id1: "+tweetid1+ " label: "+ label);
                                    if(tweetid.equals(tweetid1)){
                                        output+=cellid+","+label+"\n";
                                        bw.write(output);
                                        output="";
                                    }
                                }
                                j++;
                            }
                        }
                        br2.close();
                    }
                    i++;
                    
                }
                
                /*
                Pattern p = Pattern.compile("^([0-9+])+(.*)+([0-9+]_[0-9+])");
                Matcher m = p.matcher(st);

                if (m.find()) {
                    //System.out.println(m.group(4)+"\t"+m.group(1));
                    System.out.println(m.group(0));
                }
                        */
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

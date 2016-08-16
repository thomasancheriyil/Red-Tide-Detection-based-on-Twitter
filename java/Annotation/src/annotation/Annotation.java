/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package annotation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintStream;

/**
 *
 * @author ThomasGeorge
 */
public class Annotation {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        try{
            String cellfile = "sample_train_geo.txt";
            String textfile = "sample_train.txt";
            String st="",st1="";
            int j;
            String output="", tweetid="";
            BufferedReader br1 = new BufferedReader(new FileReader(cellfile));
            
            while((st=br1.readLine())!=null){
                //Pattern p = Pattern.compile("^([0-9+])+([0-9]+.[0-9+])+(-?[0-9]+.[0-9+])+([0-9]+_[0-9+])");
                //Pattern p = Pattern.compile("^(.*)+(.*)+(.*)+(.*)");
                int i=0;
                String temp="";
                
                for (String retval: st.split("\\t", 4)){
                    if(i%4==0){
                        temp=retval;
                        tweetid=retval;
                        
                    }
                    if(i%4==3){
                        output+=retval+","+temp;
                        System.out.println("tweet id: "+temp);
                        BufferedReader br2 = new BufferedReader(new FileReader(textfile));
                        j=0;
                        while((st1=br2.readLine())!=null){
                            
                            if(st1.endsWith(tweetid+"\"}")&& j == 0){
                               /*
                               Pattern p = Pattern.compile("{\"text\": \"+(.*)+\", \"stream_type+(.*)+\"}$");
                               Matcher m = p.matcher(st1);
                               if (m.find()) {
                                    //System.out.println(m.group(4)+"\t"+m.group(1));
                                    System.out.println(m.group(1));
                                    output+=","+m.group(1)+"\n";
                                }
                                */
                                        j=1;
                                        int size=st1.length();
                                        String temp1 = st1.subSequence(10,size-60).toString();
                                        System.out.println(temp1);
                                        output+=","+temp1+"\n";
                                    
                                
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
            File file = new File("annotation.csv");
	  FileOutputStream fis = new FileOutputStream(file);
	  PrintStream out = new PrintStream(fis);
	  System.setOut(out);
            System.out.println(output);
            
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }    
    
}

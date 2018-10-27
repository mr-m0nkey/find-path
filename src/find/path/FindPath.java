/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package find.path;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 *
 * @author mayowa
 */
public class FindPath {
    
    private static Queue<File> queue = new ConcurrentLinkedQueue();

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
   
        if(args.length > 0){
            String query = args[0];
            String path = findPath(query);
            if(path == null){
                System.out.println("File not found");
            }else{
                String cmd = "";
                for(int i = 1; i < args.length; i++){
                    cmd += args[i] + " ";
                }
                if(new File(path).isDirectory()){
                    Process p = Runtime.getRuntime().exec("cmd /c start cmd.exe /K \"cd " + path + " && " + cmd + " \""); 
                }
                
                
            }
            
        }else{
            System.out.println("Enter a file or director as a valid parameter");
            System.exit(-1);
        }
        
    }
    
    static String findPath(String query) throws IOException{
        File root = new File(System.getProperty("user.dir"));
        queue.add(root);
        while(!queue.isEmpty()){
            File current = queue.remove();
            if(current.getName().toLowerCase().equals(query.toLowerCase())){
                System.out.println(current.getAbsolutePath());
                System.out.println("Continue searching? (Y/N)");
                boolean valid_input = true;
                Scanner get = new Scanner(System.in);
                while(valid_input){
                    if(get.next().toLowerCase().equals("y")){
                        valid_input = false;
                    }else{
                        return current.getAbsolutePath();
                    }
                }
                
            }
            if(current.isDirectory()){
                File[] content = current.listFiles();
                if(content != null){
                    queue.addAll(Arrays.asList(content));
                }
                
            }
      
        }
        
        return null;
        
    }
    
}

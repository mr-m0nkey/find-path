/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package find.path;

import java.io.File;
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
    public static void main(String[] args) {
        // TODO code application logic here
        if(true){
        //if(!args[0].isEmpty()){
            //String path = args[0];
            String query = "findPath.java";
            findPath(query);
        }else{
            System.out.println("Enter a file or director as a valid parameter");
            System.exit(-1);
        }
        
    }
    
    static void findPath(String query){
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
                }else if(get.next().toLowerCase().equals("n")){
                    valid_input = false;
                    return;
                }
                }
                
            }
            if(current.isDirectory()){
                File[] content = current.listFiles();
                queue.addAll(Arrays.asList(content));
            }
      
        }
        
    }
    
}

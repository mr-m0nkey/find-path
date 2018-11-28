/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package find.path;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mayowa
 */
public class FindPath {
    
    //TODO: add alisasing 
    //TODO: add multiple paths per cache location
    //TODO: make code more structured and separate concerns
    //TODO: add multithreading for faster searches
    
    private static Queue<File> queue = new ConcurrentLinkedQueue();
    private static HashMap<String, String> cache_map;

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        
        String os = System.getProperty("os.name");
        if(!os.toLowerCase().contains("windows")){
            System.out.println("Sorry, this version is only supported on windows");
            return;
        }
        
        File cache = new File("cache.dat");
        if(!cache.exists()){
            try {
                cache.createNewFile();
            } catch (IOException ex) {
                System.out.println("Open cmd with administrative access to crawl through this directory");
            }
            cache_map = new HashMap();
        }else{
            ObjectInputStream input = new ObjectInputStream(new FileInputStream(cache));
            try {
                cache_map = (HashMap<String, String>)input.readObject();
            } catch (ClassNotFoundException ex) {
                cache_map = new HashMap();
                Logger.getLogger(FindPath.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if(args.length > 0){
            String query = args[0];
            String path = findPath(query);
            if(path == null){
                System.out.println("File not found");
            }else{
                ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(cache));
                output.writeObject(cache_map);
                String cmd = "";
                for(int i = 1; i < args.length; i++){
                    cmd += args[i] + " ";
                }
                if(new File(path).isDirectory()){
                    Process p = Runtime.getRuntime().exec("cd " + path + " && " + cmd + " \""); 
                }
                
                
            }
            
        }else{
            System.out.println("Enter a file or director as a valid parameter");
            System.exit(-1);
        }
        
    }
    
    static String findPath(String query) throws IOException{
        File root = new File(System.getProperty("user.dir"));
        //TODO: check cache from root so it doesn't return paths outside the workind directory
        Scanner get = new Scanner(System.in);
        if(cache_map.containsKey(query)){
            if(new File(cache_map.get(query)).exists()){
                System.out.println(cache_map.get(query));
                System.out.println("Continue searching?");
                if(get.next().toLowerCase().equals("y")){

                }else{
                    return cache_map.get(query);
                }
            }else{
                cache_map.remove(query);
            }
                
                
            
        }
        queue.add(root);
        while(!queue.isEmpty()){
            File current = queue.remove();
            if(current.getName().toLowerCase().equals(query.toLowerCase())){
                System.out.println(current.getAbsolutePath());
                System.out.println("Continue searching? (Y/N)");
                boolean valid_input = true;
                while(valid_input){
                    if(get.next().toLowerCase().equals("y")){
                        valid_input = false;
                    }else{
                        cache_map.put(query, current.getAbsolutePath());
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

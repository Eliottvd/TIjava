/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CodeBehind;

import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
/**
 *
 * @author faculty
 */
public class MainApp {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException 
    {
       TreeDecomposition(true);
       
//       BufferedImage printReconImage;
//       printReconImage=QuadTreeRecon.reconstructImage();
//       
//       ImageIo.writeImage(printReconImage,"jpg", "ReconImage.jpg");
       
    }
    
    
    public static void TreeDecomposition(boolean isBoundary) throws FileNotFoundException, IOException 
    {
        System.out.println("Image Decomposition to Binary String Representation");
        
        // Image Representation 
        String fname ="originalBinaryimage.jpg";
        
        //Threshold to stop splitting when the specified region size is reached
        int decomposeSizethreshold =4;
        
        /*String[] qTreeRepresentation = QuadTreeImageRepresent.imageQTreerepresentation(fname,decomposeSizethreshold, isBoundary);
        String treeRepString         = qTreeRepresentation[0];
        String objectRepString       = qTreeRepresentation[1];
         
//        PrintWriter out1 = new PrintWriter("treeRepString.txt");
//        PrintWriter out2 = new PrintWriter("objRepString.txt");
        
        System.out.println("Results : Tree Representation- "+ treeRepString );
       // out1.println(treeRepString);
        Files.write(Paths.get("./treeRepString.txt"), treeRepString.getBytes());
        System.out.println("Expected: Tree Representation- "+ "01011011111101111" );
        System.out.println("Results : Object Representation- "+ objectRepString );
        Files.write(Paths.get("./objRepString.txt"), objectRepString.getBytes());
       // out2.println(objectRepString);
        System.out.println("Expected: Object Representation- "+ "0000001111101" );*/
    }
    
}
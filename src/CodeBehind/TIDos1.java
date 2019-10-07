package CodeBehind;

import CodeBehind.Logs.FichierLog;
import UI.MainWindow;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.File; 
import java.io.IOException; 
import java.awt.image.BufferedImage; 
import javax.imageio.ImageIO; 
/**
 *
 * @author Eliott
 */
public class TIDos1 {
    // Java program to demonstrate read and write of image 
    private static String _path = "C:\\Users\\Eliott\\Desktop\\xxxx.jpg";
    
    public static void main(String args[])throws IOException 
    { 
        FichierLog fl = new FichierLog();
        fl.addLog("--NOUVELLE SESSION");
       
        MainWindow mw = new MainWindow();
        mw.setVisible(true);
    }//main() ends here 
    
}

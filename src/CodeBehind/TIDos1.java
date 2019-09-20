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
    private String _path = "C:\\Users\\Eliott\\Desktop\\Lena.jpg";
    
    public static void main(String args[])throws IOException 
    { 
        FichierLog fl = new FichierLog();
        fl.addLog("--NOUVELLE SESSION");
        BufferedImage img = null; 
        File f = null; 
  
        //read image 
        try
        { 
            f = new File("C:\\Users\\Eliott\\Desktop\\Lena.jpg"); 
            img = ImageIO.read(f); 
        } 
        catch(IOException e) 
        { 
            System.out.println(e); 
        } 
  
        int width = img.getWidth(); 
        int height = img.getHeight(); 
  

        int p = img.getRGB(0,0); 
        int x, y, avg, a, r, g, b;
  

        /*
        for(x = 0; x < width;x++)
        {
            for(y = 0; y < height; y++)
            {
                p = img.getRGB(x,y); 
  
                a = (p>>24)&0xff; 
                r = (p>>16)&0xff; 
                g = (p>>8)&0xff; 
                b = p&0xff; 
                
                if(b > ((r+g)/2))
                {
                    a = (int)(Math.random()*256); //generating 
                    r = (int)(Math.random()*256); //values 
                    g = (int)(Math.random()*256); //less than 
                    b = (int)(Math.random()*256); //256 
                }
                
                // calculate average 
                avg = (r+g+b)/3; 
  
                //set the pixel value 
                p = (a<<24) | (r<<16) | (g<<8) | b; 
                img.setRGB(x, y, p); 
            }
        }
  */
        
        // create BufferedImage object of same width and 
        // height as of input image 
        BufferedImage temp = new BufferedImage(img.getWidth(), 
                    img.getHeight(), BufferedImage.TYPE_INT_RGB); 
  
        // Create graphics object and add original 
        // image to it 
        Graphics graphics = temp.getGraphics(); 
        graphics.drawImage(img, 0, 0, null); 
  
        // Set font for the watermark text 
        graphics.setFont(new Font("Arial", Font.PLAIN, 50)); 
        graphics.setColor(new Color(255, 255, 255, 100)); 
  
        // Setting watermark text 
        String watermark = "Wendy hihi <3"; 
  
        // Add the watermark text at (width/5, height/3) 
        // location 
        graphics.drawString(watermark, 0, 
                                   50); 
  
        // releases any system resources that it is using 
        graphics.dispose(); 
        
        //write image 
        try
        { 
            f = new File("C:\\Users\\Eliott\\Desktop\\out.jpg"); 
            ImageIO.write(temp, "jpg", f); 
        } 
        catch(IOException e) 
        { 
            System.out.println(e); 
        } 
        
        MainWindow mw = new MainWindow();
        mw.setVisible(true);
    }//main() ends here 
    
}

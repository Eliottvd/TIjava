/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CodeBehind;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 *
 * @author Eliott
 */
public class ImgModifier {

    public ImgModifier() {
        
    }
    
    public static BufferedImage copyImage(BufferedImage source){
        BufferedImage b = new BufferedImage(source.getWidth(), source.getHeight(), source.getType());
        Graphics g = b.getGraphics();
        g.drawImage(source, 0, 0, null);
        g.dispose();
        return b;
    }
    
    public BufferedImage ToGreyscale(BufferedImage ImgIn)
    {
        BufferedImage ImgOut = copyImage(ImgIn);
        int p; 
        int x, y, avg, a, r, g, b;

        for(x = 0; x < ImgIn.getWidth();x++)
        {
            for(y = 0; y < ImgIn.getHeight() ; y++)
            {
                p = ImgIn.getRGB(x,y); //Extraire la valeur du pixel (x;y)
  
                a = (p>>24)&0xff;      //Séparer la valeur (int -> 4 bytes)
                r = (p>>16)&0xff; 
                g = (p>>8)&0xff; 
                b = p&0xff; 
                 
                avg = (r+g+b)/3;  //calculer la moyenne 
  
                //set the pixel value 
                p = (a<<24) | (avg<<16) | (avg<<8) | avg; 
                ImgOut.setRGB(x, y, p); 
            }
        }
        
        return ImgOut;
    }
    
    public BufferedImage Negative(BufferedImage ImgIn)
    {
        BufferedImage ImgOut = copyImage(ImgIn);
        int p; 
        int x, y, avg, a, r, g, b;

        for(x = 0; x < ImgIn.getWidth();x++)
        {
            for(y = 0; y < ImgIn.getHeight() ; y++)
            {
                p = ImgIn.getRGB(x,y); //Extraire la valeur du pixel (x;y)
  
                a = 255 - ((p>>24)&0xff);      //Séparer la valeur (int -> 4 bytes)
                r = 255 - ((p>>16)&0xff); 
                g = 255 - ((p>>8)&0xff); 
                b = 255 - (p&0xff); 
  
                //set the pixel value 
                p = (a<<24) | (r<<16) | (g<<8) | b; 
                ImgOut.setRGB(x, y, p); 
            }
        }
        
        return ImgOut;
    }

    public BufferedImage Treshold(BufferedImage ImgIn, int s1)
    {
        BufferedImage ImgOut = copyImage(ImgIn);
        int p; 
        int x, y, avg, a, r, g, b;

        for(x = 0; x < ImgIn.getWidth();x++)
        {
            for(y = 0; y < ImgIn.getHeight() ; y++)
            {
                p = ImgIn.getRGB(x,y); //Extraire la valeur du pixel (x;y)
  
                a = (p>>24)&0xff;      //Séparer la valeur (int -> 4 bytes)
                r = (p>>16)&0xff; 
                g = (p>>8)&0xff; 
                b = p&0xff; 
                 
                avg = (r+g+b)/3;  //calculer la moyenne 
  
                //set the pixel value 
                if(avg <s1)
                    p = (0<<24) | (0<<16) | (0<<8) | 0; 
                else
                    p = (255<<24) | (255<<16) | (255<<8) | 255; 
                ImgOut.setRGB(x, y, p); 
            }
        }
        
        return ImgOut;
    }
    
    public BufferedImage FiltreMedian(BufferedImage ImgIn)
    {
        BufferedImage ImgOut = copyImage(ImgIn);
        int p[] = new int[9]; 
        int val[] = new int[9];
        int x, y;

        for(x = 1; x < ImgIn.getWidth()-1;x++)
        {
            for(y = 1; y < ImgIn.getHeight()-1 ; y++)
            {
                p[0] = ImgIn.getRGB(x - 1, y - 1);
                p[1] = ImgIn.getRGB(x, y - 1);
                p[2] = ImgIn.getRGB(x + 1, y - 1);
                p[3] = ImgIn.getRGB(x - 1, y);
                p[4] = ImgIn.getRGB(x , y); 
                p[5] = ImgIn.getRGB(x + 1, y);
                p[6] = ImgIn.getRGB(x - 1, y + 1);
                p[7] = ImgIn.getRGB(x , y + 1);
                p[8] = ImgIn.getRGB(x + 1, y + 1);
  
                for(int k = 0 ; k < p.length ; k++)
                {
                    val[k] = p[k]&0xff;
                }

                int n = val.length;  
                int temp = 0;  
                 for(int l=0; l < n; l++){  
                         for(int m=1; m < (n-l); m++){  
                                  if(val[m-1] > val[m]){  
                                         //swap elements  
                                         temp = val[m-1];  
                                         val[m-1] = val[m];  
                                         val[m] = temp;  
                                 }  

                         }  
                 }  
                 
                 
  
                //set the pixel value 
                
                p[5] = (val[5]<<24) | (val[5]<<16) | (val[5]<<8) | val[5]; 
                ImgOut.setRGB(x, y, p[5]); 
            }
        }
        
        return ImgOut;
    }
    
    public BufferedImage FiltreMoyen(BufferedImage ImgIn)
    {
        BufferedImage ImgOut = copyImage(ImgIn);
        int p[] = new int[9]; 
        int moyenne = 0;
        int x, y;

        for(x = 1; x < ImgIn.getWidth()-1;x++)
        {
            for(y = 1; y < ImgIn.getHeight()-1 ; y++)
            {
                p[0] = ImgIn.getRGB(x - 1, y - 1);
                p[1] = ImgIn.getRGB(x, y - 1);
                p[2] = ImgIn.getRGB(x + 1, y - 1);
                p[3] = ImgIn.getRGB(x - 1, y);
                p[4] = ImgIn.getRGB(x , y); 
                p[5] = ImgIn.getRGB(x + 1, y);
                p[6] = ImgIn.getRGB(x - 1, y + 1);
                p[7] = ImgIn.getRGB(x , y + 1);
                p[8] = ImgIn.getRGB(x + 1, y + 1);
  
                for(int k = 0 ; k < p.length ; k++)
                {
                    moyenne += p[k]&0xff;
                }
                
                moyenne /= p.length;
                
                p[5] = (moyenne<<24) | (moyenne<<16) | (moyenne<<8) | moyenne; 
                ImgOut.setRGB(x, y, p[5]); 
            }
        }
        
        return ImgOut;
    }
}

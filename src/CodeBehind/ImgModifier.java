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
    
    public BufferedImage MultiTreshold(BufferedImage ImgIn, int[] tab)
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
                if(avg >= tab[0] && avg <= tab[1])
                    p = (tab[2]<<24) | (tab[2]<<16) | (tab[2]<<8) | tab[2]; 
                else if(avg >= tab[3] && avg <= tab[4])
                    p = (tab[5]<<24) | (tab[5]<<16) | (tab[5]<<8) | tab[5]; 
                else if(avg >= tab[6] && avg <= tab[7])
                    p = (tab[8]<<24) | (tab[8]<<16) | (tab[8]<<8) | tab[8]; 
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
    
    public BufferedImage FiltreGaussien(BufferedImage ImgIn, double Sigma)
    {
        BufferedImage ImgOut = copyImage(ImgIn);
        int tempSigma = (int) (((3 * Sigma)-0.01)+1);
        int tailleMatrice = ((2*tempSigma) + 1);
        double p[] = new double[tailleMatrice*tailleMatrice];
        double matCoef[] = new double[tailleMatrice*tailleMatrice];
        double matReel[] = new double[tailleMatrice*tailleMatrice];
        int x, y, ligne, col, pos;
        double totalCoef, valeurFin;
        
        System.out.println("Sigma = " + Sigma);
        System.out.println("tempSigma = " + tempSigma);
        System.out.println("tailleMatrice = " + tailleMatrice);
        System.out.println("tailleMatrice² = " + (tailleMatrice*tailleMatrice));
        
        for(x = tempSigma; x < ImgIn.getWidth()-tempSigma; x++)
        {
            for(y = tempSigma; y < ImgIn.getHeight()-tempSigma; y++)
            {
                totalCoef = 0;
                valeurFin = 0;
                for(col = 0-tempSigma; col <= tempSigma; col++)
                {
                    for(ligne = 0-tempSigma; ligne <= tempSigma; ligne++)
                    {
                        pos = col+tempSigma+((ligne+tempSigma)*tailleMatrice);
                        matCoef[pos] = (1/((2*Sigma*Sigma)*Math.PI))*Math.exp(((-col*col)+(ligne*ligne))/(2*Sigma*Sigma));
                        totalCoef += matCoef[pos];
                    } 
                }
                
                for(col = 0-tempSigma; col <= tempSigma; col++)
                {
                    for(ligne = 0-tempSigma; ligne <= tempSigma; ligne++)
                    {
                        pos = col+tempSigma+((ligne+tempSigma)*tailleMatrice);
                        p[pos] = ImgIn.getRGB(x+col, y+ligne)&0xff; 
                        matReel[pos] = matCoef[pos] / totalCoef;
                        valeurFin += p[pos] * matReel[pos];
                    }  
                }
                
                p[tailleMatrice] = ((int)valeurFin<<24) | ((int)valeurFin<<16) | ((int)valeurFin<<8) | (int)valeurFin; 
                ImgOut.setRGB(x, y, (int)p[tailleMatrice]);
            }
        }
        
        return ImgOut;
    }
    
    public BufferedImage FiltreDeKirsh(BufferedImage ImgIn)
    {
        BufferedImage ImgOut = copyImage(ImgIn);
        int p[] = new int[9]; 
        int res = 0;
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
                    res += p[k]&0xff;
                }
                
                res /= p.length;
                
                p[5] = (res<<24) | (res<<16) | (res<<8) | res; 
                ImgOut.setRGB(x, y, p[5]); 
            }
        }
        
        return ImgOut;
    }
    
    public BufferedImage FiltreDePrewitt(BufferedImage ImgIn)
    {
        BufferedImage ImgOut = copyImage(ImgIn);
        int p[] = new int[9]; 
        int res = 0;
        int res2 = 0;
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
                    p[k] = p[k]&0xff;
                }
                
                res = -p[0] + p[2] - p[3] + p[5] - p[6] + p[8];
                res += 128;
                if(res < 0)
                    res = 0;
                
                res = (res<<24) | (res<<16) | (res<<8) | res; 
                
                res2 = -p[0] + p[6] - p[1] + p[7] - p[2] + p[8];
                res2 += 128;
                if(res2 < 0)
                    res2 = 0;
                
                res = res | res2;
                ImgOut.setRGB(x, y, res2); 
            }
        }
        
        return ImgOut;
    }
    
    public BufferedImage FiltreLaplacien(BufferedImage ImgIn)
    {
        BufferedImage ImgOut = copyImage(ImgIn);
        int p[] = new int[9]; 
        int res = 0;
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
                    p[k] = p[k]&0xff;
                }
                
                res = p[1] + p[3] - (4*p[4]) + p[5] + p[7];
                res += 128;
                if(res < 0)
                    res = 0;
                
                p[4] = (res<<24) | (res<<16) | (res<<8) | res; 
                ImgOut.setRGB(x, y, p[4]); 
            }
        }
        
        return ImgOut;
    }
}

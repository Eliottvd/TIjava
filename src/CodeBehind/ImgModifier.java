/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CodeBehind;

import UI.MainWindow;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.filechooser.FileNameExtensionFilter;

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
        
        int[] P=new int[9];
        int[] R=new int[9];
        int[] B=new int[9];
        int[] G=new int[9];
        
        int sumR = 0, sumB = 0, sumG = 0, avgR, avgB, avgG;
        int pixel;
        
        int width = ImgOut.getWidth(); 
        int height = ImgOut.getHeight(); 
  
        for (int i = 0; i < width; i++) 
        { 
            for (int j = 0; j < height; j++) 
            { 
               sumB = 0;
               sumR = 0;
               sumG = 0;
                
               try
               {
                     P[0]=ImgOut.getRGB(i-1,j-1);
                     P[1]=ImgOut.getRGB(i-1,j);
                     P[2]=ImgOut.getRGB(i-1,j+1);
                     P[3]=ImgOut.getRGB(i,j+1);
                     P[4]=ImgOut.getRGB(i+1,j+1);
                     P[5]=ImgOut.getRGB(i+1,j);
                     P[6]=ImgOut.getRGB(i+1,j-1);
                     P[7]=ImgOut.getRGB(i,j-1);
                     P[8]=ImgOut.getRGB(i,j);
               }
               catch(IndexOutOfBoundsException e)
               {
                     P[0]=0;
                     P[1]=0;
                     P[2]=0;
                     P[3]=0;
                     P[4]=0;
                     P[5]=0;
                     P[6]=0;
                     P[7]=0;
                     P[8]=ImgOut.getRGB(i,j);   
               }

               for(int k=0;k<9;k++)
               {                   
                   R[k] = (P[k]>>16)&0xff;
                   B[k] = (P[k]>>8)&0xff; 
                   G[k] = P[k]&0xff; 
               }
               
               for(int k=0;k<9;k++)
               {                   
                   sumR += R[k];
                   sumB += B[k];
                   sumG += G[k];
               }
               
               avgR = sumR/R.length;
               avgB = sumB/B.length;
               avgG = sumG/G.length;
               
               pixel = (255<<24) | (avgR<<16) | (avgB<<8) | avgG; 
               
               ImgOut.setRGB(i, j, pixel); 
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
    
    public BufferedImage GaussianFilter(BufferedImage srcImg) {

        BufferedImage finalImg = new BufferedImage(srcImg.getWidth(), srcImg.getHeight(), BufferedImage.TYPE_INT_RGB);
        Color[] pixel = new Color[9];
        int[] filter = {1, 2, 1, 
                        2, 4, 2, 
                        1, 2, 1};
        
        int R, G, B, sum = 0;
        
        for(int i=0; i<filter.length; i++)
            sum += filter[i];
        
        for (int i = 1; i < srcImg.getWidth() - 1; i++) {
            for (int j = 1; j < srcImg.getHeight() - 1; j++) {
                R = 0;
                B = 0;
                G = 0;
                pixel[0] = new Color(srcImg.getRGB(i - 1, j - 1));
                pixel[1] = new Color(srcImg.getRGB(i - 1, j));
                pixel[2] = new Color(srcImg.getRGB(i - 1, j + 1));
                pixel[3] = new Color(srcImg.getRGB(i, j + 1));
                pixel[4] = new Color(srcImg.getRGB(i + 1, j + 1));
                pixel[5] = new Color(srcImg.getRGB(i + 1, j));
                pixel[6] = new Color(srcImg.getRGB(i + 1, j - 1));
                pixel[7] = new Color(srcImg.getRGB(i, j - 1));
                pixel[8] = new Color(srcImg.getRGB(i, j));
                for (int k = 0; k < 9; k++) {
                    R += pixel[k].getRed() * filter[k];
                    B += pixel[k].getBlue() * filter[k];
                    G += pixel[k].getGreen() * filter[k];
                }
                finalImg.setRGB(i, j, new Color(R / sum, G / sum, B / sum).getRGB());
            }
        }
        return finalImg;
    }
    
    public BufferedImage LaplacianFilter(BufferedImage srcImg) {

        BufferedImage finalImg = new BufferedImage(srcImg.getWidth(), srcImg.getHeight(), BufferedImage.TYPE_INT_RGB);
        Color[] pixel = new Color[9];
        int[] filter = {-1, -1, -1, 
                        -1, 8, -1, 
                        -1, -1, -1};
        
        int R, G, B;
        
        for (int i = 1; i < srcImg.getWidth() - 1; i++) {
            for (int j = 1; j < srcImg.getHeight() - 1; j++) {
                R = 0;
                B = 0;
                G = 0;
                pixel[0] = new Color(srcImg.getRGB(i - 1, j - 1));
                pixel[1] = new Color(srcImg.getRGB(i - 1, j));
                pixel[2] = new Color(srcImg.getRGB(i - 1, j + 1));
                pixel[3] = new Color(srcImg.getRGB(i, j + 1));
                pixel[4] = new Color(srcImg.getRGB(i + 1, j + 1));
                pixel[5] = new Color(srcImg.getRGB(i + 1, j));
                pixel[6] = new Color(srcImg.getRGB(i + 1, j - 1));
                pixel[7] = new Color(srcImg.getRGB(i, j - 1));
                pixel[8] = new Color(srcImg.getRGB(i, j));
                for (int k = 0; k < 9; k++) {
                    R += pixel[k].getRed() * filter[k];
                    B += pixel[k].getBlue() * filter[k];
                    G += pixel[k].getGreen() * filter[k];
                }
                R = R<0 ? R=0 : (R>255 ? R=255 : R); // If R<0 -> R=0
                B = B<0 ? B=0 : (B>255 ? B=255 : B); // If R>255 -> R=255
                G = G<0 ? G=0 : (G>255 ? G=255 : G);
                
                finalImg.setRGB(i, j, new Color(R,G,B).getRGB());
            }
        }
        return finalImg;
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
                res /= 3;
                if(res < 0)
                    res = 0;
                
                res = (res<<24) | (res<<16) | (res<<8) | res; 
                
                res2 = -p[0] + p[6] - p[1] + p[7] - p[2] + p[8];
                res2 /= 3;
                
                if(res2 < 0)
                    res2 = 0;
                
                res = (res<<24) | (res<<16) | (res<<8) | res;
                res2 = (res2<<24) | (res2<<16) | (res2<<8) | res2;
                res = res | res2;
                
                ImgOut.setRGB(x, y, res); 
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
    
    public BufferedImage ColorChange(BufferedImage ImgIn, int RGB)
    {
        BufferedImage ImgOut = copyImage(ImgIn);
        int pixelCourant;
        int x, y;
        int r, g, b, a;
        int aModif, rModif, gModif, bModif;

        aModif = (RGB>>24)&0xff;
        rModif = (RGB>>16)&0xff; 
        gModif = (RGB>>8)&0xff; 
        bModif = RGB&0xff;
        
        for(x = 0; x < ImgIn.getWidth();x++)
        {
            for(y = 0; y < ImgIn.getHeight() ; y++)
            {
                pixelCourant = ImgIn.getRGB(x, y);
               
                a = (pixelCourant>>24)&0xff;      
                r = (pixelCourant>>16)&0xff; 
                g = (pixelCourant>>8)&0xff; 
                b = pixelCourant&0xff; 
                
                a += aModif;
                if(a > 255)
                    a = 255;
                if(a < 0)
                    a = 0;
                
                r += rModif;
                if(r > 255)
                    r = 255;
                if(r < 0)
                    r = 0;
                
                g += gModif;
                if(g > 255)
                    g = 255;
                if(g < 0)
                    g = 0;
                
                b += bModif;
                if(b > 255)
                    b = 255;
                if(b < 0)
                    b = 0;
                
                
                pixelCourant = (a<<24) | (r<<16) | (g<<8) | b; 
                ImgOut.setRGB(x, y, pixelCourant); 
            }
        }
        
        return ImgOut;
    }
    
    public BufferedImage Zoom(BufferedImage ImgIn, String coefString)
    {
        BufferedImage ImgOut = copyImage(ImgIn);
        if(!(coefString.equals("")))
        {
            float coefFloat = Float.parseFloat(coefString);
        
            Image dimg = ImgOut.getScaledInstance((int)(ImgOut.getWidth()*coefFloat), 
                                                  (int)(ImgOut.getHeight()*coefFloat), 
                                                   Image.SCALE_SMOOTH);
            
            ImgOut = toBufferedImage(dimg);
        }
        return ImgOut;
    }
    
    public static BufferedImage toBufferedImage(Image img)
    {
        if (img instanceof BufferedImage)
        {
            return (BufferedImage) img;
        }

        // Create a buffered image with transparency
        BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        // Draw the image on to the buffered image
        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();

        // Return the buffered image
        return bimage;
    }
    
    public int SaveImage(BufferedImage ImgIn, JFrame mw)
    {
        JFileChooser fileChooser = new JFileChooser();
        
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("jpg", "jpg"));
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("png", "png"));
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("bmp", "bmp"));
        
        fileChooser.setAcceptAllFileFilterUsed(false); // On empêche à l'utilsateur de choisir d'autres types,é vite de devoir check par après
        fileChooser.setDialogTitle("Specify a file to save");
        fileChooser.setCurrentDirectory(new File("C:\\Users\\Eliott\\Desktop"));
        File fileToSave = null;

        int userSelection = fileChooser.showSaveDialog(mw);

        if (userSelection == JFileChooser.APPROVE_OPTION) 
        {
            fileToSave = getSelectedFileWithExtension(fileChooser);
            System.out.println("Save as file: " + fileToSave.getAbsolutePath());
        }
        

        BufferedImage bi = copyImage(ImgIn);  // retrieve image
        //File outputfile = new File("saved.png");
        try {
            ImageIO.write(bi, "png", fileToSave);
        } catch (IOException ex) {
            System.out.println("Erreur d'écriture");
            return 0;
        }

        return 1;
    }
    
    public static File getSelectedFileWithExtension(JFileChooser c) {
        File file = c.getSelectedFile();
        if (c.getFileFilter() instanceof FileNameExtensionFilter) {
            String[] exts = ((FileNameExtensionFilter)c.getFileFilter()).getExtensions();
            String nameLower = file.getName().toLowerCase();
            for (String ext : exts) { 
                if (nameLower.endsWith('.' + ext.toLowerCase())) {
                    return file;
                }
            }

            file = new File(file.toString() + '.' + exts[0]);
        }
        return file;
    }
    
    public BufferedImage Histogramme(BufferedImage imgIn, int w, int h)
    {
        int[] values = new int[256];
        int highValue = 0;
        BufferedImage img = copyImage(imgIn);
        
        // Get pixel number of same color
        // On parcourt séquentiellement toute l'image. Le tableau values retiens le nombre de pixels pour chaque nuance de gris.
        // On a donc un tableau contenant les données de notre futur histogramme. On crée un carré jaune qui sera le fond puis pour on 
        // parcourt le tableau values et dessinant un ligne verticale proportionnelle au nopbre de pixels pour chaque nuance de gris 
        
        for (int i = 0; i < img.getWidth(); i++) {
            for (int j = 0; j < img.getHeight(); j++) {
                //System.out.println((img.getRGB(i, j) & 0x000000FF));
                values[img.getRGB(i, j) & 0xFF]++; // Add 1 to that color in values
            }
        }
        
        // Get color with most pixels
        for(int i = 0; i < values.length; i++)
        {           
            if(values[i] > values[highValue])
                highValue = i;
        }
        //jLblHV.setText(String.valueOf(values[highValue]));
        
        BufferedImage histo = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = histo.createGraphics();
        
        g2d.setColor(new Color(255,255,51));
        g2d.fillRect(0, 0, histo.getWidth(), histo.getHeight());
        for(int i = 0; i < values.length; i++)
        {
            g2d.setColor(new Color(i, i, i));
            g2d.drawLine(i, histo.getHeight(), i, histo.getHeight() - (int) ((double)values[i]/values[highValue]*histo.getHeight()));
        }
        g2d.dispose();
        
        return histo;
    }
    
    private static int get(int self, int n) {
        return (self >> (n * 8)) & 0xFF;
    }
 
    private static float lerp(float s, float e, float t) {
        return s + (e - s) * t;
    }
 
    private static float blerp(final Float c00, float c10, float c01, float c11, float tx, float ty) {
        return lerp(lerp(c00, c10, tx), lerp(c01, c11, tx), ty);
    }
 
    public static BufferedImage Expansion(BufferedImage self, float scaleX, float scaleY) {
        int newWidth = (int) (self.getWidth() * scaleX);
        int newHeight = (int) (self.getHeight() * scaleY);

        BufferedImage newImage = new BufferedImage(newWidth, newHeight, self.getType());
        
        for (int x = 0; x < newWidth; ++x) 
        {
            for (int y = 0; y < newHeight; ++y) 
            {
                float gx = ((float) x) / newWidth * (self.getWidth() - 1);
                float gy = ((float) y) / newHeight * (self.getHeight() - 1);
                int gxi = (int) gx;
                int gyi = (int) gy;
                int rgb = 0;
                int c00 = self.getRGB(gxi, gyi);
                int c10 = self.getRGB(gxi + 1, gyi);
                int c01 = self.getRGB(gxi, gyi + 1);
                int c11 = self.getRGB(gxi + 1, gyi + 1);
                for (int i = 0; i <= 2; ++i) 
                {
                    float b00 = get(c00, i);
                    float b10 = get(c10, i);
                    float b01 = get(c01, i);
                    float b11 = get(c11, i);
                    int ble = ((int) blerp(b00, b10, b01, b11, gx - gxi, gy - gyi)) << (8 * i);
                    rgb = rgb | ble;
                }
                newImage.setRGB(x, y, rgb);
                
            }
        }
        return newImage;
    }
    
     public BufferedImage Erosion(BufferedImage imgIn, int val){
        BufferedImage ImgOut;

        if(imgIn != null)
        {
            ImgOut = copyImage(imgIn);
        }

        ImgOut = this.Treshold(imgIn, val);

        int[] P = new int[9]; // Pixel
        int[] Avg = new int[9];

        int r, g, b;
        int max = 0; // de base on part de 0
        int pixel;

        int width = ImgOut.getWidth(); 
        int height = ImgOut.getHeight(); 

        for (int i = 0; i < width; i++) 
        { 
            for (int j = 0; j < height; j++) 
            { 
               max = 0;
               try
               {
                     P[0]=ImgOut.getRGB(i-1,j-1);
                     P[1]=ImgOut.getRGB(i-1,j);
                     P[2]=ImgOut.getRGB(i-1,j+1);
                     P[3]=ImgOut.getRGB(i,j+1);
                     P[4]=ImgOut.getRGB(i+1,j+1);
                     P[5]=ImgOut.getRGB(i+1,j);
                     P[6]=ImgOut.getRGB(i+1,j-1);
                     P[7]=ImgOut.getRGB(i,j-1);
                     P[8]=ImgOut.getRGB(i,j);
               }
               catch(IndexOutOfBoundsException e)
               {
                     P[0]=0;
                     P[1]=0;
                     P[2]=0;
                     P[3]=0;
                     P[4]=0;
                     P[5]=0;
                     P[6]=0;
                     P[7]=0;
                     P[8]=ImgOut.getRGB(i,j);   
               }

               for(int k=0; k<9; k++)
               {                   
                   Avg[k] = (P[k]>>16)&0xff; // Les 3 pixels ont la même valeur car on travaille en seuillé
               }


               for(int k = 0; k < 9; k++)
               {
                   if(Avg[k] == 255 && k != 4)
                   {
                       max = 255;
                       break;
                   }
               }

               if(max == 255)
               {
                   pixel = (255<<24) | (255<<16) | (255<<8) | 255; 
               }
               else
               {
                   pixel = (255<<24) | (0<<16) | (0<<8) | 0; 
               }

                ImgOut.setRGB(i, j, pixel);
            }   
        }


        return ImgOut;
    }
     
      public BufferedImage Dilatation(int val, BufferedImage ImgIn)
    {
        BufferedImage ImgOut;
        
        if(ImgIn != null)
        {
            ImgOut = copyImage(ImgIn);
        }
        
        ImgOut = Treshold(ImgIn, val);
        
        int[] P = new int[9]; // Pixel
        int[] Avg = new int[9];
        
        int r, g, b;
        int min = 255; // de base on part de 255
        int pixel;
        
        int width = ImgOut.getWidth(); 
        int height = ImgOut.getHeight(); 
  
        for (int i = 0; i < width; i++) 
        { 
            for (int j = 0; j < height; j++) 
            { 
               min = 255;
               try
               {
                     P[0]=ImgOut.getRGB(i-1,j-1);
                     P[1]=ImgOut.getRGB(i-1,j);
                     P[2]=ImgOut.getRGB(i-1,j+1);
                     P[3]=ImgOut.getRGB(i,j+1);
                     P[4]=ImgOut.getRGB(i+1,j+1);
                     P[5]=ImgOut.getRGB(i+1,j);
                     P[6]=ImgOut.getRGB(i+1,j-1);
                     P[7]=ImgOut.getRGB(i,j-1);
                     P[8]=ImgOut.getRGB(i,j);
               }
               catch(IndexOutOfBoundsException e)
               {
                     P[0]=0;
                     P[1]=0;
                     P[2]=0;
                     P[3]=0;
                     P[4]=0;
                     P[5]=0;
                     P[6]=0;
                     P[7]=0;
                     P[8]=ImgOut.getRGB(i,j);   
               }

               for(int k=0; k<9; k++)
               {                   
                   Avg[k] = (P[k]>>16)&0xff; // Les 3 pixels ont la même valeur car on travaille en seuillé
               }

               
               for(int k = 0; k < 9; k++)
               {
                   if(Avg[k] == 0 && k != 4)
                   {
                       min = 0;
                       break;
                   }
               }
               
               if(min == 255)
               {
                   pixel = (255<<24) | (255<<16) | (255<<8) | 255; 
               }
               else
               {
                   pixel = (255<<24) | (0<<16) | (0<<8) | 0; 
               }
                
                ImgOut.setRGB(i, j, pixel);
            }   
        }
        
        return ImgOut;
    }
        
     
    public BufferedImage equalize(BufferedImage src){
        BufferedImage nImg = new BufferedImage(src.getWidth(), src.getHeight(),
                             BufferedImage.TYPE_BYTE_GRAY);
        WritableRaster wr = src.getRaster();
        WritableRaster er = nImg.getRaster();
        int totpix= wr.getWidth()*wr.getHeight();
        int[] histogram = new int[256];

        for (int x = 0; x < wr.getWidth(); x++) { //Calcul histogramme
            for (int y = 0; y < wr.getHeight(); y++) {
                histogram[wr.getSample(x, y, 0)]++;
            }
        }

        int[] chistogram = new int[256];      
        chistogram[0] = histogram[0];
        for(int i=1;i<256;i++){                     //Calcul histogramme cumulé
            chistogram[i] = chistogram[i-1] + histogram[i];
        }

        float[] arr = new float[256];
        for(int i=0;i<256;i++){                     //indice = pixel,  valeur = nouvelle valeur du pixel
            arr[i] =  (float)((chistogram[i]*255.0)/(float)totpix);
        }

        for (int x = 0; x < wr.getWidth(); x++) {   // On remplace les pixels de l'image 
            for (int y = 0; y < wr.getHeight(); y++) {
                int nVal = (int) arr[wr.getSample(x, y, 0)];
                er.setSample(x, y, 0, nVal);
            }
        }
        nImg.setData(er);
        return nImg;
    }
    
    public BufferedImage FiltreDeSobel(BufferedImage ImgIn)
    {
        BufferedImage ImgOut = copyImage(ImgIn);
        
        int[] P = new int[9]; // Pixel
        int[] Avg = new int[9];
        
        int r, g, b;
        
        
        int width = ImgOut.getWidth(); 
        int height = ImgOut.getHeight(); 
  
        for (int i = 0; i < width; i++) 
        { 
            for (int j = 0; j < height; j++) 
            { 
               try
               {
                     P[0]=ImgOut.getRGB(i-1,j-1);
                     P[1]=ImgOut.getRGB(i-1,j);
                     P[2]=ImgOut.getRGB(i-1,j+1);
                     P[3]=ImgOut.getRGB(i,j+1);
                     P[4]=ImgOut.getRGB(i+1,j+1);
                     P[5]=ImgOut.getRGB(i+1,j);
                     P[6]=ImgOut.getRGB(i+1,j-1);
                     P[7]=ImgOut.getRGB(i,j-1);
                     P[8]=ImgOut.getRGB(i,j);
               }
               catch(IndexOutOfBoundsException e)
               {
                     P[0]=0;
                     P[1]=0;
                     P[2]=0;
                     P[3]=0;
                     P[4]=0;
                     P[5]=0;
                     P[6]=0;
                     P[7]=0;
                     P[8]=ImgOut.getRGB(i,j);   
               }

               for(int k=0; k<9; k++)
               {                   
                   r = (P[k]>>16)&0xff;
                   b = (P[k]>>8)&0xff; 
                   g = P[k]&0xff;
                   
                   Avg[k] = (r+b+g)/3;
               }

               
               int res = -Avg[0] + Avg[2] -2*Avg[3] + 2*Avg[5] - Avg[6] + Avg[8];
                res /= 4;
                res = Math.abs(res);
                
                int res2 = -Avg[0]  -2*Avg[1] - Avg[2] + Avg[6] + 2*Avg[7] + Avg[8];
                res2 /= 4;
                res2 = Math.abs(res2);
                
                res = (res<<24) | (res<<16) | (res<<8) | res;
                res2 = (res2<<24) | (res2<<16) | (res2<<8) | res2;
                res = res | res2;
                
                ImgOut.setRGB(i, j, res); 
            }   
        }
        return ImgOut;
    }
    
    
    public BufferedImage KirschFilter(String centre, BufferedImage ImgIn)
    {
        BufferedImage ImgOut = copyImage(ImgIn);
        
        int[] P = new int[9]; // Pixel
        int[] Avg = new int[9];
        
        int r, g, b;
        
        int pixel;
        int max = 0;
        
        int width = ImgOut.getWidth(); 
        int height = ImgOut.getHeight(); 
  
        for (int i = 0; i < width; i++) 
        { 
            for (int j = 0; j < height; j++) 
            { 
               try
               {
                     P[0]=ImgOut.getRGB(i-1,j-1);
                     P[1]=ImgOut.getRGB(i-1,j);
                     P[2]=ImgOut.getRGB(i-1,j+1);
                     P[3]=ImgOut.getRGB(i,j+1);
                     P[4]=ImgOut.getRGB(i+1,j+1);
                     P[5]=ImgOut.getRGB(i+1,j);
                     P[6]=ImgOut.getRGB(i+1,j-1);
                     P[7]=ImgOut.getRGB(i,j-1);
                     P[8]=ImgOut.getRGB(i,j);
               }
               catch(IndexOutOfBoundsException e)
               {
                     P[0]=0;
                     P[1]=0;
                     P[2]=0;
                     P[3]=0;
                     P[4]=0;
                     P[5]=0;
                     P[6]=0;
                     P[7]=0;
                     P[8]=ImgOut.getRGB(i,j);   
               }

               for(int k=0; k<9; k++)
               {                   
                   r = (P[k]>>16)&0xff;
                   b = (P[k]>>8)&0xff; 
                   g = P[k]&0xff;
                   
                   Avg[k] = (r+b+g)/3;
               }

               int[] res = new int[8];
               // Reste à calculer res pour les HUIT matrices et prendre le résultat le plus grand
               
                res[0] = 3*Avg[0] + 3*Avg[1] + 3*Avg[2] + 3*Avg[3] + 3*Avg[5] - 5*Avg[6] - 5*Avg[7] - 5*Avg[8];
                res[0] = Math.abs(res[0]);

                res[1] = 3*Avg[0] + 3*Avg[1] + 3*Avg[2] - 5*Avg[3] + 3*Avg[5] - 5*Avg[6] - 5*Avg[7] + 3*Avg[8];
                res[1] = Math.abs(res[1]);
                
                res[2] = -5*Avg[0] + 3*Avg[1] + 3*Avg[2] - 5*Avg[3] + 3*Avg[5] - 5*Avg[6] + 3*Avg[7] + 3*Avg[8];
                res[2] = Math.abs(res[2]);
                
                res[3] = -5*Avg[0] - 5*Avg[1] + 3*Avg[2] - 5*Avg[3] + 3*Avg[5] + 3*Avg[6] + 3*Avg[7] + 3*Avg[8];
                res[3] = Math.abs(res[3]);
                
                res[4] = -5*Avg[0] - 5*Avg[1] - 5*Avg[2] + 3*Avg[3] + 3*Avg[5] + 3*Avg[6] + 3*Avg[7] + 3*Avg[8];
                res[4] = Math.abs(res[4]);
                
                res[5] = 3*Avg[0] - 5*Avg[1] - 5*Avg[2] + 3*Avg[3] - 5*Avg[5] + 3*Avg[6] + 3*Avg[7] + 3*Avg[8];
                res[5] = Math.abs(res[5]);
                
                res[6] = 3*Avg[0] + 3*Avg[1] - 5*Avg[2] + 3*Avg[3] - 5*Avg[5] + 3*Avg[6] + 3*Avg[7] - 5*Avg[8];
                res[6] = Math.abs(res[6]);
                
                res[7] = 3*Avg[0] + 3*Avg[1] + 3*Avg[2] + 3*Avg[3] - 5*Avg[5] + 3*Avg[6] - 5*Avg[7] - 5*Avg[8];
                res[7] = Math.abs(res[7]);
                
                switch(centre)
                {
                    case "NO" : max = res[0];
                                break;
                    case "N" :  max = res[1];
                                break;
                    case "NE" : max = res[2];
                                break;
                    case "E" :  max = res[4];
                                break;
                    case "SE" : max = res[7];
                                break;
                    case "S" :  max = res[6];
                                break;
                    case "SO" : max = res[5];
                                break;
                    case "O" :  max = res[3];
                                break;
                    case "Auto" :   max = res[0];
                                    for(int k = 1; k<8; k++)
                                    {
                                        if(res[k] > max)
                                        {
                                            max = res[k];
                                        }
                                    }
                                    break;                
                }
                      
                pixel = (max<<24) | (max<<16) | (max<<8) | max;

                ImgOut.setRGB(i, j, pixel); 
            }   
        }

        return ImgOut;
    }
    
    
    public BufferedImage RobertCrossFilter(String centre, BufferedImage ImgIn)
    {
        BufferedImage ImgOut = copyImage(ImgIn);
        
        
        int r, g, b;
        
        int[] P = new int[4]; // Pixel
        int[] Avg = new int[4];
        
        int width = ImgOut.getWidth(); 
        int height = ImgOut.getHeight(); 
  
        for (int i = 0; i < width; i++) 
        { 
            for (int j = 0; j < height; j++) 
            { 
               try
               {
                    switch(centre)
                    {
                        case "NO" : P[0]=ImgOut.getRGB(i, j);
                                    P[1]=ImgOut.getRGB(i+1, j);
                                    P[2]=ImgOut.getRGB(i, j+1);
                                    P[3]=ImgOut.getRGB(i+1, j+1);
                                    break;
                        case "NE" : P[0]=ImgOut.getRGB(i-1, j);
                                    P[1]=ImgOut.getRGB(i, j);
                                    P[2]=ImgOut.getRGB(i-1, j+1);
                                    P[3]=ImgOut.getRGB(i, j+1);
                                    break;
                        case "SO" : P[0]=ImgOut.getRGB(i, j-1);
                                    P[1]=ImgOut.getRGB(i+1, j-1);
                                    P[2]=ImgOut.getRGB(i, j);
                                    P[3]=ImgOut.getRGB(i+1, j);
                                    break;
                        case "SE" : P[0]=ImgOut.getRGB(i-1, j-1);
                                    P[1]=ImgOut.getRGB(i, j-1);
                                    P[2]=ImgOut.getRGB(i-1, j);
                                    P[3]=ImgOut.getRGB(i, j);
                                    break;
                    }
               }
               catch(IndexOutOfBoundsException e)
               {
                     P[0]=0;
                     P[1]=0;
                     P[2]=0;
                     P[3]=0; 
               }

               for(int k=0; k<4; k++)
               {                   
                   r = (P[k]>>16)&0xff;
                   b = (P[k]>>8)&0xff; 
                   g = P[k]&0xff;
                   
                   Avg[k] = (r+b+g)/3;
               }

               
               int res = Avg[0] - Avg[3];
               res = Math.abs(res);
                
               res = (res<<24) | (res<<16) | (res<<8) | res; 
                
               int res2 = Avg[1] - Avg[2];
               res2 = Math.abs(res2);
                
               res = (res<<24) | (res<<16) | (res<<8) | res;
               res2 = (res2<<24) | (res2<<16) | (res2<<8) | res2;
               //res = res | res2;
                
               ImgOut.setRGB(i, j, res); 
            }   
        }

        return ImgOut;
    }
    
    
    public BufferedImage QuadtreeT(BufferedImage ImgIn)
    {
        System.out.println("Dans img modifier");
        return QuadTreeImageRepresent.imageQTreerepresentation(copyImage(ImgIn),4, true);
    }
}

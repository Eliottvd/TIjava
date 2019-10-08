/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CodeBehind;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.util.ArrayList;
import javax.swing.ImageIcon;

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
    
    
    public BufferedImage Histogramme(BufferedImage imgIn, int w, int h)
    {
        int[] values = new int[256];
        int highValue = 0;
        BufferedImage img = copyImage(imgIn);
        
        // Get pixel number of same color
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
                for (int i = 0; i <= 2; ++i) {
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
    
     public BufferedImage Erosion(BufferedImage imgIn, int mask[], int maskSize){
        /**
         * Dimension of the image img.
         */
        BufferedImage imgOut = copyImage(imgIn);
        int width = imgIn.getWidth();
        int height = imgIn.getHeight();
        
        //buff
        int buff[];
        
        //output of erosion
        int output[] = new int[width*height];
        
        //perform erosion
        for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++){
                buff = new int[maskSize * maskSize];
                int i = 0;
                for(int ty = y - maskSize/2, mr = 0; ty <= y + maskSize/2; ty++, mr++){
                   for(int tx = x - maskSize/2, mc = 0; tx <= x + maskSize/2; tx++, mc++){
                       /**
                        * Sample 3x3 mask [kernel or structuring element]
                        * [0, 1, 0
                        *  1, 1, 1
                        *  0, 1, 0]
                        * 
                        * Only those pixels of the image img that are under the mask element 1 are considered.
                        */
                       if(ty >= 0 && ty < height && tx >= 0 && tx < width){
                           //pixel under the mask
                           
                           if(mask[mc+mr*maskSize] != 1){
                               continue;
                           }
                           
                           buff[i] = (imgIn.getRGB(tx, ty)>>16)&0xff ;
                           i++;
                       }
                   }
                }
                
                //sort buff
                java.util.Arrays.sort(buff);
                
                //save lowest value
                output[x+y*width] = buff[(maskSize*maskSize) - i];
            }
        }
        
        /**
         * Save the erosion value in image img.
         */
        for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++){
                int v = output[x+y*width];
                v = (255<<24) | (v<<16) | (v<<8) | v;
                imgOut.setRGB(x, y, v);
            }
        }
        return imgOut;
    }
     
    public BufferedImage equalize(BufferedImage src){
        BufferedImage nImg = new BufferedImage(src.getWidth(), src.getHeight(),
                             BufferedImage.TYPE_BYTE_GRAY);
        WritableRaster wr = src.getRaster();
        WritableRaster er = nImg.getRaster();
        int totpix= wr.getWidth()*wr.getHeight();
        int[] histogram = new int[256];

        for (int x = 0; x < wr.getWidth(); x++) {
            for (int y = 0; y < wr.getHeight(); y++) {
                histogram[wr.getSample(x, y, 0)]++;
            }
        }

        int[] chistogram = new int[256];
        chistogram[0] = histogram[0];
        for(int i=1;i<256;i++){
            chistogram[i] = chistogram[i-1] + histogram[i];
        }

        float[] arr = new float[256];
        for(int i=0;i<256;i++){
            arr[i] =  (float)((chistogram[i]*255.0)/(float)totpix);
        }

        for (int x = 0; x < wr.getWidth(); x++) {
            for (int y = 0; y < wr.getHeight(); y++) {
                int nVal = (int) arr[wr.getSample(x, y, 0)];
                er.setSample(x, y, 0, nVal);
            }
        }
        nImg.setData(er);
        return nImg;
    }
}

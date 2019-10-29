/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CodeBehind;

import java.awt.Point;
import java.awt.image.BufferedImage;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Eliott
 */
public class QuadTreeImageRepresent {

    public static BufferedImage imageQTreerepresentation(BufferedImage originalImage, int decomposeSizethreshold, boolean isBoundary ) 
    {
        
        StringBuilder treeRepString   = new StringBuilder();
        StringBuilder objectRepString = new StringBuilder();
        
        BufferedImage grayImage       = ImageIo.toGray(originalImage);
        
        byte[][] grayImageArray       = ImageIo.getByteImageArray2DFromBufferedImage(grayImage);
        byte[][] grayImageArray2      = ImageIo.getByteImageArray2DFromBufferedImage(grayImage);
        
        byte[][] binaryImageArray     = ImageIo.threshold(grayImageArray, 128);
        byte[][] boundaryImageArray   = ImageIo.threshold(grayImageArray2, 128);
        
        Point topLeftLocation = new Point(0, 0);
        int currentSize = grayImage.getWidth();
        if (decomposeSizethreshold < 4)
            decomposeSizethreshold =4;
        
        decomposeQTree(binaryImageArray, boundaryImageArray, topLeftLocation, treeRepString, objectRepString, currentSize, decomposeSizethreshold, isBoundary);
        
        BufferedImage qtreeDecomposedImage = ImageIo.setByteImageArray2DToBufferedImage(boundaryImageArray, currentSize, currentSize);
        ImageIo.writeImage(qtreeDecomposedImage, "jpg", "qtreeDecomposed" + ".jpg");
        return qtreeDecomposedImage;

    }

    public static void decomposeQTree(byte[][] binaryImageArray, byte[][] boundaryImageArray, Point topLeftLocation, StringBuilder treeRepString, StringBuilder objectRepString, int currentSize, int decomposeSizethreshold, boolean isBoundary) 
    {

        if( !binaryPredicate(binaryImageArray,topLeftLocation,currentSize) && currentSize > decomposeSizethreshold)
        {
            //Split
            treeRepString.append("0");
            currentSize /=2;
            decomposeQTree(binaryImageArray,boundaryImageArray, new Point(topLeftLocation),treeRepString, objectRepString,currentSize,decomposeSizethreshold, isBoundary);
            decomposeQTree(binaryImageArray,boundaryImageArray, new Point(topLeftLocation.x + currentSize, topLeftLocation.y),treeRepString, objectRepString,currentSize,decomposeSizethreshold, isBoundary);
            decomposeQTree(binaryImageArray,boundaryImageArray, new Point(topLeftLocation.x + currentSize, topLeftLocation.y + currentSize),treeRepString, objectRepString,currentSize,decomposeSizethreshold, isBoundary);
            decomposeQTree(binaryImageArray,boundaryImageArray, new Point(topLeftLocation.x, topLeftLocation.y + currentSize),treeRepString, objectRepString,currentSize,decomposeSizethreshold, isBoundary);
        }
        else
        {
            treeRepString.append("1");
            objectRepString.append(binaryColor(binaryImageArray,topLeftLocation,currentSize));
            delineateBoundary(boundaryImageArray,topLeftLocation,currentSize);
        }  
    }
    
        public static boolean binaryPredicate(byte[][] binaryImageArray, Point topLeftLocation,int currentSize) 
        {
            byte startValue = binaryImageArray[topLeftLocation.x][topLeftLocation.y];
            for (int i = 0; i < currentSize; i++) {
                for (int j = 0; j < currentSize; j++) {
                    if (startValue != binaryImageArray[i+topLeftLocation.x][j+topLeftLocation.y])
                        return false;  
                }
            }
            return true;
        }
        
        public static String binaryColor(byte[][] binaryImageArray, Point topLeftLocation,int currentSize) 
        {
            byte startValue = binaryImageArray[topLeftLocation.x][topLeftLocation.y];
            if( (int) (startValue & 0xFF) ==0)
                return "1";
            else
                return "0";
            
        }
        
        public static void delineateBoundary(byte[][] boundaryImageArray,Point topLeftLocation,int currentSize)
        {
            for (int w = 0; w < currentSize; w++) {
                boundaryImageArray [topLeftLocation.x][w+ topLeftLocation.y]= (byte) 128;
            }
            for (int w = 0; w < currentSize; w++) {
                boundaryImageArray[topLeftLocation.x + currentSize - 1][topLeftLocation.y + w]=(byte) 128;
            }

            for (int h = 0; h < currentSize; h++) {
                boundaryImageArray[topLeftLocation.x +h] [topLeftLocation.y]=(byte) 128;
            }
            for (int h = 0; h < currentSize; h++) {
                boundaryImageArray[topLeftLocation.x +h][ topLeftLocation.y +currentSize - 1]=(byte) 128;
            }
        }
        
}

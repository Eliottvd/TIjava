/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CodeBehind;

/**
 *
 * @author Eliott
 */
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class Image {
    private boolean format_is_P3;
    private int height;
    private int width;
    private Pixel[][] matrix;
    public String file_name;

    public Pixel[][] getMatrix() { return matrix; }

    // the image will be square
    public int getDimension() { return height; }

    // sets one pixel each time
    // instead implementing nonsense "setMatrix"
    public void setPixel(int i, int j, int r, int g, int b) {
        Pixel p = new Pixel(i,j,r,g,b);
        matrix[i][j] = p;
    }


    public Image(int height, int width) {
        format_is_P3 = true;
        this.height = height;
        this.width = width;
        matrix = new Pixel[height][width];
    }

    public Image(String file_name) {
        this.file_name = file_name;
        Scanner s = null;
        try {

            // I tried to read file via BufferedReader.readLine()
            // but it came up with exception because
            // huge file lines can be broken in some instances
            // so I read and write all files by Scanner class
            // to use hasNext()
            s = new Scanner(new FileInputStream(file_name));
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }

        // implementing image features
        this.format_is_P3 = s.next().equals("P3");
        if (!format_is_P3) {
            System.out.println(
                    "image is not .ppm P3 format!\n"
            );
            System.exit(0);
        }
        width = s.nextInt();
        height = s.nextInt();
        matrix = new Pixel[height][width];
        s.nextInt(); // 255

        while (s.hasNext()) {
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    Pixel p = new Pixel(s.nextInt(), s.nextInt(), s.nextInt());
                    p.i = i; p.j = j;
                    matrix[i][j] = p;
                }
            }
        }
    }

    public String disturb() {
        // re-spreading the contents of the Image pixels with
        // picked data as [i][j] rgb(r,g,b) as "r g b" only.
        // so we can get .ppm format again. (for the new Image)
        String content = "P3\n" +
                         height + " " + width +
                         "\n255\n"; // first three lines

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                content += "" + matrix[i][j].r + " " +
                                matrix[i][j].g + " " +
                                matrix[i][j].b + " ";
            }
            content += "\n";
        }

        return content;
    }


    public boolean decider_by_threshold(int threshold, int i1, int j1, int i2, int j2) {
        // sends a boolean value to divide method in
        // QuadTree class to decide if it is necessary that
        // divide the current sub image (node) or not

        int meanR = 0, meanG = 0, meanB = 0, error = 0;
        int area = (i2-i1)*(j2-j1);

        // computing average of the square
        for (int i = i1; i < i2; i++) {
            for (int j = j1; j < j2; j++) {
                Pixel p = matrix[i][j];
                meanR += p.r;
                meanG += p.g;
                meanB += p.b;
            }
        }
        meanR /= area;
        meanG /= area;
        meanB /= area;
        // now mean color is rgb(meanR, meanG, meanB)

        for (int i = i1; i < i2; i++) {
            for (int j = j1; j < j2; j++) {
                Pixel p = matrix[i][j];
                error += (int) Math.pow(p.r - meanR, 2);
                error += (int) Math.pow(p.g - meanG, 2);
                error += (int) Math.pow(p.b - meanB, 2);
            }
        }
        error /= area;

        return error > threshold;
    }

    public Image compress(String tree_data, int depth, String output_file_name) {
        // takes index data of the squares as big as possible according
        // to the threshold value aand fill them the mean color of the
        // pixels within

        // may be this piece of code is necessary, however it is
        // written that exactly 8 new images will be given as output.
        // so if the original image is small enough, it prints images
        // which have the same resolution.
        /*if (Math.pow(4, depth) == height)
            return this;*/

        Image compressed = new Image(height,width);
        String[] ar = tree_data.split("\n");

        for (int line = 0; line < ar.length; line++) {
            Scanner s = new Scanner(ar[line]);
            int i1 = s.nextInt();
            int j1 = s.nextInt();
            int i2 = s.nextInt();
            int j2 = s.nextInt();

            Pixel mean = new Pixel();
            int meanR = 0, meanG = 0, meanB = 0;

            int count = 0;
            for (int i = i1; i < i2; i++) {
                for (int j = j1; j < j2; j++) {
                    Pixel p = matrix[i][j];
                    meanR += p.r;
                    meanG += p.g;
                    meanB += p.b;
                    count++;
                }
            }
            meanR /= count;
            meanG /= count;
            meanB /= count;
            mean.r = meanR;
            mean.g = meanG;
            mean.b = meanB;
            for (int i = i1; i < i2; i++) {
                for (int j = j1; j < j2; j++) {
                    compressed.matrix[i][j] = mean;
                }
            }
        }

        // to avoid null lines, I fill them black
        // if they exist
        for (int i = 0; i < height; i++)
            for (int j = 0; j < width; j++)
                if (compressed.matrix[i][j] == null)
                    compressed.matrix[i][j] = new Pixel(12,12,12);

        try {
            PrintWriter p = new PrintWriter(new FileOutputStream(output_file_name));
            p.write(compressed.disturb());
            p.close();
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }

        return compressed;
    }

    // optional part
    public Image edge_detection(String output_file_name) {
        Image sharpened = new Image(height, width);
        for (int i = 0; i < height; i++) {
            // for avoiding null pixels, I fill border pixels which
            // have not 8 neighbours, with black

            // border-top
            sharpened.matrix[0][i] = new Pixel(0,i,0,0,0);
            // border-left
            sharpened.matrix[i][0] = new Pixel(i,0,0,0,0);
            // border-right
            sharpened.matrix[i][height-1] = new Pixel(0,height-1,0,0,0);
            // border-bottom
            sharpened.matrix[height-1][i] = new Pixel(height-1,i,0,0,0);
        }
        for (int i = 1; i < height - 1; i++) {
            for (int j = 1; j < width - 1; j++) {
                Pixel pr = new Pixel();   // pixel of the new (sharpened) image
                Pixel[] ar = new Pixel[8];
                Pixel old = matrix[i][j]; // pixel of the old one

                                          // neighbours:
                ar[0] = matrix[i][j+1];   // right
                ar[1] = matrix[i][j-1];   // left
                ar[2] = matrix[i-1][j];   // top
                ar[3] = matrix[i+1][j];   // bottom
                ar[4] = matrix[i+1][j-1]; // bottom-left
                ar[5] = matrix[i+1][j+1]; // bottom-right
                ar[6] = matrix[i-1][j+1]; // top-right
                ar[7] = matrix[i-1][j-1]; // top-left

                // here there is a problem.
                boolean different = false;
                for (int k = 0; k < ar.length; k++) {
                    different = (Math.abs(ar[k].r - old.r) > 5) &&
                                (Math.abs(ar[k].g - old.g) > 5) &&
                                (Math.abs(ar[k].b - old.b) > 5);
                }
                if (different) {
                    pr.i = i;
                    pr.j = j;
                    pr.r = 255;
                    pr.g = 255;
                    pr.b = 255;
                    sharpened.matrix[i][j] = pr;
                }
                else {
                    pr.i = i;
                    pr.j = j;
                    pr.r = 0;
                    pr.g = 0;
                    pr.b = 0;
                    sharpened.matrix[i][j] = pr;
                }
            }
        }

        try {
            PrintWriter p = new PrintWriter(new FileOutputStream(output_file_name));
            p.write(sharpened.disturb());
            p.close();
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }

        return sharpened;
    }

    @Override
    public String toString() {
        String display = "";
        if (format_is_P3)
            display += "P3\n";
        display += "width: " + width + ", ";
        display += "height: " + height + "\n";
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++)
                display += matrix[i][j] + " ";
            display += "\n";
        }
        return display;
    }


    private class Pixel {
        private int r;
        private int g;
        private int b;
        private int i;
        private int j;

        public Pixel() { r = 0; g = 0; b = 0; }

        public Pixel(int red, int green, int blue) {
            r = red;
            g = green;
            b = blue;
        }

        public Pixel(int i, int j, int red, int green, int blue) {
            this.i = i;
            this.j = j;
            r = red;
            g = green;
            b = blue;
        }

        @Override
        public String toString() {
            // coordinates
            // plus traditional rgb format color representation in CSS
            // for instance, first pixel black: [0,0] rgb(0,0,0)
            return  "[" + i + "," + j + "] " +
                    "rgb(" + r + "," + g + "," + b + ")";
        }
    }







    // from this point, there are some extra codes. Even if they are not
    // necessary for the task, I have learned a great deal from these.
    // If any, they were fun. They can be used later.
    //
    // greyscale and negative mean clear.
    // compress_half, compresses the image by size. it makes for instance
    // 512x512 image 256x256 while removing meaningful data of the
    // original file as minimum as possible.


    public Image greyscale(String output_file_name) {
        Image monochromatic = new Image(height, width);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Pixel pm = new Pixel();   // pixel of the new (monochromatic) image
                Pixel old = matrix[i][j]; // pixel of the old one
                pm.i = i;
                pm.j = j;
                pm.r = (old.r + old.g + old.b) / 3;
                pm.g = (old.r + old.g + old.b) / 3;
                pm.b = (old.r + old.g + old.b) / 3;
                monochromatic.matrix[i][j] = pm;
            }
        }
        try {
            PrintWriter p = new PrintWriter(new FileOutputStream(output_file_name));
            p.write(monochromatic.disturb());
            p.close();
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }

        return monochromatic;
    }

    public Image negative(String output_file_name) {
        Image reversed = new Image(height, width);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Pixel pr = new Pixel();   // pixel of the new (reversed) image
                Pixel old = matrix[i][j]; // pixel of the old one
                pr.i = i;
                pr.j = j;
                pr.r = 255 - old.r;
                pr.g = 255 - old.g;
                pr.b = 255 - old.b;
                reversed.matrix[i][j] = pr;
            }
        }

        try {
            PrintWriter p = new PrintWriter(new FileOutputStream(output_file_name));
            p.write(reversed.disturb());
            p.close();
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }

        return reversed;
    }

    public Image compress_half(String index_data, String output_file_name) {
        // 0 0 1 1  nw      4x4 image
        // 0 2 1 3  ne
        // 2 0 3 1  sw
        // 2 2 3 3  se

        Image compressed = new Image(this.height / 2, this.width / 2);
        String[] arr = index_data.split("\n");
        for (int i = 0; i < arr.length; i++) {
            Scanner s = new Scanner(arr[i]);
            int ic = s.nextInt(),   // i of the non-compressed
                    jc = s.nextInt();   // j of the non-compressed
            Pixel pc = new Pixel(); // the new single one pixel of the compressed image
            // which is made as mixed of those quadruple of the
            // old one.

            int newRed = this.matrix[ic][jc].r +
                    this.matrix[ic + 1][jc].r +
                    this.matrix[ic][jc + 1].r +
                    this.matrix[ic + 1][jc + 1].r;
            newRed /= 4;

            int newGreen = this.matrix[ic][jc].g +
                    this.matrix[ic + 1][jc].g +
                    this.matrix[ic][jc + 1].g +
                    this.matrix[ic + 1][jc + 1].g;
            newGreen /= 4;

            int newBlue = this.matrix[ic][jc].b +
                    this.matrix[ic + 1][jc].b +
                    this.matrix[ic][jc + 1].b +
                    this.matrix[ic + 1][jc + 1].b;
            newBlue /= 4;

            pc.r = newRed;
            pc.g = newGreen;
            pc.b = newBlue;
            int newI = ic / 2, // i of the compressed
                    newJ = jc / 2; // j of the compressed
            pc.i = newI;
            pc.j = newJ;

            compressed.matrix[pc.i][pc.j] = pc;
        }

        try {
            PrintWriter p = new PrintWriter(new FileOutputStream(output_file_name));
            p.write(compressed.disturb());
            p.close();
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }

        return compressed;

    }
}
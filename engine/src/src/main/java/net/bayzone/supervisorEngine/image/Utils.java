package net.bayzone.supervisorEngine.image;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author lfoppiano
 */
public class Utils {

    String filename;
    BufferedImage entryImage;
    //StructElement se;
    int[][] binaryImage;
    int w;
    int h;

    public Utils(String imageFilename) {
        filename = imageFilename;
        loadImage(imageFilename);
        h = entryImage.getHeight();
        w = entryImage.getWidth();
        //binaryImage = new int[h][w];
    //se = new StructElement(StructElement.SHAPE_DIAMOND, StructElement.TYPE_NORMAL, 3);
    //binarization();
    }
    

    public double meanBinaryValue() {
        double meanValueB = 0;
        double meanValueR = 0;
        double meanValueG = 0;
        
        Color color = null;

        for (int k = 0; k < entryImage.getHeight(); k++) {
            for (int z = 0; z < entryImage.getWidth(); z++) {
                color = new Color(entryImage.getRGB(z, k));
                
                meanValueB += color.getBlue();
                meanValueG += color.getGreen();
                meanValueR += color.getRed();
                
            }
        }
        
        meanValueR /= (h*w);
        meanValueG /= (h*w);
        meanValueB /= (h*w);
                
        double meanValue = (meanValueR + meanValueG + meanValueB)/3;
        
        return meanValue;
    }

    public void save() {

        //close();

        try {
            ImageIO.write(entryImage, "png", new File(filename));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void loadImage(String imageFilename) {
        File file = new File(imageFilename);
        try {
            entryImage = ImageIO.read(file);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        w = entryImage.getWidth(); //x
        h = entryImage.getHeight(); //y
        /*
    PixelGrabber grabber = new PixelGrabber(entryImage, 0, 0, w, h, true);
    try {
    grabber.grabPixels();
    } catch (InterruptedException e) {
    System.err.println("error during grabbing " + e);
    }
    pixels = (int[]) grabber.getPixels();*/
    }

    public void sharpen() {
        Kernel kernel = new Kernel(3, 3, new float[]{-1, -1, -1, -1, 12, -1, -1, -1, -1});
        BufferedImageOp op = new ConvolveOp(kernel);
        entryImage = op.filter(entryImage, null);
    }

    public void binarization() {
        Color color = null;

        int red = 0;
        int green = 0;
        int blue = 0;

        int counter = 0;

        for (int k = 0; k < entryImage.getHeight(); k++) {
            for (int z = 0; z < entryImage.getWidth(); z++) {
                color = new Color(entryImage.getRGB(z, k));

                red += color.getRed();
                green += color.getGreen();
                blue += color.getBlue();

                counter++;
            }
        }

        double meanRed = (double) red / counter;
        double meanBlue = (double) blue / counter;
        double meanGreen = (double) green / counter;

        for (int k = 0; k < entryImage.getHeight(); k++) {
            for (int z = 0; z < entryImage.getWidth(); z++) {

                color = new Color(entryImage.getRGB(z, k));

                red = color.getRed();
                green = color.getGreen();
                blue = color.getBlue();

                if (red > 0.5 * meanRed) {
                    red = 255;
                } else {
                    red = 0;
                }

                if (blue > 0.5 * meanBlue) {
                    blue = 255;
                } else {
                    blue = 0;
                }

                if (green > 0.5 * meanGreen) {
                    green = 255;
                } else {
                    green = 0;
                }
                int tot = red + green + blue == 0 ? 0 : 255;


                color = new Color(red, green, blue);
                entryImage.setRGB(z, k, color.getRGB());

                binaryImage[k][z] = tot;
            }
        }
    }

    /*   public void close() {
    StructElement se = new StructElement(7, StructElement.SHAPE_CIRCLE, StructElement.TYPE_NORMAL);
    float[] kernel1Matrix = se.getAsVect();
    se = new StructElement(7, StructElement.SHAPE_CIRCLE, StructElement.TYPE_NORMAL);
    float[] kernel2Matrix = se.getAsVect();*/
    /*float[] kernel1Matrix = {
    0, 0, 0, 1, 0, 0, 0,
    0, 0, 1, 1, 1, 0, 0,
    0, 1, 1, 1, 1, 1, 0,
    1, 1, 1, 1, 1, 1, 1,
    0, 1, 1, 1, 1, 1, 0,
    0, 0, 1, 1, 1, 0, 0,
    0, 0, 0, 1, 0, 0, 0
    };
    float[] kernel2Matrix = {
    1, 1, 1, 0, 1, 1, 1,
    1, 1, 0, 0, 0, 1, 1,
    1, 0, 0, 0, 0, 0, 1,
    0, 0, 0, 0, 0, 0, 0,
    1, 0, 0, 0, 0, 0, 1,
    1, 1, 0, 0, 0, 1, 1,
    1, 1, 1, 0, 1, 1, 1
    };*/

//        KernelJAI kern1 = new KernelJAI(7, 7, 3, 3, kernel1Matrix);
//
//        ParameterBlock p = new ParameterBlock();
//        p.addSource(entryImage);
//        p.add(kern1);
//
//        PlanarImage processedImage = JAI.create("erode", p);
//        entryImage = processedImage.getAsBufferedImage();
//
//        KernelJAI kern2 = new KernelJAI(7, 7, 3, 3, kernel2Matrix);
//        
//        p = new ParameterBlock();
//        p.addSource(entryImage);
//        p.add(kern2);
//
//        processedImage = JAI.create("dilate", p, null);
//        entryImage = processedImage.getAsBufferedImage();
//    }
    /*
    public int[][] dilation() {
    int[][] binaryDilated = new int[binaryImage.length][binaryImage[0].length];
    Point p = new Point();
     */
    /*Applico la dilation*/
    /* for (int i = 0; i < h; i++) {
    for (int j = 0; j < w; j++) {
    p = findLocalMax(j, i, -1, -1);
    binaryDilated[i][j] = p.getValue();
    }
    }
    return binaryDilated;
    }
    public int[][] erosion() {
    int[][] binaryEroded = new int[binaryImage.length][binaryImage[0].length];
    Point p = new Point();
     */
    /*Applico la dilation*/
    /*    for (int i = 0; i < h; i++) {
    for (int j = 0; j < w; j++) {
    p = findLocalMin(j, i, -1, -1);
    binaryEroded[i][j] = p.getValue();
    }
    }
    return binaryEroded;
    }*/

    /*public void print() {
    for (int y = 0; y < h; y++) {
    for (int x = 0; x < w; x++) {
    System.out.print(pixels[x + y * w] + " ");
    }
    System.out.println();
    }
    }*/
    /*
     * Trova il minimo locale dato l'elemento strutturante
     */
    /*    private Point findLocalMin(int x, int y, int x_stop, int y_stop) {
    Point p = new Point(-1, -1, 1);
    int tmp = 0;
    for (int i = -se.getOriginY(); i < se.getRows() - se.getOriginY(); i++) {
    for (int j = -se.getOriginX(); j < se.getCols() - se.getOriginX(); j++) {
    if ((y + i) >= 0 && (y + i) < h && (x + j) >= 0 && (x + j) < w && se.getMat()[i + se.getOriginY()][j + se.getOriginX()] > 0) {
    tmp = p.getValue();
    p.setValue(Math.min(p.getValue(), binaryImage[y + i][x + j]));
    if (tmp != p.getValue() || ((x_stop >= 0 && y_stop >= 0) && (binaryImage[y + i][x + j] == binaryImage[p.getY()][p.getX()]) && (Math.abs(i + y - y_stop) + Math.abs(j + x - x_stop)) < (Math.abs(p.getX() - x_stop) + Math.abs(p.getY() - y_stop)))) {
    p.setX(x + j);
    p.setY(y + i);
    }
    }
    }
    }
    return p;
    }*/

    /*
     * Trova il massimo locale dato l'elemento strutturante
     */
    /*private Point findLocalMax(int x, int y, int x_stop, int y_stop) {
    Point p = new Point(-1, -1, 0);
    int i;
    int j;
    int tmp = 0;
    p.setValue(0);
    p.setX(x);
    p.setY(y);
    for (i = -se.getOriginY(); i < se.getRows() - se.getOriginY(); i++) {
    for (j = -se.getOriginX(); j < se.getCols() - se.getOriginX(); j++) {
    if ((y + i) >= 0 && (y + i) < h && (x + j) >= 0 && (x + j) < w && se.getMat()[i + se.getOriginY()][j + se.getOriginX()] > 0) {
    tmp = p.getValue();
    p.setValue(Math.max(p.getValue(), binaryImage[y + i][x + j]));
    if (tmp != p.getValue() || ((x_stop >= 0 && y_stop >= 0) && (binaryImage[y + i][x + j] == binaryImage[p.getY()][p.getX()]) && (Math.abs(i + y - y_stop) + Math.abs(j + x - x_stop)) < (Math.abs(p.getX() - x_stop) + Math.abs(p.getY() - y_stop)))) {
    p.setX(x + j);
    p.setY(y + i);
    }
    }
    }
    }
    return p;
    }*/
}

package net.bayzone.supervisorEngine.image;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import net.bayzone.supervisorEngine.Configuration;

/**
 *
 * @author lfoppiano
 */
public class BooleanRec {

    protected BufferedImage entryImage;
    private BooleanData bd;
    private String filename;
    private int w;
    private int h;
    private int boundTop;
    private int boundBottom;
    private int boundLeft;
    private int boundRight;
    private int subHeight;
    private int subWidth;

    /**
     * Create an istance using input file.
     *
     * @param fileIn input file
     * @param dim dimension of downsampled image
     */
    public BooleanRec(String fileIn) {

        this.filename = fileIn;

        if (Configuration.isDebugMode()) {
            System.out.println("Start boolean recognitor ");
        }

        File file = new File(fileIn);

        try {
            entryImage = ImageIO.read(file);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        w = entryImage.getWidth(); //x
        h = entryImage.getHeight(); //y

        if (Configuration.isDebugMode()) {
            System.out.println("Image height: " + h + " image width: " + w);
        }

        bd = new BooleanData(h, w);
        bd.setFilename(filename);
        downSample();

        findBounds(h, w);
        getBd().setSubDimension(new Dimension(subWidth, subHeight));

        if (Configuration.isDebugMode()) {
            System.out.println("Bounding box: Top: " + boundTop + " Bottom: " +
                    boundBottom + " Left: " + boundLeft + " Right: " +
                    boundRight);

            System.out.println("SubBox: " + subHeight + "," + subWidth);
        }
    }

    /**
     * This method is called internally to 
     * see if there are any pixels in the given
     * scan line. This method is used to perform
     * autocropping.
     * 
     * @param y The horizontal line to scan.
     * @return True if there were any pixels in this 
     * horizontal line.
     */
    protected boolean hLineClear(int y) {

        for (int i = 0; i < this.w; i++) {
            if (bd.getData(y, i) == true) {
                return true;
            }
        }
        return false;
    }

    /**
     * This method is called to determine ....
     * 
     * @param x The vertical line to scan.
     * @return True if there are any pixels in the
     * specified vertical line.
     */
    protected boolean vLineClear(int x) {
        for (int i = 0; i < this.h; i++) {
            if (bd.getData(i, x) == true) {
                return true;
            }
        }
        return false;
    }

    /**
     * This method is called to automatically
     * crop the image so that whitespace is 
     * removed.
     * 
     * @param w The width of the image.
     * @param h The height of the image
     */
    protected void findBounds(int h, int w) {
        // top line
        for (int y = 0; y < h; y++) {
            if (hLineClear(y)) {
                boundTop = y;
                break;
            }

        }
        // bottom line
        for (int y = h - 1; y >= 0; y--) {
            if (hLineClear(y)) {
                boundBottom = y;
                break;
            }
        }
        // left line
        for (int x = 0; x < w; x++) {
            if (vLineClear(x)) {
                boundLeft = x;
                break;
            }
        }

        // right line
        for (int x = w - 1; x >= 0; x--) {
            if (vLineClear(x)) {
                boundRight = x;
                break;
            }
        }

        subHeight = boundBottom - boundTop;
        subWidth = boundRight - boundLeft;

        bd.setSubTop(boundTop);
        bd.setSubBottom(boundBottom);
        bd.setSubLeft(boundLeft);
        bd.setSubRight(boundRight);
    }

    /**
     * 
     */
    private void preparing() {
        Color color = null;

        for (int k = 0; k < entryImage.getHeight(); k++) {
            for (int z = 0; z < entryImage.getWidth(); z++) {

                color = new Color(entryImage.getRGB(z, k));

                int red = color.getRed();
                int green = color.getGreen();
                int blue = color.getBlue();

                if (red > 255 / 2) {
                    red = 255;
                } else {
                    red = 0;
                }

                if (blue > 255 / 2) {
                    blue = 255;
                } else {
                    blue = 0;
                }

                if (green > 255 / 2) {
                    green = 255;
                } else {
                    green = 0;
                }

                color = new Color(red, green, blue);
                entryImage.setRGB(z, k, color.getRGB());
            }
        }
    }

    public void setSampleData(BooleanData ds) {
        this.setBd(ds);
    }

    public BooleanData getSampleData() {
        return getBd();
    }

    public void downSample() {

        for (int y = 0; y < bd.getHeight(); y++) {
            //colonne
            for (int x = 0; x < bd.getWidth(); x++) {
                /*if(Configuration.isDebugMode())
                System.out.println("riga = " + y+"/"+bd.getHeight() + ",colonna= " + x+"/"+bd.getWidth());
                 */
                Color color = new Color(entryImage.getRGB(x, y));

                if (!color.equals(Color.WHITE)) {   //bianco
                    bd.setData(y, x, true);
                } else {
                    bd.setData(y, x, false);
                }
            }
        }
    /*        
    if (Configuration.isDebugMode()) {
    for (int y = 0; y < h; y++) {
    for (int x = 0; x < w; x++) {
    if (bd.getData(y, x) == true) {
    System.out.print("x ");
    } else {
    System.out.print("  ");
    }
    }
    System.out.println();
    }
    System.out.println();
    System.out.println();
    }*/
    }

    public BooleanData getBd() {
        return bd;
    }

    public void setBd(BooleanData bd) {
        this.bd = bd;
    }
}

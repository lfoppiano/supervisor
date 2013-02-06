package net.bayzone.supervisorEngine.image;

import java.awt.Dimension;
import java.io.PrintStream;
import net.bayzone.supervisorEngine.Configuration;

public class BooleanData implements Cloneable {

    /**
     * The downsampled data as a grid of booleans.
     */
    private boolean[][] grid;
    private Dimension subDimension;
    private Dimension dimension;
    private int subTop;
    private int subBottom;
    private int subLeft;
    private int subRight;
    private String filename;

    /**
     * Create an istance of sample data
     *
     * @param width The width
     * @param height The height
     */
    public BooleanData(int height, int width) {
        grid = new boolean[height][width];
        dimension = new Dimension(width, height);
    }

    /**
     * Set one pixel of sample data.
     *
     * @param x The x coordinate
     * @param y The y coordinate
     * @param v The value to set
     */
    public void setData(int x, int y, boolean v) {
        grid[x][y] = v;
    }

    /**
     * Get a pixel from the sample.
     *
     * @param x The x coordinate
     * @param y The y coordinate
     * @return The requested pixel
     */
    public boolean getData(int x, int y) {
        return grid[x][y];
    }

    /**
     * Clear the downsampled image
     */
    public void clear() {
        for (int x = 0; x < dimension.getHeight(); x++) {
            for (int y = 0; y < dimension.getWidth(); y++) {
                grid[x][y] = false;
            }
        }
    }

    /**
     * Get the height of the down sampled image.
     *
     * @return The height of the downsampled image.
     */
    public int getHeight() {
        return (int) dimension.getHeight();
    }

    /**
     * Get the width of the downsampled image.
     *
     * @return The width of the downsampled image
     */
    public int getWidth() {
        return (int) dimension.getWidth();
    }

    /**
     * Convert this sample to a string.
     *
     * @return Just returns the letter that this sample is assigned to.
     */
    @Override
    public String toString() {
        String out = "";
        for (int x = 0; x < grid.length; x++) {
            for (int y = 0; y < grid[0].length; y++) {
                if (grid[x][y] == true) {
                    out += "x ";
                } else {
                    out += "  ";
                }
            }
            out += "\n";
        }

        return out;
    }

    /**
     * Create a copy of this sample
     *
     * @return A copy of this sample
     */
    @Override
    public Object clone() {

        BooleanData obj = new BooleanData(getWidth(), getHeight());
        for (int y = 0; y < getHeight(); y++) {
            for (int x = 0; x < getWidth(); x++) {
                obj.setData(x, y, getData(x, y));
            }
        }
        return obj;
    }

    /**
     * Recognize true/false
     * 
     * @return if the checkbox is choosen or not
     */
    public boolean yesOrNot() {
        boolean returning;
        int countOnes = 0;
        int countZeros = 0;

        if (Configuration.isDebugMode()) {
            System.out.println("Evaluate true/false value");
        }

        if ((subDimension.getHeight() != 0) && (subDimension.getWidth() != 0)) {
            for (int y = subTop; y <= subBottom; y++) {
                for (int x = subLeft; x < subRight; x++) {
                    if (this.getData(y, x) == true) {
                        countOnes++;
                    } else if (this.getData(y, x) == false) {
                        countZeros++;
                    }
                }
            }

            if (countOnes < subDimension.getWidth() * subDimension.getHeight() *
                    0.30) {
                returning = false;
            } else {
                returning = true;
            }
        } else {
            returning = false;
        }

        //printBound(System.out);
        /*
        for (int y = 0; y < this.getHeight(); y++) {
        for (int x = 0; x < this.getWidth(); x++) {
        if (this.getData(x, y) == true) {
        countOnes++;
        } else if (this.getData(x, y) == false) {
        countZeros++;
        }
        }
        }
        if (countOnes < this.subDimension.getWidth() * this.subDimension.getHeight() * 0.80) {
        returning = false;
        } else {
        returning = true;
        }
         */
        if (Configuration.isDebugMode()) {
            System.out.println("\tPercent true/false -" + this.filename + "- : " +
                    countOnes + "/" + subDimension.getWidth() *
                    subDimension.getHeight() + " percent: " + (countOnes /
                    (subDimension.getWidth() * subDimension.getHeight())) * 100 +
                    "; value:" + returning);
        }

        return returning;
    }

    /**
     * Print samples
     */
    public void print(PrintStream out) {
        for (int y = 0; y < this.getHeight(); y++) {
            for (int x = 0; x < this.getWidth(); x++) {

                if (this.getData(y, x) == true) {
                    out.print("x ");
                } else {
                    out.print("  ");
                }
            }
            out.println();
        }

        out.println("\n");
    }

    /**
     * Print samples
     */
    public void printBound(PrintStream out) {
        for (int y = subTop; y <= subBottom; y++) {
            for (int x = subLeft; x < subRight; x++) {
                if (this.getData(y, x) == true) {
                    out.print("x ");
                } else {
                    out.print("  ");
                }
            }
            out.println();
        }

        out.println("\n");
    }

    public void setSubDimension(Dimension dimension) {
        subDimension = dimension;
    }

    public Dimension getSubDimneison() {
        return subDimension;
    }

    public boolean[][] getGrid() {
        return grid;
    }

    public void setGrid(boolean[][] grid) {
        this.grid = grid;
    }

    public int getSubTop() {
        return subTop;
    }

    public void setSubTop(int subTop) {
        this.subTop = subTop;
    }

    public int getSubBottom() {
        return subBottom;
    }

    public void setSubBottom(int subBottom) {
        this.subBottom = subBottom;
    }

    public int getSubLeft() {
        return subLeft;
    }

    public void setSubLeft(int subLeft) {
        this.subLeft = subLeft;
    }

    public int getSubRight() {
        return subRight;
    }

    public void setSubRight(int subRight) {
        this.subRight = subRight;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}

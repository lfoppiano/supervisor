package net.bayzone.supervisorEngine.model;

/**
 * CoordModel.java
 *
 * Class that modelize a simple crop element.
 * This element have 2 coordinates, label, unit
 * that this coordinates are measured, a value
 * (true, false, etc etc) and filename of small
 * images that is generated and saved.
 */
/**
 *
 * @author lfoppiano
 */
public class CoordModel {

    private int x1;
    private int x2;
    private int y1;
    private int y2;
    private String label;
    private String unit;
    private String filename;
    private Object value;

    public CoordModel() {
    }

    /**
     * Set the first X coordinate
     * @param x1 the coordinate
     */
    public void setX1(int x1) {
        this.x1 = x1;
    }

    /**
     * Get the first X coordinate
     * @return x1 the coordinate
     */
    public int getX1() {
        return x1;
    }

    /**
     * Set the first Y coordinate
     * @param y1 the coordinate
     */
    public void setY1(int y1) {
        this.y1 = y1;
    }

    /**
     * Get the first Y coordinate
     * @return y1 coordinate
     */
    public int getY1() {
        return y1;
    }

    /**
     * Set the second X coordinate
     * @param x2 the coordinate
     */
    public void setX2(int x2) {
        this.x2 = x2;
    }

    /**
     * Get the second X coordinate
     * @return x2 coordinate
     */
    public int getX2() {
        return x2;
    }

    /**
     * Set the second Y coordinate
     * @param y2 the coordinate
     */
    public void setY2(int y2) {
        this.y2 = y2;
    }

    /**
     * Get the second Y coordinate
     * @return y2 coordinate
     */
    public int getY2() {
        return y2;
    }

    /**
     * Get label of couple of coordinates
     * @return label of coordinates
     */
    public String getLabel() {
        return label;
    }

    /**
     * Set label of couple of coordinates
     * @param label label of coordinates
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * Get units of coordinates (px, percentual,
     * centimeters, millimeters)
     * @return units
     */
    public String getUnit() {
        return unit;
    }

    /**
     * Set units of coordinates (px, percentual,
     * centimeters, millimeters)
     * @param unit units of coordinates
     */
    public void setUnit(String unit) {
        this.unit = unit;
    }

    /**
     * Get filename associated to this
     * couple of coordinates, the cropped images
     * will be saved and referenced to this filename
     * @return filename of coordinates
     */
    public String getFilename() {
        return filename;
    }

    /**
     * Set filename associated to this
     * couple of coordinates, the cropped images
     * will be saved and referenced to this filename
     * @param filename filename of coordinates
     */
    public void setFilename(String filename) {
        this.filename = filename;
    }

    /**
     * Get values of resulting images, after
     * process has been recognized.
     * This is an Object type to
     * avoid many type of values
     * (Boolean, String, Double, etc)
     * @return value of coordinates
     */
    public Object getValue() {
        return value;
    }

    /**
     * Set values of resulting images, after
     * process has been recognized.
     * This is an Object type to
     * avoid many type of values
     * (Boolean, String, Double, etc)
     * @param value value of coordinates
     */
    public void setValue(Object value) {
        this.value = value;
    }
}

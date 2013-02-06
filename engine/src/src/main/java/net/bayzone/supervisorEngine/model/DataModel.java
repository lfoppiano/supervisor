package net.bayzone.supervisorEngine.model;

import java.util.ArrayList;
import java.util.List;

/**
 * DataModel.java
 *
 * Created on September 24, 2007, 3:40 PM
 *
 * Modelize a sub-information about fields.
 * The quantity of information depends of type
 * of dataModel.
 * 
 * type = 1, the field is simple and contain
 * information (example Name: _________). In 
 * this case the label of dataModel is null 
 * because label of coordinates is enought.
 * 
 * type = 2, the field is multiple choosing and 
 * is possible to choose only one answer.
 * This case si similar to the one above.
 * 
 * type = 3, the field is multiple choosing and
 * is possible to choose more than one answer.
 * In this case, is necessary to memorize two
 * labels: the first one, is the label of 
 * coordinates and is stored in coordModel, the 
 * second one is a more general label 
 * memorized in dataModel.
 */
/**
 *
 * @author lfoppiano <luca@lodi.linux.it>
 */
public class DataModel {

    private List<CoordModel> coordinate;
    private String label;
    private String type;

    /** 
     * Creates a new instance of DataModel 
     */
    @SuppressWarnings("unchecked")
    public DataModel() {
        coordinate = new ArrayList();
    }

    /**
     * @return data type
     */
    public String getType() {
        return type;
    }

    /**
     * Set data type
     * @param type data type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Add a couple of coordinates
     * @param cm couple of coordinates
     */
    public void addCoord(CoordModel cm) {
        coordinate.add(cm);
    }

    /**
     * remove a couple of coordinates
     * @param cm coordinates need to be removed
     */
    public void delCoord(CoordModel cm) {
        coordinate.remove(cm);
    }

    /**
     * @return list of coordinates related to
     * this data model
     */
    public List getCoordModel() {
        return coordinate;
    }

    /**
     * @return label of data model
     */
    public String getLabel() {
        return label;
    }

    /**
     * Set data model's label.
     * 
     * @param label of data model
     */
    public void setLabel(String label) {
        this.label = label;
    }
}

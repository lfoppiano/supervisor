/**
 * DocModel.java
 *
 * Created on October 1, 2007, 3:44 PM
 *
 * This class modelize the entire document, by 
 * creating a list of fields (questions for eg.)
 * and information about document format (a4, 
 * letter, etc).
 */
package net.bayzone.supervisorEngine.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author lfoppiano <luca@lodi.linux.it>
 */
public class DocModel {

    private List<DataModel> fields;
    private String docFormat;

    /** 
     * Creates a new instance of DocModel 
     */
    @SuppressWarnings("unchecked")
    public DocModel() {
        fields = new ArrayList();
    }

    /**
     * Get list of fields
     * @return list of fields
     */
    public List<DataModel> getFields() {
        return fields;
    }

    /**
     * Set list of fields
     * @param fields list of fields
     */
    public void setFields(List<DataModel> fields) {
        this.fields = fields;
    }

    /**
     * @return document format
     */
    public String getDocFormat() {
        return docFormat;
    }

    /**
     * Set document format
     * @param docFormat document format
     */
    public void setDocFormat(String docFormat) {
        this.docFormat = docFormat;
    }

    /**
     * Add a new field
     * @param dataM new field added
     */
    public void addField(DataModel dataM) {
        fields.add(dataM);
    }
}

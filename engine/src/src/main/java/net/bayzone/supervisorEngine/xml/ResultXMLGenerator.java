/*
 * Writer.java
 *
 * Created on Nov 13, 2007, 11:27:28 AM
 *
 */
package net.bayzone.supervisorEngine.xml;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.bayzone.supervisorEngine.model.CoordModel;
import net.bayzone.supervisorEngine.model.DataModel;
import net.bayzone.supervisorEngine.model.DocModel;

/**
 *
 * @author lfoppiano
 */
public class ResultXMLGenerator {

    private PrintWriter out;
    private DocModel dm;

    /** 
     * Create an istance of class
     * 
     * @param dm Document Model necessary to 
     * got data
     */
    public ResultXMLGenerator(DocModel dm) {
        this.dm = dm;
    }

    /**
     * Write xml on file 
     * 
     * @param filename filename to write
     */
    public void writeOnFile(String filename) {
        try {
            out = new PrintWriter(new FileWriter(filename));
            out.print(generateXML());
            out.close();
        } catch (IOException ex) {
            Logger.getLogger(ResultXMLGenerator.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
    }

    /**
     * @return a string with xml
     */
    @Override
    public String toString() {
        return generateXML();
    }

    /**
     * Generate xml
     */
    private String generateXML() {
        List fields = dm.getFields();
        String xml = "";
        xml += "<?xml version=\"1.0\"?> \n";
        xml += "<document format=\"" + dm.getDocFormat() + "\">\n";

        for (int i = 0; i < fields.size(); i++) {
            DataModel dm2 = (DataModel) fields.get(i);
            List coord = dm2.getCoordModel();
            if (dm2.getType().equals("1")) {
                //xml += "\t<data label=\"" + dm2.getLabel() + "\">\n";
                xml += "\t<data type=\""+dm2.getType()+"\">\n";
                for (int j = 0; j < coord.size(); j++) {
                    CoordModel temp = (CoordModel) coord.get(j);
                    xml += "\t\t<field label=\"" + temp.getLabel() + "\">" +
                            temp.getValue().toString() + "<\\field>\n";
                }
                xml += "\t<\\data>\n";
            } else if (dm2.getType().equals("2")) {
                xml += "\t<data label=\"" + dm2.getLabel() + "\" type=\"" + dm2.getType() + "\">\n";
                for (int j = 0; j < coord.size(); j++) {
                    CoordModel temp = (CoordModel) coord.get(j);
                    xml += "\t\t <choose name=\"" + temp.getLabel() + "\">" +
                            temp.getValue().toString() + "</choose>\n";
                }
                xml += "\t<\\data>\n";
            } else if (dm2.getType().equals("3")) {
                xml += "\t<data label=\"" + dm2.getLabel() + "\" type=\"" + dm2.getType() + "\">\n";
                for (int j = 0; j < coord.size(); j++) {
                    CoordModel temp = (CoordModel) coord.get(j);
                    xml += "\t\t <choose name=\"" + temp.getLabel() + "\">" +
                            temp.getValue().toString() + "</choose>\n";
                }
                xml += "\t<\\data>\n";
            }
        }
        xml += "</document>\n";
        return xml;
    }
}

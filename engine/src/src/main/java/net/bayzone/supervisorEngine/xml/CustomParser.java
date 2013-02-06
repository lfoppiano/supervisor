package net.bayzone.supervisorEngine.xml;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import net.bayzone.supervisorEngine.model.CoordModel;
import net.bayzone.supervisorEngine.model.DataModel;
import net.bayzone.supervisorEngine.model.DocModel;
import org.xml.sax.XMLReader;
import org.xml.sax.InputSource;
import org.xml.sax.helpers.XMLReaderFactory;
import org.xml.sax.SAXException;

/**
 *
 * @author lfoppiano <luca@lodi.linux.it>
 */
public class CustomParser {

    private DocModel dm;
    private XMLReader xr;

    /**
     * Create an istance of parser
     * 
     * @param filename name of input file
     */
    public CustomParser(String filename) throws SAXException, FileNotFoundException, IOException {

        dm = new DocModel();

        xr = XMLReaderFactory.createXMLReader();
        CustomHandler handler = new CustomHandler(dm);


        xr.setContentHandler(handler);
        xr.setErrorHandler(handler);

        FileReader r = new FileReader(filename);
        xr.parse(new InputSource(r));
    }

    /**
     * @return a string with information about extracted 
     * information
     */
    @Override
    public String toString() {

        String result = "";
        if (dm != null) {
            result = "General data: \n" + "type: " + dm.getDocFormat() + "\n";
            List<DataModel> ddm = dm.getFields();
            for (int i = 0; i < ddm.size(); i++) {
                @SuppressWarnings(value = "unchecked")
                List<CoordModel> dam = ddm.get(i).getCoordModel();
                result += "label: " + ddm.get(i).getLabel() + " " + "type: " +
                        ddm.get(i).getType() + "\n";
                for ( int j = 0; j < dam.size(); j++) {
                    result += "choose " + j + " \n label: " +
                            dam.get(j).getLabel() + "\n" + "units: " +
                            dam.get(j).getUnit() + "\n" + "coordinata 1: (" +
                            dam.get(j).getX1() + "," + dam.get(j).getY1() +
                            ") \n" + "coordinata 2: (" + dam.get(j).getX2() +
                            "," + dam.get(j).getY2() + ")\n";
                }
            }
        }
        return result;
    }

    /**
     * @return document model
     */
    public DocModel getModel() {
        return dm;
    }
}

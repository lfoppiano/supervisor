package net.bayzone.supervisorEngine.xml;

import net.bayzone.supervisorEngine.model.CoordModel;
import net.bayzone.supervisorEngine.model.DataModel;
import net.bayzone.supervisorEngine.model.DocModel;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

/**
 * 
 * @author lfoppiano <luca@lodi.linux.it>
 */
public class CustomHandler extends DefaultHandler {

    CoordModel coord;
    DocModel dm;
    DataModel dataM;
    String check;
    String substring;

    /**
     * Create an istance of handler
     */
    public CustomHandler() {
        super();
        check = "0";
        substring = "";
    }

    /** Create an istance of handler with 
     * associated document model
     * 
     * @param dm document model associated with 
     * handler
     */
    public CustomHandler(DocModel dm) {
        super();
        this.dm = dm;
        check = "0";
        substring = "";
    }

    @Override
    public void startDocument() {
    //System.out.println("Start document");
    }

    @Override
    public void endDocument() {
    //System.out.println("End document");
    }

    @Override
    public void startElement(String uri, String name, String qName,
            Attributes atts) {
        if (qName.equals("document")) {
            dm.setDocFormat(atts.getValue("format"));
        } else if (qName.equals("data")) {
            dataM = new DataModel();
            dataM.setType(atts.getValue("type"));
            if ((dataM.getType().equals("2")) || (dataM.getType().equals("3"))) {
                dataM.setLabel(atts.getValue("label"));
            } else if (dataM.getType().equals("1")) {
                dataM.setLabel("null");
            }
        } else if ((qName.equals("choose")) || (qName.equals("field"))) {
            coord = new CoordModel();
            coord.setLabel(atts.getValue("label"));
            coord.setUnit(atts.getValue("unit"));
        } else if (qName.equals("x1")) {
            check = qName;
        } else if (qName.equals("y1")) {
            check = qName;
        } else if (qName.equals("x2")) {
            check = qName;
        } else if (qName.equals("y2")) {
            check = qName;
        }
    }

    @Override
    public void endElement(String uri, String name, String qName) {
        if (qName.equals("data")) {
            dm.addField(dataM);
        } else if ((qName.equals("choose")) || (qName.equals("field"))) {
            dataM.addCoord(coord);
        } else if (qName.equals("x1")) {
            coord.setX1(Integer.parseInt(substring));
            substring = "";
            check = "0";
        } else if (qName.equals("y1")) {
            coord.setY1(Integer.parseInt(substring));
            substring = "";
            check = "0";
        } else if (qName.equals("x2")) {
            coord.setX2(Integer.parseInt(substring));
            substring = "";
            check = "0";
        } else if (qName.equals("y2")) {
            coord.setY2(Integer.parseInt(substring));
            substring = "";
            check = "0";
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        //System.out.print("Characters:    \"");
        if (!check.equals(0)) {
            for (int i = start; i < start + length; i++) {
                switch (ch[i]) {
                    case '\\':
                        //System.out.print("\\\\");
                        break;
                    case '"':
                        //System.out.print("\\\"");
                        break;
                    case '\n':
                        //System.out.print("\\n");
                        break;
                    case '\r':
                        //System.out.print("\\r");
                        break;
                    case '\t':
                        //System.out.print("\\t");
                        break;
                    default:
                        //System.out.print(ch[i]);
                        if (check.equals("x1")) {
                            substring = substring + "" + ch[i];
                        } else if (check.equals("y1")) {
                            substring = substring + "" + ch[i];
                        } else if (check.equals("x2")) {
                            substring = substring + "" + ch[i];
                        } else if (check.equals("y2")) {
                            substring = substring + "" + ch[i];
                        }
                        break;
                }
            }
        }
    }
}

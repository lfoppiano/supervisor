package net.bayzone.supervisorEngine;

import java.io.File;
import java.io.PrintStream;
import java.util.List;
import net.bayzone.supervisorEngine.model.CoordModel;
import net.bayzone.supervisorEngine.model.DataModel;
import net.bayzone.supervisorEngine.model.DocModel;
import net.bayzone.supervisorEngine.image.BooleanRec;
import net.bayzone.supervisorEngine.image.ImageHandler;
import net.bayzone.supervisorEngine.image.BooleanData;
import net.bayzone.supervisorEngine.image.Utils;
import net.bayzone.supervisorEngine.xml.CustomParser;
import net.bayzone.supervisorEngine.xml.ResultXMLGenerator;

public class Main {

    public void compute() throws Exception {

        Utils utils = null;
        /* xml parsing */
        if (Configuration.isDebugMode()) {
            System.out.println("Parsing xml configuration file");
        }

        CustomParser cp = new CustomParser(Configuration.getXmlPath());
        if (Configuration.isDebugMode()) {
            System.out.println(cp.toString());
        }

        /* image cropping */
        if (Configuration.isDebugMode()) {
            System.out.println("cropping image input");
        }

        ImageHandler ih = new ImageHandler(Configuration.getImagePath());
        ih.cropImage(cp.getModel());

        String[] files = ih.getListFiles();
        if (Configuration.isDebugMode()) {
            for (int i = 0; i < files.length; i++) {
                utils = new Utils(files[i]);
                
                System.out.println("Valore medio dei pixel: " + utils.meanBinaryValue() + 
                        " - "+ (utils.meanBinaryValue()/255)*100);
            }
        }
        
        /* Adjust images */

        /* Show method */
        PrintStream out = null;

        if (Configuration.getOutputPath() != null) {
            out = new PrintStream(new File(Configuration.getOutputPath()));
        } else {
            out = System.out;
        }

        Launcher l = new Launcher();

        /* Convert images to indexed binary tif */

        l.launch("sh " + Configuration.getScriptsPath() + "/toBinaryTif.sh -i " + Configuration.getTempPath());

        if (Configuration.isDebugMode()) {
            System.out.println("sh " + Configuration.getScriptsPath() + "/toBinaryTif.sh -i " + Configuration.getTempPath());
        }

        /* Add results to coordinate */
        DocModel dm = cp.getModel();
        List fields = dm.getFields();

        int count = 0;

        for (int i = 0; i < fields.size(); i++) {
            DataModel dm2 = (DataModel) fields.get(i);
            List coord = dm2.getCoordModel();
            Object result = null;

            for (int j = 0; j < coord.size(); j++) {
                CoordModel temp = (CoordModel) coord.get(j);

                if (dm2.getType().equals("1")) {
                    result = new String("" + l.launch("sh " + Configuration.getScriptsPath() + "/tess.sh -i " + temp.getFilename().replace(".png", ".tif")).toLowerCase());
                } else {
                    String temp_filename = temp.getFilename();
                    BooleanRec booleanRec = new BooleanRec(temp_filename);
//                    booleanRec.extractSamples(false);

                    BooleanData sample = booleanRec.getBd();
                    result = new Boolean(sample.yesOrNot());
                //result = new Boolean(sample[count].yesOrNot());
                }

                temp.setValue(result);
                count++;
            }
        }
        
        if(Configuration.isDebugMode()){
            System.out.println("Cleaning temp directory");
            l.launch("sh " + Configuration.getScriptsPath() + "/clear.sh");
        }
            
        //writing XML
        ResultXMLGenerator rg = new ResultXMLGenerator(dm);
        out.print(rg.toString());
        if (Configuration.getOutputPath() != null) {
            out.close();
        }

    }

    public static void main(String args[]) throws Exception {


        /***
         * Parameters: manage of parameter is a task of shell script 
         * to launch this program. This program use static parameters.
         * 
         * jimageExtractor file.xml file.png|jpg [output.xml -d]
         * 
         * output.xml and -d are optionals and may be exchanged. 
         * file.xml and file.png are mandatory.
         * 
         */
        switch (args.length) {
            case 2:
                if (args[0].matches("^.*\\.(xml|XML)$")) {
                    Configuration.setXmlPath(args[0]);
                }

                if (args[1].matches("^.*\\.(jpg|JPG|png|PNG)$")) {
                    Configuration.setImagePath(args[1]);
                }
                break;
            case 3:
                if (args[0].matches("^.*\\.(xml|XML)$")) {
                    Configuration.setXmlPath(args[0]);
                }

                if (args[1].matches("^.*\\.(jpg|JPG|png|PNG)$")) {
                    Configuration.setImagePath(args[1]);
                }

                if (args[2].matches("^.*\\.(xml|XML)$")) {
                    Configuration.setXmlPath(args[2]);
                } else if (args[2].equals("-d")) {
                    Configuration.setDebugMode(true);
                }
                break;

            case 4:
                if (args[0].matches("^.*\\.(xml|XML)$")) {
                    Configuration.setXmlPath(args[0]);
                }

                if (args[1].matches("^.*\\.(jpg|JPG|png|PNG)$")) {
                    Configuration.setImagePath(args[1]);
                }

                if (args[2].matches("^.*\\.(xml|XML)$")) {
                    Configuration.setXmlPath(args[2]);
                }

                if (args[3].equals("-d")) {
                    Configuration.setDebugMode(true);
                }
                break;
        }
       
        Main testThesis = new Main();
        if (Configuration.isDebugMode() == true) {
            System.out.println("Arg(s): " + args.length);
            System.out.println("Image Location: " + Configuration.getImagePath());
            System.out.println("XML Location: " + Configuration.getXmlPath());
        }
        if ((Configuration.getXmlPath() != null) && (Configuration.getImagePath() != null)) {
            testThesis.compute();
        }
    }

}

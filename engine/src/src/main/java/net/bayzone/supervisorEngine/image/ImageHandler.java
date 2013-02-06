/*
 * ImageHandler.java
 *
 * Created on September 24, 2007, 4:42 PM
 *
 */
package net.bayzone.supervisorEngine.image;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;
import net.bayzone.supervisorEngine.Configuration;
import net.bayzone.supervisorEngine.model.CoordModel;
import net.bayzone.supervisorEngine.model.DataModel;
import net.bayzone.supervisorEngine.model.DocModel;
import net.sourceforge.jiu.data.PixelImage;
import net.sourceforge.jiu.geometry.Crop;
import net.sourceforge.jiu.gui.awt.ImageCreator;
import net.sourceforge.jiu.gui.awt.ToolkitLoader;
import net.sourceforge.jiu.ops.MissingParameterException;
import net.sourceforge.jiu.ops.WrongParameterException;

/**
 *
 * @author lfoppiano
 */
public class ImageHandler {

    PixelImage image;
    String[] listSavedFiles;

    /**
     * Creates a new instance of ImageHandler
     */
    public ImageHandler(String filename) {
        if (Configuration.isDebugMode()) {
            System.out.println(filename);
        }
        image = ToolkitLoader.loadAsRgb24Image(filename);
    }

    /** 
     * Crop images starting with a document model
     * 
     * @param dm document model
     */
    @SuppressWarnings(value = "unchecked")
    public void cropImage(DocModel dm) {

        List<DataModel> dam = dm.getFields();
        List<CoordModel> cm = null;
        int totImgs = 0;

        for (int i = 0; i < dam.size(); i++) {
            cm = dam.get(i).getCoordModel();
            totImgs += cm.size();
        }

        listSavedFiles = new String[totImgs];

        int c = 0;
        for (int i = 0; i < dam.size(); i++) {
            cm = dam.get(i).getCoordModel();

            for (int j = 0; j < cm.size(); j++) {

                Crop crop = new Crop();
                if (Configuration.isDebugMode()) {
                    System.out.println("processing coordinate n: " + i + "  -  " +
                            cm.get(j).getX1() + " " + cm.get(j).getY1() + " " +
                            cm.get(j).getX2() + " " + cm.get(j).getY2() + "\n");
                }

                crop.setInputImage(image);
                crop.setBounds(cm.get(j).getX1(), cm.get(j).getY1(),
                        cm.get(j).getX2(), cm.get(j).getY2());

                try {
                    crop.process();
                } catch (WrongParameterException ex) {
                    ex.printStackTrace();
                } catch (MissingParameterException ex) {
                    ex.printStackTrace();
                }

                PixelImage image2 = crop.getOutputImage();
                BufferedImage awtImage = ImageCreator.convertToAwtBufferedImage(image2);

                /* Remove red */
                for (int k = 0; k < awtImage.getHeight(); k++) {
                    for (int z = 0; z < awtImage.getWidth(); z++) {
                        Color color = new Color(awtImage.getRGB(z, k));

                        if ((color.getGreen() < color.getRed()) &&
                                (color.getBlue() < color.getRed())) {
                            awtImage.setRGB(z, k, Color.WHITE.getRGB());
                        }
                    }
                }

                try {
                    ImageIO.write(awtImage, Configuration.getImageFormat(),
                            new File(Configuration.getTempPath() + "/" +
                            cm.get(j).getLabel().replace(' ', '_') + "." +
                            Configuration.getImageFormat()));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                listSavedFiles[c] = Configuration.getTempPath() + "/" +
                        cm.get(j).getLabel().replace(' ', '_') + "." +
                        Configuration.getImageFormat();

                cm.get(j).setFilename(listSavedFiles[c]);
                c++;
            }
        }

        if (Configuration.isDebugMode()) {
            for (int i = 0; i < listSavedFiles.length; i++) {
                System.out.println(listSavedFiles[i]);
            }
        }
    }

    /**
     * @return a saved list files
     */
    public String[] getListFiles() {
        return listSavedFiles;
    }
}

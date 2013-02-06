/*
 * This class provides configuration 
 */
package net.bayzone.supervisorEngine;

/**
 *
 * @author lfoppiano <luca@foppiano.org>
 */
public class Configuration {
    private static String xmlPath;
    private static String imagePath;
    private static String outputPath = null;
    private static String configPath = "/etc/jimageExtractor/configuration.conf";
    //private static String tempPath = "/tmp/supervisor-engine";
    private static String tempPath = "/var/tmp/supervisor-engine";
    private static String scriptsPath = "/usr/share/supervisor-engine/scripts";
//    private static String scriptsPath = "/home/lfoppiano/develop/supervisor/svn/engine/trunk/scripts";
    private static boolean debugMode = false;
    private static String imageFormat = "png";
    
    public static String getXmlPath() {
        return xmlPath;
    }

    public static void setXmlPath(String aXmlPath) {
        xmlPath = aXmlPath;
    }

    public static String getImagePath() {
        return imagePath;
    }

    public static void setImagePath(String aImagePath) {
        imagePath = aImagePath;
    }

    public static String getOutputPath() {
        return outputPath;
    }

    public static void setOutputPath(String aOutputPath) {
        outputPath = aOutputPath;
    }

    public static String getConfigPath() {
        return configPath;
    }

    public static void setConfigPath(String aConfigPath) {
        configPath = aConfigPath;
    }

    public static String getTempPath() {
        return tempPath;
    }

    public static void setTempPath(String aTempPath) {
        tempPath = aTempPath;
    }

    public static String getScriptsPath() {
        return scriptsPath;
    }

    public static void setScriptsPath(String aScriptsPath) {
        scriptsPath = aScriptsPath;
    }

    public static boolean isDebugMode() {
        return debugMode;
    }

    public static void setDebugMode(boolean aDebugMode) {
        debugMode = aDebugMode;
    }

    public static String getImageFormat() {
        return imageFormat;
    }

    public static void setImageFormat(String aImageFormat) {
        imageFormat = aImageFormat;
    }
}

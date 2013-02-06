package net.bayzone.supervisorEngine;

import java.io.*;

class StreamGobbler {

    InputStream is;

    StreamGobbler(InputStream is) {
        this.is = is;
    }

    public String start() {
        String out = "";
        try {
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line = null;
            while ((line = br.readLine()) != null) {
                if (Configuration.isDebugMode()) {
                    System.out.println(">" + line);
                }

                out += line;
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return out;
    }
}

public class Launcher {

    private String output;

    public Launcher() {
        output = new String("");
    }

    public String launch(String command) {
        try {
            Runtime rt = Runtime.getRuntime();
            if (Configuration.isDebugMode()) {
                System.out.println("Execing " + command);
            }
            Process proc = rt.exec(command);

            StreamGobbler outputGobbler = new StreamGobbler(proc.getInputStream());
            output = outputGobbler.start();

            // any error???
            int exitVal = proc.waitFor();
        } catch (Throwable t) {
            t.printStackTrace();
        }

        return output;
    }
}

package com.doos.update;


import com.doos.core.Main;
import com.doos.utils.ApplicationConstants;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.net.ssl.HttpsURLConnection;
import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import java.awt.*;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

/**
 * Created by Eugene Zrazhevsky on 03.11.2016.
 */
public class Updater {
    private static final String githubUrl = "https://api.github.com/repos/benchdoos/WeblocOpener/releases/latest";
    public static File installerFile = null;
    private static HttpsURLConnection connection = null;
    private AppVersion appVersion = null;

    public Updater() {
        try {
            getConnection();
            if (!connection.getDoOutput()) {
                connection.setDoOutput(true);
            }
            if (!connection.getDoInput()) {
                connection.setDoInput(true);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            String input = null;
            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                input = bufferedReader.readLine();

                JsonParser parser = new JsonParser();
                JsonObject root = parser.parse(input).getAsJsonObject();

                appVersion = new AppVersion();
                appVersion.setVersion(root.getAsJsonObject().get("tag_name").getAsString());

                JsonArray asserts = root.getAsJsonArray("assets");
                for (JsonElement assert_ : asserts) {
                    JsonObject userObject = assert_.getAsJsonObject();
                    if (userObject.get("name").getAsString().equals("WeblocOpenerSetup.exe")) {
                        appVersion.setDownloadUrl(userObject.get("browser_download_url").getAsString());
                        appVersion.setSize(userObject.get("size").getAsInt());
                    }
                }
            } catch (NullPointerException e) {
                e.getStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();

            showErrorMessage();
        }
    }

    public static int startUpdate(AppVersion appVersion, JProgressBar progressBar) {
        installerFile = new File(ApplicationConstants.UPDATE_PATH_FILE
                + "WeblocOpenerSetupV" + appVersion.getVersion() + ".exe");
        if (!Thread.currentThread().isInterrupted()) {
            if (!installerFile.exists()) {
                installerFile = downloadNewVersionInstaller(appVersion, progressBar);
            }
            int installationResult = 0;

            try {
                if (!Thread.currentThread().isInterrupted()) {
                    installationResult = update(installerFile);
                }
            } catch (IOException e) {
                if (e.getMessage().contains("CreateProcess error=193")) {
                    installerFile.delete();
                    installerFile = downloadNewVersionInstaller(appVersion, progressBar); //Fixes corrupt file
                }
                return 2;
            }
            deleteFileIfSuccess(installationResult);

            return installationResult;
        } else {
            return 0; //TODO maybe -1?
        }
    }

    private static void deleteFileIfSuccess(int installationResult) {
        if (installationResult == 0) {
            installerFile.deleteOnExit();
        }
    }

    private static int update(File file) throws IOException {
        Runtime runtime = Runtime.getRuntime();
        Process updateProcess;
        updateProcess = runtime.exec(file.getAbsolutePath() + "");

        int result;
        try {
            result = updateProcess.waitFor();
            return result;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return 1;
        }

    }

    private static File downloadNewVersionInstaller(AppVersion appVersion, JProgressBar progressBar) {
               /*try {
            FileUtils.copyURLToFile(new URL(appVersion.getDownloadUrl()),
                    new File(ApplicationConstants.UPDATE_PATH_FILE + appVersion.getVersion() + "setup.exe"));
        } catch (IOException e) {
            e.printStackTrace();
        }*/


        try {
            progressBar.setStringPainted(true);
            BufferedInputStream in = null;
            FileOutputStream fout = null;
            try {

                in = new BufferedInputStream(new URL(appVersion.getDownloadUrl()).openStream());
                fout = new FileOutputStream(installerFile);

                final byte data[] = new byte[1024];
                int count;
                int progress = 0;
                while ((count = in.read(data, 0, 1024)) != -1) {
                    if (Thread.currentThread().isInterrupted()) {
                        installerFile.delete();
                        progressBar.setValue(0);
                        break;
                    } else {
                        fout.write(data, 0, count);
                        progress += count;
                        int prg = (int) (((double) progress / appVersion.getSize()) * 100);

                        progressBar.setValue(prg);
                    }
                }
            } finally {
                if (in != null) {
                    in.close();
                }
                if (fout != null) {
                    fout.close();
                }

                if (Thread.currentThread().isInterrupted()) {
                    installerFile.delete();
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return installerFile;
    }

    private static void openUrl(String url) {
        if (!Desktop.isDesktopSupported()) {
            return;
        }
        Desktop desktop = Desktop.getDesktop();

        try {
            desktop.browse(URI.create(url));
        } catch (IOException e) {
            JOptionPane.showMessageDialog(new Frame(), "URL is corrupt: " + url);
        }

    }

    private void showErrorMessage() {
        String msg = "<HTML><BODY><P>Can not connect to api.github.com <br>Please visit <a href=\"https://github.com/benchdoos/WeblocOpener\">https://github.com/benchdoos/WeblocOpener</P></BODY></HTML>";
        JEditorPane jEditorPane = new JEditorPane();
        jEditorPane.setContentType("text/html");
        jEditorPane.setEditable(false);
        jEditorPane.setHighlighter(null);
        jEditorPane.setEditable(false);
        jEditorPane.getCaret().deinstall(jEditorPane);
        jEditorPane.setBackground(Color.getColor("#EEEEEE"));
        jEditorPane.addHyperlinkListener(new HyperlinkListener() {
            @Override
            public void hyperlinkUpdate(HyperlinkEvent e) {
                if (e.getEventType().equals(HyperlinkEvent.EventType.ACTIVATED)) {
                    openUrl("https://github.com/benchdoos/WeblocOpener");
                }

            }
        });
        jEditorPane.setText(msg);
        if (Main.mode == Main.Mode.NORMAL) {
            JOptionPane.showMessageDialog(null,
                    jEditorPane,
                    "Can not Update", JOptionPane.ERROR_MESSAGE);
        }
    }

    private HttpsURLConnection getConnection() throws IOException {
        URL url = new URL(githubUrl);
        //if (connection == null) {
        connection = (HttpsURLConnection) url.openConnection();
        //}
        return connection;
    }

    public AppVersion getAppVersion() {
        return appVersion;
    }
}
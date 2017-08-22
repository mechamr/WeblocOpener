package com.doos.update_module.update;


import com.doos.commons.core.ApplicationConstants;
import com.doos.commons.core.Translation;
import com.doos.update_module.core.Main;
import com.doos.update_module.gui.UpdateDialog;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.log4j.Logger;
import org.bridj.Pointer;
import org.bridj.PointerIO;
import org.bridj.cpp.com.COMRuntime;
import org.bridj.cpp.com.shell.ITaskbarList3;
import org.bridj.jawt.JAWTUtils;

import javax.net.ssl.HttpsURLConnection;
import javax.swing.*;
import java.io.*;
import java.net.URL;

import static com.doos.commons.core.ApplicationConstants.UPDATE_CODE_INTERRUPT;
import static com.doos.commons.core.ApplicationConstants.WINDOWS_WEBLOCOPENER_SETUP_NAME;
import static com.doos.commons.utils.Logging.getCurrentClassName;
import static com.doos.commons.utils.UserUtils.showErrorMessageToUser;

/**
 * Created by Eugene Zrazhevsky on 03.11.2016.
 */
@SuppressWarnings({"ALL", "ResultOfMethodCallIgnored"})
public class Updater {


    private static final Logger log = Logger.getLogger(getCurrentClassName());

    private static final String GITHUB_URL = "https://api.github.com/repos/benchdoos/WeblocOpener/releases/latest";
    private static final String DEFAULT_ENCODING = "UTF-8";
    public static File installerFile = null;
    private static HttpsURLConnection connection = null;
    private AppVersion appVersion = null;

    private static final String WINDOWS_SETUP_DEFAULT_NAME = "WeblocOpenerSetup.exe";

    private static Translation translation;

    private static String canNotUpdateTitle = "Can not Update";
    private static String canNotUpdateMessage = "Can not connect to api.github.com";


    public Updater() throws IOException, NullPointerException {
        translateMessages();
        createConnection();

        getServerApllicationVersion();
    }




    public void getServerApllicationVersion() throws IOException {
        log.debug("Getting current server apllication version");
            String input;
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream(), DEFAULT_ENCODING));

            input = bufferedReader.readLine();

            JsonParser parser = new JsonParser();
            JsonObject root = parser.parse(input).getAsJsonObject();

        appVersion = new AppVersion();
        formAppVersionFromJson(root);

    }

    private void translateMessages(){
        translation = new Translation("translations/UpdaterBundle") {
            @Override
            public void initTranslations() {
                canNotUpdateTitle = messages.getString("canNotUpdateTitle");
                canNotUpdateMessage = messages.getString("canNotUpdateMessage");
            }
        };
        translation.initTranslations();
    }

    public static void canNotConnectManage(Exception e) {


        log.warn(canNotUpdateMessage, e);
        if (Main.mode != Main.Mode.SILENT) {
            showErrorMessageToUser(null, canNotUpdateTitle, canNotUpdateMessage);
        }
    }

    public void createConnection() {
        try {
            getConnection();
            if (!connection.getDoOutput()) {
                connection.setDoOutput(true);
            }
            if (!connection.getDoInput()) {
                connection.setDoInput(true);
            }

        } catch (IOException e) {
            log.warn("Could not establish connection", e);
        }
    }

    public static int startUpdate(AppVersion appVersion) throws IOException {
        installerFile = new File(ApplicationConstants.UPDATE_PATH_FILE + "WeblocOpenerSetupV"
                + appVersion.getVersion() + ".exe");
        if (!Thread.currentThread().isInterrupted()) {
            if (redownloadOnCorrupt(appVersion)) return UPDATE_CODE_INTERRUPT;

            if (!Thread.currentThread().isInterrupted()) {
                int installationResult = 0;

                try {
                    if (!Thread.currentThread().isInterrupted()) {
                        if (installerFile.length() == appVersion.getSize()) {
                            installationResult = update(installerFile);
                        }
                    }
                } catch (IOException e) {
                    if (e.getMessage().contains("CreateProcess error=193")) {
                        try {
                            if (!Thread.currentThread().isInterrupted()) {
                                installerFile.delete();
                                installerFile = downloadNewVersionInstaller(appVersion); //Fixes corrupt file
                                installationResult = update(installerFile);
                            } else {
                                return UPDATE_CODE_INTERRUPT;
                            }
                        } catch (IOException e1) {
                            if (e1.getMessage().contains("CreateProcess error=193")) {
                                installerFile.delete();
                                return 193;
                            }
                        }
                    }
                }
                deleteFileIfSuccess(installationResult);
                return installationResult;
            } else {
                return UPDATE_CODE_INTERRUPT;
            }

        } else {
            return UPDATE_CODE_INTERRUPT;
        }
    }

    private static boolean redownloadOnCorrupt(AppVersion appVersion) throws IOException {
        if (!installerFile.exists() || installerFile.length() != appVersion.getSize()) {

            installerFile.delete();
            installerFile = downloadNewVersionInstaller(appVersion);
            return true;
        }
        return false;
    }

    private static void deleteFileIfSuccess(int installationResult) {
        if (installationResult == 0) {
            installerFile.deleteOnExit();
        }
    }

    private static int update(File file) throws IOException {
        Runtime runtime = Runtime.getRuntime();
        UpdateDialog.updateDialog.buttonCancel.setEnabled(false);
        Process updateProcess;
        updateProcess = runtime.exec(file.getAbsolutePath() + ApplicationConstants.APP_INSTALL_SILENT_KEY);

        int result = -1;
        try {
            if (!Thread.currentThread().isInterrupted()) {
                result = updateProcess.waitFor();
                log.warn("Update interrupted by user.");
            }
//            UpdateDialog.updateDialog.buttonCancel.setEnabled(true);
            return result;
        } catch (InterruptedException e) {
            log.warn(e);
            return UPDATE_CODE_INTERRUPT;
        }

    }

    private static File downloadNewVersionInstaller(AppVersion appVersion) throws IOException {
        JProgressBar progressBar = null;
        if (UpdateDialog.updateDialog != null) {
            progressBar = UpdateDialog.updateDialog.progressBar1;
        }

        ITaskbarList3 list = null;
        Pointer<?> hwnd;

        try {
            list = COMRuntime.newInstance(ITaskbarList3.class);
        } catch (ClassNotFoundException ignore) {/*WINDOWS<WINDOWS 7*/}

        long hwndVal = JAWTUtils.getNativePeerHandle(UpdateDialog.updateDialog);
        hwnd = Pointer.pointerToAddress(hwndVal, PointerIO.getSizeTInstance());

            if (progressBar != null) {
                progressBar.setStringPainted(true);
            }
            BufferedInputStream in = null;
            FileOutputStream fout = null;
            try {

                in = new BufferedInputStream(new URL(appVersion.getDownloadUrl()).openStream());
                try {
                    fout = new FileOutputStream(installerFile);

                    final int bufferLength = 1024 * 1024;
                    final byte data[] = new byte[bufferLength];
                    int count;
                    int progress = 0;
                    while ((count = in.read(data, 0, bufferLength)) != -1) {
                        if (Thread.currentThread().isInterrupted()) {
                            installerFile.delete();
                            if (progressBar != null) {
                                progressBar.setValue(0);
                                if (list != null) {
                                    //noinspection unchecked
                                    list.SetProgressValue((Pointer) hwnd, progressBar.getValue(),
                                            progressBar.getMaximum());
                                }
                            }
                            break;
                        } else {
                            fout.write(data, 0, count);
                            progress += count;
                            int prg = (int) (((double) progress / appVersion.getSize()) * 100);

                            if (progressBar != null) {
                                progressBar.setValue(prg);
                                if (list != null) {
                                    //noinspection unchecked
                                    list.SetProgressValue((Pointer) hwnd, progressBar.getValue(),
                                            progressBar.getMaximum());
                                }
                            }
                        }
                    }

                } catch (FileNotFoundException e) {
                    if (installerFile.exists() && installerFile.getName().contains("WeblocOpenerSetup")) { //TODO FIX
                        // HERE
                        installerFile.delete();
                        fout = new FileOutputStream(installerFile);
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
                installerFile.deleteOnExit();
            }
            if (list != null) {
                list.Release();
            }
        }

        return installerFile;
    }

    public void formAppVersionFromJson(JsonObject root) {
        log.debug("Parsing json to app version");
        String browser_download_url = "browser_download_url";
        String assets = "assets";
        String name = "name";
        String size = "size";

        appVersion.setVersion(root.getAsJsonObject().get("tag_name").getAsString());

        JsonArray asserts = root.getAsJsonArray(assets);
        for (JsonElement assert_ : asserts) {
            JsonObject userObject = assert_.getAsJsonObject();
            if (userObject.get(name).getAsString().equals(WINDOWS_SETUP_DEFAULT_NAME)) {
                appVersion.setDownloadUrl(userObject.get(browser_download_url).getAsString());
                appVersion.setSize(userObject.get(size).getAsLong());
            }
        }
    }

    private HttpsURLConnection getConnection() throws IOException {
        URL url = new URL(GITHUB_URL);
        log.debug("Creating connection");

        connection = (HttpsURLConnection) url.openConnection();
        connection.setConnectTimeout(500);
        return connection;
    }

    public AppVersion getAppVersion() {
        return appVersion;
    }
}

/*
 * (C) Copyright 2019.  Eugene Zrazhevsky and others.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * Contributors:
 * Eugene Zrazhevsky <eugene.zrazhevsky@gmail.com>
 */

package com.github.benchdoos.weblocopener.utils;

import com.github.benchdoos.weblocopener.core.Main;
import com.github.benchdoos.weblocopener.core.constants.ApplicationConstants;
import org.apache.commons.io.FileExistsException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;
import java.util.Scanner;

public class CoreUtils {
    private static final Logger log = LogManager.getLogger(Logging.getCurrentClassName());

    static String getApplicationVersionFullInformationString() {
        Properties properties = new Properties();
        try {
            properties.load(Main.class.getResourceAsStream("/application.properties"));
            String name = properties.getProperty("application.name");
            String version = properties.getProperty("application.version");
            String build = properties.getProperty("application.build");

            if (version != null && build != null) {
                return name + " v." + version + "." + build;
            } else {
                return null;
            }
        } catch (Exception e) {
            log.warn("Could not load application version info", e);
            return null;
        }
    }


    public static String getApplicationVersionString() {
        Properties properties = new Properties();
        try {
            properties.load(Main.class.getResourceAsStream("/application.properties"));
            String version = properties.getProperty("application.version");
            String build = properties.getProperty("application.build");

            if (version != null && build != null) {
                return version + "." + build.split(" ")[0];
            } else {
                return null;
            }
        } catch (Exception e) {
            log.warn("Could not load application version info", e);
            return null;
        }
    }

    /**
     * Enables LookAndFeel for current OS.
     *
     * @see javax.swing.UIManager.LookAndFeelInfo
     */
    public static void enableLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            log.debug("Look and Feel enabled");
        } catch (Exception e) {
            log.warn("Could not enable look and feel", e);
        }
    }

    /**
     * @param file to get it's name
     * @return filename for file without extension
     * */
    public static String getFileName(File file) {
        if (file == null) {
            throw new IllegalArgumentException("File can not be null");
        }
        final int index = file.getName().lastIndexOf(".");

        if (index > 0) {
            char[] chars = new char[index];

            file.getName().getChars(0, index, chars, 0);

            return new String(chars);
        } else {
            return file.getName();
        }
    }

    public static String fixFileName(String fileName) {
        if (fileName != null) {
            fileName = fileName.replaceAll("#", "")
                    .replaceAll("/", "")
                    .replaceAll("\\\\", "")
                    .replaceAll("/", "")
                    .replaceAll(":", "")
                    .replaceAll("\"", "")
                    .replaceAll("<", "")
                    .replaceAll(">", "")
                    .replaceAll("\\|", "");
        }
        return fileName;
    }

    public static String getPageTitle(String url) throws IOException {
        if (url == null) {
            throw new IllegalArgumentException("URL can not be null");
        }
        if (url.isEmpty()) {
            throw new IllegalArgumentException("URL can not be empty");
        }
        try (InputStream response = new URL(url).openStream()) {

            Scanner scanner = new Scanner(response, ApplicationConstants.DEFAULT_APPLICATION_CHARSET);
            String responseBody = scanner.useDelimiter("\\A").next();

            return responseBody.substring(responseBody.indexOf("<title>") + 7, responseBody.indexOf("</title>"));
        } catch (IOException e) {
            throw new IOException("Can not load page title", e);
        }
    }

    public static File renameFile(File oldFile, String fileName) throws FileExistsException {

        if (oldFile == null) {
            throw new IllegalArgumentException("Can not rename file [null]: it is null!");
        }
        if (!oldFile.exists()) {
            throw new IllegalArgumentException("Can not rename file [" + oldFile + "]: does not exist!");
        }
        if (fileName == null) {
            throw new IllegalArgumentException("Can not rename file [" + oldFile + "]: new filename can not be null");
        }
        if (fileName.isEmpty()) {
            throw new IllegalArgumentException("Can not rename file [" + oldFile + "]: new filename can not be empty");
        }


        fileName = fixFileName(fileName);

        File folder = oldFile.getParentFile();
        File file = new File(folder.getAbsolutePath() + File.separator + fileName);

        if (file.exists()) {
            throw new FileExistsException("Can not rename file [" + oldFile + "]: file [" + file + "] already exists!");
        }


        log.debug("Renaming file {} to {}", oldFile, fileName);

        final boolean b = oldFile.renameTo(file);
        if (b) {
            return file;
        } else {
            throw new RuntimeException("Can not rename file for some reason: " + oldFile + " to " + file);
        }

    }

    public static void copyImageToClipBoard(BufferedImage image) {
        CopyImageToClipBoard ci = new CopyImageToClipBoard();
        ci.copyImage(image);
    }

    public static BufferedImage resize(BufferedImage img, int widht, int height) {
        Image tmp = img.getScaledInstance(widht, height, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(widht, height, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return dimg;
    }
}

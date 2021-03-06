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

import com.github.benchdoos.weblocopener.core.Translation;
import com.github.benchdoos.weblocopener.core.constants.ApplicationConstants;
import com.github.benchdoos.weblocopener.core.constants.StringConstants;
import com.github.benchdoos.weblocopener.preferences.PreferencesManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Created by Eugene Zrazhevsky on 03.12.2016.
 */
public class UserUtils {
    private static final Logger log = LogManager.getLogger(Logging.getCurrentClassName());
    private static final int MAXIMUM_MESSAGE_SIZE = 150;

    private static void createTrayMessage(String title, String message, TrayIcon.MessageType messageType) {
        final int delay = 7000;
        final TrayIcon trayIcon = new TrayIcon(Toolkit.getDefaultToolkit().getImage(
                UserUtils.class.getResource("/images/balloonIcon256.png")));
        trayIcon.setImageAutoSize(true);

        try {
            final SystemTray tray = SystemTray.getSystemTray();
            tray.add(trayIcon);
            trayIcon.displayMessage(title, message, messageType);

            PopupMenu menu = new PopupMenu();

            MenuItem close = new MenuItem(Translation.getTranslatedString("CommonsBundle", "closeButton"));
            close.addActionListener(e -> tray.remove(trayIcon));

            menu.add(close);

            trayIcon.setPopupMenu(menu);

            Timer timer = new Timer(delay, e -> tray.remove(trayIcon));
            timer.setRepeats(false);
            timer.start();
        } catch (AWTException e) {
            log.warn("Could not add icon to tray", e);
        }
    }

    public static void openWebUrl(URL url) {
        if (!Desktop.isDesktopSupported()) {
            return;
        }
        Desktop desktop = Desktop.getDesktop();

        try {
            desktop.browse(url.toURI());
        } catch (IOException | URISyntaxException e) {
            final String message = Translation.getTranslatedString("CommonsBundle", "urlIsCorruptMessage");
            showErrorMessageToUser(null, message, message + url);
        }
    }

    public static void openWebUrl(String url) {
        if (!Desktop.isDesktopSupported()) {
            return;
        }
        Desktop desktop = Desktop.getDesktop();

        try {
            desktop.browse(URI.create(url));
        } catch (IOException e) {
            final String message = Translation.getTranslatedString("CommonsBundle", "urlIsCorruptMessage");
            showErrorMessageToUser(null, message, message + url);
        }
    }

    private static void showDefaultSystemErrorMessage(Component parentComponent, String title, String message, int messageLevel) {
        JEditorPane jEditorPane = new JEditorPane();
        jEditorPane.setContentType("text/html");
        jEditorPane.setEditable(false);
        jEditorPane.setHighlighter(null);
        jEditorPane.setEditable(false);
        jEditorPane.getCaret().deinstall(jEditorPane);
        jEditorPane.setBackground(Color.getColor("#EEEEEE"));
        jEditorPane.addHyperlinkListener(e -> {
            if (e.getEventType().equals(HyperlinkEvent.EventType.ACTIVATED)) {
                openWebUrl(StringConstants.UPDATE_WEB_URL);
            }

        });
        jEditorPane.setText(message);
        int messageType = JOptionPane.ERROR_MESSAGE;
        if ((messageLevel == JOptionPane.PLAIN_MESSAGE) || (messageLevel == JOptionPane.INFORMATION_MESSAGE)) {
            messageType = JOptionPane.INFORMATION_MESSAGE;
        } else {
            if (messageLevel == JOptionPane.WARNING_MESSAGE) {
                messageType = JOptionPane.WARNING_MESSAGE;
            }
        }

        if (title == null) {
            title = "";
        }

        JOptionPane.showMessageDialog(parentComponent,
                jEditorPane,
                "[" + ApplicationConstants.WEBLOCOPENER_APPLICATION_NAME + "] " + title, messageType);
    }

    public static void showErrorMessageToUser(Component parentComponent, String title, String message) {
        FrameUtils.shakeFrame(parentComponent);
        showMessage(parentComponent, title, message, JOptionPane.ERROR_MESSAGE);
    }

    private static void showMessage(Component parentComponent, String title, String message, int messageLevel) {
        if (message.length() > MAXIMUM_MESSAGE_SIZE) {
            message = message.substring(0, Math.min(message.length(), MAXIMUM_MESSAGE_SIZE)) + "...";
        }

        String msg;
        if (messageLevel == JOptionPane.ERROR_MESSAGE) {

            msg = "<HTML><BODY><P>" + message + " <br>" + Translation.getTranslatedString("CommonsBundle", "pleaseVisitMessage") + " " +
                    "<a href=\"" + StringConstants.UPDATE_WEB_URL + "\">" + StringConstants.UPDATE_WEB_URL + "</P></BODY></HTML>";
        } else {
            msg = message;
        }
        showDefaultSystemErrorMessage(parentComponent, title, msg, messageLevel);
    }

    public static void showTrayMessage(String title, String message, TrayIcon.MessageType messageType) {
        if (PreferencesManager.isNotificationsShown()) {
            createTrayMessage(title, message, messageType);
        }
    }

    public static void showWarningMessageToUser(Component parentComponent, String title, String message) {
        FrameUtils.shakeFrame(parentComponent);
        showMessage(parentComponent, title, message, JOptionPane.WARNING_MESSAGE);
    }
}

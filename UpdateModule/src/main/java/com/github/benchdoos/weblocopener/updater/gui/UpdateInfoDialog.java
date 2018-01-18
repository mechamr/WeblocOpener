/*
 * Copyright 2018 Eugeny Zrazhevsky
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.benchdoos.weblocopener.updater.gui;

import com.github.benchdoos.weblocopener.commons.utils.FrameUtils;
import com.github.benchdoos.weblocopener.updater.update.AppVersion;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class UpdateInfoDialog extends JDialog {
    private AppVersion appVersion;
    private JPanel contentPane;
    private JButton buttonOK;
    private JTextPane textPane;

    UpdateInfoDialog(AppVersion appVersion) {
        this.appVersion = appVersion;
        createGUI();
    }

    private void createGUI() {
        setTitle("Info about update - " + appVersion.getVersion());
        setIconImage(Toolkit.getDefaultToolkit().getImage(UpdateInfoDialog.class.getResource("/infoIcon16.png")));

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        getRootPane().registerKeyboardAction(e -> {
            onOK();
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);


        textPane.setText(generatePageForDisplay(appVersion.getUpdateTitle(), appVersion.getUpdateInfo()));
        // textPane.setHighlighter(null); // TODO what to do? Stay? Or give user to select text???

        textPane.registerKeyboardAction(e -> {
            onOK();
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), JComponent.WHEN_FOCUSED);
        buttonOK.addActionListener(e -> onOK());

        setMinimumSize(new Dimension(550, 300));
        setSize(550, 300);

        textPane.setCaretPosition(0);
        setLocation(FrameUtils.getFrameOnCenterLocationPoint(this));
        setVisible(true);
    }

    private String generatePageForDisplay(String updateTitle, String updateInfo) {
        String style = "<style>" +
                "code {" +
                "    padding: 0;" +
                "    padding-top: 0.2em;" +
                "    padding-bottom: 0.2em;" +
                "    margin: 0;" +
                "    font-size: 85%;" +
                "    background-color: #f3f3f3;" +
                "    border-radius: 3px;" +
                "}" +
                "</style>";
        String defaultHead = "<html><head>" + style + "</head><body>";

        String title = "<center style=\"font-size:14px; color:#4f7ece; padding-bottom:10px;\">" + updateTitle + "</center>";
        String defaultFooter = "</body></html>";
        updateInfo = updateInfo.replaceAll("\n", "<br>");
        updateInfo = "<p style=\"font-family:'Open Sans'; font-size:12px; padding:0; margin:0;\">" + updateInfo + "</p>";

        return defaultHead + title + updateInfo + defaultFooter;
    }

    private void onOK() {
        dispose();
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        contentPane = new JPanel();
        contentPane.setLayout(new GridLayoutManager(2, 1, new Insets(10, 10, 10, 10), -1, -1));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        contentPane.add(panel1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, 1, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        panel1.add(spacer1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(panel2, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        buttonOK = new JButton();
        buttonOK.setText("OK");
        panel2.add(buttonOK, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        contentPane.add(panel3, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JScrollPane scrollPane1 = new JScrollPane();
        panel3.add(scrollPane1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        textPane = new JTextPane();
        textPane.setContentType("text/html");
        textPane.setEditable(false);
        scrollPane1.setViewportView(textPane);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return contentPane;
    }
}

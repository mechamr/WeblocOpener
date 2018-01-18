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

package com.github.benchdoos.weblocopener.weblocOpener.gui;

import com.github.benchdoos.weblocopener.commons.core.Translation;
import com.github.benchdoos.weblocopener.commons.utils.FrameUtils;
import com.github.benchdoos.weblocopener.weblocOpener.service.UrlsProceed;
import com.github.benchdoos.weblocopener.weblocOpener.service.gui.MousePickListener;
import com.google.zxing.WriterException;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ResourceBundle;

public class ShowQrDialog extends JFrame {
    BufferedImage qrCodeImage = null;
    String title = "QR-Code for .webloc";
    private String url;
    private JPanel contentPane;
    private ImagePanel imagePanel;
    private JButton openButton;


    public ShowQrDialog(String url, BufferedImage qrCodeImage) throws IOException, WriterException {
        this.qrCodeImage = qrCodeImage;
        this.url = url;
        $$$setupUI$$$();
        initGui();
        openButton.addActionListener(e -> {
            UrlsProceed.openUrl(url);
            dispose();
        });
    }

    private void initGui() {
        translateDialog();

        setTitle(title);
        setIconImage(Toolkit.getDefaultToolkit().getImage(ShowQrDialog.class.getResource("/balloonIcon64.png")));


        setContentPane(contentPane);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        imagePanel.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(
                KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        pack();
        setResizable(false);

        setLocation(FrameUtils.getFrameOnCenterLocationPoint(this));
    }

    private void translateDialog() {
        Translation translation = new Translation("translations/ShowQrDialogBundle") {
            @Override
            public void initTranslations() {
                title = messages.getString("windowTitle");
            }
        };
        translation.initTranslations();
    }

    private void createUIComponents() {
        createImagePanel();
    }

    private void createImagePanel() {
        imagePanel = new ImagePanel(qrCodeImage);
        MousePickListener mousePickListener = new MousePickListener(this);

        imagePanel.addMouseListener(mousePickListener.getMouseAdapter);
        imagePanel.addMouseMotionListener(mousePickListener.getMouseMotionAdapter);
    }

    private void onCancel() {
        dispose();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        createUIComponents();
        contentPane = new JPanel();
        contentPane.setLayout(new GridLayoutManager(2, 2, new Insets(0, 0, 0, 0), -1, -1));
        contentPane.setBackground(new Color(-1));
        contentPane.add(imagePanel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        contentPane.add(spacer1, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 1, new Insets(0, 10, 5, 10), -1, -1));
        panel1.setBackground(new Color(-1));
        contentPane.add(panel1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        openButton = new JButton();
        this.$$$loadButtonText$$$(openButton, ResourceBundle.getBundle("translations/ShowQrDialogBundle").getString("openButton"));
        panel1.add(openButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    private void $$$loadButtonText$$$(AbstractButton component, String text) {
        StringBuffer result = new StringBuffer();
        boolean haveMnemonic = false;
        char mnemonic = '\0';
        int mnemonicIndex = -1;
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == '&') {
                i++;
                if (i == text.length()) break;
                if (!haveMnemonic && text.charAt(i) != '&') {
                    haveMnemonic = true;
                    mnemonic = text.charAt(i);
                    mnemonicIndex = result.length();
                }
            }
            result.append(text.charAt(i));
        }
        component.setText(result.toString());
        if (haveMnemonic) {
            component.setMnemonic(mnemonic);
            component.setDisplayedMnemonicIndex(mnemonicIndex);
        }
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return contentPane;
    }
}

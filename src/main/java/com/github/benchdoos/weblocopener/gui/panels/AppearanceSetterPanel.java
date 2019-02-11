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

package com.github.benchdoos.weblocopener.gui.panels;

import com.github.benchdoos.weblocopener.preferences.PreferencesManager;
import com.github.benchdoos.weblocopener.service.gui.darkMode.DarkModeValue;
import com.github.benchdoos.weblocopener.service.gui.darkMode.Location;
import com.github.benchdoos.weblocopener.utils.Logging;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.DateFormatter;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

public class AppearanceSetterPanel<S> extends JPanel implements SettingsPanel {
    private static final Logger log = LogManager.getLogger(Logging.getCurrentClassName());

    private JPanel contentPane;
    private JPanel byLocationPanel;
    private JComboBox<Location> locationComboBox;
    private JLabel locationStatusLabel;
    private JRadioButton disabledDarkModeRadioButton;
    private JRadioButton alwaysDarkModeRadioButton;
    private JRadioButton byTimeDarkModeRadioButton;
    private JRadioButton byLocationDarkModeRadioButton;
    private JFormattedTextField beginningTextField;
    private JFormattedTextField endingTextField;
    private JPanel byTimePanel;
    private JLabel sunsetValueLabel;
    private JLabel sunriseValueLabel;
    private JLabel foundLocationLabel;

    public AppearanceSetterPanel() {
        initGui();
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
        contentPane.setLayout(new GridLayoutManager(1, 1, new Insets(10, 10, 10, 10), -1, -1));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(4, 2, new Insets(0, 0, 0, 0), -1, -1));
        contentPane.add(panel1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        this.$$$loadLabelText$$$(label1, ResourceBundle.getBundle("translations/AppearanceSetterPanelBundle").getString("darkMode"));
        panel1.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        panel1.add(spacer1, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        byLocationPanel = new JPanel();
        byLocationPanel.setLayout(new GridLayoutManager(3, 3, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(byLocationPanel, new GridConstraints(3, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        locationComboBox = new JComboBox();
        locationComboBox.setEditable(true);
        locationComboBox.setMaximumRowCount(10);
        byLocationPanel.add(locationComboBox, new GridConstraints(0, 0, 1, 3, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, new Dimension(400, -1), 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 6, new Insets(0, 0, 0, 0), -1, -1));
        byLocationPanel.add(panel2, new GridConstraints(2, 0, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        this.$$$loadLabelText$$$(label2, ResourceBundle.getBundle("translations/AppearanceSetterPanelBundle").getString("TimeLabel"));
        panel2.add(label2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        this.$$$loadLabelText$$$(label3, ResourceBundle.getBundle("translations/AppearanceSetterPanelBundle").getString("sunsetLabel"));
        panel2.add(label3, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        sunsetValueLabel = new JLabel();
        Font sunsetValueLabelFont = this.$$$getFont$$$(null, Font.BOLD, -1, sunsetValueLabel.getFont());
        if (sunsetValueLabelFont != null) sunsetValueLabel.setFont(sunsetValueLabelFont);
        sunsetValueLabel.setText("19:00");
        panel2.add(sunsetValueLabel, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label4 = new JLabel();
        this.$$$loadLabelText$$$(label4, ResourceBundle.getBundle("translations/AppearanceSetterPanelBundle").getString("sunriseLabel"));
        panel2.add(label4, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        sunriseValueLabel = new JLabel();
        Font sunriseValueLabelFont = this.$$$getFont$$$(null, Font.BOLD, -1, sunriseValueLabel.getFont());
        if (sunriseValueLabelFont != null) sunriseValueLabel.setFont(sunriseValueLabelFont);
        sunriseValueLabel.setText("7:00");
        panel2.add(sunriseValueLabel, new GridConstraints(0, 4, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        panel2.add(spacer2, new GridConstraints(0, 5, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        foundLocationLabel = new JLabel();
        this.$$$loadLabelText$$$(foundLocationLabel, ResourceBundle.getBundle("translations/AppearanceSetterPanelBundle").getString("unknownLocation"));
        byLocationPanel.add(foundLocationLabel, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        locationStatusLabel = new JLabel();
        locationStatusLabel.setIcon(new ImageIcon(getClass().getResource("/images/emojiCross16.png")));
        byLocationPanel.add(locationStatusLabel, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, new Dimension(16, 16), new Dimension(16, 16), new Dimension(16, 16), 0, false));
        final Spacer spacer3 = new Spacer();
        byLocationPanel.add(spacer3, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        byTimePanel = new JPanel();
        byTimePanel.setLayout(new GridLayoutManager(2, 4, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(byTimePanel, new GridConstraints(2, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        beginningTextField = new JFormattedTextField();
        beginningTextField.setText("19:00");
        byTimePanel.add(beginningTextField, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(50, -1), null, 0, false));
        final JLabel label5 = new JLabel();
        this.$$$loadLabelText$$$(label5, ResourceBundle.getBundle("translations/AppearanceSetterPanelBundle").getString("beginning"));
        byTimePanel.add(label5, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label6 = new JLabel();
        this.$$$loadLabelText$$$(label6, ResourceBundle.getBundle("translations/AppearanceSetterPanelBundle").getString("ending"));
        byTimePanel.add(label6, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        endingTextField = new JFormattedTextField();
        endingTextField.setText("7:00");
        byTimePanel.add(endingTextField, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(50, -1), null, 0, false));
        final JLabel label7 = new JLabel();
        label7.setIcon(new ImageIcon(getClass().getResource("/images/emojiCross16.png")));
        label7.setText("");
        byTimePanel.add(label7, new GridConstraints(0, 2, 2, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, new Dimension(16, 16), new Dimension(16, 16), new Dimension(16, 16), 0, false));
        final Spacer spacer4 = new Spacer();
        byTimePanel.add(spacer4, new GridConstraints(0, 3, 2, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(2, 3, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(panel3, new GridConstraints(1, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        disabledDarkModeRadioButton = new JRadioButton();
        disabledDarkModeRadioButton.setSelected(true);
        this.$$$loadButtonText$$$(disabledDarkModeRadioButton, ResourceBundle.getBundle("translations/AppearanceSetterPanelBundle").getString("disabledName"));
        panel3.add(disabledDarkModeRadioButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        byTimeDarkModeRadioButton = new JRadioButton();
        this.$$$loadButtonText$$$(byTimeDarkModeRadioButton, ResourceBundle.getBundle("translations/AppearanceSetterPanelBundle").getString("byTimeName"));
        panel3.add(byTimeDarkModeRadioButton, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        alwaysDarkModeRadioButton = new JRadioButton();
        this.$$$loadButtonText$$$(alwaysDarkModeRadioButton, ResourceBundle.getBundle("translations/AppearanceSetterPanelBundle").getString("alwaysName"));
        panel3.add(alwaysDarkModeRadioButton, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        byLocationDarkModeRadioButton = new JRadioButton();
        this.$$$loadButtonText$$$(byLocationDarkModeRadioButton, ResourceBundle.getBundle("translations/AppearanceSetterPanelBundle").getString("byLocationName"));
        panel3.add(byLocationDarkModeRadioButton, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer5 = new Spacer();
        panel3.add(spacer5, new GridConstraints(0, 2, 2, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        ButtonGroup buttonGroup;
        buttonGroup = new ButtonGroup();
        buttonGroup.add(disabledDarkModeRadioButton);
        buttonGroup.add(alwaysDarkModeRadioButton);
        buttonGroup.add(byTimeDarkModeRadioButton);
        buttonGroup.add(byLocationDarkModeRadioButton);
        buttonGroup.add(byTimeDarkModeRadioButton);
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        return new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
    }

    /**
     * @noinspection ALL
     */
    private void $$$loadLabelText$$$(JLabel component, String text) {
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
            component.setDisplayedMnemonic(mnemonic);
            component.setDisplayedMnemonicIndex(mnemonicIndex);
        }
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

    private boolean byLocationSettingsValid() {
        return true;
    }

    private boolean byTimeSettingsValid() {
        final String begins = beginningTextField.getText();
        final String ends = endingTextField.getText();

        final Date beginTime = getTimeFromString(begins);
        final Date endTime = getTimeFromString(ends);

        return beginTime.after(endTime);
    }

    private Date getTimeFromString(String begins) {
        final String[] splitBegins = begins.split(":");

        final Calendar up = Calendar.getInstance();


        up.set(Calendar.HOUR, Integer.parseInt(splitBegins[0]));
        up.set(Calendar.MINUTE, Integer.parseInt(splitBegins[1]));

        return up.getTime();
    }

    private void initComboBoxes() {
        locationComboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                if (value instanceof Location) {
                    return super.getListCellRendererComponent(list, ((Location) value).getAddress(), index, isSelected, cellHasFocus);
                }
                return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            }
        });

        locationComboBox.setEditor(new LocationComboBoxEditor());

        final JTextComponent tc = (JTextComponent) locationComboBox.getEditor().getEditorComponent();
        tc.getDocument().addDocumentListener(new DocumentListener() {
            String text = null;
            Timer timer = new Timer(1000, e -> {
                if (text != null && !text.isEmpty()) {
                    //go to api
                    System.out.println("text is: " + text);
                }
            });

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateData(e);
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                updateData(e);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateData(e);
            }

            private void updateData(DocumentEvent e) {
                text = tc.getText();
                timer.setRepeats(false);
                timer.restart();
            }
        });
    }

    private void initDarkModeButtonGroup() {
        disabledDarkModeRadioButton.addChangeListener(e -> {
            if (disabledDarkModeRadioButton.isSelected()) {
                byTimePanel.setVisible(false);
                byLocationPanel.setVisible(false);
            }
        });
        alwaysDarkModeRadioButton.addChangeListener(e -> {
            if (alwaysDarkModeRadioButton.isSelected()) {
                byTimePanel.setVisible(false);
                byLocationPanel.setVisible(false);
            }
        });
        byTimeDarkModeRadioButton.addChangeListener(e -> {
            if (byTimeDarkModeRadioButton.isSelected()) {
                byTimePanel.setVisible(true);
                byLocationPanel.setVisible(false);
            }
        });
        byLocationDarkModeRadioButton.addChangeListener(e -> {
            if (byLocationDarkModeRadioButton.isSelected()) {
                byTimePanel.setVisible(false);
                byLocationPanel.setVisible(true);
            }
        });
    }

    private void initDefaultsForPanels() {
        byTimePanel.setVisible(false);
        byLocationPanel.setVisible(false);

        beginningTextField.setText("19:00");
        endingTextField.setText("7:00");
    }

    private void initFormattedTextFields() {
        final DateFormatter formatter = new DateFormatter(new SimpleDateFormat("HH:mm"));
        beginningTextField.setFormatterFactory(new DefaultFormatterFactory(formatter));
        endingTextField.setFormatterFactory(new DefaultFormatterFactory(formatter));
    }

    private void initGui() {
        setLayout(new GridLayout());
        add(contentPane);

        initFormattedTextFields();

        initDefaultsForPanels();

        initComboBoxes();

        initDarkModeButtonGroup();
    }

    private void loadByLocationSettings(DarkModeValue realDarkModeValue) {
        byLocationDarkModeRadioButton.setSelected(true);
        locationComboBox.setSelectedItem(realDarkModeValue.getLocation());
    }

    private void loadByTimeSettings(DarkModeValue realDarkModeValue) {
        byTimeDarkModeRadioButton.setSelected(true);

        final Date startTime = realDarkModeValue.getNext().getStart();
        final Date endTime = realDarkModeValue.getPrevious().getEnd();

        DateFormat dateFormat = new SimpleDateFormat("HH:mm");

        String startTimeString = dateFormat.format(startTime);
        beginningTextField.setText(startTimeString);
        String endTimeString = dateFormat.format(endTime);
        endingTextField.setText(endTimeString);
    }

    @Override
    public void loadSettings() {
        final Object object = PreferencesManager.getRealDarkMode();
        if (object instanceof Boolean) {
            Boolean b = (Boolean) object;
            if (b) {
                alwaysDarkModeRadioButton.setSelected(true);
            } else {
                disabledDarkModeRadioButton.setSelected(true);
            }
        } else if (object instanceof DarkModeValue) {
            final DarkModeValue realDarkModeValue = (DarkModeValue) object;
            if (realDarkModeValue.getNext() != null && realDarkModeValue.getPrevious() != null) {
                loadByTimeSettings(realDarkModeValue);
            } else if (realDarkModeValue.getLocation() != null) {
                loadByLocationSettings(realDarkModeValue);
            }
        }
    }

    private void saveByTimeSettings() {
        final String begins = beginningTextField.getText();
        final String ends = endingTextField.getText();
        final String value = begins + ";" + ends;
        PreferencesManager.setDarkMode(value);
        log.info("Saving settings: dark mode: {}", value);
    }

    @Override
    public void saveSettings() {
        if (disabledDarkModeRadioButton.isSelected()) {
            final String value = PreferencesManager.DARK_MODE.DISABLED.toString();
            PreferencesManager.setDarkMode(value);
            log.info("Saving settings: dark mode: {}", value);
        } else if (alwaysDarkModeRadioButton.isSelected()) {
            final String value = PreferencesManager.DARK_MODE.ALWAYS.toString();
            PreferencesManager.setDarkMode(value);
            log.info("Saving settings: dark mode: {}", value);
        } else if (byTimeDarkModeRadioButton.isSelected()) {
            if (byTimeSettingsValid()) {
                saveByTimeSettings();
            } else {
                final String begins = beginningTextField.getText();
                final String ends = endingTextField.getText();
                log.warn("Saving settings: dark mode: declined, settings set are not valid - begin time: {} is not after ending time: {}", begins, ends);
            }
        } else {
            if (byLocationSettingsValid()) {
                //save here
                PreferencesManager.setDarkMode("Майкоп, городской округ Майкоп, Республика Адыгея, Южный федеральный округ, РФ|44.6062079;40.104053");
            }
        }
    }

    private class LocationComboBoxEditor implements ComboBoxEditor {
        JTextField textField;

        LocationComboBoxEditor() {
            this.textField = new JTextField();
        }

        @Override
        public void addActionListener(ActionListener l) {
            textField.addActionListener(l);
        }

        @Override
        public Component getEditorComponent() {
            return textField;
        }

        @Override
        public Object getItem() {
            return textField.getText();
        }

        @Override
        public void setItem(Object object) {
            if (object instanceof Location) {
                textField.setText(((Location) object).getAddress());
            } else {
                textField.setText(object == null ? null : object.toString());
            }
        }

        @Override
        public void removeActionListener(ActionListener l) {
            textField.removeActionListener(l);
        }

        @Override
        public void selectAll() {
            textField.selectAll();
        }
    }
}
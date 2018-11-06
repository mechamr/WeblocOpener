/*
 * (C) Copyright 2018.  Eugene Zrazhevsky and others.
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

package com.github.benchdoos.weblocopener.preferences;

import com.github.benchdoos.weblocopener.core.constants.ApplicationConstants;
import com.github.benchdoos.weblocopener.core.constants.SettingsConstants;

import java.util.prefs.Preferences;

/**
 * Created by Eugene Zrazhevsky on 19.11.2016.
 */
public class PreferencesManager {
    public static final String KEY_AUTO_UPDATE = "auto_update_enabled";
    private static final String DEV_MODE_KEY = "dev_mode";
    private static final String KEY_BROWSER = "browser";

    private static final Preferences PREFERENCES = Preferences.userRoot().node(ApplicationConstants.WEBLOCOPENER_APPLICATION_NAME.toLowerCase());

    public static String getBrowserValue() {
        final String value = PREFERENCES.get(KEY_BROWSER, SettingsConstants.BROWSER_DEFAULT_VALUE);
        if (value.isEmpty()) {
            return SettingsConstants.BROWSER_DEFAULT_VALUE;
        }
        return value;
    }

    public static void setBrowserValue(String callPath) {
        if (!callPath.isEmpty()) {
            PREFERENCES.put(KEY_BROWSER, callPath);
        }
    }

    public static boolean isAutoUpdateActive() {
        return PREFERENCES.getBoolean(KEY_AUTO_UPDATE, SettingsConstants.IS_APP_AUTO_UPDATE_DEFAULT_VALUE);
    }

    public static void setAutoUpdateActive(boolean autoUpdateActive) {
        PREFERENCES.putBoolean(KEY_AUTO_UPDATE, autoUpdateActive);
    }

    public static boolean isDevMode() {
        return PREFERENCES.getBoolean(DEV_MODE_KEY, false);
    }
}

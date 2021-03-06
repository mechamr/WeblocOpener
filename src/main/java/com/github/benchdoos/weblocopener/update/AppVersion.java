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

package com.github.benchdoos.weblocopener.update;

/**
 * Created by Eugene Zrazhevsky on 03.11.2016.
 */
public class AppVersion {
    private String version;
    private String downloadUrl;
    private long size;
    private String updateTitle;
    private String updateInfo;

    public String getDownloadUrl() {
        return downloadUrl;
    }

    void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getUpdateTitle() {
        return updateTitle;
    }

    void setUpdateTitle(String updateTitle) {
        this.updateTitle = updateTitle;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version.replace("v", "");
    }

    public long getSize() {
        return size;
    }


    public void setSize(long size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "AppVersion: {" + version + "\ndownload:[" + downloadUrl + "], size: " + getKilobyteFromByte() + "kb}" +
                "\n" + updateInfo;
    }

    private long getKilobyteFromByte() {
        return size / 1024;
    }

    public String getUpdateInfo() {
        return updateInfo;
    }

    void setUpdateInfo(String updateInfo) {
        this.updateInfo = updateInfo;
    }
}

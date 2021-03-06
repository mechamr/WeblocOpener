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

package com.github.benchdoos.weblocopener.service.links;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public interface AbstractLink {
    /**
     * Creates link of current file format from url
     *
     * @param file where should be {@code url} stored
     * @param url  that should be stored at {@code file}
     */
    void createLink(File file, URL url) throws IOException;

    /**
     * Gets lin from the file into URL
     *
     * @param file file with link
     * @return url in the file
     */
    URL getUrl(File file) throws IOException;

}

/*
 * AnVerGen - Android Version Generator
 * Copyright 2016 Yan QiDong
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.no_creativity.anvergen

/**
 * This is a simply version generator for Android applications.
 * <p>
 * It works only in a git repository.
 *
 * @author yanqd0
 */
public class Ver {
    /**
     * The versionCode is set to the commit count of git.
     *
     * @return The number of commits in git history.
     */
    public static int generateVersionCode() {
        return Git.calculateCommitCount()
    }

    /**
     * The versionName is set to "$version.$subVersion.$date.$shortSha1".
     *
     * <ul>
     * <li><code>$version</code> is the last git tag. If there is no git tag,
     * it is {@link Git#DEFAULT_TAG}.</li>
     * <li><code>$subVersion</code> is the commit count from last git tag.</li>
     * <li><code>$date</code> is the formatted UTC time of the latest commit.</li>
     * <li><code>$shortSha1</code> is a substring of SHA1 of current git object.</li>
     * </ul>
     *
     * For example: 0.1.9.161001.fe247f2
     *
     * @return The formatted String with git and time information.
     */
    public static String generateVersionName() {
        def version = Git.getLatestTag()
        def subVersion = Git.calculateCommitCount(version)
        String date = Git.getCommitDate().format("yyMMdd")
        String shortSha1 = Git.getShortSha1()
        return "$version.$subVersion.$date.$shortSha1"
    }
}

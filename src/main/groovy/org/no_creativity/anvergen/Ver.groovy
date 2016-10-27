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

import com.sun.istack.internal.NotNull
import groovy.transform.TypeChecked

import static org.no_creativity.anvergen.Git.DEFAULT_TAG

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
    @TypeChecked
    public static int generateVersionCode(String commit = 'HEAD')
            throws IllegalArgumentException {
        return Git.calculateCommitCount(null, commit)
    }

    /**
     * The versionName is set to the result of <code>git describe</code>.
     *
     * @return The formatted String with git and time information.
     */
    @NotNull
    @TypeChecked
    public static String generateVersionName(String commit = 'HEAD')
            throws IllegalArgumentException {
        def describe = Git.getGitDescribe(commit)
        def tag = Git.getLatestTag(commit)
        if (!tag.equals(DEFAULT_TAG)) {
            return describe
        } else {
            return "${DEFAULT_TAG}-${Git.calculateCommitCount(null, commit)}-g$describe"
        }
    }
}

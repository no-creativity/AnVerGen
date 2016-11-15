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
 * <i>It works only in a git repository.</i>
 * <p>
 * The version code will be the commit count of git, and the version name will be a result of <code>git describe</code>.
 * <p>
 * <h4>How to name a git tag?</h4>
 * See: <a href="http://semver.org/">Semantic Versioning</a>
 * <h4>How to use this class?</h4>
 * <pre>
 * <code>
 * import org.no_creativity.anvergen.Ver
 *
 * android {
 *     defaultConfig {
 *         versionCode Ver.generateVersionCode()
 *         versionName Ver.generateVersionName()
 *     }
 * }
 * </code>
 * </pre>
 * @author yanqd0
 */
public class Ver {
    /**
     * The versionCode is set to the commit count of the current git branch.
     *
     * @param commit The position where to count backwards, which could be SHA1, tag, or branch.
     * It's recommended to use the default: <code>"HEAD"</code>.
     * @return The number of commits in git history.
     * @throws IllegalArgumentException When the <code>commit</code> is not valid.
     * @since 0.2.0
     */
    @TypeChecked
    public static int generateVersionCode(String commit = 'HEAD')
            throws IllegalArgumentException {
        return Git.calculateCommitCount(null, commit)
    }

    /**
     * The versionName is set to the result of <code>git describe</code>.
     * <p>
     * If there is no TAG yet, then the {@link Git#DEFAULT_TAG} is set as the version prefix.
     *
     * @param commit The position where to count backwards, which could be SHA1, tag, or branch.
     * It's recommended to use the default: <code>"HEAD"</code>.
     * @return The formatted String with git and time information.
     * @throws IllegalArgumentException When the <code>commit</code> is not valid.
     * @since 0.2.0
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

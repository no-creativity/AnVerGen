/*
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
 * This is a subset wrapper of Git.
 * <p>
 * There may be another way to do so, but it makes this repository much bigger.
 * See:
 * <ul>
 * <li><a href="https://github.com/ethomson/jagged">jagged</a>
 * Java bindings to libgit2.</li>
 * <li><a href="https://github.com/libgit2/libgit2">libgit2</a>
 * A C-lang implementation of the Git core methods.</li>
 * </ul>
 *
 * @author yanqd0
 */
public class Git {
    /**
     * This is the return of {@link #getLatestTag()} if there is no git tag yet.
     */
    public final static String DEFAULT_TAG = "0.0"

    private Git() {
        throw new UnsupportedOperationException("This class should never be instantiated!")
    }

    /**
     * This method calculates the number of commit(s) in the specific list.
     * If <code>from</code> is <code>null</code>, the list is the whole git history.
     *
     * @param from The start of the commit list to be count. The default is <code>null</code>.
     * @param to The end of the commit list to be count. The default is "HEAD".
     * @return The commit count between <code>from</code> and <code>to</code>.
     */
    public static int calculateCommitCount(String from = null, String to = "HEAD") {
        String cmd
        if (from == null || from.trim().isEmpty()) {
            cmd = "git rev-list --count $to"
        } else {
            cmd = "git rev-list --count $from...$to"
        }

        def process = cmd.execute()
        process.waitFor()
        return process.getText().toInteger()
    }

    /**
     * If there is no git tag yet, then this returns {@link #DEFAULT_TAG}.
     *
     * @return The latest git tag.
     * @see #DEFAULT_TAG
     */
    public static String getLatestTag() {
        def cmd = "git for-each-ref --sort=-taggerdate --count=1 --format %(tag) refs/tags"
        def process = cmd.execute()
        process.waitFor()
        def tag = process.getText().trim()

        if (tag.isEmpty()) {
            tag = DEFAULT_TAG
        }

        return tag
    }

    /**
     * It's the same as the end of <code>git describe --always</code>.
     *
     * @return The short SHA1 as the commit description.
     */
    public static String getShortSha1() {
        def process = "git rev-parse HEAD".execute()
        process.waitFor()
        return process.getText().substring(0, 7)
    }

    /**
     * The date of commit is useful in versions.
     *
     * @param commit The git commit to query. The default is "HEAD".
     * @return The {@link Date} of the specified commit.
     */
    public static Date getCommitDate(String commit = '') {
        def process = "git log -1 --format=%ct $commit".execute()
        process.waitFor()
        long utc = process.getText().toLong() * 1000
        return new Date(utc)
    }
}

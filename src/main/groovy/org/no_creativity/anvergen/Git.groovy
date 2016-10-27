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
     * <p>
     * The default is <code>"0.0"</code>.
     */
    public final static String DEFAULT_TAG = "0.0"

    private Git() {
        throw new UnsupportedOperationException("This class should never be instantiated!")
    }

    /**
     * This method calculates the number of commit(s) in the specific list.
     * If <code>from</code> is <code>null</code>, the list is the whole git history.
     *
     * @param from The start of the commit list to be count.
     * @param to The end of the commit list to be count.
     * @return The commit count between <code>from</code> and <code>to</code>.
     * @throws IllegalArgumentException When <code>from</code> or <code>to</code> is not valid.
     */
    @TypeChecked
    public static int calculateCommitCount(String from = null, String to = "HEAD")
            throws IllegalArgumentException {
        Closure cmd = {
            if (from == null || from.trim().isEmpty() || from.equals(DEFAULT_TAG)) {
                "git rev-list --count $to"
            } else {
                "git rev-list --count $from...$to"
            }
        }

        def process = cmd().execute()
        process.waitFor()
        checkResultOrThrow(process, "Is the $from or $to valid commit?")
        return process.getText().toInteger()
    }

    /**
     * If there is no git tag yet, then this returns {@link #DEFAULT_TAG}.
     *
     * @return The latest git tag.
     * @see #DEFAULT_TAG
     */
    @NotNull
    @TypeChecked
    public static String getLatestTag(String commit = 'HEAD') throws IllegalArgumentException {
        def describe = getGitDescribe(commit)
        def sha1_7 = getShortSha1(7, commit)
        if (describe.contains("-g$sha1_7")) {
            def position = describe.lastIndexOf('-')
            describe = describe.substring(0, position)
            position = describe.lastIndexOf('-')
            return describe.substring(0, position)
        } else if (!sha1_7.equals(describe)) {
            return describe
        } else {
            return DEFAULT_TAG
        }
    }

    @NotNull
    @TypeChecked
    public static String getGitDescribe(String commit = 'HEAD') throws IllegalArgumentException {
        def cmd = "git describe --always --tags $commit"
        def process = cmd.execute()
        process.waitFor()
        checkResultOrThrow(process, "The commit $commit is not found!")
        return process.getText().trim()
    }

    /**
     * To query the specified SHA1 with a specified length.
     *
     * @param length The length of the SHA1 to be returned.
     * @param commit The position where you want SHA1.
     * @return The short SHA1 as the commit description.
     * @throws IllegalArgumentException When the <code>commit</code> is not valid,
     * or the <code>length</code> is not appropriate.
     */
    @NotNull
    @TypeChecked
    public static String getShortSha1(int length = 7, String commit = 'HEAD')
            throws IllegalArgumentException {
        if (length <= 0 || length > 40) {
            throw new IllegalArgumentException("The length is $length, which should be " +
                    "smaller then 40 and bigger then 0!")
        }

        def process = "git rev-parse $commit".execute()
        process.waitFor()
        checkResultOrThrow(process, "The commit $commit is not found!")
        return process.getText().trim().substring(0, length)
    }

    /**
     * The date of commit may be useful in versions.
     *
     * @param commit The git commit to query.
     * @return The {@link Date} of the specified commit.
     * @throws IllegalArgumentException When the <code>commit</code> is not valid.
     */
    @NotNull
    @TypeChecked
    public static Date getCommitDate(String commit = 'HEAD') throws IllegalArgumentException {
        def process = "git log -1 --format=%ct $commit".execute()
        process.waitFor()
        checkResultOrThrow(process, "The commit $commit is not found!")
        long utc = process.getText().toLong() * 1000
        return new Date(utc)
    }

    @TypeChecked
    private static void checkResultOrThrow(Process process, String msg) {
        if (process.exitValue() != 0) {
            throw new IllegalArgumentException(msg)
        }
    }
}

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

import org.junit.Test

import static org.junit.Assert.*

/**
 * @author yanqd0
 */
class GitTest {
    private static final def INVALID_COMMIT = "I_am_not_a_valid_commit"

    @Test
    public void testConstructor() throws Exception {
        try {
            Git.newInstance()
        } catch (UnsupportedOperationException ignored) {
            return
        }
        fail()
    }

    /**
     * @see Git#calculateCommitCount()
     */
    @Test
    public void getCommitCount0() throws Exception {
        def process = "git rev-list --count HEAD".execute()
        process.waitFor()
        def count = process.getText().toInteger()
        assertEquals(count, Git.calculateCommitCount())
    }

    /**
     * @see Git#calculateCommitCount(String)
     */
    @Test
    public void getCommitCount1() throws Exception {
        def count = Git.calculateCommitCount()
        assertEquals(count, Git.calculateCommitCount(' '))
        assertEquals(count, Git.calculateCommitCount(Git.DEFAULT_TAG))
        assertEquals(0, Git.calculateCommitCount("HEAD"))
        assertEquals(1, Git.calculateCommitCount("HEAD^"))

        try {
            Git.calculateCommitCount(INVALID_COMMIT)
            fail()
        } catch (IllegalArgumentException ignored) {
        }
    }

    /**
     * @see Git#calculateCommitCount(String, String)
     */
    @Test
    public void getCommitCount2() throws Exception {
        assertEquals(2, Git.calculateCommitCount("HEAD~3", "HEAD^"))

        final String[] INVALID_PAIRS = [
                ['HEAD', INVALID_COMMIT],
                [INVALID_COMMIT, 'HEAD'],
                [INVALID_COMMIT, INVALID_COMMIT],
        ]
        for (String[] commits in INVALID_PAIRS) {
            try {
                Git.calculateCommitCount(commits[0], commits[1])
                fail()
            } catch (IllegalArgumentException ignored) {
            }
        }
    }

    @Test
    public void getLatestTag() throws Exception {
        def tag = Git.getLatestTag()
        assertNotNull(tag)
        assertFalse(tag.trim().isEmpty())
        assertEquals(tag, Git.getLatestTag())

        def cmd = "git for-each-ref --sort=-taggerdate --count=1 --format %(tag) refs/tags"
        def process = cmd.execute()
        process.waitFor()
        assertEquals(process.getText().trim(), tag)
    }

    @Test
    public void getLatestTag1() throws Exception {
        assertEquals('0.1', Git.getLatestTag('0.1'))
        assertEquals('0.1', Git.getLatestTag('0.2^'))
        assertEquals(Git.DEFAULT_TAG, Git.getLatestTag('0.1^'))

        try {
            Git.getLatestTag(INVALID_COMMIT)
            fail()
        } catch (IllegalArgumentException ignored) {
        }
    }

    @Test
    public void getGitDescribe() throws Exception {
        def describe = Git.getGitDescribe()
        def splits = describe.split('-')
        def tag = Git.getLatestTag()
        assertEquals(tag, splits[0])
        assertEquals(Git.calculateCommitCount(tag), splits[1].toInteger())
        assertEquals(Git.getShortSha1(), splits[2].substring(1))
    }

    @Test
    public void getGitDescribe1() throws Exception {
        assertEquals('0.2', Git.getGitDescribe('0.2'))
        assertEquals('0.1-13-g878ab83', Git.getGitDescribe('0.2^'))
        assertEquals('e69aa71', Git.getGitDescribe('0.1^'))

        try {
            Git.getGitDescribe(INVALID_COMMIT)
            fail()
        } catch (IllegalArgumentException ignored) {
        }
    }

    @Test
    public void getShortSha1_0() throws Exception {
        def process = "git describe --long".execute()
        process.waitFor()
        def description = process.getText()
        def length = description.length()
        def shortSha1 = description.substring(length - 8, length).trim()
        assertEquals(shortSha1, Git.getShortSha1())
    }

    @Test
    public void getShortSha1_1() throws Exception {
        def process = "git rev-parse HEAD".execute()
        process.waitFor()
        def sha1_40 = process.getText().trim()
        assertEquals(sha1_40, Git.getShortSha1(40))
        assertEquals(sha1_40.substring(0, 9), Git.getShortSha1(9))

        for (int i in [0, 41, Integer.MIN_VALUE, Integer.MAX_VALUE]) {
            try {
                Git.getShortSha1(i)
                fail()
            } catch (IllegalArgumentException ignored) {
            }
        }
        for (int i in [1, 7, 40]) {
            try {
                Git.getShortSha1(i)
            } catch (IllegalArgumentException ignored) {
                fail()
            }
        }
    }

    @Test
    public void getShortSha1_2() throws Exception {
        try {
            Git.getShortSha1(7, INVALID_COMMIT)
            fail()
        } catch (IllegalArgumentException ignored) {
        }
    }

    @Test
    public void getCommitDate() throws Exception {
        assertEquals(Git.getCommitDate(), Git.getCommitDate('HEAD'))
        assertEquals(new Date(1476411953000), Git.getCommitDate('0.3'))

        try {
            Git.getCommitDate(INVALID_COMMIT)
            fail()
        } catch (IllegalArgumentException ignored) {
        }
    }
}
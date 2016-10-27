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

import static org.junit.Assert.assertEquals
import static org.junit.Assert.assertTrue
import static org.no_creativity.anvergen.Git.DEFAULT_TAG
import static org.no_creativity.anvergen.GitTest.INVALID_COMMIT
import static org.no_creativity.anvergen.GitTest.testIllegalArgumentException

/**
 * @author yanqd0
 */
class VerTest {
    @Test
    public void testGenerateVersionCode() throws Exception {
        def process = "git rev-list --count HEAD".execute()
        process.waitFor()
        def count = process.getText().toInteger()

        def code = Ver.generateVersionCode()
        assertEquals(count, code)
        assertTrue(code > 0)
        assertEquals(code, Ver.generateVersionCode())
    }

    @Test
    public void testGenerateVersionCode1() throws Exception {
        def head = Ver.generateVersionCode()
        assertEquals(head, Ver.generateVersionCode('HEAD'))
        assertEquals(30, Ver.generateVersionCode('0.2'))

        testIllegalArgumentException {
            Ver.generateVersionCode(INVALID_COMMIT)
        }
    }

    @Test
    public void testGenerateVersionName() throws Exception {
        String name = Ver.generateVersionName()
        String tag = Git.getLatestTag()
        assertTrue(name.startsWith(tag))
        if (!tag.equals(name)) {
            int count = Git.calculateCommitCount(tag)
            assertTrue(name.contains(count.toString()))
            assertTrue(name.endsWith(Git.getShortSha1()))
        }
    }

    @Test
    public void testGenerateVersionName1() throws Exception {
        assertEquals('0.2', Ver.generateVersionName('0.2'))
        assertEquals('0.1-13-g878ab83', Ver.generateVersionName('0.2^'))
        assertEquals("$DEFAULT_TAG-15-ge69aa71".toString(), Ver.generateVersionName('0.1^'))

        testIllegalArgumentException {
            Ver.generateVersionName(INVALID_COMMIT)
        }
    }
}

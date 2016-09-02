package io.github.yanqd0.anvergen

import org.junit.Test

import static org.junit.Assert.assertEquals
import static org.junit.Assert.assertTrue

/**
 * @author yanqd0
 */
class VerTest {
    @Test
    public void testGenerateVersionCode() throws Exception {
        Process process = "git rev-list --count HEAD".execute()
        process.waitFor()
        int commits = process.getText().toInteger()

        assertEquals(commits, Ver.generateVersionCode())
        assertEquals(commits, Ver.generateVersionCode())
    }

    @Test
    public void testGenerateVersionName() throws Exception {
        String name = Ver.generateVersionName()
        assertTrue(name.startsWith("1.0"))
        assertTrue(name.contains(new Date().format("yyMMdd")))

        String version = "2.0"
        assertTrue(Ver.generateVersionName(version).startsWith(version))
    }
}

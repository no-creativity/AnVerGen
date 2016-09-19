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
 * @author yanqd0
 */
class Ver {
    private static int commits = 0

    private static getCommitCount() {
        if (commits == 0) {
            Process process = "git rev-list --count HEAD".execute()
            process.waitFor()
            commits = process.getText().toInteger()
        }
        return commits
    }

    @SuppressWarnings("GroovyUnusedDeclaration")
    public static int generateVersionCode() {
        return getCommitCount()
    }

    @SuppressWarnings("GroovyUnusedDeclaration")
    public static String generateVersionName(String version = "1.0", int offset = 0) {
        int count = getCommitCount() - offset
        String sha1 = getGitDescription()
        String date = new Date().format("yyMMdd")
        return "$version.$count.$date.$sha1"
    }

    private static String getGitDescription() {
        Process process = "git rev-parse HEAD".execute()
        process.waitFor()
        return process.getText().substring(0, 6)
    }
}

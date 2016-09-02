package io.github.yanqd0.anvergen

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

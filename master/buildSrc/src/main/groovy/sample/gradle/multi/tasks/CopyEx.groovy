package sample.gradle.multi.tasks

import org.gradle.api.tasks.Copy

/**
 * 更新日時を維持するメソッドを追加した、拡張コピータスククラス
 *
 */
class CopyEx extends Copy {

    // 更新日時を維持する
    def presereveTimestamp(presereve) {

        if(!presereve) {
            return
        }

        def copyFileDetailses = []
        eachFile { copyFileDetailses <<  it } // eachFileの実行タイミングはdoLastと同じ

        doLast {
            copyFileDetailses.each { copyFileDetails ->
                def destFile = new File(destinationDir, copyFileDetails.path)
                if(destFile.exists()) { destFile.setLastModified(copyFileDetails.lastModified) }
            }
        }
    }
}

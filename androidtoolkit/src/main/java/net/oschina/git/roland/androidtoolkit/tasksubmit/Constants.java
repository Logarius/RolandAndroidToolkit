package net.oschina.git.roland.androidtoolkit.tasksubmit;

/**
 * 常量
 * Created by Roland on 2017/3/28.
 */

public class Constants {

    /**
     * 任务状态
     */
    public enum TaskState {
        UPLOADING, // 上传
        PAUSED, // 暂停
        WAITING, // 等待
        ERROR, // 错误
        FINISH, // 完成
        STOP // 停止
    }

    /**
     * TaskSubmitService Intent传参Key值
     */
    public static final String INTENT_KEY_WINDOW_SIZE = "INTENT_KEY_WINDOW_SIZE";
}

package net.oschina.git.roland.androidtoolkit.tasksubmit

import android.os.Binder

/**
 * 任务提交服务Binder
 * Created by Roland on 2017/3/28.
 */

class TaskSubmitServiceBinder(val service: TaskSubmitService) : Binder()
package io.github.sgpublic.uniktx.common.core.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.activity.result.contract.ActivityResultContract


/**
 * @author Madray Haven
 * @Date 2023/7/14 16:13
 */

class RequestInstallPackagePermissionContract: ActivityResultContract<String?, Boolean>() {
    override fun createIntent(context: Context, input: String?): Intent {
        val intent = Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES)
        intent.data = Uri.parse("package:${input ?: context.packageName}")
        return intent
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Boolean {
        return resultCode == Activity.RESULT_OK
    }
}
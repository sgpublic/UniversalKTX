package io.github.sgpublic.android

import com.dtflys.forest.Forest
import io.github.sgpublic.android.core.util.BuildConfigWrapper
import io.github.sgpublic.kotlin.base.forest.GsonConverter

/**
 * @author Madray Haven
 * @Date 2023/4/7 10:26
 */

fun Application.configForest() {
    Forest.config().let {
        it.jsonConverter = GsonConverter
        it.isLogResponseContent = BuildConfigWrapper.DEBUG
    }
}
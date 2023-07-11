package io.github.sgpublic.android

import com.dtflys.forest.Forest
import com.dtflys.forest.config.ForestConfiguration
import io.github.sgpublic.kotlin.base.forest.GsonConverter

/**
 * @author Madray Haven
 * @Date 2023/4/7 10:26
 */

fun ForestConfiguration.applyDefault(isDebug: Boolean) {
    Forest.config().let {
        it.jsonConverter = GsonConverter
        it.isLogResponseContent = isDebug
    }
}
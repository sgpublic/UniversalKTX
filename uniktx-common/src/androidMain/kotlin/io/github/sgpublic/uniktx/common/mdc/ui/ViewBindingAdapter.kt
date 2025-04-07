package io.github.sgpublic.uniktx.common.mdc.ui

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import io.github.sgpublic.uniktx.common.core.util.ContextResource

/**
 *
 * @author Madray Haven
 * @date 2022/10/29 10:47
 */
open class ViewBindingHolder<VB: ViewBinding>(val ViewBinding: VB):
    RecyclerView.ViewHolder(ViewBinding.root),
    ContextResource {
    final override fun getContext(): Context {
        return ViewBinding.root.context
    }
}

val <T: ViewBinding> T.asViewBindingHolder: ViewBindingHolder<T> get() {
    return ViewBindingHolder(this)
}

typealias ViewBindingRecyclerAdapter = RecyclerView.Adapter<ViewBindingHolder<*>>
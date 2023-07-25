package io.github.sgpublic.android.mdc.ui

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import io.github.sgpublic.android.core.util.ContextResource

/**
 *
 * @author Madray Haven
 * @date 2022/10/29 10:47
 */
open class ViewBindingHolder<VB: ViewBinding>(val ViewBinding: VB):
    RecyclerView.ViewHolder(ViewBinding.root),
    ContextResource {
    final override fun getContext(): Context = ViewBinding.root.context
}

val <T: ViewBinding> T.asViewBindingHolder: ViewBindingHolder<T> get() {
    return ViewBindingHolder(this)
}

typealias ViewBindingRecyclerAdapter = RecyclerView.Adapter<ViewBindingHolder<*>>
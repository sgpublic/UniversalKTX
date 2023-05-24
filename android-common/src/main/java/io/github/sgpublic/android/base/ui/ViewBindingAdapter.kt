package io.github.sgpublic.android.base.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
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

typealias ViewBindingRecyclerAdapter = RecyclerView.Adapter<ViewBindingHolder<*>>
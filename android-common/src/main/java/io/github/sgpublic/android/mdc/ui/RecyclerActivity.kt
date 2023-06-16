package io.github.sgpublic.android.mdc.ui

import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import io.github.sgpublic.android.mdc.base.MdcVMActivity
import io.github.sgpublic.android.common.databinding.ActivityRecyclerBinding
import io.github.sgpublic.android.core.util.viewBinding

/**
 *
 * @author Madray Haven
 * @date 2022/11/9 10:55
 */
abstract class RecyclerActivity<ItemT, ViewT: ViewBinding, VM: ViewModel>:
    MdcVMActivity<ActivityRecyclerBinding, VM>() {
    @CallSuper
    override fun onViewSetup() {
        ViewBinding.recyclerOrigin.adapter = Adapter
    }

    protected abstract val Adapter: ArrayRecyclerAdapter<ViewT, ItemT>

    override val ViewBinding: ActivityRecyclerBinding by viewBinding()
}
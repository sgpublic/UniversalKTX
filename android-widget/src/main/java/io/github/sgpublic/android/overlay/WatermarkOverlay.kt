package io.github.sgpublic.android.overlay

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import io.github.sgpublic.android.base.overlay.BaseInternOverlayWidget
import io.github.sgpublic.android.databinding.OverlayWatermarkBinding

/**
 * @author Madray Haven
 * @Date 2023/6/6 16:56
 */
class WatermarkOverlay(context: AppCompatActivity): BaseInternOverlayWidget<OverlayWatermarkBinding>(context) {
    override fun onCreateViewBinding(layoutInflater: LayoutInflater, parent: ViewGroup) =
        OverlayWatermarkBinding.inflate(layoutInflater, parent, false)
}
package soczuks.tabletoptimer

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.view.View
import com.skydoves.colorpickerview.ColorEnvelope

class ColorSelectorDialog (activity: Activity, private val colors: Array<ColorEnvelope>) : Dialog(activity), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.color_selector_dialog)
        createSelectorLayout()
    }

    override fun onClick(p0: View?) {
        TODO("Not yet implemented")
    }

    private fun createSelectorLayout() {
        if (colors.isEmpty()) {
            colorsFromCircle()
        } else {
            colorsFromArray()
        }
    }

    private fun colorsFromArray() {

    }

    private fun colorsFromCircle() {
    }
}
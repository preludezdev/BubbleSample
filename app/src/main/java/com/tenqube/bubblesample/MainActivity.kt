package com.tenqube.bubblesample

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import com.igalata.bubblepicker.BubblePickerListener
import com.igalata.bubblepicker.adapter.BubblePickerAdapter
import com.igalata.bubblepicker.model.BubbleGradient
import com.igalata.bubblepicker.model.PickerItem
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Created by irinagalata on 1/19/17.
 */
class MainActivity : AppCompatActivity() {

    private val boldTypeface by lazy { Typeface.createFromAsset(assets, ROBOTO_BOLD) }
    private val mediumTypeface by lazy { Typeface.createFromAsset(assets, ROBOTO_MEDIUM) }
    private val regularTypeface by lazy { Typeface.createFromAsset(assets, ROBOTO_REGULAR) }

    companion object {
        private const val ROBOTO_BOLD = "roboto_bold.ttf"
        private const val ROBOTO_MEDIUM = "roboto_medium.ttf"
        private const val ROBOTO_REGULAR = "roboto_regular.ttf"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        titleTextView.typeface = mediumTypeface
        subtitleTextView.typeface = boldTypeface
        hintTextView.typeface = regularTypeface
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            subtitleTextView.letterSpacing = 0.06f
            hintTextView.letterSpacing = 0.05f
        }

        val titles = resources.getStringArray(R.array.countries)
        val colors = resources.obtainTypedArray(R.array.colors)
        val images = resources.obtainTypedArray(R.array.images)

        picker

        picker.adapter = object : BubblePickerAdapter {

            override val totalCount = titles.size

            override fun getItem(position: Int): PickerItem {
                return PickerItem().apply {
                    title = titles[position]
//                    gradient = BubbleGradient(
//                        colors.getColor(0, 0),
//                        colors.getColor(0, 0),
//                        BubbleGradient.VERTICAL
//                    )
                    typeface = mediumTypeface
                    textColor =
                        ContextCompat.getColor(this@MainActivity, R.color.text_color_default)
                    color = ContextCompat.getColor(this@MainActivity, R.color.bubble_color_default)
                    backgroundImage = ContextCompat.getDrawable(this@MainActivity, R.drawable.bg_blue)
                    overlayAlpha = 0f
                }
            }
        }

        colors.recycle()
        images.recycle()

        picker.centerImmediately = true
        picker.bubbleSize = 20
        picker.listener = object : BubblePickerListener {
            override fun onBubbleSelected(item: PickerItem) {
                toast("${item.title} selected")
                item.apply {
                    //                    gradient = BubbleGradient(
//                        ContextCompat.getColor(this@MainActivity, R.color.bubble_color_active),
//                        ContextCompat.getColor(this@MainActivity, R.color.bubble_color_active),
//                        BubbleGradient.VERTICAL
//                    )
                    color = ContextCompat.getColor(this@MainActivity, R.color.bubble_color_active)
                    textColor = ContextCompat.getColor(this@MainActivity, R.color.text_color_active)
                    picker.visibility = View.GONE
                    picker.visibility = View.VISIBLE
                }
            }

            override fun onBubbleDeselected(item: PickerItem) {
                toast("${item.title} deselected")
                item.apply {
                    //                    gradient = BubbleGradient(
//                        ContextCompat.getColor(this@MainActivity, R.color.bubble_color_default),
//                        ContextCompat.getColor(this@MainActivity, R.color.bubble_color_default),
//                        BubbleGradient.VERTICAL
//                    )
                    color = ContextCompat.getColor(this@MainActivity, R.color.bubble_color_default)
                    textColor = ContextCompat.getColor(this@MainActivity, R.color.text_color_default)
                    picker.visibility = View.GONE
                    picker.visibility = View.VISIBLE
                }

            }
        }
    }

    override fun onResume() {
        super.onResume()
        picker.onResume()
    }

    override fun onPause() {
        super.onPause()
        picker.onPause()
    }

    private fun toast(text: String) = Toast.makeText(this, text, Toast.LENGTH_SHORT).show()

}
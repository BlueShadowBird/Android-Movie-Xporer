package id.web.dedekurniawan.moviexplorer.core.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

class HorizontalList(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int
): AppCompatTextView(context, attrs, defStyleAttr){
    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?): this(context, attrs, 0)

    private val paint = Paint().apply {
        isAntiAlias = true
        textSize = this@HorizontalList.textSize
        color = Color.BLACK
        textAlign = Paint.Align.LEFT
    }
    private var list: List<String>? = null

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (!list.isNullOrEmpty()) {
            val width = measuredWidth.toFloat() - paddingLeft - paddingRight
            var y = paddingTop.toFloat() - paint.ascent()
            val textLines = mutableListOf<String>()
            var line = list!![0]
            list!!.subList(1, list!!.size).forEach {
                val lineWidth = paint.measureText("$line | $it")
                if (lineWidth > width) {
                    textLines.add(line.trim())
                    line = it
                } else {
                    line += " | $it"
                }
            }
            textLines.add(line.trim())

            val fontMetrics = paint.fontMetrics

            textLines.forEach {
                canvas.drawText(it, paddingLeft.toFloat(), y, paint)
                y += fontMetrics.descent - fontMetrics.ascent + fontMetrics.leading
            }
            setMeasuredDimension(width.toInt(), y.toInt())
        }
    }

    fun setList(list: List<String>?) {
        this.list = list
        invalidate()
    }
}
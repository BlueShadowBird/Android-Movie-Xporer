package id.web.dedekurniawan.moviexplorer.core.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import kotlin.math.ceil

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
        textAlign = Paint.Align.LEFT
    }
    private var list: List<String>? = null
    private val textLines = mutableListOf<String>()

    init {
        // Ambil warna teks dari atribut gaya yang digunakan
        val typedArray = context.obtainStyledAttributes(attrs, intArrayOf(android.R.attr.textColor))
        try {
            paint.color = typedArray.getColor(0, currentTextColor)
        } finally {
            typedArray.recycle() // Always recycle to free resources
        }

    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (!list.isNullOrEmpty()) {
            val width = measuredWidth.toFloat() - paddingLeft - paddingRight
            var y = paddingTop.toFloat() - paint.ascent()
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
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        if (textLines.size>0){
            val fontMetrics = paint.fontMetrics
            val lineHeight = ceil(fontMetrics.descent - fontMetrics.ascent + fontMetrics.leading).toInt()
            val totalHeight = (textLines.size * lineHeight) + paddingTop + paddingBottom

            // Set the calculated height
            setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), totalHeight)
        }
    }


    fun setList(list: List<String>?) {
        this.list = list
        invalidate()
    }
}
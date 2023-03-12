package id.web.dedekurniawan.moviexplorer.core.ui

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import android.view.animation.DecelerateInterpolator
import kotlin.math.max
import kotlin.math.min

/**
 * Simple single android view component that can be used to showing a round progress bar.
 * It can be customized with size, stroke size, colors and text etc.
 * Progress change will be animated.
 * Created by Kristoffer, http://kmdev.se
 */
/**
 * Custom from https://github.com/korre/android-circular-progress-bar
 * credit to Creator Kristoffer
* */
class CircularProgressBar(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int,
    defStyleRes: Int
): View(context, attrs, defStyleAttr, defStyleRes) {
    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?): this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : this(context, attrs, defStyleAttr, 0)

    private var mViewWidth = 0
    private var mViewHeight = 0
    private val mStartAngle = -90f // Always start from top (default is: "3 o'clock on a watch.")
    private var mSweepAngle = 0f // How long to sweep from mStartAngle
    private val mMaxSweepAngle = 360f // Max degrees to sweep = full circle
    private var mStrokeWidth = 10 // Width of outline
    private val mAnimationDuration = 400 // Animation duration for progress change
    private val mMaxProgress = 100 // Max progress to use
    private var mDrawText = true // Set to true if progress text should be drawn
    private var mRoundedCorners = true // Set to true if rounded corners should be applied to outline ends
    private var mProgressColor: Int = Color.BLACK // Outline color
    private var mTextColor: Int = Color.BLACK // Progress text color
    private var mPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG) // Allocate paint outside onDraw to avoid unnecessary object creation

    //custom addition
    private var mHasProgress = false // Set to true if rounded corners should be applied to outline ends

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        initMeasurments()
        drawOutlineArc(canvas)
        if (mDrawText) {
            drawText(canvas)
        }
    }

    private fun initMeasurments() {
        mViewWidth = width
        mViewHeight = height
    }

    private fun drawOutlineArc(canvas: Canvas) {
        val diameter = min(mViewWidth, mViewHeight)

        mPaint.color = Color.BLACK
        canvas.drawCircle((diameter/2).toFloat(), (diameter/2).toFloat(),
            (diameter/2).toFloat(), mPaint)

        val pad = (mStrokeWidth / 2.0).toFloat()
        val outerOval = RectF(pad, pad, diameter - pad, diameter - pad)
        mPaint.color = mProgressColor
        mPaint.strokeWidth = mStrokeWidth.toFloat()
        mPaint.isAntiAlias = true
        mPaint.strokeCap = if (mRoundedCorners) Paint.Cap.ROUND else Paint.Cap.BUTT
        mPaint.style = Paint.Style.STROKE
        canvas.drawArc(outerOval, mStartAngle, mSweepAngle, false, mPaint)
    }

    private fun drawText(canvas: Canvas) {
        mPaint.textSize = min(mViewWidth, mViewHeight) / 3f
        mPaint.textAlign = Paint.Align.CENTER
        mPaint.strokeWidth = 0f
        mPaint.color = Color.WHITE
        mPaint.style = Paint.Style.FILL

        // Center text
        val xPos = canvas.width.toFloat() / 2
        val yPos = canvas.height.toFloat() / 2 - (mPaint.descent() + mPaint.ascent()) / 2
        canvas.drawText(
            if(mHasProgress)calcProgressFromSweepAngle(mSweepAngle).toString() + "%"
            else "X",
            xPos,
            yPos,
            mPaint
        )
    }

    private fun calcSweepAngleFromProgress(progress: Int): Float {
        return mMaxSweepAngle / mMaxProgress * progress
    }

    private fun calcProgressFromSweepAngle(sweepAngle: Float): Int {
        return (sweepAngle * mMaxProgress / mMaxSweepAngle).toInt()
    }

    /**
     * Set progress of the circular progress bar.
     * @param progress progress between 0 and 100.
     */
    fun setProgress(progress: Int) {
        mHasProgress = progress >= 0
        val animator = ValueAnimator.ofFloat(mSweepAngle, calcSweepAngleFromProgress(max(progress, 0)))
        animator.interpolator = DecelerateInterpolator()
        animator.duration = mAnimationDuration.toLong()
        animator.addUpdateListener { valueAnimator ->
            mSweepAngle = valueAnimator.animatedValue as Float
            invalidate()
        }
        animator.start()
    }

    fun setProgressColor(color: Int) {
        mProgressColor = color
        invalidate()
    }

    fun setProgressWidth(width: Int) {
        mStrokeWidth = width
        invalidate()
    }

    fun setTextColor(color: Int) {
        mTextColor = color
        invalidate()
    }

    fun showProgressText(show: Boolean) {
        mDrawText = show
        invalidate()
    }

    /**
     * Toggle this if you don't want rounded corners on progress bar.
     * Default is true.
     * @param roundedCorners true if you want rounded corners of false otherwise.
     */
    fun useRoundedCorners(roundedCorners: Boolean) {
        mRoundedCorners = roundedCorners
        invalidate()
    }
}
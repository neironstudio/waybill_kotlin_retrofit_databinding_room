package com.tetron.waybill.view.draw

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.tetron.waybill.R
import kotlin.math.abs


class DrawingView
    (
    internal var context: Context, attrs: AttributeSet
) : View(context, attrs) {

    private var mBitmap: Bitmap? = null
    private val mCanvas: Canvas
    private val mPath: Path
    private val mBitmapPaint: Paint
    private val circlePaint: Paint
    private val circlePath: Path
    private var w = 1
    private var h = 1
    private var mX: Float = 0.toFloat()
    private var mY: Float = 0.toFloat()

    fun getBitmap(w_: Int, h_: Int): Bitmap {
        val bitmap = Bitmap.createBitmap(
            w_, h_, Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        val background = this.background
        background?.draw(canvas)
        this.draw(canvas)

        return bitmap
    }

    init {

        mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        mCanvas = Canvas(mBitmap!!)
        mPath = Path()
        mBitmapPaint = Paint(Paint.DITHER_FLAG)
        setPaint(mBitmapPaint)
        circlePaint = Paint()
        circlePath = Path()
        setPaint(circlePaint)
    }

    private fun setPaint(mBitmapPaint: Paint) {

        mBitmapPaint.isAntiAlias = true
        mBitmapPaint.color = resources.getColor(R.color.colorDraw, null)
        mBitmapPaint.style = Paint.Style.STROKE
        mBitmapPaint.strokeJoin = Paint.Join.MITER
        mBitmapPaint.strokeWidth = 4f

    }

    fun setBitmap(bitmap: Bitmap?) {
        if ((bitmap != null) and (mBitmap != null)) {
            mBitmap = bitmap
        }
    }

    override fun onDraw(canvas: Canvas) {

        super.onDraw(canvas)
        canvas.drawBitmap(mBitmap!!, 0f, 0f, mBitmapPaint)
        canvas.drawPath(mPath, mBitmapPaint)
        canvas.drawPath(circlePath, circlePaint)
    }

    private fun touchStart(x: Float, y: Float) {

        mPath.moveTo(x, y)
        mX = x
        mY = y

    }

    private fun touchMove(x: Float, y: Float) {

        val dx = abs(x - mX)
        val dy = abs(y - mY)
        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
            mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2)
            mX = x
            mY = y
            circlePath.reset()
            circlePath.addCircle(mX, mY, 30f, Path.Direction.CW)
        }

    }

    private fun touchUp() {

        mPath.lineTo(mX, mY)
        mCanvas.drawPath(mPath, mBitmapPaint)

    }

    fun clearCanvas() {

        mPath.reset()
        mBitmap = Bitmap.createBitmap(IntArray(1), w, h, Bitmap.Config.ARGB_8888)
        invalidate()

    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {

        val x = event.x
        val y = event.y

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                touchStart(x, y)
                invalidate()
            }
            MotionEvent.ACTION_MOVE -> {
                touchMove(x, y)
                invalidate()
            }
            MotionEvent.ACTION_UP -> {
                touchUp()
                invalidate()
            }
        }
        return true
    }

    companion object {
        private const val TOUCH_TOLERANCE = 4f
    }
}

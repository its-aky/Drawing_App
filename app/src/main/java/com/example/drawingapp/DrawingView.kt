package com.example.drawingapp

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.*
import android.util.AttributeSet
import android.view.View


class DrawingView(context:Context,attrs: AttributeSet) : View(context,attrs) {
    private var mDrawPath:CustomPath?=null
    private var mCanvasBitmap: Bitmap?=null
    private var mDrawPaint: Paint?=null
    private var mCanvasPaint: Paint?=null
    private var mBrushSize: Float=0.toFloat()
    private var color = Color.BLACK
    private var canvas: Canvas?=null

    init{
        setUpDrawing()
    }
//init is a block used in Kotlin constructors for performing initialization code when an object of the class is created
    private fun setUpDrawing(){
        mDrawPaint=Paint()
        mDrawPath=CustomPath(color,mBrushSize)
        mDrawPaint!!.color=color
        mDrawPaint!!.style=Paint.Style.STROKE
        mDrawPaint!!.strokeJoin=Paint.Join.ROUND
        mDrawPaint!!.strokeCap=Paint.Cap.ROUND
        mCanvasPaint=Paint(Paint.DITHER_FLAG)
        //You're creating a new Paint object and setting the DITHER_FLAG as a flag. This flag indicates that dithering should be applied when drawing with this Paint object.

        //You can use this Paint object to specify various attributes for your drawing operations, such as color, stroke width, style, and more. For example, you can set the color using mCanvasPaint.setColor(color) and the stroke width using mCanvasPaint.setStrokeWidth(width)
        //The DITHER_FLAG is a flag used to enable dithering when drawing with the Paint object. Dithering is a technique used to simulate colors or smooth transitions in situations where the color palette or available colors are limited, such as when rendering graphics on devices with a restricted color depth. Dithering can help reduce visual artifacts and create smoother gradients or transitions between colors
        mBrushSize=20.toFloat()

    }






    internal inner class CustomPath(var color:Int, var brushThickness:Float):android.graphics.Path() {
        //read about path by ctrl+click

    }
}
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
    private var mBrushSize: Float?=0.toFloat()
    private var color = Color.BLACK
    private var canvas: Canvas?=null






    internal inner class CustomPath(var color:Int, var brushThickness:Float):android.graphics.Path() {
        //read about path by ctrl+click

    }
}
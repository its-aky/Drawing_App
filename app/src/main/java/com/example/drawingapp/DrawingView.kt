package com.example.drawingapp

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.*
import android.util.AttributeSet
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View


class DrawingView(context:Context,attrs: AttributeSet) : View(context,attrs) {
    private var mDrawPath:CustomPath?=null
    private var mCanvasBitmap: Bitmap?=null
    private var mDrawPaint: Paint?=null
    private var mCanvasPaint: Paint?=null
    private var mBrushSize: Float=0.toFloat()
    private var color = Color.BLACK
    private var canvas: Canvas?=null

    //Keeping/hold the drawn path on the screen
    private val mPaths=ArrayList<CustomPath>()
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
        //mBrushSize=20.toFloat()

    }

    //We need to make changes in View so some override methods needed to be defined

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mCanvasBitmap=Bitmap.createBitmap(w,h,Bitmap.Config.ARGB_8888)

        //Bitmap is a class in Android that represents an image or a bitmap. In this case, it's used to create a bitmap for drawing
        //Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888) is a static method that creates a new bitmap with the specified width (w) and height (h) and the configuration Bitmap.Config.ARGB_8888.
        //
        //w and h represent the width and height of the new bitmap, respectively.
        //
        //Bitmap.Config.ARGB_8888 is one of the bitmap configurations, and it stands for 32-bit per pixel with alpha. It's a common choice for working with images because it provides support for transparency (alpha channel) and a wide range of colors
        canvas=Canvas(mCanvasBitmap!!)

    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawBitmap(mCanvasBitmap!!,0f,0f,mCanvasPaint)

        for(path in mPaths){
            mDrawPaint!!.strokeWidth=path.brushThickness
            mDrawPaint!!.color=path.color
            canvas.drawPath(path,mDrawPaint!!)
        }

        //canvas is a Canvas object. It's the surface where you want to draw graphics, and it's often associated with the display or a Bitmap that you want to draw onto.
        //
        //drawBitmap() is a method of the Canvas class used to draw a Bitmap onto the canvas.
        //
        //mCanvasBitmap is the Bitmap that you want to draw onto the canvas. The !! operator is used to assert that the mCanvasBitmap is non-null.
        //
        //0f is the x-coordinate where you want to draw the top-left corner of the mCanvasBitmap. In this case, it's drawn at the x-coordinate 0.
        //
        //0f is the y-coordinate where you want to draw the top-left corner of the `mCanvasBitmap. In this case, it's drawn at the y-coordinate 0.
        //
        //mCanvasPaint is the Paint object that you want to use for drawing the Bitmap. This Paint object defines how the Bitmap is drawn, including attributes like color, stroke width, and other styling options
        
        if(!mDrawPath!!.isEmpty){//if path is not empty then only draw so mDrawPath is filled using onTouchEvent
            mDrawPaint!!.strokeWidth=mDrawPath!!.brushThickness
            mDrawPaint!!.color=mDrawPath!!.color
            canvas.drawPath(mDrawPath!!,mDrawPaint!!)
        }

    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        //onTouchEvent is an override of the method from the Android View or ViewGroup class that allows you to respond to touch events.
        //
        //event is the MotionEvent object that represents the touch event, and it's passed as a parameter to the method. This object contains information about the touch, such as the touch coordinates, action type, and more.
        val touchX=event?.x
        val touchY=event?.y


        when(event?.action){
            //In the ACTION_DOWN case, when the user touches the screen, you're setting the color and brush thickness for the current drawing path (mDrawPath), resetting the path, and moving to the starting point at the touch coordinates.
            //
            //In the ACTION_MOVE case, you're using the lineTo method to draw lines as the user's finger or touch moves on the screen. This continues until the user releases their touch.
            //
            //In the ACTION_UP case, when the user lifts their finger from the screen, you're creating a new CustomPath object to start fresh with a new drawing path.
            //
            //The invalidate() method is called to request a redraw of the view. This is important to update the drawing on the screen.
            //
            //Finally, you return true to indicate that you have handled the touch event
            MotionEvent.ACTION_DOWN->{//using lambda expression
                mDrawPath!!.color=color
                mDrawPath!!.brushThickness=mBrushSize

                mDrawPath!!.reset()
                mDrawPath!!.moveTo(touchX!!,touchY!!)
            }

            MotionEvent.ACTION_MOVE->{
                mDrawPath!!.lineTo(touchX!!,touchY!!)

            }

            MotionEvent.ACTION_UP->{
                mPaths.add(mDrawPath!!)
                mDrawPath=CustomPath(color,mBrushSize)
            }

            else->return false
        }
        invalidate()
        return true

    }

    //Preparing the BrushSizeSelector and How to Use Display Metrics
    fun setSizeForBrush(newSize:Float){
        //mBrushSize = TypedValue.applyDimension(: This line assigns a new value to the mBrushSize variable. It calculates the brush size based on the newSize parameter and the device's display metrics.
        //
        //TypedValue.applyDimension(: This is a method that converts an arbitrary size value (in this case, newSize) from one unit to another based on the device's display metrics. It's often used to convert sizes from device-independent pixels (dp) to actual pixel values (px).
        //
        //TypedValue.COMPLEX_UNIT_DIP, newSize, resources.displayMetrics: Here, you specify the unit of the newSize (DIP, which is Density-Independent Pixels), the newSize value itself, and the display metrics of the device. This information is used to perform the conversion
        mBrushSize=TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
            newSize,resources.displayMetrics
        )
        mDrawPaint!!.strokeWidth=mBrushSize
    }








    internal inner class CustomPath(var color:Int, var brushThickness:Float):android.graphics.Path() {
        //read about path by ctrl+click

    }
}
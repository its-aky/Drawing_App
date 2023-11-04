package com.example.drawingapp

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.view.get

class MainActivity : AppCompatActivity() {
    private var drawingView:DrawingView?=null
    private var mImageButtonCurrentPaint:ImageButton?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        drawingView=findViewById(R.id.drawing_view)
        drawingView?.setSizeForBrush(20.toFloat())

        val linearLayoutPaintColors=findViewById<LinearLayout>(R.id.ll_paint_colors)
        //mImageButtonCurrentPaint = linearLayoutPaintColors[2] as ImageButton: This line retrieves the third child view (at index 2) of the linearLayoutPaintColors and casts it to an ImageButton. The assumption here is that the third child is an ImageButton.
        //
        //mImageButtonCurrentPaint!!.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.pallet_pressed)): This line sets the image drawable of the mImageButtonCurrentPaint. It loads the drawable from the R.drawable.pallet_pressed resource using ContextCompat.getDrawable and sets it as the new drawable for the ImageButton. This action likely changes the appearance of the ImageButton to represent that it's "pressed" or active.
        mImageButtonCurrentPaint=linearLayoutPaintColors[2] as ImageButton
        mImageButtonCurrentPaint!!.setImageDrawable(
            ContextCompat.getDrawable(this,R.drawable.pallet_pressed)
        )

        val ib_brush:ImageButton=findViewById(R.id.ib_brush)
        ib_brush.setOnClickListener{
            showBrushSizeChooserDialog()
        }
    }

    //Creating the opening dialog for the brush size selector
    private fun showBrushSizeChooserDialog(){
        val brushDialog= Dialog(this)
        brushDialog.setContentView(R.layout.dialog_brush_size)
        brushDialog.setTitle("Brush Size: ")
        val smallBtn:ImageButton=brushDialog.findViewById(R.id.ib_small_brush)
        smallBtn.setOnClickListener{
            drawingView?.setSizeForBrush(10.toFloat())
            brushDialog.dismiss()
        }

        val mediumBtn:ImageButton=brushDialog.findViewById(R.id.ib_medium_brush)
        mediumBtn.setOnClickListener{
            drawingView?.setSizeForBrush(20.toFloat())
            brushDialog.dismiss()
        }

        val largeBtn:ImageButton=brushDialog.findViewById(R.id.ib_large_brush)
        largeBtn.setOnClickListener{
            drawingView?.setSizeForBrush(30.toFloat())
            brushDialog.dismiss()
        }

        brushDialog.show()

    }
}
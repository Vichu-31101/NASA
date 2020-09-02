package com.example.nasa

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.os.Build
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
class Splash(context: Context): View(context) {
    var lineP = Paint()
    var pointP = Paint()
    var start = false
    var space = 0f
    val points = mutableListOf<Point>()
    var h = Resources.getSystem().displayMetrics.heightPixels
    init {
        lineP.strokeWidth = 10f
        lineP.textSize = 100f
        lineP.isFakeBoldText = true
        lineP.textAlign = Paint.Align.CENTER
        lineP.isAntiAlias = true
        lineP.letterSpacing = 0.5f
        lineP.typeface = Typeface.MONOSPACE
        lineP.color = Color.parseColor("#FFFFFF")
        pointP.color = Color.parseColor("#FF3E64")
        pointP.strokeWidth = 10f
        pointP.isAntiAlias = true
    }
    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
        if(!start){
            for(i in (0..50)){
                points.add(Point((0..width).random(),(0..height).random(),(0..1).random()))
            }
            timer.start()
            start=true
        }

        canvas?.drawColor(Color.parseColor("#212121"))

        for(i in points){
            Log.d("tests",i.isGrow.toString())
            canvas?.drawCircle(
                i.x.toFloat(),
                i.y.toFloat(),
                i.size,
                pointP
            )
        }

        canvas?.drawText(
            "NASA",
            width/2.toFloat(),
            height-space,
            lineP
        )
        canvas?.drawCircle(
            width/2.toFloat(),
            height+800f-(space*0.2f),
            800f,
            pointP
        )






    }
    var timer = object: CountDownTimer(4015, 1) {
        override fun onFinish() {
            val intent = Intent (context, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }

        override fun onTick(millisUntilFinished: Long) {
            invalidate()
            for(i in points){
                i.twinkle()
            }
            if(space < h/2){
                space += 10f
            }

        }
    }

    class Point(var x: Int, var y: Int, var isGrow: Int, var size:Float = 7f, val rate: Float = (15..25).random()*0.01f){
        fun twinkle(){
            if(isGrow == 1){
                if(size < 15f){
                    size += rate
                }
                else{
                    isGrow = 0
                }
            }
            else{
                if(size > 3f){
                    size -= rate
                }
                else{
                    isGrow = 1
                }
            }
        }
    }
}
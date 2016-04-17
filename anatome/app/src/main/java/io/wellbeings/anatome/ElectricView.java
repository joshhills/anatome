package io.wellbeings.anatome;

import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Elizabeth on 11/04/2016.
 *
 * Purpose: A class to provide an electrical animation for the brain within the main scroll
 */
public class ElectricView extends View {

    Paint paint;
    Path path;
    List<Point> points = new ArrayList<Point>();
    Point direction;


    public ElectricView(Context context) {
        super(context);
        init();
    }

    public ElectricView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ElectricView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init(){
        paint = new Paint();

        paint.setStyle(Paint.Style.STROKE);

    }

    // generate random number
    Random random  = new Random();

    int nextDrawFrame;
    int i;
    @Override
    protected void onDraw(Canvas canvas) {
        i++;

        super.onDraw(canvas);

        // limit the number of times per second
        if ( i%4 ==0 && i > nextDrawFrame) {

            if (points.size() > 8) {
                points.clear();
                nextDrawFrame = nextDrawFrame + 100;


            }

            if (points.size()== 0 && i > nextDrawFrame) {

                int randomx = random.nextInt(100) - 50;
                int randomy = random.nextInt(100) - 50;

                // find the center point
                Point start = new Point(this.getMeasuredWidth()/2 + randomx,  this.getMeasuredHeight()/2 + randomy);

                points.add(start);

                // calculate random angle
                int a = random.nextInt(360);


                int y = (int) ( Math.sin(a) * 40);

                int x = (int) (Math.cos(a) * 40);

                direction  = new Point(x,y);

                // calculate the direction of the next electric bolt
                Point point = new Point(x + start.x,y + start.y);
                points.add(point);


            }


                if (points.size() > 0) {

                    // generate a random number to use the the next point
                    int x = random.nextInt(100) - 50;
                    int y = random.nextInt(100) - 50;

                    // get last point in the list
                    Point lastPoint = points.get(points.size() - 1);


                    if (points.size() == 6) {

                        lastPoint = points.get(points.size() - 3);

                    }

                    // calculate the end point of the line
                    Point point = new Point(x + direction.x + lastPoint.x, y + direction.y + lastPoint.y);

                    // add the the list of points
                    points.add(point);

                }






        }





        // check there is a start and finish point
        if (points.size() >= 2) {


            path = new Path();
            paint.setColor(Color.parseColor("#cefffb"));
            paint.setStrokeWidth(10);
            paint.setShader(new RadialGradient(8f, 80f, 30f, Color.parseColor("#cefffb"), Color.WHITE, Shader.TileMode.MIRROR));
            paint.setMaskFilter(new BlurMaskFilter(30, BlurMaskFilter.Blur.OUTER));
            paint.setMaskFilter(new BlurMaskFilter(15, BlurMaskFilter.Blur.SOLID));
            path.moveTo(points.get(0).x, points.get(0).y);


            int i = 0;


           // loop through points, draw a line to each
            for (Point j : points) {


                if (i == 6) {

                    path.moveTo(points.get(3).x, points.get(3).y);
                }

                path.lineTo(j.x, j.y);

                i++;
            }

            canvas.drawPath(path, paint);

        }


        this.invalidate();



    }


}


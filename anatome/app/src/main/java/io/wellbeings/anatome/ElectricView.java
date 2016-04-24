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
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Provide an electrical animation for
 * the brain within the main scroll.
 *
 * @author Team WellBeings - Lizzie
 */
public class ElectricView extends View {
    
    // constant values for line drawing calculations
    private static final int FRAMES_BEFORE_DRAW = 4;
    private static final int DELAY_BETWEEN_BOLTS = 100;
    private static final int MAX_POINTS = 8;
    private static final int RANDOM_POINT_RANGE = 100;
    private static final int SEGMENT_LENGTH = 40;
    private static final int MAX_POINTS_BEFORE_FORK = 6;
    private static final int FORK_POINT_INDEX = 3;
    private static final int LINE_WIDTH = 10;

    // painting context for line
    Paint paint;
    // store current points
    List<Point> points = new ArrayList<Point>();
    // current direction of line
    Point direction;

    /**
     * Overload constructors of the View Class,
     * it is necessary to run the init method on each instance
     * **/
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

    /**
     * set styles of the lines
     */
    private void init(){
       
       // load lightning bolt colour 
       int lightningBoltColour = ContextCompat.getColor(this.getContext(), R.color.electric_bolt );

        // create new paint and set rendering style
        paint = new Paint();
        // drawing lines so render style is stroke
        paint.setStyle(Paint.Style.STROKE);
        // set colour of line
        paint.setColor(lightningBoltColour);
        // set width of line
        paint.setStrokeWidth(LINE_WIDTH);
        // set styling of the line
        paint.setShader(new RadialGradient(8f, 80f, 30f, lightningBoltColour , Color.WHITE, Shader.TileMode.MIRROR));
    }

    // create random number generator
    // used for random angles and position of lightning
    Random random  = new Random();

    // store current frame number
    int currentFrame;
    // store next frame number to start drawing lightning bolt
    int nextDrawFrame;


    /**
     * Logic for drawing lightning bolt
      * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        currentFrame++;
        
        super.onDraw(canvas);

        // set the frames per second
        if ( currentFrame % FRAMES_BEFORE_DRAW == 0 && currentFrame > nextDrawFrame) {

            // check lightning bolt is complete?
            if (points.size() > MAX_POINTS) {
                // start a new lightning bolt
                points.clear();
                // add a delay
                nextDrawFrame = nextDrawFrame + DELAY_BETWEEN_BOLTS;
            }

            // create the first point if no points exist
            if (points.size()== 0 && currentFrame > nextDrawFrame) {

                // generate numbers for a random centre point offset
                int randomx = random.nextInt(RANDOM_POINT_RANGE) - RANDOM_POINT_RANGE/2;
                int randomy = random.nextInt(RANDOM_POINT_RANGE) - RANDOM_POINT_RANGE/2;

                // find the center point, and add the random offset
                Point start = new Point(this.getMeasuredWidth()/2 + randomx,  this.getMeasuredHeight()/4 + randomy);

                // add the starting position to the points
                points.add(start);

                // calculate a random angle for the direction
                int angle = random.nextInt(360);

                // calculate x and y components of random angle
                int x = (int) (Math.cos(angle) * SEGMENT_LENGTH);
                int y = (int) ( Math.sin(angle) * SEGMENT_LENGTH);

                // store the initial direction of the lightning bolt
                direction  = new Point(x,y);

                // add new direction to the random start point
                Point point = new Point(x + start.x,y + start.y);
                // add to the points
                points.add(point);
            }

                // check if there is a start point
                if (points.size() > 0) {

                    // generate numbers for a random centre point offset
                    int x = random.nextInt(RANDOM_POINT_RANGE) - RANDOM_POINT_RANGE/2;
                    int y = random.nextInt(RANDOM_POINT_RANGE) - RANDOM_POINT_RANGE/2;

                    // get the last point drawn
                    Point lastPoint = points.get(points.size() - 1);

                    // check if ready to fork point
                    if (points.size() == MAX_POINTS_BEFORE_FORK) {

                        // set last point to be start of fork
                        lastPoint = points.get(points.size() - FORK_POINT_INDEX);

                    }

                    // add new direction and random offset to the previous point
                    Point point = new Point(x + direction.x + lastPoint.x, y + direction.y + lastPoint.y);

                    // add to the points
                    points.add(point);
                }
        }


        // check there is at least two points to draw the line
        if (points.size() >= 2) {

            // create a new drawing path
           Path path = new Path();
            // set the start position to the first point
            path.moveTo(points.get(0).x, points.get(0).y);

           // loop through points, draw a line to each
            for (int i = 0; i < points.size(); i++){

                //check for fork
                if (i == MAX_POINTS_BEFORE_FORK) {

                    // move the path to the fork start, without drawing
                    path.moveTo(points.get(FORK_POINT_INDEX).x, points.get(FORK_POINT_INDEX).y);
                }

                // draw to the next point
                path.lineTo(points.get(i).x, points.get(i).y);
            }

            // draw the path on the canvas
            canvas.drawPath(path, paint);
        }
        // redrawn immediately
        this.invalidate();
    }
}


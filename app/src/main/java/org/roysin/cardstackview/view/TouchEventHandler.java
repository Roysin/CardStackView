package org.roysin.cardstackview.view;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import org.roysin.cardstackview.utils.LogTool;

/**
 * Created by Administrator on 2016/5/5.
 */
public class TouchEventHandler{

    private static final String TAG = "TouchEventHandler";
    CardLayoutAlgorithm mLayoutAlgorithm;
    CardStackView mCSview;
    CardStackViewScroller mScroller;
    VelocityTracker mTracker;
    public TouchEventHandler(CardStackView cardStackView){
        mCSview = cardStackView;
        mLayoutAlgorithm = mCSview.getLayoutAlgorithm();
        mScroller = mCSview.getScroller();
    }

//    float downY,distance,lastDistance,speedX,speedY;
    float downX,downY,curX,curY;
    boolean clicked = false;
    final float MAX_DISTANCE = 10;
    public boolean onTouch(MotionEvent event) {
        if(mCSview.findChildByMotionEvent(event) <0){
            return false;
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = event.getX();
                downY = event.getY();
                clicked = true;
                LogTool.d("ACTION_DOWN downY " + downY);
                break;
            case MotionEvent.ACTION_MOVE:
                curX = event.getX();
                curY = event.getY();
                if(Math.abs(curX - downX) + Math.abs(curY- downY) > MAX_DISTANCE){
                    LogTool.d("ACTION_MOVE downY " + curY);
                    clicked = false;
                }
                break;
            case MotionEvent.ACTION_UP:
                if(clicked){
                    LogTool.d("ACTION_UP clicked " + clicked);
                    if(mCSview.cardState != mCSview.STATE_FOCUS){
                        int position = mCSview.findChildByMotionEvent(event);
                        if(position > -1){
                            mCSview.updateState(CardStackView.STATE_FOCUS,position);
                        }
                    }else {
                        mCSview.updateState(CardStackView.STATE_NORMAL,-1);
                    }
                }
                break;
        }


//        if(mScroller == null){
//            return false;
//        }
//        if(mTracker == null){
//            mTracker = VelocityTracker.obtain();
//        }
//        mTracker.addMovement(event);
//        switch (event.getAction()){
//            case MotionEvent.ACTION_CANCEL:
//                LogUtil.d(TAG,"ACTION_CANCEL downy " + downY);
//                break;
//            case MotionEvent.ACTION_DOWN:
//                if(mScroller.isAutoScrolling){
//                    mScroller.stopScrolling();
//                }
//                downY = event.getY();
//                LogUtil.d(TAG,"ACTION_DOWN downy " + downY);
//                break;
//            case MotionEvent.ACTION_MOVE:
//                if(downY == 0){
//                    downY = event.getY();
//                }
//                LogUtil.d(TAG,"ACTION_MOVE downy " + downY);
//                distance = (event.getY()-downY);
//                if(Math.abs(distance) > 10){
//                    mScroller.scroll(distance - lastDistance);
//                }
//                lastDistance = distance;
//                break;
//            case MotionEvent.ACTION_UP:
//                downY = 0;
//                distance = 0;
//                lastDistance = 0;
//                mTracker.computeCurrentVelocity(1,10);
//                speedX = mTracker.getXVelocity();
//                speedY = mTracker.getYVelocity();
//                mayContinueScrolling(speedX,speedY);
//                break;
//        }
        return true;
    }

    private void mayContinueScrolling(float speedX, float speedY) {
        mScroller.autoScroll(speedX,speedY);
    }
}

package org.roysin.cardstackview.view;

import android.animation.Animator;
import android.animation.ValueAnimator;

import org.roysin.cardstackview.view.Interpolator.FocusInterpolator;
import org.roysin.cardstackview.view.Interpolator.FreeInterpolator;
import org.roysin.cardstackview.view.Interpolator.LinearInterpolator;
import org.roysin.cardstackview.view.Interpolator.SpaceInterpolator;
import org.roysin.cardstackview.view.Interpolator.Translator;
import org.roysin.cardstackview.utils.LogTool;

/**
 * Created by Administrator on 2016/5/6.
 */
public class CardStackViewScroller {
    private static final String TAG = "CardStackViewScroller";
    CardStackView mCSview;
    CardLayoutAlgorithm mLayoutAlgorithm;
    public boolean isAutoScrolling;

    private ValueAnimator mClickAnimator;
    private Translator mTranslator;

    private ValueAnimator mAnimator;
    public CardStackViewScroller(CardStackView cardStackView){
        if(cardStackView == null){
            throw new IllegalArgumentException("mCSview must not be null");
        }
        mCSview = cardStackView;
        mLayoutAlgorithm = mCSview.getLayoutAlgorithm();
    }

    public void scroll(float distance){
        if(mLayoutAlgorithm == null) return;
        mLayoutAlgorithm.changePaddingTop(distance);
        mCSview.requestLayout();
    }

    public void scrollTo(float position){
        if(mLayoutAlgorithm == null) return;
        mLayoutAlgorithm.setPaddintTop(position);
        mCSview.requestLayout();
    }

    public void focusOn(int position){

    }



    float distance,lastDistance,speedX,speedY;
    long duration;
    final float ACC = -0.01f;
    float acc;
    public void autoScroll( float speed_X,  float speed_Y) {
        stopScrolling();
        speedX = speed_X;
        speedY = speed_Y;
        if(speedY > 0){
            acc = ACC;
        }else {
            acc = 0 - ACC;
        }
        duration = Math.abs((long)(speedY/acc));
//        final double speed = Math.sqrt(speedX*speedX+speedY*speedY);
        LogTool.d("autoScroll speedY : " +speedY+" duration : " +duration);

        if(mAnimator == null){
            mAnimator = ValueAnimator.ofFloat(0,100);
            mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                float time;
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    time = animation.getAnimatedFraction()*duration;
                    distance = (speedY + 0.5f*acc*time)*time;

                    LogTool.d("autoScroll Animation time : " +time
                            +" distance : " + distance
                            + " lastDistance:" + lastDistance);

                    scroll(distance - lastDistance);
                    lastDistance = distance;

                }
            });
            mAnimator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    isAutoScrolling = true;
                    lastDistance = distance = 0;
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    lastDistance = distance = 0;
                    isAutoScrolling = false;
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                    isAutoScrolling = false;
                    lastDistance = distance = 0;
                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
        }
        mAnimator.setDuration(duration);
        mAnimator.start();
    }

    public void stopScrolling(){
        if(mAnimator != null && mAnimator.isRunning()){
            mAnimator.cancel();
        }
        distance = 0;
        lastDistance = 0;
        isAutoScrolling = false;
    }



    public void scrollToFocus(int focus) {
        LogTool.d( "scrollToFocus");
        FocusInterpolator to = new FocusInterpolator(mLayoutAlgorithm.getCardAmount());
        to.setFocus(focus);
        SpaceInterpolator from = mLayoutAlgorithm.getInterpolator();
        startClickAnimation(from,to);
    }

    public void scrollToNormal(){
        LogTool.d( "scrollToNormal");
        LinearInterpolator to = new LinearInterpolator(mLayoutAlgorithm.getCardAmount());
        SpaceInterpolator from = mLayoutAlgorithm.getInterpolator();
        startClickAnimation(from,to);
    }
    public void scrollToExpand(){

    }

    private void startClickAnimation(SpaceInterpolator from,final SpaceInterpolator to){
        long duration = 300;
        mTranslator = Translator.of(duration, from, to);
        if(mClickAnimator != null){
            mClickAnimator.end();
        }

        mClickAnimator = ValueAnimator.ofFloat(0f, 100f);
        mClickAnimator.setDuration(duration);
        mClickAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
               LogTool.d( "onAnimationUpdate");
                float percent = animation.getAnimatedFraction();
                if (mTranslator != null) {
                    FreeInterpolator interpolator = mTranslator.getResultAt(percent);
                    mLayoutAlgorithm.setInterpolator(interpolator);
                    mCSview.requestLayout();
                }

            }
        });

        mClickAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                LogTool.d( "onAnimationEnd");
                mLayoutAlgorithm.setInterpolator(to);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                LogTool.d( "onAnimationCancel");
//                    mLayoutAlgorithm.setInterpolator(to);
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        mClickAnimator.start();
    }
}

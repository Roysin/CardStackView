package org.roysin.cardstackview.view;

import android.graphics.Rect;


import org.roysin.cardstackview.cardData.CardInfo;
import org.roysin.cardstackview.view.Interpolator.LinearInterpolator;
import org.roysin.cardstackview.view.Interpolator.SpaceInterpolator;
import org.roysin.cardstackview.utils.LogTool;

/**
 * Created by Administrator on 2016/5/4.
 */
public class CardLayoutAlgorithm {
    //GAP means distance between two closed cards.
    private final int MAX_GAP = 2400;
    private final int MIN_GAP = 0;

    private  int maxPaddingTop = 2400;
    private  int minPaddingTop = 20;

    private int paddingTop = 0;
    private int paddingBottom = 20;


    private final int MAX_VISIBLE_ITEMS = 8;

    private boolean mLastCardFullyVisible = false;

    private int mCardAmount;
    private Rect mParentBound;
    private Rect mCardRect;
    private SpaceInterpolator interpolator;

    public int getCardAmount(){
        return mCardAmount;
    }


    public void setCardAmount(int number){
        mCardAmount = number;
        interpolator = new LinearInterpolator(number);
    }

    public Rect computePostion(int position, Rect parentBound){
        if(position > mCardAmount){
            throw  new IllegalArgumentException("itemId should less than " + mCardAmount + " !");
        }
        if(interpolator == null){
            return null;
        }
        updateParent(parentBound);
        int pLeft = parentBound.left;
        int pTop = parentBound.top;
        int pWidth = parentBound.width();
        int pHeight = parentBound.height();
        LogTool.i("computePostion() pWidth: " + pWidth + " pHeight: " + pHeight );
        int cardsWidth,cardsHeight;
        if(isExpanded()){
            cardsWidth = pWidth;
        }else{
            cardsWidth = (int)((1.0f-(mCardAmount-1-position)/100.0f)*pWidth);
        }
        if(mLastCardFullyVisible){
            cardsHeight = pHeight - paddingTop - paddingBottom -(int)(pWidth* CardInfo.hdw);//last card is visible
        }else{
            cardsHeight = pHeight - paddingTop - paddingBottom;
        }
        if(cardsWidth <=0 || cardsHeight <= 0){
            //// TODO: 2016/5/5
        }
        //int spaceFromTop = (int) ((1.0f-(mCardAmount - position)/(50.0f))*(float)cardsHeight/(float) mCardAmount);
//        int spaceFromTop = (int) ((float)cardsHeight/((float) mCardAmount -1));
        int spaceFromTop = (int)(cardsHeight*interpolator.getValueAt(position));
//        if(spaceFromTop < MIN_GAP){
//            spaceFromTop = MIN_GAP;
//        }else if(spaceFromTop > MAX_GAP){
//            spaceFromTop = MAX_GAP;
//        }
        LogTool.i(" cardsWidth: " + cardsWidth
                + " cardsHeight: " + cardsHeight
                + " spaceFromTop: " + spaceFromTop );

        if(mCardRect == null){
            mCardRect = new Rect();
        }

        //calculate child's layout rect.
        int left,top,right,bottom;
        left = (int)((pWidth - cardsWidth)/2.0f);
        top = paddingTop + spaceFromTop;
        right = left + cardsWidth;
        bottom = top + (int)(cardsWidth* CardInfo.hdw);
        LogTool.d("left: "+ left
                + " top: " + top
                + " right: " + right
                + " bottom: " + bottom);
        mCardRect.set(left,top,right,bottom);
        return mCardRect;
    }

    private void updateParent(Rect parentBound) {
        mParentBound = parentBound;
        maxPaddingTop = (int)(parentBound.height()*0.95f);
    }

    public void changePaddingTop(float delta){
        paddingTop += delta;
        setPaddintTop(paddingTop);
    }
    public void setPaddintTop(float top){
        paddingTop = (int)top;
        if(paddingTop < minPaddingTop){
            paddingTop = minPaddingTop;
        }else if(paddingTop > maxPaddingTop){
            paddingTop = maxPaddingTop;
        }
        LogTool.d("setPaddintTop paddingTop " + paddingTop);
    }

    private boolean isExpanded(){
        boolean isExpended = false;
        if(mParentBound != null){
            LogTool.d("isExpanded  paddingTop" +paddingTop+ " threshold " +mParentBound.height()*0.85f);
            if (paddingTop < (int)(mParentBound.height()*0.85f)){
                isExpended = true;
            }
        }
        LogTool.d("isExpanded  " + isExpended);
        return isExpended;
    }

    public void setInterpolator(SpaceInterpolator interpolator){
        this.interpolator = interpolator;
    }

    public SpaceInterpolator getInterpolator() {
        return interpolator;
    }
}

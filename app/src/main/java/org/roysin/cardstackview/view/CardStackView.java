package org.roysin.cardstackview.view;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import org.roysin.cardstackview.cardData.CardInfo;
import org.roysin.cardstackview.utils.LogTool;

import java.util.List;


/**
 * Created by Administrator on 2016/5/4.
 */
public class CardStackView extends FrameLayout{

    private static final String TAG ="CardStackView" ;
    // a focus card is the card that selected to show independently
    private final int DEFAULT_FOCUS = -1;
    public static final int STATE_NORMAL = 0x00;
    public static final int STATE_FOCUS = STATE_NORMAL + 1;
    public static final int STATE_EXPANDED = STATE_NORMAL + 2;
    protected int cardState;

    private CardStackAdapter mAdapter;
    private List<CardInfo> mCards;
    private CardLayoutAlgorithm mLayoutAlgorithm;
    private TouchEventHandler mTouchHandler;
    private CardStackViewScroller mScroller;
    private Rect tmpRect;
    private Rect mBounds;
    private int focus = DEFAULT_FOCUS;
    private StateChangedListener listener;

    public CardStackView(Context context) {
        super(context);
        init();
    }

    public CardStackView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CardStackView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        if(mAdapter == null || mAdapter.getData() ==null){
            return;
        }
        View v;
        mBounds.set(left,top,right,bottom);
        for(int i=0;i<getChildCount();i++){
            v = getChildAt(i);
            tmpRect = mLayoutAlgorithm.computePostion(i,mBounds);
            if(tmpRect != null) {
                v.layout(tmpRect.left,
                        tmpRect.top,
                        tmpRect.right,
                        tmpRect.bottom);
            }
        }
    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(mLayoutAlgorithm == null) {
            return super.onTouchEvent(event);
        }
        return mTouchHandler.onTouch(event);
    }

    Rect childRect = new Rect();
    protected int findChildByMotionEvent(MotionEvent ev){

        int x = (int)ev.getX() - mBounds.left;
        int y = (int)ev.getY() - mBounds.top;
        int childCount = mLayoutAlgorithm.getCardAmount();
        int target = -1;
        for(int i=0;i<childCount;i++){
            childRect = mLayoutAlgorithm.computePostion(i,mBounds);
            if (childRect.contains(x,y)){
                target = i;
            }
        }
        LogTool.d("findChildByMotionEvent x,y " + x +"," + y + " in target" +target);
        return target;
    }

//    float downX,downY,curX,curY;
//    final float MAX_DISTANCE = 10;
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if(findChildByMotionEvent(ev) > 0){
            return true;
        }else{
            return false;
        }
//        switch (ev.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                downX = ev.getX();
//                downY = ev.getY();
//                LogUtil.d(TAG,"ACTION_DOWN downY " + downY);
//                break;
//            case MotionEvent.ACTION_MOVE:
//                curX = ev.getX();
//                curY = ev.getY();
//                if(Math.abs(curX - downX) + Math.abs(curY- downY) > MAX_DISTANCE){
//                    LogUtil.d(TAG,"ACTION_MOVE downY " + curY);
//                    return true;
//                }
//                break;
//            case MotionEvent.ACTION_UP:
//                break;
//        }
//        return false;
    }

    private void init(){
        mLayoutAlgorithm = new CardLayoutAlgorithm();
        mTouchHandler = new TouchEventHandler(this);
        mScroller = new CardStackViewScroller(this);
        mBounds = new Rect();
        cardState = STATE_NORMAL;
    }

    public void setAdapter(CardStackAdapter adapter){
        mAdapter = adapter;
        mCards = mAdapter.getData();
        if(mCards != null) {
            mLayoutAlgorithm.setCardAmount(mCards.size());
        }
        updateChildViews();
    }

    private void updateChildViews() {
        removeAllViews();
        if(mCards != null){
            View v;
            for(int i=0;i<mCards.size();i++) {
                v = mAdapter.getView(i, this);
                addView(v);
            }
            requestLayout();
        }
    }
    public CardLayoutAlgorithm getLayoutAlgorithm(){
        return mLayoutAlgorithm;
    }



    public CardStackViewScroller getScroller() {
        return mScroller;
    }

    public void updateState(int state,int clickIndex) {
        switch (state){
            case STATE_FOCUS:
                focus = clickIndex;
                mScroller.scrollToFocus(clickIndex);
                break;
            case STATE_EXPANDED:
            case STATE_NORMAL:
                mScroller.scrollToNormal();
                focus = DEFAULT_FOCUS;
                break;
        }
        this.cardState = state;
        if(listener != null){
            listener.OnStackViewStateChanged(state,clickIndex);
        }
    }

//    public CardInfo getFocusCard() {
//        SpaceInterpolator interpolator = mLayoutAlgorithm.getInterpolator();
//        int focus = -1;
//        if(interpolator instanceof FocusInterpolator){
//            focus = ((FocusInterpolator) interpolator).getFocus();
//        }
//        if(focus >-1){
//            return mAdapter.getItem(focus);
//        }else {
//            return null;
//        }
//    }

    /**
     * the focused card is one has been clicked just now.
     * @return
     */
    public CardInfo getFocusCard() {
        if(focus >-1){
            return mAdapter.getItem(focus);
        }else {
            return null;
        }
    }

    public interface StateChangedListener{
        void OnStackViewStateChanged(int stateChangedTo, int clickIndex);
    }
    public void setOnStateChangedListener(StateChangedListener l){
        listener = l;
    }
}

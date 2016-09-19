package org.roysin.cardstackview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.ImageView;

import org.roysin.cardstackview.R;
import org.roysin.cardstackview.cardData.CardInfo;

/**
 * Created by Administrator on 2016/5/4.
 */
public class CardView extends ImageView {
    private static final String TAG = "CardView";
    private CardInfo mCard;
    private Paint mNumberPaint;

    public CardView(Context context) {
        super(context);
        init();
    }
    public CardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public CardView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public void init(){
        mNumberPaint = new Paint();
        mNumberPaint.setColor(Color.WHITE);
        mNumberPaint.setTextSize(70f);
        mNumberPaint.setStyle(Paint.Style.FILL);
        mNumberPaint.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD));
        mNumberPaint.setAntiAlias(true);
        float[] black = { 0f, 0f, 0f };
        mNumberPaint.setShadowLayer(1.5f, 0.5f, 0f, Color.HSVToColor(200, black));
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void setCardData(CardInfo info){
        mCard = info;
        invalidate();
    }


    float cardx,cardy;
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        setBackgroundResource(getBankImage(mCard));
        cardx = getWidth()*0.1f;
        cardy = getHeight()*0.9f;
        if(mCard != null && mCard.cardNumber != null) {
            canvas.drawText(mCard.cardNumber,cardx,cardy,mNumberPaint);
        }

    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        LogUtil.d(TAG,"onTouchEvent");
//        switch (event.getAction()){
//            case MotionEvent.ACTION_DOWN:
//                LogUtil.d(TAG,"ACTION_DOWN");
//                break;
//            case MotionEvent.ACTION_MOVE:
//                LogUtil.d(TAG,"ACTION_MOVE");
//                break;
//            case MotionEvent.ACTION_UP:
//                LogUtil.d(TAG,"ACTION_UP");
//                break;
//        }
//        return super.onTouchEvent(event);
//    }

    private int getBankImage(CardInfo card) {
        if(card != null){

        }
        return R.drawable.bankcard;
    }

}

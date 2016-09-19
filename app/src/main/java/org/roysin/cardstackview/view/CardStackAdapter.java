package org.roysin.cardstackview.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.roysin.cardstackview.cardData.CardInfo;
import org.roysin.cardstackview.utils.LogTool;

import java.util.List;


/**
 * Created by Administrator on 2016/5/4.
 */
public class CardStackAdapter{

    private static final String TAG = "CardStackAdapter" ;
    private List<CardInfo> mCards;
    private ViewPool mViewPool;
    private LayoutInflater mInflater;
    private Context mContext;

    public CardStackAdapter(Context ctx){
        mContext = ctx;
        mViewPool = new ViewPool();
        mInflater = LayoutInflater.from(mContext);
    }
    public void setData(List<CardInfo> cards){
        this.mCards = cards;
    }


    public int getCount() {
        return mCards.size();
    }

    public CardInfo getItem(int position) {
        return mCards.get(position);
    }


    public long getItemId(int position) {
        return Long.valueOf(mCards.get(position).cardNumber);
    }


    public View getView(final int position, ViewGroup parent) {
        if(mViewPool == null){
            return null;
        }
        CardView v = (CardView) mViewPool.get(mCards.get(position).cardNumber);
        LogTool.d("Adapter getView v = " +v);
        if(v == null){
            v = new CardView(mContext);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            v.setLayoutParams(params);
            v.setCardData(mCards.get(position));
            mViewPool.put(mCards.get(position).cardNumber,v);
        }
        if(v.getParent() !=null){
            ((ViewGroup) v.getParent()).removeView(v);
        }
        return v;
    }

    public List<CardInfo> getData() {
        return mCards;
    }
}

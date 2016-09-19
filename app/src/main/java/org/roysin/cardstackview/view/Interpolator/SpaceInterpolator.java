package org.roysin.cardstackview.view.Interpolator;

/**
 * Created by Administrator on 2016/7/22.
 */
public abstract class SpaceInterpolator {
    protected int mPointNumber;
    SpaceInterpolator(int points){
        this.mPointNumber = points;
    }
    abstract public float getValueAt(int position);
}

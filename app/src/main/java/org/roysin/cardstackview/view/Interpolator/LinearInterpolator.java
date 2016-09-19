package org.roysin.cardstackview.view.Interpolator;

/**
 * Created by Administrator on 2016/7/22.
 */
public class LinearInterpolator extends SpaceInterpolator {

    public LinearInterpolator(int pointNumber) {
        super(pointNumber);
    }

    @Override
    public float getValueAt(int index) {
        if (index < 0) {
            return 0f;
        } else if (index < mPointNumber) {
            return index / (float)mPointNumber*0.92f+0.08f;
        } else {
            return 1f;
        }
    }
}

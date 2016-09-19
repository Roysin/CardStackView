package org.roysin.cardstackview.view.Interpolator;

/**
 * Created by Administrator on 2016/7/22.
 */
public class FocusInterpolator extends SpaceInterpolator {

    private int focus;

    public FocusInterpolator(int pointNumber) {
        super(pointNumber);
    }

    public void setFocus(int n) {
        if (n >= mPointNumber) {
            n = mPointNumber;
        }
        focus = n;
    }

    @Override
    public float getValueAt(int index) {
        if (index <= focus) {
            return 0f;
        } else if (index < mPointNumber) {
            return 0.9f + 0.1f / (mPointNumber - focus) * (index - focus);
        } else {
            return 1.0f;
        }
    }

    public int getFocus(){
        return focus;
    }
}

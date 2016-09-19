package org.roysin.cardstackview.view.Interpolator;

/**
 * Created by Administrator on 2016/7/22.
 */
public class Translator{
    SpaceInterpolator from;
    SpaceInterpolator to;
    FreeInterpolator current;
    int pointNumber;
    long duration;

    private Translator(){
        current = new FreeInterpolator(pointNumber){
            @Override
            public float getValueAt(int position) {
                return percent*(to.getValueAt(position) - from.getValueAt(position))
                        +from.getValueAt(position);
            }
        };
    }

    public FreeInterpolator getResultAt( float percent){
        current.setPercent(percent);
        return current;

    }
    public void setFrom(SpaceInterpolator from){
        this.from = from;
    }
    public void setTo(SpaceInterpolator to){
        this.to = to;
    }
    public static Translator of(long duration,SpaceInterpolator from,SpaceInterpolator to){
        Translator translator = new Translator();
        translator.from = from;
        translator.to = to;
        translator.duration = duration;
        translator.pointNumber = from.mPointNumber;
        return translator;
    }

    public void clear() {
        this.from = null;
        this.to = null;
        this.current = null;
    }
}

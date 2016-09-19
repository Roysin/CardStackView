package org.roysin.cardstackview.view.Interpolator;


/**
 * Created by Administrator on 2016/7/22.
 */
public class FreeInterpolator extends SpaceInterpolator {

    float percent;
    private SpaceInterpolator from;
    private SpaceInterpolator to;

    public FreeInterpolator(int points) {
        super(points);
    }

//    public void setFrom(SpaceInterpolator from){
//        this.from = from;
//    }
//    public void setTo(SpaceInterpolator to){
//        this.to = to;
//    }
    public void setPercent(float percent){
        if(percent>1){
            percent =1;
        }else if(percent <0){
            percent = 0;
        }
        this.percent = percent;
    }

    @Override
    public float getValueAt(int position) {
//        return percent*(to.getValueAt(position) - from.getValueAt(position))
//                +from.getValueAt(position);
        return 0;
    }
}

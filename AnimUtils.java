package com.szy.erpcashier.Util;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.PointF;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;

import com.szy.erpcashier.R;


public class AnimUtils {

    /**
     * 添加商品到购物车  抛物线动画
     * @param context
     * @param main
     * @param startView
     * @param endView
     */
    public static void showAddGoodsAnim(Context context, final ViewGroup main, View startView, View endView) {
        //起始数据点
        int[] startPosition = new int[2];
        //结束数据点
        int[] endPosition = new int[2];
        //控制点
        int[] recylerPosition = new int[2];

        startView.getLocationInWindow(startPosition);
        endView.getLocationInWindow(endPosition);

        PointF startF = new PointF();
        PointF endF = new PointF();
        PointF controlF = new PointF();

        startF.x = startPosition[0];
        startF.y = startPosition[1];
        endF.x = endPosition[0];
        endF.y = endPosition[1];
        controlF.x = endF.x;
        controlF.y = startF.y;

        final ImageView imageView = new ImageView(context);
        main.addView(imageView);
        imageView.setImageResource(R.mipmap.icon_increase);
        imageView.getLayoutParams().width = context.getResources().getDimensionPixelSize(R.dimen.dimen_20dp);
        imageView.getLayoutParams().height = context.getResources().getDimensionPixelSize(R.dimen.dimen_20dp);
        imageView.setVisibility(View.VISIBLE);
        imageView.setX(startF.x);
        imageView.setY(startF.y);

        ValueAnimator valueAnimator = ValueAnimator.ofObject(new BezierTypeEvaluator(controlF), startF, endF);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF pointF = (PointF) animation.getAnimatedValue();
                imageView.setX(pointF.x);
                imageView.setY(pointF.y);
            }
        });
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                imageView.setVisibility(View.GONE);
                main.removeView(imageView);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        ObjectAnimator objectAnimatorX = new ObjectAnimator().ofFloat(endView, "scaleX", 0.6f, 1.0f);
        ObjectAnimator objectAnimatorY = new ObjectAnimator().ofFloat(endView, "scaleY", 0.6f, 1.0f);
        objectAnimatorX.setInterpolator(new AccelerateInterpolator());
        objectAnimatorY.setInterpolator(new AccelerateInterpolator());
        AnimatorSet set = new AnimatorSet();
        set.play(objectAnimatorX).with(objectAnimatorY).after(valueAnimator);
        set.setDuration(800);
        set.start();
    }

    private static class BezierTypeEvaluator implements TypeEvaluator<PointF> {
        private PointF mControllPoint;

        public BezierTypeEvaluator(PointF mControllPoint) {
            this.mControllPoint = mControllPoint;
        }

        @Override
        public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
            PointF pointCur = new PointF();
            pointCur.x = (1 - fraction) * (1 - fraction) * startValue.x + 2 * fraction * (1 - fraction) * mControllPoint.x + fraction * fraction * endValue.x;
            pointCur.y = (1 - fraction) * (1 - fraction) * startValue.y + 2 * fraction * (1 - fraction) * mControllPoint.y + fraction * fraction * endValue.y;
            return pointCur;
        }
    }
}


package com.szy.yishopcustomer.Util;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by LML
 * Date: 2019/5/16
 */
public class ShadowUtils {

    /**
     * 获取 阴影背景
     * 控件要做相应的padding设置
     *
     * @param alpha      (0.0f - 1.0f)
     * @param startColor 阴影颜色
     * @param width      阴影宽度
     * @param radius     圆角半径
     * @return
     */
    public static Drawable getShadow(float alpha, int startColor, int width, int radius) {

        if (alpha < 0) alpha = 0;
        if (alpha > 1) alpha = 1;

        ArrayList<Drawable> drawables = new ArrayList<>();

        int count = width / 2;
        if (count < 1) count = 1;
        if (count > 10) count = 10;

        float colorWidth = (alpha * 10 / width) * 0.1f;
        for (float i = colorWidth, end = alpha; i <= end; i += alpha / count) {
            GradientDrawable gd1 = new GradientDrawable();
            gd1.setColor(getColorWithAlpha(i, startColor));
            gd1.setCornerRadius(radius);
            drawables.add(gd1);
        }

        GradientDrawable gd1 = new GradientDrawable();
        gd1.setColor(Color.WHITE);
        gd1.setCornerRadius(radius);
        drawables.add(gd1);

        Drawable[] drawables1 = new Drawable[drawables.size()];
        drawables.toArray(drawables1);

        int size = drawables.size() - 1;
        LayerDrawable layerDrawable = new LayerDrawable(drawables1);
        int padding = width / count;

//        int firstpadding = width % count;
//
//        for (int i = 1, len = size; i <= len; i++) {
//            int ipadding = firstpadding + padding * (i - 1);
//            layerDrawable.setLayerInset(i, ipadding, ipadding, ipadding, ipadding);
//        }
        int firstpadding = width % count + padding;
        for (int i = 1, len = size; i <= len; i++) {
            int ipadding = firstpadding + padding * (i - 1);
            if (i == 1) {
                ipadding = firstpadding;
            } else {
                ipadding += firstpadding;
            }
            layerDrawable.setLayerInset(i, ipadding, ipadding, ipadding, ipadding);
        }
        return layerDrawable;
    }

    /**
     * 对rgb色彩加入透明度
     *
     * @param alpha     透明度，取值范围 0.0f -- 1.0f.
     * @param baseColor
     * @return a color with alpha made from base color
     */
    public static int getColorWithAlpha(float alpha, int baseColor) {
        int a = Math.min(255, Math.max(0, (int) (alpha * 255))) << 24;
        int rgb = 0x00ffffff & baseColor;
        return a + rgb;
    }

}

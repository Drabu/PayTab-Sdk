package com.paytab_dk.paytabs_sdk.utils;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;

public class RoundCornerDrawable extends Drawable {
    private Paint paint = new Paint();

    public RoundCornerDrawable(int color) {
        this.paint.setAntiAlias(true);
        this.paint.setColor(color);
    }

    public void draw(Canvas canvas) {
        int height = this.getBounds().height();
        int width = this.getBounds().width();
        RectF rect = new RectF(0.0F, 0.0F, (float)width, (float)height);
        canvas.drawRoundRect(rect, 30.0F, 30.0F, this.paint);
    }

    public void setAlpha(int alpha) {
        this.paint.setAlpha(alpha);
    }

    public void setColorFilter(ColorFilter cf) {
        this.paint.setColorFilter(cf);
    }

    public int getOpacity() {
        return -3;
    }
}

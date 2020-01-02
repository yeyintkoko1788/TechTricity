package com.yeyintkoko.techtricity.custom_control;

import android.content.res.AssetManager;
import android.graphics.Paint;
import android.graphics.Typeface;

import su.levenetc.android.textsurface.Text;
import su.levenetc.android.textsurface.TextBuilder;
import su.levenetc.android.textsurface.TextSurface;
import su.levenetc.android.textsurface.animations.Delay;
import su.levenetc.android.textsurface.animations.Parallel;
import su.levenetc.android.textsurface.animations.Rotate3D;
import su.levenetc.android.textsurface.animations.Sequential;
import su.levenetc.android.textsurface.animations.ShapeReveal;
import su.levenetc.android.textsurface.animations.SideCut;
import su.levenetc.android.textsurface.animations.TransSurface;
import su.levenetc.android.textsurface.contants.Align;
import su.levenetc.android.textsurface.contants.Axis;
import su.levenetc.android.textsurface.contants.Pivot;
import su.levenetc.android.textsurface.contants.Side;

public class SplashText {
    public static void play(TextSurface textSurface, AssetManager assetManager, int color1, int color4) {
        final Typeface geomanistBook = Typeface.createFromAsset(assetManager, "splash_font.ttf");
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setTypeface(geomanistBook);

        Text textWe = TextBuilder
                .create("Techtricity")
                .setPaint(paint)
                .setSize(42)
                .setAlpha(0)
                .setColor(color1)
                .setPosition(Align.SURFACE_CENTER).build();

        Text textPresent = TextBuilder
                .create("Presented")
                .setPaint(paint)
                .setSize(42)
                .setAlpha(0)
                .setColor(color4)
                .setPosition(Align.BOTTOM_OF, textWe).build();

        textSurface.play(
                new Sequential(
                        ShapeReveal.create(textWe, 750, SideCut.show(Side.LEFT), false),
                        new Parallel(new Sequential(Delay.duration(300), ShapeReveal.create(textWe, 600, SideCut.show(Side.LEFT), false))),
                        Delay.duration(500),
                        new Parallel(TransSurface.toCenter(textPresent, 500), Rotate3D.showFromSide(textPresent, 750, Pivot.TOP))
                )
        );

    }
}

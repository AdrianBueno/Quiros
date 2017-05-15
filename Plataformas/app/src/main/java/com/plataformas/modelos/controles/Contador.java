package com.plataformas.modelos.controles;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.plataformas.modelos.Modelo;
import com.plataformas.modelos.Nivel;

/**
 * Created by UO232346 on 06/10/2016.
 */
public class Contador extends Modelo {

    private int puntos;

    public Contador(Context context, double x, double y, int mCanvasAncho, int mCanvasAltura) {
        super(context, x, y, mCanvasAncho, mCanvasAltura);
    }

    @Override
    public void dibujar(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setAntiAlias(true);
        paint.setTextSize(20);
        canvas.drawText(String.valueOf(puntos), (int) x , (int) y, paint);
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }
}

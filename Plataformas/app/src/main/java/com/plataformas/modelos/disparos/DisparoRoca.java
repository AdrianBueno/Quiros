package com.plataformas.modelos.disparos;

import android.content.Context;

import com.plataformas.R;
import com.plataformas.graficos.CargadorGraficos;
import com.plataformas.graficos.Sprite;

/**
 * Created by nardi on 08/12/2016.
 */

public class DisparoRoca extends Disparo {
    public DisparoRoca(Context context, double xInicial, double yInicial, double xFinal, double yFinal, int orientacion) {
        super(context, xInicial, yInicial, xFinal, yFinal, orientacion);
        velocidadBase = 12;
        cDerecha = 8;
        cIzquierda = 8;
        cArriba = 8;
        cAbajo = 8;
        ancho = 20;
        altura = 20;
        inicializar();
    }

    public void inicializar (){
        damage = 3;
        int orientacionX;
        int orientacionY;
        double distanciaX = xFinal - xInicial;
        double distanciaY = yFinal - yInicial;
        double factor = 0;
        if(distanciaX >= 0)
            orientacionX = 1;
        else
            orientacionX = -1;
        if(distanciaY >= 0)
            orientacionY = 1;
        else
            orientacionY = -1;
        //orientacionX = ((int) distanciaX/ (int) distanciaX);
        //orientacionY = ((int) distanciaY/ (int) distanciaY);
        distanciaX = Math.abs(distanciaX);
        distanciaY = Math.abs(distanciaY);
        if(distanciaX > distanciaY){
            factor = distanciaX/distanciaY;
            double vB = velocidadBase * velocidadBase;
            factor = 1 + (factor * factor);
            velocidadY = vB / factor;
            velocidadY = Math.sqrt(velocidadY);
            velocidadX = velocidadBase - velocidadY;
        }else{
            factor = distanciaY/distanciaX;
            double vB = velocidadBase * velocidadBase;
            factor = 1+ (factor * factor);
            velocidadX = vB / factor;
            velocidadX = Math.sqrt(velocidadX);
            velocidadY = velocidadBase - velocidadX;
        }
        velocidadX = velocidadX * orientacionX;
        velocidadY = velocidadY * orientacionY;

        Sprite roca = new Sprite(
                CargadorGraficos.cargarDrawable(context, R.drawable.roca),
                ancho, altura,
                1, 1, true);
        sprite = roca;


    }
}

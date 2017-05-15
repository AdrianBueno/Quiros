package com.plataformas.modelos.characters.enemigos;

import android.content.Context;

import com.plataformas.R;
import com.plataformas.graficos.CargadorGraficos;
import com.plataformas.graficos.Sprite;

/**
 * Created by nardi on 07/12/2016.
 */

public class EnemigoAzul extends Enemigo {


    public EnemigoAzul(Context context, double x, double y) {
        super(context, x, y);

        cDerecha = 15;
        cIzquierda = 15;
        cArriba = 20;
        cAbajo = 20;

        pRadio = 200;
        aRadio = 30;
        vida = 2;
        velocidadBase = 6;
        inicializar();
    }

    public  void inicializar (){

        Sprite paradoDerecha = new Sprite(
                CargadorGraficos.cargarDrawable(context, R.drawable.ene_est_rigth_blue),
                ancho, altura,
                1, 1, true);
        sprites.put(PARADO_DERECHA, paradoDerecha);

        Sprite paradoIzquierda = new Sprite(
                CargadorGraficos.cargarDrawable(context, R.drawable.ene_est_left_blue),
                ancho, altura,
                1, 1, true);
        sprites.put(PARADO_IZQUIERDA, paradoIzquierda);

        Sprite paradoArriba = new Sprite(
                CargadorGraficos.cargarDrawable(context, R.drawable.ene_est_up_blue),
                ancho, altura,
                1, 1, true);
        sprites.put(PARADO_ARRIBA, paradoArriba);

        Sprite paradoAbajo = new Sprite(
                CargadorGraficos.cargarDrawable(context, R.drawable.ene_est_down_blue),
                ancho, altura,
                1, 1, true);
        sprites.put(PARADO_ABAJO, paradoAbajo);

        Sprite caminandoDerecha = new Sprite(
                CargadorGraficos.cargarDrawable(context, R.drawable.ene_run_rigth_blue),
                ancho, altura,
                2, 4, true);
        sprites.put(CAMINANDO_DERECHA, caminandoDerecha);

        Sprite caminandoIzquierda = new Sprite(
                CargadorGraficos.cargarDrawable(context, R.drawable.ene_run_left_blue),
                ancho, altura,
                2, 4, true);
        sprites.put(CAMINANDO_IZQUIERDA, caminandoIzquierda);

        Sprite caminandoArriba = new Sprite(
                CargadorGraficos.cargarDrawable(context, R.drawable.ene_run_up_blue),
                ancho, altura,
                2, 4, true);
        sprites.put(CAMINANDO_ARRIBA, caminandoArriba);

        Sprite caminandoAbajo = new Sprite(
                CargadorGraficos.cargarDrawable(context, R.drawable.ene_run_down_blue),
                ancho, altura,
                2, 4, true);
        sprites.put(CAMINANDO_ABAJO, caminandoAbajo);

        Sprite atacandoDerecha = new Sprite(
                CargadorGraficos.cargarDrawable(context, R.drawable.ene_att_rigth_blue),
                ancho, altura,
                7, 7, false);
        sprites.put(ATACANDO_DERECHA, atacandoDerecha);

        Sprite atacandoIzquierda = new Sprite(
                CargadorGraficos.cargarDrawable(context, R.drawable.ene_att_left_blue),
                ancho, altura,
                7, 7, false);
        sprites.put(ATACANDO_IZQUIERDA, atacandoIzquierda);

        Sprite atacandoArriba = new Sprite(
                CargadorGraficos.cargarDrawable(context, R.drawable.ene_att_up_blue),
                ancho, altura,
                8, 8, false);
        sprites.put(ATACANDO_ARRIBA, atacandoArriba);

        Sprite atacandoAbajo = new Sprite(
                CargadorGraficos.cargarDrawable(context, R.drawable.ene_att_down_blue),
                ancho, altura,
                8, 8, false);
        sprites.put(ATACANDO_ABAJO, atacandoAbajo);

        sprite = sprites.get(PARADO_ABAJO);
    }
}

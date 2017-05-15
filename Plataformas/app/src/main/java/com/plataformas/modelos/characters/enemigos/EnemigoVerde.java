package com.plataformas.modelos.characters.enemigos;

import android.content.Context;

import com.plataformas.R;
import com.plataformas.graficos.CargadorGraficos;
import com.plataformas.graficos.Sprite;

/**
 * Created by nardi on 07/12/2016.
 */
/*
    Enemigo estándar

 */
public class EnemigoVerde extends Enemigo {
    public EnemigoVerde(Context context, double x, double y) {
        super(context, x, y);

        cDerecha = 15;
        cIzquierda = 15;
        cArriba = 20;
        cAbajo = 20;

        pRadio = 100;
        aRadio = 40;
        vida = 1;

        inicializar();
    }

    public  void inicializar (){

        Sprite paradoDerecha = new Sprite(
                CargadorGraficos.cargarDrawable(context, R.drawable.ene_est_rigth),
                ancho, altura,
                1, 1, true);
        sprites.put(PARADO_DERECHA, paradoDerecha);

        Sprite paradoIzquierda = new Sprite(
                CargadorGraficos.cargarDrawable(context, R.drawable.ene_est_left),
                ancho, altura,
                1, 1, true);
        sprites.put(PARADO_IZQUIERDA, paradoIzquierda);

        Sprite paradoArriba = new Sprite(
                CargadorGraficos.cargarDrawable(context, R.drawable.ene_est_up),
                ancho, altura,
                1, 1, true);
        sprites.put(PARADO_ARRIBA, paradoArriba);

        Sprite paradoAbajo = new Sprite(
                CargadorGraficos.cargarDrawable(context, R.drawable.ene_est_down),
                ancho, altura,
                1, 1, true);
        sprites.put(PARADO_ABAJO, paradoAbajo);

        Sprite caminandoDerecha = new Sprite(
                CargadorGraficos.cargarDrawable(context, R.drawable.ene_run_rigth),
                ancho, altura,
                2, 4, true);
        sprites.put(CAMINANDO_DERECHA, caminandoDerecha);

        Sprite caminandoIzquierda = new Sprite(
                CargadorGraficos.cargarDrawable(context, R.drawable.ene_run_left),
                ancho, altura,
                2, 4, true);
        sprites.put(CAMINANDO_IZQUIERDA, caminandoIzquierda);

        Sprite caminandoArriba = new Sprite(
                CargadorGraficos.cargarDrawable(context, R.drawable.ene_run_up),
                ancho, altura,
                2, 4, true);
        sprites.put(CAMINANDO_ARRIBA, caminandoArriba);

        Sprite caminandoAbajo = new Sprite(
                CargadorGraficos.cargarDrawable(context, R.drawable.ene_run_down),
                ancho, altura,
                2, 4, true);
        sprites.put(CAMINANDO_ABAJO, caminandoAbajo);

        Sprite atacandoDerecha = new Sprite(
                CargadorGraficos.cargarDrawable(context, R.drawable.ene_att_rigth),
                ancho, altura,
                4, 7, false);
        sprites.put(ATACANDO_DERECHA, atacandoDerecha);

        Sprite atacandoIzquierda = new Sprite(
                CargadorGraficos.cargarDrawable(context, R.drawable.ene_att_left),
                ancho, altura,
                4, 7, false);
        sprites.put(ATACANDO_IZQUIERDA, atacandoIzquierda);

        Sprite atacandoArriba = new Sprite(
                CargadorGraficos.cargarDrawable(context, R.drawable.ene_att_up),
                ancho, altura,
                4, 8, false);
        sprites.put(ATACANDO_ARRIBA, atacandoArriba);

        Sprite atacandoAbajo = new Sprite(
                CargadorGraficos.cargarDrawable(context, R.drawable.ene_att_down),
                ancho, altura,
                4, 8, false);
        sprites.put(ATACANDO_ABAJO, atacandoAbajo);

        sprite = sprites.get(PARADO_ABAJO);
    }






}

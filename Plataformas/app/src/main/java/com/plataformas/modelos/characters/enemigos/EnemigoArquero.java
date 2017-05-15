package com.plataformas.modelos.characters.enemigos;

import android.content.Context;

import com.plataformas.R;
import com.plataformas.graficos.CargadorGraficos;
import com.plataformas.graficos.Sprite;
import com.plataformas.modelos.characters.jugadores.JugadorEstandar;
import com.plataformas.modelos.disparos.DisparoFlecha;

/**
 * Created by nardi on 07/12/2016.
 */

public class EnemigoArquero extends Enemigo {
    protected double cadencia = 800;
    private double acumulador = 0;
    public EnemigoArquero(Context context, double x, double y) {
        super(context, x, y);
        orientacion = ABAJO;
        cDerecha = 15;
        cIzquierda = 15;
        cArriba = 20;
        cAbajo = 20;

        pRadio = 150;
        aRadio = 0;
        vida = 1;
        inicializar();

    }

    public  void inicializar (){

        Sprite paradoAbajo = new Sprite(
                CargadorGraficos.cargarDrawable(context, R.drawable.arq_est),
                ancho, altura,
                1, 1, true);
        sprites.put(PARADO_ABAJO, paradoAbajo);

        Sprite caminandoAbajo = new Sprite(
                CargadorGraficos.cargarDrawable(context, R.drawable.arq_run),
                ancho, altura,
                2, 4, true);
        sprites.put(CAMINANDO_ABAJO, caminandoAbajo);

        Sprite atacandoAbajo = new Sprite(
                CargadorGraficos.cargarDrawable(context, R.drawable.arq_att),
                ancho, altura,
                7, 7, false);
        sprites.put(ATACANDO_ABAJO, atacandoAbajo);

        sprite = sprites.get(PARADO_ABAJO);
    }

    @Override
    public void actualizar(long tiempo) {
        boolean finSprite = sprite.actualizar(tiempo);
        acumulador += tiempo;
        if((atacando && finSprite) || velocidadX != 0 || velocidadY != 0){
            atacando = false;
        }
        if(velocidadX != 0 || velocidadY != 0){
            sprite = sprites.get(CAMINANDO_ABAJO);
        }else if(atacando)
            sprite = sprites.get(ATACANDO_ABAJO);
        else
            sprite = sprites.get(PARADO_ABAJO);
    }

    @Override
    public Object atacarJugador(JugadorEstandar jugador) {
        if(detecta(jugador))
            jugadorDetectado = true;
        else
            jugadorDetectado = false;
        if(jugadorDetectado && !atacando){
            if(jugador.puedeAtacarFisicamente(this)){
                atacando = false;
                double distanciaX = jugador.getX() - this.x;
                double distanciaY = jugador.getY() - this.y;
                if(distanciaX > jugador.getAncho()/2)
                    velocidadX = -3;
                else if(distanciaX < - jugador.getAncho()/2)
                    velocidadX = 3;
                else
                    velocidadX = 0;
                if(distanciaY > jugador.getAltura()/2)
                    velocidadY = -3;
                else if(distanciaY < - jugador.getAltura()/2)
                    velocidadY = 3;
                else
                    velocidadY = 0;
            }else{
                if(acumulador > cadencia) {
                    acumulador = 0;
                    atacando = true;
                    sprites.get(ATACANDO_ABAJO).setFrameActual(0);
                    return new DisparoFlecha(context, x, y, jugador.getX(), jugador.getY(), 1);
                }else
                    atacando = false;
            }
        }else{
            velocidadX = 0;
            velocidadY = 0;
        }
        return null;

    }


}

package com.plataformas.modelos.characters.enemigos;

import android.content.Context;

import com.plataformas.R;
import com.plataformas.graficos.CargadorGraficos;
import com.plataformas.graficos.Sprite;
import com.plataformas.modelos.characters.jugadores.JugadorEstandar;
import com.plataformas.modelos.disparos.DisparoFlecha;
import com.plataformas.modelos.disparos.DisparoRoca;

/**
 * Created by nardi on 07/12/2016.
 */

public class EnemigoCatapulta extends Enemigo {
    protected double cadencia = 2000;
    private double acumulador = 0;
    public EnemigoCatapulta(Context context, double x, double y) {
        super(context, x, y);
        vida = 12;
        velocidadBase = 0;
        aRadio = 0;
        pRadio = 500;
        ancho = 80;
        altura = 50;
        inicializar();
    }

    public void inicializar(){
        Sprite paradoAbajo = new Sprite(
                CargadorGraficos.cargarDrawable(context, R.drawable.cat_est),
                ancho, altura,
                1, 1, true);
        sprites.put(PARADO_ABAJO, paradoAbajo);

        Sprite caminandoAbajo = new Sprite(
                CargadorGraficos.cargarDrawable(context, R.drawable.cat_est),
                ancho, altura,
                1, 1, true);
        sprites.put(CAMINANDO_ABAJO, caminandoAbajo);

        Sprite atacandoAbajo = new Sprite(
                CargadorGraficos.cargarDrawable(context, R.drawable.cat_att),
                ancho, altura,
                4, 4, false);
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
            if(jugador.detecta(this)){
                atacando = false;
            }else{
                if(acumulador > cadencia) {
                    acumulador = 0;
                    atacando = true;
                    sprites.get(ATACANDO_ABAJO).setFrameActual(0);
                    return new DisparoRoca(context, x, y, jugador.getX(), jugador.getY(),1);
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

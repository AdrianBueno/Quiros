package com.plataformas.modelos.collec;

import android.content.Context;
import android.graphics.Canvas;

import com.plataformas.R;
import com.plataformas.graficos.CargadorGraficos;
import com.plataformas.graficos.Sprite;
import com.plataformas.modelos.Modelo;
import com.plataformas.modelos.Nivel;
import com.plataformas.modelos.characters.jugadores.JugadorEstandar;

/**
 * Created by nardi on 08/12/2016.
 */
//Aumentan la vida
public class Gema extends Modelo implements Coleccionable {
    private Sprite sprite;

    public Gema(Context context, double x, double y) {
        super(context, x, y, 32, 32);
        inicializar();
    }

    private void inicializar(){
        cDerecha = 15;
        cIzquierda = 15;
        cArriba = 15;
        cAbajo = 15;
        sprite = new Sprite(CargadorGraficos.cargarDrawable(context, R.drawable.gem),ancho, altura,4, 8, true);
    }

    public void actualizar(long tiempo){
        sprite.actualizar(tiempo);
    }

    public void dibujar(Canvas canvas){
        sprite.dibujarSprite(canvas, (int) x - Nivel.scrollEjeX  , (int) y - Nivel.scrollEjeY);
    }

    @Override
    public void improveCharacter(JugadorEstandar jugador) {
        jugador.setVida(jugador.getVida()+2);
    }
}

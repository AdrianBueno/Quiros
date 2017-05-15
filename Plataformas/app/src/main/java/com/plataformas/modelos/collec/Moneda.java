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
//La moneda le da velocidad
public class Moneda extends Modelo implements Coleccionable {
    private Sprite sprite;
    public Moneda(Context context, double x, double y) {
        super(context, x, y, 32, 32);
        inicializar();
    }

    private void inicializar(){
        cDerecha = 20;
        cIzquierda = 20;
        cArriba = 20;
        cAbajo = 20;
        sprite = new Sprite(CargadorGraficos.cargarDrawable(context, R.drawable.moneda),ancho, altura,1, 1, true);
    }
    public void actualizar(long tiempo){
        sprite.actualizar(tiempo);
    }

    public void dibujar(Canvas canvas){
        sprite.dibujarSprite(canvas, (int) x - Nivel.scrollEjeX  , (int) y - Nivel.scrollEjeY);
    }


    @Override
    public void improveCharacter(JugadorEstandar jugador) {
            jugador.setVelocidadBase(jugador.getVelocidadBase()+1);
            jugador.setaRadio(jugador.getaRadio() + jugador.getaRadio()/2);
    }
}

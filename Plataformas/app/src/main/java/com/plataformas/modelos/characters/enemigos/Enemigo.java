package com.plataformas.modelos.characters.enemigos;

import android.content.Context;
import android.graphics.Canvas;

import com.plataformas.graficos.Sprite;
import com.plataformas.modelos.Modelo;
import com.plataformas.modelos.Nivel;
import com.plataformas.modelos.characters.Movible;
import com.plataformas.modelos.characters.jugadores.JugadorEstandar;

import java.util.HashMap;

/**
 * Created by uo232346 on 13/10/2016.
 */
public class Enemigo extends Modelo implements Movible {

    public static final String PARADO_DERECHA = "Parado_derecha";
    public static final String PARADO_IZQUIERDA = "Parado_izquierda";
    public static final String PARADO_ARRIBA = "Parado_arriba";
    public static final String PARADO_ABAJO = "Parado_abajo";
    public static final String CAMINANDO_DERECHA = "Caminando_derecha";
    public static final String CAMINANDO_IZQUIERDA = "Caminando_izquierda";
    public static final String CAMINANDO_ARRIBA = "Caminando_arriba";
    public static final String CAMINANDO_ABAJO = "Caminando_abajo";

    public static final String ATACANDO_DERECHA = "Atacando_derecha";
    public static final String ATACANDO_IZQUIERDA = "Atacando_izquierda";
    public static final String ATACANDO_ARRIBA = "Atacando_arriba";
    public static final String ATACANDO_ABAJO = "Atacando_abajo";

    public static final String MUERTE = "muerte";
    public static final int ACTIVO = 1;
    public static final int INACTIVO = 0;
    public static final int ELIMINAR = -1;

    //Orientación
    public static final int DERECHA = 1;
    public static final int IZQUIERDA = -1;
    public static final int ARRIBA = 2;
    public static final int ABAJO = -2;

    public int estado = ACTIVO;


    protected Sprite sprite;
    protected HashMap<String,Sprite> sprites = new HashMap<String,Sprite> ();

    protected  double velocidadBase;
    protected double velocidadX;
    protected double velocidadY; // actual
    private int ca;
    protected int orientacion;
    protected boolean jugadorDetectado;
    protected boolean atacando;

    protected int vida;

    public Enemigo(Context context, double x, double y) {
        super(context, 0, 0, 40, 40);
        this.x = x;
        this.y = y - altura/2;
        this.xInicial = x;
        this.yInicial = y - altura/2;
        estado = ACTIVO;
        vida = 1;
        velocidadBase = 3;
        ca = 50;
    }



    public  void actualizar (long tiempo){

        boolean finSprite = sprite.actualizar(tiempo);

        if((atacando && finSprite) || velocidadX != 0 ||velocidadY != 0){
            atacando = false;
        }
        if (velocidadX > 0){
            sprite = sprites.get(CAMINANDO_DERECHA);
            orientacion = DERECHA;
        }
        if (velocidadX < 0 ){
            sprite = sprites.get(CAMINANDO_IZQUIERDA);
            orientacion = IZQUIERDA;
        }
        if(velocidadY > 0){
            sprite = sprites.get(CAMINANDO_ABAJO);
            orientacion = ABAJO;
        }
        if(velocidadY < 0){
            sprite = sprites.get(CAMINANDO_ARRIBA);
            orientacion = ARRIBA;
        }
        if (velocidadX == 0 && velocidadY == 0 ){
            if (orientacion == DERECHA){
                if(atacando)
                    sprite = sprites.get(ATACANDO_DERECHA);
                else
                    sprite = sprites.get(PARADO_DERECHA);
            } else if (orientacion == IZQUIERDA) {
                if(atacando)
                    sprite = sprites.get(ATACANDO_IZQUIERDA);
                else
                    sprite = sprites.get(PARADO_IZQUIERDA);
            } else if (orientacion == ARRIBA){
                if(atacando)
                    sprite = sprites.get(ATACANDO_ARRIBA);
                else
                    sprite = sprites.get(PARADO_ARRIBA);
            } else if (orientacion == ABAJO){
                if(atacando)
                    sprite = sprites.get(ATACANDO_ABAJO);
                else
                    sprite = sprites.get(PARADO_ABAJO);
            }
        }

    }

    public Object atacarJugador(JugadorEstandar jugador){
        if(detecta(jugador))
            jugadorDetectado = true;
        else {
            jugadorDetectado = false;
            atacando = false;
        }

        if(jugadorDetectado && !puedeAtacarFisicamente(jugador)){
            double distanciaX = jugador.getX() - this.x;
            double distanciaY = jugador.getY() - this.y;
            if(distanciaX > jugador.getAncho()/2)
                velocidadX = velocidadBase;
            else if(distanciaX < - jugador.getAncho()/2)
                velocidadX = -velocidadBase;
            else
                velocidadX = 0;
            if(distanciaY > jugador.getAltura()/2)
                velocidadY = velocidadBase;
            else if(distanciaY < - jugador.getAltura()/2)
                velocidadY = -velocidadBase;
            else
                velocidadY = 0;

        }else{
            velocidadX = 0;
            velocidadY = 0;
        }
        if(jugadorDetectado && puedeAtacarFisicamente(jugador) && !atacando){
            atacando = true;
            sprites.get(ATACANDO_DERECHA).setFrameActual(0);
            sprites.get(ATACANDO_IZQUIERDA).setFrameActual(0);
            sprites.get(ATACANDO_ABAJO).setFrameActual(0);
            sprites.get(ATACANDO_ARRIBA).setFrameActual(0);
            velocidadX = 0;
            velocidadY = 0;
        }




        return  null;
    }

    public void dibujar(Canvas canvas){
        dibujarDebug(canvas);
        sprite.dibujarSprite(canvas, (int) x - Nivel.scrollEjeX, (int) y - Nivel.scrollEjeY);
    }

    public  boolean destruir (){
        if(vida == 0){
            estado = ELIMINAR;
            return true;
        }else
            vida--;
        return false;
    }


    public boolean isAtacando() {
        return atacando;
    }

    public void setAtacando(boolean atacando) {
        this.atacando = atacando;
    }

    public int getVida() {
        return vida;
    }

    public void setVida(int vida) {
        this.vida = vida;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    @Override
    public double getVelX() {
        return velocidadX;
    }

    @Override
    public double getVelY() {
        return velocidadY;
    }

    @Override
    public void setVelX(double velX) {
        this.velocidadX = velX;
    }

    @Override
    public void setVelY(double velY) {
        this.velocidadY = velY;
    }

    @Override
    public boolean isPlayer(){
        return false;
    }

    @Override
    public boolean isShoot(){return false;}

    public int getCa() {
        return ca;
    }

    public void setCa(int ca) {
        this.ca = ca;
    }
}

package com.plataformas.modelos.disparos;

import android.content.Context;
import android.graphics.Canvas;

import com.plataformas.graficos.Sprite;
import com.plataformas.modelos.Modelo;
import com.plataformas.modelos.Nivel;
import com.plataformas.modelos.characters.Movible;

/**
 * Created by nardi on 07/12/2016.
 */

public class Disparo extends Modelo implements Movible {


    public static final int ACTIVO = 1;
    public static final int INACTIVO = 0;
    public static final int ELIMINAR = -1;

    public int estado = ACTIVO;
    public boolean contacto = false;

    protected Sprite sprite;
    protected double velocidadBase;
    protected double velocidadX;
    protected double velocidadY;

    protected double xInicial;
    protected double yInicial;
    protected double xFinal;
    protected double yFinal;

    protected int damage;

    public Disparo(Context context, double xInicial, double yInicial, double xFinal, double yFinal, int orientacion) {
        super(context, xInicial, yInicial, 10, 10);
        this.xInicial = xInicial;
        this.yInicial = yInicial;
        this.xFinal = xFinal;
        this.yFinal = yFinal;
        velocidadBase = 5;
        cDerecha = 6;
        cIzquierda = 6;
        cArriba = 6;
        cAbajo = 6;

    }



    public void actualizar (long tiempo) {

        sprite.actualizar(tiempo);
    }

    public void dibujar(Canvas canvas){
        sprite.dibujarSprite(canvas, (int) x - Nivel.scrollEjeX, (int) y - Nivel.scrollEjeY);
        dibujarDebug(canvas);
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
        velocidadX = velX;
    }

    @Override
    public void setVelY(double velY) {
        velocidadY = velY;
    }

    @Override
    public boolean isPlayer() {
        return false;
    }

    @Override
    public boolean isShoot(){return true;}

    @Override
    public  boolean destruir (){
        estado = ELIMINAR;
        return true;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }
}

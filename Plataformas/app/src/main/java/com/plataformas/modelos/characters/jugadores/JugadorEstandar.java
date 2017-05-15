package com.plataformas.modelos.characters.jugadores;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import com.plataformas.R;
import com.plataformas.graficos.CargadorGraficos;
import com.plataformas.graficos.Sprite;
import com.plataformas.modelos.Modelo;
import com.plataformas.modelos.Nivel;
import com.plataformas.modelos.characters.Movible;

import java.util.HashMap;

/**
 * Created by nardi on 06/12/2016.
 */

public class JugadorEstandar extends Modelo implements Movible {

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

    public static final int DERECHA = 1;
    public static final int IZQUIERDA = -1;
    public static final int ARRIBA = 2;
    public static final int ABAJO = -2;

    private double velocidadX;
    private double velocidadY;
    private double velocidadBase;
    private int orientacion;
    private int ca; //Clase de armadura

    private boolean atacando;
    private boolean disparando;

    private int vida;
    private double msInmunidad = 0;
    private boolean golpeado = false;

    protected Sprite sprite;
    protected HashMap<String,Sprite> sprites = new HashMap<String,Sprite>();




    public JugadorEstandar(Context context, double x, double y) {
        super(context, x, y, 40, 40);

        // guardamos la posición inicial porque más tarde vamos a reiniciarlo
        this.xInicial = x;
        this.yInicial = y - altura/2;
        ca = 200;
        inicializar();
    }

    public void inicializar (){
        x = this.xInicial;
        y = this.yInicial;
        velocidadBase = 6;
        pRadio = 100;
        aRadio = 40;
        vida = 4;
        golpeado = false;
        msInmunidad = 0;

        Sprite paradoDerecha = new Sprite(
                CargadorGraficos.cargarDrawable(context, R.drawable.cab_est_rigth),
                ancho, altura,
                1, 1, true);
        sprites.put(PARADO_DERECHA, paradoDerecha);

        Sprite paradoIzquierda = new Sprite(
                CargadorGraficos.cargarDrawable(context, R.drawable.cab_est_left),
                ancho, altura,
                1, 1, true);
        sprites.put(PARADO_IZQUIERDA, paradoIzquierda);

        Sprite paradoArriba = new Sprite(
                CargadorGraficos.cargarDrawable(context, R.drawable.cab_est_up),
                ancho, altura,
                1, 1, true);
        sprites.put(PARADO_ARRIBA, paradoArriba);

        Sprite paradoAbajo = new Sprite(
                CargadorGraficos.cargarDrawable(context, R.drawable.cab_est_down),
                ancho, altura,
                1, 1, true);
        sprites.put(PARADO_ABAJO, paradoAbajo);

        Sprite caminandoDerecha = new Sprite(
                CargadorGraficos.cargarDrawable(context, R.drawable.cab_run_rigth),
                ancho, altura,
                2, 4, true);
        sprites.put(CAMINANDO_DERECHA, caminandoDerecha);

        Sprite caminandoIzquierda = new Sprite(
                CargadorGraficos.cargarDrawable(context, R.drawable.cab_run_left),
                ancho, altura,
                2, 4, true);
        sprites.put(CAMINANDO_IZQUIERDA, caminandoIzquierda);

        Sprite caminandoArriba = new Sprite(
                CargadorGraficos.cargarDrawable(context, R.drawable.cab_run_up),
                ancho, altura,
                2, 4, true);
        sprites.put(CAMINANDO_ARRIBA, caminandoArriba);

        Sprite caminandoAbajo = new Sprite(
                CargadorGraficos.cargarDrawable(context, R.drawable.cab_run_down),
                ancho, altura,
                2, 4, true);
        sprites.put(CAMINANDO_ABAJO, caminandoAbajo);

        Sprite atacandoDerecha = new Sprite(
                CargadorGraficos.cargarDrawable(context, R.drawable.cab_att_rigth),
                ancho, altura,
                7, 7, false);
        sprites.put(ATACANDO_DERECHA, atacandoDerecha);

        Sprite atacandoIzquierda = new Sprite(
                CargadorGraficos.cargarDrawable(context, R.drawable.cab_att_left),
                ancho, altura,
                7, 7, false);
        sprites.put(ATACANDO_IZQUIERDA, atacandoIzquierda);

        Sprite atacandoArriba = new Sprite(
                CargadorGraficos.cargarDrawable(context, R.drawable.cab_att_up),
                ancho, altura,
                8, 8, false);
        sprites.put(ATACANDO_ARRIBA, atacandoArriba);

        Sprite atacandoAbajo = new Sprite(
                CargadorGraficos.cargarDrawable(context, R.drawable.cab_att_down),
                ancho, altura,
                8, 8, false);
        sprites.put(ATACANDO_ABAJO, atacandoAbajo);


        sprite = paradoAbajo;
    }

    public void actualizar (long tiempo) {
        if (msInmunidad > 0)
            msInmunidad -= tiempo;
        else
            golpeado = false;

        boolean finSprite = sprite.actualizar(tiempo);

        if((atacando && finSprite) || velocidadX != 0 ||velocidadY != 0){
            atacando = false;
        }

        if (velocidadX > 0){
            sprite = sprites.get(CAMINANDO_DERECHA);
            orientacion = DERECHA;
        } else if (velocidadX < 0 ){
            sprite = sprites.get(CAMINANDO_IZQUIERDA);
            orientacion = IZQUIERDA;
        } else if(velocidadY > 0){
            sprite = sprites.get(CAMINANDO_ABAJO);
            orientacion = ABAJO;
        } else if(velocidadY < 0){
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

    public int golpeado(int damage){
        if (msInmunidad <= 0) {
            if (vida > 0) {
                vida -= damage;
                msInmunidad = 3000;
                golpeado = true;
            }
        }
        return vida;
    }

    public void dibujar(Canvas canvas){
        sprite.dibujarSprite(canvas, (int) x - Nivel.scrollEjeX , (int) y - Nivel.scrollEjeY,false);
        if(msInmunidad > 0){
            Paint paint = new Paint();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(Color.YELLOW);
            paint.setStrokeWidth(1);
            int scrollX = scrollable ? Nivel.scrollEjeX : 0;
            int scrollY = scrollable ? Nivel.scrollEjeY : 0;
            canvas.drawCircle((float)x-scrollX,(float)y-scrollY, 30, paint);
        }
        dibujarDebug(canvas);
    }

    public void procesarOrdenes (float[] orientaciones, boolean atacar) {
        if(atacar){
            atacando = true;
            sprites.get(ATACANDO_DERECHA).setFrameActual(0);
            sprites.get(ATACANDO_IZQUIERDA).setFrameActual(0);
            sprites.get(ATACANDO_ABAJO).setFrameActual(0);
            sprites.get(ATACANDO_ARRIBA).setFrameActual(0);

        }
        if(orientaciones[0] == 0){
            velocidadX = 0;
        } else if(orientaciones[0] > 0){ //x > 0 -> DERECHA
            velocidadX = velocidadBase;
        } else if(orientaciones[0] < 0){ //x < 0 -> IZQUIERDA
            velocidadX = -velocidadBase;
        }
        if(orientaciones[1] == 0){
            velocidadY = 0;
        } else if(orientaciones[1] > 0){ //y > 0 -> ABAJO
            velocidadY = velocidadBase;
        } else if(orientaciones[1] < 0){ //y < 0 -> ARRIBA
            velocidadY = -velocidadBase;
        }
    }

    public void restablecerPosicionInicial(){
        inicializar();
    }

    public void setxInicial(double xInicial) {
        this.xInicial = xInicial;
    }

    public void setyInicial(double yInicial) {
        this.yInicial = yInicial;
    }


    public int getVida() {
        return vida;
    }

    public void setVida(int vida) {
        this.vida = vida;
    }

    public int getOrientacion() {
        return orientacion;
    }

    public void setOrientacion(int orientacion){
        this.orientacion = orientacion;
    }


    public boolean isAtacando() {
        return atacando;
    }

    public void setAtacando(boolean atacando) {
        this.atacando = atacando;
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
        return true;
    }

    @Override
    public boolean isShoot(){return false;}

    @Override
    public  boolean destruir (){
        return false;
    }

    public double getMsInmunidad() {
        return msInmunidad;
    }

    public void setMsInmunidad(double msInmunidad) {
        this.msInmunidad = msInmunidad;
    }

    public double getVelocidadBase() {
        return velocidadBase;
    }

    public void setVelocidadBase(double velocidadBase) {
        this.velocidadBase = velocidadBase;
    }

    public int getCa() {
        return ca;
    }

    public void setCa(int ca) {
        this.ca = ca;
    }
}

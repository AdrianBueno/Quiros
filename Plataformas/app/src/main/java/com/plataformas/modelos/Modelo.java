package com.plataformas.modelos;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;

import com.plataformas.GameView;
import com.plataformas.graficos.Sprite;

import java.util.HashMap;

public class Modelo implements IModelo {

    protected Context context;
    //Posiciones o absolutas en pantalla
    protected double x;
    protected double y;
    //Posiciones originales en pantalla
    protected double xInicial;
    protected double yInicial;
    //Medidas del sprite
    protected int altura;
    protected int ancho;
    //Sprites
    protected Drawable imagen;
    protected boolean scrollable = true;
    //Control de colisiones
    public int cDerecha;
    public int cIzquierda;
    public int cArriba;
    public int cAbajo;
    //Percepciones
    public int pRadio;
    //Ataca
    public int aRadio;

    public Modelo(Context context, double x, double y, int altura, int ancho){;
        this.context = context;
        this.x = x;
        this.y = y;
        this.altura = altura;
        this.ancho = ancho;
        cDerecha = ancho/2;
        cIzquierda = ancho/2;
        cArriba = altura/2;
        cAbajo = altura/2;
    }

    public void dibujar(Canvas canvas){
        int yArriva = (int)  y - altura / 2;
        int xIzquierda = (int) x - ancho / 2;

        imagen.setBounds(xIzquierda, yArriva, xIzquierda
                + ancho, yArriva + altura);
        imagen.draw(canvas);
        //dibujarDebug(canvas);
    }

    public void dibujarDebug(Canvas canvas){
        if (GameView.DEBUG) {
            Paint paint = new Paint();

            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(Color.RED);
            paint.setStrokeWidth(1);

            int scrollX = scrollable ? Nivel.scrollEjeX : 0;
            int scrollY = scrollable ? Nivel.scrollEjeY : 0;

            canvas.drawRect(
                    (float) x - cIzquierda - scrollX,
                    (float) y - cArriba - scrollY,
                    (float) x + cDerecha - scrollX,
                    (float) y + cAbajo - scrollY, paint);

            paint.setColor(Color.BLUE);
            canvas.drawCircle((float)x-scrollX,(float)y-scrollY, pRadio, paint);
            paint.setColor(Color.BLACK);
            canvas.drawCircle((float)x-scrollX,(float)y-scrollY, aRadio, paint);
        }
    }

    public void actualizar (long tiempo){}

    public boolean colisiona (IModelo modelo){
        boolean colisiona = false;
        if (modelo.getX() - modelo.getcIzquierda() / 2 <= (x + cDerecha)
                && (modelo.getX() + modelo.getcDerecha() / 2) >= (x - cIzquierda)
                && (y + cAbajo) >= (modelo.getY() - modelo.getcArriba())
                && (y - cArriba) < (modelo.getY() + modelo.getcAbajo())) {

            colisiona = true;
        }
        return colisiona;
    }

    public boolean detecta (IModelo modelo){
        boolean colisiona = false;
        if(x - pRadio <= modelo.getX()
                && x + pRadio >= modelo.getX()
                && y - pRadio <= modelo.getY()
                && y + pRadio >= modelo.getY()){
            colisiona = true;
        }
        return colisiona;
    }

    public boolean puedeAtacarFisicamente(IModelo modelo){
        boolean colisiona = false;
        if(x - aRadio <= modelo.getX() + modelo.getcDerecha()
                && x + aRadio >= modelo.getX() - modelo.getcIzquierda()
                && y - aRadio <= modelo.getY() + modelo.getcArriba()
                && y + aRadio >= modelo.getY() -modelo.getcAbajo()){
            colisiona = true;
        }
        return colisiona;
    }

    @Override
    public double getX() {
        return x;
    }

    @Override
    public void setX(double x) {
        this.x = x;
    }

    @Override
    public double getY() {
        return y;
    }

    @Override
    public void setY(double y) {
        this.y = y;
    }

    public double getxInicial() {
        return xInicial;
    }

    public void setxInicial(double xInicial) {
        this.xInicial = xInicial;
    }

    public double getyInicial() {
        return yInicial;
    }

    public void setyInicial(double yInicial) {
        this.yInicial = yInicial;
    }

    @Override
    public int getAltura() {
        return altura;
    }

    @Override
    public void setAltura(int altura) {
        this.altura = altura;
    }

    @Override
    public int getAncho() {
        return ancho;
    }

    @Override
    public void setAncho(int ancho) {
        this.ancho = ancho;
    }

    @Override
    public Drawable getImagen() {
        return imagen;
    }

    public void setImagen(Drawable imagen) {
        this.imagen = imagen;
    }

    @Override
    public boolean isScrollable() {
        return scrollable;
    }

    public void setScrollable(boolean scrollable) {
        this.scrollable = scrollable;
    }

    @Override
    public int getcDerecha() {
        return cDerecha;
    }

    @Override
    public void setcDerecha(int cDerecha) {
        this.cDerecha = cDerecha;
    }

    @Override
    public int getcIzquierda() {
        return cIzquierda;
    }

    @Override
    public void setcIzquierda(int cIzquierda) {
        this.cIzquierda = cIzquierda;
    }

    @Override
    public int getcArriba() {
        return cArriba;
    }

    @Override
    public void setcArriba(int cArriba) {
        this.cArriba = cArriba;
    }

    @Override
    public int getcAbajo() {
        return cAbajo;
    }

    @Override
    public void setcAbajo(int cAbajo) {
        this.cAbajo = cAbajo;
    }

    @Override
    public int getpRadio() {
        return pRadio;
    }

    @Override
    public void setpRadio(int pRadio) {
        this.pRadio = pRadio;
    }

    @Override
    public int getaRadio() {
        return aRadio;
    }

    @Override
    public void setaRadio(int aRadio) {
        this.aRadio = aRadio;
    }
}



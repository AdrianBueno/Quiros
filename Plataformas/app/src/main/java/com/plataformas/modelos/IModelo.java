package com.plataformas.modelos;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

/**
 * Created by nardi on 09/12/2016.
 */

public interface IModelo{
    public boolean colisiona (IModelo modelo);
    public boolean detecta (IModelo modelo);
    public boolean puedeAtacarFisicamente(IModelo modelo);

    //Posiciones iniciales
    public double getxInicial();
    public void setxInicial(double x);
    public double getyInicial();
    public void setyInicial(double y);
    //Posiciones actuales
    public double getX();
    public void setX(double x);
    public double getY();
    public void setY(double y);
    //Tamaños de imagen
    public int getAltura();
    public void setAltura(int altura);
    public int getAncho();
    public void setAncho(int ancho);
    //Imagen
    public Drawable getImagen();
    public boolean isScrollable();
    public void dibujar(Canvas canvas);
    //Colisiones
    public int getcDerecha();
    public void setcDerecha(int cDerecha);
    public int getcIzquierda();
    public void setcIzquierda(int cIzquierda);
    public int getcArriba();
    public void setcArriba(int cArriba);
    public int getcAbajo();
    public void setcAbajo(int cAbajo);
    public int getpRadio();
    public void setpRadio(int pRadio);
    //Percepción y ataque
    public int getaRadio();
    public void setaRadio(int aRadio);

}

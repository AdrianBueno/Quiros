package com.plataformas.modelos;

import android.graphics.drawable.Drawable;

/**
 * Created by UO232346 on 06/10/2016.
 */
public class Tile {
    public static int ancho = 40;
    public static int altura = 32;
    public static final int PASABLE = 0;
    public static final int SOLIDO = 1;


    public int tipoDeColision;

    public Drawable[] imagenes;
    public Tile(Drawable[] imagenes, int tipoDeColision) {
        this.imagenes = imagenes ;
        this.tipoDeColision = tipoDeColision;
    }

}

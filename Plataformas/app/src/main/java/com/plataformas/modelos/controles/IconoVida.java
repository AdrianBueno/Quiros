package com.plataformas.modelos.controles;

import android.content.Context;

import com.plataformas.R;
import com.plataformas.graficos.CargadorGraficos;
import com.plataformas.modelos.Modelo;

/**
 * Created by uo232346 on 13/10/2016.
 */

public class IconoVida extends Modelo {

    public IconoVida(Context context, double x, double y) {
        super(context, x, y, 40,40);
        imagen = CargadorGraficos.cargarDrawable(context, R.drawable.lapida);
    }
}

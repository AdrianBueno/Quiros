package com.plataformas.modelos.controles;

import android.content.Context;

import com.plataformas.GameView;
import com.plataformas.R;
import com.plataformas.graficos.CargadorGraficos;
import com.plataformas.modelos.Modelo;

/**
 * Created by UO232346 on 06/10/2016.
 */
public class BotonAtacar extends Modelo {

    public BotonAtacar(Context context) {
        super(context, GameView.pantallaAncho*0.85 , GameView.pantallaAlto*0.8,
                GameView.pantallaAlto, GameView.pantallaAncho);

        altura = 70;
        ancho = 70;
        imagen = CargadorGraficos.cargarDrawable(context, R.drawable.buttonfire);
    }

    public boolean estaPulsado(float clickX, float clickY) {
        boolean estaPulsado = false;

        if (clickX <= (x + ancho / 2) && clickX >= (x - ancho / 2)
                && clickY <= (y + altura / 2) && clickY >= (y - altura / 2)) {
            estaPulsado = true;
        }
        return estaPulsado;
    }

}

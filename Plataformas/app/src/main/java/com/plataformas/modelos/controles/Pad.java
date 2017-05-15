package com.plataformas.modelos.controles;

import android.content.Context;

import com.plataformas.GameView;
import com.plataformas.R;
import com.plataformas.graficos.CargadorGraficos;
import com.plataformas.modelos.Modelo;

/**
 * Created by UO232346 on 06/10/2016.
 */

public class Pad extends Modelo {

    public Pad(Context context) {
        super(context, GameView.pantallaAncho*0.15 , GameView.pantallaAlto*0.8 ,
                GameView.pantallaAlto, GameView.pantallaAncho);

        altura = 100;
        ancho = 100;
        imagen = CargadorGraficos.cargarDrawable(context, R.drawable.pad);
    }

    public boolean estaPulsado(float clickX, float clickY) {
        boolean estaPulsado = false;

        if (clickX <= (x + ancho / 2) && clickX >= (x - ancho / 2) && clickY <= (y + altura / 2) && clickY >= (y - altura / 2)) {
            estaPulsado = true;
        }




        return estaPulsado;
    }

    /**
     //Debemos calcular en que dirección está yendo (Arriba, abajo, derecha o izquierda,
     //Dividimos el pad en 4 triangulos iguales, el área de cada uno define en la que avanza al pulsar
     //Puesto que con la posx y la posy tenemos el tamaño de  los 2 lados del tiangualo (y sabemos que tiene 45º desde el punto de inicio)
     //El valor absoluto de los tamaños de los lados acaba determinando el triangulo en el que se está.
     * @param clickX
     * @param clickY
     * @return float[] pos 0 = x pos 1 = y
     */
    public float[] getOrientaciones(float clickX, float clickY){
        float posY = (float) (y - clickY);
        float posX = (float) (x - clickX);
        float[] orientaciones = new float[2]; // (x , y)
        if(posY == posX){
            orientaciones[0] = 0; //x
            orientaciones[1] = 0; //y
        }
        if(posY > 0){
            if(posX > 0){
                if(Math.abs(posY) > Math.abs(posX)){//Orientación hacia arriba
                    orientaciones[1] = -1;   //Arriba
                    orientaciones[0] = 0;
                }
                else if(Math.abs(posY) < Math.abs(posX)){//Orientación hacia arriba
                    orientaciones[1] = 0;
                    orientaciones[0] = -1;  //Izquierda
                }
            }
            else if(posX < 0){
                if(Math.abs(posY) > Math.abs(posX)){//Orientación hacia arriba
                    orientaciones[1] = -1;   //Arriba
                    orientaciones[0] = 0;
                }
                else if(Math.abs(posY) < Math.abs(posX)){//Orientación hacia arriba
                    orientaciones[1] = 0;
                    orientaciones[0] = 1;  //Derecha
                }
            }
        }
        if(posY < 0){
            if(posX > 0){
                if(Math.abs(posY) > Math.abs(posX)){//Orientación hacia arriba
                    orientaciones[1] = 1;   //Abajo
                    orientaciones[0] = 0;
                }
                else if(Math.abs(posY) < Math.abs(posX)){//Orientación hacia arriba
                    orientaciones[1] = 0;
                    orientaciones[0] = -1;  //Izquierda
                }
            }
            else if(posX < 0){
                if(Math.abs(posY) > Math.abs(posX)){//Orientación hacia arriba
                    orientaciones[1] = 1;   //Abajo
                    orientaciones[0] = 0;
                }
                else if(Math.abs(posY) < Math.abs(posX)){//Orientación hacia arriba
                    orientaciones[1] = 0;
                    orientaciones[0] = 1;  //Derecha
                }
            }
        }
        return orientaciones;
    }

    public int getOrientacionX(float clickX) {
        return (int) (x - clickX);
    }

    public int getOrientacionY(float clickY){
        return (int) (y - clickY);
    }

}



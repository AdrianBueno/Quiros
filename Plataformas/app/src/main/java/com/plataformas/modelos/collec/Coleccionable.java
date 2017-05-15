package com.plataformas.modelos.collec;

import com.plataformas.modelos.IModelo;
import com.plataformas.modelos.characters.jugadores.JugadorEstandar;

/**
 * Created by nardi on 08/12/2016.
 */

public interface Coleccionable extends IModelo {

    public void improveCharacter(JugadorEstandar jugador);
    public void actualizar(long tiempo);

}

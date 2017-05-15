package com.plataformas.modelos.characters;

/**
 * Created by nardi on 06/12/2016.
 */

public interface Movible {

    public double getX();
    public double getY();
    public void setX(double x);
    public void setY(double y);
    public double getVelX();
    public double getVelY();
    public void setVelX(double velX);
    public void setVelY(double velY);

    public int getAncho();
    public int getAltura();

    public boolean isPlayer();
    public boolean isShoot();

    public  boolean destruir ();

}

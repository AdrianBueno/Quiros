package com.plataformas;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.plataformas.modelos.Nivel;
import com.plataformas.modelos.controles.BotonDisparar;
import com.plataformas.modelos.controles.BotonAtacar;
import com.plataformas.modelos.controles.Pad;


public class GameView extends SurfaceView implements SurfaceHolder.Callback  {

    boolean iniciado = false;
    Context context;
    GameLoop gameloop;

    public static int pantallaAncho;
    public static int pantallaAlto;
    public static boolean DEBUG;

    private Nivel nivel;
    private Pad pad;
    private BotonAtacar botonAtacar;
    public int numeroNivel = 0;

    public GameView(Context context) {
        super(context);
        iniciado = true;
        DEBUG = false;
        getHolder().addCallback(this);
        setFocusable(true);

        this.context = context;
        gameloop = new GameLoop(this);
        gameloop.setRunning(true);
    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // valor a Binario
        int action = event.getAction() & MotionEvent.ACTION_MASK;
        // Indice del puntero
        int pointerIndex = (event.getAction() & MotionEvent.ACTION_POINTER_INDEX_MASK) >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;

        int pointerId  = event.getPointerId(pointerIndex);
        switch (action) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                accion[pointerId] = ACTION_DOWN;
                x[pointerId] = event.getX(pointerIndex);
                y[pointerId] = event.getY(pointerIndex);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
            case MotionEvent.ACTION_CANCEL:
                accion[pointerId] = ACTION_UP;
                x[pointerId] = event.getX(pointerIndex);
                y[pointerId] = event.getY(pointerIndex);
                break;
            case MotionEvent.ACTION_MOVE:
                int pointerCount = event.getPointerCount();
                for(int i =0; i < pointerCount; i++){
                    pointerIndex = i;
                    pointerId  = event.getPointerId(pointerIndex);
                    accion[pointerId] = ACTION_MOVE;
                    x[pointerId] = event.getX(pointerIndex);
                    y[pointerId] = event.getY(pointerIndex);
                }
                break;
        }

        procesarEventosTouch();
        return true;
    }
    int actualKey = 0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
            actualKey = keyCode;
            switch (keyCode){
                case KeyEvent.KEYCODE_1:
                    if(DEBUG)
                        DEBUG = false;
                    else
                        DEBUG = true;
                    break;
                case KeyEvent.KEYCODE_W:
                    nivel.orientacionPad[0] =  0;
                    nivel.orientacionPad[1] =  -1;
                    break;
                case KeyEvent.KEYCODE_S:
                    nivel.orientacionPad[0] =  0;
                    nivel.orientacionPad[1] =  1;
                    break;
                case KeyEvent.KEYCODE_A:
                    nivel.orientacionPad[0] =  -1;
                    nivel.orientacionPad[1] =  0;
                    break;
                case KeyEvent.KEYCODE_D:
                    nivel.orientacionPad[0] =  1;
                    nivel.orientacionPad[1] =  0;
                    break;
                case KeyEvent.KEYCODE_SPACE:
                    nivel.orientacionPad[0] = 0;
                    nivel.orientacionPad[1] = 0;
                    nivel.botonAtacarPulsado = true;
                    break;
                default:
                    nivel.orientacionPad[0] = 0;
                    nivel.orientacionPad[1] = 0;
                    break;
            }
        return super.onKeyDown(keyCode, event);
    }
    @Override
    public boolean onKeyUp (int keyCode, KeyEvent event) {
        if(keyCode != actualKey)
        if (keyCode == KeyEvent.KEYCODE_W || keyCode == KeyEvent.KEYCODE_S || keyCode == KeyEvent.KEYCODE_A || keyCode == KeyEvent.KEYCODE_D) {
            nivel.orientacionPad[0] = 0;
            nivel.orientacionPad[1] = 0;
        }
        return super.onKeyDown(keyCode, event);
    }



    int NO_ACTION = 0;
    int ACTION_MOVE = 1;
    int ACTION_UP = 2;
    int ACTION_DOWN = 3;
    int accion[] = new int[6];
    float x[] = new float[6];
    float y[] = new float[6];

    public void procesarEventosTouch(){
        boolean pulsacionPadMover = false;

        for(int i=0; i < 6; i++){
            if(accion[i] != NO_ACTION ) {
                if(accion[i] == ACTION_DOWN){
                    if(nivel.nivelPausado)
                        nivel.nivelPausado = false;
                }

                if (pad.estaPulsado(x[i], y[i])) {
                    // Si almenosuna pulsacion está en el pad
                    if (accion[i] != ACTION_UP) {
                        nivel.orientacionPad = pad.getOrientaciones(x[i],y[i]);
                        pulsacionPadMover = true;

                    }
                }

                if(botonAtacar.estaPulsado(x[i],y[i])){
                    if (accion[i] == ACTION_DOWN) {
                        nivel.botonAtacarPulsado = true;
                    }
                }



            }
        }
        if(!pulsacionPadMover) {
            nivel.orientacionPad[0] =  0;
            nivel.orientacionPad[1] =  0;
        }

    }

    protected void inicializar() throws Exception {
        nivel = new Nivel(context,numeroNivel);
        nivel.gameView = this;
        pad = new Pad(context);
        botonAtacar = new BotonAtacar(context);
    }

    public void nivelCompleto() throws Exception {

        if (numeroNivel < 1){ // Número Máximo de Nivel
            numeroNivel++;
        } else {
            numeroNivel = 0;
        }
        inicializar();
    }


    public void actualizar(long tiempo) throws Exception {
        if (!nivel.nivelPausado) {
            nivel.actualizar(tiempo);
        }
    }

    protected void dibujar(Canvas canvas) {
        nivel.dibujar(canvas);
        if (!nivel.nivelPausado) {
            pad.dibujar(canvas);
            botonAtacar.dibujar(canvas);
        }
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
        pantallaAncho = width;
        pantallaAlto = height;
    }

    public void surfaceCreated(SurfaceHolder holder) {
        if (iniciado) {
            iniciado = false;
            if (gameloop.isAlive()) {
                iniciado = true;
                gameloop = new GameLoop(this);
            }

            gameloop.setRunning(true);
            gameloop.start();
        } else {
            iniciado = true;
            gameloop = new GameLoop(this);
            gameloop.setRunning(true);
            gameloop.start();
        }
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        iniciado = false;

        boolean intentarDeNuevo = true;
        gameloop.setRunning(false);
        while (intentarDeNuevo) {
            try {
                gameloop.join();
                intentarDeNuevo = false;
            }
            catch (InterruptedException e) {
            }
        }
    }

}


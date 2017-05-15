package com.plataformas.modelos;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.util.Log;

import com.plataformas.GameView;
import com.plataformas.R;
import com.plataformas.graficos.CargadorGraficos;
import com.plataformas.gestores.GestorAudio;
import com.plataformas.gestores.Utilidades;
import com.plataformas.modelos.characters.Movible;
import com.plataformas.modelos.characters.enemigos.Enemigo;
import com.plataformas.modelos.characters.enemigos.EnemigoArquero;
import com.plataformas.modelos.characters.enemigos.EnemigoAzul;
import com.plataformas.modelos.characters.enemigos.EnemigoCatapulta;
import com.plataformas.modelos.characters.enemigos.EnemigoRojo;
import com.plataformas.modelos.characters.enemigos.EnemigoVerde;
import com.plataformas.modelos.characters.jugadores.JugadorEstandar;
import com.plataformas.modelos.collec.Coleccionable;
import com.plataformas.modelos.collec.Gema;
import com.plataformas.modelos.collec.Moneda;
import com.plataformas.modelos.controles.IconoVida;
import com.plataformas.modelos.disparos.Disparo;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Nivel {

    public static int scrollEjeX = 0;
    public static int scrollEjeY = 0;
    public static boolean nivelPausado = false;

    public GameView gameView;

    //Framework
    private Context context = null;
    //static Images and audio
    public Bitmap mensaje ;
    private GestorAudio gestorAudio;
    private Fondo fondo;
    //Controles
    public float orientacionPad[] = new float[2];


    private int numeroNivel;
    private JugadorEstandar jugador;
    private List<Enemigo> enemigos;
    private List<Coleccionable> coleccionables;
    private List<Disparo> disparos;
    private IconoVida[] iconosVida;
    private Tile[][] mapaTiles;
    public boolean inicializado;
    public boolean botonAtacarPulsado = false;


    public Nivel(Context context, int numeroNivel) throws Exception {
        inicializado = false;

        this.context = context;
        this.numeroNivel = numeroNivel;
        inicializar();

        inicializado = true;
    }

    public void inicializar() throws Exception {
        scrollEjeX = 0;
        scrollEjeY = 0;
        mensaje = CargadorGraficos.cargarBitmap(context, R.drawable.description);
        nivelPausado = true;
        inicializarListas(); //Siempre antes de mapa de tiles
        inicializarMapaTiles();
        inicializarGestorAudio(context);
        inicializarIconosVida();
        inicializado = true;

    }
    public void inicializarGestorAudio(Context context) {
        gestorAudio = GestorAudio.getInstancia(context, R.raw.war);
        gestorAudio.reproducirMusicaAmbiente();
        gestorAudio.registrarSonido(GestorAudio.SONIDO_VIDA_PERDIDA,
                R.raw.lost_life);
        gestorAudio.registrarSonido(GestorAudio.SONIDO_ATAQUE_JUGADOR,
                R.raw.ataque_jugador);
        gestorAudio.registrarSonido(GestorAudio.SONIDO_DISPARO_FLECHA,
                R.raw.disparo_fecha);
    }
    public void inicializarListas(){
        fondo  = new Fondo(context,CargadorGraficos.cargarBitmap(context, R.drawable.fondo_negro), 0);
        enemigos = new LinkedList<Enemigo>();
        coleccionables = new LinkedList<Coleccionable>();
        disparos = new LinkedList<Disparo>();
    }
    public void inicializarIconosVida(){
        iconosVida = new IconoVida[jugador.getVida()];
        for(int i = 0; i < iconosVida.length;i++){
            iconosVida[i] = new IconoVida(context, GameView.pantallaAncho*(0.05+(i*0.10)),GameView.pantallaAlto*0.1);
        }
    }
    public void actualizar(long tiempo) throws Exception {
        if (inicializado) {
            sucessLevel();
            accionesPersonaje(tiempo);
            accionesEnemigas(tiempo);
            actualizarDisparos(tiempo);
            colisionesColeccionables(tiempo);
        }
    }
    public void dibujar(Canvas canvas) {
        if (inicializado) {
            fondo.dibujar(canvas);
            dibujarTiles(canvas);
            for(int i = 0; i < jugador.getVida(); i++)
                iconosVida[i].dibujar(canvas);
            for(Enemigo e : enemigos)
                e.dibujar(canvas);
            for(Disparo d : disparos)
                d.dibujar(canvas);
            for(Coleccionable c : coleccionables)
                c.dibujar(canvas);
            jugador.dibujar(canvas);

            if (nivelPausado){
                // la foto mide 480x320
                Rect orgigen = new Rect(0,0 ,
                        480,320);
                Paint efectoTransparente = new Paint();
                efectoTransparente.setAntiAlias(true);
                Rect destino = new Rect((int)(GameView.pantallaAncho/2 - 480/2),
                        (int)(GameView.pantallaAlto/2 - 320/2),
                        (int)(GameView.pantallaAncho/2 + 480/2),
                        (int)(GameView.pantallaAlto/2 + 320/2));
                canvas.drawBitmap(mensaje,orgigen,destino, null);
            }
        }
    }

    private void accionesPersonaje(long tiempo) throws  Exception{
        jugador.procesarOrdenes(orientacionPad, botonAtacarPulsado);
        if (botonAtacarPulsado){
            gestorAudio.reproducirSonido(GestorAudio.SONIDO_ATAQUE_JUGADOR);
            botonAtacarPulsado = false;
        }
        jugador.actualizar(tiempo);
        aplicarReglasMovimiento(tiempo, jugador);
    }
    private void accionesEnemigas(long tiempo) throws  Exception{
        Iterator<Enemigo> itE = enemigos.iterator();
        while (itE.hasNext()){
            Enemigo e = itE.next();
            if(e.getEstado() == -1)
                itE.remove();
            else{
                e.actualizar(tiempo);
                aplicarReglasMovimiento(tiempo,e);
                Object obj = e.atacarJugador(jugador);
                if(obj != null) {
                    disparos.add((Disparo) obj);
                    gestorAudio.reproducirSonido(GestorAudio.SONIDO_DISPARO_FLECHA);
                }
                if(e.isAtacando() && e.puedeAtacarFisicamente(jugador)){
                    if(jugador.getMsInmunidad() == 0) {
                        int tiradaAtaque = (int) Math.random()*500;             //Posibilidad de acertar
                        if(jugador.getVelY() != 0 || jugador.getVelX() != 0)    //Es mas dificil darle si se mueve
                            tiradaAtaque -= 50;
                        if(tiradaAtaque >= jugador.getCa()) {                    //Comparamos con la clase de armadura del jugador.
                            gestorAudio.reproducirSonido(GestorAudio.SONIDO_VIDA_PERDIDA);
                            if (jugador.golpeado(1) <= 0)
                                gameOver();
                        }
                    }
                }
                if(jugador.isAtacando())
                    if(jugador.puedeAtacarFisicamente(e))
                        e.destruir();
            }
        }
    }
    private void actualizarDisparos(long tiempo) throws Exception{
        Iterator<Disparo> itD = disparos.iterator();
        while(itD.hasNext()){
            Disparo d = itD.next();
            aplicarReglasMovimiento(tiempo, d);
            if(d.colisiona(jugador)){
                gestorAudio.reproducirSonido(GestorAudio.SONIDO_VIDA_PERDIDA);
                d.destruir();
                if(jugador.golpeado(d.getDamage()) <= 0)
                    gameOver();
            }
            if(d.getEstado() == -1) { //Cambiar el -1 por inactivo
                itD.remove();
            }
        }
    }

    private void colisionesColeccionables(long tiempo){
        Iterator<Coleccionable> itC = coleccionables.iterator();
        while (itC.hasNext()){
            Coleccionable c = itC.next();
            c.actualizar(tiempo);
            if(jugador.colisiona(c)){
                c.improveCharacter(jugador);
                itC.remove();
                inicializarIconosVida(); //Por si se incrementa la vida
            }
        }
    }
    private void sucessLevel() throws Exception{
        if(enemigos.size() == 0){
            mostrarMensaje(R.drawable.you_win);
            if(numeroNivel < 5) {
                numeroNivel++;
            }else {
                mostrarMensaje(R.drawable.pantalla_nivel);
                numeroNivel = 0;
            }
            inicializar();
        }
    }

    private void gameOver() throws  Exception{
        inicializar();
        mostrarMensaje(R.drawable.you_lose);
    }

    public void mostrarMensaje(@DrawableRes int mensajeId) {
        mensaje = CargadorGraficos.cargarBitmap(context, mensajeId);
        nivelPausado = true;
    }

/*
    ################################################################################################################################################
    ################################################################################################################################################
    ################################################################################################################################################
    ####################################        REGLAS DE MOVIMIENTO ARRIBA,ABAJO,DERECHA E IZQUIERDA           ####################################
     */


    private void aplicarReglasMovimiento(long tiempo, Movible movible) throws Exception {

        int tileXJugadorIzquierda
                = (int) (movible.getX() - (movible.getAncho() / 2 - 1)) / Tile.ancho;
        int tileXJugadorCentro
                = (int) movible.getX() / Tile.ancho;
        int tileXJugadorDerecha
                = (int) (movible.getX() + (movible.getAncho() / 2 - 1)) / Tile.ancho;



        int tileYJugadorInferior
                = (int) (movible.getY() + (movible.getAltura() / 2 - 1)) / Tile.altura;
        int tileYJugadorCentro
                = (int) movible.getY() / Tile.altura;
        int tileYJugadorSuperior
                = (int) (movible.getY() - (movible.getAltura() / 2 - 1)) / Tile.altura;
    //Derecha
        if (movible.getVelX() >= 0) {
            // Tengo un tile delante y es PASABLE
            // El tile de delante está dentro del Nivel
            if (tileXJugadorDerecha + 1 <= anchoMapaTiles() - 1 &&
                    tileYJugadorInferior <= altoMapaTiles() - 1 &&
                    mapaTiles[tileXJugadorDerecha + 1][tileYJugadorInferior].tipoDeColision ==
                            Tile.PASABLE &&
                    mapaTiles[tileXJugadorDerecha + 1][tileYJugadorCentro].tipoDeColision ==
                            Tile.PASABLE &&
                    mapaTiles[tileXJugadorDerecha + 1][tileYJugadorSuperior].tipoDeColision ==
                            Tile.PASABLE &&
                    mapaTiles[tileXJugadorDerecha][tileYJugadorInferior].tipoDeColision ==
                            Tile.PASABLE &&
                    mapaTiles[tileXJugadorDerecha][tileYJugadorCentro].tipoDeColision ==
                            Tile.PASABLE &&
                    mapaTiles[tileXJugadorDerecha][tileYJugadorSuperior].tipoDeColision ==
                            Tile.PASABLE) {

                movible.setX(movible.getX() + movible.getVelX());

                // No tengo un tile PASABLE delante
                // o es el FINAL del nivel o es uno SOLIDO
            } else if (tileXJugadorDerecha <= anchoMapaTiles() - 1 &&
                    tileYJugadorInferior <= altoMapaTiles() - 1 &&
                    mapaTiles[tileXJugadorDerecha][tileYJugadorInferior].tipoDeColision ==
                            Tile.PASABLE &&
                    mapaTiles[tileXJugadorDerecha][tileYJugadorCentro].tipoDeColision ==
                            Tile.PASABLE &&
                    mapaTiles[tileXJugadorDerecha][tileYJugadorSuperior].tipoDeColision ==
                            Tile.PASABLE) {

                // Si en el propio tile del jugador queda espacio para
                // avanzar más, avanzo
                int TileJugadorBordeDerecho = tileXJugadorDerecha * Tile.ancho + Tile.ancho;
                double distanciaX = TileJugadorBordeDerecho - (movible.getX() + movible.getAncho() / 2);

                if (distanciaX > 0) {
                    double velocidadNecesaria = Math.min(distanciaX, movible.getVelX());
                    movible.setX(movible.getX() + velocidadNecesaria);
                } else {
                    movible.setX(TileJugadorBordeDerecho - movible.getAncho() / 2);
                    if(movible.isShoot())
                        movible.destruir();
                }
            }
        }
    // izquierda
        if (movible.getVelX() < 0) {
            // Tengo un tile detrás y es PASABLE
            // El tile de delante está dentro del Nivel
            if (tileXJugadorIzquierda - 1 >= 0 &&
                    tileYJugadorInferior < altoMapaTiles() - 1 &&
                    mapaTiles[tileXJugadorIzquierda - 1][tileYJugadorCentro].tipoDeColision ==
                            Tile.PASABLE &&
                    mapaTiles[tileXJugadorCentro - 1][tileYJugadorCentro].tipoDeColision ==
                            Tile.PASABLE &&
                    mapaTiles[tileXJugadorIzquierda - 1][tileYJugadorCentro].tipoDeColision ==
                            Tile.PASABLE &&
                    mapaTiles[tileXJugadorIzquierda][tileYJugadorInferior].tipoDeColision ==
                            Tile.PASABLE &&
                    mapaTiles[tileXJugadorIzquierda][tileYJugadorCentro].tipoDeColision ==
                            Tile.PASABLE &&
                    mapaTiles[tileXJugadorIzquierda][tileYJugadorSuperior].tipoDeColision ==
                            Tile.PASABLE) {

                movible.setX(movible.getX() + movible.getVelX());

                // No tengo un tile PASABLE detrás
                // o es el INICIO del nivel o es uno SOLIDO
            } else if (tileXJugadorIzquierda >= 0 && tileYJugadorInferior <= altoMapaTiles() - 1 &&
                    mapaTiles[tileXJugadorIzquierda][tileYJugadorInferior].tipoDeColision
                            == Tile.PASABLE &&
                    mapaTiles[tileXJugadorIzquierda][tileYJugadorCentro].tipoDeColision
                            == Tile.PASABLE &&
                    mapaTiles[tileXJugadorIzquierda][tileYJugadorSuperior].tipoDeColision
                            == Tile.PASABLE) {

                // Si en el propio tile del jugador queda espacio para
                // avanzar más, avanzo
                int TileJugadorBordeIzquierdo = tileXJugadorIzquierda * Tile.ancho;
                double distanciaX = (movible.getX() - movible.getAncho() / 2) - TileJugadorBordeIzquierdo;

                if (distanciaX > 0) {
                    double velocidadNecesaria = Utilidades.proximoACero(-distanciaX, movible.getVelX());
                    movible.setX(movible.getX() + velocidadNecesaria);
                } else {
                    movible.setX(TileJugadorBordeIzquierdo + movible.getAncho() / 2);
                    if(movible.isShoot())
                        movible.destruir();
                }
            }
        }
        // Hacia arriba
        if (movible.getVelY() < 0) {
            // Tengo un tile delante y es PASABLE
            // El tile de delante está dentro del Nivel
            if(tileYJugadorSuperior - 1 >= 0 &&
                    mapaTiles[tileXJugadorIzquierda][tileYJugadorSuperior-1].tipoDeColision == Tile.PASABLE &&
                    mapaTiles[tileXJugadorDerecha][tileYJugadorSuperior-1].tipoDeColision == Tile.PASABLE) {
                movible.setY(movible.getY() + movible.getVelY());
            }else{
                int TileJugadorBordeSuperior = (tileYJugadorSuperior) * Tile.altura;
                double distanciaY = (movible.getY() - movible.getAltura() / 2) - TileJugadorBordeSuperior;

                if (distanciaY > 0) {
                    movible.setY(movible.getY() + Utilidades.proximoACero(-distanciaY, movible.getVelY()) );
                } else {
                    movible.setY(TileJugadorBordeSuperior + movible.getAltura() /2);
                    if(movible.isShoot())
                        movible.destruir();
                }
            }
        }
        // Hacia abajo
        if (movible.getVelY() > 0) {
            if (tileYJugadorInferior + 1 <= altoMapaTiles() - 1 &&
                    mapaTiles[tileXJugadorIzquierda][tileYJugadorInferior + 1].tipoDeColision == Tile.PASABLE
                    && mapaTiles[tileXJugadorDerecha][tileYJugadorInferior + 1].tipoDeColision == Tile.PASABLE) {
                movible.setY(movible.getY() + movible.getVelY());
            } else {
                int TileJugadorBordeInferior = tileYJugadorInferior * Tile.altura + Tile.altura;
                double distanciaY = TileJugadorBordeInferior - (movible.getY() + movible.getAltura() / 2);
                if (distanciaY > 0) {
                    movible.setY(movible.getY() + Math.min(distanciaY, movible.getVelY()) );
                } else {
                    movible.setY(TileJugadorBordeInferior - movible.getAltura() / 2);
                    if(movible.isShoot())
                        movible.destruir();
                }
            }
        }
    }



    /*
    ################################################################################################################################################
    ################################################################################################################################################
    ################################################################################################################################################
    ####################################        SECCIÓN DE INICIALIZACION y DIBUJO de TILES                     ####################################
     */
    private float tilesEnDistanciaX(double distanciaX) {
        return (float) distanciaX / Tile.ancho;
    }

    public int anchoMapaTiles() {
        return mapaTiles.length;
    }

    public int altoMapaTiles() {
        return mapaTiles[0].length;
    }

    private void inicializarMapaTiles() throws Exception {
        InputStream is = context.getAssets().open(numeroNivel + ".txt");
        int anchoLinea;
        List<String> lineas = new LinkedList<String>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        {
            String linea = reader.readLine();
            anchoLinea = linea.length();
            while (linea != null) {
                lineas.add(linea);
                if (linea.length() != anchoLinea) {
                    Log.e("ERROR", "Dimensiones incorrectas en la línea");
                    throw new Exception("Dimensiones incorrectas en la línea.");
                }
                linea = reader.readLine();
            }
        } // Inicializar la matriz
        mapaTiles = new Tile[anchoLinea][lineas.size()];
        // Iterar y completar todas las posiciones
        for (int y = 0; y < altoMapaTiles(); ++y) {
            for (int x = 0; x < anchoMapaTiles(); ++x) {
                char tipoDeTile = lineas.get(y).charAt(x);
                //lines[y][x];
                mapaTiles[x][y] = inicializarTile(tipoDeTile, x, y);
            }
        }
    }

    /**
     * Aquí inicializo cada tile desde el fichero asset.
     * @param codigoTile
     * @param x
     * @param y
     * @return
     */
    private Tile inicializarTile(char codigoTile, int x, int y) {
        int xCentroAbajoTile = x * Tile.ancho + Tile.ancho / 2;
        int yCentroAbajoTile = y * Tile.altura + Tile.altura;
        Drawable[] imagenes = new Drawable[1];
        switch (codigoTile) {
            case '.':
                imagenes = new Drawable[1];
                imagenes[0] = CargadorGraficos.cargarDrawable(context, R.drawable.tile_hierba);
                return new Tile(imagenes, Tile.PASABLE);
            case ',':
                imagenes = new Drawable[1];
                imagenes[0] = CargadorGraficos.cargarDrawable(context, R.drawable.tile_arena);
                return new Tile(imagenes, Tile.PASABLE);
            case ':':
                imagenes = new Drawable[1];
                imagenes[0] = CargadorGraficos.cargarDrawable(context, R.drawable.tile_barro);
                return new Tile(imagenes, Tile.SOLIDO);
            case ';':
                imagenes = new Drawable[2];
                imagenes[0] = CargadorGraficos.cargarDrawable(context, R.drawable.tile_hierba);
                imagenes[1] = CargadorGraficos.cargarDrawable(context, R.drawable.tile_mountain);
                return new Tile(imagenes, Tile.SOLIDO);
            case '*':
                imagenes = new Drawable[1];
                imagenes[0] = CargadorGraficos.cargarDrawable(context, R.drawable.tile_mar);
                return new Tile(imagenes, Tile.SOLIDO);
            case '#':
                imagenes = new Drawable[1];
                imagenes[0] = CargadorGraficos.cargarDrawable(context, R.drawable.tile_charco);
                return new Tile(imagenes, Tile.SOLIDO);

            case 'P':
                jugador = new JugadorEstandar(context,xCentroAbajoTile,yCentroAbajoTile);
                imagenes = new Drawable[1];
                imagenes[0] = CargadorGraficos.cargarDrawable(context, R.drawable.tile_hierba);
                return new Tile(imagenes, Tile.PASABLE);
            case 'M':
                Moneda m = new Moneda(context, xCentroAbajoTile,yCentroAbajoTile);
                coleccionables.add(m);
                imagenes = new Drawable[1];
                imagenes[0] = CargadorGraficos.cargarDrawable(context, R.drawable.tile_hierba);
                return new Tile(imagenes, Tile.PASABLE);
            case 'G':
                Gema g = new Gema(context, xCentroAbajoTile,yCentroAbajoTile);
                coleccionables.add(g);
                imagenes = new Drawable[1];
                imagenes[0] = CargadorGraficos.cargarDrawable(context, R.drawable.tile_hierba);
                return new Tile(imagenes, Tile.PASABLE);
            case '1':
                imagenes = new Drawable[1];
                imagenes[0] = CargadorGraficos.cargarDrawable(context, R.drawable.tile_hierba);
                enemigos.add(new EnemigoVerde(context,xCentroAbajoTile,yCentroAbajoTile));
                return new Tile(imagenes, Tile.PASABLE);
            case '2':
                imagenes = new Drawable[1];
                imagenes[0] = CargadorGraficos.cargarDrawable(context, R.drawable.tile_hierba);
                enemigos.add(new EnemigoRojo(context,xCentroAbajoTile,yCentroAbajoTile));
                return new Tile(imagenes, Tile.PASABLE);
            case '3':
                imagenes = new Drawable[1];
                imagenes[0] = CargadorGraficos.cargarDrawable(context, R.drawable.tile_hierba);
                enemigos.add(new EnemigoAzul(context,xCentroAbajoTile,yCentroAbajoTile));
                return new Tile(imagenes, Tile.PASABLE);
            case '4':
                imagenes = new Drawable[1];
                imagenes[0] = CargadorGraficos.cargarDrawable(context, R.drawable.tile_hierba);
                enemigos.add(new EnemigoArquero(context,xCentroAbajoTile,yCentroAbajoTile));
                return new Tile(imagenes, Tile.PASABLE);
            case '5':
                imagenes = new Drawable[1];
                imagenes[0] = CargadorGraficos.cargarDrawable(context, R.drawable.tile_hierba);
                enemigos.add(new EnemigoCatapulta(context,xCentroAbajoTile,yCentroAbajoTile));
                return new Tile(imagenes, Tile.PASABLE);
            default:
                //cualquier otro caso
                return new Tile(null, Tile.PASABLE);
        }
    }


    private void dibujarTiles(Canvas canvas) {
        int tileXJugador = (int) jugador.x / Tile.ancho;
        int tileYJugador = (int) jugador.y / Tile.altura;
        int izquierda = Math.max(0,(int) (tileXJugador - tilesEnDistanciaX(jugador.x - scrollEjeX)));
        int derecha = Math.min((izquierda + GameView.pantallaAncho / Tile.ancho + 1), anchoMapaTiles() -1);
        int arriba = Math.max(0, (int) (tileYJugador - ((jugador.y -scrollEjeY)/ Tile.altura)));
        int abajo = Math.min((arriba + GameView.pantallaAlto / Tile.altura+1), altoMapaTiles()-1);

        if (jugador.x <= anchoMapaTiles() * Tile.ancho - GameView.pantallaAncho * 0.3
                && jugador.x - scrollEjeX > GameView.pantallaAncho * 0.7) {
            scrollEjeX += (int) ((jugador.x - scrollEjeX) - GameView.pantallaAncho * 0.7);
        }
        if (jugador.x >= GameView.pantallaAncho * 0.3
                && jugador.x - scrollEjeX < GameView.pantallaAncho * 0.3) {
            scrollEjeX -= (int) (GameView.pantallaAncho * 0.3 - (jugador.x - scrollEjeX));
        }
        if (jugador.y >= GameView.pantallaAlto * 0.3
                && jugador.y - scrollEjeY < GameView.pantallaAlto * 0.3) {
            scrollEjeY -= (int) (GameView.pantallaAlto * 0.3 - (jugador.y - scrollEjeY));
        }
        if (jugador.y <= altoMapaTiles() * Tile.altura - GameView.pantallaAlto * 0.3
                && jugador.y - scrollEjeY > GameView.pantallaAlto * 0.7) {
            scrollEjeY += (int) ((jugador.y - scrollEjeY) - GameView.pantallaAlto * 0.7);
        }

        for (int y = arriba; y <= abajo; ++y) {
            for (int x = izquierda; x <= derecha; ++x) {
                if (mapaTiles[x][y].imagenes != null) {
                    // Calcular la posici?n en pantalla correspondiente
                    // izquierda, arriba, derecha , abajo
                    for(int i = 0; i < mapaTiles[x][y].imagenes.length; i++){
                        mapaTiles[x][y].imagenes[i].setBounds(
                                (x * Tile.ancho) - scrollEjeX,
                                y * Tile.altura -scrollEjeY,
                                (x * Tile.ancho) + Tile.ancho - scrollEjeX,
                                y * Tile.altura + Tile.altura - scrollEjeY);
                        mapaTiles[x][y].imagenes[i].draw(canvas);
                    }
                }
            }
        }

    }
}

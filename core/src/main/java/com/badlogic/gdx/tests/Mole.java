package com.badlogic.gdx.tests;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Mole extends ApplicationAdapter {

    //Propriedades  que informam o tamanho da tela VIRTUAL do jogo
    static int vScreenWidth =800;
    static int vScreenHeight= 600;

    //Propriedades que armazenam a camera e o Viewport
    OrthographicCamera camera;
    Viewport viewport;

    // Desenha as texturas em batch
    private SpriteBatch batch;

    //tela de fundo
    static Texture backgroundTexture; // texture image for background

    // Variáveis para armazenar os objetos tocas
    Toca toca1;
    Toca toca2;
    Toca toca3;

    //Variaveis para armazenar o objeto Toupeira
    Toupeira toupeira;

    //controle do placar
    private BitmapFont font;
    private int placar = 0;


    @Override
    public void create() {

        // cria a camera e o Viewport
        camera = new OrthographicCamera();
        viewport = new StretchViewport(vScreenHeight,vScreenHeight,camera);
        viewport.apply();
        camera.position.set(camera.viewportWidth/2,camera.viewportHeight/2,0);

        // Tela de fundo
        batch = new SpriteBatch();
        backgroundTexture = new Texture("data/ground.jpg");

        // Cria as tocas
        toca1 = new Toca(20,20);
        toca2 = new Toca(500,20);
        toca3 = new Toca(300,200);

        //cria toupeira - passa como parametro as tocas criadas
        toupeira = new Toupeira(toca1,toca2,toca3);
        // Inicializa bitmap do Placar
        font = new BitmapFont();

    }

        @Override
        public void render() {
            camera.update();
            ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
            batch.setProjectionMatrix(camera.combined);
            batch.begin();
            batch.draw(backgroundTexture,0,0,vScreenWidth,vScreenHeight);
            toupeira.render(batch);
            toca1.render(batch);
            toca2.render(batch);
            toca3.render(batch);
            font.draw(batch, "Placar: " + String.valueOf(placar), 20 , vScreenHeight - 10);
            batch.end();
            testaClique();
        }

    @Override
    public void dispose() {
        batch.dispose();
        backgroundTexture.dispose();
        toca1.dispose();
        toca2.dispose();
        toca3.dispose();
        toupeira.dispose();
    }

    void testaClique() {
        // Check if the screen is touched
        if (Gdx.input.justTouched()) {
            // Get input touch coordinates and set the temp vector with these values
            if (toupeira.handleTouch(Gdx.input.getX(), Gdx.input.getY(),viewport))
                placar += 1;
        }
    }
    @Override
    public void resize(int width, int height)
    {
        viewport.update(width, height);
    }
}

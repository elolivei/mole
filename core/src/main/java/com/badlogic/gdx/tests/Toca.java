package com.badlogic.gdx.tests;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class Toca {
    // A textura é estática pos não será alterada no decorrer do jogo
    static Texture texturaToca;
    float posX;
    float posY;

    // cria e inicializa a classe Toca
    Toca(float posX,float posY){
        this.posX=posX;
        this.posY=posY;
        //inicialza a textura uma única vêz
       Toca.texturaToca = new Texture("data/hole.png");
    }

    // O método render é simples pois a toca não sai do lugar
    public void render(SpriteBatch batch) {
        batch.draw(texturaToca,posX,posY+80);
    }

    // somente a textura deve ser descartada
    public void dispose() {
        texturaToca.dispose();
    }
}

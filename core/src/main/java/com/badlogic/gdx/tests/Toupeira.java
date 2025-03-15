package com.badlogic.gdx.tests;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Random;

public class Toupeira {

    // A textura é estática pos não será alterada no decorrer do jogo
    static Texture texturaToupeira;
    Sprite toupeira;

    // guarda as tocas recebidas
    Toca t1;
    Toca t2;
    Toca t3;

    // guarda a toca corrente - onde a toupeira está
    Toca tAtual;

    // o deslocamento do centro da toca
    float deslocaX;
    float deslocaY;

    //Controle de tempo da toupeira na toca
    float correnteTime = 0.0f;
    float MAXTIME = 0.7f;

    //Controles da Animação da Toupeira
    int larguraMole = 454;  //Largura da textura
    int alturaMole = 466;   //Altura da textura
    int elevacaoMole = 0;   //Porção da textura que esta sendo exibida
    int sliceMole = 12;    //Tamanho da fatia que iremos dividir a toupeira
    int direcaoMole=-1;     //Direção do movimento - 1=sobe 1=desce

    //Controles para saber se a toupeira foi atingida
    float posicaoX;
    float posicaoY;
    static Sound hitSound;

    // O construtor recebe tocas para poder escolhe-las
    Toupeira(Toca t1, Toca t2, Toca t3) {
        this.t1 = t1;
        this.t2 = t2;
        this.t3 = t3;

        //inicialza a textura
        texturaToupeira = new Texture("data/mole.png");

        // inicializa a Sprite definindo sua posição e seu tamanho
        toupeira = new Sprite(texturaToupeira);

        // Vamos posicionar as tocas
        tAtual = sorteia();

        // Como as tocas tem o mesmo tamanho vamos usar uma delas para calcular o deslocamento
        // que devemos adicionara a toupeira para ela se posicionar no centro

        deslocaX = (t1.texturaToca.getWidth()-toupeira.getWidth())/2;
        deslocaY = 0;

        // inicializa o som
        hitSound = Gdx.audio.newSound(Gdx.files.internal("sounds/hit.wav"));

        // Se retirar o comentário habilita o log (aba logcat)
        Gdx.app.setLogLevel(Application.LOG_DEBUG);

    }

    // Aqui será implementada a lógica para troca de toca
    Toca sorteia() {
        Random rand = new Random();
        int randomNum = rand.nextInt((2) + 1);

        switch (randomNum) {
            case 0:
                return t1;
            case 1:
                return t2;
            default:
                return t3;
        }

    }

    public void render(SpriteBatch batch) {
        // Atualiza a posição da toupeira de acordo com posição da toca
        posicaoX = tAtual.posX + deslocaX;
        posicaoY = tAtual.posY + deslocaY;
        //Gdx.app.log("Desloca", "largura " + String.valueOf(deslocaX));
        //Gdx.app.log("Desloca", "altura " + String.valueOf(deslocaY));
        //Gdx.app.log("Toca", "posX " + String.valueOf(tAtual.posX));
        //Gdx.app.log("Toca", "posY " + String.valueOf(tAtual.posY));

        //atualiza a animação  da toupeira
        //atualiza tempo que o frame foi exibido
        // Verifica se o tempo do frame expirou
        toupeira.setRegion(0, 0, larguraMole, elevacaoMole);
        toupeira.setSize(toupeira.getWidth(), elevacaoMole);
        toupeira.setBounds(posicaoX, posicaoY, toupeira.getWidth(), toupeira.getHeight());
        toupeira.setScale(0.2f, 0.2f);

        elevacaoMole += (sliceMole * direcaoMole);

        // Se toupeira esta completamente escondida muda direção do movimento
        if (elevacaoMole <= 0) {
            elevacaoMole = 0;
            direcaoMole = 1;
        }
        // se toupeira totalmente fora da toca inverte direção
        if (elevacaoMole >= alturaMole) {
            elevacaoMole = alturaMole;
            direcaoMole = -1;
        }

        // testar se esgotou o tempo na toca e trocar a toca
        if (correnteTime >= MAXTIME && elevacaoMole == 0) {
            correnteTime = 0.0f;
            tAtual = sorteia();
        } else {
            //Atualiza o tempo que a toupeira ficou em um buraco
            correnteTime += Gdx.graphics.getDeltaTime();
        }

        // Desenhar a toupeira
        toupeira.draw(batch);
    }

    // somente a textura e o som  devem ser descartados
    public void dispose() {
        texturaToupeira.dispose();
        hitSound.dispose();
    }

    // Clique atingiu a toupeira
    public void handleTouch() {

            elevacaoMole = 0;
            tAtual = sorteia();
            correnteTime = 0.0f;
            direcaoMole=-1;
            hitSound.play();
    }
}

package com.animation;

import javafx.animation.TranslateTransition;
import javafx.util.Duration;
import javafx.scene.Node;

public class shake {

    private TranslateTransition tt;
    public  shake(Node node){
        tt = new TranslateTransition(Duration.millis( 100), node);
        tt.setFromX(0f);
        tt.setFromY(0F);
        tt.setByX(10f);
        tt.setByY(10f);
        tt.setCycleCount(4);
        tt.setAutoReverse(true);

    }

    public void  playAnim(){
        tt.playFromStart();
    }
}

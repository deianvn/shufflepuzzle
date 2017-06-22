package net.rizov.shufflepuzzle.utils.helper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import net.rizov.shufflepuzzle.utils.config.ConfigManager;

public class UIHelper {

    private static Sound clickSample;

    private static Sound dropSample;

    private static Sound swapSample;

    private static Sound winSample;

    private static Sound loseSample;

    public static Sound getClickSample() {
        return clickSample;
    }

    public static void setClickSample(Sound clickSample) {
        UIHelper.clickSample = clickSample;
    }

    public static Sound getDropSample() {
        return dropSample;
    }

    public static void setDropSample(Sound dropSample) {
        UIHelper.dropSample = dropSample;
    }

    public static Sound getSwapSample() {
        return swapSample;
    }

    public static void setSwapSample(Sound swapSample) {
        UIHelper.swapSample = swapSample;
    }

    public static Sound getWinSample() {
        return winSample;
    }

    public static void setWinSample(Sound winSample) {
        UIHelper.winSample = winSample;
    }

    public static Sound getLoseSample() {
        return loseSample;
    }

    public static void setLoseSample(Sound loseSample) {
        UIHelper.loseSample = loseSample;
    }

    public static void click() {
        if (ConfigManager.isVibrationEnabled()) {
            Gdx.input.vibrate(20);
        }

        if (ConfigManager.isSoundEnabled()) {
            clickSample.play();
        }
    }

    public static void winGame() {
        if (ConfigManager.isVibrationEnabled()) {
            Gdx.input.vibrate(new long[]{0, 100, 100, 100, 100, 1600}, -1);
        }

        if (ConfigManager.isSoundEnabled()) {
            winSample.play();
        }
    }

    public static void loseGame() {
        if (ConfigManager.isVibrationEnabled()) {
            Gdx.input.vibrate(new long[]{0, 1500}, -1);
        }

        if (ConfigManager.isSoundEnabled()) {
            loseSample.play();
        }
    }

    public static void pick() {
        if (ConfigManager.isVibrationEnabled()) {
            Gdx.input.vibrate(10);
        }

        if (ConfigManager.isSoundEnabled()) {
            clickSample.play();
        }
    }

    public static void drop() {
        if (ConfigManager.isVibrationEnabled()) {
            Gdx.input.vibrate(10);
        }

        if (ConfigManager.isSoundEnabled()) {
            dropSample.play();
        }
    }

    public static void swap() {
        if (ConfigManager.isVibrationEnabled()) {
            Gdx.input.vibrate(20);
        }

        if (ConfigManager.isSoundEnabled()) {
            swapSample.play();
        }
    }

}

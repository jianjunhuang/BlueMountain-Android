package com.jianjunhuang.lib.waveview;

import android.graphics.Color;

/**
 * +------------------------+
 * |<--wave length->        |______
 * |   /\          |   /\   |  |
 * |  /  \         |  /  \  | amplitude
 * | /    \        | /    \ |  |
 * |/      \       |/      \|__|____
 * |        \      /        |  |
 * |         \    /         |  |
 * |          \  /          |  |
 * |           \/           | water level
 * |                        |  |
 * |                        |  |
 * +------------------------+__|____
 */
public class Wave {

    private float spaceRatio = 0.0f;
    private int color = Color.LTGRAY;
    private float alphaRatio = 0.5f;

    public float getSpaceRatio() {
        return spaceRatio;
    }

    public void setSpaceRatio(float spaceRatio) {
        this.spaceRatio = spaceRatio;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public float getAlphaRatio() {
        return alphaRatio;
    }

    public void setAlphaRatio(float alphaRatio) {
        this.alphaRatio = alphaRatio;
    }
}

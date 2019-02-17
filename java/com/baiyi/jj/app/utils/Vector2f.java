package com.baiyi.jj.app.utils;

public final class Vector2f {
    public float x;
    public float y;

    public Vector2f() {

    }

    public Vector2f(float x, float y) {
        set(x, y);
    }

    public Vector2f(Vector2f vector) {
        x = vector.x;
        y = vector.y;
    }

    public void set(Vector2f vector) {
        x = vector.x;
        y = vector.y;
    }

    public void set(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void add(Vector2f vector) {
        x += vector.x;
        y += vector.y;
    }

    public void subtract(Vector2f vector) {
        x -= vector.x;
        y -= vector.y;
    }

    public boolean equals(Vector2f vector) {
        if (x == vector.x && y == vector.y)
            return true;
        return false;
    }

    @Override
    public String toString() {
        return (new String("(" + x + ", " + y ));
    }

    public void add(float x, float y) {
        this.x += x;
        this.y += y;
    }

    public void scale(float spreadValueX, float spreadValueY) {
        x *= spreadValueX;
        y *= spreadValueY;
    }
}

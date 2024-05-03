package logarlec.view.utility;

import java.awt.Color;

public class Player {
    String name;
    Color color;

    public Player() {
        this.name = "";
        //random color
        generateColor();
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return this.name;
    }
    public void generateColor() {
        this.color = new Color((int)(Math.random() * 0x1000000));
    }
    public Color getColor() {
        return this.color;
    }
}

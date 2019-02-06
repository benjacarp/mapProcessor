package utn.frt.MapProcessor.model;

public class Point {
    private int x;
    private int y;
    private String color;
    private String name;

    public Point() {
    }

    public Point(int x, int y, String color, String name) {
        this.x = x;
        this.y = y;
        this.color = color;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}

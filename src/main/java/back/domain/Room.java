package back.domain;

public class Room {
    private double perimeter;
    private double area;
    
    private int number;
    
    private int[] wallsLength;
    private String type;
    private int level;

    public int[] getWallsLength() {
        return wallsLength;
    }

    public void setWallsLength(int[] wallsLength) {
        this.wallsLength = wallsLength;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public double getPerimeter() {
        return perimeter;
    }

    public void setPerimeter(double perimeter) {
        this.perimeter = perimeter;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    
    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

}

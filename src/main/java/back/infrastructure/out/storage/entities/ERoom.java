package back.infrastructure.out.storage.entities;

import java.io.Serializable;

import jakarta.persistence.*;

@Entity
@Table(name = "\"rooms\"")
public class ERoom implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "\"flat_id\"")
    private int flat_id;

    @Column(name = "\"type\"", nullable = false)
    private String type;

    @Column(name = "\"level\"", nullable = false)
    private int level;

    // @Column(name = "\"wallslength\"", nullable = false)
    // private int[] wallsLength;

    @Column(name = "\"length\"", nullable = false)
    private int length;

    @Column(name = "\"width\"", nullable = false)
    private int width;

    @Column(name = "\"perimeter\"")
    private double perimeter;

    @Column(name = "\"area\"")
    private double area;

    @Column(name = "\"number\"")
    private int number;

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getFlatId() {
        return flat_id;
    }

    public void setFlatId(int user_id) {
        this.flat_id = user_id;
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

    // public int[] getWallsLength() {
    //     return wallsLength;
    // }

    // public void setWallsLength(int[] wallsLength) {
    //     this.wallsLength = wallsLength;
    // }

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
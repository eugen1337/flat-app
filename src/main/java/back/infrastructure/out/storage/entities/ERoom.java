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
    private Integer flat_id;

    @Column(name = "\"type\"", nullable = false)
    private String type;

    @Column(name = "\"level\"", nullable = false)
    private Integer level;

    @Column(name = "\"length\"", nullable = false)
    private Integer length;

    @Column(name = "\"width\"", nullable = false)
    private Integer width;

    @Column(name = "\"perimeter\"")
    private Double perimeter;

    @Column(name = "\"area\"")
    private Double area;

    @Column(name = "\"number\"")
    private Integer number;

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getFlatId() {
        return flat_id;
    }

    public void setFlatId(Integer user_id) {
        this.flat_id = user_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Double getPerimeter() {
        return perimeter;
    }

    public void setPerimeter(Double perimeter) {
        this.perimeter = perimeter;
    }

    public Double getArea() {
        return area;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

}
package back.infrastructure.out.storage.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "\"flats\"")
public class EFlat {
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "\"user_id\"")
    private int userId;

    @Column(name = "\"total_area\"", nullable = false)
    private double totalArea;

    @Column(name = "\"total_perimeter\"", nullable = false)
    private double totalPerimeter;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public double getTotalArea() {
        return totalArea;
    }

    public void setTotalArea(double totalArea) {
        this.totalArea = totalArea;
    }

    public double getTotalPerimeter() {
        return totalPerimeter;
    }

    public void setTotalPerimeter(double totalPerimeter) {
        this.totalPerimeter = totalPerimeter;
    }

}

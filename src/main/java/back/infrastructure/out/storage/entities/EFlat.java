package back.infrastructure.out.storage.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "\"flats\"")
public class EFlat {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "\"user_id\"")
    private Integer userId;

    @Column(name = "\"total_area\"", nullable = false)
    private Double totalArea;

    @Column(name = "\"total_perimeter\"", nullable = false)
    private Double totalPerimeter;

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Double getTotalArea() {
        return totalArea;
    }

    public void setTotalArea(double totalArea) {
        this.totalArea = totalArea;
    }

    public Double getTotalPerimeter() {
        return totalPerimeter;
    }

    public void setTotalPerimeter(double totalPerimeter) {
        this.totalPerimeter = totalPerimeter;
    }

}

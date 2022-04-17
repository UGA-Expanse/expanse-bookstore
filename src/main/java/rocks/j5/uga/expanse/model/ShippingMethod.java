package rocks.j5.uga.expanse.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "shipping_method")
public class ShippingMethod {
    @Id
    @Column(name = "method_id", nullable = false)
    private Integer id;

    @Column(name = "method_name", length = 100)
    private String methodName;

    @Column(name = "cost", precision = 6, scale = 2)
    private BigDecimal cost;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

}
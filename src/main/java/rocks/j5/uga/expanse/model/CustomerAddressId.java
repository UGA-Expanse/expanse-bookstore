package rocks.j5.uga.expanse.model;

import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class CustomerAddressId implements Serializable {
    private static final long serialVersionUID = 3722650126214744870L;
    @Column(name = "customer_id", nullable = false)
    private Integer customerId;

    @Column(name = "address_id", nullable = false)
    private Integer addressId;

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        CustomerAddressId entity = (CustomerAddressId) o;
        return Objects.equals(this.customerId, entity.customerId) &&
                Objects.equals(this.addressId, entity.addressId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId, addressId);
    }

}
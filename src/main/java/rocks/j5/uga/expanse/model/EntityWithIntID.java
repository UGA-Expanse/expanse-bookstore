package rocks.j5.uga.expanse.model;

import java.util.concurrent.ThreadLocalRandom;
import lombok.Getter;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.UUID;

@Getter
@MappedSuperclass
public class EntityWithIntID {

    @Id
    @Column(name = "cart_item_id", nullable = false)
    public Integer id;

    private int min = 40;
    private int max = 10000;

    public EntityWithIntID() {
        this.id = ThreadLocalRandom.current().nextInt(min, max + 1);
    }

} // EntityWithUUID

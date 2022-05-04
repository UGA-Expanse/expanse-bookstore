package rocks.j5.uga.expanse.model;

import javax.persistence.Entity;

import lombok.Getter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.UUID;

@Getter
@MappedSuperclass
public class EntityWithUUID
{
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID id;

    public EntityWithUUID()
    {
        this.id = UUID.randomUUID();
    }
} // EntityWithUUID

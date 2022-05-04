package rocks.j5.uga.expanse.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.FetchType.EAGER;
import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Role implements Serializable
{
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
    private Long id;
    private String name;

    public Long getId()
    {
        return this.id;
    }

    public String getName()
    {
        return this.name;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof Role)
        {
            Role rhs = (Role) obj;
            return rhs.id == this.id;
        }
        return false;
    }

    @Override
    public int hashCode()
    {
        return Long.valueOf(id).hashCode();
    }
}

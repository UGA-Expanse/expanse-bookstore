package rocks.j5.uga.expanse.domain;

import lombok.Data;
import rocks.j5.uga.expanse.model.Cart;

import java.util.Collection;

@Data
public class ExpanseRequest
{
    User user;
    Cart cart;
    Collection<EncounteredError> errors;
    String action;
}

package rocks.j5.uga.expanse.component;

import org.springframework.security.core.Authentication;

public interface IAuthenticationFacade
{
    Authentication getAuthentication();
}

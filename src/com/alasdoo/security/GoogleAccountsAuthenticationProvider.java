package com.alasdoo.security;

import java.util.logging.Logger;

import com.alasdoo.entity.WineEnthusiast;
import com.alasdoo.repositories.WineEnthusiastRepository;
import com.google.appengine.api.users.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

/**
 * A simple authentication provider which interacts with {@code User} returned by the GAE {@code UserService},
 * and also the local persistent {@code UserRegistry} to build an application user principal.
 * <p>
 * If the user has been authenticated through google accounts, it will check if they are already registered
 * and either load the existing user information or assign them a temporary identity with limited access until they
 * have registered.
 * <p>
 * If the account has been disabled, a {@code DisabledException} will be raised.
 *
 * @author Vilmos Somogyi
 */

@Component
public class GoogleAccountsAuthenticationProvider implements AuthenticationProvider, MessageSourceAware {
    protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();

	private static final Logger log = Logger.getLogger(GoogleAccountsAuthenticationProvider.class.getName());

    @Autowired
    private WineEnthusiastRepository wineEnthusiastRepository;
    
    final private static String NAME = ""; // googleUser.getNickname() can be also used 
    
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        User googleUser = (User) authentication.getPrincipal();

        WineEnthusiast user = wineEnthusiastRepository.get(googleUser);

        if (user == null) {
            // User not in registry. Needs to register
        	log.info("Preparing new account - " + googleUser.getEmail());
            
            user = new WineEnthusiast(NAME, googleUser.getEmail(), googleUser);
        }else{
         	log.info("Logging in -" + user);
            
        }

        if (!user.isEnabled()) {
        	log.info("Disabled account:" + user);
            throw new DisabledException("Account is disabled");
        }
    	
        return new GaeUserAuthentication(user, authentication.getDetails());
    }

    /**
     * Indicate that this provider only supports PreAuthenticatedAuthenticationToken (sub)classes.
     */
    public final boolean supports(Class<?> authentication) {
        return PreAuthenticatedAuthenticationToken.class.isAssignableFrom(authentication);
    }

    // TODO: Check if we need this
    public void setMessageSource(MessageSource messageSource) {
        this.messages = new MessageSourceAccessor(messageSource);
    }
}

package org.odesamama.mcd.multitenancy;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
// @Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class CurrentTenantIdentifierResolverImpl implements CurrentTenantIdentifierResolver {

    @Override
    public String resolveCurrentTenantIdentifier() {

        RequestAttributes attribs = RequestContextHolder.getRequestAttributes();
        if (attribs instanceof ServletRequestAttributes) {
            HttpServletRequest request = ((ServletRequestAttributes) attribs).getRequest();
            String login = request.getHeader("login");

            return login != null ? login : "public";
        }
        return "public";
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }

}

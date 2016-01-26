package org.odesamama.mcd.multitenancy;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.HandlerMapping;

@Component
public class CurrentTenantIdentifierResolverImpl implements CurrentTenantIdentifierResolver {

    @Override
    public String resolveCurrentTenantIdentifier() {

        RequestAttributes attribs = RequestContextHolder.getRequestAttributes();
        if (attribs instanceof ServletRequestAttributes) {
            HttpServletRequest request = ((ServletRequestAttributes) attribs).getRequest();
            String email = null;
            if ("POST".equals(request.getMethod().toUpperCase())) {
                email = request.getParameter("email");
            } else if ("GET".equals(request.getMethod().toUpperCase())) {
                email = exctractEmail(request);
            }
            return email != null ? email : "public";
        }
        return "public";
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }

    private String exctractEmail(HttpServletRequest request) {

        String filePath = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
        String[] values = filePath.split("/");
        if (values.length >= 4) {
            return values[3];
        }
        return null;
    }

}

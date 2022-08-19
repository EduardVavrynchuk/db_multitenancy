package com.evavrynchuk.multitenancy.servlet.filter;

import com.evavrynchuk.multitenancy.context.TenantId;
import com.evavrynchuk.multitenancy.enums.TenantIdSource;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;

public class TenantIdSetFilter implements Filter {

    static final String HEADER_TENANT_ID = "tenantId";
    static final String ERROR_MESSAGE_MISSING = "Unable to determine tenantId for this request";
    static final String ERROR_INVALID_TENANT_ID = "request contains invalid tenant id";

    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        String tenantId = httpServletRequest.getHeader(HEADER_TENANT_ID);

        if (tenantId == null) {
            handleException(httpServletResponse, ERROR_MESSAGE_MISSING);
            return;
        } else if (! TenantIdSource.contains(tenantId)) {
            handleException(httpServletResponse, ERROR_INVALID_TENANT_ID);
            return;
        }

        TenantId.set(tenantId);

        try {
            chain.doFilter(request, response);
        } finally {
            TenantId.remove();
        }
    }

    private void handleException(HttpServletResponse httpServletResponse, String error) throws IOException {
        httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        httpServletResponse.setContentType(MediaType.TEXT_PLAIN);
        httpServletResponse.getWriter().print(error);
        httpServletResponse.flushBuffer();
    }

    @Override
    public void destroy() {

    }
}

package com.evavrynchuk.multitenancy.servlet.filter;

import java.io.IOException;
import java.util.Date;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggingFilter implements Filter {

    private static final String FILLER = "---";
    private static final String REQUEST = ">>> rqst";
    private static final String RESPONSE = "<<< resp";
    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingFilter.class);

    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        String remoteAddress = getClientIp(httpServletRequest);
        String method = httpServletRequest.getMethod();
        String path = httpServletRequest.getRequestURI();
        Date startTimestamp = new Date();

        LOGGER.info(logDataToString(new Object[] {REQUEST, FILLER, remoteAddress, FILLER, method, path}));

        try {
            chain.doFilter(request, response);
        } finally {
            int responseStatus = httpServletResponse.getStatus();
            Date endTimestamp = new Date();
            String duration = (endTimestamp.getTime() - startTimestamp.getTime()) + "ms";
            LOGGER.info(logDataToString(new Object[] {RESPONSE, duration, remoteAddress, responseStatus, method, path}));
        }
    }

    @Override
    public void destroy() {

    }

    private static String logDataToString(Object[] valuesToLog) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < valuesToLog.length; i++) {
            if (i > 0) {
                sb.append("\t");
            }
            sb.append(valuesToLog[i]);
        }
        return sb.toString();
    }

    private static String getClientIp(HttpServletRequest servletRequest) {

        String remoteAddress = "";

        if (servletRequest != null) {
            remoteAddress = servletRequest.getHeader("X-FORWARDED-FOR");
            if (remoteAddress == null || "".equals(remoteAddress)) {
                remoteAddress = servletRequest.getRemoteAddr();
            }
        }

        return remoteAddress;
    }
}

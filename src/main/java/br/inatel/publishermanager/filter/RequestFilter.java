/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  br.inatel.publishermanager.filter.RequestFilter
 *  jakarta.servlet.FilterChain
 *  jakarta.servlet.ServletException
 *  jakarta.servlet.ServletRequest
 *  jakarta.servlet.ServletResponse
 *  jakarta.servlet.annotation.WebFilter
 *  jakarta.servlet.http.HttpServletRequest
 *  jakarta.servlet.http.HttpServletResponse
 *  lombok.Generated
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.beans.factory.annotation.Value
 *  org.springframework.core.annotation.Order
 *  org.springframework.stereotype.Component
 *  org.springframework.util.AntPathMatcher
 *  org.springframework.web.filter.OncePerRequestFilter
 *  org.springframework.web.util.ContentCachingRequestWrapper
 *  org.springframework.web.util.ContentCachingResponseWrapper
 */
package br.inatel.publishermanager.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

@Component
@WebFilter(filterName="Request Filter", urlPatterns={"/*"})
@Order(value=-999)
public class RequestFilter
extends OncePerRequestFilter {
    @Generated
    private static final Logger log = LoggerFactory.getLogger(RequestFilter.class);
    @Value(value="${publisher-manager.filter.include-response}")
    private boolean includeResponse;

    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String responseBody;
        ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request, Integer.MAX_VALUE);
        ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper(response);
        filterChain.doFilter((ServletRequest)wrappedRequest, (ServletResponse)wrappedResponse);
        String requestBody = this.getContentAsString(wrappedRequest.getContentAsByteArray(), request.getCharacterEncoding());
        if (requestBody.length() > 0) {
            log.info("Request body:\n{}", (Object)requestBody);
        }
        if ((responseBody = this.getContentAsString(wrappedResponse.getContentAsByteArray(), response.getCharacterEncoding())).length() > 0 && this.includeResponse) {
            log.info("Response body:\n{}", (Object)responseBody);
        }
        wrappedResponse.copyBodyToResponse();
    }

    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        AntPathMatcher pathMatcher = new AntPathMatcher();
        List<String> excludeUrlPatterns = List.of("/actuator", "/actuator/*");
        return excludeUrlPatterns.stream().anyMatch(p -> pathMatcher.match(p, request.getServletPath()));
    }

    private String getContentAsString(byte[] buf, String charsetName) throws UnsupportedEncodingException {
        if (buf == null || buf.length == 0) {
            return "";
        }
        return new String(buf, 0, buf.length, charsetName);
    }
}


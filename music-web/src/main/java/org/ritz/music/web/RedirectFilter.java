/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ritz.music.web;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author hans
 */
public class RedirectFilter implements Filter{

    private static String SKIP_REDIRECTION = "skipRedirection";
    
    @Override
    public void init(FilterConfig fc) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if(servletRequest instanceof HttpServletRequest){
            HttpServletRequest request = (HttpServletRequest)servletRequest;
            Voter voter = (Voter)request.getSession().getAttribute("voter");
            if(voter != null && voter.hasVoted() && request.getAttribute(SKIP_REDIRECTION) == null && !request.getRequestURI().endsWith("confirm.xhtml")){
                request.setAttribute(SKIP_REDIRECTION, true);
                request.getRequestDispatcher("confirm.xhtml").forward(servletRequest, response);
            }
        }
        chain.doFilter(servletRequest, response);
    }

    @Override
    public void destroy() {
    }
    
    
    
    
    
}

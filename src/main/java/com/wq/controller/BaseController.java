package com.wq.controller;

import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BaseController {

    @Autowired
    protected HttpServletRequest request;

    @Autowired
    protected HttpServletResponse response;

    public String getBasePath() throws Exception {
        String path = this.request.getContextPath();
        String basePath = this.request.getScheme() + "://" + this.request.getServerName() + ":" + this.request.getServerPort() + path
                + "/";
        return basePath;
    }

    @SuppressWarnings("deprecation")
    public void getReleaseAddr() throws Exception {
        String path = this.request.getContextPath();
        String basePath = this.request.getScheme() + "://" + this.request.getServerName() + ":" + this.request.getServerPort() + path
                + "/";
        System.out.println("basePath:" + basePath);
        System.out.println("<br/>");
        System.out.println("getContextPath:" + this.request.getContextPath());
        System.out.println("<br/>");
        System.out.println("getServletPath:" + this.request.getServletPath());
        System.out.println("<br/>");
        System.out.println("getRequestURI:" + this.request.getRequestURI());
        System.out.println("<br/>");
        System.out.println("getRequestURL:" + this.request.getRequestURL());
        System.out.println("<br/>");
        System.out.println("getRealPath:" + this.request.getRealPath("/"));
        System.out.println("<br/>");
        System.out.println("getServletContext().getRealPath:" + this.request.getServletContext().getRealPath("/"));
        System.out.println("<br/>");
        System.out.println("getQueryString:" + this.request.getQueryString());
    }

}

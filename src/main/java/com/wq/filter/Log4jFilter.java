package com.wq.filter;

import com.wq.utils.PropertiesUtils;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

public class Log4jFilter implements Filter {

    private static final Logger logger = Logger.getLogger(Log4jFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        String url = req.getRequestURI();
        if (url.matches("^(\\/\\w+)?\\/rs\\/.*$")) {
            chain.doFilter(request, response);
            return;
        }
        if (url.indexOf("/log.log") != -1) {
            this.handleLogAccess(request, response);
            return;
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }

    /**
     * 原通过"域名/log/log.txt"和"域名/log/log.txt.2018-08-01"方式访问日志访问请求也要能正常工作。
     *
     * @param request
     * @param response
     */
    private void handleLogAccess(ServletRequest request, ServletResponse response) throws IOException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        resp.setContentType("application/octet-stream;charset=UTF-8");
        String logDir = PropertiesUtils.getInstance().getProperty("log4j.properties", "log4j.appender.DAILY_ROLLING_FILE.File");
        logDir = logDir.replaceAll("\\\\", "/");
        logDir = PropertiesUtils.getInstance().appendSlash(logDir);
        PrintWriter pw = resp.getWriter();
        String logFileName = "";
        String timeStr = "";
        try {
            logFileName = PropertiesUtils.getInstance().getProperty("log4j.properties", "log4j.appender.DAILY_ROLLING_FILE.File");
            logFileName = logFileName.replaceAll("\\\\", "/");
            String[] strList = logFileName.split("[/]");
            logFileName = strList[strList.length - 1];
            String url = req.getRequestURI();
            strList = url.split("[/]");
            String urlLogFileName = strList[strList.length - 1];
            strList = urlLogFileName.split("[.]");
            if (strList.length == 3) {
                timeStr = strList[strList.length - 1];
            }
        } catch (Exception e) {
            logger.error("系统日志访问错误：", e);
        }
        String logRealFileName = logDir + logFileName;
        if (timeStr != null && timeStr.trim().length() != 0) {
            logRealFileName = logRealFileName + "." + timeStr;
        }
        File logFile = new File(logRealFileName);
        if (logFile.exists()) {
            BufferedReader bw = new BufferedReader(new InputStreamReader(new FileInputStream(logFile), "UTF-8"));
            String line = null;
            while ((line = bw.readLine()) != null) {
                pw.println(line);
                pw.flush();
            }
            bw.close();
        } else {
            pw.print("日志文件不存在！");
        }
        pw.close();

    }
}

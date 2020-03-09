package com.x.protocol.network.factory.http.utils;

import com.x.commons.util.string.Strings;
import org.apache.http.StatusLine;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

/**
 * @Desc TODO
 * @Date 2020-03-04 21:32
 * @Author AD
 */
public final class HttpResponseHelper {
    
    private HttpResponseHelper() {}
    
    public static boolean flush(HttpServletResponse response) {
        try {
            if (!response.isCommitted()) {
                response.flushBuffer();
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public static void write(HttpServletResponse response, String msg) throws IOException {
        if (!response.isCommitted()) {
            PrintWriter writer = response.getWriter();
            writer.write(msg);
        }
    }
    
    public static void write(HttpServletResponse resp, byte[] bytes) throws IOException {
        if (!resp.isCommitted()) {
            ServletOutputStream out = resp.getOutputStream();
            out.write(bytes);
        }
    }
    
    public static void sendError(HttpServletResponse resp, int errorCode, String errorMsg) throws Exception {
        if (!resp.isCommitted()) {
            if (!Strings.isNull(errorMsg)) {
                String error = URLEncoder.encode(errorMsg, resp.getCharacterEncoding());
                resp.addHeader("Error-message", error);
                resp.sendError(errorCode, error);
            } else {
                resp.sendError(errorCode);
            }
        }
    }
    
    public static String getStatusMessage(StatusLine line) {
        return line == null ? "" : line.getReasonPhrase();
    }
    
    public static String getStatusMessage(StatusLine line, String charset) {
        if (line == null) {
            return "";
        } else {
            String reason = line.getReasonPhrase();
            try {
                return new String(reason.getBytes("ISO-8859-1"), charset);
            } catch (UnsupportedEncodingException e) {
                return "";
            }
        }
    }
    
    public static byte[] readResponse(InputStream in) throws IOException {
        BufferedInputStream reader = new BufferedInputStream(in);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        int length;
        while ((length = reader.read(buf)) != -1) {
            out.write(buf, 0, length);
        }
        out.flush();
        out.close();
        return out.toByteArray();
    }
    
}

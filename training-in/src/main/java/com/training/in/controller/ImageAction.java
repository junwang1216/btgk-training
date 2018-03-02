package com.training.in.controller;

import com.training.core.common.util.CustomizedPropertyConfigurer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

public class ImageAction extends HttpServlet {
    private static final long serialVersionUID = -1L;

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.doPost(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String basePath = request.getSession().getServletContext().getRealPath("");
        String sourcePath = CustomizedPropertyConfigurer.getPhotoPath().toString();

        File file = new File(basePath + sourcePath + request.getParameter("p"));

        if (file.exists()) {
            FileInputStream inputStream = new FileInputStream(file);
            int i = inputStream.available();
            byte[] buff = new byte[i];
            inputStream.read(buff);
            inputStream.close();
            response.setContentType("image/*");
            OutputStream out = response.getOutputStream();
            out.write(buff);
            out.close();
        }
    }
}

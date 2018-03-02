package com.training.web.controller;

import com.training.core.common.annotation.Desc;
import com.training.core.common.annotation.NotProtected;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.net.URL;

/**
 * Created by wangjun on 2017/4/28.
 */
@Controller
@RequestMapping("/web/document")
public class DocumentController extends BaseController {

    /*keywords,class,article*/

    @Desc("文献检索")
    @NotProtected
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public ModelAndView renderDocumentSearch() {
        return new ModelAndView("Document/Search")
                .addObject("isLogin", false);
    }

    @Desc("文献列表")
    @NotProtected
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String renderDocumentList(Model model) {

        model.addAttribute("isLogin", true);

        return "Document/List";
    }

    @Desc("文献内容")
    @NotProtected
    @RequestMapping(value = "/content", method = RequestMethod.GET)
    public String renderDocumentContent(Model model) {

        model.addAttribute("isLogin", false);

        return "Document/Content";
    }

    @Desc("文献预览")
    @NotProtected
    @RequestMapping(value = "/preview", method = RequestMethod.GET)
    public void renderDocumentPreview(HttpServletResponse response) throws Exception {

        File f = new File("/Users/wangjun/workspace/test/data/test.pdf");

        response.reset(); // 非常重要
        if (true) { // 在线打开方式
            URL u = new URL("file:///" + f.getAbsolutePath());
            String contentType = u.openConnection().getContentType();
            response.setContentType(contentType);
            response.setHeader("Content-Disposition", "inline;filename=" + f.getName());
            // 文件名应该编码成utf-8
        } else {
            // 纯下载方式
            response.setContentType("application/x-msdownload");
            response.setHeader("Content-Disposition", "attachment;filename=" + f.getName());
        }
    }

}

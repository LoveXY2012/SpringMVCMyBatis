package com.baozi.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.baozi.manager.ImageCodeManager;

/**
 * @author 包志晨 lovexy201211@gmail.com
 * @version 创建时间：2019年1月29日 下午4:58:43
 */
@Controller
public class ImageCodeController{

    private static final Logger LOGGER = LoggerFactory.getLogger(ImageCodeController.class);

    @Autowired
    private ImageCodeManager imageCodeManager;

    @RequestMapping(value = "/imageCode/get",method = RequestMethod.GET)
    public String getImageCode(Model model){
        String imagCode = imageCodeManager.getImagCode();
        model.addAttribute("imageCode", imagCode);
        return "imageCode";
    }
}

package com.baozi.manager.image;

import javax.servlet.http.HttpServletResponse;

/**
 * 图形验证码控制类
 * 
 * @author 包志晨 lovexy201211@gmail.com
 * @version 创建时间：2019年1月29日 下午2:21:14
 */
public interface ImageCodeManager{

    String getImageCode();

    String createCode(int length);
    
    void writeImageCode2Response(HttpServletResponse response);
}

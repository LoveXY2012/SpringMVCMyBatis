package com.baozi.manager.image;

/**
* @author 包志晨  lovexy201211@gmail.com
* @version 创建时间：2019年1月29日 下午2:21:14
*/
public interface ImageCodeManager{
    
    String getImagCode();
    
    String createCode(int length);
}

package com.baozi.manager.image.impl;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.baozi.manager.image.ImageCodeManager;
import com.baozi.util.BASE64Util;
import com.feilong.core.util.RandomUtil;

/**
 * @author 包志晨 lovexy201211@gmail.com
 * @version 创建时间：2019年1月29日 下午2:52:10
 */
@Service
public class ImageCodeManagerImpl implements ImageCodeManager{

    private static final Logger LOGGER = LoggerFactory.getLogger(ImageCodeManagerImpl.class);

    // 图片的宽度。
    private int width = 1200;

    // 图片的高度。
    private int height = 400;

    // 验证码干扰线数
    private int lineCount = 10;

    public String getImageCode(){
        String code = createCode(4);
        BufferedImage bufferedImage = drawCodeImage(code);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try{
            ImageIO.write(bufferedImage, "png", byteArrayOutputStream);
        }catch (IOException e){
            LOGGER.error("write image error", e);
        }finally {
            try{
                byteArrayOutputStream.close();
            }catch (IOException e){
                LOGGER.error("Close ByteArrayOutputStream failed",e);
            }
        }
        byte[] bytes = byteArrayOutputStream.toByteArray();
        return "data:image/png;base64," + BASE64Util.encode(bytes);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.baozi.manager.ImageCodeManager#createCode(int)
     */
    public String createCode(int length){
        String mixedChar = "abcdefghijklmnopqrstuvwxyz0123456789";
        return RandomUtil.createRandomFromString(mixedChar, length);
    }

    private BufferedImage drawCodeImage(String verCode){
        int x = 0;
        int fontHeight;
        int codeY = 0;
        int red = 0;
        int green = 0;
        int blue = 0;
        int codeCount = verCode.length();
        x = width / codeCount;//每个字符的宽度
        fontHeight = height - 20;//字体的高度
        codeY = height - 40;

        // 图像buffer
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = bufferedImage.createGraphics();
        // 生成随机数
        Random random = new Random();
        // 将图像填充为白色
        graphics2D.setColor(Color.BLACK);
        graphics2D.fillRect(0, 0, width, height);

        //图片划线
        for (int i = 0; i < lineCount; i++){
            // 起点X坐标
            int xs = random.nextInt(width / 2);
            // 起点Y坐标
            int ys = random.nextInt(height);
            // 终点X坐标
            int xe = random.nextInt(width / 2) + width / 2;
            // 终点Y坐标
            int ye = random.nextInt(height);
            red = random.nextInt(255);
            green = random.nextInt(255);
            blue = random.nextInt(255);
            graphics2D.setColor(new Color(red, green, blue));
            graphics2D.drawLine(xs, ys, xe, ye);
        }

        Font font = new Font("Arial", Font.BOLD, fontHeight);
        graphics2D.setFont(font);
        // 将验证码写入图片
        for (int i = 0; i < codeCount; i++){
            red = random.nextInt(255);
            green = random.nextInt(255);
            blue = random.nextInt(255);
            graphics2D.setColor(new Color(red, green, blue));
            graphics2D.drawString(String.valueOf(verCode.charAt(i)), i * x, codeY);
        }
        return bufferedImage;
    }

    /* (non-Javadoc)
     * @see com.baozi.manager.image.ImageCodeManager#writeImageCode2Response(javax.servlet.http.HttpServletResponse)
     */
    public void writeImageCode2Response(HttpServletResponse response){
        String code = createCode(4);
        BufferedImage bufferedImage = drawCodeImage(code);
        try{
            ImageIO.write(bufferedImage, "png", response.getOutputStream());
        }catch (IOException e){
            LOGGER.error("write image error", e);
        }
    }
}

package com.baozi.manager.impl;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.baozi.manager.ImageCodeManager;
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
    private int width = 120;

    // 图片的高度。
    private int height = 40;

    // 验证码干扰线数
    private int lineCount = 10;

    public String getImagCode(){
        String code = createCode(4);
        BufferedImage bufferedImage = drawCodeImage(code);
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try{
            ImageIO.write(bufferedImage, "png", output);
        }catch (IOException e){
            LOGGER.error("write image error", e);
        }
        byte[] bytes = output.toByteArray();
        return "data:image/jpeg;base64," + BASE64Util.encode(bytes);
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
        fontHeight = height - 2;//字体的高度
        codeY = height - 4;

        // 图像buffer
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = bufferedImage.createGraphics();
        // 生成随机数
        Random random = new Random();
        // 将图像填充为白色
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);

        //图片划线
        for (int i = 0; i < lineCount; i++){
            int xs = random.nextInt(width / 2);
            int ys = random.nextInt(height);
            int xe = random.nextInt(width / 2) + width / 2;
            int ye = random.nextInt(height);
            red = random.nextInt(255);
            green = random.nextInt(255);
            blue = random.nextInt(255);
            g.setColor(new Color(red, green, blue));
            g.drawLine(xs, ys, xe, ye);
        }

        Font font = new Font("Arial", Font.BOLD, fontHeight);
        g.setFont(font);
        // 将验证码写入图片
        for (int i = 0; i < codeCount; i++){
            red = random.nextInt(255);
            green = random.nextInt(255);
            blue = random.nextInt(255);
            g.setColor(new Color(red, green, blue));
            g.drawString(String.valueOf(verCode.charAt(i)), i * x, codeY);
        }
        return bufferedImage;
    }
}

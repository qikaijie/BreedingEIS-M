package com.liuxn.yuzhong.common.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code128Writer;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

/**
 * 使用YuZhongCode生成带文字的二维码/条形码图片，自动调节文字相对二维码/条形码图片的大小
 * 可选是否保存二维码图片；结果返回base64编码的图片数据字符串
 * 页面显示：<img src="data:image/png;base64,${imageBase64QRCode}"/>
 * @author liuxn
 * @date 20210601
 */
public class YuZhongCodeUtils {

    public static void main(String[] args) throws WriterException  
    {  	
        try  
        {  
        	String outPath = "d:\\" + DateUtils.dateTimeNow("yyyyMMddHHmmssSSS") + ".png";
        	//createQRCode("Z9-BM1W-1501-4@1602-422", outPath, 400, 400, "这是二维码下的文字");
        	//createBARCode("Z9-BM1W-1501-4@1602-422", outPath, 300, 80, "这是二维码下的文字");
        	//createPlantCode("Z9-BM1W-1501-4@1602-422", outPath, "Z9-BM1W-1501-4@1602-422");
        	List<String> list = new ArrayList<String>();
        	list.add("Z5-BMZ2-0101-1@1601-1");
        	list.add("Z5-BMZ2-0101-10@1601-10");
        	list.add("Z5-BMZ2-0201-51@1601-100");
        	list.add("Z5-BMZ2-0201-52@1601-101");
        	list.add("Z5-BMZ2-0201-53@1601-102");
        	list.add("Z5-BMZ2-0201-54@1601-103");
        	list.add("Z5-BMZ2-0201-55@1601-104");
        	list.add("Z5-BMZ2-0201-56@1601-105");
        	list.add("Z5-BMZ2-0201-57@1601-106");
        	list.add("Z5-BMZ2-0201-58@1601-107");
        	list.add("Z5-BMZ2-0201-59@1601-108");
        	list.add("Z5-BMZ2-0201-60@1601-109");
        	list.add("Z5-BMZ2-0101-11@1601-11");
        	list.add("Z5-BMZ2-0201-61@1601-110");
        	list.add("Z5-BMZ2-0201-62@1601-111");
        	list.add("Z5-BMZ2-0201-63@1601-112");
        	list.add("Z5-BMZ2-0201-64@1601-113");
        	list.add("Z5-BMZ2-0201-65@1601-114");
        	list.add("Z5-BMZ2-0201-66@1601-115");
        	list.add("Z5-BMZ2-0201-67@1601-116");
        	list.add("Z5-BMZ2-0201-68@1601-117");
        	list.add("Z5-BMZ2-0201-69@1601-118");
        	list.add("Z5-BMZ2-0201-70@1601-119");
        	list.add("Z5-BMZ2-0101-12@1601-12");
        	list.add("Z5-BMZ2-0201-71@1601-120");
        	list.add("Z5-BMZ2-0201-72@1601-121");
        	list.add("Z5-BMZ2-0201-73@1601-122");
        	list.add("Z5-BMZ2-0201-74@1601-123");
        	list.add("Z5-BMZ2-0201-75@1601-124");
        	list.add("Z5-BMZ2-0201-76@1601-125");
        	list.add("Z5-BMZ2-0201-77@1601-126");
        	list.add("Z5-BMZ2-0201-78@1601-127");
        	list.add("Z5-BMZ2-0201-79@1601-128");
        	list.add("Z5-BMZ2-0201-80@1601-129");
        	list.add("Z5-BMZ2-0101-13@1601-13");
        	list.add("Z5-BMZ2-0201-81@1601-130");
        	list.add("Z5-BMZ2-0201-82@1601-131");
        	list.add("Z5-BMZ2-0201-83@1601-132");
        	list.add("Z5-BMZ2-0201-84@1601-133");
        	list.add("Z5-BMZ2-0201-85@1601-134");
        	list.add("Z5-BMZ2-0201-86@1601-135");
        	list.add("Z5-BMZ2-0201-87@1601-136");
        	list.add("Z5-BMZ2-0201-88@1601-137");
        	list.add("Z5-BMZ2-0201-89@1601-138");
        	list.add("Z5-BMZ2-0201-90@1601-139");
        	list.add("Z5-BMZ2-0101-14@1601-14");
        	list.add("Z5-BMZ2-0201-91@1601-140");
        	list.add("Z5-BMZ2-0201-92@1601-141");
        	list.add("Z5-BMZ2-0201-93@1601-142");
        	list.add("Z5-BMZ2-0201-94@1601-143");
        	list.add("Z5-BMZ2-0201-95@1601-144");
        	list.add("Z5-BMZ2-0201-96@1601-145");
        	list.add("Z5-BMZ2-0201-97@1601-146");
        	list.add("Z5-BMZ2-0201-98@1601-147");
        	list.add("Z5-BMZ2-0201-99@1601-148");
        	list.add("Z5-BMZ2-0101-15@1601-15");
        	list.add("Z5-BMZ2-0101-16@1601-16");
        	list.add("Z5-BMZ2-0101-17@1601-17");
        	list.add("Z5-BMZ2-0101-18@1601-18");
        	list.add("Z5-BMZ2-0101-19@1601-19");
        	list.add("Z5-BMZ2-0101-2@1601-2");
        	list.add("Z5-BMZ2-0101-20@1601-20");
        	list.add("Z5-BMZ2-0101-21@1601-21");
        	list.add("Z5-BMZ2-0101-22@1601-22");
        	list.add("Z5-BMZ2-0101-23@1601-23");
        	list.add("Z5-BMZ2-0101-24@1601-24");
        	list.add("Z5-BMZ2-0101-25@1601-25");
        	list.add("Z5-BMZ2-0101-26@1601-26");
        	list.add("Z5-BMZ2-0101-27@1601-27");
        	list.add("Z5-BMZ2-0101-28@1601-28");
        	list.add("Z5-BMZ2-0101-29@1601-29");
        	list.add("Z5-BMZ2-0101-3@1601-3");
        	list.add("Z5-BMZ2-0101-30@1601-30");
        	list.add("Z5-BMZ2-0101-31@1601-31");
        	list.add("Z5-BMZ2-0101-32@1601-32");
        	list.add("Z5-BMZ2-0101-33@1601-33");
        	list.add("Z5-BMZ2-0101-34@1601-34");
        	list.add("Z5-BMZ2-0101-35@1601-35");
        	list.add("Z5-BMZ2-0101-36@1601-36");
        	list.add("Z5-BMZ2-0101-37@1601-37");
        	list.add("Z5-BMZ2-0101-38@1601-38");
        	list.add("Z5-BMZ2-0101-39@1601-39");
        	list.add("Z5-BMZ2-0101-4@1601-4");
        	list.add("Z5-BMZ2-0101-40@1601-40");
        	list.add("Z5-BMZ2-0101-41@1601-41");
        	list.add("Z5-BMZ2-0101-42@1601-42");
        	list.add("Z5-BMZ2-0101-43@1601-43");
        	list.add("Z5-BMZ2-0101-44@1601-44");
        	list.add("Z5-BMZ2-0101-45@1601-45");
        	list.add("Z5-BMZ2-0101-46@1601-46");
        	list.add("Z5-BMZ2-0101-47@1601-47");
        	list.add("Z5-BMZ2-0101-48@1601-48");
        	list.add("Z5-BMZ2-0101-49@1601-49");
        	list.add("Z5-BMZ2-0101-5@1601-5");
        	list.add("Z5-BMZ2-0201-1@1601-50");
        	list.add("Z5-BMZ2-0201-2@1601-51");
        	list.add("Z5-BMZ2-0201-3@1601-52");
        	list.add("Z5-BMZ2-0201-4@1601-53");
        	list.add("Z5-BMZ2-0201-5@1601-54");
        	list.add("Z5-BMZ2-0201-6@1601-55");
        	list.add("Z5-BMZ2-0201-7@1601-56");
        	list.add("Z5-BMZ2-0201-8@1601-57");
        	list.add("Z5-BMZ2-0201-9@1601-58");
        	list.add("Z5-BMZ2-0201-10@1601-59");
        	list.add("Z5-BMZ2-0101-6@1601-6");
        	list.add("Z5-BMZ2-0201-11@1601-60");
        	list.add("Z5-BMZ2-0201-12@1601-61");
        	list.add("Z5-BMZ2-0201-13@1601-62");
        	list.add("Z5-BMZ2-0201-14@1601-63");
        	list.add("Z5-BMZ2-0201-15@1601-64");
        	list.add("Z5-BMZ2-0201-16@1601-65");
        	list.add("Z5-BMZ2-0201-17@1601-66");
        	list.add("Z5-BMZ2-0201-18@1601-67");
        	list.add("Z5-BMZ2-0201-19@1601-68");
        	list.add("Z5-BMZ2-0201-20@1601-69");
        	list.add("Z5-BMZ2-0101-7@1601-7");
        	list.add("Z5-BMZ2-0201-21@1601-70");
        	list.add("Z5-BMZ2-0201-22@1601-71");
        	list.add("Z5-BMZ2-0201-23@1601-72");
        	list.add("Z5-BMZ2-0201-24@1601-73");
        	list.add("Z5-BMZ2-0201-25@1601-74");
        	list.add("Z5-BMZ2-0201-26@1601-75");
        	list.add("Z5-BMZ2-0201-27@1601-76");
        	list.add("Z5-BMZ2-0201-28@1601-77");
        	list.add("Z5-BMZ2-0201-29@1601-78");
        	list.add("Z5-BMZ2-0201-30@1601-79");
        	list.add("Z5-BMZ2-0101-8@1601-8");
        	list.add("Z5-BMZ2-0201-31@1601-80");
        	list.add("Z5-BMZ2-0201-32@1601-81");
        	list.add("Z5-BMZ2-0201-33@1601-82");
        	list.add("Z5-BMZ2-0201-34@1601-83");
        	list.add("Z5-BMZ2-0201-35@1601-84");
        	list.add("Z5-BMZ2-0201-36@1601-85");
        	list.add("Z5-BMZ2-0201-37@1601-86");
        	list.add("Z5-BMZ2-0201-38@1601-87");
        	list.add("Z5-BMZ2-0201-39@1601-88");
        	list.add("Z5-BMZ2-0201-40@1601-89");
        	list.add("Z5-BMZ2-0101-9@1601-9");
        	list.add("Z5-BMZ2-0201-41@1601-90");
        	list.add("Z5-BMZ2-0201-42@1601-91");
        	list.add("Z5-BMZ2-0201-43@1601-92");
        	list.add("Z5-BMZ2-0201-44@1601-93");
        	list.add("Z5-BMZ2-0201-45@1601-94");
        	list.add("Z5-BMZ2-0201-46@1601-95");
        	list.add("Z5-BMZ2-0201-47@1601-96");
        	list.add("Z5-BMZ2-0201-48@1601-97");
        	list.add("Z5-BMZ2-0201-49@1601-98");
        	list.add("Z5-BMZ2-0201-50@1601-99");
        	list.add("Z11-BM1E-0601-1@1604-1");
        	list.add("Z11-BM1E-0601-10@1604-10");
        	list.add("Z11-BM1E-0701-47@1604-100");
        	list.add("Z11-BM1E-0701-48@1604-101");
        	list.add("Z11-BM1E-0701-49@1604-102");
        	list.add("Z11-BM1E-0701-50@1604-103");
        	list.add("Z11-BM1E-0701-51@1604-104");
        	list.add("Z11-BM1E-0701-52@1604-105");
        	list.add("Z11-BM1E-0701-53@1604-106");
        	list.add("Z11-BM1E-0801-1@1604-107");
        	list.add("Z11-BM1E-0801-2@1604-108");
        	list.add("Z11-BM1E-0801-3@1604-109");
        	list.add("Z11-BM1E-0601-11@1604-11");
        	list.add("Z11-BM1E-0801-4@1604-110");
        	list.add("Z11-BM1E-0801-5@1604-111");
        	list.add("Z11-BM1E-0801-6@1604-112");
        	list.add("Z11-BM1E-0801-7@1604-113");
        	list.add("Z11-BM1E-0801-8@1604-114");
        	list.add("Z11-BM1E-0801-9@1604-115");
        	list.add("Z11-BM1E-0801-10@1604-116");
        	list.add("Z11-BM1E-0801-11@1604-117");
        	list.add("Z11-BM1E-0801-12@1604-118");
        	list.add("Z11-BM1E-0801-13@1604-119");
        	list.add("Z11-BM1E-0601-12@1604-12");
        	list.add("Z11-BM1E-0801-14@1604-120");
        	list.add("Z11-BM1E-0801-15@1604-121");
        	list.add("Z11-BM1E-0801-16@1604-122");
        	list.add("Z11-BM1E-0801-17@1604-123");
        	list.add("Z11-BM1E-0801-18@1604-124");
        	list.add("Z11-BM1E-0801-19@1604-125");
        	list.add("Z11-BM1E-0801-20@1604-126");
        	list.add("Z11-BM1E-0801-21@1604-127");
        	list.add("Z11-BM1E-0801-22@1604-128");
        	list.add("Z11-BM1E-0801-23@1604-129");
        	list.add("Z11-BM1E-0601-13@1604-13");
        	list.add("Z11-BM1E-0801-24@1604-130");
        	list.add("Z11-BM1E-0801-25@1604-131");
        	list.add("Z11-BM1E-0801-26@1604-132");
        	list.add("Z11-BM1E-0801-27@1604-133");
        	list.add("Z11-BM1E-0801-28@1604-134");
        	list.add("Z11-BM1E-0801-29@1604-135");
        	list.add("Z11-BM1E-0801-30@1604-136");
        	list.add("Z11-BM1E-0801-31@1604-137");
        	list.add("Z11-BM1E-0801-32@1604-138");
        	list.add("Z11-BM1E-0801-33@1604-139");
        	list.add("Z11-BM1E-0601-14@1604-14");
        	list.add("Z11-BM1E-0801-34@1604-140");
        	list.add("Z11-BM1E-0801-35@1604-141");
        	list.add("Z11-BM1E-0801-36@1604-142");
        	list.add("Z11-BM1E-0801-37@1604-143");
        	list.add("Z11-BM1E-0801-38@1604-144");
        	list.add("Z11-BM1E-0801-39@1604-145");
        	list.add("Z11-BM1E-0801-40@1604-146");
        	list.add("Z11-BM1E-0801-41@1604-147");
        	list.add("Z11-BM1E-0801-42@1604-148");
        	list.add("Z11-BM1E-0801-43@1604-149");
        	list.add("Z11-BM1E-0601-15@1604-15");
        	list.add("Z11-BM1E-0801-44@1604-150");
        	list.add("Z11-BM1E-0801-45@1604-151");
        	list.add("Z11-BM1E-0801-46@1604-152");
        	list.add("Z11-BM1E-0801-47@1604-153");
        	list.add("Z11-BM1E-0801-48@1604-154");
        	list.add("Z11-BM1E-0801-49@1604-155");
        	list.add("Z11-BM1E-0801-50@1604-156");
        	list.add("Z11-BM1E-0901-1@1604-157");
        	list.add("Z11-BM1E-0901-2@1604-158");
        	list.add("Z11-BM1E-0901-3@1604-159");
        	list.add("Z11-BM1E-0601-16@1604-16");
        	list.add("Z11-BM1E-0901-4@1604-160");
        	list.add("Z11-BM1E-0901-5@1604-161");
        	list.add("Z11-BM1E-0901-6@1604-162");
        	list.add("Z11-BM1E-0901-7@1604-163");
        	list.add("Z11-BM1E-0901-8@1604-164");
        	list.add("Z11-BM1E-0901-9@1604-165");
        	list.add("Z11-BM1E-0901-10@1604-166");
        	list.add("Z11-BM1E-0901-11@1604-167");
        	list.add("Z11-BM1E-0901-12@1604-168");
        	list.add("Z11-BM1E-0901-13@1604-169");
        	list.add("Z11-BM1E-0601-17@1604-17");
        	list.add("Z11-BM1E-0901-14@1604-170");
        	list.add("Z11-BM1E-0901-15@1604-171");
        	list.add("Z11-BM1E-0901-16@1604-172");
        	list.add("Z11-BM1E-0901-17@1604-173");
        	list.add("Z11-BM1E-0901-18@1604-174");
        	list.add("Z11-BM1E-0901-19@1604-175");
        	list.add("Z11-BM1E-0901-20@1604-176");
        	list.add("Z11-BM1E-0901-21@1604-177");
        	list.add("Z11-BM1E-0901-22@1604-178");
        	list.add("Z11-BM1E-0901-23@1604-179");
        	list.add("Z11-BM1E-0601-18@1604-18");
        	list.add("Z11-BM1E-0901-24@1604-180");
        	list.add("Z11-BM1E-0901-25@1604-181");
        	list.add("Z11-BM1E-0901-26@1604-182");
        	list.add("Z11-BM1E-0901-27@1604-183");
        	list.add("Z11-BM1E-0901-28@1604-184");
        	list.add("Z11-BM1E-0901-29@1604-185");
        	list.add("Z11-BM1E-0901-30@1604-186");
        	list.add("Z11-BM1E-0901-31@1604-187");
        	list.add("Z11-BM1E-0901-32@1604-188");
        	list.add("Z11-BM1E-0901-33@1604-189");
        	list.add("Z11-BM1E-0601-19@1604-19");
        	list.add("Z11-BM1E-0901-34@1604-190");
        	list.add("Z11-BM1E-0901-35@1604-191");
        	list.add("Z11-BM1E-0901-36@1604-192");
        	list.add("Z11-BM1E-0901-37@1604-193");
        	list.add("Z11-BM1E-0901-38@1604-194");
        	list.add("Z11-BM1E-0901-39@1604-195");
        	list.add("Z11-BM1E-0901-40@1604-196");
        	list.add("Z11-BM1E-0901-41@1604-197");
        	list.add("Z11-BM1E-0901-42@1604-198");
        	list.add("Z11-BM1E-0901-43@1604-199");
        	list.add("Z11-BM1E-0601-2@1604-2");
        	list.add("Z11-BM1E-0601-20@1604-20");
        	list.add("Z11-BM1E-0901-44@1604-200");
        	list.add("Z11-BM1E-0901-45@1604-201");
        	list.add("Z11-BM1E-0901-46@1604-202");
        	list.add("Z11-BM1E-0901-47@1604-203");
        	list.add("Z11-BM1E-0901-48@1604-204");
        	list.add("Z11-BM1E-0901-49@1604-205");
        	list.add("Z11-BM1E-0901-50@1604-206");
        	list.add("Z11-BM1E-1001-1@1604-207");
        	list.add("Z11-BM1E-1001-2@1604-208");
        	list.add("Z11-BM1E-1001-3@1604-209");
        	list.add("Z11-BM1E-0601-21@1604-21");
        	list.add("Z11-BM1E-1001-4@1604-210");
        	list.add("Z11-BM1E-1001-5@1604-211");
        	list.add("Z11-BM1E-1001-6@1604-212");
        	list.add("Z11-BM1E-1001-7@1604-213");
        	list.add("Z11-BM1E-1001-8@1604-214");
        	list.add("Z11-BM1E-1001-9@1604-215");
        	list.add("Z11-BM1E-1001-10@1604-216");
        	list.add("Z11-BM1E-1001-11@1604-217");
        	list.add("Z11-BM1E-1001-12@1604-218");
        	list.add("Z11-BM1E-1001-13@1604-219");
        	list.add("Z11-BM1E-0601-22@1604-22");
        	list.add("Z11-BM1E-1001-14@1604-220");
        	list.add("Z11-BM1E-1001-15@1604-221");
        	list.add("Z11-BM1E-1001-16@1604-222");
        	list.add("Z11-BM1E-1001-17@1604-223");
        	list.add("Z11-BM1E-1001-18@1604-224");
        	list.add("Z11-BM1E-1001-19@1604-225");
        	list.add("Z11-BM1E-1001-20@1604-226");
        	list.add("Z11-BM1E-1001-21@1604-227");
        	list.add("Z11-BM1E-1001-22@1604-228");
        	list.add("Z11-BM1E-1001-23@1604-229");
        	list.add("Z11-BM1E-0601-23@1604-23");
        	list.add("Z11-BM1E-1001-24@1604-230");
        	list.add("Z11-BM1E-1001-25@1604-231");
        	list.add("Z11-BM1E-1001-26@1604-232");
        	list.add("Z11-BM1E-1001-27@1604-233");
        	list.add("Z11-BM1E-1001-28@1604-234");
        	list.add("Z11-BM1E-1001-29@1604-235");
        	list.add("Z11-BM1E-1001-30@1604-236");
        	list.add("Z11-BM1E-1001-31@1604-237");
        	list.add("Z11-BM1E-1001-32@1604-238");
        	list.add("Z11-BM1E-1001-33@1604-239");
        	list.add("Z11-BM1E-0601-24@1604-24");
        	list.add("Z11-BM1E-1001-34@1604-240");
        	list.add("Z11-BM1E-1001-35@1604-241");
        	list.add("Z11-BM1E-1001-36@1604-242");
        	list.add("Z11-BM1E-1001-37@1604-243");
        	list.add("Z11-BM1E-1001-38@1604-244");
        	list.add("Z11-BM1E-1001-39@1604-245");
        	list.add("Z11-BM1E-1001-40@1604-246");
        	list.add("Z11-BM1E-1001-41@1604-247");
        	list.add("Z11-BM1E-1001-42@1604-248");
        	list.add("Z11-BM1E-1001-43@1604-249");
        	list.add("Z11-BM1E-0601-25@1604-25");
        	list.add("Z11-BM1E-1001-44@1604-250");
        	list.add("Z11-BM1E-1001-45@1604-251");
        	list.add("Z11-BM1E-1001-46@1604-252");
        	list.add("Z11-BM1E-1001-47@1604-253");
        	list.add("Z11-BM1E-1101-1@1604-254");
        	list.add("Z11-BM1E-1101-2@1604-255");
        	list.add("Z11-BM1E-1101-3@1604-256");
        	list.add("Z11-BM1E-1101-4@1604-257");
        	list.add("Z11-BM1E-1101-5@1604-258");
        	list.add("Z11-BM1E-1101-6@1604-259");
        	list.add("Z11-BM1E-0601-26@1604-26");
        	list.add("Z11-BM1E-1101-7@1604-260");
        	list.add("Z11-BM1E-1101-8@1604-261");
        	list.add("Z11-BM1E-1101-9@1604-262");
        	list.add("Z11-BM1E-1101-10@1604-263");
        	list.add("Z11-BM1E-1101-11@1604-264");
        	list.add("Z11-BM1E-1101-12@1604-265");
        	list.add("Z11-BM1E-1101-13@1604-266");
        	list.add("Z11-BM1E-1101-14@1604-267");
        	list.add("Z11-BM1E-1101-15@1604-268");
        	list.add("Z11-BM1E-1101-16@1604-269");
        	list.add("Z11-BM1E-0601-27@1604-27");
        	list.add("Z11-BM1E-1101-17@1604-270");
        	list.add("Z11-BM1E-1101-18@1604-271");
        	list.add("Z11-BM1E-1101-19@1604-272");
        	list.add("Z11-BM1E-1101-20@1604-273");
        	list.add("Z11-BM1E-1101-21@1604-274");
        	list.add("Z11-BM1E-1101-22@1604-275");
        	list.add("Z11-BM1E-1101-23@1604-276");
        	list.add("Z11-BM1E-1101-24@1604-277");
        	list.add("Z11-BM1E-1101-25@1604-278");
        	list.add("Z11-BM1E-1101-26@1604-279");
        	list.add("Z11-BM1E-0601-28@1604-28");
        	list.add("Z11-BM1E-1101-27@1604-280");
        	list.add("Z11-BM1E-1101-28@1604-281");
        	list.add("Z11-BM1E-1101-29@1604-282");
        	list.add("Z11-BM1E-1101-30@1604-283");
        	list.add("Z11-BM1E-1101-31@1604-284");
        	list.add("Z11-BM1E-1101-32@1604-285");
        	list.add("Z11-BM1E-1101-33@1604-286");
        	list.add("Z11-BM1E-1101-34@1604-287");
        	list.add("Z11-BM1E-1101-35@1604-288");
        	list.add("Z11-BM1E-1101-36@1604-289");
        	list.add("Z11-BM1E-0601-29@1604-29");
        	list.add("Z11-BM1E-1101-37@1604-290");
        	list.add("Z11-BM1E-1101-38@1604-291");
        	list.add("Z11-BM1E-1101-39@1604-292");
        	list.add("Z11-BM1E-1101-40@1604-293");
        	list.add("Z11-BM1E-1101-41@1604-294");
        	list.add("Z11-BM1E-1101-42@1604-295");
        	list.add("Z11-BM1E-1101-43@1604-296");
        	list.add("Z11-BM1E-1101-44@1604-297");
        	list.add("Z11-BM1E-1101-45@1604-298");
        	list.add("Z11-BM1E-1101-46@1604-299");
        	list.add("Z11-BM1E-0601-3@1604-3");
        	list.add("Z11-BM1E-0601-30@1604-30");
        	list.add("Z11-BM1E-1101-47@1604-300");
        	list.add("Z11-BM1E-1101-48@1604-301");
        	list.add("Z11-BM1E-1101-49@1604-302");
        	list.add("Z11-BM1E-1101-50@1604-303");
        	list.add("Z11-BM1E-1101-51@1604-304");
        	list.add("Z11-BM1E-1101-52@1604-305");
        	list.add("Z11-BM1E-1201-1@1604-306");
        	list.add("Z11-BM1E-1201-2@1604-307");
        	list.add("Z11-BM1E-1201-3@1604-308");
        	list.add("Z11-BM1E-1201-4@1604-309");
        	list.add("Z11-BM1E-0601-31@1604-31");
        	list.add("Z11-BM1E-1201-5@1604-310");
        	list.add("Z11-BM1E-1201-6@1604-311");
        	list.add("Z11-BM1E-1201-7@1604-312");
        	list.add("Z11-BM1E-1201-8@1604-313");
        	list.add("Z11-BM1E-1201-9@1604-314");
        	list.add("Z11-BM1E-1201-10@1604-315");
        	list.add("Z11-BM1E-1201-11@1604-316");
        	list.add("Z11-BM1E-1201-12@1604-317");
        	list.add("Z11-BM1E-1201-13@1604-318");
        	list.add("Z11-BM1E-1201-14@1604-319");
        	list.add("Z11-BM1E-0601-32@1604-32");
        	list.add("Z11-BM1E-1201-15@1604-320");
        	list.add("Z11-BM1E-1201-16@1604-321");
        	list.add("Z11-BM1E-1201-17@1604-322");
        	list.add("Z11-BM1E-1201-18@1604-323");
        	list.add("Z11-BM1E-1201-19@1604-324");
        	list.add("Z11-BM1E-1201-20@1604-325");
        	list.add("Z11-BM1E-1201-21@1604-326");
        	list.add("Z11-BM1E-1201-22@1604-327");
        	list.add("Z11-BM1E-1201-23@1604-328");
        	list.add("Z11-BM1E-1201-24@1604-329");
        	list.add("Z11-BM1E-0601-33@1604-33");
        	list.add("Z11-BM1E-1201-25@1604-330");
        	list.add("Z11-BM1E-1201-26@1604-331");
        	list.add("Z11-BM1E-1201-27@1604-332");
        	list.add("Z11-BM1E-1201-28@1604-333");
        	list.add("Z11-BM1E-1201-29@1604-334");
        	list.add("Z11-BM1E-1201-30@1604-335");
        	list.add("Z11-BM1E-1201-31@1604-336");
        	list.add("Z11-BM1E-1201-32@1604-337");
        	list.add("Z11-BM1E-1201-33@1604-338");
        	list.add("Z11-BM1E-1201-34@1604-339");
        	list.add("Z11-BM1E-0601-34@1604-34");
        	list.add("Z11-BM1E-1201-35@1604-340");
        	list.add("Z11-BM1E-1201-36@1604-341");
        	list.add("Z11-BM1E-1201-37@1604-342");
        	list.add("Z11-BM1E-1201-38@1604-343");
        	list.add("Z11-BM1E-1201-39@1604-344");
        	list.add("Z11-BM1E-1201-40@1604-345");
        	list.add("Z11-BM1E-1201-41@1604-346");
        	list.add("Z11-BM1E-1201-42@1604-347");
        	list.add("Z11-BM1E-1201-43@1604-348");
        	list.add("Z11-BM1E-1201-44@1604-349");
        	list.add("Z11-BM1E-0601-35@1604-35");
        	list.add("Z11-BM1E-1201-45@1604-350");
        	list.add("Z11-BM1E-1201-46@1604-351");
        	list.add("Z11-BM1E-1201-47@1604-352");
        	list.add("Z11-BM1E-1201-48@1604-353");
        	list.add("Z11-BM1E-1201-49@1604-354");
        	list.add("Z11-BM1E-1201-50@1604-355");
        	list.add("Z11-BM1E-1201-51@1604-356");
        	list.add("Z11-BM1E-1201-52@1604-357");
        	list.add("Z11-BM1E-1201-53@1604-358");
        	list.add("Z11-BM1E-1201-54@1604-359");
        	list.add("Z11-BM1E-0601-36@1604-36");
        	list.add("Z11-BM1E-1301-5@1604-360");
        	list.add("Z11-BM1E-1301-6@1604-361");
        	list.add("Z11-BM1E-1301-7@1604-362");
        	list.add("Z11-BM1E-1301-8@1604-363");
        	list.add("Z11-BM1E-1301-9@1604-364");
        	list.add("Z11-BM1E-1301-10@1604-365");
        	list.add("Z11-BM1E-1301-11@1604-366");
        	list.add("Z11-BM1E-1301-12@1604-367");
        	list.add("Z11-BM1E-1301-13@1604-368");
        	list.add("Z11-BM1E-1301-14@1604-369");
        	list.add("Z11-BM1E-0601-37@1604-37");
        	list.add("Z11-BM1E-1301-15@1604-370");
        	list.add("Z11-BM1E-1301-16@1604-371");
        	list.add("Z11-BM1E-1301-17@1604-372");
        	list.add("Z11-BM1E-1301-18@1604-373");
        	list.add("Z11-BM1E-1301-19@1604-374");
        	list.add("Z11-BM1E-1301-20@1604-375");
        	list.add("Z11-BM1E-1301-21@1604-376");
        	list.add("Z11-BM1E-1301-22@1604-377");
        	list.add("Z11-BM1E-1301-23@1604-378");
        	list.add("Z11-BM1E-1301-24@1604-379");
        	list.add("Z11-BM1E-0601-38@1604-38");
        	list.add("Z11-BM1E-1301-25@1604-380");
        	list.add("Z11-BM1E-1301-26@1604-381");
        	list.add("Z11-BM1E-1301-27@1604-382");
        	list.add("Z11-BM1E-1301-28@1604-383");
        	list.add("Z11-BM1E-1301-29@1604-384");
        	list.add("Z11-BM1E-1301-30@1604-385");
        	list.add("Z11-BM1E-1301-31@1604-386");
        	list.add("Z11-BM1E-1301-32@1604-387");
        	list.add("Z11-BM1E-1301-33@1604-388");
        	list.add("Z11-BM1E-1301-34@1604-389");
        	list.add("Z11-BM1E-0601-39@1604-39");
        	list.add("Z11-BM1E-1301-35@1604-390");
        	list.add("Z11-BM1E-1301-36@1604-391");
        	list.add("Z11-BM1E-1301-37@1604-392");
        	list.add("Z11-BM1E-1301-38@1604-393");
        	list.add("Z11-BM1E-1301-39@1604-394");
        	list.add("Z11-BM1E-1301-40@1604-395");
        	list.add("Z11-BM1E-1301-41@1604-396");
        	list.add("Z11-BM1E-1301-42@1604-397");
        	list.add("Z11-BM1E-1301-43@1604-398");
        	list.add("Z11-BM1E-1301-44@1604-399");
        	list.add("Z11-BM1E-0601-4@1604-4");
        	list.add("Z11-BM1E-0601-40@1604-40");
        	list.add("Z11-BM1E-1301-45@1604-400");
        	list.add("Z11-BM1E-1301-46@1604-401");
        	list.add("Z11-BM1E-1301-47@1604-402");
        	list.add("Z11-BM1E-1301-48@1604-403");
        	list.add("Z11-BM1E-1301-49@1604-404");
        	list.add("Z11-BM1E-1301-50@1604-405");
        	list.add("Z11-BM1E-1301-51@1604-406");
        	list.add("Z11-BM1E-1301-52@1604-407");
        	list.add("Z11-BM1E-1301-53@1604-408");
        	list.add("Z11-BM1E-1301-54@1604-409");
        	list.add("Z11-BM1E-0601-41@1604-41");
        	list.add("Z11-BM1E-0601-42@1604-42");
        	list.add("Z11-BM1E-0601-43@1604-43");
        	list.add("Z11-BM1E-0601-44@1604-44");
        	list.add("Z11-BM1E-0601-45@1604-45");
        	list.add("Z11-BM1E-0601-46@1604-46");
        	list.add("Z11-BM1E-0601-47@1604-47");
        	list.add("Z11-BM1E-0601-48@1604-48");
        	list.add("Z11-BM1E-0601-49@1604-49");
        	list.add("Z11-BM1E-0601-5@1604-5");
        	list.add("Z11-BM1E-0601-50@1604-50");
        	list.add("Z11-BM1E-0601-51@1604-51");
        	list.add("Z11-BM1E-0601-52@1604-52");
        	list.add("Z11-BM1E-0601-53@1604-53");
        	list.add("Z11-BM1E-0701-1@1604-54");
        	list.add("Z11-BM1E-0701-2@1604-55");
        	list.add("Z11-BM1E-0701-3@1604-56");
        	list.add("Z11-BM1E-0701-4@1604-57");
        	list.add("Z11-BM1E-0701-5@1604-58");
        	list.add("Z11-BM1E-0701-6@1604-59");
        	list.add("Z11-BM1E-0601-6@1604-6");
        	list.add("Z11-BM1E-0701-7@1604-60");
        	list.add("Z11-BM1E-0701-8@1604-61");
        	list.add("Z11-BM1E-0701-9@1604-62");
        	list.add("Z11-BM1E-0701-10@1604-63");
        	list.add("Z11-BM1E-0701-11@1604-64");
        	list.add("Z11-BM1E-0701-12@1604-65");
        	list.add("Z11-BM1E-0701-13@1604-66");
        	list.add("Z11-BM1E-0701-14@1604-67");
        	list.add("Z11-BM1E-0701-15@1604-68");
        	list.add("Z11-BM1E-0701-16@1604-69");
        	list.add("Z11-BM1E-0601-7@1604-7");
        	list.add("Z11-BM1E-0701-17@1604-70");
        	list.add("Z11-BM1E-0701-18@1604-71");
        	list.add("Z11-BM1E-0701-19@1604-72");
        	list.add("Z11-BM1E-0701-20@1604-73");
        	list.add("Z11-BM1E-0701-21@1604-74");
        	list.add("Z11-BM1E-0701-22@1604-75");
        	list.add("Z11-BM1E-0701-23@1604-76");
        	list.add("Z11-BM1E-0701-24@1604-77");
        	list.add("Z11-BM1E-0701-25@1604-78");
        	list.add("Z11-BM1E-0701-26@1604-79");
        	list.add("Z11-BM1E-0601-8@1604-8");
        	list.add("Z11-BM1E-0701-27@1604-80");
        	list.add("Z11-BM1E-0701-28@1604-81");
        	list.add("Z11-BM1E-0701-29@1604-82");
        	list.add("Z11-BM1E-0701-30@1604-83");
        	list.add("Z11-BM1E-0701-31@1604-84");
        	list.add("Z11-BM1E-0701-32@1604-85");
        	list.add("Z11-BM1E-0701-33@1604-86");
        	list.add("Z11-BM1E-0701-34@1604-87");
        	list.add("Z11-BM1E-0701-35@1604-88");
        	list.add("Z11-BM1E-0701-36@1604-89");
        	list.add("Z11-BM1E-0601-9@1604-9");
        	list.add("Z11-BM1E-0701-37@1604-90");
        	list.add("Z11-BM1E-0701-38@1604-91");
        	list.add("Z11-BM1E-0701-39@1604-92");
        	list.add("Z11-BM1E-0701-40@1604-93");
        	list.add("Z11-BM1E-0701-41@1604-94");
        	list.add("Z11-BM1E-0701-42@1604-95");
        	list.add("Z11-BM1E-0701-43@1604-96");
        	list.add("Z11-BM1E-0701-44@1604-97");
        	list.add("Z11-BM1E-0701-45@1604-98");
        	list.add("Z11-BM1E-0701-46@1604-99");
        	list.add("Z12-BM1E-2701-1@1605-1");
        	list.add("Z12-BM1E-2701-10@1605-10");
        	list.add("Z12-BM1E-2901-1@1605-100");
        	list.add("Z12-BM1E-2901-2@1605-101");
        	list.add("Z12-BM1E-2901-3@1605-102");
        	list.add("Z12-BM1E-2901-4@1605-103");
        	list.add("Z12-BM1E-2901-5@1605-104");
        	list.add("Z12-BM1E-2901-6@1605-105");
        	list.add("Z12-BM1E-2901-7@1605-106");
        	list.add("Z12-BM1E-2901-8@1605-107");
        	list.add("Z12-BM1E-2901-9@1605-108");
        	list.add("Z12-BM1E-2901-10@1605-109");
        	list.add("Z12-BM1E-2701-11@1605-11");
        	list.add("Z12-BM1E-2901-11@1605-110");
        	list.add("Z12-BM1E-2901-12@1605-111");
        	list.add("Z12-BM1E-2901-13@1605-112");
        	list.add("Z12-BM1E-2901-14@1605-113");
        	list.add("Z12-BM1E-2901-15@1605-114");
        	list.add("Z12-BM1E-2901-16@1605-115");
        	list.add("Z12-BM1E-2901-17@1605-116");
        	list.add("Z12-BM1E-2901-18@1605-117");
        	list.add("Z12-BM1E-2901-19@1605-118");
        	list.add("Z12-BM1E-2901-20@1605-119");
        	list.add("Z12-BM1E-2701-12@1605-12");
        	list.add("Z12-BM1E-2901-21@1605-120");
        	list.add("Z12-BM1E-2901-22@1605-121");
        	list.add("Z12-BM1E-2901-23@1605-122");
        	list.add("Z12-BM1E-2901-24@1605-123");
        	list.add("Z12-BM1E-2901-25@1605-124");
        	list.add("Z12-BM1E-2901-26@1605-125");
        	list.add("Z12-BM1E-2901-27@1605-126");
        	list.add("Z12-BM1E-2901-28@1605-127");
        	list.add("Z12-BM1E-2901-29@1605-128");
        	list.add("Z12-BM1E-2901-30@1605-129");
        	list.add("Z12-BM1E-2701-13@1605-13");
        	list.add("Z12-BM1E-2901-31@1605-130");
        	list.add("Z12-BM1E-2901-32@1605-131");
        	list.add("Z12-BM1E-2901-33@1605-132");
        	list.add("Z12-BM1E-2901-34@1605-133");
        	list.add("Z12-BM1E-2901-35@1605-134");
        	list.add("Z12-BM1E-2901-36@1605-135");
        	list.add("Z12-BM1E-2901-37@1605-136");
        	list.add("Z12-BM1E-2901-38@1605-137");
        	list.add("Z12-BM1E-2901-39@1605-138");
        	list.add("Z12-BM1E-2901-40@1605-139");
        	list.add("Z12-BM1E-2701-14@1605-14");
        	list.add("Z12-BM1E-2901-41@1605-140");
        	list.add("Z12-BM1E-2901-42@1605-141");
        	list.add("Z12-BM1E-2901-43@1605-142");
        	list.add("Z12-BM1E-2901-44@1605-143");
        	list.add("Z12-BM1E-2901-45@1605-144");
        	list.add("Z12-BM1E-2901-46@1605-145");
        	list.add("Z12-BM1E-2901-47@1605-146");
        	list.add("Z12-BM1E-2901-48@1605-147");
        	list.add("Z12-BM1E-2901-49@1605-148");
        	list.add("Z12-BM1E-2901-50@1605-149");
        	list.add("Z12-BM1E-2701-15@1605-15");
        	list.add("Z12-BM1E-2901-51@1605-150");
        	list.add("Z12-BM1E-2901-52@1605-151");
        	list.add("Z12-BM1E-2901-53@1605-152");
        	list.add("Z12-BM1E-3001-1@1605-153");
        	list.add("Z12-BM1E-3001-2@1605-154");
        	list.add("Z12-BM1E-3001-3@1605-155");
        	list.add("Z12-BM1E-3001-4@1605-156");
        	list.add("Z12-BM1E-3001-5@1605-157");
        	list.add("Z12-BM1E-3001-6@1605-158");
        	list.add("Z12-BM1E-3001-7@1605-159");
        	list.add("Z12-BM1E-2701-16@1605-16");
        	list.add("Z12-BM1E-3001-8@1605-160");
        	list.add("Z12-BM1E-3001-9@1605-161");
        	list.add("Z12-BM1E-3001-10@1605-162");
        	list.add("Z12-BM1E-3001-11@1605-163");
        	list.add("Z12-BM1E-3001-12@1605-164");
        	list.add("Z12-BM1E-3001-13@1605-165");
        	list.add("Z12-BM1E-3001-14@1605-166");
        	list.add("Z12-BM1E-3001-15@1605-167");
        	list.add("Z12-BM1E-3001-16@1605-168");
        	list.add("Z12-BM1E-3001-17@1605-169");
        	list.add("Z12-BM1E-2701-17@1605-17");
        	list.add("Z12-BM1E-3001-18@1605-170");
        	list.add("Z12-BM1E-3001-19@1605-171");
        	list.add("Z12-BM1E-3001-20@1605-172");
        	list.add("Z12-BM1E-3001-21@1605-173");
        	list.add("Z12-BM1E-3001-22@1605-174");
        	list.add("Z12-BM1E-3001-23@1605-175");
        	list.add("Z12-BM1E-3001-24@1605-176");
        	list.add("Z12-BM1E-3001-25@1605-177");
        	list.add("Z12-BM1E-3001-26@1605-178");
        	list.add("Z12-BM1E-3001-27@1605-179");
        	list.add("Z12-BM1E-2701-18@1605-18");
        	list.add("Z12-BM1E-3001-28@1605-180");
        	list.add("Z12-BM1E-3001-29@1605-181");
        	list.add("Z12-BM1E-3001-30@1605-182");
        	list.add("Z12-BM1E-3001-31@1605-183");
        	list.add("Z12-BM1E-3001-32@1605-184");
        	list.add("Z12-BM1E-3001-33@1605-185");
        	list.add("Z12-BM1E-3001-34@1605-186");
        	list.add("Z12-BM1E-3001-35@1605-187");
        	list.add("Z12-BM1E-3001-36@1605-188");
        	list.add("Z12-BM1E-3001-37@1605-189");
        	list.add("Z12-BM1E-2701-19@1605-19");
        	list.add("Z12-BM1E-3001-38@1605-190");
        	list.add("Z12-BM1E-3001-39@1605-191");
        	list.add("Z12-BM1E-3001-40@1605-192");
        	list.add("Z12-BM1E-3001-41@1605-193");
        	list.add("Z12-BM1E-3001-42@1605-194");
        	list.add("Z12-BM1E-3001-43@1605-195");
        	list.add("Z12-BM1E-3001-44@1605-196");
        	list.add("Z12-BM1E-3001-45@1605-197");
        	list.add("Z12-BM1E-3001-46@1605-198");
        	list.add("Z12-BM1E-3001-47@1605-199");
        	list.add("Z12-BM1E-2701-2@1605-2");
        	list.add("Z12-BM1E-2701-20@1605-20");
        	list.add("Z12-BM1E-3001-48@1605-200");
        	list.add("Z12-BM1E-3001-49@1605-201");
        	list.add("Z12-BM1E-3001-50@1605-202");
        	list.add("Z12-BM1E-3001-51@1605-203");
        	list.add("Z12-BM1E-3001-52@1605-204");
        	list.add("Z12-BM1E-3001-53@1605-205");
        	list.add("Z12-BM1E-3001-54@1605-206");
        	list.add("Z12-BM1E-3001-55@1605-207");
        	list.add("Z12-BM1E-3001-56@1605-208");
        	list.add("Z12-BM1E-3001-57@1605-209");
        	list.add("Z12-BM1E-2701-21@1605-21");
        	list.add("Z12-BM1E-3001-58@1605-210");
        	list.add("Z12-BM1E-3001-59@1605-211");
        	list.add("Z12-BM1E-3001-60@1605-212");
        	list.add("Z12-BM1E-3101-1@1605-213");
        	list.add("Z12-BM1E-3101-2@1605-214");
        	list.add("Z12-BM1E-3101-3@1605-215");
        	list.add("Z12-BM1E-3101-4@1605-216");
        	list.add("Z12-BM1E-3101-5@1605-217");
        	list.add("Z12-BM1E-3101-6@1605-218");
        	list.add("Z12-BM1E-3101-7@1605-219");
        	list.add("Z12-BM1E-2701-22@1605-22");
        	list.add("Z12-BM1E-3101-8@1605-220");
        	list.add("Z12-BM1E-3101-9@1605-221");
        	list.add("Z12-BM1E-3101-10@1605-222");
        	list.add("Z12-BM1E-3101-11@1605-223");
        	list.add("Z12-BM1E-3101-12@1605-224");
        	list.add("Z12-BM1E-3101-13@1605-225");
        	list.add("Z12-BM1E-3101-14@1605-226");
        	list.add("Z12-BM1E-3101-15@1605-227");
        	list.add("Z12-BM1E-3101-16@1605-228");
        	list.add("Z12-BM1E-3101-17@1605-229");
        	list.add("Z12-BM1E-2701-23@1605-23");
        	list.add("Z12-BM1E-3101-18@1605-230");
        	list.add("Z12-BM1E-3101-19@1605-231");
        	list.add("Z12-BM1E-3101-20@1605-232");
        	list.add("Z12-BM1E-3101-21@1605-233");
        	list.add("Z12-BM1E-3101-22@1605-234");
        	list.add("Z12-BM1E-3101-23@1605-235");
        	list.add("Z12-BM1E-3101-24@1605-236");
        	list.add("Z12-BM1E-3101-25@1605-237");
        	list.add("Z12-BM1E-2701-24@1605-24");
        	list.add("Z12-BM1E-2701-25@1605-25");
        	list.add("Z12-BM1E-2701-26@1605-26");
        	list.add("Z12-BM1E-2701-27@1605-27");
        	list.add("Z12-BM1E-2701-28@1605-28");
        	list.add("Z12-BM1E-2701-29@1605-29");
        	list.add("Z12-BM1E-2701-3@1605-3");
        	list.add("Z12-BM1E-2701-30@1605-30");
        	list.add("Z12-BM1E-2701-31@1605-31");
        	list.add("Z12-BM1E-2701-32@1605-32");
        	list.add("Z12-BM1E-2701-33@1605-33");
        	list.add("Z12-BM1E-2701-34@1605-34");
        	list.add("Z12-BM1E-2701-35@1605-35");
        	list.add("Z12-BM1E-2701-36@1605-36");
        	list.add("Z12-BM1E-2701-37@1605-37");
        	list.add("Z12-BM1E-2701-38@1605-38");
        	list.add("Z12-BM1E-2701-39@1605-39");
        	list.add("Z12-BM1E-2701-4@1605-4");
        	list.add("Z12-BM1E-2701-40@1605-40");
        	list.add("Z12-BM1E-2701-41@1605-41");
        	list.add("Z12-BM1E-2701-42@1605-42");
        	list.add("Z12-BM1E-2701-43@1605-43");
        	list.add("Z12-BM1E-2701-44@1605-44");
        	list.add("Z12-BM1E-2701-45@1605-45");
        	list.add("Z12-BM1E-2701-46@1605-46");
        	list.add("Z12-BM1E-2701-47@1605-47");
        	list.add("Z12-BM1E-2701-48@1605-48");
        	list.add("Z12-BM1E-2701-49@1605-49");
        	list.add("Z12-BM1E-2701-5@1605-5");
        	list.add("Z12-BM1E-2701-50@1605-50");
        	list.add("Z12-BM1E-2701-51@1605-51");
        	list.add("Z12-BM1E-2801-1@1605-52");
        	list.add("Z12-BM1E-2801-2@1605-53");
        	list.add("Z12-BM1E-2801-3@1605-54");
        	list.add("Z12-BM1E-2801-4@1605-55");
        	list.add("Z12-BM1E-2801-5@1605-56");
        	list.add("Z12-BM1E-2801-6@1605-57");
        	list.add("Z12-BM1E-2801-7@1605-58");
        	list.add("Z12-BM1E-2801-8@1605-59");
        	list.add("Z12-BM1E-2701-6@1605-6");
        	list.add("Z12-BM1E-2801-9@1605-60");
        	list.add("Z12-BM1E-2801-10@1605-61");
        	list.add("Z12-BM1E-2801-11@1605-62");
        	list.add("Z12-BM1E-2801-12@1605-63");
        	list.add("Z12-BM1E-2801-13@1605-64");
        	list.add("Z12-BM1E-2801-14@1605-65");
        	list.add("Z12-BM1E-2801-15@1605-66");
        	list.add("Z12-BM1E-2801-16@1605-67");
        	list.add("Z12-BM1E-2801-17@1605-68");
        	list.add("Z12-BM1E-2801-18@1605-69");
        	list.add("Z12-BM1E-2701-7@1605-7");
        	list.add("Z12-BM1E-2801-19@1605-70");
        	list.add("Z12-BM1E-2801-20@1605-71");
        	list.add("Z12-BM1E-2801-21@1605-72");
        	list.add("Z12-BM1E-2801-22@1605-73");
        	list.add("Z12-BM1E-2801-23@1605-74");
        	list.add("Z12-BM1E-2801-24@1605-75");
        	list.add("Z12-BM1E-2801-25@1605-76");
        	list.add("Z12-BM1E-2801-26@1605-77");
        	list.add("Z12-BM1E-2801-27@1605-78");
        	list.add("Z12-BM1E-2801-28@1605-79");
        	list.add("Z12-BM1E-2701-8@1605-8");
        	list.add("Z12-BM1E-2801-29@1605-80");
        	list.add("Z12-BM1E-2801-30@1605-81");
        	list.add("Z12-BM1E-2801-31@1605-82");
        	list.add("Z12-BM1E-2801-32@1605-83");
        	list.add("Z12-BM1E-2801-33@1605-84");
        	list.add("Z12-BM1E-2801-34@1605-85");
        	list.add("Z12-BM1E-2801-35@1605-86");
        	list.add("Z12-BM1E-2801-36@1605-87");
        	list.add("Z12-BM1E-2801-37@1605-88");
        	list.add("Z12-BM1E-2801-38@1605-89");
        	list.add("Z12-BM1E-2701-9@1605-9");
        	list.add("Z12-BM1E-2801-39@1605-90");
        	list.add("Z12-BM1E-2801-40@1605-91");
        	list.add("Z12-BM1E-2801-41@1605-92");
        	list.add("Z12-BM1E-2801-42@1605-93");
        	list.add("Z12-BM1E-2801-43@1605-94");
        	list.add("Z12-BM1E-2801-44@1605-95");
        	list.add("Z12-BM1E-2801-45@1605-96");
        	list.add("Z12-BM1E-2801-46@1605-97");
        	list.add("Z12-BM1E-2801-47@1605-98");
        	list.add("Z12-BM1E-2801-48@1605-99");
        	list.add("1607-BM1E-0101-1@1607-1");
        	list.add("1607-BM1E-0101-10@1607-10");
        	list.add("1607-BM1E-0101-11@1607-11");
        	list.add("1607-BM1E-0101-12@1607-12");
        	list.add("1607-BM1E-0101-13@1607-13");
        	list.add("1607-BM1E-0101-14@1607-14");
        	list.add("1607-BM1E-0101-15@1607-15");
        	list.add("1607-BM1E-0101-16@1607-16");
        	list.add("1607-BM1E-0101-2@1607-2");
        	list.add("1607-BM1E-0101-3@1607-3");
        	list.add("1607-BM1E-0101-4@1607-4");
        	list.add("1607-BM1E-0101-5@1607-5");
        	list.add("1607-BM1E-0101-6@1607-6");
        	list.add("1607-BM1E-0101-7@1607-7");
        	list.add("1607-BM1E-0101-8@1607-8");
        	list.add("1607-BM1E-0101-9@1607-9");
        	list.add("Z17-BM1E-3201-1@1701-1");
        	list.add("Z17-BM1E-3201-10@1701-10");
        	list.add("Z17-BM1E-3301-47@1701-100");
        	list.add("Z17-BM1E-3301-48@1701-101");
        	list.add("Z17-BM1E-3301-49@1701-102");
        	list.add("Z17-BM1E-3301-50@1701-103");
        	list.add("Z17-BM1E-3301-51@1701-104");
        	list.add("Z17-BM1E-3301-52@1701-105");
        	list.add("Z17-BM1E-3301-53@1701-106");
        	list.add("Z17-BM1E-3301-54@1701-107");
        	list.add("Z17-BM1E-3301-55@1701-108");
        	list.add("Z17-BM1E-3301-56@1701-109");
        	list.add("Z17-BM1E-3201-11@1701-11");
        	list.add("Z17-BM1E-3301-57@1701-110");
        	list.add("Z17-BM1E-3401-1@1701-111");
        	list.add("Z17-BM1E-3401-2@1701-112");
        	list.add("Z17-BM1E-3401-3@1701-113");
        	list.add("Z17-BM1E-3401-4@1701-114");
        	list.add("Z17-BM1E-3401-5@1701-115");
        	list.add("Z17-BM1E-3401-6@1701-116");
        	list.add("Z17-BM1E-3401-7@1701-117");
        	list.add("Z17-BM1E-3401-8@1701-118");
        	list.add("Z17-BM1E-3401-9@1701-119");
        	list.add("Z17-BM1E-3201-12@1701-12");
        	list.add("Z17-BM1E-3401-10@1701-120");
        	list.add("Z17-BM1E-3401-11@1701-121");
        	list.add("Z17-BM1E-3401-12@1701-122");
        	list.add("Z17-BM1E-3401-13@1701-123");
        	list.add("Z17-BM1E-3401-14@1701-124");
        	list.add("Z17-BM1E-3401-15@1701-125");
        	list.add("Z17-BM1E-3401-16@1701-126");
        	list.add("Z17-BM1E-3401-17@1701-127");
        	list.add("Z17-BM1E-3401-18@1701-128");
        	list.add("Z17-BM1E-3401-19@1701-129");
        	list.add("Z17-BM1E-3201-13@1701-13");
        	list.add("Z17-BM1E-3401-20@1701-130");
        	list.add("Z17-BM1E-3401-21@1701-131");
        	list.add("Z17-BM1E-3401-22@1701-132");
        	list.add("Z17-BM1E-3401-23@1701-133");
        	list.add("Z17-BM1E-3401-24@1701-134");
        	list.add("Z17-BM1E-3401-25@1701-135");
        	list.add("Z17-BM1E-3401-26@1701-136");
        	list.add("Z17-BM1E-3401-27@1701-137");
        	list.add("Z17-BM1E-3401-28@1701-138");
        	list.add("Z17-BM1E-3401-29@1701-139");
        	list.add("Z17-BM1E-3201-14@1701-14");
        	list.add("Z17-BM1E-3401-30@1701-140");
        	list.add("Z17-BM1E-3401-31@1701-141");
        	list.add("Z17-BM1E-3401-32@1701-142");
        	list.add("Z17-BM1E-3401-33@1701-143");
        	list.add("Z17-BM1E-3401-34@1701-144");
        	list.add("Z17-BM1E-3401-35@1701-145");
        	list.add("Z17-BM1E-3401-36@1701-146");
        	list.add("Z17-BM1E-3401-37@1701-147");
        	list.add("Z17-BM1E-3401-38@1701-148");
        	list.add("Z17-BM1E-3401-39@1701-149");
        	list.add("Z17-BM1E-3201-15@1701-15");
        	list.add("Z17-BM1E-3401-40@1701-150");
        	list.add("Z17-BM1E-3401-41@1701-151");
        	list.add("Z17-BM1E-3401-42@1701-152");
        	list.add("Z17-BM1E-3401-43@1701-153");
        	list.add("Z17-BM1E-3401-44@1701-154");
        	list.add("Z17-BM1E-3401-45@1701-155");
        	list.add("Z17-BM1E-3401-46@1701-156");
        	list.add("Z17-BM1E-3401-47@1701-157");
        	list.add("Z17-BM1E-3401-48@1701-158");
        	list.add("Z17-BM1E-3401-49@1701-159");
        	list.add("Z17-BM1E-3201-16@1701-16");
        	list.add("Z17-BM1E-3401-50@1701-160");
        	list.add("Z17-BM1E-3401-51@1701-161");
        	list.add("Z17-BM1E-3401-52@1701-162");
        	list.add("Z17-BM1E-3401-53@1701-163");
        	list.add("Z17-BM1E-3101-26@1701-165");
        	list.add("Z17-BM1E-3101-27@1701-166");
        	list.add("Z17-BM1E-3101-28@1701-167");
        	list.add("Z17-BM1E-3101-29@1701-168");
        	list.add("Z17-BM1E-3101-30@1701-169");
        	list.add("Z17-BM1E-3201-17@1701-17");
        	list.add("Z17-BM1E-3101-31@1701-170");
        	list.add("Z17-BM1E-3101-32@1701-171");
        	list.add("Z17-BM1E-3101-33@1701-172");
        	list.add("Z17-BM1E-3101-34@1701-173");
        	list.add("Z17-BM1E-3101-35@1701-174");
        	list.add("Z17-BM1E-3101-36@1701-175");
        	list.add("Z17-BM1E-3101-37@1701-176");
        	list.add("Z17-BM1E-3101-38@1701-177");
        	list.add("Z17-BM1E-3101-39@1701-178");
        	list.add("Z17-BM1E-3101-40@1701-179");
        	list.add("Z17-BM1E-3201-18@1701-18");
        	list.add("Z17-BM1E-3101-41@1701-180");
        	list.add("Z17-BM1E-3101-42@1701-181");
        	list.add("Z17-BM1E-3101-43@1701-182");
        	list.add("Z17-BM1E-3101-44@1701-183");
        	list.add("Z17-BM1E-3101-45@1701-184");
        	list.add("Z17-BM1E-3101-46@1701-185");
        	list.add("Z17-BM1E-3101-47@1701-186");
        	list.add("Z17-BM1E-3101-48@1701-187");
        	list.add("Z17-BM1E-3101-49@1701-188");
        	list.add("Z17-BM1E-3101-50@1701-189");
        	list.add("Z17-BM1E-3201-19@1701-19");
        	list.add("Z17-BM1E-3101-51@1701-190");
        	list.add("Z17-BM1E-3201-2@1701-2");
        	list.add("Z17-BM1E-3201-20@1701-20");
        	list.add("Z17-BM1E-3201-21@1701-21");
        	list.add("Z17-BM1E-3201-22@1701-22");
        	list.add("Z17-BM1E-3201-23@1701-23");
        	list.add("Z17-BM1E-3201-24@1701-24");
        	list.add("Z17-BM1E-3201-25@1701-25");
        	list.add("Z17-BM1E-3201-26@1701-26");
        	list.add("Z17-BM1E-3201-27@1701-27");
        	list.add("Z17-BM1E-3201-28@1701-28");
        	list.add("Z17-BM1E-3201-29@1701-29");
        	list.add("Z17-BM1E-3201-3@1701-3");
        	list.add("Z17-BM1E-3201-30@1701-30");
        	list.add("Z17-BM1E-3201-31@1701-31");
        	list.add("Z17-BM1E-3201-32@1701-32");
        	list.add("Z17-BM1E-3201-33@1701-33");
        	list.add("Z17-BM1E-3201-34@1701-34");
        	list.add("Z17-BM1E-3201-35@1701-35");
        	list.add("Z17-BM1E-3201-36@1701-36");
        	list.add("Z17-BM1E-3201-37@1701-37");
        	list.add("Z17-BM1E-3201-38@1701-38");
        	list.add("Z17-BM1E-3201-39@1701-39");
        	list.add("Z17-BM1E-3201-4@1701-4");
        	list.add("Z17-BM1E-3201-40@1701-40");
        	list.add("Z17-BM1E-3201-41@1701-41");
        	list.add("Z17-BM1E-3201-42@1701-42");
        	list.add("Z17-BM1E-3201-43@1701-43");
        	list.add("Z17-BM1E-3201-44@1701-44");
        	list.add("Z17-BM1E-3201-45@1701-45");
        	list.add("Z17-BM1E-3201-46@1701-46");
        	list.add("Z17-BM1E-3201-47@1701-47");
        	list.add("Z17-BM1E-3201-48@1701-48");
        	list.add("Z17-BM1E-3201-49@1701-49");
        	list.add("Z17-BM1E-3201-5@1701-5");
        	list.add("Z17-BM1E-3201-50@1701-50");
        	list.add("Z17-BM1E-3201-51@1701-51");
        	list.add("Z17-BM1E-3201-52@1701-52");
        	list.add("Z17-BM1E-3201-53@1701-53");
        	list.add("Z17-BM1E-3301-1@1701-54");
        	list.add("Z17-BM1E-3301-2@1701-55");
        	list.add("Z17-BM1E-3301-3@1701-56");
        	list.add("Z17-BM1E-3301-4@1701-57");
        	list.add("Z17-BM1E-3301-5@1701-58");
        	list.add("Z17-BM1E-3301-6@1701-59");
        	list.add("Z17-BM1E-3201-6@1701-6");
        	list.add("Z17-BM1E-3301-7@1701-60");
        	list.add("Z17-BM1E-3301-8@1701-61");
        	list.add("Z17-BM1E-3301-9@1701-62");
        	list.add("Z17-BM1E-3301-10@1701-63");
        	list.add("Z17-BM1E-3301-11@1701-64");
        	list.add("Z17-BM1E-3301-12@1701-65");
        	list.add("Z17-BM1E-3301-13@1701-66");
        	list.add("Z17-BM1E-3301-14@1701-67");
        	list.add("Z17-BM1E-3301-15@1701-68");
        	list.add("Z17-BM1E-3301-16@1701-69");
        	list.add("Z17-BM1E-3201-7@1701-7");
        	list.add("Z17-BM1E-3301-17@1701-70");
        	list.add("Z17-BM1E-3301-18@1701-71");
        	list.add("Z17-BM1E-3301-19@1701-72");
        	list.add("Z17-BM1E-3301-20@1701-73");
        	list.add("Z17-BM1E-3301-21@1701-74");
        	list.add("Z17-BM1E-3301-22@1701-75");
        	list.add("Z17-BM1E-3301-23@1701-76");
        	list.add("Z17-BM1E-3301-24@1701-77");
        	list.add("Z17-BM1E-3301-25@1701-78");
        	list.add("Z17-BM1E-3301-26@1701-79");
        	list.add("Z17-BM1E-3201-8@1701-8");
        	list.add("Z17-BM1E-3301-27@1701-80");
        	list.add("Z17-BM1E-3301-28@1701-81");
        	list.add("Z17-BM1E-3301-29@1701-82");
        	list.add("Z17-BM1E-3301-30@1701-83");
        	list.add("Z17-BM1E-3301-31@1701-84");
        	list.add("Z17-BM1E-3301-32@1701-85");
        	list.add("Z17-BM1E-3301-33@1701-86");
        	list.add("Z17-BM1E-3301-34@1701-87");
        	list.add("Z17-BM1E-3301-35@1701-88");
        	list.add("Z17-BM1E-3301-36@1701-89");
        	list.add("Z17-BM1E-3201-9@1701-9");
        	list.add("Z17-BM1E-3301-37@1701-90");
        	list.add("Z17-BM1E-3301-38@1701-91");
        	list.add("Z17-BM1E-3301-39@1701-92");
        	list.add("Z17-BM1E-3301-40@1701-93");
        	list.add("Z17-BM1E-3301-41@1701-94");
        	list.add("Z17-BM1E-3301-42@1701-95");
        	list.add("Z17-BM1E-3301-43@1701-96");
        	list.add("Z17-BM1E-3301-44@1701-97");
        	list.add("Z17-BM1E-3301-45@1701-98");
        	list.add("Z17-BM1E-3301-46@1701-99");
        	list.add("Z18-BM1E-2201-1@1702-1");
        	list.add("Z18-BM1E-2201-10@1702-10");
        	list.add("Z18-BM1E-2401-3@1702-100");
        	list.add("Z18-BM1E-2401-4@1702-101");
        	list.add("Z18-BM1E-2401-5@1702-102");
        	list.add("Z18-BM1E-2401-6@1702-103");
        	list.add("Z18-BM1E-2401-7@1702-104");
        	list.add("Z18-BM1E-2401-8@1702-105");
        	list.add("Z18-BM1E-2401-9@1702-106");
        	list.add("Z18-BM1E-2401-10@1702-107");
        	list.add("Z18-BM1E-2401-11@1702-108");
        	list.add("Z18-BM1E-2401-12@1702-109");
        	list.add("Z18-BM1E-2201-11@1702-11");
        	list.add("Z18-BM1E-2401-13@1702-110");
        	list.add("Z18-BM1E-2401-14@1702-111");
        	list.add("Z18-BM1E-2401-15@1702-112");
        	list.add("Z18-BM1E-2401-16@1702-113");
        	list.add("Z18-BM1E-2401-17@1702-114");
        	list.add("Z18-BM1E-2401-18@1702-115");
        	list.add("Z18-BM1E-2401-19@1702-116");
        	list.add("Z18-BM1E-2401-20@1702-117");
        	list.add("Z18-BM1E-2401-21@1702-118");
        	list.add("Z18-BM1E-2401-22@1702-119");
        	list.add("Z18-BM1E-2201-12@1702-12");
        	list.add("Z18-BM1E-2401-23@1702-120");
        	list.add("Z18-BM1E-2401-24@1702-121");
        	list.add("Z18-BM1E-2401-25@1702-122");
        	list.add("Z18-BM1E-2401-26@1702-123");
        	list.add("Z18-BM1E-2401-27@1702-124");
        	list.add("Z18-BM1E-2401-28@1702-125");
        	list.add("Z18-BM1E-2401-29@1702-126");
        	list.add("Z18-BM1E-2401-30@1702-127");
        	list.add("Z18-BM1E-2401-31@1702-128");
        	list.add("Z18-BM1E-2401-32@1702-129");
        	list.add("Z18-BM1E-2201-13@1702-13");
        	list.add("Z18-BM1E-2401-33@1702-130");
        	list.add("Z18-BM1E-2401-34@1702-131");
        	list.add("Z18-BM1E-2401-35@1702-132");
        	list.add("Z18-BM1E-2401-36@1702-133");
        	list.add("Z18-BM1E-2401-37@1702-134");
        	list.add("Z18-BM1E-2401-38@1702-135");
        	list.add("Z18-BM1E-2401-39@1702-136");
        	list.add("Z18-BM1E-2401-40@1702-137");
        	list.add("Z18-BM1E-2401-41@1702-138");
        	list.add("Z18-BM1E-2401-42@1702-139");
        	list.add("Z18-BM1E-2201-14@1702-14");
        	list.add("Z18-BM1E-2401-43@1702-140");
        	list.add("Z18-BM1E-2401-44@1702-141");
        	list.add("Z18-BM1E-2401-45@1702-142");
        	list.add("Z18-BM1E-2401-46@1702-143");
        	list.add("Z18-BM1E-2401-47@1702-144");
        	list.add("Z18-BM1E-2401-48@1702-145");
        	list.add("Z18-BM1E-2401-49@1702-146");
        	list.add("Z18-BM1E-2401-50@1702-147");
        	list.add("Z18-BM1E-2201-15@1702-15");
        	list.add("Z18-BM1E-2101-1@1702-151");
        	list.add("Z18-BM1E-2101-2@1702-152");
        	list.add("Z18-BM1E-2101-3@1702-153");
        	list.add("Z18-BM1E-2101-4@1702-154");
        	list.add("Z18-BM1E-2101-5@1702-155");
        	list.add("Z18-BM1E-2101-6@1702-156");
        	list.add("Z18-BM1E-2101-7@1702-157");
        	list.add("Z18-BM1E-2101-8@1702-158");
        	list.add("Z18-BM1E-2101-9@1702-159");
        	list.add("Z18-BM1E-2201-16@1702-16");
        	list.add("Z18-BM1E-2101-10@1702-160");
        	list.add("Z18-BM1E-2101-11@1702-161");
        	list.add("Z18-BM1E-2101-12@1702-162");
        	list.add("Z18-BM1E-2101-13@1702-163");
        	list.add("Z18-BM1E-2101-14@1702-164");
        	list.add("Z18-BM1E-2101-15@1702-165");
        	list.add("Z18-BM1E-2101-16@1702-166");
        	list.add("Z18-BM1E-2101-17@1702-167");
        	list.add("Z18-BM1E-2101-18@1702-168");
        	list.add("Z18-BM1E-2101-19@1702-169");
        	list.add("Z18-BM1E-2201-17@1702-17");
        	list.add("Z18-BM1E-2101-20@1702-170");
        	list.add("Z18-BM1E-2101-21@1702-171");
        	list.add("Z18-BM1E-2101-22@1702-172");
        	list.add("Z18-BM1E-2101-23@1702-173");
        	list.add("Z18-BM1E-2101-24@1702-174");
        	list.add("Z18-BM1E-2201-18@1702-18");
        	list.add("Z18-BM1E-2201-19@1702-19");
        	list.add("Z18-BM1E-2201-2@1702-2");
        	list.add("Z18-BM1E-2201-20@1702-20");
        	list.add("Z18-BM1E-2201-21@1702-21");
        	list.add("Z18-BM1E-2201-22@1702-22");
        	list.add("Z18-BM1E-2201-23@1702-23");
        	list.add("Z18-BM1E-2201-24@1702-24");
        	list.add("Z18-BM1E-2201-25@1702-25");
        	list.add("Z18-BM1E-2201-26@1702-26");
        	list.add("Z18-BM1E-2201-27@1702-27");
        	list.add("Z18-BM1E-2201-28@1702-28");
        	list.add("Z18-BM1E-2201-29@1702-29");
        	list.add("Z18-BM1E-2201-3@1702-3");
        	list.add("Z18-BM1E-2201-30@1702-30");
        	list.add("Z18-BM1E-2201-31@1702-31");
        	list.add("Z18-BM1E-2201-32@1702-32");
        	list.add("Z18-BM1E-2201-33@1702-33");
        	list.add("Z18-BM1E-2201-34@1702-34");
        	list.add("Z18-BM1E-2201-35@1702-35");
        	list.add("Z18-BM1E-2201-36@1702-36");
        	list.add("Z18-BM1E-2201-37@1702-37");
        	list.add("Z18-BM1E-2201-38@1702-38");
        	list.add("Z18-BM1E-2201-39@1702-39");
        	list.add("Z18-BM1E-2201-4@1702-4");
        	list.add("Z18-BM1E-2201-40@1702-40");
        	list.add("Z18-BM1E-2201-41@1702-41");
        	list.add("Z18-BM1E-2201-42@1702-42");
        	list.add("Z18-BM1E-2201-43@1702-43");
        	list.add("Z18-BM1E-2201-44@1702-44");
        	list.add("Z18-BM1E-2201-45@1702-45");
        	list.add("Z18-BM1E-2201-46@1702-46");
        	list.add("Z18-BM1E-2201-47@1702-47");
        	list.add("Z18-BM1E-2201-48@1702-48");
        	list.add("Z18-BM1E-2201-49@1702-49");
        	list.add("Z18-BM1E-2201-5@1702-5");
        	list.add("Z18-BM1E-2301-1@1702-50");
        	list.add("Z18-BM1E-2301-2@1702-51");
        	list.add("Z18-BM1E-2301-3@1702-52");
        	list.add("Z18-BM1E-2301-4@1702-53");
        	list.add("Z18-BM1E-2301-5@1702-54");
        	list.add("Z18-BM1E-2301-6@1702-55");
        	list.add("Z18-BM1E-2301-7@1702-56");
        	list.add("Z18-BM1E-2301-8@1702-57");
        	list.add("Z18-BM1E-2301-9@1702-58");
        	list.add("Z18-BM1E-2301-10@1702-59");
        	list.add("Z18-BM1E-2201-6@1702-6");
        	list.add("Z18-BM1E-2301-11@1702-60");
        	list.add("Z18-BM1E-2301-12@1702-61");
        	list.add("Z18-BM1E-2301-13@1702-62");
        	list.add("Z18-BM1E-2301-14@1702-63");
        	list.add("Z18-BM1E-2301-15@1702-64");
        	list.add("Z18-BM1E-2301-16@1702-65");
        	list.add("Z18-BM1E-2301-17@1702-66");
        	list.add("Z18-BM1E-2301-18@1702-67");
        	list.add("Z18-BM1E-2301-19@1702-68");
        	list.add("Z18-BM1E-2301-20@1702-69");
        	list.add("Z18-BM1E-2201-7@1702-7");
        	list.add("Z18-BM1E-2301-21@1702-70");
        	list.add("Z18-BM1E-2301-22@1702-71");
        	list.add("Z18-BM1E-2301-23@1702-72");
        	list.add("Z18-BM1E-2301-24@1702-73");
        	list.add("Z18-BM1E-2301-25@1702-74");
        	list.add("Z18-BM1E-2301-26@1702-75");
        	list.add("Z18-BM1E-2301-27@1702-76");
        	list.add("Z18-BM1E-2301-28@1702-77");
        	list.add("Z18-BM1E-2301-29@1702-78");
        	list.add("Z18-BM1E-2301-30@1702-79");
        	list.add("Z18-BM1E-2201-8@1702-8");
        	list.add("Z18-BM1E-2301-31@1702-80");
        	list.add("Z18-BM1E-2301-32@1702-81");
        	list.add("Z18-BM1E-2301-33@1702-82");
        	list.add("Z18-BM1E-2301-34@1702-83");
        	list.add("Z18-BM1E-2301-35@1702-84");
        	list.add("Z18-BM1E-2301-36@1702-85");
        	list.add("Z18-BM1E-2301-37@1702-86");
        	list.add("Z18-BM1E-2301-38@1702-87");
        	list.add("Z18-BM1E-2301-39@1702-88");
        	list.add("Z18-BM1E-2301-40@1702-89");
        	list.add("Z18-BM1E-2201-9@1702-9");
        	list.add("Z18-BM1E-2301-41@1702-90");
        	list.add("Z18-BM1E-2301-42@1702-91");
        	list.add("Z18-BM1E-2301-43@1702-92");
        	list.add("Z18-BM1E-2301-44@1702-93");
        	list.add("Z18-BM1E-2301-45@1702-94");
        	list.add("Z18-BM1E-2301-46@1702-95");
        	list.add("Z18-BM1E-2301-47@1702-96");
        	list.add("Z18-BM1E-2301-48@1702-97");
        	list.add("Z18-BM1E-2401-1@1702-98");
        	list.add("Z18-BM1E-2401-2@1702-99");
        	list.add("Z22-BM1W-0101-1@1710-1");
        	list.add("Z22-BM1W-0101-10@1710-10");
        	list.add("Z22-BM1W-0101-11@1710-11");
        	list.add("Z22-BM1W-0101-12@1710-12");
        	list.add("Z22-BM1W-0101-13@1710-13");
        	list.add("Z22-BM1W-0101-14@1710-14");
        	list.add("Z22-BM1W-0101-15@1710-15");
        	list.add("Z22-BM1W-0101-16@1710-16");
        	list.add("Z22-BM1W-0101-17@1710-17");
        	list.add("Z22-BM1W-0101-18@1710-18");
        	list.add("Z22-BM1W-0101-19@1710-19");
        	list.add("Z22-BM1W-0101-2@1710-2");
        	list.add("Z22-BM1W-0101-20@1710-20");
        	list.add("Z22-BM1W-0101-21@1710-21");
        	list.add("Z22-BM1W-0101-22@1710-22");
        	list.add("Z22-BM1W-0101-3@1710-3");
        	list.add("Z22-BM1W-0101-4@1710-4");
        	list.add("Z22-BM1W-0101-5@1710-5");
        	list.add("Z22-BM1W-0101-6@1710-6");
        	list.add("Z22-BM1W-0101-7@1710-7");
        	list.add("Z22-BM1W-0101-8@1710-8");
        	list.add("Z22-BM1W-0101-9@1710-9");
        	list.add("Z1-BMB1-1401-125@ZY-1");
        	list.add("Z1-BMB1-1401-134@ZY-10");
        	list.add("Z1-BMB1-1801-224@ZY-100");
        	list.add("Z1-BMB1-1801-225@ZY-101");
        	list.add("Z1-BMB1-1801-226@ZY-102");
        	list.add("Z1-BMB1-1801-227@ZY-103");
        	list.add("Z1-BMB1-1801-228@ZY-104");
        	list.add("Z1-BMB1-1801-229@ZY-105");
        	list.add("Z1-BMB1-1801-230@ZY-106");
        	list.add("Z1-BMB1-1801-231@ZY-107");
        	list.add("Z1-BMB1-1801-232@ZY-108");
        	list.add("Z1-BMB1-1801-233@ZY-109");
        	list.add("Z1-BMB1-1401-135@ZY-11");
        	list.add("Z1-BMB1-1801-234@ZY-110");
        	list.add("Z1-BMB1-1801-235@ZY-111");
        	list.add("Z1-BMB1-1801-236@ZY-112");
        	list.add("Z1-BMB1-1801-237@ZY-113");
        	list.add("Z1-BMB1-1801-238@ZY-114");
        	list.add("Z1-BMB1-1801-239@ZY-115");
        	list.add("Z1-BMB1-1801-240@ZY-116");
        	list.add("Z1-BMB1-1801-241@ZY-117");
        	list.add("Z1-BMB1-1801-242@ZY-118");
        	list.add("Z1-BMB1-1801-243@ZY-119");
        	list.add("Z1-BMB1-1401-136@ZY-12");
        	list.add("Z1-BMB1-1801-244@ZY-120");
        	list.add("Z1-BMB1-1801-245@ZY-121");
        	list.add("Z1-BMB1-1801-246@ZY-122");
        	list.add("Z1-BMB1-1801-247@ZY-123");
        	list.add("Z1-BMB1-1801-248@ZY-124");
        	list.add("Z1-BMB1-1801-249@ZY-125");
        	list.add("Z1-BMB1-1801-250@ZY-126");
        	list.add("Z1-BMB1-1801-251@ZY-127");
        	list.add("Z1-BMB1-1801-252@ZY-128");
        	list.add("Z1-BMB1-1801-253@ZY-129");
        	list.add("Z1-BMB1-1401-137@ZY-13");
        	list.add("Z1-BMB1-1801-254@ZY-130");
        	list.add("Z1-BMB1-1901-255@ZY-131");
        	list.add("Z1-BMB1-1901-256@ZY-132");
        	list.add("Z1-BMB1-1901-257@ZY-133");
        	list.add("Z1-BMB1-1901-258@ZY-134");
        	list.add("Z1-BMB1-1901-259@ZY-135");
        	list.add("Z1-BMB1-1901-260@ZY-136");
        	list.add("Z1-BMB1-1901-261@ZY-137");
        	list.add("Z1-BMB1-1901-262@ZY-138");
        	list.add("Z1-BMB1-1901-263@ZY-139");
        	list.add("Z1-BMB1-1401-138@ZY-14");
        	list.add("Z1-BMB1-1901-264@ZY-140");
        	list.add("Z1-BMB1-1901-265@ZY-141");
        	list.add("Z1-BMB1-1901-266@ZY-142");
        	list.add("Z1-BMB1-1901-267@ZY-143");
        	list.add("Z1-BMB1-1901-268@ZY-144");
        	list.add("Z1-BMB1-1901-269@ZY-145");
        	list.add("Z1-BMB1-1901-270@ZY-146");
        	list.add("Z1-BMB1-1901-271@ZY-147");
        	list.add("Z1-BMB1-1901-272@ZY-148");
        	list.add("Z1-BMB1-1901-273@ZY-149");
        	list.add("Z1-BMB1-1401-139@ZY-15");
        	list.add("Z1-BMB1-1901-274@ZY-150");
        	list.add("Z1-BMB1-1901-275@ZY-151");
        	list.add("Z1-BMB1-1901-276@ZY-152");
        	list.add("Z1-BMB1-1901-277@ZY-153");
        	list.add("Z1-BMB1-1901-278@ZY-154");
        	list.add("Z1-BMB1-1901-279@ZY-155");
        	list.add("Z1-BMB1-2001-280@ZY-156");
        	list.add("Z1-BMB1-2001-281@ZY-157");
        	list.add("Z1-BMB1-2001-282@ZY-158");
        	list.add("Z1-BMB1-2001-283@ZY-159");
        	list.add("Z1-BMB1-1401-140@ZY-16");
        	list.add("Z1-BMB1-2001-284@ZY-160");
        	list.add("Z1-BMB1-2001-285@ZY-161");
        	list.add("Z1-BMB1-2001-286@ZY-162");
        	list.add("Z1-BMB1-2001-287@ZY-163");
        	list.add("Z1-BMB1-2001-288@ZY-164");
        	list.add("Z1-BMB1-2001-289@ZY-165");
        	list.add("Z1-BMB1-2001-290@ZY-166");
        	list.add("Z1-BMB1-2001-291@ZY-167");
        	list.add("Z1-BMB1-2001-292@ZY-168");
        	list.add("Z1-BMB1-2001-293@ZY-169");
        	list.add("Z1-BMB1-1401-141@ZY-17");
        	list.add("Z1-BMB1-2001-294@ZY-170");
        	list.add("Z1-BMB1-2001-295@ZY-171");
        	list.add("Z1-BMB1-2001-296@ZY-172");
        	list.add("Z1-BMB1-2001-297@ZY-173");
        	list.add("Z1-BMB1-2001-298@ZY-174");
        	list.add("Z1-BMB1-2001-299@ZY-175");
        	list.add("Z1-BMB1-2001-300@ZY-176");
        	list.add("Z1-BMB1-2001-301@ZY-177");
        	list.add("Z1-BMB1-2001-302@ZY-178");
        	list.add("Z1-BMB1-2001-303@ZY-179");
        	list.add("Z1-BMB1-1401-142@ZY-18");
        	list.add("Z1-BMB1-2001-304@ZY-180");
        	list.add("Z1-BMB1-2101-305@ZY-181");
        	list.add("Z1-BMB1-2101-306@ZY-182");
        	list.add("Z1-BMB1-2101-307@ZY-183");
        	list.add("Z1-BMB1-2101-308@ZY-184");
        	list.add("Z1-BMB1-2101-309@ZY-185");
        	list.add("Z1-BMB1-2101-310@ZY-186");
        	list.add("Z1-BMB1-2101-311@ZY-187");
        	list.add("Z1-BMB1-2101-312@ZY-188");
        	list.add("Z1-BMB1-2101-313@ZY-189");
        	list.add("Z1-BMB1-1401-143@ZY-19");
        	list.add("Z1-BMB1-2101-314@ZY-190");
        	list.add("Z1-BMB1-2101-315@ZY-191");
        	list.add("Z1-BMB1-2101-316@ZY-192");
        	list.add("Z1-BMB1-2101-317@ZY-193");
        	list.add("Z1-BMB1-2101-318@ZY-194");
        	list.add("Z1-BMB1-2101-319@ZY-195");
        	list.add("Z1-BMB1-2101-320@ZY-196");
        	list.add("Z1-BMB1-2101-321@ZY-197");
        	list.add("Z1-BMB1-2101-322@ZY-198");
        	list.add("Z1-BMB1-2101-323@ZY-199");
        	list.add("Z1-BMB1-1401-126@ZY-2");
        	list.add("Z1-BMB1-1401-144@ZY-20");
        	list.add("Z1-BMB1-2101-324@ZY-200");
        	list.add("Z1-BMB1-2101-325@ZY-201");
        	list.add("Z1-BMB1-2201-326@ZY-202");
        	list.add("Z1-BMB1-2201-327@ZY-203");
        	list.add("Z1-BMB1-2201-328@ZY-204");
        	list.add("Z1-BMB1-2201-329@ZY-205");
        	list.add("Z1-BMB1-2201-330@ZY-206");
        	list.add("Z1-BMB1-2201-331@ZY-207");
        	list.add("Z1-BMB1-2201-332@ZY-208");
        	list.add("Z1-BMB1-2201-333@ZY-209");
        	list.add("Z1-BMB1-1401-145@ZY-21");
        	list.add("Z1-BMB1-2201-334@ZY-210");
        	list.add("Z1-BMB1-2201-335@ZY-211");
        	list.add("Z1-BMB1-2201-336@ZY-212");
        	list.add("Z1-BMB1-2201-337@ZY-213");
        	list.add("Z1-BMB1-2201-338@ZY-214");
        	list.add("Z1-BMB1-2201-339@ZY-215");
        	list.add("Z1-BMB1-2201-340@ZY-216");
        	list.add("Z1-BMB1-2201-341@ZY-217");
        	list.add("Z1-BMB1-2201-342@ZY-218");
        	list.add("Z1-BMB1-2201-343@ZY-219");
        	list.add("Z1-BMB1-1401-146@ZY-22");
        	list.add("Z1-BMB1-2201-344@ZY-220");
        	list.add("Z1-BMB1-2201-345@ZY-221");
        	list.add("Z1-BMB1-2201-346@ZY-222");
        	list.add("Z1-BMB1-2201-347@ZY-223");
        	list.add("Z1-BMB1-2201-348@ZY-224");
        	list.add("Z1-BMB1-2201-349@ZY-225");
        	list.add("Z1-BMB1-2201-350@ZY-226");
        	list.add("Z1-BMB1-2301-351@ZY-227");
        	list.add("Z1-BMB1-2301-352@ZY-228");
        	list.add("Z1-BMB1-2301-353@ZY-229");
        	list.add("Z1-BMB1-1401-147@ZY-23");
        	list.add("Z1-BMB1-2301-354@ZY-230");
        	list.add("Z1-BMB1-2301-355@ZY-231");
        	list.add("Z1-BMB1-2301-356@ZY-232");
        	list.add("Z1-BMB1-2301-357@ZY-233");
        	list.add("Z1-BMB1-2301-358@ZY-234");
        	list.add("Z1-BMB1-2301-359@ZY-235");
        	list.add("Z1-BMB1-2301-360@ZY-236");
        	list.add("Z1-BMB1-2301-361@ZY-237");
        	list.add("Z1-BMB1-2301-362@ZY-238");
        	list.add("Z1-BMB1-2301-363@ZY-239");
        	list.add("Z1-BMB1-1401-148@ZY-24");
        	list.add("Z1-BMB1-2301-364@ZY-240");
        	list.add("Z1-BMB1-2301-365@ZY-241");
        	list.add("Z1-BMB1-2301-366@ZY-242");
        	list.add("Z1-BMB1-2301-367@ZY-243");
        	list.add("Z1-BMB1-2301-368@ZY-244");
        	list.add("Z1-BMB1-2301-369@ZY-245");
        	list.add("Z1-BMB1-2301-370@ZY-246");
        	list.add("Z1-BMB1-2301-371@ZY-247");
        	list.add("Z1-BMB1-2301-372@ZY-248");
        	list.add("Z1-BMB1-2301-373@ZY-249");
        	list.add("Z1-BMB1-1401-149@ZY-25");
        	list.add("Z1-BMB1-2301-374@ZY-250");
        	list.add("Z1-BMB1-2301-375@ZY-251");
        	list.add("Z1-BMB1-2401-376@ZY-252");
        	list.add("Z1-BMB1-2401-377@ZY-253");
        	list.add("Z1-BMB1-2401-378@ZY-254");
        	list.add("Z1-BMB1-2401-379@ZY-255");
        	list.add("Z1-BMB1-2401-380@ZY-256");
        	list.add("Z1-BMB1-2401-381@ZY-257");
        	list.add("Z1-BMB1-2401-382@ZY-258");
        	list.add("Z1-BMB1-2401-383@ZY-259");
        	list.add("Z1-BMB1-1401-150@ZY-26");
        	list.add("Z1-BMB1-2401-384@ZY-260");
        	list.add("Z1-BMB1-2401-385@ZY-261");
        	list.add("Z1-BMB1-2401-386@ZY-262");
        	list.add("Z1-BMB1-2401-387@ZY-263");
        	list.add("Z1-BMB1-2401-388@ZY-264");
        	list.add("Z1-BMB1-2401-389@ZY-265");
        	list.add("Z1-BMB1-2401-390@ZY-266");
        	list.add("Z1-BMB1-2401-391@ZY-267");
        	list.add("Z1-BMB1-2401-392@ZY-268");
        	list.add("Z1-BMB1-2401-393@ZY-269");
        	list.add("Z1-BMB1-1401-151@ZY-27");
        	list.add("Z1-BMB1-2501-394@ZY-270");
        	list.add("Z1-BMB1-2501-395@ZY-271");
        	list.add("Z1-BMB1-2501-396@ZY-272");
        	list.add("Z1-BMB1-2501-397@ZY-273");
        	list.add("Z1-BMB1-2501-398@ZY-274");
        	list.add("Z1-BMB1-2501-399@ZY-275");
        	list.add("Z1-BMB1-2501-400@ZY-276");
        	list.add("Z1-BMB1-2501-401@ZY-277");
        	list.add("Z1-BMB1-2501-402@ZY-278");
        	list.add("Z1-BMB1-2501-403@ZY-279");
        	list.add("Z1-BMB1-1401-152@ZY-28");
        	list.add("Z1-BMB1-2501-404@ZY-280");
        	list.add("Z1-BMB1-2501-405@ZY-281");
        	list.add("Z1-BMB1-2501-406@ZY-282");
        	list.add("Z1-BMB1-2501-407@ZY-283");
        	list.add("Z1-BMB1-2501-408@ZY-284");
        	list.add("Z1-BMB1-2501-409@ZY-285");
        	list.add("Z1-BMB1-2501-410@ZY-286");
        	list.add("Z1-BMB1-2501-411@ZY-287");
        	list.add("Z1-BMB1-2501-412@ZY-288");
        	list.add("Z1-BMB1-2601-413@ZY-289");
        	list.add("Z1-BMB1-1401-153@ZY-29");
        	list.add("Z1-BMB1-2601-414@ZY-290");
        	list.add("Z1-BMB1-2601-415@ZY-291");
        	list.add("Z1-BMB1-2601-416@ZY-292");
        	list.add("Z1-BMB1-2601-417@ZY-293");
        	list.add("Z1-BMB1-2601-418@ZY-294");
        	list.add("Z1-BMB1-2601-419@ZY-295");
        	list.add("Z1-BMB1-2601-420@ZY-296");
        	list.add("Z1-BMB1-2601-421@ZY-297");
        	list.add("Z1-BMB1-2601-422@ZY-298");
        	list.add("Z1-BMB1-2601-423@ZY-299");
        	list.add("Z1-BMB1-1401-127@ZY-3");
        	list.add("Z1-BMB1-1401-154@ZY-30");
        	list.add("Z1-BMB1-2601-424@ZY-300");
        	list.add("Z1-BMB1-2601-425@ZY-301");
        	list.add("Z1-BMB1-2601-426@ZY-302");
        	list.add("Z1-BMB1-2601-427@ZY-303");
        	list.add("Z1-BMB1-2601-428@ZY-304");
        	list.add("Z1-BMB1-2601-429@ZY-305");
        	list.add("Z1-BMB1-2601-430@ZY-306");
        	list.add("Z1-BMB1-2601-431@ZY-307");
        	list.add("Z1-BMB1-2601-432@ZY-308");
        	list.add("Z1-BMB1-2601-433@ZY-309");
        	list.add("Z1-BMB1-1401-155@ZY-31");
        	list.add("Z1-BMB1-2601-434@ZY-310");
        	list.add("Z1-BMB1-2601-435@ZY-311");
        	list.add("Z1-BMB1-2601-436@ZY-312");
        	list.add("Z1-BMB1-1401-156@ZY-32");
        	list.add("Z1-BMB1-1501-157@ZY-33");
        	list.add("Z1-BMB1-1501-158@ZY-34");
        	list.add("Z1-BMB1-1501-159@ZY-35");
        	list.add("Z1-BMB1-1501-160@ZY-36");
        	list.add("Z1-BMB1-1501-161@ZY-37");
        	list.add("Z1-BMB1-1501-162@ZY-38");
        	list.add("Z1-BMB1-1501-163@ZY-39");
        	list.add("Z1-BMB1-1401-128@ZY-4");
        	list.add("Z1-BMB1-1501-164@ZY-40");
        	list.add("Z1-BMB1-1501-165@ZY-41");
        	list.add("Z1-BMB1-1501-166@ZY-42");
        	list.add("Z1-BMB1-1501-167@ZY-43");
        	list.add("Z1-BMB1-1501-168@ZY-44");
        	list.add("Z1-BMB1-1501-169@ZY-45");
        	list.add("Z1-BMB1-1501-170@ZY-46");
        	list.add("Z1-BMB1-1501-171@ZY-47");
        	list.add("Z1-BMB1-1501-172@ZY-48");
        	list.add("Z1-BMB1-1501-173@ZY-49");
        	list.add("Z1-BMB1-1401-129@ZY-5");
        	list.add("Z1-BMB1-1501-174@ZY-50");
        	list.add("Z1-BMB1-1501-175@ZY-51");
        	list.add("Z1-BMB1-1501-176@ZY-52");
        	list.add("Z1-BMB1-1601-177@ZY-53");
        	list.add("Z1-BMB1-1601-178@ZY-54");
        	list.add("Z1-BMB1-1601-179@ZY-55");
        	list.add("Z1-BMB1-1601-180@ZY-56");
        	list.add("Z1-BMB1-1601-181@ZY-57");
        	list.add("Z1-BMB1-1601-182@ZY-58");
        	list.add("Z1-BMB1-1601-183@ZY-59");
        	list.add("Z1-BMB1-1401-130@ZY-6");
        	list.add("Z1-BMB1-1601-184@ZY-60");
        	list.add("Z1-BMB1-1601-185@ZY-61");
        	list.add("Z1-BMB1-1601-186@ZY-62");
        	list.add("Z1-BMB1-1601-187@ZY-63");
        	list.add("Z1-BMB1-1601-188@ZY-64");
        	list.add("Z1-BMB1-1601-189@ZY-65");
        	list.add("Z1-BMB1-1601-190@ZY-66");
        	list.add("Z1-BMB1-1601-191@ZY-67");
        	list.add("Z1-BMB1-1601-192@ZY-68");
        	list.add("Z1-BMB1-1601-193@ZY-69");
        	list.add("Z1-BMB1-1401-131@ZY-7");
        	list.add("Z1-BMB1-1601-194@ZY-70");
        	list.add("Z1-BMB1-1601-195@ZY-71");
        	list.add("Z1-BMB1-1701-196@ZY-72");
        	list.add("Z1-BMB1-1701-197@ZY-73");
        	list.add("Z1-BMB1-1701-198@ZY-74");
        	list.add("Z1-BMB1-1701-199@ZY-75");
        	list.add("Z1-BMB1-1701-200@ZY-76");
        	list.add("Z1-BMB1-1701-201@ZY-77");
        	list.add("Z1-BMB1-1701-202@ZY-78");
        	list.add("Z1-BMB1-1701-203@ZY-79");
        	list.add("Z1-BMB1-1401-132@ZY-8");
        	list.add("Z1-BMB1-1701-204@ZY-80");
        	list.add("Z1-BMB1-1701-205@ZY-81");
        	list.add("Z1-BMB1-1701-206@ZY-82");
        	list.add("Z1-BMB1-1701-207@ZY-83");
        	list.add("Z1-BMB1-1701-208@ZY-84");
        	list.add("Z1-BMB1-1701-209@ZY-85");
        	list.add("Z1-BMB1-1701-210@ZY-86");
        	list.add("Z1-BMB1-1701-211@ZY-87");
        	list.add("Z1-BMB1-1701-212@ZY-88");
        	list.add("Z1-BMB1-1701-213@ZY-89");
        	list.add("Z1-BMB1-1401-133@ZY-9");
        	list.add("Z1-BMB1-1701-214@ZY-90");
        	list.add("Z1-BMB1-1701-215@ZY-91");
        	list.add("Z1-BMB1-1701-216@ZY-92");
        	list.add("Z1-BMB1-1701-217@ZY-93");
        	list.add("Z1-BMB1-1701-218@ZY-94");
        	list.add("Z1-BMB1-1701-219@ZY-95");
        	list.add("Z1-BMB1-1701-220@ZY-96");
        	list.add("Z1-BMB1-1701-221@ZY-97");
        	list.add("Z1-BMB1-1801-222@ZY-98");
        	list.add("Z1-BMB1-1801-223@ZY-99");

        	for(String str : list)
        	{
        		String outPath1 = "d:\\plantCode\\" + str + ".png";
        		String[] strs = str.split("@");
        		
            	createPlantCode("果树资源与育种信息管理系统", strs[0], strs[1], "南京农业大学 监制", outPath1, 400, 400);
        	}
        	
        }  
        catch (Exception e)  
        {  
            e.printStackTrace();  
        }  
    }  
    
    /**
     * 生成物种的二维码/条形码图片
     * @param content 内容或跳转路径
     * @param outPath 二维码输出路径，如果为""则表示不输出图片到指定位置，只返回base64图片字符串
     * @param qrImgWidth 二维码图片宽度
     * @param qrImgHeight 二维码图片高度（有文字的话会加高45px）
     * @param displayName 条形码图片下的文字
     * @return
     */
    public static String createPlantCode(String content, String outPath, String displayName)
    {
    	int barImgWidth = 600;
    	int barImgHeight = 140;
    	int qrImgWidth = 200;
    	int qrImgHeight = 200;
    	
    	int width = 20 + 600 + 50 + 200 + 20;
    	int height = 20 + 200 + 20;
    	
    	Map<EncodeHintType, Object> hints = getDecodeHintType();
    	
    	try
        {  
        	BitMatrix barCodeBitMatrix = new Code128Writer().encode(content, BarcodeFormat.CODE_128, barImgWidth, barImgHeight, hints);        
        	BufferedImage barCodeBufferedImage = MatrixToImageWriter.toBufferedImage(barCodeBitMatrix);
        	
        	BitMatrix qrCodeBitMatrix = new QRCodeWriter().encode(content, BarcodeFormat.QR_CODE, qrImgWidth, qrImgHeight, hints);         
        	BufferedImage qrCodeBufferedImage = MatrixToImageWriter.toBufferedImage(qrCodeBitMatrix);
        	
        	//新的图片，把二维码和条形码和到一起，再加上条形码下面加上文字
            BufferedImage outImage = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
            Graphics2D outg = outImage.createGraphics();
            //1、画条形码到新的面板
            outg.drawImage(barCodeBufferedImage, 20, 20, barCodeBufferedImage.getWidth(), barCodeBufferedImage.getHeight(), null);
        	
            //2、条形码下面画文字到新的面板
            outg.setColor(Color.BLACK); 
            outg.setFont(new Font("宋体",Font.BOLD, 26)); //字体、字型、字号 
            int strWidth = outg.getFontMetrics().stringWidth(displayName);
            strWidth = (barImgWidth - strWidth)/2;
            if(strWidth < 0)
            {
            	strWidth = 0;
            }
            
            outg.drawString(displayName, strWidth + 20, barCodeBufferedImage.getHeight() + (outImage.getHeight() - barCodeBufferedImage.getHeight())/2 + 10 ); //画文字
            
            //3、画二维码到新的面板
            outg.drawImage(qrCodeBufferedImage, 20 + barImgWidth + 50, 20, qrCodeBufferedImage.getWidth(), qrCodeBufferedImage.getHeight(), null);
            
            outg.dispose(); 
            outImage.flush();
            
            // 如果输出路径为空，则不保存二维码图片到指定路径下
            if(StringUtils.isNotEmpty(outPath)){
	            //二维码生成的路径，但是实际项目中，我们是把这生成的二维码显示到界面上的，因此下面的折行代码可以注释掉
	            //可以看到这个方法最终返回的是这个二维码的imageBase64字符串
	            //前端用 <img src="data:image/png;base64,${imageBase64QRCode}"/> 其中${imageBase64QRCode}对应二维码的imageBase64字符串
	            ImageIO.write(outImage, "png", new File(outPath));   
            }
            
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(outImage, "png", os);
			//添加 data:image/png;base64 前缀方便前端解析
			String resultImage = new String("data:image/png;base64," + Base64.encodeBase64String(os.toByteArray()));
			return resultImage;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    	return null;
    }
    
    /**
     * 生成二维码图片
     * @param content 内容或跳转路径
     * @param outPath 二维码输出路径，如果为""则表示不输出图片到指定位置，只返回base64图片字符串
     * @param qrImgWidth 二维码图片宽度
     * @param qrImgHeight 二维码图片高度（有文字的话会加高45px）
     * @param displayName 二维码图片下的文字
     * @return
     */
    public static String createPlantCode(String headerName, String oneCodeName, String twoCodeName, String footerName, String outPath, int qrImgWidth, int qrImgHeight)
    {
    	Map<EncodeHintType, Object> hints = getDecodeHintType();
        int width = 500;
        int height = 800;
        try
        {  
        	BitMatrix qrCodeBitMatrix = new QRCodeWriter().encode(oneCodeName + "@" + twoCodeName, BarcodeFormat.QR_CODE, qrImgWidth, qrImgHeight, hints);         
        	BufferedImage qrCodeBufferedImage = MatrixToImageWriter.toBufferedImage(qrCodeBitMatrix);
        	
        	//新的图片，把二维码和条形码和到一起，再加上条形码下面加上文字
            BufferedImage outImage = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
            Graphics2D outg = outImage.createGraphics();
        	
            //1、条形码下面画文字到新的面板
            outg.setColor(Color.BLACK); 
            outg.setFont(new Font("宋体",Font.PLAIN, 28)); //字体、字型、字号 
            int headerNameWidth = outg.getFontMetrics().stringWidth(headerName);
            headerNameWidth = (width - headerNameWidth)/2;
            if(headerNameWidth < 0)
            {
            	headerNameWidth = 0;
            }
            outg.drawString(headerName, headerNameWidth, 150); //画文字
            
            //4、画二维码到新的面板
            outg.setColor(Color.BLACK); 
            outg.setFont(new Font("Calibri",Font.BOLD, 50)); //字体、字型、字号 
            int twoCodeNameWidth = outg.getFontMetrics().stringWidth(twoCodeName);
            twoCodeNameWidth = (width - twoCodeNameWidth)/2;
            if(twoCodeNameWidth < 0)
            {
            	twoCodeNameWidth = 0;
            }
            outg.drawString(twoCodeName, twoCodeNameWidth, 220);
            
            //3、画条形码到新的面板
            outg.drawImage(qrCodeBufferedImage, 50, 260, qrCodeBufferedImage.getWidth(), qrCodeBufferedImage.getHeight(), null);

            //2、
            outg.setColor(Color.BLACK); 
            outg.setFont(new Font("Calibri",Font.PLAIN, 30)); //字体、字型、字号 
            int oneCodeNameWidth = outg.getFontMetrics().stringWidth(oneCodeName);
            oneCodeNameWidth = (width - oneCodeNameWidth)/2;
            if(oneCodeNameWidth < 0)
            {
            	oneCodeNameWidth = 0;
            }
            outg.drawString(oneCodeName, oneCodeNameWidth, 310 + qrCodeBufferedImage.getHeight()); //画文字
            
            //5、画二维码到新的面板
            outg.setColor(Color.BLACK); 
            outg.setFont(new Font("宋体",Font.PLAIN, 26)); //字体、字型、字号 
            int footerNameWidth = outg.getFontMetrics().stringWidth(footerName);
            footerNameWidth = (width - footerNameWidth)/2;
            if(footerNameWidth < 0)
            {
            	footerNameWidth = 0;
            }
       
            outg.drawString(footerName, footerNameWidth, 360 + qrCodeBufferedImage.getHeight());
            
            outg.dispose(); 
            outImage.flush();
            
            // 如果输出路径为空，则不保存二维码图片到指定路径下
            if(StringUtils.isNotEmpty(outPath)){
            	File outFile = new File(outPath);
            	if(!outFile.exists())
            	{
            		outFile.mkdirs();
            	}
	            //二维码生成的路径，但是实际项目中，我们是把这生成的二维码显示到界面上的，因此下面的折行代码可以注释掉
	            //可以看到这个方法最终返回的是这个二维码的imageBase64字符串
	            //前端用 <img src="data:image/png;base64,${imageBase64QRCode}"/> 其中${imageBase64QRCode}对应二维码的imageBase64字符串
	            ImageIO.write(outImage, "png", new File(outPath));   
            }
            
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(outImage, "png", os);
			//添加 data:image/png;base64 前缀方便前端解析
			String resultImage = new String("data:image/png;base64," + Base64.encodeBase64String(os.toByteArray()));
			return resultImage;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 生成二维码图片
     * @param content 内容或跳转路径
     * @param outPath 二维码输出路径，如果为""则表示不输出图片到指定位置，只返回base64图片字符串
     * @param qrImgWidth 二维码图片宽度
     * @param qrImgHeight 二维码图片高度（有文字的话会加高45px）
     * @param displayName 二维码图片下的文字
     * @return
     */
    public static String createQRCode(String content, String outPath, int qrImgWidth, int qrImgHeight, String displayName)
    {
    	Map<EncodeHintType, Object> hints = getDecodeHintType();
        
        try
        {  
        	BitMatrix bitMatrix = new QRCodeWriter().encode(content, BarcodeFormat.QR_CODE, qrImgWidth, qrImgHeight, hints);         

        	BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
        	
        	// 如果有文字，则二维码图片高度增加45px
            if(StringUtils.isNotEmpty(displayName)){
            	qrImgHeight += 45;
            	
            	//新的图片，把带logo的二维码下面加上文字
                BufferedImage outImage = new BufferedImage(qrImgWidth, qrImgHeight, BufferedImage.TYPE_4BYTE_ABGR);
                Graphics2D outg = outImage.createGraphics();
                //画二维码到新的面板
                outg.drawImage(bufferedImage, 0, 0, bufferedImage.getWidth(), bufferedImage.getHeight(), null);
                //画文字到新的面板
                outg.setColor(Color.BLACK); 
                outg.setFont(new Font("宋体",Font.BOLD, 26)); //字体、字型、字号 
                int strWidth = outg.getFontMetrics().stringWidth(displayName);
                
                strWidth = (outImage.getWidth() - strWidth)/2;
                if(strWidth < 0)
                {
                	strWidth = 0;
                }
                
                outg.drawString(displayName, strWidth , bufferedImage.getHeight() + (outImage.getHeight() - bufferedImage.getHeight())/2 + 12 ); //画文字
                
                outg.dispose(); 
                outImage.flush();
                
                bufferedImage = outImage;
        	}
            
            // 如果输出路径为空，则不保存二维码图片到指定路径下
            if(StringUtils.isNotEmpty(outPath)){
	            //二维码生成的路径，但是实际项目中，我们是把这生成的二维码显示到界面上的，因此下面的折行代码可以注释掉
	            //可以看到这个方法最终返回的是这个二维码的imageBase64字符串
	            //前端用 <img src="data:image/png;base64,${imageBase64QRCode}"/> 其中${imageBase64QRCode}对应二维码的imageBase64字符串
	            ImageIO.write(bufferedImage, "png", new File(outPath));   
            }
            
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "png", os);
			//添加 data:image/png;base64 前缀方便前端解析
			String resultImage = new String("data:image/png;base64," + Base64.encodeBase64String(os.toByteArray()));
			return resultImage;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * 生成条形码图片
     * @param content 内容或跳转路径
     * @param outPath 条形码输出路径，如果为""则表示不输出图片到指定位置，只返回base64图片字符串
     * @param barImgWidth 条形码图片宽度
     * @param barImgHeight 条形码图片高度（有文字的话会加高45px）
     * @param displayName 条形码图片下的文字
     * @return
     */
    public static String createBARCode(String content, String outPath, int barImgWidth, int barImgHeight, String displayName)
    {
    	Map<EncodeHintType, Object> hints = getDecodeHintType();
    	
    	int codeWidth = 3 + // start guard
                (7 * 6) + // left bars
                5 + // middle guard
                (7 * 6) + // right bars
                3; // end guard
    	barImgWidth = Math.max(codeWidth, barImgWidth);
        
        try
        {  
        	BitMatrix bitMatrix = new Code128Writer().encode(content, BarcodeFormat.CODE_128, barImgWidth, barImgHeight, hints);         

        	BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
        	
        	// 如果有文字，则二维码图片高度增加45px
            if(StringUtils.isNotEmpty(displayName)){
            	barImgHeight += 45;
            	
            	//新的图片，把带logo的二维码下面加上文字
                BufferedImage outImage = new BufferedImage(barImgWidth, barImgHeight, BufferedImage.TYPE_4BYTE_ABGR);
                Graphics2D outg = outImage.createGraphics();
                //画二维码到新的面板
                outg.drawImage(bufferedImage, 0, 0, bufferedImage.getWidth(), bufferedImage.getHeight(), null);
                //画文字到新的面板
                outg.setColor(Color.BLACK); 
                outg.setFont(new Font("宋体",Font.BOLD, 26)); //字体、字型、字号 
                int strWidth = outg.getFontMetrics().stringWidth(displayName);
                strWidth = (outImage.getWidth() - strWidth)/2;
                if(strWidth < 0)
                {
                	strWidth = 0;
                }
                
                outg.drawString(displayName, strWidth, bufferedImage.getHeight() + (outImage.getHeight() - bufferedImage.getHeight())/2 + 12 ); //画文字
                
                outg.dispose(); 
                outImage.flush();
                
                bufferedImage = outImage;
        	}
            
            // 如果输出路径为空，则不保存二维码图片到指定路径下
            if(StringUtils.isNotEmpty(outPath)){
	            //二维码生成的路径，但是实际项目中，我们是把这生成的二维码显示到界面上的，因此下面的折行代码可以注释掉
	            //可以看到这个方法最终返回的是这个二维码的imageBase64字符串
	            //前端用 <img src="data:image/png;base64,${imageBase64QRCode}"/> 其中${imageBase64QRCode}对应二维码的imageBase64字符串
	            ImageIO.write(bufferedImage, "png", new File(outPath));   
            }
            
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "png", os);
			//添加 data:image/png;base64 前缀方便前端解析
			String resultImage = new String("data:image/png;base64," + Base64.encodeBase64String(os.toByteArray()));
			return resultImage;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 设置二维码的格式参数
     *
     * @return
     */
    private static Map<EncodeHintType, Object> getDecodeHintType()
    {
        // 用于设置QR二维码参数
        Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
        // 设置QR二维码的纠错级别（H为最高级别）具体级别信息
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        // 设置编码方式
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        hints.put(EncodeHintType.MARGIN, 0);
        hints.put(EncodeHintType.MAX_SIZE, 350);
        hints.put(EncodeHintType.MIN_SIZE, 100);

        return hints;
    }
}
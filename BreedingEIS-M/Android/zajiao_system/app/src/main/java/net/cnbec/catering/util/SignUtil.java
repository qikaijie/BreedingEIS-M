package net.cnbec.catering.util;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Environment;

import net.cnbec.catering.app.GJApplication;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @Describe: 签名工具类
 * @Author: gujie
 * @Email: 939395465@qq.com
 * @Date: 2018/3/20
 */


public class SignUtil {


    //根据Wifi信息获取本地Mac
    public static String getLocalMacAddressFromWifiInfo(Context context) {
        if (!StringUtil.isEmpty(GJApplication.MD5_LOCAL_MAC))
            return GJApplication.MD5_LOCAL_MAC;
        String mac = String.valueOf(SPUtils.get(SPUtils.LOCAL_MAC, ""));
        if (!StringUtil.isEmpty(mac)) {
            GJApplication.MD5_LOCAL_MAC = mac;
            return mac;
        }
        WifiInfo info = null;
        try {
            WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            info = wifi.getConnectionInfo();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (info == null) return "";
        mac = MD5(info.getMacAddress());
        SPUtils.put(SPUtils.LOCAL_MAC, mac);
        GJApplication.MD5_LOCAL_MAC = mac;
        return mac;
    }

    /**
     * MD5加密
     *
     * @param val
     * @return
     */
    public static String MD5(String val) {
        if (StringUtil.isEmpty(val)) return "";
        byte[] hash;
        try {
            hash = MessageDigest.getInstance("MD5").digest(val.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5 should not be supported!", e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("UTF-8 should not be supported!", e);
        }
        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10) hex.append("0");
            hex.append(Integer.toHexString(b & 0xFF));
        }
        return hex.toString();
    }

    /**
     * 获取设备机器码
     *
     * @return
     */
    public static String loadMachineNumber() {
        File tempFile = new File(Environment.getExternalStorageDirectory().getPath() + "/.BEC/.BEC_DEVICE_ID.sys");
        if (tempFile.exists()) {

            BufferedReader br;
            String decryStr = null;
            try {
                br = new BufferedReader(new FileReader(tempFile));
                decryStr = AesUtils.decrypt(GJApplication.SECRET_KEY, br.readLine());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return decryStr;
        } else {
            LogUtil.d("机器码不存在");
            return null;
        }
    }

    /**
     * 获取机器码
     */
    public static void initPrinter() {
        try {
            GJApplication.MACHINE_NUMBER = loadMachineNumber();
            if (GJApplication.MACHINE_NUMBER != null && GJApplication.MACHINE_NUMBER.length() > 10) {
                String type = GJApplication.MACHINE_NUMBER.substring(1, 5);
                switch (type) {
                    case "1710":
                        //1710一代机
                        break;
                    case "1720":
                        //1720二代机
                        break;
                    default:
                        break;
                }
            } else {

            }
        } catch (NullPointerException e) {
            e.printStackTrace();
            ToastUtil.show("打印机未连接！");
        }
    }


}

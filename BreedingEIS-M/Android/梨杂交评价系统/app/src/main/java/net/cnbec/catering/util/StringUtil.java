package net.cnbec.catering.util;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Describe: String 工具类
 * @Author: gujie
 * @Email: 939395465@qq.com
 * @Date: 2018/3/19
 */


public class StringUtil {
    /**
     * 将以逗号分隔的字符串转换成字符串数组
     *
     * @param valStr
     * @return String[]
     */
    public static String[] StrList(String valStr) {
        int i = 0;
        String TempStr = valStr;
        String[] returnStr = new String[valStr.length() + 1
                - TempStr.replace(",", "").length()];
        valStr = valStr + ",";
        while (valStr.indexOf(',') > 0) {
            returnStr[i] = valStr.substring(0, valStr.indexOf(','));
            valStr = valStr.substring(valStr.indexOf(',') + 1, valStr.length());

            i++;
        }
        return returnStr;
    }

    /**
     * 将字符串转换成以分隔符分割的字符串
     *
     * @param list  <String>
     * @param split :分割符
     * @return String
     */
    public static String ListToStr(List<String> list, char split) {
        StringBuilder result = new StringBuilder();
        boolean flag = false;
        for (String s : list) {
            if (flag) {
                result.append(split);
            } else {
                flag = true;
            }
            result.append(s);
        }

        return result.toString();
    }


    /**
     * 判断字符串是否为空
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(Object str) {
        return str == null || "".equals(str.toString().trim());
    }

    /**
     * 过滤字符串中包含超链接
     *
     * @param str
     * @return
     */
    public static String filerLinkStr(Object str) {
        String formatStr = "";
        if (str != null) {
            formatStr = str.toString().replaceAll("HYPERLINK", "");
            formatStr = formatStr.replaceAll("", "");
            int startIndex = formatStr.indexOf("\"http");
            int lastIndex = formatStr.lastIndexOf("http");
            if (startIndex == -1 || lastIndex == -1) {
                return formatStr;
            }
            String subStr = formatStr.substring(startIndex, lastIndex).trim();
            formatStr = formatStr.replace(subStr, "").trim();
            subStr = subStr.replaceAll("\"", "");
            formatStr = formatStr.replace(subStr, "").trim();
            formatStr = formatStr.replace(subStr, "").trim();
            formatStr = formatStr.replace("   ", "").trim();
        }
        return formatStr;

    }

    /**
     * 拼凑URL字符串
     *
     * @param url1 第一个URL
     * @param url2 第二个URL
     * @return
     */
    public static String combineUrl(String url1, String url2) {
        String sep = "/";
        boolean hasEnd = url1.endsWith(sep);
        boolean hasStart = url2.startsWith(sep);
        if (hasEnd && hasStart) {
            return url1.concat(url2.substring(1));
        } else if (!hasEnd && !hasStart) {
            return url1.concat(sep).concat(url2);
        } else {
            return url1.concat(url2);
        }
    }

    /**
     * List转换为字符串并加入分隔符
     *
     * @param list      字符串数组
     * @param separator 分隔符
     * @return
     */
    public static String listToString(List<String> list, char separator) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            if (i == list.size() - 1) {
                sb.append(list.get(i));
            } else {
                sb.append(list.get(i));
                sb.append(separator);
            }
        }
        return sb.toString();
    }

    /**
     * 转码
     *
     * @param source
     * @return
     */
    public static String urlDecodeUTF8(String source) {
        String result = source;
        try {
            result = java.net.URLDecoder.decode(source, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 去除字符串中的空格
     *
     * @return
     */
    public static String delBlank(String content) {
        if (content == null) {
            return "";
        }
        int index = 0;//清除当前空格
        StringBuilder sBuilder = new StringBuilder(content);
        while (index < sBuilder.length()) {
            if (sBuilder.charAt(index) == ' ') {
                sBuilder.deleteCharAt(index);
            } else {
                index++;
            }
        }
        return sBuilder.toString();
    }

    /**
     *
     */

    public static String getBlankString(String content) {
        StringBuilder str = new StringBuilder(content);
        for (int i = 4; i <= str.length() - 1; i += 5) {
            str.insert(i, ' ');
        }
        return str.toString();
    }

    /**
     * 比较版本号的大小,前者大则返回一个正数,后者大返回一个负数,相等则返回0
     *
     * @param version1
     * @param version2
     */
    public static int compareVersion(String version1, String version2) throws Exception {
        if (version1 == null || version2 == null) {
            throw new Exception("compareVersion error:illegal params.");
        }
        String[] versionArray1 = version1.split("\\.");//注意此处为正则匹配，不能用.；
        String[] versionArray2 = version2.split("\\.");
        int idx = 0;
        int minLength = Math.min(versionArray1.length, versionArray2.length);//取最小长度值
        int diff = 0;
        while (idx < minLength
                && (diff = versionArray1[idx].length() - versionArray2[idx].length()) == 0//先比较长度
                && (diff = versionArray1[idx].compareTo(versionArray2[idx])) == 0) {//再比较字符
            ++idx;
        }
        //如果已经分出大小，则直接返回，如果未分出大小，则再比较位数，有子版本的为大；
        diff = (diff != 0) ? diff : versionArray1.length - versionArray2.length;
        return diff;
    }

    /**
     * 改变字符串，使字符串为123 *** *** 123形式
     */
    public static String changeString(String content, int startNum, int endNum) {
        String startString = content.substring(0, startNum);
        String endString = content.substring(content.length() - endNum);
        int mid = content.length() - (startNum + endNum);
        String midString = "";
        for (int i = 0; i < mid; i++) {
            midString = midString + "*";
        }
        midString = midString.replaceAll("\\d{4}(?!$)", "$0 ");
        String resultString = startString + midString + endString;
        return resultString.replaceAll("\\d{4}(?!$)", "$0 ");
    }

    /**
     * 检测String是否全是中文
     *
     * @param name
     * @return
     */
    public static boolean checkNameChese(String name) {
        boolean res = false;
        char[] cTemp = name.toCharArray();
        for (int i = 0; i < name.length(); i++) {
            if (isChinese(cTemp[i])) {
                res = true;
                break;
            }
        }
        return res;
    }

    /**
     * 判定输入汉字
     *
     * @param c
     * @return
     */
    public static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;
    }

//    /**
//     * 数据库模糊查询,关键字前后添加%
//     * @param key
//     * @return
//     */
//    public static String dbLikeSearchKey(String key) {
//        String split = "%";
//        StringBuilder result = new StringBuilder();
//        String[] item = key.split("");
//        for (int i = 0; i < item.length; i++) {
//            result.append(item[i]).append(split);
//        }
//        return result.toString();
//    }

    /**
     * 数据库模糊查询,关键字前后添加%
     *
     * @param key
     * @return
     */
    public static String dbLikeSearchKey(String key) {

        return "%" + key + "%";
    }

    /**
     * 修改城市名（去除最后市）
     *
     * @param city
     * @return
     */
    public static String editCityName(String city) {
        String editCity = city;
        if (!isEmpty(city) && city.endsWith("市") && city.lastIndexOf('市') != -1) {
            editCity = city.substring(0, city.length() - 1);
        }
        return editCity;
    }

    /**
     * 截取银行卡号后四位
     *
     * @param cardNum
     * @return
     */
    public static String getCardNumLastFourStr(String cardNum) {
        if (StringUtil.isEmpty(cardNum) || cardNum.length() < 4) {
            return "";
        }
        return cardNum.substring(cardNum.length() - 4);
    }

    /**
     * 判断是否为http网络地址
     *
     * @param str
     * @return
     */
    public static boolean isHttpUrlOrHttpsUrl(String str) {
        if (isEmpty(str)) {
            return false;
        }
        if (str.indexOf("http://") != -1 || str.indexOf("https://") != -1) {
            return true;
        }
        return false;
    }


    /**
     * 判断银行卡号是否正确
     *
     * @param bankcard
     * @return boolean
     */
    public static boolean isBankcardRegex(String bankcard) {
        String bankcardPattern ="(^([1-9]{1})(\\d{14}|\\d{18})$)";
        return Pattern.matches(bankcardPattern, bankcard);
    }

    /**
     * 判断是否包含特殊字符
     *
     * @param str
     * @return
     */
    public static boolean hasSpecialCharacter(String str) {
        String regEx="[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        if( m.find()){
            return true;
        }
        return false;
    }

}

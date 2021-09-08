package com.iamle.utils;


import cn.izern.sequence.Sequence;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * 字符串处理工具类
 *
 * @ChavinKing
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {

    /**
     * 字符串截取
     *
     * @param str
     * @param beginIndex
     * @return
     */
    public static String substr(String str, Integer beginIndex) {
        return str.substring(beginIndex);
    }

    /**
     * 字符串截取
     *
     * @param str
     * @param beginIndex
     * @param subLen
     * @return
     */
    public static String substr(String str, Integer beginIndex, Integer subLen) {
        Integer endIndex = beginIndex + subLen;
        return str.substring(beginIndex, endIndex > str.length() ? str.length() : endIndex);
    }

    /**
     * 判断子字符串是否存在
     *
     * @param str
     * @param subStr
     * @return
     */
    public static Integer instr(String str, String subStr) {
        return str.indexOf(subStr);
    }

    /**
     * 判断传入字符串是否为null
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        if (null == str || str.length() <= 0 || "".equals(str)) {
            return true;
        }
        return false;
    }

    /**
     * 判断传入字符串是否不为null
     *
     * @param str
     * @return
     */
    public static boolean isNotEmpty(String str) {
        if (null == str || str.length() <= 0 || "".equals(str)) {
            return false;
        }
        return true;
    }

    /**
     * 判断一组字符串是否有null值
     *
     * @param strs
     * @return
     */
    public static boolean hasEmpty(String ... strs){
        if (null == strs || 0 == strs.length) {
            return true;
        } else {
            for (String str : strs) {
                if (isEmpty(str)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 字符串加密
     *
     * @param clearText
     * @param password
     * @return
     */
    public static String encryptString(String clearText, String password) {
        return EncryptUtils.encryptHex(clearText, password);
    }

    /**
     * 字符串解密
     *
     * @param cipherText
     * @param password
     * @return
     */
    public static String decryptString(String cipherText, String password) {
        return EncryptUtils.decryptHex(cipherText, password);
    }

    /**
     * 获得UUID
     *
     * @return
     */
    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "").toUpperCase();
    }

    /**
     * 获取全局整数唯一值
     *
     * @return
     */
    public static long getGUID() {
        return new Sequence().nextId();
    }

    /**
     * 判断字符串相等
     *
     * @param str1
     * @param str2
     * @return
     */
    public static boolean equals(String str1, String str2) {
        return str1.equals(str2);
    }

    /**
     * 判断字符串相等-忽略大小写
     *
     * @param str1
     * @param str2
     * @return
     */
    public static boolean equals(String str1, String str2, boolean ignoreCase) {
        if (ignoreCase) {
            return str1.equalsIgnoreCase(str2);
        } else {
            return str1.equals(str2);
        }
    }

    /**
     * 字符串拼接
     *
     * @param strs
     * @return
     */
    public static String concat(String... strs) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < strs.length; i++) {
            sb.append(strs[i]);
        }
        return sb.toString();
    }

    /**
     * 字符串拼接-通过分隔符
     *
     * @param sep
     * @param strs
     * @return
     */
    public static String concatWS(String sep, String... strs) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < strs.length; i++) {
            sb.append(sep).append(strs[i]);
        }
        return sb.substring(1);
    }

    /**
     * 字符串替换
     *
     * @param str
     * @param regex
     * @param replacement
     * @return
     */
    public static String replace(String str, String regex, String replacement) {
        return str.replaceAll(regex, replacement);
    }

    /**
     * 字符串切割数组
     *
     * @param str
     * @param regex
     * @return
     */
    public static String[] split(String str, String regex) {
        return str.split(regex);
    }

    /**
     * 删除字符串两端空白字符
     *
     * @param str
     * @return
     */
    public static String trim(String str) {
        return str.trim();
    }

    /**
     * 删除字符串两头特殊字符串
     *
     * @param str
     * @param strSymbol
     * @return
     */
    public static String trim(String str,String strSymbol){

        while (str.startsWith(strSymbol)){
            str = str.substring(strSymbol.length());
        }

        while(str.endsWith(strSymbol)){
            str = str.substring(0,str.length()-strSymbol.length());
        }

        return str;
    }

    /**
     * 字符串反转
     *
     * @param str
     * @return
     */
    public static String reverse(String str) {
        return new StringBuffer(str).reverse().toString();
    }

    /**
     * 将字符串转换成16进制
     *
     * @param str
     * @return
     */
    public static String stringToHex(String str) {
        byte buf[] = str.getBytes();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * 将16进制转换为字符串
     *
     * @param hexStr
     * @return
     */
    public static String hexToString(String hexStr) {
        if (hexStr.length() < 1)
            return null;
        byte[] result = new byte[hexStr.length()/2];
        for (int i = 0;i< hexStr.length()/2; i++) {
            int high = Integer.parseInt(hexStr.substring(i*2, i*2+1), 16);
            int low = Integer.parseInt(hexStr.substring(i*2+1, i*2+2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return new String(result);
    }


    /**
     * 科学计数法格式化为标准数字格式
     *
     * @param numberString
     * @param scale
     * @return
     */
    public static String toNumberString(String numberString,Integer scale){

        try {
            BigDecimal bd = new BigDecimal(numberString);
            return bd.setScale(scale,BigDecimal.ROUND_FLOOR).toPlainString();
        }catch (Exception e){
            return numberString.trim();
        }

    }


    /**
     * null 赋值 替换函数
     *
     * @param inputStr
     * @param ouputStr
     * @return
     */
    public static String nullElse(String inputStr ,String ouputStr){

        try{

            if(null == inputStr){
                return ouputStr;
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return inputStr;

    }


}

package com.venus.helper;

/**
 * Copyright: 版权所有 ( c ) 北京启明星辰信息安全技术有限公司 2013。保留所有权利
 * 作者: 郭宁
 * 创建时间: 2014/6/9 下午20:55
 * 文件描述:String 工具类
 * 修改描述：
 */
public class StringHelper {

    private static final String EMPTY_STR = "";

    private static final String DEFAULT_SEPARATOR="_";


    /**
     * 首字母小写的驼峰
     * @param src
     * @return
     */
    public static String toHumpFirstLower(String src){
        return toLowerCaseFirst(toHump(src));
    }

    /**
     * 首字母大写的驼峰
     * @param src
     * @return
     */
    public static String toHumpFirstUpper(String src){
        return toUpperCaseFirst(toHump(src));
    }



    /**
     *  添加前置
     * @param src
     * @param pre
     * @return
     */
    public static String addPre(String src,String pre){
        return pre+toUpperCaseFirst(toHump(src));
    }

    /**
     * 驼峰格式
     * @param src
     * @return
     */
    public static String toHump(String src){
        return toHump(src,DEFAULT_SEPARATOR);
    }

    /**
     * 驼峰格式
     * @param src
     * @param separator
     * @return
     */
    public static String toHump(String src,String separator){
        if(isEmpty(separator)){
            separator = DEFAULT_SEPARATOR;
        }
        if(isEmpty(src))
            return EMPTY_STR;
        String[] srcs = src.toLowerCase().split(separator);
        StringBuilder sb = new StringBuilder(srcs[0]);
        for(int i=1; i<srcs.length; i++){
            sb.append(toUpperCaseFirst(srcs[i]));
        }
        return sb.toString();
    }

    /**
     * 首字母小写
     * @param src
     * @return
     */
    public static String toLowerCaseFirst(String src){
        if(isEmpty(src))
            return EMPTY_STR;
        char[] char4src = src.toCharArray();
        char4src[0] = toLower(char4src[0]);
        return new String(char4src);
    }

    /**
     * 首字母大写
     * @param src
     * @return
     */
    public static String toUpperCaseFirst(String src){
        if(isEmpty(src))
            return EMPTY_STR;
        char[] char4src = src.toCharArray();
        char4src[0] = toUpper(char4src[0]);
        return new String(char4src);
    }

    /**
     * 转小写
     * @param src
     * @return
     */
    public static char toLower(char src){
        if(src>=65 && src<=90)
            return (char) (src+32);
        else
            return src;
    }

    /**
     * 转大写
     * @param src
     * @return
     */
    public static char toUpper(char src){
        if(src>=97 && src<=122)
            return (char) (src-32);
        else
            return src;
    }

    /**
     * 转换成大写
     * @param str
     * @return
     */
    public static String toUpper(String str){
        if(str!=null)
            return str.toUpperCase();
        return null;
    }

    /**
     * 断定字符串为 null 或 "" ;
     * @param src
     * @return
     */
    public static boolean isEmpty(String src){
        return null==src || EMPTY_STR.equals(src);
    }

    public static void main(String[] args){
        String src = "Usar_name";
        System.out.println((short)'a');
        System.out.println(toHump(src,"a"));
    }

}

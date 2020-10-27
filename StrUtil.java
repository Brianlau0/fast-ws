package com.yl.fast.core.utils;

import java.util.Collection;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StrUtil extends cn.hutool.core.util.StrUtil {
  public static final String EMPTY = "";
  public static final String NULL = "null";
  /** 下划线 */
  private static final char UNDERLINE = '_';

  /** 判断字符串列表是否全都不为空，不为Null且不为空字符串"" */
  public static boolean isNonEmpty(String... str) {
    return isAllNotEmpty(str);
  }

  /**
   * 全大写
   *
   * @param str
   * @return
   */
  public static String upperCase(String str) {
    return isEmpty(str) ? EMPTY : str.toUpperCase();
  }

  /**
   * 全小写
   *
   * @param str
   * @return
   */
  public static String lowerCase(String str) {
    return isEmpty(str) ? EMPTY : str.toLowerCase();
  }

  /**
   * 正则校验
   *
   * @param regex 正则表达式字符串
   * @param value 要匹配的字符串
   * @return 正则校验结果
   */
  public static boolean match(String regex, String value) {
    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(value);
    return matcher.matches();
  }

  /**
   * 判断是否包含中文
   *
   * @param value 内容
   * @return 结果
   */
  public static boolean containChinese(String value) {
    Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
    Matcher m = p.matcher(value);
    return m.find();
  }

  /**
   * 驼峰转下划线
   *
   * @param param
   * @return
   */
  public static String camelToUnderline(String param) {
    return camelToUnderline(param, UNDERLINE);
  }

  /**
   * 驼峰
   *
   * @param param
   * @return
   */
  public static String camelToUnderline(String param, char separateLine) {
    if (cn.hutool.core.util.StrUtil.isEmpty(param)) {
      return "";
    }
    StringBuilder sb = new StringBuilder();
    int len = param.length();
    for (int i = 0; i < len; i++) {
      char c = param.charAt(i);
      if (Character.isUpperCase(c)) {
        sb.append(separateLine);
        sb.append(Character.toLowerCase(c));
      } else {
        sb.append(c);
      }
    }
    return sb.toString();
  }

  /**
   * 驼峰
   *
   * @param param
   * @return
   */
  public static String underlineToCamel(String param) {
    if (cn.hutool.core.util.StrUtil.isEmpty(param)) {
      return "";
    }
    StringBuilder sb = new StringBuilder();
    int len = param.length();
    for (int i = 0; i < len; i++) {
      char c = param.charAt(i);
      if (c == UNDERLINE) {
        if (++i < len) {
          sb.append(Character.toUpperCase(param.charAt(i)));
        }
      } else {
        sb.append(c);
      }
    }
    return sb.toString();
  }

  /** 将以逗号分隔的字符串转成Set */
  public static Set<String> commaDelimitedListToSet(String str) {
    return org.springframework.util.StringUtils.commaDelimitedListToSet(str);
  }

  /** 将集合转成以逗号分隔的字符串 */
  public static String collectionToCommaDelimitedString(Collection<?> coll) {
    return org.springframework.util.StringUtils.collectionToCommaDelimitedString(coll);
  }

  /** 将集合转成以某个符号分隔的字符串 */
  public static String collectionToDelimitedString(Collection<?> coll, String delim) {
    return org.springframework.util.StringUtils.collectionToDelimitedString(coll, delim);
  }

  /** 路径连接 */
  public static String applyRelativePath(String path, String relativePath) {
    return org.springframework.util.StringUtils.applyRelativePath(path, relativePath);
  }

  /** 将数组转成以逗号分隔的字符串 */
  public static String arrayToCommaDelimitedString(Object[] arr) {
    return org.springframework.util.StringUtils.arrayToCommaDelimitedString(arr);
  }


}

package com.startx.http.wordfilter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 敏感词过滤
 *
 * @author minghu.zhang
 */
@SuppressWarnings("rawtypes")
public class WordFilter {
    /**
     * 初始化敏感词库
     */
    private static Map sensitiveWordMap = new WordContext().initKeyWord();

    /**
     * 替换敏感词
     *
     * @param text     输入文本
     * @param distance 文本距离
     * @param symbol   替换符号
     */
    public static String replace(final String text, final int distance, final char symbol) {
        char[] charset = text.toCharArray();
        for (int i = 0; i < charset.length; i++) {
            FlagIndex fi = getFlagIndex(charset, i, distance);
            if (fi.isFlag()) {
                for (int j : fi.getIndex()) {
                    charset[j] = symbol;
                }
            }
        }
        return new String(charset);
    }

    /**
     * 是否包含敏感词
     *
     * @param text     输入文本
     * @param distance 文本距离
     */
    public static boolean include(final String text, final int distance) {
        boolean flag = false;
        char[] charset = text.toCharArray();
        for (int i = 0; i < charset.length; i++) {
            flag = getFlagIndex(charset, i, distance).isFlag();
            if (flag) {
                break;
            }
        }
        return flag;
    }

    /**
     * 获取敏感词数量
     *
     * @param text     输入文本
     * @param distance 文本距离
     */
    public static int wordCount(final String text, final int distance) {
        int count = 0;
        char[] charset = text.toCharArray();
        for (int i = 0; i < charset.length; i++) {
            FlagIndex fi = getFlagIndex(charset, i, distance);
            if (fi.isFlag()) {
                count++;
            }
        }
        return count;
    }

    /**
     * 获取敏感词列表
     *
     * @param text     输入文本
     * @param distance 文本距离
     */
    public static List<String> wordList(final String text, final int distance) {
        List<String> sensitives = new ArrayList<>();
        char[] charset = text.toCharArray();
        for (int i = 0; i < charset.length; i++) {
            FlagIndex fi = getFlagIndex(charset, i, distance);
            if (fi.isFlag()) {
                StringBuilder builder = new StringBuilder();
                for (int j : fi.getIndex()) {
                    char word = text.charAt(j);
                    builder.append(word);
                }
                sensitives.add(builder.toString());
            }
        }
        return sensitives;
    }

    /**
     * 获取标记索引
     *
     * @param charset  输入文本
     * @param begin    检测起始
     * @param distance 文本距离
     */
    private static FlagIndex getFlagIndex(final char[] charset, final int begin, final int distance) {
        FlagIndex fi = new FlagIndex();

        Map nowMap = sensitiveWordMap;
        boolean flag = false;
        int count = 0;
        List<Integer> index = new ArrayList<>();
        for (int i = begin; i < charset.length; i++) {
            char word = charset[i];
            Map mapTree = (Map) nowMap.get(word);
            if (count > distance || (i == begin && Objects.isNull(mapTree))) {
                break;
            }
            if (Objects.nonNull(mapTree)) {
                nowMap = mapTree;
                count = 0;
                index.add(i);
            } else {
                count++;
                if (flag && count > distance) {
                    break;
                }
            }
            if ("1".equals(nowMap.get("isEnd"))) {
                flag = true;
            }
            if ("1".equals(nowMap.get("isWhiteWord"))) {
                flag = false;
                break;
            }
        }

        fi.setFlag(flag);
        fi.setIndex(index);

        return fi;
    }
}

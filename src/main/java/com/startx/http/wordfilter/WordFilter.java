package com.startx.http.wordfilter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 敏感词过滤
 */
public class WordFilter {
	/**
	 * 初始化敏感词库
	 */
	@SuppressWarnings("rawtypes")
	private static Map sensitiveWordMap = new WordContext().initKeyWord();
	
	/**
	 * 替换敏感词
	 * @param text       输入文本
	 * @param distence   文本距离
	 * @param symbol     替换符号
	 */
	public static String replace(final String text,final int distence,final char symbol) {
		char[] charest = text.toCharArray();
		for (int i = 0; i < charest.length; i++) {
			FlagIndex fi = getFlagIndex(charest, i, distence);
			if (fi.isFlag()) {
				for (int j : fi.getIndex()) {
					charest[j] = symbol;
				}
			}
		}
		return new String(charest);
	}

	/**
	 * 是否包含敏感词
	 * @param text       输入文本
	 * @param distence   文本距离
	 */
	public static boolean include(final String text,final int distence) {
		boolean flag = false;
		char[] charest = text.toCharArray();
		for (int i = 0; i < charest.length; i++) {
			flag = getFlagIndex(charest, i, distence).isFlag();
			if (flag) {
				break;
			}
		}
		return flag;
	}

	/**
	 * 获取敏感词数量
	 * @param text       输入文本
	 * @param distence   文本距离
	 */
	public static int wordcount(final String text,final int distence) {
		int count = 0;
		char[] charest = text.toCharArray();
		for (int i = 0; i < charest.length; i++) {
			FlagIndex fi = getFlagIndex(charest, i, distence);
			if (fi.isFlag()) {
				count ++;
			}
		}
		return count;
	}
	
	/**
	 * 获取敏感词列表
	 * @param text       输入文本
	 * @param distence   文本距离
	 */
	public static List<String> wordlist(final String text,final int distence) {
		List<String> sensitives = new ArrayList<>();
		char[] charest = text.toCharArray();
		for (int i = 0; i < charest.length; i++) {
			FlagIndex fi = getFlagIndex(charest, i, distence);
			if (fi.isFlag()) {
				StringBuffer buffer = new StringBuffer();
				for (int j : fi.getIndex()) {
					char word = text.charAt(j);
					buffer.append(word);
				}
				sensitives.add(buffer.toString());
			}
		}
		return sensitives;
	}
	
	/**
	 * 获取标记索引
	 * @param charest    输入文本
	 * @param begin      检测起始
	 * @param distence   文本距离
	 */
	@SuppressWarnings("rawtypes")
	private static FlagIndex getFlagIndex(final char[] charest,final int begin,final int distence) {
		FlagIndex fi = new FlagIndex();
		
		Map nowMap = sensitiveWordMap;
		boolean flag = false;
		int count = 0;
		List<Integer> index = new ArrayList<>();
		for (int i = begin; i < charest.length; i++) {
			char word = charest[i];
			Map mapTree = (Map) nowMap.get(word);
			if (count > distence || (i == begin && Objects.isNull(mapTree))) {
				break;
			}
			if (!Objects.isNull(mapTree)) {
				nowMap = mapTree;
				count = 0;
				index.add(i);
			} else {
				count++;
			}
			if ("1".equals(nowMap.get("isEnd"))) {
				flag = true;
				break;
			}
		}

		fi.setFlag(flag);
		fi.setIndex(index);

		return fi;
	}
}

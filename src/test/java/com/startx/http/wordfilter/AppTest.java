package com.startx.http.wordfilter;

import org.junit.Test;

import java.util.List;

/**
 * Unit test for simple App.
 */
public class AppTest {
	
	/**
	 * 测试替换过滤池
	 */
    @Test
    public void replace() {
        {
            String text   = "我小时候有个朋友叫张三，现在和他几乎没联系了";
            String result = WordFilter.replace(text, 0, '*');
            System.out.println(result);		//我小时候有个朋友叫**，现在和他几乎没联系了
        }
        {
            String text   = "我小时候有个朋友叫张大三，现在和他几乎没联系了";
            String result = WordFilter.replace(text, 1, '*');
            System.out.println(result);		//我小时候有个朋友叫*大*，现在和他几乎没联系了
        }
    }

    /**
	 * 测试是否包含过滤池
	 */
    @Test
    public void include() {
        {
            String text   = "我小时候有个朋友叫张三，现在和他几乎没联系了";
            boolean result = WordFilter.include(text, 0);
            System.out.println(result);        //true
        }
        {
            String text   = "我小时候有个朋友叫张大三，现在和他几乎没联系了";
            boolean result = WordFilter.include(text, 1);
            System.out.println(result);        //true
        }
    }
	
	/**
	 * 获取过滤词数
	 */
    @Test
    public void wordCount() {
        {
            String text   = "我小时候有个朋友叫张三，现在和他几乎没联系了";
            int result = WordFilter.wordCount(text, 0);
            System.out.println(result);        //1
        }
        {
            String text   = "我小时候有个朋友叫张大三，现在和他几乎没联系了";
            int result = WordFilter.wordCount(text, 1);
            System.out.println(result);        //1
        }
    }
	
	/**
	 * 获取过滤词列表
	 */
    @Test
    public void wordList() {
        {
            String text   = "我小时候有个朋友叫张三，现在和他几乎没联系了";
            List<String> words = WordFilter.wordList(text, 0);
            System.out.println(words);        //[张三]
        }
        {
            String text   = "我小时候有个朋友叫张大三，现在和他几乎没联系了";
            List<String> words = WordFilter.wordList(text, 1);
            System.out.println(words);        //[张三]
        }
    }
	
}

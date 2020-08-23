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
            String text   = "我们决定紧急征调5000人前往宅区帮助灾民，并且决定为紧急事件打开绿灯";
            String result = WordFilter.replace(text);
            System.out.println(result);		//我们决定**征调5000人前往宅区帮助灾民，并且决定为紧急事件打开绿灯
        }
        {
            String text   = "我们决定紧x急征调5000人前往宅区帮助灾民，并且决定为紧急x事x件打开绿灯";
            String result = WordFilter.replace(text, 1, '*');
            System.out.println(result);		//我们决定*x*征调5000人前往宅区帮助灾民，并且决定为紧急x事x件打开绿灯
        }
    }

    /**
	 * 测试是否包含过滤池
	 */
    @Test
    public void include() {
        {
            String text   = "我小时候有个朋友叫张三，现在和他几乎没联系了";
            boolean result = WordFilter.include(text);
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
            int result = WordFilter.wordCount(text);
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
            List<String> words = WordFilter.wordList(text);
            System.out.println(words);        //[张三]
        }
        {
            String text   = "我小时候有个朋友叫张大三，现在和他几乎没联系了";
            List<String> words = WordFilter.wordList(text, 1);
            System.out.println(words);        //[张三]
        }
    }
	
}

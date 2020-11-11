package com.startx.http.wordfilter;

import org.junit.Test;

import java.util.Collections;
import java.util.List;

/**
 * 单元测试用例
 *
 * @author minghu.zhang
 */
public class AppTest {

    /**
     * 词库上下文环境
     */
    private WordContext context = new WordContext();
    private WordFilter filter = new WordFilter(context);

    /**
     * 测试替换敏感词
     */
    @Test
    public void replace() {
        {
            String text = "我们决定紧急征调5000人前往宅区帮助灾民，并且决定为紧急事件打开绿灯";
            String result = filter.replace(text);
            System.out.println(result);        //我们决定**征调5000人前往宅区帮助灾民，并且决定为紧急事件打开绿灯
        }
        {
            //临时添加敏感词
            context.addWord(Collections.singletonList("5000"), WordType.BLACK);
        }
        {
            String text = "我们决定紧x急征调5000人前往宅区帮助灾民，并且决定为紧急x事x件打开绿灯";
            String result = filter.replace(text, 1, '*');
            System.out.println(result);        //我们决定*x*征调****人前往宅区帮助灾民，并且决定为紧急x事x件打开绿灯
        }

        {
            //此处将5000移出黑名单（若黑名单没有该词组将忽略）
            context.removeWord(Collections.singletonList("5000"), WordType.BLACK);
            //此处将紧急事件移出白名单（若白名单没有该词组将忽略）
            context.removeWord(Collections.singletonList("紧急事件"), WordType.WHITE);
        }
        {
            String text = "我们决定紧x急征调5000人前往宅区帮助灾民，并且决定为紧急x事x件打开绿灯";
            String result = filter.replace(text, 1, '*');
            System.out.println(result);        //我们决定*x*征调5000人前往宅区帮助灾民，并且决定为**x事x件打开绿灯
        }
    }

    /**
     * 测试是否包含敏感词
     */
    @Test
    public void include() {
        {
            String text = "我小时候有个朋友叫张三，现在和他几乎没联系了";
            boolean result = filter.include(text);
            System.out.println(result);        //true
        }
        {
            String text = "我小时候有个朋友叫张大三，现在和他几乎没联系了";
            boolean result = filter.include(text, 1);
            System.out.println(result);        //true
        }
    }

    /**
     * 获取敏感词数
     */
    @Test
    public void wordCount() {
        {
            String text = "我小时候有个朋友叫张三，现在和他几乎没联系了";
            int result = filter.wordCount(text);
            System.out.println(result);        //1
        }
        {
            String text = "我小时候有个朋友叫张大三，现在和他几乎没联系了";
            int result = filter.wordCount(text, 1);
            System.out.println(result);        //1
        }
    }

    /**
     * 获取敏感词列表
     */
    @Test
    public void wordList() {
        {
            String text = "我小时候有个朋友叫张三，现在和他几乎没联系了";
            List<String> words = filter.wordList(text);
            System.out.println(words);        //[张三]
        }
        {
            String text = "我小时候有个朋友叫张大三，现在和他几乎没联系了";
            List<String> words = filter.wordList(text, 1);
            System.out.println(words);        //[张三]
        }
    }

}

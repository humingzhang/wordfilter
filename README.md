## 一、功能 ##

1.1. 敏感词替换

1.2. 测试是否包含过滤词

1.3. 获取过滤词数

1.4. 获取过滤词列表

1.5. 增加白名单功能

- blacklist.txt
- whitelist.txt

1.6. 增加方法重载

- replace(final String text)                     // distance=0&symbol=*
- replace(final String text, final char symbol)  // distance=0
- include(final String text)                     // distance=0
- wordCount(final String text)                   // distance=0
- wordList(final String text)                    // distance=0

1.7. 增加在线添加敏感词方法

- WordContext.getInstance()
  .addSensitiveWordOnline(Iterator<String> wordList, boolean isWhiteWord)
  //敏感词列表，是否白名单 true 白名单 false 黑名单

## 二、Distance ##

程序会根据不同的距离，查找敏感词，距离越长，过滤越严格，效率越低，开发者可以根据具体需求设置，这里以“张三”为敏感词举例，以此类推

  
- 0  匹配张三
- 1  匹配张三，张x三
- 2  匹配张三，张x三，张xx三


## 三、重大更新 ##

解决类似24口交换机的问题

拆分为白名单和黑名单

式例如下：

- 黑名单（紧急）
- 白名单（紧急事件）


- 我们决定紧急征调5000人前往宅区帮助灾民 / 紧急会被监测
- 并且决定为紧急事件打开绿灯 / 紧急事件不会被监测


## 四、测试预览 ##


* 测试替换过滤词
```
/**
     * 测试替换过滤池
     */
    @Test
    public void replace() {
        {
            String text = "我们决定紧急征调5000人前往宅区帮助灾民，并且决定为紧急事件打开绿灯";
            String result = WordFilter.replace(text);
            System.out.println(result);        //我们决定**征调5000人前往宅区帮助灾民，并且决定为紧急事件打开绿灯
        }
        {
            //临时添加敏感词
            WordContext.getInstance().addSensitiveWordOnline(Collections.singletonList("5000"), false);
        }
        {
            String text = "我们决定紧x急征调5000人前往宅区帮助灾民，并且决定为紧急x事x件打开绿灯";
            String result = WordFilter.replace(text, 1, '*');
            System.out.println(result);        //我们决定*x*征调****人前往宅区帮助灾民，并且决定为紧急x事x件打开绿灯
        }
    }

    /**
     * 测试是否包含过滤池
     */
    @Test
    public void include() {
        {
            String text = "我小时候有个朋友叫张三，现在和他几乎没联系了";
            boolean result = WordFilter.include(text);
            System.out.println(result);        //true
        }
        {
            String text = "我小时候有个朋友叫张大三，现在和他几乎没联系了";
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
            String text = "我小时候有个朋友叫张三，现在和他几乎没联系了";
            int result = WordFilter.wordCount(text);
            System.out.println(result);        //1
        }
        {
            String text = "我小时候有个朋友叫张大三，现在和他几乎没联系了";
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
            String text = "我小时候有个朋友叫张三，现在和他几乎没联系了";
            List<String> words = WordFilter.wordList(text);
            System.out.println(words);        //[张三]
        }
        {
            String text = "我小时候有个朋友叫张大三，现在和他几乎没联系了";
            List<String> words = WordFilter.wordList(text, 1);
            System.out.println(words);        //[张三]
        }
    }
```


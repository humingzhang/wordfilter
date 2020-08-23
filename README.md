## 功能 ##

1. 敏感词替换

2. 测试是否包含过滤词

3. 获取过滤词数

4. 获取过滤词列表

5. 增加白名单功能


## DISTANCE ##

程序会根据不同的距离，查找敏感词，距离越长，过滤越严格，效率越低，开发者可以根据具体需求设置，这里以“张三”为敏感词举例，以此类推

  
- 0  匹配张三
- 1  匹配张三，张x三
- 2  匹配张三，张x三，张xx三


## 重大更新 ##

解决类似24口交换机的问题

拆分为白名单和黑名单

式例如下：

- 黑名单（紧急）
- 白名单（紧急事件）


- 我们决定紧急征调5000人前往宅区帮助灾民 / 紧急会被监测
- 并且决定为紧急事件打开绿灯 / 紧急事件不会被监测


## 预览 ##


* 测试替换过滤词
```
    @Test
    public void replace() {
        {
            String text   = "我们决定紧急征调5000人前往宅区帮助灾民，并且决定为紧急事件打开绿灯";
            String result = WordFilter.replace(text, 0, '*');
            System.out.println(result);		//我们决定**征调5000人前往宅区帮助灾民，并且决定为紧急事件打开绿灯
        }
        {
            String text   = "我们决定紧x急征调5000人前往宅区帮助灾民，并且决定为紧急x事x件打开绿灯";
            String result = WordFilter.replace(text, 1, '*');
            System.out.println(result);		//我们决定*x*征调5000人前往宅区帮助灾民，并且决定为紧急x事x件打开绿灯
        }
    }
```

* 测试是否包含过滤词
```
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
```

* 获取过滤词数
```
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
```

* 获取过滤词列表
```
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
```


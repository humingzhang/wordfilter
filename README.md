## 一、API预览 ##

1.1. 敏感词替换

```java
String text = "我们决定紧急征调5000人前往宅区帮助灾民，并且决定为紧急事件打开绿灯";
filter.replace(text);
```

1.2. 是否包含敏感词

```java
String text = "我小时候有个朋友叫张三，现在和他几乎没联系了";
filter.include(text);
```

1.3. 获取敏感词数

```java
String text = "我小时候有个朋友叫张三，现在和他几乎没联系了";
int result = filter.wordCount(text);
```

1.4. 获取敏感词列表

```java
String text = "我小时候有个朋友叫张三，现在和他几乎没联系了";
List<String> words = filter.wordList(text);
```

1.5. 增加白名单功能

```
- blacklist.txt
- whitelist.txt
```

1.6. 增加方法重载

```java
- replace(final String text)                     // distance=0&symbol=*
- replace(final String text, final char symbol)  // distance=0
- include(final String text)                     // distance=0
- wordCount(final String text)                   // distance=0
- wordList(final String text)                    // distance=0
```

1.7. 增加在线添加敏感词方法

```java
//此处将5000加入黑名单
context.addWord(Collections.singletonList("5000"), WordType.BLACK);
```

1.8、增加在线删除敏感词方法

```java
//此处将5000移出黑名单（若黑名单没有该词组将忽略）
context.removeWord(Collections.singletonList("5000"), WordType.BLACK);
//此处将紧急事件移出白名单（若白名单没有该词组将忽略）
context.removeWord(Collections.singletonList("紧急事件"), WordType.WHITE);
```



## 二、基本用法

### 2.1、创建WordContext对象

```java
WordContext context = new WordContext();
```

该对象主要用来初始化和管理词库，默认读取类路径下 名为：blacklist.txt 和 whitelist.txt 的文件，也可以传入文件名，如下所示：

```
WordContext context = new WordContext("/blacklist.txt","/whitelist.txt");
```

### 2.2、创建过滤器对象

```java
WordFilter filter = new WordFilter(context);
```

传入上一步创建的词库上下文，获得过滤器对象之后即可使用filter调用相关方法，若需要添加或删除词库的词汇，可以使用WordContext的API实现，如下所示：

```java
//此处将5000加入黑名单
context.addWord(Collections.singletonList("5000"), WordType.BLACK);
//此处将5000移出黑名单（若黑名单没有该词组将忽略）
context.removeWord(Collections.singletonList("5000"), WordType.BLACK);
//此处将紧急事件移出白名单（若白名单没有该词组将忽略）
context.removeWord(Collections.singletonList("紧急事件"), WordType.WHITE);
```



## 三、Skip距离 ##

程序会跳过不同的距离，查找敏感词，距离越长，过滤越严格，效率越低，开发者可以根据具体需求设置，这里以“张三”为敏感词举例，以此类推


- 0  匹配张三

- 1  匹配张三，张x三

- 2  匹配张三，张x三，张xx三

  


## 四、重大更新 ##

解决类似24口交换机的问题

拆分为白名单和黑名单

如下所示：

- 黑名单（紧急）
- 白名单（紧急事件）


- 我们决定紧急征调5000人前往宅区帮助灾民 / 紧急会被检测
- 并且决定为紧急事件打开绿灯 / 紧急事件不会被检测




## 五、测试预览 ##


* 测试替换敏感词
```java
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
```


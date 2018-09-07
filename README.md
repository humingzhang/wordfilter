## 功能 ##

1. 敏感词替换

2. 测试是否包含过滤词

3. 获取过滤词数

4. 获取过滤词列表

## Distance ##

程序会根据不同的距离，查找敏感词，距离越长，过滤越严格，效率越低，开发者可以根据具体需求设置，这里以“张三”为敏感词举例，以此类推

  
- 0  匹配张三
- 1  匹配张三，张x三
- 2  匹配张三，张x三，张xx三


## 在线体验 ##


###REQUEST###
```
POST http://wf.instanceof.cn/word/filter
{
	"text":"我想说一句骂人的话",              //待检测文本     
	"distence":2,                           //文本距离，默认为0
	"replace":"*"                           //替换符号，默认为*
}
```
###RESPONSE###
```
{
    "code": {
        "msg": "successful",
        "code": 200
    },
    "data": {
        "include": false,
        "wordlist": [],
        "replace": "我想说一句骂人的话",
        "count": 0
    }
}
```


## 预览 ##


* 测试替换过滤词
```
@Test
public void replace0() {
	{
		String text   = "我们家旁边住了一个张三，我还认识一个自称大侠的人";
		String result = WordFilter.replace(text, 0, '*');
		System.out.println(result);		//我们家旁边住了一个**，我还认识一个自称**的人
	}
	{
		String text   = "我们家旁边住了一个张大三，我还认识一个自称大黑侠的人";
		String result = WordFilter.replace(text, 1, '*');
		System.out.println(result);		//我们家旁边住了一个*大*，我还认识一个自称*黑*的人
	}
} 
```

* 测试是否包含过滤词
```
@Test
public void include0() {
	{
		String text   = "我们家旁边住了一个张三，我还认识一个自称大侠的人";
		boolean result = WordFilter.include(text, 0);
		System.out.println(result);        //true
	}
	{
		String text   = "我们家旁边住了一个张大三，我还认识一个自称大黑侠的人";
		boolean result = WordFilter.include(text, 1);
		System.out.println(result);        //true
	}
}
```

* 获取过滤词数
```
@Test
public void wordcount0() {
	{
		String text   = "我们家旁边住了一个张三，我还认识一个自称大侠的人";
		int result = WordFilter.wordcount(text, 0);
		System.out.println(result);        //2
	}
	{
		String text   = "我们家旁边住了一个张大三，我还认识一个自称大黑侠的人";
		int result = WordFilter.wordcount(text, 1);
		System.out.println(result);        //2
	}
}
```

* 获取过滤词列表
```
@Test
public void wordlist0() {
	{
		String text   = "我们家旁边住了一个张三，我还认识一个自称大侠的人";
		List<String> words = WordFilter.wordlist(text, 0);
		System.out.println(words);        //[张三, 大侠]
	}
	{
		String text   = "我们家旁边住了一个张大三，我还认识一个自称大黑侠的人";
		List<String> words = WordFilter.wordlist(text, 1);
		System.out.println(words);        //[张三, 大侠]
	}
}
```


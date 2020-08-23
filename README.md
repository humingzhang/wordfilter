## 功能 ##

1. 敏感词替换

2. 测试是否包含过滤词

3. 获取过滤词数

4. 获取过滤词列表

## DISTANCE ##

程序会根据不同的距离，查找敏感词，距离越长，过滤越严格，效率越低，开发者可以根据具体需求设置，这里以“张三”为敏感词举例，以此类推

  
- 0  匹配张三
- 1  匹配张三，张x三
- 2  匹配张三，张x三，张xx三


## 预览 ##


* 测试替换过滤词
```
@Test
public void replace0() {
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
```

* 测试是否包含过滤词
```
@Test
public void include0() {
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
public void wordcount0() {
	{
		String text   = "我小时候有个朋友叫张三，现在和他几乎没联系了";
		int result = WordFilter.wordcount(text, 0);
		System.out.println(result);        //1
	}
	{
		String text   = "我小时候有个朋友叫张大三，现在和他几乎没联系了";
		int result = WordFilter.wordcount(text, 1);
		System.out.println(result);        //1
	}
}
```

* 获取过滤词列表
```
@Test
public void wordlist0() {
	{
		String text   = "我小时候有个朋友叫张三，现在和他几乎没联系了";
		List<String> words = WordFilter.wordlist(text, 0);
		System.out.println(words);        //[张三]
	}
	{
		String text   = "我小时候有个朋友叫张大三，现在和他几乎没联系了";
		List<String> words = WordFilter.wordlist(text, 1);
		System.out.println(words);        //[张三]
	}
}
```


package com.startx.http.wordfilter;

import java.util.List;

import org.junit.Test;

import com.startx.http.wordfilter.WordFilter;

/**
 * Unit test for simple App.
 */
public class AppTest {
	
	/**
	 * 测试替换过滤池
	 */
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
	
	/**
	 * 测试是否包含过滤池
	 */
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
	
	/**
	 * 获取过滤词数
	 */
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
	
	/**
	 * 获取过滤词列表
	 */
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
	
}

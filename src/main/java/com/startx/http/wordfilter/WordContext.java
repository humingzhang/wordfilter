package com.startx.http.wordfilter;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 初始化敏感词库，将敏感词加入到HashMap中，构建DFA算法模型
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class WordContext {


    private Map sensitiveWordMap;

    /**
     * 初始化
     */
    public Map initKeyWord() {
        try {
            // 将敏感词库加入到HashMap中
            addSensitiveWordToHashMap(readWordFile("/blacklist.txt"), false);
            // 将非敏感词库也加入到HashMap中
            addSensitiveWordToHashMap(readWordFile("/whitelist.txt"), true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sensitiveWordMap;
    }

    /**
     * 读取敏感词库，将敏感词放入HashSet中，构建一个DFA算法模型：<br>
     * 中 = { isEnd = 0 国 = {<br>
     * isEnd = 1 人 = {isEnd = 0 民 = {isEnd = 1} } 男 = { isEnd = 0 人 = { isEnd = 1 }
     * } } } 五 = { isEnd = 0 星 = { isEnd = 0 红 = { isEnd = 0 旗 = { isEnd = 1 } } } }
     */
    private void addSensitiveWordToHashMap(Set<String> keyWordSet, boolean isWhiteWord) {
        if (sensitiveWordMap == null) {
            sensitiveWordMap = new HashMap(keyWordSet.size()); // 初始化敏感词容器，减少扩容操作
        }
        String key;
        Map nowMap;
        Map<String, String> newWorMap;
        // 迭代keyWordSet
        for (String aKeyWordSet : keyWordSet) {
            key = aKeyWordSet; // 关键字
            nowMap = sensitiveWordMap;
            for (int i = 0; i < key.length(); i++) {
                char keyChar = key.charAt(i); // 转换成char型
                Object wordMap = nowMap.get(keyChar); // 获取
                if (wordMap != null) { // 如果存在该key，直接赋值
                    nowMap = (Map) wordMap;
                } else { // 不存在则构建一个map，同时将isEnd设置为0，因为他不是最后一个
                    newWorMap = new HashMap<>();
                    newWorMap.put("isEnd", "0"); // 不是最后一个
                    nowMap.put(keyChar, newWorMap);
                    nowMap = newWorMap;
                }

                if (i == key.length() - 1) {
                    nowMap.put("isEnd", "1"); // 最后一个
                    if (isWhiteWord) {
                        nowMap.put("isWhiteWord", "1");
                    }
                }
            }
        }
    }

    /**
     * 读取敏感词库中的内容，将内容添加到set集合中
     */
    private Set<String> readWordFile(String file) throws Exception {
        Set<String> set;
        // 字符编码
        String ENCODING = "UTF-8";
        try (InputStreamReader read = new InputStreamReader(
                this.getClass().getResourceAsStream(file), ENCODING)) {
            set = new HashSet<>();
            BufferedReader bufferedReader = new BufferedReader(read);
            String txt;
            while ((txt = bufferedReader.readLine()) != null) { // 读取文件，将文件内容放入到set中
                set.add(txt);
            }
        } catch (Exception e) {
            throw e;
        }
        // 关闭文件流
        return set;
    }


}

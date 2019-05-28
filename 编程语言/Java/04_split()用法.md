```
public class SplitTest{
    public static void main(String[] args){
    	//split函数工作原理：
    	//（1）先将str尾部的分隔符删除
    	//（2）再对剩下的部分进行切割
    	//（3）切割时不能忽略空字符串
    	//（4）如果str中不存在分隔符，则str作为切割的结果，得到的String数组长度为1
    	String str = ",,,,,m,,,";
    	String[] strs= str.split(",");
    	int len = strs.length;
    	System.out.println(len);
    	int i = 0;
    	for(String s:strs) {
    		System.out.println("<" + i++ + ">" + s);
    	}
    	
	System.out.println("-----------------------");
	
	String str2 = "";
	String[] strs2= str2.split(",");
	int len2 = strs2.length;
	System.out.println(len2);
	i = 0;
	for(String s:strs2) {
		System.out.println("<" + i++ + ">" +s);
	}        
}
}

运行结果：
6
<0>
<1>
<2>
<3>
<4>
<5>m
-----------------------
1
<0>
```

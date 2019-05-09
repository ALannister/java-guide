###  split()用法
```
import java.util.*;


public class Test {

	public static void main(String[] args) {
		String str = "  aaa  a  aaaa    ";
		String[] strs = str.split(" ");
		int i = 0;
		for(String s : strs) {
			System.out.println("<"+i+">"+s);
			i++;
		}
	}
}

//输出结果：
<0>
<1>
<2>aaa
<3>
<4>a
<5>
<6>aaaa
```



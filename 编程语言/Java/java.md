1. String to char[]

```
public class Test {
    public static void main(String args[]) {
        String Str = new String("www.runoob.com");

        System.out.print("返回值 :" );
        System.out.println( Str.toCharArray() );
    }
}
```

2. char[] to String
```
public class StringDemo{
   public static void main(String args[]){
      char[] helloArray = { 'r', 'u', 'n', 'o', 'o', 'b'};
      String helloString = new String(helloArray);  
      System.out.println( helloString );
   }
}
```
3. 基本类型 to String
```
//static String valueOf(primitive data type x)
public class Test {
    public static void main(String args[]) {
        double d = 1100.00;
        boolean b = true;
        long l = 1234567890;
        char[] arr = {'r', 'u', 'n', 'o', 'o', 'b' };

        System.out.println("返回值 : " + String.valueOf(d) );
        System.out.println("返回值 : " + String.valueOf(b) );
        System.out.println("返回值 : " + String.valueOf(l) );
        System.out.println("返回值 : " + String.valueOf(arr) );
    }
}
```
4.  Collections.sort()
```
/*
public static <T> void sort(List<T> list, Comparator<? super T> c) {
        list.sort(c);
    }
*/
class Solution {
    public boolean wordBreak(String s, List<String> wordDict) {
        Collections.sort(wordDict,new Comparator<String>(){
            public int compare(String s1,String s2){
                if(s1.length()>s2.length()) return 1;
                if(s1.length()==s2.length()) return 0;
                return -1;
            }
        });
        return wordBreak2(s,wordDict);
    }
    public boolean wordBreak2(String s, List<String> wordDict){
        if(s.length() == 0) return true;
        for(String str : wordDict){
            if(s.startsWith(str) && wordBreak2(s.substring(str.length()),wordDict)) return true; 
        }
        return false;
    }
}
```
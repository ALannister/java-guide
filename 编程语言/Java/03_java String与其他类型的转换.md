1. String to char[]

```
public class Test {
    public static void main(String args[]) {
        String str = new String("www.runoob.com");

        System.out.print("返回值 :" );
        System.out.println( str.toCharArray() );
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



# java代码编译及反汇编

### java 代码

```
package test;

public class Math {

	public int math(byte b, short s, char c, int i, long l, float f, Object o, double d) {
		int m1 = 10;
		int m2 = 11;
		int m3 = (m1 + m2) * 10;
		return m3;
	}

	public static void main(String[] args) {
		Math m = new Math();
		int res = m.math((byte)1, (short)1, 'a', 1, 1L, 1.0f, new Object(), 1.0 );
		System.out.println(res);
	}
}
```

### 编译

```
C:\Users\HINOC\Desktop\eclipse-workspace\test\src\test> javac Math.java
```

### 反汇编

```
C:\Users\HINOC\Desktop\eclipse-workspace\test\src\test> javap -v Math.class > math.txt
```

### 字节码文件

```
Classfile /C:/Users/HINOC/Desktop/eclipse-workspace/test/src/test/Math.class
  Last modified 2019-5-17; size 543 bytes
  MD5 checksum e3d2b2fb7599abe246d20e3aac5de55d
  Compiled from "Math.java"
public class test.Math
  minor version: 0
  major version: 52
  flags: ACC_PUBLIC, ACC_SUPER
Constant pool:
   #1 = Methodref          #4.#18         // java/lang/Object."<init>":()V
   #2 = Class              #19            // test/Math
   #3 = Methodref          #2.#18         // test/Math."<init>":()V
   #4 = Class              #20            // java/lang/Object
   #5 = Methodref          #2.#21         // test/Math.math:(BSCIJFLjava/lang/Object;D)I
   #6 = Fieldref           #22.#23        // java/lang/System.out:Ljava/io/PrintStream;
   #7 = Methodref          #24.#25        // java/io/PrintStream.println:(I)V
   #8 = Utf8               <init>
   #9 = Utf8               ()V
  #10 = Utf8               Code
  #11 = Utf8               LineNumberTable
  #12 = Utf8               math
  #13 = Utf8               (BSCIJFLjava/lang/Object;D)I
  #14 = Utf8               main
  #15 = Utf8               ([Ljava/lang/String;)V
  #16 = Utf8               SourceFile
  #17 = Utf8               Math.java
  #18 = NameAndType        #8:#9          // "<init>":()V
  #19 = Utf8               test/Math
  #20 = Utf8               java/lang/Object
  #21 = NameAndType        #12:#13        // math:(BSCIJFLjava/lang/Object;D)I
  #22 = Class              #26            // java/lang/System
  #23 = NameAndType        #27:#28        // out:Ljava/io/PrintStream;
  #24 = Class              #29            // java/io/PrintStream
  #25 = NameAndType        #30:#31        // println:(I)V
  #26 = Utf8               java/lang/System
  #27 = Utf8               out
  #28 = Utf8               Ljava/io/PrintStream;
  #29 = Utf8               java/io/PrintStream
  #30 = Utf8               println
  #31 = Utf8               (I)V
{
  public test.Math();
    descriptor: ()V
    flags: ACC_PUBLIC
    Code:
      stack=1, locals=1, args_size=1
         0: aload_0
         1: invokespecial #1                  // Method java/lang/Object."<init>":()V
         4: return
      LineNumberTable:
        line 3: 0

  public int math(byte, short, char, int, long, float, java.lang.Object, double);
    descriptor: (BSCIJFLjava/lang/Object;D)I
    flags: ACC_PUBLIC
    Code:
      stack=2, locals=14, args_size=9
         0: bipush        10
         2: istore        11
         4: bipush        11
         6: istore        12
         8: iload         11
        10: iload         12
        12: iadd
        13: bipush        10
        15: imul
        16: istore        13
        18: iload         13
        20: ireturn
      LineNumberTable:
        line 6: 0
        line 7: 4
        line 8: 8
        line 9: 18

  public static void main(java.lang.String[]);
    descriptor: ([Ljava/lang/String;)V
    flags: ACC_PUBLIC, ACC_STATIC
    Code:
      stack=11, locals=3, args_size=1
         0: new           #2                  // class test/Math
         3: dup
         4: invokespecial #3                  // Method "<init>":()V
         7: astore_1
         8: aload_1
         9: iconst_1
        10: iconst_1
        11: bipush        97
        13: iconst_1
        14: lconst_1
        15: fconst_1
        16: new           #4                  // class java/lang/Object
        19: dup
        20: invokespecial #1                  // Method java/lang/Object."<init>":()V
        23: dconst_1
        24: invokevirtual #5                  // Method math:(BSCIJFLjava/lang/Object;D)I
        27: istore_2
        28: getstatic     #6                  // Field java/lang/System.out:Ljava/io/PrintStream;
        31: iload_2
        32: invokevirtual #7                  // Method java/io/PrintStream.println:(I)V
        35: return
      LineNumberTable:
        line 13: 0
        line 14: 8
        line 15: 28
        line 16: 35
}
SourceFile: "Math.java"

```

### 为什么对象被new以后会执行dup操作?

​	我们先来看看为dup指令的作用,dup指令可以复制栈顶的一个字再压入栈,也就是把栈顶的内容做个备份。大家知道，JAVA/CLR是完全基于栈的实现，任何操作都是入栈出栈，没有任何寄存器，所以如果要对某一操作数做两次连续操作，那就要复制两次栈顶操作数，比如：

```
int x;
int y = x = 20;
```

​	当常数2被压入栈顶后，它要连续两次store到变量x和y，所以这里编译后肯定有一个dup操作：

```
 bipush  20
 dup
 istore_1
 istore_2
```

​	如果不做dup操作，那么istore_1将20存到内存中的x后，再istore_2要么没有操作数，要么是一个其它的操作数。当然这在编译时对连续操作已经做dup操作了，所以不会真的出现这个情况。
​	那么new 指令后，为什么一定要dup操作呢?
​	因为java代码的new操作编译为虚拟机指令后，虚拟机指令new在堆上分配了内存并在栈顶压入了指向这段内存的地址供任何下面的操作来调用，但是在这个操作数被程序员能访问的操作之前，虚拟机自己肯定要调用对象的<init>方法，也就是如果程序员做一个 Type a = new Type()；其实要连续两次对栈顶的操作数进行操作。其中一次是虚拟机内部自动调用的，这种情况是99%以上存在的，而java 编译器是一种聪明的编译器，所以只要有new操作就优化为将对象的地址操作数DUP，第一次调用invokespecial <init>时会弹出一个，下面一个留给对该对象访问的操作，即使你的代码是:new Type();没有任何引用。有些虚拟机也会先dup（不同版本编译结果不同），然后<init>时弹出一个操作数，后面会立即pop掉被复制的那个操作数。这样的做目的是为了编译优化。
​	有人说那可以直接从栈顶先store到内存中，需要操作的时候再load到栈顶啊，注意在没有<init>操作之前对象对于程序员是不可见的，否则就会访问到残废的对象，所以只能是先<init>然后才能store到内存中。这两步操作的操作数必须都直接是原来已经存在栈中的，所以只能是dup。

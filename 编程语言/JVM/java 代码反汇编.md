### ### java 代码

```
package test;

public class Math {

	public static int a0 = 3;
	public static final long a1 = 5;
	int a2 = 7;

	public int math() {
		int a = 10;
		int b = 11;
		int c = 12;
		int d = (a + b + c) * 10;
		return d;
	}

	public static void main(String[] args) {
		Math m = new Math();
		int res = m.math();
		System.out.println(res);
	}
}
```

### 反汇编

```
C:\Users\HINOC\Desktop\GIT\eclipse-workspace\test\bin\test> javap -c Math.class > math.txt
```

### 字节码文件

```
Classfile /C:/Users/HINOC/Desktop/GIT/eclipse-workspace/test/bin/test/Math.class
  Last modified 2019-5-9; size 899 bytes
  MD5 checksum 40919d988c35681d7a1296854dfb7249
  Compiled from "Math.java"
public class test.Math
  minor version: 0
  major version: 52
  flags: ACC_PUBLIC, ACC_SUPER
Constant pool:
   #1 = Class              #2             // test/Math
   #2 = Utf8               test/Math
   #3 = Class              #4             // java/lang/Object
   #4 = Utf8               java/lang/Object
   #5 = Utf8               a0
   #6 = Utf8               I
   #7 = Utf8               a1
   #8 = Utf8               J
   #9 = Utf8               ConstantValue
  #10 = Long               5l
  #12 = Utf8               a2
  #13 = Utf8               <clinit>
  #14 = Utf8               ()V
  #15 = Utf8               Code
  #16 = Fieldref           #1.#17         // test/Math.a0:I
  #17 = NameAndType        #5:#6          // a0:I
  #18 = Utf8               LineNumberTable
  #19 = Utf8               LocalVariableTable
  #20 = Utf8               <init>
  #21 = Methodref          #3.#22         // java/lang/Object."<init>":()V
  #22 = NameAndType        #20:#14        // "<init>":()V
  #23 = Fieldref           #1.#24         // test/Math.a2:I
  #24 = NameAndType        #12:#6         // a2:I
  #25 = Utf8               this
  #26 = Utf8               Ltest/Math;
  #27 = Utf8               math
  #28 = Utf8               ()I
  #29 = Utf8               a
  #30 = Utf8               b
  #31 = Utf8               c
  #32 = Utf8               d
  #33 = Utf8               main
  #34 = Utf8               ([Ljava/lang/String;)V
  #35 = Methodref          #1.#22         // test/Math."<init>":()V
  #36 = Methodref          #1.#37         // test/Math.math:()I
  #37 = NameAndType        #27:#28        // math:()I
  #38 = Fieldref           #39.#41        // java/lang/System.out:Ljava/io/PrintStream;
  #39 = Class              #40            // java/lang/System
  #40 = Utf8               java/lang/System
  #41 = NameAndType        #42:#43        // out:Ljava/io/PrintStream;
  #42 = Utf8               out
  #43 = Utf8               Ljava/io/PrintStream;
  #44 = Methodref          #45.#47        // java/io/PrintStream.println:(I)V
  #45 = Class              #46            // java/io/PrintStream
  #46 = Utf8               java/io/PrintStream
  #47 = NameAndType        #48:#49        // println:(I)V
  #48 = Utf8               println
  #49 = Utf8               (I)V
  #50 = Utf8               args
  #51 = Utf8               [Ljava/lang/String;
  #52 = Utf8               m
  #53 = Utf8               res
  #54 = Utf8               SourceFile
  #55 = Utf8               Math.java
{
  public static int a0;
    descriptor: I
    flags: ACC_PUBLIC, ACC_STATIC

  public static final long a1;
    descriptor: J
    flags: ACC_PUBLIC, ACC_STATIC, ACC_FINAL
    ConstantValue: long 5l

  int a2;
    descriptor: I
    flags:

  static {};
    descriptor: ()V
    flags: ACC_STATIC
    Code:
      stack=1, locals=0, args_size=0
         0: iconst_3
         1: putstatic     #16                 // Field a0:I
         4: return
      LineNumberTable:
        line 5: 0
        line 6: 4
      LocalVariableTable:
        Start  Length  Slot  Name   Signature

  public test.Math();
    descriptor: ()V
    flags: ACC_PUBLIC
    Code:
      stack=2, locals=1, args_size=1
         0: aload_0
         1: invokespecial #21                 // Method java/lang/Object."<init>":()V
         4: aload_0
         5: bipush        7
         7: putfield      #23                 // Field a2:I
        10: return
      LineNumberTable:
        line 3: 0
        line 7: 4
        line 3: 10
      LocalVariableTable:
        Start  Length  Slot  Name   Signature
            0      11     0  this   Ltest/Math;

  public int math();
    descriptor: ()I
    flags: ACC_PUBLIC
    Code:
      stack=2, locals=5, args_size=1
         0: bipush        10
         2: istore_1
         3: bipush        11
         5: istore_2
         6: bipush        12
         8: istore_3
         9: iload_1
        10: iload_2
        11: iadd
        12: iload_3
        13: iadd
        14: bipush        10
        16: imul
        17: istore        4
        19: iload         4
        21: ireturn
      LineNumberTable:
        line 10: 0
        line 11: 3
        line 12: 6
        line 13: 9
        line 14: 19
      LocalVariableTable:
        Start  Length  Slot  Name   Signature
            0      22     0  this   Ltest/Math;
            3      19     1     a   I
            6      16     2     b   I
            9      13     3     c   I
           19       3     4     d   I

  public static void main(java.lang.String[]);
    descriptor: ([Ljava/lang/String;)V
    flags: ACC_PUBLIC, ACC_STATIC
    Code:
      stack=2, locals=3, args_size=1
         0: new           #1                  // class test/Math
         3: dup
         4: invokespecial #35                 // Method "<init>":()V
         7: astore_1
         8: aload_1
         9: invokevirtual #36                 // Method math:()I
        12: istore_2
        13: getstatic     #38                 // Field java/lang/System.out:Ljava/io/PrintStream;
        16: iload_2
        17: invokevirtual #44                 // Method java/io/PrintStream.println:(I)V
        20: return
      LineNumberTable:
        line 18: 0
        line 19: 8
        line 20: 13
        line 21: 20
      LocalVariableTable:
        Start  Length  Slot  Name   Signature
            0      21     0  args   [Ljava/lang/String;
            8      13     1     m   Ltest/Math;
           13       8     2   res   I
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

### java 代码

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
    int res =m.math();
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
Compiled from "Math.java"
public class test.Math {
  public static int a0;

  public static final long a1;

  int a2;

  static {};
    Code:
       0: iconst_3
       1: putstatic     #16                 // Field a0:I
       4: return

  public test.Math();
    Code:
       0: aload_0
       1: invokespecial #21                 // Method java/lang/Object."<init>":()V
       4: aload_0
       5: bipush        7
       7: putfield      #23                 // Field a2:I
      10: return

  public int math();
    Code:
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

  public static void main(java.lang.String[]);
    Code:
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
}
```

### 为什么对象被new 以后在执行dup操作?

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
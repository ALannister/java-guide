# Java不可变类



#### 不可变类（Immutable Class）：所谓的不可变类是指这个类的实例一旦创建完成后，就不能改变其成员变量值。如JDK内部自带的很多不可变类：Interger、Long和String等。

#### 可变类（Mutable Class）：相对于不可变类，可变类创建实例后可以改变其成员变量值，开发中创建的大部分类都属于可变类。



#### 不可变类的设计原则

>  immutable对象的状态在创建之后就不能发生改变，任何对它的改变都应该产生一个新的对象。

如何在Java中写出Immutable的类？要写出这样的类，需要遵循以下几个原则：

1) Immutable类的==所有的成员都应该是private final的==。通过这种方式保证成员变量不可改变。但只做到这一步还不够，因为如果成员变量是对象，它保存的只是引用，有可能在外部改变其引用指向的值，所以第5点弥补这个不足

2）对象必须被正确的创建，比如：==对象引用在对象创建过程中不能泄露==。

3）只提供读取成员变量的get方法，不提供改变成员变量的set方法，避免通过其他接口改变成员变量的值，破坏不可变特性。

4）==类应该是final的==，保证类不被继承，如果类可以被继承会破坏类的不可变性机制，只要继承类覆盖父类的方法并且继承类可以改变成员变量值，那么一旦子类以父类的形式出现时，不能保证当前类是否可变。

5）如果==类中包含mutable类对象，那么返回给客户端的时候，返回该对象的一个深拷贝，而不是该对象本身==（该条可以归为第一条中的一个特例）



#### 注意

如果将构造器传入的对象直接赋值给成员变量，还是可以通过对传入对象的修改进而导致改变内部变量的值。例如：

```java
public final class ImmutableDemo {  
    private final int[] myArray;  
    public ImmutableDemo(int[] array) {  
        this.myArray = array;		 // wrong  
    }  
}
```


这种方式不能保证不可变性，myArray和array指向同一块内存地址，用户可以在ImmutableDemo之外通过修改array对象的值来改变myArray内部的值。为了保证内部的值不被修改，可以采用深度copy来创建一个新内存保存传入的值。正确做法：

```java
public final class MyImmutableDemo {  
    private final int[] myArray;  
    public MyImmutableDemo(int[] array) {  
        this.myArray = array.clone();   
    }   
}
```



#### String类的不可变实现

String对象在内存创建后就不可改变，不可变对象的创建一般满足以上原则，我们看看String代码是如何实现的。

```java
public final class String
    implements java.io.Serializable, Comparable<String>, CharSequence
{
    private final char value[]; 	/** The value is used for character storage. */
    /** The offset is the first index of the storage that is used. */
    private final int offset;
    /** The count is the number of characters in the String. */
    private final int count;
    private int hash; // Default to 0
    ....
    public String(char value[]) {
         this.value = Arrays.copyOf(value, value.length); // deep copy操作
     }
     public char[] toCharArray() {
        char result[] = new char[value.length];
        System.arraycopy(value, 0, result, 0, value.length);
        return result;
    }
    ...
}
```



#### 通过反射机制改变不可变类的成员变量

如代码所示，可以观察到String类的设计符合上面总结的不变类型的设计原则。虽然String对象将value设置为final，并且还通过各种机制保证其成员变量不可改变。但是还是可以通过反射机制改变其值。例如：

```java
String s = "Hello World"; 	//创建字符串"Hello World"， 并赋给引用s
System.out.println("s = " + s); 	    
//获取String类中的value字段
Field valueFieldOfString = String.class.getDeclaredField("value");
valueFieldOfString.setAccessible(true); 	//改变value属性的访问权限
char[] value = (char[]) valueFieldOfString.get(s);
value[5] = '_'; //改变value所引用的数组中的第5个字符
System.out.println("s = " + s);  			//Hello_World
```


打印结果为：

```java
s = Hello World

s = Hello_World
```

发现String的值已经发生了改变。也就是说，通过反射是可以修改所谓的“不可变”对象的。



#### 不可变类的优缺点

不可变类有两个主要有点，效率和安全。

- 效率

  当一个对象是不可变的，那么需要拷贝这个对象的内容时，就不用复制它的本身而只是复制它的地址，复制地址（通常一个指针的大小）只需要很小的内存空间，具有非常高的效率。同时，对于引用该对象的其他变量也不会造成影响。

  此外，不变性保证了hashCode 的唯一性，因此可以放心地进行缓存而不必每次重新计算新的哈希码。而哈希码被频繁地使用, 比如在hashMap 等容器中。将hashCode 缓存可以提高以不变类实例为key的容器的性能。

- 线程安全

  在多线程情况下，一个可变对象的值很可能被其他进程改变，这样会造成不可预期的结果，而使用不可变对象就可以避免这种情况同时省去了同步加锁等过程，因此不可变类是线程安全的。

当然，不可变类也有缺点：不可变类的每一次“改变”都会产生新的对象，因此在使用中不可避免的会产生很多垃圾。



#### 来源

https://blog.csdn.net/fuzhongmin05/article/details/54880139

https://www.cnblogs.com/zhiheng/p/6653969.html
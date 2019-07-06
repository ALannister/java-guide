# Java反射定义、获取Class的三种方法

### 反射机制的定义：

```

	在运行状态时(动态的)：对于任意一个类，都能够得到这个类的所有属性和方法。

　　　　　　　　　　　　   对于任意一个对象，都能够调用它的任意属性和方法。
　　　　　　　　　　　　      
```

 ### Class类是反射机制的起源，我们得到Class类对象有3种方法：

	以自定义的Person类为例:
	
	第一种：通过类名获得:
	
	Class<?> clazz = Person.class;
	
	第二种：通过类名全路径获得：
	
	Class<?> clazz = Class.forName("Person");
	
	第三种：通过实例对象获得：
	
	Class<?> clazz = new Person().getClass();

### 通过代码总结三种方法的区别：

```

class Person {
    static{
        System.out.println("Person的静态初始化块");
    }
    {
        System.out.println("Person的普通初始化块");
    }
    public Person(){
        System.out.println("Person的构造器");
    }
}

```

---

```

/**
 * 第一种方法获得Class对象
 */
public class ClassTest1 {
    public static void main(String[] args) {
        Class<?> clazz = Person.class;
    }
}

/**
 * 打印结果：
 * 什么都没打印
 * 总结：JVM将使用类Person的类装载器,将类Person装入内存(前提是:类Person还没有装入内存),
 *      不对类Person做类的初始化工作,返回类A的Class的对象
 */
 
```

---

```

/**
 * 第二种方法获得Class对象
 */
public class ClassTest2 {
    public static void main(String[] args) {
        try {
            Class<?> clazz = Class.forName("Person");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

/**
 * 打印结果：
 * Person的静态初始化块
 * 总结：JVM将使用类Person的类装载器,将类Person装入内存(前提是:类Person还没有装入内存),
 *      并对类Person做类的初始化工作,返回类Person的Class的对象
 */

```

---

```

/**
 * 第三种方法获得Class对象
 */
public class ClassTest3 {
    public static void main(String[] args) {
    	Person p = new Person();
        Class<?> clazz = p.getClass();
    }
}

/**Person的构造器
 * 打印结果是：
 * Person的静态初始化块
 * Person的普通初始化块
 * Person的构造器
 * 总结：返回引用p运行时真正所指的对象(因为:子类对象的引用可能会赋给父类对象的引用变量)所属的类的Class的对象；
 *      getClass()方法是Object类的方法，所有的类都继承了Object，因此所有类的对象也都具有getClass()方法。
 */
```

### 建议

	使用类名.class，这样做即简单又安全，因为在编译时就会受到检查，因此不需要置于try语句块中，并且它根除了对forName()方法的调用，所以也更高效。
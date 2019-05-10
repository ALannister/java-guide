# SDK JDK JRE JAR

## SDK

​	SDK全称Software Development Kit，中文意思为 “软件开发工具包”，是一个覆盖范围相当广的名词，可以说辅助开发某一类软件的相关文档、范例和工具的集合都可以叫做 SDK。SDK是一系列文件的集合，他为软件的开发提供一个平台为软件开发使用提供各种API提供便利

## JDK

​	JDK全程为 （Java Development Kit），中文意思 Java开发工具包  是SUN Microsystems针对Java开发员的产品，JDK已经成为使用最广泛的JAVA SDK 。可以认为JDK是SDK的一个子集 。
​	JDK包括了JRE，JAVA工具和JAVA基础类
​	JRE（Java Runtime Environment）包含JVM（Java Virtual Machine [JAVA虚拟机]）标准实现及Java核心类库

## JRE

​	JRE（Java Runtime Environment[Java运行环境]）　是运行基于Java语言编写的程序所不可缺少的运行环境。也是通过它，Java的开发者才得以将自己开发的程序发布到用户手中，让用户使用。
 　　JRE中包含了Java virtual machine（JVM），runtime class libraries和Java application launcher，这些是运行Java程序的必要组件。
 　　与大家熟知的JDK不同，JRE是Java运行环境，并不是一个开发环境，所以没有包含任何开发工具（如编译器和调试器），只是针对于使用Java程序的用户。

## JVM

​	Java Virtual Mechinal(JAVA虚拟机)。JVM是JRE的一部分，它是一个虚构出来的计算机，是通过在实际的计算机上仿真模拟各种计算机功能来实现的。JVM有自己完善的硬件架构，如处理器、堆栈、寄存器等，还具有相应的指令系统。JVM 的主要工作是解释自己的指令集（即字节码）并映射到本地的 CPU 的指令集或 OS 的系统调用。Java语言是跨平台运行的，其实就是不同的操作系统，使用不同的JVM映射规则，让其与操作系统无关，完成了跨平台性。JVM 对上层的 Java 源文件是不关心的，它关注的只是由源文件生成的类文件（ class file ）。类文件的组成包括 JVM 指令集，符号表以及一些补助信息。



![img](https:////upload-images.jianshu.io/upload_images/4622762-f8effb081e6b935e.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/658)

​							SDK  JDK JRE JVM之间的关系图

## JAR

​	JAVA的可执行程序 ，一个可行的Java应用程序，他存储在特别配置的JAR文件中，由

## JDK深入

​	JDK是整个Java的核心，包括Java运行环境JRE、一堆Java工具（javac/java/jdb等）和java基础的类库（Java API、rt.jar等）



![img](https:////upload-images.jianshu.io/upload_images/4622762-513a6830fb10ace5.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/684)

​											JDK结构图

## JDK版本

①SE（J2SE）标准版 ，使我们通常用的一个版本，从JDK5.0开始改名为 JAVA SE
②EE（J2EE）企业版，使用这种JDK开发J2EE应用程序，从JDK5.0开始 改名为 JAVA EE
③ME （J2ME）移动版  主要用于移动设备 嵌入式设备上的Java应用程序，从JDK5.0 开始 改名为 JAVA ME

作者：中華田園雞

链接：https://www.jianshu.com/p/b74e296a6ac9

来源：简书

简书著作权归作者所有，任何形式的转载都请联系作者获得授权并注明出处。
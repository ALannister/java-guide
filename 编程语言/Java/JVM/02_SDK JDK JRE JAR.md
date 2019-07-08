# SDK JDK JRE JAR

![img](assets/855294-20180804235813235-834661084-1562385682160.png)

## SDK

​	SDK全称Software Development Kit，中文意思为 “软件开发工具包”，是一个覆盖范围相当广的名词，可以说辅助开发某一类软件的相关文档、范例和工具的集合都可以叫做 SDK。SDK是一系列文件的集合，它为软件的开发提供一个平台为软件开发使用提供各种API提供便利

## JDK

​	JDK全程为 （Java Development Kit），中文意思 Java开发工具包  是SUN Microsystems针对Java开发员的产品，JDK已经成为使用最广泛的JAVA SDK 。可以认为JDK是SDK的一个子集 。

## JRE

​	JRE（Java Runtime Environment[Java运行环境]）　是运行基于Java语言编写的程序所不可缺少的运行环境。也是通过它，Java的开发者才得以将自己开发的程序发布到用户手中，让用户使用。
 　　JRE中包含了Java virtual machine（JVM），runtime class libraries和Java application launcher，这些是运行Java程序的必要组件。
 　　与大家熟知的JDK不同，JRE是Java运行环境，并不是一个开发环境，所以没有包含任何开发工具（如编译器和调试器），只是针对于使用Java程序的用户。

## JVM

​	Java Virtual Mechinal(JAVA虚拟机)。JVM是JRE的一部分，它是一个虚构出来的计算机，是通过在实际的计算机上仿真模拟各种计算机功能来实现的。JVM有自己完善的硬件架构，如处理器、堆栈、寄存器等，还具有相应的指令系统。JVM 的主要工作是解释自己的指令集（即字节码）并映射到本地的 CPU 的指令集或 OS 的系统调用。Java语言是跨平台运行的，其实就是不同的操作系统，使用不同的JVM映射规则，让其与操作系统无关，完成了跨平台性。JVM 对上层的 Java 源文件是不关心的，它关注的只是由源文件生成的类文件（ class file ）。类文件的组成包括 JVM 指令集，符号表以及一些补助信息。

## JAR

​	一个可执行的jar 文件是一个自包含的 Java 应用程序，它存储在特别配置的JAR 文件中，可以由 JVM 直接执行它而无需事先提取文件或者设置类路径

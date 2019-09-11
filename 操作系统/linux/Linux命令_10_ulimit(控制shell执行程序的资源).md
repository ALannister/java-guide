# ulimit

ulimit命令是查看或者设置当前用户或者进程使用资源的阈值。

![img](assets/20160703012526529.jfif)

上面的各个阈值都可以使用自己对应的选项来显值，比如：

![img](assets/20160703012820390.jfif)

上面中比较常用的有：

1）core file size，即进程产生core文件时的最大size，这里的显示为0，表示进程不能生成core，为了让进程异常退出时可以生成core文件，就需要设置core file size数值为不大于0的正整数。一般设置为unlimited，设置方法如下所示：

![img](assets/20160703013150189.jfif)



2）stack size，即进程的堆栈占用内存的最大size，单位一般是KB，有的递归程序（假定不会无限循环递归）会发生堆栈溢出问题而退出，此时如果设置stack size为unlimted，表示可以无限制地申请堆栈空间并放存后续递归调用的程序的栈帧，再次运行此递归程序时发现，递归程序不会再发生堆栈溢出问题而退出，而是成功执行完毕并且给出了正确的程序结果。由于stack size设置为了unlimited，递归程序可以“无限制”地申请内存空间存放后续调用的函数的栈帧stack-frame，从而可以使得递归程序执行完毕并得出正确程序结果。这个可以自己写程序来验证的，很简单。

![img](assets/20160703014122718.jfif)

系统自动默认stack size为8192，即8MB

然后我们修改(ulimit -Ss 10240)为10MB

最后我们又修改为无限制size（ulimit -Ss unlimited）。



3）open file这个在socket当中比较有用的阈值，这里默认为1024，表示用户可以在进程中打开的文件描述符总数上限为1024个。

### 来源

https://blog.csdn.net/u012421852/article/details/51813591
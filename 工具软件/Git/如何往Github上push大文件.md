## 如何往Github上push大文件

#### 1.先在仓库中添加一个.gitattributes文件

![1557668662533](assets\1557668662533.png)

#### 2.添加以下内容，*.zip 和 *.db为大文件的后缀

![1557668746914](assets\1557668746914.png)

#### 3.先单独将.gitattributes文件commit，之后大文件就可以像普通文件一样commit了
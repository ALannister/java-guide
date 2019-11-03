# Main Branch

 

### master

 

主分支，代表着部署（生产）环境最新版本的代码状态，即代码始终与部署环境最新版本代码保持一致；

 

### develop

 

开发分支，代表着即将发布的下一个版本的代码状态；也可以理解为“整合”分支，开发新特性或修复Bug过程中产生的新代码需要定期合并至开发分支，用于“每日”构建。

 

当开发分支（develop）中的代码稳定到一个可发布的状态时，需要被合并至主分支（master），由主分支创建相应版本（Tag）。

 

# Supporting Branch

 

### Feature

 

特性分支，用于开发新的功能特性，生命周期如下：

 

（1）需要开发新的功能特性时，从开发分支创建一个或多个特性分支；


```
    git checkout -b myfeature develop
```


（2）新的功能特性开发完成时，特性分支需要合并至开发分支；


```
    git checkout develop

    git merge --no-ff myfeature
```


（3）删除特性分支；


```
    git branch -d myfeature
```


（4）推送开发分支至远程版本库；


```
    git push origin develop
```


注：特性分支仅存在于开发者的本地环境中，一般不将其推送至远程版本库。

 

### Release

 

发布分支，特定版本发布之前的“准备”分支，用于测试或Bug修复，生命周期如下：

 

（1）某一个版本的功能特性全部开发完成（即该版本的所有功能特性分支已全部合并至开发分支）之后、正式发布之前需要创建相应的发布分支；


```
    git checkout -b release-1.2 develop
```


注：之所以需要创建发布分支，是因为发布分支一旦创建完成，发布分支测试或修复Bug的过程中，开发分支可同时用于下一个版本的代码开发。

 

（2）更新、提交版本信息；


```
    ./bump-version.sh 1.2

    git commit -m "Bumped version number to 1.2"
```


bump-version.sh只是一个示例脚本，用于更新版本文件中的版本号，也可以根据实际情况手动更新版本号。

 

（3）测试或Bug修复，均在此发布分支中进行；

 

（4）测试或Bug修复完成之后，合并至主分支、创建版本、推送至远程版本库；


```
    git checkout master

    git merge --no-ff release-1.2

    git push origin master

    

    git tag -m "Version 1.2" 1.2

    git push origin 1.2
```


（5）也需要合并至开发分支、推送至远程版本库；


```
    git checkout develop

    git merge --no-ff release-1.2

    git push origin develop

```

（6）删除发布分支；


```
    git branch -d release-1.2
```


注1：为什么不是发布分支合并至开发分支，再由开发分支合并至主分支？这是因为发布分支在测试或Bug修复过程中，开发分支可能会参与下一个版本的代码开发，这样做当前版本的代码中可以混合有下一个版本尚为完成的代码。

 

注2 ：发布分支合并至主分支及开发分支时，由于涉及到版本号更新，因具体方式不同，可能会出现冲突；合并至主分支时，以发布分支为主；合并至开发分支时，以开发分支为主。

 

### Hotfix

 

修复分支，用于修复已发布版本中存在的Bug，生命周期如下：

 

（1）某一个已发布版本存在Bug时，需要从相应版本创建修复分支，修复Bug及测试；


```
    git checkout -b hotfix-1.2.1 1.2
```


（2）更新、提交版本信息；


```
    ./bump-version.sh 1.2.1

    git commit -m “Bumped version number to 1.2.1”
```


（3）修复Bug及测试完成之后，创建新版本，并推送至远程版本库；


```
    git tag -m "Version 1.2.1” 1.2.1

    git push origin 1.2.1
```


（4）也需要合并至开发分支，并推送至远程版本库；


```
    git checkout develop

    git merge --no-ff hotfix-1.2.1

    git push origin develop
```


（5）删除修复分支；


```
    git branch -d hotfix-1.2.1
```


注1：修复分支（1）与（3）原文描述不相符，主要出于以下几个方面的考虑：

 

（1）主分支（master）仅维护最新版本代码；

（2）部署环境可能同时使用多个版本代码，且版本之间存在代码不兼容的情况；

（3）Bug可能出现在比较“旧”的版本中；

 

如果任何一个版本的Bug修复都从主分支创建修复分支，然后再合并至主分支，可能会导致代码混乱。

 

注2：合并至开发分支可以使得后续新发布的版本中不再包含已修复的Bug。

 

注3：修复特定版本的Bug时，需要在“大”版本号（1.2）的基础之上创建“小”版本号。

 

### Code Deploy

 

代码部署，并不在原文讨论范围之内，但也是非常重要的一点，这里仅描述一下自己的方式：


```
git clone <repo>
//克隆远程版本库这一个命令就可以了

git pull

git checkout <tag>
```


注：可以通过版本文件中的版本号确认具体版本信息。
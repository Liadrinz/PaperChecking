# PaperChecking

## 说明

### PaperChecking.java

MapReduce建立倒排索引

### CheckRepeat.java

MapReduce批量查重

### Search.java

普通Java查重

## MapReduce运行步骤

### 编译

Build > Build Project

### 打包

File > Project Structure > Artifacts > Jar > From modules with dependencies...

### 运行

hadoop jar PaperChecking.jar paper.PaperChecking <输入路径> <输出路径>

## Search.java运行

java -jar Search.jar <输入txt文章> <输出文件名>

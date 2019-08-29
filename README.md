# Gif_Compress_Demo

gif图片最强加载方式（android-gif-drawable改版，其实实质使用了gifLib）

### 实现方式
- 下载android-gif-drawable 1.2.17，copy c代码放置cpp目录 
- gif.h文件加入native方法的头文件，gif.c
  文件写入openFile，getWidth，getHeight
- bitmap.c写入renderFile
- cmake修改android_gif



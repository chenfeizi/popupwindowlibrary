# popupwindowlibrary
 
## 使用

```java

allprojects {
    repositories {
        jcenter()
        maven { url 'https://jitpack.io' }
    }
}

// 引用 
compile 'com.github.chenfeizi:popupwindowlibrary:v1.5'

PopupUtils.getInstance()
           .setActivity(MainActivity.this)          //
           .setInfoList(list)                       //数据集合
           .setIds(R.drawable.f1)                   //选择背景
           .setAnimation(true)                      //是否开启动画
            //默认是上进下出动画
            //R.style.top_down 上进下出
            //R.style.down_top 上出下进
           .setMyAnimationStyle(R.style.down_top)   //自定义动画'
           .setAdapter(adapter)                     //设置自己的适配器
           .init()                                  //初始化
           .showPopupWindow(textView);              //显示PopupWindow


```

























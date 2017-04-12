# BifacialView
![Showcase](https://github.com/pavel163/BifacialView/blob/master/media/bifacialview1.gif)
![Showcase](https://github.com/pavel163/BifacialView/blob/master/media/bifacialview2.gif)

## Gradle

```gradle
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}
```

```gradle
dependencies {
    compile 'com.github.pavel163:BifacialView:1.0.1'
}
```

## How to use
```xml
<com.ebr163.bifacialview.view.BifacialView
    android:layout_centerInParent="true"
    android:layout_width="match_parent"
    android:layout_height="256dp"
    app:drawableLeft="@drawable/left"
    app:drawableRight="@drawable/right"/>
```

To install the picture programmatically use:
```java
    bifacialView.setDrawableLeft(drawableLeft);
    bifacialView.setDrawableRight(drawableRight);
```
## License
MIT

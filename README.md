# BifacialView
![Showcase](https://github.com/pavel163/BifacialView/blob/master/media/bifacialview1.gif)
![Showcase](https://github.com/pavel163/BifacialView/blob/master/media/bifacialview3.gif)

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
    compile 'com.github.pavel163:BifacialView:1.2.0'
}
```

## How to use
```xml
<com.ebr163.bifacialview.view.BifacialView
    android:layout_width="match_parent"
    android:layout_height="226dp"
    app:drawableLeft="@drawable/left"
    app:drawableRight="@drawable/right"
    app:arrowVisibility="true"
    app:leftText="before"
    app:rightText="after"
    app:textSize="20sp"
    app:touchMode="delimiter"
    app:delimiterColor="@android:color/white"
    app:arrowColor="@android:color/holo_orange_light"
    app:textColor="@android:color/holo_orange_light" />
```

To install the picture programmatically use:
```java
    bifacialView.setDrawableLeft(drawableLeft);
    bifacialView.setDrawableRight(drawableRight);
```
## License
MIT

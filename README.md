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
    compile 'com.github.pavel163:BifacialView:1.3.2'
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

You can use xml attributes to control the appearance of arrows
```xml
<com.ebr163.bifacialview.view.BifacialView
    android:id="@+id/view"
    android:layout_width="match_parent"
    android:layout_height="256dp"
    android:layout_centerInParent="true"
    app:arrowColor="@color/colorAccent"
    app:arrowCornerRadius="2dp"
    app:arrowFill="false"
    app:arrowStrokeWidth="3dp"
    app:arrowWidth="27dp"
    app:arrowHeight="41dp"
    app:arrowMargin="10dp"
    app:arrowVisibility="true"
    app:delimiterWidth="3dp"
    app:drawableLeft="@drawable/left"
    app:drawableRight="@drawable/right"
    app:leftText="before"
    app:rightText="after"
    app:textColor="@color/colorPrimary"
    app:textSize="20sp"
    app:delimiterPadding="20dp"/>
```
## TouchMode

ALl - reacts to the touch in any part of view.
DELIMITER - reacts to a touch in the delimiter area.

### For correct operation in ViewPager and RecyclerView, use TouchMode = DELIMITER

## License
MIT

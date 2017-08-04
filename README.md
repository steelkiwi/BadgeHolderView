# BadgeHolderView

[![Made in SteelKiwi](https://github.com/steelkiwi/IncrementProductView/blob/master/assets/made_in_steelkiwi.png)](http://steelkiwi.com/blog/)
[ ![Download](https://api.bintray.com/packages/soulyaroslav/maven/badge-holder-view/images/download.svg) ](https://bintray.com/soulyaroslav/maven/badge-holder-view/_latestVersion)

# Description

Custom ViewGroup for browse badge with count of some products

# View

![Animation](https://github.com/steelkiwi/BadgeHolderView/blob/master/assets/animation.gif)

# Download

For project API 21+.

## Gradle

```gradle
compile 'com.steelkiwi:badge-holder-view:1.1.0'
```

# Usage

First of all, need add BadgeHolderView to your xml layout. Also inside it you can provide ImageView with your icon

```xml
<com.steelkiwi.library.view.BadgeHolderLayout
    android:id="@+id/view"
    android:layout_width="40dp"
    android:layout_height="40dp"
    android:layout_centerInParent="true"
    app:bhl_default_badge_background="@color/color3"
    app:bhl_text_color="@android:color/white"
    app:bhl_text_size="12sp"
    app:bhl_badge_radius="10dp"
    app:bhl_text_font="Pacifico.ttf">

    <ImageView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        app:srcCompat="@drawable/test_drawable"/>
</com.steelkiwi.library.view.BadgeHolderLayout>
```

You can customize view, through this attributes

* app:bhl_default_badge_background - badge background
* app:bhl_text_color - badge text color
* app:bhl_text_size - badge text size
* app:bhl_badge_radius - badge corner radius
* app:bhl_text_font - font type for text

You can interact with BadgeHolderView through interface below

```java
    void increment(); // increment badge count with animation
    void decrement(); // decrement badge count with animation
    void setBadgeBackground(int color); // set badge background color if need
    void setCount(int count); // set start count of the badge
    void setCountWithAnimation(int count); // set count value with animation
    void reset(); // reset badge count
    int getCount(); // get current badge count
```

# License

```
Copyright © 2017 SteelKiwi, http://steelkiwi.com

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
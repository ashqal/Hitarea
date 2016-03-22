# Hitarea
Designates another view to serve as the hit area for a view in android layout file.
[![](https://jitpack.io/v/ashqal/Hitarea.svg)](https://jitpack.io/#ashqal/Hitarea)

## Demo Preview
![DemoPreview](https://raw.githubusercontent.com/ashqal/Hitarea/master/screenshot/HitareaPreview.gif)

## Download
```java
allprojects {
    repositories {
        ...
        maven { url "https://jitpack.io" }
    }
}
```
```java
dependencies {
        compile 'com.github.ashqal:Hitarea:1.0.0'
}
```

## Usage
### Hitarea
set `app:targetId` attribute in `com.asha.Hitarea` to serve as the hit area for a view.
```java
<FrameLayout
    android:layout_width="match_parent"
    android:layout_height="wrap_content">
    <Button
        android:id="@+id/buttonTest"
        android:text="list view"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content" />
    <com.asha.Hitarea
        app:debug="true"
        app:targetId="@id/buttonTest"
        android:layout_width="match_parent"
        android:layout_height="50dp" />
</FrameLayout>
```

### HitareaWrapper
`com.asha.HitareaWrapper` is a subclass of `RelativeLayout`, and it will be regard as hit area for a child view which set tag=`@string/tag_hitarea`.
```java
<com.asha.HitareaWrapper
        app:debug="true"
        android:layout_marginTop="5dp"
        android:layout_width="200dp"
        android:layout_height="200dp">
    <com.asha.hitarea.DemoView
        android:tag="@string/tag_hitarea"
        android:layout_gravity="center"
        android:background="#CCCCCC"
        android:layout_width="100dp"
        android:layout_height="100dp" />
    <Button
        android:text="other"
        android:layout_alignParentRight="true"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content" />
</com.asha.HitareaWrapper>
```

### Debug Mode
set attribute `app:debug=false` in `com.asha.Hitarea` or `com.asha.HitareaWrapper`, and the hit area will be transparent.

##LICENSE
```
Copyright 2015 Asha

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
# ZGallery
[![](https://jitpack.io/v/simonebortolin/ZGallery.svg)](https://jitpack.io/#simonebortolin/ZGallery)

Android 3rd party library to make implementing galleries more easier

ZGallery is useful to avoid writing the same code to manage small or large image galleries


It includes two activities : 
* For displaying a list of images in `GridLayout` using `RecyclerView`.
* For displaying images in a `ViewPager` with a nice scroll `HorizontalList` and nice zooming gestures **Thanks for [PhotoView Library](https://github.com/chrisbanes/PhotoView)** 

## Screenshots
<a href="https://github.com/simonebortolin/ZGallery/blob/master/image_1.png"><img src="https://github.com/simonebortolin/ZGallery/blob/master/image_1.png" alt="" width="200px"></a>
<a href="https://github.com/simonebortolin/ZGallery/blob/master/image_2.png"><img src="https://github.com/simonebortolin/ZGallery/blob/master/image_2.png" alt="" width="200px"></a>
<a href="https://github.com/simonebortolin/ZGallery/blob/master/image_3.png"><img src="https://github.com/simonebortolin/ZGallery/blob/master/image_3.png" alt="" width="200px"></a>
<a href="https://github.com/simonebortolin/ZGallery/blob/master/image_4.png"><img src="https://github.com/simonebortolin/ZGallery/blob/master/image_4.png" alt="" width="200px"></a>
<a href="https://github.com/simonebortolin/ZGallery/blob/master/image_5.png"><img src="https://github.com/simonebortolin/ZGallery/blob/master/image_5.png" alt="" width="200px"></a>
<a href="https://github.com/simonebortolin/ZGallery/blob/master/image_6.png"><img src="https://github.com/simonebortolin/ZGallery/blob/master/image_6.png" alt="" width="200px"></a>
<a href="https://github.com/simonebortolin/ZGallery/blob/master/image_7.png"><img src="https://github.com/simonebortolin/ZGallery/blob/master/image_7.png" alt="" width="200px"></a>



## How it works
### Grid List Builder

Simply with a very nice builder you will find it done.
```java
ZGrid.with(this, /*your string arraylist of image urls*/)
                .setToolbarColorResId(R.color.colorPrimary) // toolbar color
                .setStatusbarColorResId(R.color.colorPrimaryDark) // status color
                .setTitle("Zak Gallery") // toolbar title
                .setToolbarTitleColor(ZColor.WHITE) // toolbar title color
                .setSpanCount(3) // colums count
                .setGridImgPlaceHolder(R.color.colorPrimary) // color placeholder for the grid image until it loads
                .show();
```

```kotlin
ZGrid.with(this, /*your string arraylist of image urls*/)
                .setToolbarColorResId(R.color.colorPrimary) // toolbar color
                .setStatusbarColorResId(R.color.colorPrimaryDark) // status color
                .setTitle("Zak Gallery") // toolbar title
                .setToolbarTitleColor(ZColor.WHITE) // toolbar title color
                .setSpanCount(3) // colums count
                .setGridImgPlaceHolder(R.color.colorPrimary) // color placeholder for the grid image until it loads
                .show()
```

### Gallery Builder

```java
ZGallery.with(this, /*your string arraylist of image urls*/)
                .setToolbarTitleColor(ZColor.WHITE) // toolbar title color
                .setStatusbarColorResId(R.color.colorPrimaryDark) // status color
                .setGalleryBackgroundColor(ZColor.WHITE) // activity background color
                .setToolbarColorResId(R.color.colorPrimary) // toolbar color
                .setTitle("Zak Gallery") // toolbar title
                .show();
```

```kotlin
ZGallery.with(this, /*your string arraylist of image urls*/)
                .setToolbarTitleColor(ZColor.WHITE) // toolbar title color
                .setStatusbarColorResId(R.color.colorPrimaryDark) // status color
                .setGalleryBackgroundColor(ZColor.WHITE) // activity background color
                .setToolbarColorResId(R.color.colorPrimary) // toolbar color
                .setTitle("Zak Gallery") // toolbar title
                .show()
```
                
## Installation

Add this to your **root** build.gradle file (not your module build.gradle file) :
```java
allprojects {
  repositories {
    ...
    maven { url "https://jitpack.io" }
  }
}
```

Add this to your module `build.gradle` file:
```java
dependencies {
  ...
    implementation 'com.github.simonebortolin:ZGallery:0.5'
    implementation 'com.github.bumptech.glide:glide:4.7.1'
    annotationProcessor 'com.github.bumptech.glide:compiler:4.7.1'
    implementation 'com.github.chrisbanes:PhotoView:2.1.4'
}
```

## Credits


I thank all the authors of the various commits that I have included in my fork


## License

      Copyright 2016 mzelzoghbi
      Copyright 2018-2020 Simone Bortolin

      Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at

      http://www.apache.org/licenses/LICENSE-2.0

      Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.

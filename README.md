# PanelList

PanelList is a simple library for displaying massive data.

The original motivation of developing this library is that I wanna the same chart on my phone screen just like what I see when I`m using Microsoft Excel.

When you scroll up and down, the column title on the left will scroll at same speed while the raw title is pinned down at top of the screen. When you scroll left and right, the raw title will scroll at same speed while the column title is pinned down on the left.


# Using PanelList in your application
Step 1. Add the JitPack repository to your build file

Add it in your root build.gradle at the end of repositories:
```gradle
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
Step 2. Add the dependency in your module gradle
```gradle
dependencies {
	        compile 'com.github.z3896823:PanelList:v1.0'
	}
```

# Get started





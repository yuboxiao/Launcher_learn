# Launcher_learn
学习目标：
1、敲完tab+fragment的布局

2、敲完metro Fragment

3、**学习下TvRecycleView的内部机制**





----------

#知识点
1、  android:supportsRtl="true"
从Android 17才开始的 是否支持布局从右到左

##2、自定义控件TvTabLayout

###2.1  注解的值约束
    
    public void setScaleValue(@FloatRange(from = 1.0) float value) {
    	mScaleValue = value;
    }


###2.2 gradle.properties的配置
    
    # Project-wide Gradle settings.
    APPLICATION_ID=com.whut.mytvrecycleview
    #添加ndk支持(按需添加)
    android.useDeprecatedNdk=flase
    # 应用版本名称
    VERSION_NAME=1.0.0
    # 应用版本号
    VERSION_CODE=1
    # 支持库版本
    SUPPORT_LIBRARY=24.2.1
    # MIN_SDK_VERSION
    ANDROID_BUILD_MIN_SDK_VERSION=19
    # TARGET_SDK_VERSION
    ANDROID_BUILD_TARGET_SDK_VERSION=26
    # BUILD_SDK_VERSION
    ANDROID_BUILD_SDK_VERSION=26
    # BUILD_TOOLS_VERSION
    ANDROID_BUILD_TOOLS_VERSION=28.0.1
    
build.gradle的配置

    android {
	    compileSdkVersion project.ANDROID_BUILD_SDK_VERSION as int
	    buildToolsVersion project.ANDROID_BUILD_TOOLS_VERSION
	    defaultConfig {
	    applicationId  project.APPLICATION_ID
	    versionCode project.VERSION_CODE as int
	    versionName project.VERSION_NAME
	    minSdkVersion project.ANDROID_BUILD_MIN_SDK_VERSION as int
	    targetSdkVersion project.ANDROID_BUILD_TARGET_SDK_VERSION as int
    }

    buildTypes {
	    release {
		    minifyEnabled false
		    proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'
	    	}
    	}
    }
    


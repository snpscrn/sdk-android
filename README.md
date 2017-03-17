# Snapscreen SDK Description

## Requirements

We assume that you already have an Android project in Android Studio or another Android IDE. The SDK runs on devices with Android 4.0.3, 4.0.4 (API Level 15) or later, but you need to build your app with Android SDK 7.0 (Level 24) or later for the integration with Snapscreen SDK.

## Setup

We recommend integration of our compiled library into your project using Android Studio and Gradle.

### 1. Obtain an application client id and client secret

Snapscreen Team should provide you required client id and client secret.

### 2. Get the SDK

Add the SDK to your app module's dependencies in Android Studio by adding the following line to your

```
dependencies { ... }
```

configuration:

```
compile 'com.snapscreen.mobile:snapscreen-sdk'
```

Additionally you need to set your compiler options to Java 8 by adding the following in the android section of your build.gradle

```
compileOptions {
	sourceCompatibility JavaVersion.VERSION_1_8
	targetCompatibility JavaVersion.VERSION_1_8
}
```

And you need to enable Jack support by adding the following to defaultConfig section in the android section of your build.gradle:

```
jackOptions {
    enabled true
}
```

So your build.gradle should at least contain the following parts:

```
android {
    defaultConfig {
        jackOptions {
            enabled true
        }
    }

    compileOptions {
        sourceCompatibility JavaVersion.VERSION_1_8
        targetCompatibility JavaVersion.VERSION_1_8
    }
}

dependencies {
    compile 'com.snapscreen.mobile:snapscreen-sdk:1.0.0'
}
```

As an alternative you can directly add the .aar file to your project. In this case we recommend you copy the .aar file into a subfolder named libs and modify your build.gradle to look like this:

```
repositories {
    flatDir {
        dirs './libs'
    }
}

android {
    defaultConfig {
        jackOptions {
            enabled true
        }
    }

    compileOptions {
        sourceCompatibility JavaVersion.VERSION_1_8
        targetCompatibility JavaVersion.VERSION_1_8
    }
}

dependencies {
    compile 'com.snapscreen.mobile:snapscreen-sdk:release@aar'
}
```

### 3. Integrate Snapscreen SDK

Open your class that extends Application class.

Add line that initialize Snapscreen SDK.

```
public class SnapApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SnapscreenKit.init(this, {CONSUMER_KEY}, {SECRET}, {connectToTestEnvironment});
    }
}
```

## Snapping TV content

### 1. Start SnapActivity with configuration

```
SnapConfiguration configuration = new SnapConfiguration();
configuration.setSearchForTvResults(true);
context.startActivityForResult(SnapActivity.getIntentForContext(getContext(), configuration), requestCode);
```

### 2. Get the result of snapping

```
class YourActivity extends Activity {
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULTS_CODE && resultCode == Activity.RESULT_OK) {
            SearchResult searchResult = data.getParcelableExtra("result");
            if (searchResult.getTvSearchResult()) {
                // Handle TV Search results
            }
        }
    }
}
```

## Snapping Sports Content

### 1. Start SnapActivity with configuration

```
SnapConfiguration configuration = new SnapConfiguration();
configuration.setSearchForTvResults(true);
context.startActivityForResult(SnapActivity.getIntentForContext(getContext(), configuration), requestCode);
```

### 2. Get the result of snapping

```
class YourActivity extends Activity {
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == <requestCode you passed to activity> && resultCode == Activity.RESULT_OK) {
            SearchResult searchResult = data.getParcelableExtra("result");
            if (searchResult.getSportsMatchSearchResult()) {
                // Handle sports results
            }
        }
    }
}
```

## Snapping Advertisements

### 1. Start SnapActivity with configuration

```
 SnapConfiguration configuration = new SnapConfiguration();
 configuration.setSearchForAdvertisements(true);
 context.startActivityForResult(SnapActivity.getIntentForContext(getContext(), configuration), requestCode);
```

### 2. Get the result of snapping

```
class YourActivity extends Activity {
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == <requestCode you passed to activity> && resultCode == Activity.RESULT_OK) {
            SearchResult searchResult = data.getParcelableExtra("result");
            if (searchResult.getAdvertisementSearchResult()) {
                // Handle advertisement results
            }
        }
    }
}
```

## Web Search

In order to perform searches for web content first acquire an instance of the WebSearchService:


```
WebSearchService webSearch = SnapscreenKit.getInstance().getWebSearchService();
```

After you have acquired the WebSearchService, by calling one of the search methods. SnapscreenKit internally uses Retrofit 2 and exposes RxJava methods that return Observables for making web searches:

```
SnapscreenKit.getInstance().getWebSearchService().searchSites("Search term", "locale", "channelCode", SnapscreenKit.getInstance().getCurrentSnapscreenTimestamp(), <page>, <pagesize>)
    .subscribeOn(Schedulers.io())
    .observeOn(AndroidSchedulers.mainThread())
    .unsubscribeOn(Schedulers.io())
    .subscribe(new Subscriber<WebSearchResponse>WebSearchSiteResult>>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onNext(WebSearchResponse<WebSearchSiteResult> response) {
            if (response.getWebSearchEntries() != null) {
                // Do something with results
            } else {
            }
        }
    });
```

Use searchSites(...) method for searching web-pages, searchImages(...) - for images, searchVideos(...) - for
videos.

## Displaying Web Search Results

When you are displaying a web search result, you need to use the visitUrl of the corresponding WebSearchResult to resolve the actual result page and also have Snapscreen track the visit to this result. This is necessary to correctly provide feedback to Snapscreen in order to provide ranking and priorization for the web results.

## Using with a simulator or emulator

For snapping SnapscreenKit has dependencies to native libraries which are only available on actual devices. In order to still be able to develop the rest of your application and all features of SnapscreenKit not related to snapping, you need to add the following configuration in the android section of your application's build.gradle. The universalApk option set to true makes sure that when you are building your application, a universal APK is generated that contains all APK splits just like without the splits option.

```
splits {
    abi {
        enable true
        reset()
        include 'armeabi-v7a', 'mips', 'x86'
        universalApk true
    }
}
```

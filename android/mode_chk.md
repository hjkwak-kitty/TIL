# Android Debug Vs Release Build Check in Running Code

1. [ ApplicationInfo](https://developer.android.com/reference/android/content/pm/ApplicationInfo) (since API Level 1)
  ~~~
  boolean InDebug=(getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE)!=0;
  ~~~
  ~~~
  if(appInfo!=null) {
    if( (appInfo.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0)
        ((TextView)findViewById(R.id.textView2)).setText("TRUE");
    else
        ((TextView)findViewById(R.id.textView2)).setText("FALSE");
    }
  ~~~
  * 결과가 영이 아니면 디버그 모드.
  * 과거에는 Medifest file에서 android:debuggable="false" 오버라이딩 가능했지만 지금은 안됨.(warning)
  
2.  [BuildConfig](https://developer.android.com/studio/build/gradle-tips#share-custom-fields-and-resource-values-with-your-app-code) (subsequent SDK releases)
    ~~~
    if(BuildConfig.DEBUG) {
    ((TextView)findViewById(R.id.textView4)).setText("TRUE");
    } else {
        ((TextView)findViewById(R.id.textView4)).setText("FALSE");
    }
    ~~~
   * gradle에서 하드코딩할 경우 그대록 작동- 가상장치 실기기 테스트 시 요긴


* [참고](https://tekeye.uk/android/examples/android-debug-vs-release-build)
# Splash Screen

* 기존에는 loadingActivity 생성 후 Handler.postDelayed를 이용하여 일정 시간 지연 함.
    * 문제: 1. 적당한 시간은? 2. Cold start blank화면 후 로딩 화면 보임.


* 개선된 방법 > 레이아웃 아닌 백그라운드 테마 이용
    * splash_background.xml res/drawable에 생성
        ~~~
        <?xml version="1.0" encoding="utf-8"?>
        <layer-list xmlns:android="http://schemas.android.com/apk/res/android">

            <item android:drawable="@android:color/holo_blue_bright" />

            <item>
                <bitmap android:src="@mipmap/ic_launcher_round"
                    android:gravity="center" />
            </item>

        </layer-list>
        ~~~

    * value/styles.xml 에 splash theme 생성해줌
        ~~~
        <!-- Splash theme. -->
        <style name="SplashTheme" parent="Theme.AppCompat.NoActionBar">
            <item name="android:windowBackground">@drawable/splash_background</item>
        </style>   
        ~~~

    * SplashActivity.class 생성
        * java
        ~~~
        public class SplashActivity extends AppCompatActivity {

            @Override
            protected void onCreate(@Nullable Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);

                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);

                finish();
            }
        }   
        ~~~
        * kotlin
        ~~~
        class SplashActivity : AppCompatActivity() {

            override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)
            //        setContentView(R.layout.activity_splash2)
                    val intent = Intent(this, MainActivity::class.java).apply {
                        //            data = Uri.parse(fileUrl)
                    }
                    startActivity(intent)
                    finish()
            }
        }
        ~~~
    * AndroidManifest.xml 설정해줌.
        ~~~
         <activity android:name=".SplashActivity"
        android:theme="@style/SplashTheme">
        <intent-filter>
            <action android:name="android.intent.action.MAIN" />

            <category android:name="android.intent.category.LAUNCHER" />
        </intent-filter>
        </activity>
        <activity android:name=".MainActivity" />
        ~~~
  
  * Android - Splash (Portrait)

    - LDPI: 200 x 320

    - MDPI: 320 x 480

    - HDPI: 480 x 800

    - XHDPI: 720 x 1280

    - XXHDPI: 960 x 1600

    - XXXHDPI: 1280 x 1920


* 참고
    * [개선된로딩화면](http://dudmy.net/android/2017/04/09/improved-loading-screen/)
    * [Android / iOS Icon & Splash Image Size 정리](https://asata.pe.kr/517)
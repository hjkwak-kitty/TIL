
* progurad 적용 시, gson이랑 사용하면 mapping 안되는 현상 있음.

* option 1 (keep class) 난독화 제외

    // all classes in a package

    -keep class com.example.app.json.** { *; }

    // or a specific class

    -keep class com.example.app.json.SpecificClass { *; }


* option 2 (use @SerializedName):
    ~~~
    public class YourJsonClass{
    @SerializedName("name") String username;

    public MyClass(String username) {
        this.username = username;
    }
    }

    ~~~

* innerClass 도 때때로 동일한 이슈가 발생한다고 함
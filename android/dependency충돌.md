
### library 버전 충돌시

* ./gradlew :app:dependencies 로 찾아서 해결함


### No toolchains found in the NDK toolchains folder for ABI with prefix: mips64el-linux-android

* 안드로이드에서 사용되는 NDK 버전이 올라가면서 MIPS형 CPU에 대한 지원이 중단됨. build.gradle에 지정되어있는 gradle 버전이 낮으면 안드로이드 스튜디오가 MIPS에 대한 정보를 계속 찾으려고 하다가 위와 같은 에러를 내게 됨. gradle 버전을 3.1.4 나 그 이상으로 설정해준 뒤 안드로이드 스튜디오를 껐다가 다시 실행하면 문제가 해결.
    ~~~
        dependencies {
            classpath 'com.android.tools.build:gradle:3.1.4'
        }
    ~~~

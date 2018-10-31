
# .gitlab-ci.yml Sample

image: openjdk:8-jdk  // 사용하는 도커이미지

variables:   // 사전 변수 설정
  ANDROID_COMPILE_SDK: "26"
  ANDROID_BUILD_TOOLS: "27.0.2"
  ANDROID_SDK_TOOLS: "26.0.1"
 
cache:  // 캐시설정
  paths:  // 캐시하는 파일 경로
   - .gradle/. 
   - app/build/
   - bankPlayer/build/
   - build/
   - android-sdk-linux/
   - android-sdk-tools.zip //sdkTool
   - .gradle/wrapper
   - .gradle/caches // 이게 없으면 빌드를 처음부터 다시함

before_script:
//sdk툴 다운로드
  - mkdir -p android-sdk-linux. 
  - test ! -f android-sdk-tools.zip && wget --quiet --output-document=android-sdk-tools.zip https://dl.google.com/android/repository/sdk-tools-linux-3859397.zip && unzip android-sdk-tools.zip -d android-sdk-linux  // 툴 있으면 스킵 함.
  - echo y | $PWD/android-sdk-linux/tools/bin/sdkmanager --update
  - export ANDROID_HOME=$PWD/android-sdk-linux
  - export PATH=$PATH:$PWD/android-sdk-linux/platform-tools/
  - chmod +x ./gradlew
  - export GRADLE_USER_HOME=`pwd`/.gradle.   //빌드 파일 경로 변경

stages:  //조건 설정 가능 예를 들 면 디벨롭일때 등
  - build
  - test

build: //빌드함
  stage: build
  script:
    - cd applefile
    - ./gradlew assembleRelease crashlyticsUploadDistributionRelease
  artifacts:  // 빌드 후 성공하면 apk파일 저장할 수 있게 함.
    name: "apk_file"
    when: on_success
    paths:
    - app/release/app-release.apk

unitTests: // 유닛 테스트
  stage: test
  script:
    - cd applefile
    - ./gradlew test

functionalTests:// 아직 안해봄





* 패브릭 베타 연동 : 
  그라들 파일에 설정 : 앱 그라들에 
  안드로이드 부분에 이거 넣어주고 빌드할 때 ./gradlew assembleRelease crashlyticsUploadDistributionRelease 명령어 사용. 
  키암호설정 자동 입력 가능하게 해줘야 다른 설정 없이도 매끄럽게 됨.

    productFlavors {
          ext.betaDistributionReleaseNotes="Release Notes for this build."
          ext.betaDistributionGroupAliases="Tester"
  //        ext.betaDistributionEmails="BetaUser@yourcompany.com, BetaUser2@yourcompany.com"
  //        ext.betaDistributionEmailsFilePath=beta_distribution_emails.txt
  //        ext.betaDistributionNotifications=true
      } 이메일은 개인정보라 파일로 넣으면 좀 그럴거같아서 뺌



* 빌드실패가 날 시, 꼬여서 그런 경우 gradlew clear 해주고 하면 됨

* Functional test 받아야할 것
    system-images;android-26;google_apis;x86

    platforms;android-26

    platform-tools


    extras;android;m2repository


    export ANDROID_HOME="android-sdk-linux"
    export PATH="$ANDROID_HOME/tools:$ANDROID_HOME/platform-tools:$PATH"

image: openjdk:8-jdk

variables:
  ANDROID_COMPILE_SDK: "27"
  ANDROID_BUILD_TOOLS: "27.0.2"
  ANDROID_SDK_TOOLS: "26.1.1"

cache:
  paths:
   - .gradle/
   - app/build/
   - build/
   - bankplayer/build/
   - android-sdk-linux/
   - android-sdk-tools.zip
   - .gradle/wrapper
   - .gradle/caches 

before_script:
  - mkdir -p android-sdk-linux
  - test ! -f android-sdk-tools.zip && wget --quiet --output-document=android-sdk-tools.zip https://dl.google.com/android/repository/sdk-tools-linux-3859397.zip && unzip android-sdk-tools.zip -d android-sdk-linux
  - echo y | $PWD/android-sdk-linux/tools/bin/sdkmanager --update
  - export ANDROID_HOME=$PWD/android-sdk-linux
  - export PATH=$PATH:$PWD/android-sdk-linux/platform-tools/
  - chmod +x ./gradlew
  - export GRADLE_USER_HOME=`pwd`/.gradle 

stages:
  - build
  - test

build:
  stage: build
  script:
    - cd projectName
    - ./gradlew assembleRelease
    - ./gradlew assembleRelease crashlyticsUploadDistributionRelease
  only:
    - develop
  artifacts:
    name: "apk_file"
    when: on_success
    paths:
    - app/release/app-release.apk

unitTests:
  stage: test
  script:
    - wget --quiet --output-document=android-wait-for-emulator https://raw.githubusercontent.com/travis-ci/travis-cookbooks/0f497eb71291b52a703143c5cd63a217c8766dc9/community-cookbooks/android-sdk/files/default/android-wait-for-emulator
    - chmod +x android-wait-for-emulator
    - echo y | android-sdk-linux/tools/bin/sdkmanager "system-images;android-26;google_apis;x86" "platforms;android-26" platform-tools 
    - echo no | android-sdk-linux/tools/bin/avdmanager create avd -n test -k "system-images;android-26;google_apis;x86"
    - android-sdk-linux/tools/emulator -avd test -no-window -no-audio &
    - ./android-wait-for-emulator
    - adb shell input keyevent 82
    - cd projectName
    - ./gradlew cAT
  only:
    - master

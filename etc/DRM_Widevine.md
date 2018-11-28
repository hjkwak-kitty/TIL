 ## Google’s Widevine 

* [가이드문서](https://storage.googleapis.com/wvdocs/Widevine_DRM_Getting_Started.pdf)
* 아래 내용은 가이드 문서 정리/추가 한 내용임
  
### 지원 플렛폼
  ![지원 플렛폼](../res/widevine_device.png)
  ![지원 플렛폼2](../res/widevine_device2.png)

* 보안레벨[^3] L1를 만족해야 넷플릭스 같이 HD 컨텐츠 스트리밍 가능. 안드로이드의 경우 하드웨어나 소프트웨어 구현에 따라 L1이나 L3를 만족함. 기기가 L3를 만족할때는 하위 HD해상도로 제한됨
  
* 라이센스 및 서비스 사용 무료

### 구성
  ![Ecosystem](../res/ecosystem.png)

* The Lockbox, Keysmith, OEMCrypto는 실기기에 필요한 요소
* Key system의 경우 Widevine, PlayReady, FairPlay, Marlin 호환됨
* Widevine Cloud License Service
    *  단체에서만 사용 가능. 자격증명의 경우 문의 필요.
    * 라이센싱 서비스는 테스트/실서버로 구분되어 있음.
        * 테스트:  https://license.uat.widevine.com
        * 실서버:  https://license.widevine.com
    * 실서버에서 250qps이상 사용하기위해서는 구글에 문의 필요

* Contents Encryption
    * API는 단체별 고유한 서명필드를 사용해야함


### EncodingPackaging

* Widevine 지원 패키징 툴은 Shaka Packager
* [도커로 설치 가능](https://github.com/google/shaka-packager/blob/master/docs/source/docker_instructions.md)
  
* [indexnext |Shaka Packager documentation](https://google.github.io/shaka-packager/html/index.html)
* FFmpeg 파이프라이닝 가능

* [인코딩/패키징 문서](https://storage.googleapis.com/wvdocs/Widevine_DRM_Encoding_and_Packaging.pdf)


### Client
* Android
    * ExpoPlayer 추천
    * 현재 예스파일/애플파일 플레이어의 경우 기본 플레이어로 drm구현시 플레이어 새로 만들어아햠
    * ExoPlayer 사용 시 Widevine callback 연결 시 mpd 재생 되는 것 확인
  
* Browser
    * Shaka Player 제공 (open-source)
    * [튜토리얼](https://shaka-player-demo.appspot.com/docs/api/tutorial-welcome.html)
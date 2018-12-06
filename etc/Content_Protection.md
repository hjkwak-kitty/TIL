# Content Protection & Implementing DRM 가이드

* 방법의 경우 직/간접적 기술비용 및 추가 구현 유지보수에 대한 작업 난이도에 따라 결정하면 됨.
* Offline viewing / TVE 구현하려면 좀 더 복잡한 시스템이 필요함

## 주요 접근방법

* Tokenization(토큰화)

    * 바인가된 사용자로 하여금 영상 시청과 공유를 막아주는 기본적인 content protection 방법
    * 키와 토큰을 이용한 짧은 유효기간의 url을 사용하여 접근 제한, 주로 시간, GeoBlocking, IP로 제한

    * 장점
        * 간단하고 저렴한 구현
        * 사용자의 디바이스에 다른 소프트웨어를 설치하지 않아도 됨

    * 단점
        * 컨텐츠에 접근에 대해서만 보호 되며 이미 내려받은 컨텐츠에 대해서는 보호할 수 없음
        * 주로 CDN과의 결합을 통해 구현, CDN provider와 함께 구현해야함.

* MPEG-CENC ClearKey and HLS AES Encryption
    
    * 높은 수준의 컨텐츠 보안이 필요할 때 활용 가능한 옵션.
    * MPEG-CENC ClearKey는 Media Source Extensions (MSE),  Encrypted Media Extension (EME)의 일부로 구현되며 웹브라우저에서 재생가능함
    * These content protection technologies protect the actual content themselves, scrambling the video with the AES-128 algorithm in either the CTR mode for DASH or the CBC mode for HLS 

    * 장점
        * 실 컨텐츠를 보호해 줌
        * 암호화된 컨텐츠는 해독키 없이 볼 수 없음.
        * 암호화구현 이후 추가 비용이 없음 
        * HLS의 경우 sample-HLS를 활용하여 영상의 특정 세그먼트나 암호화하는 방법을 통해 영상 시청에 제한을 둠으로써 리소스를 적게 사용할 수 있음

    * 단점
        * 보안키 핸들링에 있어 DRM시스템보다는 보안이 제한적임
        * 추가적인 라이센스 관리나 output control은 불가능(DRM에서는 제공)

* DRM
    * 컨텐츠 조회를 최대한으로 제어
    * DRM 시스템
        * 가장 일반적인 DRM시스템은 다음과 같다
            * Google’s Widevine Modular (with some legacy implementations of Widevine Classic)
            * Microsoft’s PlayReady
            * Apple’s FairPlay
            * Adobe Primetime (a successor to Adobe Access, their flash-based DRM). 
        *  EZDRM 같은 Multi-DRM solution사용시 간단하게 구현 가능 - 비용이 듦

    * CMAF(Common Media Application Format)[^2]활용 시 자원 낭비를 줄이고 구현을 간단히 할 수 있음, 하지만 아직 적용하기는 어려워 보임
    *  크로스플랫폼 표준이 없어 각각 병렬로 구현해야함 - 각각의 환경을 지원하기위한 파일을 생성/저장해야 함
    *  일부 시스템은 오프라인 보기, pre-provisioning 라이센스 및 출력에 대한 세부 컨트롤 제공
    *  HDCP[^1]사용 - 인터셉터를 방지해줌
    *  구현이 복잡할 수 있음

  *  구현시 주의사항
        *  실 기기에서 테스트 할것
        *  HDMI/DVI모두 확인이 필요 - DVI에서는 문제없이 동작하지만 HDMI에서 안하는 경우가 있음

    * Flow
    ![원리](https://ox4zindgwb3p1qdp2lznn7zb-wpengine.netdna-ssl.com/wp-content/uploads/2017/05/drm-overview.png)

        - 인코딩/ 패키징 단계에서는 헐리우드 기준 DRM을 적용하는 것과 그냥 AES 암호화 하는 것과 큰 차이점이 없음 DRM도 AES 암호화 하는 것임 몇가지 메타데이터를 추가하는 차이 정도. PlayReady, Widevine,PrimeTime, Fairplay또한 encryption단계에서는 큰 차이점이 없음.
        - Multi DRM and MPEG-CENC: 각 디바이스는 하나의 DRM만 지원하므로 다양한 디바이스를 지원하기위해서는 몇개의 DRM을 병렬로 구성해야함. MPEG Common Encryption(MPEG-CENC) 표준을 쓰면 동일한 키로 암호화 가능함.
        - 구성요소
             ![](https://docs.microsoft.com/en-us/azure/media-services/latest/media/design-multi-drm-system-with-access-control/media-services-generic-drm-subsystem-with-cenc.png)
            - Key management
            - DRM encryption packaging
            - DRM license delivery
            - Entitlement check/access control
            - User authentication/authorization
            - Player app
            - Origin/content delivery network(CDN)


* 각주
  - [^1]: HDCP는 High-bandwidth Digital Content Protection, 즉 높은 대역폭 디지털 컨텐츠 보호를 나타냄. HDCP의 목적은 일반적으로 HDMI 또는 DVI 연결을 통해 디바이스에서 TV로 전송되는 디지털 저작권 콘텐츠를 보호하는 것을 말함
  - [^2]: CMAF는 비디오 스트림을 여러 장치에서 저렴하고 쉽게 전달할 수 있도록 Microsoft 및 Apple에서 MPEG 형식으로 정의한 표준화 된 새로운 미디어 스트리밍 형식, 거의 모든 스트리밍 비디오가 표준화 된 인코딩 기술을 사용하여 압축되지만 표준화되지 않은 컨테이너를 사용해서 문제가 되었는데 CMAF가 암호화를 제외한 부분을 표준화 함으로써 이를 해결, Encrytion부분은 CBC를 모두 지원하는 방향으로 논의가 되었다고 함



## 참고
* [MPEG-CMAF: Threat or Opportunity?](https://bitmovin.com/what-is-cmaf-threat-opportunity/)
* [CMAF (Common Media Application Format)](https://steemit.com/kr/@giljae/cmaf-common-media-application-format)
* [Digital Rights Management (DRM) Overview](https://bitmovin.com/docs/encoding/articles/digital-rights-management-drm-overview#how-does-it-work-)
* [Guide to Selecting and Implementing DRM & Content Protection](https://bitmovin.com/guide-selecting-implementing-premium-content-protection/)
* [Design of a multi-DRM content protection system with access control](https://docs.microsoft.com/ko-kr/azure/media-services/latest/design-multi-drm-system-with-access-control)
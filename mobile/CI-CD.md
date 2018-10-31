
# 테스트앱 배포
* Fabric: 현재 사용중
    * 장점
        * 파일 업데이트와 동시에 실시간으로 사용 가능
        * CI 등록하여 자동 업데이트 가능
    * 단점
        * 프로덕션 업데이트 전에 apk파일을 따로 공유해주어야 함.
        * 실질적으로 구글 플레이 사용자를 대상으로 테스트 하려면 구글 플레이 콘솔을 사용해야함.
* 구글 콘솔
    * 특징
        * 구글 계정이 필요.
        * 비공개 > 알파 > 베타 > 프로덕션 순서로 버전네임이 정해져야함.
        * 타겟버전/미니멈버전은 업데이트 시, 다운그레이드 되지 않음.
    * 장점
        * 특정 그룹을 대상으로 앱을 테스트하거나, 구글 플레이 사용자를 대상으로 공개 테스트 가능
        * 테스터는 공개리뷰를 쓸 수 없음 >> 테스트 중 이슈가 발생 > 항의 리뷰 (비공개처리됨)
        * apk파일을 따로 주고받을 필요 없이 테스트 시작부터 출시까지 가능
        * Gradle 사용하여  자동 배포 가능(테스트 필요) - https://github.com/Triple-T/gradle-play-publisher#quick-start-guide
    * 단점
        * 테스트 앱 업데이트까지 최대 48시간 걸릴 수 있음.(테스트 필요)
        * 개시중인 앱만 테스트 가능
        * Grade 배포임으로 CI와는 별개로 진행됨. CI와 사용하려면 Jenkins Sever나 fastlane, bitrise 같은거 사용해야함.


* 고민
    *  CI 는 기존거 그대로 + fabric beta로 테스트 + fastlane supply 로 CD해주면 될거 같기도...?
    * 다른 CI/CD tool 은 뭐가 있지? https://instabug.com/blog/continuous-integration-tools/ : 종류 / 가격 정보만 있음. 사용하는거 확인하려면 검토가 필요함.
    * 다른데는 어떻게 하고 있지?
        * https://academy.realm.io/kr/posts/continuous-delivery-for-android/
        * http://tech.kakao.com/2016/04/21/mobil/
        * http://woowabros.github.io/experience/2018/06/26/bros-cicd.html
        * https://www.slideshare.net/isjang98/travisci-cicd-jenkins-for-android-94750798

* [Gitlab CI 내용 정리](gitlab-CI.md)
# AVPlayerViewController

 * Custom Controller
    * 방법 1
        * AVPlayerViewController에 customControllerView 추가.
        * 크기변경이 커스텀하게 안됨 (기본 플레이어처럼만 플레이어 크기 변경된다. 기본/전체화면) - gravity로 변경
        * 화면 회전 시 추가한 뷰는 오토레이아웃 안됨. 다시 맞춰주어야 함. (매우 불편)
        * controllerView 추가 했을 때 플레이어 아래로 들어감/ 클릭액션 플레이어가 가져감
            * 해결 방법: 
                > UIApplication.shared.keyWindow?.addSubview(customview)

                > // 이 방법은 써봤는데 안됨
                > //        self.view.addSubview(customview)
                > //        self.view.bringSubview(toFront: btnPlay)

        * 플레이어 크기변경 안되는 게 치명적이라 이 방법은 안쓰기로 함.

    * 방법 2
        * 기본 ViewController 에 ContainerView-AvPlayerview 추가해서 사용
        * (진행중)



# ETC

 * CMTIme
    : CMTime은 시간 값을 나타내는 구조체 타입입니다. CMTime은 유리수로 표현되며 CMTime을 표현하기 위해서는 분자와 분모에 해당하는 값을 필요로 합니다.



let time1 = CMTimeMake(1,4) // 1/4
let time2 = CMTimeMake(2,8) // 2/8
let time3 = CMTimeMake(1,10000) // 1/10000

CMTime구조체는 CMTimeMake라는 함수로 생성할 수 있습니다.



time1은 value는 1이고 timescale은 4로 1/4초를 의미한다.
time2는 value는 2이고 timescale은 8로 2/8초를 의미한다.
time3는 value는 1이고 timescale은 10000으로 1/10000초를 의미한다.
즉 time1과 time2는 같은 값이다. (CMTimeCompare(time1, time2) = 0)
CMTime은 비디오와 오디오 같은 미디어의 타임라인을 위해 고안된 타입이라고 애플의 문서에 나와있습니다. 그러므로 비디오와 오디오와 같은 미디어를 다루는 작업을 해야 한다면 CMTime을 자주 보게 될 것 같습니다.

출처: http://baked-corn.tistory.com/95

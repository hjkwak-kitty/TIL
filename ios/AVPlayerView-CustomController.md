# AVPlayerViewController

 * Custom Controller
    * 방법 1
        * AVPlayerViewController에 customControllerView 추가.
        * 크기변경이 커스텀하게 안됨 (기본 플레이어처럼만 플레이어 크기 변경된다. 기본/전체화면) - gravity로 변경
        * 화면 회전 시 추가한 뷰는 오토레이아웃 안됨. 다시 맞춰주어야 함. (매우 불편)
        * controllerView 추가 했을 때 플레이어 아래로 들어감/ 클릭액션 플레이어가 가져감
            * 해결 방법: 
                > UIApplication.shared.keyWindow?.addSubview(customview)
                > // 아래 방법은 써봤는데 안됨
                > //        self.view.addSubview(customview)
                > //        self.view.bringSubview(toFront: btnPlay)

        * 플레이어 크기변경 안되는 게 치명적이라 이 방법은 안쓰기로 함.

    * 방법 2
        * 기본 ViewController 에 ContainerView-AvPlayerview 추가해서 사용
        * (진행중)


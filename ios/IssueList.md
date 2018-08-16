# modification requires exclusive access
 
 * swap 대신 array.swapAt 사용 (http://zeddios.tistory.com/354)

 * navigationcontroller 안에 tabbarcontroller 구조에서 새로운 컨트롤러 출력하여 회전 시, 이전 페이지 인 tabcontroller 로 돌아갔을 때 tabbar 가 깨짐 (회전 막아놨음)
    * 네비게이션 바가 없으면 문제 없음.
    * 새로 그려주는 방법으로 해결
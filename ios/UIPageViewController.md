

* 도트 인디게이터 바 출력방법

//    /**
//     * 인디케이터의 총 갯수
//     */
//    func presentationCount(for pageViewController: UIPageViewController) -> Int {
//        return self.pageImages.count
//    }

//    /**
//     * 인디케이터의 시작 포지션
//     */
//    func presentationIndex(for pageViewController: UIPageViewController) -> Int {
//        return 0
//    }

이거 넣어주면 자동으로 생기는데 모양을 맘대로 못함



* 현재 위치
    *     func pageViewController(_ pageViewController: UIPageViewController, willTransitionTo pendingViewControllers: [UIViewController]) {
        if let pageItemController = pendingViewControllers[0] as? ContentViewController {
            print(pageItemController.pageIndex) // THIS IS THE CURRENT PAGE NUMBER
            self.pageControl.currentPage =  pageItemController.pageIndex
            
            NSLog("인디게이터바움직여라 \(pageItemController.pageIndex)")
        }
    }
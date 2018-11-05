

# api
 * api는 의존성에 추가하는 모듈이 의존하고 있는 다른 모듈 까지 접근이 가능합니다.
예 ) ModuleX 에서 LibraryA 를 api project(path: ':LibrayA')로 의존성에 추가하면 ModuleX에서 LibraryC 클래스를 접근 할 수 있습니다.

# implement
* implementation는 의존성에 추가하는 모듈 외 추가하는 모듈이 의존하는 다른 모듈에는 접근이 불가능 합니다. 즉 위에 상황을 보자면 implementation project(path: ':LibraryA')로 의존성에 추가하면 ModuleX에서 LibraryC 클래스에는 접근할 수 없습니다.

[참고링크](https://sikeeoh.github.io/2017/08/28/implementation-vs-api-android-gradle-plugin-3/)

# Intent

* App Component간 Messaging object, facilitate communication between components.
* 기본 활용
    * Starting an Activity : 데이터 전달
    * Starting a Service
        * 5.0이상은 Jobscheduler로 시작
        * 5.0이하는 service class로 시작
    * Delivering a broadcast
        * broadcast-messages that any app can recieve

* TYPE
    * Explicit Intents(명시적)
        * 패키지명, 클래스 명으로 호출(완전히 정규화된 이름)
        * ex) 새 액티비티 시작, 백그라운드 파일 다운로드 서비스 시작 등
    * Implicit Intents(암시적)
        * declare a general action to perform(another app handle)
        * 명시적으로 호출시 직접 시작, 암시적으로 호출시 manifest 검토 후, 요청 기능 가진 앱을 사용자가 선택해 실행가능(보안), 받을 앱이 없으면 crash -> resolveActivity()확인 해주어야 함.
        * 항상 createChooser()로 유저가 선택할 수 있게 해주어야함.

* 주요 정보
    * 인텐트 성격 정의
        * Component name(optional)
        * 


* 코틀린 예제
    - 명시적
    ~~~
    val downloadIntent = Intent(this, DownloadService::class.java).apply {
        data = Uri.parse(fileUrl)
    }
    startService(downloadIntent)
    ~~~

    - 암시적
    ~~~
    // Create the text message with a string
    val sendIntent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, textMessage)
        type = "text/plain"
    }

    val webIntent: Intent = Uri.parse("http://www.android.com").let { webpage ->
    Intent(Intent.ACTION_VIEW, webpage)
    }

    // Verify that the intent will resolve to an activity
    //설치된 앱 없으면 crash - 꼭 필요함.
    if (sendIntent.resolveActivity(packageManager) != null) {
        startActivity(sendIntent)
    }
    ~~~

    ~~~
    val sendIntent = Intent(Intent.ACTION_SEND)
    ...

    // Always use string resources for UI text.
    // This says something like "Share this photo with"
    val title: String = resources.getString(R.string.chooser_title)
    // Create intent to show the chooser dialog
    val chooser: Intent = Intent.createChooser(sendIntent, title)

    // 유효성 확인 Verify the original intent will resolve to at least one activity
    if (sendIntent.resolveActivity(packageManager) != null) {
        startActivity(chooser)
    }

    val activities: List<ResolveInfo> = packageManager.queryIntentActivities(
        intent,
        PackageManager.MATCH_DEFAULT_ONLY
    )
    val isIntentSafe: Boolean = activities.isNotEmpty()
    ~~~



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

    // Verify the original intent will resolve to at least one activity
    if (sendIntent.resolveActivity(packageManager) != null) {
        startActivity(chooser)
    }
    ~~~

### Lolipop Webview issue
 * HTTPS > HTTP 전송시 내장 브라우저에서 block 시켜 데이터 전송이 안됨
    * [blocked] The page at 'https://xxx' was loaded over HTTPS, but ran insecure content from http://xxx.css': this content should also be loaded over HTTPS.
    * 혼합된 컨텐츠와 서드파티 쿠키가 설정에 따라 Webview 에서 Block 시키는 게 기본이 됨.
    
    > public abstract void setMixedContentMode (int mode)

    > Configures the WebView's behavior when a secure origin attempts to load a resource from an insecure origin. By default, apps that target KITKAT or below default to MIXED_CONTENT_ALWAYS_ALLOW. Apps targeting LOLLIPOP default toMIXED_CONTENT_NEVER_ALLOW. The preferred and most secure mode of operation for the WebView is MIXED_CONTENT_NEVER_ALLOW and use of MIXED_CONTENT_ALWAYS_ALLOW is strongly discouraged.

    > MIXED_CONTENT_ALWAYS_ALLOW : 항상 허용

    > MIXED_CONTENT_COMPATIBILITY_MODE : 호환성 모드

### 타겟버전 업데이트 후 (19->26) 쿠키허용설정 해주어야 함.
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            mWebView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);

            CookieManager cookieManager = CookieManager.getInstance();

            cookieManager.setAcceptCookie(true);

            cookieManager.setAcceptThirdPartyCookies(mWebView, true);

        }
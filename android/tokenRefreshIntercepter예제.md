# sample code (okhttp3)
...
public class AuthenticationInterceptor implements Interceptor {

    private String authToken;

    public AuthenticationInterceptor(String token) {
        this.authToken = token;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request original = chain.request();

        authToken =  Prefs.getString(Constants.ACCESS_TOKEN);
        Response response = chain.proceed(requestBuild(original,authToken).build());
        if (response.code() == 401) {
            Dlog.d("토큰 만료", " 만료");
            // 토큰이 만료되었다.
            // 이렇게 안하면 함수형처럼 response를 설정할 수 없어서 로우하게 만듬.
            String refreshToken = Prefs.getString(Constants.REFRESH_TOKEN);
            Request newRequest = requestBuild(
                    new Request.Builder()
                            .url(String.format("%sauth/refresh", API_URL))
                            .post(RequestBody.create(MediaType.parse("application/json"), new byte[0]))
                            .build(), refreshToken).build(); // create simple requestBuilder

            Response newResponse = chain.proceed(newRequest); // 동기, 순차적 작동.
            // 성공했을 경우에만 토큰을 새로 저장한다.
            Dlog.d("토큰", "새로운 거 ? " + newResponse.toString()+ "         " + newRequest.toString());
            if (newResponse.code() == 200) {
                Dlog.d("토큰 성공","ㄸ");
                Auth newAuth = new GsonBuilder().create().fromJson(newResponse.body().string(), Auth.class);
                newAuth.create();
                authToken = newAuth.getAccess_token();
                response = chain.proceed(requestBuild(original, authToken).build());
            }

            // else {} // 아니면 그냥 흘려보낸다.
        }

        return response;
    }

    // Header를 넣어준다. / header insert in retrofit request builder
    private static Request.Builder requestBuild(Request request, String auth) {
        return request.newBuilder()
                .header("Accept", "application/json")
                .addHeader("Authorization", "Bearer " + auth )
                .method(request.method(), request.body());
    }
}

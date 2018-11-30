
# Android play Local drm mp4 file

## Tool

1. ShakaPackager - 도커로 설치
2. ffmpeg(선택)
3. Exoplayer2



## 파일 암호화
테스트 파일: example.mp4

./packager-osx in=example.mp4,stream=video,output=h264_360.mp4 --enable_raw_key_encryption --keys label=:key_id=abba271e8bcf552bbd2e86a434a9a5d9:key=69eaa802a6763af979e8d1940fb88392 --clear_lead 0

## exoplayer2

 git clone https://github.com/googlecodelabs/exoplayer-intro.git
 help site >> https://codelabs.developers.google.com/codelabs/exoplayer-intro/#1


## key

 * key example: 91341951696b5e1ba232439ecec1f12a
 
 ~~~
 ByteBuffer buffer = ByteBuffer.allocate(16);
 buffer.putLong(0x91341951696b5e1bL);
 buffer.putLong(0xa232439ecec1f12aL);
 byte[] kidBytes = buffer.array();

 String base64Kid = Base64.encodeToString(kidBytes, Base64.NO_PADDING);
 ~~~


 * 키관리 어떻게 할지 생각해봐야할듯.

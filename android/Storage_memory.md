# 사용가능한 메모리 영역

  ** 명칭이 늘 헷갈림.

 ~~~
    int k=0;
    File[] fs = getObbDirs();
    Log.e("", "fs.length : "+ fs.length);
    Log.e("", "getExternalFilesDir : "+ getExternalFilesDir(null));
    for(File file : fs) {
         Log.e("", "fs["+k+"] " +file.getAbsolutePath());
         k++;
    }
~~~

* fs.length : 2
* getExternalFilesDir : /storage/emulated/0/Android/data/com.my.example/files
*  fs[0] /storage/emulated/0/Android/obb/com.my.example
*  fs[1] /storage/extSdCard/Android/obb/com.my.example
* getObbDirs() 의 리턴되는 파일배열의 갯수로 판단이 가능


* 안드로이드에서 사용가능한 메모리 영역
    1. 시스템 메모리- 힙과 스택으로 사용되는 DRAM 메모리 (512GB/1GB/2GB/3GB)
    2. 시스템 영역 플레시메모리- 안드로이드시스템파일들과 어플들이 설치되는 내장메모리 일반 어플에서는 건들지 못하는영역으로 루팅을 하면 가능
    3. 외장메모리- 일반적으로 sdcard 카드라 불리는 영역. 일반 어플에서 읽고 쓰기가 가능한 영역
    4. 추가장착 extSdCard 영역 - 주로 폰의 배터리커버를 벗기면 유심옆에 있는 슬롯. 많은 사람들이 오해를 하는 부분이 시스템이 있는 sdcard 플레시 메모리가 내장메모리, 추가 장착한 플레시가 외장이라고 부르는 경향이 있는데 이런 오해는 구글이 애초에 4번 추가 장착 메모리에 대해서는 전혀 고려를 하지않았기때문이고, 제조사들은 사용자의 필요에 의해 추가장착하여 확장가능하게 만들면서 이런 오해가 생겨났다.

> 정리하면
> 내장플레시메모리 : 시스템영역/어플이 기본적으로 설치되는곳
> 외장플레시메모리 : 일반적으로 사용가능한곳(PC연결시 보이는 드라이브)
> 확장플레시메모리 : 추가 장착한 메모리(착탈가능)

* mounts 를 검토해서 유추하는 방법과 
    System.getenv("EXTERNAL_STORAGE");
    System.getenv("SECONDARY_STORAGE");


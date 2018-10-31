* objective-C와 사용방법 변경됨

    - Objective-C
    ~~~
    + (id)sharedInstance {
        static MyObject *sharedInstance = nil;
        static dispatch_once_t onceToken;
        dispatch_once(&onceToken, ^{
         sharedInstance = [[self alloc] init];
        });
        return sharedInstance;
    }
    - (id)init {
        if (self = [super init]) {
         // write your code here
        }
        return self;
        }
    ~~~

    - Swift
    ~~~
        static let sharedInstance = MyObject()
        private init(){}
    ~~~

* HOW?
    * The lazy initializer for a global variable (also for static members of structs and enums) is run the first time that global is accessed, and is launched as dispatch_once to make sure that the initialization is atomic. This enables a cool way to use dispatch_once in your code: just declare a global variable with an initializer and mark it private.(from Apple Swift Blog)
 
* [관련내용참고](https://blog.naver.com/itperson/220906532829)
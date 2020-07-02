# Android Reverse development 

 Android系统的启动流程
         bootloader先启动加载内核和执行init进程
         init进程启动系统所需的各种守护线程
         init进程根据init.rc文件中的配置创建启动Zygote进程

    Zygote进程 开启一个Socket接口来监听请求
    接收到app启动请求后，复制自身快速提供Dalvik虚拟机实例来执行应用程序
         
    Zygote会启动一个系统服务System Server管理进程，该进程会启动Android所有系统核心服务，包括Activity Manager Service、Package Manager Service等service
    启动Launcher进程


App启动流程：
    当用户点击桌面上一个APP图标时，这个APP的启动流程大致如下：
        (1)点击APP图标，产生Click Event事件；
        (2)Launcher程序接收到Click Event事件，调用startActivity(Intent)，通过Binder IPC机制调用Activity Manager  Service的服务；
        (3)Activity Manager Service会调用startProcessLocked方法来创建新的进程；
        (4)startProcessLocked方法调用Process类的静态成员函数start来创建这个APP的进程，并指定APP进程的入口函数为android.app.ActivityThread类的静态成员函数main；
        (5)main方法成功创建ActivityThread对象后，再调用attach方法完成初始化，然后进入消息循环，直到进程退出；


几个关键类的关系和用途：
    ActivityThread
         一个App启动时会创建一个ActivityThread，它管理着app进程中主线程的执行，其main方法作为app启动的入口。它根据Activity Manager发送的请求，对activities、broadcasts和其他操作进行调度、执行。

    Application
        ActivityThread是单例模式，而且是应用程序的主线程。其中，Application对象mInitialApplication是ActivityThread类的成员。
        可见，应用程序中有且仅有一个Application组件，它是全局的单例的。而且Application对象的生命周期跟应用程序的生命周期一样长，从应用启动开始到应用退出结束。
        Application跟Activity、Service一样，是Android系统的一个组件，当Android程序启动时会创建一个Application对象，用来存储系统的一些信息。
        默认情况下，系统会帮我们自动创建一个Application对象，我们也可以在AndroidManifest.xml中指定并创建自己的Application做一些全局初始化的工作。
   
    Instrumentation
        同样也是全局的单例的。
        主要是用来监控系统和应用的交互，并为ActivityThread创建Activity、Application等组件

    LoadedApk
         一个应用程序对应一个LoadedApk对象。它用来保存当前加载的APK包的各种信息，包括app安装路径、资源路径、用户数据保存路径、使用的类加载器、Application信息等。

    ApplicationLoaders
         获取当前应用程序的类加载器，通过LoadedApk类的getClassLoader方法获取。


        public final class ActivityThread {
                //关键成员
              final ApplicationThread mAppThread = new ApplicationThread();
              final H mH = new H();
              Application mInitialApplication;
              private static ActivityThread sCurrentActivityThread;
              Instrumentation mInstrumentation;
              final ArrayMap<String, WeakReference<LoadedApk>>  mPackages = new ArrayMap<String, WeakReference<LoadedApk>>();
              …
              //关键方法
              public static void main(String[] args) { … }
              private void attach(boolean system) { … }
              public final void bindApplication(String processName, ApplicationInfo appInfo, List<ProviderInfo> providers,
                            ComponentName instrumentationName, String profileFile, ParcelFileDescriptor profileFd, boolean autoStopProfiler,
                            Bundle instrumentationArgs, IInstrumentationWatcher instrumentationWatcher, IUiAutomationConnection instrumentationUiConnection,
                            int debugMode, boolean enableOpenGlTrace, boolean isRestrictedBackupMode, boolean persistent, Configuration config,
                            CompatibilityInfo compatInfo, Map<String, IBinder> services,Bundle coreSettings)
              { .. }
              private void handleBindApplication(AppBindData data) {…}
              …
            }

    public class Application extends ContextWrapper implements ComponentCallbacks2 {
          //关键成员
        public LoadedApk mLoadedApk;
        …
        //关键方法
        public void onCreate(){ … }
        protected void attachBaseContext(Context base) { ... }
        …
    }

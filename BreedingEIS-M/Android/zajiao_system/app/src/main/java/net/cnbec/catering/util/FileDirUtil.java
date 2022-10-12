package net.cnbec.catering.util;

import android.content.Context;
import android.os.Environment;
import android.os.storage.StorageManager;

import net.cnbec.catering.app.GJApplication;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Android文件路径获取整理
 */
public class FileDirUtil {

    private FileDirUtil(){}

    static class SingleHolder{
        public static FileDirUtil instance = new FileDirUtil();
    }

    public static FileDirUtil getInstance(){
        return SingleHolder.instance;
    }

    /**
     * （1）内部存储
     * （2）获取内部存储中当前应用程序下的files目录的路径
     * （其路径为/data/data/com.xxx.ooo.filetestdemo/files有些手机的路径为/data/user/0/com.xxx.ooo.filetestdemo/files）
     * （3）个别设备虽然获取内部存储的路径貌似不同，其实最终都是映射在同一个路径下。比如/data/data/对应的映射路径是data/user/0/
     * （4）不需要额外的权限来读取或在返回的路径下写入文件
     * （5）当应用被卸载时，文件数据被清除
     * （6）一般情况下，非root手机不能访问
     * @return
     */
    public String getFilesDir(){
        File dir = GJApplication.getContext().getFilesDir();
        return dir.getAbsolutePath();
    }

    /**
     * （1）内部存储
     * （2）获取内部存储中当前应用程序下的cache目录的路径
     * （其路径为/data/data/com.xxx.ooo.filetestdemo/cache有些手机的路径为/data/user/0/com.xxx.ooo.filetestdemo/cache）
     * （3）个别设备虽然获取内部存储的路径貌似不同，其实最终都是映射在同一个路径下。比如/data/data/对应的映射路径是data/user/0/
     * （4）不需要额外的权限来读取或在返回的路径下写入文件
     * （5）当该文件夹超过当前被分配的最大缓存时，系统将自动删除该目录中的文件为其他地方提供需要空间，当未超出时则不会
     * （6）当应用被卸载时，文件数据被清除
     * （7）一般情况下，非root手机不能访问
     * @return
     */
    public String getCacheDir(){
        File dir = GJApplication.getContext().getCacheDir();
        return dir.getAbsolutePath();
    }

    /**
     * （1）外部存储
     * （2）获取外部存储中当前应用程序下的cache目录的路径（/storage/emulated/0/Android/data/com.xxx.ooo.filetestdemo/cache）
     * （3）不需要额外的权限来读取或在返回的路径下写入文件
     * （4）当应用被卸载时，文件数据被清除
     * （5）一般情况下，非root手机可以访问
     * @return
     */
    public String getExternalCacheDir(){
        File dir = GJApplication.getContext().getExternalCacheDir();
        return dir.getAbsolutePath();
    }

    /**
     * （1）和getExternalCacheDir类似，getExternalCacheDirs获取所有内置存储器的cache目录的路径
     * （2）Android4.4新增接口
     * @return
     */
    public List<File> getExternalCacheDirs(){

        List<File> list = new ArrayList<>();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            list.addAll(Arrays.asList(GJApplication.getContext().getExternalCacheDirs()));
        }
        return list;
    }

    /**
     * （1）外部存储
     * （2）获取外部存储中当前应用程序下的files目录中的type文件夹的路径（/storage/emulated/0/Android/data/com.xxx.ooo.filetestdemo/files/aa）
     * （3）不需要额外的权限来读取或在返回的路径下写入文件
     * （4）当应用被卸载时，文件数据被清除
     * （5）一般情况下，非root手机可以访问
     * @return
     */
    public String getExternalFilesDir(String name){
        //参数type就是files目录下的aa文件夹
        File dir = GJApplication.getContext().getExternalFilesDir(name);
        return dir.getAbsolutePath();
    }

    /**
     * （1）和getExternalFilesDir类似，getExternalFilesDirs获取所有内置存储器的files目录下的aa文件夹
     * （2）Android4.4新增接口
     *
     * @return
     */
    public List<File> getExternalFilesDirs(String name){
        //参数type就是files目录下的name文件夹

        List<File> list = new ArrayList<>();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            list.addAll(Arrays.asList(GJApplication.getContext().getExternalFilesDirs(name)));
        }
        return list;
    }

    /**
     * （1）外部存储
     * （2）获取外部存储中当前应用程序文件夹的路径（/storage/emulated/0/Android/obb/com.xxx.ooo.filetestdemo）
     * （3）不需要额外的权限来读取或在返回的路径下写入文件
     * （4）当应用被卸载时，文件数据被清除
     * （5）一般情况下，非root手机可以访问
     * @return
     */
    public String getObbDir(){
        File dir = GJApplication.getContext().getObbDir();
        return dir.getAbsolutePath();
    }

    /**
     * （1）和getObbDir类似，getObbDirs获取所有内置存储器的相应目录
     * （2）Android4.4新增接口
     * @return
     */
    public List<File> getObbDirs(){

        List<File> list = new ArrayList<>();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            list.addAll(Arrays.asList(GJApplication.getContext().getObbDirs()));
        }

        return list;
    }

    /**
     * （1）内部存储
     * （2）不会自动备份到远程存储的应用程序文件的路径
     * （3）获取内部存储中当前应用程序下的no_backup目录的路径
     * （其路径为/data/data/com.xxx.ooo.filetestdemo/no_backup有些手机的路径为/data/user/0/com.xxx.ooo.filetestdemo/no_backup）
     * （4）个别设备虽然获取内部存储的路径貌似不同，其实最终都是映射在同一个路径下。比如/data/data/对应的映射路径是data/user/0/
     * （5）不需要额外的权限来读取或在返回的路径下写入文件
     * （6）当应用被卸载时，文件数据被清除
     * （7）一般情况下，非root手机不能访问
     * （8）Android 5.0新增接口，低于5.0手机不支持
     * @return
     */
    public String getNoBackupFilesDir(){
        File dir = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            dir = GJApplication.getContext().getNoBackupFilesDir();
        }
        return dir == null ? "" : dir.getAbsolutePath();
    }

    /**
     * （1）内部存储
     * （2）保存应用程序代码缓存文件的目录路径,适合在运行时存放应用产生的编译或者优化的代码。
     * （3）获取内部存储中当前应用程序下的code_cache目录的路径
     * （其路径为/data/data/com.xxx.ooo.filetestdemo/code_cache有些手机的路径为/data/user/0/com.xxx.ooo.filetestdemo/code_cache）
     * （4）个别设备虽然获取内部存储的路径貌似不同，其实最终都是映射在同一个路径下。比如/data/data/对应的映射路径是data/user/0/
     * （5）不需要额外的权限来读取或在返回的路径下写入文件
     * （6）当应用被卸载时，文件数据被清除
     * （7）一般情况下，非root手机不能访问
     * （8）Android 5.0新增接口，低于5.0手机不支持
     * @return
     */
    public String getCodeCacheDir(){
        File dir = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            dir = GJApplication.getContext().getCodeCacheDir();
        }
        return dir == null ? "" : dir.getAbsolutePath();
    }

    /**
     * （1）内部存储
     * （2）获取内部存储中当前应用程序路径
     * （其路径为/data/data/com.xxx.ooo.filetestdemo有些手机的路径为/data/user/0/com.xxx.ooo.filetestdemo）
     * （3）个别设备虽然获取内部存储的路径貌似不同，其实最终都是映射在同一个路径下。比如/data/data/对应的映射路径是data/user/0/
     * （4）不需要额外的权限来读取或在返回的路径下写入文件
     * （5）当应用被卸载时，文件数据被清除
     * （6）一般情况下，非root手机不能访问
     * （7）Android 7.0新增接口，低于7.0手机不支持
     * @return
     */
    public String getDataDir(){
        File dir = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            dir = GJApplication.getContext().getDataDir();
        }
        return dir == null ? "" : dir.getAbsolutePath();
    }

    /**
     * （1）内部存储
     * （2）此上下文的主Android包的完整路径。这对应用程序通常没有用处，因为它们不应该直接访问文件系统
     * （其路径为/data/app/com.xxx.ooo.filetestdemo-1PN4Y-p3v7XA-OqXnbud8A==/base.apk)
     * （3）不需要额外的权限来读取或在返回的路径下写入文件
     * （4）当应用被卸载时，文件数据被清除
     * （5）一般情况下，非root手机不能访问
     * @return
     */
    public String getPackageCodePath(){
        String dir = GJApplication.getContext().getPackageCodePath();
        return dir;
    }

    /**
     * 和getPackageCodePath一致
     * @return
     */
    public String getPackageResourcePath(){
        String dir = GJApplication.getContext().getPackageResourcePath();
        return dir;
    }

    /**
     * （1）外部存储
     * （2）获取外部存储中指定文件夹的目录
     * （其路径为/storage/emulated/0/type）
     * （3）需要配置文件外部存储权限
     *     <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
     *     <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
     *     仅仅添加这读写权限还是不够的，还需要在应用信息中检查存储权限是否打开，如果没有打开，需要手动打开。
     * （4）当应用被卸载时，文件数据不会被清除
     * @return
     */
    public String getExternalStoragePublicDirectory(){
        File dir = Environment.getExternalStoragePublicDirectory("aaa");
        return dir.getAbsolutePath();
    }

    /**
     * （1）外部存储
     * （2）获取外部存储中指定文件夹的目录
     * （其路径为/storage/emulated/0）
     * （3）需要配置文件外部存储权限
     *     <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
     *     <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
     *     仅仅添加这读写权限还是不够的，还需要在应用信息中检查存储权限是否打开，如果没有打开，需要手动打开。
     * （4）当应用被卸载时，文件数据不会被清除
     * @return
     */
    public String getExternalStorageDirectory(){
        File dir = Environment.getExternalStorageDirectory();
        return dir.getAbsolutePath();
    }

    /**
     * 获取存储状态（媒体是指外部存储，比如SD卡）
     *
     * MEDIA_UNKNOWN：未知存储状态，例如路径没有由已知存储支持时
     * MEDIA_REMOVED：存储媒体被移除
     * MEDIA_UNMOUNTED：存储媒体没有挂载
     * MEDIA_CHECKING：如果媒体存在并正在检查磁盘
     * MEDIA_NOFS：不支持的文件系统
     * MEDIA_MOUNTED：媒体已经挂载，并且可读/写
     * MEDIA_MOUNTED_READ_ONLY：媒体已经挂载，只读
     * MEDIA_SHARED：在通过USB共享
     * MEDIA_BAD_REMOVAL：在没有挂载前存储媒体已经被移除
     * MEDIA_UNMOUNTABLE：存储媒体无法挂载
     * MEDIA_EJECTING：存储媒体处于被弹出的过程
     *
     * 其中MEDIA_MOUNTED最常用，可以判断媒体是否存在，如果不存在可以将数据存储到内部存储中
     * boolean isSDCardExist = Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
     * @return
     */
    public String getExternalStorageState(){
        String state = Environment.getExternalStorageState();
        return state;
    }

    /**
     * （1）内部存储
     * （2）其路径为/data
     * （3）如果写文件需要data文件夹读写权限，不过即使将文件夹的文件设置成可读可写权限，Android的createNewFile方法还是报权限错误（这个问题待定吧）
     * （4）不过一般app开发不需要读写/data目录中的文件
     * （5）一般情况下，非root手机不能访问
     * @return
     */
    public String getDataDirectory(){
        File file = Environment.getDataDirectory();
        return file.getAbsolutePath();

    }

    /**
     * （1）内部存储
     * （2）其路径为/data/cache
     * （3）如果写文件需要data文件夹读写权限，不过即使将文件夹的文件设置成可读可写权限，Android的createNewFile方法还是报权限错误（这个问题待定吧）
     * （4）不过一般app开发不需要读写/data目录中的文件
     * （5）一般情况下，非root手机不能访问
     * @return
     */
    public String getDownloadCacheDirectory(){
        File file = Environment.getDownloadCacheDirectory();
        return file.getAbsolutePath();
    }


    /**
     * （1）获取系统目录
     * （2）其路径为/system
     * （3）该文件夹只读权限，不可写
     * （4）一般情况下，非root手机不能访问
     * @return
     */
    public String getRootDirectory(){

        File file = Environment.getRootDirectory();
        return file.getAbsolutePath();

    }

    /**
     * 获取所有的外部存储路径
     * /storage/emulated/0、/storage/usbotg
     * @return
     */
//    public String[] getStoragePaths(){
//
//        String[] paths = new String[0];
//        StorageManager sm = (StorageManager)GJApplication.getContext().getSystemService(Context.STORAGE_SERVICE);
//        try {
//            paths = (String[]) sm.getClass().getMethod("getVolumePaths", null).invoke(sm, null);
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        }
//        return paths;
//    }

}

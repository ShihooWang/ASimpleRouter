package com.shihoo.router_library;

import android.app.Application;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.util.Log;
import android.util.LruCache;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

/**
 * Created by shihoo ON 2018/12/6.
 * Email shihu.wang@bodyplus.cc 451082005@qq.com
 *
 * 路由管理器,负责处理各种跳转
 */

public class ARouter {

    private static  final int DEFAULT_SIZE = 10;

    private static Application application;

    private static LruCache<Method, String> urlCache;


    public static void init(Application app){
        application = app;
        urlCache = new LruCache<>(DEFAULT_SIZE);
    }


    /**
     * 创建
     * @param aRouter
     * @param <T>
     * @return
     */

    @SuppressWarnings("unchecked")
    public static <T> T create(final Class<T> aRouter){

        // aRouter.getClass().getInterfaces()
        return (T) Proxy.newProxyInstance(aRouter.getClassLoader(), new Class[]{aRouter}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                // 如果Class<T> 中有多个Method,则invoke方法会执行多次
                // 判断缓存中是否有
                if (urlCache == null){
                    urlCache = new LruCache<>(DEFAULT_SIZE);
                }
                String url = urlCache.get(method);
                if (url != null){
                    startActivity(url);
                    return null;
                }
                // 解析 域名
                String domain = null;
                if (aRouter.isAnnotationPresent(ARouterDomain.class)){
                    ARouterDomain aRouterDomain = aRouter.getAnnotation(ARouterDomain.class);
                    domain = aRouterDomain.value();
                }
                // 解析 地址
                Log.d("Wsh ", aRouter.getName()+" --- "+ method.getName());
                if (!method.isAnnotationPresent(ARouterPath.class)){
                    throw new RuntimeException("wsh : " + aRouter.getName()+"  "+ method.getName() + "  no ARouterPath match");
                }
                ARouterPath  aRouterPath = method.getAnnotation(ARouterPath.class);
                String path = aRouterPath.value();
                StringBuilder urlBuilder = new StringBuilder();
                if (domain != null){
                    urlBuilder.append(domain);
                }
                urlBuilder.append(path);

                // 携带的参数
                Annotation[][] paramsAnnotations = method.getParameterAnnotations();
                for (int index=0;index < paramsAnnotations.length;index++){
                    Annotation[] annotations = paramsAnnotations[index];
                    if (annotations == null || annotations.length==0){
                        continue;
                    }
                    if (annotations[0] instanceof ARouterPath){
                        ARouterParam aRouterParam = (ARouterParam) annotations[0];
                        String param = aRouterParam.value();
                        String name = aRouterParam.name();
                        String age = aRouterParam.age();
                        String connector = index == 0 ? "?" : "&";
                        urlBuilder.append(connector).append(param).append("=").append(args[index]);
                    }
                }
                // 打开新页面 并缓存路由信息
                if (startActivity(urlBuilder.toString())){
                    urlCache.put(method,urlBuilder.toString());
                }
                return null;
            }
        });

    }


    private static boolean startActivity(String url){
        PackageManager packageManager = application.getPackageManager();
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        List<ResolveInfo> activities = packageManager.queryIntentActivities(intent, 0);
        boolean isValid = !activities.isEmpty();
        if (isValid){
            application.startActivity(intent);
        }
        return isValid;

    }

}

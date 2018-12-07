package com.shihoo.simplerouter;


import com.shihoo.router_library.ARouterDomain;
import com.shihoo.router_library.ARouterPath;

/**
 * Created by shihoo ON 2018/12/7.
 * Email shihu.wang@bodyplus.cc 451082005@qq.com
 *
 *
 */


@ARouterDomain("app://me")
public interface AMeRouter {

    @ARouterPath("/info")
    void showMeView();

}

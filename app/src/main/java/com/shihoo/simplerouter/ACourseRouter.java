package com.shihoo.simplerouter;

import com.shihoo.router_library.ARouterDomain;
import com.shihoo.router_library.ARouterPath;

/**
 * Created by shihoo ON 2018/12/7.
 * Email shihu.wang@bodyplus.cc 451082005@qq.com
 *
 * 打开课程页面的接口
 *
 */


@ARouterDomain("app://course")
public interface ACourseRouter {

    @ARouterPath("/info")
    void showCourse();

}

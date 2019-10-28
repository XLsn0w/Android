package com.chen.fakevibrato.ui.home.contract;

import com.chen.fakevibrato.MainActivity;
import com.chen.fakevibrato.SplashActivity;
import com.chen.fakevibrato.base.BasePresenter;
import com.chen.fakevibrato.base.BaseView;

/**
 * @author Created by CHEN on 2019/6/11
 * @email 188669@163.com
 */
public interface SplashContract {

    interface View extends BaseView {
    }
    abstract class Presenter extends BasePresenter<SplashActivity> {

    }
}

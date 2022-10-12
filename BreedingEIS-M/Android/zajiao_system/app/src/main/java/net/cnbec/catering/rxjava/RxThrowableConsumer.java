package net.cnbec.catering.rxjava;

import net.cnbec.catering.R;
import net.cnbec.catering.app.GJApplication;
import net.cnbec.catering.constant.Constant;
import net.cnbec.catering.exception.ServerException;
import net.cnbec.catering.util.LogUtil;
import net.cnbec.catering.util.NetWorkUtil;
import net.cnbec.catering.util.ToastUtil;

import java.net.NoRouteToHostException;
import java.net.SocketTimeoutException;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * @Describe: 统一异常封装
 * @Author: gujie
 * @Email: 939395465@qq.com
 * @Date: 2018/3/19
 */


public class RxThrowableConsumer implements Consumer<Throwable> {
    @Override
    public void accept(@NonNull Throwable throwable) throws Exception {
        throwable.printStackTrace();
        //网络
        if (!NetWorkUtil.isNetConnected(GJApplication.getContext())) {
            _accept(Constant.ExceptionCode.NETWORK_EXCEPTION, GJApplication.getContext().getString(R.string.common_no_net));
        }
        //服务器
        else if (throwable instanceof ServerException) {
//            _accept(Constant.ExceptionCode.SERVER_EXCEPTION, throwable.getMessage());
            _accept(Constant.ExceptionCode.SERVER_EXCEPTION, GJApplication.getContext().getString(R.string.common_other_error));
        }
        //服务器超时
        else if (throwable instanceof SocketTimeoutException) {
//            _accept(Constant.ExceptionCode.SERVER_EXCEPTION, throwable.getMessage());
            _accept(Constant.ExceptionCode.SERVER_EXCEPTION, GJApplication.getContext().getString(R.string.common_other_error));
        }
        else if (throwable instanceof NoRouteToHostException) {
            _accept(Constant.ExceptionCode.SERVER_EXCEPTION, GJApplication.getContext().getString(R.string.common_other_error));
        }
        //其它
        else {
            _accept(Constant.ExceptionCode.OTHER_EXCEPTION, GJApplication.getContext().getString(R.string.common_other_error));
        }
    }

    public void _accept(int errorCode, String errorMsg) {
        LogUtil.e(errorMsg);
        ToastUtil.show(errorMsg);
    }

}


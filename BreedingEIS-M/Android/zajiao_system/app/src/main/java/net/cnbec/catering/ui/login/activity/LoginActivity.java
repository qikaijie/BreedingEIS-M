package net.cnbec.catering.ui.login.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import net.cnbec.catering.R;
import net.cnbec.catering.bean.UserInfo;
import net.cnbec.catering.ui.base.BaseActivity;
import net.cnbec.catering.ui.login.contract.LoginContract;
import net.cnbec.catering.ui.login.presenter.LoginPresenter;
import net.cnbec.catering.ui.main.activity.MainActivity;
import net.cnbec.catering.util.SPUtils;
import net.cnbec.catering.util.SoftKeyBroadManager;
import net.cnbec.catering.util.ToastUtil;
import net.cnbec.catering.util.UserInfoUtil;
import net.posprinter.service.PosprinterService;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Describe: 登录页面
 * @Author: gujie
 * @Email: 939395465@qq.com
 * @Date: 2018/3/19
 */


public class LoginActivity extends BaseActivity<LoginContract.View, LoginContract.Presenter> implements LoginContract.View {

    @BindView(R.id.btn_login)
    Button btnLogin;

    @BindView(R.id.usernameEt)
    EditText usernameEt;
    @BindView(R.id.passwordEt)
    EditText passwordEt;

    @BindView(R.id.tv_login_logo)
    LinearLayout tvLoginLogo;
    @BindView(R.id.ll_login)
    LinearLayout llLogin;
    @BindView(R.id.content)
    RelativeLayout content;

    public static int ANIM_DURATION = 200;
    SoftKeyBroadManager softKeyBroadManager;

    boolean usernameHasFocus;
    boolean passwordHasFocus;
    /**
     * 0 忽略
     * 1 做判断用的
     * 2 不要关闭输入法
     */
    int notCloseKeybord;


    /**
     * 跳转登录页面
     *
     * @param context
     */
    public static void startActivity(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);//后期放开
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login_copy;
    }

    @Override
    protected LoginContract.Presenter initPresenter() {
        return new LoginPresenter();
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);

        usernameEt.setHintTextColor(getResources().getColor(R.color.white_bg));
        passwordEt.setHintTextColor(getResources().getColor(R.color.white_bg));

        String username = (String) SPUtils.get(SPUtils.KEY_USER_NAME, "liuxiaoning");//testuser
        String password = (String) SPUtils.get(SPUtils.KEY_USER_PASS, "Test1234");//Test1234
        usernameEt.setText(username);
        passwordEt.setText(password);

        softKeyBroadManager = new SoftKeyBroadManager(content);
        softKeyBroadManager.addSoftKeyboardStateListener(softKeyboardStateListener);

        usernameHasFocus = false;
        passwordHasFocus = false;
        notCloseKeybord = 0;
        //根据是否有焦点更新背景（这里只是演示使用，其实这种更换背景的效果可以通过Selector来实现）
        usernameEt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                usernameHasFocus = hasFocus;

                //获得焦点，如果密码框刚失去焦点
                if(hasFocus && notCloseKeybord == 1){
                    //不要关闭输入法
                    notCloseKeybord = 2;
                }else{
                    notCloseKeybord = 0;
                }
            }
        });
        passwordEt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                passwordHasFocus = hasFocus;

                //如果这个时候刚失去焦点
                if(!hasFocus){
                    notCloseKeybord = 1;
                }

            }
        });

    }

    private SoftKeyBroadManager.SoftKeyboardStateListener softKeyboardStateListener = new
            SoftKeyBroadManager.SoftKeyboardStateListener() {

                @Override
                public void onSoftKeyboardOpened(int keyboardHeightInPx) {
                    onKeyBoardShow();

                }

                @Override
                public void onSoftKeyboardClosed() {
                    onKeyBoardHidden();
                }
            };

    @OnClick({R.id.btn_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login: {
                getPresenter().login(usernameEt.getText().toString(), passwordEt.getText().toString());
            }
            break;
        }
    }

    @Override
    public void onLoginCallback(UserInfo userInfo) {
        ToastUtil.show("登录成功！");

        SPUtils.put(SPUtils.KEY_USER_NAME, usernameEt.getText().toString());
        SPUtils.put(SPUtils.KEY_USER_PASS, passwordEt.getText().toString());

        MainActivity.startActivity(this);

        UserInfoUtil.saveUserInfo(userInfo);

        finish();

    }


    private void goUp(View view, View view2) {
        ObjectAnimator transYAnim = ObjectAnimator.ofFloat(view, "translationY", 0, -view2.getHeight() + 20);
        transYAnim.setDuration(ANIM_DURATION);
        transYAnim.start();
    }

    /**
     * 缩小
     * 属性动画
     *
     * @param view
     */
    public void zoomOut(final View view, float scale, float dist) {
        view.setPivotY(0);
        view.setPivotX(view.getWidth() / 2);
        AnimatorSet mAnimatorSet = new AnimatorSet();
        ObjectAnimator mAnimatorScaleX = ObjectAnimator.ofFloat(view, "scaleX", 1.0f, scale);
        ObjectAnimator mAnimatorScaleY = ObjectAnimator.ofFloat(view, "scaleY", 1.0f, scale);
        ObjectAnimator mAnimatorAlpha = ObjectAnimator.ofFloat(view, "alpha", 1.0f, 0.8f, 0.6f, 0.4f, 0.2f, 0.0f);
        //     ObjectAnimator transYAnim = ObjectAnimator.ofFloat(view, "translationY", view.getHeight()/2,-view.getHeight()/3);

        mAnimatorSet.playTogether(mAnimatorAlpha, mAnimatorScaleX, mAnimatorScaleY);
        mAnimatorSet.setDuration(ANIM_DURATION);
        mAnimatorSet.start();
    }

    private void goDonw(View view, View view2) {
        //  ObjectAnimator transXAnim = ObjectAnimator.ofFloat(view, "translationX", 100, 400);
        ObjectAnimator transYAnim = ObjectAnimator.ofFloat(view, "translationY", -view2.getHeight() + 20, 0);
        transYAnim.setDuration(ANIM_DURATION);
        transYAnim.start();
    }

    /**
     * f放大
     *
     * @param view
     */
    public void zoomIn(final View view, float scale) {
        view.setPivotY(0);
        view.setPivotX(view.getWidth() / 2);
        AnimatorSet mAnimatorSet = new AnimatorSet();

        ObjectAnimator mAnimatorScaleX = ObjectAnimator.ofFloat(view, "scaleX", scale, 1.0f);
        ObjectAnimator mAnimatorScaleY = ObjectAnimator.ofFloat(view, "scaleY", scale, 1.0f);
        ObjectAnimator mAnimatorAlpha = ObjectAnimator.ofFloat(view, "alpha", 0.0f, 0.2f, 0.4f, 0.6f, 0.8f, 1.0f);
        //   ObjectAnimator transYAnim = ObjectAnimator.ofFloat(view, "translationY", view.getHeight()/2,view.getHeight()/3);

        mAnimatorSet.playTogether(mAnimatorAlpha, mAnimatorScaleX, mAnimatorScaleY);
        mAnimatorSet.setDuration(ANIM_DURATION);
        mAnimatorSet.start();
    }


    @Override
    protected boolean applyFullScreen() {
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }


    public void onKeyBoardShow() {
        Log.d("lwjing", "键盘显示");
        Log.d("姓名输入框是否有焦点：", usernameHasFocus ? "YES" : "NO");
        Log.d("密码输入框是否有焦点：", passwordHasFocus ? "YES" : "NO");
        Log.d("状态", notCloseKeybord+"");
        Log.d("分割——————","————————————————————————————————————");
        if(notCloseKeybord != 4){
            //缩小动画
            zoomOut(tvLoginLogo,0f,0f);
            //上移动画
            goUp(llLogin,tvLoginLogo);
            notCloseKeybord = 0;
        }else{
            notCloseKeybord = 0;
        }
    }


    public void onKeyBoardHidden() {
        Log.d("lwjing", "键盘隐藏");
        Log.d("姓名输入框是否有焦点：", usernameHasFocus ? "YES" : "NO");
        Log.d("密码输入框是否有焦点：", passwordHasFocus ? "YES" : "NO");
        Log.d("状态", notCloseKeybord+"");
        Log.d("分割——————","————————————————————————————————————");

        if(notCloseKeybord != 2){
            //放大动画
            zoomIn(tvLoginLogo,0f);
            goDonw(llLogin,tvLoginLogo);

            notCloseKeybord = 0;
        }else{
            notCloseKeybord = 4;
        }

    }
}

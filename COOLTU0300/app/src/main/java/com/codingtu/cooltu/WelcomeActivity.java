package com.codingtu.cooltu;

import com.codingtu.cooltu.lib.data.User;
import com.codingtu.cooltu.lib.ui.page.BaseWelcomeActivity;
import com.codingtu.cooltu.processor.annotation.activity.ActBase;
import com.codingtu.cooltu.processor.annotation.base.BaseClass;
import com.codingtu.cooltu.processor.annotation.base.Fanxing;
import com.codingtu.cooltu.processor.annotation.ui.Layout;

import core.app.actbase.WelcomeActivityBase;

@ActBase
@Layout(R.layout.activity_welcome)
@BaseClass(
        value = BaseWelcomeActivity.class,
        fanxings = {
                @Fanxing(name = "T", extendsClass = User.class),
                @Fanxing(String.class),
                @Fanxing(name = "F"),
        })
public class WelcomeActivity extends WelcomeActivityBase<User, User> {
}

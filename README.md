# 自定义密码输入框

### 效果：

![效果图](https://github.com/genius-ye/PasswordInputView_library/blob/master/device-2016-03-22-155217.png?raw=true)

### 使用方法：

```xml
    <com.genius.view.PasswordInputView
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
             app:passwordLength="4"
             app:borderColor="#cccbcb"
             app:passwordColor="#4f4e4e"
             app:passwordColor_bg="#e6e6e6"
             app:isShowPwd="true"
            />
```
### 说明：

> `passwordLength`表示密码长度，也就是格子的个数，`isShowPwd`表示是否显示密码，`passwordColor_bg`表示密码框的背景色，`borderColor`密码边框的颜色
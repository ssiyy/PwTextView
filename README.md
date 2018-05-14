# 一个高度灵活的仿微信密码框
依赖：
implementation 'com.siyer:pwdview:1.0.0‘

## Demo截图
![demo](./show.gif)

## 绘制原理图
！[demo](./show1.gif)

## 代码
```xml
<com.siy.pwdview.PwdView
	android:id="@+id/pwdviewId"
	android:layout_width="match_parent"
	android:layout_height="100dp"
	android:textColor="@color/colorAccent"
	android:textSize="26sp"/>
```
## 对外接口
| 接口名称 | 参数 | 参数类型 | 默认值 | 解释 |
| :---: | :---: | :-------: | :---: | :---:|
| showCursor | 无 | 无 | 无 | 显示光标 |
| hideCursor | 无 | 无 | 无 | 隐藏光标 |
| isShowCursor | 无 | 无 | 无 | 判断光标是否显示 |
| setBorderColor | borderColor | int |Color.parseColor("#818b90") |设置边框的颜色|
|setBorderWidth|borderWidth|int|1dip|设置边框的宽|
|setBorderRadius|borderRadius|int|0|设置边框的圆角|
|setContentColor|contentColor|int|Color.WHITE|设置内容的颜色|
|setContentRadius|contentRadius|int|0|设置内容的圆角|
|setSplitLineColor|splitLineColor|int|Color.parseColor("#818b90")|设置分割线的颜色|
|setCursorColor|cursorColor|int|Color.parseColor("#000000")|设置光标的颜色|
|setCursorMargin|cursorMargin|int|10dip|设置光标距离边框的距离|
|setPwdLen|pwdLen|int|6|设置密码长度|
|getPwdLen|无|无|6|获取当前密码框密码的长度|
|setPwdColor|pwdColor|int|Color.parseColor("#000000")|设置密码显示小圆点的颜色|
|setPwdWidth|pwdWidth|int|28dip|设置密码显示小圆点直径|
|setContentMargin|contentMargin|int|1dip|设置内容区域之间的间隔|
|setContentBoardColor|contentBoardColor|int|Color.TRANSPARENT|设置内容边界的颜色|
|setContentBoardWidth|contentBoardWidth|int|0|设置内容边界的宽度|
|setAppendText|appendText|CharSequence|null|用追加的形式传入信息|
|deleteLast|无|无|无|从追后一个字符逐个删除|
|showPwdText|showPwdText|boolean|false|是否显示明文|
|isShowPwdText|无|无|无|当前显示的是否是明文|

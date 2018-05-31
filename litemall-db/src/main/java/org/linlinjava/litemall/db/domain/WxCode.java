package org.linlinjava.litemall.db.domain;

public class WxCode {
    private String scene;      //最大32个可见字符，只支持数字，大小写英文以及部分特殊字符：!#$&'()*+,/:;=?@-._~，其它字符请自行编码为合法字符（因不支持%，中文无法使用 urlencode 处理，请使用其他编码方式）
    private String page;       //必须是已经发布的小程序存在的页面（否则报错），例如 "pages/index/index" ,根路径前不要填加'/',不能携带参数（参数请放在scene字段里），如果不填写这个字段，默认跳主页面
    private Integer width;     //默认值430 二维码的宽度
    private Boolean auto_color;//默认值false 自动配置线条颜色，如果颜色依然是黑色，则说明不建议配置主色调
    private Object line_color; //默认值{"r":"0","g":"0","b":"0"} auto_color 为 false 时生效，使用 rgb 设置颜色 例如 {"r":"xxx","g":"xxx","b":"xxx"} 十进制表示
    private Boolean is_hyaline;//默认值false 是否需要透明底色， is_hyaline 为true时，生成透明底色的小程序码
    private String path;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getScene() {
        return scene;
    }

    public void setScene(String scene) {
        this.scene = scene;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Boolean getAuto_color() {
        return auto_color;
    }

    public void setAuto_color(Boolean auto_color) {
        this.auto_color = auto_color;
    }

    public Object getLine_color() {
        return line_color;
    }

    public void setLine_color(Object line_color) {
        this.line_color = line_color;
    }

    public Boolean getIs_hyaline() {
        return is_hyaline;
    }

    public void setIs_hyaline(Boolean is_hyaline) {
        this.is_hyaline = is_hyaline;
    }
}

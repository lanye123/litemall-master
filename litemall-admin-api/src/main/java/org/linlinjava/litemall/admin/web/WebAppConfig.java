package org.linlinjava.litemall.admin.web;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @ClassName: WebAppConfig
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Administrator
 * @date 2017年7月11日
 */
/*@Configuration
public class WebAppConfig extends WebMvcConfigurerAdapter {
    private final Log logger = LogFactory.getLog(WebAppConfig.class);
    //获取配置文件中图片的路径
    @Value("${cbs.imagesPath}")
    private String mImagesPath;

    //访问图片方法
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        if (mImagesPath.equals("") || mImagesPath.equals("${cbs.imagesPath}")) {
            String imagesPath = WebAppConfig.class.getClassLoader().getResource("").getPath();
            if (imagesPath.indexOf(".jar") > 0) {
                imagesPath = imagesPath.substring(0, imagesPath.indexOf(".jar"));
            } else if (imagesPath.indexOf("classes") > 0) {
                imagesPath = "file:" + imagesPath.substring(0, imagesPath.indexOf("classes"));
            }
            imagesPath = imagesPath.substring(0, imagesPath.lastIndexOf("/")) + "/images/";
            mImagesPath = imagesPath;
        }
        registry.addResourceHandler("/images/**").addResourceLocations(mImagesPath);
        super.addResourceHandlers(registry);
    }
}*/

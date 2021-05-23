package news.Config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Value("${upload.path}")
    private String uploadPath;
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        var path = "file:\\\\\\" +  new File(uploadPath).getAbsolutePath() + "\\";
        registry.addResourceHandler("/img/**")
                //.addResourceLocations("classpath:/img/");
                .addResourceLocations(path);
    }
}

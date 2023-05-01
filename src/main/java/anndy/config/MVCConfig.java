package anndy.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MVCConfig implements WebMvcConfigurer {
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/home").setViewName("profile/profile");
        registry.addViewController("/").setViewName("profile/profile");
        registry.addViewController("/sign_in").setViewName("sign_in");
        registry.addViewController("/sign_up").setViewName("sign_up");
    }
}

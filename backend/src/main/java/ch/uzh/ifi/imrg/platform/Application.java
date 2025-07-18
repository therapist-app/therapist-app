package ch.uzh.ifi.imrg.platform;

import ch.uzh.ifi.imrg.platform.security.TherapistIdArgumentResolver;
import ch.uzh.ifi.imrg.platform.utils.RouteLoggingInterceptor;
import java.util.List;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@RestController
@SpringBootApplication
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @GetMapping(value = "/", produces = MediaType.TEXT_PLAIN_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public String helloWorld() {
    return "The application is running.";
  }

  @Bean
  public WebMvcConfigurer webMvcConfigurer(
      RouteLoggingInterceptor routeLoggingInterceptor,
      TherapistIdArgumentResolver therapistIdArgumentResolver) {
    return new WebMvcConfigurer() {
      @Override
      public void addCorsMappings(CorsRegistry registry) {
        registry
            .addMapping("/**")
            .allowedOriginPatterns("*")
            .allowedMethods("*")
            .allowedHeaders("*")
            .allowCredentials(true);
      }

      @Override
      public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(routeLoggingInterceptor).addPathPatterns("/**");
      }

      @Override
      public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(therapistIdArgumentResolver);
      }
    };
  }

  @Bean
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }
}

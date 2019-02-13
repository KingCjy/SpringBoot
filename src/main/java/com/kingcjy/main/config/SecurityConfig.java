package com.kingcjy.main.config;

import com.kingcjy.main.domain.enums.SocialType;
import com.kingcjy.main.oauth.ClientResources;
import com.kingcjy.main.oauth.UserTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.CompositeFilter;

import javax.servlet.Filter;
import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableOAuth2Client
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Qualifier("oauth2ClientContext")
    @Autowired
    private OAuth2ClientContext oAuth2ClientContext;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        CharacterEncodingFilter filter = new CharacterEncodingFilter();

        http
                .authorizeRequests()
                    .antMatchers("/", "/login/**", "/css/**", "/images/**", "/js/**", "/console/**").permitAll()
                    .anyRequest().permitAll()
                .and()
                    .headers().frameOptions().disable()
                .and()
                    .exceptionHandling()
                    .authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login"))
                .and()
                    .formLogin()
                    .successForwardUrl("/")
                .and()
                    .logout()
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/")
                    .deleteCookies("JSESSIONID")
                    .invalidateHttpSession(true)
                .and()
                    .addFilterBefore(filter, CsrfFilter.class)
                    .addFilterBefore(oauth2Filter(), BasicAuthenticationFilter.class)
                    .csrf().disable();
    }

    @Bean
    public FilterRegistrationBean oauth2ClientFilterRegistration(OAuth2ClientContextFilter filter) {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(filter);
        registration.setOrder(-100);
        return registration;
    }

    private Filter oauth2Filter() {
        CompositeFilter filter = new CompositeFilter();
        List<Filter> filterList = new ArrayList();

        filterList.add(oauth2Filter(kakao(), "/login/kakao", SocialType.KAKAO));
        filter.setFilters(filterList);
        return filter;
    }

    private Filter oauth2Filter(ClientResources client, String path, SocialType socialType) {
//        인증이 수행될 경로를 넣어 OAuth2 클라이언트용 인증 처리 필터 생성
        OAuth2ClientAuthenticationProcessingFilter filter = new OAuth2ClientAuthenticationProcessingFilter(path);
//        권한 서버와의 통신을 위해 restTemplate를 생성 생성하기 위해선 client 프로퍼티 정보와 OAuth2ClientContext 가 필요
        OAuth2RestTemplate restTemplate = new OAuth2RestTemplate(client.getClient(), oAuth2ClientContext);

        filter.setRestTemplate(restTemplate);
//        User의 권한을 최적화해서 생성하고자 UserInfoTokenService를 상속받아 생성함. 필터의 토큰 서비스로 등록
        filter.setTokenServices(new UserTokenService(client, socialType));
//        인증이 성공했을때 redirect 될 url을 설정
        filter.setAuthenticationSuccessHandler((request, response, authentication) ->
                response.sendRedirect("/" + socialType.getValue() + "/complete"));
//        인증이 실패했을때 redirect 될 url을 설정
        filter.setAuthenticationFailureHandler((request, response, exception) ->
                response.sendRedirect("/error"));
        return filter;
    }

    @Bean
    @ConfigurationProperties("kakao")
    public ClientResources kakao() {
        return new ClientResources();
    }
}
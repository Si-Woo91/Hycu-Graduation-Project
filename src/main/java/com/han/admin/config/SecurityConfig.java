package com.han.admin.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity // 시큐리티 관리 설정
public class SecurityConfig {

    private final AuthenticationFailureHandler customFailureHandler;
    
    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
		        .csrf((csrf) -> csrf
						.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
				)

                .authorizeHttpRequests((authorizeRequests) ->
                        authorizeRequests
                        		.requestMatchers("/userList", "/prodList").hasRole("ADMIN") // 'admin' 권한이 있는 사람만 접근 가능
                                .requestMatchers("/", "/js/**", "/css/**").permitAll()  // 권한 없는 모든 사용자 접근 가능
                                .anyRequest().authenticated()
                )
                .formLogin((formLogin) ->
                        formLogin
                        .loginPage("/") 
                        .failureHandler(customFailureHandler) // 로그인 실패시 핸들러
                        .usernameParameter("userId")
                        .defaultSuccessUrl("/userList", true) // 로그인 성공 시 이동할 URL
                )
                .logout((logoutConfig) ->
                        logoutConfig
                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                .logoutSuccessUrl("/")
                                .invalidateHttpSession(true)	// 로그아웃시 세션삭제
                );

        return http.build();
    }


    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder(){
    	return new BCryptPasswordEncoder();
    }
    
    
}

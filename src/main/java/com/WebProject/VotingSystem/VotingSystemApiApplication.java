package com.WebProject.VotingSystem;

import com.WebProject.VotingSystem.filters.AdminFilter;
import com.WebProject.VotingSystem.filters.UserFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class VotingSystemApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(VotingSystemApiApplication.class, args);
	}

	@Bean
	public FilterRegistrationBean<AdminFilter> adminFilterRegistrationBean(){
		FilterRegistrationBean<AdminFilter> registrationBean = new FilterRegistrationBean<>();
		AdminFilter adminFilter = new AdminFilter() ;
		registrationBean.setFilter(adminFilter);
		registrationBean.addUrlPatterns("/api/admin/*");
		return registrationBean;
	}

	@Bean
	public FilterRegistrationBean<UserFilter> userFilterRegistrationBean(){
		FilterRegistrationBean<UserFilter> registrationBean = new FilterRegistrationBean<>();
		UserFilter userFilter = new UserFilter() ;
		registrationBean.setFilter(userFilter);
		registrationBean.addUrlPatterns("/api/user/*");
		return registrationBean;
	}

}

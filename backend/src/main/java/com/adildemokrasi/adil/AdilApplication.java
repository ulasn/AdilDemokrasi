package com.adildemokrasi.adil;

import com.adildemokrasi.adil.Entity.Role;
import com.adildemokrasi.adil.Entity.User;
import com.adildemokrasi.adil.Service.RoleService;
import com.adildemokrasi.adil.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringBootApplication
public class AdilApplication {

	@Autowired
	RoleService roleService;

	public static void main(String[] args) {
		SpringApplication.run(AdilApplication.class, args);
	}

//	Collection<? extends GrantedAuthority> authorities;
//
//	public CustomUserDetails(User user){
//		this.user = user;
//
//		List<GrantedAuthority> auths = new ArrayList<>();
//		for(Role role : user.getRoles()){
//			auths.add(new SimpleGrantedAuthority(role.getRole().toUpperCase()));
//		}

	@Bean
	public FilterRegistrationBean corsFilterRegistration() {
		FilterRegistrationBean registrationBean =
				new FilterRegistrationBean(new CORSFilter());
		registrationBean.setName("CORS Filter");
		registrationBean.addUrlPatterns("/*");
		registrationBean.setOrder(1);
		return registrationBean;
	}

	@Bean
	public CommandLineRunner setupDefaultUser(UserService service) {
		roleService.addRole(new Role("ADMIN"));
		roleService.addRole(new Role("USER"));

		Set<Role> currentRoles = new HashSet<>(roleService.getRoles());
		User adminUser = new User(
				"ulas", //username
				"nuhoglu", //password
				"admin",
				"email",
				"admin",
				currentRoles
		);

		adminUser.setActive(true);

		return args -> {
			service.save(adminUser);
		};
	}

}


package com.ms.common.service;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.crypto.bcrypt.BCrypt;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ms.common.dto.requestDto.AuthRequestDto;
import com.ms.common.dto.responseDto.AuthResponseDto;
import com.ms.common.dto.responseDto.UserResponseDto;
import com.ms.common.entity.ERole;
import com.ms.common.entity.Role;
import com.ms.common.entity.User;
import com.ms.common.exception.ServiceException;
import com.ms.common.repository.RoleRepository;
import com.ms.common.repository.UserRepository;

import jakarta.annotation.Nonnull;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class UserServiceImpl implements UserService {

	Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private JWTService jwtService;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RoleRepository roleRepository;

	@Override
	public AuthResponseDto login(AuthRequestDto authRequestDto, @Nonnull HttpServletRequest request) {
		User user = userRepository.findByUsernameAndEnabledTrue(authRequestDto.getUsername())
				.orElseThrow(() -> new ServiceException("CS01"));

		if (user != null) {
			if (!user.getForceValidation()) {
				UserResponseDto userResponseDto = modelMapper.map(user, UserResponseDto.class);
				log.error("Please verify your account, otp send to your mobile number");
				throw new ServiceException("CS21", userResponseDto);
			}
			if (BCrypt.checkpw(authRequestDto.getPassword(), user.getPassword().toString())) {
				try {
					String jwtToken = jwtService.generateToken(user.getUsername());
					UserResponseDto userResponseDto = modelMapper.map(user, UserResponseDto.class);

					request.getSession().setAttribute("username", user.getUsername());

					AuthResponseDto authResponseDto = new AuthResponseDto(jwtToken, userResponseDto);

					return authResponseDto;
				} catch (Exception e) {
					log.error("JWT Token creation failed");
					throw new ServiceException("CS06");
				}
			} else {
				log.error("Wrong credentials");
				throw new ServiceException("CS02", HttpStatus.UNAUTHORIZED);
			}
		} else {
			log.error("User not found");
			throw new ServiceException("CS01", HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public void addSuperUser() {
		for (ERole eRole : ERole.values()) {
			try {
				Role exsistRole = roleRepository.findByNameAndEnabledTrue(eRole.name()).get();
				if (exsistRole == null) {
					generateRole(eRole.name());
				}
			} catch (Exception e) {
				generateRole(eRole.name());
			}
		}

		try {
			User user = userRepository.findByUsernameAndEnabledTrue("8708809806").get();
			if (user == null) {
				generateSuperUser();
			}
		} catch (Exception e) {
			generateSuperUser();
		}
	}

	private void generateSuperUser() {
		User user = new User();
		user.setName("Jitender");
		user.setUsername("8708809806");
		user.setEmail("jitender221216@gmail.com");
		user.setMobileNumber("8708809806");
		user.setForceValidation(true);
		user.setCreatedBy("8708809806");
		user.setUpdatedBy("8708809806");
//		user.setPassword(passwordEncoder.encode("#Universal@app"));

		Role exsistRole = roleRepository.findByNameAndEnabledTrue("ROLE_SUPER_ADMIN").get();
		if (exsistRole == null) {
			generateRole("ROLE_SUPER_ADMIN");
			exsistRole = roleRepository.findByNameAndEnabledTrue("ROLE_SUPER_ADMIN").get();
		}

		user.setRole(exsistRole);
		userRepository.save(user);
		log.info("Super Admin User created successfully");
	}

	private void generateRole(String roleName) {
		Role role = new Role();
		role.setName(roleName);
		role.setCreatedBy("8708809806");
		role.setUpdatedBy("8708809806");
		role.setDescription("This role is only for " + roleName);
		roleRepository.save(role);
		log.info(roleName + " created successfully");
	}

//	@Override
//	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//
//		User user = userRepository.findByUsernameAndEnabledTrue(username).get();
//		if (user != null)
//			return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
//					user.getAuthorities());
//		else {
//			throw new UsernameNotFoundException("username is not valid");
//		}
//	}
}

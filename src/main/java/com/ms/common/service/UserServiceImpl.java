package com.ms.common.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.crypto.bcrypt.BCrypt;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ms.common.dto.requestDto.AuthRequestDto;
import com.ms.common.dto.requestDto.UserRequestDto;
import com.ms.common.dto.requestDto.UserUpdateRequestDto;
import com.ms.common.dto.responseDto.AuthResponseDto;
import com.ms.common.dto.responseDto.UserResponseDto;
import com.ms.common.entity.ERole;
import com.ms.common.entity.Role;
import com.ms.common.entity.User;
import com.ms.common.exception.ServiceException;
import com.ms.common.repository.RoleRepository;
import com.ms.common.repository.UserRepository;
import com.ms.common.utils.CommonUtil;

import jakarta.annotation.Nonnull;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

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
		user.setPassword(passwordEncoder.encode("#Universal@app"));

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

	@Override
	public UserResponseDto adduser(@Valid UserRequestDto userRequestDto) {
		User user = new User();
		UserResponseDto userResponseDto = new UserResponseDto();

		if (userRepository.existsByUsernameAndEnabledTrue(userRequestDto.getUsername())) {
			log.error("Username Already Taken");
			throw new ServiceException("CS04");
		}
		if (!CommonUtil.isValidPassword(userRequestDto.getPassword())) {
			log.error("Use 8 or more characters with a mix of letters, numbers & symbols");
			throw new ServiceException("CS07");
		}
		if (!userRequestDto.getPassword().equals(userRequestDto.getRePassword())) {
			log.error("Password and confirm Password must be same");
			throw new ServiceException("CS08");
		}

		user = CommonUtil.convert(userRequestDto, User.class);

		user.setCreatedBy(userRequestDto.getUsername());
		user.setUpdatedBy(userRequestDto.getUsername());
		user.setPassword(passwordEncoder.encode(user.getPassword().trim()));

		Role role = roleRepository.findByNameAndEnabledTrue("ROLE_USER").get();
		if (role == null) {
			log.error("Role not found");
			throw new ServiceException("CS17");
		}
		user.setRole(role);

		user = userRepository.save(user);
		if (user == null) {
			log.error("User Creation failed");
			throw new ServiceException("CS09");
		}

		userResponseDto = modelMapper.map(user, UserResponseDto.class);

		return userResponseDto;
	}

	@Override
	public UserResponseDto updateUser(@Valid UserUpdateRequestDto userUpdateRequestDto) {
		User user = new User();
		UserResponseDto userResponseDto = new UserResponseDto();
		user = userRepository.findByIdAndEnabledTrue(Long.valueOf(userUpdateRequestDto.getId())).get();
		if (user == null) {
			log.error("User not found");
			throw new ServiceException("CS01");
		}
		if (!userUpdateRequestDto.getUsername().equals(user.getUsername())) {
			if (userRepository.existsByUsernameAndEnabledTrue(userUpdateRequestDto.getUsername())) {
				log.error("Username Already Taken");
				throw new ServiceException("CS04");
			}
		}

		user.setName(userUpdateRequestDto.getName());
		user.setUsername(userUpdateRequestDto.getUsername());
		user.setEmail(userUpdateRequestDto.getEmail());
		user.setMobileNumber(userUpdateRequestDto.getMobileNumber());
		user.setUpdatedBy(userUpdateRequestDto.getUsername());

//		user.setPassword(passwordEncoder.encode(user.getPassword().trim()));

		user = userRepository.save(user);
		if (user == null) {
			log.error("User update failed");
			throw new ServiceException("CS43");
		}

		userResponseDto = modelMapper.map(user, UserResponseDto.class);
		return userResponseDto;
	}

	@Override
	public UserResponseDto getUserById(Long id, @NotNull HttpServletRequest request) {
		User user = getUserDetail(request);
		if (ERole.ROLE_USER.name().equals(user.getRole().getName())
				|| ERole.ROLE_EMPLOYEE.name().equals(user.getRole().getName())) {
			return modelMapper.map(user, UserResponseDto.class);
		}
		if (id != null && id != 0) {
			user = userRepository.findById(id).orElseThrow(() -> new ServiceException("CS01"));
			return modelMapper.map(user, UserResponseDto.class);
		}
		return null;
	}

	@Override
	public List<UserResponseDto> getAllUser(Integer perPage, Integer page, @NotNull HttpServletRequest request) {
		User user = getUserDetail(request);

		List<UserResponseDto> userResponseDtos = new ArrayList<>();
		UserResponseDto userResponseDto = new UserResponseDto();

		Pageable pageable = setPageable(perPage, page);

		List<User> users = new ArrayList<>();
		if (ERole.ROLE_SUPER_ADMIN.name().equals(user.getRole().getName())) {
			users = userRepository.findByEnabledTrue(pageable);
		} else if (ERole.ROLE_ADMIN.name().equals(user.getRole().getName())
				|| ERole.ROLE_EMPLOYEE.name().equals(user.getRole().getName())) {
			Set<Integer> roles = new HashSet<>();
			roles.add(1);
			users = userRepository.findByRoleRoleIdNotInAndEnabledTrue(roles, pageable);

		} else {
			userResponseDtos = new ArrayList<>();
		}

		if (users == null || users.isEmpty()) {
			log.info("user not found");
			return userResponseDtos;
		}
		for (User user3 : users) {
			userResponseDto = modelMapper.map(user3, UserResponseDto.class);
			userResponseDtos.add(userResponseDto);
		}

		return userResponseDtos;
	}

	@Override
	public void deleteUser(Long id, @NotNull HttpServletRequest request) {
		User user = getUserDetail(request);
		if (ERole.ROLE_ADMIN.name().equals(user.getRole().getName())
				|| ERole.ROLE_SUPER_ADMIN.name().equals(user.getRole().getName())) {
			if (id != null && id != 0) {
				if (userRepository.existsById(id)) {
					User user1 = userRepository.findById(id).get();
					user1.setEnabled(false);
					userRepository.save(user1);
				} else {
					log.error("User not found");
					throw new ServiceException("CS01");
				}
			}
		} else {
			user.setEnabled(false);
			userRepository.save(user);
			request.setAttribute("username", "");
		}

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
	
	private User getUserDetail(@NotNull HttpServletRequest request) {
		String username = (String) request.getSession().getAttribute("username");
		if (username == null || username.isBlank()) {
			log.error("Unthorized request");
			throw new ServiceException("CS46");
		}
		User user = userRepository.findByUsernameAndEnabledTrue(username).get();
		if (user == null) {
			log.error("User not found");
			throw new ServiceException("CS01");
		}
		return user;
	}

	private Pageable setPageable(Integer perPage, Integer page) {
		if (perPage == null || perPage == 0) {
			perPage = 10;
		}
		if (page == null || page == 0) {
			page = 1;
		}

		Pageable pageable = PageRequest.of((page - 1) * perPage, perPage);
		return pageable;
	}
}

package com.ms.common.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ms.common.dto.requestDto.RoleRequestDto;
import com.ms.common.entity.ERole;
import com.ms.common.entity.Role;
import com.ms.common.entity.User;
import com.ms.common.exception.ServiceException;
import com.ms.common.repository.RoleRepository;
import com.ms.common.repository.UserRepository;
import com.ms.common.utils.CommonUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@Service
public class RoleServiceImpl implements RoleService {

	Logger log = LoggerFactory.getLogger(RoleServiceImpl.class);

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private UserRepository userRepository;

	@Override
	public String saveRole(@Valid RoleRequestDto roleRequestDto, @NotNull HttpServletRequest request) {

		Role role = new Role();
		String username = (String) request.getSession().getAttribute("username");

		if (roleRepository.existsByNameAndEnabledTrue(roleRequestDto.getName().toUpperCase())) {
			log.error("Role already exists");
			throw new ServiceException("CS18");
		}

		Map<String, String> roleMap = new HashMap<>();
		for (ERole eRole : ERole.values()) {
			roleMap.put(eRole.name(), eRole.name());
		}

		if (!roleMap.containsKey(roleRequestDto.getName().toUpperCase())) {
			log.error("Unknown role");
			throw new ServiceException("CS20");
		}

		role = CommonUtil.convert(roleRequestDto, Role.class);
		role.setName(role.getName().toUpperCase());

		role.setCreatedBy(username);
		role.setUpdatedBy(username);
		role = roleRepository.save(role);
		if (role == null) {
			log.error("Role creation failed");
			throw new ServiceException("CS13");
		}
		return role.getName();
	}

	@Override
	public List<Role> getAllRoles() {

		return roleRepository.findByEnabledTrue();
	}

	@Override
	public void deleteRole(Integer id) {

		Role role = roleRepository.findByRoleIdAndEnabledTrue(id).get();
		if (role == null) {
			log.error("Role not found");
			throw new ServiceException("CS17");
		}

		role.setEnabled(false);

		role = roleRepository.save(role);
		if (role == null) {
			log.error("Role not found");
			throw new ServiceException("CS17");
		}

	}

	@Override
	public void assignRole(Integer roleId, Long userId) {

		User user = userRepository.findByIdAndEnabledTrue(userId).get();
		if (user == null) {
			log.error("User not found");
			throw new ServiceException("CS01");
		}

		Role role = roleRepository.findByRoleIdAndEnabledTrue(roleId).get();
		if (role == null) {
			log.error("Role not found");
			throw new ServiceException("CS17");
		}

		Set<Role> roles = new HashSet<>();

		if (role.getName().equals("ROLE_SUPER_ADMIN")) {
			log.error("Cannot assign this role");
			throw new ServiceException("CS27");
		}

//		roles.add(role);
//
//		user.setRoles(roles);
		user.setRole(role);

		user = userRepository.save(user);
		if (user == null) {
			log.error("Failed to assign role");
			throw new ServiceException("CS28");
		}
	}

}

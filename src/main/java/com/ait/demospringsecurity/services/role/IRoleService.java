package com.ait.demospringsecurity.services.role;

import java.util.Optional;


import com.ait.demospringsecurity.models.ERole;
import com.ait.demospringsecurity.models.Role;
import com.ait.demospringsecurity.services.IGeneralService;

public interface IRoleService extends IGeneralService<Role>{
	Optional<Role> findByName(ERole name);
}

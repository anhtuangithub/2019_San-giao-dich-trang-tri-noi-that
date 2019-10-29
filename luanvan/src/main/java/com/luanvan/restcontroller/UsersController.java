package com.luanvan.restcontroller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.luanvan.dto.request.CustomerDTO;
import com.luanvan.dto.request.ResetPasswordDTO;
import com.luanvan.dto.request.RoleUserDTO;
import com.luanvan.dto.request.StoreDTO;
import com.luanvan.dto.request.TestDTO;
import com.luanvan.dto.response.InfoUserDTO;
import com.luanvan.model.Users;
import com.luanvan.service.UsersService;

@RestController
@RequestMapping("users")
public class UsersController {

	
	private UsersService usersService;
	
	@Autowired
	public UsersController(UsersService usersService) {
		this.usersService = usersService; 
	}
	
	@GetMapping()
	public List<Users> findAllUsers(){
		return usersService.findAllUsers();
	}
	
	@GetMapping("/counts")
	public List<RoleUserDTO> countAllUsers(){
		return usersService.countUserAllRole();
	}
	
//	@GetMapping("dang-nhap")
//	public UserDTO dangNhap() {
//		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//		String username = "";
//		if (principal instanceof UserDetails) {
//		 username = ((UserDetails)principal).getUsername();
//		} else {
//		 username = principal.toString();
//		}
//		return usersService.findByEmail(username);
//	}
	
	@GetMapping("check/{email}")
	public Map<String, String> checkEmail(@PathVariable String email){
		return usersService.checkEmail(email);
	}
	
	@GetMapping("info")
	public InfoUserDTO infoAccount(Authentication auth){
		return usersService.info(auth);
	}
	
	@PostMapping()
	public void create(@RequestBody Users users){
		usersService.save(users);
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		usersService.delete(id);
	}
	
	@PostMapping("dang-ki")
	public void dangKi(@RequestBody CustomerDTO customerDTO) {
		usersService.saveCustomer(customerDTO);
	}
	
	@PostMapping("dktv/{id}")
	public void DKTV(@Valid @RequestBody StoreDTO storeDTO,@PathVariable Long id) {
		usersService.saveStore(storeDTO, id);
	}
	
	@PostMapping("/roles")
	public void testDTO(@RequestBody TestDTO testDTOs){
		 usersService.multiSave(testDTOs);
	}
	
	
	@GetMapping("thong-tin-nguoi-dang-nhap")
	public Authentication Authentication(Authentication auth) {
		return auth;
	}
	
	@GetMapping("resetPassword")
	public void resetPassword(@RequestParam(value="email") String email) {
		usersService.resetPassword(email);
	}
	
	@PostMapping("savePassword")
	public void saveChangePassword(@Valid @RequestBody ResetPasswordDTO password) {
       usersService.savePassword(password);
	}
}

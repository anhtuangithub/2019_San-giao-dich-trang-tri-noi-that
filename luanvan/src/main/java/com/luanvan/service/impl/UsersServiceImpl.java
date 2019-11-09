package com.luanvan.service.impl;

import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.luanvan.dto.request.CustomerDTO;
import com.luanvan.dto.request.ResetPasswordDTO;
import com.luanvan.dto.request.RoleUserDTO;
import com.luanvan.dto.request.StoreDTO;
import com.luanvan.dto.request.TestDTO;
import com.luanvan.dto.response.InfoUserDTO;
import com.luanvan.dto.response.UserDTO;
import com.luanvan.exception.NotFoundException;
import com.luanvan.model.Customer;
import com.luanvan.model.Member;
import com.luanvan.model.PasswordResetToken;
import com.luanvan.model.Role;
import com.luanvan.model.Store;
import com.luanvan.model.Users;
import com.luanvan.repo.CustomerRepository;
import com.luanvan.repo.MemberRepository;
import com.luanvan.repo.MemberTypeRepository;
import com.luanvan.repo.PasswordResetTokenRepository;
import com.luanvan.repo.RoleRepository;
import com.luanvan.repo.StoreRepository;
import com.luanvan.repo.UsersRepository;
import com.luanvan.service.SendGridMailService;
import com.luanvan.service.UsersService;


@Service
public class UsersServiceImpl  implements UsersService{

	
	private UsersRepository usersRepository;
	private RoleRepository roleRepository;
	private CustomerRepository customerRepository; 
	private StoreRepository storeRepository;
	private MemberRepository memberRepository;
	private MemberTypeRepository memberTypeRepository;
	private PasswordResetTokenRepository passwordResetTokenRepository;
	private SendGridMailService sendGridMailService;
	
	@Autowired 
    private PasswordEncoder passwordEncoder;
	
	@Autowired
	public UsersServiceImpl(UsersRepository  usersRepository, 
							RoleRepository roleRepository, 
							CustomerRepository customerRepository,
							StoreRepository storeRepository,
							MemberRepository memberRepository,
							MemberTypeRepository memberTypeRepository,
							PasswordResetTokenRepository passwordResetTokenRepository,
							SendGridMailService sendGridMailService) {
		this.usersRepository = usersRepository;
		this.roleRepository = roleRepository;
		this.customerRepository = customerRepository;
		this.storeRepository = storeRepository;
		this.memberRepository = memberRepository;
		this.memberTypeRepository = memberTypeRepository;
		this.passwordResetTokenRepository = passwordResetTokenRepository;
		this.sendGridMailService =sendGridMailService;
	}

	@Override
	public List<Users> findAllUsers() {
		return usersRepository.findAll();
	}

	@Override
	public void save(Users users) {
		HashSet<Role> roles = new HashSet<>();
		users.setPassword(passwordEncoder.encode(users.getPassword()));
        roles.add(roleRepository.getOne((long)1));
        roles.add(roleRepository.getOne((long)2));
        users.setRoles(roles);
		usersRepository.save(users);
		
	}

	@Override
	public void delete(Long id) {
		usersRepository.deleteById(id);
		
	}

	@Override
	public Optional<Users> findUsersById(Long id) {
		return usersRepository.findById(id);
	}
	
	@Override
	public List<RoleUserDTO> countUserAllRole(){
		return usersRepository.countUserAllRole();
	}

	@Override
	public void multiSave(TestDTO testDTO) {
		Users users = new Users();
		users.setEmail(testDTO.getEmail());
		users.setPassword(testDTO.getPassword());

		users.setRoles(testDTO.getRoleList());
		usersRepository.save(users);
        
		
	}

	@Override
	public UserDTO findByEmail(String email) {
		Users user = usersRepository.findByEmail(email);
		ModelMapper mapper = new ModelMapper();
		UserDTO userDTO = mapper.map(user,UserDTO.class);
		return userDTO;
	}

	@Override
	public Map<String, String> checkEmail(String email) {
		Map<String, String> message = new HashMap<String, String>();
		if(usersRepository.findByEmail(email) != null)
			message.put("message","false");
		else message.put("message","true");
		return message;
	}

	@Override
	@Transactional
	public void saveCustomer(CustomerDTO customerDTO) {
		HashSet<Role> roles = new HashSet<>();
		ModelMapper mapper = new ModelMapper();
		Users user = mapper.map(customerDTO,Users.class);
		
		user.setPassword(passwordEncoder.encode(user.getPassword()));
        roles.add(roleRepository.getOne((long)3));
        user.setRoles(roles);
        
		Users usered =  usersRepository.save(user);
		Customer customer = mapper.map(customerDTO.getCustomers(),Customer.class);
		customer.setUsers(usered);
		customerRepository.save(customer);
        
	}

	@Override
	@Transactional
	public void saveStore(StoreDTO storeDTO, Long id) {
		HashSet<Role> roles = new HashSet<>();
		ModelMapper mapper = new ModelMapper();
		Users user = mapper.map(storeDTO,Users.class);
		
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		roles.add(roleRepository.getOne((long)2));
        roles.add(roleRepository.getOne((long)3));
        user.setRoles(roles);
		Users usered = usersRepository.save(user);
		
		Store store = mapper.map(storeDTO.getStores(),Store.class);
		store.setUsers(usered);
		Store stored = storeRepository.save(store);
		
		Customer customer = new Customer();
		customer.setName(store.getName());
		customer.setPhone(store.getPhone());
		customer.setAddress(store.getAddress()+", "+store.getWard().getName()+", "+store.getDistrict().getName()+ ", " +store.getProvince().getName());
		customer.setUsers(usered);
		customerRepository.save(customer);
		
		Calendar today = Calendar.getInstance();
		Member member = new Member();
		member.setStores(stored);
		member.setStart_time(today.getTime());
		if(id == 4) {
			today.add(Calendar.MONTH, 12);;
			member.setEnd_time(today.getTime());
		}
		else if(id == 3) {
			today.add(Calendar.MONTH, 6);;
			member.setEnd_time(today.getTime());
		}
		else if(id == 2) {
			today.add(Calendar.MONTH, 3);;
			member.setEnd_time(today.getTime());
		}
		else if(id == 1) {
			today.add(Calendar.MONTH, 1);;
			member.setEnd_time(today.getTime());
		}
		
		member.setMembertypes(memberTypeRepository.getOne(id));
		memberRepository.save(member);
		
	}

//	@Override
	public InfoUserDTO info(Authentication auth) {
		Long userid = usersRepository.findByEmail(auth.getName()).getId();

		Store store = storeRepository.findByUsersId(userid);
		Customer customer = customerRepository.findByUsersId(userid);	
	    InfoUserDTO userDTO = new InfoUserDTO();
 
	    userDTO.setEmail(auth.getName());
	    userDTO.setStore(store);
	    userDTO.setCustomer(customer);
	    return userDTO; 
		
	}

	@Override
	public void resetPassword(String email) {
		Users user = usersRepository.findByEmail(email);
		if(user == null) {
			throw new NotFoundException();
		}
		String token = UUID.randomUUID().toString();
		createPasswordResetTokenForUser(user, token);
		StringBuilder  string = new StringBuilder("NoiThat246 Xin chào bạn !!!");
		string.append("<p>Bạn có một yêu cầu thay đổi mật khẩu và mã xác thực: <strong>"+token+ "</strong></p>");
		string.append("<p>Lưu ý: mã xác thực có hiệu lực trong vòng 30 phút</p>");
		string.append("Mọi thắc mắc và góp ý vui lòng liên hệ với NoiThat Care qua email: support@noithat246.vn hoặc số điện thoại 0941 426 824 (1000đ/phút , 8-21h kể cả T7, CN).");
		string.append("<p>Trân trọng</p><p>NoiThat246</p>");
		sendGridMailService.sendHTML(user.getEmail(), "Mã xác thực Nội Thất 246", string.toString());
		
	}
	public void createPasswordResetTokenForUser(Users user, String token) {
		PasswordResetToken myToken = new PasswordResetToken();
		myToken.setToken(token);
		myToken.setUser(user);
		myToken.setExpiryDate(30);
	    passwordResetTokenRepository.save(myToken);
	}

	@Override
	public void savePassword(ResetPasswordDTO passwordDTO) {
		Users user = usersRepository.findByEmail(passwordDTO.getEmail());
		user.setPassword(passwordEncoder.encode(passwordDTO.getPassword()));
		usersRepository.save(user);
		
	}
	
	
}

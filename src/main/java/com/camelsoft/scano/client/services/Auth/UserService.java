package com.camelsoft.scano.client.services.Auth;

import com.camelsoft.scano.client.Interfaces.CusomUserResponse;
import com.camelsoft.scano.client.Repository.Auth.RoleRepository;
import com.camelsoft.scano.client.Repository.Auth.UserRepository;
import com.camelsoft.scano.client.Response.DynamicResponse;
import com.camelsoft.scano.client.models.Auth.Role;
import com.camelsoft.scano.client.models.Auth.users;
import com.camelsoft.scano.tools.Enum.Auth.RoleEnum;
import com.camelsoft.scano.tools.exception.NotFoundException;
import com.camelsoft.scano.tools.util.BaseController;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;



@Service
public class  UserService extends BaseController implements UserDetailsService {
    private final Log logger = LogFactory.getLog(UserService.class);
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public void update_password(users user) {
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            this.userRepository.save(user);
        } catch (NoSuchElementException ex) {
            throw new NotFoundException("not found data");
        }

    }





    public users saveUser(users user) {
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setActive(true);
            Role userRole = roleRepository.findByRole("ROLE_USER");
            user.setRole(userRole);
            return userRepository.save(user);
        } catch (NoSuchElementException ex) {
            throw new NotFoundException("not found data");
        }

    }

    public users saveAdmins(users user) {
        try {
            if (this.userRepository.findByRole(this.roleRepository.findByRole(RoleEnum.ROLE_ADMIN.name()))!=null)
                throw new NotFoundException("couch already found");
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setActive(true);
            Role userRole = roleRepository.findByRole("ADMIN");
            user.setRole(userRole);
            return userRepository.save(user);
        } catch (NoSuchElementException ex) {
            throw new NotFoundException("not found data");
        }

    }

    public users UpdateUser(users users) {

        try {
            return userRepository.save(users);
        } catch (NoSuchElementException ex) {
            throw new NotFoundException("not found data");
        }

    }

    public users saveAdmin(users users) {

        try {
            users.setPassword(passwordEncoder.encode(users.getPassword()));
            users.setActive(true);
            Role userRole = roleRepository.findByRole("ROLE_ADMIN");
            users.setRole(userRole);
            return userRepository.save(users);
        } catch (NoSuchElementException ex) {
            throw new NotFoundException("not found data");
        }

    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        users users = userRepository.findByEmail(username);
        if (users == null) {
            users = userRepository.findByUsername(username);
        }
        if (users != null) {
            List<GrantedAuthority> authorities = getUserAuthority(new HashSet<Role>(Arrays.asList(users.getRole())));
            return buildUserForAuthentication(users, authorities);
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }

    private List<GrantedAuthority> getUserAuthority(Set<Role> userRoles) {
        Set<GrantedAuthority> roles = new HashSet<GrantedAuthority>();
        for (Role role : userRoles) {
            roles.add(new SimpleGrantedAuthority(role.getRole()));
        }
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>(roles);
        return grantedAuthorities;
    }

    private UserDetails buildUserForAuthentication(users users, List<GrantedAuthority> authorities) {
        return new org.springframework.security.core.userdetails.User(users.getUsername(), users.getPassword(),
                users.getActive(), true, true, true, authorities);
    }

    public String GenerateUserName(String name, Long iduser) {
        String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder(4);
        for (int i = 0; i < 4; i++)
            sb.append(chars.charAt(rnd.nextInt(chars.length())));
        String username = trimAllWhitespace(name).toUpperCase(Locale.ROOT) + iduser + sb;
        return username;

    }

    public static String trimAllWhitespace(String str) {
        if (!hasLength(str)) {
            return str;
        }

        int len = str.length();
        StringBuilder sb = new StringBuilder(str.length());
        for (int i = 0; i < len; i++) {
            char c = str.charAt(i);
            if (!Character.isWhitespace(c)) {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    public static boolean hasLength(String str) {
        return (str != null && !str.isEmpty());
    }

    public users findByUserName(String username) {
        try {
            users user = this.userRepository.findByUsername(username);
            if (user == null)
                user = this.userRepository.findByEmail(username);
            return user;
        } catch (NoSuchElementException ex) {
            throw new NotFoundException(String.format("No user found with username [%s] in our data base", username));
        }

    }

    public users findbyemail(String email) {
        try {
            return userRepository.findByEmail(email);
        } catch (NoSuchElementException ex) {
            throw new NotFoundException(String.format("No data found"));
        }

    }



    public List<users> findAll()
    {
        try {
            return  this.userRepository.findAll();
        } catch (NoSuchElementException ex) {
            throw new NotFoundException(String.format("No data found"));
        }
    }

    public List<users> findAllbycompanie(Long id)
    {
        try {
            return  this.userRepository.findAllByCompanies_Id(id);
        } catch (NoSuchElementException ex) {
            throw new NotFoundException(String.format("No data found"));
        }
    }

    public users findTop()
    {
        try {
            return  this.userRepository.findTopByOrderByIdDesc();
        } catch (NoSuchElementException ex) {
            throw new NotFoundException(String.format("No data found"));
        }
    }

    public boolean existbyemail(String email) {
        try {
            return userRepository.existsByEmail(email);
        } catch (NoSuchElementException ex) {
            throw new NotFoundException(String.format("No data found"));
        }

    }

    public boolean existbyid(Long id) {
        try {
            return userRepository.existsById(id);
        } catch (NoSuchElementException ex) {
            throw new NotFoundException(String.format("No data found"));
        }

    }

    public users findById(Long userid) {
        try {
            return userRepository.findById(userid).get();

        } catch (NoSuchElementException ex) {
            throw new NotFoundException(String.format("No data found"));
        }
    }
    public CusomUserResponse findByUser_UId(String userid) {
        try {
            logger.info("inside"+userid);
            if (!this.userRepository.existsByCard_Uid(userid))
                throw new NotFoundException(String.format("No user found with card uid: "+ userid ));

            return userRepository.findByCard_Uid(userid);

        } catch (NoSuchElementException ex) {
            throw new NotFoundException(String.format("No data found"));
        }
    }

    public void updatepassword(users user, String password) {
        user.setPassword(passwordEncoder.encode(password));
        this.userRepository.save(user);
    }




    public DynamicResponse findAllUsers(int page, int size) {
        try {
            Role userRole = roleRepository.findByRole("ROLE_USER");
            Page<users> user = this.userRepository.findAllByRole(PageRequest.of(page, size),userRole);

            return new DynamicResponse(user.getContent(),user.getNumber(),user.getTotalElements(),user.getTotalPages());
        } catch (NoSuchElementException ex) {
            throw new NotFoundException(String.format("No data found"));
        }

    }

    public Long countAllUsers() {
        try {
            Role userRole = roleRepository.findByRole("ROLE_USER");
            Long user = this.userRepository.countAllByRole(userRole);

            return  user;
        } catch (NoSuchElementException ex) {
            throw new NotFoundException(String.format("No data found"));
        }

    }

    public Long AllUsersSales() {
        try {
            Role userRole = roleRepository.findByRole("ROLE_USER");
            Long user = this.userRepository.countAllByRole(userRole);

            return  user;
        } catch (NoSuchElementException ex) {
            throw new NotFoundException(String.format("No data found"));
        }

    }

    public List<users> FindAllAdmin(){
        List<users> users = this.userRepository.findAllByRole(this.roleRepository.findByRole(RoleEnum.ROLE_ADMIN.name()));
        return  users;
    }



}

package pers.ljy.background.jwt;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

/***
 * 创建一个工厂类   负责创建JWTUserDetails 对象
 * @author ljy
 *
 */
public final class JWTUserDetailsFactory {

	private JWTUserDetailsFactory(){
		
	}
	
	public static JWTUserDetails create(User user,Long userId,Date date){
		return new JWTUserDetails(userId, user.getUsername(), user.getPassword(),user.getAuthorities(), date);
	}
	
	private static List<GrantedAuthority> mapToGrantedAuthorities(List<String> authorities) {
        return authorities.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

}

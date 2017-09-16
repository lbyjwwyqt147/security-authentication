package pers.ljy.background.jwt;

import java.util.Collection;
import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/***
 * JWT保存的用户信息
 * 
 * 
 * Spring Security需要我们实现几个东西，第一个是UserDetails：这个接口中规定了用户的几个必须要有的方法，所以我们创建一个JwtUser类来实现这个接口。为什么不直接使用User类？因为这个UserDetails完全是为了安全服务的，它和我们的领域类可能有部分属性重叠，但很多的接口其实是安全定制的，所以最好新建一个类：
 * 
 * @author ljy
 *
 */
public class JWTUserDetails implements UserDetails {

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long userId;         //用户ID
    private String password;       //用户密码
    private final String username; //用户名
    private final Collection<? extends GrantedAuthority> authorities;  //用户角色权限
    private final boolean isAccountNonExpired;       //账号是否过期
    private final boolean isAccountNonLocked;        //账户是否锁定
    private final boolean isCredentialsNonExpired;   //密码是否过期
    private final boolean enabled;                   //是否激活
    private final Date lastPasswordResetDate;        //上次密码重置时间

    
    public JWTUserDetails(Long userId, String username, String password, Collection<? extends GrantedAuthority> authorities,Date lastPasswordResetDate) {
        this(userId, username, password, true, true, true, true, authorities,lastPasswordResetDate);
    }

    public JWTUserDetails(Long userId, String username, String password, boolean enabled, boolean isAccountNonExpired, boolean isCredentialsNonExpired, boolean isAccountNonLocked, Collection<? extends GrantedAuthority> authorities,Date lastPasswordResetDate) {
        if (username != null && !"".equals(username) && password != null) {
            this.userId = userId;
            this.username = username;
            this.password = password;
            this.enabled = enabled;
            this.isAccountNonExpired = isAccountNonExpired;
            this.isAccountNonLocked = isAccountNonLocked;
            this.isCredentialsNonExpired = isCredentialsNonExpired;
            this.authorities = authorities;
            this.lastPasswordResetDate = lastPasswordResetDate;
        } else {
            throw new IllegalArgumentException("Cannot pass null or empty values to constructor");
        }
    }

	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

    @JsonIgnore
	@Override
	public String getPassword() {
		return password;
	}

    
	@Override
	public String getUsername() {
		return username;
	}

    @JsonIgnore
	@Override
	public boolean isAccountNonExpired() {
		return isAccountNonExpired;
	}

    @JsonIgnore
	@Override
	public boolean isAccountNonLocked() {
		return isAccountNonLocked;
	}

    @JsonIgnore
	@Override
	public boolean isCredentialsNonExpired() {
		return isCredentialsNonExpired;
	}

    @JsonIgnore
	@Override
	public boolean isEnabled() {
		return enabled;
	}

    @JsonIgnore
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@JsonIgnore
	public Date getLastPasswordResetDate() {
		return lastPasswordResetDate;
	}
	
	

}

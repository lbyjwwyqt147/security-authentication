package pers.ljy.background.oauth;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.infinispan.security.AuthorizationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/***
 * 认证授权服务端配置
 * @author ljy
 * 
 * 实现所有父类方法
 *
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

	private String resourceId = "apple";
	
	// access_token 的超时时间   默认12个小时
    private	int accessTokenValiditySeconds = 10;
    
    //refresh_token 的超时时间  默认2592000秒
    private int refreshTokenValiditySeconds = 2592000;     

   //是否支持access_token 刷新，默认是false,在配置文件中以配置成可以支持了，
    private boolean supportRefreshToken = false;            
   
   //使用refresh_token刷新之后该refresh_token是否依然使用，默认是依然使用
    private boolean reuseRefreshToken = true;               
    
                      
    
	
    @Autowired
	private AuthenticationManager authenticationManager;


	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		//配置 认证器
		endpoints.authenticationManager(this.authenticationManager);
		// 配置令牌转换
		endpoints.accessTokenConverter(accessTokenConverter());
		// 配置 token 存储方式
		endpoints.tokenStore(tokenStore());
	}


	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		//
		security.tokenKeyAccess("isAnonymous() || hasAuthority('ROLE_TRUSTED_CLIENT')");
		//
		security.checkTokenAccess("hasAuthority('ROLE_TRUSTED_CLIENT')");
		
	}


	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
	     clients.inMemory().withClient("normal-app").authorizedGrantTypes("authorization_code", "implicit")
	             .authorities("ROLE_CLIENT").scopes("read", "write").resourceIds(resourceId)
	             .accessTokenValiditySeconds(accessTokenValiditySeconds).and().withClient("trusted-app")
	             .authorizedGrantTypes("client_credentials", "password").authorities("ROLE_TRUSTED_CLIENT")
	             .scopes("read", "write").resourceIds(resourceId).accessTokenValiditySeconds(accessTokenValiditySeconds)
	             .secret("secret");
	}
    
	
	/**
	 * 注册 token 令牌转换
	 * @return
	 */
	@Bean
	public JwtAccessTokenConverter accessTokenConverter(){
		JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter(){

			/**
			 * 重写增强token 方法,用于自定义一些token返回的信息
			 */
			@Override
			public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
				//获取当前登录名
				String loginUserName = authentication.getUserAuthentication().getName();
		        //获取登录人信息(与登录时候放进去的UserDetail实现类一致 详见 类：MyUserDetailService)
				User user = (User) authentication.getUserAuthentication().getPrincipal();
				/** 自定义一些token 属性 */
				final ConcurrentMap<String, Object> additionalInformation = new ConcurrentHashMap<>();
				//用户
				additionalInformation.put("userName",loginUserName);
				//用户 拥有的角色
				additionalInformation.put("roles", user.getAuthorities());
				((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInformation);
				OAuth2AccessToken enhancedToken = super.enhance(accessToken, authentication);
				return enhancedToken;
			}
		};
		accessTokenConverter.setSigningKey("123");// 测试用,资源服务使用相同的字符达到一个对称加密的效果,生产时候使用RSA非对称加密方式
		return accessTokenConverter;
	}
    
	/**
	 *  注册 token  令牌存储
	 * @return
	 */
	@Bean
	public TokenStore tokenStore() {
		TokenStore tokenStore = new JwtTokenStore(accessTokenConverter());
		return tokenStore;
	}
}

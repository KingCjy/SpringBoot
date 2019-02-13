package com.kingcjy.main.resolver;

import com.kingcjy.main.annotation.SocialUser;
import com.kingcjy.main.domain.enums.SocialType;
import com.kingcjy.main.entity.UserEntity;
import com.kingcjy.main.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

import static com.kingcjy.main.domain.enums.SocialType.*;

@Component
public class UserArgumentResolver implements HandlerMethodArgumentResolver {

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {

        return methodParameter.getParameterAnnotation(SocialUser.class) != null
                && methodParameter.getParameterType().equals(UserEntity.class);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        HttpSession session = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getSession();

        UserEntity userEntity = (UserEntity) session.getAttribute("user");

        return getUser(userEntity, session);
    }

    private UserEntity getUser(UserEntity userEntity, HttpSession session) {
        if(userEntity == null) {
            try {
                OAuth2Authentication oAuth2Authentication= (OAuth2Authentication) SecurityContextHolder.getContext().getAuthentication();
                Map<String, String> map = (Map<String, String>) oAuth2Authentication.getUserAuthentication().getDetails();
                UserEntity convertUser = convertUser(String.valueOf(oAuth2Authentication.getAuthorities().toArray()[0]), map);


                userEntity = userRepository.findByEmail(convertUser.getEmail());

                if(userEntity == null) {
                    userRepository.save(convertUser);
                }

                setRoleIfNotSame(userEntity, oAuth2Authentication, map);
                session.setAttribute("user", userEntity);
            } catch(ClassCastException e) {
                return userEntity;
            }
        }
        return userEntity;
    }

    private UserEntity convertUser(String authority, Map<String, String> map) {
        if(FACEBOOK.equals(authority)) return getModernUser(FACEBOOK, map);
        if(GOOGLE.equals(authority)) return getModernUser(GOOGLE, map);
        if(KAKAO.equals(authority)) return getKaKaoUser(map);
        if(NAVER.equals(authority)) return getModernUser(NAVER, map);
        return null;
    }

    private UserEntity getModernUser(SocialType socialType, Map<String, String> map) {
        return UserEntity.builder()
                .name(map.get("name"))
                .email(map.get("email"))
                .principal(map.get("id"))
                .socialType(socialType)
                .build();
    }

    private UserEntity getKaKaoUser(Map<String, String> map) {
        HashMap<String, String> propertyMap = (HashMap<String, String>) (Object) map.get("properties");
        return UserEntity.builder()
                .name(propertyMap.get("nickname"))
                .email(map.get("kaccount_email"))
                .principal(String.valueOf(map.get("id")))
                .socialType(KAKAO)
                .build();
    }

    private void setRoleIfNotSame(UserEntity userEntity, OAuth2Authentication oAuth2Authentication, Map<String, String> map) {
        if(!oAuth2Authentication.getAuthorities().contains(new SimpleGrantedAuthority(userEntity.getSocialType().getRoleType()))) {
            SecurityContextHolder.getContext().setAuthentication(
                    new UsernamePasswordAuthenticationToken(
                            map,
                            "N/A",
                            AuthorityUtils.createAuthorityList(userEntity.getSocialType().getRoleType())
                    )
            );
        }
    }

}

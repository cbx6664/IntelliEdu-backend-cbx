package me.intelliedu.intellieduuserservice.config;

import me.intelliedu.intellieduuserservice.constant.Constant;
import me.intelliedu.intelliedumodel.vo.UserVo;
import me.intelliedu.intellieduuserservice.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AdminInterceptor implements HandlerInterceptor {

    @Resource
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            RequiresAdmin annotation = handlerMethod.getMethodAnnotation(RequiresAdmin.class);
            if (annotation != null) {
                UserVo user = (UserVo) request.getSession().getAttribute(Constant.LOGIN_USER);
                if (user == null) {
                    response.sendRedirect("/login");
                    return false;
                }
                UserVo userVo = userService.getMyInfo(request);
                if (!userVo.getRole().equals("admin")) {
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                    response.getWriter().write("{\"error\": \"Forbidden\"}");
                    return false;
                }
                return true;
            }
        }
        return true;
    }
}


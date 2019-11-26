package com.kgc.controller;

import com.kgc.domain.SysLog;
import com.kgc.service.ISysLogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Date;

@Component
@Aspect  //标识切面类
public class LogAop {
    @Autowired
    private ISysLogService sysLogService;
    @Autowired
    private HttpServletRequest request;

    private Date visitTime;//开始时间
    private Class clazz;//访问的哪个类
    private Method method;//访问的方法
    //前置通知  主要获取开始时间  执行的类是哪一个  执行的方法是哪一个
    @Before("execution(* com.kgc.controller.*.*(..))")
    public void doBefore(JoinPoint jp) throws NoSuchMethodException {
        visitTime = new Date();//当前时间就是开始访问的时间
        clazz = jp.getTarget().getClass();//具体访问的类
        String methodName = jp.getSignature().getName();//拿到访问的方法名称
        Object[] args = jp.getArgs();//获取访问方法的参数
        //获取具体执行的method方法对象
        if(args == null||args.length==0){
            method = clazz.getMethod(methodName);//只能获取无参方法
        }else {
            Class[] classArgs = new Class[args.length];
            for (int i = 0; i < args.length; i++) {
                classArgs[i] = args[i].getClass();
            }
            method = clazz.getMethod(methodName,classArgs);//获取有参方法
        }
    }

    //后置通知
    @After("execution(* com.kgc.controller.*.*(..))")
    public void doAfter(JoinPoint jp) throws Exception {
        long time = new Date().getTime() - visitTime.getTime();//获取访问的时长
        String url = "";
        //获取url
        if(clazz!=null&&method!=null&&clazz!=LogAop.class){
            //获取类上的@RequestMapping注解("/user")
            RequestMapping classAnnotation = (RequestMapping) clazz.getAnnotation(RequestMapping.class);
            if(classAnnotation!=null){
                String[] classValue = classAnnotation.value();
                //获取方法上的@RequestMapping
                RequestMapping methodAnnotation = method.getAnnotation(RequestMapping.class);
                if(methodAnnotation!=null){
                    String[] methodValue = methodAnnotation.value();
                    url=classValue[0]+methodValue[0];//user/findAll.do
                    //获取访问的ip
                    String ip = request.getRemoteAddr();

                    //获取当前操作的用户
                    SecurityContext context = SecurityContextHolder.getContext();//从上下文获取当前登录的用户
                    User user = (User) context.getAuthentication().getPrincipal();
                    String username = user.getUsername();

                    //将日志相关信息封装到SysLog对象中
                    SysLog sysLog = new SysLog();
                    sysLog.setExecutionTime(time);
                    sysLog.setIp(ip);
                    sysLog.setMethod("[类名]"+clazz.getName()+"[方法名]"+method.getName());
                    sysLog.setUrl(url);
                    sysLog.setUsername(username);
                    sysLog.setVisitTime(visitTime);
                    //调用service去完成日志的保存
                    sysLogService.save(sysLog);
                }
            }
        }

    }
}

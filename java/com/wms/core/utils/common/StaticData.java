package com.wms.core.utils.common;

import com.wms.domain.MenuRepo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

/**
 * 所有的静态数据
 * <p>
 * xb
 *
 * @date：2010-5-20 上午09:46:11
 */
public final class StaticData {
    /**
     * 反斜杠字符
     */
    public static final String BACKSLASH_CHARACTER = "/";

    /**
     * 半角逗号
     */
    public static final String COMMA = ",";

    /**
     * 下滑杠
     */
    public static final String DOWN_BARS = "_";

    /**
     * 小数点
     */
    public static final String RADIX_POINT = ".";

    /**
     * 空字符串
     */
    public static final String EMPTY_STRING = "";

    /**
     * 数字0
     */
    public static final int ZERO = 0;

    /**
     * 数字1
     */
    public static final int FIRST = 1;

    public static final String DEFAULT_PAGE_SIZE = "1";

    public static final String FIRST_PAGE_NUM = "10";

    public static final  boolean LOG_ENABLED = ResourceBundleUtil.getBoolean("application",
            "log.enabled");

    public static boolean CAN_MENU_SUBMIT = true;

    public static Map<String, String> PERSONAL_PREV = new HashMap<>();

    public static final Set<HashMap<String, String>> OPERATOR_DATAS = new HashSet<>() {
        {
            var classes = ClassUtil.getClassesFromPackage("com.wms.ui.controller");
            for (var clazz : classes) {
                var methods = clazz.getMethods();
                if (ObjectUtils.isNotEmpty(clazz.getAnnotation(RequestMapping.class))) {
                    var clazzName = clazz.getAnnotation(RequestMapping.class).name();
                    if (ObjectUtils.isNotEmpty(clazzName)) {
                        var clazzPath = clazz.getAnnotation(RequestMapping.class).value()[0];
                        if (clazzPath.indexOf("/") == 0) {
                            clazzPath = clazzPath.substring(1, clazzPath.length());
                        }
                        for (var method : methods) {
                            var get = method.getAnnotation(GetMapping.class);
                            var post = method.getAnnotation(PostMapping.class);
                            var request = method.getAnnotation(RequestMapping.class);
                            if (ObjectUtils.isNotEmpty(get)) {
                                var methodPath = get.value()[0];
                                var methodName = get.name();
                                if (methodPath.indexOf("/") == 0) {
                                    methodPath = methodPath.substring(1, methodPath.length());
                                }
                                if (ObjectUtils.isNotEmpty(methodName)) {
                                    HashMap<String,String> tmpMap = new HashMap<>();
                                    tmpMap.put("methodName",methodName);
                                    tmpMap.put("path", "/" + clazzPath + "/" + methodPath);
                                    tmpMap.put("menuName",clazzName);
                                    var menu = new MenuRepo().findByName(clazzName).block();
                                    if(menu.nonEmpty()){
                                        tmpMap.put("menuUid",menu.get().uid());
                                    }
                                    add(tmpMap);
                                }
                            } else if (ObjectUtils.isNotEmpty(post)) {
                                var methodPath = post.value()[0];
                                var methodName = post.name();
                                if (methodPath.indexOf("/") == 0) {
                                    methodPath = methodPath.substring(1, methodPath.length());
                                }
                                if (ObjectUtils.isNotEmpty(methodName)) {
                                    HashMap<String,String> tmpMap = new HashMap<>();
                                    tmpMap.put("methodName",methodName);
                                    tmpMap.put("path", "/" + clazzPath + "/" + methodPath);
                                    tmpMap.put("menuName",clazzName);
                                    var menu = new MenuRepo().findByName(clazzName).block();
                                    if(menu.nonEmpty()){
                                        tmpMap.put("menuUid",menu.get().uid());
                                    }
                                    add(tmpMap);
                                }
                            } else if (ObjectUtils.isNotEmpty(request)) {
                                var methodPath = request.value()[0];
                                var methodName = request.name();
                                if (methodPath.indexOf("/") == 0) {
                                    methodPath = methodPath.substring(1, methodPath.length());
                                }
                                if (ObjectUtils.isNotEmpty(methodName)) {
                                    HashMap<String,String> tmpMap = new HashMap<>();
                                    tmpMap.put("methodName",methodName);
                                    tmpMap.put("path", "/" + clazzPath + "/" + methodPath);
                                    tmpMap.put("menuName",clazzName);
                                    var menu = new MenuRepo().findByName(clazzName).block();
                                    if(menu.nonEmpty()){
                                        tmpMap.put("menuUid",menu.get().uid());
                                    }
                                    add(tmpMap);
                                }
                            }
                        }
                    }
                }
            }
        }
    };

    public static final Set<String> DEFAULT_OPERATOR_URLS = new HashSet(){
        {
            var classes = ClassUtil.getClassesFromPackage("com.wms.ui.controller");
            for (var clazz : classes) {
                var methods = clazz.getMethods();
                if (ObjectUtils.isNotEmpty(clazz.getAnnotation(RequestMapping.class))) {
                    var clazzName = clazz.getAnnotation(RequestMapping.class).name();
                    var clazzPath = clazz.getAnnotation(RequestMapping.class).value()[0];
                    if (clazzPath.indexOf("/") == 0) {
                        clazzPath = clazzPath.substring(1, clazzPath.length());
                    }
                    for (var method : methods) {
                        var get = method.getAnnotation(GetMapping.class);
                        var post = method.getAnnotation(PostMapping.class);
                        var request = method.getAnnotation(RequestMapping.class);
                        if (ObjectUtils.isNotEmpty(get)) {
                            var methodPath = get.value()[0];
                            var methodName = get.name();
                            if (methodPath.indexOf("/") == 0) {
                                methodPath = methodPath.substring(1, methodPath.length());
                            }
                            if (ObjectUtils.isEmpty(clazzName)||ObjectUtils.isEmpty(methodName)) {
                                add("/" + clazzPath + "/" + methodPath);
                            }
                        } else if (ObjectUtils.isNotEmpty(post)) {
                            var methodPath = post.value()[0];
                            var methodName = post.name();
                            if (methodPath.indexOf("/") == 0) {
                                methodPath = methodPath.substring(1, methodPath.length());
                            }
                            if (ObjectUtils.isEmpty(clazzName)||ObjectUtils.isEmpty(methodName)) {
                                add("/" + clazzPath + "/" + methodPath);
                            }
                        } else if (ObjectUtils.isNotEmpty(request)) {
                            var methodPath = request.value()[0];
                            var methodName = request.name();
                            if (methodPath.indexOf("/") == 0) {
                                methodPath = methodPath.substring(1, methodPath.length());
                            }
                            if (ObjectUtils.isEmpty(clazzName)||ObjectUtils.isEmpty(methodName)) {
                                add("/" + clazzPath + "/" + methodPath);
                            }
                        }
                    }
                }
            }
        }
    };

}
package com.wms.core.utils.operator;

import com.wms.core.utils.common.ObjectUtils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static com.wms.core.utils.common.StaticData.OPERATOR_DATAS;
import static com.wms.core.utils.common.StaticData.DEFAULT_OPERATOR_URLS;

public class OperatorUtil {
    public static Set<HashMap<String,String>> findOperatorDatasByMenuName(String menuName){
        var result = new HashSet<HashMap<String,String>>();
        var datas =OPERATOR_DATAS;
        for(var data : datas){
            if(data.get("menuName").equals(menuName)){
                result.add(data);
            }
        }
        return result;
    }

    public static Map<String,String>findOperatorDataByMenuNameAndMethodName(String menuName,String methodName){
        var datas =OPERATOR_DATAS;
        for(var data : datas){
            if(data.get("menuName").equals(menuName)&&data.get("methodName").equals(methodName)){
                return data;
            }
        }
        return null;
    }

    public static HashMap<String,String>findOperatorDataByMenuUidAndMethodName(String menuUid,String methodName){
        var datas =OPERATOR_DATAS;
        for(var data : datas){
            if(ObjectUtils.isNotEmpty(data)&&ObjectUtils.isNotEmpty(data.get("menuUid"))) {
                if (data.get("menuUid").equals(menuUid) && data.get("methodName").equals(methodName)) {
                    return data;
                }
            }
        }
        return null;
    }

    public static Set<String> findDefaultUrls(){
        return DEFAULT_OPERATOR_URLS;
    }

    public static Set<HashMap<String,String>> findOperatorDatas(){
        return OPERATOR_DATAS;
    }
}

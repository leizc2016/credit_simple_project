package com.pactera.pds.u2.commerce.service.car;

import java.sql.Timestamp;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.pactera.pds.u2.commerce.repository.mybatis.CreditSearchMybatisDao;
import com.pactera.pds.u2.commerce.utils.Sessions;

@Component
@Transactional
public class CreditSearchCacheService {
    
    @Autowired
    private CreditSearchMybatisDao creditSearch;
    
    public static final Long _24H = 24 * 60 * 60 * 1000L;
//    public static final Long _24H =  0L;
    
    public void save24HCache(String idCardNum, String queryType,CacheResultWapper cache){
        creditSearch.save24hCache(Sessions.insCode(), idCardNum, queryType, cache.serializeEncoding());
    }
    
    public void update24Hcache(String idCardNum, String queryType,CacheResultWapper cache){
        creditSearch.update24hCache(Sessions.insCode(), idCardNum, queryType, cache.serializeEncoding());
    }
    
    public void safeSave24HCache(String idCardNum, String queryType,CacheResultWapper cache, String insCode){
//        String insCode=Sessions.insCode();
        String objStr=cache.serializeEncoding();
        creditSearch.delete24hCache(insCode, idCardNum, queryType);
        creditSearch.save24hCache(insCode,idCardNum, queryType, objStr);
        //查询日志
        creditSearch.save24hCacheLog(insCode, idCardNum, queryType, objStr);
    }
    
    //查询24小时缓存
    public CacheResultWapper get24hCahce(String insCode, String idCardNum, String queryType, long now) {
        Map<String, Object> cache = creditSearch.getIns24hCahceStr(insCode, idCardNum, queryType);
        if (null != cache && cache.size() > 0) {
            Timestamp cacheTime = (Timestamp) cache.get("cacheTime");
            if (null != cacheTime) {
                if ((now - cacheTime.getTime()) <=_24H //_24H
                		) {
                    return CacheResultWapper.deserializeEncoding((String) cache.get("cacheObj"));
                }else{
                    //缓存超过24小时，清除缓存
                    creditSearch.delete24hCache(insCode, idCardNum, queryType);
                }
            }
        }
        
        return null;
    }
    
    public CacheResultWapper get24hCahce(String idCardNum, String queryType, String insCode) {
        return get24hCahce(insCode, idCardNum, queryType, System.currentTimeMillis());
    }

    public CreditSearchMybatisDao getCreditSearch() {
        return creditSearch;
    }
    
    public void setCreditSearch(CreditSearchMybatisDao creditSearch) {
        this.creditSearch = creditSearch;
    }
}

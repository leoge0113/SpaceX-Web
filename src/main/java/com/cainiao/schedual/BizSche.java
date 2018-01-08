package com.cainiao.schedual;

//import com.cainiao.cache.RedisCache;
import com.cainiao.cache.RedisClusterCache;
import com.cainiao.cache.RedisClusterCache;
import com.cainiao.dao.UserDao;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

//import java.util.logging.Logger;

@Component
public class BizSche {
    private final org.slf4j.Logger LOG = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private UserDao userDao;
    @Autowired
    //private RedisCache redisCache;
    private RedisClusterCache redisCache;
    @Scheduled(cron = "0 0/1 9-17 * * ? ")
    public void addUserScore() {
        LOG.info("@Scheduled--------addUserScore()");
        userDao.addScore(10);
    }
    /**
     * 每隔5分钟定时清理缓存
     */
    @Scheduled(cron = "0 0/5 * * * ? ")
    public void cacheClear() {
        LOG.info("@Scheduled-------cacheClear()");
        redisCache.clearCache();
    }
}

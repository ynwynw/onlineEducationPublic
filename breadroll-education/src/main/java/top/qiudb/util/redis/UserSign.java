package top.qiudb.util.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
/**
 * @author Qiu
 * @email qiudb.top@aliyun.com
 * @date 2021/5/5 20:19
 * @description 用户签到
 */


/**
 * 基于Redis位图的用户签到功能实现类
 * 实现功能：
 * 1. 用户签到
 * 2. 检查用户是否签到
 * 3. 获取当月签到次数
 * 4. 获取当月连续签到次数
 * 5. 获取当月首次签到日期
 * 6. 获取当月签到情况
 */

@Component
@SuppressWarnings("all")
public class UserSign {
    @Autowired
    RedisUtil redisUtil;

    /**
     * 用户签到
     *
     * @param uid  用户ID
     * @param date 日期
     * @return 之前的签到状态
     */
    public boolean doSign(int uid, LocalDate date) {
        int offset = date.getDayOfMonth() - 1;
        return redisUtil.setBit(buildSignKey(uid, date), offset, true);
    }

    /**
     * 检查用户是否签到
     *
     * @param uid  用户ID
     * @param date 日期
     * @return 当前的签到状态
     */
    public boolean checkSign(int uid, LocalDate date) {
        int offset = date.getDayOfMonth() - 1;
        return redisUtil.getBit(buildSignKey(uid, date), offset);
    }

    /**
     * 获取用户签到次数
     *
     * @param uid  用户ID
     * @param date 日期
     * @return 当前的签到次数
     */
    public long getSignCount(int uid, LocalDate date) {
        return redisUtil.bitCount(buildSignKey(uid, date));
    }

    /**
     * 获取当月连续签到次数
     *
     * @param uid  用户ID
     * @param date 日期
     * @return 当月连续签到次数
     */
    public long getContinuousSignCount(int uid, LocalDate date) {
        int signCount = 0;
        String type = String.format("u%d", date.getDayOfMonth());
        List<Long> list = redisUtil.bitField(buildSignKey(uid, date),date.getDayOfMonth(),0);
        if (list != null && list.size() > 0) {
            // 取低位连续不为0的个数即为连续签到次数，需考虑当天尚未签到的情况
            long v = list.get(0) == null ? 0 : list.get(0);
            for (int i = 0; i < date.getDayOfMonth(); i++) {
                if (v >> 1 << 1 == v) {
                    // 低位为0且非当天说明连续签到中断了
                    if (i > 0) break;
                } else {
                    signCount += 1;
                }
                v >>= 1;
            }
        }
        return signCount;
    }

    /**
     * 获取当月首次签到日期
     *
     * @param uid  用户ID
     * @param date 日期
     * @return 首次签到日期
     */
    public LocalDate getFirstSignDate(int uid, LocalDate date) {
        long pos = redisUtil.bitPos(buildSignKey(uid, date), true);
        return pos < 0 ? null : date.withDayOfMonth((int) (pos + 1));
    }

    /**
     * 获取当月签到情况
     *
     * @param uid  用户ID
     * @param date 日期
     * @return Key为签到日期，Value为签到状态的Map
     */
    public Map<String, Boolean> getSignInfo(int uid, LocalDate date) {
        Map<String, Boolean> signMap = new HashMap<>(date.getDayOfMonth());
        String type = String.format("u%d", date.lengthOfMonth());
        List<Long> list = redisUtil.bitField(buildSignKey(uid, date), date.getDayOfMonth(), 0);
        if (list != null && list.size() > 0) {
            // 由低位到高位，为0表示未签，为1表示已签
            long v = list.get(0) == null ? 0 : list.get(0);
            //定义栈，先进后出
            Stack<Boolean> stack = new Stack<>();
            while(v!=0){
                stack.push(v >> 1 << 1 != v);
                v >>= 1;
            }
            for (int i = date.lengthOfMonth(); i > 0; i--) {
                LocalDate d = date.withDayOfMonth(i);
                signMap.put(formatDate(d, "yyyy-MM-dd"), false);
            }
            long pos = redisUtil.bitPos(buildSignKey(uid, date), true);
            int index = (int)pos;
            while (!stack.empty()) {
                signMap.put(formatDate(date.withDayOfMonth(++index), "yyyy-MM-dd"), stack.pop());
            }
        }
        return signMap;
    }

    private static String formatDate(LocalDate date) {
        return formatDate(date, "yyyyMM");
    }

    private static String formatDate(LocalDate date, String pattern) {
        return date.format(DateTimeFormatter.ofPattern(pattern));
    }

    private static String buildSignKey(int uid, LocalDate date) {
        return String.format("u:sign:%d:%s", uid, formatDate(date));
    }
}

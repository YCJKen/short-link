package com.nageoffer.shortlink.project.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nageoffer.shortlink.project.dao.entity.LinkAccessLogsDO;
import com.nageoffer.shortlink.project.dto.req.ShortLinkStatsReqDTO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.HashMap;
import java.util.List;

/**
 * 访问日志监控持久层
 */
public interface LinkAccessLogsMapper extends BaseMapper<LinkAccessLogsDO> {

    /**
     * 根据短链接获取指定日期内高频访问IP数据， 根据hashmap自动映射返回结果成键值对
     */
    @Select("SELECT " +
            "    ip, " +
            "    COUNT(ip) AS count " +
            "FROM " +
            "    t_link_access_logs " +
            "WHERE " +
            "    full_short_url = #{param.fullShortUrl} " +
            "    AND gid = #{param.gid} " +
            "    AND create_time BETWEEN CONCAT(#{param.startDate},' 00:00:00') and CONCAT(#{param.endDate},' 23:59:59') " +
            "GROUP BY " +
            "    full_short_url, gid, ip " +
            "ORDER BY " +
            "    count DESC " +
            "LIMIT 5;")
    List<HashMap<String, Object>> listTopIpByShortLink(@Param("param") ShortLinkStatsReqDTO requestParam);

    /**
     * 根据短链接获取指定日期内新旧访客数据
     * 返回的结果类型为键值对，oldUserCnt: 5, newUserCnt: 6. 查询到的结果会根据返回类型自动进行映射
     */
    @Select("SELECT " +
            "    SUM(old_user) AS oldUserCnt, " +
            "    SUM(new_user) AS newUserCnt " +
            "FROM ( " +
            "    SELECT " +
            "        CASE WHEN COUNT(DISTINCT DATE(create_time)) > 1 THEN 1 ELSE 0 END AS old_user, " +
            "        CASE WHEN COUNT(DISTINCT DATE(create_time)) = 1 AND MAX(create_time) >= CONCAT(#{param.startDate},' 00:00:00') AND MAX(create_time) <= CONCAT(#{param.endDate},' 23:59:59') THEN 1 ELSE 0 END AS new_user " +
            "    FROM " +
            "        t_link_access_logs " +
            "    WHERE " +
            "        full_short_url = #{param.fullShortUrl} " +
            "        AND gid = #{param.gid} " +
            "    GROUP BY " +
            "        user " +
            ") AS user_counts;")
    HashMap<String, Object> findUvTypeCntByShortLink(@Param("param") ShortLinkStatsReqDTO requestParam);
}

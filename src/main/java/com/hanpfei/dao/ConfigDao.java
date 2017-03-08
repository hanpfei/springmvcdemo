package com.hanpfei.dao;

import com.hanpfei.meta.Config;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface ConfigDao {
    @Insert("INSERT INTO config(product_key, device_version, platform, app_version, sdk_version, data) values(#{product_key}, #{device_version}, #{platform}, #{app_version}, #{sdk_version}, #{data})")
    int addConfig(@Param("product_key") String productKey, @Param("device_version") String deviceVersion,
                  @Param("platform") String platform, @Param("app_version") String appVersion,
                  @Param("sdk_version") String sdkVersion, @Param("data") String data);

    @Update("UPDATE config SET product_key=#{product_key}, device_version=#{device_version}, platform=#{platform}, app_version=#{app_version}, product_key=#{product_key}, sdk_version=#{sdk_version}, data=#{data} WHERE id=#{id}")
    int updateConfig(@Param("id") long id, @Param("product_key") String productKey, @Param("device_version") String deviceVersion,
                     @Param("platform") String platform, @Param("app_version") String appVersion,
                     @Param("sdk_version") String sdkVersion, @Param("data") String data);

    @Select("select * from config where (product_key= #{product_key}) order by id desc ")
    List<Config> getConfigByProductKey(@Param("product_key") String productKey);

    @Select("SELECT * FROM config WHERE id=#{id}")
    Config getConfig(@Param("id") long id);
}



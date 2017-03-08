package com.hanpfei.service;

import com.hanpfei.dao.ConfigDao;
import com.hanpfei.meta.Config;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ConfigService {

    @Resource
    private ConfigDao configDao;

    public void save(Config configItem) {
        configDao.addConfig(configItem.getProduct_key(), configItem.getDevice_version(), configItem.getPlatform(),
                configItem.getApp_version(), configItem.getSdk_version(), configItem.getData());
    }

    public void update(long id, Config configItem) {
        configDao.updateConfig(id, configItem.getProduct_key(), configItem.getDevice_version(), configItem.getPlatform(),
                configItem.getApp_version(), configItem.getSdk_version(), configItem.getData());
        return;
    }

    public Config getConfigItem(Integer id) {
        Config dataItem = configDao.getConfig(id);
        return dataItem;
    }

    public List<Config> getConfigItemByProductKey(String productKey) {
        return configDao.getConfigByProductKey(productKey);
    }

    public void delete(Integer id) {
        return;
    }
}

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
        List<Config> configs = configDao.getConfigByAll(configItem.getProduct_key(), configItem.getDevice_version(),
                configItem.getPlatform(), configItem.getApp_version(), configItem.getSdk_version());
        if (configs != null && !configs.isEmpty()) {
            Config config = configs.get(0);
            if (!config.getData().equalsIgnoreCase(configItem.getData())) {
                update(config.getId(), configItem);
            }
            if (configs.size() > 1) {
                for (int i = 1; i < configs.size(); ++ i) {
                    config = configs.get(i);
                    configDao.deleteConfig(config.getId());
                }
            }
        } else {
            configDao.addConfig(configItem.getProduct_key(), configItem.getDevice_version(), configItem.getPlatform(),
                    configItem.getApp_version(), configItem.getSdk_version(), configItem.getData());
        }
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
        configDao.deleteConfig(id);
        return;
    }
}

package com.hanpfei;

import com.hanpfei.meta.Config;
import com.hanpfei.meta.ConfigData;
import com.hanpfei.service.ConfigService;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;


@Controller
public class ConfigController {
    private static final Logger logger = Logger.getLogger(ConfigController.class);

    @Resource
    ConfigService configService;

    @RequestMapping(value = "/v1/setconfig", method = RequestMethod.POST)
    public @ResponseBody
    ConfigData saveConfig(HttpServletRequest request, HttpServletResponse response,
                         ModelMap model) throws Exception {
        String compressed = GeneralUtils.getString(request.getHeader(Const.HEADER_MAM_COMPRESSED), "false");
        Map<String, Object> headerMap = WebUtils.extractHeader(request);
        String productKey = (String) headerMap.get(FieldConst.Header.productKey);

        String platform = (String) headerMap.get(FieldConst.Header.platform);
        String deviceVersion = (String)headerMap.get(FieldConst.Header.deviceVersion);
        String appVersion = (String) headerMap.get(FieldConst.Header.appVersion);
        String sdkVersion = (String) headerMap.get(FieldConst.Header.sdkVersion);

        Config config = new Config();
        config.setProduct_key(productKey);
        config.setPlatform(platform);
        config.setDevice_version(deviceVersion);
        config.setApp_version(appVersion);
        config.setSdk_version(sdkVersion);

        ConfigData configData = new ConfigData(ConfigData.RES_CODE_SUCCESS, ConfigData.MSG_SUCCESS);
        try {
            InputStream input = request.getInputStream();
            if (compressed.equals("true")) {
                input = new GZIPInputStream(input);
            }
            String requestBody = IOUtils.toString(input, "utf-8");
            config.setData(requestBody);
            configService.save(config);
        } catch (Exception e) {
            configData = new ConfigData(ConfigData.RES_CODE_FAILED, ConfigData.MSG_FAILED);
        }

        return configData;
    }

    private List<Config> getConfigForProduct(String productKey) {
        List<Config> configs = configService.getConfigItemByProductKey(productKey);
        return configs;
    }

    private Config getBestFitConfig(List<Config> configDataItems, String deviceVersion,
                                    String platform, String appVersion, String sdkVersion) {
        return null;
    }

    @RequestMapping(value = "/v1/getconfig", method = RequestMethod.GET)
    public @ResponseBody
    ConfigData getConfig(HttpServletRequest request, HttpServletResponse response,
                         ModelMap model) throws Exception {
        ConfigData configData;
        try {
            Map<String, Object> headerMap = WebUtils.extractHeader(request);
            String productKey = (String) headerMap.get(FieldConst.Header.productKey);

            String platform = (String) headerMap.get(FieldConst.Header.platform);
            String deviceVersion = (String)headerMap.get(FieldConst.Header.deviceVersion);
            String appVersion = (String) headerMap.get(FieldConst.Header.appVersion);
            String sdkVersion = (String) headerMap.get(FieldConst.Header.sdkVersion);

            configData = new ConfigData(ConfigData.RES_CODE_SUCCESS, ConfigData.MSG_SUCCESS);
            List<Config> configDataItems = getConfigForProduct(productKey);
            if (configDataItems != null && !configDataItems.isEmpty()) {
                Config configDataItem = getBestFitConfig(configDataItems, deviceVersion, platform,
                        appVersion, sdkVersion);
                if (configDataItem != null) {
                    String data = configDataItem.getData();
//                    for (String fieldName : data.keySet()) {
//                        configData.addData(fieldName, data.get(fieldName));
//                    }
                }
            }
        } catch (Exception e) {
            logger.error(e);
            configData = null;
        }
        if (configData == null) {
            configData = new ConfigData(ConfigData.RES_CODE_FAILED, ConfigData.MSG_FAILED);
        }
        return configData;
    }
}

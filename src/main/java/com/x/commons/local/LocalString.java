package com.x.commons.local;

import com.x.commons.util.file.Files;
import com.x.commons.util.reflact.Loader;
import com.x.commons.util.string.Strings;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import static com.x.commons.enums.Charset.UTF8;

/**
 * @Desc TODO
 * @Date 2019-11-02 22:38
 * @Author AD
 */
public final class LocalString {

    private Properties props;

    public LocalString() {
        this.props = new Properties();
    }

    /**
     * 加载区域性配置文件
     *
     * @param path 配置文件路径，如：com/x/commons/local/zh_CN.properties
     * @return 是否加载成功
     */
    public boolean load(String path) {
        try (InputStream in = Loader.getStream(path) ;) {
            if (in == null) return false;
            try (InputStreamReader reader = Files.getUnicodeReader(in, UTF8);
            ) {
                this.props.load(reader);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 根据xxx.properties里的key替换成相应的信息
     *
     * @param localKey
     * @param params
     * @return
     */
    public String text(String localKey, Object... params) {
        return Strings.replace(props.getProperty(localKey), params);
    }

}

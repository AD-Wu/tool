package com.x.framework.config;

import com.ax.commons.utils.FileHelper;
import com.x.commons.enums.Charset;
import com.x.commons.util.file.Files;
import com.x.commons.util.string.Strings;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * @Desc
 * @Date 2020-03-21 22:03
 * @Author AD
 */
public class StringText {
    private Properties props = null;
    private String langKey = null;
    
    public StringText(String name, String langKey) throws Exception {
        this.langKey = langKey;
        if (Strings.isNotNull(name)) {
            name = name + FileHelper.SP;
        } else {
            name = "";
        }
        String filePath = Files.getAppPath(true) + "local" + FileHelper.SP + name;
        String fileName = langKey + ".properties";
        this.props = new Properties();
        
        try (FileInputStream in = new FileInputStream(filePath + fileName);
             InputStreamReader reader = Files.getUnicodeReader(in, Charset.UTF8);){
            this.props.load(reader);
        } catch (Exception e) {
            throw e;
        }
        
    }
    
    public String text(String key, Object... params) {
        return Strings.replace(this.props.getProperty(key), params);
    }
    
    public String getLangKey() {
        return this.langKey;
    }
}

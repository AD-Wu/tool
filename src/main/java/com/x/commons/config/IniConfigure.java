package com.x.commons.config;

import com.ax.commons.utils.ConvertHelper;
import com.ax.commons.utils.FileHelper;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Desc
 * @Date 2019-11-29 23:24
 * @Author AD
 */
public class IniConfigure {
    private Map<String, Section> sections = new LinkedHashMap();
    
    public IniConfigure() {
    }
    
    public IniConfigure.Section putSection(String var1) {
        return this.putSection(var1, (String)null);
    }
    
    public IniConfigure.Section putSection(String var1, String var2) {
        IniConfigure.Section var3 = new IniConfigure.Section(var1, var2);
        IniConfigure.Section var4 = (IniConfigure.Section)this.sections.put(var1, var3);
        if (var2 == null && var4 != null) {
            var3.setNote(var4.getNote());
        }
        
        return var3;
    }
    
    public IniConfigure.Section getSection(String var1) {
        return (IniConfigure.Section)this.sections.get(var1);
    }
    
    public IniConfigure.Section[] getSections() {
        return (IniConfigure.Section[])this.sections.values().toArray(new IniConfigure.Section[0]);
    }
    
    public IniConfigure.Variable getVariable(String var1, String var2) {
        IniConfigure.Section var3 = (IniConfigure.Section)this.sections.get(var1);
        return var3 == null ? null : var3.getVariable(var2);
    }
    
    public String getValue(String var1, String var2) {
        IniConfigure.Section var3 = (IniConfigure.Section)this.sections.get(var1);
        return var3 == null ? null : var3.getValue(var2);
    }
    
    public String getString(String var1, String var2, String var3) {
        IniConfigure.Section var4 = (IniConfigure.Section)this.sections.get(var1);
        return var4 == null ? var3 : var4.getString(var2, var3);
    }
    
    public int getInt(String var1, String var2, int var3) {
        IniConfigure.Section var4 = (IniConfigure.Section)this.sections.get(var1);
        return var4 == null ? var3 : var4.getInt(var2, var3);
    }
    
    public long getLong(String var1, String var2, long var3) {
        IniConfigure.Section var5 = (IniConfigure.Section)this.sections.get(var1);
        return var5 == null ? var3 : var5.getLong(var2, var3);
    }
    
    public boolean getBoolean(String var1, String var2, boolean var3) {
        IniConfigure.Section var4 = (IniConfigure.Section)this.sections.get(var1);
        return var4 == null ? var3 : var4.getBoolean(var2, var3);
    }
    
    public static void main(String[] args) {
        IniConfigure conf = new IniConfigure();
        conf.load("x-framework/PoolConfig.properties");
    }
    
    public void load(String var1) {
        this.sections = new LinkedHashMap();
        String var2 = null;
        
        try {
            var2 = FileHelper.readTxt(var1, "UTF-8");
        } catch (IOException var15) {
        }
        
        if (var2 != null && var2.length() != 0) {
            var2 = var2.replaceAll("\r\n", "\n");
            var2 = var2.replaceAll("\r", "\n");
            String[] var3 = var2.split("[\n]");
            if (var3 != null && var3.length != 0) {
                Pattern var4 = Pattern.compile("^\\[\\s*(.*)\\s*\\]$");
                StringBuilder var5 = new StringBuilder();
                IniConfigure.Section var6 = null;
                String[] var7 = var3;
                int var8 = var3.length;
                
                for(int var9 = 0; var9 < var8; ++var9) {
                    String var10 = var7[var9];
                    var10 = var10.trim();
                    if (var10.length() != 0 && !var10.startsWith("#")) {
                        Matcher var11 = var4.matcher(var10);
                        String var13;
                        if (var11.matches()) {
                            MatchResult var12 = var11.toMatchResult();
                            if (var12.groupCount() > 0) {
                                var13 = var12.group(1);
                                if (var13 != null && var13.length() > 0) {
                                    if (this.sections.containsKey(var13)) {
                                        var6 = (IniConfigure.Section)this.sections.get(var13);
                                    } else {
                                        var6 = new IniConfigure.Section(var13, this.getNote(var5));
                                        this.sections.put(var13, var6);
                                    }
                                    
                                    if (var5.length() > 0) {
                                        var5 = new StringBuilder();
                                    }
                                }
                            }
                        } else if (var6 != null) {
                            int var16 = var10.indexOf("=");
                            if (var16 > 0) {
                                var13 = var10.substring(0, var16).trim();
                                if (var13.length() > 0) {
                                    String var14 = var10.substring(var16 + 1).trim();
                                    var6.put(var13, var14, this.getNote(var5));
                                    if (var5.length() > 0) {
                                        var5 = new StringBuilder();
                                    }
                                }
                            }
                        }
                    } else {
                        if (var10.startsWith("#")) {
                            var10 = var10.replaceAll("^#\\s*", "");
                        }
                        
                        var5.append(var10);
                        var5.append("\r\n");
                    }
                }
                
            }
        }
    }
    
    public boolean save(String var1, String var2) {
        StringBuilder var3 = new StringBuilder();
        IniConfigure.Section[] var4 = this.getSections();
        IniConfigure.Section[] var5 = var4;
        int var6 = var4.length;
        
        for(int var7 = 0; var7 < var6; ++var7) {
            IniConfigure.Section var8 = var5[var7];
            var3.append(this.fixNote(var8.getNote()));
            var3.append("[");
            var3.append(var8.getName());
            var3.append("]\r\n");
            IniConfigure.Variable[] var9 = var8.getVariables();
            IniConfigure.Variable[] var10 = var9;
            int var11 = var9.length;
            
            for(int var12 = 0; var12 < var11; ++var12) {
                IniConfigure.Variable var13 = var10[var12];
                var3.append(this.fixNote(var13.getNote()));
                var3.append(var13.getKey());
                var3.append("=");
                var3.append(var13.getValue());
                var3.append("\r\n");
            }
        }
        
        var1 = FileHelper.getLocalPath(var1, false);
        int var14 = var1.lastIndexOf(FileHelper.SP);
        if (var14 != -1) {
            String var15 = var1.substring(0, var14);
            FileHelper.createFolder(var15);
        }
        
        return FileHelper.createFile(var1, var3.toString(), var2);
    }
    
    private String getNote(StringBuilder var1) {
        if (var1.length() == 0) {
            return null;
        } else {
            String var2 = var1.toString();
            return var2.replaceAll("\r\n$", "");
        }
    }
    
    private String fixNote(String var1) {
        if (var1 != null && var1.length() != 0) {
            var1 = var1.replaceAll("\r\n", "\n");
            var1 = var1.replaceAll("\r", "\n");
            String[] var2 = var1.split("[\n]");
            if (var2 != null && var2.length != 0) {
                StringBuilder var3 = new StringBuilder();
                String[] var4 = var2;
                int var5 = var2.length;
                
                for(int var6 = 0; var6 < var5; ++var6) {
                    String var7 = var4[var6];
                    var7 = var7.trim();
                    if (var7.length() == 0) {
                        var3.append("\r\n");
                    } else if (!var7.startsWith("#")) {
                        var3.append("# ");
                        var3.append(var7);
                        var3.append("\r\n");
                    }
                }
                
                return var3.toString();
            } else {
                return "";
            }
        } else {
            return "";
        }
    }
    
    public class Variable {
        private String key;
        private String value;
        private String note;
        
        public Variable(String var2, String var3, String var4) {
            this.key = var2;
            this.value = var3;
            this.note = var4;
        }
        
        public String getKey() {
            return this.key;
        }
        
        public String getValue() {
            return this.value;
        }
        
        public void setValue(String var1) {
            this.value = var1;
        }
        
        public String getNote() {
            return this.note;
        }
        
        public void setNote(String var1) {
            this.note = var1;
        }
    }
    
    public class Section {
        private String name;
        private String note;
        private Map<String, IniConfigure.Variable> variables = new LinkedHashMap();
        
        public Section(String var2, String var3) {
            this.name = var2;
            this.note = var3;
        }
        
        public String getName() {
            return this.name;
        }
        
        public String getNote() {
            return this.note;
        }
        
        public void setNote(String var1) {
            this.note = var1;
        }
        
        public void put(String var1, Object var2) {
            this.put(var1, var2, (String)null);
        }
        
        public void put(String var1, Object var2, String var3) {
            IniConfigure.Variable var4 = IniConfigure.this.new Variable(var1, String.valueOf(var2), var3);
            IniConfigure.Variable var5 = (IniConfigure.Variable)this.variables.put(var1, var4);
            if (var3 == null && var5 != null) {
                var4.setNote(var5.getNote());
            }
            
        }
        
        public IniConfigure.Variable getVariable(String var1) {
            return (IniConfigure.Variable)this.variables.get(var1);
        }
        
        public IniConfigure.Variable[] getVariables() {
            return (IniConfigure.Variable[])this.variables.values().toArray(new IniConfigure.Variable[0]);
        }
        
        public IniConfigure.Variable remove(String var1) {
            return (IniConfigure.Variable)this.variables.remove(var1);
        }
        
        public String getValue(String var1) {
            IniConfigure.Variable var2 = (IniConfigure.Variable)this.variables.get(var1);
            return var2 == null ? null : var2.getValue();
        }
        
        public String getString(String var1, String var2) {
            String var3 = this.getValue(var1);
            return var3 != null && var3.trim().length() != 0 ? var3 : var2;
        }
        
        public int getInt(String var1, int var2) {
            return ConvertHelper.toInt(this.getValue(var1), var2);
        }
        
        public long getLong(String var1, long var2) {
            return ConvertHelper.toLong(this.getValue(var1), var2);
        }
        
        public boolean getBoolean(String var1, boolean var2) {
            return ConvertHelper.toBoolean(this.getValue(var1), var2);
        }
    }
}

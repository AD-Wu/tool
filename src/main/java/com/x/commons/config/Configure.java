package com.x.commons.config;

import com.x.commons.util.bean.New;
import com.x.commons.util.collection.XArrays;
import com.x.commons.util.convert.Converts;
import com.x.commons.util.file.Files;
import com.x.commons.util.string.Strings;

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
public class Configure {
    
    private Map<String, Section> sections = new LinkedHashMap<>();
    
    public Configure() {
    }
    
    public Section putSection(String name) {
        return this.putSection(name, null);
    }
    
    public Section putSection(String name, String note) {
        Section section = new Section(name, note);
        Section rep = sections.put(name, section);
        if (note == null && rep != null) {
            section.setNote(rep.getNote());
        }
        return section;
    }
    
    public Section getSection(String name) {
        return sections.get(name);
    }
    
    public Section[] getSections() {
        return sections.values().toArray(new Section[0]);
    }
    
    public Variable getVariable(String name, String key) {
        Section sct = sections.get(name);
        return sct == null ? null : sct.getVariable(key);
    }
    
    public String getValue(String name, String key) {
        Section sct = sections.get(name);
        return sct == null ? null : sct.getValue(key);
    }
    
    public String getString(String name, String key, String defaultValue) {
        Section sct = sections.get(name);
        return sct == null ? defaultValue : sct.getString(key, defaultValue);
    }
    
    public int getInt(String name, String key, int defaultValue) {
        Section sct = sections.get(name);
        return sct == null ? defaultValue : sct.getInt(key, defaultValue);
    }
    
    public long getLong(String name, String key, long defaultValue) {
        Section sct = sections.get(name);
        return sct == null ? defaultValue : sct.getLong(key, defaultValue);
    }
    
    public boolean getBoolean(String name, String key, boolean defaultValue) {
        Section sct = sections.get(name);
        return sct == null ? defaultValue : sct.getBoolean(key, defaultValue);
    }
    
    public void load(String path) {
        this.sections = new LinkedHashMap();
        String text = null;
        
        try {
            text = Files.readTxt(path, "UTF-8");
        } catch (IOException e) {
        }
        
        if (text != null && text.length() != 0) {
            text = text.replaceAll("\r\n", "\n");
            text = text.replaceAll("\r", "\n");
            String[] lines = text.split("[\n]");
            if (!XArrays.isEmpty(lines)) {
                Pattern p = Pattern.compile("^\\[\\s*(.*)\\s*\\]$");
                StringBuilder snote = new StringBuilder();
                Section section = null;
                
                for (String line : lines) {
                    line = line.trim();
                    if (line.length() != 0 && !line.startsWith("#")) {
                        Matcher m = p.matcher(line);
                        String key;
                        if (m.matches()) {
                            MatchResult eqIndex = m.toMatchResult();
                            if (eqIndex.groupCount() > 0) {
                                key = eqIndex.group(1);
                                if (key != null && key.length() > 0) {
                                    if (this.sections.containsKey(key)) {
                                        section = sections.get(key);
                                    } else {
                                        section = new Section(key, this.getNote(snote));
                                        this.sections.put(key, section);
                                    }
                                    
                                    if (snote.length() > 0) {
                                        snote = new StringBuilder();
                                    }
                                }
                            }
                        } else if (section != null) {
                            int equalIndex = line.indexOf("=");
                            if (equalIndex > 0) {
                                key = line.substring(0, equalIndex).trim();
                                if (key.length() > 0) {
                                    String value = line.substring(equalIndex + 1).trim();
                                    section.put(key, value, this.getNote(snote));
                                    if (snote.length() > 0) {
                                        snote = new StringBuilder();
                                    }
                                }
                            }
                        }
                    } else {
                        if (line.startsWith("#")) {
                            line = line.replaceAll("^#\\s*", "");
                        }
                        
                        snote.append(line);
                        snote.append("\r\n");
                    }
                }
                
            }
        }
    }
    
    public boolean save(String fileName, String charset) {
        StringBuilder text = new StringBuilder();
        Section[] sections = this.getSections();
        
        for (Section pos : sections) {
            text.append(this.fixNote(pos.getNote()));
            text.append("[");
            text.append(pos.getName());
            text.append("]\r\n");
            Variable[] vs = pos.getVariables();
            
            for (Variable v : vs) {
                text.append(this.fixNote(v.getNote()));
                text.append(v.getKey());
                text.append("=");
                text.append(v.getValue());
                text.append("\r\n");
            }
        }
        
        fileName = Files.getLocalPath(fileName, false);
        int spIndex = fileName.lastIndexOf(Files.SP);
        if (spIndex != -1) {
            String folder = fileName.substring(0, spIndex);
            Files.createFolder(folder);
        }
        
        return Files.createFile(fileName, text.toString(), charset);
    }
    
    private String getNote(StringBuilder sb) {
        if (sb.length() == 0) {
            return null;
        } else {
            String note = sb.toString();
            return note.replaceAll("\r\n$", "");
        }
    }
    
    private String fixNote(String note) {
        if (note != null && note.length() != 0) {
            note = note.replaceAll("\r\n", "\n");
            note = note.replaceAll("\r", "\n");
            String[] lines = note.split("[\n]");
            if (lines != null && lines.length != 0) {
                StringBuilder snote = new StringBuilder();
                
                for (String line : lines) {
                    line = line.trim();
                    if (line.length() == 0) {
                        snote.append("\r\n");
                    } else if (!line.startsWith("#")) {
                        snote.append("# ");
                        snote.append(line);
                        snote.append("\r\n");
                    }
                }
                return snote.toString();
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
        
        public Variable(String key, String value, String note) {
            this.key = key;
            this.value = value;
            this.note = note;
        }
        
        public String getKey() {
            return this.key;
        }
        
        public String getValue() {
            return this.value;
        }
        
        public void setValue(String value) {
            this.value = value;
        }
        
        public String getNote() {
            return this.note;
        }
        
        public void setNote(String note) {
            this.note = note;
        }

        @Override
        public String toString() {
            return Strings.defaultToString(this);
        }
    }
    
    public class Section {
        
        private String name;
        
        private String note;
        
        private Map<String, Variable> variables = New.linkedMap();
        
        public Section(String name, String note) {
            this.name = name;
            this.note = note;
        }
        
        public String getName() {
            return this.name;
        }
        
        public String getNote() {
            return this.note;
        }
        
        public void setNote(String note) {
            this.note = note;
        }
        
        public void put(String var1, Object var2) {
            this.put(var1, var2, null);
        }
        
        public void put(String key, Object value, String note) {
            Variable v = Configure.this.new Variable(key, String.valueOf(value), note);
            Variable r = variables.put(key, v);
            if (note == null && r != null) {
                v.setNote(r.getNote());
            }
            
        }
        
        public Variable getVariable(String key) {
            return variables.get(key);
        }
        
        public Variable[] getVariables() {
            return variables.values().toArray(new Variable[0]);
        }
        
        public Variable remove(String key) {
            return variables.remove(key);
        }
        
        public String getValue(String key) {
            Variable v = variables.get(key);
            return v == null ? null : v.getValue();
        }
        
        public String getString(String key, String defaultValue) {
            String value = this.getValue(key);
            return value != null && value.trim().length() != 0 ? value : defaultValue;
        }
        
        public int getInt(String key, int defaultValue) {
            return Converts.toInt(this.getValue(key), defaultValue);
        }
        
        public long getLong(String key, long defaultValue) {
            return Converts.toLong(this.getValue(key), defaultValue);
        }
        
        public boolean getBoolean(String key, boolean defaultValue) {
            return Converts.toBoolean(this.getValue(key), defaultValue);
        }

        @Override
        public String toString() {
            return Strings.defaultToString(this);
        }
    }
    
}

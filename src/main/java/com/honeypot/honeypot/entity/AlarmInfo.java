package com.honeypot.honeypot.entity;

import java.util.Objects;

public class AlarmInfo {
    private Integer Type;
    private String ProvessName;
    private String Path;
    private String Explain;
    private String ExtendPath;

    public Integer getType() {
        return Type;
    }

    public void setType(Integer type) {
        Type = type;
    }

    public String getProvessName() {
        return ProvessName;
    }

    public void setProvessName(String provessName) {
        ProvessName = provessName;
    }

    public String getPath() {
        return Path;
    }

    public void setPath(String path) {
        Path = path;
    }

    public String getExplain() {
        return Explain;
    }

    public void setExplain(String explain) {
        Explain = explain;
    }

    public String getExtendPath() {
        return ExtendPath;
    }

    public void setExtendPath(String extendPath) {
        ExtendPath = extendPath;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AlarmInfo alarmInfo = (AlarmInfo) o;
        return Objects.equals(Type, alarmInfo.Type) &&
                Objects.equals(ProvessName, alarmInfo.ProvessName) &&
                Objects.equals(Path, alarmInfo.Path) &&
                Objects.equals(Explain, alarmInfo.Explain) &&
                Objects.equals(ExtendPath, alarmInfo.ExtendPath);
    }
    @Override
    public int hashCode() {

        return Objects.hash(Type, ProvessName, Path, Explain, ExtendPath);
    }
    @Override
    public String toString() {
        return "AlarmInfo{" +
                "Type=" + Type +
                ", ProvessName='" + ProvessName + '\'' +
                ", Path='" + Path + '\'' +
                ", Explain='" + Explain + '\'' +
                ", ExtendPath='" + ExtendPath + '\'' +
                '}';
    }
}

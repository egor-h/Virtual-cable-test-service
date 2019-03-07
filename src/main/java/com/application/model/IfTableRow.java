package com.application.model;

public class IfTableRow {
    private long ifIndex;
    private String ifDescr;
    private String ifType;
    private int ifMtu;
    private String operStatus;

    public IfTableRow() {
    }

    public IfTableRow(long ifIndex, String ifDescr, String ifType, int ifMtu, String operStatus) {
        this.ifIndex = ifIndex;
        this.ifDescr = ifDescr;
        this.ifType = ifType;
        this.ifMtu = ifMtu;
        this.operStatus = operStatus;
    }

    @Override
    public String toString() {
        return "IfTableRow{" +
                "ifIndex=" + ifIndex +
                ", ifDescr='" + ifDescr + '\'' +
                ", ifType='" + ifType + '\'' +
                ", ifMtu=" + ifMtu +
                ", operStatus='" + operStatus + '\'' +
                '}';
    }

    public long getIfIndex() {
        return ifIndex;
    }

    public void setIfIndex(long ifIndex) {
        this.ifIndex = ifIndex;
    }

    public String getIfDescr() {
        return ifDescr;
    }

    public void setIfDescr(String ifDescr) {
        this.ifDescr = ifDescr;
    }

    public String getIfType() {
        return ifType;
    }

    public void setIfType(String ifType) {
        this.ifType = ifType;
    }

    public int getIfMtu() {
        return ifMtu;
    }

    public void setIfMtu(int ifMtu) {
        this.ifMtu = ifMtu;
    }

    public String getOperStatus() {
        return operStatus;
    }

    public void setOperStatus(String operStatus) {
        this.operStatus = operStatus;
    }
}

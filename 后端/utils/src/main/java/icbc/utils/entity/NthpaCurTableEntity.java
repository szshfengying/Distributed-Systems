package icbc.utils.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "nthpa_cur_table", catalog = "")
@IdClass(NthpaCurTableEntityPK.class)
public class NthpaCurTableEntity implements Serializable {
    private long snId;
    private int zoneno;
    private short currType;
    private byte useStats;
    private short currCode;
    private String chinese;
    private String english;
    private String currSymbol;
    private short rateExDay;
    private String currUnit;
    private byte currMinUnit;
    private byte foreignCurrFlag;

    @Id
    @Column(name = "sn_id")
    public long getSnId() {
        return snId;
    }

    public void setSnId(long snId) {
        this.snId = snId;
    }

    @Id
    @Column(name = "zoneno")
    public int getZoneno() {
        return zoneno;
    }

    public void setZoneno(int zoneno) {
        this.zoneno = zoneno;
    }

    @Id
    @Column(name = "curr_type")
    public short getCurrType() {
        return currType;
    }

    @Override
    public String toString() {
        return "NthpaCurTableEntity{" +
                "snId=" + snId +
                ", zoneno=" + zoneno +
                ", currType=" + currType +
                ", useStats=" + useStats +
                ", currCode=" + currCode +
                ", chinese='" + chinese + '\'' +
                ", english='" + english + '\'' +
                ", currSymbol='" + currSymbol + '\'' +
                ", rateExDay=" + rateExDay +
                ", currUnit='" + currUnit + '\'' +
                ", currMinUnit=" + currMinUnit +
                ", foreignCurrFlag=" + foreignCurrFlag +
                '}';
    }

    public void setCurrType(short currType) {
        this.currType = currType;
    }

    @Basic
    @Column(name = "use_stats")
    public byte getUseStats() {
        return useStats;
    }

    public void setUseStats(byte useStats) {
        this.useStats = useStats;
    }

    @Basic
    @Column(name = "curr_code")
    public short getCurrCode() {
        return currCode;
    }

    public void setCurrCode(short currCode) {
        this.currCode = currCode;
    }

    @Basic
    @Column(name = "chinese")
    public String getChinese() {
        return chinese;
    }

    public void setChinese(String chinese) {
        this.chinese = chinese;
    }

    @Basic
    @Column(name = "english")
    public String getEnglish() {
        return english;
    }

    public void setEnglish(String english) {
        this.english = english;
    }

    @Basic
    @Column(name = "curr_symbol")
    public String getCurrSymbol() {
        return currSymbol;
    }

    public void setCurrSymbol(String currSymbol) {
        this.currSymbol = currSymbol;
    }

    @Basic
    @Column(name = "rate_ex_day")
    public short getRateExDay() {
        return rateExDay;
    }

    public void setRateExDay(short rateExDay) {
        this.rateExDay = rateExDay;
    }

    @Basic
    @Column(name = "curr_unit")
    public String getCurrUnit() {
        return currUnit;
    }

    public void setCurrUnit(String currUnit) {
        this.currUnit = currUnit;
    }

    @Basic
    @Column(name = "curr_min_unit")
    public byte getCurrMinUnit() {
        return currMinUnit;
    }

    public void setCurrMinUnit(byte currMinUnit) {
        this.currMinUnit = currMinUnit;
    }

    @Basic
    @Column(name = "foreign_curr_flag")
    public byte getForeignCurrFlag() {
        return foreignCurrFlag;
    }

    public void setForeignCurrFlag(byte foreignCurrFlag) {
        this.foreignCurrFlag = foreignCurrFlag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NthpaCurTableEntity that = (NthpaCurTableEntity) o;
        return snId == that.snId &&
                currType == that.currType &&
                useStats == that.useStats &&
                currCode == that.currCode &&
                rateExDay == that.rateExDay &&
                currMinUnit == that.currMinUnit &&
                foreignCurrFlag == that.foreignCurrFlag &&
                Objects.equals(zoneno, that.zoneno) &&
                Objects.equals(chinese, that.chinese) &&
                Objects.equals(english, that.english) &&
                Objects.equals(currSymbol, that.currSymbol) &&
                Objects.equals(currUnit, that.currUnit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(snId, zoneno, currType, useStats, currCode, chinese, english, currSymbol, rateExDay, currUnit, currMinUnit, foreignCurrFlag);
    }
}

package icbc.utils.entity;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "acc_info_table",  catalog = "")
public class AccInfoTableEntity {
    private long bankId;
    private String accId;
    private byte accStatus;
    private String accTitle;
    private String password;
    private int depositSign;
    private int exchangeSign;
    private int depositSwitch;
    private int withdrawalSwitch;
    private String openingInstitution;
    private Date lastModifiedDate;
    private Date signingDate;
    private Date logoutDate;
    private int regionId;

    @Id
    @Column(name = "bank_id")
    public long getBankId() {
        return bankId;
    }

    public void setBankId(long bankId) {
        this.bankId = bankId;
    }

    @Basic
    @Column(name = "acc_id")
    public String getAccId() {
        return accId;
    }

    public void setAccId(String accId) {
        this.accId = accId;
    }

    @Basic
    @Column(name = "acc_status")
    public byte getAccStatus() {
        return accStatus;
    }

    public void setAccStatus(byte accStatus) {
        this.accStatus = accStatus;
    }

    @Basic
    @Column(name = "acc_title")
    public String getAccTitle() {
        return accTitle;
    }

    public void setAccTitle(String accTitle) {
        this.accTitle = accTitle;
    }

    @Basic
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "AccInfoTableEntity{" +
                "bankId=" + bankId +
                ", accId=" + accId +
                ", accStatus=" + accStatus +
                ", accTitle='" + accTitle + '\'' +
                ", password=" + password +
                ", depositSign=" + depositSign +
                ", exchangeSign=" + exchangeSign +
                ", depositSwitch=" + depositSwitch +
                ", withdrawalSwitch=" + withdrawalSwitch +
                ", openingInstitution='" + openingInstitution + '\'' +
                ", lastModifiedDate=" + lastModifiedDate +
                ", signingDate=" + signingDate +
                ", logoutDate=" + logoutDate +
                ", regionId=" + regionId +
                '}';
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "deposit_sign")
    public int getDepositSign() {
        return depositSign;
    }

    public void setDepositSign(int depositSign) {
        this.depositSign = depositSign;
    }

    @Basic
    @Column(name = "exchange_sign")
    public int getExchangeSign() {
        return exchangeSign;
    }

    public void setExchangeSign(int exchangeSign) {
        this.exchangeSign = exchangeSign;
    }

    @Basic
    @Column(name = "deposit_switch")
    public int getDepositSwitch() {
        return depositSwitch;
    }

    public void setDepositSwitch(int depositSwitch) {
        this.depositSwitch = depositSwitch;
    }

    @Basic
    @Column(name = "withdrawal_switch")
    public int getWithdrawalSwitch() {
        return withdrawalSwitch;
    }

    public void setWithdrawalSwitch(int withdrawalSwitch) {
        this.withdrawalSwitch = withdrawalSwitch;
    }

    @Basic
    @Column(name = "opening_institution")
    public String getOpeningInstitution() {
        return openingInstitution;
    }

    public void setOpeningInstitution(String openingInstitution) {
        this.openingInstitution = openingInstitution;
    }

    @Basic
    @Column(name = "last_modified_date")
    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    @Basic
    @Column(name = "signing_date")
    public Date getSigningDate() {
        return signingDate;
    }

    public void setSigningDate(Date signingDate) {
        this.signingDate = signingDate;
    }

    @Basic
    @Column(name = "logout_date")
    public Date getLogoutDate() {
        return logoutDate;
    }

    public void setLogoutDate(Date logoutDate) {
        this.logoutDate = logoutDate;
    }

    @Basic
    @Column(name = "region_id")
    public int getRegionId() {
        return regionId;
    }

    public void setRegionId(int regionId) {
        this.regionId = regionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccInfoTableEntity that = (AccInfoTableEntity) o;
        return bankId == that.bankId &&
                accId == that.accId &&
                accStatus == that.accStatus &&
                password == that.password &&
                depositSign == that.depositSign &&
                exchangeSign == that.exchangeSign &&
                depositSwitch == that.depositSwitch &&
                withdrawalSwitch == that.withdrawalSwitch &&
                regionId == that.regionId &&
                Objects.equals(accTitle, that.accTitle) &&
                Objects.equals(openingInstitution, that.openingInstitution) &&
                Objects.equals(lastModifiedDate, that.lastModifiedDate) &&
                Objects.equals(signingDate, that.signingDate) &&
                Objects.equals(logoutDate, that.logoutDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bankId, accId, accStatus, accTitle, password, depositSign, exchangeSign, depositSwitch, withdrawalSwitch, openingInstitution, lastModifiedDate, signingDate, logoutDate, regionId);
    }
}

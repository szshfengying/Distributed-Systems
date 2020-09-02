package icbc.utils.entity;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "biz_reg_table",  catalog = "")
public class BizRegTableEntity {
    private long snId;
    private Date txnDate;
    private String txnId;
    private short channelType;
    private String execOrganno;
    private String execTellerno;
    private int txnCode;
    private int txnType;
    private byte cashTransferFlag;
    private String debitAccId;
    private String debitAccName;
    private short debitCurrType;
    private long debitAmount;
    private String creditAccId;
    private String creditAccName;
    private short creditCurrType;
    private long creditAmount;
    private byte status;
    private Date lastModifyDate;
    private int regionId;

    @Id
    @Column(name = "sn_id")
    public long getSnId() {
        return snId;
    }

    public void setSnId(long snId) {
        this.snId = snId;
    }

    @Basic
    @Column(name = "txn_date")
    public Date getTxnDate() {
        return txnDate;
    }

    public void setTxnDate(Date txnDate) {
        this.txnDate = txnDate;
    }

    @Basic
    @Column(name = "txn_id")
    public String getTxnId() {
        return txnId;
    }

    public void setTxnId(String txnId) {
        this.txnId = txnId;
    }

    @Basic
    @Column(name = "channel_type")
    public short getChannelType() {
        return channelType;
    }

    public void setChannelType(short channelType) {
        this.channelType = channelType;
    }

    @Basic
    @Column(name = "exec_organno")
    public String getExecOrganno() {
        return execOrganno;
    }

    public void setExecOrganno(String execOrganno) {
        this.execOrganno = execOrganno;
    }

    @Basic
    @Column(name = "exec_tellerno")
    public String getExecTellerno() {
        return execTellerno;
    }

    public void setExecTellerno(String execTellerno) {
        this.execTellerno = execTellerno;
    }

    @Basic
    @Column(name = "txn_code")
    public int getTxnCode() {
        return txnCode;
    }

    public void setTxnCode(int txnCode) {
        this.txnCode = txnCode;
    }

    @Basic
    @Column(name = "txn_type")
    public int getTxnType() {
        return txnType;
    }

    public void setTxnType(byte txnType) {
        this.txnType = txnType;
    }

    @Basic
    @Column(name = "cash_transfer_flag")
    public byte getCashTransferFlag() {
        return cashTransferFlag;
    }

    public void setCashTransferFlag(byte cashTransferFlag) {
        this.cashTransferFlag = cashTransferFlag;
    }

    @Basic
    @Column(name = "debit_acc_id")
    public String getDebitAccId() {
        return debitAccId;
    }

    public void setDebitAccId(String debitAccId) {
        this.debitAccId = debitAccId;
    }

    @Basic
    @Column(name = "debit_acc_name")
    public String getDebitAccName() {
        return debitAccName;
    }

    public void setDebitAccName(String debitAccName) {
        this.debitAccName = debitAccName;
    }

    @Basic
    @Column(name = "debit_curr_type")
    public short getDebitCurrType() {
        return debitCurrType;
    }

    public void setDebitCurrType(short debitCurrType) {
        this.debitCurrType = debitCurrType;
    }

    @Basic
    @Column(name = "debit_amount")
    public long getDebitAmount() {
        return debitAmount;
    }

    public void setDebitAmount(long debitAmount) {
        this.debitAmount = debitAmount;
    }

    @Basic
    @Column(name = "credit_acc_id")
    public String getCreditAccId() {
        return creditAccId;
    }

    public void setCreditAccId(String creditAccId) {
        this.creditAccId = creditAccId;
    }

    @Basic
    @Column(name = "credit_acc_name")
    public String getCreditAccName() {
        return creditAccName;
    }

    public void setCreditAccName(String creditAccName) {
        this.creditAccName = creditAccName;
    }

    @Basic
    @Column(name = "credit_curr_type")
    public short getCreditCurrType() {
        return creditCurrType;
    }

    public void setCreditCurrType(short creditCurrType) {
        this.creditCurrType = creditCurrType;
    }

    @Basic
    @Column(name = "credit_amount")
    public long getCreditAmount() {
        return creditAmount;
    }

    public void setCreditAmount(long creditAmount) {
        this.creditAmount = creditAmount;
    }

    @Basic
    @Column(name = "status")
    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    @Basic
    @Column(name = "last_modify_date")
    public Date getLastModifyDate() {
        return lastModifyDate;
    }

    public void setLastModifyDate(Date lastModifyDate) {
        this.lastModifyDate = lastModifyDate;
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
    public String toString() {
        return "BizRegTableEntity{" +
                "snId=" + snId +
                ", txnDate=" + txnDate +
                ", txnId='" + txnId + '\'' +
                ", channelType=" + channelType +
                ", execOrganno=" + execOrganno +
                ", execTellerno=" + execTellerno +
                ", txnCode=" + txnCode +
                ", txnType=" + txnType +
                ", cashTransferFlag=" + cashTransferFlag +
                ", debitAccId='" + debitAccId + '\'' +
                ", debitAccName='" + debitAccName + '\'' +
                ", debitCurrType=" + debitCurrType +
                ", debitAmount=" + debitAmount +
                ", creditAccId='" + creditAccId + '\'' +
                ", creditAccName='" + creditAccName + '\'' +
                ", creditCurrType=" + creditCurrType +
                ", creditAmount=" + creditAmount +
                ", status=" + status +
                ", lastModifyDate=" + lastModifyDate +
                ", regionId=" + regionId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BizRegTableEntity that = (BizRegTableEntity) o;
        return snId == that.snId &&
                channelType == that.channelType &&
                execOrganno == that.execOrganno &&
                execTellerno == that.execTellerno &&
                txnType == that.txnType &&
                cashTransferFlag == that.cashTransferFlag &&
                debitCurrType == that.debitCurrType &&
                debitAmount == that.debitAmount &&
                creditCurrType == that.creditCurrType &&
                creditAmount == that.creditAmount &&
                status == that.status &&
                regionId == that.regionId &&
                Objects.equals(txnDate, that.txnDate) &&
                Objects.equals(txnId, that.txnId) &&
                Objects.equals(txnCode, that.txnCode) &&
                Objects.equals(debitAccId, that.debitAccId) &&
                Objects.equals(debitAccName, that.debitAccName) &&
                Objects.equals(creditAccId, that.creditAccId) &&
                Objects.equals(creditAccName, that.creditAccName) &&
                Objects.equals(lastModifyDate, that.lastModifyDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(snId, txnDate, txnId, channelType, execOrganno, execTellerno, txnCode, txnType, cashTransferFlag, debitAccId, debitAccName, debitCurrType, debitAmount, creditAccId, creditAccName, creditCurrType, creditAmount, status, lastModifyDate, regionId);
    }
}

package icbc.utils.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "nthpa_brp_table",  catalog = "")
@IdClass(NthpaBrpTableEntityPK.class)
public class NthpaBrpTableEntity implements Serializable {
    private Long snId;
    private Integer zoneno;
    private Integer brno;
    private Integer mbrno;
    private Byte brnoType;
    private String brnoName;
    private Byte status;
    private String brnoFullName;
    private Integer rmbrno;
    private String address;
    private String zip;
    private String phone;
    private String shortnm;
    private Integer actBrno;
    private Byte brnoStatus;
    private Date lastModDate;
    private Long bakDecl;
    private String bakVarchar;

    @Id
    @Column(name = "sn_id")
    public Long getSnId() {
        return snId;
    }

    public void setSnId(Long snId) {
        this.snId = snId;
    }

    @Id
    @Column(name = "zoneno")
    public Integer getZoneno() {
        return zoneno;
    }

    public void setZoneno(Integer zoneno) {
        this.zoneno = zoneno;
    }

    @Id
    @Column(name = "brno")
    public Integer getBrno() {
        return brno;
    }

    public void setBrno(Integer brno) {
        this.brno = brno;
    }

    @Id
    @Column(name = "mbrno")
    public Integer getMbrno() {
        return mbrno;
    }

    public void setMbrno(Integer mbrno) {
        this.mbrno = mbrno;
    }

    @Basic
    @Column(name = "brno_type")
    public Byte getBrnoType() {
        return brnoType;
    }

    public void setBrnoType(Byte brnoType) {
        this.brnoType = brnoType;
    }

    @Basic
    @Column(name = "brno_name")
    public String getBrnoName() {
        return brnoName;
    }

    public void setBrnoName(String brnoName) {
        this.brnoName = brnoName;
    }

    @Basic
    @Column(name = "status")
    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    @Basic
    @Column(name = "brno_full_name")
    public String getBrnoFullName() {
        return brnoFullName;
    }

    public void setBrnoFullName(String brnoFullName) {
        this.brnoFullName = brnoFullName;
    }

    @Basic
    @Column(name = "rmbrno")
    public Integer getRmbrno() {
        return rmbrno;
    }

    public void setRmbrno(Integer rmbrno) {
        this.rmbrno = rmbrno;
    }

    @Basic
    @Column(name = "address")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Basic
    @Column(name = "zip")
    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    @Basic
    @Column(name = "phone")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Basic
    @Column(name = "shortnm")
    public String getShortnm() {
        return shortnm;
    }

    public void setShortnm(String shortnm) {
        this.shortnm = shortnm;
    }

    @Basic
    @Column(name = "act_brno")
    public Integer getActBrno() {
        return actBrno;
    }

    public void setActBrno(Integer actBrno) {
        this.actBrno = actBrno;
    }

    @Basic
    @Column(name = "brno_status")
    public Byte getBrnoStatus() {
        return brnoStatus;
    }

    public void setBrnoStatus(Byte brnoStatus) {
        this.brnoStatus = brnoStatus;
    }

    @Basic
    @Column(name = "last_mod_date")
    public Date getLastModDate() {
        return lastModDate;
    }

    public void setLastModDate(Date lastModDate) {
        this.lastModDate = lastModDate;
    }

    @Basic
    @Column(name = "bak_decl")
    public Long getBakDecl() {
        return bakDecl;
    }

    public void setBakDecl(Long bakDecl) {
        this.bakDecl = bakDecl;
    }

    @Basic
    @Column(name = "bak_varchar")
    public String getBakVarchar() {
        return bakVarchar;
    }

    public void setBakVarchar(String bakVarchar) {
        this.bakVarchar = bakVarchar;
    }

    @Override
    public String toString() {
        return "NthpaBrpTableEntity{" +
                "snId=" + snId +
                ", zoneno=" + zoneno +
                ", brno=" + brno +
                ", mbrno=" + mbrno +
                ", brnoType=" + brnoType +
                ", brnoName='" + brnoName + '\'' +
                ", status=" + status +
                ", brnoFullName='" + brnoFullName + '\'' +
                ", rmbrno=" + rmbrno +
                ", address='" + address + '\'' +
                ", zip='" + zip + '\'' +
                ", phone='" + phone + '\'' +
                ", shortnm='" + shortnm + '\'' +
                ", actBrno=" + actBrno +
                ", brnoStatus=" + brnoStatus +
                ", lastModDate=" + lastModDate +
                ", bakDecl=" + bakDecl +
                ", bakVarchar='" + bakVarchar + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NthpaBrpTableEntity that = (NthpaBrpTableEntity) o;
        return snId == that.snId &&
                brnoType == that.brnoType &&
                status == that.status &&
                brnoStatus == that.brnoStatus &&
                bakDecl == that.bakDecl &&
                Objects.equals(zoneno, that.zoneno) &&
                Objects.equals(brno, that.brno) &&
                Objects.equals(mbrno, that.mbrno) &&
                Objects.equals(brnoName, that.brnoName) &&
                Objects.equals(brnoFullName, that.brnoFullName) &&
                Objects.equals(rmbrno, that.rmbrno) &&
                Objects.equals(address, that.address) &&
                Objects.equals(zip, that.zip) &&
                Objects.equals(phone, that.phone) &&
                Objects.equals(shortnm, that.shortnm) &&
                Objects.equals(actBrno, that.actBrno) &&
                Objects.equals(lastModDate, that.lastModDate) &&
                Objects.equals(bakVarchar, that.bakVarchar);
    }

    @Override
    public int hashCode() {
        return Objects.hash(snId, zoneno, brno, mbrno, brnoType, brnoName, status, brnoFullName, rmbrno, address, zip, phone, shortnm, actBrno, brnoStatus, lastModDate, bakDecl, bakVarchar);
    }
}

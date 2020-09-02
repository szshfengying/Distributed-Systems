package icbc.utils.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "nthpa_zon_table", catalog = "")
public class NthpaZonTableEntity implements Serializable {
    private long snId;
    private int zoneno;
    private String notes;
    private byte status;
    private Date lstmodd;
    private long bakdecl;
    private String bakchar;
    private String province;

    @Override
    public String toString() {
        return "NthpaZonTableEntity{" +
                "snId=" + snId +
                ", zoneno=" + zoneno +
                ", notes='" + notes + '\'' +
                ", status=" + status +
                ", lstmodd=" + lstmodd +
                ", bakdecl=" + bakdecl +
                ", bakchar='" + bakchar + '\'' +
                '}';
    }

    @Basic
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

    @Basic
    @Column(name = "notes")
    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
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
    @Column(name = "lstmodd")
    public Date getLstmodd() {
        return lstmodd;
    }

    public void setLstmodd(Date lstmodd) {
        this.lstmodd = lstmodd;
    }

    @Basic
    @Column(name = "bakdecl")
    public long getBakdecl() {
        return bakdecl;
    }

    public void setBakdecl(long bakdecl) {
        this.bakdecl = bakdecl;
    }

    @Basic
    @Column(name = "bakchar")
    public String getBakchar() {
        return bakchar;
    }

    public void setBakchar(String bakchar) {
        this.bakchar = bakchar;
    }

    @Basic
    @Column(name = "province")
    public String getProvince() {return province; }

    public void setProvince(String province) {this.province=province; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NthpaZonTableEntity that = (NthpaZonTableEntity) o;
        return snId == that.snId &&
                zoneno == that.zoneno &&
                status == that.status &&
                bakdecl == that.bakdecl &&
                Objects.equals(notes, that.notes) &&
                Objects.equals(lstmodd, that.lstmodd) &&
                Objects.equals(bakchar, that.bakchar);
    }

    @Override
    public int hashCode() {
        return Objects.hash(snId, zoneno, notes, status, lstmodd, bakdecl, bakchar);
    }
}

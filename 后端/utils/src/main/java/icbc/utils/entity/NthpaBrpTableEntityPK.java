package icbc.utils.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class NthpaBrpTableEntityPK implements Serializable {
    private long snId;
    private int zoneno;
    private int brno;
    private int mbrno;

    @Column(name = "sn_id")
    @Id
    public long getSnId() {
        return snId;
    }

    public void setSnId(long snId) {
        this.snId = snId;
    }

    @Column(name = "zoneno")
    @Id
    public int getZoneno() {
        return zoneno;
    }

    public void setZoneno(int zoneno) {
        this.zoneno = zoneno;
    }

    @Column(name = "brno")
    @Id
    public int getBrno() {
        return brno;
    }

    public void setBrno(int brno) {
        this.brno = brno;
    }

    @Column(name = "mbrno")
    @Id
    public int getMbrno() {
        return mbrno;
    }

    public void setMbrno(int mbrno) {
        this.mbrno = mbrno;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NthpaBrpTableEntityPK that = (NthpaBrpTableEntityPK) o;
        return snId == that.snId &&
                Objects.equals(zoneno, that.zoneno) &&
                Objects.equals(brno, that.brno) &&
                Objects.equals(mbrno, that.mbrno);
    }

    @Override
    public int hashCode() {
        return Objects.hash(snId, zoneno, brno, mbrno);
    }
}

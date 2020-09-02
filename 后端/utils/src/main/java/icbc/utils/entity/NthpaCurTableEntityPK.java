package icbc.utils.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class NthpaCurTableEntityPK implements Serializable {
    private long snId;
    private int zoneno;
    private short currType;

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

    @Column(name = "curr_type")
    @Id
    public short getCurrType() {
        return currType;
    }

    public void setCurrType(short currType) {
        this.currType = currType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NthpaCurTableEntityPK that = (NthpaCurTableEntityPK) o;
        return snId == that.snId &&
                currType == that.currType &&
                Objects.equals(zoneno, that.zoneno);
    }

    @Override
    public int hashCode() {
        return Objects.hash(snId, zoneno, currType);
    }
}

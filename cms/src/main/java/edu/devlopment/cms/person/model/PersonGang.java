package edu.devlopment.cms.person.model;

import java.io.Serializable;

public class PersonGang implements Serializable {
    private Long id;

    private Long personId;

    private Long gangId;

    /**
     * 是否团伙头目 0不是，1是
     *
     * @mbg.generated
     */
    private Integer ismain;

    /**
     * 团伙性质（固定关系 ；社会关系 ID  参考拓扑关系 固定关系类型）
     *
     * @mbg.generated
     */
    private Long rId;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    public Long getGangId() {
        return gangId;
    }

    public void setGangId(Long gangId) {
        this.gangId = gangId;
    }

    public Integer getIsmain() {
        return ismain;
    }

    public void setIsmain(Integer ismain) {
        this.ismain = ismain;
    }

    public Long getrId() {
        return rId;
    }

    public void setrId(Long rId) {
        this.rId = rId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", personId=").append(personId);
        sb.append(", gangId=").append(gangId);
        sb.append(", ismain=").append(ismain);
        sb.append(", rId=").append(rId);
        sb.append("]");
        return sb.toString();
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        PersonGang other = (PersonGang) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getPersonId() == null ? other.getPersonId() == null : this.getPersonId().equals(other.getPersonId()))
            && (this.getGangId() == null ? other.getGangId() == null : this.getGangId().equals(other.getGangId()))
            && (this.getIsmain() == null ? other.getIsmain() == null : this.getIsmain().equals(other.getIsmain()))
            && (this.getrId() == null ? other.getrId() == null : this.getrId().equals(other.getrId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getPersonId() == null) ? 0 : getPersonId().hashCode());
        result = prime * result + ((getGangId() == null) ? 0 : getGangId().hashCode());
        result = prime * result + ((getIsmain() == null) ? 0 : getIsmain().hashCode());
        result = prime * result + ((getrId() == null) ? 0 : getrId().hashCode());
        return result;
    }
}
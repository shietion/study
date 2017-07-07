package edu.devlopment.cms.person.model;

import java.io.Serializable;
import java.util.Date;

public class PersonCar implements Serializable {
    private Long id;

    private Long carId;

    private String personId;

    private Date beginTime;

    private Date endTime;

    private String address;

    private Integer routeIndex;

    private Integer timeIndex;

    private Integer syntheticalIndex;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getRouteIndex() {
        return routeIndex;
    }

    public void setRouteIndex(Integer routeIndex) {
        this.routeIndex = routeIndex;
    }

    public Integer getTimeIndex() {
        return timeIndex;
    }

    public void setTimeIndex(Integer timeIndex) {
        this.timeIndex = timeIndex;
    }

    public Integer getSyntheticalIndex() {
        return syntheticalIndex;
    }

    public void setSyntheticalIndex(Integer syntheticalIndex) {
        this.syntheticalIndex = syntheticalIndex;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", carId=").append(carId);
        sb.append(", personId=").append(personId);
        sb.append(", beginTime=").append(beginTime);
        sb.append(", endTime=").append(endTime);
        sb.append(", address=").append(address);
        sb.append(", routeIndex=").append(routeIndex);
        sb.append(", timeIndex=").append(timeIndex);
        sb.append(", syntheticalIndex=").append(syntheticalIndex);
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
        PersonCar other = (PersonCar) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getCarId() == null ? other.getCarId() == null : this.getCarId().equals(other.getCarId()))
            && (this.getPersonId() == null ? other.getPersonId() == null : this.getPersonId().equals(other.getPersonId()))
            && (this.getBeginTime() == null ? other.getBeginTime() == null : this.getBeginTime().equals(other.getBeginTime()))
            && (this.getEndTime() == null ? other.getEndTime() == null : this.getEndTime().equals(other.getEndTime()))
            && (this.getAddress() == null ? other.getAddress() == null : this.getAddress().equals(other.getAddress()))
            && (this.getRouteIndex() == null ? other.getRouteIndex() == null : this.getRouteIndex().equals(other.getRouteIndex()))
            && (this.getTimeIndex() == null ? other.getTimeIndex() == null : this.getTimeIndex().equals(other.getTimeIndex()))
            && (this.getSyntheticalIndex() == null ? other.getSyntheticalIndex() == null : this.getSyntheticalIndex().equals(other.getSyntheticalIndex()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getCarId() == null) ? 0 : getCarId().hashCode());
        result = prime * result + ((getPersonId() == null) ? 0 : getPersonId().hashCode());
        result = prime * result + ((getBeginTime() == null) ? 0 : getBeginTime().hashCode());
        result = prime * result + ((getEndTime() == null) ? 0 : getEndTime().hashCode());
        result = prime * result + ((getAddress() == null) ? 0 : getAddress().hashCode());
        result = prime * result + ((getRouteIndex() == null) ? 0 : getRouteIndex().hashCode());
        result = prime * result + ((getTimeIndex() == null) ? 0 : getTimeIndex().hashCode());
        result = prime * result + ((getSyntheticalIndex() == null) ? 0 : getSyntheticalIndex().hashCode());
        return result;
    }
}
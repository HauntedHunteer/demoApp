package com.appv1.demo.Entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class VehicleInsurance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idVehicleInsurance;

    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate effectiveDate;

    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate expirationDate;

    @Column(precision=8, scale=2, nullable = false)
    private BigDecimal cost;

    @Column(length = 25, nullable = false)
    private String policyNumber;

    @Column(length = 100)
    private String description;

    @ManyToOne
    @JoinColumn(name = "InsuranceTypeFk", nullable = false)
    private InsuranceType insuranceType;

    @ManyToOne
    @JoinColumn(name = "VehicleFk", nullable = false)
    private Vehicle vehicle;

    public Integer getIdVehicleInsurance() {
        return idVehicleInsurance;
    }

    public void setIdVehicleInsurance(Integer idVehicleInsurance) {
        this.idVehicleInsurance = idVehicleInsurance;
    }

    public LocalDate getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(LocalDate effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public String getPolicyNumber() {
        return policyNumber;
    }

    public void setPolicyNumber(String policyNumber) {
        this.policyNumber = policyNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public InsuranceType getInsuranceType() {
        return insuranceType;
    }

    public void setInsuranceType(InsuranceType insuranceType) {
        this.insuranceType = insuranceType;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }
}

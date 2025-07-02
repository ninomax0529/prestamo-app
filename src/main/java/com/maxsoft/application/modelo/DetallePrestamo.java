/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.maxsoft.application.modelo;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Maximiliano
 */
@Entity
@Table(name = "detalle_prestamo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DetallePrestamo.findAll", query = "SELECT d FROM DetallePrestamo d")})
public class DetallePrestamo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigo")
    private Integer codigo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Column(name = "fecha_aplicado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAplicado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "numero_cuota")
    private int numeroCuota;
    @Basic(optional = false)
    @NotNull
    @Column(name = "valor_cuota")
    private double valorCuota;
    @Basic(optional = false)
    @NotNull
    @Column(name = "capital")
    private double capital;
    @Basic(optional = false)
    @NotNull
    @Column(name = "interes")
    private double interes;
    @Basic(optional = false)
    @NotNull
    @Column(name = "monto_pagado")
    private double montoPagado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "monto_pendiente")
    private double montoPendiente;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "concepto")
    private String concepto;
    @Basic(optional = false)
    @NotNull
    @Column(name = "estado")
    private boolean estado;
    @Column(name = "fecha_actualizacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaActualizacion;
    @JoinColumn(name = "prestamo", referencedColumnName = "codigo")
    @ManyToOne(optional = false)
    private Prestamo prestamo;

    public DetallePrestamo() {
    }

    public DetallePrestamo(Integer codigo) {
        this.codigo = codigo;
    }

    public DetallePrestamo(Integer codigo, Date fecha, int numeroCuota, double valorCuota, double capital, double interes, double montoPagado, double montoPendiente, String concepto, boolean estado) {
        this.codigo = codigo;
        this.fecha = fecha;
        this.numeroCuota = numeroCuota;
        this.valorCuota = valorCuota;
        this.capital = capital;
        this.interes = interes;
        this.montoPagado = montoPagado;
        this.montoPendiente = montoPendiente;
        this.concepto = concepto;
        this.estado = estado;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Date getFechaAplicado() {
        return fechaAplicado;
    }

    public void setFechaAplicado(Date fechaAplicado) {
        this.fechaAplicado = fechaAplicado;
    }

    public int getNumeroCuota() {
        return numeroCuota;
    }

    public void setNumeroCuota(int numeroCuota) {
        this.numeroCuota = numeroCuota;
    }

    public double getValorCuota() {
        return valorCuota;
    }

    public void setValorCuota(double valorCuota) {
        this.valorCuota = valorCuota;
    }

    public double getCapital() {
        return capital;
    }

    public void setCapital(double capital) {
        this.capital = capital;
    }

    public double getInteres() {
        return interes;
    }

    public void setInteres(double interes) {
        this.interes = interes;
    }

    public double getMontoPagado() {
        return montoPagado;
    }

    public void setMontoPagado(double montoPagado) {
        this.montoPagado = montoPagado;
    }

    public double getMontoPendiente() {
        return montoPendiente;
    }

    public void setMontoPendiente(double montoPendiente) {
        this.montoPendiente = montoPendiente;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public boolean getEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public Date getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(Date fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    public Prestamo getPrestamo() {
        return prestamo;
    }

    public void setPrestamo(Prestamo prestamo) {
        this.prestamo = prestamo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigo != null ? codigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DetallePrestamo)) {
            return false;
        }
        DetallePrestamo other = (DetallePrestamo) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.maxsoft.application.modelo.DetallePrestamo[ codigo=" + codigo + " ]";
    }
    
}

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
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 *
 * @author Maximiliano
 */
@Entity
@Table(name = "detalle_recibo_de_ingreso")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DetalleReciboDeIngreso.findAll", query = "SELECT d FROM DetalleReciboDeIngreso d")})
public class DetalleReciboDeIngreso implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigo")
    private Integer codigo;
    @Column(name = "cuota")
    private Integer cuota;
    @Column(name = "numero_cuota")
    private Integer numeroCuota;
    @Basic(optional = false)
    @NotNull
    @Column(name = "total")
    private double total;
    @Basic(optional = false)
    @NotNull
    @Column(name = "monto_pendiente")
    private double montoPendiente;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 120)
    @Column(name = "concepto")
    private String concepto;
    @JoinColumn(name = "recibo", referencedColumnName = "codigo")
    @ManyToOne(optional = false)
    private ReciboDeIngreso recibo;

    public DetalleReciboDeIngreso() {
    }

    public DetalleReciboDeIngreso(Integer codigo) {
        this.codigo = codigo;
    }

    public DetalleReciboDeIngreso(Integer codigo, double total, double montoPendiente, String concepto) {
        this.codigo = codigo;
        this.total = total;
        this.montoPendiente = montoPendiente;
        this.concepto = concepto;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public Integer getCuota() {
        return cuota;
    }

    public void setCuota(Integer cuota) {
        this.cuota = cuota;
    }

    public Integer getNumeroCuota() {
        return numeroCuota;
    }

    public void setNumeroCuota(Integer numeroCuota) {
        this.numeroCuota = numeroCuota;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
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

    public ReciboDeIngreso getRecibo() {
        return recibo;
    }

    public void setRecibo(ReciboDeIngreso recibo) {
        this.recibo = recibo;
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
        if (!(object instanceof DetalleReciboDeIngreso)) {
            return false;
        }
        DetalleReciboDeIngreso other = (DetalleReciboDeIngreso) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.maxsoft.application.modelo.DetalleReciboDeIngreso[ codigo=" + codigo + " ]";
    }
    
}

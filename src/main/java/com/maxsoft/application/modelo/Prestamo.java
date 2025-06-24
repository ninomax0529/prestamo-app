/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.maxsoft.application.modelo;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

/**
 *
 * @author Maximiliano
 */
@Entity
@Table(name = "prestamo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Prestamo.findAll", query = "SELECT p FROM Prestamo p")})
public class Prestamo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigo")
    private Integer codigo;
    @Column(name = "fecha_inicio")
    @Temporal(TemporalType.DATE)
    private Date fechaInicio;
    @Column(name = "fecha_final")
    @Temporal(TemporalType.DATE)
    private Date fechaFinal;
    @Size(max = 30)
    @Column(name = "nombre_tipo_prestamo")
    private String nombreTipoPrestamo;
    @Size(max = 15)
    @Column(name = "nombre_periodo")
    private String nombrePeriodo;
    @Column(name = "cantidad_periodo")
    private Integer cantidadPeriodo;
    @Size(max = 90)
    @Column(name = "nombre_cliente")
    private String nombreCliente;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "tasa_de_intere")
    private Double tasaDeIntere;
    @Column(name = "monto_prestado")
    private Double montoPrestado;
    @Column(name = "monto_intere")
    private Double montoIntere;
    @Column(name = "total")
    private Double total;
    @Column(name = "total_pagado")
    private Double totalPagado;
    @Column(name = "total_pendiente")
    private Double totalPendiente;
    @Column(name = "monto_cuota")
    private Double montoCuota;
    @Size(max = 90)
    @Column(name = "creado_por")
    private String creadoPor;
    @Column(name = "fecha_creacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Column(name = "fecha_actualizacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaActualizacion;
    @Size(max = 90)
    @Column(name = "actualizado_por")
    private String actualizadoPor;
    @Basic(optional = false)
    @NotNull
    @Column(name = "anulado")
    private boolean anulado;
    @Size(max = 90)
    @Column(name = "anulado_por")
    private String anuladoPor;
    @Column(name = "fecha_anulado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAnulado;
    @Column(name = "fecha_primer_pago")
    @Temporal(TemporalType.DATE)
    private Date fechaPrimerPago;
    @JoinColumn(name = "cliente", referencedColumnName = "codigo")
    @ManyToOne
    private Cliente cliente;
    @JoinColumn(name = "periodo", referencedColumnName = "codigo")
    @ManyToOne
    private Periodo periodo;
    @JoinColumn(name = "tipo_prestamo", referencedColumnName = "codigo")
    @ManyToOne
    private TipoPrestamo tipoPrestamo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "prestamo")
    private Collection<ReciboDeIngreso> reciboDeIngresoCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "prestamo")
    private Collection<DetallePrestamo> detallePrestamoCollection;

    public Prestamo() {
    }

    public Prestamo(Integer codigo) {
        this.codigo = codigo;
    }

    public Prestamo(Integer codigo, boolean anulado) {
        this.codigo = codigo;
        this.anulado = anulado;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(Date fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public String getNombreTipoPrestamo() {
        return nombreTipoPrestamo;
    }

    public void setNombreTipoPrestamo(String nombreTipoPrestamo) {
        this.nombreTipoPrestamo = nombreTipoPrestamo;
    }

    public String getNombrePeriodo() {
        return nombrePeriodo;
    }

    public void setNombrePeriodo(String nombrePeriodo) {
        this.nombrePeriodo = nombrePeriodo;
    }

    public Integer getCantidadPeriodo() {
        return cantidadPeriodo;
    }

    public void setCantidadPeriodo(Integer cantidadPeriodo) {
        this.cantidadPeriodo = cantidadPeriodo;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public Double getTasaDeIntere() {
        return tasaDeIntere;
    }

    public void setTasaDeIntere(Double tasaDeIntere) {
        this.tasaDeIntere = tasaDeIntere;
    }

    public Double getMontoPrestado() {
        return montoPrestado;
    }

    public void setMontoPrestado(Double montoPrestado) {
        this.montoPrestado = montoPrestado;
    }

    public Double getMontoIntere() {
        return montoIntere;
    }

    public void setMontoIntere(Double montoIntere) {
        this.montoIntere = montoIntere;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Double getTotalPagado() {
        return totalPagado;
    }

    public void setTotalPagado(Double totalPagado) {
        this.totalPagado = totalPagado;
    }

    public Double getTotalPendiente() {
        return totalPendiente;
    }

    public void setTotalPendiente(Double totalPendiente) {
        this.totalPendiente = totalPendiente;
    }

    public Double getMontoCuota() {
        return montoCuota;
    }

    public void setMontoCuota(Double montoCuota) {
        this.montoCuota = montoCuota;
    }

    public String getCreadoPor() {
        return creadoPor;
    }

    public void setCreadoPor(String creadoPor) {
        this.creadoPor = creadoPor;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(Date fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    public String getActualizadoPor() {
        return actualizadoPor;
    }

    public void setActualizadoPor(String actualizadoPor) {
        this.actualizadoPor = actualizadoPor;
    }

    public boolean getAnulado() {
        return anulado;
    }

    public void setAnulado(boolean anulado) {
        this.anulado = anulado;
    }

    public String getAnuladoPor() {
        return anuladoPor;
    }

    public void setAnuladoPor(String anuladoPor) {
        this.anuladoPor = anuladoPor;
    }

    public Date getFechaAnulado() {
        return fechaAnulado;
    }

    public void setFechaAnulado(Date fechaAnulado) {
        this.fechaAnulado = fechaAnulado;
    }

    public Date getFechaPrimerPago() {
        return fechaPrimerPago;
    }

    public void setFechaPrimerPago(Date fechaPrimerPago) {
        this.fechaPrimerPago = fechaPrimerPago;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Periodo getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Periodo periodo) {
        this.periodo = periodo;
    }

    public TipoPrestamo getTipoPrestamo() {
        return tipoPrestamo;
    }

    public void setTipoPrestamo(TipoPrestamo tipoPrestamo) {
        this.tipoPrestamo = tipoPrestamo;
    }

    @XmlTransient
    public Collection<ReciboDeIngreso> getReciboDeIngresoCollection() {
        return reciboDeIngresoCollection;
    }

    public void setReciboDeIngresoCollection(Collection<ReciboDeIngreso> reciboDeIngresoCollection) {
        this.reciboDeIngresoCollection = reciboDeIngresoCollection;
    }

    @XmlTransient
    public Collection<DetallePrestamo> getDetallePrestamoCollection() {
        return detallePrestamoCollection;
    }

    public void setDetallePrestamoCollection(Collection<DetallePrestamo> detallePrestamoCollection) {
        this.detallePrestamoCollection = detallePrestamoCollection;
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
        if (!(object instanceof Prestamo)) {
            return false;
        }
        Prestamo other = (Prestamo) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.maxsoft.application.modelo.Prestamo[ codigo=" + codigo + " ]";
    }
    
}

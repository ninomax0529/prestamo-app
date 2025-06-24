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
@Table(name = "recibo_de_ingreso")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ReciboDeIngreso.findAll", query = "SELECT r FROM ReciboDeIngreso r")})
public class ReciboDeIngreso implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigo")
    private Integer codigo;
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Size(max = 90)
    @Column(name = "nombre_cliente")
    private String nombreCliente;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "total")
    private Double total;
    @Column(name = "monto_pendiente")
    private Double montoPendiente;
    @Size(max = 90)
    @Column(name = "creado_por")
    private String creadoPor;
    @Column(name = "fecha_creacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
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
    @Size(max = 150)
    @Column(name = "descripcion_pago")
    private String descripcionPago;
    @JoinColumn(name = "cliente", referencedColumnName = "codigo")
    @ManyToOne(optional = false)
    private Cliente cliente;
    @JoinColumn(name = "prestamo", referencedColumnName = "codigo")
    @ManyToOne(optional = false)
    private Prestamo prestamo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recibo")
    private Collection<DetalleReciboDeIngreso> detalleReciboDeIngresoCollection;

    public ReciboDeIngreso() {
    }

    public ReciboDeIngreso(Integer codigo) {
        this.codigo = codigo;
    }

    public ReciboDeIngreso(Integer codigo, boolean anulado) {
        this.codigo = codigo;
        this.anulado = anulado;
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

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Double getMontoPendiente() {
        return montoPendiente;
    }

    public void setMontoPendiente(Double montoPendiente) {
        this.montoPendiente = montoPendiente;
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

    public String getDescripcionPago() {
        return descripcionPago;
    }

    public void setDescripcionPago(String descripcionPago) {
        this.descripcionPago = descripcionPago;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Prestamo getPrestamo() {
        return prestamo;
    }

    public void setPrestamo(Prestamo prestamo) {
        this.prestamo = prestamo;
    }

    @XmlTransient
    public Collection<DetalleReciboDeIngreso> getDetalleReciboDeIngresoCollection() {
        return detalleReciboDeIngresoCollection;
    }

    public void setDetalleReciboDeIngresoCollection(Collection<DetalleReciboDeIngreso> detalleReciboDeIngresoCollection) {
        this.detalleReciboDeIngresoCollection = detalleReciboDeIngresoCollection;
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
        if (!(object instanceof ReciboDeIngreso)) {
            return false;
        }
        ReciboDeIngreso other = (ReciboDeIngreso) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.maxsoft.application.modelo.ReciboDeIngreso[ codigo=" + codigo + " ]";
    }
    
}

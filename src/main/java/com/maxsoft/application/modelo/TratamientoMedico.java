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
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author maximilianoalmonte
 */
@Entity
@Table(name = "tratamiento_medico")
@NamedQueries({
    @NamedQuery(name = "TratamientoMedico.findAll", query = "SELECT t FROM TratamientoMedico t")})
public class TratamientoMedico implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigo")
    private Integer codigo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "nombre_medicamento")
    private String nombreMedicamento;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_bebida")
    @Temporal(TemporalType.DATE)
    private Date fechaBebida;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "dia")
    private String dia;
    @Size(max = 10)
    @Column(name = "mes")
    private String mes;
    @Size(max = 100)
    @Column(name = "anio")
    private String anio;
    @Basic(optional = false)
    @NotNull
    @Column(name = "hora_bebida")
    @Temporal(TemporalType.TIME)
    private Date horaBebida;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "hora")
    private String hora;
    @JoinColumn(name = "medicamento", referencedColumnName = "codigo")
    @ManyToOne(optional = false)
    private Medicamento medicamento;

    public TratamientoMedico() {
    }

    public TratamientoMedico(Integer codigo) {
        this.codigo = codigo;
    }

    public TratamientoMedico(Integer codigo, String nombreMedicamento, Date fechaBebida, String dia, Date horaBebida, String hora) {
        this.codigo = codigo;
        this.nombreMedicamento = nombreMedicamento;
        this.fechaBebida = fechaBebida;
        this.dia = dia;
        this.horaBebida = horaBebida;
        this.hora = hora;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getNombreMedicamento() {
        return nombreMedicamento;
    }

    public void setNombreMedicamento(String nombreMedicamento) {
        this.nombreMedicamento = nombreMedicamento;
    }

    public Date getFechaBebida() {
        return fechaBebida;
    }

    public void setFechaBebida(Date fechaBebida) {
        this.fechaBebida = fechaBebida;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public String getAnio() {
        return anio;
    }

    public void setAnio(String anio) {
        this.anio = anio;
    }

    public Date getHoraBebida() {
        return horaBebida;
    }

    public void setHoraBebida(Date horaBebida) {
        this.horaBebida = horaBebida;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public Medicamento getMedicamento() {
        return medicamento;
    }

    public void setMedicamento(Medicamento medicamento) {
        this.medicamento = medicamento;
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
        if (!(object instanceof TratamientoMedico)) {
            return false;
        }
        TratamientoMedico other = (TratamientoMedico) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.maxsoft.application.modelo.TratamientoMedico[ codigo=" + codigo + " ]";
    }
    
}

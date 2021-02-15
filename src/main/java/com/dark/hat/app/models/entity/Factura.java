package com.dark.hat.app.models.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="facturas")
public class Factura implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter @Setter private Long id;
	
	@NotEmpty
	@Getter	@Setter private String descripcion;

	@Getter	@Setter private String observacion;

	@Temporal(TemporalType.DATE)
	@Column(name="create_at")
	@Getter	@Setter private Date createAt;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@Getter(onMethod_={@XmlTransient})
	@Setter()
	@JsonBackReference
	private Cliente cliente;
	
	@OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	@JoinColumn(name = "factura_id")
	@Getter	@Setter	private List<ItemFactura> items;
	
	public void agregarItemFactura(ItemFactura item) {
		this.items.add(item);
	}

	public Factura() {
		this.items = new ArrayList<>();
	}
	
	public Double getTotal() {
		Double total = 0.0;
		int size = items.size();
		for (int i = 0; i < size; i++) {
			total += items.get(i).calcularImporte();
		}
		return total;
	}
	
	@PrePersist
	public void prepersist() {
		createAt = new Date();
	}
	
	
}

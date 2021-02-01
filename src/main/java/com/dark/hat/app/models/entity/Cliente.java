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
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;
import lombok.Data;
import lombok.ToString;

@Data
@Entity
@Table(name="clientes")
public class Cliente implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ToString.Exclude
	private Long id;
	
	@NotEmpty
	private String nombre;
	
	@NotEmpty
	private String apellido;
	
	@NotEmpty
	@Email
	@ToString.Exclude
	private String email;
	
	//La anotacion @Temporal define el tipo de dato date, time, timestamp
	@Column(name="create_at")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@NotNull
	@ToString.Exclude
	private Date createAt;
	
	@ToString.Exclude
	private String foto;
	
	@OneToMany(mappedBy = "cliente",fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@ToString.Exclude
	private List<Factura> facturas;
	
	/*@PrePersist
	public void prePersist() {
		this.createAt = new Date();
	}*/
	
	public void agregarFactura(Factura factura) {
		facturas.add(factura);
	}

	public Cliente() {
		facturas = new ArrayList<>();
	}
	
	@PrePersist
	public void prepersist() {
		createAt = new Date();
	}
	
}

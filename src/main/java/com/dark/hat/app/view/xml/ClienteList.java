package com.dark.hat.app.view.xml;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import com.dark.hat.app.models.entity.Cliente;
import lombok.Getter;

@Getter
@XmlRootElement(name="clientes")
public class ClienteList {
	
	@XmlElement(name = "cliente")
	public List<Cliente> clientes;
	
	public ClienteList() {
	}
	
	public ClienteList(List<Cliente> clientes) {
		this.clientes = clientes;
	}
}

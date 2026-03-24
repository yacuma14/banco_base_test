package banco.test.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "Pago")
public class PagoEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idPago", unique = true, nullable = false)
	private Long idPago;

    @Column(name = "concepto")
	private String concepto;

    @Column(name = "cantidad_productos")
	private Integer cantidadProductos;

    @Column(name = "realiza_pago")
	private String realizaPago;

    @Column(name = "recibe_pago")
	private String recibePago;

    @Column(name = "monto")
	private Float  monto;

    @Column(name = "status_Pago")
	private String  statusPago;

}

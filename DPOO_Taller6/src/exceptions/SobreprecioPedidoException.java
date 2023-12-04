package exceptions;

public class SobreprecioPedidoException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6915482760075424415L;
	
	@Override
	public String getMessage() {
		return "El pedido sobrepasa los $150.000, el producto no pudo ser agregado";
	}
	
}

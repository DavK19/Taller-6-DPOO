package exceptions;

public class ProductoRepetidoException extends HamburguesaException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3376185900506779123L;
	
	public ProductoRepetidoException(String producto) {
		super(producto);
	}
	
	@Override
	public String getMessage() {
		return "Hubo un producto repetido: " + super.getMessage();
	}

}

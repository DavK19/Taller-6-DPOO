package exceptions;

public class IngredienteRepetidoException extends HamburguesaException{

	public IngredienteRepetidoException(String ingrediente) {
		super(ingrediente);
	}

	
	@Override
	public String getMessage() {
		return "Hubo un ingrediente repetido: " + super.getMessage();
	}
	/**
	 * 
	 */
	/**
	 * 
	 */
	private static final long serialVersionUID = -5070607046643293298L;

}

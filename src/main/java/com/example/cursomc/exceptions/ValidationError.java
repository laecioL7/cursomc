package com.example.cursomc.exceptions;

import java.util.ArrayList;
import java.util.List;

public class ValidationError extends StandardError
{
	private static final long serialVersionUID = 1L;
	
	private List<FieldMessage> listErrors = new ArrayList<FieldMessage>();
	
	public ValidationError(Integer status, String msg, Long timeStamp)
	{
		super(status, msg, timeStamp);
	}

	public List<FieldMessage> getErrors()
	{
		return listErrors;
	}

	/**adiciona um objeto de dados personalizado para exibir o erro*/
	public void addError(String sFieldName, String sMessage)
	{
		this.listErrors.add(new FieldMessage(sFieldName,sMessage));
	}
}

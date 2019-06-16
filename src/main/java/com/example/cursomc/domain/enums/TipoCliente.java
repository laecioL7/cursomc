package com.example.cursomc.domain.enums;

public enum TipoCliente
{
	PESSOAFISICA(1, "Pessoa Física"), PESSOAJURIDICA(2, "Pessoa Jurídica");

	private int cod;
	private String descricao;

	private TipoCliente(int cod, String descricao)
	{
		this.cod = cod;
		this.descricao = descricao;
	}

	public int getCod()
	{
		return cod;
	}

	public String getDescricao()
	{
		return descricao;
	}

	public static TipoCliente toEnum(Integer cod)
	{
		//se o codigo for nulo
		if (cod == null)
		{
			return null;
		}

		//percorre a lista da enum e retorna se achar a enum correspondente ao codigo
		for (TipoCliente tipoCliente : TipoCliente.values())
		{
			if (cod.equals(tipoCliente.getCod()))
			{
				return tipoCliente;
			}
		}

		//se não lança uma excessão com mensagem
		throw new IllegalArgumentException("Id inválido: " + cod);
	}

}

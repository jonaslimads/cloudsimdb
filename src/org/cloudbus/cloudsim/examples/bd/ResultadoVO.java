package org.cloudbus.cloudsim.examples.bd;

import org.cloudbus.cloudsim.examples.bd.ProVMBD.EnumTipoVM;

/**
 * @author Humberto Lima
 * 
 */
public class ResultadoVO
{

	// Atributos
	private Integer qtdVMs;

	private Double tempoSimulacao;

	private Double prcViolacaoSLA;

	private Double custoRecursos;

	private Double custoPorHora;

	private Double valorMulta;

	private EnumTipoVM enumTipoVM;

	private Long totalRegistro;

	public ResultadoVO()
	{
		super();
	}

	public ResultadoVO(final Integer qtdVMs, final Double tempoSimulacao,
			final Double prcViolacaoSLA, final Double custoRecursos,
			final EnumTipoVM enumTipoVM, final Double valorMulta,
			final Double custoPorHora)
	{
		this.qtdVMs = qtdVMs;
		this.tempoSimulacao = tempoSimulacao;
		this.prcViolacaoSLA = prcViolacaoSLA;
		this.custoRecursos = custoRecursos;
		this.enumTipoVM = enumTipoVM;
		this.valorMulta = valorMulta;
		this.custoPorHora = custoPorHora;
	}

	public ResultadoVO(final Integer qtdVMs, final Double tempoSimulacao,
			final Double prcViolacaoSLA, final Double custoRecursos,
			final EnumTipoVM enumTipoVM, final Double valorMulta,
			final Double custoPorHora, final Long totalRegistro)
	{
		this.qtdVMs = qtdVMs;
		this.tempoSimulacao = tempoSimulacao;
		this.prcViolacaoSLA = prcViolacaoSLA;
		this.custoRecursos = custoRecursos;
		this.enumTipoVM = enumTipoVM;
		this.valorMulta = valorMulta;
		this.custoPorHora = custoPorHora;
		this.totalRegistro = totalRegistro;
	}

	public Integer getQtdVMs()
	{
		return qtdVMs;
	}

	public Double getTempoSimulacao()
	{
		return tempoSimulacao;
	}

	public Double getPrcViolacaoSLA()
	{
		return prcViolacaoSLA;
	}

	public Double getCustoRecursos()
	{
		return custoRecursos;
	}

	public Double getCustoTotal()
	{
		return custoRecursos + valorMulta;
	}

	/**
	 * 
	 * @author Humberto Lima[humbertolimaa@gmail.com] 16/11/2011 - 19:55:40.
	 * @return
	 */
	public String getValorDebitoFormatado()
	{

		return String.format("Custo recursos Data Center (US$): %.3f /h"
				+ "    Custos Total (US$): %.3f", +custoPorHora, getCustoTotal());
	}

	public String getPrcViolacaoSlaFormatado()
	{
		return String.format("Percentual violacao SLA: %.2f%%", prcViolacaoSLA);
	}

	/**
	 * 
	 * @author Humberto Lima[humbertolimaa@gmail.com] 16/11/2011 - 19:55:40.
	 * @return
	 */
	public String getValorMultaFormatado()
	{
		return String.format("Valor Receita (US$): %.3f"
				+ "                     Valor multa violacao (US$): %.3f",
				getReceita(), valorMulta);

	}

	public EnumTipoVM getEnumTipoVM()
	{
		return enumTipoVM;
	}

	public Double getValorMulta()
	{
		return valorMulta;
	}

	public Double getCustoPorHora()
	{
		return custoPorHora;
	}

	public Double getReceita()
	{
		return custoRecursos * 2;
	}

	public Long getTotalRegistro()
	{
		return totalRegistro;
	}
}

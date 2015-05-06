package org.cloudbus.cloudsim.examples.bd;

import org.cloudbus.cloudsim.examples.bd.ProVMBD.EnumTipoVM;

/**
 * @author Humberto Lima
 * 
 */
public class SimulacaoVO
{

	// Atributos
	private Integer cloudletID;

	private String status;

	private String vmID;

	private String totalRegistro;

	private String totalProcessadosDentroSLA;

	private String totalProcessadosForaSLA;

	private String tempoProcessamento;

	private EnumTipoVM enumTipoVM;

	public SimulacaoVO()
	{
		super();
	}

	public Integer getCloudletID()
	{
		return cloudletID;
	}

	public void setCloudletID(Integer cloudletID)
	{
		this.cloudletID = cloudletID;
	}

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	public String getVmID()
	{
		return vmID;
	}

	public void setVmID(String vmID)
	{
		this.vmID = vmID;
	}

	public String getTotalRegistro()
	{
		return totalRegistro;
	}

	public void setTotalRegistro(String totalRegistro)
	{
		this.totalRegistro = totalRegistro;
	}

	public String getTotalProcessadosDentroSLA()
	{
		return totalProcessadosDentroSLA;
	}

	public void setTotalProcessadosDentroSLA(String totalProcessadosDentroSLA)
	{
		this.totalProcessadosDentroSLA = totalProcessadosDentroSLA;
	}

	public String getTempoProcessamento()
	{
		return tempoProcessamento;
	}

	public void setTempoProcessamento(String tempoProcessamento)
	{
		this.tempoProcessamento = tempoProcessamento;
	}

	public String getTotalProcessadoForaSLA()
	{
		return totalProcessadosForaSLA;
	}

	public void setTotalProcessadoForaSLA(String totalProcessadoEmTempoViolacao)
	{
		this.totalProcessadosForaSLA = totalProcessadoEmTempoViolacao;
	}

	public EnumTipoVM getEnumTipoVM()
	{
		return enumTipoVM;
	}

	public void setEnumTipoVM(EnumTipoVM enumTipoVM)
	{
		this.enumTipoVM = enumTipoVM;
	}

}

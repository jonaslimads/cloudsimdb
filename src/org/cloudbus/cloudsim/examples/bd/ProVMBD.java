/*
 * Title: CloudSim Toolkit Description: CloudSim (Cloud Simulation) Toolkit for
 * Modeling and Simulation of Clouds Licence: GPL -
 * http://www.gnu.org/copyleft/gpl.html
 * 
 * Copyright (c) 2009, The University of Melbourne, Australia
 */
package org.cloudbus.cloudsim.examples.bd;

import java.awt.BorderLayout;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.swing.JFrame;

import org.cloudbus.cloudsim.Cloudlet;
import org.cloudbus.cloudsim.CloudletSchedulerTimeShared;
import org.cloudbus.cloudsim.Datacenter;
import org.cloudbus.cloudsim.DatacenterBroker;
import org.cloudbus.cloudsim.DatacenterCharacteristics;
import org.cloudbus.cloudsim.Host;
import org.cloudbus.cloudsim.Log;
import org.cloudbus.cloudsim.Pe;
import org.cloudbus.cloudsim.Storage;
import org.cloudbus.cloudsim.UtilizationModelFull;
import org.cloudbus.cloudsim.Vm;
import org.cloudbus.cloudsim.VmAllocationPolicySimple;
import org.cloudbus.cloudsim.VmSchedulerTimeShared;
import org.cloudbus.cloudsim.core.CloudSim;
import org.cloudbus.cloudsim.provisioners.BwProvisionerSimple;
import org.cloudbus.cloudsim.provisioners.PeProvisionerSimple;
import org.cloudbus.cloudsim.provisioners.RamProvisionerSimple;

/**
 * Utilizacao do framework CloudSim para simulacao de comprimento de SLA
 * referente tempo de execucao de consulta em BD, bem como provisionamento de
 * recursos "VM" de maneira dinamica.
 */
public class ProVMBD extends JFrame
{

	/**
	 * @author Humberto Lima 15/10/2011 - 08:54:36.
	 */
	private static final long serialVersionUID = -2428815928443211238L;

	/** Lista de cloudlet. */
	private static List<Cloudlet> cloudletList;

	/** Lista de vms. */
	private static List<Vm> vmList;

	/** Numero de vms */
	private static int numeroVms;

	/** Numero de cloudlets */
	private static int numeroCloudlets;

	/** **/
	private static long totalRegistroBD; // 1s em 10000 regps

	/** Qtd. de Registros processados por VM a cada segundo */
	private static int slaps;

	private static double prcSlaViolacao;

	/** Custo por hora de uma instância Small **/
	private static double CUSTO_POR_HORA_SMALL = 0.085;

	/** Custo por hora de uma instância Large **/
	private static double CUSTO_POR_HORA_LARGE = 0.34;

	/** Custo por hora de uma instância Extra **/
	private static double CUSTO_POR_HORA_EXTRA = 0.68;

	public static int comprimentoTela = 1023;

	public static int alturaTela = 700;

	/** panel de cadastro de acao **/
	private SimulaPanel cadastroPanel = null;

	/** atributo que representa a tela principal para o usuario **/
	private static final ProVMBD proVMDB = new ProVMBD();

	private static Datacenter datacenter0;

	private static List<Long> lstTotalRegistroPorCloudlet;

	private static Map<Long, Map<Integer, ResultadoVO>> mapResultadosQtdVms;

	private static SortedMap<EnumTipoVM, ResultadoVO> mapResultadosCombinacaoVms;

	public enum EnumTipoVM
	{
		NENHUM, SMALL, LARGE, EXTRA
	}

	/**
	 * Construtor padrao
	 */
	private ProVMBD()
	{
		super();

		initialize();
	}

	/**
	 * Inicializa as opcao da tela principal
	 * 
	 * @return void
	 */
	private void initialize()
	{
		// define os parametros da tela
		this.setSize(comprimentoTela, alturaTela);
		setTitle("CloudSimBD - Provisionamento de Vms para Banco de Dados");
		setLayout(new BorderLayout());

		// obtem e adiciona a tela uma instancia do panel de controle
		setContentPane(getCadastroPanel());

	}

	/**
	 * Creates the datacenter.
	 * 
	 * @param name
	 *          the name
	 * 
	 * @return the datacenter
	 */
	private static Datacenter createDatacenter(final String name,
			final EnumTipoVM enumTipoVM)
	{

		// Here are the steps needed to create a PowerDatacenter:
		// 1. We need to create a list to store
		// our machine
		final List<Host> hostList = new ArrayList<Host>();

		// 2. A Machine contains one or more PEs or CPUs/Cores.
		// In this example, it will have only one core.
		final List<Pe> peList = new ArrayList<Pe>();

		final int mips = 1000000;

		// 4. Create Host with its id and list of PEs and add them to the list
		// of machines
		final int hostId = 0;
		final int ram = 3000000; // host memory (MB)
		final long storage = 10000000; // host storage
		final int bw = 100000;

		// 5. Create a DatacenterCharacteristics object that stores the
		// properties of a data center: architecture, OS, list of
		// Machines, allocation policy: time- or space-shared, time zone
		// and its price (G$/Pe time unit).
		final String arch = "x86"; // system architecture
		final String os = "Linux"; // operating system
		final String vmm = "Xen";
		final double time_zone = 10.0; // time zone this resource located

		final double cost;

		switch (enumTipoVM)
		{
			case SMALL:
				cost = CUSTO_POR_HORA_SMALL; // Custo por hora de uma instancia Small
				// 3. Create PEs and add these into a list.
				peList.add(new Pe(0, new PeProvisionerSimple(mips))); // need to store
				// Pe id
				// and MIPS Rating
				break;
			case LARGE:
				cost = CUSTO_POR_HORA_LARGE; // Custo por hora de uma instancia Large
				// 3. Create PEs and add these into a list.
				peList.add(new Pe(0, new PeProvisionerSimple(mips))); // need to store
				// Pe id and MIPS Rating
				peList.add(new Pe(1, new PeProvisionerSimple(mips))); // need to
				// store Pe id and MIPS Rating
				break;
			case EXTRA:
				cost = CUSTO_POR_HORA_EXTRA; // Custo por hora de uma instancia Extra
				// 3. Create PEs and add these into a list.
				peList.add(new Pe(0, new PeProvisionerSimple(mips))); // need to store
				// Pe id and MIPS Rating
				peList.add(new Pe(1, new PeProvisionerSimple(mips))); // need to store
				// Pe id and MIPS Rating
				// 3. Create PEs and add these into a list.
				peList.add(new Pe(2, new PeProvisionerSimple(mips))); // need to store
				// Pe id and MIPS Rating
				peList.add(new Pe(3, new PeProvisionerSimple(mips))); // need to store
				// Pe id and MIPS Rating
				break;

			default:
				cost = CUSTO_POR_HORA_SMALL; // Custo por hora de uma instancia Small

				break;
		}

		hostList.add(new Host(hostId, new RamProvisionerSimple(ram),
				new BwProvisionerSimple(bw), storage, peList,
				new VmSchedulerTimeShared(peList))); // This is our machine

		final double costPerMem = 0.05; // the cost of using memory in this resource
		final double costPerStorage = 0.001; // the cost of using storage in this
		// resource
		final double costPerBw = 0.0; // the cost of using bw in this resource
		final LinkedList<Storage> storageList = new LinkedList<Storage>(); // we are
		// not adding SAN devices by now

		final DatacenterCharacteristics characteristics = new DatacenterCharacteristics(
				arch, os, vmm, hostList, time_zone, cost, costPerMem, costPerStorage,
				costPerBw);

		// 6. Finally, we need to create a PowerDatacenter object.
		Datacenter datacenter = null;
		try
		{
			datacenter = new Datacenter(name, characteristics,
					new VmAllocationPolicySimple(hostList), storageList, 0);
		}
		catch (final Exception e)
		{
			e.printStackTrace();
		}

		return datacenter;
	}

	// We strongly encourage users to develop their own broker policies, to
	// submit vms and cloudlets according
	// to the specific rules of the simulated scenario
	/**
	 * Creates the broker.
	 * 
	 * @return the datacenter broker
	 */
	private static DatacenterBroker createBroker(final String id)
	{
		DatacenterBroker broker = null;
		try
		{
			broker = new DatacenterBroker(id);
		}
		catch (final Exception e)
		{
			e.printStackTrace();
			return null;
		}
		return broker;
	}

	private static List<Vm> createVM(final int brokerId,
			final EnumTipoVM enumTipoVM)
	{
		final List<Vm> vms = new ArrayList<Vm>();

		// 1. descricao dos atributos
		final long size; // Armazenamento da instancia (MB)
		final int mips; // MIPS de cada CPU
		int mipsTotal;
		final int ram; // Memoria RAM da instancia (MB)
		final long bw; // Largura de banda
		final String vmm; // VMM name
		final int pesNumber; // numero de CPUs da instancia

		// 2. identifica o tipo de instancia a ser criada
		switch (enumTipoVM)
		{
			case SMALL:

				// descrição da VM do tipo Small
				size = 160000; // Armazenamento da instancia (MB)
				mips = 2000; // MIPS de cada CPU
				ram = 1700; // Memoria RAM da instancia (MB)
				bw = 1000; // Largura de banda
				vmm = "Xen"; // VMM name
				pesNumber = 1; // numero de cpus da instancia

				break;

			case LARGE:

				// descrição da VM do tipo Large
				size = 850000; // Armazenamento da instancia (MB)
				mips = 2000; // MIPSRating
				ram = 7500; // Memoria RAM da instancia (MB)
				bw = 1000; // Largura de banda
				vmm = "Xen"; // VMM name
				pesNumber = 2; // numero de cpus da instancia

				break;

			case EXTRA:

				// descrição da VM do tipo Extra Large
				size = 1690000; // Armazenamento da instancia (MB)
				mips = 2000; // MIPS de cada CPU
				ram = 15000; // vm memory (MB)
				bw = 1000; // bandwidth
				vmm = "Xen"; // VMM name
				pesNumber = 4; // numero de cpus da instancia

				break;

			default:

				// descrição da VM do tipo Small
				size = 160000; // Armazenamento da instancia (MB)
				mips = 2000; // MIPS de cada CPU
				ram = 1700; // Memoria RAM da instancia (MB)
				bw = 1000; // Largura de banda
				vmm = "Xen"; // VMM name
				pesNumber = 1; // numero de cpus da instancia

				break;
		}

		// 3. criar as instancias
		for (int i = 0; i < numeroVms; i++)
		{
			mipsTotal = mips * pesNumber;

			final Vm vm = new Vm(i, brokerId, mipsTotal, pesNumber, ram, bw, size,
					vmm, new CloudletSchedulerTimeShared());

			vms.add(vm);

		}

		return vms;
	}

	private static List<Cloudlet> createCloudlet(final int brokerId)
	{

		final List<Cloudlet> list = new ArrayList<Cloudlet>();

		// final long length = 150000; // 10 min on 250 MIPS

		final long fileSize = 300;
		final long outputSize = 300;
		final int pesNumber = 1;

		final int mipsVm = (int) vmList.get(0).getMips();

		final double tmpTotalProcessamentoEmSeg = NumberUtils.divide(
				totalRegistroBD, mipsVm, 15, 3);

		final long capacidadeProcessamentoPorVM = mipsVm * slaps;

		final UtilizationModelFull utilizationModelFull = new UtilizationModelFull();

		for (int i = 0; i < numeroCloudlets; i++)
		{

			final long totalRegistro = getTotalRegistroPorCloudlet(i,
					tmpTotalProcessamentoEmSeg, capacidadeProcessamentoPorVM);

			final Cloudlet cloudlet = new Cloudlet(i, totalRegistro, pesNumber,
					fileSize, outputSize, utilizationModelFull, utilizationModelFull,
					utilizationModelFull);

			cloudlet.setUserId(brokerId);
			cloudlet.setVmId(i);
			list.add(cloudlet);
		}

		lstTotalRegistroPorCloudlet = null;

		return list;
	}

	private static void calculcarPercViolacaoSLA(final double tempoTotalSimulacao)
	{

		// 1. verifica se houve violacao
		if ( tempoTotalSimulacao <= slaps )
		{
			prcSlaViolacao = 0;
		}
		else
		{
			// 2. efetua o calcula da violacao
			final double tempoViolado = (tempoTotalSimulacao - slaps) * 100;

			prcSlaViolacao = NumberUtils.divide(tempoViolado, tempoTotalSimulacao,
					15, 2);

		}

	}

	private static Long calculcarQtdProcessadaNoTempoViolacaoSLA(
			final Long totalProcessado)
	{

		Long totalProcessadosEmTempoViolacao;

		if ( prcSlaViolacao != 0.0 )
		{

			final double prc = NumberUtils.divide(prcSlaViolacao, 100, 15, 4)
					.doubleValue();

			totalProcessadosEmTempoViolacao = NumberUtils.multiplicar(
					totalProcessado, prc, 15, 0).longValue();
		}
		else
		{
			totalProcessadosEmTempoViolacao = 0L;
		}

		return totalProcessadosEmTempoViolacao;
	}

	private static Double calculcarValorMulta(final Double custoPorHora)
	{

		Double valorMulta;

		// 1. identifica se houve violacao
		if ( prcSlaViolacao == 0.0 )
		{

			valorMulta = 0D;
		}
		else
		{

			// 2. calcula o valor da multa
			final double prc = NumberUtils.divide(prcSlaViolacao, 100, 15, 4)
					.doubleValue();

			valorMulta = prc * custoPorHora * 2;

		}

		return valorMulta;
	}

	private static long getTotalRegistroPorCloudlet(final int i,
			final double tmpTotalProcessamentoEmSeg,
			final long capacidadeProcessamentoPorVM)
	{

		final long totalRegistroPorCloudlet;

		if ( lstTotalRegistroPorCloudlet == null )
		{
			lstTotalRegistroPorCloudlet = new ArrayList<Long>();

			final Long registroPorCloudlet = NumberUtils.dividirArredondarAMaior(
					totalRegistroBD, numeroVms, 15).longValue();

			Long totalRequisicao = 0L;

			for (int j = 0; j < numeroVms; j++)
			{
				totalRequisicao = totalRequisicao + registroPorCloudlet;

				if ( totalRequisicao > totalRegistroBD )
				{
					lstTotalRegistroPorCloudlet.add(registroPorCloudlet
							- (totalRequisicao - totalRegistroBD));
				}
				else
				{
					lstTotalRegistroPorCloudlet.add(registroPorCloudlet);
				}
			}
		}

		totalRegistroPorCloudlet = lstTotalRegistroPorCloudlet.get(i);

		return totalRegistroPorCloudlet;
	}

	/**
	 * Prints the Cloudlet objects.
	 * 
	 * @param list
	 *          list of Cloudlets
	 */
	private static List<SimulacaoVO> printCloudletList(final List<Cloudlet> list,
			final EnumTipoVM enumTipoVM)
	{
		List<SimulacaoVO> lstSimulacaoVO = new ArrayList<SimulacaoVO>();

		final int qtdCloudletsProcessados = list.size();

		if ( qtdCloudletsProcessados < numeroVms )
		{
			// Se houve qualquer erro de alocação(Processamento), então deve
			// solicitar reduza a quantidade de VMs.
			lstSimulacaoVO = null;

			Log.printLine();
			Log.printLine("========== OUTPUT ==========");
			Log
					.printLine("Erro ao tentar alocar recursos, favor reduzir o numero de VMs");
		}
		else
		{
			Cloudlet cloudlet;

			final String indent = "\t";
			Log.printLine();
			Log.printLine("========== OUTPUT ==========");
			Log.printLine("Cloudlet ID" + indent + "STATUS" + indent + "VM ID"
					+ indent + "Total Registro" + indent + "Processados" + indent
					+ "Processados em tempo violacao" + "Time");

			final DecimalFormat dft = new DecimalFormat("###.##");
			for (int i = 0; i < qtdCloudletsProcessados; i++)
			{
				cloudlet = list.get(i);

				final int cloudletId = cloudlet.getCloudletId();

				Log.print(indent + cloudletId);

				if ( cloudlet.getCloudletStatus() == Cloudlet.SUCCESS )
				{
					final String status = "SUCCESS";
					final int vmId = cloudlet.getVmId();
					final String totalRegistro = dft.format(cloudlet.getCloudletLength());
					final String totalProcessadosForaSLA = dft
							.format(calculcarQtdProcessadaNoTempoViolacaoSLA(cloudlet
									.getCloudletLength()));
					final String totalProcessadoDentroSLA = dft.format(cloudlet
							.getCloudletLength()
							- NumberUtils.getLong(totalProcessadosForaSLA));

					final String tempoProcessamento = dft.format(NumberUtils.divide(
							cloudlet.getFinishTime(), 1000, 15, 2));

					Log.printLine(indent + status + indent + indent + vmId + indent
							+ totalRegistro + indent + indent + totalProcessadoDentroSLA
							+ indent + indent + totalProcessadosForaSLA + indent + indent
							+ tempoProcessamento + indent + indent
							+ +cloudlet.getExecStartTime() + indent + indent
							+ cloudlet.getActualCPUTime() + indent + indent
							+ cloudlet.getFinishTime() + indent + indent
							+ cloudlet.getSubmissionTime() + indent + indent
							+ cloudlet.getWallClockTime());

					final SimulacaoVO simulacaoVO = new SimulacaoVO();

					simulacaoVO.setCloudletID(cloudletId);
					simulacaoVO.setStatus(status);
					simulacaoVO.setVmID(vmId + "");
					simulacaoVO.setTotalRegistro(totalRegistro);
					simulacaoVO.setTotalProcessadosDentroSLA(totalProcessadoDentroSLA);
					simulacaoVO.setTempoProcessamento(tempoProcessamento);
					simulacaoVO.setTotalProcessadoForaSLA(totalProcessadosForaSLA);
					simulacaoVO.setEnumTipoVM(enumTipoVM);

					lstSimulacaoVO.add(simulacaoVO);

				}
			}
		}

		return lstSimulacaoVO;
	}

	/**
	 * Criando o metodo main(), responsevel por executar esta simulacao.
	 * 
	 * @param args
	 *          the args
	 */
	public static void main(final String[] args)
	{
		Log.printLine("Inicializando a simulacao ProVMDB...");

		final ProVMBD proVMBD = getInstancia();
		proVMBD.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		proVMBD.setVisible(true); // display frame

	}

	public SimulaPanel getCadastroPanel()
	{
		// verifica se existe uma instancia do panel de controle de exibicao
		if ( cadastroPanel == null )
		{
			cadastroPanel = new SimulaPanel();
		}

		return cadastroPanel;
	}

	@SuppressWarnings( { "static-access", "unchecked" })
	public Map<Integer, List<SimulacaoVO>> simularQtdVMs(
			final Long totalRegistroBD, final EnumTipoVM enumTipoVM,
			final Integer numeroVms, final Integer slaps, final Integer intervaloVms,
			final Integer numSimulacao)
	{

		Map<Integer, List<SimulacaoVO>> mapSimulacao = new HashMap<Integer, List<SimulacaoVO>>();

		// Verifica se o map totalRegistroBD tem valor, em caso afirmativo limpa os
		// dados para a nova simulacao
		if ( mapResultadosQtdVms != null )
		{
			if ( mapResultadosQtdVms.containsKey(totalRegistroBD) )
			{
				mapResultadosQtdVms.put(totalRegistroBD, null);
			}
		}

		this.numeroVms = numeroVms;
		numeroCloudlets = this.numeroVms;

		for (int i = 0; i < numSimulacao; i++)
		{

			try
			{
				this.totalRegistroBD = totalRegistroBD;
				this.slaps = slaps;

				final int num_user = 1; // nemero de usuerio da nuvens
				final Calendar calendar = Calendar.getInstance();
				final boolean trace_flag = false; // mean trace GridSim events

				// Inicializando CloudSim
				CloudSim.init(num_user, calendar, trace_flag);

				// Segundo passo: Criar os DataCenters
				// Datacenters seo os provedores de recursos no CloudSim. Precisamos de
				// pelo menos um, para que possamos executar a simulacao.
				datacenter0 = createDatacenter("Datacenter_0", enumTipoVM);

				// Terceiro Passo: Cria o Broker(Corretor), esta objeto e responsavel
				// em alocar vms para que possa ser executado uma determinada tarefa
				// cloudlet
				final DatacenterBroker broker = createBroker("Broker_0");
				final int brokerId = broker.getId();

				// Fourth step: Create VMs and Cloudlets and send them to broker
				vmList = createVM(brokerId, enumTipoVM); // creating 5 vms
				cloudletList = createCloudlet(brokerId); // creating 10 cloudlets

				// submite a lista de vms ao broker
				broker.submitVmList(vmList);

				// submite a lista de cloudlet para o broker
				broker.submitCloudletList(cloudletList);

				// Sexto Passo: Inicializa a simulacao
				final double tempoTotalSimulacao = NumberUtils.divide(CloudSim
						.startSimulation(), 1000, 15, 2);

				// Passo Final: Imprimir os resultados ao final da simulacao
				final List<Cloudlet> newList = broker.getCloudletReceivedList();
				Log.printLine("Received " + newList.size() + " cloudlets");

				CloudSim.stopSimulation();

				calculcarPercViolacaoSLA(tempoTotalSimulacao);

				final List<SimulacaoVO> lstSimulacaoVO = printCloudletList(newList,
						enumTipoVM);

				if ( lstSimulacaoVO != null )
				{
					Collections.sort(lstSimulacaoVO, new Comparator()
					{
						public int compare(final Object o1, final Object o2)
						{
							final SimulacaoVO s1 = (SimulacaoVO) o1;
							final SimulacaoVO s2 = (SimulacaoVO) o2;
							return s1.getCloudletID().compareTo(s2.getCloudletID());
						}
					});

					Log.printLine();

					final double costPerSecond = custoPorHora(cloudletList,
							vmList.size(), enumTipoVM);
					final Double valorMulta = calculcarValorMulta(costPerSecond);

					Double custoHora;
					switch (enumTipoVM)
					{
						case SMALL:
							custoHora = CUSTO_POR_HORA_SMALL;
							break;

						case LARGE:
							custoHora = CUSTO_POR_HORA_LARGE;
							break;
						case EXTRA:
							custoHora = CUSTO_POR_HORA_EXTRA;

							break;

						default:
							custoHora = CUSTO_POR_HORA_SMALL;
							break;
					}
					final ResultadoVO resultadoVO = new ResultadoVO(this.numeroVms,
							tempoTotalSimulacao, prcSlaViolacao, costPerSecond, enumTipoVM,
							valorMulta, custoHora);

					addMapResultadoQtdVms(i + 1, resultadoVO);

					this.numeroVms = this.numeroVms + intervaloVms;
					numeroCloudlets = this.numeroVms;

					if ( mapSimulacao == null )
					{
						mapSimulacao = new HashMap<Integer, List<SimulacaoVO>>();
					}

					mapSimulacao.put(i + 1, lstSimulacaoVO);

				}
				else
				{
					mapSimulacao = null;
					break;
				}

			}
			catch (final Exception e)
			{
				e.printStackTrace();

				Log.printLine("Ocorreu o seguinte ERRO durante a simulacaoo:"
						+ e.getMessage());
			}
		}
		Log.printLine("Finalizando a simulacao ProVMDB...");

		for (final Map.Entry<Long, Map<Integer, ResultadoVO>> entryTotalRegistro : mapResultadosQtdVms
				.entrySet())
		{

			System.out.println("Total Registro:" + entryTotalRegistro.getKey());

			for (final Map.Entry<Integer, ResultadoVO> entryResultadoPorTotalRegistro : entryTotalRegistro
					.getValue().entrySet())
			{
				final ResultadoVO resultadoVO = entryResultadoPorTotalRegistro
						.getValue();

				System.out.println("qtdVms:" + resultadoVO.getQtdVMs()
						+ " tmpSimulacao:" + resultadoVO.getTempoSimulacao()
						+ " prcVioSLA:" + resultadoVO.getPrcViolacaoSLA() + " Custo:"
						+ resultadoVO.getCustoRecursos());
			}
		}

		return mapSimulacao;
	}

	@SuppressWarnings( { "static-access", "unchecked" })
	public SortedMap<EnumTipoVM, List<SimulacaoVO>> simularCombinacaoVMs(
			final Long totalRegistroBD,
			final Map<EnumTipoVM, Integer> mapTipoVmPorQtdVm, final Integer slaps,
			final int totalVms)
	{

		// Verifica se o map totalRegistroBD tem valor, em caso afirmativo limpa
		// os dados para a nova simulacao
		if ( mapResultadosCombinacaoVms != null )
		{
			mapResultadosCombinacaoVms = new TreeMap<EnumTipoVM, ResultadoVO>();
		}

		SortedMap<EnumTipoVM, List<SimulacaoVO>> mapSimulacao = new TreeMap<EnumTipoVM, List<SimulacaoVO>>();

		// itera no map de tipo de vms por total de entrada para processar
		for (final Map.Entry<EnumTipoVM, Integer> entryTipoVmPorQtdVm : mapTipoVmPorQtdVm
				.entrySet())
		{

			numeroVms = entryTipoVmPorQtdVm.getValue();
			numeroCloudlets = numeroVms;

			// Distribui a quantidade a processar com base na quantidade de VM
			final Integer qtdPorVm = NumberUtils.dividirArredondarAMaior(
					totalRegistroBD, totalVms, 15);

			final Long totalAProcessar = NumberUtils.multiplicar(qtdPorVm, numeroVms,
					15, 2).longValue();

			System.out.println("Tipo a Processar: " + totalAProcessar);

			final EnumTipoVM enumTipoVM = entryTipoVmPorQtdVm.getKey();

			try
			{
				// inicializa os atributo da simulacao
				this.totalRegistroBD = totalAProcessar;
				this.slaps = slaps;

				// Primeiro Passo: Inicializar os pacotes de CloudSim.
				final int num_user = 1; // nemero de usuerio da nuvens
				final Calendar calendar = Calendar.getInstance();
				final boolean trace_flag = false; // mean trace GridSim events

				// Inicializando CloudSim
				CloudSim.init(num_user, calendar, trace_flag);

				// Segundo passo: Criar os DataCenters
				// Datacenters seo os provedores de recursos no CloudSim. Precisamos
				// de pelo menos um, para que possamos executar a simulacao.
				datacenter0 = createDatacenter("Datacenter_0", enumTipoVM);

				// Terceiro Passo: Cria o Broker(Corretor), esta objeto e responsavel
				// em alocar vms para que possa ser executado uma determinada tarefa
				// cloudlet
				final DatacenterBroker broker = createBroker("Broker_0");
				final int brokerId = broker.getId();

				// Fourth step: Create VMs and Cloudlets and send them to broker
				vmList = createVM(brokerId, enumTipoVM); // creating 5 vms
				cloudletList = createCloudlet(brokerId); // creating 10 cloudlets

				// submite a lista de vms ao broker
				broker.submitVmList(vmList);

				// submite a lista de cloudlet para o broker
				broker.submitCloudletList(cloudletList);

				// Sexto Passo: Inicializa a simulacao
				final double tempoTotalSimulacao = NumberUtils.divide(CloudSim
						.startSimulation(), 1000, 15, 2);

				// Passo Final: Imprimir os resultados ao final da simulacao
				final List<Cloudlet> newList = broker.getCloudletReceivedList();
				Log.printLine("Received " + newList.size() + " cloudlets");

				CloudSim.stopSimulation();

				calculcarPercViolacaoSLA(tempoTotalSimulacao);

				final List<SimulacaoVO> lstSimulacaoVO = printCloudletList(newList,
						enumTipoVM);

				if ( lstSimulacaoVO != null )
				{
					Collections.sort(lstSimulacaoVO, new Comparator()
					{
						public int compare(final Object o1, final Object o2)
						{
							final SimulacaoVO s1 = (SimulacaoVO) o1;
							final SimulacaoVO s2 = (SimulacaoVO) o2;
							return s1.getCloudletID().compareTo(s2.getCloudletID());
						}
					});

					Log.printLine();

					final Double costPerHora = custoPorHora(cloudletList, vmList.size(),
							enumTipoVM);

					final Double valorMulta = calculcarValorMulta(costPerHora);

					Double custoHora;
					switch (enumTipoVM)
					{
						case SMALL:
							custoHora = CUSTO_POR_HORA_SMALL;
							break;

						case LARGE:
							custoHora = CUSTO_POR_HORA_LARGE;
							break;
						case EXTRA:
							custoHora = CUSTO_POR_HORA_EXTRA;

							break;

						default:
							custoHora = CUSTO_POR_HORA_SMALL;
							break;
					}
					final ResultadoVO resultadoVO = new ResultadoVO(numeroVms,
							tempoTotalSimulacao, prcSlaViolacao, costPerHora, enumTipoVM,
							valorMulta, custoHora, this.totalRegistroBD);

					addMapResultadoCombinacaoVms(enumTipoVM, resultadoVO);

					if ( mapSimulacao == null )
					{
						mapSimulacao = new TreeMap<EnumTipoVM, List<SimulacaoVO>>();
					}

					mapSimulacao.put(enumTipoVM, lstSimulacaoVO);

				}
				else
				{
					mapSimulacao = null;
					break;
				}

			}
			catch (final Exception e)
			{
				e.printStackTrace();

				Log.printLine("Ocorreu o seguinte ERRO durante a simulacaoo:"
						+ e.getMessage());
			}

		}

		Log.printLine("Finalizando a simulacao ProVMDB...");

		for (final Map.Entry<EnumTipoVM, ResultadoVO> entryTotalRegistro : mapResultadosCombinacaoVms
				.entrySet())
		{

			System.out.println("Tipo VM:" + entryTotalRegistro.getKey());

			final ResultadoVO resultadoVO = entryTotalRegistro.getValue();

			System.out.println("qtdVms:" + resultadoVO.getQtdVMs() + " tmpSimulacao:"
					+ resultadoVO.getTempoSimulacao() + " prcVioSLA:"
					+ resultadoVO.getPrcViolacaoSLA() + " Custo:"
					+ resultadoVO.getCustoRecursos() + " Total Registro:"
					+ resultadoVO.getTotalRegistro());
		}

		return mapSimulacao;

	}

	@SuppressWarnings( { "static-access", "unchecked" })
	public SortedMap<Long, List<ResultadoVO>> simularVMs(
			final Map<Long, List<EnumTipoVM>> mapTipoVmPorEntradaTotal,
			final Integer slaps, final int numeroVms)
	{

		this.numeroVms = numeroVms;
		numeroCloudlets = numeroVms;

		// map que contera o resultado da simulacao
		SortedMap<Long, List<ResultadoVO>> mapResultadoSimulacao = new TreeMap<Long, List<ResultadoVO>>();

		// itera no map de tipo de vms por total de entrada para processar
		for (final Map.Entry<Long, List<EnumTipoVM>> entryTipoVmPorEntradaTotal : mapTipoVmPorEntradaTotal
				.entrySet())
		{

			final Long totalAProcessar = entryTipoVmPorEntradaTotal.getKey();

			System.out.println("Tipo a Processar: " + totalAProcessar);

			final List<ResultadoVO> lstResultadoVO = new ArrayList<ResultadoVO>();

			for (final EnumTipoVM enumTipoVM : entryTipoVmPorEntradaTotal.getValue())
			{

				try
				{
					// inicializa os atributo da simulacao
					totalRegistroBD = totalAProcessar;
					this.slaps = slaps;

					// Primeiro Passo: Inicializar os pacotes de CloudSim.
					final int num_user = 1; // nemero de usuerio da nuvens
					final Calendar calendar = Calendar.getInstance();
					final boolean trace_flag = false; // mean trace GridSim events

					// Inicializando CloudSim
					CloudSim.init(num_user, calendar, trace_flag);

					// Segundo passo: Criar os DataCenters
					// Datacenters sao os provedores de recursos no CloudSim. Precisamos
					// de pelo menos um, para que possamos executar a simulacao.
					datacenter0 = createDatacenter("Datacenter_0", enumTipoVM);

					// Terceiro Passo: Cria o Broker(Corretor), esta objeto e
					// responsavel em alocar vms para que possa ser executado uma
					// determinada tarefa cloudlet
					final DatacenterBroker broker = createBroker("Broker_0");
					final int brokerId = broker.getId();

					// Fourth step: Create VMs and Cloudlets and send them to broker
					vmList = createVM(brokerId, enumTipoVM); // creating 5 vms
					cloudletList = createCloudlet(brokerId); // creating 10 cloudlets

					// submite a lista de vms ao broker
					broker.submitVmList(vmList);

					// submite a lista de cloudlet para o broker
					broker.submitCloudletList(cloudletList);

					// Sexto Passo: Inicializa a simulacao
					final double tempoTotalSimulacao = NumberUtils.divide(CloudSim
							.startSimulation(), 1000, 15, 2);

					// Passo Final: Imprimir os resultados ao final da simulacao
					final List<Cloudlet> newList = broker.getCloudletReceivedList();
					Log.printLine("Received " + newList.size() + " cloudlets");

					CloudSim.stopSimulation();

					final List<SimulacaoVO> lstSimulacaoVO = printCloudletList(newList,
							enumTipoVM);

					if ( lstSimulacaoVO != null )
					{
						Collections.sort(lstSimulacaoVO, new Comparator()
						{
							public int compare(final Object o1, final Object o2)
							{
								final SimulacaoVO s1 = (SimulacaoVO) o1;
								final SimulacaoVO s2 = (SimulacaoVO) o2;
								return s1.getCloudletID().compareTo(s2.getCloudletID());
							}
						});

						Log.printLine();

						calculcarPercViolacaoSLA(tempoTotalSimulacao);

						final Double costPerSecond = custoPorHora(cloudletList, vmList
								.size(), enumTipoVM);

						final Double valorMulta = calculcarValorMulta(costPerSecond);

						Double custoHora;
						switch (enumTipoVM)
						{
							case SMALL:
								custoHora = CUSTO_POR_HORA_SMALL;
								break;

							case LARGE:
								custoHora = CUSTO_POR_HORA_LARGE;
								break;
							case EXTRA:
								custoHora = CUSTO_POR_HORA_EXTRA;

								break;

							default:
								custoHora = CUSTO_POR_HORA_SMALL;
								break;
						}
						final ResultadoVO resultadoVO = new ResultadoVO(this.numeroVms,
								tempoTotalSimulacao, prcSlaViolacao, costPerSecond, enumTipoVM,
								valorMulta, custoHora);

						lstResultadoVO.add(resultadoVO);

					}
					else
					{
						mapResultadoSimulacao = null;
						break;
					}

				}
				catch (final Exception e)
				{
					e.printStackTrace();

					Log.printLine("Ocorreu o seguinte ERRO durante a simulacaoo:"
							+ e.getMessage());
				}
			}

			if ( mapResultadoSimulacao != null )
			{
				mapResultadoSimulacao.put(totalAProcessar, lstResultadoVO);
			}

		}

		Log.printLine("Finalizando a simulacao ProVMDB...");

		if ( mapResultadoSimulacao != null )
		{
			for (final Map.Entry<Long, List<ResultadoVO>> entryTipoVMPorResultadoSimulacao : mapResultadoSimulacao
					.entrySet())
			{

				System.out.println("Total a Processar:"
						+ entryTipoVMPorResultadoSimulacao.getKey());

				for (final ResultadoVO resultadoVO : entryTipoVMPorResultadoSimulacao
						.getValue())
				{
					System.out.println("Tipo VM:" + resultadoVO.getEnumTipoVM()
							+ " tmpSimulacao:" + resultadoVO.getTempoSimulacao()
							+ " prcVioSLA:" + resultadoVO.getPrcViolacaoSLA() + " Custo:"
							+ resultadoVO.getCustoRecursos());
				}
			}
		}
		return mapResultadoSimulacao;
	}

	/**
	 * Obtem a instancia atual
	 */
	public static ProVMBD getInstancia()
	{
		return proVMDB;
	}

	public void addMapResultadoQtdVms(final Integer idSimulacao,
			final ResultadoVO resultadoVO)
	{
		if ( mapResultadosQtdVms == null )
		{
			mapResultadosQtdVms = new HashMap<Long, Map<Integer, ResultadoVO>>();
		}

		if ( mapResultadosQtdVms.get(totalRegistroBD) == null )
		{
			mapResultadosQtdVms.put(totalRegistroBD,
					new HashMap<Integer, ResultadoVO>());
		}

		mapResultadosQtdVms.get(totalRegistroBD).put(idSimulacao, resultadoVO);
	}

	public void addMapResultadoCombinacaoVms(final EnumTipoVM enumTipoVM,
			final ResultadoVO resultadoVO)
	{
		if ( mapResultadosCombinacaoVms == null )
		{
			mapResultadosCombinacaoVms = new TreeMap<EnumTipoVM, ResultadoVO>();
		}

		mapResultadosCombinacaoVms.put(enumTipoVM, resultadoVO);

	}

	public Map<Long, Map<Integer, ResultadoVO>> getResultadoSimulacaoQtdVms()
	{
		return mapResultadosQtdVms;
	}

	public static void limparMapResultadosQtdVms()
	{
		mapResultadosQtdVms = null;
	}

	public SortedMap<EnumTipoVM, ResultadoVO> getResultadoSimulacaoCombinacaoVms()
	{
		return mapResultadosCombinacaoVms;
	}

	public static void limparMapCombinacaoVms()
	{
		mapResultadosCombinacaoVms = null;
	}

	public static Long getTotalRegistroBD()
	{
		return totalRegistroBD;
	}

	public static double custoPorHora(final List<Cloudlet> list, final int vms,
			final EnumTipoVM enumTipoVM)
	{
		double custoTotal = 0;
		double custoPorHora;

		// 1. identifica o custo da hora atraves do tipo de instancia
		switch (enumTipoVM)
		{
			case SMALL:
				custoPorHora = CUSTO_POR_HORA_SMALL;
				break;

			case LARGE:
				custoPorHora = CUSTO_POR_HORA_LARGE;
				break;

			case EXTRA:
				custoPorHora = CUSTO_POR_HORA_EXTRA;
				break;

			default:
				custoPorHora = CUSTO_POR_HORA_SMALL;
				break;
		}

		// 2. flag de controle de instancia examinada
		final int vmID[] = new int[vms];

		for (int j = 0; j < vms; j++)
		{
			vmID[j] = 0;
		}

		// 3. calcula o custo total de cada instancia
		for (final Cloudlet cloudlet : list)
		{
			if ( vmID[cloudlet.getVmId()] == 0 )
			{
				vmID[cloudlet.getVmId()] = 1;

				final double tempoInicial = cloudlet.getExecStartTime();
				final double tempoFinal = cloudlet.getFinishTime();
				final double tempoUtilizado = tempoFinal - tempoInicial;

				if ( tempoUtilizado / 3600000 < 1 )
				{
					custoTotal = custoTotal + custoPorHora;
				}
				else if ( tempoUtilizado / 3600000 > 1 && tempoUtilizado / 3600000 < 2 )
				{
					custoTotal = custoTotal + 2 * custoPorHora;
				}
				else if ( tempoUtilizado / 3600000 > 2 && tempoUtilizado / 3600000 < 3 )
				{
					custoTotal = custoTotal + 3 * custoPorHora;
				}
			}
		}

		return custoTotal;
	}
}

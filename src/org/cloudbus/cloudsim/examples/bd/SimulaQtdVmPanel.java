package org.cloudbus.cloudsim.examples.bd;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.AbstractTableModel;

import org.cloudbus.cloudsim.examples.bd.ProVMBD.EnumTipoVM;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 * CadastroPanel, representa a tela de cadastro das acoes
 * 
 * @version 1.0
 * @author Humberto Lima
 */
public class SimulaQtdVmPanel extends JPanel
{

	/**
	 * @author Humberto Lima 12/11/2011 - 07:41:24.
	 */
	private static final long serialVersionUID = -7283873869719799966L;

	/** rotulo do campo total de registro BD **/
	private JLabel jLTotalRegistroBD = null;

	/** rotulo do campos tipo de vm **/
	private JLabel jLTipoVm = null;

	/** rotulo do campos qtd. inicial de vms **/
	private JLabel jLQtdIncialVms = null;

	private JLabel jLIntervaloVms = null;

	private JScrollPane jSPTables = null;

	private JPanel jPTables = null;

	/** campos codigo **/
	private JTextField jTFTotalRegistroBD = null;

	/** campos valor venda **/
	private JTextField jTFNumeroVms = null;

	/** botao salvar **/
	private JButton jBSimular = null;

	private JLabel jLTotalAcoes = null;

	private JLabel jLTempoSla = null;

	private JLabel jLNumSimulacao = null;

	private JTextField jTFSlaps = null;

	private JTextField jTFNumSimulacao = null;

	private JButton jBValorPadrao = null;

	/** lista que ira conter cada acao a ser exibida **/
	private static Map<Integer, List<SimulacaoVO>> mapSimulacao;

	private JPanel jPanel = null;

	private JPanel jPanel1 = null;

	private JComboBox jComboBox = null;

	private JComboBox jCIntervaloVms = null;

	/**
	 * Construtor padrao
	 */
	public SimulaQtdVmPanel()
	{
		super();
		initialize();
	}

	/**
	 * Inicializa os parametros da tela
	 * 
	 * @return void
	 */
	private void initialize()
	{
		// cria os componentes e seta os parametros
		jLNumSimulacao = new JLabel();
		jLNumSimulacao.setBounds(new Rectangle(414, 30, 163, 17));
		jLNumSimulacao.setText("Numero Simulacoes:");
		jLTempoSla = new JLabel();
		jLTempoSla.setBounds(new Rectangle(414, 75, 163, 17));
		jLTempoSla.setText("Tempo SLA em Seg.:");
		jLTotalAcoes = new JLabel();
		jLTotalAcoes.setBounds(new Rectangle(443, 180, 147, 17));
		jLTotalAcoes.setText("");
		jLTotalRegistroBD = new JLabel();
		jLTotalRegistroBD.setBounds(new Rectangle(15, 30, 172, 15));
		jLTotalRegistroBD.setText("Total Registros do BD:");
		jLTipoVm = new JLabel();
		jLTipoVm.setBounds(new Rectangle(209, 28, 79, 17));
		jLTipoVm.setText("Tipo VM:");
		jLQtdIncialVms = new JLabel();
		jLQtdIncialVms.setBounds(new Rectangle(17, 75, 140, 15));
		jLQtdIncialVms.setText("VM - Qtd. Inicial:");
		jLIntervaloVms = new JLabel();
		jLIntervaloVms.setBounds(new Rectangle(209, 75, 140, 15));
		jLIntervaloVms.setText("VM - Qtd. Intervalo:");

		// seta o tamanho da tela e adiciona os componentes
		this.setSize(ProVMBD.comprimentoTela - 5, ProVMBD.alturaTela - 5);

		setLayout(null);
		this.add(jLTotalRegistroBD, null);
		this.add(getJTFCodigo(), null);
		this.add(jLTipoVm, null);
		this.add(jLQtdIncialVms, null);
		this.add(jLIntervaloVms, null);
		this.add(getJTFValorVenda(), null);
		this.add(getJBSimular(), null);
		this.add(jLNumSimulacao, null);
		this.add(getJTFNumSimulacao(), null);
		this.add(jLTempoSla, null);
		this.add(getJTFSlaps(), null);

		jPTables = new JPanel();
		jPTables.setLayout(null);
		jPTables.setPreferredSize(new Dimension(530, 500));

		jSPTables = new JScrollPane(jPTables);
		jSPTables.setVisible(true);
		jSPTables.setBounds(new Rectangle(15, 135, 560, 500));
		jSPTables
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		jPTables.setVisible(true);

		this.add(getJPanel(), null);
		this.add(getJPanel1(), null);
		this.add(getJComboBox(), null);
		this.add(getJCIntervaloVms(), null);
		this.add(getJValorPadrao(), null);

		this.add(jSPTables, null);

		setValoresDefault();
	}

	/**
	 * Inicializa o jTFValorVenda
	 * 
	 * @return uma instancia jTFValorVenda
	 */
	private JTextField getJTFValorVenda()
	{
		// verifica se existe uma instancia
		if ( jTFNumeroVms == null )
		{
			jTFNumeroVms = new JTextField();
			jTFNumeroVms.setBounds(new Rectangle(17, 95, 140, 19));
		}

		return jTFNumeroVms;
	}

	/**
	 * Inicializa o jTFCodigo
	 * 
	 * @return uma instancia jTFCodigo
	 */
	private JTextField getJTFCodigo()
	{
		// verifica se existe uma instancia
		if ( jTFTotalRegistroBD == null )
		{
			jTFTotalRegistroBD = new JTextField();
			jTFTotalRegistroBD.setBounds(new Rectangle(15, 47, 131, 19));
		}

		return jTFTotalRegistroBD;
	}

	/**
	 * Inicializa o jBSalvar
	 * 
	 * @return uma instancia jBSalvar
	 */
	private JButton getJBSimular()
	{
		// verifica se existe uma instancia
		if ( jBSimular == null )
		{
			jBSimular = new JButton();
			jBSimular.setBounds(new Rectangle(683, 91, 132, 23));
			jBSimular.setText("Simular");

			// adiciona o tratamento de evento
			jBSimular.addActionListener(new java.awt.event.ActionListener()
			{
				public void actionPerformed(final java.awt.event.ActionEvent e)
				{
					// verifica se todos os campos foram preechidos
					if ( validarCampos() )
					{
						// obtem uma instacia do menu principal
						final ProVMBD proVMBD = ProVMBD.getInstancia();

						// obtem o total de registro do BD
						final Long totalRegistroBD = NumberUtils.getLong(jTFTotalRegistroBD
								.getText());

						// obtem o capacidade do numero de registro processado pela VM por
						// segundo.
						final EnumTipoVM enumTipoVM = (EnumTipoVM) jComboBox
								.getSelectedItem();

						// obtem o numero de vms
						final Integer numeroVms = NumberUtils
								.getInt(jTFNumeroVms.getText());

						if ( numeroVms == 0 )
						{
							JOptionPane.showMessageDialog(jPTables,
									"Campo Obrigatorio - Qtd. Inicial", "Error",
									JOptionPane.ERROR_MESSAGE);

							setValoresDefault();

							return;

						}

						// obtem o tempo de SLA definido em segundos
						final Integer slaps = NumberUtils.getInt(jTFSlaps.getText());

						// obtem o intervalo de vms
						final Integer intervaloVms = (Integer) jCIntervaloVms
								.getSelectedItem();

						// obtem o numero de simulacoes
						final Integer numSimulacao = NumberUtils.getInt(jTFNumSimulacao
								.getText());

						if ( numSimulacao == 0 )
						{
							JOptionPane.showMessageDialog(jPTables,
									"Campo Obrigatorio - Numero Simulacoes", "Error",
									JOptionPane.ERROR_MESSAGE);

							setValoresDefault();

							return;

						}

						// executa a operacao de cadastro da acao
						mapSimulacao = proVMBD.simularQtdVMs(totalRegistroBD, enumTipoVM,
								numeroVms, slaps, intervaloVms, numSimulacao);

						atualizarTabela();

					}
				}
			});
		}

		return jBSimular;
	}

	/**
	 * Verifica se todos os campos da tela foram informados
	 * 
	 * @return verdade se todos os campos foram informados
	 */
	private boolean validarCampos()
	{
		boolean isValido;

		// verifica se todos os campos foram preechidos
		if ( jTFTotalRegistroBD.getText() != null && jTFNumeroVms.getText() != null
				&& jTFSlaps.getText() != null )
		{
			isValido = true;
		}
		else
		{
			// exibe a mensagem de erro
			JOptionPane.showMessageDialog(this,
					"Todos os campos devem estar preechidos corretamente",
					"Campo Invalido", JOptionPane.ERROR_MESSAGE);

			isValido = false;
		}

		return isValido;
	}

	/**
	 * Limpa todos os campos apos cadastro
	 */
	private void setValoresDefault()
	{
		// seta valores default dos campos
		jTFTotalRegistroBD.setText("13000000");
		jComboBox.setSelectedItem(EnumTipoVM.SMALL);
		jTFNumeroVms.setText("1");
		jTFSlaps.setText("5");
		jTFNumSimulacao.setText("4");
		mapSimulacao = new HashMap<Integer, List<SimulacaoVO>>();

		ProVMBD.limparMapResultadosQtdVms();

		atualizarTabela();

	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTFSlaps()
	{
		if ( jTFSlaps == null )
		{
			jTFSlaps = new JTextField();
			jTFSlaps.setBounds(new Rectangle(413, 95, 158, 19));
		}
		return jTFSlaps;
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTFNumSimulacao()
	{
		if ( jTFNumSimulacao == null )
		{
			jTFNumSimulacao = new JTextField();
			jTFNumSimulacao.setBounds(new Rectangle(413, 47, 158, 19));
		}
		return jTFNumSimulacao;
	}

	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJValorPadrao()
	{
		if ( jBValorPadrao == null )
		{
			jBValorPadrao = new JButton();
			jBValorPadrao.setText("Valor Padrao");

			jBValorPadrao.setBounds(new Rectangle(854, 91, 133, 23));
			jBValorPadrao.addActionListener(new java.awt.event.ActionListener()
			{

				@Override
				public void actionPerformed(final ActionEvent arg0)
				{
					setValoresDefault();
				}

			});
		}
		return jBValorPadrao;

	}

	/**
	 * This method initializes jTable1
	 * 
	 * @param i
	 * 
	 * @return javax.swing.JTable
	 */
	private JTable getJTable(final int numeroSimulacao)
	{
		// Obtem o resultado para detalhar na tabela de simulacao
		final List<SimulacaoVO> lstSimulacaoVO = mapSimulacao.get(numeroSimulacao);

		// cria os dados da tabela
		final ResultadoTableModel resultadoTableModel = new ResultadoTableModel(
				lstSimulacaoVO);

		// cria a tabela com os dados
		final JTable jTable = new JTable(resultadoTableModel);

		// seta o modo de selecao das acoes
		jTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		return jTable;
	}

	/**
	 * Classe interna que irar conter os dados das acoes
	 * 
	 * @author Humberto Lima
	 * 
	 */
	private class ResultadoTableModel extends AbstractTableModel
	{
		/** identificacao da classe **/
		private static final long serialVersionUID = -9138000385450417562L;

		/** lista que ira conter cada acao a ser exibida **/
		private List<SimulacaoVO> lstSimulacaoVO = new ArrayList<SimulacaoVO>();

		/**
		 * Construtor padrao
		 * 
		 * @param lstSimulacaoVO
		 */
		private ResultadoTableModel(final List<SimulacaoVO> lstSimulacaoVO)
		{
			super();

			this.lstSimulacaoVO = lstSimulacaoVO;
		}

		/**
		 * Obtem o numero de colunas da tabela
		 * 
		 * @return o numero de colunas da tabela
		 */
		@Override
		public int getColumnCount()
		{
			return 5;
		}

		/**
		 * Obtem o nome das colunas
		 * 
		 * @return o nome da coluna
		 */
		@Override
		public String getColumnName(final int column) throws IllegalStateException
		{
			String nomeColuna;

			// define o nome de cada coluna da tabela
			switch (column)
			{
				case 0:
					nomeColuna = "VM ID";
					break;

				case 1:
					nomeColuna = "Total Registro";
					break;

				case 2:
					nomeColuna = "Dentro SLA";
					break;

				case 3:
					nomeColuna = "Fora SLA";
					break;

				case 4:
					nomeColuna = "Tempo";
					break;

				default:
					nomeColuna = null;
					break;
			}

			return nomeColuna;
		}

		/**
		 * Obtem o numero de linhas da tabela
		 * 
		 * @return o numero de linhas da tabela
		 */
		@Override
		public int getRowCount()
		{

			if ( lstSimulacaoVO != null )
			{
				return lstSimulacaoVO.size();
			}

			return 0;
		}

		/**
		 * Obtem o valor de cada celula da tabela
		 * 
		 * @return o valor de cada celula da tabela
		 */
		@Override
		public Object getValueAt(final int rowIndex, final int columnIndex)
		{
			Object valor;

			// obtem a acao da linha
			final SimulacaoVO simulacaoVO = lstSimulacaoVO.get(rowIndex);

			// obtem o valor de cada celula da tabela
			switch (columnIndex)
			{
				case 0:
					valor = simulacaoVO.getVmID();
					break;

				case 1:
					valor = simulacaoVO.getTotalRegistro();
					break;

				case 2:
					valor = simulacaoVO.getTotalProcessadosDentroSLA();
					break;

				case 3:
					valor = simulacaoVO.getTotalProcessadoForaSLA();
					break;

				case 4:
					valor = simulacaoVO.getTempoProcessamento();
					break;
				default:
					valor = null;
					break;
			}

			return valor;
		}

	}

	/**
	 * Atualiza os dados da tabela
	 * 
	 * @param proVMBD
	 * @param slaps
	 * @param numeroVms
	 * @param regps
	 * @param totalRegistroBD
	 * @param pesNumber2
	 * @param pesNumber
	 */
	@SuppressWarnings("static-access")
	public void atualizarTabela()
	{

		if ( mapSimulacao != null )
		{
			jPTables.removeAll();
			final int alturaPanel = 166 * mapSimulacao.size();
			jPTables.setPreferredSize(new Dimension(530, alturaPanel));

			int altura = 10;

			for (final Map.Entry<Integer, List<SimulacaoVO>> entry : mapSimulacao
					.entrySet())
			{

				final JScrollPane jScrollPane = new JScrollPane();
				jScrollPane.setBounds(new Rectangle(5, altura, 530, 80));

				altura = altura + 164;

				jScrollPane.setVisible(true);
				jScrollPane.setViewportView(getJTable(entry.getKey()));

				jPTables.add(jScrollPane);

			}

			final ProVMBD proVMBD = ProVMBD.getInstancia();

			if ( proVMBD != null )
			{

				final Map<Long, Map<Integer, ResultadoVO>> mapResultadoSimulacao = proVMBD
						.getResultadoSimulacaoQtdVms();

				final Map<Integer, ResultadoVO> entryTotalRegistro;
				if ( mapResultadoSimulacao != null )
				{
					entryTotalRegistro = mapResultadoSimulacao.get(proVMBD
							.getTotalRegistroBD());
				}
				else
				{
					entryTotalRegistro = new HashMap<Integer, ResultadoVO>();
				}

				int alturaViolacaoSla = 90;
				int alturaValorVMs = 105;
				int alturaValorMultaVMs = 120;

				for (final Map.Entry<Integer, ResultadoVO> entryResultadoPorTotalRegistro : entryTotalRegistro
						.entrySet())
				{
					// Obtem os valores de prcViolacaoSLA e Custo com recurso a partir
					// do primeiro resultado;
					final ResultadoVO resultadoVO = entryResultadoPorTotalRegistro
							.getValue();

					final JLabel jLViolacaoSla = new JLabel();
					final JLabel jLValorVms = new JLabel();
					final JLabel jLValorMulta = new JLabel();

					jLViolacaoSla.setBounds(new Rectangle(5, alturaViolacaoSla, 512, 15));
					jLValorVms.setBounds(new Rectangle(5, alturaValorVMs, 512, 15));
					jLValorMulta
							.setBounds(new Rectangle(5, alturaValorMultaVMs, 512, 15));

					jLViolacaoSla.setText(resultadoVO.getPrcViolacaoSlaFormatado());
					jLValorVms.setText(resultadoVO.getValorDebitoFormatado());
					jLValorMulta.setText(resultadoVO.getValorMultaFormatado());

					jPTables.add(jLViolacaoSla, null);
					jPTables.add(jLValorVms, null);
					jPTables.add(jLValorMulta, null);

					alturaViolacaoSla = alturaViolacaoSla + 164;
					alturaValorVMs = alturaValorVMs + 164;
					alturaValorMultaVMs = alturaValorMultaVMs + 164;

				}

				jPTables.revalidate();
				jPTables.repaint();
				jPTables.setVisible(true);

				// setBarChart(proVMBD.getResultadoSimulacao());
				if ( !mapSimulacao.isEmpty() )
				{
					setXYSerieChart(proVMBD.getResultadoSimulacaoQtdVms());
				}
				else
				{
					jPanel.setVisible(false);
				}

			}
			else
			{
				// Oculta a tabela de violacao
				jPanel.setVisible(false);

				final JLabel jLErro = new JLabel();

				jLErro.setBounds(new Rectangle(17, 228, 512, 15));
				jLErro
						.setText("Erro ao tentar alocar recursos, favor reduzir o numero de VMs");

			}

		}
	}

	public void setBarChart(final Map<Integer, ResultadoVO> mapResultado)
	{
		final DefaultCategoryDataset dataset = new DefaultCategoryDataset();

		for (final Map.Entry<Integer, ResultadoVO> entry : mapResultado.entrySet())
		{
			final ResultadoVO resultadoVO = entry.getValue();
			dataset.setValue(resultadoVO.getPrcViolacaoSLA(), "%Violacao SLA", entry
					.getKey());
		}

		final JFreeChart chart = ChartFactory.createBarChart3D("Violacao SLA",
				"Qtd Vms", "%Violacao SLA", dataset, PlotOrientation.VERTICAL, false,
				true, false);
		chart.setBackgroundPaint(Color.white);
		chart.getTitle().setPaint(Color.black);
		final CategoryPlot p = chart.getCategoryPlot();
		p.setRangeGridlinePaint(Color.blue);
		final ChartPanel myChartPanel = new ChartPanel(chart, false);
		myChartPanel.setSize(jPanel.getWidth(), jPanel.getHeight());
		myChartPanel.setVisible(true);
		jPanel.removeAll();
		jPanel.add(myChartPanel);
		jPanel.revalidate();
		jPanel.repaint();
	}

	public void setXYSerieChart(
			final Map<Long, Map<Integer, ResultadoVO>> mapResultado)
	{

		final XYSeriesCollection xyDataset = new XYSeriesCollection();

		for (final Map.Entry<Long, Map<Integer, ResultadoVO>> entryTotalRegistro : mapResultado
				.entrySet())
		{

			final XYSeries serieTotalRegistro = new XYSeries("Total Registro - "
					+ entryTotalRegistro.getKey());

			for (final Map.Entry<Integer, ResultadoVO> entryResultadoPorTotalRegistro : entryTotalRegistro
					.getValue().entrySet())
			{

				final ResultadoVO resultadoVO = entryResultadoPorTotalRegistro
						.getValue();

				serieTotalRegistro.add(resultadoVO.getQtdVMs(), resultadoVO
						.getPrcViolacaoSLA());

			}

			xyDataset.addSeries(serieTotalRegistro);

		}

		final JFreeChart chart = ChartFactory.createXYLineChart("% Violacao SLA",
				"Qtd VMs", "% Violacao SLA", xyDataset, PlotOrientation.VERTICAL, true,
				true, false);

		chart.setBackgroundPaint(Color.white);
		chart.getTitle().setPaint(Color.black);
		final ChartPanel myChartPanel = new ChartPanel(chart, false);
		myChartPanel.setSize(jPanel.getWidth(), jPanel.getHeight());
		myChartPanel.setVisible(true);
		jPanel.removeAll();
		jPanel.add(myChartPanel);
		jPanel.revalidate();
		jPanel.repaint();
		jPanel.setVisible(true);
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel()
	{
		if ( jPanel == null )
		{
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.setBounds(new Rectangle(579, 190, 420, 400));
		}
		return jPanel;
	}

	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel1()
	{
		if ( jPanel1 == null )
		{
			jPanel1 = new JPanel();
			jPanel1.setLayout(new BorderLayout());
			jPanel1.setBounds(new Rectangle(17, 129, 981, 2));
			jPanel1.setBorder(BorderFactory.createLineBorder(Color.gray));
		}
		return jPanel1;
	}

	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getJComboBox()
	{
		if ( jComboBox == null )
		{

			final List<EnumTipoVM> values = Arrays.asList(EnumTipoVM.SMALL,
					EnumTipoVM.LARGE, EnumTipoVM.EXTRA);
			jComboBox = new JComboBox(values.toArray());
			jComboBox.setBounds(new Rectangle(209, 48, 141, 18));
		}
		return jComboBox;
	}

	/**
	 * @author Humberto Lima[humbertolimaa@gmail.com] 16/11/2011 - 20:16:59.
	 * @return
	 */
	private JComboBox getJCIntervaloVms()
	{
		if ( jCIntervaloVms == null )
		{

			final List<Integer> values = Arrays.asList(1, 2, 3, 4, 5);
			jCIntervaloVms = new JComboBox(values.toArray());
			jCIntervaloVms.setBounds(new Rectangle(209, 95, 141, 18));
		}
		return jCIntervaloVms;
	}
} // @jve:decl-index=0:visual-constraint="10,10"

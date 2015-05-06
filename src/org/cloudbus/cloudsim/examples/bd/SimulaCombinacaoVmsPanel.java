package org.cloudbus.cloudsim.examples.bd;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;

import org.cloudbus.cloudsim.examples.bd.ProVMBD.EnumTipoVM;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 * CadastroPanel, representa a tela de cadastro das acoes
 * 
 * @version 1.0
 * @author Humberto Lima
 */
public class SimulaCombinacaoVmsPanel extends JPanel
{

	/**
	 * @author Humberto Lima[humbertolimaa@gmail.com] 22/11/2011 - 20:09:43.
	 */
	private static final long serialVersionUID = -9115099879732143511L;

	/** rotulo do campos codigo **/
	private JLabel jLTotalRegistroBD = null;

	/** campos codigo **/
	private JTextField jTFTotalRegistroBD = null;

	/** botao salvar **/
	private JButton jBSimular = null;

	private JLabel jLTotalAcoes = null;

	private JLabel jLabel = null;

	private JTextField jTFSlaps = null;

	private JButton jBValorPadrao = null;

	private JPanel jPGraficoCusto = null;

	private JPanel jPGraficoViolacao = null;

	/** lista que ira conter cada vm que foi simulada **/
	private static SortedMap<EnumTipoVM, List<SimulacaoVO>> mapSimulacao;

	private JPanel jPBarraDivisoria = null;

	private JCheckBox jCBSmall = null;

	private JCheckBox jCBLarge = null;

	private JCheckBox jCBExtra = null;

	private JTextField jTFQtdSmall = null;

	private JTextField jTFQtdLarge = null;

	private JTextField jTFQtdExtra = null;

	private JPanel jPTipoEQtdVM = null;

	private JPanel jPTables = null;

	/**
	 * Construtor padrao
	 */
	public SimulaCombinacaoVmsPanel()
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
		jLabel = new JLabel();
		jLabel.setBounds(new Rectangle(417, 73, 163, 17));
		jLabel.setText("Tempo SLA em Seg.:");
		jLTotalAcoes = new JLabel();
		jLTotalAcoes.setBounds(new Rectangle(443, 180, 147, 17));
		jLTotalAcoes.setText("");
		jLTotalRegistroBD = new JLabel();
		jLTotalRegistroBD.setBounds(new Rectangle(15, 30, 172, 15));
		jLTotalRegistroBD.setText("Total Registros do BD:");

		jPTables = new JPanel();
		jPTables.setLayout(null);
		jPTables.setBounds(new Rectangle(15, 135, 570, 600));
		jPTables.setVisible(true);

		// seta o tamanho da tela e adiciona os componentes
		this.setSize(ProVMBD.comprimentoTela - 5, ProVMBD.alturaTela - 5);

		setLayout(null);
		this.add(jLTotalRegistroBD, null);
		this.add(getJTFCodigo(), null);
		this.add(getJBSimular(), null);
		this.add(jLabel, null);
		this.add(getJTextField(), null);
		this.add(getJValorPadrao(), null);

		this.add(getJPBarraDivisoria(), null);
		this.add(getJPTipoEQtdVM(), null);
		this.add(getJValorPadrao(), null);

		this.add(getJPGraficoCusto(), null);
		this.add(getJPGraficoViolacao(), null);

		this.add(jPTables, null);

		setValoresDefault();
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

						// obtem o as quantidades por tipo de VM
						final Map<EnumTipoVM, Integer> mapTipoVmPorQtdVm = new HashMap<EnumTipoVM, Integer>();

						int totalVms = 0;
						if ( jCBSmall.isSelected() )
						{
							final int qtdVmSmall = NumberUtils.getInt(jTFQtdSmall.getText());
							mapTipoVmPorQtdVm.put(EnumTipoVM.SMALL, qtdVmSmall);

							if ( qtdVmSmall == 0 )
							{
								JOptionPane.showMessageDialog(jPTables,
										"Informar a Qtd. de VMs Small ou Desmarcar", "Error",
										JOptionPane.ERROR_MESSAGE);

								return;

							}

							totalVms = totalVms + qtdVmSmall;
						}

						if ( jCBLarge.isSelected() )
						{
							final int qtdVmLarge = NumberUtils.getInt(jTFQtdLarge.getText());
							mapTipoVmPorQtdVm.put(EnumTipoVM.LARGE, qtdVmLarge);

							if ( qtdVmLarge == 0 )
							{
								JOptionPane.showMessageDialog(jPTables,
										"Informar a Qtd. de VMs Large ou Desmarcar", "Error",
										JOptionPane.ERROR_MESSAGE);

								return;

							}

							totalVms = totalVms + qtdVmLarge;
						}

						if ( jCBExtra.isSelected() )
						{
							final int qtdVmExtra = NumberUtils.getInt(jTFQtdExtra.getText());
							mapTipoVmPorQtdVm.put(EnumTipoVM.EXTRA, qtdVmExtra);

							if ( qtdVmExtra == 0 )
							{
								JOptionPane.showMessageDialog(jPTables,
										"Informar a Qtd. de VMs Extra ou Desmarcar", "Error",
										JOptionPane.ERROR_MESSAGE);

								return;

							}

							totalVms = totalVms + qtdVmExtra;
						}

						// obtem o tempo de SLA definido em segundos
						final Integer slaps = NumberUtils.getInt(jTFSlaps.getText());

						if ( slaps == 0 )
						{
							JOptionPane.showMessageDialog(jPTables,
									"Campo Obrigatorio - Tempo SLA em Seg.", "Error",
									JOptionPane.ERROR_MESSAGE);

							setValoresDefault();
							return;

						}

						// executa a operacao de cadastro da acao
						mapSimulacao = proVMBD.simularCombinacaoVMs(totalRegistroBD,
								mapTipoVmPorQtdVm, slaps, totalVms);

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
		if ( jTFTotalRegistroBD.getText() != null
				&& jTFSlaps.getText() != null
				&& (jCBSmall.isSelected() || jCBLarge.isSelected() || jCBExtra
						.isSelected()) )
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
		jTFQtdSmall.setText("1");
		jTFQtdLarge.setText("1");
		jTFQtdExtra.setText("1");

		jCBSmall.setSelected(true);
		jCBLarge.setSelected(true);
		jCBExtra.setSelected(true);

		jTFSlaps.setText("5");
		mapSimulacao = new TreeMap<EnumTipoVM, List<SimulacaoVO>>();

		ProVMBD.limparMapCombinacaoVms();

		atualizarTabela();

	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField()
	{
		if ( jTFSlaps == null )
		{
			jTFSlaps = new JTextField();
			jTFSlaps.setBounds(new Rectangle(416, 95, 158, 19));
		}
		return jTFSlaps;
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
	private JTable getJTable(final EnumTipoVM enumTipoVM)
	{
		// Obtem o resultado para detalhar a primeira tabela de simulacao.
		final List<SimulacaoVO> lstSimulacaoVO = mapSimulacao.get(enumTipoVM);

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
			return 6;
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
					nomeColuna = "Tipo VM";
					break;

				case 2:
					nomeColuna = "Total Registro";
					break;

				case 3:
					nomeColuna = "Dentro SLA";
					break;

				case 4:
					nomeColuna = "Fora SLA";
					break;

				case 5:
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
					valor = simulacaoVO.getEnumTipoVM().name();
					break;
				case 2:
					valor = simulacaoVO.getTotalRegistro();
					break;

				case 3:
					valor = simulacaoVO.getTotalProcessadosDentroSLA();
					break;

				case 4:
					valor = simulacaoVO.getTotalProcessadoForaSLA();
					break;

				case 5:
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
	public void atualizarTabela()
	{

		jPTables.removeAll();

		if ( mapSimulacao != null )
		{

			int altura = 10;

			for (final Map.Entry<EnumTipoVM, List<SimulacaoVO>> entry : mapSimulacao
					.entrySet())
			{

				final JScrollPane jScrollPane = new JScrollPane();
				jScrollPane.setBounds(new Rectangle(5, altura, 550, 80));

				altura = altura + 164;

				jScrollPane.setVisible(true);
				jScrollPane.setViewportView(getJTable(entry.getKey()));

				jPTables.add(jScrollPane);

			}

			final ProVMBD proVMBD = ProVMBD.getInstancia();

			if ( proVMBD != null )
			{

				final Map<EnumTipoVM, ResultadoVO> mapResultadoSimulacao;

				if ( proVMBD.getResultadoSimulacaoCombinacaoVms() != null )
				{
					mapResultadoSimulacao = proVMBD.getResultadoSimulacaoCombinacaoVms();
				}
				else
				{
					mapResultadoSimulacao = new HashMap<EnumTipoVM, ResultadoVO>();
				}

				int alturaViolacaoSla = 90;
				int alturaValorVMs = 105;
				int alturaValorMultaVMs = 120;

				for (final Map.Entry<EnumTipoVM, ResultadoVO> entryResultadoPorTipoVM : mapResultadoSimulacao
						.entrySet())
				{
					// Obtem os valores de prcViolacaoSLA e Custo com recurso a partir
					// do primeiro resultado;
					final ResultadoVO resultadoVO = entryResultadoPorTipoVM.getValue();

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
					atualizarGraficos(proVMBD.getResultadoSimulacaoCombinacaoVms());
				}
				else
				{
					// jPanel.setVisible(false);
					getJPGraficoCusto().setVisible(false);
					getJPGraficoViolacao().setVisible(false);
				}
			}
		}
		else
		{
			// TODO: Exibir mensagem de erro e limpar campos
			// jLViolacaoSla3
			// .setText("Erro ao tentar alocar recursos, favor reduzir o numero de VMs");
		}
	}

	protected void atualizarGraficos(
			final SortedMap<EnumTipoVM, ResultadoVO> mapResultadoSimulacao)
	{

		if ( !mapResultadoSimulacao.isEmpty() )
		{
			final DefaultCategoryDataset datasetCusto = new DefaultCategoryDataset();
			final DefaultCategoryDataset datasetViolacao = new DefaultCategoryDataset();

			Double custoTotal = 0D;
			for (final Map.Entry<EnumTipoVM, ResultadoVO> entry : mapResultadoSimulacao
					.entrySet())
			{
				final ResultadoVO resultadoVO = entry.getValue();

				// dataset da tabela de custos
				final Double custoTotalPorVm = resultadoVO.getCustoTotal();

				custoTotal = custoTotal + custoTotalPorVm;

				datasetCusto.setValue(custoTotalPorVm, resultadoVO.getTotalRegistro(),
						resultadoVO.getEnumTipoVM().name());

				// dataset da tabela de violacao
				datasetViolacao.setValue(resultadoVO.getPrcViolacaoSLA(), resultadoVO
						.getTotalRegistro(), resultadoVO.getEnumTipoVM().name());
			}

			datasetCusto.setValue(custoTotal, custoTotal, "Total");

			// 1. Grafico de CUSTOS
			final JFreeChart chart = ChartFactory.createBarChart("Custos", null,
					"Custos US$", datasetCusto, PlotOrientation.VERTICAL, false, true,
					false);

			chart.setBackgroundPaint(Color.white);
			chart.getTitle().setPaint(Color.black);
			chart.getTitle().setFont(new Font("SansSerif", Font.BOLD, 12));

			final CategoryPlot p = chart.getCategoryPlot();

			final CategoryAxis domainAxis = p.getDomainAxis();
			domainAxis.setTickLabelPaint(Color.black);
			domainAxis.setTickLabelFont(new Font("SansSerif",
					Font.LAYOUT_NO_LIMIT_CONTEXT, 11));
			domainAxis.setLabelPaint(Color.black);

			final NumberAxis rangeAxis = (NumberAxis) p.getRangeAxis();
			rangeAxis.setTickLabelFont(new Font("SansSerif",
					Font.LAYOUT_NO_LIMIT_CONTEXT, 11));
			rangeAxis.setStandardTickUnits(NumberAxis.createStandardTickUnits());

			final BarRenderer renderer = (BarRenderer) p.getRenderer();
			renderer.setItemLabelGenerator(new StandardCategoryItemLabelGenerator());

			renderer.setItemLabelsVisible(true);

			final Font f = renderer.getBaseItemLabelFont();
			final Font f2 = new Font("SansSerif", f.getStyle(), 11);
			renderer.setBaseItemLabelFont(f2);

			chart.getCategoryPlot().setRenderer(renderer);

			p.setRangeGridlinePaint(Color.blue);
			final ChartPanel cPCusto = new ChartPanel(chart, false);
			cPCusto.setSize(jPGraficoCusto.getWidth(), jPGraficoCusto.getHeight());
			cPCusto.setVisible(true);
			jPGraficoCusto.removeAll();
			jPGraficoCusto.add(cPCusto);
			jPGraficoCusto.revalidate();
			jPGraficoCusto.repaint();
			jPGraficoCusto.setVisible(true);

			// 2. Grafico de VIOLACAO DE SLA
			final JFreeChart chart2 = ChartFactory.createBarChart("% Violacao SLA",
					"Total Registro BD por Tipo VM", "%Violacao SLA", datasetViolacao,
					PlotOrientation.VERTICAL, true, true, false);

			chart2.setBackgroundPaint(Color.white);
			chart2.getTitle().setPaint(Color.black);
			chart2.getTitle().setFont(new Font("SansSerif", Font.BOLD, 12));
			final CategoryPlot p2 = chart2.getCategoryPlot();

			final CategoryAxis domainAxisSLA = p2.getDomainAxis();
			domainAxisSLA.setTickLabelPaint(Color.black);
			domainAxisSLA.setTickLabelFont(new Font("SansSerif",
					Font.LAYOUT_NO_LIMIT_CONTEXT, 11));
			domainAxisSLA.setLabelPaint(Color.black);

			final NumberAxis rangeAxisSLA = (NumberAxis) p2.getRangeAxis();
			rangeAxisSLA.setTickLabelFont(new Font("SansSerif",
					Font.LAYOUT_NO_LIMIT_CONTEXT, 11));
			rangeAxisSLA.setStandardTickUnits(NumberAxis.createStandardTickUnits());

			final BarRenderer rendererSLA = (BarRenderer) p2.getRenderer();
			rendererSLA
					.setItemLabelGenerator(new StandardCategoryItemLabelGenerator());

			rendererSLA.setItemLabelsVisible(true);

			final Font fSLA = rendererSLA.getBaseItemLabelFont();
			final Font f2SLA = new Font("SansSerif", fSLA.getStyle(), 11);
			rendererSLA.setBaseItemLabelFont(f2SLA);

			chart.getCategoryPlot().setRenderer(rendererSLA);

			p2.setRangeGridlinePaint(Color.blue);
			final ChartPanel cPViolacao = new ChartPanel(chart2);
			cPViolacao.setSize(jPGraficoViolacao.getWidth(), jPGraficoViolacao
					.getHeight());
			cPViolacao.setVisible(true);
			jPGraficoViolacao.removeAll();
			jPGraficoViolacao.add(cPViolacao);
			jPGraficoViolacao.revalidate();
			jPGraficoViolacao.repaint();
			jPGraficoViolacao.setVisible(true);

		}
		else
		{
			jPGraficoCusto.setVisible(false);
			jPGraficoViolacao.setVisible(false);
		}

	}

	/**
	 * This method initializes jPBarraDivisoria
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPBarraDivisoria()
	{
		if ( jPBarraDivisoria == null )
		{
			jPBarraDivisoria = new JPanel();
			jPBarraDivisoria.setLayout(new BorderLayout());
			jPBarraDivisoria.setBounds(new Rectangle(17, 129, 981, 2));
			jPBarraDivisoria.setBorder(BorderFactory.createLineBorder(Color.gray));
		}
		return jPBarraDivisoria;
	}

	/**
	 * This method initializes jCBSmall
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getJCBSmall()
	{
		if ( jCBSmall == null )
		{
			jCBSmall = new JCheckBox();
			jCBSmall.setBounds(new Rectangle(10, 15, 72, 21));
			jCBSmall.setText("Small");
		}
		return jCBSmall;
	}

	/**
	 * This method initializes jCBLarge
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getJCBLarge()
	{
		if ( jCBLarge == null )
		{
			jCBLarge = new JCheckBox();
			jCBLarge.setBounds(new Rectangle(10, 42, 73, 15));
			jCBLarge.setText("Large");
		}
		return jCBLarge;
	}

	/**
	 * This method initializes jCBExtra
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private JCheckBox getJCBExtra()
	{
		if ( jCBExtra == null )
		{
			jCBExtra = new JCheckBox();
			jCBExtra.setBounds(new Rectangle(10, 63, 72, 18));
			jCBExtra.setText("Extra");
		}
		return jCBExtra;
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTFQtdSmall()
	{
		if ( jTFQtdSmall == null )
		{
			jTFQtdSmall = new JTextField();
			jTFQtdSmall.setBounds(new Rectangle(83, 15, 60, 19));
		}
		return jTFQtdSmall;
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTFQtdLarge()
	{
		if ( jTFQtdLarge == null )
		{
			jTFQtdLarge = new JTextField();
			jTFQtdLarge.setBounds(new Rectangle(83, 40, 60, 19));
		}
		return jTFQtdLarge;
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTFQtdExtra()
	{
		if ( jTFQtdExtra == null )
		{
			jTFQtdExtra = new JTextField();
			jTFQtdExtra.setBounds(new Rectangle(83, 63, 60, 19));
		}
		return jTFQtdExtra;
	}

	/**
	 * This method initializes jPTipoEQtdVM
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPTipoEQtdVM()
	{
		if ( jPTipoEQtdVM == null )
		{
			jPTipoEQtdVM = new JPanel();
			jPTipoEQtdVM.setLayout(null);
			jPTipoEQtdVM.setBounds(new Rectangle(197, 30, 201, 85));

			jPTipoEQtdVM.add(getJCBSmall(), null);
			jPTipoEQtdVM.add(getJCBLarge(), null);
			jPTipoEQtdVM.add(getJCBExtra(), null);
			jPTipoEQtdVM.add(getJTFQtdSmall(), null);
			jPTipoEQtdVM.add(getJTFQtdLarge(), null);
			jPTipoEQtdVM.add(getJTFQtdExtra(), null);

			jPTipoEQtdVM.setBorder(BorderFactory.createLineBorder(Color.gray));
			jPTipoEQtdVM.setBorder(BorderFactory
					.createTitledBorder("Timpo e Qtd. VMs"));
		}
		return jPTipoEQtdVM;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPGraficoCusto()
	{
		if ( jPGraficoCusto == null )
		{
			jPGraficoCusto = new JPanel();
			jPGraficoCusto.setLayout(null);
			jPGraficoCusto.setBounds(new Rectangle(575, 145, 420, 290));
		}
		return jPGraficoCusto;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPGraficoViolacao()
	{
		if ( jPGraficoViolacao == null )
		{
			jPGraficoViolacao = new JPanel();
			jPGraficoViolacao.setLayout(null);
			jPGraficoViolacao.setBounds(new Rectangle(575, 440, 420, 200));
		}
		return jPGraficoViolacao;
	}

} // @jve:decl-index=0:visual-constraint="-204,-39"

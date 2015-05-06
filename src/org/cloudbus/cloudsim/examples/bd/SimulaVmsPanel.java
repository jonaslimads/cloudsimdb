package org.cloudbus.cloudsim.examples.bd;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.Border;

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
public class SimulaVmsPanel extends JPanel
{

	/**
	 * @author Humberto Lima 12/11/2011 - 07:41:24.
	 */
	private static final long serialVersionUID = 9185744625835839205L;

	private JPanel jPEntradaTotais = null;

	private JTextField jTextField = null;

	private JTextField jTextField1 = null;

	private JTextField jTextField2 = null;

	private JList jLstQtdRegistroBD = null;

	private JScrollPane jSPQtdRegistroBD = null;

	private JPanel jPTiposVms = null;

	private JCheckBox jCBSmall = null;

	private JCheckBox jCBLarge = null;

	private JCheckBox jCBExtra = null;

	private JLabel jLTempoSla = null;

	private JLabel jLQtdRegistroBD = null;

	private JTextField jTFSlaps = null;

	private JTextField jTFQTdRegistroBD = null;

	private JButton jBSimular = null;

	private JButton jBValorPadrao = null;

	private JPanel jPGraficoCusto = null;

	private JPanel jPGraficoViolacao = null;

	private JPanel jPLinha = null;

	private DefaultListModel lmResumo = null;

	private JButton jBRemover = null;

	/**
	 * @author Humberto Lima[humbertolimaa@gmail.com] 12/11/2011 - 08:25:11.
	 */
	public SimulaVmsPanel()
	{
		super();
		initialize();
	}

	/**
	 * @author Humberto Lima[humbertolimaa@gmail.com] 12/11/2011 - 08:25:47.
	 */
	private void initialize()
	{

		setLayout(null);
		this.setSize(ProVMBD.comprimentoTela - 5, ProVMBD.alturaTela - 5);

		// cria os objetos e seta os parametros
		jLQtdRegistroBD = new JLabel();
		jLQtdRegistroBD.setBounds(new Rectangle(15, 33, 143, 15));
		jLQtdRegistroBD.setText("Qtd. Registro BD:");

		jLTempoSla = new JLabel();
		jLTempoSla.setBounds(new Rectangle(548, 118, 143, 15));
		jLTempoSla.setText("Tempo SLA em Seg.:");

		this.add(jLQtdRegistroBD, null);
		this.add(getJTFQtdRegistroBD());

		this.add(getJSPQtdRegistroBD());

		this.add(getJPTiposVms(), null);
		this.add(jLTempoSla, null);
		this.add(getJTFSlaps(), null);
		this.add(getJBSimular(), null);
		this.add(getJBValorPadrao(), null);
		this.add(getJPLinha());

		this.add(getJPGraficoCusto(), null);
		this.add(getJPGraficoViolacao(), null);

		this.add(getJBAdicionar(), null);
		this.add(getJBRemove(), null);
		setValoresDefault();
	}

	public void addCompForBorder(final Border border,
			final JTextField jTextField, final Container container)
	{
		final JPanel comp = new JPanel(new GridLayout(1, 1), false);

		comp.add(jTextField);
		comp.setBorder(border);

		container.add(Box.createRigidArea(new Dimension(0, 10)));
		container.add(comp);
	}

	private JPanel getJPEntradaTotais()
	{
		if ( jPEntradaTotais == null )
		{
			jPEntradaTotais = new JPanel();
			jPEntradaTotais.setLayout(null);
			jPEntradaTotais.setBounds(new Rectangle(15, 33, 236, 125));
			jPEntradaTotais.add(getJTextField(), null);
			jPEntradaTotais.add(getJTextField1(), null);
			jPEntradaTotais.add(getJTextField2(), null);
			jPEntradaTotais.setBorder(BorderFactory.createLineBorder(Color.gray));
			jPEntradaTotais.setBorder(BorderFactory
					.createTitledBorder("Totais Entrada"));

		}
		return jPEntradaTotais;
	}

	private JScrollPane getJSPQtdRegistroBD()
	{
		if ( jSPQtdRegistroBD == null )
		{
			lmResumo = new DefaultListModel();
			lmResumo.addElement("A");
			lmResumo.addElement("B");
			lmResumo.addElement("C");
			lmResumo.addElement("D");
			lmResumo.addElement("E");
			lmResumo.addElement("F");
			lmResumo.addElement("G");
			lmResumo.addElement("H");
			lmResumo.addElement("I");
			lmResumo.addElement("J");
			lmResumo.addElement("K");
			lmResumo.addElement("L");
			lmResumo.addElement("L");
			jLstQtdRegistroBD = new JList(lmResumo);
			// jLstQtdRegistroBD.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			jLstQtdRegistroBD.setSelectedIndex(0);
			// jLstQtdRegistroBD.setLayoutOrientation(JList.VERTICAL);
			// jLstQtdRegistroBD.setBounds(new Rectangle(13, 30, 234, 123));

			jSPQtdRegistroBD = new JScrollPane();
			// jSPQtdRegistroBD.setLayout(null);
			jSPQtdRegistroBD.setBounds(new Rectangle(15, 82, 150, 70));
			jSPQtdRegistroBD.getViewport().setView(jLstQtdRegistroBD);
			// jSPQtdRegistroBD.setBorder(BorderFactory
			// .createTitledBorder("Totais Entrada"));
			jSPQtdRegistroBD.revalidate();
			jSPQtdRegistroBD.repaint();
			jSPQtdRegistroBD.setVisible(true);

		}
		return jSPQtdRegistroBD;
	}

	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPLinha()
	{
		if ( jPLinha == null )
		{
			jPLinha = new JPanel();
			jPLinha.setLayout(new BorderLayout());
			jPLinha.setBounds(new Rectangle(17, 170, 981, 2));
			jPLinha.setBorder(BorderFactory.createLineBorder(Color.gray));
		}
		return jPLinha;
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField()
	{
		if ( jTextField == null )
		{
			jTextField = new JTextField();
			jTextField.setBounds(new Rectangle(21, 23, 107, 19));
		}
		return jTextField;
	}

	/**
	 * This method initializes jTextField1
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField1()
	{
		if ( jTextField1 == null )
		{
			jTextField1 = new JTextField();
			jTextField1.setBounds(new Rectangle(22, 53, 107, 19));
		}
		return jTextField1;
	}

	/**
	 * This method initializes jTextField2
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTextField2()
	{
		if ( jTextField2 == null )
		{
			jTextField2 = new JTextField();
			jTextField2.setBounds(new Rectangle(23, 85, 107, 19));
		}
		return jTextField2;
	}

	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPTiposVms()
	{
		if ( jPTiposVms == null )
		{
			jPTiposVms = new JPanel();
			jPTiposVms.setLayout(null);
			jPTiposVms.setBounds(new Rectangle(275, 33, 260, 125));
			jPTiposVms.add(getJCSmall(), null);
			jPTiposVms.add(getJCBLarge(), null);
			jPTiposVms.add(getJCBExtra(), null);
			jPTiposVms.setBorder(BorderFactory.createLineBorder(Color.gray));
			jPTiposVms.setBorder(BorderFactory.createTitledBorder("Timpos VMs"));

		}
		return jPTiposVms;
	}

	/**
	 * This method initializes jComboBox
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JCheckBox getJCSmall()
	{
		if ( jCBSmall == null )
		{
			jCBSmall = new JCheckBox(ProVMBD.EnumTipoVM.SMALL.name());
			jCBSmall.setBounds(new Rectangle(15, 23, 121, 19));
		}
		return jCBSmall;
	}

	/**
	 * This method initializes jComboBox1
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JCheckBox getJCBLarge()
	{
		if ( jCBLarge == null )
		{
			jCBLarge = new JCheckBox(ProVMBD.EnumTipoVM.LARGE.name());
			jCBLarge.setBounds(new Rectangle(14, 53, 123, 19));
		}
		return jCBLarge;
	}

	/**
	 * This method initializes jComboBox2
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JCheckBox getJCBExtra()
	{
		if ( jCBExtra == null )
		{
			jCBExtra = new JCheckBox(ProVMBD.EnumTipoVM.EXTRA.name());
			jCBExtra.setBounds(new Rectangle(14, 85, 125, 19));
		}
		return jCBExtra;
	}

	/**
	 * This method initializes jTFSlaps
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTFSlaps()
	{
		if ( jTFSlaps == null )
		{
			jTFSlaps = new JTextField();
			jTFSlaps.setBounds(new Rectangle(548, 137, 128, 19));
		}
		return jTFSlaps;
	}

	/**
	 * This method initializes jTFSlaps
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getJTFQtdRegistroBD()
	{
		if ( jTFQTdRegistroBD == null )
		{
			jTFQTdRegistroBD = new JTextField();
			jTFQTdRegistroBD.setBounds(new Rectangle(15, 50, 150, 19));
		}
		return jTFQTdRegistroBD;
	}

	private JButton getJBAdicionar()
	{
		final JButton jBAdicionar = new JButton("+");
		jBAdicionar.setBounds(new Rectangle(167, 51, 50, 19));
		jBAdicionar.addActionListener(new ActionListener()
		{
			public void actionPerformed(final ActionEvent e)
			{
				// Obtem a quantidade de registro a adicionar

				final String text = getJTFQtdRegistroBD().getText();
				try
				{
					// valida se e numero
					NumberUtils.getInt(text);

					if ( text != null && text.length() > 0 && !lmResumo.contains(text) )
					{
						lmResumo.addElement(text);
					}
				}
				catch (final Exception e2)
				{
					// Nao faz nada, o usuario nao digitou numeros
				}

			}
		});

		return jBAdicionar;
	}

	/**
	 * This method initializes jBSimular
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJBSimular()
	{
		if ( jBSimular == null )
		{
			jBSimular = new JButton();
			jBSimular.setBounds(new Rectangle(709, 133, 126, 23));
			jBSimular.setText("Simular");
			jBSimular.addActionListener(new java.awt.event.ActionListener()
			{
				public void actionPerformed(final java.awt.event.ActionEvent e)
				{
					// verifica se todos os campos foram preechidos
					if ( validarCampos() )
					{
						// obtem uma instacia do menu principal
						final ProVMBD proVMBD = ProVMBD.getInstancia();

						final Map<Long, List<EnumTipoVM>> mapTotalAProcessarPorTipoVM = getMapTotalAProcessarPorTipoVM();

						// obtem o tempo de SLA definido em segundos
						final Integer slaps = NumberUtils.getInt(jTFSlaps.getText());

						// executa os processos e retorna o map com resultado da simulacao
						final SortedMap<Long, List<ResultadoVO>> mapResultadoSimulacao = proVMBD
								.simularVMs(mapTotalAProcessarPorTipoVM, slaps, 1);

						atualizarGraficos(mapResultadoSimulacao);

					}
				}
			});
		}
		return jBSimular;
	}

	/**
	 * @author Humberto Lima[humbertolimaa@gmail.com] 12/11/2011 - 19:31:25.
	 * @return
	 */
	protected boolean validarCampos()
	{
		boolean isValido;

		// verifica se todos os campos foram preechidos
		if ( jTFSlaps.getText() != null
				&& lmResumo.size() > 0
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
	 * @author Humberto Lima[humbertolimaa@gmail.com] 12/11/2011 - 19:27:25.
	 * @param mapResultadoSimulacao
	 */
	protected void atualizarGraficos(
			final SortedMap<Long, List<ResultadoVO>> mapResultadoSimulacao)
	{

		if ( mapResultadoSimulacao != null && !mapResultadoSimulacao.isEmpty() )
		{
			final DefaultCategoryDataset datasetCusto = new DefaultCategoryDataset();
			final DefaultCategoryDataset datasetViolacao = new DefaultCategoryDataset();

			for (final Map.Entry<Long, List<ResultadoVO>> entry : mapResultadoSimulacao
					.entrySet())
			{
				for (final ResultadoVO resultadoVO : entry.getValue())
				{
					// dataset da tabela de custos
					datasetCusto.setValue(resultadoVO.getCustoTotal(), resultadoVO
							.getEnumTipoVM().name(), entry.getKey());

					// dataset da tabela de violacao
					datasetViolacao.setValue(resultadoVO.getPrcViolacaoSLA(), resultadoVO
							.getEnumTipoVM().name(), entry.getKey());
				}
			}

			// 1. Grafico de CUSTOS
			final JFreeChart chart = ChartFactory.createBarChart("Custos",
					"Total Entrada", "Custos US$", datasetCusto,
					PlotOrientation.VERTICAL, true, true, false);

			chart.setBackgroundPaint(Color.white);
			chart.getTitle().setPaint(Color.black);
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
					"Total Entrada", "%Violacao SLA", datasetViolacao,
					PlotOrientation.VERTICAL, true, true, false);

			chart2.setBackgroundPaint(Color.white);
			chart2.getTitle().setPaint(Color.black);
			final CategoryPlot p2 = chart2.getCategoryPlot();
			p2.setRangeGridlinePaint(Color.blue);

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
	 * @author Humberto Lima[humbertolimaa@gmail.com] 12/11/2011 - 19:15:17.
	 * @return
	 */
	protected Map<Long, List<EnumTipoVM>> getMapTotalAProcessarPorTipoVM()
	{
		// Map que contera a associacao das VM com as entradas a serem processadas
		final Map<Long, List<EnumTipoVM>> mapTotalAProcessarPorTipoVM = new HashMap<Long, List<EnumTipoVM>>();

		// obtem os tipo de VMs.
		final List<EnumTipoVM> lstTipoVM = new ArrayList<EnumTipoVM>();

		if ( jCBSmall.isSelected() )
		{
			lstTipoVM.add(EnumTipoVM.SMALL);
		}

		if ( jCBLarge.isSelected() )
		{
			lstTipoVM.add(EnumTipoVM.LARGE);
		}
		if ( jCBExtra.isSelected() )
		{
			lstTipoVM.add(EnumTipoVM.EXTRA);
		}

		for (int i = 0; i < lstTipoVM.size(); i++)
		{

			// obtem as entradas a serem processadas
			for (int j = 0; j < lmResumo.size(); j++)
			{
				mapTotalAProcessarPorTipoVM.put(NumberUtils.getLong(lmResumo.get(j)
						.toString()), lstTipoVM);
			}

		}

		return mapTotalAProcessarPorTipoVM;
	}

	/**
	 * This method initializes jBValorPadrao
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJBValorPadrao()
	{
		if ( jBValorPadrao == null )
		{
			jBValorPadrao = new JButton();
			jBValorPadrao.setBounds(new Rectangle(870, 133, 126, 23));
			jBValorPadrao.setText("Valor Padrao");
			jBValorPadrao.addActionListener(new java.awt.event.ActionListener()
			{
				public void actionPerformed(final java.awt.event.ActionEvent e)
				{
					setValoresDefault();
				}
			});
		}
		return jBValorPadrao;
	}

	/**
	 * Limpa todos os campos apos cadastro
	 */
	private void setValoresDefault()
	{
		// seta valores default dos campos
		lmResumo.clear();

		lmResumo.addElement("10000000");
		lmResumo.addElement("20000000");
		lmResumo.addElement("30000000");
		lmResumo.addElement("40000000");
		lmResumo.addElement("50000000");

		jTFQTdRegistroBD.setText(null);

		jCBSmall.setSelected(true);
		jCBLarge.setSelected(true);
		jCBExtra.setSelected(true);

		jTFSlaps.setText("5");

		atualizarGraficos(new TreeMap<Long, List<ResultadoVO>>());

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
			jPGraficoCusto.setBounds(new Rectangle(15, 210, 500, 400));
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
			jPGraficoViolacao.setBounds(new Rectangle(530, 210, 465, 400));
		}
		return jPGraficoViolacao;
	}

	/**
	 * This method initializes addButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJBRemove()
	{
		if ( jBRemover == null )
		{
			jBRemover = new JButton("-");
			jBRemover.setBounds(new Rectangle(166, 83, 50, 18));

			jBRemover.addActionListener(new ActionListener()
			{
				public void actionPerformed(final ActionEvent e)
				{
					// Remove o registro selecionado
					final int index = jLstQtdRegistroBD.getSelectedIndex();

					if ( index >= 0 )
					{
						lmResumo.remove(index);
					}

				}
			});
		}
		return jBRemover;
	}
} // @jve:decl-index=0:visual-constraint="5,30"

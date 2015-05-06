package org.cloudbus.cloudsim.examples.bd;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 * CadastroPanel, representa a tela de cadastro das acoes
 * 
 * @version 1.0
 * @author Humberto Lima
 */
public class SimulaPanel extends JPanel
{

	/**
	 * @author Humberto Lima[humbertolimaa@gmail.com] 12/11/2011 - 07:51:26.
	 */
	private static final long serialVersionUID = 8700068610936238026L;

	/** tabbed pane que ira conter as guias de alteracao **/
	private JTabbedPane jTabbedPane = null;

	/** panel para alteracao do valor de venda da acao **/
	private JPanel jPSimulaQtdVm = null;

	/** panel para alteracao do valor de venda da acao com base no percentual **/
	private JPanel JPSimulaVms = null;

	/** panel para alteracao do valor de venda da acao com base no percentual **/
	private JPanel JPSimulaCombinacaoVms = null;

	private SimulaQtdVmPanel simulaQtdVmPanel = null;

	private SimulaVmsPanel simulaVmsPanel = null;

	private SimulaCombinacaoVmsPanel simulaCombinacaoVmsPanel = null;

	/**
	 * @author Humberto Lima[humbertolimaa@gmail.com] 12/11/2011 - 07:51:42.
	 */
	public SimulaPanel()
	{
		super();
		initialize();
	}

	/**
	 * @author Humberto Lima[humbertolimaa@gmail.com] 12/11/2011 - 07:53:39.
	 */
	private void initialize()
	{
		// cria os objetos e seta os parametros
		final GridLayout gridLayout = new GridLayout();
		gridLayout.setRows(1);

		setLayout(null);
		this.setSize(ProVMBD.comprimentoTela - 1, ProVMBD.alturaTela - 1);
		jTabbedPane = new JTabbedPane();
		jTabbedPane.setName("jTabbedPane");
		jTabbedPane.setLocation(new Point(0, 0));
		jTabbedPane.setSize(new Dimension(ProVMBD.comprimentoTela - 2,
				ProVMBD.alturaTela - 2));
		this.add(jTabbedPane, jTabbedPane.getName());

		// cria as guias
		jPSimulaQtdVm = new JPanel();
		JPSimulaVms = new JPanel();
		JPSimulaCombinacaoVms = new JPanel();

		jPSimulaQtdVm.setLayout(null);
		JPSimulaVms.setLayout(null);
		JPSimulaCombinacaoVms.setLayout(null);

		// cria a instancia dos panels e adiciona as guias
		simulaVmsPanel = new SimulaVmsPanel();
		simulaQtdVmPanel = new SimulaQtdVmPanel();
		simulaCombinacaoVmsPanel = new SimulaCombinacaoVmsPanel();

		JPSimulaVms.add(simulaVmsPanel, null);
		jPSimulaQtdVm.add(simulaQtdVmPanel, null);
		JPSimulaCombinacaoVms.add(simulaCombinacaoVmsPanel, null);

		// cria as guias do tabbedpane e adiciona as guias ao tabbedpane
		jTabbedPane.addTab("Avaliar Custo / SLA entre VMs", JPSimulaVms);
		jTabbedPane.addTab("Avaliar Custo / SLA por Qtd. VM", jPSimulaQtdVm);
		jTabbedPane.addTab("Avaliar Custo / SLA por Combinacao VM",
				JPSimulaCombinacaoVms);

	}
}

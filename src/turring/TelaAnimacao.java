package turring;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

/**
 *
 * @author Anderson, Carlos e Jean
 * @since 26/05/2019 
 */
public class TelaAnimacao extends javax.swing.JFrame {

    private String[] fita;
    private Object[][] matrizTabela;
    private String[] colunasTabela;
    private ArrayList<JLabel> fitaDesenho = new ArrayList();
    private int inicio;
    private int fim;
    private String colunaValor;
    private String[] valorCelulaAtual;
    private String valorGravar;
    private String valorPosicionarFita;
    private String direcaoFita;
    private int estadoAtual;
    private int posicaoFita;
    private boolean start;
    private int loopInfinito;
    private int colunaIndex;
    private int velocidadeExecucao;
    private boolean parar;
    private Thread thread;
    private JPanel jPanelAtual;

    /**
     * Creates new form TelaAnimacao
     */
    public TelaAnimacao() {
        initComponents();
    }

    public TelaAnimacao(Object[][] matrizTabelaRecebida,
            String[] colunasTab, String[] palavraEntrada, int inicio, int fim) {
        this.matrizTabela = matrizTabelaRecebida;
        this.colunasTabela = colunasTab;
        this.fita = palavraEntrada;
        this.inicio = inicio;
        this.fim = fim;
        this.colunaValor = "*"; //coluna padrão que inicia a máquina
        this.start = true;
        this.loopInfinito = 0;
        this.posicaoFita = 0;
        this.parar = false;

        initComponents();

        jTableMatrizPrincipal.setModel(new javax.swing.table.DefaultTableModel(matrizTabela, colunasTabela));

        //definindo que a linha seleciona terá fundo amarela e letra preta
        jTableMatrizPrincipal.setSelectionBackground(Color.yellow);
        jTableMatrizPrincipal.setSelectionForeground(Color.black);

        jComboBoxVelocidade.addItem("Normal");
        jComboBoxVelocidade.addItem("Rápida");
        jComboBoxVelocidade.addItem("Lenta");

        
        jLabelMovimento.setText("Fita pronta na posição '0'");
        jLabelTroca.setText("Clique em 'Próximo Passo' para continuar");
        jPanelDaFita.removeAll();
        iniciarFita();
        fitaDesenho.get(0).setBackground(Color.yellow);
    }

    private void iniciarFita() {
        fitaDesenho.clear();
        jPanelAtual=new JPanel();
        jPanelAtual.setBackground(Color.white);
        jPanelAtual.setOpaque(true);
        jPanelAtual.setBorder(new LineBorder(Color.BLACK, 2));
        JLabel jLabel;
        for (String fita1 : fita) {//Adiciona n labels
            jLabel = new JLabel(String.valueOf(fita1)); //Cria o label já com o texto definido
            jLabel.setBorder(new LineBorder(Color.BLACK, 2));
            jLabel.setBackground(Color.white);
            jLabel.setOpaque(true);
            jLabel.setPreferredSize(new Dimension(25, 25));//Define o tamanho
            jLabel.setHorizontalAlignment(SwingConstants.CENTER);//Define o texto centralizado
            fitaDesenho.add(jLabel);//Adiciona na lista, que pode ser utilizada depois
            jPanelAtual.add(jLabel);//Adiciona na painel
        }
        
        jPanelDaFita.add(jPanelAtual);
        jScrollPane1.getVerticalScrollBar().setValue(jScrollPane1.getVerticalScrollBar().getMaximum());
        
    }

    private void proximoPasso() {

        jTableMatrizPrincipal.setFocusCycleRoot(true);
        jComboBoxVelocidade.setFocusable(false);
        jButtonComecar.setFocusable(false);
        jButtonParar.setFocusable(false);

        if (inicio == fim) {
            JOptionPane.showMessageDialog(rootPane, "Chegou ao fim! ACEITA!");
            jButtonProximoPasso.setVisible(false);
            jButtonComecar.setVisible(false);
            jLabelEstado.setText("ACEITOU!");
            this.parar = true;
            return;
        }

        if (loopInfinito == 100) {
            JOptionPane.showMessageDialog(rootPane, "Loop Infinito! REJEITA!");
            jButtonProximoPasso.setVisible(false);
            jButtonComecar.setVisible(false);
            jLabelEstado.setText("REJEITOU!");
            this.parar = true;
            return;
        }

        if (posicaoFita > fita.length - 1 || posicaoFita < 0) {
            JOptionPane.showMessageDialog(rootPane, "Limite da Fita Excedido! REJEITA!");
            jButtonProximoPasso.setVisible(false);
            jButtonComecar.setVisible(false);
            jLabelEstado.setText("REJEITOU!");
            this.parar = true;
            return;
        }
        colunaValor = fita[posicaoFita];
        if (jTableMatrizPrincipal.getValueAt(inicio, procurarColuna(colunaValor)) == null) {
            JOptionPane.showMessageDialog(rootPane, "Referência nula, REJEITA.");
            jButtonProximoPasso.setVisible(false);
            jButtonComecar.setVisible(false);
            jLabelEstado.setText("REJEITOU!");
            this.parar = true;
            return;
        }

        colunaIndex = procurarColuna(colunaValor); //pega posição da coluna que está o valor p/ mover

        String aux = jTableMatrizPrincipal.getValueAt(inicio, colunaIndex).toString(); //pega valor da cel

        jTableMatrizPrincipal.setRowSelectionInterval(inicio, inicio); //seleciona a linha
        jTableMatrizPrincipal.setColumnSelectionInterval(colunaIndex, colunaIndex); //seleciona a coluna

        valorCelulaAtual = aux.split(",");
        estadoAtual = Integer.parseInt(valorCelulaAtual[0].replace("q", ""));
        valorGravar = valorCelulaAtual[1].trim();
        direcaoFita = valorCelulaAtual[2].trim();

        fita[posicaoFita] = valorGravar;
        jLabelMovimento.setText("Gravou '" + valorGravar + "' na fita");
        fitaDesenho.get(posicaoFita).setText(valorGravar);
        //preparando para o próximo passo
        jPanelAtual.setBackground(Color.lightGray);
        iniciarFita();
        inicio = estadoAtual;

        switch (direcaoFita) {
            case "D":
                jLabelTroca.setText("Moveu a fita para DIREITA");
                fitaDesenho.get(posicaoFita).setBackground(Color.white);
                if (posicaoFita + 1 <= fita.length - 1) {
                    fitaDesenho.get(posicaoFita + 1).setBackground(Color.yellow);
                }
                posicaoFita++;
                break;

            case "E":
                jLabelTroca.setText("Moveu a fita para ESQUERDA");
                fitaDesenho.get(posicaoFita).setBackground(Color.white);
                if (posicaoFita - 1 > -1) {
                    fitaDesenho.get(posicaoFita - 1).setBackground(Color.yellow);
                }
                posicaoFita--;
                break;

        }
        loopInfinito++;
    }

    private void executar() {
        this.thread = new Thread(() -> {
            try {
                executarAutomatico();
            } catch (InterruptedException ex) {
                Logger.getLogger(TelaAnimacao.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        this.thread.start();
    }

    private void executarAutomatico() throws InterruptedException {
        while (!this.parar) {
            proximoPasso();
            this.thread.sleep(velocidadeExecucao);
        }
    }

    private int procurarColuna(String str) {
        for (int i = 0; i < colunasTabela.length; i++) {
            if (jTableMatrizPrincipal.getColumnName(i).equals(str)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanelDaFita = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableMatrizPrincipal = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jButtonParar = new javax.swing.JButton();
        jButtonComecar = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jComboBoxVelocidade = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jButtonProximoPasso = new javax.swing.JButton();
        jLabelTroca = new javax.swing.JLabel();
        jLabelMovimento = new javax.swing.JLabel();
        jLabelEstado = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setModalExclusionType(java.awt.Dialog.ModalExclusionType.APPLICATION_EXCLUDE);

        jLabel1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Execução da Palavra");

        jPanelDaFita.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                jPanelDaFitaComponentResized(evt);
            }
        });
        jPanelDaFita.setLayout(new javax.swing.BoxLayout(jPanelDaFita, javax.swing.BoxLayout.PAGE_AXIS));
        jScrollPane1.setViewportView(jPanelDaFita);

        jTableMatrizPrincipal.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(jTableMatrizPrincipal);

        jButtonParar.setText("Parar");
        jButtonParar.setPreferredSize(new java.awt.Dimension(75, 23));
        jButtonParar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPararActionPerformed(evt);
            }
        });

        jButtonComecar.setText("Começar");
        jButtonComecar.setPreferredSize(new java.awt.Dimension(200, 23));
        jButtonComecar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonComecarActionPerformed(evt);
            }
        });

        jLabel4.setText("Velocidade:");

        jComboBoxVelocidade.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBoxVelocidadeItemStateChanged(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Execução Automática");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Execução Manual");

        jButtonProximoPasso.setText("Próximo Passo");
        jButtonProximoPasso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonProximoPassoActionPerformed(evt);
            }
        });

        jLabelTroca.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabelTroca.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelTroca.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabelMovimento.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabelMovimento.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelMovimento.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabelEstado.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        jLabelEstado.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelEstado.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButtonProximoPasso)
                    .addComponent(jLabel2))
                .addGap(18, 18, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelMovimento, javax.swing.GroupLayout.PREFERRED_SIZE, 383, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelTroca, javax.swing.GroupLayout.PREFERRED_SIZE, 383, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 383, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jComboBoxVelocidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButtonComecar, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonParar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jButtonProximoPasso, jLabel2});

        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabelMovimento, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboBoxVelocidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4)
                            .addComponent(jLabelTroca, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(jButtonProximoPasso)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButtonComecar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButtonParar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabelEstado))
                .addContainerGap(40, Short.MAX_VALUE))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jButtonComecar, jButtonParar});

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 708, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(13, 13, 13)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonProximoPassoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonProximoPassoActionPerformed
        jButtonProximoPasso.setFocusable(false);
        jTableMatrizPrincipal.setFocusCycleRoot(true);
        proximoPasso();
    }//GEN-LAST:event_jButtonProximoPassoActionPerformed

    private void jComboBoxVelocidadeItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBoxVelocidadeItemStateChanged
        if (jComboBoxVelocidade.getSelectedItem() == "Normal") {
            velocidadeExecucao = 800;
        } else if (jComboBoxVelocidade.getSelectedItem() == "Rápida") {
            velocidadeExecucao = 200;
        } else {
            velocidadeExecucao = 1600;
        }
    }//GEN-LAST:event_jComboBoxVelocidadeItemStateChanged

    private void jButtonComecarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonComecarActionPerformed
        executar();
    }//GEN-LAST:event_jButtonComecarActionPerformed

    private void jButtonPararActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPararActionPerformed
        this.thread.interrupt();

    }//GEN-LAST:event_jButtonPararActionPerformed

    private void jPanelDaFitaComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jPanelDaFitaComponentResized
        jScrollPane1.getVerticalScrollBar().setValue(jScrollPane1.getVerticalScrollBar().getMaximum());
    }//GEN-LAST:event_jPanelDaFitaComponentResized


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonComecar;
    private javax.swing.JButton jButtonParar;
    private javax.swing.JButton jButtonProximoPasso;
    private javax.swing.JComboBox jComboBoxVelocidade;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabelEstado;
    private javax.swing.JLabel jLabelMovimento;
    private javax.swing.JLabel jLabelTroca;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanelDaFita;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTableMatrizPrincipal;
    // End of variables declaration//GEN-END:variables

}

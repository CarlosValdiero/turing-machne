package turring;

import javax.swing.JOptionPane;

/**
 *
 * @author Anderson, Carlos e Jean
 * @since 26/05/2019
 */
public class TelaTurring extends javax.swing.JFrame {

    private String[] palavraEntrada;
    private boolean liberaExecução;
    private int numLinhas;
    private int numColunas;
    private String[] colunasTabPrincipal;
    private Object[][] matrizTabela;

    /**
     * Creates new form TelaTurring
     */
    public TelaTurring() {

        initComponents();
        liberaExecução = false;

    }

    private void criaTabela() {

        //verificando se foi digitado um alfabeto
        if (jtfAlfabeto.getText() == null || jtfAlfabeto.getText().equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(rootPane, "Alfabeto é obrigatório!");
            return;
        }

        numLinhas = Integer.parseInt(jspNEstados.getModel().getValue().toString());
        //verificando se o número de estados digitados é suficiente para a máquina
        if (numLinhas < 2) {
            JOptionPane.showMessageDialog(rootPane, "Número de estados deve ser maior que 1!");
            return;
        }

        String alfabeto;
        if (jtfAlfabetoAux.getText().equalsIgnoreCase("")) {
            alfabeto = "Estado,*," + jtfAlfabeto.getText() + ",β";
        } else {
            alfabeto = "Estado,*," + jtfAlfabeto.getText() + "," + jtfAlfabetoAux.getText() + ",β";
        }
        colunasTabPrincipal = alfabeto.split(",");
        // monta as colunas conforme o num informado
        numColunas = colunasTabPrincipal.length;

        //inserindo os primeiros valores da tabela para o objeto
        Object[][] matrizTabelaAux = new Object[numLinhas][numColunas];
        jcbInicio.removeAllItems();
        jcbFim.removeAllItems();
        
        for (int i = 0; i < numLinhas; i++) {
            // Seta os estado na coluna 0 da tabela
            matrizTabelaAux[i][0] = "q" + i;

            // popula os combobox estadoInicio e EstadoFim
            jcbInicio.addItem("q" + i);
            jcbFim.addItem("q" + i);
        }

        tabelaPrincipal.setModel(new javax.swing.table.DefaultTableModel(matrizTabelaAux, colunasTabPrincipal));
        liberaExecução = true;
    }

    //Retorna os dados da tabela principal em uma matriz de objetos
    public void capturarMatriz() {

        this.matrizTabela = new Object[numLinhas][numColunas];

        for (int l = 0; l < numLinhas; l++) {
            for (int c = 0; c < numColunas; c++) {
                this.matrizTabela[l][c] = tabelaPrincipal.getValueAt(l, c);
            }
        }
    }

    private void setCelula(Object obj, int numlinha, int numcoluna) { //
        tabelaPrincipal.setValueAt(obj, numlinha, numcoluna);
    }

    public void limparTela() {
        jtfPalavra.setText("");
        jtfAlfabeto.setText("");
        jtfAlfabetoAux.setText("");
        jcbInicio.removeAllItems();
        jcbFim.removeAllItems();
        jspNEstados.setValue(0);
        Object[][] Linhas_auto;
        Linhas_auto = new Object[2][];
        tabelaPrincipal.setModel(new javax.swing.table.DefaultTableModel(Linhas_auto, 1));
        liberaExecução = false;
    }

    public void insereExemploAceita() {
        limparTela();

        jtfAlfabeto.setText("a,b");
        jtfAlfabetoAux.setText("A,B");
        jspNEstados.setValue(5);
        this.criaTabela();
        jcbInicio.setSelectedIndex(0);
        jcbFim.setSelectedIndex(4);

        this.setCelula("q0,*,D", 0, 1);
        this.setCelula("q1,A,D", 0, 2);
        this.setCelula("q3,B,D", 0, 3);
        this.setCelula("q0,A,D", 0, 4);
        this.setCelula("q0,B,D", 0, 5);
        this.setCelula("q4,β,D", 0, 6);

        this.setCelula("q1,a,D", 1, 2);
        this.setCelula("q2,B,E", 1, 3);
        this.setCelula("q1,A,D", 1, 4);
        this.setCelula("q1,B,D", 1, 5);

        this.setCelula("q0,*,D", 2, 1);
        this.setCelula("q2,a,E", 2, 2);
        this.setCelula("q2,b,E", 2, 3);
        this.setCelula("q2,A,E", 2, 4);
        this.setCelula("q2,B,E", 2, 5);

        this.setCelula("q2,A,E", 3, 2);
        this.setCelula("q3,b,D", 3, 3);
        this.setCelula("q3,A,D", 3, 4);
        this.setCelula("q3,B,D", 3, 5);

        //this.setCelula("Fim", 4, 0);
        this.jtfPalavra.setText("*abbabaβββ");
    }

    public void insereExemploLoop() {

        limparTela();

        jtfAlfabeto.setText("a,b");
        jtfAlfabetoAux.setText("A,B");
        jspNEstados.setValue(5);
        this.criaTabela();

        jcbInicio.setSelectedIndex(0);
        jcbFim.setSelectedIndex(4);

        this.setCelula("q0,*,D", 0, 1);
        this.setCelula("q0,b,D", 0, 2);
        this.setCelula("q0,b,E", 0, 3);
        this.setCelula("q0,A,D", 0, 4);
        this.setCelula("q0,B,D", 0, 5);
        this.setCelula("q4,β,D", 0, 6);

        this.setCelula("q1,a,D", 1, 2);
        this.setCelula("q2,a,E", 1, 3);
        this.setCelula("q1,A,D", 1, 4);
        this.setCelula("q1,B,D", 1, 5);

        this.setCelula("q0,*,D", 2, 1);
        this.setCelula("q2,a,E", 2, 2);
        this.setCelula("q2,b,E", 2, 3);
        this.setCelula("q2,A,E", 2, 4);
        this.setCelula("q2,B,E", 2, 5);

        this.setCelula("q2,A,E", 3, 2);
        this.setCelula("q3,b,D", 3, 3);
        this.setCelula("q3,A,D", 3, 4);
        this.setCelula("q3,B,D", 3, 5);

        this.jtfPalavra.setText("*aBbabaAβββ");

    }

    public void inserirExemploRejeita() {
        limparTela();

        jtfAlfabeto.setText("a,b");
        jtfAlfabetoAux.setText("A,B");
        jspNEstados.setValue(5);
        this.criaTabela();

        jcbInicio.setSelectedIndex(0);
        jcbFim.setSelectedIndex(4);

        this.setCelula("q0,*,D", 0, 1);
        this.setCelula("q1,A,D", 0, 2);
        this.setCelula("q3,B,D", 0, 3);
        this.setCelula("q0,A,D", 0, 4);
        this.setCelula("q0,B,D", 0, 5);
        this.setCelula("q4,β,D", 0, 6);

        this.setCelula("q1,a,D", 1, 2);
        this.setCelula("q2,B,E", 1, 3);
        this.setCelula("q1,A,D", 1, 4);
        this.setCelula("q1,B,D", 1, 5);

        this.setCelula("q0,*,D", 2, 1);
        this.setCelula("q2,a,E", 2, 2);
        this.setCelula("q2,b,E", 2, 3);
        this.setCelula("q2,A,E", 2, 4);
        this.setCelula("q2,B,E", 2, 5);

        this.setCelula("q2,A,E", 3, 2);
        this.setCelula("q3,b,D", 3, 3);
        this.setCelula("q3,A,D", 3, 4);
        this.setCelula("q3,B,D", 3, 5);

        this.jtfPalavra.setText("*abaabaβββ");
    }

    /**
     * This metho is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jtfPalavra = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        btnExecuta = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelaPrincipal = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        btnGerarTabela = new javax.swing.JButton();
        jtfAlfabeto = new javax.swing.JTextField();
        jtfAlfabetoAux = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jcbInicio = new javax.swing.JComboBox<String>();
        jspNEstados = new javax.swing.JSpinner();
        jcbFim = new javax.swing.JComboBox<String>();
        jButtonAddSimbolo = new javax.swing.JButton();
        jButtonAddSimboloTabela = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItemNovo = new javax.swing.JMenuItem();
        jMenuItemAceita = new javax.swing.JMenuItem();
        jMenuItemRejeita = new javax.swing.JMenuItem();
        jMenuItemLoop = new javax.swing.JMenuItem();
        jMenuItemAjuda = new javax.swing.JMenuItem();
        jMenuItemSobre = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        jLabel1.setText("Máquina de Turring");

        jLabel2.setText("Palavra:");

        btnExecuta.setText("Pronto");
        btnExecuta.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnExecutaMouseClicked(evt);
            }
        });
        btnExecuta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExecutaActionPerformed(evt);
            }
        });

        tabelaPrincipal.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Estado", "*", "a", "b", "ß"
            }
        ));
        jScrollPane1.setViewportView(tabelaPrincipal);

        jScrollPane2.setViewportView(jScrollPane1);

        jLabel3.setText("Alfabeto:");

        jLabel4.setText("Alfabeto Auxiliar:");

        jLabel5.setText("Estados");

        btnGerarTabela.setText("Gerar Tabela");
        btnGerarTabela.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnGerarTabelaMouseClicked(evt);
            }
        });

        jLabel6.setText("Estado Inicial:");

        jLabel7.setText("Estado Final:");

        jButtonAddSimbolo.setText("β");
        jButtonAddSimbolo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAddSimboloActionPerformed(evt);
            }
        });

        jButtonAddSimboloTabela.setText("β");
        jButtonAddSimboloTabela.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAddSimboloTabelaActionPerformed(evt);
            }
        });

        jMenu1.setText("Opções");
        jMenu1.setActionCommand("Opções");

        jMenuItemNovo.setText("Novo");
        jMenuItemNovo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemNovoActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItemNovo);

        jMenuItemAceita.setText("Exemplo Aceita");
        jMenuItemAceita.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemAceitaActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItemAceita);

        jMenuItemRejeita.setText("Exemplo Rejeita");
        jMenuItemRejeita.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemRejeitaActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItemRejeita);

        jMenuItemLoop.setText("Exemplo Loop Infinito");
        jMenuItemLoop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemLoopActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItemLoop);

        jMenuItemAjuda.setText("Ajuda");
        jMenuItemAjuda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemAjudaActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItemAjuda);

        jMenuItemSobre.setText("Sobre");
        jMenuItemSobre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemSobreActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItemSobre);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 632, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(65, 65, 65)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(30, 30, 30))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(35, 35, 35)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jcbFim, 0, 51, Short.MAX_VALUE)
                    .addComponent(jcbInicio, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jtfPalavra, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonAddSimbolo)
                .addGap(37, 37, 37))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnGerarTabela, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(133, 133, 133)
                        .addComponent(jButtonAddSimboloTabela))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(84, 84, 84)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtfAlfabeto, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(40, 40, 40)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jtfAlfabetoAux, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(39, 39, 39)
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jspNEstados)))))
                .addGap(84, 84, 84))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnExecuta, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(245, 245, 245))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jtfAlfabeto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jtfAlfabetoAux, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jspNEstados, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5)
                            .addComponent(jLabel4))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(19, 19, 19)
                                .addComponent(btnGerarTabela)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButtonAddSimboloTabela)
                                .addGap(18, 18, 18)))))
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jtfPalavra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2)
                            .addComponent(jButtonAddSimbolo))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(jcbInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(jcbFim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(2, 2, 2)
                .addComponent(btnExecuta)
                .addGap(31, 31, 31))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnGerarTabelaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGerarTabelaMouseClicked
        criaTabela();

    }//GEN-LAST:event_btnGerarTabelaMouseClicked

    private void btnExecutaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnExecutaMouseClicked
        if (!liberaExecução) {
            JOptionPane.showMessageDialog(rootPane, "É necessário gerar a tabela primeiro.");
            return;
        }

        if (jcbFim.getSelectedIndex() == jcbInicio.getSelectedIndex()) {
            JOptionPane.showMessageDialog(rootPane, "Início não pode ser igual ao fim!");
            return;
        }

        String palavraAux = jtfPalavra.getText();
        if (palavraAux.equals("") || palavraAux.equals(" ")) {
            JOptionPane.showMessageDialog(rootPane, "Digite uma palavra para a execução!");
            return;
        }

        this.palavraEntrada = palavraAux.split("");
        int inicio = jcbInicio.getSelectedIndex();
        int fim = jcbFim.getSelectedIndex();

        capturarMatriz();
        //this.hide();
        TelaAnimacao telaAnimacao = new TelaAnimacao(this.matrizTabela,
                this.colunasTabPrincipal, this.palavraEntrada, inicio, fim);
        telaAnimacao.setVisible(true);

    }//GEN-LAST:event_btnExecutaMouseClicked

    private void btnExecutaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExecutaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnExecutaActionPerformed

    private void jButtonAddSimboloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAddSimboloActionPerformed
        String aux = jtfPalavra.getText();
        jtfPalavra.setText(aux + "β");
    }//GEN-LAST:event_jButtonAddSimboloActionPerformed

    private void jButtonAddSimboloTabelaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAddSimboloTabelaActionPerformed
        try {
            int linha = tabelaPrincipal.getSelectedRow();
            int coluna = tabelaPrincipal.getSelectedColumn();
            String aux = tabelaPrincipal.getValueAt(linha, coluna).toString();
            tabelaPrincipal.setValueAt(aux + "β", linha, coluna);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, "Selecione editar célula, enter e depois clique no botão!");
        }
    }//GEN-LAST:event_jButtonAddSimboloTabelaActionPerformed

    private void jMenuItemAceitaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemAceitaActionPerformed
        insereExemploAceita();
    }//GEN-LAST:event_jMenuItemAceitaActionPerformed

    private void jMenuItemNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemNovoActionPerformed
        limparTela();
    }//GEN-LAST:event_jMenuItemNovoActionPerformed

    private void jMenuItemRejeitaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemRejeitaActionPerformed
        inserirExemploRejeita();
    }//GEN-LAST:event_jMenuItemRejeitaActionPerformed

    private void jMenuItemLoopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemLoopActionPerformed
        insereExemploLoop();
    }//GEN-LAST:event_jMenuItemLoopActionPerformed

    private void jMenuItemAjudaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemAjudaActionPerformed
        StringBuilder mensagem = new StringBuilder();
        mensagem.append("1º - Preencha o(s) alfabeto(s).\n");
        mensagem.append("2º - Preencha quantidade de estados.\n");
        mensagem.append("3º - Clique em 'Gerar Tabela'.\n");
        mensagem.append("4º - Defina Início e Fim.\n");
        mensagem.append("5º - Defina uma palavra de entrada.\n");
        mensagem.append("6º - Clique em 'Pronto'.\n");
        mensagem.append("\n");
        mensagem.append("* = representa Início.\n");
        mensagem.append("β = representa Vazio.\n");
        JOptionPane.showMessageDialog(rootPane, mensagem);
    }//GEN-LAST:event_jMenuItemAjudaActionPerformed

    private void jMenuItemSobreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemSobreActionPerformed
        StringBuilder mensagem = new StringBuilder();
        mensagem.append("Trabalho para disciplina de Teoria da Computação.\n");
        mensagem.append("Grupo:\n");
        mensagem.append("- Anderson Felipe\n");
        mensagem.append("- Carlos Valdiero\n");
        mensagem.append("- Jean Justen\n");
        mensagem.append("\n");
        mensagem.append("--Ciência da Computação--\n");
        JOptionPane.showMessageDialog(rootPane, mensagem);        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItemSobreActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TelaTurring.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaTurring.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaTurring.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaTurring.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaTurring().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnExecuta;
    private javax.swing.JButton btnGerarTabela;
    private javax.swing.JButton jButtonAddSimbolo;
    private javax.swing.JButton jButtonAddSimboloTabela;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItemAceita;
    private javax.swing.JMenuItem jMenuItemAjuda;
    private javax.swing.JMenuItem jMenuItemLoop;
    private javax.swing.JMenuItem jMenuItemNovo;
    private javax.swing.JMenuItem jMenuItemRejeita;
    private javax.swing.JMenuItem jMenuItemSobre;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JComboBox<String> jcbFim;
    private javax.swing.JComboBox<String> jcbInicio;
    private javax.swing.JSpinner jspNEstados;
    private javax.swing.JTextField jtfAlfabeto;
    private javax.swing.JTextField jtfAlfabetoAux;
    private javax.swing.JTextField jtfPalavra;
    private javax.swing.JTable tabelaPrincipal;
    // End of variables declaration//GEN-END:variables

}

/*      */ package control_entrada;
/*      */ import com.digitalpersona.onetouch.DPFPDataPurpose;
/*      */ import com.digitalpersona.onetouch.DPFPFeatureSet;
/*      */ import com.digitalpersona.onetouch.DPFPGlobal;
/*      */ import com.digitalpersona.onetouch.DPFPSample;
/*      */ import com.digitalpersona.onetouch.DPFPTemplate;
/*      */ import com.digitalpersona.onetouch.capture.DPFPCapture;
/*      */ import com.digitalpersona.onetouch.capture.event.DPFPDataAdapter;
/*      */ import com.digitalpersona.onetouch.capture.event.DPFPDataEvent;
/*      */ import com.digitalpersona.onetouch.capture.event.DPFPErrorAdapter;
/*      */ import com.digitalpersona.onetouch.capture.event.DPFPErrorEvent;
/*      */ import com.digitalpersona.onetouch.capture.event.DPFPErrorListener;
/*      */ import com.digitalpersona.onetouch.capture.event.DPFPReaderStatusAdapter;
/*      */ import com.digitalpersona.onetouch.capture.event.DPFPReaderStatusEvent;
/*      */ import com.digitalpersona.onetouch.capture.event.DPFPSensorAdapter;
/*      */ import com.digitalpersona.onetouch.capture.event.DPFPSensorEvent;
/*      */ import com.digitalpersona.onetouch.capture.event.DPFPSensorListener;
/*      */ import com.digitalpersona.onetouch.processing.DPFPFeatureExtraction;
/*      */ import com.digitalpersona.onetouch.processing.DPFPImageQualityException;
/*      */ import com.digitalpersona.onetouch.processing.DPFPTemplateStatus;
/*      */ import com.digitalpersona.onetouch.processing.DPFPEnrollment;
import com.digitalpersona.onetouch.verification.DPFPVerification;
import com.digitalpersona.onetouch.verification.DPFPVerificationResult;
import com.digitalpersona.onetouch.capture.event.DPFPDataListener;
import com.digitalpersona.onetouch.capture.event.DPFPReaderStatusListener;
import java.awt.Component;
/*      */ import controlador.conexion;
/*      */ import java.awt.BorderLayout;
/*      */ import java.awt.Dimension;
/*      */ import java.awt.EventQueue;
/*      */ import java.awt.Image;
/*      */ import java.awt.event.ActionEvent;
/*      */ import java.awt.event.ActionListener;
/*      */ import java.awt.event.KeyAdapter;
/*      */ import java.awt.event.KeyEvent;
/*      */ import java.awt.event.WindowAdapter;
/*      */ import java.awt.event.WindowEvent;
/*      */ import java.io.ByteArrayInputStream;
/*      */ import java.io.IOException;
/*      */ import java.sql.Connection;
/*      */ import java.sql.PreparedStatement;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.util.logging.Level;
/*      */ import javax.swing.BorderFactory;
/*      */ import javax.swing.GroupLayout;
/*      */ import javax.swing.Icon;
/*      */ import javax.swing.ImageIcon;
/*      */ import javax.swing.JButton;
/*      */ import javax.swing.JLabel;
/*      */ import javax.swing.JOptionPane;
/*      */ import javax.swing.JPanel;
/*      */ import javax.swing.JScrollPane;
/*      */ import javax.swing.JTextArea;
/*      */ import javax.swing.JTextField;
/*      */ import javax.swing.LayoutStyle;
/*      */ import javax.swing.SwingUtilities;
/*      */ import javax.swing.UIManager;
/*      */ import javax.swing.JDialog;
/*      */ import java.awt.Font;
/*      */ import java.awt.Component;
/*      */ import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComboBox;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

/*      */ public class CapturaHuellaEnroler extends JDialog {
/*   56 */   Object EncontroHuella = null;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private DPFPCapture Lector;
/*      */ 
/*      */ 
/*      */   
/*      */   private DPFPEnrollment Reclutador;
/*      */ 
/*      */ 
/*      */   
/*      */   private DPFPVerification Verificador;
/*      */ 
/*      */ 
/*      */   
/*      */   private DPFPTemplate template;
/*      */   private JComboBox<String> cmbEmpresa;
/*      */   private JComboBox<String> cmbEmpleado;
/*      */   private List<String> empresaIds = new ArrayList<>();
/*      */   private List<String> empleadoDocNums = new ArrayList<>();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void initComponents() {
/*   79 */     this.jLcedula1 = new JLabel();
/*   80 */     this.jLcedula3 = new JLabel();
/*   81 */     this.jTnombre1 = new JTextField();
/*   82 */     this.panHuellas = new JPanel();
/*   83 */     this.jPanel1 = new JPanel();
/*   84 */     this.lblImagenHuella = new JLabel();
/*   85 */     this.panBtns = new JPanel();
/*   86 */     this.jPanel2 = new JPanel();
/*   87 */     this.btnSalir = new JButton();
/*   88 */     this.btnVerificar = new JButton();
/*   89 */     this.btnIdentificar = new JButton();
/*   90 */     this.btnGuardar = new JButton();
/*   91 */     this.jPanel4 = new JPanel();
/*   92 */     this.jScrollPane1 = new JScrollPane();
/*   93 */     this.txtArea = new JTextArea();
/*   94 */     this.jPanel3 = new JPanel();
/*   95 */     jTRecibeCedula = new JTextField();
/*   96 */     this.jLcedula = new JLabel();
/*   97 */     this.jTcontieneHuella = new JLabel();
/*   98 */     this.jTnombre = new JTextField();
/*   99 */     this.jLcedula2 = new JLabel();
/*  100 */     this.jLcedula4 = new JLabel();
/*  101 */     this.jTapellidos = new JTextField();
/*      */     
/*  103 */     this.jLcedula1.setText("Cedula:");
/*      */     
/*  105 */     this.jLcedula3.setText("Nombres:");
/*      */     
/*  107 */     setDefaultCloseOperation(2);
/*  108 */     setTitle("Gestión de Huellas Dactilares");
/*  109 */     setResizable(false);
/*  110 */     addWindowListener(new WindowAdapter() {
/*      */           public void windowClosing(WindowEvent evt) {
/*  112 */             CapturaHuellaEnroler.this.formWindowClosing(evt);
/*      */           }
/*      */           public void windowOpened(WindowEvent evt) {
/*  115 */             CapturaHuellaEnroler.this.formWindowOpened(evt);
/*      */           }
/*      */         });
/*      */     
/*  119 */     this.panHuellas.setBorder(BorderFactory.createTitledBorder(null, "Huella Digital Capturada", 2, 0));
/*  120 */     this.panHuellas.setPreferredSize(new Dimension(400, 270));
/*      */     
/*  122 */     this.jPanel1.setBorder(BorderFactory.createBevelBorder(1));
/*  123 */     this.jPanel1.setLayout(new BorderLayout());
/*  124 */     this.jPanel1.add(this.lblImagenHuella, "Center");
/*      */     
/*  126 */     GroupLayout panHuellasLayout = new GroupLayout(this.panHuellas);
/*  127 */     this.panHuellas.setLayout(panHuellasLayout);
/*  128 */     panHuellasLayout.setHorizontalGroup(panHuellasLayout
/*  129 */         .createParallelGroup(GroupLayout.Alignment.LEADING)
/*  130 */         .addComponent(this.jPanel1, -2, 420, -2));
/*      */     
/*  132 */     panHuellasLayout.setVerticalGroup(panHuellasLayout
/*  133 */         .createParallelGroup(GroupLayout.Alignment.LEADING)
/*  134 */         .addComponent(this.jPanel1, -2, 247, -2));
/*      */ 
/*      */     
/*  137 */     this.panBtns.setBorder(BorderFactory.createTitledBorder(null, "Acciones", 2, 0));
/*  138 */     this.panBtns.setPreferredSize(new Dimension(400, 190));
/*  139 */     this.panBtns.setLayout(new BorderLayout());
/*      */     
/*  141 */     this.jPanel2.setPreferredSize(new Dimension(366, 90));
/*      */     
/*  143 */     this.btnSalir.setText("Salir");
/*  144 */     this.btnSalir.setEnabled(false);
/*  145 */     this.btnSalir.addActionListener(new ActionListener() {
/*      */           public void actionPerformed(ActionEvent evt) {
/*  147 */             CapturaHuellaEnroler.this.btnSalirActionPerformed(evt);
/*      */           }
/*      */         });
/*      */     
/*  151 */     this.btnVerificar.setText("Ingreso");
/*  152 */     this.btnVerificar.addActionListener(new ActionListener() {
/*      */           public void actionPerformed(ActionEvent evt) {
/*  154 */             CapturaHuellaEnroler.this.btnVerificarActionPerformed(evt);
/*      */           }
/*      */         });
/*      */     
/*  158 */     this.btnIdentificar.setText("Identificar");
/*  159 */     this.btnIdentificar.setPreferredSize(new Dimension(71, 23));
/*  160 */     this.btnIdentificar.addActionListener(new ActionListener() {
/*      */           public void actionPerformed(ActionEvent evt) {
/*  162 */             CapturaHuellaEnroler.this.btnIdentificarActionPerformed(evt);
/*      */           }
/*      */         });
/*      */     
/*  166 */     this.btnGuardar.setText("Guardar");
/*  167 */     this.btnGuardar.addActionListener(new ActionListener() {
/*      */           public void actionPerformed(ActionEvent evt) {
/*  169 */             CapturaHuellaEnroler.this.btnGuardarActionPerformed(evt);
/*      */           }
/*      */         });
/*      */     
/*  173 */     GroupLayout jPanel2Layout = new GroupLayout(this.jPanel2);
/*  174 */     this.jPanel2.setLayout(jPanel2Layout);
/*  175 */     jPanel2Layout.setHorizontalGroup(jPanel2Layout
/*  176 */         .createParallelGroup(GroupLayout.Alignment.LEADING)
/*  177 */         .addGroup(jPanel2Layout.createSequentialGroup()
/*  178 */           .addGap(43, 43, 43)
/*  179 */           .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
/*  180 */             .addComponent(this.btnIdentificar, -1, -1, 32767)
/*  181 */             .addComponent(this.btnVerificar, -2, 139, -2))
/*  182 */           .addGap(44, 44, 44)
/*  183 */           .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
/*  184 */             .addComponent(this.btnSalir, -1, -1, 32767)
/*  185 */             .addComponent(this.btnGuardar, -2, 128, -2))
/*  186 */           .addContainerGap(66, 32767)));
/*      */     
/*  188 */     jPanel2Layout.setVerticalGroup(jPanel2Layout
/*  189 */         .createParallelGroup(GroupLayout.Alignment.LEADING)
/*  190 */         .addGroup(GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
/*  191 */           .addGap(11, 11, 11)
/*  192 */           .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
/*  193 */             .addComponent(this.btnVerificar, -2, 33, -2)
/*  194 */             .addComponent(this.btnGuardar, -2, 32, -2))
/*  195 */           .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
/*  196 */           .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
/*  197 */             .addComponent(this.btnIdentificar, -1, -1, 32767)
/*  198 */             .addComponent(this.btnSalir, -2, 30, -2))
/*  199 */           .addContainerGap()));
/*      */ 
/*      */     
/*  202 */     this.panBtns.add(this.jPanel2, "North");
/*      */     
/*  204 */     this.jPanel4.setLayout(new BorderLayout());
/*      */     
/*  206 */     this.txtArea.setColumns(20);
/*  207 */     this.txtArea.setFont(new Font("Lucida Sans", 1, 10));
/*  208 */     this.txtArea.setRows(5);
/*  209 */     this.jScrollPane1.setViewportView(this.txtArea);
/*      */     
/*  211 */     this.jPanel4.add(this.jScrollPane1, "Center");
/*      */     
/*  213 */     this.panBtns.add(this.jPanel4, "Center");
/*      */     
/*  215 */     this.jPanel3.setPreferredSize(new Dimension(366, 20));
/*      */     
/*  217 */     GroupLayout jPanel3Layout = new GroupLayout(this.jPanel3);
/*  218 */     this.jPanel3.setLayout(jPanel3Layout);
/*  219 */     jPanel3Layout.setHorizontalGroup(jPanel3Layout
/*  220 */         .createParallelGroup(GroupLayout.Alignment.LEADING)
/*  221 */         .addGap(0, 420, 32767));
/*      */     
/*  223 */     jPanel3Layout.setVerticalGroup(jPanel3Layout
/*  224 */         .createParallelGroup(GroupLayout.Alignment.LEADING)
/*  225 */         .addGap(0, 20, 32767));
/*      */ 
/*      */     
/*  228 */     this.panBtns.add(this.jPanel3, "South");
/*      */     
/*  230 */     jTRecibeCedula.setToolTipText("N° de documento de identidad");
/*  231 */     jTRecibeCedula.addActionListener(new ActionListener() {
/*      */           public void actionPerformed(ActionEvent evt) {
/*  233 */             CapturaHuellaEnroler.this.jTRecibeCedulaActionPerformed(evt);
/*      */           }
/*      */         });
/*  236 */     jTRecibeCedula.addKeyListener(new KeyAdapter() {
/*      */           public void keyTyped(KeyEvent evt) {
/*  238 */             CapturaHuellaEnroler.this.jTRecibeCedulaKeyTyped(evt);
/*      */           }
/*      */         });
/*      */     
/*  242 */     this.jLcedula.setText("Cedula:");
/*      */     
/*  244 */     this.jTnombre.addKeyListener(new KeyAdapter() {
/*      */           public void keyTyped(KeyEvent evt) {
/*  246 */             CapturaHuellaEnroler.this.jTnombreKeyTyped(evt);
/*      */           }
/*      */         });
/*      */     
/*  250 */     this.jLcedula2.setText("Nombres:");
/*      */     
/*  252 */     this.jLcedula4.setText("Apellidos:");
/*      */     
/*  254 */     this.jTapellidos.addKeyListener(new KeyAdapter() {
/*      */           public void keyTyped(KeyEvent evt) {
/*  256 */             CapturaHuellaEnroler.this.jTapellidosKeyTyped(evt);
/*      */           }
/*      */         });
/*      */     
/*  260 */     // Panel selector empresa/empleado
/*      */     JPanel selectorPanel = new JPanel(new GridBagLayout());
/*      */     selectorPanel.setBorder(BorderFactory.createTitledBorder("Seleccionar Empleado"));
/*      */     GridBagConstraints gbc = new GridBagConstraints();
/*      */     gbc.insets = new Insets(4, 6, 4, 6);
/*      */     gbc.anchor = GridBagConstraints.WEST;
/*      */     gbc.gridy = 0; gbc.gridx = 0;
/*      */     selectorPanel.add(new JLabel("Empresa:"), gbc);
/*      */     gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
/*      */     this.cmbEmpresa = new JComboBox<>();
/*      */     this.cmbEmpresa.setPreferredSize(new Dimension(300, 25));
/*      */     selectorPanel.add(this.cmbEmpresa, gbc);
/*      */     gbc.gridy = 1; gbc.gridx = 0; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
/*      */     selectorPanel.add(new JLabel("Empleado:"), gbc);
/*      */     gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
/*      */     this.cmbEmpleado = new JComboBox<>();
/*      */     this.cmbEmpleado.setPreferredSize(new Dimension(300, 25));
/*      */     selectorPanel.add(this.cmbEmpleado, gbc);
/*      */     this.cmbEmpresa.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent e) {
/*      */         int idx = CapturaHuellaEnroler.this.cmbEmpresa.getSelectedIndex();
/*      */         if (idx > 0 && idx < CapturaHuellaEnroler.this.empresaIds.size()) {
/*      */           CapturaHuellaEnroler.this.cargarEmpleados(CapturaHuellaEnroler.this.empresaIds.get(idx));
/*      */         }
/*      */       }
/*      */     });
/*      */     this.cmbEmpleado.addActionListener(new ActionListener() {
/*      */       public void actionPerformed(ActionEvent e) {
/*      */         int idx = CapturaHuellaEnroler.this.cmbEmpleado.getSelectedIndex();
/*      */         if (idx > 0 && idx < CapturaHuellaEnroler.this.empleadoDocNums.size()) {
/*      */           String[] data = CapturaHuellaEnroler.this.empleadoDocNums.get(idx).split("\\|");
/*      */           jTRecibeCedula.setText(data[0]);
/*      */           CapturaHuellaEnroler.this.jTnombre.setText(data.length > 1 ? data[1] : "");
/*      */           CapturaHuellaEnroler.this.jTapellidos.setText(data.length > 2 ? data[2] : "");
/*      */         }
/*      */       }
/*      */     });
/*      */     JPanel mainPanel = new JPanel();
/*      */     GroupLayout layout = new GroupLayout(mainPanel);
/*      */     mainPanel.setLayout(layout);
/*  262 */     layout.setHorizontalGroup(layout
/*  263 */         .createParallelGroup(GroupLayout.Alignment.LEADING)
/*  264 */         .addGroup(layout.createSequentialGroup()
/*  265 */           .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
/*  266 */             .addGroup(layout.createSequentialGroup()
/*  267 */               .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
/*  268 */                 .addComponent(this.panHuellas, -2, 432, -2)
/*  269 */                 .addComponent(this.panBtns, -2, 432, -2))
/*  270 */               .addGap(0, 0, 32767))
/*  271 */             .addGroup(layout.createSequentialGroup()
/*  272 */               .addGap(21, 21, 21)
/*  273 */               .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
/*  274 */                 .addGroup(layout.createSequentialGroup()
/*  275 */                   .addComponent(this.jLcedula4)
/*  276 */                   .addGap(26, 26, 26)
/*  277 */                   .addComponent(this.jTapellidos, -2, 185, -2))
/*  278 */                 .addGroup(layout.createSequentialGroup()
/*  279 */                   .addComponent(this.jLcedula2)
/*  280 */                   .addGap(26, 26, 26)
/*  281 */                   .addComponent(this.jTnombre, -2, 185, -2)))
/*  282 */               .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
/*  283 */                 .addGroup(layout.createSequentialGroup()
/*  284 */                   .addGap(152, 152, 152)
/*  285 */                   .addComponent(this.jTcontieneHuella, -1, -1, 32767))
/*  286 */                 .addGroup(layout.createSequentialGroup()
/*  287 */                   .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
/*  288 */                   .addComponent(this.jLcedula)
/*  289 */                   .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
/*  290 */                   .addComponent(jTRecibeCedula, -2, 89, -2)
/*  291 */                   .addGap(0, 0, 32767)))))
/*  292 */           .addContainerGap()));
/*      */     
/*  294 */     layout.setVerticalGroup(layout
/*  295 */         .createParallelGroup(GroupLayout.Alignment.LEADING)
/*  296 */         .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
/*  297 */           .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
/*  298 */             .addGroup(layout.createSequentialGroup()
/*  299 */               .addGap(0, 0, 32767)
/*  300 */               .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
/*  301 */                 .addComponent(this.jTnombre, -2, -1, -2)
/*  302 */                 .addComponent(this.jLcedula2)))
/*  303 */             .addGroup(layout.createSequentialGroup()
/*  304 */               .addComponent(this.panHuellas, -2, -1, -2)
/*  305 */               .addGap(14, 14, 14)
/*  306 */               .addComponent(this.jTcontieneHuella, -1, 20, 32767)))
/*  307 */           .addGap(7, 7, 7)
/*  308 */           .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
/*  309 */             .addComponent(this.jTapellidos, -2, -1, -2)
/*  310 */             .addComponent(this.jLcedula4)
/*  311 */             .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
/*  312 */               .addComponent(jTRecibeCedula, -2, -1, -2)
/*  313 */               .addComponent(this.jLcedula)))
/*  314 */           .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
/*  315 */           .addComponent(this.panBtns, -2, 163, -2)));
/*      */ 
/*      */     
/*  318 */     getContentPane().setLayout(new java.awt.BorderLayout());
/*      */     getContentPane().add(selectorPanel, java.awt.BorderLayout.NORTH);
/*      */     getContentPane().add(mainPanel, java.awt.BorderLayout.CENTER);
/*      */     setSize(new Dimension(460, 640));
/*      */     setLocationRelativeTo((Component)null);
/*      */   }
/*      */ 
/*      */   
/*      */   private void btnSalirActionPerformed(ActionEvent evt) {
/*  324 */     dispose();
/*      */   }
/*      */ 
/*      */   
/*      */   private void btnVerificarActionPerformed(ActionEvent evt) {
/*  329 */     stop();
/*  330 */     EnviarTexto("Deteniendo lector para abrir enrolador...");
/*      */ 
/*      */     
/*      */     try {
/*  334 */       Thread.sleep(500L);
/*  335 */     } catch (InterruptedException ex) {
/*  336 */       Thread.currentThread().interrupt();
/*      */     } 
/*      */ 
/*      */     
/*      */     try {
/*  341 */       if (this.Lector != null)
/*      */       {
/*  343 */         this.Lector = null;
/*      */       }
/*  345 */     } catch (Exception e) {
/*  346 */       System.err.println("Error al liberar lector: " + e.getMessage());
/*      */     } 
/*      */ 
/*      */     
/*  350 */     if (this.Reclutador != null) {
/*  351 */       this.Reclutador.clear();
/*      */     }
/*      */ 
/*      */     
/*  355 */     CapturaHuella hhuella = new CapturaHuella();
/*  356 */     hhuella.setVisible(true);
/*      */ 
/*      */     
/*  359 */     dispose();
/*      */   }
/*      */   
/*      */   private void btnIdentificarActionPerformed(ActionEvent evt) {
/*      */     try {
/*  364 */       identificarHuella();
/*  365 */       this.Reclutador.clear();
/*  366 */     } catch (IOException ex) {
/*  367 */       Logger.getLogger(CapturaHuellaEnroler.class.getName()).log(Level.SEVERE, (String)null, ex);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void btnGuardarActionPerformed(ActionEvent evt) {
/*  373 */     if (this.jTnombre.equals("") || jTRecibeCedula.equals("") || this.jTapellidos.equals("")) {
/*  374 */       JOptionPane.showMessageDialog(null, "Por favor diligencie todos los campos ");
/*      */     }
/*      */     else {
/*      */       
/*  378 */       guardarHuella();
/*  379 */       this.Reclutador.clear();
/*  380 */       this.lblImagenHuella.setIcon((Icon)null);
/*  381 */       start();
/*      */     } 
/*      */   }
/*      */   
/*      */   private void formWindowOpened(WindowEvent evt) {
/*  386 */     Iniciar();
/*      */     start();
/*      */     EstadoHuellas();
/*      */     this.btnGuardar.setEnabled(false);
/*      */     this.btnIdentificar.setEnabled(false);
/*      */     this.btnVerificar.setEnabled(true);
/*      */     this.btnSalir.grabFocus();
/*      */     cargarEmpresas();
/*      */   }
/*      */   
/*      */   private void formWindowClosing(WindowEvent evt) {
/*  396 */     stop();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void jTRecibeCedulaActionPerformed(ActionEvent evt) {}
/*      */ 
/*      */ 
/*      */   
/*      */   private void jTRecibeCedulaKeyTyped(KeyEvent evt) {
/*  406 */     char c = evt.getKeyChar();
/*      */     
/*  408 */     if (c < '0' || c > '9') {
/*  409 */       evt.consume();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private void jTnombreKeyTyped(KeyEvent evt) {
/*  415 */     char c = evt.getKeyChar();
/*      */ 
/*      */     
/*  418 */     c = Character.toUpperCase(c);
/*      */ 
/*      */     
/*  421 */     evt.setKeyChar(c);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void jTapellidosKeyTyped(KeyEvent evt) {
/*  427 */     char c = evt.getKeyChar();
/*      */ 
/*      */     
/*  430 */     c = Character.toUpperCase(c);
/*      */ 
/*      */     
/*  433 */     evt.setKeyChar(c);
/*      */   }
/*      */ 
/*      */   

/*      */   private void cargarEmpresas() {
/*      */     this.cmbEmpresa.removeAllItems();
/*      */     this.empresaIds.clear();
/*      */     this.cmbEmpleado.removeAllItems();
/*      */     this.empleadoDocNums.clear();
/*      */     try {
/*      */       ConexionBD cc = new ConexionBD();
/*      */       Connection cn = cc.conectar();
/*      */       PreparedStatement stmt = cn.prepareStatement("SELECT id, razon_social FROM employers ORDER BY razon_social");
/*      */       ResultSet rs = stmt.executeQuery();
/*      */       this.cmbEmpresa.addItem("-- Seleccione empresa --");
/*      */       this.empresaIds.add(null);
/*      */       while (rs.next()) {
/*      */         this.empresaIds.add(rs.getString("id"));
/*      */         this.cmbEmpresa.addItem(rs.getString("razon_social"));
/*      */       }
/*      */       rs.close(); stmt.close(); cn.close();
/*      */     } catch (SQLException e) {
/*      */       JOptionPane.showMessageDialog(null, "Error cargando empresas: " + e.getMessage());
/*      */     }
/*      */   }

/*      */   private void cargarEmpleados(String employerId) {
/*      */     this.cmbEmpleado.removeAllItems();
/*      */     this.empleadoDocNums.clear();
/*      */     jTRecibeCedula.setText("");
/*      */     this.jTnombre.setText("");
/*      */     this.jTapellidos.setText("");
/*      */     try {
/*      */       ConexionBD cc = new ConexionBD();
/*      */       Connection cn = cc.conectar();
/*      */       PreparedStatement stmt = cn.prepareStatement("SELECT numero_documento, nombres, apellidos FROM employees WHERE employer_id = ?::uuid ORDER BY apellidos, nombres");
/*      */       stmt.setString(1, employerId);
/*      */       ResultSet rs = stmt.executeQuery();
/*      */       this.cmbEmpleado.addItem("-- Seleccione empleado --");
/*      */       this.empleadoDocNums.add(null);
/*      */       while (rs.next()) {
/*      */         String docNum = rs.getString("numero_documento");
/*      */         String nombres = rs.getString("nombres");
/*      */         String apellidos = rs.getString("apellidos");
/*      */         this.empleadoDocNums.add(docNum + "|" + nombres + "|" + apellidos);
/*      */         this.cmbEmpleado.addItem(apellidos + ", " + nombres);
/*      */       }
/*      */       rs.close(); stmt.close(); cn.close();
/*      */     } catch (SQLException e) {
/*      */       JOptionPane.showMessageDialog(null, "Error cargando empleados: " + e.getMessage());
/*      */     }
/*      */   }

/*      */   public CapturaHuellaEnroler()
/*      */   {
/*  439 */     this.Lector = DPFPGlobal.getCaptureFactory().createCapture();
/*      */ 
/*      */ 
/*      */     
/*  443 */     this.Reclutador = DPFPGlobal.getEnrollmentFactory().createEnrollment();
/*      */ 
/*      */ 
/*      */     
/*  447 */     this.Verificador = DPFPGlobal.getVerificationFactory().createVerification();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  617 */     this.con = new conexion(); try {
/*      */       UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
/*      */     } catch (Exception e) {
/*      */       JOptionPane.showMessageDialog(null, "Imposible modificar el tema visual", "Lookandfeel inválido.", 0);
/*      */     }  initComponents(); this.txtArea.setEditable(false); String cedula_intern = "";
/*      */     jTRecibeCedula.setText(cedula_intern);
/*  623 */     buscarPorCedula(); } public static String TEMPLATE_PROPERTY = "template"; public DPFPFeatureSet featuresinscripcion; public DPFPFeatureSet featuresverificacion; conexion con; private JButton btnGuardar; private JButton btnIdentificar; private JButton btnSalir; private JButton btnVerificar; private JLabel jLcedula; private JLabel jLcedula1; private JLabel jLcedula2; private JLabel jLcedula3; private JLabel jLcedula4; private JPanel jPanel1; private JPanel jPanel2; private JPanel jPanel3; private JPanel jPanel4; private JScrollPane jScrollPane1; public static JTextField jTRecibeCedula; private JTextField jTapellidos; private JLabel jTcontieneHuella; private JTextField jTnombre; private JTextField jTnombre1; private JLabel lblImagenHuella; private JPanel panBtns; private JPanel panHuellas; private JTextArea txtArea; public void guardarHuella() { if (jTRecibeCedula.getText().isEmpty() || this.jTnombre.getText().isEmpty() || this.jTapellidos.getText().isEmpty()) {
/*  624 */       JOptionPane.showMessageDialog(null, "Debe cargar los datos del empleado primero.");
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/*  629 */     try { String CedulaPanta = jTRecibeCedula.getText().trim();
/*      */       
/*  631 */       if (CedulaPanta.isEmpty()) {
/*  632 */         JOptionPane.showMessageDialog(null, "La cédula no puede estar vacía");
/*      */         
/*      */         return;
/*      */       } 
/*      */       String sql = "SELECT numero_documento FROM employees WHERE numero_documento = ?";
/*      */       PreparedStatement verificarStmt = null;
/*      */       ResultSet rsCheck = null;
/*      */       try {
/*      */         ConexionBD cc = new ConexionBD();
/*      */         Connection cn = cc.conectar();
/*      */         verificarStmt = cn.prepareStatement(sql);
/*      */         verificarStmt.setString(1, CedulaPanta);
/*      */         rsCheck = verificarStmt.executeQuery();
/*      */         if (rsCheck.next()) {
/*      */           byte[] bytesHuella = this.template.serialize();
/*      */           String base64Huella = java.util.Base64.getEncoder().encodeToString(bytesHuella);
/*      */           PreparedStatement pst = cn.prepareStatement("UPDATE employees SET huella_dactilar = ? WHERE numero_documento = ?");
/*      */           pst.setString(1, base64Huella);
/*      */           pst.setString(2, CedulaPanta);
/*      */           int resultado = pst.executeUpdate();
/*      */           if (resultado > 0) {
/*      */             JOptionPane.showMessageDialog(null, "Huella guardada correctamente");
/*      */             this.jTcontieneHuella.setText("SI");
/*      */             this.btnGuardar.setEnabled(false);
/*      */             this.btnVerificar.grabFocus();
/*      */           } else {
/*      */             JOptionPane.showMessageDialog(null, "No se encontro empleado con cedula: " + CedulaPanta, "Error", 2);
/*      */           }
/*      */           pst.close();
/*      */         } else {
/*      */           JOptionPane.showMessageDialog(null, "No existe empleado con numero de documento: " + CedulaPanta, "Error", 2);
/*      */         }
/*      */         rsCheck.close(); verificarStmt.close(); cn.close();
/*      */       } catch (SQLException ex2) {
/*      */         JOptionPane.showMessageDialog(null, "Error al guardar huella: " + ex2.getMessage());
/*      */       }
/*      */       jTRecibeCedula.setText("");
/*      */     } catch (Exception outerE) { System.err.println("Error en guardarHuella: " + outerE.getMessage()); }
/*      */   }
/*      */
/*      */   protected void Iniciar() {
/*      */     this.Lector.addDataListener((DPFPDataListener)new DPFPDataAdapter() {
/*      */       public void dataAcquired(final DPFPDataEvent e) {
/*      */         SwingUtilities.invokeLater(new Runnable() {
/*      */           public void run() { CapturaHuellaEnroler.this.EnviarTexto("La Huella Digital ha sido Capturada"); CapturaHuellaEnroler.this.ProcesarCaptura(e.getSample()); }
/*      */         }); } });
/*      */     this.Lector.addReaderStatusListener((DPFPReaderStatusListener)new DPFPReaderStatusAdapter() {
/*      */       public void readerConnected(DPFPReaderStatusEvent e) { SwingUtilities.invokeLater(new Runnable() { public void run() { CapturaHuellaEnroler.this.EnviarTexto("El Sensor de Huella Digital esta Activado o Conectado"); } }); }
/*      */       public void readerDisconnected(DPFPReaderStatusEvent e) { SwingUtilities.invokeLater(new Runnable() { public void run() { CapturaHuellaEnroler.this.EnviarTexto("El Sensor de Huella Digital esta Desactivado o no Conectado"); } }); }
/*      */     });
/*      */     this.Lector.addSensorListener((DPFPSensorListener)new DPFPSensorAdapter() {
/*      */       public void fingerTouched(DPFPSensorEvent e) { SwingUtilities.invokeLater(new Runnable() { public void run() { CapturaHuellaEnroler.this.EnviarTexto("El dedo ha sido colocado sobre el Lector de Huella"); } }); }
/*      */       public void fingerGone(DPFPSensorEvent e) { SwingUtilities.invokeLater(new Runnable() { public void run() { CapturaHuellaEnroler.this.EnviarTexto("El dedo ha sido quitado del Lector de Huella"); } }); }
/*      */     });
/*      */     this.Lector.addErrorListener((DPFPErrorListener)new DPFPErrorAdapter() {
/*      */       public void errorReader(final DPFPErrorEvent e) { SwingUtilities.invokeLater(new Runnable() { public void run() { CapturaHuellaEnroler.this.EnviarTexto("Error: " + e.getError()); } }); }
/*      */     });
/*      */   }
/*      */   public void ProcesarCaptura(DPFPSample sample) { this.featuresinscripcion = extraerCaracteristicas(sample, DPFPDataPurpose.DATA_PURPOSE_ENROLLMENT); this.featuresverificacion = extraerCaracteristicas(sample, DPFPDataPurpose.DATA_PURPOSE_VERIFICATION); if (this.featuresinscripcion != null)
/*      */       try { System.out.println("Las Caracteristicas de la Huella han sido creada"); this.Reclutador.addFeatures(this.featuresinscripcion); Image image = CrearImagenHuella(sample); DibujarHuella(image); this.btnVerificar.setEnabled(true); this.btnIdentificar.setEnabled(true); } catch (DPFPImageQualityException ex) { System.err.println("Error: " + ex.getMessage()); } finally { EstadoHuellas(); switch (this.Reclutador.getTemplateStatus()) { case TEMPLATE_STATUS_READY:
/*      */             stop(); setTemplate(this.Reclutador.getTemplate()); EnviarTexto("La Plantilla de la Huella ha Sido Creada, ya puede Verificarla o Identificarla"); this.btnIdentificar.setEnabled(false); this.btnVerificar.setEnabled(false); this.btnGuardar.setEnabled(true); this.btnGuardar.grabFocus(); break;
/*      */           case TEMPLATE_STATUS_FAILED:
/*      */             this.Reclutador.clear(); stop(); EstadoHuellas(); setTemplate((DPFPTemplate)null); JOptionPane.showMessageDialog(this, "La Plantilla de la Huella no pudo ser creada, Repita el Proceso", "Inscripcion de Huellas Dactilares", 0); start(); break; }  }   }
/*      */   public DPFPFeatureSet extraerCaracteristicas(DPFPSample sample, DPFPDataPurpose purpose) { DPFPFeatureExtraction extractor = DPFPGlobal.getFeatureExtractionFactory().createFeatureExtraction(); try { return extractor.createFeatureSet(sample, purpose); } catch (DPFPImageQualityException e) { return null; }  }
/*      */   public Image CrearImagenHuella(DPFPSample sample) { return DPFPGlobal.getSampleConversionFactory().createImage(sample); }
/*      */   public void DibujarHuella(Image image) { this.lblImagenHuella.setIcon(new ImageIcon(image.getScaledInstance(this.lblImagenHuella.getWidth(), this.lblImagenHuella.getHeight(), 1))); repaint(); }
/*      */   public void EstadoHuellas() { EnviarTexto("Muestra de Huellas Necesarias para Guardar Template " + this.Reclutador.getFeaturesNeeded()); }
/*  790 */   public void verificarHuella(String nom) { try { Connection c = this.con.conectar();
/*      */       
/*  792 */       PreparedStatement verificarStmt = c.prepareStatement("SELECT hue_huella FROM huellas WHERE hue_nombre=?");
/*  793 */       verificarStmt.setString(1, nom);
/*  794 */       ResultSet rs = verificarStmt.executeQuery();
/*      */ 
/*      */       
/*  797 */       if (rs.next()) {
/*      */         
/*  799 */         byte[] templateBuffer = rs.getBytes("hue_huella");
/*      */         
/*  801 */         DPFPTemplate referenceTemplate = DPFPGlobal.getTemplateFactory().createTemplate(templateBuffer);
/*      */         
/*  803 */         setTemplate(referenceTemplate);
/*      */ 
/*      */ 
/*      */         
/*  807 */         DPFPVerificationResult result = this.Verificador.verify(this.featuresverificacion, getTemplate());
/*      */ 
/*      */         
/*  810 */         if (result.isVerified()) {
/*  811 */           JOptionPane.showMessageDialog(null, "Las huella capturada coinciden con la de " + nom, "Verificacion de Huella", 1);
/*      */         } else {
/*  813 */           JOptionPane.showMessageDialog(null, "No corresponde la huella con " + nom, "Verificacion de Huella", 0);
/*      */         }
/*      */       
/*      */       } else {
/*      */         
/*  818 */         JOptionPane.showMessageDialog(null, "No existe un registro de huella para " + nom, "Verificacion de Huella", 0);
/*      */       }  }
/*  820 */     catch (SQLException e)
/*      */     
/*  822 */     { System.err.println("Error al verificar los datos de la huella."); }
/*      */     finally
/*  824 */     { this.con.desconectar(); }
/*      */      } public void EnviarTexto(String string) { this.txtArea.append(string + "\n"); }
/*      */   public void start() { this.Lector.startCapture(); EnviarTexto("Utilizando el Lector de Huella Dactilar "); }
/*      */   public void stop() { this.Lector.stopCapture();
/*      */     EnviarTexto("No se está usando el Lector de Huella Dactilar "); }
/*      */   public DPFPTemplate getTemplate() { return this.template; }
/*      */   public void setTemplate(DPFPTemplate template) { DPFPTemplate old = this.template;
/*      */     this.template = template;
/*      */     firePropertyChange(TEMPLATE_PROPERTY, old, template); }
/*      */   public void identificarHuella() throws IOException {
/*      */     try {
/*      */       ConexionBD cc = new ConexionBD();
/*      */       Connection c = cc.conectar();
/*      */       PreparedStatement identificarStmt = c.prepareStatement("SELECT numero_documento, nombres, apellidos, huella_dactilar FROM employees WHERE huella_dactilar IS NOT NULL");
/*      */       ResultSet rs = identificarStmt.executeQuery();
/*      */       boolean encontrado = false;
/*      */       while (rs.next()) {
/*      */         String dbValue = rs.getString("huella_dactilar");
/*      */         if (dbValue == null || dbValue.isEmpty()) continue;
/*      */         byte[] templateBuffer;
/*      */         try { templateBuffer = java.util.Base64.getDecoder().decode(dbValue); } catch (Exception ex) { continue; }
/*      */         DPFPTemplate referenceTemplate = DPFPGlobal.getTemplateFactory().createTemplate(templateBuffer);
/*      */         DPFPVerificationResult result = this.Verificador.verify(this.featuresverificacion, referenceTemplate);
/*      */         if (result.isVerified()) {
/*      */           String nombre = rs.getString("nombres") + " " + rs.getString("apellidos");
/*      */           JOptionPane.showMessageDialog(null, "Huella identificada: " + nombre, "Identificacion", 1);
/*      */           encontrado = true; break;
/*      */         }
/*      */       }
/*      */       if (!encontrado) JOptionPane.showMessageDialog(null, "No coincide con ningun registro", "Verificacion de Huella", 0);
/*      */       rs.close(); identificarStmt.close(); c.close();
/*      */     } catch (SQLException e) {
/*      */       System.err.println("Error al identificar: " + e.getMessage());
/*      */     }  }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void main(String[] args) {
/*  892 */     EventQueue.invokeLater(new Runnable() {
/*      */           public void run() {
/*  894 */             (new CapturaHuellaEnroler()).setVisible(true);
/*      */           }
/*      */         });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void buscarPorCedula() {
/*      */     String cedula = jTRecibeCedula.getText().trim();
/*      */     if (cedula.isEmpty()) return;
/*      */     try {
/*      */       ConexionBD cc = new ConexionBD();
/*      */       Connection c = cc.conectar();
/*      */       PreparedStatement pst = c.prepareStatement("SELECT nombres, apellidos, huella_dactilar FROM employees WHERE numero_documento = ?");
/*      */       pst.setString(1, cedula);
/*      */       ResultSet rs = pst.executeQuery();
/*      */       if (rs.next()) {
/*      */         this.jTnombre.setText(rs.getString("nombres"));
/*      */         this.jTapellidos.setText(rs.getString("apellidos"));
/*      */         String h = rs.getString("huella_dactilar");
/*      */         this.jTcontieneHuella.setText((h != null && !h.isEmpty()) ? "SI" : "NO");
/*      */       }
/*      */       rs.close(); pst.close(); c.close();
/*      */     } catch (Exception e) {
/*      */       System.out.println("Error en buscarPorCedula: " + e.getMessage());
/*      */     }
/*      */   }
/*      */   
/*      */   public void identificarHuellaCESAR() throws IOException {
/*      */     try {
/*  967 */       Connection c = this.con.conectar();
/*      */ 
/*      */       
/*  970 */       PreparedStatement identificarStmt = c.prepareStatement("SELECT CEDULA,NOMBRES,APELLIDO1,APELLIDO2,AREA,HUELLA FROM empleados");
/*  971 */       ResultSet rs = identificarStmt.executeQuery();
/*      */ 
/*      */       
/*  974 */       while (rs.next()) {
/*      */ 
/*      */         
/*  977 */         String CEDULA = rs.getString("CEDULA");
/*  978 */         byte[] templateBuffer = rs.getBytes("HUELLA");
/*  979 */         String nombre = rs.getString("NOMBRES");
/*  980 */         String APELLIDO1 = rs.getString("APELLIDO1");
/*  981 */         String APELLIDO2 = rs.getString("APELLIDO2");
/*  982 */         String APELLIDO3 = rs.getString("AREA");
/*      */         
/*  984 */         this.btnGuardar.setEnabled(true);
/*      */         
/*  986 */         DPFPTemplate referenceTemplate = DPFPGlobal.getTemplateFactory().createTemplate(templateBuffer);
/*      */         
/*  988 */         setTemplate(referenceTemplate);
/*      */ 
/*      */ 
/*      */         
/*  992 */         DPFPVerificationResult result = this.Verificador.verify(this.featuresverificacion, getTemplate());
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  997 */         if (result.isVerified()) {
/*      */           return;
/*      */         }
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1013 */       JOptionPane.showMessageDialog(null, "No existe ningún registro que coincida con la huella", "Verificacion de Huella", 0);
/* 1014 */       setTemplate((DPFPTemplate)null);
/*      */ 
/*      */       
/* 1017 */       this.btnGuardar.setEnabled(false);
/* 1018 */     } catch (SQLException e) {
/*      */       
/* 1020 */       System.err.println("Error al identificar huella dactilar." + e.getMessage());
/*      */     } finally {
/* 1022 */       this.con.desconectar();
/*      */     } 
/*      */   }
/*      */ }


/* Location:              C:\Gap-x\dist\control_entrada.jar!\control_entrada\CapturaHuellaEnroler.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */
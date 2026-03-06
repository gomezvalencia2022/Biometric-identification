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
/*      */ import com.digitalpersona.onetouch.processing.DPFPEnrollment;
/*      */ import com.digitalpersona.onetouch.processing.DPFPFeatureExtraction;
/*      */ import com.digitalpersona.onetouch.processing.DPFPImageQualityException;
/*      */ import com.digitalpersona.onetouch.processing.DPFPTemplateStatus;
/*      */ import com.digitalpersona.onetouch.verification.DPFPVerification;
import com.digitalpersona.onetouch.verification.DPFPVerificationResult;
import com.digitalpersona.onetouch.capture.event.DPFPDataListener;
import com.digitalpersona.onetouch.capture.event.DPFPReaderStatusListener;
import java.awt.Component;
/*      */ import controlador.conexion;
/*      */ import java.awt.BorderLayout;
/*      */ import java.awt.Dimension;
/*      */ import java.awt.EventQueue;
/*      */ import java.awt.Font;
/*      */ import java.awt.Image;
/*      */ import java.awt.event.ActionEvent;
/*      */ import java.awt.event.ActionListener;
/*      */ import java.awt.event.ComponentAdapter;
/*      */ import java.awt.event.ComponentEvent;
/*      */ import java.awt.event.WindowAdapter;
/*      */ import java.awt.event.WindowEvent;
/*      */ import java.io.ByteArrayInputStream;
/*      */ import java.io.IOException;
/*      */ import java.sql.Connection;
/*      */ import java.sql.PreparedStatement;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.sql.Statement;
/*      */ import java.text.SimpleDateFormat;
/*      */ import java.util.Base64;
/*      */ import java.util.Calendar;
/*      */ import java.util.Date;
/*      */ import java.util.Formatter;
/*      */ import java.util.GregorianCalendar;
/*      */ import java.util.logging.Level;
/*      */ import java.util.logging.Logger;
/*      */ import javax.swing.BorderFactory;
/*      */ import javax.swing.GroupLayout;
/*      */ import javax.swing.Icon;
/*      */ import javax.swing.ImageIcon;
/*      */ import javax.swing.JButton;
/*      */ import javax.swing.JDialog;
/*      */ import javax.swing.JLabel;
/*      */ import javax.swing.JOptionPane;
/*      */ import javax.swing.JPanel;
/*      */ import javax.swing.JScrollPane;
/*      */ import javax.swing.JTextArea;
/*      */ import javax.swing.JTextField;
/*      */ import javax.swing.LayoutStyle;
/*      */ import javax.swing.SwingUtilities;
/*      */ import javax.swing.Timer;
/*      */ import javax.swing.UIManager;
/*      */ 
/*      */ public class CapturaHuella extends JDialog {
/*   68 */   Object EncontroHuella = null;
/*   69 */   String fechaActual = "";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private DPFPCapture Lector;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private DPFPEnrollment Reclutador;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void initComponents() {
/*   93 */     this.panHuellas = new JPanel();
/*   94 */     this.jPanel1 = new JPanel();
/*   95 */     this.lblImagenHuella = new JLabel();
/*   96 */     this.panBtns = new JPanel();
/*   97 */     this.jPanel2 = new JPanel();
/*   98 */     this.btnSalir = new JButton();
/*   99 */     this.btnIdentificar = new JButton();
/*  100 */     this.jPanel4 = new JPanel();
/*  101 */     this.jScrollPane1 = new JScrollPane();
/*  102 */     this.txtArea = new JTextArea();
/*  103 */     this.jPanel3 = new JPanel();
/*  104 */     this.jLabel2 = new JLabel();
/*  105 */     this.jLabel3 = new JLabel();
/*  106 */     this.jTNombres = new JTextField();
/*  107 */     this.jTApellidos = new JTextField();
/*  108 */     this.jLabel5 = new JLabel();
/*  109 */     this.jTcedula = new JTextField();
/*  110 */     this.jLabel1 = new JLabel();
/*      */     
/*  112 */     setDefaultCloseOperation(2);
/*  113 */     setTitle("Gestión de Huellas Dactilares");
/*  114 */     setResizable(false);
/*  115 */     addWindowListener(new WindowAdapter() {
/*      */           public void windowClosing(WindowEvent evt) {
/*  117 */             CapturaHuella.this.formWindowClosing(evt);
/*      */           }
/*      */           public void windowOpened(WindowEvent evt) {
/*  120 */             CapturaHuella.this.formWindowOpened(evt);
/*      */           }
/*      */         });
/*      */     
/*  124 */     this.panHuellas.setBorder(BorderFactory.createTitledBorder(null, "Huella Digital Capturada", 2, 0));
/*  125 */     this.panHuellas.setAutoscrolls(true);
/*  126 */     this.panHuellas.setPreferredSize(new Dimension(400, 270));
/*      */     
/*  128 */     this.jPanel1.setBorder(BorderFactory.createBevelBorder(1));
/*  129 */     this.jPanel1.setLayout(new BorderLayout());
/*  130 */     this.jPanel1.add(this.lblImagenHuella, "Center");
/*      */     
/*  132 */     GroupLayout panHuellasLayout = new GroupLayout(this.panHuellas);
/*  133 */     this.panHuellas.setLayout(panHuellasLayout);
/*  134 */     panHuellasLayout.setHorizontalGroup(panHuellasLayout
/*  135 */         .createParallelGroup(GroupLayout.Alignment.LEADING)
/*  136 */         .addComponent(this.jPanel1, -2, 420, -2));
/*      */     
/*  138 */     panHuellasLayout.setVerticalGroup(panHuellasLayout
/*  139 */         .createParallelGroup(GroupLayout.Alignment.LEADING)
/*  140 */         .addComponent(this.jPanel1, -2, 247, -2));
/*      */ 
/*      */     
/*  143 */     this.panBtns.setBorder(BorderFactory.createTitledBorder(null, "Acciones", 2, 0));
/*  144 */     this.panBtns.setPreferredSize(new Dimension(400, 190));
/*  145 */     this.panBtns.setLayout(new BorderLayout());
/*      */     
/*  147 */     this.jPanel2.setPreferredSize(new Dimension(366, 90));
/*      */     
/*  149 */     this.btnSalir.setText("Salir");
/*  150 */     this.btnSalir.addActionListener(new ActionListener() {
/*      */           public void actionPerformed(ActionEvent evt) {
/*  152 */             CapturaHuella.this.btnSalirActionPerformed(evt);
/*      */           }
/*      */         });
/*      */     
/*  156 */     this.btnIdentificar.setText("  Registrar Huella");
/*  157 */     this.btnIdentificar.setPreferredSize(new Dimension(71, 23));
/*  158 */     this.btnIdentificar.addActionListener(new ActionListener() {
/*      */           public void actionPerformed(ActionEvent evt) {
/*  160 */             CapturaHuella.this.btnIdentificarActionPerformed(evt);
/*      */           }
/*      */         });
/*      */     
/*  164 */     GroupLayout jPanel2Layout = new GroupLayout(this.jPanel2);
/*  165 */     this.jPanel2.setLayout(jPanel2Layout);
/*  166 */     jPanel2Layout.setHorizontalGroup(jPanel2Layout
/*  167 */         .createParallelGroup(GroupLayout.Alignment.LEADING)
/*  168 */         .addGroup(jPanel2Layout.createSequentialGroup()
/*  169 */           .addGap(41, 41, 41)
/*  170 */           .addComponent(this.btnIdentificar, -2, 139, -2)
/*  171 */           .addGap(50, 50, 50)
/*  172 */           .addComponent(this.btnSalir, -2, 128, -2)
/*  173 */           .addContainerGap(62, 32767)));
/*      */     
/*  175 */     jPanel2Layout.setVerticalGroup(jPanel2Layout
/*  176 */         .createParallelGroup(GroupLayout.Alignment.LEADING)
/*  177 */         .addGroup(jPanel2Layout.createSequentialGroup()
/*  178 */           .addGap(16, 16, 16)
/*  179 */           .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
/*  180 */             .addComponent(this.btnSalir, -1, -1, 32767)
/*  181 */             .addComponent(this.btnIdentificar, -1, 51, 32767))
/*  182 */           .addGap(23, 23, 23)));
/*      */ 
/*      */     
/*  185 */     this.panBtns.add(this.jPanel2, "North");
/*      */     
/*  187 */     this.jPanel4.setLayout(new BorderLayout());
/*      */     
/*  189 */     this.txtArea.setColumns(20);
/*  190 */     this.txtArea.setFont(new Font("Lucida Sans", 1, 10));
/*  191 */     this.txtArea.setRows(5);
/*  192 */     this.jScrollPane1.setViewportView(this.txtArea);
/*      */     
/*  194 */     this.jPanel4.add(this.jScrollPane1, "Center");
/*      */     
/*  196 */     this.panBtns.add(this.jPanel4, "Center");
/*      */     
/*  198 */     this.jPanel3.setPreferredSize(new Dimension(366, 20));
/*      */     
/*  200 */     GroupLayout jPanel3Layout = new GroupLayout(this.jPanel3);
/*  201 */     this.jPanel3.setLayout(jPanel3Layout);
/*  202 */     jPanel3Layout.setHorizontalGroup(jPanel3Layout
/*  203 */         .createParallelGroup(GroupLayout.Alignment.LEADING)
/*  204 */         .addGap(0, 420, 32767));
/*      */     
/*  206 */     jPanel3Layout.setVerticalGroup(jPanel3Layout
/*  207 */         .createParallelGroup(GroupLayout.Alignment.LEADING)
/*  208 */         .addGap(0, 20, 32767));
/*      */ 
/*      */     
/*  211 */     this.panBtns.add(this.jPanel3, "South");
/*      */     
/*  213 */     this.jLabel2.setText("Nombres:");
/*      */     
/*  215 */     this.jLabel3.setText("Apellidos:");
/*      */     
/*  217 */     this.jTNombres.setEditable(false);
/*  218 */     this.jTNombres.addActionListener(new ActionListener() {
/*      */           public void actionPerformed(ActionEvent evt) {
/*  220 */             CapturaHuella.this.jTNombresActionPerformed(evt);
/*      */           }
/*      */         });
/*      */     
/*  224 */     this.jTApellidos.setEditable(false);
/*      */     
/*  226 */     this.jLabel5.setText("Cedula:");
/*      */     
/*  228 */     this.jTcedula.setEditable(false);
/*      */     
/*  230 */     this.jLabel1.setIcon(new ImageIcon(getClass().getResource("/imagenes/logo-gap-X.jpeg")));
/*  231 */     this.jLabel1.setText("jLabel1");
/*      */     
/*  233 */     GroupLayout layout = new GroupLayout(getContentPane());
/*  234 */     getContentPane().setLayout(layout);
/*  235 */     layout.setHorizontalGroup(layout
/*  236 */         .createParallelGroup(GroupLayout.Alignment.LEADING)
/*  237 */         .addGroup(layout.createSequentialGroup()
/*  238 */           .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
/*  239 */             .addComponent(this.panHuellas, -1, 432, 32767)
/*  240 */             .addComponent(this.panBtns, -2, 432, -2))
/*  241 */           .addGap(18, 18, 18)
/*  242 */           .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
/*  243 */             .addGroup(layout.createSequentialGroup()
/*  244 */               .addComponent(this.jLabel3)
/*  245 */               .addGap(18, 18, 18)
/*  246 */               .addComponent(this.jTApellidos, -2, 134, -2)
/*  247 */               .addGap(0, 0, 32767))
/*  248 */             .addGroup(layout.createSequentialGroup()
/*  249 */               .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
/*  250 */                 .addGroup(layout.createSequentialGroup()
/*  251 */                   .addComponent(this.jLabel2)
/*  252 */                   .addGap(18, 18, 18)
/*  253 */                   .addComponent(this.jTNombres, -2, 134, -2)
/*  254 */                   .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
/*  255 */                   .addComponent(this.jLabel5)
/*  256 */                   .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
/*  257 */                   .addComponent(this.jTcedula, -2, 90, -2)
/*  258 */                   .addGap(0, 0, 32767))
/*  259 */                 .addComponent(this.jLabel1, -1, -1, 32767))
/*  260 */               .addContainerGap()))));
/*      */     
/*  262 */     layout.setVerticalGroup(layout
/*  263 */         .createParallelGroup(GroupLayout.Alignment.LEADING)
/*  264 */         .addGroup(layout.createSequentialGroup()
/*  265 */           .addComponent(this.panHuellas, -1, -1, 32767)
/*  266 */           .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, 32767)
/*  267 */           .addComponent(this.panBtns, -2, -1, -2)
/*  268 */           .addGap(59, 59, 59))
/*  269 */         .addGroup(layout.createSequentialGroup()
/*  270 */           .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
/*  271 */             .addGroup(layout.createSequentialGroup()
/*  272 */               .addGap(27, 27, 27)
/*  273 */               .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
/*  274 */                 .addComponent(this.jLabel2)
/*  275 */                 .addComponent(this.jTNombres, -2, -1, -2)))
/*  276 */             .addGroup(layout.createSequentialGroup()
/*  277 */               .addGap(40, 40, 40)
/*  278 */               .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
/*  279 */                 .addComponent(this.jLabel5)
/*  280 */                 .addComponent(this.jTcedula, -2, -1, -2))))
/*  281 */           .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
/*  282 */           .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
/*  283 */             .addComponent(this.jLabel3)
/*  284 */             .addComponent(this.jTApellidos, -2, -1, -2))
/*  285 */           .addGap(18, 18, 18)
/*  286 */           .addComponent(this.jLabel1, -2, 333, -2)
/*  287 */           .addContainerGap(-1, 32767)));
/*      */ 
/*      */     
/*  290 */     setSize(new Dimension(812, 512));
/*  291 */     setLocationRelativeTo((Component)null);
/*      */   }
/*      */ 
/*      */   
/*      */   private void btnSalirActionPerformed(ActionEvent evt) {
/*  296 */     dispose();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void btnIdentificarActionPerformed(ActionEvent evt) {
/*  303 */     stop();
/*  304 */     EnviarTexto("Deteniendo lector para abrir enrolador...");
/*      */ 
/*      */     
/*      */     try {
/*  308 */       Thread.sleep(500L);
/*  309 */     } catch (InterruptedException ex) {
/*  310 */       Thread.currentThread().interrupt();
/*      */     } 
/*      */ 
/*      */     
/*      */     try {
/*  315 */       if (this.Lector != null)
/*      */       {
/*  317 */         this.Lector = null;
/*      */       }
/*  319 */     } catch (Exception e) {
/*  320 */       System.err.println("Error al liberar lector: " + e.getMessage());
/*      */     } 
/*      */ 
/*      */     
/*  324 */     if (this.Reclutador != null) {
/*  325 */       this.Reclutador.clear();
/*      */     }
/*      */ 
/*      */     
/*  329 */     CapturaHuellaEnroler henroler = new CapturaHuellaEnroler();
/*  330 */     henroler.setVisible(true);
/*      */ 
/*      */     
/*  333 */     dispose();
/*      */   }
/*      */ 
/*      */   
/*      */   private void formWindowOpened(WindowEvent evt) {
/*  338 */     Iniciar();
/*  339 */     start();
/*  340 */     EstadoHuellas();
/*      */     
/*  342 */     this.btnIdentificar.setEnabled(true);
/*      */     
/*  344 */     this.btnSalir.grabFocus();
/*      */   }
/*      */   
/*      */   private void formWindowClosing(WindowEvent evt) {
/*  348 */     stop();
/*      */   }
/*      */ 
/*      */   
/*      */   private void jTNombresActionPerformed(ActionEvent evt) {}
/*      */ 
/*      */   
/*      */   public CapturaHuella()
/*      */   {
/*  357 */     this.Lector = DPFPGlobal.getCaptureFactory().createCapture();
/*      */ 
/*      */ 
/*      */     
/*  361 */     this.Reclutador = DPFPGlobal.getEnrollmentFactory().createEnrollment();
/*      */ 
/*      */ 
/*      */     
/*  365 */     this.Verificador = DPFPGlobal.getVerificationFactory().createVerification();
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
/*  723 */     this.con = new ConexionBD(); try {
/*      */       UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
/*      */     } catch (Exception e) {
/*      */       JOptionPane.showMessageDialog(null, "Imposible modificar el tema visual", "Lookandfeel inválido.", 0);
/*      */     } 
/*      */     initComponents();
/*      */     cargarYConfigurarImagen();
/*      */     this.btnIdentificar.setEnabled(true);
/*      */     this.txtArea.setEditable(false);
/*      */     buscarPorCedula(); } static DPFPEnrollment Reclutador2 = DPFPGlobal.getEnrollmentFactory().createEnrollment(); private DPFPVerification Verificador; private DPFPTemplate template; public static String TEMPLATE_PROPERTY = "template"; public DPFPFeatureSet featuresinscripcion; public DPFPFeatureSet featuresverificacion; ConexionBD con; private JButton btnIdentificar; private JButton btnSalir; private JLabel jLabel1; private JLabel jLabel2; private JLabel jLabel3; private JLabel jLabel5; private JPanel jPanel1; private JPanel jPanel2; private JPanel jPanel3; private JPanel jPanel4; private JScrollPane jScrollPane1; private JTextField jTApellidos; public void guardarHuella() { guardarHuellaComoBase64(); } private JTextField jTNombres; private JTextField jTcedula; private JLabel lblImagenHuella; private JPanel panBtns; private JPanel panHuellas; private JTextArea txtArea; private Image imagenOriginal; protected void Iniciar() { this.Lector.addDataListener((DPFPDataListener)new DPFPDataAdapter() { public void dataAcquired(final DPFPDataEvent e) { SwingUtilities.invokeLater(new Runnable() { public void run() { CapturaHuella.this.EnviarTexto("La Huella Digital ha sido Capturada"); CapturaHuella.this.ProcesarCaptura(e.getSample()); } }
/*      */               ); } }
/*      */       ); this.Lector.addReaderStatusListener((DPFPReaderStatusListener)new DPFPReaderStatusAdapter() { public void readerConnected(DPFPReaderStatusEvent e) { SwingUtilities.invokeLater(new Runnable() { public void run() { CapturaHuella.this.EnviarTexto("El Sensor de Huella Digital esta Activado o Conectado"); } }
/*      */               ); } public void readerDisconnected(DPFPReaderStatusEvent e) { SwingUtilities.invokeLater(new Runnable() { public void run() { CapturaHuella.this.EnviarTexto("El Sensor de Huella Digital esta Desactivado o no Conectado"); } }
/*      */               ); } }
/*      */       ); this.Lector.addSensorListener((DPFPSensorListener)new DPFPSensorAdapter() { public void fingerTouched(DPFPSensorEvent e) { SwingUtilities.invokeLater(new Runnable() { public void run() { CapturaHuella.this.EnviarTexto("El dedo ha sido colocado sobre el Lector de Huella"); } }
/*      */               ); } public void fingerGone(DPFPSensorEvent e) { SwingUtilities.invokeLater(new Runnable() { public void run() { CapturaHuella.this.EnviarTexto("El dedo ha sido quitado del Lector de Huella"); } }
/*      */               ); } }
/*      */       ); this.Lector.addErrorListener((DPFPErrorListener)new DPFPErrorAdapter() { public void errorReader(final DPFPErrorEvent e) { SwingUtilities.invokeLater(new Runnable() { public void run() { CapturaHuella.this.EnviarTexto("Error: " + e.getError()); } }
/*      */               ); } }
/*      */       ); }
/*      */   public void ProcesarCaptura(DPFPSample sample) { this.featuresinscripcion = extraerCaracteristicas(sample, DPFPDataPurpose.DATA_PURPOSE_ENROLLMENT); this.featuresverificacion = extraerCaracteristicas(sample, DPFPDataPurpose.DATA_PURPOSE_VERIFICATION); if (this.featuresinscripcion != null) try { System.out.println("Las Caracteristicas de la Huella han sido creada"); this.Reclutador.addFeatures(this.featuresinscripcion); Image image = CrearImagenHuella(sample); DibujarHuella(image); this.btnIdentificar.setEnabled(true); } catch (DPFPImageQualityException ex) { System.err.println("Error: " + ex.getMessage()); } finally { EstadoHuellas(); switch (this.Reclutador.getTemplateStatus()) { case TEMPLATE_STATUS_READY: stop(); setTemplate(this.Reclutador.getTemplate()); EnviarTexto("✓ PLANTILLA CREADA EXITOSAMENTE"); EnviarTexto("  Tamaño del template: " + (this.template.serialize()).length + " bytes"); this.btnIdentificar.setEnabled(true); if (this.jTcedula.getText() != null && !this.jTcedula.getText().isEmpty()) { int respuesta = JOptionPane.showConfirmDialog(this, "¿Desea reinscribir esta huella para la cédula " + this.jTcedula.getText() + "?", "Template Listo", 0); if (respuesta == 0) reinscribirHuellaManual(this.jTcedula.getText());  }  break;case TEMPLATE_STATUS_FAILED: this.Reclutador.clear(); stop(); EstadoHuellas(); setTemplate((DPFPTemplate)null); JOptionPane.showMessageDialog(this, "La Plantilla de la Huella no pudo ser creada, Repita el Proceso", "Inscripcion de Huellas Dactilares", 0); start(); break; }  }   }
/*      */   public DPFPFeatureSet extraerCaracteristicas(DPFPSample sample, DPFPDataPurpose purpose) { DPFPFeatureExtraction extractor = DPFPGlobal.getFeatureExtractionFactory().createFeatureExtraction(); try { return extractor.createFeatureSet(sample, purpose); } catch (DPFPImageQualityException e) { return null; }  }
/*  820 */   public void verificarHuella(String nom) { try { Connection c = this.con.conectar();
/*      */       
/*  822 */       PreparedStatement verificarStmt = c.prepareStatement("SELECT hue_huella FROM huellas WHERE hue_nombre=?");
/*  823 */       verificarStmt.setString(1, nom);
/*  824 */       ResultSet rs = verificarStmt.executeQuery();
/*      */ 
/*      */       
/*  827 */       if (rs.next()) {
/*      */         
/*  829 */         byte[] templateBuffer = rs.getBytes("hue_huella");
/*      */         
/*  831 */         DPFPTemplate referenceTemplate = DPFPGlobal.getTemplateFactory().createTemplate(templateBuffer);
/*      */         
/*  833 */         setTemplate(referenceTemplate);
/*      */ 
/*      */ 
/*      */         
/*  837 */         DPFPVerificationResult result = this.Verificador.verify(this.featuresverificacion, getTemplate());
/*      */ 
/*      */         
/*  840 */         if (result.isVerified()) {
/*  841 */           JOptionPane.showMessageDialog(null, "Las huella capturada coinciden con la de " + nom, "Verificacion de Huella", 1);
/*      */         } else {
/*  843 */           JOptionPane.showMessageDialog(null, "No corresponde la huella con " + nom, "Verificacion de Huella", 0);
/*      */         }
/*      */       
/*      */       } else {
/*      */         
/*  848 */         JOptionPane.showMessageDialog(null, "No existe un registro de huella para " + nom, "Verificacion de Huella", 0);
/*      */       }  }
/*  850 */     catch (SQLException e)
/*      */     
/*  852 */     { System.err.println("Error al verificar los datos de la huella."); }
/*      */     finally
/*  854 */     { this.con.desconectar(); }  }
/*      */   public Image CrearImagenHuella(DPFPSample sample) { return DPFPGlobal.getSampleConversionFactory().createImage(sample); }
/*      */   public void reinscribirHuellaManual(String cedula) { try { if (this.template == null) { JOptionPane.showMessageDialog(this, "No hay template disponible. Capture la huella varias veces hasta que se cree.", "Error", 0); return; }  byte[] templateBytes = this.template.serialize(); if (templateBytes == null || templateBytes.length == 0) { JOptionPane.showMessageDialog(this, "El template está vacío. Capture la huella nuevamente.", "Error", 0); return; }  String info = String.format("Template listo para guardar:\nCédula: %s\nTamaño: %d bytes\n¿Continuar?", new Object[] { cedula, Integer.valueOf(templateBytes.length) }); int confirm = JOptionPane.showConfirmDialog(this, info, "Confirmar Reinscripción", 0, 3); if (confirm != 0)
/*      */         return;  String templateBase64 = Base64.getEncoder().encodeToString(templateBytes); Connection conn = null; PreparedStatement pstmt = null; try { conn = this.con.conectar(); String sql = "UPDATE employees SET huella_dactilar = ? WHERE numero_documento = ?"; pstmt = conn.prepareStatement(sql); pstmt.setString(1, templateBase64); pstmt.setString(2, cedula); int filas = pstmt.executeUpdate(); if (filas > 0) { JOptionPane.showMessageDialog(this, "✓ Huella reinscrita exitosamente\nCédula: " + cedula + "\nTamaño: " + templateBytes.length + " bytes", "Éxito", 1); this.EncontroHuella = "OK"; EnviarTexto("=== HUELLA REINSCRITA ==="); EnviarTexto("Cédula: " + cedula); EnviarTexto("Tamaño: " + templateBytes.length + " bytes"); EnviarTexto("Base64: " + templateBase64.substring(0, 50) + "..."); this.Reclutador.clear(); setTemplate((DPFPTemplate)null); this.lblImagenHuella.setIcon((Icon)null); start(); } else { JOptionPane.showMessageDialog(this, "No se encontró el usuario con cédula: " + cedula, "Error", 0); }  } catch (SQLException e) { JOptionPane.showMessageDialog(this, "Error de BD: " + e.getMessage(), "Error", 0); e.printStackTrace(); } finally { try { if (pstmt != null)
/*      */             pstmt.close();  }
/*      */         catch (Exception exception) {} try { if (conn != null)
/*      */             this.con.desconectar();  }
/*      */         catch (Exception exception) {} }
/*      */        }
/*      */     catch (Exception e) { JOptionPane.showMessageDialog(this, "Error inesperado: " + e.getMessage(), "Error", 0); e.printStackTrace(); }
/*  864 */      } public void identificarHuella() throws IOException { try { Connection c = this.con.conectar();
/*  865 */       EnviarTexto("Buscando en base de datos...");
/*      */ 
/*      */       
/*  868 */       String sql = "SELECT numero_documento, nombres, apellidos, huella_dactilar FROM employees WHERE huella_dactilar IS NOT NULL";
/*  869 */       PreparedStatement identificarStmt = c.prepareStatement(sql);
/*  870 */       ResultSet rs = identificarStmt.executeQuery();
/*      */       
/*  872 */       boolean encontrado = false;
/*      */ 
/*      */       
/*  875 */       while (rs.next()) {
/*  876 */         byte[] templateBuffer; String dbValue = rs.getString("huella_dactilar");
/*      */         
/*  878 */         if (dbValue == null || dbValue.isEmpty()) {
/*      */           continue;
/*      */         }
/*      */ 
/*      */         
/*  883 */         if (dbValue.startsWith("\\x")) {
/*      */           
/*  885 */           templateBuffer = hexToBytes(dbValue.substring(2));
/*      */         } else {
/*      */           
/*      */           try {
/*  889 */             templateBuffer = Base64.getDecoder().decode(dbValue);
/*  890 */           } catch (Exception e) {
/*      */             
/*  892 */             templateBuffer = hexToBytes(dbValue);
/*      */           } 
/*      */         } 
/*      */ 
/*      */         
/*  897 */         DPFPTemplate referenceTemplate = DPFPGlobal.getTemplateFactory().createTemplate(templateBuffer);
/*      */ 
/*      */ 
/*      */         
/*  901 */         DPFPVerificationResult result = this.Verificador.verify(this.featuresverificacion, referenceTemplate);
/*      */         
/*  903 */         if (result.isVerified()) {
/*  904 */           encontrado = true;
/*  905 */           String nombreCompleto = rs.getString("nombres") + " " + rs.getString("apellidos");
/*  906 */           String cedula = rs.getString("numero_documento");
/*      */ 
/*      */           
/*  909 */           this.jTNombres.setText(rs.getString("nombres"));
/*  910 */           this.jTApellidos.setText(rs.getString("apellidos"));
/*  911 */           this.jTcedula.setText(cedula);
/*      */           
/*  913 */           EnviarTexto("✓ IDENTIFICADO: " + nombreCompleto);
/*      */           
/*  915 */           mostrarBienvenidaAutoCerrar("Bienvenido(a) " + nombreCompleto);
/*      */           
/*      */           // insertEntrada() removido - marcaentrada() en DibujarHuella() registra en tabla marcaciones
/*      */           break;
/*      */         } 
/*      */       } 
/*      */       
/*  923 */       if (!encontrado) {
/*  924 */         EnviarTexto("X Huella no encontrada.");
/*  925 */         JOptionPane.showMessageDialog(null, "No existe ningún registro que coincida con esta huella", "Error de Identificación", 0);
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  930 */       rs.close();
/*  931 */       identificarStmt.close();
/*  932 */       this.con.desconectar(); }
/*      */     
/*  934 */     catch (SQLException e)
/*  935 */     { System.err.println("Error de SQL: " + e.getMessage());
/*  936 */       JOptionPane.showMessageDialog(null, "Error de base de datos: " + e.getMessage()); }
/*  937 */     catch (Exception e)
/*  938 */     { System.err.println("Error general: " + e.getMessage()); }  }
/*      */   private void verificarHuellaEspecifica(String cedula) { System.out.println("verificarHuellaEspecifica deshabilitado"); }
  public void DibujarHuella(Image image) { this.lblImagenHuella.setIcon(new ImageIcon(image.getScaledInstance(this.lblImagenHuella.getWidth(), this.lblImagenHuella.getHeight(), 1))); repaint(); try { identificarHuella(); try { cambiodia(); } catch (SQLException ex) { Logger.getLogger(CapturaHuella.class.getName()).log(Level.SEVERE, (String)null, ex); }  marcaentrada(); this.Reclutador.clear(); } catch (IOException ex) { Logger.getLogger(CapturaHuella.class.getName()).log(Level.SEVERE, (String)null, ex); }  }
/*      */   public void EstadoHuellas() { EnviarTexto("Muestra de Huellas Necesarias para Guardar Template " + this.Reclutador.getFeaturesNeeded()); EnviarTexto("LOS ULTIMOS DATOS INGRESADOS SON:  \n" + this.jTNombres.getText() + " " + this.jTApellidos.getText()); }
/*      */   public void EnviarTexto(String string) { this.txtArea.append(string + "\n"); }
/*      */   public void start() { this.Lector.startCapture(); EnviarTexto("Utilizando el Lector de Huella Dactilar "); }
/*      */   public void stop() { this.Lector.stopCapture(); EnviarTexto("No se está usando el Lector de Huella Dactilar "); }
/*  945 */   public DPFPTemplate getTemplate() { return this.template; } public void setTemplate(DPFPTemplate template) { DPFPTemplate old = this.template; this.template = template; firePropertyChange(TEMPLATE_PROPERTY, old, template); } private void mostrarBienvenidaAutoCerrar(String mensaje) { JOptionPane pane = new JOptionPane(mensaje, 1);
/*  946 */     final JDialog dialog = pane.createDialog("Identificación Exitosa");
/*      */     
/*  948 */     Timer t = new Timer(3000, new ActionListener()
/*      */         {
/*      */           public void actionPerformed(ActionEvent e) {
/*  951 */             dialog.dispose();
/*      */           }
/*      */         });
/*  954 */     t.setRepeats(false);
/*  955 */     t.start();
/*      */     
/*  957 */     dialog.setModal(true);
/*  958 */     dialog.setVisible(true); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private byte[] hexToBytes(String s) {
/*  965 */     int len = s.length();
/*      */     
/*  967 */     if (len % 2 != 0) return null;
/*      */     
/*  969 */     byte[] data = new byte[len / 2];
/*  970 */     for (int i = 0; i < len; i += 2) {
/*  971 */       data[i / 2] = 
/*  972 */         (byte)((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i + 1), 16));
/*      */     }
/*  974 */     return data;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean esBase64Valido(String texto) {
/*  980 */     if (texto == null || texto.isEmpty()) return false; 
/*      */     try {
/*  982 */       Base64.getDecoder().decode(texto);
/*  983 */       return true;
/*  984 */     } catch (IllegalArgumentException e) {
/*  985 */       return false;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private String limpiarTextoHuella(String texto) {
/*  991 */     if (texto == null) return "";
/*      */ 
/*      */     
/*  994 */     StringBuilder limpio = new StringBuilder();
/*  995 */     for (char c : texto.toCharArray()) {
/*      */       
/*  997 */       if (c >= ' ' && c <= '~') {
/*  998 */         limpio.append(c);
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 1003 */     return limpio.toString().replaceAll("\\s", "");
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static void main(String[] args) {
/* 1009 */     EventQueue.invokeLater(new Runnable() {
/*      */           public void run() {
/* 1011 */             (new CapturaHuella()).setVisible(true);
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
/*      */   public void guardarHuellaComoBase64() {
/*      */     try {
/*      */       String sql;
/* 1040 */       if (this.template == null) {
/* 1041 */         JOptionPane.showMessageDialog(null, "No hay una huella válida para guardar", "Error", 0);
/*      */ 
/*      */         
/*      */         return;
/*      */       } 
/*      */       
/* 1047 */       byte[] templateBytes = this.template.serialize();
/* 1048 */       String templateBase64 = Base64.getEncoder().encodeToString(templateBytes);
/*      */       
/* 1050 */       Connection c = this.con.conectar();
/*      */ 
/*      */       
/* 1053 */       if (this.EncontroHuella == null) {
/*      */         
/* 1055 */         sql = "UPDATE employees SET huella_dactilar = ? WHERE numero_documento = ?";
/*      */       } else {
/* 1057 */         sql = "UPDATE employees SET huella_dactilar = ? WHERE numero_documento = ?";
/*      */       } 
/*      */       
/* 1060 */       PreparedStatement pst = c.prepareStatement(sql);
/* 1061 */       pst.setString(1, templateBase64);
/* 1062 */       pst.setString(2, this.jTcedula.getText());
/*      */       
/* 1064 */       int filasAfectadas = pst.executeUpdate();
/*      */       
/* 1066 */       if (filasAfectadas > 0) {
/* 1067 */         JOptionPane.showMessageDialog(null, "Huella guardada correctamente en formato Base64", "Éxito", 1);
/*      */       } else {
/*      */         
/* 1070 */         JOptionPane.showMessageDialog(null, "No se pudo guardar la huella", "Error", 0);
/*      */       } 
/*      */ 
/*      */       
/* 1074 */       pst.close();
/* 1075 */       this.con.desconectar();
/*      */     }
/* 1077 */     catch (SQLException ex) {
/* 1078 */       System.err.println("Error al guardar la huella: " + ex.getMessage());
/* 1079 */       JOptionPane.showMessageDialog(null, "Error al guardar la huella: " + ex.getMessage(), "Error", 0);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void buscarPorCedula() {
/*      */     try {
/* 1085 */       ConexionBD cc = new ConexionBD();
/* 1086 */       try (Connection cn = cc.conectar()) {
/* 1087 */         Statement estado1 = cn.createStatement();
/* 1088 */         String cedulaVerificacion = this.jTcedula.getText();
/*      */         
/* 1090 */         ResultSet resultado = estado1.executeQuery("SELECT * FROM employees WHERE numero_documento = '" + cedulaVerificacion + "'");
/*      */ 
/*      */ 
/*      */         
/* 1094 */         String nombre = null, apellidos = null, cedula = null;
/* 1095 */         String huellaBase64 = null;
/*      */         
/* 1097 */         while (resultado.next()) {
/* 1098 */           nombre = resultado.getString("nombres");
/* 1099 */           apellidos = resultado.getString("apellidos");
/* 1100 */           cedula = resultado.getString("numero_documento");
/* 1101 */           huellaBase64 = resultado.getString("huella_dactilar");
/*      */         } 
/*      */ 
/*      */         
/* 1105 */         this.EncontroHuella = (huellaBase64 != null && !huellaBase64.isEmpty()) ? "OK" : null;
/*      */         
/* 1107 */         cn.close();
/*      */       } 
/* 1109 */     } catch (SQLException ex) {
/* 1110 */       System.out.println("Error en búsqueda: " + ex);
/*      */     } 
/*      */   }
/*      */   public void migrarHuellasABase64() {
/*      */     try {
/* 1115 */       Connection c = this.con.conectar();
/*      */ 
/*      */       
/* 1118 */       PreparedStatement stmt = c.prepareStatement("SELECT cedula, hue_huella FROM datosinternospersonal WHERE hue_huella IS NOT NULL");
/*      */ 
/*      */       
/* 1121 */       ResultSet rs = stmt.executeQuery();
/*      */       
/* 1123 */       int contador = 0;
/* 1124 */       while (rs.next()) {
/* 1125 */         String cedula = rs.getString("cedula");
/* 1126 */         byte[] templateBytes = rs.getBytes("hue_huella");
/*      */         
/* 1128 */         if (templateBytes != null && templateBytes.length > 0) {
/*      */           
/* 1130 */           String templateBase64 = Base64.getEncoder().encodeToString(templateBytes);
/*      */ 
/*      */           
/* 1133 */           PreparedStatement updateStmt = c.prepareStatement("UPDATE datosinternospersonal SET hue_huella = ? WHERE cedula = ?");
/*      */ 
/*      */           
/* 1136 */           updateStmt.setString(1, templateBase64);
/* 1137 */           updateStmt.setString(2, cedula);
/* 1138 */           updateStmt.executeUpdate();
/* 1139 */           updateStmt.close();
/*      */           
/* 1141 */           contador++;
/* 1142 */           System.out.println("Migrada huella para cédula: " + cedula);
/*      */         } 
/*      */       } 
/*      */       
/* 1146 */       System.out.println("Migración completada. " + contador + " registros actualizados.");
/* 1147 */       JOptionPane.showMessageDialog(null, "Migración completada. " + contador + " registros actualizados.", "Éxito", 1);
/*      */ 
/*      */       
/* 1150 */       rs.close();
/* 1151 */       stmt.close();
/* 1152 */       this.con.desconectar();
/*      */     }
/* 1154 */     catch (SQLException e) {
/* 1155 */       System.err.println("Error en migración: " + e.getMessage());
/* 1156 */       JOptionPane.showMessageDialog(null, "Error en migración: " + e.getMessage(), "Error", 0);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void identificarHuellaCESAR() throws IOException {
/*      */     try {
/* 1163 */       Connection c = this.con.conectar();
/*      */ 
/*      */       
/* 1166 */       PreparedStatement identificarStmt = c.prepareStatement("SELECT CEDULA,NOMBRES,APELLIDO1,APELLIDO2,AREA,HUELLA FROM empleados");
/* 1167 */       ResultSet rs = identificarStmt.executeQuery();
/*      */ 
/*      */       
/* 1170 */       while (rs.next()) {
/*      */ 
/*      */         
/* 1173 */         String CEDULA = rs.getString("CEDULA");
/* 1174 */         byte[] templateBuffer = rs.getBytes("HUELLA");
/* 1175 */         String nombre = rs.getString("NOMBRES");
/* 1176 */         String APELLIDO1 = rs.getString("APELLIDO1");
/* 1177 */         String APELLIDO2 = rs.getString("APELLIDO2");
/* 1178 */         String APELLIDO3 = rs.getString("AREA");
/*      */ 
/*      */         
/* 1181 */         DPFPTemplate referenceTemplate = DPFPGlobal.getTemplateFactory().createTemplate(templateBuffer);
/*      */         
/* 1183 */         setTemplate(referenceTemplate);
/*      */ 
/*      */ 
/*      */         
/* 1187 */         DPFPVerificationResult result = this.Verificador.verify(this.featuresverificacion, getTemplate());
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1192 */         if (result.isVerified()) {
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
/* 1207 */       JOptionPane.showMessageDialog(null, "No existe ningún registro que coincida con la huella", "Verificacion de Huella", 0);
/* 1208 */       setTemplate((DPFPTemplate)null);
/*      */ 
/*      */     
/*      */     }
/* 1212 */     catch (SQLException e) {
/*      */       
/* 1214 */       System.err.println("Error al identificar huella dactilar." + e.getMessage());
/*      */     } finally {
/* 1216 */       this.con.desconectar();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void cambiodia() throws SQLException {
/*      */     // No requerido con la tabla marcaciones de Supabase
/*      */     System.out.println("cambiodia() omitido - usando tabla marcaciones");
/*      */   }
/*      */   public void cambiodia_legacy() throws SQLException {
/* 1225 */     conexion cc3 = new conexion();
/* 1226 */     Connection cn3 = cc3.conectar();
/* 1227 */     Statement estado = cn3.createStatement();
/*      */ 
/*      */ 
/*      */     
/* 1231 */     Calendar fecha = new GregorianCalendar();
/*      */ 
/*      */ 
/*      */     
/* 1235 */     int año = fecha.get(1);
/* 1236 */     int mes = fecha.get(2);
/* 1237 */     int dia = fecha.get(5);
/* 1238 */     int hora = fecha.get(11);
/* 1239 */     int minuto = fecha.get(12);
/* 1240 */     int segundo = fecha.get(13);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1245 */     Formatter mesok = new Formatter();
/* 1246 */     Formatter diaok = new Formatter();
/* 1247 */     mes++;
/*      */     
/* 1249 */     switch (mes) {
/*      */       
/*      */       case 1:
/* 1252 */         mesok.format("%02d", new Object[] { Integer.valueOf(mes) });
/* 1253 */         this.fechaActual = año + "-" + mesok + "-" + dia;
/*      */         break;
/*      */       
/*      */       case 2:
/* 1257 */         mesok.format("%02d", new Object[] { Integer.valueOf(mes) });
/* 1258 */         this.fechaActual = año + "-" + mesok + "-" + dia;
/*      */         break;
/*      */       
/*      */       case 3:
/* 1262 */         mesok.format("%02d", new Object[] { Integer.valueOf(mes) });
/* 1263 */         this.fechaActual = año + "-" + mesok + "-" + dia;
/*      */         break;
/*      */       
/*      */       case 4:
/* 1267 */         mesok.format("%02d", new Object[] { Integer.valueOf(mes) });
/* 1268 */         this.fechaActual = año + "-" + mesok + "-" + dia;
/*      */         break;
/*      */       case 5:
/* 1271 */         mesok.format("%02d", new Object[] { Integer.valueOf(mes) });
/* 1272 */         this.fechaActual = año + "-" + mesok + "-" + dia;
/*      */         break;
/*      */       case 6:
/* 1275 */         mesok.format("%02d", new Object[] { Integer.valueOf(mes) });
/* 1276 */         this.fechaActual = año + "-" + mesok + "-" + dia;
/*      */         break;
/*      */       case 7:
/* 1279 */         mesok.format("%02d", new Object[] { Integer.valueOf(mes) });
/* 1280 */         this.fechaActual = año + "-" + mesok + "-" + dia;
/*      */         break;
/*      */       case 8:
/* 1283 */         mesok.format("%02d", new Object[] { Integer.valueOf(mes) });
/* 1284 */         this.fechaActual = año + "-" + mesok + "-" + dia;
/*      */         break;
/*      */       case 9:
/* 1287 */         mesok.format("%02d", new Object[] { Integer.valueOf(mes) });
/* 1288 */         this.fechaActual = año + "-" + mesok + "-" + dia;
/*      */         break;
/*      */       case 10:
/* 1291 */         mesok.format("%d", new Object[] { Integer.valueOf(mes) });
/* 1292 */         this.fechaActual = año + "-" + mesok + "-" + dia;
/*      */         break;
/*      */       case 11:
/* 1295 */         mesok.format("%d", new Object[] { Integer.valueOf(mes) });
/* 1296 */         this.fechaActual = año + "-" + mesok + "-" + dia;
/*      */         break;
/*      */       case 12:
/* 1299 */         mesok.format("%d", new Object[] { Integer.valueOf(mes) });
/* 1300 */         this.fechaActual = año + "-" + mesok + "-" + dia;
/*      */         break;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1311 */     switch (dia) {
/*      */       case 1:
/* 1313 */         diaok.format("%02d", new Object[] { Integer.valueOf(dia) });
/* 1314 */         this.fechaActual = this.fechaActual.substring(0, 7) + "-" + diaok;
/*      */         break;
/*      */       
/*      */       case 2:
/* 1318 */         diaok.format("%02d", new Object[] { Integer.valueOf(dia) });
/* 1319 */         this.fechaActual = this.fechaActual.substring(0, 7) + "-" + diaok;
/*      */         break;
/*      */       case 3:
/* 1322 */         diaok.format("%02d", new Object[] { Integer.valueOf(dia) });
/* 1323 */         this.fechaActual = this.fechaActual.substring(0, 7) + "-" + diaok;
/*      */         break;
/*      */       case 4:
/* 1326 */         diaok.format("%02d", new Object[] { Integer.valueOf(dia) });
/* 1327 */         this.fechaActual = this.fechaActual.substring(0, 7) + "-" + diaok;
/*      */         break;
/*      */       case 5:
/* 1330 */         diaok.format("%02d", new Object[] { Integer.valueOf(dia) });
/* 1331 */         this.fechaActual = this.fechaActual.substring(0, 7) + "-" + diaok;
/*      */         break;
/*      */       case 6:
/* 1334 */         diaok.format("%02d", new Object[] { Integer.valueOf(dia) });
/* 1335 */         this.fechaActual = this.fechaActual.substring(0, 7) + "-" + diaok;
/*      */         break;
/*      */       case 7:
/* 1338 */         diaok.format("%02d", new Object[] { Integer.valueOf(dia) });
/* 1339 */         this.fechaActual = this.fechaActual.substring(0, 7) + "-" + diaok;
/*      */         break;
/*      */       case 8:
/* 1342 */         diaok.format("%02d", new Object[] { Integer.valueOf(dia) });
/* 1343 */         this.fechaActual = this.fechaActual.substring(0, 7) + "-" + diaok;
/*      */         break;
/*      */       case 9:
/* 1346 */         diaok.format("%02d", new Object[] { Integer.valueOf(dia) });
/* 1347 */         this.fechaActual = this.fechaActual.substring(0, 7) + "-" + diaok;
/*      */         break;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1357 */     String sql = null;
/*      */ 
/*      */     
/*      */     try {
/* 1361 */       conexion cc = new conexion();
/*      */       
/* 1363 */       try (Connection cn = cc.conectar()) {
/* 1364 */         Statement estado1 = cn.createStatement();
/*      */         
/* 1366 */         ResultSet resultado = estado1.executeQuery("select fmarcacionProgramada from nomentradahuella order by fmarcacionProgramada desc limit 1");
/*      */         
/* 1368 */         while (resultado.next()) {
/* 1369 */           String fecha_gps = resultado.getString("fmarcacionProgramada");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1376 */           String sSubCadena = fecha_gps.substring(0, 10);
/*      */ 
/*      */           
/* 1379 */           if (!sSubCadena.equals(this.fechaActual)) {
/*      */ 
/*      */             
/*      */             try {
/*      */               
/* 1384 */               ConexionBD cc2 = new ConexionBD();
/* 1385 */               try (Connection cn2 = cc2.conectar()) {
/* 1386 */                 Statement estado2 = cn2.createStatement();
/* 1387 */                 String nombre = null, apellidos = null, cedula = null, centroTrabajo = null, organizacion = null, ocupacion = null;
/*      */                 
/* 1389 */                 String cedulaVerificacion = "";
/*      */ 
/*      */                 
/* 1392 */                 ResultSet resultado2 = estado2.executeQuery("SELECT nombres, apellidos, cedula, centroTrabajoCC, organizacion, ocupacion FROM datosinternospersonal where estado= 'Activo' and ocupacion != 'operador de vehiculo' order by  apellidos asc");
/*      */ 
/*      */                 
/* 1395 */                 while (resultado2.next()) {
/*      */ 
/*      */                   
/* 1398 */                   nombre = resultado2.getString("nombres");
/* 1399 */                   apellidos = resultado2.getString("apellidos");
/* 1400 */                   cedula = resultado2.getString("cedula");
/* 1401 */                   centroTrabajo = resultado2.getString("centroTrabajoCC");
/* 1402 */                   organizacion = resultado2.getString("organizacion");
/* 1403 */                   ocupacion = resultado2.getString("ocupacion");
/*      */                   
/* 1405 */                   sql = "INSERT INTO nomentradahuella(nombres, apellidos, cedula, fmarcacionProgramada, marcoMañana, marcoTarde, centroTrabajo, organizacion, ocupacion ) VALUES (?,?,?,?,?,?,?,?,?)";
/* 1406 */                   PreparedStatement pst3 = cn3.prepareStatement(sql);
/*      */ 
/*      */ 
/*      */                   
/*      */                   try {
/* 1411 */                     pst3.setString(1, nombre);
/* 1412 */                     pst3.setString(2, apellidos);
/* 1413 */                     pst3.setString(3, cedula);
/* 1414 */                     pst3.setString(4, this.fechaActual);
/* 1415 */                     pst3.setString(5, "No_Marco");
/* 1416 */                     pst3.setObject(6, "No_Marco");
/* 1417 */                     pst3.setObject(7, centroTrabajo);
/* 1418 */                     pst3.setObject(8, organizacion);
/* 1419 */                     pst3.setObject(9, ocupacion);
/*      */ 
/*      */                     
/* 1422 */                     int n = pst3.executeUpdate();
/*      */                     
/* 1424 */                     if (n > 0)
/*      */                     {
/*      */                       
/* 1427 */                       System.out.println("ARTICULO INGRESADO CON EXITO");
/*      */ 
/*      */ 
/*      */                     
/*      */                     }
/*      */ 
/*      */                   
/*      */                   }
/* 1435 */                   catch (SQLException ex) {
/*      */                     
/* 1437 */                     System.err.println("Error al guardar los datos de la huella. " + nombre);
/* 1438 */                     System.err.println(ex);
/* 1439 */                     JOptionPane.showMessageDialog(null, "su huella no pudo ser validada, reporte a sistemas para verificar que no exista un doble registro de su usuario en la BD");
/*      */                   } 
/*      */                 } 
/*      */ 
/*      */                 
/* 1444 */                 cn2.close();
/*      */               }
/*      */             
/* 1447 */             } catch (SQLException ex) {
/* 1448 */               JOptionPane.showMessageDialog(null, ex);
/*      */             } 
/*      */ 
/*      */ 
/*      */             
/* 1453 */             cn.close();
/*      */ 
/*      */             
/*      */             continue;
/*      */           } 
/*      */           
/* 1459 */           System.out.println("el dia ya ha sido registrado");
/*      */           
/* 1461 */           Reclutador2.clear();
/*      */         
/*      */         }
/*      */ 
/*      */       
/*      */       }
/*      */     
/*      */     }
/* 1469 */     catch (SQLException ex) {
/* 1470 */       System.out.println(ex);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void probarCapturaActual() {
/* 1476 */     System.out.println("\n=== PRUEBA DE CAPTURA ACTUAL ===");
/*      */ 
/*      */     
/* 1479 */     if (this.template != null) {
/* 1480 */       byte[] bytes = this.template.serialize();
/* 1481 */       System.out.println("Template OK - Bytes: " + bytes.length);
/*      */     } else {
/* 1483 */       System.out.println("Template NULL");
/*      */     } 
/*      */ 
/*      */     
/*      */     try {
/* 1488 */       int necesarias = this.Reclutador.getFeaturesNeeded();
/* 1489 */       System.out.println("Muestras necesarias: " + necesarias);
/*      */ 
/*      */       
/* 1492 */       DPFPTemplate tempTemplate = this.Reclutador.getTemplate();
/* 1493 */       if (tempTemplate != null) {
/* 1494 */         byte[] tempBytes = tempTemplate.serialize();
/* 1495 */         System.out.println("Reclutador tiene template - Bytes: " + tempBytes.length);
/*      */ 
/*      */         
/* 1498 */         if (this.template == null) {
/* 1499 */           setTemplate(tempTemplate);
/* 1500 */           System.out.println("Template asignado desde reclutador");
/*      */         } 
/*      */       } else {
/* 1503 */         System.out.println("Reclutador sin template listo");
/*      */       } 
/* 1505 */     } catch (Exception e) {
/* 1506 */       System.out.println("Error con reclutador: " + e.getMessage());
/*      */     } 
/*      */ 
/*      */     
/* 1510 */     System.out.println("Features Verificación: " + ((this.featuresverificacion != null) ? "OK" : "NULL"));
/* 1511 */     System.out.println("Features Inscripción: " + ((this.featuresinscripcion != null) ? "OK" : "NULL"));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void marcaentrada() {
/*      */     Date ahora = new Date();
/*      */     SimpleDateFormat horaFormato = new SimpleDateFormat("HH:mm:ss");
/*      */     SimpleDateFormat fechaFormato = new SimpleDateFormat("yyyy-MM-dd");
/*      */     String horaActual = horaFormato.format(ahora);
/*      */     String fechaHoy = fechaFormato.format(ahora);
/*      */     try {
/*      */       ConexionBD cc = new ConexionBD();
/*      */       try (Connection cn = cc.conectar()) {
/*      */         String numeroDoc = this.jTcedula.getText();
/*      */         PreparedStatement empStmt = cn.prepareStatement("SELECT id, employer_id FROM employees WHERE numero_documento = ?");
/*      */         empStmt.setString(1, numeroDoc);
/*      */         ResultSet empRs = empStmt.executeQuery();
/*      */         if (!empRs.next()) { System.err.println("Empleado no encontrado: " + numeroDoc); return; }
/*      */         String employeeId = empRs.getString("id");
/*      */         String employerId = empRs.getString("employer_id");
/*      */         empRs.close(); empStmt.close();
/*      */         PreparedStatement checkStmt = cn.prepareStatement("SELECT id, hora_entrada_real FROM marcaciones WHERE employee_id = ?::uuid AND fecha = ? LIMIT 1");
/*      */         checkStmt.setString(1, employeeId);
/*      */         checkStmt.setString(2, fechaHoy);
/*      */         ResultSet checkRs = checkStmt.executeQuery();
/*      */         if (checkRs.next()) {
/*      */           String marcacionId = checkRs.getString("id");
/*      */           checkRs.close(); checkStmt.close();
/*      */           PreparedStatement salidaStmt = cn.prepareStatement("UPDATE marcaciones SET hora_salida_real = ?, updated_at = NOW() WHERE id = ?::uuid");
/*      */           salidaStmt.setString(1, horaActual);
/*      */           salidaStmt.setString(2, marcacionId);
/*      */           salidaStmt.executeUpdate(); salidaStmt.close();
/*      */           EnviarTexto("Salida registrada: " + horaActual);
/*      */         } else {
/*      */           checkRs.close(); checkStmt.close();
/*      */           PreparedStatement insertStmt = cn.prepareStatement("INSERT INTO marcaciones (employee_id, employer_id, fecha, hora_entrada_real, validado, created_at, updated_at) VALUES (?::uuid, ?::uuid, ?, ?, false, NOW(), NOW())");
/*      */           insertStmt.setString(1, employeeId);
/*      */           insertStmt.setString(2, employerId);
/*      */           insertStmt.setString(3, fechaHoy);
/*      */           insertStmt.setString(4, horaActual);
/*      */           insertStmt.executeUpdate(); insertStmt.close();
/*      */           EnviarTexto("Entrada registrada: " + horaActual);
/*      */         }
/*      */       }
/*      */     } catch (SQLException ex) {
/*      */       JOptionPane.showMessageDialog(null, "Error al registrar marcacion: " + ex.getMessage());
/*      */     }
/*      */   }

/*      */   /*      */   private void cargarYConfigurarImagen() {
/* 1595 */     ImageIcon icon = new ImageIcon(getClass().getResource("/imagenes/logo-gap-X.jpeg"));
/* 1596 */     this.imagenOriginal = icon.getImage();
/*      */     
/* 1598 */     this.jLabel1.setHorizontalAlignment(0);
/* 1599 */     this.jLabel1.setVerticalAlignment(0);
/*      */ 
/*      */     
/* 1602 */     escalarYAsignar();
/*      */ 
/*      */     
/* 1605 */     this.jLabel1.addComponentListener(new ComponentAdapter()
/*      */         {
/*      */           public void componentResized(ComponentEvent e) {
/* 1608 */             CapturaHuella.this.escalarYAsignar();
/*      */           }
/*      */         });
/*      */   }
/*      */   
/*      */   private void escalarYAsignar() {
/* 1614 */     if (this.imagenOriginal == null)
/* 1615 */       return;  int w = Math.max(1, this.jLabel1.getWidth());
/* 1616 */     int h = Math.max(1, this.jLabel1.getHeight());
/* 1617 */     if (w == 0 || h == 0) {
/*      */       return;
/*      */     }
/* 1620 */     int iw = this.imagenOriginal.getWidth(null);
/* 1621 */     int ih = this.imagenOriginal.getHeight(null);
/* 1622 */     double ratio = Math.min(w / iw, h / ih);
/* 1623 */     int nw = Math.max(1, (int)Math.round(iw * ratio));
/* 1624 */     int nh = Math.max(1, (int)Math.round(ih * ratio));
/*      */     
/* 1626 */     Image imgEscalada = this.imagenOriginal.getScaledInstance(nw, nh, 4);
/* 1627 */     this.jLabel1.setIcon(new ImageIcon(imgEscalada));
/* 1628 */     this.jLabel1.setText((String)null);
/*      */   }
/*      */   
/*      */   private void insertEntrada() {
/*      */     try {
/* 1633 */       Connection cInsert = this.con.conectar();
/*      */       
/* 1635 */       String nombresPanta = this.jTNombres.getText();
/* 1636 */       String apellidosPanta = this.jTApellidos.getText();
/*      */       
/* 1638 */       Integer CedulaPanta = Integer.valueOf(Integer.parseInt(this.jTcedula.getText()));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1644 */       String sqlInsert = "INSERT INTO nomentradahuella (nombres, apellidos, cedula) VALUES (?, ?, ?)";
/* 1645 */       PreparedStatement insertStmt = cInsert.prepareStatement(sqlInsert);
/*      */ 
/*      */       
/* 1648 */       insertStmt.setString(1, nombresPanta);
/*      */       
/* 1650 */       insertStmt.setString(2, apellidosPanta);
/* 1651 */       insertStmt.setInt(3, CedulaPanta.intValue());
/*      */ 
/*      */       
/* 1654 */       int filasAfectadas = insertStmt.executeUpdate();
/*      */       
/* 1656 */       if (filasAfectadas > 0) {
/* 1657 */         System.out.print("Registro insertado con exito");
/*      */       }
/*      */       else {
/*      */         
/* 1661 */         System.out.print("Registro no se inserto");
/*      */       } 
/*      */       
/* 1664 */       insertStmt.close();
/*      */     
/*      */     }
/* 1667 */     catch (SQLException ex) {
/* 1668 */       Logger.getLogger(CapturaHuella.class.getName()).log(Level.SEVERE, (String)null, ex);
/*      */     } 
/*      */   }
/*      */ }


/* Location:              C:\Gap-x\dist\control_entrada.jar!\control_entrada\CapturaHuella.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */
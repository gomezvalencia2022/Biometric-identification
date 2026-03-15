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
/*      */ import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Cursor;
import java.awt.Image;
import javax.swing.ImageIcon;
/*      */ import java.awt.Font;
/*      */ import java.awt.Component;
/*      */ import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComboBox;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.FlowLayout;

/*      */ public class CapturaHuellaEnroler extends javax.swing.JFrame {
/*   56 */   Object EncontroHuella = null;
/*      */   private String loggedUserId = null;
/*      */   private String loggedUserRole = null;
/*      */   // Sesión persistente entre instancias (no pide login al volver)
/*      */   private static String staticUserId = null;
/*      */   private static String staticUserRole = null;
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
  private JLabel[] dotLabels;
  private JLabel lblProgresoTexto;
  private static final int TOTAL_MUESTRAS = 4;
/*      */   private JComboBox<String> cmbEmpresa;
/*      */   private JComboBox<String> cmbEmpleado;
/*      */   private List<String> empresaIds = new ArrayList<>();
/*      */   private List<String> empleadoDocNums = new ArrayList<>();
/*      */ 
/*      */ 
/*      */ 
/*      */   
  // ══════════════════════════════════════════════════════════════════
  // GAP COLOR PALETTE — modifica aquí para cambiar toda la paleta UI
  // ══════════════════════════════════════════════════════════════════
  private static final Color C_HEADER_BG     = new Color(64,  121, 150); // --primary-hover  hsl(200,40%,42%)
  private static final Color C_ACCENT        = new Color(80,  137, 165); // --primary        hsl(200,35%,48%)
  private static final Color C_SUCCESS       = new Color(33,  196, 93);  // --success        hsl(142,71%,45%)
  private static final Color C_SAVE         = new Color(210, 165, 55);  // --secondary dorado GAP hsl(42,78%,55%)
  private static final Color C_MUTED_BTN     = new Color(99,  114, 136); // --muted-fg (btn) hsl(215,16%,46%)
  private static final Color C_BG            = new Color(243, 245, 247); // --muted          hsl(210,20%,96%)
  private static final Color C_CARD          = Color.WHITE;              // --card
  private static final Color C_BORDER        = new Color(218, 224, 231); // --border         hsl(214,20%,88%)
  private static final Color C_INPUT_BG      = new Color(243, 245, 247); // --muted (inputs)
  private static final Color C_MUTED_TEXT    = new Color(99,  114, 136); // --muted-fg       hsl(215,16%,46%)
  private static final Color C_STATUS_ON_FG  = new Color(22,  101, 52);  // lector activo — texto
  private static final Color C_STATUS_ON_BG  = new Color(220, 252, 231); // lector activo — fondo
  private static final Color C_STATUS_OFF_FG = new Color(185, 28,  28);  // desconectado  — texto
  private static final Color C_STATUS_OFF_BG = new Color(254, 226, 226); // desconectado  — fondo
  private static final Color C_DARK_TEXT     = new Color(29,  37,  48);  // --foreground   texto oscuro botones

  private void initComponents() {
    // Initialize unused but declared field references
    this.jLcedula1 = new JLabel();
    this.jLcedula3 = new JLabel();
    this.jTnombre1 = new JTextField();
    this.jPanel2   = new JPanel();
    this.jPanel3   = new JPanel();
    this.jPanel4   = new JPanel();
    this.panBtns   = new JPanel();

    // Aliases de color — valores definidos en las constantes de clase arriba
    Color darkBlue  = C_HEADER_BG;
    Color accentBlue= C_ACCENT;
    Color greenBtn  = C_SAVE;
    Color grayBtn   = C_MUTED_BTN;
    Color bgGray    = C_BG;
    Color cardBg    = C_CARD;
    Color borderClr = C_BORDER;
    Color inputBg   = C_INPUT_BG;
    Color mutedText = C_MUTED_TEXT;

    // Window setup
    setDefaultCloseOperation(2);
    setTitle("GAP Sistema \u2014 Gesti\u00f3n de Huellas Dactilares");
    setResizable(true);
    setMinimumSize(new Dimension(480, 650));
    addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent evt) { CapturaHuellaEnroler.this.formWindowClosing(evt); }
      public void windowOpened(WindowEvent evt)  { CapturaHuellaEnroler.this.formWindowOpened(evt);  }
    });

    getContentPane().setBackground(bgGray);
    getContentPane().setLayout(new BorderLayout());

    // ══════════════════ HEADER ══════════════════
    JPanel headerPanel = new JPanel(new BorderLayout(14, 0));
    headerPanel.setBackground(darkBlue);
    headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 16, 10, 20));

    // Logo (cargarYConfigurarImagen sets the icon after init)
    this.jLabel1 = new JLabel();
    this.jLabel1.setPreferredSize(new Dimension(140, 90));
    this.jLabel1.setHorizontalAlignment(JLabel.CENTER);
    this.jLabel1.setVerticalAlignment(JLabel.CENTER);
    headerPanel.add(this.jLabel1, BorderLayout.WEST);

    JLabel lblTitle = new JLabel("Registro de Huellas Dactilares");
    lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
    lblTitle.setForeground(Color.WHITE);
    JLabel lblSubTitle = new JLabel("Sistema GAP  \u00b7  Biometr\u00eda Dactilar");
    lblSubTitle.setFont(new Font("Segoe UI", Font.PLAIN, 11));
    lblSubTitle.setForeground(mutedText);
    JPanel headerInner = new JPanel(new GridLayout(2, 1, 0, 2));
    headerInner.setOpaque(false);
    headerInner.add(lblTitle);
    headerInner.add(lblSubTitle);
    headerPanel.add(headerInner, BorderLayout.CENTER);
    getContentPane().add(headerPanel, BorderLayout.NORTH);

    // ══════════════════ CONTENT ══════════════════
    JPanel contentPanel = new JPanel(new GridBagLayout());
    contentPanel.setBackground(bgGray);
    contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 14, 10, 14));
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.fill    = GridBagConstraints.HORIZONTAL;
    gbc.weightx = 1.0;
    gbc.gridx   = 0;

    // ─── SELECTOR EMPRESA / EMPLEADO ─────────────
    JPanel selectorCard = new JPanel(new GridBagLayout());
    selectorCard.setBackground(cardBg);
    selectorCard.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(borderClr, 1),
        BorderFactory.createEmptyBorder(12, 14, 12, 14)));
    GridBagConstraints sc = new GridBagConstraints();
    sc.fill = GridBagConstraints.HORIZONTAL;
    sc.anchor = GridBagConstraints.WEST;
    sc.gridx = 0; sc.gridy = 0; sc.gridwidth = 2; sc.weightx = 1.0; sc.insets = new Insets(0, 0, 8, 0);
    JLabel selTitle = new JLabel("Seleccionar Empleador y Trabajador");
    selTitle.setFont(new Font("Segoe UI", Font.BOLD, 13));
    selTitle.setForeground(darkBlue);
    selectorCard.add(selTitle, sc);

    sc.gridy = 1; sc.gridwidth = 1; sc.weightx = 0; sc.fill = GridBagConstraints.NONE; sc.insets = new Insets(4, 0, 4, 10);
    JLabel lblEmpresaLbl = new JLabel("Empresa:");
    lblEmpresaLbl.setFont(new Font("Segoe UI", Font.PLAIN, 12));
    selectorCard.add(lblEmpresaLbl, sc);
    sc.gridx = 1; sc.fill = GridBagConstraints.HORIZONTAL; sc.weightx = 1.0; sc.insets = new Insets(4, 0, 4, 0);
    this.cmbEmpresa = new JComboBox<>();
    this.cmbEmpresa.setFont(new Font("Segoe UI", Font.PLAIN, 12));
    this.cmbEmpresa.setBorder(BorderFactory.createLineBorder(borderClr, 1));
    selectorCard.add(this.cmbEmpresa, sc);

    sc.gridy = 2; sc.gridx = 0; sc.fill = GridBagConstraints.NONE; sc.weightx = 0; sc.insets = new Insets(4, 0, 2, 10);
    JLabel lblEmpleadoLbl = new JLabel("Trabajador:");
    lblEmpleadoLbl.setFont(new Font("Segoe UI", Font.PLAIN, 12));
    selectorCard.add(lblEmpleadoLbl, sc);
    sc.gridx = 1; sc.fill = GridBagConstraints.HORIZONTAL; sc.weightx = 1.0; sc.insets = new Insets(4, 0, 2, 0);
    this.txtBuscarEmpleado = new JTextField();
    this.txtBuscarEmpleado.setFont(new Font("Segoe UI", Font.ITALIC, 12));
    this.txtBuscarEmpleado.setForeground(C_MUTED_TEXT);
    this.txtBuscarEmpleado.setBackground(inputBg);
    this.txtBuscarEmpleado.setOpaque(false);
    this.txtBuscarEmpleado.setText("Buscar trabajador por nombre...");
    this.txtBuscarEmpleado.setBorder(new RoundedInputBorder(borderClr, 10));
    this.txtBuscarEmpleado.addFocusListener(new java.awt.event.FocusAdapter() {
      public void focusGained(java.awt.event.FocusEvent e) {
        if (CapturaHuellaEnroler.this.txtBuscarEmpleado.getText().startsWith("Buscar")) {
          CapturaHuellaEnroler.this.txtBuscarEmpleado.setText("");
          CapturaHuellaEnroler.this.txtBuscarEmpleado.setFont(new Font("Segoe UI", Font.PLAIN, 12));
          CapturaHuellaEnroler.this.txtBuscarEmpleado.setForeground(C_ACCENT);
        }
      }
    });
    this.txtBuscarEmpleado.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
      public void insertUpdate(javax.swing.event.DocumentEvent e)  { filtrarEmpleados(txtBuscarEmpleado.getText()); }
      public void removeUpdate(javax.swing.event.DocumentEvent e)  { filtrarEmpleados(txtBuscarEmpleado.getText()); }
      public void changedUpdate(javax.swing.event.DocumentEvent e) {}
    });
    selectorCard.add(this.txtBuscarEmpleado, sc);

    sc.gridy = 3; sc.gridx = 0; sc.fill = GridBagConstraints.NONE; sc.weightx = 0; sc.insets = new Insets(0, 0, 4, 10);
    selectorCard.add(new JLabel(), sc); // spacer
    sc.gridx = 1; sc.fill = GridBagConstraints.HORIZONTAL; sc.weightx = 1.0; sc.insets = new Insets(0, 0, 4, 0);
    this.cmbEmpleado = new JComboBox<>();
    this.cmbEmpleado.setFont(new Font("Segoe UI", Font.PLAIN, 12));
    this.cmbEmpleado.setBorder(BorderFactory.createLineBorder(borderClr, 1));
    selectorCard.add(this.cmbEmpleado, sc);

    this.cmbEmpresa.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        int idx = CapturaHuellaEnroler.this.cmbEmpresa.getSelectedIndex();
        if (idx > 0 && idx < CapturaHuellaEnroler.this.empresaIds.size())
          CapturaHuellaEnroler.this.cargarEmpleados(CapturaHuellaEnroler.this.empresaIds.get(idx));
      }
    });
    this.cmbEmpleado.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if (CapturaHuellaEnroler.this.isFilteringCombo) return;
        int idx = CapturaHuellaEnroler.this.cmbEmpleado.getSelectedIndex();
        if (idx > 0 && idx < CapturaHuellaEnroler.this.empleadoDocNums.size()) {
          String raw = CapturaHuellaEnroler.this.empleadoDocNums.get(idx);
          String[] data = raw.split("\\|");
          // Poblar campos directamente desde el dato almacenado (identificación por ID)
          jTRecibeCedula.setText(data[0]);
          CapturaHuellaEnroler.this.jTnombre.setText(data.length > 1 ? data[1] : "");
          CapturaHuellaEnroler.this.jTapellidos.setText(data.length > 2 ? data[2] : "");
          // Refrescar "Tiene Huella" consultando por número de documento (no por nombre)
          CapturaHuellaEnroler.this.buscarPorCedula();
        } else if (idx == 0) {
          jTRecibeCedula.setText("");
          CapturaHuellaEnroler.this.jTnombre.setText("");
          CapturaHuellaEnroler.this.jTapellidos.setText("");
          CapturaHuellaEnroler.this.jTcontieneHuella.setText("--");
        }
      }
    });
    gbc.gridy = 0; gbc.weighty = 0; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.insets = new Insets(0, 0, 6, 0);
    contentPanel.add(selectorCard, gbc);

    // ─── DATOS DEL EMPLEADO ───────────────────────
    JPanel infoCard = new JPanel(new GridBagLayout());
    infoCard.setBackground(cardBg);
    infoCard.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(borderClr, 1),
        BorderFactory.createEmptyBorder(12, 14, 12, 14)));
    GridBagConstraints ic = new GridBagConstraints();
    ic.fill   = GridBagConstraints.HORIZONTAL;
    ic.anchor = GridBagConstraints.WEST;

    ic.gridx = 0; ic.gridy = 0; ic.gridwidth = 4; ic.weightx = 1.0; ic.insets = new Insets(0, 0, 8, 0);
    JLabel infoTitle = new JLabel("Datos del trabajador");
    infoTitle.setFont(new Font("Segoe UI", Font.BOLD, 13));
    infoTitle.setForeground(darkBlue);
    infoCard.add(infoTitle, ic);

    // Cedula + Tiene Huella
    ic.gridy = 1; ic.gridwidth = 1; ic.weightx = 0; ic.fill = GridBagConstraints.NONE; ic.insets = new Insets(4, 0, 4, 10);
    this.jLcedula = new JLabel("Cedula:");
    this.jLcedula.setFont(new Font("Segoe UI", Font.PLAIN, 12));
    infoCard.add(this.jLcedula, ic);
    ic.gridx = 1; ic.fill = GridBagConstraints.HORIZONTAL; ic.weightx = 0.4; ic.insets = new Insets(4, 0, 4, 16);
    jTRecibeCedula = new JTextField();
    jTRecibeCedula.setFont(new Font("Segoe UI", Font.PLAIN, 12));
    jTRecibeCedula.setBackground(inputBg);
    jTRecibeCedula.setOpaque(false);
    jTRecibeCedula.setBorder(new RoundedInputBorder(borderClr, 10));
    jTRecibeCedula.setToolTipText("N\u00ba de documento de identidad");
    jTRecibeCedula.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) { CapturaHuellaEnroler.this.jTRecibeCedulaActionPerformed(evt); }
    });
    jTRecibeCedula.addKeyListener(new KeyAdapter() {
      public void keyTyped(KeyEvent evt) { CapturaHuellaEnroler.this.jTRecibeCedulaKeyTyped(evt); }
    });
    infoCard.add(jTRecibeCedula, ic);
    ic.gridx = 2; ic.fill = GridBagConstraints.NONE; ic.weightx = 0; ic.insets = new Insets(4, 0, 4, 10);
    JLabel lblTieneHuella = new JLabel("Tiene Huella:");
    lblTieneHuella.setFont(new Font("Segoe UI", Font.PLAIN, 12));
    infoCard.add(lblTieneHuella, ic);
    ic.gridx = 3; ic.fill = GridBagConstraints.HORIZONTAL; ic.weightx = 0.2; ic.insets = new Insets(4, 0, 4, 0);
    this.jTcontieneHuella = new JLabel("--");
    this.jTcontieneHuella.setFont(new Font("Segoe UI", Font.BOLD, 12));
    this.jTcontieneHuella.setForeground(grayBtn);
    infoCard.add(this.jTcontieneHuella, ic);

    // Nombres
    ic.gridy = 2; ic.gridx = 0; ic.gridwidth = 1; ic.fill = GridBagConstraints.NONE; ic.weightx = 0; ic.insets = new Insets(4, 0, 4, 10);
    this.jLcedula2 = new JLabel("Nombres:");
    this.jLcedula2.setFont(new Font("Segoe UI", Font.PLAIN, 12));
    infoCard.add(this.jLcedula2, ic);
    ic.gridx = 1; ic.gridwidth = 3; ic.fill = GridBagConstraints.HORIZONTAL; ic.weightx = 1.0; ic.insets = new Insets(4, 0, 4, 0);
    this.jTnombre = new JTextField();
    this.jTnombre.setFont(new Font("Segoe UI", Font.PLAIN, 12));
    this.jTnombre.setBackground(inputBg);
    this.jTnombre.setOpaque(false);
    this.jTnombre.setBorder(new RoundedInputBorder(borderClr, 10));
    this.jTnombre.addKeyListener(new KeyAdapter() {
      public void keyTyped(KeyEvent evt) { CapturaHuellaEnroler.this.jTnombreKeyTyped(evt); }
    });
    infoCard.add(this.jTnombre, ic);

    // Apellidos
    ic.gridy = 3; ic.gridx = 0; ic.gridwidth = 1; ic.fill = GridBagConstraints.NONE; ic.weightx = 0; ic.insets = new Insets(4, 0, 4, 10);
    this.jLcedula4 = new JLabel("Apellidos:");
    this.jLcedula4.setFont(new Font("Segoe UI", Font.PLAIN, 12));
    infoCard.add(this.jLcedula4, ic);
    ic.gridx = 1; ic.gridwidth = 3; ic.fill = GridBagConstraints.HORIZONTAL; ic.weightx = 1.0; ic.insets = new Insets(4, 0, 4, 0);
    this.jTapellidos = new JTextField();
    this.jTapellidos.setFont(new Font("Segoe UI", Font.PLAIN, 12));
    this.jTapellidos.setBackground(inputBg);
    this.jTapellidos.setOpaque(false);
    this.jTapellidos.setBorder(new RoundedInputBorder(borderClr, 10));
    this.jTapellidos.addKeyListener(new KeyAdapter() {
      public void keyTyped(KeyEvent evt) { CapturaHuellaEnroler.this.jTapellidosKeyTyped(evt); }
    });
    infoCard.add(this.jTapellidos, ic);
    gbc.gridy = 1; gbc.insets = new Insets(0, 0, 6, 0);
    contentPanel.add(infoCard, gbc);

    // ─── VISTA PREVIA HUELLA ──────────────────────
    this.panHuellas = new JPanel(new BorderLayout(0, 8));
    this.panHuellas.setBackground(cardBg);
    this.panHuellas.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(borderClr, 1),
        BorderFactory.createEmptyBorder(12, 14, 12, 14)));
    // Header: título + badge de estado del sensor
    JPanel fpHeader = new JPanel(new BorderLayout(8, 0));
    fpHeader.setOpaque(false);
    JLabel fpTitle = new JLabel("Vista Previa \u2014 Huella Capturada");
    fpTitle.setFont(new Font("Segoe UI", Font.BOLD, 13));
    fpTitle.setForeground(darkBlue);
    fpHeader.add(fpTitle, BorderLayout.WEST);
    this.lblSensorStatus = new JLabel("  \u25CF  Desconectado  ");
    this.lblSensorStatus.setFont(new Font("Segoe UI", Font.BOLD, 11));
    this.lblSensorStatus.setForeground(C_STATUS_OFF_FG);
    this.lblSensorStatus.setBackground(C_STATUS_OFF_BG);
    this.lblSensorStatus.setOpaque(true);
    this.lblSensorStatus.setBorder(BorderFactory.createEmptyBorder(3, 8, 3, 8));
    fpHeader.add(this.lblSensorStatus, BorderLayout.EAST);
    this.panHuellas.add(fpHeader, BorderLayout.NORTH);
    this.jPanel1 = new JPanel(new BorderLayout());
    this.jPanel1.setBackground(inputBg);
    this.jPanel1.setBorder(BorderFactory.createLineBorder(borderClr, 1));
    this.jPanel1.setMinimumSize(new Dimension(460, 200));
    this.jPanel1.setPreferredSize(new Dimension(460, 360));
    this.lblImagenHuella = new JLabel("Coloque su dedo en el lector...", JLabel.CENTER);
    this.lblImagenHuella.setFont(new Font("Segoe UI", Font.ITALIC, 12));
    this.lblImagenHuella.setForeground(mutedText);
    this.jPanel1.add(this.lblImagenHuella, BorderLayout.CENTER);
    this.panHuellas.add(this.jPanel1, BorderLayout.CENTER);

    // ─── INDICADOR DE PROGRESO DE MUESTRAS ───────────────────────────
    JPanel progressPanel = new JPanel(new BorderLayout(0, 4));
    progressPanel.setOpaque(false);
    progressPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 2, 0));

    this.lblProgresoTexto = new JLabel("Capture 4 huellas para crear el template", JLabel.CENTER);
    this.lblProgresoTexto.setFont(new Font("Segoe UI", Font.PLAIN, 11));
    this.lblProgresoTexto.setForeground(C_MUTED_TEXT);

    JPanel dotsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 0));
    dotsPanel.setOpaque(false);
    this.dotLabels = new JLabel[TOTAL_MUESTRAS];
    for (int i = 0; i < TOTAL_MUESTRAS; i++) {
        this.dotLabels[i] = new JLabel("\u25CF");
        this.dotLabels[i].setFont(new Font("Segoe UI", Font.PLAIN, 24));
        this.dotLabels[i].setForeground(C_BORDER);
        dotsPanel.add(this.dotLabels[i]);
    }

    progressPanel.add(this.lblProgresoTexto, BorderLayout.NORTH);
    progressPanel.add(dotsPanel, BorderLayout.CENTER);
    this.panHuellas.add(progressPanel, BorderLayout.SOUTH);
    // weighty=1 => panHuellas se lleva todo el espacio vertical sobrante
    gbc.gridy = 2; gbc.weighty = 1.0; gbc.fill = GridBagConstraints.BOTH; gbc.insets = new Insets(0, 0, 8, 0);
    contentPanel.add(this.panHuellas, gbc);

    // (txtArea kept initialized but not shown)
    this.txtArea = new javax.swing.JTextPane();
    this.txtArea.setContentType("text/html");
    this.txtArea.setEditable(false);

    // ─── BOTONES ──────────────────────────────────
    JPanel btnCard = new JPanel(new GridBagLayout());
    btnCard.setBackground(cardBg);
    btnCard.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(borderClr, 1),
        BorderFactory.createEmptyBorder(12, 14, 12, 14)));
    GridBagConstraints bc = new GridBagConstraints();
    bc.fill = GridBagConstraints.HORIZONTAL;
    bc.weightx = 1.0;
    bc.gridy = 0;

    bc.gridx = 0; bc.insets = new Insets(0, 0, 0, 6);
    this.btnVerificar = new JButton("Volver");
    applyBtnStyle(this.btnVerificar, grayBtn, Color.WHITE);
    this.btnVerificar.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) { CapturaHuellaEnroler.this.btnVerificarActionPerformed(evt); }
    });
    btnCard.add(this.btnVerificar, bc);

    bc.gridx = 1; bc.insets = new Insets(0, 3, 0, 3);
    this.btnIdentificar = new JButton("Identificar");
    applyBtnStyle(this.btnIdentificar, accentBlue, Color.WHITE);
    this.btnIdentificar.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) { CapturaHuellaEnroler.this.btnIdentificarActionPerformed(evt); }
    });
    btnCard.add(this.btnIdentificar, bc);

    bc.gridx = 2; bc.insets = new Insets(0, 6, 0, 0);
    this.btnGuardar = new JButton("Guardar Huella");
    applyBtnStyle(this.btnGuardar, greenBtn, Color.BLACK);
    this.btnGuardar.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) { CapturaHuellaEnroler.this.btnGuardarActionPerformed(evt); }
    });
    btnCard.add(this.btnGuardar, bc);

    this.btnSalir = new JButton("Salir");
    this.btnSalir.setVisible(false);
    this.btnSalir.setEnabled(false);
    this.btnSalir.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) { CapturaHuellaEnroler.this.btnSalirActionPerformed(evt); }
    });

    gbc.gridy = 3; gbc.weighty = 0; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.insets = new Insets(0, 0, 0, 0);
    contentPanel.add(btnCard, gbc);

    getContentPane().add(contentPanel, BorderLayout.CENTER);
    setSize(new Dimension(520, 830));
    setLocationRelativeTo(null);
  }

  private void applyBtnStyle(JButton btn, Color bg, Color fg) {
    btn.setBackground(bg);
    btn.setForeground(fg);
    btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
    btn.setBorder(BorderFactory.createEmptyBorder(10, 18, 10, 18));
    btn.setFocusPainted(false);
    btn.setOpaque(true);
    btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
  }

  /** Border redondeado para inputs.
   *  NOTA: paintBorder se ejecuta DESPUÉS de paintComponent (el texto), por eso
   *  NO se puede pintar fill aquí sin tapar el texto. En su lugar el fondo se
   *  pinta usando un JTextField subclaseado que pinta el fondo redondeado en
   *  paintComponent antes del texto. */
  private static class RoundedInputBorder implements javax.swing.border.Border {
    private final Color stroke;
    private final int r;
    RoundedInputBorder(Color stroke, int r) { this.stroke = stroke; this.r = r; }
    public java.awt.Insets getBorderInsets(Component c) { return new java.awt.Insets(6, 10, 6, 10); }
    public boolean isBorderOpaque() { return false; }
    public void paintBorder(Component c, java.awt.Graphics g, int x, int y, int w, int h) {
      java.awt.Graphics2D g2 = (java.awt.Graphics2D) g.create();
      g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
      g2.setColor(stroke);
      g2.setStroke(new java.awt.BasicStroke(1.2f));
      g2.drawRoundRect(x, y, w - 1, h - 1, r, r);
      g2.dispose();
    }
  }

  /** JTextField con fondo redondeado pintado en paintComponent (antes del texto) */
  private static javax.swing.JTextField roundedField(Color bg, Color border, int radius) {
    return new javax.swing.JTextField() {
      { setOpaque(false); setBorder(new RoundedInputBorder(border, radius)); }
      @Override protected void paintComponent(java.awt.Graphics g) {
        java.awt.Graphics2D g2 = (java.awt.Graphics2D) g.create();
        g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(bg);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
        g2.dispose();
        super.paintComponent(g);
      }
    };
  }

  private void cargarLogoEnroler() {
    try {
      java.net.URL imgUrl = getClass().getResource("/imagenes/logo-gap-X.jpeg");
      if (imgUrl == null) return;
      ImageIcon icon = new ImageIcon(imgUrl);
      this.imagenOriginalEnroler = icon.getImage();
      int w = Math.max(1, this.jLabel1.getPreferredSize().width);
      int h = Math.max(1, this.jLabel1.getPreferredSize().height);
      int iw = this.imagenOriginalEnroler.getWidth(null);
      int ih = this.imagenOriginalEnroler.getHeight(null);
      if (iw > 0 && ih > 0) {
        double ratio = Math.min((double)w / iw, (double)h / ih);
        int nw = Math.max(1, (int)Math.round(iw * ratio));
        int nh = Math.max(1, (int)Math.round(ih * ratio));
        Image scaled = this.imagenOriginalEnroler.getScaledInstance(nw, nh, java.awt.Image.SCALE_SMOOTH);
        this.jLabel1.setIcon(new ImageIcon(scaled));
      }
    } catch (Exception ex) { /* logo not found, skip */ }
  }


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
/*      */     mostrarLogin();
/*      */     if (this.loggedUserId != null) { cargarEmpresas(); }
/*      */   }
/*      */
/*      */   private static final String SUPABASE_URL = "https://bbhrkovseinvguoxeybu.supabase.co";
/*      */   private static final String SUPABASE_ANON_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImJiaHJrb3ZzZWludmd1b3hleWJ1Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3NzAwMjI4MTEsImV4cCI6MjA4NTU5ODgxMX0.NWcK_Z-Qd2Ca2uBpgNjFCnMyTeQPCf1cy-UsgNh_3mg";
/*      */
/*      */   private void mostrarLogin() {
/*      */     // Si ya hay sesión activa de una visita anterior, reutilizarla
/*      */     if (staticUserId != null) {
/*      */       this.loggedUserId = staticUserId;
/*      */       this.loggedUserRole = staticUserRole;
/*      */       return;
/*      */     }
/*      */     javax.swing.JTextField txtEmail = new javax.swing.JTextField(25);
/*      */     javax.swing.JPasswordField txtPass = new javax.swing.JPasswordField(25);
/*      */     JPanel panel = new JPanel(new GridBagLayout());
/*      */     GridBagConstraints g = new GridBagConstraints();
/*      */     g.insets = new Insets(5, 5, 5, 5); g.anchor = GridBagConstraints.WEST;
/*      */     g.gridx = 0; g.gridy = 0; g.gridwidth = 2;
/*      */     panel.add(new JLabel("<html><b>Inicio de sesi\u00f3n</b><br><small>Ingrese sus credenciales del sistema GAP</small></html>"), g);
/*      */     g.gridy = 1; g.gridwidth = 1; g.fill = GridBagConstraints.NONE; g.weightx = 0;
/*      */     panel.add(new JLabel("Correo:"), g);
/*      */     g.gridx = 1; g.fill = GridBagConstraints.HORIZONTAL; g.weightx = 1.0;
/*      */     panel.add(txtEmail, g);
/*      */     g.gridy = 2; g.gridx = 0; g.fill = GridBagConstraints.NONE; g.weightx = 0;
/*      */     panel.add(new JLabel("Contrase\u00f1a:"), g);
/*      */     g.gridx = 1; g.fill = GridBagConstraints.HORIZONTAL; g.weightx = 1.0;
/*      */     panel.add(txtPass, g);
/*      */     int result = JOptionPane.showConfirmDialog(this, panel, "Inicio de sesi\u00f3n", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
/*      */     if (result != JOptionPane.OK_OPTION) {
/*      */       stop(); dispose(); (new CapturaHuella()).setVisible(true); return;
/*      */     }
/*      */     String email = txtEmail.getText().trim();
/*      */     String password = new String(txtPass.getPassword());
/*      */     if (email.isEmpty() || password.isEmpty()) {
/*      */       JOptionPane.showMessageDialog(this, "Debe ingresar correo y contrase\u00f1a.", "Error", JOptionPane.ERROR_MESSAGE);
/*      */       mostrarLogin(); return;
/*      */     }
/*      */     String userId = autenticarSupabase(email, password);
/*      */     if (userId == null) {
/*      */       JOptionPane.showMessageDialog(this, "Credenciales incorrectas.", "Acceso denegado", JOptionPane.ERROR_MESSAGE);
/*      */       mostrarLogin(); return;
/*      */     }
/*      */     this.loggedUserId = userId;
/*      */     try {
/*      */       ConexionBD cc = new ConexionBD(); Connection cn = cc.conectar();
/*      */       PreparedStatement stmt = cn.prepareStatement("SELECT role FROM user_roles WHERE user_id = ?::uuid LIMIT 1");
/*      */       stmt.setString(1, this.loggedUserId);
/*      */       ResultSet rs = stmt.executeQuery();
/*      */       this.loggedUserRole = rs.next() ? rs.getString("role") : "cliente_gap";
/*      */       rs.close(); stmt.close(); cn.close();
/*      */     } catch (SQLException ex) {
/*      */       JOptionPane.showMessageDialog(this, "Error al obtener rol: " + ex.getMessage());
/*      */     }
/*      */     // Guardar sesión para futuras instancias
/*      */     staticUserId = this.loggedUserId;
/*      */     staticUserRole = this.loggedUserRole;
/*      */   }
/*      */
/*      */   private String autenticarSupabase(String email, String password) {
/*      */     try {
/*      */       String esc = email.replace("\\", "\\\\").replace("\"", "\\\"");
/*      */       String psc = password.replace("\\", "\\\\").replace("\"", "\\\"");
/*      */       String body = "{\"email\":\"" + esc + "\",\"password\":\"" + psc + "\"}";
/*      */       java.net.URL apiUrl = new java.net.URL(SUPABASE_URL + "/auth/v1/token?grant_type=password");
/*      */       java.net.HttpURLConnection conn = (java.net.HttpURLConnection) apiUrl.openConnection();
/*      */       conn.setRequestMethod("POST");
/*      */       conn.setRequestProperty("apikey", SUPABASE_ANON_KEY);
/*      */       conn.setRequestProperty("Content-Type", "application/json");
/*      */       conn.setConnectTimeout(8000); conn.setReadTimeout(8000);
/*      */       conn.setDoOutput(true);
/*      */       conn.getOutputStream().write(body.getBytes("UTF-8"));
/*      */       int code = conn.getResponseCode();
/*      */       java.io.InputStream is = (code == 200) ? conn.getInputStream() : conn.getErrorStream();
/*      */       java.io.BufferedReader br = new java.io.BufferedReader(new java.io.InputStreamReader(is, "UTF-8"));
/*      */       StringBuilder sb = new StringBuilder(); String ln;
/*      */       while ((ln = br.readLine()) != null) sb.append(ln);
/*      */       br.close(); conn.disconnect();
/*      */       if (code != 200) return null;
/*      */       String resp = sb.toString();
/*      */       int userIdx = resp.indexOf("\"user\"");
/*      */       if (userIdx < 0) return null;
/*      */       String userPart = resp.substring(userIdx);
/*      */       int idIdx = userPart.indexOf("\"id\":\"");
/*      */       if (idIdx < 0) return null;
/*      */       int start = idIdx + 6;
/*      */       return userPart.substring(start, userPart.indexOf("\"", start));
/*      */     } catch (Exception ex) {
/*      */       JOptionPane.showMessageDialog(this, "Error de conexi\u00f3n: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
/*      */       return null;
/*      */     }
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
/*      */       PreparedStatement stmt;
/*      */       if ("superadmin".equals(this.loggedUserRole)) {
/*      */         stmt = cn.prepareStatement("SELECT id, razon_social FROM employers WHERE estado = 'ACTIVO' ORDER BY razon_social");
/*      */       } else {
/*      */         stmt = cn.prepareStatement(
/*      */           "SELECT e.id, e.razon_social FROM employers e " +
/*      */           "JOIN client_companies cc ON cc.employer_id = e.id " +
/*      */           "WHERE cc.user_id = ?::uuid AND e.estado = 'ACTIVO' " +
/*      */           "ORDER BY e.razon_social");
/*      */         stmt.setString(1, this.loggedUserId);
/*      */       }
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
/*      */     this.allEmpleadoItems.clear();
/*      */     this.allEmpleadoDocNums_all.clear();
/*      */     jTRecibeCedula.setText("");
/*      */     this.jTnombre.setText("");
/*      */     this.jTapellidos.setText("");
/*      */     if (this.txtBuscarEmpleado != null) {
/*      */       this.txtBuscarEmpleado.setFont(new Font("Segoe UI", Font.ITALIC, 12));
/*      */       this.txtBuscarEmpleado.setForeground(C_MUTED_TEXT);
/*      */       this.txtBuscarEmpleado.setText("Buscar empleado por nombre...");
/*      */     }
/*      */     try {
/*      */       ConexionBD cc = new ConexionBD();
/*      */       Connection cn = cc.conectar();
/*      */       PreparedStatement stmt = cn.prepareStatement("SELECT numero_documento, nombres, apellidos FROM employees WHERE employer_id = ?::uuid AND estado = 'activo' ORDER BY apellidos, nombres");
/*      */       stmt.setString(1, employerId);
/*      */       ResultSet rs = stmt.executeQuery();
/*      */       while (rs.next()) {
/*      */         String docNum = rs.getString("numero_documento");
/*      */         String nombres = rs.getString("nombres");
/*      */         String apellidos = rs.getString("apellidos");
/*      */         this.allEmpleadoDocNums_all.add(docNum + "|" + nombres + "|" + apellidos);
/*      */         this.allEmpleadoItems.add(apellidos + ", " + nombres + "  \u00b7  " + docNum);
/*      */       }
/*      */       rs.close(); stmt.close(); cn.close();
/*      */       filtrarEmpleados("");
/*      */     } catch (SQLException e) {
/*      */       JOptionPane.showMessageDialog(null, "Error cargando empleados: " + e.getMessage());
/*      */     }
/*      */   }

  private void filtrarEmpleados(String texto) {
    if (texto != null && texto.startsWith("Buscar")) texto = "";
    isFilteringCombo = true;
    this.cmbEmpleado.removeAllItems();
    this.empleadoDocNums.clear();
    this.cmbEmpleado.addItem("-- Seleccione el trabajador --");
    this.empleadoDocNums.add(null);
    String lower = (texto == null) ? "" : texto.toLowerCase().trim();
    for (int i = 0; i < allEmpleadoItems.size(); i++) {
      if (lower.isEmpty() || allEmpleadoItems.get(i).toLowerCase().contains(lower)) {
        this.cmbEmpleado.addItem(allEmpleadoItems.get(i));
        this.empleadoDocNums.add(allEmpleadoDocNums_all.get(i));
      }
    }
    isFilteringCombo = false;
  }

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
/*      */       UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
/*      */     } catch (Exception e) {
/*      */       JOptionPane.showMessageDialog(null, "Imposible modificar el tema visual", "Lookandfeel inválido.", 0);
/*      */     }  initComponents(); cargarLogoEnroler(); this.txtArea.setEditable(false); String cedula_intern = "";
/*      */     jTRecibeCedula.setText(cedula_intern);
/*  623 */     buscarPorCedula(); } public static String TEMPLATE_PROPERTY = "template"; public DPFPFeatureSet featuresinscripcion; public DPFPFeatureSet featuresverificacion; conexion con; private JButton btnGuardar; private JButton btnIdentificar; private JButton btnSalir; private JButton btnVerificar; private JLabel jLabel1; private Image imagenOriginalEnroler; private JLabel jLcedula; private JLabel jLcedula1; private JLabel jLcedula2; private JLabel jLcedula3; private JLabel jLcedula4; private JPanel jPanel1; private JPanel jPanel2; private JPanel jPanel3; private JPanel jPanel4; private JScrollPane jScrollPane1; public static JTextField jTRecibeCedula; private JTextField jTapellidos; private JLabel jTcontieneHuella; private JTextField jTnombre; private JTextField jTnombre1; private JLabel lblImagenHuella; private JPanel panBtns; private JPanel panHuellas; private javax.swing.JTextPane txtArea; private JLabel lblSensorStatus; private JTextField txtBuscarEmpleado; private List<String> allEmpleadoItems = new ArrayList<>(); private List<String> allEmpleadoDocNums_all = new ArrayList<>(); private boolean isFilteringCombo = false; public void guardarHuella() { if (jTRecibeCedula.getText().isEmpty() || this.jTnombre.getText().isEmpty() || this.jTapellidos.getText().isEmpty()) {
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
/*      */             stop(); setTemplate(this.Reclutador.getTemplate()); EnviarTexto("La Plantilla de la Huella ha Sido Creada, ya puede Verificarla o Identificarla"); this.btnIdentificar.setEnabled(false); this.btnVerificar.setEnabled(true); this.btnGuardar.setEnabled(true); this.btnGuardar.grabFocus(); break;
/*      */           case TEMPLATE_STATUS_FAILED:
/*      */             this.Reclutador.clear(); stop(); EstadoHuellas(); setTemplate((DPFPTemplate)null); JOptionPane.showMessageDialog(this, "La Plantilla de la Huella no pudo ser creada, Repita el Proceso", "Inscripcion de Huellas Dactilares", 0); start(); break; }  }   }
/*      */   public DPFPFeatureSet extraerCaracteristicas(DPFPSample sample, DPFPDataPurpose purpose) { DPFPFeatureExtraction extractor = DPFPGlobal.getFeatureExtractionFactory().createFeatureExtraction(); try { return extractor.createFeatureSet(sample, purpose); } catch (DPFPImageQualityException e) { return null; }  }
/*      */   public Image CrearImagenHuella(DPFPSample sample) { return DPFPGlobal.getSampleConversionFactory().createImage(sample); }
/*      */   public void DibujarHuella(Image image) { this.lblImagenHuella.setIcon(new ImageIcon(image.getScaledInstance(this.lblImagenHuella.getWidth(), this.lblImagenHuella.getHeight(), 1))); repaint(); }
/*      */   public void EstadoHuellas() {
    int needed   = this.Reclutador.getFeaturesNeeded();
    int captured = TOTAL_MUESTRAS - needed;
    EnviarTexto("Muestra de Huellas Necesarias para Guardar Template " + needed);
    // Actualizar puntos: verde = capturada, gris = pendiente
    for (int i = 0; i < TOTAL_MUESTRAS; i++) {
        this.dotLabels[i].setForeground(i < captured ? C_SUCCESS : C_BORDER);
    }
    // Actualizar texto de progreso
    if (needed == 0) {
        this.lblProgresoTexto.setText("\u2714 \u00a1Template listo! Puede guardar la huella");
        this.lblProgresoTexto.setForeground(C_SUCCESS);
    } else {
        this.lblProgresoTexto.setText("Faltan " + needed + " muestra" + (needed == 1 ? "" : "s") + " de " + TOTAL_MUESTRAS);
        this.lblProgresoTexto.setForeground(C_MUTED_TEXT);
    }
  }
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
/*      */      }
  private void appendHtmlLog(String html) {
    try {
      javax.swing.text.html.HTMLDocument doc = (javax.swing.text.html.HTMLDocument) this.txtArea.getDocument();
      javax.swing.text.html.HTMLEditorKit kit = (javax.swing.text.html.HTMLEditorKit) this.txtArea.getEditorKit();
      kit.insertHTML(doc, doc.getLength(), html, 0, 0, null);
    } catch (Exception ex) {}
    this.txtArea.setCaretPosition(this.txtArea.getDocument().getLength());
  }
  public void EnviarTexto(String string) {
    String s = string.replace("&","&amp;").replace("<","&lt;").replace(">","&gt;");
    if (this.lblSensorStatus == null) return;
    if (string.contains("no Conectado") || string.contains("Desactivado") || string.contains("No se est")) {
      this.lblSensorStatus.setText("  \u25CF  Desconectado  ");
      this.lblSensorStatus.setForeground(C_STATUS_OFF_FG);
      this.lblSensorStatus.setBackground(C_STATUS_OFF_BG);
    } else if (string.contains("Activado") || string.contains("Conectado") || string.contains("Utilizando")) {
      this.lblSensorStatus.setText("  \u25CF  Lector Activo  ");
      this.lblSensorStatus.setForeground(C_STATUS_ON_FG);
      this.lblSensorStatus.setBackground(C_STATUS_ON_BG);
    }
  }
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
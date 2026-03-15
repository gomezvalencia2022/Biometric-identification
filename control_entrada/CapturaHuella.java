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
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Cursor;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
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
/*      */ public class CapturaHuella extends javax.swing.JFrame {
/*   68 */   Object EncontroHuella = null;
/*   69 */   String fechaActual = "";
/*      */   private String cachedEmployeeId  = null;
/*      */   private String cachedEmployerId  = null;
/*      */   private String cachedTurnoId     = null;
/*      */   private String cachedKioskoId       = null; // ID del kiosko asignado a este dispositivo
/*      */   private String cachedKioskoNombre  = null; // Nombre del kiosko para mostrar en UI
/*      */   private String cachedKioskoUbicacion = null; // Ubicacion (coords o direccion) para guardar en BD
/*      */   private boolean cachedDispositivoRegistrado = false; // true si la MAC esta registrada en dispositivos_biometricos
/*      */   private javax.swing.JTextField jTUbicacion;
/*      */   private javax.swing.JTextField jTObservaciones;
/*      */   private javax.swing.JLabel     jLblTurno;
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
  // ══════════════════════════════════════════════════════════════════
  // GAP COLOR PALETTE — modifica aquí para cambiar toda la paleta UI
  // ══════════════════════════════════════════════════════════════════
  private static final Color C_HEADER_BG     = new Color(64,  121, 150); // --primary-hover  hsl(200,40%,42%)
  private static final Color C_ACCENT        = new Color(80,  137, 165); // --primary        hsl(200,35%,48%)
  private static final Color C_DANGER        = new Color(220, 40,  40);  // --destructive    hsl(0,72%,51%)
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
    // Unused but declared field references
    this.jPanel2 = new JPanel();
    this.jPanel3 = new JPanel();
    this.jPanel4 = new JPanel();
    this.panBtns = new JPanel();

    // Aliases de color — valores definidos en las constantes de clase arriba
    Color darkBlue  = C_HEADER_BG;
    Color accentBlue= C_ACCENT;
    Color redBtn    = C_DANGER;
    Color bgGray    = C_BG;
    Color cardBg    = C_CARD;
    Color borderClr = C_BORDER;
    Color inputBg   = C_INPUT_BG;
    Color mutedText = C_MUTED_TEXT;

    // Window
    setDefaultCloseOperation(2);
    setTitle("GAP Sistema \u2014 Control de Marcaci\u00f3n");
    setResizable(true);
    setMinimumSize(new Dimension(540, 560));
    addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent evt) { CapturaHuella.this.formWindowClosing(evt); }
      public void windowOpened(WindowEvent evt)  { CapturaHuella.this.formWindowOpened(evt);  }
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

    JLabel lblTitle = new JLabel("Control de Marcaci\u00f3n");
    lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
    lblTitle.setForeground(Color.WHITE);
    JLabel lblSubTitle = new JLabel("Sistema GAP  \u00b7  Biometr\u00eda Dactilar");
    lblSubTitle.setFont(new Font("Segoe UI", Font.PLAIN, 11));
    lblSubTitle.setForeground(mutedText);
    JPanel headerText = new JPanel(new GridLayout(2, 1, 0, 2));
    headerText.setOpaque(false);
    headerText.add(lblTitle);
    headerText.add(lblSubTitle);
    headerPanel.add(headerText, BorderLayout.CENTER);
    getContentPane().add(headerPanel, BorderLayout.NORTH);

    // ══════════════════ CONTENT (GridBagLayout con pesos) ══════════════════
    JPanel contentPanel = new JPanel(new GridBagLayout());
    contentPanel.setBackground(bgGray);
    contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 14, 10, 14));
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridx   = 0;
    gbc.weightx = 1.0;
    gbc.fill    = GridBagConstraints.HORIZONTAL;

    // (txtArea kept initialized but not shown — used internally)
    this.txtArea = new javax.swing.JTextPane();
    this.txtArea.setContentType("text/html");
    this.txtArea.setEditable(false);

    // ─── VISTA PREVIA HUELLA (crece con la ventana) ──────────────
    this.panHuellas = new JPanel(new BorderLayout(0, 8));
    this.panHuellas.setBackground(cardBg);
    this.panHuellas.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(borderClr, 1),
        BorderFactory.createEmptyBorder(12, 14, 12, 14)));
    // Header row: título + badge de conexión
    JPanel fpHeader = new JPanel(new BorderLayout(8, 0));
    fpHeader.setOpaque(false);
    JLabel fpTitle = new JLabel("Coloque su dedo en el lector");
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
    this.jPanel1.setMinimumSize(new Dimension(460, 360));
    this.jPanel1.setPreferredSize(new Dimension(460, 480));
    this.lblImagenHuella = new JLabel("Esperando huella...", JLabel.CENTER);
    this.lblImagenHuella.setFont(new Font("Segoe UI", Font.ITALIC, 14));
    this.lblImagenHuella.setForeground(mutedText);
    this.jPanel1.add(this.lblImagenHuella, BorderLayout.CENTER);
    this.panHuellas.add(this.jPanel1, BorderLayout.CENTER);
    // weighty=1 => este panel se lleva el espacio sobrante
    gbc.gridy = 0; gbc.weighty = 1.0; gbc.fill = GridBagConstraints.BOTH; gbc.insets = new Insets(0, 0, 8, 0);
    contentPanel.add(this.panHuellas, gbc);

    // ─── DATOS IDENTIFICADOS ──────────────────────
    JPanel infoCard = new JPanel(new GridBagLayout());
    infoCard.setBackground(cardBg);
    infoCard.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(borderClr, 1),
        BorderFactory.createEmptyBorder(10, 14, 10, 14)));
    GridBagConstraints ic = new GridBagConstraints();
    ic.fill   = GridBagConstraints.HORIZONTAL;
    ic.anchor = GridBagConstraints.WEST;

    ic.gridx = 0; ic.gridy = 0; ic.gridwidth = 4; ic.weightx = 1.0; ic.insets = new Insets(0, 0, 6, 0);
    JLabel infoTitle = new JLabel("Trabajador identificado");
    infoTitle.setFont(new Font("Segoe UI", Font.BOLD, 12));
    infoTitle.setForeground(darkBlue);
    infoCard.add(infoTitle, ic);

    ic.gridy = 1; ic.gridwidth = 1; ic.weightx = 0; ic.fill = GridBagConstraints.NONE; ic.insets = new Insets(3, 0, 3, 10);
    this.jLabel5 = new JLabel("C\u00e9dula:");
    this.jLabel5.setFont(new Font("Segoe UI", Font.PLAIN, 12));
    infoCard.add(this.jLabel5, ic);
    ic.gridx = 1; ic.gridwidth = 3; ic.fill = GridBagConstraints.HORIZONTAL; ic.weightx = 1.0; ic.insets = new Insets(3, 0, 3, 0);
    this.jTcedula = new JTextField();
    this.jTcedula.setFont(new Font("Segoe UI", Font.PLAIN, 12));
    this.jTcedula.setEditable(false);
    this.jTcedula.setBackground(inputBg);
    this.jTcedula.setOpaque(false);
    this.jTcedula.setBorder(new RoundedInputBorder(borderClr, 10));
    infoCard.add(this.jTcedula, ic);

    ic.gridy = 2; ic.gridx = 0; ic.gridwidth = 1; ic.fill = GridBagConstraints.NONE; ic.weightx = 0; ic.insets = new Insets(3, 0, 3, 10);
    this.jLabel2 = new JLabel("Nombres:");
    this.jLabel2.setFont(new Font("Segoe UI", Font.PLAIN, 12));
    infoCard.add(this.jLabel2, ic);
    ic.gridx = 1; ic.gridwidth = 3; ic.fill = GridBagConstraints.HORIZONTAL; ic.weightx = 1.0; ic.insets = new Insets(3, 0, 3, 0);
    this.jTNombres = new JTextField();
    this.jTNombres.setFont(new Font("Segoe UI", Font.PLAIN, 12));
    this.jTNombres.setEditable(false);
    this.jTNombres.setBackground(inputBg);
    this.jTNombres.setOpaque(false);
    this.jTNombres.setBorder(new RoundedInputBorder(borderClr, 10));
    this.jTNombres.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) { CapturaHuella.this.jTNombresActionPerformed(evt); }
    });
    infoCard.add(this.jTNombres, ic);

    ic.gridy = 3; ic.gridx = 0; ic.gridwidth = 1; ic.fill = GridBagConstraints.NONE; ic.weightx = 0; ic.insets = new Insets(3, 0, 3, 10);
    this.jLabel3 = new JLabel("Apellidos:");
    this.jLabel3.setFont(new Font("Segoe UI", Font.PLAIN, 12));
    infoCard.add(this.jLabel3, ic);
    ic.gridx = 1; ic.gridwidth = 3; ic.fill = GridBagConstraints.HORIZONTAL; ic.weightx = 1.0; ic.insets = new Insets(3, 0, 3, 0);
    this.jTApellidos = new JTextField();
    this.jTApellidos.setFont(new Font("Segoe UI", Font.PLAIN, 12));
    this.jTApellidos.setEditable(false);
    this.jTApellidos.setBackground(inputBg);
    this.jTApellidos.setOpaque(false);
    this.jTApellidos.setBorder(new RoundedInputBorder(borderClr, 10));
    infoCard.add(this.jTApellidos, ic);

    gbc.gridy = 1; gbc.weighty = 0; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.insets = new Insets(0, 0, 8, 0);
    contentPanel.add(infoCard, gbc);

    // ─── BOTONES ──────────────────────────────────
    JPanel btnCard = new JPanel(new GridBagLayout());
    btnCard.setBackground(cardBg);
    btnCard.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(borderClr, 1),
        BorderFactory.createEmptyBorder(12, 14, 12, 14)));
    GridBagConstraints bc = new GridBagConstraints();
    bc.fill    = GridBagConstraints.HORIZONTAL;
    bc.weightx = 1.0;
    bc.gridy   = 0;

    bc.gridx = 0; bc.insets = new Insets(0, 0, 0, 8);
    this.btnIdentificar = new JButton("Registrar / Editar Huella");
    applyBtnStyle(this.btnIdentificar, accentBlue, C_DARK_TEXT);
    this.btnIdentificar.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) { CapturaHuella.this.btnIdentificarActionPerformed(evt); }
    });
    btnCard.add(this.btnIdentificar, bc);

    bc.gridx = 1; bc.insets = new Insets(0, 8, 0, 0); bc.weightx = 0.35;
    this.btnSalir = new JButton("Salir");
    applyBtnStyle(this.btnSalir, redBtn, Color.WHITE);
    this.btnSalir.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) { CapturaHuella.this.btnSalirActionPerformed(evt); }
    });
    btnCard.add(this.btnSalir, bc);

    gbc.gridy = 2; gbc.weighty = 0; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.insets = new Insets(0, 0, 0, 0);
    contentPanel.add(btnCard, gbc);

    getContentPane().add(contentPanel, BorderLayout.CENTER);
    setSize(new Dimension(620, 720));
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
/*      */       UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
/*      */     } catch (Exception e) {
/*      */       JOptionPane.showMessageDialog(null, "Imposible modificar el tema visual", "Lookandfeel inválido.", 0);
/*      */     }
/*      */     initComponents();
/*      */     cargarYConfigurarImagen();
/*      */     this.btnIdentificar.setEnabled(true);
/*      */     this.txtArea.setEditable(false);
/*      */     cargarDispositivoBiometrico();
/*      */     buscarPorCedula(); } static DPFPEnrollment Reclutador2 = DPFPGlobal.getEnrollmentFactory().createEnrollment(); private DPFPVerification Verificador; private DPFPTemplate template; public static String TEMPLATE_PROPERTY = "template"; public DPFPFeatureSet featuresinscripcion; public DPFPFeatureSet featuresverificacion; ConexionBD con; private JButton btnIdentificar; private JButton btnSalir; private JLabel jLabel1; private JLabel jLabel2; private JLabel jLabel3; private JLabel jLabel5; private JPanel jPanel1; private JPanel jPanel2; private JPanel jPanel3; private JPanel jPanel4; private JScrollPane jScrollPane1; private JTextField jTApellidos; public void guardarHuella() { guardarHuellaComoBase64(); } private JTextField jTNombres; private JTextField jTcedula; private JLabel lblImagenHuella; private JPanel panBtns; private JPanel panHuellas; private javax.swing.JTextPane txtArea; private JLabel lblSensorStatus; private Image imagenOriginal; protected void Iniciar() { this.Lector.addDataListener((DPFPDataListener)new DPFPDataAdapter() { public void dataAcquired(final DPFPDataEvent e) { SwingUtilities.invokeLater(new Runnable() { public void run() { CapturaHuella.this.EnviarTexto("La Huella Digital ha sido Capturada"); CapturaHuella.this.ProcesarCaptura(e.getSample()); } }
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
/*  868 */       String sql = "SELECT id, employer_id, numero_documento, nombres, apellidos, huella_dactilar FROM employees WHERE huella_dactilar IS NOT NULL";
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
/*      */           this.cachedEmployeeId = rs.getString("id");
/*      */           this.cachedEmployerId = rs.getString("employer_id");
/*      */           // Buscar turno activo para hoy
/*      */           this.cachedTurnoId = null;
/*      */           try (java.sql.PreparedStatement turnoStmt = c.prepareStatement(
/*      */               "SELECT id, hora_inicio, hora_fin FROM turnos " +
/*      */               "WHERE employee_id = ?::uuid AND employer_id = ?::uuid " +
/*      */               "AND fecha_inicio <= CURRENT_DATE AND fecha_fin >= CURRENT_DATE " +
/*      */               "ORDER BY fecha_fin DESC LIMIT 1")) {
/*      */             turnoStmt.setString(1, this.cachedEmployeeId);
/*      */             turnoStmt.setString(2, this.cachedEmployerId);
/*      */             java.sql.ResultSet turnoRs = turnoStmt.executeQuery();
/*      */             if (turnoRs.next()) {
/*      */               this.cachedTurnoId = turnoRs.getString("id");
/*      */               String hIni = turnoRs.getString("hora_inicio");
/*      */               String hFin = turnoRs.getString("hora_fin");
/*      */               if (hIni != null && hFin != null) {
/*      */                 final String turnoText = hIni.substring(0, 5) + " \u2013 " + hFin.substring(0, 5);
/*      */                 SwingUtilities.invokeLater(new Runnable() { public void run() {
/*      */                   if (CapturaHuella.this.jLblTurno != null) {
/*      */                     CapturaHuella.this.jLblTurno.setText(turnoText);
/*      */                     CapturaHuella.this.jLblTurno.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 12));
/*      */                     CapturaHuella.this.jLblTurno.setForeground(C_DARK_TEXT);
/*      */                   }
/*      */                 } });
/*      */               }
/*      */             } else {
/*      */               SwingUtilities.invokeLater(new Runnable() { public void run() {
/*      */                 if (CapturaHuella.this.jLblTurno != null) {
/*      */                   CapturaHuella.this.jLblTurno.setText("Sin turno asignado");
/*      */                   CapturaHuella.this.jLblTurno.setFont(new java.awt.Font("Segoe UI", java.awt.Font.ITALIC, 12));
/*      */                   CapturaHuella.this.jLblTurno.setForeground(C_MUTED_TEXT);
/*      */                 }
/*      */               } });
/*      */             }
/*      */             turnoRs.close();
/*      */           } catch (Exception ex) { System.err.println("Error al buscar turno: " + ex.getMessage()); }
/*      */           EnviarTexto("\u2713 IDENTIFICADO: " + nombreCompleto);
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

/*      */   /** Extrae el valor de cadena de una clave en un JSON simple (sin libreria externa). */
/*      */   private static String jsonStr(String json, String key, String def) {
/*      */     if (json == null) return def;
/*      */     java.util.regex.Matcher m = java.util.regex.Pattern
/*      */       .compile("\"" + key + "\"\\s*:\\s*\"((?:[^\"\\\\]|\\\\.)*)\"")
/*      */       .matcher(json);
/*      */     return m.find() ? m.group(1) : def;
/*      */   }
/*      */   /** Extrae el valor booleano de una clave en un JSON simple. */
/*      */   private static boolean jsonBool(String json, String key, boolean def) {
/*      */     if (json == null) return def;
/*      */     java.util.regex.Matcher m = java.util.regex.Pattern
/*      */       .compile("\"" + key + "\"\\s*:\\s*(true|false)")
/*      */       .matcher(json);
/*      */     return m.find() ? "true".equals(m.group(1)) : def;
/*      */   }

/*      */   private void cargarDispositivoBiometrico() {
/*      */     final String[] macHolder = { null };
/*      */     try {
/*      */       // Leer MAC address del primer adaptador de red activo
/*      */       String macDetectada = null;
/*      */       java.util.Enumeration<java.net.NetworkInterface> interfaces = java.net.NetworkInterface.getNetworkInterfaces();
/*      */       while (interfaces.hasMoreElements()) {
/*      */         java.net.NetworkInterface ni = interfaces.nextElement();
/*      */         if (ni.isLoopback() || !ni.isUp()) continue;
/*      */         byte[] mac = ni.getHardwareAddress();
/*      */         if (mac == null || mac.length == 0) continue;
/*      */         StringBuilder sb = new StringBuilder();
/*      */         for (int i = 0; i < mac.length; i++) {
/*      */           sb.append(String.format("%02X", mac[i]));
/*      */           if (i < mac.length - 1) sb.append(":");
/*      */         }
/*      */         macDetectada = sb.toString();
/*      */         break;
/*      */       }
/*      */       if (macDetectada == null) {
/*      */         System.out.println("[Biometrico] No se pudo detectar la MAC address.");
/*      */         return;
/*      */       }
/*      */       macHolder[0] = macDetectada;
/*      */       final String macFinal = macDetectada;
/*      */       System.out.println("[Biometrico] MAC detectada: " + macFinal);
/*      */       // Consultar BD
/*      */       ConexionBD bdConf = new ConexionBD();
/*      */       try (java.sql.Connection cn = bdConf.conectar();
/*      */            java.sql.PreparedStatement ps = cn.prepareStatement(
/*      */              "SELECT d.id, d.nombre, d.kiosko_id, k.nombre AS kiosko_nombre, " +
/*      */              "k.direccion AS kiosko_direccion, k.latitud, k.longitud " +
/*      */              "FROM dispositivos_biometricos d " +
/*      */              "LEFT JOIN kioskos k ON k.id = d.kiosko_id " +
/*      */              "WHERE UPPER(d.mac_address) = UPPER(?) AND d.estado = 'activo' LIMIT 1")) {
/*      */         ps.setString(1, macFinal);
/*      */         java.sql.ResultSet rs = ps.executeQuery();
/*      */         if (rs.next()) {
/*      */           this.cachedDispositivoRegistrado = true;
/*      */           this.cachedKioskoId = rs.getString("kiosko_id");
/*      */           String kDir = rs.getString("kiosko_direccion");
/*      */           String kNom = rs.getString("kiosko_nombre");
/*      */           String kLat = rs.getString("latitud");
/*      */           String kLon = rs.getString("longitud");
/*      */           // UI: mostrar siempre el nombre de la sede
/*      */           this.cachedKioskoNombre = kNom;
/*      */           // BD: guardar direccion o coordenadas como ubicacion
/*      */           if (kDir != null && !kDir.trim().isEmpty()) {
/*      */             this.cachedKioskoUbicacion = kDir.trim();
/*      */           } else if (kLat != null && kLon != null) {
/*      */             this.cachedKioskoUbicacion = kLat + ", " + kLon;
/*      */           } else {
/*      */             this.cachedKioskoUbicacion = kNom;
/*      */           }
/*      */           final String dispNombre = rs.getString("nombre");
/*      */           final String sedeNombre = this.cachedKioskoNombre != null ? this.cachedKioskoNombre : "Sin sede asignada";
/*      */           final boolean sinSede = (this.cachedKioskoId == null || this.cachedKioskoId.isEmpty());
/*      */           System.out.println("[Biometrico] Dispositivo: " + dispNombre + " | Sede: " + sedeNombre);
/*      */           SwingUtilities.invokeLater(new Runnable() { public void run() {
/*      */             if (sinSede) {
/*      */               setTitle("GAP \u2014 " + dispNombre + "  |  \u26A0 SIN SEDE ASIGNADA");
/*      */               EnviarTexto("ADVERTENCIA: El dispositivo '" + dispNombre + "' no tiene sede asignada. Las marcaciones seran bloqueadas.");
/*      */             } else {
/*      */               setTitle("GAP \u2014 " + dispNombre + "  |  Sede: " + sedeNombre);
/*      */               EnviarTexto("Dispositivo: " + dispNombre + "  |  Sede: " + sedeNombre);
/*      */             }
/*      */           } });
/*      */         } else {
/*      */           System.out.println("[Biometrico] Dispositivo no registrado para MAC: " + macFinal);
/*      */           SwingUtilities.invokeLater(new Runnable() { public void run() {
/*      */             setTitle("GAP \u2014 Control de Marcacion  |  MAC: " + macFinal + "  [Sin registrar]");
/*      */             EnviarTexto("AVISO: Dispositivo no registrado. MAC: " + macFinal);
/*      */             EnviarTexto("Registre este dispositivo en el sistema web con la MAC: " + macFinal);
/*      */           } });
/*      */         }
/*      */         rs.close();
/*      */       }
/*      */     } catch (Exception ex) {
/*      */       System.err.println("[Biometrico] Error al cargar dispositivo: " + ex.getMessage());
/*      */       final String macErr = macHolder[0] != null ? macHolder[0] : "desconocida";
/*      */       SwingUtilities.invokeLater(new Runnable() { public void run() {
/*      */         EnviarTexto("Error al consultar dispositivo. MAC local: " + macErr);
/*      */       } });
/*      */     }
/*      */   }
  public void DibujarHuella(Image image) { this.lblImagenHuella.setIcon(new ImageIcon(image.getScaledInstance(this.lblImagenHuella.getWidth(), this.lblImagenHuella.getHeight(), 1))); repaint(); try { identificarHuella(); try { cambiodia(); } catch (SQLException ex) { Logger.getLogger(CapturaHuella.class.getName()).log(Level.SEVERE, (String)null, ex); }  marcaentrada(); this.Reclutador.clear(); } catch (IOException ex) { Logger.getLogger(CapturaHuella.class.getName()).log(Level.SEVERE, (String)null, ex); }  }
/*      */   public void EstadoHuellas() { EnviarTexto("Muestra de Huellas Necesarias para Guardar Template " + this.Reclutador.getFeaturesNeeded()); EnviarTexto("LOS ULTIMOS DATOS INGRESADOS SON:  \n" + this.jTNombres.getText() + " " + this.jTApellidos.getText()); }
/*      */   private void appendHtmlLog(String html) {
    try {
      javax.swing.text.html.HTMLDocument doc = (javax.swing.text.html.HTMLDocument) this.txtArea.getDocument();
      javax.swing.text.html.HTMLEditorKit kit = (javax.swing.text.html.HTMLEditorKit) this.txtArea.getEditorKit();
      kit.insertHTML(doc, doc.getLength(), html, 0, 0, null);
    } catch (Exception ex) {}
    this.txtArea.setCaretPosition(this.txtArea.getDocument().getLength());
  }
  public void EnviarTexto(String string) {
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
/*      */   private void mostrarAviso(String titulo, String emoji, String linea1, String linea2, String hora, java.awt.Color color) {
/*      */     String hex = String.format("%06X", color.getRGB() & 0xFFFFFF);
/*      */     String html = "<html><div style='font-family:Arial;text-align:center;padding:10px'>"
/*      */       + "<div style='font-size:36px'>" + emoji + "</div>"
/*      */       + "<div style='font-size:18px;font-weight:bold;color:#" + hex + ";margin-top:8px'>" + linea1 + "</div>"
/*      */       + "<div style='font-size:14px;color:#555;margin-top:4px'>" + linea2 + "</div>"
/*      */       + "<div style='font-size:22px;font-weight:bold;color:#222;margin-top:10px;border-top:2px solid #ddd;padding-top:8px'>" + hora + "</div>"
/*      */       + "</div></html>";
/*      */     javax.swing.JLabel label = new javax.swing.JLabel(html);
/*      */     JOptionPane pane = new JOptionPane(label, JOptionPane.PLAIN_MESSAGE);
/*      */     final JDialog dlg = pane.createDialog(titulo);
/*      */     dlg.setModal(true); dlg.setVisible(true);
/*      */   }
/*      */
/*      */   private void marcaentrada() {
/*      */     if (this.cachedEmployeeId == null) { EnviarTexto("Sin empleado identificado."); return; }
/*      */     SimpleDateFormat horaDisplay = new SimpleDateFormat("hh:mm a");
/*      */     String horaLegible = horaDisplay.format(new Date());
/*      */     String employeeId = this.cachedEmployeeId;
/*      */     String kioskoId   = this.cachedKioskoId;
/*      */     // Bloquear marcacion si el dispositivo no esta registrado en dispositivos_biometricos
/*      */     if (!this.cachedDispositivoRegistrado) {
/*      */       this.cachedEmployeeId = null; this.cachedEmployerId = null; this.cachedTurnoId = null;
/*      */       JOptionPane.showMessageDialog(this,
/*      */         "Este equipo no esta registrado como dispositivo biometrico autorizado.\nNo es posible registrar marcaciones.\n\nContacte al administrador para registrar este dispositivo en el sistema.",
/*      */         "Dispositivo no autorizado", JOptionPane.ERROR_MESSAGE);
/*      */       return;
/*      */     }
/*      */     // Bloquear marcacion si el dispositivo no tiene sede asignada
/*      */     if (kioskoId == null || kioskoId.isEmpty()) {
/*      */       this.cachedEmployeeId = null; this.cachedEmployerId = null; this.cachedTurnoId = null;
/*      */       JOptionPane.showMessageDialog(this,
/*      */         "Este dispositivo no tiene una sede asignada.\nNo es posible registrar la marcacion.\n\nContacte al administrador para asignar una sede a este lector.",
/*      */         "Sede no asignada", JOptionPane.ERROR_MESSAGE);
/*      */       return;
/*      */     }
/*      */     String observaciones = (this.jTObservaciones != null) ? this.jTObservaciones.getText().trim() : "";
/*      */     this.cachedEmployeeId = null; this.cachedEmployerId = null; this.cachedTurnoId = null;
/*      */     try {
/*      */       ConexionBD cc = new ConexionBD();
/*      */       try (Connection cn = cc.conectar();
/*      */            PreparedStatement ps = cn.prepareStatement(
/*      */              "SELECT registrar_marcacion_biometrico(?::uuid, ?::uuid, ?)")) {
/*      */         ps.setString(1, employeeId);
/*      */         ps.setString(2, kioskoId);
/*      */         ps.setString(3, observaciones);
/*      */         ResultSet rs = ps.executeQuery();
/*      */         if (!rs.next()) {
/*      */           JOptionPane.showMessageDialog(null, "Sin respuesta del servidor al registrar marcacion.");
/*      */           return;
/*      */         }
/*      */         org.postgresql.util.PGobject pgObj = (org.postgresql.util.PGobject) rs.getObject(1);
/*      */         String jsonStr = pgObj.getValue();
/*      */         rs.close();
/*      */         if (!jsonBool(jsonStr, "success", false)) {
/*      */           String errorMsg = jsonStr(jsonStr, "error", "Error desconocido");
/*      */           String code     = jsonStr(jsonStr, "code", "");
/*      */           if ("JORNADA_COMPLETA".equals(code)) {
/*      */             mostrarAviso("Jornada completa", "\uD83C\uDFC1", "Jornada ya registrada",
/*      */               "Tu jornada de hoy ya está completa.", horaLegible, new java.awt.Color(0, 80, 160));
/*      */           } else if ("ENTRADA_PENDIENTE".equals(code)) {
/*      */             mostrarAviso("Jornada en curso", "\uD83D\uDED1", "Ya tienes entrada registrada",
/*      */               "Debes marcar salida primero.", horaLegible, new java.awt.Color(150, 0, 0));
/*      */           } else {
/*      */             JOptionPane.showMessageDialog(null, errorMsg, "No se pudo registrar", JOptionPane.WARNING_MESSAGE);
/*      */           }
/*      */           EnviarTexto("Error marcacion: " + errorMsg);
/*      */           return;
/*      */         }
/*      */         String tipo   = jsonStr(jsonStr, "tipo_marcacion", "");
/*      */         String estado = jsonStr(jsonStr, "estado_aprobacion", "APROBADA");
/*      */         String obs    = jsonStr(jsonStr, "observaciones", "");
/*      */         boolean porAprobar = "POR_APROBAR".equals(estado);
/*      */         switch (tipo) {
/*      */           case "entrada":
/*      */             mostrarAviso("Entrada registrada", "\uD83D\uDC4B", "\u00A1Bienvenido/a!",
/*      */               (porAprobar ? "\u26A0 Pendiente aprobacion  \u2022  " : "Entrada registrada  \u2022  ") + obs,
/*      */               horaLegible, porAprobar ? new java.awt.Color(180, 100, 0) : new java.awt.Color(0, 130, 80));
/*      */             EnviarTexto((porAprobar ? "[Por aprobar] " : "") + "Entrada registrada");
/*      */             break;
/*      */           case "descanso_inicio":
/*      */             mostrarAviso("Inicio de descanso", "\u2615", "Inicio de descanso registrado",
/*      */               "\u00A1Disfruta tu descanso!", horaLegible, new java.awt.Color(180, 100, 0));
/*      */             EnviarTexto("Inicio descanso registrado");
/*      */             break;
/*      */           case "descanso_fin":
/*      */             mostrarAviso("Fin de descanso", "\u2705", "Fin de descanso registrado",
/*      */               "\u00A1De vuelta al trabajo!", horaLegible, new java.awt.Color(30, 130, 30));
/*      */             EnviarTexto("Fin descanso registrado");
/*      */             break;
/*      */           case "salida":
/*      */             mostrarAviso("Salida registrada", "\uD83D\uDC4B", "Salida registrada",
/*      */               (porAprobar ? "\u26A0 Pendiente aprobacion  \u2022  " : "\u00A1Hasta ma\u00F1ana!  \u2022  ") + obs,
/*      */               horaLegible, porAprobar ? new java.awt.Color(180, 100, 0) : new java.awt.Color(0, 80, 160));
/*      */             EnviarTexto((porAprobar ? "[Por aprobar] " : "") + "Salida registrada");
/*      */             break;
/*      */           default:
/*      */             EnviarTexto("Marcacion registrada: " + tipo);
/*      */         }
/*      */       }
/*      */       // Limpiar observaciones y turno despues de marcar
/*      */       SwingUtilities.invokeLater(new Runnable() { public void run() {
/*      */         if (CapturaHuella.this.jTObservaciones != null) CapturaHuella.this.jTObservaciones.setText("");
/*      */         if (CapturaHuella.this.jLblTurno != null) {
/*      */           CapturaHuella.this.jLblTurno.setText("Sin turno asignado");
/*      */           CapturaHuella.this.jLblTurno.setFont(new java.awt.Font("Segoe UI", java.awt.Font.ITALIC, 12));
/*      */           CapturaHuella.this.jLblTurno.setForeground(C_MUTED_TEXT);
/*      */         }
/*      */       } });
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
/* 1622 */     double ratio = Math.min((double)w / iw, (double)h / ih);
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
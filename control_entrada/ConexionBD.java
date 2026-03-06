/*    */ package control_entrada;
/*    */ 
/*    */ import java.sql.Connection;
/*    */ import java.sql.DriverManager;
/*    */ import java.sql.SQLException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ConexionBD
/*    */ {
/* 15 */   public String bd = "postgres";
/* 16 */   public String login = "postgres.bbhrkovseinvguoxeybu";
/* 17 */   public String password = "Contraseña1234";
/* 18 */   public String host = "aws-1-us-east-2.pooler.supabase.com";
/* 19 */   public String puerto = "6543";
/*    */ 
/*    */   
/* 22 */   public String url = "jdbc:postgresql://" + this.host + ":" + this.puerto + "/" + this.bd;
/*    */ 
/*    */ 
/*    */   
/*    */   public Connection conectar() {
/* 27 */     Connection conn = null;
/*    */ 
/*    */     
/*    */     try {
/* 31 */       Class.forName("org.postgresql.Driver");
/*    */ 
/*    */       
/* 34 */       conn = DriverManager.getConnection(this.url, this.login, this.password);
/*    */       
/* 36 */       if (conn != null) {
/* 37 */         System.out.println("Conexión exitosa a Supabase (PostgreSQL) ... Ok");
/*    */       }
/*    */     }
/* 40 */     catch (SQLException ex) {
/* 41 */       System.out.println("Error de SQL: " + ex.getMessage());
/* 42 */       System.out.println("Revisa si tu IP tiene acceso en el dashboard de Supabase.");
/*    */     }
/* 44 */     catch (ClassNotFoundException ex) {
/* 45 */       System.out.println("Error: No se encontró el Driver de PostgreSQL. " + ex);
/*    */     } 
/* 47 */     return conn;
/*    */   }
/*    */   
/*    */   public void desconectar() {
/* 51 */     Object conn = null;
/* 52 */     System.out.println("Desconexion a base de datos listo...");
/*    */   }
/*    */ }


/* Location:              C:\Gap-x\dist\control_entrada.jar!\control_entrada\ConexionBD.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */
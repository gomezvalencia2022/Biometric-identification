/*    */ package controlador;
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
/*    */ public class conexion
/*    */ {
/* 14 */   public String bd = "postgres";
/* 15 */   public String login = "postgres.bbhrkovseinvguoxeybu";
/* 16 */   public String password = "Contraseña1234";
/* 17 */   public String host = "aws-1-us-east-2.pooler.supabase.com";
/* 18 */   public String puerto = "6543";
/*    */ 
/*    */   
/* 21 */   public String url = "jdbc:postgresql://" + this.host + ":" + this.puerto + "/" + this.bd;
/*    */ 
/*    */   
/*    */   public Connection conectar() {
/* 25 */     Connection conn = null;
/*    */ 
/*    */     
/*    */     try {
/* 29 */       Class.forName("org.postgresql.Driver");
/*    */ 
/*    */       
/* 32 */       conn = DriverManager.getConnection(this.url, this.login, this.password);
/*    */       
/* 34 */       if (conn != null) {
/* 35 */         System.out.println("Conexión exitosa a Supabase (PostgreSQL) ... Ok");
/*    */       }
/*    */     }
/* 38 */     catch (SQLException ex) {
/* 39 */       System.out.println("Error de SQL: " + ex.getMessage());
/* 40 */       System.out.println("Revisa si tu IP tiene acceso en el dashboard de Supabase.");
/*    */     }
/* 42 */     catch (ClassNotFoundException ex) {
/* 43 */       System.out.println("Error: No se encontró el Driver de PostgreSQL. " + ex);
/*    */     } 
/* 45 */     return conn;
/*    */   }
/*    */   public void desconectar() {
/* 48 */     Object conn = null;
/* 49 */     System.out.println("Desconexion a base de datos listo...");
/*    */   }
/*    */ }


/* Location:              C:\Gap-x\dist\control_entrada.jar!\controlador\conexion.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */
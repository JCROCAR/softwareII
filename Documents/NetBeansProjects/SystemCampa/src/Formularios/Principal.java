/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Formularios;

import Clases.Database;
import static java.awt.SystemColor.window;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

//librerias para encriptar
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Calendar;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.JOptionPane;
import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author Juan Carlos Roca
 */
public class Principal extends javax.swing.JFrame {

    /**
     * Creates new form Principal
     */
    public Principal() {
        initComponents();           
        this.setLocationRelativeTo(null);
    Calendar Cal= Calendar.getInstance();
    String fecha = Cal.get(Cal.DATE)+"/"+(Cal.get(Cal.MONTH)+1)+"/"+Cal.get(Cal.YEAR)+" "+Cal.get(Cal.HOUR_OF_DAY)+":"+Cal.get(Cal.MINUTE);
    jLabel25.setText(fecha);
    }
    private void mostrar() {        
        this.setTitle("Campistas");
        DefaultTableModel modelo = new DefaultTableModel();               
        ResultSet rs = Database.getTabla("select idcampistas,nombre,apellido,direccion,telefono, staff from campistas where inhabilitado = 0");
        modelo.setColumnIdentifiers(new Object[]{"ID","Nombres", "Apellidos", "Dirección","Teléfono", "Staff"});
        try {
            while (rs.next()) {
                // añade los resultado a al modelo de tabla
                modelo.addRow(new Object[]{rs.getString("idcampistas"),rs.getString("nombre"), rs.getString("apellido"), rs.getString("direccion"),rs.getString("telefono"), rs.getString("staff")});
            }            
            // asigna el modelo a la tabla
            table.setModel(modelo);            
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    private void mostrarPagos() {
        DefaultTableModel modelo = new DefaultTableModel();               
        ResultSet rs = Database.getTabla("select idcampistas,nombre,apellido,telefono,staff from campistas WHERE inhabilitado = 0");
        modelo.setColumnIdentifiers(new Object[]{"ID","Nombres", "Apellidos", "Teléfono", "Staff"});
        try {
            while (rs.next()) {
                // añade los resultado a al modelo de tabla
                modelo.addRow(new Object[]{rs.getString("idcampistas"),rs.getString("nombre"), rs.getString("apellido"), rs.getString("telefono"), rs.getString("staff")});
            }            
            // asigna el modelo a la tabla
            tablePagos.setModel(modelo);            
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
     private void mostrarRegistros() {
        DefaultTableModel modelo = new DefaultTableModel();               
        ResultSet rs = Database.getTabla("select idcampistas,nombre,apellido,staff from campistas WHERE inhabilitado = 0");
        modelo.setColumnIdentifiers(new Object[]{"ID","Nombres", "Apellidos","Staff"});
        try {
            while (rs.next()) {
                // añade los resultado a al modelo de tabla
                modelo.addRow(new Object[]{rs.getString("idcampistas"),rs.getString("nombre"), rs.getString("apellido"),rs.getString("staff")});
            }            
            // asigna el modelo a la tabla
            tableRegistros.setModel(modelo);            
        } catch (Exception e) {
            System.out.println(e);
        }
    }
     
    private void busquedaCampista(String nombre) {
        String SQL;
        String [] columnas = {"ID","Nombres","Apellidos","Telefono","Staff"};
        String [] registro = new String[5];
        DefaultTableModel modelo = new DefaultTableModel(null, columnas);
        SQL = "SELECT idcampistas, nombre, apellido,telefono,staff"
              + " FROM campistas WHERE nombre LIKE '%"+nombre+"%';";
        ResultSet rs = Database.getTabla(SQL);
        try {
            while(rs.next()){
                
                registro[0] = rs.getString("idcampistas");
                registro[1] = rs.getString("nombre");
                registro[2] = rs.getString("apellido");
                registro[3] = rs.getString("telefono");
                registro[4] = rs.getString("staff");
                
                modelo.addRow(registro);
            }
            
            tablePagos.setModel(modelo);
            
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, e, "Error en la búsqueda", JOptionPane.ERROR_MESSAGE);
        }
       
    }
    
    private void busquedaCampistas(String nombre) {
        String SQL;
        String [] columnas = {"ID","Nombres","Apellidos","Direccion","Telefono","Staff"};
        String [] registro = new String[6];
        DefaultTableModel modelo = new DefaultTableModel(null, columnas);
        SQL = "SELECT idcampistas, nombre, apellido,direccion,telefono,staff"
              + " FROM campistas WHERE nombre LIKE '%"+nombre+"%';";
        ResultSet rs = Database.getTabla(SQL);
        try {
            while(rs.next()){
                
                registro[0] = rs.getString("idcampistas");
                registro[1] = rs.getString("nombre");
                registro[2] = rs.getString("apellido");
                registro[3] = rs.getString("direccion");
                registro[4] = rs.getString("telefono");
                registro[5] = rs.getString("staff");
                
                modelo.addRow(registro);
            }
            
            table.setModel(modelo);
            
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, e, "Error en la búsqueda", JOptionPane.ERROR_MESSAGE);
        }
       
    }
    
    private void busquedaRegistro(String nombre) {
        String SQL;
        String [] columnas = {"ID","Nombres","Apellidos","Staff"};
        String [] registro = new String[4];
        DefaultTableModel modelo = new DefaultTableModel(null, columnas);
        SQL = "SELECT idcampistas, nombre, apellido,staff"
              + " FROM campistas WHERE nombre LIKE '%"+nombre+"%';";
        ResultSet rs = Database.getTabla(SQL);
        try {
            while(rs.next()){
                
                registro[0] = rs.getString("idcampistas");
                registro[1] = rs.getString("nombre");
                registro[2] = rs.getString("apellido");
                registro[3] = rs.getString("staff");
                
                modelo.addRow(registro);
            }
            
            tableRegistros.setModel(modelo);
            
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, e, "Error en la búsqueda", JOptionPane.ERROR_MESSAGE);
        }
       
    }
    
    private void crearAbono(String abono) {
        String idpago = "";
        String id = tablePagos.getValueAt(tablePagos.getSelectedRow(), 0).toString();
        String SQQL = "SELECT idpagos FROM pagos WHERE campistas_id='" + id + "';";
        ResultSet rs = Database.getTabla(SQQL);
        try {
            while(rs.next())
                idpago = rs.getString("idpagos");
        } catch (SQLException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
        String SQL = "INSERT INTO detalle_pagos (abono,idpagos) VALUES ('"+abono+"','"+idpago+"')";
        Database.ejecutarQuery(SQL);

    }
    
    private void insertarCampista(){
        String check = null;
        if (staffCheck.isSelected())
        {
            check = "Si";
        }
        String datos= "INSERT INTO campistas (nombre,apellido,direccion,telefono,staff) VALUES('"
                +nombreCampista.getText()+"','"+apellidoCampista.getText()+"','"+direccionCampista.getText()+"','"
                +telefonoCampista.getText()+"','"+check
                +"')";
       Database.ejecutarQuery(datos);
       nombreCampista.setText("");
       apellidoCampista.setText("");
       direccionCampista.setText("");
       telefonoCampista.setText("");
       staffCheck.setSelected(false);
    }
    private String encriptar(String contra){
       String encriptacion = "";
       String secretKey = "softwareII";
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] llavePassword = md5.digest(secretKey.getBytes("utf-8"));
            byte[] BytesKey = Arrays.copyOf(llavePassword, 24);
            SecretKey key = new SecretKeySpec(BytesKey, "DESede");
            Cipher cifrado = Cipher.getInstance("DESede");
            cifrado.init(Cipher.ENCRYPT_MODE, key);
            
            byte[] plainTextBytes = contra.getBytes("utf-8");
            byte[] buf = cifrado.doFinal(plainTextBytes);
            byte[] base64Bytes = Base64.encodeBase64(buf);
            
            encriptacion = new String(base64Bytes);
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Algo salió mal");
        }
        
        return encriptacion;
    }
    private void insertarUsuario(){
      String password = encriptar(txtContraseña.getText());
        
        
        String datos= "INSERT INTO usuarios (correo,contraseña,rol) VALUES('"
                +txtCorreo.getText()+"','"+password+"','"+txtRol.getSelectedItem().toString()
                +"')";
       Database.ejecutarQuery(datos);
       txtCorreo.setText("");
       txtContraseña.setText("");
    }
    
    private void caja(){
        double cantidad = Double.parseDouble(campoCajaCant.getText());
        String descripcion = campoCajaDescrip.getText();
        String tipo = "";
        
        if (opcionDeposito.isSelected()){
            tipo = "Deposito";
        } else if (opcionRetiro.isSelected()){
            tipo = "Retiro";
            cantidad = cantidad * -1; 
        }
        
        String SQL = "INSERT INTO detalle_capital (descripcion,cantidad,tipo) VALUES('"
                +descripcion+"','"+cantidad+"','"+tipo+"')";
        Database.ejecutarQuery(SQL);
        campoCajaCant.setText("");
        campoCajaDescrip.setText("");
        opcionDeposito.setSelected(false);
        opcionRetiro.setSelected(false);
    }
    
    private void mostrarCaja(){
        DefaultTableModel modelo = new DefaultTableModel();               
        ResultSet rs = Database.getTabla("select tipo, cantidad, descripcion, fecha FROM detalle_capital");
        modelo.setColumnIdentifiers(new Object[]{"Tipo", "Cantidad","Descripcion", "Fecha"});
        try {
            while (rs.next()) {
                // añade los resultado a al modelo de tabla
                modelo.addRow(new Object[]{rs.getString("tipo"),rs.getString("cantidad"), rs.getString("descripcion"), rs.getString("fecha")});
            }            
            // asigna el modelo a la tabla
            cajaTable.setModel(modelo);            
        } catch (Exception e) {
            System.out.println(e);
        }    
    }
    
    private void sumaCapital(){
        String SQL = "SELECT SUM(cantidad) AS suma FROM detalle_capital";
        double total = 0;
        ResultSet rs = Database.getTabla(SQL);
        try {
            while (rs.next()){
                total = rs.getDouble("suma");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        campoCapital.setText(String.valueOf(total));
    }
    
    private void busquedaCaja(String nombre){
        String SQL;
        String [] columnas = {"Tipo","Cantidad","Descripción","Fecha"};
        String [] registro = new String[4];
        DefaultTableModel modelo = new DefaultTableModel(null, columnas);
        SQL = "SELECT tipo,cantidad,descripcion,fecha"
              + " FROM detalle_capital WHERE descripcion LIKE '%"+nombre+"%';";
        ResultSet rs = Database.getTabla(SQL);
        try {
            while(rs.next()){
                
                registro[0] = rs.getString("tipo");
                registro[1] = rs.getString("cantidad");
                registro[2] = rs.getString("descripcion");
                registro[3] = rs.getString("fecha");
                
                modelo.addRow(registro);
            }
            
            cajaTable.setModel(modelo);
            
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, e, "Error en la búsqueda", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelCampistas = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        jLabel14 = new javax.swing.JLabel();
        busquedaCampo1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel26 = new javax.swing.JLabel();
        panelNuevoCampista = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel27 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        staffCheck = new javax.swing.JCheckBox();
        telefonoCampista = new javax.swing.JTextField();
        nombreCampista = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        apellidoCampista = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        direccionCampista = new javax.swing.JTextField();
        jButton10 = new javax.swing.JButton();
        panelAbonos = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablePagos = new javax.swing.JTable();
        busquedaCampo = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        deudaCampo = new javax.swing.JTextField();
        abonoCampo = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jLabel28 = new javax.swing.JLabel();
        panelRegistros = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tableRegistros = new javax.swing.JTable();
        jScrollPane4 = new javax.swing.JScrollPane();
        tableRegistrosCampista = new javax.swing.JTable();
        jLabel13 = new javax.swing.JLabel();
        busquedaCampoRegistros = new javax.swing.JTextField();
        jButton7 = new javax.swing.JButton();
        jLabel29 = new javax.swing.JLabel();
        panelCaja = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        campoCajaCant = new javax.swing.JTextField();
        campoCajaDescrip = new javax.swing.JTextField();
        jButton8 = new javax.swing.JButton();
        opcionDeposito = new javax.swing.JRadioButton();
        opcionRetiro = new javax.swing.JRadioButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        cajaTable = new javax.swing.JTable();
        jLabel20 = new javax.swing.JLabel();
        campoCapital = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        campoBusquedaCaja = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        panelUsuarios = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        txtCorreo = new javax.swing.JTextField();
        txtContraseña = new javax.swing.JTextField();
        txtRol = new javax.swing.JComboBox();
        jButton9 = new javax.swing.JButton();
        jLabel31 = new javax.swing.JLabel();
        panelMenu = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();
        jMenu3 = new javax.swing.JMenu();
        jMenu4 = new javax.swing.JMenu();

        panelCampistas.setPreferredSize(new java.awt.Dimension(666, 379));

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Nombre", "Apellido", "Dirección", "Telefono"
            }
        ));
        table.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tableKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(table);

        jLabel14.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        jLabel14.setText("Buscar:");

        busquedaCampo1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                busquedaCampo1KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                busquedaCampo1KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                busquedaCampo1KeyTyped(evt);
            }
        });

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/boton-agregar.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/cerrar.png"))); // NOI18N

        jLabel26.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Logo.png"))); // NOI18N

        javax.swing.GroupLayout panelCampistasLayout = new javax.swing.GroupLayout(panelCampistas);
        panelCampistas.setLayout(panelCampistasLayout);
        panelCampistasLayout.setHorizontalGroup(
            panelCampistasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCampistasLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelCampistasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panelCampistasLayout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addGap(18, 18, 18)
                        .addComponent(busquedaCampo1, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel26))
                    .addGroup(panelCampistasLayout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 496, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(panelCampistasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jButton1)
                            .addComponent(jButton3))))
                .addContainerGap(92, Short.MAX_VALUE))
        );
        panelCampistasLayout.setVerticalGroup(
            panelCampistasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCampistasLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelCampistasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel26)
                    .addGroup(panelCampistasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel14)
                        .addComponent(busquedaCampo1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(panelCampistasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelCampistasLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelCampistasLayout.createSequentialGroup()
                        .addGap(56, 56, 56)
                        .addComponent(jButton1)
                        .addGap(18, 18, 18)
                        .addComponent(jButton3)))
                .addContainerGap(119, Short.MAX_VALUE))
        );

        panelNuevoCampista.setPreferredSize(new java.awt.Dimension(666, 379));
        panelNuevoCampista.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel27.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Logo.png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel27)
                .addContainerGap(555, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel27)
                .addGap(0, 292, Short.MAX_VALUE))
        );

        panelNuevoCampista.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(385, 6, -1, -1));

        jLabel5.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        jLabel5.setText("Teléfono:");
        panelNuevoCampista.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 134, -1, -1));

        jLabel6.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        jLabel6.setText("Staff:");
        panelNuevoCampista.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(38, 180, -1, -1));
        panelNuevoCampista.add(staffCheck, new org.netbeans.lib.awtextra.AbsoluteConstraints(107, 174, -1, -1));
        panelNuevoCampista.add(telefonoCampista, new org.netbeans.lib.awtextra.AbsoluteConstraints(109, 132, 270, -1));
        panelNuevoCampista.add(nombreCampista, new org.netbeans.lib.awtextra.AbsoluteConstraints(109, 6, 270, -1));

        jLabel2.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        jLabel2.setText("Nombre:");
        panelNuevoCampista.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 8, -1, -1));

        jLabel3.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        jLabel3.setText("Apellido:");
        panelNuevoCampista.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 50, -1, -1));
        panelNuevoCampista.add(apellidoCampista, new org.netbeans.lib.awtextra.AbsoluteConstraints(109, 48, 270, -1));

        jLabel4.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        jLabel4.setText("Dirección:");
        panelNuevoCampista.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 92, -1, -1));
        panelNuevoCampista.add(direccionCampista, new org.netbeans.lib.awtextra.AbsoluteConstraints(109, 90, 270, -1));

        jButton10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/salvar.png"))); // NOI18N
        panelNuevoCampista.add(jButton10, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 170, -1, -1));

        panelAbonos.setPreferredSize(new java.awt.Dimension(666, 379));

        tablePagos.setModel(new javax.swing.table.DefaultTableModel(
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
        tablePagos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tablePagosKeyReleased(evt);
            }
        });
        jScrollPane2.setViewportView(tablePagos);

        busquedaCampo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                busquedaCampoKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                busquedaCampoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                busquedaCampoKeyTyped(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        jLabel7.setText("Buscar:");

        jLabel8.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        jLabel8.setText("Deuda:");

        jLabel9.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        jLabel9.setText("Abono:");

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/salvar.png"))); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButton4MousePressed(evt);
            }
        });
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jLabel28.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Logo.png"))); // NOI18N

        javax.swing.GroupLayout panelAbonosLayout = new javax.swing.GroupLayout(panelAbonos);
        panelAbonos.setLayout(panelAbonosLayout);
        panelAbonosLayout.setHorizontalGroup(
            panelAbonosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAbonosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelAbonosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 359, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelAbonosLayout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(18, 18, 18)
                        .addComponent(busquedaCampo, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(panelAbonosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelAbonosLayout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addGroup(panelAbonosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton4)
                            .addComponent(jButton2)))
                    .addGroup(panelAbonosLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(panelAbonosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel28)
                            .addGroup(panelAbonosLayout.createSequentialGroup()
                                .addGroup(panelAbonosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(panelAbonosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(abonoCampo)
                                    .addComponent(deudaCampo, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap(177, Short.MAX_VALUE))
        );
        panelAbonosLayout.setVerticalGroup(
            panelAbonosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAbonosLayout.createSequentialGroup()
                .addGroup(panelAbonosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelAbonosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(busquedaCampo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel7)
                        .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelAbonosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelAbonosLayout.createSequentialGroup()
                        .addGroup(panelAbonosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(deudaCampo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8))
                        .addGap(18, 18, 18)
                        .addGroup(panelAbonosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(abonoCampo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(78, Short.MAX_VALUE))
        );

        panelRegistros.setPreferredSize(new java.awt.Dimension(666, 379));

        tableRegistros.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane3.setViewportView(tableRegistros);

        tableRegistrosCampista.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane4.setViewportView(tableRegistrosCampista);

        jLabel13.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        jLabel13.setText("Buscar:");

        busquedaCampoRegistros.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                busquedaCampoRegistrosKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                busquedaCampoRegistrosKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                busquedaCampoRegistrosKeyTyped(evt);
            }
        });

        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jLabel29.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Logo.png"))); // NOI18N

        javax.swing.GroupLayout panelRegistrosLayout = new javax.swing.GroupLayout(panelRegistros);
        panelRegistros.setLayout(panelRegistrosLayout);
        panelRegistrosLayout.setHorizontalGroup(
            panelRegistrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRegistrosLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(panelRegistrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(panelRegistrosLayout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(busquedaCampoRegistros, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton7)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelRegistrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel29, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(55, 55, 55))
        );
        panelRegistrosLayout.setVerticalGroup(
            panelRegistrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRegistrosLayout.createSequentialGroup()
                .addGroup(panelRegistrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelRegistrosLayout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addGroup(panelRegistrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(busquedaCampoRegistros, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13)
                            .addComponent(jButton7)))
                    .addComponent(jLabel29))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelRegistrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(151, Short.MAX_VALUE))
        );

        panelCaja.setPreferredSize(new java.awt.Dimension(666, 379));
        panelCaja.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel18.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        jLabel18.setText("Descripción:");
        panelCaja.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, -1, -1));

        jLabel19.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        jLabel19.setText("Cantidad:");
        panelCaja.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, -1));

        campoCajaCant.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                campoCajaCantActionPerformed(evt);
            }
        });
        panelCaja.add(campoCajaCant, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 20, 50, -1));
        panelCaja.add(campoCajaDescrip, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 50, 271, -1));

        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });
        panelCaja.add(jButton8, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 10, 40, 30));

        opcionDeposito.setText("Deposito");
        opcionDeposito.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                opcionDepositoMouseClicked(evt);
            }
        });
        opcionDeposito.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionDepositoActionPerformed(evt);
            }
        });
        panelCaja.add(opcionDeposito, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 10, -1, -1));

        opcionRetiro.setText("Retiro");
        opcionRetiro.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                opcionRetiroMouseClicked(evt);
            }
        });
        panelCaja.add(opcionRetiro, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 10, -1, -1));

        cajaTable.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane5.setViewportView(cajaTable);

        panelCaja.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 110, 527, 169));

        jLabel20.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        jLabel20.setText("Capital Total:");
        panelCaja.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 290, -1, -1));
        panelCaja.add(campoCapital, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 290, 60, -1));

        jLabel21.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel21.setText("Buscar:");
        panelCaja.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 80, -1, -1));

        campoBusquedaCaja.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                campoBusquedaCajaKeyReleased(evt);
            }
        });
        panelCaja.add(campoBusquedaCaja, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 80, 180, -1));

        jLabel30.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Logo.png"))); // NOI18N
        panelCaja.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 10, -1, -1));

        panelUsuarios.setPreferredSize(new java.awt.Dimension(666, 379));

        jLabel22.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        jLabel22.setText("Correo:");

        jLabel23.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        jLabel23.setText("Rol:");

        jLabel24.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        jLabel24.setText("Contraseña:");

        txtCorreo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCorreoActionPerformed(evt);
            }
        });

        txtRol.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "administrador", "normal" }));

        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jLabel31.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Logo.png"))); // NOI18N

        javax.swing.GroupLayout panelUsuariosLayout = new javax.swing.GroupLayout(panelUsuarios);
        panelUsuarios.setLayout(panelUsuariosLayout);
        panelUsuariosLayout.setHorizontalGroup(
            panelUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelUsuariosLayout.createSequentialGroup()
                .addGroup(panelUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelUsuariosLayout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(panelUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(panelUsuariosLayout.createSequentialGroup()
                                .addGroup(panelUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel24)
                                    .addComponent(jLabel23))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(panelUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtContraseña)
                                    .addComponent(txtRol, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelUsuariosLayout.createSequentialGroup()
                                .addComponent(jLabel22)
                                .addGap(45, 45, 45)
                                .addComponent(txtCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(44, 44, 44)
                        .addComponent(jLabel31))
                    .addGroup(panelUsuariosLayout.createSequentialGroup()
                        .addGap(153, 153, 153)
                        .addComponent(jButton9)))
                .addContainerGap(249, Short.MAX_VALUE))
        );
        panelUsuariosLayout.setVerticalGroup(
            panelUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelUsuariosLayout.createSequentialGroup()
                .addGroup(panelUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelUsuariosLayout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(panelUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelUsuariosLayout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addComponent(jLabel22))
                            .addComponent(txtCorreo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(panelUsuariosLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel31)))
                .addGap(18, 18, 18)
                .addGroup(panelUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtContraseña, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel24))
                .addGap(18, 18, 18)
                .addGroup(panelUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(txtRol, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(199, Short.MAX_VALUE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        panelMenu.setBackground(javax.swing.UIManager.getDefaults().getColor("Button.darcula.selection.color1"));

        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Formularios/35272hd.png"))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 48)); // NOI18N
        jLabel1.setText("¡Bienvenido!");

        javax.swing.GroupLayout panelMenuLayout = new javax.swing.GroupLayout(panelMenu);
        panelMenu.setLayout(panelMenuLayout);
        panelMenuLayout.setHorizontalGroup(
            panelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelMenuLayout.createSequentialGroup()
                .addGroup(panelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelMenuLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel25))
                    .addGroup(panelMenuLayout.createSequentialGroup()
                        .addGap(237, 237, 237)
                        .addComponent(jLabel17))
                    .addGroup(panelMenuLayout.createSequentialGroup()
                        .addGap(179, 179, 179)
                        .addComponent(jLabel1)))
                .addContainerGap(192, Short.MAX_VALUE))
        );
        panelMenuLayout.setVerticalGroup(
            panelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelMenuLayout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(jLabel17)
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 81, Short.MAX_VALUE)
                .addComponent(jLabel25)
                .addContainerGap())
        );

        jMenuBar1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jMenu1.setText("Campistas");
        jMenu1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenu1MouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jMenu1MousePressed(evt);
            }
        });
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Pagos");
        jMenu2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenu2MouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jMenu2MousePressed(evt);
            }
        });
        jMenuBar1.add(jMenu2);

        jMenu3.setText("Caja");
        jMenu3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jMenu3MousePressed(evt);
            }
        });
        jMenuBar1.add(jMenu3);

        jMenu4.setText("Usuarios");
        jMenu4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jMenu4MousePressed(evt);
            }
        });
        jMenuBar1.add(jMenu4);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelMenu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelMenu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenu1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenu1MouseClicked
        // TODO add your handling code here
      
    }//GEN-LAST:event_jMenu1MouseClicked

    private void tablePagosKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablePagosKeyReleased
        
    }//GEN-LAST:event_tablePagosKeyReleased

    private void jMenu2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenu2MouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_jMenu2MouseClicked

    private void busquedaCampoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_busquedaCampoKeyPressed
        //if (evt.getKeyCode() == KeyEvent.VK_ENTER)
          // busquedaCampista(busquedaCampo.getText());
    }//GEN-LAST:event_busquedaCampoKeyPressed

    private void busquedaCampoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_busquedaCampoKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_busquedaCampoKeyTyped

    private void busquedaCampoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_busquedaCampoKeyReleased
        // TODO add your handling code here:
        busquedaCampista(busquedaCampo.getText());
    }//GEN-LAST:event_busquedaCampoKeyReleased

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        String cantidad = "";
            try{
                String idcampista = tablePagos.getValueAt(tablePagos.getSelectedRow(), 0).toString();
                String SQL = "SELECT cantidad FROM pagos WHERE campistas_id = '"+idcampista+"';";
                ResultSet rs = Database.getTabla(SQL);
                while (rs.next())
                    cantidad = rs.getString("cantidad");
                deudaCampo.setText(cantidad);
                
            } catch (Exception e){
                JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
            }
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        crearAbono(abonoCampo.getText());
        JOptionPane.showMessageDialog(null,"¡Abono Realizado!");
        deudaCampo.setText("");
        abonoCampo.setText("");
        
    }//GEN-LAST:event_jButton2ActionPerformed

    private void busquedaCampoRegistrosKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_busquedaCampoRegistrosKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_busquedaCampoRegistrosKeyPressed

    private void busquedaCampoRegistrosKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_busquedaCampoRegistrosKeyReleased
        busquedaRegistro(busquedaCampoRegistros.getText());
    }//GEN-LAST:event_busquedaCampoRegistrosKeyReleased

    private void busquedaCampoRegistrosKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_busquedaCampoRegistrosKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_busquedaCampoRegistrosKeyTyped

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        String [] columnas = {"Abono", "Fecha"};
        String [] registro = new String[2];
        DefaultTableModel modelo = new DefaultTableModel(null,columnas);
        String id = tableRegistros.getValueAt(tableRegistros.getSelectedRow(), 0).toString();
        String SQL = "SELECT abono, fechaa from detalle_pagos INNER JOIN pagos on detalle_pagos.idpagos = pagos.idpagos where pagos.campistas_id ='"+id+"';";
        ResultSet rs = Database.getTabla(SQL);
        try{
           while (rs.next()){
               registro[0] = rs.getString("abono");
               registro[1] = rs.getString("fechaa");
               
               modelo.addRow(registro);
           } 
           
           tableRegistrosCampista.setModel(modelo);
           
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, e, "Error en los registros", JOptionPane.ERROR_MESSAGE);
        }
        
            
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_jButton4ActionPerformed

    private void jMenu1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenu1MousePressed
       panelMenu.setVisible(false);
       panelNuevoCampista.setVisible(false);
       panelAbonos.setVisible(false);
       panelRegistros.setVisible(false);
       panelCaja.setVisible(false);
       panelUsuarios.setVisible(false);
       this.getContentPane().remove(panelMenu);
       this.getContentPane().remove(panelCaja);
       this.getContentPane().remove(panelNuevoCampista);
       this.getContentPane().remove(panelAbonos);
       this.getContentPane().remove(panelRegistros);
       this.getContentPane().remove(panelUsuarios);
       mostrar();
       
       this.getContentPane().add(panelCampistas);
       panelCampistas.setLocation(0, 25);
       panelCampistas.setSize(666, 379);
       panelCampistas.setVisible(true);
    }//GEN-LAST:event_jMenu1MousePressed

    private void jMenu2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenu2MousePressed
       mostrarPagos();
       panelMenu.setVisible(false);
       panelNuevoCampista.setVisible(false);
       panelCampistas.setVisible(false);
       panelRegistros.setVisible(false);
       panelCaja.setVisible(false);
       panelUsuarios.setVisible(false);
       this.getContentPane().remove(panelCampistas);
       this.getContentPane().remove(panelMenu);
       this.getContentPane().remove(panelNuevoCampista);
       this.getContentPane().remove(panelRegistros);
       this.getContentPane().remove(panelCaja);
       this.getContentPane().remove(panelUsuarios);
       this.getContentPane().add(panelAbonos);
       panelAbonos.setLocation(0, 25);
       panelAbonos.setSize(666, 379);
       panelAbonos.setVisible(true);
    }//GEN-LAST:event_jMenu2MousePressed

    private void jButton4MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton4MousePressed
       panelAbonos.setVisible(false);
       this.getContentPane().remove(panelAbonos);
       this.getContentPane().add(panelRegistros);
       panelRegistros.setLocation(0, 25);
       panelRegistros.setSize(666, 379);
       panelRegistros.setVisible(true);
       mostrarRegistros();
      
    }//GEN-LAST:event_jButton4MousePressed

    private void campoCajaCantActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_campoCajaCantActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_campoCajaCantActionPerformed

    private void jMenu3MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenu3MousePressed
       
       panelMenu.setVisible(false);
       panelNuevoCampista.setVisible(false);
       panelAbonos.setVisible(false);
       panelRegistros.setVisible(false);
       panelCampistas.setVisible(false);
       panelUsuarios.setVisible(false);
       this.getContentPane().remove(panelMenu);
       this.getContentPane().remove(panelNuevoCampista);
       this.getContentPane().remove(panelAbonos);
       this.getContentPane().remove(panelRegistros);
       this.getContentPane().remove(panelCampistas);
       this.getContentPane().remove(panelUsuarios);
       
       mostrarCaja();
       sumaCapital();
       this.getContentPane().add(panelCaja);
       panelCaja.setLocation(0, 25);
       panelCaja.setSize(666, 379);
       panelCaja.setVisible(true);
    }//GEN-LAST:event_jMenu3MousePressed

    private void opcionDepositoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_opcionDepositoMouseClicked

          opcionRetiro.setSelected(false);
    }//GEN-LAST:event_opcionDepositoMouseClicked

    private void opcionDepositoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionDepositoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_opcionDepositoActionPerformed

    private void opcionRetiroMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_opcionRetiroMouseClicked
         opcionDeposito.setSelected(false);
    }//GEN-LAST:event_opcionRetiroMouseClicked

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        caja();
        JOptionPane.showMessageDialog(null,"¡Transacción Realizada!");
        mostrarCaja();
        sumaCapital();
    }//GEN-LAST:event_jButton8ActionPerformed

    private void campoBusquedaCajaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_campoBusquedaCajaKeyReleased
        busquedaCaja(campoBusquedaCaja.getText());
    }//GEN-LAST:event_campoBusquedaCajaKeyReleased

    private void txtCorreoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCorreoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCorreoActionPerformed

    private void jMenu4MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenu4MousePressed
        // TODO add your handling code here:
       panelMenu.setVisible(false);
       panelNuevoCampista.setVisible(false);
       panelAbonos.setVisible(false);
       panelRegistros.setVisible(false);
       panelCampistas.setVisible(false);
       panelCaja.setVisible(false);
       this.getContentPane().remove(panelMenu);
       this.getContentPane().remove(panelNuevoCampista);
       this.getContentPane().remove(panelAbonos);
       this.getContentPane().remove(panelRegistros);
       this.getContentPane().remove(panelCampistas);
       this.getContentPane().remove(panelCaja);
       
      
       this.getContentPane().add(panelUsuarios);
       panelUsuarios.setLocation(0, 25);
       panelUsuarios.setSize(666, 379);
       panelUsuarios.setVisible(true);
    }//GEN-LAST:event_jMenu4MousePressed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        insertarUsuario();
    }//GEN-LAST:event_jButton9ActionPerformed

    private void busquedaCampo1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_busquedaCampo1KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_busquedaCampo1KeyTyped

    private void busquedaCampo1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_busquedaCampo1KeyReleased
        // TODO add your handling code here:
        busquedaCampistas(busquedaCampo1.getText());
    }//GEN-LAST:event_busquedaCampo1KeyReleased

    private void busquedaCampo1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_busquedaCampo1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_busquedaCampo1KeyPressed

    private void tableKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tableKeyReleased
        // TODO add your handling code here:
        if (evt.getKeyCode()==KeyEvent.VK_ENTER) {
            try{
                String idcampista = table.getValueAt(table.getSelectedRow(), 0).toString();
                String nombre = table.getValueAt(table.getSelectedRow(), 1).toString();
                String apellido = table.getValueAt(table.getSelectedRow(), 2).toString();
                String direccion = table.getValueAt(table.getSelectedRow(), 3).toString();
                String telefono= table.getValueAt(table.getSelectedRow(), 4).toString();
                String staff = table.getValueAt(table.getSelectedRow(), 5).toString();
                String sql = "UPDATE campistas SET nombre='" + nombre + "',apellido='" +
                apellido + "',direccion='" + direccion + "',telefono='" + telefono + "',staff='" + staff + "' WHERE idcampistas='" + idcampista + "';";
                Database.ejecutarQuery(sql);
                JOptionPane.showMessageDialog(null, "¡Registro Actualizado!");
            } catch (Exception e){

            }
        }
    }//GEN-LAST:event_tableKeyReleased

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Principal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField abonoCampo;
    private javax.swing.JTextField apellidoCampista;
    private javax.swing.JTextField busquedaCampo;
    private javax.swing.JTextField busquedaCampo1;
    private javax.swing.JTextField busquedaCampoRegistros;
    private javax.swing.JTable cajaTable;
    private javax.swing.JTextField campoBusquedaCaja;
    private javax.swing.JTextField campoCajaCant;
    private javax.swing.JTextField campoCajaDescrip;
    private javax.swing.JTextField campoCapital;
    private javax.swing.JTextField deudaCampo;
    private javax.swing.JTextField direccionCampista;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    public javax.swing.JMenu jMenu4;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTextField nombreCampista;
    private javax.swing.JRadioButton opcionDeposito;
    private javax.swing.JRadioButton opcionRetiro;
    private javax.swing.JPanel panelAbonos;
    private javax.swing.JPanel panelCaja;
    private javax.swing.JPanel panelCampistas;
    private javax.swing.JPanel panelMenu;
    private javax.swing.JPanel panelNuevoCampista;
    private javax.swing.JPanel panelRegistros;
    private javax.swing.JPanel panelUsuarios;
    private javax.swing.JCheckBox staffCheck;
    private javax.swing.JTable table;
    private javax.swing.JTable tablePagos;
    private javax.swing.JTable tableRegistros;
    private javax.swing.JTable tableRegistrosCampista;
    private javax.swing.JTextField telefonoCampista;
    private javax.swing.JTextField txtContraseña;
    private javax.swing.JTextField txtCorreo;
    private javax.swing.JComboBox txtRol;
    // End of variables declaration//GEN-END:variables
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Formularios;

import Clases.Database;
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

    }
    
    private void mostrar() {        
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
        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        busquedaCampo1 = new javax.swing.JTextField();
        panelNuevoCampista = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        nombreCampista = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        apellidoCampista = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        direccionCampista = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        staffCheck = new javax.swing.JCheckBox();
        telefonoCampista = new javax.swing.JTextField();
        jButton5 = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
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
        jLabel10 = new javax.swing.JLabel();
        jButton6 = new javax.swing.JButton();
        panelRegistros = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tableRegistros = new javax.swing.JTable();
        jScrollPane4 = new javax.swing.JScrollPane();
        tableRegistrosCampista = new javax.swing.JTable();
        jLabel13 = new javax.swing.JLabel();
        busquedaCampoRegistros = new javax.swing.JTextField();
        jButton7 = new javax.swing.JButton();
        panelCaja = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
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
        panelMenu = new javax.swing.JPanel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();
        jMenu3 = new javax.swing.JMenu();

        panelCampistas.setPreferredSize(new java.awt.Dimension(666, 379));

        table.setModel(new javax.swing.table.DefaultTableModel(
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
        table.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tableKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(table);

        jButton1.setText("Nuevo");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton3.setText("Eliminar");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Verdana", 1, 24)); // NOI18N
        jLabel1.setText("CAMPISTAS");

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

        javax.swing.GroupLayout panelCampistasLayout = new javax.swing.GroupLayout(panelCampistas);
        panelCampistas.setLayout(panelCampistasLayout);
        panelCampistasLayout.setHorizontalGroup(
            panelCampistasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCampistasLayout.createSequentialGroup()
                .addGroup(panelCampistasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelCampistasLayout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addComponent(jLabel1))
                    .addGroup(panelCampistasLayout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(panelCampistasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelCampistasLayout.createSequentialGroup()
                                .addComponent(jLabel14)
                                .addGap(18, 18, 18)
                                .addComponent(busquedaCampo1, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelCampistasLayout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 496, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
                                .addGroup(panelCampistasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addGap(25, 25, 25))
        );
        panelCampistasLayout.setVerticalGroup(
            panelCampistasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCampistasLayout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(panelCampistasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(busquedaCampo1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(panelCampistasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelCampistasLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(143, 143, 143))
                    .addGroup(panelCampistasLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(46, Short.MAX_VALUE))))
        );

        panelNuevoCampista.setPreferredSize(new java.awt.Dimension(666, 379));

        jLabel2.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        jLabel2.setText("Nombre:");

        jLabel3.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        jLabel3.setText("Apellido:");

        jLabel4.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        jLabel4.setText("Dirección:");

        jLabel5.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        jLabel5.setText("Teléfono:");

        jLabel6.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        jLabel6.setText("Staff:");

        jButton5.setText("Guardar");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel3)
                            .addComponent(jLabel5))
                        .addGap(26, 26, 26)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(telefonoCampista, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 165, Short.MAX_VALUE))
                            .addComponent(direccionCampista)
                            .addComponent(nombreCampista)
                            .addComponent(apellidoCampista)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(32, 32, 32)
                                .addComponent(jLabel6)
                                .addGap(26, 26, 26)
                                .addComponent(staffCheck)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 306, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(175, 175, 175))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(nombreCampista, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(apellidoCampista, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(direccionCampista, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(telefonoCampista, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(staffCheck, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(19, 19, 19)
                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel11.setFont(new java.awt.Font("Verdana", 1, 24)); // NOI18N
        jLabel11.setText("NUEVO CAMPISTA");

        javax.swing.GroupLayout panelNuevoCampistaLayout = new javax.swing.GroupLayout(panelNuevoCampista);
        panelNuevoCampista.setLayout(panelNuevoCampistaLayout);
        panelNuevoCampistaLayout.setHorizontalGroup(
            panelNuevoCampistaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelNuevoCampistaLayout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addGroup(panelNuevoCampistaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(56, Short.MAX_VALUE))
        );
        panelNuevoCampistaLayout.setVerticalGroup(
            panelNuevoCampistaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelNuevoCampistaLayout.createSequentialGroup()
                .addContainerGap(15, Short.MAX_VALUE)
                .addComponent(jLabel11)
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41))
        );

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

        jButton2.setText("Guardar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton4.setText("Ver registros");
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

        jLabel10.setFont(new java.awt.Font("Verdana", 1, 24)); // NOI18N
        jLabel10.setText("ABONOS");

        jButton6.setText("Seleccionar");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelAbonosLayout = new javax.swing.GroupLayout(panelAbonos);
        panelAbonos.setLayout(panelAbonosLayout);
        panelAbonosLayout.setHorizontalGroup(
            panelAbonosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAbonosLayout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(panelAbonosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addGroup(panelAbonosLayout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(18, 18, 18)
                        .addComponent(busquedaCampo, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton6))
                    .addGroup(panelAbonosLayout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 359, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(panelAbonosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelAbonosLayout.createSequentialGroup()
                                .addGap(71, 71, 71)
                                .addGroup(panelAbonosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(panelAbonosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(abonoCampo)
                                    .addComponent(deudaCampo, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(panelAbonosLayout.createSequentialGroup()
                                .addGap(84, 84, 84)
                                .addGroup(panelAbonosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap(71, Short.MAX_VALUE))
        );
        panelAbonosLayout.setVerticalGroup(
            panelAbonosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAbonosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                .addGroup(panelAbonosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(busquedaCampo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(jButton6))
                .addGroup(panelAbonosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelAbonosLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelAbonosLayout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addGroup(panelAbonosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(deudaCampo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8))
                        .addGap(18, 18, 18)
                        .addGroup(panelAbonosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(abonoCampo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9))
                        .addGap(32, 32, 32)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(46, 46, 46))
        );

        panelRegistros.setPreferredSize(new java.awt.Dimension(666, 379));

        jLabel12.setFont(new java.awt.Font("Verdana", 1, 24)); // NOI18N
        jLabel12.setText("REGISTROS");

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

        jButton7.setText("Seleccionar");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelRegistrosLayout = new javax.swing.GroupLayout(panelRegistros);
        panelRegistros.setLayout(panelRegistrosLayout);
        panelRegistrosLayout.setHorizontalGroup(
            panelRegistrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRegistrosLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(panelRegistrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelRegistrosLayout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(panelRegistrosLayout.createSequentialGroup()
                        .addGroup(panelRegistrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addGroup(panelRegistrosLayout.createSequentialGroup()
                                .addComponent(jLabel13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(busquedaCampoRegistros, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton7)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(37, 37, 37))))
        );
        panelRegistrosLayout.setVerticalGroup(
            panelRegistrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRegistrosLayout.createSequentialGroup()
                .addComponent(jLabel12)
                .addGap(31, 31, 31)
                .addGroup(panelRegistrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(busquedaCampoRegistros, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13)
                    .addComponent(jButton7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelRegistrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(109, Short.MAX_VALUE))
        );

        panelCaja.setPreferredSize(new java.awt.Dimension(666, 379));

        jLabel15.setFont(new java.awt.Font("Verdana", 1, 24)); // NOI18N
        jLabel15.setText("CAJA");

        jLabel18.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        jLabel18.setText("Descripción:");

        jLabel19.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        jLabel19.setText("Cantidad:");

        campoCajaCant.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                campoCajaCantActionPerformed(evt);
            }
        });

        jButton8.setText("Aceptar");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

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

        opcionRetiro.setText("Retiro");
        opcionRetiro.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                opcionRetiroMouseClicked(evt);
            }
        });

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

        jLabel20.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        jLabel20.setText("Capital Total:");

        jLabel21.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel21.setText("Buscar:");

        campoBusquedaCaja.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                campoBusquedaCajaKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout panelCajaLayout = new javax.swing.GroupLayout(panelCaja);
        panelCaja.setLayout(panelCajaLayout);
        panelCajaLayout.setHorizontalGroup(
            panelCajaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCajaLayout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(panelCajaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel19)
                    .addComponent(jLabel18))
                .addGap(32, 32, 32)
                .addGroup(panelCajaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelCajaLayout.createSequentialGroup()
                        .addComponent(campoCajaCant, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(41, 41, 41)
                        .addComponent(opcionDeposito)
                        .addGap(18, 18, 18)
                        .addComponent(opcionRetiro))
                    .addComponent(campoCajaDescrip, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 62, Short.MAX_VALUE)
                .addComponent(jButton8)
                .addGap(94, 94, 94))
            .addGroup(panelCajaLayout.createSequentialGroup()
                .addGroup(panelCajaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelCajaLayout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(jLabel15))
                    .addGroup(panelCajaLayout.createSequentialGroup()
                        .addGap(62, 62, 62)
                        .addGroup(panelCajaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(panelCajaLayout.createSequentialGroup()
                                .addComponent(jLabel20)
                                .addGap(18, 18, 18)
                                .addComponent(campoCapital, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 527, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelCajaLayout.createSequentialGroup()
                                .addComponent(jLabel21)
                                .addGap(18, 18, 18)
                                .addComponent(campoBusquedaCaja, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelCajaLayout.setVerticalGroup(
            panelCajaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCajaLayout.createSequentialGroup()
                .addComponent(jLabel15)
                .addGroup(panelCajaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelCajaLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelCajaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel19)
                            .addComponent(campoCajaCant, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(opcionDeposito)
                            .addComponent(opcionRetiro))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelCajaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel18)
                            .addComponent(campoCajaDescrip, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(panelCajaLayout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelCajaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(campoBusquedaCaja, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelCajaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel21)))
                .addGap(4, 4, 4)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelCajaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(campoCapital, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(64, Short.MAX_VALUE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        panelMenu.setBackground(new java.awt.Color(204, 204, 255));

        javax.swing.GroupLayout panelMenuLayout = new javax.swing.GroupLayout(panelMenu);
        panelMenu.setLayout(panelMenuLayout);
        panelMenuLayout.setHorizontalGroup(
            panelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 666, Short.MAX_VALUE)
        );
        panelMenuLayout.setVerticalGroup(
            panelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 377, Short.MAX_VALUE)
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

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
                
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        // TODO add your handling code here:
        panelCampistas.setVisible(false);
        this.getContentPane().remove(panelCampistas);
        this.getContentPane().add(panelNuevoCampista);
        panelNuevoCampista.setLocation(0, 25);
        panelNuevoCampista.setSize(666, 379);
        panelNuevoCampista.setVisible(true);
        panelNuevoCampista.setVisible(true);
    }//GEN-LAST:event_jButton1MouseClicked

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        insertarCampista();
    }//GEN-LAST:event_jButton5ActionPerformed

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

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        String id = table.getValueAt(table.getSelectedRow(), 0).toString();
        String sql = "UPDATE campistas SET inhabilitado = 1 WHERE  idcampistas='" + id + "'";
        //String sql = "DELETE From campistas WHERE idcampistas='" + id + "'";
        Database.ejecutarQuery(sql);
        JOptionPane.showMessageDialog(null, "¡Registro eliminado!");
        mostrar();
    }//GEN-LAST:event_jButton3ActionPerformed

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
       this.getContentPane().remove(panelMenu);
       this.getContentPane().remove(panelCaja);
       this.getContentPane().remove(panelNuevoCampista);
       this.getContentPane().remove(panelAbonos);
       this.getContentPane().remove(panelRegistros);
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
       this.getContentPane().remove(panelCampistas);
       this.getContentPane().remove(panelMenu);
       this.getContentPane().remove(panelNuevoCampista);
       this.getContentPane().remove(panelRegistros);
       this.getContentPane().remove(panelCaja);
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

    private void busquedaCampo1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_busquedaCampo1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_busquedaCampo1KeyPressed

    private void busquedaCampo1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_busquedaCampo1KeyReleased
        // TODO add your handling code here:
        busquedaCampistas(busquedaCampo1.getText());
    }//GEN-LAST:event_busquedaCampo1KeyReleased

    private void busquedaCampo1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_busquedaCampo1KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_busquedaCampo1KeyTyped

    private void campoCajaCantActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_campoCajaCantActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_campoCajaCantActionPerformed

    private void jMenu3MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenu3MousePressed
       
       panelMenu.setVisible(false);
       panelNuevoCampista.setVisible(false);
       panelAbonos.setVisible(false);
       panelRegistros.setVisible(false);
       panelCampistas.setVisible(false);
       this.getContentPane().remove(panelMenu);
       this.getContentPane().remove(panelNuevoCampista);
       this.getContentPane().remove(panelAbonos);
       this.getContentPane().remove(panelRegistros);
       this.getContentPane().remove(panelCampistas);
       
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
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
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
    private javax.swing.JCheckBox staffCheck;
    private javax.swing.JTable table;
    private javax.swing.JTable tablePagos;
    private javax.swing.JTable tableRegistros;
    private javax.swing.JTable tableRegistrosCampista;
    private javax.swing.JTextField telefonoCampista;
    // End of variables declaration//GEN-END:variables
}

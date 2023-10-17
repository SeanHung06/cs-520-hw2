package view;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.text.NumberFormat;

import model.Transaction;
import java.util.List;

public class ExpenseTrackerView extends JFrame {

  private JTable transactionsTable;
  private JButton addTransactionBtn;
  private JFormattedTextField amountField;
  private JTextField categoryField;
  private DefaultTableModel model;

  private JComboBox<String> filterTypeDropdown;
  private JTextField categoryFilterField;
  private JTextField amountFilterField;
  private JButton filterButton;



  public ExpenseTrackerView() {
    setTitle("Expense Tracker"); // Set title
    setSize(600, 400); // Make GUI larger

    String[] columnNames = {"serial", "Amount", "Category", "Date"};
    this.model = new DefaultTableModel(columnNames, 0);

    addTransactionBtn = new JButton("Add Transaction");

    // Create UI components
    JLabel amountLabel = new JLabel("Amount:");
    NumberFormat format = NumberFormat.getNumberInstance();

    amountField = new JFormattedTextField(format);
    amountField.setColumns(10);

    
    JLabel categoryLabel = new JLabel("Category:");
    categoryField = new JTextField(10);

    // Create table
    transactionsTable = new JTable(model);
  
    // Layout components
    JPanel inputPanel = new JPanel();
    inputPanel.add(amountLabel);
    inputPanel.add(amountField);
    inputPanel.add(categoryLabel); 
    inputPanel.add(categoryField);
    inputPanel.add(addTransactionBtn);
    
    JPanel buttonPanel = new JPanel();
    buttonPanel.add(addTransactionBtn);
  
    // Add panels to frame
    add(inputPanel, BorderLayout.NORTH);
    add(new JScrollPane(transactionsTable), BorderLayout.CENTER); 
    add(buttonPanel, BorderLayout.SOUTH);
  
    // Set frame properties
    setSize(400, 300);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);

    JPanel FilterPanel = new JPanel();
    add(FilterPanel, BorderLayout.AFTER_LAST_LINE);

    // Add Filter Dropdown 
    String[] filterTypes = {"Amount", "Category"};
    filterTypeDropdown = new JComboBox<>(filterTypes);
    FilterPanel.add(filterTypeDropdown);

    // Add input fileds for filter criteria
    categoryFilterField = new JTextField(15);
    FilterPanel.add(categoryFilterField);

    amountFilterField = new JTextField(15);
    // FilterPanel.add(amountFilterField);

    // Add filter button
    filterButton = new JButton("Filter");
    FilterPanel.add(filterButton);

    // Initialize the table
    FilterPanel.add(new JScrollPane(transactionsTable));
  }


  public JComboBox<String> getFilterTypeDropDown(){
    return filterTypeDropdown;
  }

  public JTextField getCategoryFilterField(){
    return categoryFilterField;
  }
  public JTextField getAmountFilterField() {
    return amountFilterField;

  }


  public JButton getFilterButton(){
    return filterButton;
  }

  public void highlightFilteredTransactions_category(List<Transaction> filteredTransactions) {
    setupHighlightingRenderer_category(filteredTransactions);
    transactionsTable.updateUI();
}
  public void highlightFilteredTransactions_amount(List<Transaction> filteredTransactions) {
    setupHighlightingRenderer_amount(filteredTransactions);
    transactionsTable.updateUI();
  }
  private void setupHighlightingRenderer_category(List<Transaction> filteredTransactions) {
    transactionsTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
      @Override
      public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                     boolean hasFocus, int row, int column) {
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        String category = (String) table.getModel().getValueAt(row, 2);

        boolean isFiltered = false;

        for (Transaction filteredTransaction : filteredTransactions) {
          if (filteredTransaction.getCategory().equals(category)) {
            isFiltered = true;
            break;
          }
        }
        if (isFiltered) {
          c.setBackground(new Color(173, 255, 168)); // Light green
        } else {
          c.setBackground(table.getBackground());
        }
        return c;
      }
    });
  }
  private void setupHighlightingRenderer_amount(List<Transaction> filteredTransactions) {
    transactionsTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
      @Override
      public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                     boolean hasFocus, int row, int column) {
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        Object amountObj = table.getModel().getValueAt(row, 1);
        double amount = 0;
        if (amountObj != null) {
          amount = (double) amountObj;
        }
        boolean isFiltered = false;

        for (Transaction filteredTransaction : filteredTransactions) {
          if (filteredTransaction.getAmount() == amount) {
            isFiltered = true;
            break;
          }
        }
        if (isFiltered) {
          c.setBackground(new Color(173, 255, 168)); // Light green
        } else {
          c.setBackground(table.getBackground());
        }
        return c;
      }
    });
  }

  public void refreshTable(List<Transaction> transactions) {
      // Clear existing rows
      model.setRowCount(0);
      // Get row count
      int rowNum = model.getRowCount();
      double totalCost=0;
      // Calculate total cost
      for(Transaction t : transactions) {
        totalCost+=t.getAmount();
      }
      // Add rows from transactions list
      for(Transaction t : transactions) {
        model.addRow(new Object[]{rowNum+=1,t.getAmount(), t.getCategory(), t.getTimestamp()}); 
      }
        // Add total row
        Object[] totalRow = {"Total", null, null, totalCost};
        model.addRow(totalRow);
  
      // Fire table update
      transactionsTable.updateUI();
  
    }  
  

  
  
  public JButton getAddTransactionBtn() {
    return addTransactionBtn;
  }
  public DefaultTableModel getTableModel() {
    return model;
  }
  // Other view methods
    public JTable getTransactionsTable() {
    return transactionsTable;
  }

  public double getAmountField() {
    if(amountField.getText().isEmpty()) {
      return 0;
    }else {
    double amount = Double.parseDouble(amountField.getText());
    return amount;
    }
  }

  public void setAmountField(JFormattedTextField amountField) {
    this.amountField = amountField;
  }

  
  public String getCategoryField() {
    return categoryField.getText();
  }

  public void setCategoryField(JTextField categoryField) {
    this.categoryField = categoryField;
  }
}
